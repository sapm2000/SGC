--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

-- Started on 2020-07-27 10:37:45

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3507 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 289 (class 1255 OID 38896)
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
-- TOC entry 290 (class 1255 OID 38897)
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
-- TOC entry 306 (class 1255 OID 38901)
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
-- TOC entry 311 (class 1255 OID 38906)
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
-- TOC entry 315 (class 1255 OID 38910)
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
-- TOC entry 318 (class 1255 OID 38912)
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
-- TOC entry 322 (class 1255 OID 38916)
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
-- TOC entry 323 (class 1255 OID 38917)
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
-- TOC entry 327 (class 1255 OID 38921)
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
-- TOC entry 330 (class 1255 OID 38925)
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
-- TOC entry 333 (class 1255 OID 38928)
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
-- TOC entry 337 (class 1255 OID 38932)
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
-- TOC entry 338 (class 1255 OID 38933)
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
-- TOC entry 343 (class 1255 OID 38937)
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
-- TOC entry 346 (class 1255 OID 38940)
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
-- TOC entry 351 (class 1255 OID 38945)
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
-- TOC entry 356 (class 1255 OID 38949)
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
-- TOC entry 376 (class 1255 OID 38971)
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
-- TOC entry 286 (class 1255 OID 38893)
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
-- TOC entry 285 (class 1255 OID 38892)
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
-- TOC entry 304 (class 1255 OID 38899)
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
-- TOC entry 308 (class 1255 OID 38903)
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
-- TOC entry 313 (class 1255 OID 38908)
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
-- TOC entry 320 (class 1255 OID 38914)
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
-- TOC entry 325 (class 1255 OID 38919)
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
-- TOC entry 329 (class 1255 OID 38923)
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
-- TOC entry 335 (class 1255 OID 38930)
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
-- TOC entry 377 (class 1255 OID 38972)
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
-- TOC entry 341 (class 1255 OID 38935)
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
-- TOC entry 345 (class 1255 OID 38939)
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
-- TOC entry 348 (class 1255 OID 38942)
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
-- TOC entry 354 (class 1255 OID 38947)
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
-- TOC entry 287 (class 1255 OID 38894)
-- Name: limpiar_mensaje(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.limpiar_mensaje() RETURNS void
    LANGUAGE plpgsql
    AS $$
	BEGIN

		DELETE FROM puente_mensaje_usuario AS pu WHERE (SELECT id FROM mensaje AS me WHERE pu.id_mensaje = me.id) = id_mensaje AND ((LOCALTIMESTAMP(0)::DATE) - (SELECT fecha FROM mensaje AS me WHERE pu.id_mensaje = me.id)::DATE > 90) ;
		
		DELETE FROM mensaje WHERE (LOCALTIMESTAMP(0)::DATE - fecha::DATE) > 90;
	 
	END;
	$$;


ALTER FUNCTION public.limpiar_mensaje() OWNER TO postgres;

--
-- TOC entry 379 (class 1255 OID 38974)
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
-- TOC entry 288 (class 1255 OID 38895)
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
-- TOC entry 358 (class 1255 OID 38951)
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
-- TOC entry 359 (class 1255 OID 38952)
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
-- TOC entry 360 (class 1255 OID 38953)
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
-- TOC entry 361 (class 1255 OID 38954)
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
-- TOC entry 362 (class 1255 OID 38955)
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
-- TOC entry 363 (class 1255 OID 38956)
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
-- TOC entry 364 (class 1255 OID 38957)
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
-- TOC entry 365 (class 1255 OID 38958)
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
-- TOC entry 366 (class 1255 OID 38959)
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
-- TOC entry 367 (class 1255 OID 38960)
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
-- TOC entry 368 (class 1255 OID 38961)
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
-- TOC entry 369 (class 1255 OID 38962)
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
-- TOC entry 370 (class 1255 OID 38963)
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
-- TOC entry 371 (class 1255 OID 38964)
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
-- TOC entry 372 (class 1255 OID 38965)
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
-- TOC entry 373 (class 1255 OID 38966)
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
-- TOC entry 374 (class 1255 OID 38967)
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
-- TOC entry 375 (class 1255 OID 38968)
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
-- TOC entry 339 (class 1255 OID 38969)
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
-- TOC entry 352 (class 1255 OID 38970)
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
-- TOC entry 291 (class 1255 OID 38898)
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
-- TOC entry 307 (class 1255 OID 38902)
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
-- TOC entry 312 (class 1255 OID 38907)
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
-- TOC entry 316 (class 1255 OID 38911)
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
-- TOC entry 319 (class 1255 OID 38913)
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
-- TOC entry 324 (class 1255 OID 38918)
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
-- TOC entry 328 (class 1255 OID 38922)
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
-- TOC entry 331 (class 1255 OID 38926)
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
-- TOC entry 334 (class 1255 OID 38929)
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
-- TOC entry 340 (class 1255 OID 38934)
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
-- TOC entry 344 (class 1255 OID 38938)
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
-- TOC entry 347 (class 1255 OID 38941)
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
-- TOC entry 350 (class 1255 OID 38944)
-- Name: modificar_tipo_usuario(character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_tipo_usuario(_tipo character varying, _id integer, _id_usuario integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	resul int;
	
BEGIN
	UPDATE tipo_usuario SET tipo = _tipo WHERE id = _id;
	GET DIAGNOSTICS resul = ROW_COUNT;

	IF resul > 0 THEN
	
 		UPDATE bitacora SET id_usuario = _id_usuario, operacion = 'Modificado'
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
		RETURN true;
		
	ELSE
		RETURN false;
	END IF;
END;
$$;


ALTER FUNCTION public.modificar_tipo_usuario(_tipo character varying, _id integer, _id_usuario integer) OWNER TO postgres;

--
-- TOC entry 353 (class 1255 OID 38946)
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
-- TOC entry 378 (class 1255 OID 38973)
-- Name: pagar_gasto(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.pagar_gasto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.pagar_gasto() OWNER TO postgres;

--
-- TOC entry 305 (class 1255 OID 38900)
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
-- TOC entry 309 (class 1255 OID 38904)
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
-- TOC entry 314 (class 1255 OID 38909)
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
-- TOC entry 321 (class 1255 OID 38915)
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
-- TOC entry 326 (class 1255 OID 38920)
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
-- TOC entry 317 (class 1255 OID 38924)
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
-- TOC entry 336 (class 1255 OID 38931)
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
-- TOC entry 342 (class 1255 OID 38936)
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
-- TOC entry 349 (class 1255 OID 38943)
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
-- TOC entry 355 (class 1255 OID 38948)
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
-- TOC entry 310 (class 1255 OID 38905)
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
-- TOC entry 332 (class 1255 OID 38927)
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
-- TOC entry 357 (class 1255 OID 38950)
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
-- TOC entry 197 (class 1259 OID 38336)
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
-- TOC entry 196 (class 1259 OID 38334)
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
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 196
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 199 (class 1259 OID 38347)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 38345)
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
-- TOC entry 3509 (class 0 OID 0)
-- Dependencies: 198
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 259 (class 1259 OID 38877)
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
-- TOC entry 258 (class 1259 OID 38875)
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
-- TOC entry 3510 (class 0 OID 0)
-- Dependencies: 258
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bitacora_id_bitacora_seq OWNED BY public.bitacora.id_bitacora;


--
-- TOC entry 201 (class 1259 OID 38358)
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
-- TOC entry 200 (class 1259 OID 38356)
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
-- TOC entry 3511 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 231 (class 1259 OID 38606)
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
-- TOC entry 230 (class 1259 OID 38604)
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
-- TOC entry 3512 (class 0 OID 0)
-- Dependencies: 230
-- Name: cobro_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_unidad_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 218 (class 1259 OID 38467)
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
-- TOC entry 217 (class 1259 OID 38465)
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
-- TOC entry 3513 (class 0 OID 0)
-- Dependencies: 217
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 202 (class 1259 OID 38367)
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
-- TOC entry 219 (class 1259 OID 38479)
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
-- TOC entry 225 (class 1259 OID 38538)
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
-- TOC entry 224 (class 1259 OID 38536)
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
-- TOC entry 3514 (class 0 OID 0)
-- Dependencies: 224
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 241 (class 1259 OID 38705)
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
-- TOC entry 240 (class 1259 OID 38703)
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
-- TOC entry 3515 (class 0 OID 0)
-- Dependencies: 240
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 221 (class 1259 OID 38502)
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
-- TOC entry 220 (class 1259 OID 38500)
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
-- TOC entry 3516 (class 0 OID 0)
-- Dependencies: 220
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 204 (class 1259 OID 38376)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pago (
    id integer NOT NULL,
    forma_pago character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 38374)
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
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 203
-- Name: forma_pago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pago_id_seq OWNED BY public.forma_pago.id;


--
-- TOC entry 206 (class 1259 OID 38390)
-- Name: funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcion (
    id integer NOT NULL,
    funcion character varying NOT NULL
);


ALTER TABLE public.funcion OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 38388)
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
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 205
-- Name: funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcion_id_seq OWNED BY public.funcion.id;


--
-- TOC entry 223 (class 1259 OID 38514)
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
-- TOC entry 222 (class 1259 OID 38512)
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
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 222
-- Name: gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_id_seq OWNED BY public.gasto.id;


--
-- TOC entry 208 (class 1259 OID 38403)
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
-- TOC entry 207 (class 1259 OID 38401)
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
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 207
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 237 (class 1259 OID 38669)
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
-- TOC entry 236 (class 1259 OID 38667)
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
-- TOC entry 3521 (class 0 OID 0)
-- Dependencies: 236
-- Name: mensaje_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mensaje_id_seq OWNED BY public.mensaje.id;


--
-- TOC entry 209 (class 1259 OID 38418)
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
-- TOC entry 226 (class 1259 OID 38564)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
    ci_persona character varying(11) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 38426)
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
-- TOC entry 243 (class 1259 OID 38726)
-- Name: puente_asambleas_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asambleas_propietario (
    id integer NOT NULL,
    id_asamblea integer NOT NULL,
    ci_propietario character varying(11) NOT NULL
);


ALTER TABLE public.puente_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 38724)
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
-- TOC entry 3522 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_asambleas_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asambleas_propietario_id_seq OWNED BY public.puente_asambleas_propietario.id;


