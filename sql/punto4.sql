-- primera funcion con su trigger

-------- funciones mayúsculas --------

-- mayuscula_asambleas
-- DROP FUNCTION mayuscula_asamblea();
CREATE OR REPLACE FUNCTION mayuscula_asambleas() RETURNS TRIGGER AS $$
BEGIN

	NEW.nombre = UPPER(NEW.nombre);
	NEW.descripcion = UPPER(NEW.descripcion);

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--------- Trigers mayúsculas --------
-- tg_mayuscula_asamblea
CREATE TRIGGER tg_mayuscula_asamblea
BEFORE INSERT OR UPDATE
ON asamblea
FOR EACH ROW
EXECUTE PROCEDURE mayuscula_cargo();



-- 2 trigger

-- calcular_alicuota
CREATE OR REPLACE FUNCTION calcular_alicuota() RETURNS TRIGGER AS $$
DECLARE
	area_total double precision;
BEGIN
	area_total := (SELECT SUM(tp.area) FROM unidad AS u INNER JOIN tipo_unidad AS tp ON u.id_tipo = tp.id WHERE 	 u.activo = true);
		
	UPDATE unidad SET alicuota = (SELECT area FROM tipo_unidad WHERE id = id_tipo) / area_total;
	
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--tg_calcular_alicuota
CREATE TRIGGER tg_calcular_alicuota
AFTER INSERT OR UPDATE OF n_documento, direccion, id_tipo
ON unidad
FOR EACH STATEMENT
EXECUTE PROCEDURE calcular_alicuota();

-- 3 trigger

-- eliminar_mensaje

-- eliminar_mensaje

CREATE OR REPLACE FUNCTION eliminar_mensaje() RETURNS TRIGGER AS $$
DECLARE
	emisor_bd boolean;
	receptor_bd integer;
BEGIN
	emisor_bd := true;
	RAISE INFO 'Consultando si el emisor eliminó el mensaje con id: %...', OLD.id;
	emisor_bd := (SELECT activo_emisor FROM mensaje AS msj WHERE msj.id = NEW.id);
	RAISE INFO 'El resultado es %', emisor_bd;

	IF emisor_bd = true THEN
		RAISE INFO 'Emisor no ha eliminado el mensaje';
		RETURN null;
	
	ELSE
		RAISE INFO 'Emisor eliminó el mensaje. Buscando en receptores...';
		receptor_bd := (SELECT COUNT(*) FROM puente_mensaje_usuario WHERE activo_receptor = true AND id_mensaje = OLD.id);
	
		IF receptor_bd = 0 THEN
			RAISE INFO 'Todos los receptores eliminaron el mensaje';
			DELETE FROM puente_mensaje_usuario WHERE id_mensaje = OLD.id;
			DELETE FROM mensaje WHERE id = old.id;
			
			RETURN NEW;
			
		ELSE
			RETURN null;
		END IF;
	END IF;
END;
$$ LANGUAGE plpgsql;
	
-- tg_eliminar_mensaje

CREATE TRIGGER tg_eliminar_mensaje
AFTER UPDATE
ON mensaje
FOR EACH ROW
EXECUTE PROCEDURE eliminar_mensaje();

-- tg_eliminar_puente_mensaje

CREATE TRIGGER tg_eliminar_puente_mensaje
AFTER UPDATE
ON puente_mensaje_usuario
FOR EACH ROW
EXECUTE PROCEDURE eliminar_mensaje();

-- 4 trigger

CREATE OR REPLACE FUNCTION pagar_gasto() RETURNS TRIGGER AS $$
DECLARE

	saldo_bd double precision;
	
BEGIN
	
	UPDATE fondos SET saldo = saldo - NEW.monto WHERE id = NEW.id_fondo;

	UPDATE gasto SET saldo = saldo - NEW.monto WHERE id = NEW.id_gasto;
	
	saldo_bd := (SELECT saldo FROM gasto WHERE id = NEW.id_gasto);

	IF saldo_bd = 0 THEN
	
		UPDATE gasto SET pagado = 'Pagado' WHERE id = NEW.id_gasto;
		
	END IF;
	
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- tg_restar_saldo
CREATE TRIGGER tg_restar_saldo
AFTER INSERT
ON cuenta_pagar
FOR EACH ROW
EXECUTE PROCEDURE pagar_gasto();

