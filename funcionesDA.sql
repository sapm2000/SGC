CREATE TABLE cuenta_pagar(
	id serial NOT NULL PRIMARY KEY,
	num_ref character varying(10) NOT NULL,
	forma_pago character varying(25) NOT NULL,
	descripcion character varying(60) NOT NULL,
	monto float NOT NULL,
	fecha date NOT NULL,
	id_proveedor character varying(10) NOT NULL REFERENCES proveedores(cedula),
	id_cuenta character varying(20) NOT NULL REFERENCES cuenta(n_cuenta),
	id_fondo integer NOT NULL REFERENCES fondos(id)
	
);

CREATE OR REPLACE FUNCTION actualizar_status(id2 int) RETURNS void AS $$
DECLARE
	saldo2 int;
	estado2 varchar;
BEGIN
saldo2 := (SELECT saldo FROM gasto_comun WHERE id = id2);
estado2 := (SELECT estado FROM gasto_comun WHERE id = id2);

IF saldo2 = 0 THEN
	IF estado2 = 'Pendiente' THEN
		UPDATE gasto_comun SET estado = 'Pagado' WHERE id = id2;
	ELSIF estado2 = 'Procesado' THEN
		UPDATE gasto_comun SET estado = 'Procesado y pagado' WHERE id = id2;
	END IF;
END IF;
END;
$$ LANGUAGE plpgsql;