--
-- TOC entry 245 (class 1259 OID 38744)
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
-- TOC entry 244 (class 1259 OID 38742)
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
-- TOC entry 3523 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 247 (class 1259 OID 38765)
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
-- TOC entry 246 (class 1259 OID 38763)
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
-- TOC entry 3524 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_gasto_concepto_id_seq OWNED BY public.puente_gasto_concepto.id;


--
-- TOC entry 249 (class 1259 OID 38783)
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
-- TOC entry 248 (class 1259 OID 38781)
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
-- TOC entry 3525 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_mensaje_usuario_id_seq OWNED BY public.puente_mensaje_usuario.id;


--
-- TOC entry 251 (class 1259 OID 38803)
-- Name: puente_persona_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_persona_condominio (
    id integer NOT NULL,
    ci_persona character varying(11) NOT NULL,
    rif_condominio character varying(15) NOT NULL
);


ALTER TABLE public.puente_persona_condominio OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 38801)
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
-- TOC entry 3526 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_persona_condominio_id_seq OWNED BY public.puente_persona_condominio.id;


--
-- TOC entry 253 (class 1259 OID 38821)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id integer NOT NULL,
    id_sancion integer NOT NULL,
    id_unidad bigint NOT NULL
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 38819)
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
-- TOC entry 3527 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 257 (class 1259 OID 38859)
-- Name: puente_tipo_funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_tipo_funcion (
    id integer NOT NULL,
    id_tipo integer NOT NULL,
    id_funcion integer NOT NULL,
    ver boolean,
    registrar boolean,
    modificar boolean,
    eliminar boolean
);


ALTER TABLE public.puente_tipo_funcion OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 38857)
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
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_tipo_funcion_id_seq OWNED BY public.puente_tipo_funcion.id;


--
-- TOC entry 255 (class 1259 OID 38839)
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
-- TOC entry 254 (class 1259 OID 38837)
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
-- TOC entry 3529 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 233 (class 1259 OID 38632)
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
-- TOC entry 232 (class 1259 OID 38630)
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
-- TOC entry 3530 (class 0 OID 0)
-- Dependencies: 232
-- Name: recibo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recibo_id_seq OWNED BY public.recibo.id;