-- trigger bitacora + funciones

CREATE OR REPLACE FUNCTION agregar_cuenta(
	n_cuenta2 character varying,
	tipo2 character varying,
	id_banco2 integer,
	ci_rif2 character varying,
	id_usuario2 integer
) RETURNS boolean AS $$
DECLARE
	letra char;
	resul int;
BEGIN
	letra := SUBSTRING(ci_rif2 FROM 1 FOR 1);
	
	IF letra = 'V' OR letra = 'E' THEN
		INSERT INTO cuenta (n_cuenta, tipo, id_banco, ci_persona) VALUES (n_cuenta2, tipo2, id_banco2, ci_rif2);
		GET DIAGNOSTICS resul = ROW_COUNT;

	ELSIF letra = 'J' THEN
		INSERT INTO cuenta (n_cuenta, tipo, id_banco, rif_condominio) VALUES (n_cuenta2, tipo2, id_banco2, ci_rif2);
		GET DIAGNOSTICS resul = ROW_COUNT;
	END IF;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$ LANGUAGE plpgsql;

--tg_cuenta
CREATE TRIGGER tg_cuenta
BEFORE INSERT OR  UPDATE
ON cuenta
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

-- llenar_bitacora
CREATE OR REPLACE FUNCTION llenar_bitacora() RETURNS TRIGGER AS $$
BEGIN
	IF TG_OP = 'INSERT' THEN
		INSERT INTO bitacora (operacion, tabla, valor_nuevo)
		VALUES ('Registro', TG_TABLE_NAME, NEW);
		RETURN NEW;
		END IF;
	IF TG_OP = 'UPDATE' THEN
		INSERT INTO bitacora (operacion, tabla, valor_viejo, valor_nuevo)
		VALUES (TG_OP, TG_TABLE_NAME, OLD, NEW);
		RETURN NEW;
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- bitacora
CREATE TABLE IF NOT EXISTS bitacora (
	id_bitacora serial NOT NULL PRIMARY KEY,
	operacion text NOT NULL,
	tabla text NOT NULL,
	id_usuario int REFERENCES usuario(id),
	valor_viejo text,
	valor_nuevo text,
	fecha_hora timestamp without time zone DEFAULT LOCALTIMESTAMP(0)
);

-- consulta anidada

	SELECT u.id, n_unidad, n_documento, direccion, alicuota, tu.id AS id_tipo, tu.tipo, (SELECT area FROM tipo_unidad WHERE id = u.id_tipo) AS area
	FROM unidad AS u
	INNER JOIN tipo_unidad AS tu ON tu.id = u.id_tipo
	WHERE u.activo = true;

	-- vista bitacora

	CREATE OR REPLACE VIEW v_bitacora AS
	SELECT id_bitacora, operacion, tabla, usu.usuario, usu.ci_persona AS cedula,
	CONCAT(per.p_nombre,' ', per.p_apellido) AS persona, valor_viejo, valor_nuevo, fecha_hora AS fecha FROM bitacora 
	INNER JOIN usuario AS usu ON id_usuario = usu.id 
	INNER JOIN persona AS per ON cedula = usu.ci_persona;  

	-- inner join

	SELECT cp.id, cp.num_ref, cp.descripcion, cp.monto, cp.moneda, cp.tasa_cambio, cp.fecha, cp.id_gasto, ga.nombre AS gasto, cp.n_cuenta, cu.id_banco, b.nombre_banco AS banco, cp.id_fondo, f.tipo AS fondo, cp.id_forma_pago, fp.forma_pago
	FROM cuenta_pagar AS cp
	INNER JOIN gasto AS ga ON ga.id = cp.id_gasto
	INNER JOIN cuenta AS cu ON cu.n_cuenta = cp.n_cuenta
	INNER JOIN banco AS b ON b.id = cu.id_banco
	INNER JOIN fondos AS f ON f.id = cp.id_fondo
	INNER JOIN forma_pago AS fp ON fp.id = cp.id_forma_pago
	ORDER BY fecha DESC;