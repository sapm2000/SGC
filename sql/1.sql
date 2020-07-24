--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

-- Started on 2020-07-23 20:29:37


--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3516 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 296 (class 1255 OID 32504)
-- Name: agregar_asambleas(character varying, date, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_asambleas(nombre2 character varying, fecha2 date, descripcion2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO asambleas(nombre, descripcion, fecha) VALUES (nombre2, descripcion2, fecha2);
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul>0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
	
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_asambleas(nombre2 character varying, fecha2 date, descripcion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 309 (class 1255 OID 32505)
-- Name: agregar_banco(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_banco(nombre2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO banco (nombre_banco) VALUES (nombre2);
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_banco(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 312 (class 1255 OID 32509)
-- Name: agregar_categoria(integer, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO categoriagasto (nombre, descripcion) VALUES (nombre2, descripcion2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
		
	ELSE
		RETURN false;
	END IF;	
END;
$$;


ALTER FUNCTION public.agregar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying) OWNER TO postgres;

--
-- TOC entry 317 (class 1255 OID 32514)
-- Name: agregar_concepto(character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_concepto(nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES (nombre2, descripcion2, id_categoria2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_concepto(nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 321 (class 1255 OID 32518)
-- Name: agregar_condominio(integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO condominio (rif, razon_social, telefono, correo_electronico)
	VALUES(rif2, razon_social2, telefono2, correo_electronico2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
	
END;
$$;


ALTER FUNCTION public.agregar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) OWNER TO postgres;

--
-- TOC entry 323 (class 1255 OID 32520)
-- Name: agregar_cuenta(character varying, character varying, integer, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_cuenta(n_cuenta2 character varying, tipo2 character varying, id_banco2 integer, ci_rif2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.agregar_cuenta(n_cuenta2 character varying, tipo2 character varying, id_banco2 integer, ci_rif2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 327 (class 1255 OID 32524)
-- Name: agregar_cuenta_pagar(character varying, character varying, character varying, double precision, date, double precision, integer, integer, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_cuenta_pagar(descripcion2 character varying, num_ref2 character varying, moneda2 character varying, monto2 double precision, fecha2 date, tasa_cambio2 double precision, id_gasto2 integer, id_forma_pago2 integer, n_cuenta2 character varying, id_fondo2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO cuenta_pagar (descripcion, num_ref, moneda, monto, fecha, tasa_cambio, id_gasto,
	id_forma_pago, n_cuenta, id_fondo) VALUES (descripcion2, num_ref2, moneda2, monto2, fecha2,
	tasa_cambio2, id_gasto2, id_forma_pago2, n_cuenta2, id_fondo2);
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_cuenta_pagar(descripcion2 character varying, num_ref2 character varying, moneda2 character varying, monto2 double precision, fecha2 date, tasa_cambio2 double precision, id_gasto2 integer, id_forma_pago2 integer, n_cuenta2 character varying, id_fondo2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 328 (class 1255 OID 32525)
-- Name: agregar_fondos(integer, character varying, date, character varying, character varying, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_fondos(id_usuario2 integer, tipo2 character varying, fecha2 date, descripcion2 character varying, observaciones2 character varying, monto_inicial2 double precision, moneda2 character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda)
	VALUES (tipo2, fecha2, descripcion2, observaciones2, monto_inicial2, monto_inicial2, moneda2);
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
		
	ELSE
		RETURN false;
	END IF;	
END;
$$;


ALTER FUNCTION public.agregar_fondos(id_usuario2 integer, tipo2 character varying, fecha2 date, descripcion2 character varying, observaciones2 character varying, monto_inicial2 double precision, moneda2 character varying) OWNER TO postgres;

--
-- TOC entry 332 (class 1255 OID 32529)
-- Name: agregar_forma_pago(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_forma_pago(nombre2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO forma_pago (forma_pago) VALUES (nombre2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_forma_pago(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 336 (class 1255 OID 32533)
-- Name: agregar_gasto(character varying, character varying, character varying, character varying, integer, integer, integer, integer, text, integer, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_gasto(nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, moneda2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO gasto(nombre, tipo, id_proveedor, calcular_por, mes, anio, n_meses, observacion, id_asamblea, meses_restantes, monto, saldo, moneda) VALUES (nombre2, tipo2, id_proveedor2, calcular_por2, mes2, anio2, n_meses2, observacion2, id_asamblea2, meses_restantes2, monto2, monto2, moneda2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_gasto(nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 339 (class 1255 OID 32536)
-- Name: agregar_interes(character varying, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_interes(nombre2 character varying, factor2 double precision, rif_condominio2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO interes (nombre, factor, rif_condominio) VALUES (nombre2, factor2, rif_condominio2);
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
	 	UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_interes(nombre2 character varying, factor2 double precision, rif_condominio2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 343 (class 1255 OID 32540)
-- Name: agregar_propietario(character varying, character varying, character varying, character varying, character varying, character varying, character varying, boolean, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_propietario(_cedula character varying, _p_nombre character varying, _s_nombre character varying, _p_apellido character varying, _s_apellido character varying, _telefono character varying, _correo character varying, _existe boolean, _id_usuario integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul integer;
	fila RECORD;
BEGIN
	IF _existe = false THEN
		RAISE INFO 'Agregando en la tabla persona...';
		INSERT INTO persona (cedula, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo) VALUES (_cedula, _p_nombre, _s_nombre, _p_apellido, _s_apellido, _telefono, _correo) ON CONFLICT DO NOTHING;
		GET DIAGNOSTICS resul = ROW_COUNT;

		IF resul <> 1 THEN
			RAISE WARNING 'No se pudo agregar en persona';
			RETURN false;
		ELSE
			RAISE INFO 'Éxito';
			SELECT * INTO fila FROM persona WHERE cedula = _cedula;
		END IF;
	END IF;

	RAISE INFO 'Agregando en la tabla propietario...';
	INSERT INTO propietario (ci_persona) VALUES (_cedula) ON CONFLICT DO NOTHING;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul <> 1 THEN
		RAISE WARNING 'No se pudo agregar en propietario';
		RETURN false;

	ELSE
		RAISE INFO 'Éxito';
		RAISE INFO 'Actualizando la bitácora...';
		UPDATE bitacora SET id_usuario = _id_usuario, valor_nuevo = fila WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora) AND tabla = 'propietario';
		RETURN true;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_propietario(_cedula character varying, _p_nombre character varying, _s_nombre character varying, _p_apellido character varying, _s_apellido character varying, _telefono character varying, _correo character varying, _existe boolean, _id_usuario integer) OWNER TO postgres;

--
-- TOC entry 345 (class 1255 OID 32541)
-- Name: agregar_proveedor(character varying, character varying, character varying, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO proveedores(cedula, nombre, telefono, correo, contacto, direccion) VALUES (cedula2, nombre2, telefono2, correo2, contacto2, direccion2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
	 	UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 349 (class 1255 OID 32545)
-- Name: agregar_sancion(character varying, integer, integer, double precision, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, estado2 character varying, moneda2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO sancion(tipo, mes, anio, monto, descripcion,  estado, moneda) VALUES (tipo2, mes2, anio2, monto2, descripcion2,  estado2, moneda2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN	
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, estado2 character varying, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 352 (class 1255 OID 32548)
-- Name: agregar_tipo_unidad(character varying, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_tipo_unidad(tipo2 character varying, area2 double precision, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO tipo_unidad(tipo, area) VALUES (tipo2,area2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_tipo_unidad(tipo2 character varying, area2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 356 (class 1255 OID 32552)
-- Name: agregar_unidad(character varying, character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_unidad(n_unidad2 character varying, n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO unidad(n_unidad, n_documento, direccion, id_tipo) VALUES (n_unidad2, n_documento2, direccion2, id_tipo2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_unidad(n_unidad2 character varying, n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 360 (class 1255 OID 32556)
-- Name: agregar_visita(integer, character varying, integer, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_visita(id_unidad2 integer, ci_persona2 character varying, n_personas2 integer, matricula2 character varying, modelo2 character varying, color2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO visita(id_unidad, ci_visitante, n_personas, matricula, modelo, color) VALUES (id_unidad2, ci_persona2, n_personas2, matricula2, modelo2, color2);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.agregar_visita(id_unidad2 integer, ci_persona2 character varying, n_personas2 integer, matricula2 character varying, modelo2 character varying, color2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 344 (class 1255 OID 32578)
-- Name: calcular_alicuota(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.calcular_alicuota() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	area_total double precision;
BEGIN
	area_total := (SELECT SUM(tp.area) FROM unidad AS u INNER JOIN tipo_unidad AS tp ON u.id_tipo = tp.id WHERE 	 u.activo = true);
		
	UPDATE unidad SET alicuota = (SELECT area FROM tipo_unidad WHERE id = id_tipo) / area_total;
	
RETURN NEW;
END;
$$;


ALTER FUNCTION public.calcular_alicuota() OWNER TO postgres;

--
-- TOC entry 292 (class 1255 OID 32502)
-- Name: cambiar_clave(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cambiar_clave(usuario2 character varying, password_nuevo character varying, password_actual character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$ 
DECLARE
	password_bd character varying;
	usuario_bd character varying;

BEGIN
	usuario_bd := (SELECT usuario FROM usuario where usuario=usuario2 AND password=password_actual);
	password_bd := (SELECT password FROM usuario where usuario=usuario2 AND password=password_actual);
	
	IF password_actual = password_bd AND usuario2 = usuario_bd THEN
		UPDATE usuario SET password = password_nuevo WHERE usuario = usuario2;
		RETURN TRUE;
	
	ELSE 
		RETURN FALSE;
	END IF;
	END;
$$;


ALTER FUNCTION public.cambiar_clave(usuario2 character varying, password_nuevo character varying, password_actual character varying) OWNER TO postgres;

--
-- TOC entry 290 (class 1255 OID 32501)
-- Name: cambiar_pregunta(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cambiar_pregunta(usuario2 character varying, pregunta2 character varying, respuesta2 character varying, password2 character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	password_bd character varying;
	usuario_bd character varying;

BEGIN
	usuario_bd := (SELECT usuario FROM usuario where usuario=usuario2 AND password=password2);
	password_bd := (SELECT password FROM usuario where usuario=usuario2 AND password=password2);
	
	IF password2 = password_bd AND usuario2 = usuario_bd THEN
		UPDATE usuario SET pregunta = pregunta2, respuesta = respuesta2 WHERE usuario = usuario2;
		RETURN TRUE;
	
	ELSE 
		RETURN FALSE;
	END IF;
	END;
$$;


ALTER FUNCTION public.cambiar_pregunta(usuario2 character varying, pregunta2 character varying, respuesta2 character varying, password2 character varying) OWNER TO postgres;

--
-- TOC entry 291 (class 1255 OID 32507)
-- Name: eliminar_banco(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_banco(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN

	UPDATE banco SET activo = false WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN	
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_banco(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 314 (class 1255 OID 32511)
-- Name: eliminar_categoria(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_categoria(id_usuario2 integer, id2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE categoriagasto SET activo=false WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_categoria(id_usuario2 integer, id2 integer) OWNER TO postgres;

--
-- TOC entry 319 (class 1255 OID 32516)
-- Name: eliminar_concepto(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_concepto(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE concepto_gasto SET activo=false WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
		
	ELSE
		RETURN false;
	END IF;	
END;
$$;


ALTER FUNCTION public.eliminar_concepto(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 325 (class 1255 OID 32522)
-- Name: eliminar_cuenta(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_cuenta(n_cuenta2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE cuenta SET activo=false WHERE n_cuenta = n_cuenta2;
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	   	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_cuenta(n_cuenta2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 330 (class 1255 OID 32527)
-- Name: eliminar_fondos(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_fondos(id_usuario2 integer, id2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE fondos SET activo=false WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_fondos(id_usuario2 integer, id2 integer) OWNER TO postgres;

--
-- TOC entry 334 (class 1255 OID 32531)
-- Name: eliminar_forma_pago(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_forma_pago(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE forma_pago SET activo = false WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_forma_pago(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 341 (class 1255 OID 32538)
-- Name: eliminar_interes(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_interes(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE interes SET activo = false WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
	 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_interes(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 380 (class 1255 OID 32579)
-- Name: eliminar_mensaje(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_mensaje() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	emisor_bd boolean;
	receptor_bd integer;
BEGIN
	emisor_bd := true;
	--RAISE INFO 'Consultando si el emisor eliminó el mensaje con id: %...', OLD.id;
	emisor_bd := (SELECT activo_emisor FROM mensaje AS msj WHERE msj.id = NEW.id);
	--RAISE INFO 'El resultado es %', emisor_bd;

	IF emisor_bd = true THEN
		--RAISE INFO 'Emisor no ha eliminado el mensaje';
		RETURN null;
	
	ELSE
		--RAISE INFO 'Emisor eliminó el mensaje. Buscando en receptores...';
		receptor_bd := (SELECT COUNT(*) FROM puente_mensaje_usuario WHERE activo_receptor = true AND id_mensaje = OLD.id);
	
		IF receptor_bd = 0 THEN
			--RAISE INFO 'Todos los receptores eliminaron el mensaje';
			DELETE FROM puente_mensaje_usuario WHERE id_mensaje = OLD.id;
			DELETE FROM mensaje WHERE id = old.id;
			
			RETURN NEW;
			
		ELSE
			RETURN null;
		END IF;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_mensaje() OWNER TO postgres;

--
-- TOC entry 347 (class 1255 OID 32543)
-- Name: eliminar_proveedor(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_proveedor(cedula2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE proveedores SET activo = false WHERE cedula = cedula2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_proveedor(cedula2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 351 (class 1255 OID 32547)
-- Name: eliminar_sancion(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_sancion(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	DELETE FROM sancion WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_sancion(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 354 (class 1255 OID 32550)
-- Name: eliminar_tipo_unidad(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_tipo_unidad(tipo2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE tipo_unidad SET activo = false WHERE id = tipo2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_tipo_unidad(tipo2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 358 (class 1255 OID 32554)
-- Name: eliminar_unidad(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_unidad(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE unidad SET activo = false WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.eliminar_unidad(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 381 (class 1255 OID 32580)
-- Name: llenar_bitacora(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.llenar_bitacora() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.llenar_bitacora() OWNER TO postgres;

--
-- TOC entry 295 (class 1255 OID 32503)
-- Name: login(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.login(usu character varying, pass character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	usu1 character varying;
	pass1 character varying;

BEGIN
	usu1 := (SELECT usuario FROM usuario where usuario=usu AND password=pass);
	pass1 := (SELECT password FROM usuario where usuario=usu AND password=pass);

	IF usu = usu1 AND pass = pass1 THEN 
		RETURN TRUE;
		
	ELSE
		RETURN FALSE;
	END IF;
END;
$$;


ALTER FUNCTION public.login(usu character varying, pass character varying) OWNER TO postgres;

--
-- TOC entry 362 (class 1255 OID 32558)
-- Name: mayuscula_asambleas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_asambleas() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	NEW.nombre = UPPER(NEW.nombre);
	NEW.descripcion = UPPER(NEW.descripcion);

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_asambleas() OWNER TO postgres;

--
-- TOC entry 363 (class 1255 OID 32559)
-- Name: mayuscula_banco(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_banco() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	NEW.nombre_banco = UPPER(NEW.nombre_banco);

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_banco() OWNER TO postgres;

--
-- TOC entry 364 (class 1255 OID 32560)
-- Name: mayuscula_categoriagasto(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_categoriagasto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	NEW.nombre = UPPER(NEW.nombre);
	NEW.descripcion = UPPER(NEW.descripcion);

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_categoriagasto() OWNER TO postgres;

--
-- TOC entry 365 (class 1255 OID 32561)
-- Name: mayuscula_cobro_unidad(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_cobro_unidad() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	NEW.descripcion = UPPER(NEW.descripcion);

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_cobro_unidad() OWNER TO postgres;

--
-- TOC entry 366 (class 1255 OID 32562)
-- Name: mayuscula_concepto_gasto(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_concepto_gasto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.nom_concepto = UPPER(NEW.nom_concepto);
	NEW.descripcion = UPPER(NEW.descripcion);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_concepto_gasto() OWNER TO postgres;

--
-- TOC entry 367 (class 1255 OID 32563)
-- Name: mayuscula_condominio(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_condominio() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.razon_social = UPPER(NEW.razon_social);
	NEW.correo_electronico = UPPER(NEW.correo_electronico);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_condominio() OWNER TO postgres;

--
-- TOC entry 368 (class 1255 OID 32564)
-- Name: mayuscula_cuenta(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_cuenta() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.tipo = UPPER(NEW.tipo);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_cuenta() OWNER TO postgres;

--
-- TOC entry 369 (class 1255 OID 32565)
-- Name: mayuscula_cuenta_pagar(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_cuenta_pagar() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.descripcion = UPPER(NEW.descripcion);	
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_cuenta_pagar() OWNER TO postgres;

--
-- TOC entry 370 (class 1255 OID 32566)
-- Name: mayuscula_detalle_pagos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_detalle_pagos() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.tipo_gasto = UPPER(NEW.tipo_gasto);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_detalle_pagos() OWNER TO postgres;

--
-- TOC entry 371 (class 1255 OID 32567)
-- Name: mayuscula_fondos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_fondos() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.tipo = UPPER(NEW.tipo);
	NEW.descripcion = UPPER(NEW.descripcion);
	NEW.observaciones = UPPER(NEW.observaciones);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_fondos() OWNER TO postgres;

--
-- TOC entry 372 (class 1255 OID 32568)
-- Name: mayuscula_forma_pago(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_forma_pago() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.forma_pago = UPPER(NEW.forma_pago);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_forma_pago() OWNER TO postgres;

--
-- TOC entry 373 (class 1255 OID 32569)
-- Name: mayuscula_gasto(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_gasto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.nombre = UPPER(NEW.nombre);
	NEW.tipo = UPPER(NEW.tipo);
	NEW.observacion = UPPER(NEW.observacion);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_gasto() OWNER TO postgres;

--
-- TOC entry 374 (class 1255 OID 32570)
-- Name: mayuscula_interes(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_interes() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.nombre = UPPER(NEW.nombre);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_interes() OWNER TO postgres;

--
-- TOC entry 375 (class 1255 OID 32571)
-- Name: mayuscula_persona(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_persona() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.p_nombre = UPPER(NEW.p_nombre);
	NEW.s_nombre = UPPER(NEW.s_nombre);
	NEW.p_apellido = UPPER(NEW.p_apellido);
	NEW.s_apellido = UPPER(NEW.s_apellido);
	NEW.correo = UPPER(NEW.correo);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_persona() OWNER TO postgres;

--
-- TOC entry 376 (class 1255 OID 32572)
-- Name: mayuscula_proveedores(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_proveedores() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.nombre = UPPER(NEW.nombre);
	NEW.correo = UPPER(NEW.correo);
	NEW.contacto = UPPER(NEW.contacto);
	NEW.direccion = UPPER(NEW.direccion);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_proveedores() OWNER TO postgres;

--
-- TOC entry 377 (class 1255 OID 32573)
-- Name: mayuscula_sancion(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_sancion() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.descripcion = UPPER(NEW.descripcion);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_sancion() OWNER TO postgres;

--
-- TOC entry 378 (class 1255 OID 32574)
-- Name: mayuscula_tipo_unidad(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_tipo_unidad() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.tipo = UPPER(NEW.tipo);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_tipo_unidad() OWNER TO postgres;

--
-- TOC entry 379 (class 1255 OID 32575)
-- Name: mayuscula_tipo_usuario(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_tipo_usuario() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.tipo = UPPER(NEW.tipo);
	
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_tipo_usuario() OWNER TO postgres;

--
-- TOC entry 293 (class 1255 OID 32576)
-- Name: mayuscula_unidad(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_unidad() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.n_unidad = UPPER(NEW.n_unidad);
	NEW.n_documento = UPPER(NEW.n_documento);
	NEW.direccion = UPPER(NEW.direccion);

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_unidad() OWNER TO postgres;

--
-- TOC entry 294 (class 1255 OID 32577)
-- Name: mayuscula_visita(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mayuscula_visita() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	NEW.matricula = UPPER(NEW.matricula);
	NEW.modelo = UPPER(NEW.modelo);
	NEW.color = UPPER(NEW.color);

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.mayuscula_visita() OWNER TO postgres;

--
-- TOC entry 310 (class 1255 OID 32506)
-- Name: modificar_banco(integer, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_banco(id2 integer, nombre2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE	
	resul int;
BEGIN
	UPDATE banco SET nombre_banco = nombre2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
	
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_banco(id2 integer, nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 313 (class 1255 OID 32510)
-- Name: modificar_categoria(integer, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying, id2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE categoriagasto SET nombre=nombre2, descripcion=descripcion2 WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying, id2 integer) OWNER TO postgres;

--
-- TOC entry 318 (class 1255 OID 32515)
-- Name: modificar_concepto(integer, character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_concepto(id2 integer, nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE concepto_gasto SET nom_concepto = nombre2, descripcion = descripcion2, id_categoria = id_categoria2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_concepto(id2 integer, nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 322 (class 1255 OID 32519)
-- Name: modificar_condominio(integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE condominio SET razon_social=razon_social2, telefono=telefono2, 	correo_electronico=correo_electronico2 WHERE rif=rif2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) OWNER TO postgres;

--
-- TOC entry 324 (class 1255 OID 32521)
-- Name: modificar_cuenta(character varying, integer, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_cuenta(tipo2 character varying, id_banco2 integer, ci_persona2 character varying, n_cuenta2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE cuenta SET tipo=tipo2, id_banco=id_banco2, ci_persona=ci_persona2
	WHERE n_cuenta=n_cuenta2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_cuenta(tipo2 character varying, id_banco2 integer, ci_persona2 character varying, n_cuenta2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 329 (class 1255 OID 32526)
-- Name: modificar_fondos(integer, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_fondos(id_usuario2 integer, tipo2 character varying, descripcion2 character varying, observaciones2 character varying, id2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE fondos SET descripcion=descripcion2, observaciones=observaciones2, 
	tipo=tipo2 WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_fondos(id_usuario2 integer, tipo2 character varying, descripcion2 character varying, observaciones2 character varying, id2 integer) OWNER TO postgres;

--
-- TOC entry 333 (class 1255 OID 32530)
-- Name: modificar_forma_pago(integer, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_forma_pago(id2 integer, nombre2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE forma_pago SET forma_pago = nombre2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_forma_pago(id2 integer, nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 337 (class 1255 OID 32534)
-- Name: modificar_gasto(integer, character varying, character varying, character varying, character varying, integer, integer, integer, integer, text, integer, double precision, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_gasto(id2 integer, nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, saldo2 double precision, moneda2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE gasto SET nombre = nombre2, tipo = tipo2, id_proveedor = id_proveedor2, calcular_por = calcular_por2, mes = mes2, anio = anio2, n_meses = n_meses2, id_asamblea =id_asamblea2, observacion = observacion2, meses_restantes = meses_restantes2, monto = monto2, saldo = saldo2, moneda = moneda2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_gasto(id2 integer, nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, saldo2 double precision, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 340 (class 1255 OID 32537)
-- Name: modificar_interes(integer, character varying, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_interes(id2 integer, nombre2 character varying, factor2 double precision, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE interes SET nombre = nombre2, factor = factor2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
	 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_interes(id2 integer, nombre2 character varying, factor2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 346 (class 1255 OID 32542)
-- Name: modificar_proveedor(character varying, character varying, character varying, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE proveedores SET nombre = nombre2, telefono = telefono2, correo = correo2, contacto = contacto2, direccion = direccion2 WHERE cedula = cedula2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 350 (class 1255 OID 32546)
-- Name: modificar_sancion(character varying, integer, integer, double precision, character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, moneda2 character varying, id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE sancion SET tipo=tipo2, mes=mes2, anio=anio2, monto=monto2, descripcion=descripcion2, moneda=moneda2 WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, moneda2 character varying, id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 353 (class 1255 OID 32549)
-- Name: modificar_tipo_unidad(character varying, double precision, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_tipo_unidad(tipo2 character varying, area2 double precision, id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE tipo_unidad SET tipo = tipo2, area = area2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_tipo_unidad(tipo2 character varying, area2 double precision, id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 357 (class 1255 OID 32553)
-- Name: modificar_unidad(character varying, character varying, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_unidad(n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE unidad SET n_documento = n_documento2, direccion = direccion2, id_tipo=id_tipo2 WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_unidad(n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 289 (class 1255 OID 32500)
-- Name: pagar_gasto(integer, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.pagar_gasto(id2 integer, monto2 double precision) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
	saldo_bd double precision;
BEGIN
	UPDATE gasto SET saldo = saldo - monto2 WHERE id = id2;
	
	saldo_bd := (SELECT saldo FROM gasto WHERE id = id2);

	IF saldo_bd = 0 THEN
		UPDATE gasto SET pagado = 'Pagado' WHERE id = id2;
	END IF;
END;
$$;


ALTER FUNCTION public.pagar_gasto(id2 integer, monto2 double precision) OWNER TO postgres;

--
-- TOC entry 311 (class 1255 OID 32508)
-- Name: reactivar_banco(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_banco(nombre_banco2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE banco SET activo=true WHERE nombre_banco=nombre_banco2;
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_banco(nombre_banco2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 315 (class 1255 OID 32512)
-- Name: reactivar_categoria(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_categoria(nombre2 character varying, descripcion2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE categoriagasto SET descripcion = descripcion2, activo = true WHERE nombre = nombre2;
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_categoria(nombre2 character varying, descripcion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 320 (class 1255 OID 32517)
-- Name: reactivar_concepto(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_concepto(nombre2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE concepto_gasto SET activo = true WHERE nom_concepto = nombre2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_concepto(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 326 (class 1255 OID 32523)
-- Name: reactivar_cuenta(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_cuenta(n_cuenta2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE cuenta SET activo = true WHERE n_cuenta = n_cuenta2;
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_cuenta(n_cuenta2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 331 (class 1255 OID 32528)
-- Name: reactivar_fondo(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_fondo(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE fondos SET activo=true WHERE id=id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_fondo(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 335 (class 1255 OID 32532)
-- Name: reactivar_forma_pago(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_forma_pago(nombre2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE forma_pago SET activo = true WHERE forma_pago = nombre2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_forma_pago(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 342 (class 1255 OID 32539)
-- Name: reactivar_interes(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_interes(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE interes SET activo = true WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
	 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_interes(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 348 (class 1255 OID 32544)
-- Name: reactivar_proveedor(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_proveedor(cedula2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE proveedores SET activo = true WHERE cedula = cedula2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_proveedor(cedula2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 355 (class 1255 OID 32551)
-- Name: reactivar_tipo_unidad(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_tipo_unidad(tipo2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE tipo_unidad SET activo = true WHERE tipo = tipo2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_tipo_unidad(tipo2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 359 (class 1255 OID 32555)
-- Name: reactivar_unidad(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_unidad(n_unidad2 character varying, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	UPDATE unidad SET activo = true WHERE n_unidad = n_unidad2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.reactivar_unidad(n_unidad2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 316 (class 1255 OID 32513)
-- Name: registrar_cuota(integer, integer, integer, integer, double precision, double precision, character varying, character varying, double precision, double precision, double precision, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_cuota(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO recibo (
		id_unidad, id_gasto, mes, anio, monto_dolar, monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota
	) VALUES (
		id_unidad2, id_gasto2, mes2, anio2, monto_dolar2, monto_bolivar2, tipo_gasto2, moneda_dominante2, paridad2, monto_bolivar2, monto_dolar2, alicuota2
	);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.registrar_cuota(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 338 (class 1255 OID 32535)
-- Name: registrar_interes(integer, integer, integer, integer, double precision, double precision, character varying, character varying, double precision, double precision, double precision, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_interes(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO recibo (
		id_unidad, id_gasto, mes, anio, monto_dolar, monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota
	) VALUES (
		id_unidad2, id_gasto2, mes2, anio2, monto_dolar2, monto_bolivar2, tipo_gasto2, moneda_dominante2, paridad2, saldo_restante_bolivar2, saldo_restante_dolar2, alicuota2
	);
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
	 	UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.registrar_interes(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 361 (class 1255 OID 32557)
-- Name: registrar_salida(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_salida(id2 integer, id_usuario2 integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
BEGIN

	UPDATE visita SET fecha_salida = LOCALTIMESTAMP(0), hora_salida = LOCALTIMESTAMP(0) WHERE id = id2;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro de salida'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.registrar_salida(id2 integer, id_usuario2 integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 31941)
-- Name: asambleas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asambleas (
    id integer NOT NULL,
    nombre character varying(60) NOT NULL,
    descripcion text NOT NULL,
    fecha date NOT NULL
);


ALTER TABLE public.asambleas OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 31939)
-- Name: asambleas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.asambleas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.asambleas_id_seq OWNER TO postgres;

--
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 196
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 199 (class 1259 OID 31952)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 31950)
-- Name: banco_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.banco_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.banco_id_seq OWNER TO postgres;

--
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 198
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 261 (class 1259 OID 32485)
-- Name: bitacora; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bitacora (
    id_bitacora integer NOT NULL,
    operacion text NOT NULL,
    tabla text NOT NULL,
    id_usuario integer,
    valor_viejo text,
    valor_nuevo text,
    fecha_hora timestamp without time zone DEFAULT LOCALTIMESTAMP(0)
);


ALTER TABLE public.bitacora OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 32483)
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bitacora_id_bitacora_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bitacora_id_bitacora_seq OWNER TO postgres;

--
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 260
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bitacora_id_bitacora_seq OWNED BY public.bitacora.id_bitacora;


--
-- TOC entry 201 (class 1259 OID 31963)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(200),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 31961)
-- Name: categoriagasto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoriagasto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoriagasto_id_seq OWNER TO postgres;

--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 218 (class 1259 OID 32066)
-- Name: cierre_de_mes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cierre_de_mes (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    id_condominio character varying(15) NOT NULL
);


ALTER TABLE public.cierre_de_mes OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 32064)
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cierre_de_mes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cierre_de_mes_id_seq OWNER TO postgres;

--
-- TOC entry 3521 (class 0 OID 0)
-- Dependencies: 217
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cierre_de_mes_id_seq OWNED BY public.cierre_de_mes.id;


--
-- TOC entry 233 (class 1259 OID 32214)
-- Name: cobro_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cobro_unidad (
    id integer NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(500) NOT NULL,
    id_cuenta character varying(20) NOT NULL,
    forma_pago character varying(150) NOT NULL,
    referencia character varying(50) NOT NULL,
    fecha date NOT NULL,
    id_fondo bigint NOT NULL,
    id_unidad bigint NOT NULL,
    moneda character varying NOT NULL,
    paridad double precision NOT NULL
);


ALTER TABLE public.cobro_unidad OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 32212)
-- Name: cobro_unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cobro_unidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cobro_unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3522 (class 0 OID 0)
-- Dependencies: 232
-- Name: cobro_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_unidad_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 220 (class 1259 OID 32079)
-- Name: concepto_gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gasto (
    id integer NOT NULL,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.concepto_gasto OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 32077)
-- Name: concepto_gasto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.concepto_gasto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.concepto_gasto_id_seq OWNER TO postgres;

--
-- TOC entry 3523 (class 0 OID 0)
-- Dependencies: 219
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 202 (class 1259 OID 31970)
-- Name: condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.condominio (
    rif character varying(11) NOT NULL,
    razon_social character varying(200) NOT NULL,
    telefono character varying(12) NOT NULL,
    correo_electronico character varying(80) DEFAULT ''::character varying,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.condominio OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 32091)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    n_cuenta character varying(20) NOT NULL,
    tipo character varying(10) NOT NULL,
    id_banco bigint NOT NULL,
    ci_persona character varying(11),
    rif_condominio character varying(11),
    activo boolean DEFAULT true
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 32150)
-- Name: cuenta_pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta_pagar (
    id integer NOT NULL,
    num_ref character varying(10),
    descripcion character varying(60) NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    moneda character varying(10) NOT NULL,
    tasa_cambio double precision,
    id_forma_pago integer NOT NULL,
    id_gasto integer NOT NULL,
    n_cuenta character varying(20),
    id_fondo integer NOT NULL
);


ALTER TABLE public.cuenta_pagar OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 32148)
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuenta_pagar_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuenta_pagar_id_seq OWNER TO postgres;

--
-- TOC entry 3524 (class 0 OID 0)
-- Dependencies: 226
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 243 (class 1259 OID 32313)
-- Name: detalle_pagos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_pagos (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto_dolar double precision NOT NULL,
    id_gasto integer NOT NULL,
    id_unidad integer NOT NULL,
    tipo_gasto character varying NOT NULL,
    monto_bolivar double precision NOT NULL,
    paridad double precision NOT NULL,
    moneda_dominante character varying NOT NULL,
    saldo_restante_bolivar double precision NOT NULL,
    saldo_restante_dolar double precision
);


ALTER TABLE public.detalle_pagos OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 32311)
-- Name: detalle_pagos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_pagos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.detalle_pagos_id_seq OWNER TO postgres;

--
-- TOC entry 3525 (class 0 OID 0)
-- Dependencies: 242
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 223 (class 1259 OID 32114)
-- Name: fondos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fondos (
    tipo character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion character varying(200) NOT NULL,
    observaciones character varying(200),
    monto_inicial double precision NOT NULL,
    saldo double precision NOT NULL,
    id integer NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    moneda character varying NOT NULL
);


ALTER TABLE public.fondos OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 32112)
-- Name: fondos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fondos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fondos_id_seq OWNER TO postgres;

--
-- TOC entry 3526 (class 0 OID 0)
-- Dependencies: 222
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 204 (class 1259 OID 31979)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pago (
    id integer NOT NULL,
    forma_pago character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 31977)
-- Name: forma_pago_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.forma_pago_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pago_id_seq OWNER TO postgres;

--
-- TOC entry 3527 (class 0 OID 0)
-- Dependencies: 203
-- Name: forma_pago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pago_id_seq OWNED BY public.forma_pago.id;


--
-- TOC entry 206 (class 1259 OID 31991)
-- Name: funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcion (
    id integer NOT NULL,
    funcion character varying NOT NULL
);


ALTER TABLE public.funcion OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 31989)
-- Name: funcion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.funcion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.funcion_id_seq OWNER TO postgres;

--
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 205
-- Name: funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcion_id_seq OWNED BY public.funcion.id;


--
-- TOC entry 225 (class 1259 OID 32126)
-- Name: gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gasto (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    id_proveedor character varying(15) NOT NULL,
    calcular_por character varying(20) NOT NULL,
    mes integer NOT NULL,
    anio integer NOT NULL,
    n_meses integer NOT NULL,
    id_asamblea integer,
    observacion text DEFAULT ''::text,
    meses_restantes integer NOT NULL,
    monto double precision NOT NULL,
    saldo double precision NOT NULL,
    estado character varying DEFAULT 'Pendiente'::character varying NOT NULL,
    pagado character varying DEFAULT 'Pendiente'::character varying NOT NULL,
    moneda character varying NOT NULL
);


ALTER TABLE public.gasto OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 32124)
-- Name: gasto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.gasto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gasto_id_seq OWNER TO postgres;

--
-- TOC entry 3529 (class 0 OID 0)
-- Dependencies: 224
-- Name: gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_id_seq OWNED BY public.gasto.id;


--
-- TOC entry 208 (class 1259 OID 32004)
-- Name: interes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interes (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    factor double precision NOT NULL,
    activo boolean DEFAULT true,
    rif_condominio character varying
);


ALTER TABLE public.interes OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 32002)
-- Name: interes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.interes_id_seq OWNER TO postgres;

--
-- TOC entry 3530 (class 0 OID 0)
-- Dependencies: 207
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 239 (class 1259 OID 32277)
-- Name: mensaje; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mensaje (
    id integer NOT NULL,
    asunto character varying(60) DEFAULT 'Sin Asunto'::character varying,
    contenido character varying(420) NOT NULL,
    emisor integer NOT NULL,
    fecha timestamp without time zone DEFAULT LOCALTIMESTAMP(0) NOT NULL,
    activo_emisor boolean DEFAULT true NOT NULL
);


ALTER TABLE public.mensaje OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 32275)
-- Name: mensaje_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mensaje_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mensaje_id_seq OWNER TO postgres;

--
-- TOC entry 3531 (class 0 OID 0)
-- Dependencies: 238
-- Name: mensaje_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mensaje_id_seq OWNED BY public.mensaje.id;


--
-- TOC entry 209 (class 1259 OID 32019)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    cedula character varying(11) NOT NULL,
    p_nombre character varying(25) NOT NULL,
    s_nombre character varying(25) DEFAULT ''::character varying,
    p_apellido character varying(25) NOT NULL,
    s_apellido character varying(25) DEFAULT ''::character varying,
    telefono character varying(12),
    correo character varying(60),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 32176)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
    ci_persona character varying(11) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 32027)
-- Name: proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proveedores (
    cedula character varying(11) NOT NULL,
    nombre character varying(60) NOT NULL,
    telefono character varying(12) NOT NULL,
    correo character varying(80),
    contacto character varying(60) NOT NULL,
    direccion character varying(150) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.proveedores OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 32334)
-- Name: puente_asambleas_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asambleas_propietario (
    id integer NOT NULL,
    id_asamblea integer NOT NULL,
    ci_propietario character varying(11) NOT NULL
);


ALTER TABLE public.puente_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 32332)
-- Name: puente_asambleas_propietario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_asambleas_propietario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_asambleas_propietario_id_seq OWNER TO postgres;

--
-- TOC entry 3532 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_asambleas_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asambleas_propietario_id_seq OWNED BY public.puente_asambleas_propietario.id;


--
-- TOC entry 247 (class 1259 OID 32352)
-- Name: puente_cobro_factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_cobro_factura (
    id integer NOT NULL,
    id_recibo bigint NOT NULL,
    id_cobro bigint NOT NULL,
    parte_monto double precision NOT NULL,
    moneda character varying
);


ALTER TABLE public.puente_cobro_factura OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 32350)
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_cobro_factura_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_cobro_factura_id_seq OWNER TO postgres;

--
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 249 (class 1259 OID 32373)
-- Name: puente_gasto_concepto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_gasto_concepto (
    id integer NOT NULL,
    id_gasto integer,
    id_concepto integer,
    monto double precision NOT NULL
);


ALTER TABLE public.puente_gasto_concepto OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 32371)
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_gasto_concepto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_gasto_concepto_id_seq OWNER TO postgres;

--
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_gasto_concepto_id_seq OWNED BY public.puente_gasto_concepto.id;


--
-- TOC entry 251 (class 1259 OID 32391)
-- Name: puente_mensaje_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_mensaje_usuario (
    id integer NOT NULL,
    id_mensaje integer NOT NULL,
    receptor integer NOT NULL,
    leido boolean DEFAULT false NOT NULL,
    activo_receptor boolean DEFAULT true NOT NULL
);


ALTER TABLE public.puente_mensaje_usuario OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 32389)
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_mensaje_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_mensaje_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_mensaje_usuario_id_seq OWNED BY public.puente_mensaje_usuario.id;


--
-- TOC entry 253 (class 1259 OID 32411)
-- Name: puente_persona_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_persona_condominio (
    id integer NOT NULL,
    ci_persona character varying(11) NOT NULL,
    rif_condominio character varying(15) NOT NULL
);


ALTER TABLE public.puente_persona_condominio OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 32409)
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_persona_condominio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_persona_condominio_id_seq OWNER TO postgres;

--
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_persona_condominio_id_seq OWNED BY public.puente_persona_condominio.id;


--
-- TOC entry 255 (class 1259 OID 32429)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id integer NOT NULL,
    id_sancion integer NOT NULL,
    id_unidad bigint NOT NULL
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 32427)
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_sancion_unidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_sancion_unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 259 (class 1259 OID 32467)
-- Name: puente_tipo_funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_tipo_funcion (
    id integer NOT NULL,
    id_tipo integer NOT NULL,
    id_funcion integer NOT NULL,
    registrar boolean,
    modificar boolean,
    eliminar boolean,
    todo boolean
);


ALTER TABLE public.puente_tipo_funcion OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 32465)
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_tipo_funcion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_tipo_funcion_id_seq OWNER TO postgres;

--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 258
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_tipo_funcion_id_seq OWNED BY public.puente_tipo_funcion.id;


--
-- TOC entry 257 (class 1259 OID 32447)
-- Name: puente_unidad_propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_unidad_propietarios (
    id integer NOT NULL,
    ci_propietario character varying(11) NOT NULL,
    id_unidad bigint NOT NULL,
    fecha_desde date DEFAULT LOCALTIMESTAMP(0) NOT NULL,
    fecha_hasta date,
    estado bigint NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.puente_unidad_propietarios OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 32445)
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_unidad_propietarios_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_unidad_propietarios_id_seq OWNER TO postgres;

--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 235 (class 1259 OID 32240)
-- Name: recibo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recibo (
    id integer NOT NULL,
    mes smallint NOT NULL,
    anio smallint NOT NULL,
    monto_dolar double precision NOT NULL,
    id_gasto bigint,
    id_unidad bigint,
    tipo_gasto character varying,
    monto_bolivar double precision,
    paridad double precision,
    moneda_dominante character varying,
    saldo_restante_bolivar double precision,
    saldo_restante_dolar double precision,
    alicuota double precision
);


ALTER TABLE public.recibo OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 32238)
-- Name: recibo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recibo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recibo_id_seq OWNER TO postgres;

--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 234
-- Name: recibo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recibo_id_seq OWNED BY public.recibo.id;


--
-- TOC entry 229 (class 1259 OID 32187)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
    ci_persona character varying(11) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 32035)
-- Name: sancion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sancion (
    id integer NOT NULL,
    tipo character varying(40) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(120) NOT NULL,
    estado character varying(20) NOT NULL,
    moneda character varying(10)
);


ALTER TABLE public.sancion OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 32033)
-- Name: sancion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sancion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sancion_id_seq OWNER TO postgres;

--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 211
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 214 (class 1259 OID 32043)
-- Name: tipo_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_unidad (
    id integer NOT NULL,
    area double precision NOT NULL,
    tipo character varying(60),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tipo_unidad OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 32041)
-- Name: tipo_unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_unidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3542 (class 0 OID 0)
-- Dependencies: 213
-- Name: tipo_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_unidad_id_seq OWNED BY public.tipo_unidad.id;


--
-- TOC entry 216 (class 1259 OID 32052)
-- Name: tipo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_usuario (
    id integer NOT NULL,
    tipo character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tipo_usuario OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 32050)
-- Name: tipo_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3543 (class 0 OID 0)
-- Dependencies: 215
-- Name: tipo_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_usuario_id_seq OWNED BY public.tipo_usuario.id;


--
-- TOC entry 231 (class 1259 OID 32200)
-- Name: unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidad (
    id integer NOT NULL,
    n_unidad character varying(10) NOT NULL,
    n_documento character varying(15) NOT NULL,
    direccion character varying(200) NOT NULL,
    activo boolean DEFAULT true,
    alicuota double precision,
    id_tipo integer NOT NULL
);


ALTER TABLE public.unidad OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 32198)
-- Name: unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.unidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3544 (class 0 OID 0)
-- Dependencies: 230
-- Name: unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidad_id_seq OWNED BY public.unidad.id;


--
-- TOC entry 237 (class 1259 OID 32256)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    usuario character varying(25) NOT NULL,
    password character varying(32) NOT NULL,
    pregunta character varying(120) NOT NULL,
    respuesta character varying(120) NOT NULL,
    ci_persona character varying(11),
    id_tipo_usuario integer,
    activo boolean DEFAULT true
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 32254)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3545 (class 0 OID 0)
-- Dependencies: 236
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 264 (class 1259 OID 32630)
-- Name: v_asambleas; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_asambleas AS
 SELECT asa.id,
    asa.nombre,
    asa.descripcion,
    asa.fecha
   FROM public.asambleas asa;


ALTER TABLE public.v_asambleas OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 32622)
-- Name: v_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_propietario AS
 SELECT pro.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.propietario pro
     JOIN public.persona per ON (((per.cedula)::text = (pro.ci_persona)::text)))
  WHERE (pro.activo = true);


ALTER TABLE public.v_propietario OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 32634)
-- Name: v_asambleas_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_asambleas_propietario AS
 SELECT asa.id,
    pr.ci_persona AS cedula,
    pr.p_nombre AS nombre,
    pr.p_apellido AS apellido,
    pu.id AS id_puente
   FROM ((public.v_propietario pr
     JOIN public.puente_asambleas_propietario pu ON (((pu.ci_propietario)::text = (pr.ci_persona)::text)))
     JOIN public.asambleas asa ON ((asa.id = pu.id_asamblea)));


ALTER TABLE public.v_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 32680)
-- Name: v_bandeja_entrada; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_bandeja_entrada AS
 SELECT me.id,
    me.asunto,
    me.contenido,
    me.fecha,
    me.emisor AS id_emisor,
    u1.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido,
    pu.receptor,
    pu.leido,
    pu.activo_receptor
   FROM ((((public.mensaje me
     JOIN public.usuario u1 ON ((u1.id = me.emisor)))
     JOIN public.puente_mensaje_usuario pu ON ((pu.id_mensaje = me.id)))
     JOIN public.usuario u2 ON ((u2.id = pu.receptor)))
     JOIN public.persona pe ON (((pe.cedula)::text = (u1.ci_persona)::text)))
  WHERE (pu.activo_receptor = true);


ALTER TABLE public.v_bandeja_entrada OWNER TO postgres;

--
-- TOC entry 274 (class 1259 OID 32675)
-- Name: v_bandeja_salida; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_bandeja_salida AS
 SELECT me.id,
    me.asunto,
    me.contenido,
    me.fecha,
    me.emisor AS id_emisor,
    u.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido
   FROM ((public.mensaje me
     JOIN public.usuario u ON ((u.id = me.emisor)))
     JOIN public.persona pe ON (((pe.cedula)::text = (u.ci_persona)::text)))
  WHERE (me.activo_emisor = true);


ALTER TABLE public.v_bandeja_salida OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 32638)
-- Name: v_concepto_gasto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_concepto_gasto AS
 SELECT cg.id,
    cg.nom_concepto AS nombre,
    cg.descripcion,
    cat.id AS id_categoria,
    cat.nombre AS nombre_categoria,
    cg.activo
   FROM (public.concepto_gasto cg
     JOIN public.categoriagasto cat ON ((cat.id = cg.id_categoria)));


ALTER TABLE public.v_concepto_gasto OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 32642)
-- Name: v_condominio; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_condominio AS
 SELECT condominio.rif,
    condominio.razon_social,
    condominio.telefono,
    condominio.correo_electronico AS correo
   FROM public.condominio;


ALTER TABLE public.v_condominio OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 32646)
-- Name: v_cuenta; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta AS
 SELECT cue.n_cuenta,
    cue.tipo,
    cue.id_banco,
    ban.nombre_banco AS banco,
    cue.ci_persona,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido,
    cue.rif_condominio,
    co.razon_social,
    cue.activo
   FROM (((public.cuenta cue
     JOIN public.banco ban ON ((ban.id = cue.id_banco)))
     LEFT JOIN public.persona per ON (((per.cedula)::text = (cue.ci_persona)::text)))
     LEFT JOIN public.condominio co ON (((co.rif)::text = (cue.rif_condominio)::text)))
  WHERE (cue.activo = true);


ALTER TABLE public.v_cuenta OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 32651)
-- Name: v_cuenta_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta_inactivo AS
 SELECT cue.n_cuenta,
    cue.tipo,
    cue.id_banco,
    ban.nombre_banco AS banco,
    cue.ci_persona,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido,
    cue.rif_condominio,
    cue.activo
   FROM ((public.cuenta cue
     JOIN public.banco ban ON ((ban.id = cue.id_banco)))
     JOIN public.persona per ON (((per.cedula)::text = (cue.ci_persona)::text)))
  WHERE (cue.activo = false);


ALTER TABLE public.v_cuenta_inactivo OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 32656)
-- Name: v_cuenta_pagar; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta_pagar AS
 SELECT cp.id,
    cp.num_ref,
    cp.descripcion,
    cp.monto,
    cp.moneda,
    cp.tasa_cambio,
    cp.fecha,
    cp.id_gasto,
    ga.nombre AS gasto,
    cp.n_cuenta,
    cu.id_banco,
    b.nombre_banco AS banco,
    cp.id_fondo,
    f.tipo AS fondo,
    cp.id_forma_pago,
    fp.forma_pago
   FROM (((((public.cuenta_pagar cp
     JOIN public.gasto ga ON ((ga.id = cp.id_gasto)))
     JOIN public.cuenta cu ON (((cu.n_cuenta)::text = (cp.n_cuenta)::text)))
     JOIN public.banco b ON ((b.id = cu.id_banco)))
     JOIN public.fondos f ON ((f.id = cp.id_fondo)))
     JOIN public.forma_pago fp ON ((fp.id = cp.id_forma_pago)))
  ORDER BY cp.fecha DESC;


ALTER TABLE public.v_cuenta_pagar OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 32661)
-- Name: v_dueno_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_dueno_unidad AS
 SELECT prop.ci_persona,
    puente.id,
    puente.id_unidad,
    puente.fecha_hasta
   FROM (public.propietario prop
     LEFT JOIN public.puente_unidad_propietarios puente ON (((prop.ci_persona)::text = (puente.ci_propietario)::text)))
  WHERE ((prop.activo = true) AND (puente.fecha_hasta IS NULL));


ALTER TABLE public.v_dueno_unidad OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 32665)
-- Name: v_gasto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_gasto AS
 SELECT ga.id,
    ga.tipo,
    ga.nombre,
    ga.id_proveedor,
    ga.moneda,
    pr.nombre AS proveedor,
    ga.calcular_por,
    ga.mes,
    ga.anio,
    ga.monto,
    ga.saldo,
    ga.n_meses,
    ga.meses_restantes,
    asa.id AS id_asamblea,
    asa.nombre AS asamblea,
    asa.fecha AS fecha_asamblea,
    ga.observacion,
    ga.estado,
    ga.pagado
   FROM ((public.gasto ga
     JOIN public.proveedores pr ON (((pr.cedula)::text = (ga.id_proveedor)::text)))
     LEFT JOIN public.asambleas asa ON ((asa.id = ga.id_asamblea)));


ALTER TABLE public.v_gasto OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 32670)
-- Name: v_gasto_concepto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_gasto_concepto AS
 SELECT ga.id AS id_gasto,
    cg.id AS id_concepto,
    cg.nombre,
    pu.monto,
    pu.id AS id_puente
   FROM ((public.v_concepto_gasto cg
     JOIN public.puente_gasto_concepto pu ON ((pu.id_concepto = cg.id)))
     JOIN public.gasto ga ON ((ga.id = pu.id_gasto)));


ALTER TABLE public.v_gasto_concepto OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 32685)
-- Name: v_mensaje_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_mensaje_usuario AS
 SELECT pu.id_mensaje,
    pu.receptor AS id_receptor,
    u.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido
   FROM ((public.puente_mensaje_usuario pu
     JOIN public.usuario u ON ((u.id = pu.receptor)))
     JOIN public.persona pe ON (((pe.cedula)::text = (u.ci_persona)::text)));


ALTER TABLE public.v_mensaje_usuario OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 32690)
-- Name: v_perfil; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_perfil AS
 SELECT per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo,
    usu.id,
    usu.usuario,
    usu.pregunta,
    tip.id AS id_tipo,
    tip.tipo
   FROM ((public.persona per
     JOIN public.usuario usu ON (((usu.ci_persona)::text = (per.cedula)::text)))
     JOIN public.tipo_usuario tip ON ((tip.id = usu.id_tipo_usuario)));


ALTER TABLE public.v_perfil OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 32695)
-- Name: v_permisos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_permisos AS
 SELECT u.usuario,
    tipo.id AS id_tipo,
    tipo.tipo,
    puente.id_funcion,
    f.funcion,
    puente.registrar,
    puente.modificar,
    puente.eliminar,
    puente.todo
   FROM (((public.puente_tipo_funcion puente
     JOIN public.tipo_usuario tipo ON ((tipo.id = puente.id_tipo)))
     JOIN public.funcion f ON ((f.id = puente.id_funcion)))
     JOIN public.usuario u ON ((u.id_tipo_usuario = puente.id_tipo)));


ALTER TABLE public.v_permisos OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 32626)
-- Name: v_propietario_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_propietario_inactivo AS
 SELECT pro.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.propietario pro
     JOIN public.persona per ON (((per.cedula)::text = (pro.ci_persona)::text)))
  WHERE (pro.activo = false);


ALTER TABLE public.v_propietario_inactivo OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 32700)
-- Name: v_responsable; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_responsable AS
 SELECT r.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.responsable r
     JOIN public.persona per ON (((per.cedula)::text = (r.ci_persona)::text)))
  WHERE (r.activo = true);


ALTER TABLE public.v_responsable OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 32704)
-- Name: v_responsable_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_responsable_inactivo AS
 SELECT r.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.responsable r
     JOIN public.persona per ON (((per.cedula)::text = (r.ci_persona)::text)))
  WHERE (r.activo = false);


ALTER TABLE public.v_responsable_inactivo OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 32708)
-- Name: v_tipo_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_unidad AS
 SELECT tu.id,
    tu.tipo,
    tu.area
   FROM public.tipo_unidad tu
  WHERE (tu.activo = true);


ALTER TABLE public.v_tipo_unidad OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 32712)
-- Name: v_tipo_unidad_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_unidad_inactivo AS
 SELECT tu.id,
    tu.tipo,
    tu.area
   FROM public.tipo_unidad tu
  WHERE (tu.activo = false);


ALTER TABLE public.v_tipo_unidad_inactivo OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 32716)
-- Name: v_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad AS
 SELECT u.id,
    u.n_unidad,
    u.n_documento,
    u.direccion,
    u.alicuota,
    tu.id AS id_tipo,
    tu.tipo,
    tu.area
   FROM (public.unidad u
     JOIN public.tipo_unidad tu ON ((tu.id = u.id_tipo)))
  WHERE (u.activo = true);


ALTER TABLE public.v_unidad OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 32720)
-- Name: v_unidad_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad_propietario AS
 SELECT u.id,
    pro.ci_persona,
    pro.p_nombre,
    pro.s_nombre,
    pro.p_apellido,
    pro.s_apellido,
    pro.telefono,
    pro.correo,
    puente.id AS id_puente,
    puente.fecha_desde,
    puente.fecha_hasta,
    puente.estado
   FROM ((public.v_propietario pro
     JOIN public.puente_unidad_propietarios puente ON (((puente.ci_propietario)::text = (pro.ci_persona)::text)))
     JOIN public.unidad u ON ((u.id = puente.id_unidad)))
  WHERE ((u.activo = true) AND (puente.estado = 1));


ALTER TABLE public.v_unidad_propietario OWNER TO postgres;

--
-- TOC entry 285 (class 1259 OID 32725)
-- Name: v_unidades_inactivas; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidades_inactivas AS
 SELECT unidad.id,
    unidad.n_unidad,
    unidad.n_documento,
    unidad.direccion
   FROM public.unidad
  WHERE (unidad.activo = false);


ALTER TABLE public.v_unidades_inactivas OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 32729)
-- Name: v_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_usuario AS
 SELECT u.id,
    u.usuario,
    u.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido
   FROM (public.usuario u
     JOIN public.persona pe ON (((pe.cedula)::text = (u.ci_persona)::text)))
  WHERE (u.activo = true);


ALTER TABLE public.v_usuario OWNER TO postgres;

--
-- TOC entry 287 (class 1259 OID 32733)
-- Name: v_usuario_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_usuario_inactivo AS
 SELECT usuario.id,
    usuario.usuario,
    usuario.ci_persona
   FROM public.usuario
  WHERE (usuario.activo = false);


ALTER TABLE public.v_usuario_inactivo OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 32293)
-- Name: visita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visita (
    id integer NOT NULL,
    fecha_entrada date DEFAULT LOCALTIMESTAMP(0),
    hora_entrada time without time zone DEFAULT LOCALTIMESTAMP(0),
    fecha_salida date,
    hora_salida time without time zone,
    n_personas smallint,
    matricula character varying(8),
    modelo character varying(25),
    color character varying(15),
    ci_visitante character varying(11) NOT NULL,
    id_unidad integer NOT NULL
);


ALTER TABLE public.visita OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 32737)
-- Name: v_visita; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_visita AS
 SELECT vis.id,
    vis.id_unidad,
    u.n_unidad,
    vis.ci_visitante AS cedula,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido,
    vis.n_personas,
    vis.fecha_entrada,
    vis.hora_entrada,
    vis.fecha_salida,
    vis.hora_salida,
    vis.matricula,
    vis.modelo,
    vis.color
   FROM ((public.visita vis
     JOIN public.unidad u ON ((u.id = vis.id_unidad)))
     JOIN public.persona per ON (((per.cedula)::text = (vis.ci_visitante)::text)));


ALTER TABLE public.v_visita OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 32291)
-- Name: visita_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visita_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visita_id_seq OWNER TO postgres;

--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 240
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 3069 (class 2604 OID 31944)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 3070 (class 2604 OID 31955)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 3129 (class 2604 OID 32488)
-- Name: bitacora id_bitacora; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora ALTER COLUMN id_bitacora SET DEFAULT nextval('public.bitacora_id_bitacora_seq'::regclass);


--
-- TOC entry 3072 (class 2604 OID 31966)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 3090 (class 2604 OID 32069)
-- Name: cierre_de_mes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes ALTER COLUMN id SET DEFAULT nextval('public.cierre_de_mes_id_seq'::regclass);


--
-- TOC entry 3105 (class 2604 OID 32217)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_unidad_id_seq'::regclass);


--
-- TOC entry 3091 (class 2604 OID 32082)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 3100 (class 2604 OID 32153)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 3116 (class 2604 OID 32316)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 3094 (class 2604 OID 32117)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 3076 (class 2604 OID 31982)
-- Name: forma_pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago ALTER COLUMN id SET DEFAULT nextval('public.forma_pago_id_seq'::regclass);


--
-- TOC entry 3078 (class 2604 OID 31994)
-- Name: funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion ALTER COLUMN id SET DEFAULT nextval('public.funcion_id_seq'::regclass);


--
-- TOC entry 3096 (class 2604 OID 32129)
-- Name: gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto ALTER COLUMN id SET DEFAULT nextval('public.gasto_id_seq'::regclass);


--
-- TOC entry 3079 (class 2604 OID 32007)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 3109 (class 2604 OID 32280)
-- Name: mensaje id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje ALTER COLUMN id SET DEFAULT nextval('public.mensaje_id_seq'::regclass);


--
-- TOC entry 3117 (class 2604 OID 32337)
-- Name: puente_asambleas_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asambleas_propietario_id_seq'::regclass);


--
-- TOC entry 3118 (class 2604 OID 32355)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 3119 (class 2604 OID 32376)
-- Name: puente_gasto_concepto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto ALTER COLUMN id SET DEFAULT nextval('public.puente_gasto_concepto_id_seq'::regclass);


--
-- TOC entry 3120 (class 2604 OID 32394)
-- Name: puente_mensaje_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_mensaje_usuario_id_seq'::regclass);


--
-- TOC entry 3123 (class 2604 OID 32414)
-- Name: puente_persona_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_persona_condominio_id_seq'::regclass);


--
-- TOC entry 3124 (class 2604 OID 32432)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 3128 (class 2604 OID 32470)
-- Name: puente_tipo_funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion ALTER COLUMN id SET DEFAULT nextval('public.puente_tipo_funcion_id_seq'::regclass);


--
-- TOC entry 3125 (class 2604 OID 32450)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 3106 (class 2604 OID 32243)
-- Name: recibo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo ALTER COLUMN id SET DEFAULT nextval('public.recibo_id_seq'::regclass);


--
-- TOC entry 3085 (class 2604 OID 32038)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3086 (class 2604 OID 32046)
-- Name: tipo_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad ALTER COLUMN id SET DEFAULT nextval('public.tipo_unidad_id_seq'::regclass);


--
-- TOC entry 3088 (class 2604 OID 32055)
-- Name: tipo_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario ALTER COLUMN id SET DEFAULT nextval('public.tipo_usuario_id_seq'::regclass);


--
-- TOC entry 3103 (class 2604 OID 32203)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 3107 (class 2604 OID 32259)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3113 (class 2604 OID 32296)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3444 (class 0 OID 31941)
-- Dependencies: 197
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (1, 'PORTON', '-', '2020-07-12');


--
-- TOC entry 3446 (class 0 OID 31952)
-- Dependencies: 199
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banco VALUES (1, 'BANCO DE VENEZUELA', true);
INSERT INTO public.banco VALUES (2, 'BANCO DEL CARIBE', true);
INSERT INTO public.banco VALUES (3, 'BANCO PROVINCIAL', true);
INSERT INTO public.banco VALUES (4, 'BANCO BICENTENARIO', true);
INSERT INTO public.banco VALUES (5, 'BANESCO', true);
INSERT INTO public.banco VALUES (6, 'BANCO EXTERIOR', true);
INSERT INTO public.banco VALUES (7, 'BANCO BOD', true);


--
-- TOC entry 3508 (class 0 OID 32485)
-- Dependencies: 261
-- Data for Name: bitacora; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bitacora VALUES (1, 'Registro', 'banco', NULL, NULL, '(1,"Banco de Venezuela",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (2, 'Registro', 'banco', NULL, NULL, '(2,"Banco del Caribe",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (3, 'Registro', 'banco', NULL, NULL, '(3,"Banco Provincial",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (4, 'Registro', 'banco', NULL, NULL, '(4,"Banco Bicentenario",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (5, 'Registro', 'banco', NULL, NULL, '(5,Banesco,t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (6, 'Registro', 'banco', NULL, NULL, '(6,"Banco Exterior",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (7, 'Registro', 'banco', NULL, NULL, '(7,"Banco BOD",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (8, 'Registro', 'forma_pago', NULL, NULL, '(1,"Pago móvil",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (9, 'Registro', 'forma_pago', NULL, NULL, '(2,Transferencia,t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (10, 'Registro', 'forma_pago', NULL, NULL, '(3,Depósito,t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (11, 'Registro', 'forma_pago', NULL, NULL, '(4,Efectivo,t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (12, 'Registro', 'forma_pago', NULL, NULL, '(5,Cheque,t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (13, 'Registro', 'forma_pago', NULL, NULL, '(6,"Punto de venta",t)', '2020-07-23 18:17:28');
INSERT INTO public.bitacora VALUES (14, 'Registro', 'condominio', 1, NULL, '(32136565,"urb. el jardín",02548466543,dasd@hotmail.com,t)', '2020-07-23 18:23:29');
INSERT INTO public.bitacora VALUES (15, 'Registro', 'tipo_unidad', 1, NULL, '(1,200,TULIPAN,t)', '2020-07-23 18:32:47');
INSERT INTO public.bitacora VALUES (16, 'Registro', 'tipo_unidad', 1, NULL, '(2,150,ROSAS,t)', '2020-07-23 18:32:59');
INSERT INTO public.bitacora VALUES (17, 'Registro', 'propietario', 1, NULL, '(V-27328852,MARYORITH,NAZARETH,SINGER,MUJICA,04125084544,MAS@DASD.COM,t)', '2020-07-23 18:33:35');
INSERT INTO public.bitacora VALUES (18, 'Registro', 'propietario', 1, NULL, '(V-27699315,MARIA,MERCEDES,ALVAREZ,BARRIOS,04245616546,MAR@HOTMAS.CPM,t)', '2020-07-23 18:34:06');
INSERT INTO public.bitacora VALUES (19, 'Registro', 'unidad', NULL, NULL, '(1,1,243211656232465,-,t,,1)', '2020-07-23 18:34:25');
INSERT INTO public.bitacora VALUES (20, 'UPDATE', 'unidad', 1, '(1,1,243211656232465,-,t,,1)', '(1,1,243211656232465,-,t,1,1)', '2020-07-23 18:34:25');
INSERT INTO public.bitacora VALUES (21, 'Registro', 'unidad', NULL, NULL, '(2,2,325136164632136,-,t,,2)', '2020-07-23 18:34:41');
INSERT INTO public.bitacora VALUES (22, 'UPDATE', 'unidad', NULL, '(1,1,243211656232465,-,t,1,1)', '(1,1,243211656232465,-,t,0.5714285714285714,1)', '2020-07-23 18:34:41');
INSERT INTO public.bitacora VALUES (23, 'UPDATE', 'unidad', 1, '(2,2,325136164632136,-,t,,2)', '(2,2,325136164632136,-,t,0.42857142857142855,2)', '2020-07-23 18:34:41');
INSERT INTO public.bitacora VALUES (24, 'Registro', 'sancion', 1, NULL, '(1,MULTA,7,2020,10,-,Pendiente,DÓLAR)', '2020-07-23 18:35:29');
INSERT INTO public.bitacora VALUES (25, 'Registro', 'sancion', 1, NULL, '(2,"INTERES DE MORA",7,2020,3,-,Pendiente,BOLÍVAR)', '2020-07-23 18:35:48');
INSERT INTO public.bitacora VALUES (26, 'Registro', 'categoriagasto', 1, NULL, '(1,Reparaciones,-,t)', '2020-07-23 18:36:54');
INSERT INTO public.bitacora VALUES (27, 'Registro', 'categoriagasto', 1, NULL, '(2,compras,-,t)', '2020-07-23 18:37:06');
INSERT INTO public.bitacora VALUES (28, 'Registro', 'categoriagasto', 1, NULL, '(3,administrativo,-,t)', '2020-07-23 18:37:16');
INSERT INTO public.bitacora VALUES (29, 'Registro', 'concepto_gasto', 1, NULL, '(1,Porton,-,1,t)', '2020-07-23 18:37:34');
INSERT INTO public.bitacora VALUES (30, 'Registro', 'concepto_gasto', 1, NULL, '(2,Bombillos,-,2,t)', '2020-07-23 18:37:59');
INSERT INTO public.bitacora VALUES (31, 'Registro', 'concepto_gasto', 1, NULL, '(3,"pago nomina",-,3,t)', '2020-07-23 18:38:16');
INSERT INTO public.bitacora VALUES (32, 'Registro', 'proveedores', 1, NULL, '(J-32131635,"PORTONES LOS HERMANOS",025489802313,ASDAS@HOASD.COM,LUIS,-,t)', '2020-07-23 18:38:56');
INSERT INTO public.bitacora VALUES (33, 'Registro', 'proveedores', 1, NULL, '(J-12646500,LIBRERIA,025435132135,ASDASD@HJSADA.COM,ANNA,-,t)', '2020-07-23 18:39:38');
INSERT INTO public.bitacora VALUES (34, 'Registro', 'proveedores', 1, NULL, '(V-20888725,"ADMINISTRADORA CONTABLE",041265416513,SADAS@FASD.CO,MARIA,-,t)', '2020-07-23 18:40:23');
INSERT INTO public.bitacora VALUES (35, 'Registro', 'asambleas', 1, NULL, '(1,porton,-,2020-07-12)', '2020-07-23 18:41:11');
INSERT INTO public.bitacora VALUES (36, 'Modificado', 'concepto_gasto', 1, '(2,BOMBILLOS,-,2,t)', '(2,papeleria,-,2,t)', '2020-07-23 18:42:11');
INSERT INTO public.bitacora VALUES (37, 'Registro', 'gasto', 1, NULL, '(1,papeleria,EXTRAORDINARIO,J-12646500,ALICUOTA,7,2020,1,,-,1,200,200,Pendiente,Pendiente,BOLÍVAR)', '2020-07-23 19:05:46');
INSERT INTO public.bitacora VALUES (38, 'Registro', 'gasto', 1, NULL, '(2,porton,EXTRAORDINARIO,J-32131635,"TOTAL DE INMUEBLES",7,2020,1,1,-,1,50,50,Pendiente,Pendiente,DÓLAR)', '2020-07-23 19:06:31');


--
-- TOC entry 3448 (class 0 OID 31963)
-- Dependencies: 201
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (1, 'REPARACIONES', '-', true);
INSERT INTO public.categoriagasto VALUES (2, 'COMPRAS', '-', true);
INSERT INTO public.categoriagasto VALUES (3, 'ADMINISTRATIVO', '-', true);


--
-- TOC entry 3465 (class 0 OID 32066)
-- Dependencies: 218
-- Data for Name: cierre_de_mes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3480 (class 0 OID 32214)
-- Dependencies: 233
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3467 (class 0 OID 32079)
-- Dependencies: 220
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (1, 'PORTON', '-', 1, true);
INSERT INTO public.concepto_gasto VALUES (3, 'PAGO NOMINA', '-', 3, true);
INSERT INTO public.concepto_gasto VALUES (2, 'PAPELERIA', '-', 2, true);


--
-- TOC entry 3449 (class 0 OID 31970)
-- Dependencies: 202
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('32136565', 'URB. EL JARDÍN', '02548466543', 'DASD@HOTMAIL.COM', true);


--
-- TOC entry 3468 (class 0 OID 32091)
-- Dependencies: 221
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3474 (class 0 OID 32150)
-- Dependencies: 227
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3490 (class 0 OID 32313)
-- Dependencies: 243
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3470 (class 0 OID 32114)
-- Dependencies: 223
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3451 (class 0 OID 31979)
-- Dependencies: 204
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.forma_pago VALUES (1, 'PAGO MÓVIL', true);
INSERT INTO public.forma_pago VALUES (2, 'TRANSFERENCIA', true);
INSERT INTO public.forma_pago VALUES (3, 'DEPÓSITO', true);
INSERT INTO public.forma_pago VALUES (4, 'EFECTIVO', true);
INSERT INTO public.forma_pago VALUES (5, 'CHEQUE', true);
INSERT INTO public.forma_pago VALUES (6, 'PUNTO DE VENTA', true);


--
-- TOC entry 3453 (class 0 OID 31991)
-- Dependencies: 206
-- Data for Name: funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.funcion VALUES (1, 'Asambleas');
INSERT INTO public.funcion VALUES (2, 'Banco');
INSERT INTO public.funcion VALUES (3, 'Categoria Gastos');
INSERT INTO public.funcion VALUES (4, 'Concepto Gastos');
INSERT INTO public.funcion VALUES (5, 'Condominio');
INSERT INTO public.funcion VALUES (6, 'Cuenta');
INSERT INTO public.funcion VALUES (7, 'Cuentas por cobrar');
INSERT INTO public.funcion VALUES (8, 'Cuentas por pagar');
INSERT INTO public.funcion VALUES (9, 'Fondo');
INSERT INTO public.funcion VALUES (10, 'Forma de pago');
INSERT INTO public.funcion VALUES (11, 'Gasto');
INSERT INTO public.funcion VALUES (12, 'Generar recibo');
INSERT INTO public.funcion VALUES (13, 'Gestionar Usuario');
INSERT INTO public.funcion VALUES (14, 'Intereses');
INSERT INTO public.funcion VALUES (15, 'Propietarios');
INSERT INTO public.funcion VALUES (16, 'Proveedores');
INSERT INTO public.funcion VALUES (17, 'Recibo');
INSERT INTO public.funcion VALUES (18, 'Responsables');
INSERT INTO public.funcion VALUES (19, 'Sanciones');
INSERT INTO public.funcion VALUES (20, 'Tipo de unidad');
INSERT INTO public.funcion VALUES (21, 'Tipo de usuario');
INSERT INTO public.funcion VALUES (22, 'Unidades');
INSERT INTO public.funcion VALUES (23, 'Visitas autorizadas');


--
-- TOC entry 3472 (class 0 OID 32126)
-- Dependencies: 225
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto VALUES (1, 'PAPELERIA', 'EXTRAORDINARIO', 'J-12646500', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 200, 200, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (2, 'PORTON', 'EXTRAORDINARIO', 'J-32131635', 'TOTAL DE INMUEBLES', 7, 2020, 1, 1, '-', 1, 50, 50, 'Pendiente', 'Pendiente', 'DÓLAR');


--
-- TOC entry 3455 (class 0 OID 32004)
-- Dependencies: 208
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3486 (class 0 OID 32277)
-- Dependencies: 239
-- Data for Name: mensaje; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3456 (class 0 OID 32019)
-- Dependencies: 209
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.persona VALUES ('V-00000000', 'ADMIN', '', 'ISTRADOR', '', '0000-0000000', 'ADMIN@ADMIN.COM', true);
INSERT INTO public.persona VALUES ('V-27328852', 'MARYORITH', 'NAZARETH', 'SINGER', 'MUJICA', '04125084544', 'MAS@DASD.COM', true);
INSERT INTO public.persona VALUES ('V-27699315', 'MARIA', 'MERCEDES', 'ALVAREZ', 'BARRIOS', '04245616546', 'MAR@HOTMAS.CPM', true);


--
-- TOC entry 3475 (class 0 OID 32176)
-- Dependencies: 228
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietario VALUES ('V-27328852', true);
INSERT INTO public.propietario VALUES ('V-27699315', true);


--
-- TOC entry 3457 (class 0 OID 32027)
-- Dependencies: 210
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('J-32131635', 'PORTONES LOS HERMANOS', '025489802313', 'ASDAS@HOASD.COM', 'LUIS', '-', true);
INSERT INTO public.proveedores VALUES ('J-12646500', 'LIBRERIA', '025435132135', 'ASDASD@HJSADA.COM', 'ANNA', '-', true);
INSERT INTO public.proveedores VALUES ('V-20888725', 'ADMINISTRADORA CONTABLE', '041265416513', 'SADAS@FASD.CO', 'MARIA', '-', true);


--
-- TOC entry 3492 (class 0 OID 32334)
-- Dependencies: 245
-- Data for Name: puente_asambleas_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asambleas_propietario VALUES (1, 1, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (2, 1, 'V-27699315');


--
-- TOC entry 3494 (class 0 OID 32352)
-- Dependencies: 247
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3496 (class 0 OID 32373)
-- Dependencies: 249
-- Data for Name: puente_gasto_concepto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_gasto_concepto VALUES (1, 1, 2, 200);
INSERT INTO public.puente_gasto_concepto VALUES (2, 2, 2, 200);
INSERT INTO public.puente_gasto_concepto VALUES (3, 2, 1, 50);


--
-- TOC entry 3498 (class 0 OID 32391)
-- Dependencies: 251
-- Data for Name: puente_mensaje_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3500 (class 0 OID 32411)
-- Dependencies: 253
-- Data for Name: puente_persona_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3502 (class 0 OID 32429)
-- Dependencies: 255
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (1, 1, 1);
INSERT INTO public.puente_sancion_unidad VALUES (2, 2, 2);


--
-- TOC entry 3506 (class 0 OID 32467)
-- Dependencies: 259
-- Data for Name: puente_tipo_funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_tipo_funcion VALUES (1, 1, 1, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (2, 1, 2, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (3, 1, 3, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (4, 1, 4, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (5, 1, 5, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (6, 1, 6, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (7, 1, 7, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (8, 1, 8, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (9, 1, 9, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (10, 1, 10, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (11, 1, 11, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (12, 1, 12, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (13, 1, 13, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (14, 1, 14, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (15, 1, 15, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (16, 1, 16, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (17, 1, 17, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (18, 1, 18, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (19, 1, 19, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (20, 1, 20, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (21, 1, 21, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (22, 1, 22, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (23, 1, 23, true, true, true, true);


--
-- TOC entry 3504 (class 0 OID 32447)
-- Dependencies: 257
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (1, 'V-27328852', 1, '2020-07-23', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (2, 'V-27699315', 2, '2020-07-23', NULL, 1, true);


--
-- TOC entry 3482 (class 0 OID 32240)
-- Dependencies: 235
-- Data for Name: recibo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3476 (class 0 OID 32187)
-- Dependencies: 229
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.responsable VALUES ('V-00000000', true);


--
-- TOC entry 3459 (class 0 OID 32035)
-- Dependencies: 212
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (1, 'MULTA', 7, 2020, 10, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (2, 'INTERES DE MORA', 7, 2020, 3, '-', 'Pendiente', 'BOLÍVAR');


--
-- TOC entry 3461 (class 0 OID 32043)
-- Dependencies: 214
-- Data for Name: tipo_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_unidad VALUES (1, 200, 'TULIPAN', true);
INSERT INTO public.tipo_unidad VALUES (2, 150, 'ROSAS', true);


--
-- TOC entry 3463 (class 0 OID 32052)
-- Dependencies: 216
-- Data for Name: tipo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_usuario VALUES (1, 'ADMINISTRADOR', true);


--
-- TOC entry 3478 (class 0 OID 32200)
-- Dependencies: 231
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidad VALUES (1, '1', '243211656232465', '-', true, 0.5714285714285714, 1);
INSERT INTO public.unidad VALUES (2, '2', '325136164632136', '-', true, 0.42857142857142855, 2);


--
-- TOC entry 3484 (class 0 OID 32256)
-- Dependencies: 237
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (1, 'Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 'V-00000000', 1, true);


--
-- TOC entry 3488 (class 0 OID 32293)
-- Dependencies: 241
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 196
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 1, true);


--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 198
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 7, true);


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 260
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bitacora_id_bitacora_seq', 38, true);


--
-- TOC entry 3550 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 3, true);


--
-- TOC entry 3551 (class 0 OID 0)
-- Dependencies: 217
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cierre_de_mes_id_seq', 1, false);


--
-- TOC entry 3552 (class 0 OID 0)
-- Dependencies: 232
-- Name: cobro_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_unidad_id_seq', 1, false);


--
-- TOC entry 3553 (class 0 OID 0)
-- Dependencies: 219
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 3, true);


--
-- TOC entry 3554 (class 0 OID 0)
-- Dependencies: 226
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3555 (class 0 OID 0)
-- Dependencies: 242
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1, false);


--
-- TOC entry 3556 (class 0 OID 0)
-- Dependencies: 222
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 1, false);


--
-- TOC entry 3557 (class 0 OID 0)
-- Dependencies: 203
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pago_id_seq', 6, true);


--
-- TOC entry 3558 (class 0 OID 0)
-- Dependencies: 205
-- Name: funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcion_id_seq', 23, true);


--
-- TOC entry 3559 (class 0 OID 0)
-- Dependencies: 224
-- Name: gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_id_seq', 2, true);


--
-- TOC entry 3560 (class 0 OID 0)
-- Dependencies: 207
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 1, false);


--
-- TOC entry 3561 (class 0 OID 0)
-- Dependencies: 238
-- Name: mensaje_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mensaje_id_seq', 1, false);


--
-- TOC entry 3562 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_asambleas_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asambleas_propietario_id_seq', 2, true);


--
-- TOC entry 3563 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 1, false);


--
-- TOC entry 3564 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_gasto_concepto_id_seq', 3, true);


--
-- TOC entry 3565 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_mensaje_usuario_id_seq', 1, false);


--
-- TOC entry 3566 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_persona_condominio_id_seq', 1, false);


--
-- TOC entry 3567 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 2, true);


--
-- TOC entry 3568 (class 0 OID 0)
-- Dependencies: 258
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_tipo_funcion_id_seq', 23, true);


--
-- TOC entry 3569 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 2, true);


--
-- TOC entry 3570 (class 0 OID 0)
-- Dependencies: 234
-- Name: recibo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recibo_id_seq', 1, false);


--
-- TOC entry 3571 (class 0 OID 0)
-- Dependencies: 211
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 2, true);


--
-- TOC entry 3572 (class 0 OID 0)
-- Dependencies: 213
-- Name: tipo_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_unidad_id_seq', 2, true);


--
-- TOC entry 3573 (class 0 OID 0)
-- Dependencies: 215
-- Name: tipo_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_usuario_id_seq', 1, true);


--
-- TOC entry 3574 (class 0 OID 0)
-- Dependencies: 230
-- Name: unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidad_id_seq', 2, true);


--
-- TOC entry 3575 (class 0 OID 0)
-- Dependencies: 236
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 1, true);


--
-- TOC entry 3576 (class 0 OID 0)
-- Dependencies: 240
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 1, false);


--
-- TOC entry 3132 (class 2606 OID 31949)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 3134 (class 2606 OID 31960)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 3136 (class 2606 OID 31958)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 3210 (class 2606 OID 32494)
-- Name: bitacora bitacora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_pkey PRIMARY KEY (id_bitacora);


--
-- TOC entry 3138 (class 2606 OID 31969)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3162 (class 2606 OID 32071)
-- Name: cierre_de_mes cierre_de_mes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_pkey PRIMARY KEY (id);


--
-- TOC entry 3180 (class 2606 OID 32222)
-- Name: cobro_unidad cobro_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3164 (class 2606 OID 32085)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3140 (class 2606 OID 31976)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 3172 (class 2606 OID 32155)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3166 (class 2606 OID 32096)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 3192 (class 2606 OID 32321)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 3168 (class 2606 OID 32123)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 3142 (class 2606 OID 31988)
-- Name: forma_pago forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id);


--
-- TOC entry 3144 (class 2606 OID 32001)
-- Name: funcion funcion_funcion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_funcion_key UNIQUE (funcion);


--
-- TOC entry 3146 (class 2606 OID 31999)
-- Name: funcion funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3170 (class 2606 OID 32137)
-- Name: gasto gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3148 (class 2606 OID 32013)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 3188 (class 2606 OID 32285)
-- Name: mensaje mensaje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (id);


--
-- TOC entry 3150 (class 2606 OID 32026)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3174 (class 2606 OID 32181)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3152 (class 2606 OID 32032)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3194 (class 2606 OID 32339)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3196 (class 2606 OID 32360)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3198 (class 2606 OID 32378)
-- Name: puente_gasto_concepto puente_gasto_concepto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_pkey PRIMARY KEY (id);


--
-- TOC entry 3200 (class 2606 OID 32398)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3202 (class 2606 OID 32416)
-- Name: puente_persona_condominio puente_persona_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3204 (class 2606 OID 32434)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3208 (class 2606 OID 32472)
-- Name: puente_tipo_funcion puente_tipo_funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3206 (class 2606 OID 32454)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3182 (class 2606 OID 32248)
-- Name: recibo recibo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT recibo_pkey PRIMARY KEY (id);


--
-- TOC entry 3176 (class 2606 OID 32192)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3154 (class 2606 OID 32040)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3156 (class 2606 OID 32049)
-- Name: tipo_unidad tipo_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3158 (class 2606 OID 32061)
-- Name: tipo_usuario tipo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3160 (class 2606 OID 32063)
-- Name: tipo_usuario tipo_usuario_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_tipo_key UNIQUE (tipo);


--
-- TOC entry 3178 (class 2606 OID 32206)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3184 (class 2606 OID 32262)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3186 (class 2606 OID 32264)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3190 (class 2606 OID 32300)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3255 (class 2620 OID 32601)
-- Name: asambleas tg_asambleas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_asambleas BEFORE INSERT ON public.asambleas FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3257 (class 2620 OID 32602)
-- Name: banco tg_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3286 (class 2620 OID 32603)
-- Name: unidad tg_calcular_alicuota; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_calcular_alicuota AFTER INSERT OR UPDATE OF n_documento, direccion, id_tipo ON public.unidad FOR EACH STATEMENT EXECUTE PROCEDURE public.calcular_alicuota();


--
-- TOC entry 3259 (class 2620 OID 32604)
-- Name: categoriagasto tg_categoria_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_categoria_gasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3275 (class 2620 OID 32605)
-- Name: concepto_gasto tg_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3261 (class 2620 OID 32606)
-- Name: condominio tg_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3277 (class 2620 OID 32607)
-- Name: cuenta tg_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3283 (class 2620 OID 32608)
-- Name: cuenta_pagar tg_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3290 (class 2620 OID 32620)
-- Name: mensaje tg_eliminar_mensaje; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_eliminar_mensaje AFTER UPDATE ON public.mensaje FOR EACH ROW EXECUTE PROCEDURE public.eliminar_mensaje();


--
-- TOC entry 3294 (class 2620 OID 32621)
-- Name: puente_mensaje_usuario tg_eliminar_puente_mensaje; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_eliminar_puente_mensaje AFTER UPDATE ON public.puente_mensaje_usuario FOR EACH ROW EXECUTE PROCEDURE public.eliminar_mensaje();


--
-- TOC entry 3279 (class 2620 OID 32609)
-- Name: fondos tg_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3263 (class 2620 OID 32610)
-- Name: forma_pago tg_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3281 (class 2620 OID 32611)
-- Name: gasto tg_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3265 (class 2620 OID 32612)
-- Name: interes tg_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3254 (class 2620 OID 32581)
-- Name: asambleas tg_mayuscula_asambleas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_asambleas BEFORE INSERT OR UPDATE ON public.asambleas FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_asambleas();


--
-- TOC entry 3256 (class 2620 OID 32582)
-- Name: banco tg_mayuscula_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_banco();


--
-- TOC entry 3258 (class 2620 OID 32583)
-- Name: categoriagasto tg_mayuscula_categoriagasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_categoriagasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_categoriagasto();


--
-- TOC entry 3288 (class 2620 OID 32584)
-- Name: cobro_unidad tg_mayuscula_cobro_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cobro_unidad BEFORE INSERT OR UPDATE ON public.cobro_unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cobro_unidad();


--
-- TOC entry 3274 (class 2620 OID 32585)
-- Name: concepto_gasto tg_mayuscula_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_concepto_gasto();


--
-- TOC entry 3260 (class 2620 OID 32586)
-- Name: condominio tg_mayuscula_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_condominio();


--
-- TOC entry 3276 (class 2620 OID 32587)
-- Name: cuenta tg_mayuscula_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cuenta();


--
-- TOC entry 3282 (class 2620 OID 32588)
-- Name: cuenta_pagar tg_mayuscula_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cuenta_pagar();


--
-- TOC entry 3293 (class 2620 OID 32589)
-- Name: detalle_pagos tg_mayuscula_detalle_pagos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_detalle_pagos BEFORE INSERT OR UPDATE ON public.detalle_pagos FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_detalle_pagos();


--
-- TOC entry 3278 (class 2620 OID 32590)
-- Name: fondos tg_mayuscula_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_fondos();


--
-- TOC entry 3262 (class 2620 OID 32591)
-- Name: forma_pago tg_mayuscula_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_forma_pago();


--
-- TOC entry 3280 (class 2620 OID 32592)
-- Name: gasto tg_mayuscula_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_gasto();


--
-- TOC entry 3264 (class 2620 OID 32593)
-- Name: interes tg_mayuscula_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_interes();


--
-- TOC entry 3266 (class 2620 OID 32594)
-- Name: persona tg_mayuscula_persona; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_persona BEFORE INSERT OR UPDATE ON public.persona FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_persona();


--
-- TOC entry 3267 (class 2620 OID 32595)
-- Name: proveedores tg_mayuscula_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_proveedores();


--
-- TOC entry 3269 (class 2620 OID 32596)
-- Name: sancion tg_mayuscula_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_sancion();


--
-- TOC entry 3271 (class 2620 OID 32597)
-- Name: tipo_unidad tg_mayuscula_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_tipo_unidad();


--
-- TOC entry 3273 (class 2620 OID 32598)
-- Name: tipo_usuario tg_mayuscula_tipo_usuario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_tipo_usuario BEFORE INSERT OR UPDATE ON public.tipo_usuario FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_tipo_usuario();


--
-- TOC entry 3285 (class 2620 OID 32599)
-- Name: unidad tg_mayuscula_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_unidad();


--
-- TOC entry 3291 (class 2620 OID 32600)
-- Name: visita tg_mayuscula_visita; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_visita BEFORE INSERT OR UPDATE ON public.visita FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_visita();


--
-- TOC entry 3284 (class 2620 OID 32613)
-- Name: propietario tg_propietario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_propietario BEFORE INSERT OR UPDATE ON public.propietario FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3268 (class 2620 OID 32614)
-- Name: proveedores tg_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3289 (class 2620 OID 32615)
-- Name: recibo tg_recibo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_recibo BEFORE INSERT OR UPDATE ON public.recibo FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3270 (class 2620 OID 32616)
-- Name: sancion tg_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3272 (class 2620 OID 32617)
-- Name: tipo_unidad tg_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3287 (class 2620 OID 32618)
-- Name: unidad tg_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3292 (class 2620 OID 32619)
-- Name: visita tg_visita; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_visita BEFORE INSERT ON public.visita FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3253 (class 2606 OID 32495)
-- Name: bitacora bitacora_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3212 (class 2606 OID 32072)
-- Name: cierre_de_mes cierre_de_mes_id_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_id_condominio_fkey FOREIGN KEY (id_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3226 (class 2606 OID 32223)
-- Name: cobro_unidad cobro_unidad_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3227 (class 2606 OID 32228)
-- Name: cobro_unidad cobro_unidad_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3228 (class 2606 OID 32233)
-- Name: cobro_unidad cobro_unidad_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3213 (class 2606 OID 32086)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 3215 (class 2606 OID 32102)
-- Name: cuenta cuenta_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3214 (class 2606 OID 32097)
-- Name: cuenta cuenta_id_banco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_id_banco_fkey FOREIGN KEY (id_banco) REFERENCES public.banco(id);


--
-- TOC entry 3222 (class 2606 OID 32171)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3219 (class 2606 OID 32156)
-- Name: cuenta_pagar cuenta_pagar_id_forma_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_forma_pago_fkey FOREIGN KEY (id_forma_pago) REFERENCES public.forma_pago(id);


--
-- TOC entry 3220 (class 2606 OID 32161)
-- Name: cuenta_pagar cuenta_pagar_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3221 (class 2606 OID 32166)
-- Name: cuenta_pagar cuenta_pagar_n_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_n_cuenta_fkey FOREIGN KEY (n_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3216 (class 2606 OID 32107)
-- Name: cuenta cuenta_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3235 (class 2606 OID 32322)
-- Name: detalle_pagos detalle_pagos_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3236 (class 2606 OID 32327)
-- Name: detalle_pagos detalle_pagos_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3218 (class 2606 OID 32143)
-- Name: gasto gasto_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3217 (class 2606 OID 32138)
-- Name: gasto gasto_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 3211 (class 2606 OID 32014)
-- Name: interes interes_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3232 (class 2606 OID 32286)
-- Name: mensaje mensaje_emisor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_emisor_fkey FOREIGN KEY (emisor) REFERENCES public.usuario(id);


--
-- TOC entry 3223 (class 2606 OID 32182)
-- Name: propietario propietario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3238 (class 2606 OID 32345)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3237 (class 2606 OID 32340)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3240 (class 2606 OID 32366)
-- Name: puente_cobro_factura puente_cobro_factura_id_cobro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_id_cobro_fkey FOREIGN KEY (id_cobro) REFERENCES public.cobro_unidad(id);


--
-- TOC entry 3239 (class 2606 OID 32361)
-- Name: puente_cobro_factura puente_cobro_factura_id_recibo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_id_recibo_fkey FOREIGN KEY (id_recibo) REFERENCES public.detalle_pagos(id);


--
-- TOC entry 3242 (class 2606 OID 32384)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_concepto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_concepto_fkey FOREIGN KEY (id_concepto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3241 (class 2606 OID 32379)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3243 (class 2606 OID 32399)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_id_mensaje_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_id_mensaje_fkey FOREIGN KEY (id_mensaje) REFERENCES public.mensaje(id);


--
-- TOC entry 3244 (class 2606 OID 32404)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_receptor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_receptor_fkey FOREIGN KEY (receptor) REFERENCES public.usuario(id);


--
-- TOC entry 3245 (class 2606 OID 32417)
-- Name: puente_persona_condominio puente_persona_condominio_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3246 (class 2606 OID 32422)
-- Name: puente_persona_condominio puente_persona_condominio_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3247 (class 2606 OID 32435)
-- Name: puente_sancion_unidad puente_sancion_unidad_id_sancion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_id_sancion_fkey FOREIGN KEY (id_sancion) REFERENCES public.sancion(id);


--
-- TOC entry 3248 (class 2606 OID 32440)
-- Name: puente_sancion_unidad puente_sancion_unidad_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3252 (class 2606 OID 32478)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_funcion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_funcion_fkey FOREIGN KEY (id_funcion) REFERENCES public.funcion(id);


--
-- TOC entry 3251 (class 2606 OID 32473)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3249 (class 2606 OID 32455)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3250 (class 2606 OID 32460)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3229 (class 2606 OID 32249)
-- Name: recibo recibo_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT recibo_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3224 (class 2606 OID 32193)
-- Name: responsable responsable_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3225 (class 2606 OID 32207)
-- Name: unidad unidad_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_unidad(id);


--
-- TOC entry 3230 (class 2606 OID 32265)
-- Name: usuario usuario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3231 (class 2606 OID 32270)
-- Name: usuario usuario_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3233 (class 2606 OID 32301)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.persona(cedula);


--
-- TOC entry 3234 (class 2606 OID 32306)
-- Name: visita visita_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


-- Completed on 2020-07-23 20:29:40

--
-- PostgreSQL database dump complete
--