--
-- TOC entry 227 (class 1259 OID 38575)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
    ci_persona character varying(11) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 38434)
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
-- TOC entry 211 (class 1259 OID 38432)
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
-- TOC entry 3531 (class 0 OID 0)
-- Dependencies: 211
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 214 (class 1259 OID 38442)
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
-- TOC entry 213 (class 1259 OID 38440)
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
-- TOC entry 3532 (class 0 OID 0)
-- Dependencies: 213
-- Name: tipo_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_unidad_id_seq OWNED BY public.tipo_unidad.id;


--
-- TOC entry 216 (class 1259 OID 38453)
-- Name: tipo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_usuario (
    id integer NOT NULL,
    tipo character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tipo_usuario OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 38451)
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
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 215
-- Name: tipo_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_usuario_id_seq OWNED BY public.tipo_usuario.id;


--
-- TOC entry 229 (class 1259 OID 38588)
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
-- TOC entry 228 (class 1259 OID 38586)
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
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 228
-- Name: unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidad_id_seq OWNED BY public.unidad.id;


--
-- TOC entry 235 (class 1259 OID 38648)
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
-- TOC entry 234 (class 1259 OID 38646)
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
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 234
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 279 (class 1259 OID 39103)
-- Name: v_area_total; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_area_total AS
 SELECT sum(tu.area) AS area
   FROM (public.tipo_unidad tu
     JOIN public.unidad u ON ((u.id_tipo = tu.id)))
  WHERE (u.activo = true);


ALTER TABLE public.v_area_total OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 39021)
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
-- TOC entry 260 (class 1259 OID 39017)
-- Name: v_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_propietario AS
 SELECT pro.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo,
    pro.activo
   FROM (public.propietario pro
     JOIN public.persona per ON (((per.cedula)::text = (pro.ci_persona)::text)));


ALTER TABLE public.v_propietario OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 39025)
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
-- TOC entry 272 (class 1259 OID 39071)
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
-- TOC entry 271 (class 1259 OID 39066)
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
-- TOC entry 263 (class 1259 OID 39029)
-- Name: v_bitacora; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_bitacora AS
 SELECT bitacora.id_bitacora,
    bitacora.operacion,
    bitacora.tabla,
    usu.usuario,
    usu.ci_persona AS cedula,
    concat(per.p_nombre, ' ', per.p_apellido) AS persona,
    bitacora.valor_viejo,
    bitacora.valor_nuevo,
    bitacora.fecha_hora AS fecha
   FROM ((public.bitacora
     JOIN public.usuario usu ON ((bitacora.id_usuario = usu.id)))
     JOIN public.persona per ON (((per.cedula)::text = (usu.ci_persona)::text)));


ALTER TABLE public.v_bitacora OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 39034)
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
-- TOC entry 265 (class 1259 OID 39038)
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
-- TOC entry 266 (class 1259 OID 39042)
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
-- TOC entry 267 (class 1259 OID 39047)
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
-- TOC entry 268 (class 1259 OID 39052)
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
-- TOC entry 269 (class 1259 OID 39056)
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
-- TOC entry 270 (class 1259 OID 39061)
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
-- TOC entry 273 (class 1259 OID 39076)
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
-- TOC entry 274 (class 1259 OID 39081)
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
-- TOC entry 275 (class 1259 OID 39086)
-- Name: v_permisos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_permisos AS
 SELECT u.usuario,
    tipo.id AS id_tipo,
    tipo.tipo,
    puente.id_funcion,
    f.funcion,
    puente.ver,
    puente.registrar,
    puente.modificar,
    puente.eliminar
   FROM (((public.puente_tipo_funcion puente
     JOIN public.tipo_usuario tipo ON ((tipo.id = puente.id_tipo)))
     JOIN public.funcion f ON ((f.id = puente.id_funcion)))
     JOIN public.usuario u ON ((u.id_tipo_usuario = puente.id_tipo)));


ALTER TABLE public.v_permisos OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 39091)
-- Name: v_responsable; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_responsable AS
 SELECT r.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo,
    r.activo
   FROM (public.responsable r
     JOIN public.persona per ON (((per.cedula)::text = (r.ci_persona)::text)));


ALTER TABLE public.v_responsable OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 39095)
-- Name: v_tipo_funcion; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_funcion AS
 SELECT tipo.id,
    puente.id_funcion,
    f.funcion,
    puente.ver,
    puente.registrar,
    puente.modificar,
    puente.eliminar
   FROM ((public.puente_tipo_funcion puente
     JOIN public.tipo_usuario tipo ON ((tipo.id = puente.id_tipo)))
     JOIN public.funcion f ON ((f.id = puente.id_funcion)));


ALTER TABLE public.v_tipo_funcion OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 39099)
-- Name: v_tipo_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_unidad AS
 SELECT tu.id,
    tu.tipo,
    tu.area,
    tu.activo
   FROM public.tipo_unidad tu;


ALTER TABLE public.v_tipo_unidad OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 39107)
-- Name: v_tipo_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_usuario AS
 SELECT tu.id,
    tu.tipo
   FROM public.tipo_usuario tu
  WHERE (tu.activo = true);


ALTER TABLE public.v_tipo_usuario OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 39111)
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
    ( SELECT tipo_unidad.area
           FROM public.tipo_unidad
          WHERE (tipo_unidad.id = u.id_tipo)) AS area,
    u.activo
   FROM (public.unidad u
     JOIN public.tipo_unidad tu ON ((tu.id = u.id_tipo)));


ALTER TABLE public.v_unidad OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 39116)
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
-- TOC entry 283 (class 1259 OID 39121)
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
-- TOC entry 239 (class 1259 OID 38685)
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
-- TOC entry 284 (class 1259 OID 39125)
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
-- TOC entry 238 (class 1259 OID 38683)
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
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 238
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 3057 (class 2604 OID 38339)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 3058 (class 2604 OID 38350)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 3116 (class 2604 OID 38880)
-- Name: bitacora id_bitacora; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora ALTER COLUMN id_bitacora SET DEFAULT nextval('public.bitacora_id_bitacora_seq'::regclass);


--
-- TOC entry 3060 (class 2604 OID 38361)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 3092 (class 2604 OID 38609)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_unidad_id_seq'::regclass);


--
-- TOC entry 3078 (class 2604 OID 38470)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 3087 (class 2604 OID 38541)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 3103 (class 2604 OID 38708)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 3081 (class 2604 OID 38505)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 3064 (class 2604 OID 38379)
-- Name: forma_pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago ALTER COLUMN id SET DEFAULT nextval('public.forma_pago_id_seq'::regclass);


