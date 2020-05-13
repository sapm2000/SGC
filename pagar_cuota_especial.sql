CREATE TABLE pagar_cuota_especial(
	id serial NOT NULL PRIMARY KEY,
	numero_ref character varying(10) NOT NULL,
	forma_pago character varying(30) NOT NULL,
	descripcion character varying(200) NOT NULL,
	monto double precision NOT NULL,
	fecha date NOT NULL,
	id_cuenta character varying NOT NULL REFERENCES cuenta(n_cuenta),
	id_fondo integer NOT NULL REFERENCES fondos(id),
	id_cuota_e integer NOT NULL REFERENCES cuotas_especiales(id)
);

CREATE OR REPLACE FUNCTION actualizar_status_cuotas(id2 integer)
    RETURNS void AS $$
DECLARE
	saldo2 int;
BEGIN
saldo2 := (SELECT saldo FROM cuotas_especiales WHERE id = id2);

IF saldo2 = 0 THEN
	UPDATE cuotas_especiales SET pagado = 'Pagado' WHERE id = id2;
END IF;
END;
$$ LANGUAGE plpgsql;