--
-- TOC entry 3066 (class 2604 OID 38393)
-- Name: funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion ALTER COLUMN id SET DEFAULT nextval('public.funcion_id_seq'::regclass);


--
-- TOC entry 3083 (class 2604 OID 38517)
-- Name: gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto ALTER COLUMN id SET DEFAULT nextval('public.gasto_id_seq'::regclass);


--
-- TOC entry 3067 (class 2604 OID 38406)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 3096 (class 2604 OID 38672)
-- Name: mensaje id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje ALTER COLUMN id SET DEFAULT nextval('public.mensaje_id_seq'::regclass);


--
-- TOC entry 3104 (class 2604 OID 38729)
-- Name: puente_asambleas_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asambleas_propietario_id_seq'::regclass);


--
-- TOC entry 3105 (class 2604 OID 38747)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 3106 (class 2604 OID 38768)
-- Name: puente_gasto_concepto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto ALTER COLUMN id SET DEFAULT nextval('public.puente_gasto_concepto_id_seq'::regclass);


--
-- TOC entry 3107 (class 2604 OID 38786)
-- Name: puente_mensaje_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_mensaje_usuario_id_seq'::regclass);


--
-- TOC entry 3110 (class 2604 OID 38806)
-- Name: puente_persona_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_persona_condominio_id_seq'::regclass);


--
-- TOC entry 3111 (class 2604 OID 38824)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 3115 (class 2604 OID 38862)
-- Name: puente_tipo_funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion ALTER COLUMN id SET DEFAULT nextval('public.puente_tipo_funcion_id_seq'::regclass);


--
-- TOC entry 3112 (class 2604 OID 38842)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 3093 (class 2604 OID 38635)
-- Name: recibo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo ALTER COLUMN id SET DEFAULT nextval('public.recibo_id_seq'::regclass);


--
-- TOC entry 3073 (class 2604 OID 38437)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3074 (class 2604 OID 38445)
-- Name: tipo_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad ALTER COLUMN id SET DEFAULT nextval('public.tipo_unidad_id_seq'::regclass);


--
-- TOC entry 3076 (class 2604 OID 38456)
-- Name: tipo_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario ALTER COLUMN id SET DEFAULT nextval('public.tipo_usuario_id_seq'::regclass);


--
-- TOC entry 3090 (class 2604 OID 38591)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 3094 (class 2604 OID 38651)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3100 (class 2604 OID 38688)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3437 (class 0 OID 38336)
-- Dependencies: 197
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (1, 'PORTON', '-', '2020-07-07');


--
-- TOC entry 3439 (class 0 OID 38347)
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
-- TOC entry 3499 (class 0 OID 38877)
-- Dependencies: 259
-- Data for Name: bitacora; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bitacora VALUES (1, 'Registro', 'banco', NULL, NULL, '(1,"Banco de Venezuela",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (2, 'Registro', 'banco', NULL, NULL, '(2,"Banco del Caribe",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (3, 'Registro', 'banco', NULL, NULL, '(3,"Banco Provincial",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (4, 'Registro', 'banco', NULL, NULL, '(4,"Banco Bicentenario",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (5, 'Registro', 'banco', NULL, NULL, '(5,Banesco,t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (6, 'Registro', 'banco', NULL, NULL, '(6,"Banco Exterior",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (7, 'Registro', 'banco', NULL, NULL, '(7,"Banco BOD",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (8, 'Registro', 'forma_pago', NULL, NULL, '(1,"Pago móvil",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (9, 'Registro', 'forma_pago', NULL, NULL, '(2,Transferencia,t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (10, 'Registro', 'forma_pago', NULL, NULL, '(3,Depósito,t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (11, 'Registro', 'forma_pago', NULL, NULL, '(4,Efectivo,t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (12, 'Registro', 'forma_pago', NULL, NULL, '(5,Cheque,t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (13, 'Registro', 'forma_pago', NULL, NULL, '(6,"Punto de venta",t)', '2020-07-27 10:12:32');
INSERT INTO public.bitacora VALUES (14, 'Registro', 'condominio', 1, NULL, '(123456789,"urb. el jadín",02542101124,urbElJardin@hotmail.com,t)', '2020-07-27 10:18:25');
INSERT INTO public.bitacora VALUES (15, 'Registro', 'categoriagasto', 1, NULL, '(1,mantenimiento,-,t)', '2020-07-27 10:18:50');
INSERT INTO public.bitacora VALUES (16, 'Registro', 'categoriagasto', 1, NULL, '(2,papeleria,-,t)', '2020-07-27 10:19:05');
INSERT INTO public.bitacora VALUES (17, 'Registro', 'concepto_gasto', 1, NULL, '(1,"mantenimiento de porton",-,1,t)', '2020-07-27 10:19:45');
INSERT INTO public.bitacora VALUES (18, 'Registro', 'concepto_gasto', 1, NULL, '(2,compras,-,2,t)', '2020-07-27 10:20:03');
INSERT INTO public.bitacora VALUES (19, 'Registro', 'cuenta', 1, NULL, '(12345678912345678912,CORRIENTE,5,V-00000000,,t)', '2020-07-27 10:20:29');
INSERT INTO public.bitacora VALUES (20, 'Registro', 'fondos', 1, NULL, '(reserva,2020-07-20,-,-,1000,1000,1,t,DÓLAR)', '2020-07-27 10:22:20');
INSERT INTO public.bitacora VALUES (21, 'Registro', 'fondos', 1, NULL, '("gastos varios",2020-07-19,-,-,25000000,25000000,2,t,BOLÍVAR)', '2020-07-27 10:23:59');
INSERT INTO public.bitacora VALUES (22, 'Registro', 'interes', 1, NULL, '(1,mora,5,t,123456789)', '2020-07-27 10:24:27');
INSERT INTO public.bitacora VALUES (23, 'Registro', 'proveedores', 1, NULL, '(J-00220132,"PORTONES LOS HERMANOS",025435463516,PH@HOTMAIL.COM,LUIS,-,t)', '2020-07-27 10:25:11');
INSERT INTO public.bitacora VALUES (24, 'Registro', 'proveedores', 1, NULL, '(J-00121654,"LIBRERIA LA ROSA",025421650065,LR@HOTMAIL.COM,ANNA,-,t)', '2020-07-27 10:25:53');
INSERT INTO public.bitacora VALUES (25, 'Registro', 'tipo_unidad', 1, NULL, '(1,200,TULIPANES,t)', '2020-07-27 10:27:10');
INSERT INTO public.bitacora VALUES (26, 'Registro', 'tipo_unidad', 1, NULL, '(2,150,ROSAS,t)', '2020-07-27 10:27:19');
INSERT INTO public.bitacora VALUES (27, 'Registro', 'propietario', 1, NULL, '(V-27328852,MARYORITH,NAZARETH,SINGER,MUJICA,04125084544,MS@HOTMAIL.COM,t)', '2020-07-27 10:32:23');
INSERT INTO public.bitacora VALUES (28, 'Registro', 'propietario', 1, NULL, '(V-27699315,MARIA,MERCEDES,ALVAREZ,BARRIOS,04246351698,MA@HOTMAIL.COM,t)', '2020-07-27 10:32:59');
INSERT INTO public.bitacora VALUES (29, 'Registro', 'unidad', NULL, NULL, '(1,1,123456789123456,-,t,,1)', '2020-07-27 10:33:24');
INSERT INTO public.bitacora VALUES (30, 'UPDATE', 'unidad', 1, '(1,1,123456789123456,-,t,,1)', '(1,1,123456789123456,-,t,1,1)', '2020-07-27 10:33:24');
INSERT INTO public.bitacora VALUES (31, 'Registro', 'unidad', NULL, NULL, '(2,2,234567891234567,-,t,,2)', '2020-07-27 10:33:50');
INSERT INTO public.bitacora VALUES (32, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,-,t,1,1)', '(1,1,123456789123456,-,t,0.5714285714285714,1)', '2020-07-27 10:33:50');
INSERT INTO public.bitacora VALUES (33, 'UPDATE', 'unidad', 1, '(2,2,234567891234567,-,t,,2)', '(2,2,234567891234567,-,t,0.42857142857142855,2)', '2020-07-27 10:33:50');
INSERT INTO public.bitacora VALUES (34, 'Registro', 'asambleas', 1, NULL, '(1,porton,-,2020-07-07)', '2020-07-27 10:34:21');
INSERT INTO public.bitacora VALUES (35, 'Registro', 'gasto', 1, NULL, '(1,"compra de papeleria",ORDINARIO,J-00121654,ALICUOTA,7,2020,1,,-,1,1000000,1000000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-27 10:35:33');
INSERT INTO public.bitacora VALUES (36, 'Registro', 'gasto', 1, NULL, '(2,porton,EXTRAORDINARIO,J-00220132,"TOTAL DE INMUEBLES",7,2020,1,1,-,1,400,400,Pendiente,Pendiente,DÓLAR)', '2020-07-27 10:36:44');


--
-- TOC entry 3441 (class 0 OID 38358)
-- Dependencies: 201
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (1, 'MANTENIMIENTO', '-', true);
INSERT INTO public.categoriagasto VALUES (2, 'PAPELERIA', '-', true);


--
-- TOC entry 3471 (class 0 OID 38606)
-- Dependencies: 231
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3458 (class 0 OID 38467)
-- Dependencies: 218
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (1, 'MANTENIMIENTO DE PORTON', '-', 1, true);
INSERT INTO public.concepto_gasto VALUES (2, 'COMPRAS', '-', 2, true);


--
-- TOC entry 3442 (class 0 OID 38367)
-- Dependencies: 202
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('123456789', 'URB. EL JADÍN', '02542101124', 'URBELJARDIN@HOTMAIL.COM', true);


--
-- TOC entry 3459 (class 0 OID 38479)
-- Dependencies: 219
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('12345678912345678912', 'CORRIENTE', 5, 'V-00000000', NULL, true);


--
-- TOC entry 3465 (class 0 OID 38538)
-- Dependencies: 225
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3481 (class 0 OID 38705)
-- Dependencies: 241
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3461 (class 0 OID 38502)
-- Dependencies: 221
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('RESERVA', '2020-07-20', '-', '-', 1000, 1000, 1, true, 'DÓLAR');
INSERT INTO public.fondos VALUES ('GASTOS VARIOS', '2020-07-19', '-', '-', 25000000, 25000000, 2, true, 'BOLÍVAR');


--
-- TOC entry 3444 (class 0 OID 38376)
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
-- TOC entry 3446 (class 0 OID 38390)
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
-- TOC entry 3463 (class 0 OID 38514)
-- Dependencies: 223
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto VALUES (1, 'COMPRA DE PAPELERIA', 'ORDINARIO', 'J-00121654', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 1000000, 1000000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (2, 'PORTON', 'EXTRAORDINARIO', 'J-00220132', 'TOTAL DE INMUEBLES', 7, 2020, 1, 1, '-', 1, 400, 400, 'Pendiente', 'Pendiente', 'DÓLAR');


--
-- TOC entry 3448 (class 0 OID 38403)
-- Dependencies: 208
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (1, 'MORA', 5, true, '123456789');


--
-- TOC entry 3477 (class 0 OID 38669)
-- Dependencies: 237
-- Data for Name: mensaje; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3449 (class 0 OID 38418)
-- Dependencies: 209
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.persona VALUES ('V-00000000', 'ADMIN', '', 'ISTRADOR', '', '0000-0000000', 'ADMIN@ADMIN.COM', true);
INSERT INTO public.persona VALUES ('E-00000000', 'ASASD', 'ASDAS', 'ASDAD', 'ASDASD', '24323423423', 'ASDAS', true);
INSERT INTO public.persona VALUES ('V-27328852', 'MARYORITH', 'NAZARETH', 'SINGER', 'MUJICA', '04125084544', 'MS@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-27699315', 'MARIA', 'MERCEDES', 'ALVAREZ', 'BARRIOS', '04246351698', 'MA@HOTMAIL.COM', true);


--
-- TOC entry 3466 (class 0 OID 38564)
-- Dependencies: 226
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietario VALUES ('V-27328852', true);
INSERT INTO public.propietario VALUES ('V-27699315', true);


--
-- TOC entry 3450 (class 0 OID 38426)
-- Dependencies: 210
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('J-00220132', 'PORTONES LOS HERMANOS', '025435463516', 'PH@HOTMAIL.COM', 'LUIS', '-', true);
INSERT INTO public.proveedores VALUES ('J-00121654', 'LIBRERIA LA ROSA', '025421650065', 'LR@HOTMAIL.COM', 'ANNA', '-', true);


--
-- TOC entry 3483 (class 0 OID 38726)
-- Dependencies: 243
-- Data for Name: puente_asambleas_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asambleas_propietario VALUES (1, 1, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (2, 1, 'V-27699315');


--
-- TOC entry 3485 (class 0 OID 38744)
-- Dependencies: 245
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3487 (class 0 OID 38765)
-- Dependencies: 247
-- Data for Name: puente_gasto_concepto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_gasto_concepto VALUES (1, 1, 2, 1000000);
INSERT INTO public.puente_gasto_concepto VALUES (2, 2, 1, 400);


--
-- TOC entry 3489 (class 0 OID 38783)
-- Dependencies: 249
-- Data for Name: puente_mensaje_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3491 (class 0 OID 38803)
-- Dependencies: 251
-- Data for Name: puente_persona_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3493 (class 0 OID 38821)
-- Dependencies: 253
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3497 (class 0 OID 38859)
-- Dependencies: 257
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
-- TOC entry 3495 (class 0 OID 38839)
-- Dependencies: 255
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (1, 'V-27328852', 1, '2020-07-27', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (2, 'V-27699315', 2, '2020-07-27', NULL, 1, true);


--
-- TOC entry 3473 (class 0 OID 38632)
-- Dependencies: 233
-- Data for Name: recibo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3467 (class 0 OID 38575)
-- Dependencies: 227
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.responsable VALUES ('E-00000000', false);
INSERT INTO public.responsable VALUES ('V-00000000', true);


--
-- TOC entry 3452 (class 0 OID 38434)
-- Dependencies: 212
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3454 (class 0 OID 38442)
-- Dependencies: 214
-- Data for Name: tipo_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_unidad VALUES (1, 200, 'TULIPANES', true);
INSERT INTO public.tipo_unidad VALUES (2, 150, 'ROSAS', true);


--
-- TOC entry 3456 (class 0 OID 38453)
-- Dependencies: 216
-- Data for Name: tipo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_usuario VALUES (1, 'ADMINISTRADOR', true);


--
-- TOC entry 3469 (class 0 OID 38588)
-- Dependencies: 229
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidad VALUES (1, '1', '123456789123456', '-', true, 0.5714285714285714, 1);
INSERT INTO public.unidad VALUES (2, '2', '234567891234567', '-', true, 0.42857142857142855, 2);


--
-- TOC entry 3475 (class 0 OID 38648)
-- Dependencies: 235
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (1, 'Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 'V-00000000', 1, true);


--
-- TOC entry 3479 (class 0 OID 38685)
-- Dependencies: 239
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 196
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 1, true);


--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 198
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 7, true);


--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 258
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bitacora_id_bitacora_seq', 36, true);


--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 2, true);


--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 230
-- Name: cobro_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_unidad_id_seq', 1, false);


--
-- TOC entry 3542 (class 0 OID 0)
-- Dependencies: 217
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 2, true);


--
-- TOC entry 3543 (class 0 OID 0)
-- Dependencies: 224
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3544 (class 0 OID 0)
-- Dependencies: 240
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1, false);


--
-- TOC entry 3545 (class 0 OID 0)
-- Dependencies: 220
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 2, true);


--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 203
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pago_id_seq', 6, true);


--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 205
-- Name: funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcion_id_seq', 23, true);


--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 222
-- Name: gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_id_seq', 2, true);


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 207
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 1, true);


--
-- TOC entry 3550 (class 0 OID 0)
-- Dependencies: 236
-- Name: mensaje_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mensaje_id_seq', 1, false);


--
-- TOC entry 3551 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_asambleas_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asambleas_propietario_id_seq', 2, true);


--
-- TOC entry 3552 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 1, false);


--
-- TOC entry 3553 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_gasto_concepto_id_seq', 2, true);


--
-- TOC entry 3554 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_mensaje_usuario_id_seq', 1, false);


--
-- TOC entry 3555 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_persona_condominio_id_seq', 1, false);


--
-- TOC entry 3556 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 1, false);


--
-- TOC entry 3557 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_tipo_funcion_id_seq', 23, true);


--
-- TOC entry 3558 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 2, true);


--
-- TOC entry 3559 (class 0 OID 0)
-- Dependencies: 232
-- Name: recibo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recibo_id_seq', 1, false);


--
-- TOC entry 3560 (class 0 OID 0)
-- Dependencies: 211
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 1, false);


--
-- TOC entry 3561 (class 0 OID 0)
-- Dependencies: 213
-- Name: tipo_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_unidad_id_seq', 2, true);


--
-- TOC entry 3562 (class 0 OID 0)
-- Dependencies: 215
-- Name: tipo_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_usuario_id_seq', 1, true);


--
-- TOC entry 3563 (class 0 OID 0)
-- Dependencies: 228
-- Name: unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidad_id_seq', 2, true);


--
-- TOC entry 3564 (class 0 OID 0)
-- Dependencies: 234
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 1, true);


--
-- TOC entry 3565 (class 0 OID 0)
-- Dependencies: 238
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 1, false);


--
-- TOC entry 3119 (class 2606 OID 38344)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 3121 (class 2606 OID 38355)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 3123 (class 2606 OID 38353)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 3205 (class 2606 OID 38886)
-- Name: bitacora bitacora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_pkey PRIMARY KEY (id_bitacora);


--
-- TOC entry 3125 (class 2606 OID 38366)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 3127 (class 2606 OID 38364)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3175 (class 2606 OID 38614)
-- Name: cobro_unidad cobro_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3155 (class 2606 OID 38473)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3129 (class 2606 OID 38373)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 3163 (class 2606 OID 38543)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3157 (class 2606 OID 38484)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 3187 (class 2606 OID 38713)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 3159 (class 2606 OID 38511)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 3131 (class 2606 OID 38387)
-- Name: forma_pago forma_pago_forma_pago_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_forma_pago_key UNIQUE (forma_pago);


--
-- TOC entry 3133 (class 2606 OID 38385)
-- Name: forma_pago forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id);


--
-- TOC entry 3135 (class 2606 OID 38400)
-- Name: funcion funcion_funcion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_funcion_key UNIQUE (funcion);


--
-- TOC entry 3137 (class 2606 OID 38398)
-- Name: funcion funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3161 (class 2606 OID 38525)
-- Name: gasto gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3139 (class 2606 OID 38412)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 3183 (class 2606 OID 38677)
-- Name: mensaje mensaje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (id);


--
-- TOC entry 3141 (class 2606 OID 38425)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3165 (class 2606 OID 38569)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3143 (class 2606 OID 38431)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3189 (class 2606 OID 38731)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3191 (class 2606 OID 38752)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3193 (class 2606 OID 38770)
-- Name: puente_gasto_concepto puente_gasto_concepto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 38790)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 38808)
-- Name: puente_persona_condominio puente_persona_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3199 (class 2606 OID 38826)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3203 (class 2606 OID 38864)
-- Name: puente_tipo_funcion puente_tipo_funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 38846)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3177 (class 2606 OID 38640)
-- Name: recibo recibo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT recibo_pkey PRIMARY KEY (id);


--
-- TOC entry 3167 (class 2606 OID 38580)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3145 (class 2606 OID 38439)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3147 (class 2606 OID 38448)
-- Name: tipo_unidad tipo_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3149 (class 2606 OID 38450)
-- Name: tipo_unidad tipo_unidad_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_tipo_key UNIQUE (tipo);


--
-- TOC entry 3151 (class 2606 OID 38462)
-- Name: tipo_usuario tipo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3153 (class 2606 OID 38464)
-- Name: tipo_usuario tipo_usuario_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_tipo_key UNIQUE (tipo);


--
-- TOC entry 3169 (class 2606 OID 38598)
-- Name: unidad unidad_n_documento_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_n_documento_key UNIQUE (n_documento);


--
-- TOC entry 3171 (class 2606 OID 38596)
-- Name: unidad unidad_n_unidad_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_n_unidad_key UNIQUE (n_unidad);


--
-- TOC entry 3173 (class 2606 OID 38594)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3179 (class 2606 OID 38654)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3181 (class 2606 OID 38656)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3185 (class 2606 OID 38692)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2620 OID 38995)
-- Name: asambleas tg_asambleas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_asambleas BEFORE INSERT ON public.asambleas FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3251 (class 2620 OID 38996)
-- Name: banco tg_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3281 (class 2620 OID 38997)
-- Name: unidad tg_calcular_alicuota; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_calcular_alicuota AFTER INSERT OR UPDATE OF n_documento, direccion, id_tipo ON public.unidad FOR EACH STATEMENT EXECUTE PROCEDURE public.calcular_alicuota();


--
-- TOC entry 3253 (class 2620 OID 38998)
-- Name: categoriagasto tg_categoria_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_categoria_gasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3269 (class 2620 OID 38999)
-- Name: concepto_gasto tg_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3255 (class 2620 OID 39000)
-- Name: condominio tg_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3271 (class 2620 OID 39001)
-- Name: cuenta tg_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3277 (class 2620 OID 39002)
-- Name: cuenta_pagar tg_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3285 (class 2620 OID 39014)
-- Name: mensaje tg_eliminar_mensaje; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_eliminar_mensaje AFTER UPDATE ON public.mensaje FOR EACH ROW EXECUTE PROCEDURE public.eliminar_mensaje();


--
-- TOC entry 3289 (class 2620 OID 39015)
-- Name: puente_mensaje_usuario tg_eliminar_puente_mensaje; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_eliminar_puente_mensaje AFTER UPDATE ON public.puente_mensaje_usuario FOR EACH ROW EXECUTE PROCEDURE public.eliminar_mensaje();


--
-- TOC entry 3273 (class 2620 OID 39003)
-- Name: fondos tg_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3257 (class 2620 OID 39004)
-- Name: forma_pago tg_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3275 (class 2620 OID 39005)
-- Name: gasto tg_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3259 (class 2620 OID 39006)
-- Name: interes tg_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3248 (class 2620 OID 38975)
-- Name: asambleas tg_mayuscula_asambleas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_asambleas BEFORE INSERT OR UPDATE ON public.asambleas FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_asambleas();


--
-- TOC entry 3250 (class 2620 OID 38976)
-- Name: banco tg_mayuscula_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_banco();


--
-- TOC entry 3252 (class 2620 OID 38977)
-- Name: categoriagasto tg_mayuscula_categoriagasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_categoriagasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_categoriagasto();


--
-- TOC entry 3283 (class 2620 OID 38978)
-- Name: cobro_unidad tg_mayuscula_cobro_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cobro_unidad BEFORE INSERT OR UPDATE ON public.cobro_unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cobro_unidad();


--
-- TOC entry 3268 (class 2620 OID 38979)
-- Name: concepto_gasto tg_mayuscula_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_concepto_gasto();


--
-- TOC entry 3254 (class 2620 OID 38980)
-- Name: condominio tg_mayuscula_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_condominio();


--
-- TOC entry 3270 (class 2620 OID 38981)
-- Name: cuenta tg_mayuscula_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cuenta();


--
-- TOC entry 3276 (class 2620 OID 38982)
-- Name: cuenta_pagar tg_mayuscula_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cuenta_pagar();


--
-- TOC entry 3288 (class 2620 OID 38983)
-- Name: detalle_pagos tg_mayuscula_detalle_pagos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_detalle_pagos BEFORE INSERT OR UPDATE ON public.detalle_pagos FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_detalle_pagos();


--
-- TOC entry 3272 (class 2620 OID 38984)
-- Name: fondos tg_mayuscula_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_fondos();


--
-- TOC entry 3256 (class 2620 OID 38985)
-- Name: forma_pago tg_mayuscula_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_forma_pago();


--
-- TOC entry 3274 (class 2620 OID 38986)
-- Name: gasto tg_mayuscula_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_gasto();


--
-- TOC entry 3258 (class 2620 OID 38987)
-- Name: interes tg_mayuscula_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_interes();


--
-- TOC entry 3260 (class 2620 OID 38988)
-- Name: persona tg_mayuscula_persona; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_persona BEFORE INSERT OR UPDATE ON public.persona FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_persona();


--
-- TOC entry 3261 (class 2620 OID 38989)
-- Name: proveedores tg_mayuscula_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_proveedores();


--
-- TOC entry 3263 (class 2620 OID 38990)
-- Name: sancion tg_mayuscula_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_sancion();


--
-- TOC entry 3265 (class 2620 OID 38991)
-- Name: tipo_unidad tg_mayuscula_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_tipo_unidad();


--
-- TOC entry 3267 (class 2620 OID 38992)
-- Name: tipo_usuario tg_mayuscula_tipo_usuario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_tipo_usuario BEFORE INSERT OR UPDATE ON public.tipo_usuario FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_tipo_usuario();


--
-- TOC entry 3280 (class 2620 OID 38993)
-- Name: unidad tg_mayuscula_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_unidad();


--
-- TOC entry 3286 (class 2620 OID 38994)
-- Name: visita tg_mayuscula_visita; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_visita BEFORE INSERT OR UPDATE ON public.visita FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_visita();


--
-- TOC entry 3279 (class 2620 OID 39007)
-- Name: propietario tg_propietario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_propietario BEFORE INSERT OR UPDATE ON public.propietario FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3262 (class 2620 OID 39008)
-- Name: proveedores tg_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3284 (class 2620 OID 39009)
-- Name: recibo tg_recibo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_recibo BEFORE INSERT OR UPDATE ON public.recibo FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3278 (class 2620 OID 39016)
-- Name: cuenta_pagar tg_restar_saldo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_restar_saldo AFTER INSERT ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.pagar_gasto();


--
-- TOC entry 3264 (class 2620 OID 39010)
-- Name: sancion tg_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3266 (class 2620 OID 39011)
-- Name: tipo_unidad tg_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3282 (class 2620 OID 39012)
-- Name: unidad tg_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3287 (class 2620 OID 39013)
-- Name: visita tg_visita; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_visita BEFORE INSERT ON public.visita FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3247 (class 2606 OID 38887)
-- Name: bitacora bitacora_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3220 (class 2606 OID 38615)
-- Name: cobro_unidad cobro_unidad_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3221 (class 2606 OID 38620)
-- Name: cobro_unidad cobro_unidad_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3222 (class 2606 OID 38625)
-- Name: cobro_unidad cobro_unidad_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3207 (class 2606 OID 38474)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 3209 (class 2606 OID 38490)
-- Name: cuenta cuenta_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3208 (class 2606 OID 38485)
-- Name: cuenta cuenta_id_banco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_id_banco_fkey FOREIGN KEY (id_banco) REFERENCES public.banco(id);


--
-- TOC entry 3216 (class 2606 OID 38559)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3213 (class 2606 OID 38544)
-- Name: cuenta_pagar cuenta_pagar_id_forma_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_forma_pago_fkey FOREIGN KEY (id_forma_pago) REFERENCES public.forma_pago(id);


--
-- TOC entry 3214 (class 2606 OID 38549)
-- Name: cuenta_pagar cuenta_pagar_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3215 (class 2606 OID 38554)
-- Name: cuenta_pagar cuenta_pagar_n_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_n_cuenta_fkey FOREIGN KEY (n_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3210 (class 2606 OID 38495)
-- Name: cuenta cuenta_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3229 (class 2606 OID 38714)
-- Name: detalle_pagos detalle_pagos_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3230 (class 2606 OID 38719)
-- Name: detalle_pagos detalle_pagos_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3212 (class 2606 OID 38531)
-- Name: gasto gasto_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3211 (class 2606 OID 38526)
-- Name: gasto gasto_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 3206 (class 2606 OID 38413)
-- Name: interes interes_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3226 (class 2606 OID 38678)
-- Name: mensaje mensaje_emisor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_emisor_fkey FOREIGN KEY (emisor) REFERENCES public.usuario(id);


--
-- TOC entry 3217 (class 2606 OID 38570)
-- Name: propietario propietario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3232 (class 2606 OID 38737)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3231 (class 2606 OID 38732)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3234 (class 2606 OID 38758)
-- Name: puente_cobro_factura puente_cobro_factura_id_cobro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_id_cobro_fkey FOREIGN KEY (id_cobro) REFERENCES public.cobro_unidad(id);


--
-- TOC entry 3233 (class 2606 OID 38753)
-- Name: puente_cobro_factura puente_cobro_factura_id_recibo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_id_recibo_fkey FOREIGN KEY (id_recibo) REFERENCES public.recibo(id);


--
-- TOC entry 3236 (class 2606 OID 38776)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_concepto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_concepto_fkey FOREIGN KEY (id_concepto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3235 (class 2606 OID 38771)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3237 (class 2606 OID 38791)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_id_mensaje_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_id_mensaje_fkey FOREIGN KEY (id_mensaje) REFERENCES public.mensaje(id);


--
-- TOC entry 3238 (class 2606 OID 38796)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_receptor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_receptor_fkey FOREIGN KEY (receptor) REFERENCES public.usuario(id);


--
-- TOC entry 3239 (class 2606 OID 38809)
-- Name: puente_persona_condominio puente_persona_condominio_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3240 (class 2606 OID 38814)
-- Name: puente_persona_condominio puente_persona_condominio_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3241 (class 2606 OID 38827)
-- Name: puente_sancion_unidad puente_sancion_unidad_id_sancion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_id_sancion_fkey FOREIGN KEY (id_sancion) REFERENCES public.sancion(id);


--
-- TOC entry 3242 (class 2606 OID 38832)
-- Name: puente_sancion_unidad puente_sancion_unidad_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3246 (class 2606 OID 38870)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_funcion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_funcion_fkey FOREIGN KEY (id_funcion) REFERENCES public.funcion(id);


--
-- TOC entry 3245 (class 2606 OID 38865)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3243 (class 2606 OID 38847)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3244 (class 2606 OID 38852)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3223 (class 2606 OID 38641)
-- Name: recibo recibo_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT recibo_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3218 (class 2606 OID 38581)
-- Name: responsable responsable_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3219 (class 2606 OID 38599)
-- Name: unidad unidad_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_unidad(id);


--
-- TOC entry 3224 (class 2606 OID 38657)
-- Name: usuario usuario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3225 (class 2606 OID 38662)
-- Name: usuario usuario_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3227 (class 2606 OID 38693)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.persona(cedula);


--
-- TOC entry 3228 (class 2606 OID 38698)
-- Name: visita visita_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


-- Completed on 2020-07-27 10:37:51

--
-- PostgreSQL database dump complete
--
