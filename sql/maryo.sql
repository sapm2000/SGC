--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

-- Started on 2020-07-28 18:14:19

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
-- TOC entry 289 (class 1255 OID 39694)
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
-- TOC entry 290 (class 1255 OID 39695)
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
-- TOC entry 306 (class 1255 OID 39699)
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
-- TOC entry 311 (class 1255 OID 39704)
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
-- TOC entry 315 (class 1255 OID 39708)
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
-- TOC entry 318 (class 1255 OID 39710)
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
-- TOC entry 322 (class 1255 OID 39714)
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
-- TOC entry 323 (class 1255 OID 39715)
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
-- TOC entry 327 (class 1255 OID 39719)
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
-- TOC entry 330 (class 1255 OID 39723)
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
-- TOC entry 333 (class 1255 OID 39726)
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
-- TOC entry 337 (class 1255 OID 39730)
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
-- TOC entry 338 (class 1255 OID 39731)
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
-- TOC entry 343 (class 1255 OID 39735)
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
-- TOC entry 346 (class 1255 OID 39738)
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
-- TOC entry 351 (class 1255 OID 39743)
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
-- TOC entry 356 (class 1255 OID 39747)
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
-- TOC entry 376 (class 1255 OID 39769)
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
-- TOC entry 286 (class 1255 OID 39691)
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
-- TOC entry 285 (class 1255 OID 39690)
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
-- TOC entry 304 (class 1255 OID 39697)
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
-- TOC entry 308 (class 1255 OID 39701)
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
-- TOC entry 313 (class 1255 OID 39706)
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
-- TOC entry 320 (class 1255 OID 39712)
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
-- TOC entry 325 (class 1255 OID 39717)
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
-- TOC entry 329 (class 1255 OID 39721)
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
-- TOC entry 335 (class 1255 OID 39728)
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
-- TOC entry 377 (class 1255 OID 39770)
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
-- TOC entry 341 (class 1255 OID 39733)
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
-- TOC entry 345 (class 1255 OID 39737)
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
-- TOC entry 348 (class 1255 OID 39740)
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
-- TOC entry 354 (class 1255 OID 39745)
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
-- TOC entry 287 (class 1255 OID 39692)
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
-- TOC entry 379 (class 1255 OID 39772)
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
-- TOC entry 288 (class 1255 OID 39693)
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
-- TOC entry 358 (class 1255 OID 39749)
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
-- TOC entry 359 (class 1255 OID 39750)
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
-- TOC entry 360 (class 1255 OID 39751)
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
-- TOC entry 361 (class 1255 OID 39752)
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
-- TOC entry 362 (class 1255 OID 39753)
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
-- TOC entry 363 (class 1255 OID 39754)
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
-- TOC entry 364 (class 1255 OID 39755)
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
-- TOC entry 365 (class 1255 OID 39756)
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
-- TOC entry 366 (class 1255 OID 39757)
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
-- TOC entry 367 (class 1255 OID 39758)
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
-- TOC entry 368 (class 1255 OID 39759)
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
-- TOC entry 369 (class 1255 OID 39760)
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
-- TOC entry 370 (class 1255 OID 39761)
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
-- TOC entry 371 (class 1255 OID 39762)
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
-- TOC entry 372 (class 1255 OID 39763)
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
-- TOC entry 373 (class 1255 OID 39764)
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
-- TOC entry 374 (class 1255 OID 39765)
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
-- TOC entry 375 (class 1255 OID 39766)
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
-- TOC entry 339 (class 1255 OID 39767)
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
-- TOC entry 352 (class 1255 OID 39768)
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
-- TOC entry 291 (class 1255 OID 39696)
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
-- TOC entry 307 (class 1255 OID 39700)
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
-- TOC entry 312 (class 1255 OID 39705)
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
-- TOC entry 316 (class 1255 OID 39709)
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
-- TOC entry 319 (class 1255 OID 39711)
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
-- TOC entry 324 (class 1255 OID 39716)
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
-- TOC entry 328 (class 1255 OID 39720)
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
-- TOC entry 331 (class 1255 OID 39724)
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
-- TOC entry 334 (class 1255 OID 39727)
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
-- TOC entry 340 (class 1255 OID 39732)
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
-- TOC entry 344 (class 1255 OID 39736)
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
-- TOC entry 347 (class 1255 OID 39739)
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
-- TOC entry 350 (class 1255 OID 39742)
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
-- TOC entry 353 (class 1255 OID 39744)
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
-- TOC entry 378 (class 1255 OID 39771)
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
-- TOC entry 305 (class 1255 OID 39698)
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
-- TOC entry 309 (class 1255 OID 39702)
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
-- TOC entry 314 (class 1255 OID 39707)
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
-- TOC entry 321 (class 1255 OID 39713)
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
-- TOC entry 326 (class 1255 OID 39718)
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
-- TOC entry 317 (class 1255 OID 39722)
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
-- TOC entry 336 (class 1255 OID 39729)
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
-- TOC entry 342 (class 1255 OID 39734)
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
-- TOC entry 349 (class 1255 OID 39741)
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
-- TOC entry 355 (class 1255 OID 39746)
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
-- TOC entry 310 (class 1255 OID 39703)
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
-- TOC entry 332 (class 1255 OID 39725)
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
-- TOC entry 357 (class 1255 OID 39748)
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
-- TOC entry 197 (class 1259 OID 39134)
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
-- TOC entry 196 (class 1259 OID 39132)
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
-- TOC entry 199 (class 1259 OID 39145)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 39143)
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
-- TOC entry 259 (class 1259 OID 39675)
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
-- TOC entry 258 (class 1259 OID 39673)
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
-- TOC entry 201 (class 1259 OID 39156)
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
-- TOC entry 200 (class 1259 OID 39154)
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
-- TOC entry 231 (class 1259 OID 39404)
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
-- TOC entry 230 (class 1259 OID 39402)
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
-- TOC entry 218 (class 1259 OID 39265)
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
-- TOC entry 217 (class 1259 OID 39263)
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
-- TOC entry 202 (class 1259 OID 39165)
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
-- TOC entry 219 (class 1259 OID 39277)
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
-- TOC entry 225 (class 1259 OID 39336)
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
-- TOC entry 224 (class 1259 OID 39334)
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
-- TOC entry 241 (class 1259 OID 39503)
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
-- TOC entry 240 (class 1259 OID 39501)
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
-- TOC entry 221 (class 1259 OID 39300)
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
-- TOC entry 220 (class 1259 OID 39298)
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
-- TOC entry 204 (class 1259 OID 39174)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pago (
    id integer NOT NULL,
    forma_pago character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 39172)
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
-- TOC entry 206 (class 1259 OID 39188)
-- Name: funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcion (
    id integer NOT NULL,
    funcion character varying NOT NULL
);


ALTER TABLE public.funcion OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 39186)
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
-- TOC entry 223 (class 1259 OID 39312)
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
-- TOC entry 222 (class 1259 OID 39310)
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
-- TOC entry 208 (class 1259 OID 39201)
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
-- TOC entry 207 (class 1259 OID 39199)
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
-- TOC entry 237 (class 1259 OID 39467)
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
-- TOC entry 236 (class 1259 OID 39465)
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
-- TOC entry 209 (class 1259 OID 39216)
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
-- TOC entry 226 (class 1259 OID 39362)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
    ci_persona character varying(11) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 39224)
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
-- TOC entry 243 (class 1259 OID 39524)
-- Name: puente_asambleas_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asambleas_propietario (
    id integer NOT NULL,
    id_asamblea integer NOT NULL,
    ci_propietario character varying(11) NOT NULL
);


ALTER TABLE public.puente_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 39522)
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
-- TOC entry 245 (class 1259 OID 39542)
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
-- TOC entry 244 (class 1259 OID 39540)
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
-- TOC entry 247 (class 1259 OID 39563)
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
-- TOC entry 246 (class 1259 OID 39561)
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
-- TOC entry 249 (class 1259 OID 39581)
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
-- TOC entry 248 (class 1259 OID 39579)
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
-- TOC entry 251 (class 1259 OID 39601)
-- Name: puente_persona_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_persona_condominio (
    id integer NOT NULL,
    ci_persona character varying(11) NOT NULL,
    rif_condominio character varying(15) NOT NULL
);


ALTER TABLE public.puente_persona_condominio OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 39599)
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
-- TOC entry 253 (class 1259 OID 39619)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id integer NOT NULL,
    id_sancion integer NOT NULL,
    id_unidad bigint NOT NULL
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 39617)
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
-- TOC entry 257 (class 1259 OID 39657)
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
-- TOC entry 256 (class 1259 OID 39655)
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
-- TOC entry 255 (class 1259 OID 39637)
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
-- TOC entry 254 (class 1259 OID 39635)
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
-- TOC entry 233 (class 1259 OID 39430)
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
-- TOC entry 232 (class 1259 OID 39428)
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
-- TOC entry 227 (class 1259 OID 39373)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
    ci_persona character varying(11) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 39232)
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
-- TOC entry 211 (class 1259 OID 39230)
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
-- TOC entry 214 (class 1259 OID 39240)
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
-- TOC entry 213 (class 1259 OID 39238)
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
-- TOC entry 216 (class 1259 OID 39251)
-- Name: tipo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_usuario (
    id integer NOT NULL,
    tipo character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tipo_usuario OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 39249)
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
-- TOC entry 229 (class 1259 OID 39386)
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
-- TOC entry 228 (class 1259 OID 39384)
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
-- TOC entry 235 (class 1259 OID 39446)
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
-- TOC entry 234 (class 1259 OID 39444)
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
-- TOC entry 279 (class 1259 OID 39901)
-- Name: v_area_total; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_area_total AS
 SELECT sum(tu.area) AS area
   FROM (public.tipo_unidad tu
     JOIN public.unidad u ON ((u.id_tipo = tu.id)))
  WHERE (u.activo = true);


ALTER TABLE public.v_area_total OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 39819)
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
-- TOC entry 260 (class 1259 OID 39815)
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
-- TOC entry 262 (class 1259 OID 39823)
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
-- TOC entry 272 (class 1259 OID 39869)
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
-- TOC entry 271 (class 1259 OID 39864)
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
-- TOC entry 263 (class 1259 OID 39827)
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
-- TOC entry 264 (class 1259 OID 39832)
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
-- TOC entry 265 (class 1259 OID 39836)
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
-- TOC entry 266 (class 1259 OID 39840)
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
-- TOC entry 267 (class 1259 OID 39845)
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
-- TOC entry 268 (class 1259 OID 39850)
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
-- TOC entry 269 (class 1259 OID 39854)
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
-- TOC entry 270 (class 1259 OID 39859)
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
-- TOC entry 273 (class 1259 OID 39874)
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
-- TOC entry 274 (class 1259 OID 39879)
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
-- TOC entry 275 (class 1259 OID 39884)
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
-- TOC entry 276 (class 1259 OID 39889)
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
-- TOC entry 277 (class 1259 OID 39893)
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
-- TOC entry 278 (class 1259 OID 39897)
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
-- TOC entry 280 (class 1259 OID 39905)
-- Name: v_tipo_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_usuario AS
 SELECT tu.id,
    tu.tipo
   FROM public.tipo_usuario tu
  WHERE (tu.activo = true);


ALTER TABLE public.v_tipo_usuario OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 39909)
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
-- TOC entry 282 (class 1259 OID 39914)
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
-- TOC entry 283 (class 1259 OID 39919)
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
-- TOC entry 239 (class 1259 OID 39483)
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
-- TOC entry 284 (class 1259 OID 39923)
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
-- TOC entry 238 (class 1259 OID 39481)
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
-- TOC entry 3057 (class 2604 OID 39137)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 3058 (class 2604 OID 39148)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 3116 (class 2604 OID 39678)
-- Name: bitacora id_bitacora; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora ALTER COLUMN id_bitacora SET DEFAULT nextval('public.bitacora_id_bitacora_seq'::regclass);


--
-- TOC entry 3060 (class 2604 OID 39159)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 3092 (class 2604 OID 39407)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_unidad_id_seq'::regclass);


--
-- TOC entry 3078 (class 2604 OID 39268)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 3087 (class 2604 OID 39339)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 3103 (class 2604 OID 39506)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 3081 (class 2604 OID 39303)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 3064 (class 2604 OID 39177)
-- Name: forma_pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago ALTER COLUMN id SET DEFAULT nextval('public.forma_pago_id_seq'::regclass);


--
-- TOC entry 3066 (class 2604 OID 39191)
-- Name: funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion ALTER COLUMN id SET DEFAULT nextval('public.funcion_id_seq'::regclass);


--
-- TOC entry 3083 (class 2604 OID 39315)
-- Name: gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto ALTER COLUMN id SET DEFAULT nextval('public.gasto_id_seq'::regclass);


--
-- TOC entry 3067 (class 2604 OID 39204)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 3096 (class 2604 OID 39470)
-- Name: mensaje id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje ALTER COLUMN id SET DEFAULT nextval('public.mensaje_id_seq'::regclass);


--
-- TOC entry 3104 (class 2604 OID 39527)
-- Name: puente_asambleas_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asambleas_propietario_id_seq'::regclass);


--
-- TOC entry 3105 (class 2604 OID 39545)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 3106 (class 2604 OID 39566)
-- Name: puente_gasto_concepto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto ALTER COLUMN id SET DEFAULT nextval('public.puente_gasto_concepto_id_seq'::regclass);


--
-- TOC entry 3107 (class 2604 OID 39584)
-- Name: puente_mensaje_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_mensaje_usuario_id_seq'::regclass);


--
-- TOC entry 3110 (class 2604 OID 39604)
-- Name: puente_persona_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_persona_condominio_id_seq'::regclass);


--
-- TOC entry 3111 (class 2604 OID 39622)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 3115 (class 2604 OID 39660)
-- Name: puente_tipo_funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion ALTER COLUMN id SET DEFAULT nextval('public.puente_tipo_funcion_id_seq'::regclass);


--
-- TOC entry 3112 (class 2604 OID 39640)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 3093 (class 2604 OID 39433)
-- Name: recibo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo ALTER COLUMN id SET DEFAULT nextval('public.recibo_id_seq'::regclass);


--
-- TOC entry 3073 (class 2604 OID 39235)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3074 (class 2604 OID 39243)
-- Name: tipo_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad ALTER COLUMN id SET DEFAULT nextval('public.tipo_unidad_id_seq'::regclass);


--
-- TOC entry 3076 (class 2604 OID 39254)
-- Name: tipo_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario ALTER COLUMN id SET DEFAULT nextval('public.tipo_usuario_id_seq'::regclass);


--
-- TOC entry 3090 (class 2604 OID 39389)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 3094 (class 2604 OID 39449)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3100 (class 2604 OID 39486)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3437 (class 0 OID 39134)
-- Dependencies: 197
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (1, 'PORTÓN', 'REPARACIÓN DE PORTÓN', '2020-06-16');
INSERT INTO public.asambleas VALUES (2, 'PISCINA', 'MANTENIMIENTO DE PISCINA', '2020-06-09');
INSERT INTO public.asambleas VALUES (3, 'PARQUE INFANTIL', 'MANTENIMIENTO DEL PARQUE INFANTIL', '2020-06-11');
INSERT INTO public.asambleas VALUES (4, 'AREAS VERDES', 'MANTENIMIENTO DE LAS AREAS VERDES', '2020-06-14');
INSERT INTO public.asambleas VALUES (5, 'ESTACIONAMIENTO', 'MANTENIMIENTO DEL ESTACIONAMIENTO', '2020-06-17');
INSERT INTO public.asambleas VALUES (6, 'CERCADO ELÉCTRICO', 'MANTENIMIENTO DEL CERCADO ELÉCTRICO', '2020-06-21');
INSERT INTO public.asambleas VALUES (7, 'FACHADA', 'REUNION SOBRE LA FACHADA DEL CONDOMINIO', '2020-06-23');
INSERT INTO public.asambleas VALUES (8, 'ALUMBRADO', 'REUNION SOBRE EL ALUMBRADO DEL CONDOMINIO', '2020-06-25');
INSERT INTO public.asambleas VALUES (9, 'VIGILANTE', 'REUNIÓN PARA CAMBIAR AL VIGILANTE', '2020-06-28');
INSERT INTO public.asambleas VALUES (10, 'CISTERNA', 'REUNION PARA PEDIR UNA CISTERNA DEBIDO A LA FALTA DE AGUA', '2020-06-30');


--
-- TOC entry 3439 (class 0 OID 39145)
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
INSERT INTO public.banco VALUES (8, 'BANCO VENEZOLANO DE CREDITO', true);
INSERT INTO public.banco VALUES (9, 'BANCO CARONÍ', true);
INSERT INTO public.banco VALUES (10, 'BANCO DEL TESORO', true);


--
-- TOC entry 3499 (class 0 OID 39675)
-- Dependencies: 259
-- Data for Name: bitacora; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bitacora VALUES (1, 'Registro', 'banco', NULL, NULL, '(1,"Banco de Venezuela",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (2, 'Registro', 'banco', NULL, NULL, '(2,"Banco del Caribe",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (3, 'Registro', 'banco', NULL, NULL, '(3,"Banco Provincial",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (4, 'Registro', 'banco', NULL, NULL, '(4,"Banco Bicentenario",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (5, 'Registro', 'banco', NULL, NULL, '(5,Banesco,t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (6, 'Registro', 'banco', NULL, NULL, '(6,"Banco Exterior",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (7, 'Registro', 'banco', NULL, NULL, '(7,"Banco BOD",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (8, 'Registro', 'forma_pago', NULL, NULL, '(1,"Pago móvil",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (9, 'Registro', 'forma_pago', NULL, NULL, '(2,Transferencia,t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (10, 'Registro', 'forma_pago', NULL, NULL, '(3,Depósito,t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (11, 'Registro', 'forma_pago', NULL, NULL, '(4,Efectivo,t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (12, 'Registro', 'forma_pago', NULL, NULL, '(5,Cheque,t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (13, 'Registro', 'forma_pago', NULL, NULL, '(6,"Punto de venta",t)', '2020-07-28 10:08:02');
INSERT INTO public.bitacora VALUES (14, 'Registro', 'condominio', 1, NULL, '(123456789,"Urb. el jardín",02540123402,Ur1eljardin@hotmail.com,t)', '2020-07-28 10:24:09');
INSERT INTO public.bitacora VALUES (15, 'Registro', 'categoriagasto', 1, NULL, '(1,"Uso común","Conjunto de conceptos de uso comun y consumo",t)', '2020-07-28 10:26:29');
INSERT INTO public.bitacora VALUES (16, 'Registro', 'categoriagasto', 1, NULL, '(2,Administración,"gatos administrativos",t)', '2020-07-28 10:30:18');
INSERT INTO public.bitacora VALUES (17, 'Registro', 'categoriagasto', 1, NULL, '(3,mantenimiento,"categoría de mantenimiento",t)', '2020-07-28 10:30:55');
INSERT INTO public.bitacora VALUES (18, 'Registro', 'categoriagasto', 1, NULL, '(4,reparación,"categoría de reparación",t)', '2020-07-28 10:31:17');
INSERT INTO public.bitacora VALUES (19, 'Registro', 'categoriagasto', 1, NULL, '(5,"De uso o consumo","categoría De uso o consumo",t)', '2020-07-28 10:31:57');
INSERT INTO public.bitacora VALUES (20, 'Modificado', 'categoriagasto', 1, '(5,"DE USO O CONSUMO","CATEGORÍA DE USO O CONSUMO",t)', '(5,"gastos varios","CATEGORÍA DE gastos varios",t)', '2020-07-28 10:36:33');
INSERT INTO public.bitacora VALUES (21, 'Registro', 'categoriagasto', 1, NULL, '(6,pagos,"categoría pagos",t)', '2020-07-28 10:39:23');
INSERT INTO public.bitacora VALUES (22, 'Eliminado', 'categoriagasto', 1, '(6,PAGOS,"CATEGORÍA PAGOS",t)', NULL, '2020-07-28 10:40:44');
INSERT INTO public.bitacora VALUES (23, 'Registro', 'categoriagasto', 1, NULL, '(7,transporte,"categoría de transporte",t)', '2020-07-28 10:47:08');
INSERT INTO public.bitacora VALUES (24, 'Registro', 'categoriagasto', 1, NULL, '(8,seguros,"categoría de seguros",t)', '2020-07-28 10:47:26');
INSERT INTO public.bitacora VALUES (25, 'Modificado', 'categoriagasto', 1, '(2,ADMINISTRACIÓN,"GATOS ADMINISTRATIVOS",t)', '(2,ADMINISTRACIÓN,"GAsTOS ADMINISTRATIVOS",t)', '2020-07-28 10:47:54');
INSERT INTO public.bitacora VALUES (26, 'Registro', 'categoriagasto', 1, NULL, '(9,nomina,"categoría de nomina",t)', '2020-07-28 10:54:11');
INSERT INTO public.bitacora VALUES (27, 'Registro', 'categoriagasto', 1, NULL, '(10,servicios,"categoría de servicios",t)', '2020-07-28 10:56:44');
INSERT INTO public.bitacora VALUES (28, 'Registro', 'concepto_gasto', 1, NULL, '(1,porton,"mantenimiento de porton",3,t)', '2020-07-28 10:57:13');
INSERT INTO public.bitacora VALUES (29, 'Registro', 'concepto_gasto', 1, NULL, '(2,ascensor,"mantenimiento de ascensor",3,t)', '2020-07-28 10:57:45');
INSERT INTO public.bitacora VALUES (30, 'Registro', 'concepto_gasto', 1, NULL, '(3,tuberias,"mantenimiento de tuberias",3,t)', '2020-07-28 10:57:59');
INSERT INTO public.bitacora VALUES (31, 'Registro', 'concepto_gasto', 1, NULL, '(4,piscina,"mantenimiento de piscina",3,t)', '2020-07-28 10:58:21');
INSERT INTO public.bitacora VALUES (32, 'Registro', 'concepto_gasto', 1, NULL, '(5,"parque infantil","mantenimiento de parque infantil",3,t)', '2020-07-28 10:58:42');
INSERT INTO public.bitacora VALUES (33, 'Registro', 'concepto_gasto', 1, NULL, '(6,"zonas verdes","mantenimiento zonas verdes",3,t)', '2020-07-28 10:59:06');
INSERT INTO public.bitacora VALUES (34, 'Registro', 'concepto_gasto', 1, NULL, '(7,estacionamiento,"mantenimiento de estacionamiento",3,t)', '2020-07-28 10:59:52');
INSERT INTO public.bitacora VALUES (35, 'Registro', 'concepto_gasto', 1, NULL, '(8,camaras,"mantenimiento de camaras",3,t)', '2020-07-28 11:00:10');
INSERT INTO public.bitacora VALUES (36, 'Modificado', 'concepto_gasto', 1, '(8,CAMARAS,"MANTENIMIENTO DE CAMARAS",3,t)', '(8,CAMARAS,"MANTENIMIENTO DE CAMARAS de vigilancia",3,t)', '2020-07-28 11:00:19');
INSERT INTO public.bitacora VALUES (37, 'Registro', 'concepto_gasto', 1, NULL, '(9,fachada,"mantenimiento de la fachada del condominio",3,t)', '2020-07-28 11:00:48');
INSERT INTO public.bitacora VALUES (38, 'Registro', 'concepto_gasto', 1, NULL, '(10,alumbrado,"matenimiento del alumbrado del condominio",3,t)', '2020-07-28 11:04:12');
INSERT INTO public.bitacora VALUES (39, 'Eliminado', 'categoriagasto', 1, '(1,"USO COMÚN","CONJUNTO DE CONCEPTOS DE USO COMUN Y CONSUMO",t)', NULL, '2020-07-28 11:05:23');
INSERT INTO public.bitacora VALUES (40, 'Registro', 'concepto_gasto', 1, NULL, '(11,ascensor,"reparacion de ascensor",4,t)', '2020-07-28 11:05:54');
INSERT INTO public.bitacora VALUES (41, 'Registro', 'concepto_gasto', 1, NULL, '(12,alumbrado,"reparación de alumbrado",4,t)', '2020-07-28 11:06:22');
INSERT INTO public.bitacora VALUES (42, 'Modificado', 'concepto_gasto', 1, '(10,ALUMBRADO,"MATENIMIENTO DEL ALUMBRADO DEL CONDOMINIO",3,t)', '(10," mantenimiento de ALUMBRADO","MATENIMIENTO DEL ALUMBRADO DEL CONDOMINIO",3,t)', '2020-07-28 11:07:39');
INSERT INTO public.bitacora VALUES (43, 'Modificado', 'concepto_gasto', 1, '(9,FACHADA,"MANTENIMIENTO DE LA FACHADA DEL CONDOMINIO",3,t)', '(9,"mantenimiento de FACHADA","MANTENIMIENTO DE LA FACHADA DEL CONDOMINIO",3,t)', '2020-07-28 11:07:53');
INSERT INTO public.bitacora VALUES (44, 'Modificado', 'concepto_gasto', 1, '(8,CAMARAS,"MANTENIMIENTO DE CAMARAS DE VIGILANCIA",3,t)', '(8,"mantenimiento de CAMARAS","MANTENIMIENTO DE CAMARAS DE VIGILANCIA",3,t)', '2020-07-28 11:08:02');
INSERT INTO public.bitacora VALUES (45, 'Modificado', 'concepto_gasto', 1, '(7,ESTACIONAMIENTO,"MANTENIMIENTO DE ESTACIONAMIENTO",3,t)', '(7,"mangtenimiento de ESTACIONAMIENTO","MANTENIMIENTO DE ESTACIONAMIENTO",3,t)', '2020-07-28 11:08:10');
INSERT INTO public.bitacora VALUES (46, 'Modificado', 'concepto_gasto', 1, '(6,"ZONAS VERDES","MANTENIMIENTO ZONAS VERDES",3,t)', '(6,"manmtenimiento de ZONAS VERDES","MANTENIMIENTO ZONAS VERDES",3,t)', '2020-07-28 11:08:21');
INSERT INTO public.bitacora VALUES (47, 'Modificado', 'concepto_gasto', 1, '(5,"PARQUE INFANTIL","MANTENIMIENTO DE PARQUE INFANTIL",3,t)', '(5,"mantenimiento de PARQUE INFANTIL","MANTENIMIENTO DE PARQUE INFANTIL",3,t)', '2020-07-28 11:08:30');
INSERT INTO public.bitacora VALUES (48, 'Modificado', 'concepto_gasto', 1, '(4,PISCINA,"MANTENIMIENTO DE PISCINA",3,t)', '(4,"mantenimiento de PISCINA","MANTENIMIENTO DE PISCINA",3,t)', '2020-07-28 11:08:39');
INSERT INTO public.bitacora VALUES (49, 'Modificado', 'concepto_gasto', 1, '(3,TUBERIAS,"MANTENIMIENTO DE TUBERIAS",3,t)', '(3,"mantenimiento de TUBERIAS","MANTENIMIENTO DE TUBERIAS",3,t)', '2020-07-28 11:08:48');
INSERT INTO public.bitacora VALUES (50, 'Modificado', 'concepto_gasto', 1, '(2,ASCENSOR,"MANTENIMIENTO DE ASCENSOR",3,t)', '(2,"mantenimiento de ASCENSOR","MANTENIMIENTO DE ASCENSOR",3,t)', '2020-07-28 11:09:04');
INSERT INTO public.bitacora VALUES (51, 'Modificado', 'concepto_gasto', 1, '(1,PORTON,"MANTENIMIENTO DE PORTON",3,t)', '(1,"mantenimiento de PORTON","MANTENIMIENTO DE PORTON",3,t)', '2020-07-28 11:09:13');
INSERT INTO public.bitacora VALUES (52, 'Modificado', 'concepto_gasto', 1, '(12,ALUMBRADO,"REPARACIÓN DE ALUMBRADO",4,t)', '(12,"reparación de ALUMBRADO","REPARACIÓN DE ALUMBRADO",4,t)', '2020-07-28 11:09:24');
INSERT INTO public.bitacora VALUES (53, 'Modificado', 'concepto_gasto', 1, '(11,ASCENSOR,"REPARACION DE ASCENSOR",4,t)', '(11,"reparación de ASCENSOR","REPARACION DE ASCENSOR",4,t)', '2020-07-28 11:09:38');
INSERT INTO public.bitacora VALUES (54, 'Registro', 'concepto_gasto', 1, NULL, '(13,"reparación de porton","reparación de portón",4,t)', '2020-07-28 11:10:08');
INSERT INTO public.bitacora VALUES (55, 'Modificado', 'concepto_gasto', 1, '(1,"MANTENIMIENTO DE PORTON","MANTENIMIENTO DE PORTON",3,t)', '(1,"MANTENIMIENTO DE PORTóN","MANTENIMIENTO DE PORTóN",3,t)', '2020-07-28 11:10:24');
INSERT INTO public.bitacora VALUES (56, 'Modificado', 'concepto_gasto', 1, '(13,"REPARACIÓN DE PORTON","REPARACIÓN DE PORTÓN",4,t)', '(13,"REPARACIÓN DE PORTóN","REPARACIÓN DE PORTÓN",4,t)', '2020-07-28 11:10:45');
INSERT INTO public.bitacora VALUES (57, 'Registro', 'concepto_gasto', 1, NULL, '(14,"reparación de tuberias","reparación de tuberias",4,t)', '2020-07-28 11:12:04');
INSERT INTO public.bitacora VALUES (58, 'Registro', 'concepto_gasto', 1, NULL, '(15,"reparación de cercado eléctrico","reparación de cercado eléctrico",4,t)', '2020-07-28 11:13:04');
INSERT INTO public.bitacora VALUES (59, 'Registro', 'concepto_gasto', 1, NULL, '(16,"reparación de cámaras de vigilancia","reparación de cámaras",4,t)', '2020-07-28 11:14:01');
INSERT INTO public.bitacora VALUES (60, 'Modificado', 'concepto_gasto', 1, '(8,"MANTENIMIENTO DE CAMARAS","MANTENIMIENTO DE CAMARAS DE VIGILANCIA",3,t)', '(8,"MANTENIMIENTO DE CáMARAS","MANTENIMIENTO DE CáMARAS DE VIGILANCIA",3,t)', '2020-07-28 11:14:15');
INSERT INTO public.bitacora VALUES (61, 'Registro', 'concepto_gasto', 1, NULL, '(17,"reparación eléctrica","Reparacion/Sustitucion de componentes eléctricos del condominio",4,t)', '2020-07-28 11:15:27');
INSERT INTO public.bitacora VALUES (62, 'Registro', 'concepto_gasto', 1, NULL, '(18,"reparación de hidroneumático","Reparacion la bomba de agua",4,t)', '2020-07-28 11:17:27');
INSERT INTO public.bitacora VALUES (63, 'Registro', 'concepto_gasto', 1, NULL, '(19,"reparación de limpiafondos","Reparacion/Sustitucion de limpiafondos para la piscina",4,t)', '2020-07-28 11:22:37');
INSERT INTO public.bitacora VALUES (64, 'Registro', 'concepto_gasto', 1, NULL, '(20,"reparación de Recogehojas","reparación/sustitución de Recogehojas",4,t)', '2020-07-28 11:23:26');
INSERT INTO public.bitacora VALUES (65, 'Modificado', 'concepto_gasto', 1, '(20,"REPARACIÓN DE RECOGEHOJAS","REPARACIÓN/SUSTITUCIÓN DE RECOGEHOJAS",4,t)', '(20,"REPARACIÓN DE RECOGEHOJAS","REPARACIÓN/SUSTITUCIÓN DE RECOGEHOJAS para la piscina",4,t)', '2020-07-28 11:23:41');
INSERT INTO public.bitacora VALUES (66, 'Registro', 'forma_pago', 1, NULL, '(7,petros,t)', '2020-07-28 11:24:37');
INSERT INTO public.bitacora VALUES (67, 'Registro', 'forma_pago', 1, NULL, '(8,vale,t)', '2020-07-28 11:24:51');
INSERT INTO public.bitacora VALUES (68, 'Registro', 'forma_pago', 1, NULL, '(9,pagare,t)', '2020-07-28 11:25:10');
INSERT INTO public.bitacora VALUES (69, 'Registro', 'forma_pago', 1, NULL, '(10,paypal,t)', '2020-07-28 11:26:32');
INSERT INTO public.bitacora VALUES (70, 'Registro', 'tipo_unidad', 1, NULL, '(1,40,ORQUÍDEA,t)', '2020-07-28 11:30:50');
INSERT INTO public.bitacora VALUES (71, 'Registro', 'tipo_unidad', 1, NULL, '(2,45,ROSA,t)', '2020-07-28 11:31:02');
INSERT INTO public.bitacora VALUES (72, 'Registro', 'tipo_unidad', 1, NULL, '(3,50,PETUNIA,t)', '2020-07-28 11:31:30');
INSERT INTO public.bitacora VALUES (73, 'Registro', 'tipo_unidad', 1, NULL, '(4,55,GIRASOL,t)', '2020-07-28 11:31:41');
INSERT INTO public.bitacora VALUES (74, 'Registro', 'tipo_unidad', 1, NULL, '(5,60,LAVANDA,t)', '2020-07-28 11:31:55');
INSERT INTO public.bitacora VALUES (75, 'Registro', 'tipo_unidad', 1, NULL, '(6,65,TULIPAN,t)', '2020-07-28 11:32:05');
INSERT INTO public.bitacora VALUES (76, 'Registro', 'tipo_unidad', 1, NULL, '(7,70,HORTENSIAS,t)', '2020-07-28 11:32:38');
INSERT INTO public.bitacora VALUES (77, 'Registro', 'tipo_unidad', 1, NULL, '(8,75,JAZMÍN,t)', '2020-07-28 11:33:08');
INSERT INTO public.bitacora VALUES (78, 'Registro', 'tipo_unidad', 1, NULL, '(9,80,AMAPOLA,t)', '2020-07-28 11:33:55');
INSERT INTO public.bitacora VALUES (79, 'Registro', 'tipo_unidad', 1, NULL, '(10,85,LIRIO,t)', '2020-07-28 11:34:18');
INSERT INTO public.bitacora VALUES (80, 'Registro', 'propietario', 1, NULL, '(V-8517596,BLANCA,ROSA,SINGER,MUJICA,04127616516,BRSM@HOTMAIL.COM,t)', '2020-07-28 11:35:14');
INSERT INTO public.bitacora VALUES (81, 'Registro', 'propietario', 1, NULL, '(V-1434801,BLANCA,CELESTINA,MUJICA,SILVA,04245789625,BCMS@HOTMAIL.COM,t)', '2020-07-28 11:36:05');
INSERT INTO public.bitacora VALUES (82, 'Registro', 'propietario', 1, NULL, '(V-20888725,MARIA,DELOSANGELES,OSORIO,SINGER,04127909117,MDLAOS@HOTMAIL.COM,t)', '2020-07-28 11:39:16');
INSERT INTO public.bitacora VALUES (83, 'Registro', 'propietario', 1, NULL, '(V-20458966,MARIEL,MERCEDES,SALAZAR,RODRIGUEZ,04245789652,MMSR@,t)', '2020-07-28 11:40:46');
INSERT INTO public.bitacora VALUES (84, 'Registro', 'propietario', 1, NULL, '(V-27328852,MARYORITH,NAZARETH,SINGER,MUJICA,04125084544,MNSM@HOTMAIL.COM,t)', '2020-07-28 11:41:52');
INSERT INTO public.bitacora VALUES (85, 'Registro', 'propietario', 1, NULL, '(V-27699315,MARIA,MERCEDES,ALVAREZ,BARRIOS,04265465100,MMAB@HOTMAIL.COM,t)', '2020-07-28 11:42:28');
INSERT INTO public.bitacora VALUES (86, 'Registro', 'propietario', 1, NULL, '(V-6412943,JULIO,ALEJANDRO,PEREZ,ALVAREZ,04245781200,JAPA@HOTMAIL.COM,t)', '2020-07-28 11:43:42');
INSERT INTO public.bitacora VALUES (87, 'Registro', 'propietario', 1, NULL, '(V-26943430,SAMUEL,ALEJANDRO,PEREZ,MORA,04245222312,SAPM@GMAIL.COM,t)', '2020-07-28 11:44:46');
INSERT INTO public.bitacora VALUES (88, 'Registro', 'propietario', 1, NULL, '(V-27458101,ABRAHAM,ALEJANDRO,GIL,PEREZ,04241578966,AAGP@GMAIL.COM,t)', '2020-07-28 11:47:34');
INSERT INTO public.bitacora VALUES (89, 'Registro', 'propietario', 1, NULL, '(V-27145012,ROBERT,JOSUE,BLANCO,CAMARACO,04264015651,RJBC@GMAIL.COM,t)', '2020-07-28 11:48:19');
INSERT INTO public.bitacora VALUES (90, 'Registro', 'proveedores', 1, NULL, '(J-12345678,"LIBRERIA LA ROSA",025400124578,LR@HOTMAIL.COM,ANNA,"5TA AVENIDA ENTRE CALLE 18 Y 19",t)', '2020-07-28 11:53:03');
INSERT INTO public.bitacora VALUES (91, 'Registro', 'proveedores', 1, NULL, '(J-23456789,"PORTONES LOS HERMANOS",025478141032,PH@HOTMAIL.COM,LUIS,"6TA AVENIDA CON ESQUINA CALLE 12",t)', '2020-07-28 11:54:13');
INSERT INTO public.bitacora VALUES (92, 'Registro', 'proveedores', 1, NULL, '(V-16216543,"REPARADOR DEL ASCENSOR",042411210038,REAS@HOTMAIL.COM,JUAN,-,t)', '2020-07-28 11:56:17');
INSERT INTO public.bitacora VALUES (93, 'Registro', 'proveedores', 1, NULL, '(J-34567890,"FERRETERIA TODO",025448741000,FERRETODO@GMAIL.COM,LUCAS,"8VA AVENIDA ENTRE CALLE 8 Y 7",t)', '2020-07-28 11:57:33');
INSERT INTO public.bitacora VALUES (94, 'Registro', 'proveedores', 1, NULL, '(V-13843549,"SEÑORA DE LA LIMPIEZA",042647400441,I3SRIS@HOTMAILC.COM,IRIS,-,t)', '2020-07-28 11:58:34');
INSERT INTO public.bitacora VALUES (95, 'Registro', 'proveedores', 1, NULL, '(J-01248325,"PISCINAS TODO YARACUY",025478910065,PISCINAS@HOTMAIL.COM,FELIX,"6TA AVENIDA ENTRE CALLES 12 Y 13",t)', '2020-07-28 12:00:22');
INSERT INTO public.bitacora VALUES (96, 'Registro', 'proveedores', 1, NULL, '(J-01214000,FERREGAITA,025410230147,FERREGAITAS12@GMAIL.COM,JOSE,"2 AVENIDA ENTRE CALLES 16 Y 17",t)', '2020-07-28 12:01:44');
INSERT INTO public.bitacora VALUES (97, 'Registro', 'proveedores', 1, NULL, '(V-19484654,VIGILANTE,041247825100,HUGO12@GMAIL.COM,HUGO,-,t)', '2020-07-28 12:04:36');
INSERT INTO public.bitacora VALUES (98, 'Registro', 'proveedores', 1, NULL, '(J-01654632,"INVERSIONES RAVICA",025478120456,INVERSIONESRAVICA12@HOTMAIL.COM,CARLOS,"CALLE 18, ESQUINA DE LA 7MA AVENIDA",t)', '2020-07-28 12:06:21');
INSERT INTO public.bitacora VALUES (99, 'Registro', 'proveedores', 1, NULL, '(V-19444578,"REPARADOR DEL HIDRONEUMÁTICO",041248720100,REPARACIONHIDRO@HOTMAIL.COM,JAVIER,-,t)', '2020-07-28 12:08:02');
INSERT INTO public.bitacora VALUES (100, 'Registro', 'banco', 1, NULL, '(8,"banco venezolano de credito",t)', '2020-07-28 12:09:10');
INSERT INTO public.bitacora VALUES (101, 'Registro', 'banco', 1, NULL, '(9,"banco caroní",t)', '2020-07-28 12:09:21');
INSERT INTO public.bitacora VALUES (102, 'Registro', 'banco', 1, NULL, '(10,"banco del tesoro",t)', '2020-07-28 12:10:19');
INSERT INTO public.bitacora VALUES (103, 'Registro', 'fondos', 1, NULL, '(reserva,2020-06-01,"este fondo tiene como proposito mantener el capital de reservas del condominio en dólares",-,1800,1800,1,t,DÓLAR)', '2020-07-28 12:12:14');
INSERT INTO public.bitacora VALUES (104, 'Registro', 'fondos', 1, NULL, '("reserva en bolívares",2020-06-01,"","",25000000,25000000,2,t,BOLÍVAR)', '2020-07-28 12:13:28');
INSERT INTO public.bitacora VALUES (105, 'modificado', 'fondos', 1, '(RESERVA,2020-06-01,"ESTE FONDO TIENE COMO PROPOSITO MANTENER EL CAPITAL DE RESERVAS DEL CONDOMINIO EN DÓLARES",-,1800,1800,1,t,DÓLAR)', '("RESERVA en dólares",2020-06-01,"ESTE FONDO TIENE COMO PROPOSITO MANTENER EL CAPITAL DE RESERVAS DEL CONDOMINIO EN DÓLARES",-,1800,1800,1,t,DÓLAR)', '2020-07-28 12:13:47');
INSERT INTO public.bitacora VALUES (106, 'modificado', 'fondos', 1, '("RESERVA EN BOLÍVARES",2020-06-01,"","",25000000,25000000,2,t,BOLÍVAR)', '("RESERVA EN BOLÍVARES",2020-06-01,"ESTE FONDO TIENE COMO PROPOSITO MANTENER EL CAPITAL DE RESERVAS DEL CONDOMINIO EN bolívares",-,25000000,25000000,2,t,BOLÍVAR)', '2020-07-28 12:14:00');
INSERT INTO public.bitacora VALUES (107, 'Registro', 'fondos', 1, NULL, '(operativo,2020-06-01,"este fondo tiene como finalidad reguardar el dinero que sera utilizado en cualquier gasto que necesite realizar el condominio",-,190000000,190000000,3,t,BOLÍVAR)', '2020-07-28 12:18:49');
INSERT INTO public.bitacora VALUES (108, 'Registro', 'fondos', 1, NULL, '("operativo en dólares",2020-06-01,"este fondo tiene como finalidad reguardar el dinero que sera utilizado en cualquier gasto que necesite realizar el condominio los cuales sean de moneda dólar",-,1000,1000,4,t,DÓLAR)', '2020-07-28 12:19:38');
INSERT INTO public.bitacora VALUES (144, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.28947368421052633,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.22,4)', '2020-07-28 12:36:36');
INSERT INTO public.bitacora VALUES (308, 'Registro', 'concepto_gasto', 1, NULL, '(32,Liquidación,"Liquidacion trabajador",2,t)', '2020-07-28 14:02:23');
INSERT INTO public.bitacora VALUES (109, 'modificado', 'fondos', 1, '(OPERATIVO,2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO",-,190000000,190000000,3,t,BOLÍVAR)', '("OPERATIVO en bolívares",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO los cuales sena de moneda bolívar",-,190000000,190000000,3,t,BOLÍVAR)', '2020-07-28 12:20:03');
INSERT INTO public.bitacora VALUES (110, 'modificado', 'fondos', 1, '("OPERATIVO EN DÓLARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SEAN DE MONEDA DÓLAR",-,1000,1000,4,t,DÓLAR)', '("OPERATIVO EN DÓLARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SEAN DE MONEDA DÓLAR",-,1000,1000,4,t,DÓLAR)', '2020-07-28 12:20:15');
INSERT INTO public.bitacora VALUES (111, 'modificado', 'fondos', 1, '("OPERATIVO EN BOLÍVARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SENA DE MONEDA BOLÍVAR",-,190000000,190000000,3,t,BOLÍVAR)', '("OPERATIVO EN BOLÍVARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SENA DE MONEDA BOLÍVAR",-,190000000,190000000,3,t,BOLÍVAR)', '2020-07-28 12:20:25');
INSERT INTO public.bitacora VALUES (112, 'Registro', 'fondos', 1, NULL, '(administrativo,2020-06-01,"este fondo tiene como finalidad resguardar el dinero que sera utilizado para pagar la nomina de los gastos administrativos",-,15000000,15000000,5,t,BOLÍVAR)', '2020-07-28 12:22:19');
INSERT INTO public.bitacora VALUES (113, 'modificado', 'fondos', 1, '("OPERATIVO EN BOLÍVARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SENA DE MONEDA BOLÍVAR",-,190000000,190000000,3,t,BOLÍVAR)', '("OPERATIVO EN BOLÍVARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REsGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SENA DE MONEDA BOLÍVAR",-,190000000,190000000,3,t,BOLÍVAR)', '2020-07-28 12:22:36');
INSERT INTO public.bitacora VALUES (114, 'modificado', 'fondos', 1, '("OPERATIVO EN DÓLARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SEAN DE MONEDA DÓLAR",-,1000,1000,4,t,DÓLAR)', '("OPERATIVO EN DÓLARES",2020-06-01,"ESTE FONDO TIENE COMO FINALIDAD REsGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SEAN DE MONEDA DÓLAR",-,1000,1000,4,t,DÓLAR)', '2020-07-28 12:22:45');
INSERT INTO public.bitacora VALUES (115, 'Registro', 'fondos', 1, NULL, '("mantenimiento del personal obrero",2020-06-01,"este fondo tiene como finalidad resguardar el dinero que sera utilizado para el pago de nomina del personal obrero",-,10000000,10000000,6,t,BOLÍVAR)', '2020-07-28 12:24:19');
INSERT INTO public.bitacora VALUES (116, 'Registro', 'interes', 1, NULL, '(1,mora,8,t,123456789)', '2020-07-28 12:25:50');
INSERT INTO public.bitacora VALUES (117, 'Modificado', 'interes', 1, '(1,MORA,8,t,123456789)', '(1,compensatorio,3,t,123456789)', '2020-07-28 12:27:16');
INSERT INTO public.bitacora VALUES (118, 'Registro', 'interes', 1, NULL, '(2,moratorio,6,t,123456789)', '2020-07-28 12:28:30');
INSERT INTO public.bitacora VALUES (119, 'Modificado', 'interes', 1, '(2,MORATORIO,6,t,123456789)', '(2,MORATORIO,3,t,123456789)', '2020-07-28 12:29:11');
INSERT INTO public.bitacora VALUES (120, 'Modificado', 'interes', 1, '(1,COMPENSATORIO,3,t,123456789)', '(1,COMPENSATORIO,1,t,123456789)', '2020-07-28 12:31:11');
INSERT INTO public.bitacora VALUES (121, 'Registro', 'unidad', NULL, NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,,1)', '2020-07-28 12:33:37');
INSERT INTO public.bitacora VALUES (122, 'UPDATE', 'unidad', 1, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,1,1)', '2020-07-28 12:33:37');
INSERT INTO public.bitacora VALUES (123, 'Registro', 'unidad', NULL, NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,,2)', '2020-07-28 12:34:02');
INSERT INTO public.bitacora VALUES (124, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,1,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.47058823529411764,1)', '2020-07-28 12:34:02');
INSERT INTO public.bitacora VALUES (125, 'UPDATE', 'unidad', 1, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.52941176470588236,2)', '2020-07-28 12:34:02');
INSERT INTO public.bitacora VALUES (126, 'Registro', 'unidad', NULL, NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,,3)', '2020-07-28 12:34:27');
INSERT INTO public.bitacora VALUES (127, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.47058823529411764,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.29629629629629628,1)', '2020-07-28 12:34:27');
INSERT INTO public.bitacora VALUES (128, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.52941176470588236,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.33333333333333331,2)', '2020-07-28 12:34:27');
INSERT INTO public.bitacora VALUES (129, 'UPDATE', 'unidad', 1, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.37037037037037035,3)', '2020-07-28 12:34:27');
INSERT INTO public.bitacora VALUES (130, 'Registro', 'unidad', NULL, NULL, '(4,4,456789123456789,-,t,,4)', '2020-07-28 12:34:54');
INSERT INTO public.bitacora VALUES (131, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.29629629629629628,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.21052631578947367,1)', '2020-07-28 12:34:54');
INSERT INTO public.bitacora VALUES (132, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.33333333333333331,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.23684210526315788,2)', '2020-07-28 12:34:54');
INSERT INTO public.bitacora VALUES (133, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.37037037037037035,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.26315789473684209,3)', '2020-07-28 12:34:54');
INSERT INTO public.bitacora VALUES (134, 'UPDATE', 'unidad', 1, '(4,4,456789123456789,-,t,,4)', '(4,4,456789123456789,-,t,0.28947368421052633,4)', '2020-07-28 12:34:54');
INSERT INTO public.bitacora VALUES (135, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,-,t,0.28947368421052633,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.28947368421052633,4)', '2020-07-28 12:35:24');
INSERT INTO public.bitacora VALUES (136, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.21052631578947367,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.21052631578947367,1)', '2020-07-28 12:35:24');
INSERT INTO public.bitacora VALUES (137, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.23684210526315788,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.23684210526315788,2)', '2020-07-28 12:35:24');
INSERT INTO public.bitacora VALUES (138, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.26315789473684209,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.26315789473684209,3)', '2020-07-28 12:35:24');
INSERT INTO public.bitacora VALUES (139, 'Modificado', 'unidad', 1, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.28947368421052633,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.28947368421052633,4)', '2020-07-28 12:35:24');
INSERT INTO public.bitacora VALUES (140, 'Registro', 'unidad', NULL, NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,,5)', '2020-07-28 12:36:36');
INSERT INTO public.bitacora VALUES (141, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.21052631578947367,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.16,1)', '2020-07-28 12:36:36');
INSERT INTO public.bitacora VALUES (142, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.23684210526315788,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.17999999999999999,2)', '2020-07-28 12:36:36');
INSERT INTO public.bitacora VALUES (143, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.26315789473684209,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.20000000000000001,3)', '2020-07-28 12:36:36');
INSERT INTO public.bitacora VALUES (309, 'Registro', 'concepto_gasto', 1, NULL, '(33,"Provisión vacaciones","Vacaciones trabajador",2,t)', '2020-07-28 14:02:51');
INSERT INTO public.bitacora VALUES (145, 'UPDATE', 'unidad', 1, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.23999999999999999,5)', '2020-07-28 12:36:36');
INSERT INTO public.bitacora VALUES (146, 'Registro', 'unidad', NULL, NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,,6)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (147, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.16,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.12698412698412698,1)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (148, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.17999999999999999,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.14285714285714285,2)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (149, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.20000000000000001,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.15873015873015872,3)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (150, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.22,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.17460317460317459,4)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (151, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.23999999999999999,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.19047619047619047,5)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (152, 'UPDATE', 'unidad', 1, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.20634920634920634,6)', '2020-07-28 12:37:17');
INSERT INTO public.bitacora VALUES (153, 'Registro', 'unidad', NULL, NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,,7)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (154, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.12698412698412698,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.1038961038961039,1)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (155, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.14285714285714285,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.11688311688311688,2)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (156, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.15873015873015872,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.12987012987012986,3)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (157, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.17460317460317459,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.14285714285714285,4)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (158, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.19047619047619047,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.15584415584415584,5)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (159, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.20634920634920634,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.16883116883116883,6)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (160, 'UPDATE', 'unidad', 1, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.18181818181818182,7)', '2020-07-28 12:39:09');
INSERT INTO public.bitacora VALUES (161, 'Registro', 'unidad', NULL, NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,,8)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (162, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.1038961038961039,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.086956521739130432,1)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (163, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.11688311688311688,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.097826086956521743,2)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (164, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.12987012987012986,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.10869565217391304,3)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (165, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.14285714285714285,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (166, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.15584415584415584,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (167, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.16883116883116883,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (168, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.18181818181818182,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (169, 'UPDATE', 'unidad', 1, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:39:44');
INSERT INTO public.bitacora VALUES (170, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 10",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (171, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.097826086956521743,2)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (172, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.10869565217391304,3)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (173, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (174, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (175, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (176, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (177, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (178, 'Modificado', 'unidad', 1, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:08');
INSERT INTO public.bitacora VALUES (179, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 9",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (180, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.10869565217391304,3)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (181, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (182, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (183, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (184, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (185, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (186, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (187, 'Modificado', 'unidad', 1, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:16');
INSERT INTO public.bitacora VALUES (188, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 8",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (189, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (190, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (191, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (192, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (193, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (194, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (195, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (196, 'Modificado', 'unidad', 1, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:40:24');
INSERT INTO public.bitacora VALUES (197, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 7",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (198, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (199, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (200, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (201, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (202, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (203, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (204, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (205, 'Modificado', 'unidad', 1, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:40:36');
INSERT INTO public.bitacora VALUES (206, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 6",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (207, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (208, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (209, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (210, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (211, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (212, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (213, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (214, 'Modificado', 'unidad', 1, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '2020-07-28 12:40:43');
INSERT INTO public.bitacora VALUES (215, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 5",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (216, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (217, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (306, 'Eliminado', 'categoriagasto', 1, '(8,SEGUROS,"CATEGORÍA DE SEGUROS",t)', NULL, '2020-07-28 13:58:39');
INSERT INTO public.bitacora VALUES (310, 'Registro', 'concepto_gasto', 1, NULL, '(34,"Ley de alimentación","Pago alimentación",2,t)', '2020-07-28 14:03:17');
INSERT INTO public.bitacora VALUES (311, 'Registro', 'concepto_gasto', 1, NULL, '(35,FAHO,"Fondo de ahorro obligatorio de la vivienda",2,t)', '2020-07-28 14:03:40');
INSERT INTO public.bitacora VALUES (313, 'Registro', 'concepto_gasto', 1, NULL, '(37,"Honorarios administrador","Honorarios administrador/a del condominio",2,t)', '2020-07-28 14:04:34');
INSERT INTO public.bitacora VALUES (314, 'Registro', 'concepto_gasto', 1, NULL, '(38,"bono vacacional","bono vacacional",2,t)', '2020-07-28 14:05:35');
INSERT INTO public.bitacora VALUES (315, 'Registro', 'concepto_gasto', 1, NULL, '(39,"Prestaciones sociales","Prestaciones sociales trabajador",2,t)', '2020-07-28 14:06:09');
INSERT INTO public.bitacora VALUES (218, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (219, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (220, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (221, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (222, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (223, 'Modificado', 'unidad', 1, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '2020-07-28 12:40:50');
INSERT INTO public.bitacora VALUES (224, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 4",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.15217391304347827,7)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (225, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (226, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (227, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (228, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (229, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (230, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (231, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (232, 'Modificado', 'unidad', 1, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.15217391304347827,7)', '2020-07-28 12:40:58');
INSERT INTO public.bitacora VALUES (233, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 3",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.16304347826086957,8)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (234, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (235, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (236, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (237, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (238, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (239, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (240, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.15217391304347827,7)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (241, 'Modificado', 'unidad', 1, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.16304347826086957,8)', '2020-07-28 12:41:04');
INSERT INTO public.bitacora VALUES (242, 'Registro', 'unidad', NULL, NULL, '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,,9)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (243, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.11956521739130435,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.10185185185185185,4)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (244, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.086956521739130432,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.07407407407407407,1)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (245, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.097826086956521743,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.083333333333333329,2)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (246, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.10869565217391304,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.092592592592592587,3)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (247, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.13043478260869565,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.1111111111111111,5)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (248, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.14130434782608695,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.12037037037037036,6)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (249, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.15217391304347827,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.12962962962962962,7)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (250, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.16304347826086957,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.1388888888888889,8)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (251, 'UPDATE', 'unidad', 1, '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,,9)', '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.14814814814814814,9)', '2020-07-28 12:41:35');
INSERT INTO public.bitacora VALUES (252, 'Registro', 'unidad', NULL, NULL, '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,,10)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (253, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.10185185185185185,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.087999999999999995,4)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (254, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.07407407407407407,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.064000000000000001,1)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (255, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.083333333333333329,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.071999999999999995,2)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (256, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.092592592592592587,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.080000000000000002,3)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (307, 'Registro', 'concepto_gasto', 1, NULL, '(31,"Provisión utilidades","Utilidades del trabajador",2,t)', '2020-07-28 14:01:59');
INSERT INTO public.bitacora VALUES (312, 'Registro', 'concepto_gasto', 1, NULL, '(36,"Caja chica","caja chica",2,t)', '2020-07-28 14:03:56');
INSERT INTO public.bitacora VALUES (257, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.1111111111111111,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.096000000000000002,5)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (258, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.12037037037037036,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.104,6)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (259, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.12962962962962962,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.112,7)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (260, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.1388888888888889,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.12,8)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (261, 'UPDATE', 'unidad', NULL, '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.14814814814814814,9)', '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.128,9)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (262, 'UPDATE', 'unidad', 1, '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,,10)', '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,0.13600000000000001,10)', '2020-07-28 12:42:06');
INSERT INTO public.bitacora VALUES (263, 'Registro', 'propietario', 1, NULL, '(V-7888725,KATHERINE,ALEJANDRA,MORA,CRISTANTE,04245104165,KATHY@HOTMAIL.COM,t)', '2020-07-28 12:43:25');
INSERT INTO public.bitacora VALUES (264, 'Registro', 'unidad', NULL, NULL, '(11,11,112324457445453,"CALLE 11, CASA NÚMERO 11",t,,8)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (265, 'UPDATE', 'unidad', NULL, '(11,11,112324457445453,"CALLE 11, CASA NÚMERO 11",t,,8)', '(11,11,112324457445453,"CALLE 11, CASA NÚMERO 11",t,0.10714285714285714,8)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (266, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.087999999999999995,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.07857142857142857,4)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (267, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.064000000000000001,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.057142857142857141,1)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (268, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.071999999999999995,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.064285714285714279,2)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (269, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.080000000000000002,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.071428571428571425,3)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (270, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.096000000000000002,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.085714285714285715,5)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (271, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.104,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.09285714285714286,6)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (272, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.112,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.10000000000000001,7)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (273, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.12,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.10714285714285714,8)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (274, 'UPDATE', 'unidad', NULL, '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.128,9)', '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.11428571428571428,9)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (275, 'UPDATE', 'unidad', 1, '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,0.13600000000000001,10)', '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,0.12142857142857143,10)', '2020-07-28 12:44:57');
INSERT INTO public.bitacora VALUES (276, 'Registro', 'sancion', 1, NULL, '(1,MULTA,7,2020,5,-,Pendiente,DÓLAR)', '2020-07-28 12:45:38');
INSERT INTO public.bitacora VALUES (277, 'Registro', 'sancion', 1, NULL, '(2,MULTA,7,2020,10000000,-,Pendiente,BOLÍVAR)', '2020-07-28 12:46:20');
INSERT INTO public.bitacora VALUES (278, 'Registro', 'sancion', 1, NULL, '(3,MULTA,7,2020,6,-,Pendiente,DÓLAR)', '2020-07-28 12:46:48');
INSERT INTO public.bitacora VALUES (279, 'Registro', 'sancion', 1, NULL, '(4,MULTA,7,2020,1000000,-,Pendiente,BOLÍVAR)', '2020-07-28 13:30:05');
INSERT INTO public.bitacora VALUES (280, 'Registro', 'sancion', 1, NULL, '(5,MULTA,7,2020,2,-,Pendiente,DÓLAR)', '2020-07-28 13:30:22');
INSERT INTO public.bitacora VALUES (281, 'Registro', 'sancion', 1, NULL, '(6,MULTA,7,2020,4,-,Pendiente,DÓLAR)', '2020-07-28 13:30:53');
INSERT INTO public.bitacora VALUES (282, 'Registro', 'sancion', 1, NULL, '(7,MULTA,7,2020,10,-,Pendiente,DÓLAR)', '2020-07-28 13:31:11');
INSERT INTO public.bitacora VALUES (283, 'Registro', 'sancion', 1, NULL, '(8,MULTA,7,2020,2,-,Pendiente,DÓLAR)', '2020-07-28 13:31:48');
INSERT INTO public.bitacora VALUES (284, 'Registro', 'sancion', 1, NULL, '(9,MULTA,7,2020,3000000,-,Pendiente,DÓLAR)', '2020-07-28 13:32:29');
INSERT INTO public.bitacora VALUES (285, 'Modificado', 'sancion', 1, '(9,MULTA,7,2020,3000000,-,Pendiente,DÓLAR)', '(9,MULTA,7,2020,3000000,-,Pendiente,BOLÍVAR)', '2020-07-28 13:32:42');
INSERT INTO public.bitacora VALUES (286, 'Registro', 'sancion', 1, NULL, '(10,"INTERES DE MORA",7,2020,10000000,-,Pendiente,BOLÍVAR)', '2020-07-28 13:33:31');
INSERT INTO public.bitacora VALUES (287, 'Registro', 'cuenta', 1, NULL, '(01029665435468432012,CORRIENTE,1,V-00000000,,t)', '2020-07-28 13:41:09');
INSERT INTO public.bitacora VALUES (288, 'Registro', 'cuenta', 1, NULL, '(01140135451654512313,CORRIENTE,2,V-00000000,,t)', '2020-07-28 13:41:30');
INSERT INTO public.bitacora VALUES (289, 'Registro', 'cuenta', 1, NULL, '(01082315320006453612,AHORRO,3,V-00000000,,t)', '2020-07-28 13:41:49');
INSERT INTO public.bitacora VALUES (290, 'Registro', 'cuenta', 1, NULL, '(01347894501212105416,AHORRO,5,V-00000000,,t)', '2020-07-28 13:42:41');
INSERT INTO public.bitacora VALUES (291, 'Registro', 'concepto_gasto', 1, NULL, '(21,"artículos de oficina","gastos de todo lo que tenga que ver con artículos de oficina",5,t)', '2020-07-28 13:45:50');
INSERT INTO public.bitacora VALUES (292, 'Registro', 'concepto_gasto', 1, NULL, '(22,"artículos de limpieza","todo lo que tenga que ver con la compra de artículos de limpieza",5,t)', '2020-07-28 13:46:25');
INSERT INTO public.bitacora VALUES (293, 'Modificado', 'concepto_gasto', 1, '(22,"ARTÍCULOS DE LIMPIEZA","TODO LO QUE TENGA QUE VER CON LA COMPRA DE ARTÍCULOS DE LIMPIEZA",5,t)', '(22,"ARTÍCULOS DE LIMPIEZA","gastos de TODO LO QUE TENGA QUE VER CON LA COMPRA DE ARTÍCULOS DE LIMPIEZA",5,t)', '2020-07-28 13:46:44');
INSERT INTO public.bitacora VALUES (294, 'Registro', 'concepto_gasto', 1, NULL, '(23,"artículos de ferreteria","GASTOS DE TODO LO QUE TENGA QUE VER CON ARTÍCULOS DE ferreteria",5,t)', '2020-07-28 13:47:23');
INSERT INTO public.bitacora VALUES (295, 'Modificado', 'concepto_gasto', 1, '(23,"ARTÍCULOS DE FERRETERIA","GASTOS DE TODO LO QUE TENGA QUE VER CON ARTÍCULOS DE FERRETERIA",5,t)', '(23,bombillos,"compra de bombillos",5,t)', '2020-07-28 13:52:23');
INSERT INTO public.bitacora VALUES (296, 'Modificado', 'concepto_gasto', 1, '(22,"ARTÍCULOS DE LIMPIEZA","GASTOS DE TODO LO QUE TENGA QUE VER CON LA COMPRA DE ARTÍCULOS DE LIMPIEZA",5,t)', '(22,limpiapisos,"compra de limpiapisos",5,t)', '2020-07-28 13:52:54');
INSERT INTO public.bitacora VALUES (297, 'Modificado', 'concepto_gasto', 1, '(21,"ARTÍCULOS DE OFICINA","GASTOS DE TODO LO QUE TENGA QUE VER CON ARTÍCULOS DE OFICINA",5,t)', '(21,hojas,"compra de hojas de papel",5,t)', '2020-07-28 13:53:13');
INSERT INTO public.bitacora VALUES (298, 'Registro', 'concepto_gasto', 1, NULL, '(24,pintura,"compra de galones de pintura",5,t)', '2020-07-28 13:53:41');
INSERT INTO public.bitacora VALUES (299, 'Registro', 'concepto_gasto', 1, NULL, '(25,"cloro para piscina","compra de cloro para piscina",5,t)', '2020-07-28 13:54:39');
INSERT INTO public.bitacora VALUES (300, 'Registro', 'concepto_gasto', 1, NULL, '(26,"cloro para pisos","compra de cloro para pisos",5,t)', '2020-07-28 13:55:10');
INSERT INTO public.bitacora VALUES (301, 'Registro', 'concepto_gasto', 1, NULL, '(27,lapices,"compra de lapices",5,t)', '2020-07-28 13:55:35');
INSERT INTO public.bitacora VALUES (302, 'Registro', 'concepto_gasto', 1, NULL, '(28,escoba,"compra de escoba para la limpieza",5,t)', '2020-07-28 13:56:47');
INSERT INTO public.bitacora VALUES (303, 'Registro', 'concepto_gasto', 1, NULL, '(29,"artículos de ferreteria","compra de artículos de ferreteria",5,t)', '2020-07-28 13:57:32');
INSERT INTO public.bitacora VALUES (304, 'Registro', 'concepto_gasto', 1, NULL, '(30,rastrillo,"compra de rastrillo para cuidar las areas verdes",5,t)', '2020-07-28 13:58:23');
INSERT INTO public.bitacora VALUES (305, 'Eliminado', 'categoriagasto', 1, '(7,TRANSPORTE,"CATEGORÍA DE TRANSPORTE",t)', NULL, '2020-07-28 13:58:33');
INSERT INTO public.bitacora VALUES (316, 'Registro', 'concepto_gasto', 1, NULL, '(40,"Seguro social","Seguro social del trabajador",2,t)', '2020-07-28 14:06:30');
INSERT INTO public.bitacora VALUES (318, 'Registro', 'concepto_gasto', NULL, NULL, '(41,"Provisión utilidades ","Utilidades del trabajador",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (319, 'Registro', 'concepto_gasto', NULL, NULL, '(42,"Liquidación ","Liquidacion trabajador",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (320, 'Registro', 'concepto_gasto', NULL, NULL, '(43,"Provisión vacaciones ","Vacaciones trabajador",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (321, 'Registro', 'concepto_gasto', NULL, NULL, '(44,"Ley de alimentación","Pago alimentación",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (322, 'Registro', 'concepto_gasto', NULL, NULL, '(45,"Bono Vacacional ","Bono vacacional trabajador",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (323, 'Registro', 'concepto_gasto', NULL, NULL, '(46,"Prestaciones soc.","Prestaciones sociales trabajador",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (324, 'Registro', 'concepto_gasto', NULL, NULL, '(47,"Seguro social","Seguro social trabajador",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (325, 'Registro', 'concepto_gasto', NULL, NULL, '(48,"Retiro prestaciones","Retiro de prestaciones sociales",9,t)', '2020-07-28 14:12:01');
INSERT INTO public.bitacora VALUES (326, 'Registro', 'concepto_gasto', NULL, NULL, '(49,HidroCapital,"Consumo de agua",10,t)', '2020-07-28 14:15:18');
INSERT INTO public.bitacora VALUES (327, 'Registro', 'concepto_gasto', NULL, NULL, '(50,Vigilancia,"Gastos por vigilancia",10,t)', '2020-07-28 14:15:18');
INSERT INTO public.bitacora VALUES (328, 'Registro', 'concepto_gasto', NULL, NULL, '(51,Cantv,"pago a cantv",10,t)', '2020-07-28 14:15:18');
INSERT INTO public.bitacora VALUES (329, 'Registro', 'concepto_gasto', NULL, NULL, '(52,"Consumo de electricidad","Consumo de electricidad",10,t)', '2020-07-28 14:15:18');
INSERT INTO public.bitacora VALUES (330, 'Registro', 'fondos', 1, NULL, '("prestaciones sociales",2020-06-01,-,-,20000000,20000000,7,t,BOLÍVAR)', '2020-07-28 14:52:40');
INSERT INTO public.bitacora VALUES (331, 'Registro', 'asambleas', 1, NULL, '(1,portón,"reparación de portón",2020-06-16)', '2020-07-28 14:54:02');
INSERT INTO public.bitacora VALUES (332, 'Registro', 'asambleas', 1, NULL, '(2,piscina,"mantenimiento de piscina",2020-06-09)', '2020-07-28 14:54:45');
INSERT INTO public.bitacora VALUES (333, 'Registro', 'asambleas', 1, NULL, '(3,"parque infantil","mantenimiento del parque infantil",2020-06-11)', '2020-07-28 14:55:49');
INSERT INTO public.bitacora VALUES (334, 'Registro', 'asambleas', 1, NULL, '(4,"areas verdes","mantenimiento de las areas verdes",2020-06-14)', '2020-07-28 14:56:22');
INSERT INTO public.bitacora VALUES (335, 'Registro', 'asambleas', 1, NULL, '(5,estacionamiento,"mantenimiento del estacionamiento",2020-06-17)', '2020-07-28 14:56:51');
INSERT INTO public.bitacora VALUES (336, 'Registro', 'asambleas', 1, NULL, '(6,"cercado eléctrico","mantenimiento del cercado eléctrico",2020-06-21)', '2020-07-28 14:57:28');
INSERT INTO public.bitacora VALUES (337, 'Registro', 'asambleas', 1, NULL, '(7,fachada,"reunion sobre la fachada del condominio",2020-06-23)', '2020-07-28 14:58:05');
INSERT INTO public.bitacora VALUES (338, 'Registro', 'asambleas', 1, NULL, '(8,alumbrado,"reunion sobre el alumbrado del condominio",2020-06-25)', '2020-07-28 14:58:55');
INSERT INTO public.bitacora VALUES (339, 'Registro', 'asambleas', 1, NULL, '(9,vigilante,"reunión para cambiar al vigilante",2020-06-28)', '2020-07-28 14:59:42');
INSERT INTO public.bitacora VALUES (340, 'Registro', 'asambleas', 1, NULL, '(10,cisterna,"reunion para pedir una cisterna debido a la falta de agua",2020-06-30)', '2020-07-28 15:01:35');
INSERT INTO public.bitacora VALUES (353, 'Registro', 'concepto_gasto', 1, NULL, '(53,"cisterna ","cisterna de agua",10,t)', '2020-07-28 15:03:03');
INSERT INTO public.bitacora VALUES (354, 'Registro', 'concepto_gasto', 1, NULL, '(54,limpieza,"servicios de limpieza",10,t)', '2020-07-28 15:03:41');
INSERT INTO public.bitacora VALUES (355, 'Registro', 'unidad', NULL, NULL, '(12,12,654616846513584,"CALLE 1, CASA NÚMERO 12",t,,1)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (356, 'UPDATE', 'unidad', NULL, '(11,11,112324457445453,"CALLE 11, CASA NÚMERO 11",t,0.10714285714285714,8)', '(11,11,112324457445453,"CALLE 11, CASA NÚMERO 11",t,0.10135135135135136,8)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (357, 'UPDATE', 'unidad', NULL, '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.07857142857142857,4)', '(4,4,456789123456789,"CALLE 4, CASA NÚMERO 4",t,0.074324324324324328,4)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (358, 'UPDATE', 'unidad', NULL, '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.057142857142857141,1)', '(1,1,123456789123456,"CALLE 1, CASA NÚMERO 1",t,0.054054054054054057,1)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (359, 'UPDATE', 'unidad', NULL, '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.064285714285714279,2)', '(2,2,234567891234567,"CALLE 2, CASA NÚMERO 2",t,0.060810810810810814,2)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (360, 'UPDATE', 'unidad', NULL, '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.071428571428571425,3)', '(3,3,345678912345678,"CALLE 3, CASA NÚMERO 3",t,0.067567567567567571,3)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (361, 'UPDATE', 'unidad', NULL, '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.085714285714285715,5)', '(5,5,567891234567891,"CALLE 5, CASA NÚMERO 5",t,0.081081081081081086,5)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (362, 'UPDATE', 'unidad', NULL, '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.09285714285714286,6)', '(6,6,678945612345678,"CALLE 6, CASA NÚMERO 6",t,0.087837837837837843,6)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (363, 'UPDATE', 'unidad', NULL, '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.10000000000000001,7)', '(7,7,789123456789123,"CALLE 7, CASA NÚMERO 7",t,0.0945945945945946,7)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (364, 'UPDATE', 'unidad', NULL, '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.10714285714285714,8)', '(8,8,891234567891234,"CALLE 8, CASA NÚMERO 8",t,0.10135135135135136,8)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (365, 'UPDATE', 'unidad', NULL, '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.11428571428571428,9)', '(9,9,912345678912345,"CALLE 9, CASA NÚMERO 9",t,0.10810810810810811,9)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (366, 'UPDATE', 'unidad', NULL, '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,0.12142857142857143,10)', '(10,10,102345678912304,"CALLE 10, CASA NÚMERO 10",t,0.11486486486486487,10)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (367, 'UPDATE', 'unidad', 1, '(12,12,654616846513584,"CALLE 1, CASA NÚMERO 12",t,,1)', '(12,12,654616846513584,"CALLE 1, CASA NÚMERO 12",t,0.054054054054054057,1)', '2020-07-28 15:21:32');
INSERT INTO public.bitacora VALUES (368, 'modificado', 'fondos', 1, '("PRESTACIONES SOCIALES",2020-06-01,-,-,20000000,20000000,7,t,BOLÍVAR)', '("PRESTACIONES SOCIALES",2020-06-01,-,-,20000000,20000000,7,t,BOLÍVAR)', '2020-07-28 15:22:43');
INSERT INTO public.bitacora VALUES (369, 'Registro', 'gasto', 1, NULL, '(1,papeleria,ORDINARIO,J-12345678,ALICUOTA,1,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:24:32');
INSERT INTO public.bitacora VALUES (370, 'Registro', 'gasto', 1, NULL, '(2,"compra de escobas",ORDINARIO,J-01654632,ALICUOTA,1,2020,1,,-,1,100000,100000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:25:18');
INSERT INTO public.bitacora VALUES (371, 'Registro', 'gasto', 1, NULL, '(3,limpiapisos,ORDINARIO,J-01654632,ALICUOTA,10,2019,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:27:17');
INSERT INTO public.bitacora VALUES (372, 'Modificado', 'gasto', 1, '(1,PAPELERIA,ORDINARIO,J-12345678,ALICUOTA,1,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '(1,PAPELERIA,ORDINARIO,J-12345678,ALICUOTA,10,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:27:27');
INSERT INTO public.bitacora VALUES (373, 'Modificado', 'gasto', 1, '(2,"COMPRA DE ESCOBAS",ORDINARIO,J-01654632,ALICUOTA,1,2020,1,,-,1,100000,100000,Pendiente,Pendiente,BOLÍVAR)', '(2,"COMPRA DE ESCOBAS",ORDINARIO,J-01654632,ALICUOTA,10,2020,1,,-,1,100000,100000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:27:38');
INSERT INTO public.bitacora VALUES (374, 'Modificado', 'gasto', 1, '(3,LIMPIAPISOS,ORDINARIO,J-01654632,ALICUOTA,10,2019,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '(3,LIMPIAPISOS,ORDINARIO,J-01654632,ALICUOTA,10,2019,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:27:51');
INSERT INTO public.bitacora VALUES (375, 'Registro', 'gasto', 1, NULL, '(4,alumbrado,EXTRAORDINARIO,J-34567890,ALICUOTA,10,2019,1,,-,1,50,50,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:37:09');
INSERT INTO public.bitacora VALUES (376, 'Registro', 'gasto', 1, NULL, '(5,pintura,ORDINARIO,J-34567890,ALICUOTA,10,2019,1,,-,1,250,250,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:38:10');
INSERT INTO public.bitacora VALUES (377, 'Registro', 'gasto', 1, NULL, '(6,pintura,ORDINARIO,J-01214000,ALICUOTA,10,2019,1,,"pintura para repasar las lineas del estacionamiento",1,40,40,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:39:25');
INSERT INTO public.bitacora VALUES (378, 'Registro', 'gasto', 1, NULL, '(7,"areas verdes",ORDINARIO,V-13843549,ALICUOTA,10,2019,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:41:00');
INSERT INTO public.bitacora VALUES (379, 'Registro', 'gasto', 1, NULL, '(8,piscina,EXTRAORDINARIO,J-01248325,ALICUOTA,10,2019,1,,-,1,80,80,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:41:36');
INSERT INTO public.bitacora VALUES (380, 'Registro', 'gasto', 1, NULL, '(9,"pago nomina",EXTRAORDINARIO,V-19484654,ALICUOTA,10,2019,1,,-,1,1200000,1200000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:43:06');
INSERT INTO public.bitacora VALUES (381, 'Registro', 'gasto', 1, NULL, '(10,"pago nomina",EXTRAORDINARIO,V-13843549,ALICUOTA,10,2019,1,,"",1,1200000,1200000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:45:22');
INSERT INTO public.bitacora VALUES (382, 'Registro', 'gasto', 1, NULL, '(11,cisterna,EXTRAORDINARIO,V-13843549,ALICUOTA,11,2019,1,,-,1,35,35,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:53:07');
INSERT INTO public.bitacora VALUES (383, 'Registro', 'gasto', 1, NULL, '(12,limpieza,ORDINARIO,V-13843549,ALICUOTA,11,2019,1,,-,1,1200000,1200000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:54:02');
INSERT INTO public.bitacora VALUES (384, 'Registro', 'gasto', 1, NULL, '(13,"pago a vigilante",ORDINARIO,V-19484654,ALICUOTA,11,2019,1,,-,1,1200000,1200000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:55:15');
INSERT INTO public.bitacora VALUES (385, 'Registro', 'proveedores', 1, NULL, '(J-12312354,CANTV,025486549685,CANTV.A@HOTMAIL.COM,CANTV,-,t)', '2020-07-28 15:56:42');
INSERT INTO public.bitacora VALUES (386, 'Registro', 'proveedores', 1, NULL, '(J-01214651,"AGUAS DEL YARACUY",025486216541,ADY@HOTMAIL.COM,"AGUAS DEL YARACUY",-,t)', '2020-07-28 15:57:21');
INSERT INTO public.bitacora VALUES (387, 'Registro', 'gasto', 1, NULL, '(14,cantv,ORDINARIO,J-12312354,ALICUOTA,11,2019,1,,-,1,5,5,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:58:33');
INSERT INTO public.bitacora VALUES (388, 'Registro', 'gasto', 1, NULL, '(15,cisterna,EXTRAORDINARIO,J-01214651,ALICUOTA,11,2019,1,,-,1,35,35,Pendiente,Pendiente,DÓLAR)', '2020-07-28 15:59:04');
INSERT INTO public.bitacora VALUES (389, 'Registro', 'gasto', 1, NULL, '(16,"pago de agua",ORDINARIO,J-01214651,ALICUOTA,11,2019,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 15:59:48');
INSERT INTO public.bitacora VALUES (390, 'Registro', 'gasto', 1, NULL, '(17,"compra de rastrillo",ORDINARIO,J-01654632,ALICUOTA,11,2019,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:00:28');
INSERT INTO public.bitacora VALUES (391, 'Registro', 'gasto', 1, NULL, '(18,"compra de cloros para pisos",ORDINARIO,J-01654632,ALICUOTA,11,2019,1,,-,1,80000,80000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:01:06');
INSERT INTO public.bitacora VALUES (392, 'Registro', 'gasto', 1, NULL, '(19,limpiapisos,ORDINARIO,J-01654632,ALICUOTA,11,2019,1,,-,1,60000,60000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:01:37');
INSERT INTO public.bitacora VALUES (393, 'Registro', 'gasto', 1, NULL, '(20,"compra de hojas blancas",ORDINARIO,J-12345678,ALICUOTA,11,2019,1,,-,1,350000,350000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:02:07');
INSERT INTO public.bitacora VALUES (394, 'Registro', 'gasto', 1, NULL, '(21,"liquidacion de vigilancia",ORDINARIO,V-19484654,ALICUOTA,12,2020,1,,-,1,1500000,1500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:05:28');
INSERT INTO public.bitacora VALUES (395, 'Modificado', 'gasto', 1, '(21,"LIQUIDACION DE VIGILANCIA",ORDINARIO,V-19484654,ALICUOTA,12,2020,1,,-,1,1500000,1500000,Pendiente,Pendiente,BOLÍVAR)', '(21,"LIQUIDACION DE VIGILANCIA",EXTRAORDINARIO,V-19484654,ALICUOTA,12,2020,1,,-,1,1500000,1500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:05:52');
INSERT INTO public.bitacora VALUES (396, 'Registro', 'gasto', 1, NULL, '(22,"vacaciones iris",EXTRAORDINARIO,V-13843549,ALICUOTA,12,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:06:48');
INSERT INTO public.bitacora VALUES (397, 'Registro', 'gasto', 1, NULL, '(23,"artículos de ferreteria",ORDINARIO,J-34567890,ALICUOTA,12,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:08:07');
INSERT INTO public.bitacora VALUES (398, 'Modificado', 'gasto', 1, '(23,"ARTÍCULOS DE FERRETERIA",ORDINARIO,J-34567890,ALICUOTA,12,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '(23,"ARTÍCULOS DE FERRETERIA",ORDINARIO,J-34567890,ALICUOTA,12,2019,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:08:16');
INSERT INTO public.bitacora VALUES (399, 'Modificado', 'gasto', 1, '(21,"LIQUIDACION DE VIGILANCIA",EXTRAORDINARIO,V-19484654,ALICUOTA,12,2020,1,,-,1,1500000,1500000,Pendiente,Pendiente,BOLÍVAR)', '(21,"LIQUIDACION DE VIGILANCIA",EXTRAORDINARIO,V-19484654,ALICUOTA,12,2019,1,,-,1,1500000,1500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:08:23');
INSERT INTO public.bitacora VALUES (400, 'Modificado', 'gasto', 1, '(22,"VACACIONES IRIS",EXTRAORDINARIO,V-13843549,ALICUOTA,12,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '(22,"VACACIONES IRIS",EXTRAORDINARIO,V-13843549,ALICUOTA,12,2019,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:08:31');
INSERT INTO public.bitacora VALUES (401, 'Modificado', 'gasto', 1, '(23,"ARTÍCULOS DE FERRETERIA",ORDINARIO,J-34567890,ALICUOTA,12,2019,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '(23,"ARTÍCULOS DE FERRETERIA",ORDINARIO,J-34567890,ALICUOTA,12,2019,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:08:49');
INSERT INTO public.bitacora VALUES (402, 'Registro', 'gasto', 1, NULL, '(24,porton,EXTRAORDINARIO,J-23456789,ALICUOTA,12,2019,1,,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:09:26');
INSERT INTO public.bitacora VALUES (403, 'Registro', 'gasto', 1, NULL, '(25,tuberias,EXTRAORDINARIO,J-34567890,ALICUOTA,12,2019,1,,-,1,40,40,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:10:19');
INSERT INTO public.bitacora VALUES (404, 'Registro', 'gasto', 1, NULL, '(26,alumbrado,EXTRAORDINARIO,J-01214000,ALICUOTA,12,2019,1,,-,1,1500000,1500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:11:19');
INSERT INTO public.bitacora VALUES (405, 'Registro', 'gasto', 1, NULL, '(27,pintar,ORDINARIO,V-13843549,ALICUOTA,12,2019,1,,-,1,15,15,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:12:20');
INSERT INTO public.bitacora VALUES (406, 'Registro', 'gasto', 1, NULL, '(28,"cercado electrico",ORDINARIO,J-34567890,ALICUOTA,12,2019,1,,-,1,80,80,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:13:26');
INSERT INTO public.bitacora VALUES (407, 'Registro', 'gasto', 1, NULL, '(29,hidro,ORDINARIO,V-19444578,ALICUOTA,12,2019,1,,"",1,70,70,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:14:03');
INSERT INTO public.bitacora VALUES (408, 'Registro', 'gasto', 1, NULL, '(30,limpiafondos,ORDINARIO,J-01248325,ALICUOTA,12,2019,1,,-,1,15,15,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:14:40');
INSERT INTO public.bitacora VALUES (409, 'Modificado', 'gasto', 1, '(30,LIMPIAFONDOS,ORDINARIO,J-01248325,ALICUOTA,12,2019,1,,-,1,15,15,Pendiente,Pendiente,DÓLAR)', '(30,LIMPIAFONDOS,ORDINARIO,J-01248325,ALICUOTA,1,2020,1,,-,1,15,15,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:21:07');
INSERT INTO public.bitacora VALUES (410, 'Registro', 'gasto', 1, NULL, '(31,bombillos,ORDINARIO,J-01654632,ALICUOTA,1,2020,1,,-,1,1200000,1200000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:22:44');
INSERT INTO public.bitacora VALUES (411, 'Registro', 'gasto', 1, NULL, '(32,vigilante,EXTRAORDINARIO,V-19484654,ALICUOTA,1,2020,2,,-,2,3,3,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:24:33');
INSERT INTO public.bitacora VALUES (412, 'Registro', 'gasto', 1, NULL, '(33,"pago de agua",ORDINARIO,J-01214651,ALICUOTA,1,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:25:34');
INSERT INTO public.bitacora VALUES (413, 'Registro', 'gasto', 1, NULL, '(34,"pago nomina vigilante",ORDINARIO,V-19484654,ALICUOTA,1,2020,1,,-,1,800000,800000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:26:57');
INSERT INTO public.bitacora VALUES (414, 'Registro', 'gasto', 1, NULL, '(35,cisterna,ORDINARIO,J-01214651,ALICUOTA,1,2020,1,,-,1,30,30,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:27:25');
INSERT INTO public.bitacora VALUES (415, 'Registro', 'gasto', 1, NULL, '(36,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,1,2020,1,,-,1,800000,800000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:29:18');
INSERT INTO public.bitacora VALUES (416, 'Registro', 'gasto', 1, NULL, '(37,cantv,ORDINARIO,J-12312354,ALICUOTA,1,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:30:11');
INSERT INTO public.bitacora VALUES (417, 'Registro', 'concepto_gasto', 1, NULL, '(55,corpolect,-,10,t)', '2020-07-28 16:30:32');
INSERT INTO public.bitacora VALUES (418, 'Registro', 'gasto', 1, NULL, '(38,"pago de luz",ORDINARIO,J-01214000,ALICUOTA,1,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:32:05');
INSERT INTO public.bitacora VALUES (419, 'Registro', 'proveedores', 1, NULL, '(J-45254645,CORPOLECT,025488465323,CORPOLECT@HOTMAIL.COM,CORPOLECT,-,t)', '2020-07-28 16:33:04');
INSERT INTO public.bitacora VALUES (420, 'Modificado', 'gasto', 1, '(38,"PAGO DE LUZ",ORDINARIO,J-01214000,ALICUOTA,1,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '(38,"PAGO DE LUZ",ORDINARIO,J-45254645,ALICUOTA,1,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:33:15');
INSERT INTO public.bitacora VALUES (421, 'Eliminado', 'concepto_gasto', 1, '(55,CORPOLECT,-,10,t)', NULL, '2020-07-28 16:33:24');
INSERT INTO public.bitacora VALUES (422, 'Registro', 'gasto', 1, NULL, '(39,recogehojas,ORDINARIO,J-01248325,ALICUOTA,1,2020,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:34:15');
INSERT INTO public.bitacora VALUES (423, 'Modificado', 'gasto', 1, '(38,"PAGO DE LUZ",ORDINARIO,J-45254645,ALICUOTA,1,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '(38,"PAGO DE LUZ",ORDINARIO,J-45254645,ALICUOTA,1,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:34:57');
INSERT INTO public.bitacora VALUES (424, 'Modificado', 'gasto', 1, '(39,RECOGEHOJAS,ORDINARIO,J-01248325,ALICUOTA,1,2020,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '(39,RECOGEHOJAS,ORDINARIO,J-01248325,ALICUOTA,1,2020,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:35:08');
INSERT INTO public.bitacora VALUES (425, 'Registro', 'gasto', 1, NULL, '(40,"compra de limpiapisos",ORDINARIO,J-01654632,ALICUOTA,1,2020,1,,-,1,80000,80000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:35:45');
INSERT INTO public.bitacora VALUES (426, 'Registro', 'gasto', 1, NULL, '(41,"compra de hojas de papel",ORDINARIO,J-12345678,ALICUOTA,2,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:42:02');
INSERT INTO public.bitacora VALUES (427, 'Registro', 'gasto', 1, NULL, '(42,"cloro para piscinas",ORDINARIO,J-01248325,ALICUOTA,2,2020,1,,"",1,30,30,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:42:38');
INSERT INTO public.bitacora VALUES (428, 'Registro', 'gasto', 1, NULL, '(43,lapices,ORDINARIO,J-12345678,ALICUOTA,2,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:43:06');
INSERT INTO public.bitacora VALUES (429, 'Registro', 'gasto', 1, NULL, '(44,"articulos de ferreteria",ORDINARIO,J-34567890,ALICUOTA,2,2020,1,,-,1,800000,800000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:45:08');
INSERT INTO public.bitacora VALUES (430, 'Registro', 'gasto', 1, NULL, '(45,cisterna,ORDINARIO,J-01214651,ALICUOTA,2,2020,1,,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:47:43');
INSERT INTO public.bitacora VALUES (431, 'Modificado', 'gasto', 1, '(45,CISTERNA,ORDINARIO,J-01214651,ALICUOTA,2,2020,1,,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '(45,CISTERNA,ORDINARIO,J-01214651,ALICUOTA,2,2020,1,,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:48:44');
INSERT INTO public.bitacora VALUES (432, 'Registro', 'gasto', 1, NULL, '(46,porton,ORDINARIO,J-23456789,ALICUOTA,2,2020,1,,-,1,35,35,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:49:15');
INSERT INTO public.bitacora VALUES (433, 'Registro', 'gasto', 1, NULL, '(47,hidro,ORDINARIO,V-19444578,ALICUOTA,2,2020,1,,-,1,80,80,Pendiente,Pendiente,DÓLAR)', '2020-07-28 16:50:27');
INSERT INTO public.bitacora VALUES (434, 'Registro', 'gasto', 1, NULL, '(48,"señora de la limpieza",EXTRAORDINARIO,V-13843549,ALICUOTA,2,2020,2,,-,2,600000,600000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:52:01');
INSERT INTO public.bitacora VALUES (435, 'Registro', 'gasto', 1, NULL, '(49,vigilante,EXTRAORDINARIO,V-19484654,ALICUOTA,2,2020,5,,-,5,400000,400000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:54:01');
INSERT INTO public.bitacora VALUES (436, 'Registro', 'gasto', 1, NULL, '(50,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,2,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 16:54:44');
INSERT INTO public.bitacora VALUES (437, 'Registro', 'gasto', 1, NULL, '(51,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,3,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:06:00');
INSERT INTO public.bitacora VALUES (438, 'Registro', 'gasto', 1, NULL, '(52,cisterna,ORDINARIO,J-01214651,ALICUOTA,3,2020,1,,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:07:15');
INSERT INTO public.bitacora VALUES (439, 'Registro', 'gasto', 1, NULL, '(53,"pago electricidad",ORDINARIO,J-45254645,ALICUOTA,3,2020,1,,-,1,350000,350000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:07:54');
INSERT INTO public.bitacora VALUES (440, 'Registro', 'gasto', 1, NULL, '(54,"pago cantv",ORDINARIO,J-12312354,ALICUOTA,3,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:08:54');
INSERT INTO public.bitacora VALUES (441, 'Registro', 'gasto', 1, NULL, '(55,"pago nomina",ORDINARIO,V-19484654,ALICUOTA,3,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:09:54');
INSERT INTO public.bitacora VALUES (442, 'Registro', 'gasto', 1, NULL, '(56,"pago aguas del yaracuy",ORDINARIO,J-01214651,ALICUOTA,3,2020,1,,-,1,400000,400000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:10:35');
INSERT INTO public.bitacora VALUES (443, 'Registro', 'gasto', 1, NULL, '(57,"compra de bombillos",ORDINARIO,J-01654632,ALICUOTA,3,2020,1,,-,1,1000000,1000000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:11:30');
INSERT INTO public.bitacora VALUES (444, 'Registro', 'proveedores', 1, NULL, '(J-31365564,"CÁMARAS SR",025478998432,SR@HOTMAIL.COM,SOFIA,-,t)', '2020-07-28 17:12:32');
INSERT INTO public.bitacora VALUES (445, 'Registro', 'gasto', 1, NULL, '(58,piscina,ORDINARIO,J-01248325,ALICUOTA,3,2020,1,,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:13:42');
INSERT INTO public.bitacora VALUES (446, 'Registro', 'gasto', 1, NULL, '(59,"parque infantil",ORDINARIO,J-34567890,ALICUOTA,3,2020,1,,-,1,5,5,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:14:13');
INSERT INTO public.bitacora VALUES (447, 'Registro', 'gasto', 1, NULL, '(60,"zonas verdes",ORDINARIO,V-13843549,ALICUOTA,3,2020,1,,-,1,5,5,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:14:48');
INSERT INTO public.bitacora VALUES (448, 'Registro', 'gasto', 1, NULL, '(61,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,4,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:26:50');
INSERT INTO public.bitacora VALUES (449, 'Registro', 'gasto', 1, NULL, '(62,"pago corpolect",ORDINARIO,J-45254645,ALICUOTA,4,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:27:44');
INSERT INTO public.bitacora VALUES (450, 'Registro', 'gasto', 1, NULL, '(63,cantv,ORDINARIO,J-12312354,ALICUOTA,4,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:28:24');
INSERT INTO public.bitacora VALUES (451, 'Registro', 'gasto', 1, NULL, '(64,"pago nomina",ORDINARIO,V-19484654,ALICUOTA,4,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:29:30');
INSERT INTO public.bitacora VALUES (452, 'Registro', 'gasto', 1, NULL, '(65,"pago aguas del yaracuy",ORDINARIO,J-01214651,ALICUOTA,4,2020,1,,-,1,350000,350000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:30:08');
INSERT INTO public.bitacora VALUES (453, 'Registro', 'gasto', 1, NULL, '(66,"compra de rastrillo",ORDINARIO,J-01654632,ALICUOTA,4,2020,1,,-,1,50000,50000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:31:28');
INSERT INTO public.bitacora VALUES (454, 'Registro', 'gasto', 1, NULL, '(67,"compra de articulos de ferreteria",ORDINARIO,J-34567890,ALICUOTA,4,2020,1,,-,1,550000,550000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:32:15');
INSERT INTO public.bitacora VALUES (455, 'Registro', 'gasto', 1, NULL, '(68,camaras,ORDINARIO,J-31365564,ALICUOTA,4,2020,1,,-,1,20,20,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:33:19');
INSERT INTO public.bitacora VALUES (456, 'Registro', 'gasto', 1, NULL, '(69,"cercado electrico",ORDINARIO,J-45254645,ALICUOTA,4,2020,1,,-,1,40,40,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:34:02');
INSERT INTO public.bitacora VALUES (457, 'Registro', 'gasto', 1, NULL, '(70,"pago por mantenimiento de estacionamiento",ORDINARIO,V-19484654,ALICUOTA,4,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:34:53');
INSERT INTO public.bitacora VALUES (458, 'Registro', 'gasto', 1, NULL, '(71,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,5,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:37:40');
INSERT INTO public.bitacora VALUES (459, 'Registro', 'gasto', 1, NULL, '(72,"pago de cisterna",ORDINARIO,J-01214651,ALICUOTA,5,2020,1,,-,1,20,20,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:38:13');
INSERT INTO public.bitacora VALUES (460, 'Registro', 'gasto', 1, NULL, '(73,"pago corpolect",ORDINARIO,J-45254645,ALICUOTA,5,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:38:42');
INSERT INTO public.bitacora VALUES (461, 'Registro', 'gasto', 1, NULL, '(74,"pago cantv",ORDINARIO,J-12312354,ALICUOTA,5,2020,1,,-,1,350000,350000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:39:04');
INSERT INTO public.bitacora VALUES (462, 'Registro', 'gasto', 1, NULL, '(75,"pago nomina",ORDINARIO,V-19484654,ALICUOTA,5,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:39:29');
INSERT INTO public.bitacora VALUES (463, 'Registro', 'gasto', 1, NULL, '(76,"pago a aguas del yaracuy",ORDINARIO,J-01214651,ALICUOTA,5,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:39:57');
INSERT INTO public.bitacora VALUES (464, 'Registro', 'gasto', 1, NULL, '(77,"pago seguro social",ORDINARIO,V-13843549,ALICUOTA,5,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:40:40');
INSERT INTO public.bitacora VALUES (465, 'Registro', 'gasto', 1, NULL, '(78,"pago seguro social",ORDINARIO,V-19484654,ALICUOTA,5,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:41:07');
INSERT INTO public.bitacora VALUES (466, 'Registro', 'gasto', 1, NULL, '(79,"cloro para pisos",ORDINARIO,J-01654632,ALICUOTA,5,2020,1,,-,1,80000,80000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:41:48');
INSERT INTO public.bitacora VALUES (467, 'Registro', 'gasto', 1, NULL, '(80,desinfectante,ORDINARIO,J-01654632,ALICUOTA,5,2020,1,,-,1,60000,60000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:42:11');
INSERT INTO public.bitacora VALUES (468, 'Registro', 'gasto', 1, NULL, '(81,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,6,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:49:48');
INSERT INTO public.bitacora VALUES (469, 'Registro', 'gasto', 1, NULL, '(82,"pago luz",ORDINARIO,J-45254645,ALICUOTA,6,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:50:15');
INSERT INTO public.bitacora VALUES (470, 'Registro', 'gasto', 1, NULL, '(83,"pago cantv",ORDINARIO,J-12312354,ALICUOTA,6,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:50:40');
INSERT INTO public.bitacora VALUES (471, 'Registro', 'gasto', 1, NULL, '(84,"pago nomina",ORDINARIO,V-19484654,ALICUOTA,6,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:51:12');
INSERT INTO public.bitacora VALUES (472, 'Registro', 'gasto', 1, NULL, '(85,"pago aguas del yaracuy",ORDINARIO,J-01214651,ALICUOTA,6,2020,1,,-,1,330000,330000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:51:49');
INSERT INTO public.bitacora VALUES (473, 'Registro', 'gasto', 1, NULL, '(86,"pago seguro social",ORDINARIO,V-13843549,ALICUOTA,6,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:52:18');
INSERT INTO public.bitacora VALUES (474, 'Registro', 'gasto', 1, NULL, '(87,"pago seguro social",ORDINARIO,V-19484654,ALICUOTA,6,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:52:39');
INSERT INTO public.bitacora VALUES (475, 'Registro', 'gasto', 1, NULL, '(88,"compra de hojas de papel",ORDINARIO,J-12345678,ALICUOTA,6,2020,1,,-,1,1000000,1000000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:53:29');
INSERT INTO public.bitacora VALUES (476, 'Registro', 'gasto', 1, NULL, '(89,"compra de bombillos",ORDINARIO,J-01654632,ALICUOTA,6,2020,1,,-,1,1000000,1000000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 17:54:11');
INSERT INTO public.bitacora VALUES (477, 'Registro', 'gasto', 1, NULL, '(90,hidro,ORDINARIO,V-19444578,ALICUOTA,6,2020,1,,-,1,80,80,Pendiente,Pendiente,DÓLAR)', '2020-07-28 17:54:42');
INSERT INTO public.bitacora VALUES (478, 'Registro', 'gasto', 1, NULL, '(91,"pago nomina",ORDINARIO,V-13843549,ALICUOTA,6,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:04:28');
INSERT INTO public.bitacora VALUES (479, 'Modificado', 'gasto', 1, '(91,"PAGO NOMINA",ORDINARIO,V-13843549,ALICUOTA,6,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '(91,"PAGO NOMINA",ORDINARIO,V-13843549,ALICUOTA,7,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:05:12');
INSERT INTO public.bitacora VALUES (480, 'Registro', 'gasto', 1, NULL, '(92,"pago cisterna",ORDINARIO,J-01214651,ALICUOTA,7,2020,1,,-,1,20,20,Pendiente,Pendiente,DÓLAR)', '2020-07-28 18:05:53');
INSERT INTO public.bitacora VALUES (481, 'Registro', 'gasto', 1, NULL, '(93,"pago luz",ORDINARIO,J-45254645,ALICUOTA,7,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:06:20');
INSERT INTO public.bitacora VALUES (482, 'Registro', 'gasto', 1, NULL, '(94,"pago cantv",ORDINARIO,J-12312354,ALICUOTA,7,2020,1,,-,1,300000,300000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:06:52');
INSERT INTO public.bitacora VALUES (483, 'Registro', 'gasto', 1, NULL, '(95,"pago nomina",ORDINARIO,V-19484654,ALICUOTA,7,2020,1,,-,1,500000,500000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:07:12');
INSERT INTO public.bitacora VALUES (484, 'Registro', 'gasto', 1, NULL, '(96,"pago agua",ORDINARIO,J-01214651,ALICUOTA,7,2020,1,,-,1,250000,250000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:07:36');
INSERT INTO public.bitacora VALUES (485, 'Registro', 'gasto', 1, NULL, '(97,"reparacion de porton",EXTRAORDINARIO,J-23456789,ALICUOTA,7,2020,1,1,-,1,80,80,Pendiente,Pendiente,DÓLAR)', '2020-07-28 18:08:23');
INSERT INTO public.bitacora VALUES (486, 'Registro', 'gasto', 1, NULL, '(98,"parque infantil",EXTRAORDINARIO,J-34567890,ALICUOTA,7,2020,1,3,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 18:09:39');
INSERT INTO public.bitacora VALUES (487, 'Registro', 'gasto', 1, NULL, '(99,"compra de articulo de limpieza",ORDINARIO,J-01654632,ALICUOTA,7,2020,1,,-,1,60000,60000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:10:47');
INSERT INTO public.bitacora VALUES (488, 'Modificado', 'gasto', 1, '(98,"PARQUE INFANTIL",EXTRAORDINARIO,J-34567890,ALICUOTA,7,2020,1,3,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '(98,"PARQUE INFANTIL",EXTRAORDINARIO,J-34567890,ALICUOTA,7,2020,1,3,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 18:11:17');
INSERT INTO public.bitacora VALUES (489, 'Modificado', 'gasto', 1, '(98,"PARQUE INFANTIL",EXTRAORDINARIO,J-34567890,ALICUOTA,7,2020,1,3,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '(98,"PARQUE INFANTIL",EXTRAORDINARIO,J-34567890,ALICUOTA,7,2020,1,3,-,1,25,25,Pendiente,Pendiente,DÓLAR)', '2020-07-28 18:11:29');
INSERT INTO public.bitacora VALUES (490, 'Modificado', 'gasto', 1, '(99,"COMPRA DE ARTICULO DE LIMPIEZA",ORDINARIO,J-01654632,ALICUOTA,7,2020,1,,-,1,60000,60000,Pendiente,Pendiente,BOLÍVAR)', '(99,"COMPRA DE ARTICULO DE LIMPIEZA",ORDINARIO,J-01654632,ALICUOTA,7,2020,1,,-,1,60000,60000,Pendiente,Pendiente,BOLÍVAR)', '2020-07-28 18:11:44');
INSERT INTO public.bitacora VALUES (491, 'Registro', 'gasto', 1, NULL, '(100,"compra de cloro para piscina",ORDINARIO,J-01248325,ALICUOTA,7,2020,1,,-,1,5,5,Pendiente,Pendiente,DÓLAR)', '2020-07-28 18:12:13');


--
-- TOC entry 3441 (class 0 OID 39156)
-- Dependencies: 201
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (3, 'MANTENIMIENTO', 'CATEGORÍA DE MANTENIMIENTO', true);
INSERT INTO public.categoriagasto VALUES (4, 'REPARACIÓN', 'CATEGORÍA DE REPARACIÓN', true);
INSERT INTO public.categoriagasto VALUES (5, 'GASTOS VARIOS', 'CATEGORÍA DE GASTOS VARIOS', true);
INSERT INTO public.categoriagasto VALUES (6, 'PAGOS', 'CATEGORÍA PAGOS', false);
INSERT INTO public.categoriagasto VALUES (2, 'ADMINISTRACIÓN', 'GASTOS ADMINISTRATIVOS', true);
INSERT INTO public.categoriagasto VALUES (9, 'NOMINA', 'CATEGORÍA DE NOMINA', true);
INSERT INTO public.categoriagasto VALUES (10, 'SERVICIOS', 'CATEGORÍA DE SERVICIOS', true);
INSERT INTO public.categoriagasto VALUES (1, 'USO COMÚN', 'CONJUNTO DE CONCEPTOS DE USO COMUN Y CONSUMO', false);
INSERT INTO public.categoriagasto VALUES (7, 'TRANSPORTE', 'CATEGORÍA DE TRANSPORTE', false);
INSERT INTO public.categoriagasto VALUES (8, 'SEGUROS', 'CATEGORÍA DE SEGUROS', false);


--
-- TOC entry 3471 (class 0 OID 39404)
-- Dependencies: 231
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3458 (class 0 OID 39265)
-- Dependencies: 218
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (10, ' MANTENIMIENTO DE ALUMBRADO', 'MATENIMIENTO DEL ALUMBRADO DEL CONDOMINIO', 3, true);
INSERT INTO public.concepto_gasto VALUES (9, 'MANTENIMIENTO DE FACHADA', 'MANTENIMIENTO DE LA FACHADA DEL CONDOMINIO', 3, true);
INSERT INTO public.concepto_gasto VALUES (7, 'MANGTENIMIENTO DE ESTACIONAMIENTO', 'MANTENIMIENTO DE ESTACIONAMIENTO', 3, true);
INSERT INTO public.concepto_gasto VALUES (6, 'MANMTENIMIENTO DE ZONAS VERDES', 'MANTENIMIENTO ZONAS VERDES', 3, true);
INSERT INTO public.concepto_gasto VALUES (5, 'MANTENIMIENTO DE PARQUE INFANTIL', 'MANTENIMIENTO DE PARQUE INFANTIL', 3, true);
INSERT INTO public.concepto_gasto VALUES (4, 'MANTENIMIENTO DE PISCINA', 'MANTENIMIENTO DE PISCINA', 3, true);
INSERT INTO public.concepto_gasto VALUES (3, 'MANTENIMIENTO DE TUBERIAS', 'MANTENIMIENTO DE TUBERIAS', 3, true);
INSERT INTO public.concepto_gasto VALUES (2, 'MANTENIMIENTO DE ASCENSOR', 'MANTENIMIENTO DE ASCENSOR', 3, true);
INSERT INTO public.concepto_gasto VALUES (12, 'REPARACIÓN DE ALUMBRADO', 'REPARACIÓN DE ALUMBRADO', 4, true);
INSERT INTO public.concepto_gasto VALUES (11, 'REPARACIÓN DE ASCENSOR', 'REPARACION DE ASCENSOR', 4, true);
INSERT INTO public.concepto_gasto VALUES (1, 'MANTENIMIENTO DE PORTÓN', 'MANTENIMIENTO DE PORTÓN', 3, true);
INSERT INTO public.concepto_gasto VALUES (13, 'REPARACIÓN DE PORTÓN', 'REPARACIÓN DE PORTÓN', 4, true);
INSERT INTO public.concepto_gasto VALUES (14, 'REPARACIÓN DE TUBERIAS', 'REPARACIÓN DE TUBERIAS', 4, true);
INSERT INTO public.concepto_gasto VALUES (15, 'REPARACIÓN DE CERCADO ELÉCTRICO', 'REPARACIÓN DE CERCADO ELÉCTRICO', 4, true);
INSERT INTO public.concepto_gasto VALUES (16, 'REPARACIÓN DE CÁMARAS DE VIGILANCIA', 'REPARACIÓN DE CÁMARAS', 4, true);
INSERT INTO public.concepto_gasto VALUES (8, 'MANTENIMIENTO DE CÁMARAS', 'MANTENIMIENTO DE CÁMARAS DE VIGILANCIA', 3, true);
INSERT INTO public.concepto_gasto VALUES (17, 'REPARACIÓN ELÉCTRICA', 'REPARACION/SUSTITUCION DE COMPONENTES ELÉCTRICOS DEL CONDOMINIO', 4, true);
INSERT INTO public.concepto_gasto VALUES (18, 'REPARACIÓN DE HIDRONEUMÁTICO', 'REPARACION LA BOMBA DE AGUA', 4, true);
INSERT INTO public.concepto_gasto VALUES (19, 'REPARACIÓN DE LIMPIAFONDOS', 'REPARACION/SUSTITUCION DE LIMPIAFONDOS PARA LA PISCINA', 4, true);
INSERT INTO public.concepto_gasto VALUES (20, 'REPARACIÓN DE RECOGEHOJAS', 'REPARACIÓN/SUSTITUCIÓN DE RECOGEHOJAS PARA LA PISCINA', 4, true);
INSERT INTO public.concepto_gasto VALUES (23, 'BOMBILLOS', 'COMPRA DE BOMBILLOS', 5, true);
INSERT INTO public.concepto_gasto VALUES (22, 'LIMPIAPISOS', 'COMPRA DE LIMPIAPISOS', 5, true);
INSERT INTO public.concepto_gasto VALUES (21, 'HOJAS', 'COMPRA DE HOJAS DE PAPEL', 5, true);
INSERT INTO public.concepto_gasto VALUES (24, 'PINTURA', 'COMPRA DE GALONES DE PINTURA', 5, true);
INSERT INTO public.concepto_gasto VALUES (25, 'CLORO PARA PISCINA', 'COMPRA DE CLORO PARA PISCINA', 5, true);
INSERT INTO public.concepto_gasto VALUES (26, 'CLORO PARA PISOS', 'COMPRA DE CLORO PARA PISOS', 5, true);
INSERT INTO public.concepto_gasto VALUES (27, 'LAPICES', 'COMPRA DE LAPICES', 5, true);
INSERT INTO public.concepto_gasto VALUES (28, 'ESCOBA', 'COMPRA DE ESCOBA PARA LA LIMPIEZA', 5, true);
INSERT INTO public.concepto_gasto VALUES (29, 'ARTÍCULOS DE FERRETERIA', 'COMPRA DE ARTÍCULOS DE FERRETERIA', 5, true);
INSERT INTO public.concepto_gasto VALUES (30, 'RASTRILLO', 'COMPRA DE RASTRILLO PARA CUIDAR LAS AREAS VERDES', 5, true);
INSERT INTO public.concepto_gasto VALUES (31, 'PROVISIÓN UTILIDADES', 'UTILIDADES DEL TRABAJADOR', 2, true);
INSERT INTO public.concepto_gasto VALUES (32, 'LIQUIDACIÓN', 'LIQUIDACION TRABAJADOR', 2, true);
INSERT INTO public.concepto_gasto VALUES (33, 'PROVISIÓN VACACIONES', 'VACACIONES TRABAJADOR', 2, true);
INSERT INTO public.concepto_gasto VALUES (34, 'LEY DE ALIMENTACIÓN', 'PAGO ALIMENTACIÓN', 2, true);
INSERT INTO public.concepto_gasto VALUES (35, 'FAHO', 'FONDO DE AHORRO OBLIGATORIO DE LA VIVIENDA', 2, true);
INSERT INTO public.concepto_gasto VALUES (36, 'CAJA CHICA', 'CAJA CHICA', 2, true);
INSERT INTO public.concepto_gasto VALUES (37, 'HONORARIOS ADMINISTRADOR', 'HONORARIOS ADMINISTRADOR/A DEL CONDOMINIO', 2, true);
INSERT INTO public.concepto_gasto VALUES (38, 'BONO VACACIONAL', 'BONO VACACIONAL', 2, true);
INSERT INTO public.concepto_gasto VALUES (39, 'PRESTACIONES SOCIALES', 'PRESTACIONES SOCIALES TRABAJADOR', 2, true);
INSERT INTO public.concepto_gasto VALUES (40, 'SEGURO SOCIAL', 'SEGURO SOCIAL DEL TRABAJADOR', 2, true);
INSERT INTO public.concepto_gasto VALUES (41, 'PROVISIÓN UTILIDADES ', 'UTILIDADES DEL TRABAJADOR', 9, true);
INSERT INTO public.concepto_gasto VALUES (42, 'LIQUIDACIÓN ', 'LIQUIDACION TRABAJADOR', 9, true);
INSERT INTO public.concepto_gasto VALUES (43, 'PROVISIÓN VACACIONES ', 'VACACIONES TRABAJADOR', 9, true);
INSERT INTO public.concepto_gasto VALUES (44, 'LEY DE ALIMENTACIÓN', 'PAGO ALIMENTACIÓN', 9, true);
INSERT INTO public.concepto_gasto VALUES (45, 'BONO VACACIONAL ', 'BONO VACACIONAL TRABAJADOR', 9, true);
INSERT INTO public.concepto_gasto VALUES (46, 'PRESTACIONES SOC.', 'PRESTACIONES SOCIALES TRABAJADOR', 9, true);
INSERT INTO public.concepto_gasto VALUES (47, 'SEGURO SOCIAL', 'SEGURO SOCIAL TRABAJADOR', 9, true);
INSERT INTO public.concepto_gasto VALUES (48, 'RETIRO PRESTACIONES', 'RETIRO DE PRESTACIONES SOCIALES', 9, true);
INSERT INTO public.concepto_gasto VALUES (49, 'HIDROCAPITAL', 'CONSUMO DE AGUA', 10, true);
INSERT INTO public.concepto_gasto VALUES (50, 'VIGILANCIA', 'GASTOS POR VIGILANCIA', 10, true);
INSERT INTO public.concepto_gasto VALUES (51, 'CANTV', 'PAGO A CANTV', 10, true);
INSERT INTO public.concepto_gasto VALUES (52, 'CONSUMO DE ELECTRICIDAD', 'CONSUMO DE ELECTRICIDAD', 10, true);
INSERT INTO public.concepto_gasto VALUES (53, 'CISTERNA ', 'CISTERNA DE AGUA', 10, true);
INSERT INTO public.concepto_gasto VALUES (54, 'LIMPIEZA', 'SERVICIOS DE LIMPIEZA', 10, true);
INSERT INTO public.concepto_gasto VALUES (55, 'CORPOLECT', '-', 10, false);


--
-- TOC entry 3442 (class 0 OID 39165)
-- Dependencies: 202
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('123456789', 'URB. EL JARDÍN', '02540123402', 'UR1ELJARDIN@HOTMAIL.COM', true);


--
-- TOC entry 3459 (class 0 OID 39277)
-- Dependencies: 219
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('01029665435468432012', 'CORRIENTE', 1, 'V-00000000', NULL, true);
INSERT INTO public.cuenta VALUES ('01140135451654512313', 'CORRIENTE', 2, 'V-00000000', NULL, true);
INSERT INTO public.cuenta VALUES ('01082315320006453612', 'AHORRO', 3, 'V-00000000', NULL, true);
INSERT INTO public.cuenta VALUES ('01347894501212105416', 'AHORRO', 5, 'V-00000000', NULL, true);


--
-- TOC entry 3465 (class 0 OID 39336)
-- Dependencies: 225
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3481 (class 0 OID 39503)
-- Dependencies: 241
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3461 (class 0 OID 39300)
-- Dependencies: 221
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('RESERVA EN DÓLARES', '2020-06-01', 'ESTE FONDO TIENE COMO PROPOSITO MANTENER EL CAPITAL DE RESERVAS DEL CONDOMINIO EN DÓLARES', '-', 1800, 1800, 1, true, 'DÓLAR');
INSERT INTO public.fondos VALUES ('RESERVA EN BOLÍVARES', '2020-06-01', 'ESTE FONDO TIENE COMO PROPOSITO MANTENER EL CAPITAL DE RESERVAS DEL CONDOMINIO EN BOLÍVARES', '-', 25000000, 25000000, 2, true, 'BOLÍVAR');
INSERT INTO public.fondos VALUES ('ADMINISTRATIVO', '2020-06-01', 'ESTE FONDO TIENE COMO FINALIDAD RESGUARDAR EL DINERO QUE SERA UTILIZADO PARA PAGAR LA NOMINA DE LOS GASTOS ADMINISTRATIVOS', '-', 15000000, 15000000, 5, true, 'BOLÍVAR');
INSERT INTO public.fondos VALUES ('OPERATIVO EN BOLÍVARES', '2020-06-01', 'ESTE FONDO TIENE COMO FINALIDAD RESGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SENA DE MONEDA BOLÍVAR', '-', 190000000, 190000000, 3, true, 'BOLÍVAR');
INSERT INTO public.fondos VALUES ('OPERATIVO EN DÓLARES', '2020-06-01', 'ESTE FONDO TIENE COMO FINALIDAD RESGUARDAR EL DINERO QUE SERA UTILIZADO EN CUALQUIER GASTO QUE NECESITE REALIZAR EL CONDOMINIO LOS CUALES SEAN DE MONEDA DÓLAR', '-', 1000, 1000, 4, true, 'DÓLAR');
INSERT INTO public.fondos VALUES ('MANTENIMIENTO DEL PERSONAL OBRERO', '2020-06-01', 'ESTE FONDO TIENE COMO FINALIDAD RESGUARDAR EL DINERO QUE SERA UTILIZADO PARA EL PAGO DE NOMINA DEL PERSONAL OBRERO', '-', 10000000, 10000000, 6, true, 'BOLÍVAR');
INSERT INTO public.fondos VALUES ('PRESTACIONES SOCIALES', '2020-06-01', '-', '-', 20000000, 20000000, 7, true, 'BOLÍVAR');


--
-- TOC entry 3444 (class 0 OID 39174)
-- Dependencies: 204
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.forma_pago VALUES (1, 'PAGO MÓVIL', true);
INSERT INTO public.forma_pago VALUES (2, 'TRANSFERENCIA', true);
INSERT INTO public.forma_pago VALUES (3, 'DEPÓSITO', true);
INSERT INTO public.forma_pago VALUES (4, 'EFECTIVO', true);
INSERT INTO public.forma_pago VALUES (5, 'CHEQUE', true);
INSERT INTO public.forma_pago VALUES (6, 'PUNTO DE VENTA', true);
INSERT INTO public.forma_pago VALUES (7, 'PETROS', true);
INSERT INTO public.forma_pago VALUES (8, 'VALE', true);
INSERT INTO public.forma_pago VALUES (9, 'PAGARE', true);
INSERT INTO public.forma_pago VALUES (10, 'PAYPAL', true);


--
-- TOC entry 3446 (class 0 OID 39188)
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
INSERT INTO public.funcion VALUES (24, 'Bitacora');


--
-- TOC entry 3463 (class 0 OID 39312)
-- Dependencies: 223
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto VALUES (1, 'PAPELERIA', 'ORDINARIO', 'J-12345678', 'ALICUOTA', 10, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (2, 'COMPRA DE ESCOBAS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 10, 2020, 1, NULL, '-', 1, 100000, 100000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (3, 'LIMPIAPISOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 10, 2019, 1, NULL, '-', 1, 50000, 50000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (4, 'ALUMBRADO', 'EXTRAORDINARIO', 'J-34567890', 'ALICUOTA', 10, 2019, 1, NULL, '-', 1, 50, 50, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (5, 'PINTURA', 'ORDINARIO', 'J-34567890', 'ALICUOTA', 10, 2019, 1, NULL, '-', 1, 250, 250, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (6, 'PINTURA', 'ORDINARIO', 'J-01214000', 'ALICUOTA', 10, 2019, 1, NULL, 'PINTURA PARA REPASAR LAS LINEAS DEL ESTACIONAMIENTO', 1, 40, 40, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (7, 'AREAS VERDES', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 10, 2019, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (8, 'PISCINA', 'EXTRAORDINARIO', 'J-01248325', 'ALICUOTA', 10, 2019, 1, NULL, '-', 1, 80, 80, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (9, 'PAGO NOMINA', 'EXTRAORDINARIO', 'V-19484654', 'ALICUOTA', 10, 2019, 1, NULL, '-', 1, 1200000, 1200000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (10, 'PAGO NOMINA', 'EXTRAORDINARIO', 'V-13843549', 'ALICUOTA', 10, 2019, 1, NULL, '', 1, 1200000, 1200000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (11, 'CISTERNA', 'EXTRAORDINARIO', 'V-13843549', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 35, 35, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (12, 'LIMPIEZA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 1200000, 1200000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (13, 'PAGO A VIGILANTE', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 1200000, 1200000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (14, 'CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 5, 5, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (15, 'CISTERNA', 'EXTRAORDINARIO', 'J-01214651', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 35, 35, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (16, 'PAGO DE AGUA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (17, 'COMPRA DE RASTRILLO', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 50000, 50000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (18, 'COMPRA DE CLOROS PARA PISOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 80000, 80000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (19, 'LIMPIAPISOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 60000, 60000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (20, 'COMPRA DE HOJAS BLANCAS', 'ORDINARIO', 'J-12345678', 'ALICUOTA', 11, 2019, 1, NULL, '-', 1, 350000, 350000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (21, 'LIQUIDACION DE VIGILANCIA', 'EXTRAORDINARIO', 'V-19484654', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 1500000, 1500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (22, 'VACACIONES IRIS', 'EXTRAORDINARIO', 'V-13843549', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (23, 'ARTÍCULOS DE FERRETERIA', 'ORDINARIO', 'J-34567890', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (24, 'PORTON', 'EXTRAORDINARIO', 'J-23456789', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 25, 25, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (25, 'TUBERIAS', 'EXTRAORDINARIO', 'J-34567890', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 40, 40, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (26, 'ALUMBRADO', 'EXTRAORDINARIO', 'J-01214000', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 1500000, 1500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (27, 'PINTAR', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 15, 15, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (28, 'CERCADO ELECTRICO', 'ORDINARIO', 'J-34567890', 'ALICUOTA', 12, 2019, 1, NULL, '-', 1, 80, 80, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (29, 'HIDRO', 'ORDINARIO', 'V-19444578', 'ALICUOTA', 12, 2019, 1, NULL, '', 1, 70, 70, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (30, 'LIMPIAFONDOS', 'ORDINARIO', 'J-01248325', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 15, 15, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (31, 'BOMBILLOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 1200000, 1200000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (32, 'VIGILANTE', 'EXTRAORDINARIO', 'V-19484654', 'ALICUOTA', 1, 2020, 2, NULL, '-', 2, 3, 3, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (33, 'PAGO DE AGUA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (34, 'PAGO NOMINA VIGILANTE', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 800000, 800000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (35, 'CISTERNA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 30, 30, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (36, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 800000, 800000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (37, 'CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (38, 'PAGO DE LUZ', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (39, 'RECOGEHOJAS', 'ORDINARIO', 'J-01248325', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 50000, 50000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (40, 'COMPRA DE LIMPIAPISOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 1, 2020, 1, NULL, '-', 1, 80000, 80000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (41, 'COMPRA DE HOJAS DE PAPEL', 'ORDINARIO', 'J-12345678', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (42, 'CLORO PARA PISCINAS', 'ORDINARIO', 'J-01248325', 'ALICUOTA', 2, 2020, 1, NULL, '', 1, 30, 30, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (43, 'LAPICES', 'ORDINARIO', 'J-12345678', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (44, 'ARTICULOS DE FERRETERIA', 'ORDINARIO', 'J-34567890', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 800000, 800000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (45, 'CISTERNA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 25, 25, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (46, 'PORTON', 'ORDINARIO', 'J-23456789', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 35, 35, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (47, 'HIDRO', 'ORDINARIO', 'V-19444578', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 80, 80, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (48, 'SEÑORA DE LA LIMPIEZA', 'EXTRAORDINARIO', 'V-13843549', 'ALICUOTA', 2, 2020, 2, NULL, '-', 2, 600000, 600000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (49, 'VIGILANTE', 'EXTRAORDINARIO', 'V-19484654', 'ALICUOTA', 2, 2020, 5, NULL, '-', 5, 400000, 400000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (50, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 2, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (51, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (52, 'CISTERNA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 25, 25, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (53, 'PAGO ELECTRICIDAD', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 350000, 350000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (54, 'PAGO CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (55, 'PAGO NOMINA', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (56, 'PAGO AGUAS DEL YARACUY', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 400000, 400000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (57, 'COMPRA DE BOMBILLOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 1000000, 1000000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (58, 'PISCINA', 'ORDINARIO', 'J-01248325', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 25, 25, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (59, 'PARQUE INFANTIL', 'ORDINARIO', 'J-34567890', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 5, 5, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (60, 'ZONAS VERDES', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 3, 2020, 1, NULL, '-', 1, 5, 5, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (61, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (62, 'PAGO CORPOLECT', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (63, 'CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (64, 'PAGO NOMINA', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (65, 'PAGO AGUAS DEL YARACUY', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 350000, 350000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (66, 'COMPRA DE RASTRILLO', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 50000, 50000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (67, 'COMPRA DE ARTICULOS DE FERRETERIA', 'ORDINARIO', 'J-34567890', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 550000, 550000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (68, 'CAMARAS', 'ORDINARIO', 'J-31365564', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 20, 20, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (69, 'CERCADO ELECTRICO', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 40, 40, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (70, 'PAGO POR MANTENIMIENTO DE ESTACIONAMIENTO', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 4, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (71, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (72, 'PAGO DE CISTERNA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 20, 20, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (73, 'PAGO CORPOLECT', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (74, 'PAGO CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 350000, 350000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (75, 'PAGO NOMINA', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (76, 'PAGO A AGUAS DEL YARACUY', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (77, 'PAGO SEGURO SOCIAL', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (78, 'PAGO SEGURO SOCIAL', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (79, 'CLORO PARA PISOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 80000, 80000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (80, 'DESINFECTANTE', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 5, 2020, 1, NULL, '-', 1, 60000, 60000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (81, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (82, 'PAGO LUZ', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (83, 'PAGO CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (84, 'PAGO NOMINA', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (85, 'PAGO AGUAS DEL YARACUY', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 330000, 330000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (86, 'PAGO SEGURO SOCIAL', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (87, 'PAGO SEGURO SOCIAL', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (88, 'COMPRA DE HOJAS DE PAPEL', 'ORDINARIO', 'J-12345678', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 1000000, 1000000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (89, 'COMPRA DE BOMBILLOS', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 1000000, 1000000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (90, 'HIDRO', 'ORDINARIO', 'V-19444578', 'ALICUOTA', 6, 2020, 1, NULL, '-', 1, 80, 80, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (91, 'PAGO NOMINA', 'ORDINARIO', 'V-13843549', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (92, 'PAGO CISTERNA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 20, 20, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (93, 'PAGO LUZ', 'ORDINARIO', 'J-45254645', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (94, 'PAGO CANTV', 'ORDINARIO', 'J-12312354', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 300000, 300000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (95, 'PAGO NOMINA', 'ORDINARIO', 'V-19484654', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 500000, 500000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (96, 'PAGO AGUA', 'ORDINARIO', 'J-01214651', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 250000, 250000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (97, 'REPARACION DE PORTON', 'EXTRAORDINARIO', 'J-23456789', 'ALICUOTA', 7, 2020, 1, 1, '-', 1, 80, 80, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (99, 'COMPRA DE ARTICULO DE LIMPIEZA', 'ORDINARIO', 'J-01654632', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 60000, 60000, 'Pendiente', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.gasto VALUES (98, 'PARQUE INFANTIL', 'EXTRAORDINARIO', 'J-34567890', 'ALICUOTA', 7, 2020, 1, 3, '-', 1, 25, 25, 'Pendiente', 'Pendiente', 'DÓLAR');
INSERT INTO public.gasto VALUES (100, 'COMPRA DE CLORO PARA PISCINA', 'ORDINARIO', 'J-01248325', 'ALICUOTA', 7, 2020, 1, NULL, '-', 1, 5, 5, 'Pendiente', 'Pendiente', 'DÓLAR');


--
-- TOC entry 3448 (class 0 OID 39201)
-- Dependencies: 208
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (2, 'MORATORIO', 3, true, '123456789');
INSERT INTO public.interes VALUES (1, 'COMPENSATORIO', 1, true, '123456789');


--
-- TOC entry 3477 (class 0 OID 39467)
-- Dependencies: 237
-- Data for Name: mensaje; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3449 (class 0 OID 39216)
-- Dependencies: 209
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.persona VALUES ('V-00000000', 'ADMIN', '', 'ISTRADOR', '', '0000-0000000', 'ADMIN@ADMIN.COM', true);
INSERT INTO public.persona VALUES ('V-8517596', 'BLANCA', 'ROSA', 'SINGER', 'MUJICA', '04127616516', 'BRSM@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-1434801', 'BLANCA', 'CELESTINA', 'MUJICA', 'SILVA', '04245789625', 'BCMS@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-20888725', 'MARIA', 'DELOSANGELES', 'OSORIO', 'SINGER', '04127909117', 'MDLAOS@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-20458966', 'MARIEL', 'MERCEDES', 'SALAZAR', 'RODRIGUEZ', '04245789652', 'MMSR@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-27328852', 'MARYORITH', 'NAZARETH', 'SINGER', 'MUJICA', '04125084544', 'MNSM@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-27699315', 'MARIA', 'MERCEDES', 'ALVAREZ', 'BARRIOS', '04265465100', 'MMAB@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-6412943', 'JULIO', 'ALEJANDRO', 'PEREZ', 'ALVAREZ', '04245781200', 'JAPA@HOTMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-26943430', 'SAMUEL', 'ALEJANDRO', 'PEREZ', 'MORA', '04245222312', 'SAPM@GMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-27458101', 'ABRAHAM', 'ALEJANDRO', 'GIL', 'PEREZ', '04241578966', 'AAGP@GMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-27145012', 'ROBERT', 'JOSUE', 'BLANCO', 'CAMARACO', '04264015651', 'RJBC@GMAIL.COM', true);
INSERT INTO public.persona VALUES ('V-7888725', 'KATHERINE', 'ALEJANDRA', 'MORA', 'CRISTANTE', '04245104165', 'KATHY@HOTMAIL.COM', true);


--
-- TOC entry 3466 (class 0 OID 39362)
-- Dependencies: 226
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietario VALUES ('V-8517596', true);
INSERT INTO public.propietario VALUES ('V-1434801', true);
INSERT INTO public.propietario VALUES ('V-20888725', true);
INSERT INTO public.propietario VALUES ('V-20458966', true);
INSERT INTO public.propietario VALUES ('V-27328852', true);
INSERT INTO public.propietario VALUES ('V-27699315', true);
INSERT INTO public.propietario VALUES ('V-6412943', true);
INSERT INTO public.propietario VALUES ('V-26943430', true);
INSERT INTO public.propietario VALUES ('V-27458101', true);
INSERT INTO public.propietario VALUES ('V-27145012', true);
INSERT INTO public.propietario VALUES ('V-7888725', true);


--
-- TOC entry 3450 (class 0 OID 39224)
-- Dependencies: 210
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('J-12345678', 'LIBRERIA LA ROSA', '025400124578', 'LR@HOTMAIL.COM', 'ANNA', '5TA AVENIDA ENTRE CALLE 18 Y 19', true);
INSERT INTO public.proveedores VALUES ('J-23456789', 'PORTONES LOS HERMANOS', '025478141032', 'PH@HOTMAIL.COM', 'LUIS', '6TA AVENIDA CON ESQUINA CALLE 12', true);
INSERT INTO public.proveedores VALUES ('V-16216543', 'REPARADOR DEL ASCENSOR', '042411210038', 'REAS@HOTMAIL.COM', 'JUAN', '-', true);
INSERT INTO public.proveedores VALUES ('J-34567890', 'FERRETERIA TODO', '025448741000', 'FERRETODO@GMAIL.COM', 'LUCAS', '8VA AVENIDA ENTRE CALLE 8 Y 7', true);
INSERT INTO public.proveedores VALUES ('V-13843549', 'SEÑORA DE LA LIMPIEZA', '042647400441', 'I3SRIS@HOTMAILC.COM', 'IRIS', '-', true);
INSERT INTO public.proveedores VALUES ('J-01248325', 'PISCINAS TODO YARACUY', '025478910065', 'PISCINAS@HOTMAIL.COM', 'FELIX', '6TA AVENIDA ENTRE CALLES 12 Y 13', true);
INSERT INTO public.proveedores VALUES ('J-01214000', 'FERREGAITA', '025410230147', 'FERREGAITAS12@GMAIL.COM', 'JOSE', '2 AVENIDA ENTRE CALLES 16 Y 17', true);
INSERT INTO public.proveedores VALUES ('V-19484654', 'VIGILANTE', '041247825100', 'HUGO12@GMAIL.COM', 'HUGO', '-', true);
INSERT INTO public.proveedores VALUES ('J-01654632', 'INVERSIONES RAVICA', '025478120456', 'INVERSIONESRAVICA12@HOTMAIL.COM', 'CARLOS', 'CALLE 18, ESQUINA DE LA 7MA AVENIDA', true);
INSERT INTO public.proveedores VALUES ('V-19444578', 'REPARADOR DEL HIDRONEUMÁTICO', '041248720100', 'REPARACIONHIDRO@HOTMAIL.COM', 'JAVIER', '-', true);
INSERT INTO public.proveedores VALUES ('J-12312354', 'CANTV', '025486549685', 'CANTV.A@HOTMAIL.COM', 'CANTV', '-', true);
INSERT INTO public.proveedores VALUES ('J-01214651', 'AGUAS DEL YARACUY', '025486216541', 'ADY@HOTMAIL.COM', 'AGUAS DEL YARACUY', '-', true);
INSERT INTO public.proveedores VALUES ('J-45254645', 'CORPOLECT', '025488465323', 'CORPOLECT@HOTMAIL.COM', 'CORPOLECT', '-', true);
INSERT INTO public.proveedores VALUES ('J-31365564', 'CÁMARAS SR', '025478998432', 'SR@HOTMAIL.COM', 'SOFIA', '-', true);


--
-- TOC entry 3483 (class 0 OID 39524)
-- Dependencies: 243
-- Data for Name: puente_asambleas_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asambleas_propietario VALUES (1, 1, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (2, 1, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (3, 1, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (4, 1, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (5, 1, 'V-27458101');
INSERT INTO public.puente_asambleas_propietario VALUES (6, 1, 'V-27145012');
INSERT INTO public.puente_asambleas_propietario VALUES (7, 1, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (8, 2, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (9, 2, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (10, 2, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (11, 2, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (12, 2, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (13, 2, 'V-27699315');
INSERT INTO public.puente_asambleas_propietario VALUES (14, 2, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (15, 2, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (16, 2, 'V-27458101');
INSERT INTO public.puente_asambleas_propietario VALUES (17, 2, 'V-27145012');
INSERT INTO public.puente_asambleas_propietario VALUES (18, 2, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (19, 3, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (20, 3, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (21, 3, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (22, 4, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (23, 4, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (24, 4, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (25, 4, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (26, 4, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (27, 4, 'V-27699315');
INSERT INTO public.puente_asambleas_propietario VALUES (28, 4, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (29, 5, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (30, 5, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (31, 5, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (32, 5, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (33, 5, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (34, 5, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (35, 5, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (36, 6, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (37, 6, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (38, 6, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (39, 6, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (40, 6, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (41, 6, 'V-27699315');
INSERT INTO public.puente_asambleas_propietario VALUES (42, 6, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (43, 6, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (44, 6, 'V-27458101');
INSERT INTO public.puente_asambleas_propietario VALUES (45, 6, 'V-27145012');
INSERT INTO public.puente_asambleas_propietario VALUES (46, 6, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (47, 7, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (48, 7, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (49, 7, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (50, 7, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (51, 7, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (52, 7, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (53, 7, 'V-27458101');
INSERT INTO public.puente_asambleas_propietario VALUES (54, 7, 'V-27145012');
INSERT INTO public.puente_asambleas_propietario VALUES (55, 7, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (56, 8, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (57, 8, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (58, 8, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (59, 8, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (60, 8, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (61, 8, 'V-27458101');
INSERT INTO public.puente_asambleas_propietario VALUES (62, 8, 'V-27145012');
INSERT INTO public.puente_asambleas_propietario VALUES (63, 8, 'V-7888725');
INSERT INTO public.puente_asambleas_propietario VALUES (64, 9, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (65, 9, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (66, 9, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (67, 9, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (68, 9, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (69, 9, 'V-27699315');
INSERT INTO public.puente_asambleas_propietario VALUES (70, 9, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (71, 9, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (72, 10, 'V-8517596');
INSERT INTO public.puente_asambleas_propietario VALUES (73, 10, 'V-1434801');
INSERT INTO public.puente_asambleas_propietario VALUES (74, 10, 'V-20888725');
INSERT INTO public.puente_asambleas_propietario VALUES (75, 10, 'V-20458966');
INSERT INTO public.puente_asambleas_propietario VALUES (76, 10, 'V-27328852');
INSERT INTO public.puente_asambleas_propietario VALUES (77, 10, 'V-27699315');
INSERT INTO public.puente_asambleas_propietario VALUES (78, 10, 'V-6412943');
INSERT INTO public.puente_asambleas_propietario VALUES (79, 10, 'V-26943430');
INSERT INTO public.puente_asambleas_propietario VALUES (80, 10, 'V-27458101');
INSERT INTO public.puente_asambleas_propietario VALUES (81, 10, 'V-27145012');
INSERT INTO public.puente_asambleas_propietario VALUES (82, 10, 'V-7888725');


--
-- TOC entry 3485 (class 0 OID 39542)
-- Dependencies: 245
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3487 (class 0 OID 39563)
-- Dependencies: 247
-- Data for Name: puente_gasto_concepto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_gasto_concepto VALUES (1, 1, 27, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (2, 2, 28, 100000);
INSERT INTO public.puente_gasto_concepto VALUES (3, 3, 22, 50000);
INSERT INTO public.puente_gasto_concepto VALUES (4, 4, 10, 50);
INSERT INTO public.puente_gasto_concepto VALUES (5, 5, 9, 250);
INSERT INTO public.puente_gasto_concepto VALUES (6, 6, 7, 40);
INSERT INTO public.puente_gasto_concepto VALUES (7, 7, 6, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (8, 8, 4, 80);
INSERT INTO public.puente_gasto_concepto VALUES (9, 9, 50, 1200000);
INSERT INTO public.puente_gasto_concepto VALUES (10, 10, 54, 1200000);
INSERT INTO public.puente_gasto_concepto VALUES (11, 11, 53, 35);
INSERT INTO public.puente_gasto_concepto VALUES (12, 12, 54, 1200000);
INSERT INTO public.puente_gasto_concepto VALUES (13, 13, 50, 1200000);
INSERT INTO public.puente_gasto_concepto VALUES (14, 14, 4, 5);
INSERT INTO public.puente_gasto_concepto VALUES (15, 15, 3, 35);
INSERT INTO public.puente_gasto_concepto VALUES (16, 16, 49, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (17, 17, 30, 50000);
INSERT INTO public.puente_gasto_concepto VALUES (18, 18, 26, 80000);
INSERT INTO public.puente_gasto_concepto VALUES (19, 19, 22, 60000);
INSERT INTO public.puente_gasto_concepto VALUES (20, 20, 21, 350000);
INSERT INTO public.puente_gasto_concepto VALUES (21, 21, 42, 1500000);
INSERT INTO public.puente_gasto_concepto VALUES (22, 22, 38, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (23, 23, 29, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (24, 24, 1, 25);
INSERT INTO public.puente_gasto_concepto VALUES (25, 25, 3, 40);
INSERT INTO public.puente_gasto_concepto VALUES (26, 26, 12, 1500000);
INSERT INTO public.puente_gasto_concepto VALUES (27, 27, 9, 15);
INSERT INTO public.puente_gasto_concepto VALUES (28, 28, 15, 80);
INSERT INTO public.puente_gasto_concepto VALUES (29, 29, 18, 70);
INSERT INTO public.puente_gasto_concepto VALUES (30, 30, 19, 15);
INSERT INTO public.puente_gasto_concepto VALUES (31, 31, 23, 1200000);
INSERT INTO public.puente_gasto_concepto VALUES (32, 32, 40, 3);
INSERT INTO public.puente_gasto_concepto VALUES (33, 33, 49, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (34, 34, 50, 800000);
INSERT INTO public.puente_gasto_concepto VALUES (35, 35, 53, 30);
INSERT INTO public.puente_gasto_concepto VALUES (36, 36, 54, 800000);
INSERT INTO public.puente_gasto_concepto VALUES (37, 37, 51, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (38, 38, 52, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (39, 39, 20, 50000);
INSERT INTO public.puente_gasto_concepto VALUES (40, 40, 22, 80000);
INSERT INTO public.puente_gasto_concepto VALUES (41, 41, 21, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (42, 42, 25, 30);
INSERT INTO public.puente_gasto_concepto VALUES (43, 43, 27, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (44, 44, 29, 800000);
INSERT INTO public.puente_gasto_concepto VALUES (45, 45, 53, 25);
INSERT INTO public.puente_gasto_concepto VALUES (46, 46, 1, 35);
INSERT INTO public.puente_gasto_concepto VALUES (47, 47, 18, 80);
INSERT INTO public.puente_gasto_concepto VALUES (48, 48, 47, 600000);
INSERT INTO public.puente_gasto_concepto VALUES (49, 49, 47, 400000);
INSERT INTO public.puente_gasto_concepto VALUES (50, 50, 54, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (51, 51, 54, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (52, 52, 53, 25);
INSERT INTO public.puente_gasto_concepto VALUES (53, 53, 52, 350000);
INSERT INTO public.puente_gasto_concepto VALUES (54, 54, 51, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (55, 55, 50, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (56, 56, 49, 400000);
INSERT INTO public.puente_gasto_concepto VALUES (57, 57, 23, 1000000);
INSERT INTO public.puente_gasto_concepto VALUES (58, 58, 4, 25);
INSERT INTO public.puente_gasto_concepto VALUES (59, 59, 5, 5);
INSERT INTO public.puente_gasto_concepto VALUES (60, 60, 6, 5);
INSERT INTO public.puente_gasto_concepto VALUES (61, 61, 54, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (62, 62, 52, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (63, 63, 51, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (64, 64, 50, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (65, 65, 49, 350000);
INSERT INTO public.puente_gasto_concepto VALUES (66, 66, 30, 50000);
INSERT INTO public.puente_gasto_concepto VALUES (67, 67, 29, 550000);
INSERT INTO public.puente_gasto_concepto VALUES (68, 68, 8, 20);
INSERT INTO public.puente_gasto_concepto VALUES (69, 69, 15, 40);
INSERT INTO public.puente_gasto_concepto VALUES (70, 70, 7, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (71, 71, 54, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (72, 72, 53, 20);
INSERT INTO public.puente_gasto_concepto VALUES (73, 73, 52, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (74, 74, 51, 350000);
INSERT INTO public.puente_gasto_concepto VALUES (75, 75, 50, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (76, 76, 49, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (77, 77, 47, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (78, 78, 47, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (79, 79, 26, 80000);
INSERT INTO public.puente_gasto_concepto VALUES (80, 80, 22, 60000);
INSERT INTO public.puente_gasto_concepto VALUES (81, 81, 54, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (82, 82, 52, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (83, 83, 51, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (84, 84, 50, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (85, 85, 49, 330000);
INSERT INTO public.puente_gasto_concepto VALUES (86, 86, 47, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (87, 87, 47, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (88, 88, 21, 1000000);
INSERT INTO public.puente_gasto_concepto VALUES (89, 89, 23, 1000000);
INSERT INTO public.puente_gasto_concepto VALUES (90, 90, 18, 80);
INSERT INTO public.puente_gasto_concepto VALUES (91, 91, 54, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (92, 92, 53, 20);
INSERT INTO public.puente_gasto_concepto VALUES (93, 93, 52, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (94, 94, 51, 300000);
INSERT INTO public.puente_gasto_concepto VALUES (95, 95, 50, 500000);
INSERT INTO public.puente_gasto_concepto VALUES (96, 96, 49, 250000);
INSERT INTO public.puente_gasto_concepto VALUES (97, 97, 13, 80);
INSERT INTO public.puente_gasto_concepto VALUES (98, 98, 5, 25);
INSERT INTO public.puente_gasto_concepto VALUES (99, 99, 22, 60000);
INSERT INTO public.puente_gasto_concepto VALUES (100, 100, 25, 5);


--
-- TOC entry 3489 (class 0 OID 39581)
-- Dependencies: 249
-- Data for Name: puente_mensaje_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3491 (class 0 OID 39601)
-- Dependencies: 251
-- Data for Name: puente_persona_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3493 (class 0 OID 39619)
-- Dependencies: 253
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (1, 1, 1);
INSERT INTO public.puente_sancion_unidad VALUES (2, 2, 2);
INSERT INTO public.puente_sancion_unidad VALUES (3, 3, 3);
INSERT INTO public.puente_sancion_unidad VALUES (4, 4, 4);
INSERT INTO public.puente_sancion_unidad VALUES (5, 5, 5);
INSERT INTO public.puente_sancion_unidad VALUES (6, 6, 6);
INSERT INTO public.puente_sancion_unidad VALUES (7, 7, 7);
INSERT INTO public.puente_sancion_unidad VALUES (8, 8, 8);
INSERT INTO public.puente_sancion_unidad VALUES (10, 9, 11);
INSERT INTO public.puente_sancion_unidad VALUES (11, 10, 11);
INSERT INTO public.puente_sancion_unidad VALUES (12, 10, 10);


--
-- TOC entry 3497 (class 0 OID 39657)
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
INSERT INTO public.puente_tipo_funcion VALUES (24, 1, 24, true, true, true, true);


--
-- TOC entry 3495 (class 0 OID 39637)
-- Dependencies: 255
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (1, 'V-8517596', 1, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (2, 'V-1434801', 2, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (3, 'V-20888725', 3, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (4, 'V-20458966', 4, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (5, 'V-27328852', 5, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (6, 'V-27699315', 6, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (7, 'V-6412943', 7, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (8, 'V-26943430', 8, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (9, 'V-27458101', 9, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (10, 'V-27145012', 10, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (11, 'V-7888725', 11, '2020-07-28', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (12, 'V-8517596', 12, '2020-07-28', NULL, 1, true);


--
-- TOC entry 3473 (class 0 OID 39430)
-- Dependencies: 233
-- Data for Name: recibo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3467 (class 0 OID 39373)
-- Dependencies: 227
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.responsable VALUES ('V-00000000', true);


--
-- TOC entry 3452 (class 0 OID 39232)
-- Dependencies: 212
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (1, 'MULTA', 7, 2020, 5, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (2, 'MULTA', 7, 2020, 10000000, '-', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.sancion VALUES (3, 'MULTA', 7, 2020, 6, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (4, 'MULTA', 7, 2020, 1000000, '-', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.sancion VALUES (5, 'MULTA', 7, 2020, 2, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (6, 'MULTA', 7, 2020, 4, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (7, 'MULTA', 7, 2020, 10, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (8, 'MULTA', 7, 2020, 2, '-', 'Pendiente', 'DÓLAR');
INSERT INTO public.sancion VALUES (9, 'MULTA', 7, 2020, 3000000, '-', 'Pendiente', 'BOLÍVAR');
INSERT INTO public.sancion VALUES (10, 'INTERES DE MORA', 7, 2020, 10000000, '-', 'Pendiente', 'BOLÍVAR');


--
-- TOC entry 3454 (class 0 OID 39240)
-- Dependencies: 214
-- Data for Name: tipo_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_unidad VALUES (1, 40, 'ORQUÍDEA', true);
INSERT INTO public.tipo_unidad VALUES (2, 45, 'ROSA', true);
INSERT INTO public.tipo_unidad VALUES (3, 50, 'PETUNIA', true);
INSERT INTO public.tipo_unidad VALUES (4, 55, 'GIRASOL', true);
INSERT INTO public.tipo_unidad VALUES (5, 60, 'LAVANDA', true);
INSERT INTO public.tipo_unidad VALUES (6, 65, 'TULIPAN', true);
INSERT INTO public.tipo_unidad VALUES (7, 70, 'HORTENSIAS', true);
INSERT INTO public.tipo_unidad VALUES (8, 75, 'JAZMÍN', true);
INSERT INTO public.tipo_unidad VALUES (9, 80, 'AMAPOLA', true);
INSERT INTO public.tipo_unidad VALUES (10, 85, 'LIRIO', true);


--
-- TOC entry 3456 (class 0 OID 39251)
-- Dependencies: 216
-- Data for Name: tipo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_usuario VALUES (1, 'ADMINISTRADOR', true);


--
-- TOC entry 3469 (class 0 OID 39386)
-- Dependencies: 229
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidad VALUES (11, '11', '112324457445453', 'CALLE 11, CASA NÚMERO 11', true, 0.10135135135135136, 8);
INSERT INTO public.unidad VALUES (4, '4', '456789123456789', 'CALLE 4, CASA NÚMERO 4', true, 0.074324324324324328, 4);
INSERT INTO public.unidad VALUES (1, '1', '123456789123456', 'CALLE 1, CASA NÚMERO 1', true, 0.054054054054054057, 1);
INSERT INTO public.unidad VALUES (2, '2', '234567891234567', 'CALLE 2, CASA NÚMERO 2', true, 0.060810810810810814, 2);
INSERT INTO public.unidad VALUES (3, '3', '345678912345678', 'CALLE 3, CASA NÚMERO 3', true, 0.067567567567567571, 3);
INSERT INTO public.unidad VALUES (5, '5', '567891234567891', 'CALLE 5, CASA NÚMERO 5', true, 0.081081081081081086, 5);
INSERT INTO public.unidad VALUES (6, '6', '678945612345678', 'CALLE 6, CASA NÚMERO 6', true, 0.087837837837837843, 6);
INSERT INTO public.unidad VALUES (7, '7', '789123456789123', 'CALLE 7, CASA NÚMERO 7', true, 0.0945945945945946, 7);
INSERT INTO public.unidad VALUES (8, '8', '891234567891234', 'CALLE 8, CASA NÚMERO 8', true, 0.10135135135135136, 8);
INSERT INTO public.unidad VALUES (9, '9', '912345678912345', 'CALLE 9, CASA NÚMERO 9', true, 0.10810810810810811, 9);
INSERT INTO public.unidad VALUES (10, '10', '102345678912304', 'CALLE 10, CASA NÚMERO 10', true, 0.11486486486486487, 10);
INSERT INTO public.unidad VALUES (12, '12', '654616846513584', 'CALLE 1, CASA NÚMERO 12', true, 0.054054054054054057, 1);


--
-- TOC entry 3475 (class 0 OID 39446)
-- Dependencies: 235
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (1, 'Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 'V-00000000', 1, true);


--
-- TOC entry 3479 (class 0 OID 39483)
-- Dependencies: 239
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 196
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 10, true);


--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 198
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 10, true);


--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 258
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bitacora_id_bitacora_seq', 491, true);


--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 10, true);


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

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 55, true);


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

SELECT pg_catalog.setval('public.fondos_id_seq', 7, true);


--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 203
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pago_id_seq', 10, true);


--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 205
-- Name: funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcion_id_seq', 24, true);


--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 222
-- Name: gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_id_seq', 100, true);


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 207
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 2, true);


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

SELECT pg_catalog.setval('public.puente_asambleas_propietario_id_seq', 82, true);


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

SELECT pg_catalog.setval('public.puente_gasto_concepto_id_seq', 100, true);


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

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 12, true);


--
-- TOC entry 3557 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_tipo_funcion_id_seq', 24, true);


--
-- TOC entry 3558 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 12, true);


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

SELECT pg_catalog.setval('public.sancion_id_seq', 10, true);


--
-- TOC entry 3561 (class 0 OID 0)
-- Dependencies: 213
-- Name: tipo_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_unidad_id_seq', 10, true);


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

SELECT pg_catalog.setval('public.unidad_id_seq', 12, true);


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
-- TOC entry 3119 (class 2606 OID 39142)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 3121 (class 2606 OID 39153)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 3123 (class 2606 OID 39151)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 3205 (class 2606 OID 39684)
-- Name: bitacora bitacora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_pkey PRIMARY KEY (id_bitacora);


--
-- TOC entry 3125 (class 2606 OID 39164)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 3127 (class 2606 OID 39162)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3175 (class 2606 OID 39412)
-- Name: cobro_unidad cobro_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3155 (class 2606 OID 39271)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3129 (class 2606 OID 39171)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 3163 (class 2606 OID 39341)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3157 (class 2606 OID 39282)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 3187 (class 2606 OID 39511)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 3159 (class 2606 OID 39309)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 3131 (class 2606 OID 39185)
-- Name: forma_pago forma_pago_forma_pago_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_forma_pago_key UNIQUE (forma_pago);


--
-- TOC entry 3133 (class 2606 OID 39183)
-- Name: forma_pago forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id);


--
-- TOC entry 3135 (class 2606 OID 39198)
-- Name: funcion funcion_funcion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_funcion_key UNIQUE (funcion);


--
-- TOC entry 3137 (class 2606 OID 39196)
-- Name: funcion funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3161 (class 2606 OID 39323)
-- Name: gasto gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3139 (class 2606 OID 39210)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 3183 (class 2606 OID 39475)
-- Name: mensaje mensaje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (id);


--
-- TOC entry 3141 (class 2606 OID 39223)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3165 (class 2606 OID 39367)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3143 (class 2606 OID 39229)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3189 (class 2606 OID 39529)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3191 (class 2606 OID 39550)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3193 (class 2606 OID 39568)
-- Name: puente_gasto_concepto puente_gasto_concepto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 39588)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 39606)
-- Name: puente_persona_condominio puente_persona_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3199 (class 2606 OID 39624)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3203 (class 2606 OID 39662)
-- Name: puente_tipo_funcion puente_tipo_funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 39644)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3177 (class 2606 OID 39438)
-- Name: recibo recibo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT recibo_pkey PRIMARY KEY (id);


--
-- TOC entry 3167 (class 2606 OID 39378)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3145 (class 2606 OID 39237)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3147 (class 2606 OID 39246)
-- Name: tipo_unidad tipo_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3149 (class 2606 OID 39248)
-- Name: tipo_unidad tipo_unidad_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_tipo_key UNIQUE (tipo);


--
-- TOC entry 3151 (class 2606 OID 39260)
-- Name: tipo_usuario tipo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3153 (class 2606 OID 39262)
-- Name: tipo_usuario tipo_usuario_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_tipo_key UNIQUE (tipo);


--
-- TOC entry 3169 (class 2606 OID 39396)
-- Name: unidad unidad_n_documento_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_n_documento_key UNIQUE (n_documento);


--
-- TOC entry 3171 (class 2606 OID 39394)
-- Name: unidad unidad_n_unidad_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_n_unidad_key UNIQUE (n_unidad);


--
-- TOC entry 3173 (class 2606 OID 39392)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3179 (class 2606 OID 39452)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3181 (class 2606 OID 39454)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3185 (class 2606 OID 39490)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2620 OID 39793)
-- Name: asambleas tg_asambleas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_asambleas BEFORE INSERT ON public.asambleas FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3251 (class 2620 OID 39794)
-- Name: banco tg_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3281 (class 2620 OID 39795)
-- Name: unidad tg_calcular_alicuota; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_calcular_alicuota AFTER INSERT OR UPDATE OF n_documento, direccion, id_tipo ON public.unidad FOR EACH STATEMENT EXECUTE PROCEDURE public.calcular_alicuota();


--
-- TOC entry 3253 (class 2620 OID 39796)
-- Name: categoriagasto tg_categoria_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_categoria_gasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3269 (class 2620 OID 39797)
-- Name: concepto_gasto tg_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3255 (class 2620 OID 39798)
-- Name: condominio tg_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3271 (class 2620 OID 39799)
-- Name: cuenta tg_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3277 (class 2620 OID 39800)
-- Name: cuenta_pagar tg_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3285 (class 2620 OID 39812)
-- Name: mensaje tg_eliminar_mensaje; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_eliminar_mensaje AFTER UPDATE ON public.mensaje FOR EACH ROW EXECUTE PROCEDURE public.eliminar_mensaje();


--
-- TOC entry 3289 (class 2620 OID 39813)
-- Name: puente_mensaje_usuario tg_eliminar_puente_mensaje; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_eliminar_puente_mensaje AFTER UPDATE ON public.puente_mensaje_usuario FOR EACH ROW EXECUTE PROCEDURE public.eliminar_mensaje();


--
-- TOC entry 3273 (class 2620 OID 39801)
-- Name: fondos tg_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3257 (class 2620 OID 39802)
-- Name: forma_pago tg_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3275 (class 2620 OID 39803)
-- Name: gasto tg_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3259 (class 2620 OID 39804)
-- Name: interes tg_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3248 (class 2620 OID 39773)
-- Name: asambleas tg_mayuscula_asambleas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_asambleas BEFORE INSERT OR UPDATE ON public.asambleas FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_asambleas();


--
-- TOC entry 3250 (class 2620 OID 39774)
-- Name: banco tg_mayuscula_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_banco();


--
-- TOC entry 3252 (class 2620 OID 39775)
-- Name: categoriagasto tg_mayuscula_categoriagasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_categoriagasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_categoriagasto();


--
-- TOC entry 3283 (class 2620 OID 39776)
-- Name: cobro_unidad tg_mayuscula_cobro_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cobro_unidad BEFORE INSERT OR UPDATE ON public.cobro_unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cobro_unidad();


--
-- TOC entry 3268 (class 2620 OID 39777)
-- Name: concepto_gasto tg_mayuscula_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_concepto_gasto();


--
-- TOC entry 3254 (class 2620 OID 39778)
-- Name: condominio tg_mayuscula_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_condominio();


--
-- TOC entry 3270 (class 2620 OID 39779)
-- Name: cuenta tg_mayuscula_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cuenta();


--
-- TOC entry 3276 (class 2620 OID 39780)
-- Name: cuenta_pagar tg_mayuscula_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_cuenta_pagar();


--
-- TOC entry 3288 (class 2620 OID 39781)
-- Name: detalle_pagos tg_mayuscula_detalle_pagos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_detalle_pagos BEFORE INSERT OR UPDATE ON public.detalle_pagos FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_detalle_pagos();


--
-- TOC entry 3272 (class 2620 OID 39782)
-- Name: fondos tg_mayuscula_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_fondos();


--
-- TOC entry 3256 (class 2620 OID 39783)
-- Name: forma_pago tg_mayuscula_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_forma_pago();


--
-- TOC entry 3274 (class 2620 OID 39784)
-- Name: gasto tg_mayuscula_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_gasto();


--
-- TOC entry 3258 (class 2620 OID 39785)
-- Name: interes tg_mayuscula_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_interes();


--
-- TOC entry 3260 (class 2620 OID 39786)
-- Name: persona tg_mayuscula_persona; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_persona BEFORE INSERT OR UPDATE ON public.persona FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_persona();


--
-- TOC entry 3261 (class 2620 OID 39787)
-- Name: proveedores tg_mayuscula_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_proveedores();


--
-- TOC entry 3263 (class 2620 OID 39788)
-- Name: sancion tg_mayuscula_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_sancion();


--
-- TOC entry 3265 (class 2620 OID 39789)
-- Name: tipo_unidad tg_mayuscula_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_tipo_unidad();


--
-- TOC entry 3267 (class 2620 OID 39790)
-- Name: tipo_usuario tg_mayuscula_tipo_usuario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_tipo_usuario BEFORE INSERT OR UPDATE ON public.tipo_usuario FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_tipo_usuario();


--
-- TOC entry 3280 (class 2620 OID 39791)
-- Name: unidad tg_mayuscula_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_unidad();


--
-- TOC entry 3286 (class 2620 OID 39792)
-- Name: visita tg_mayuscula_visita; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_mayuscula_visita BEFORE INSERT OR UPDATE ON public.visita FOR EACH ROW EXECUTE PROCEDURE public.mayuscula_visita();


--
-- TOC entry 3279 (class 2620 OID 39805)
-- Name: propietario tg_propietario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_propietario BEFORE INSERT OR UPDATE ON public.propietario FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3262 (class 2620 OID 39806)
-- Name: proveedores tg_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3284 (class 2620 OID 39807)
-- Name: recibo tg_recibo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_recibo BEFORE INSERT OR UPDATE ON public.recibo FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3278 (class 2620 OID 39814)
-- Name: cuenta_pagar tg_restar_saldo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_restar_saldo AFTER INSERT ON public.cuenta_pagar FOR EACH ROW EXECUTE PROCEDURE public.pagar_gasto();


--
-- TOC entry 3264 (class 2620 OID 39808)
-- Name: sancion tg_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3266 (class 2620 OID 39809)
-- Name: tipo_unidad tg_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3282 (class 2620 OID 39810)
-- Name: unidad tg_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3287 (class 2620 OID 39811)
-- Name: visita tg_visita; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_visita BEFORE INSERT ON public.visita FOR EACH ROW EXECUTE PROCEDURE public.llenar_bitacora();


--
-- TOC entry 3247 (class 2606 OID 39685)
-- Name: bitacora bitacora_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3220 (class 2606 OID 39413)
-- Name: cobro_unidad cobro_unidad_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3221 (class 2606 OID 39418)
-- Name: cobro_unidad cobro_unidad_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3222 (class 2606 OID 39423)
-- Name: cobro_unidad cobro_unidad_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_unidad_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3207 (class 2606 OID 39272)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 3209 (class 2606 OID 39288)
-- Name: cuenta cuenta_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3208 (class 2606 OID 39283)
-- Name: cuenta cuenta_id_banco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_id_banco_fkey FOREIGN KEY (id_banco) REFERENCES public.banco(id);


--
-- TOC entry 3216 (class 2606 OID 39357)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3213 (class 2606 OID 39342)
-- Name: cuenta_pagar cuenta_pagar_id_forma_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_forma_pago_fkey FOREIGN KEY (id_forma_pago) REFERENCES public.forma_pago(id);


--
-- TOC entry 3214 (class 2606 OID 39347)
-- Name: cuenta_pagar cuenta_pagar_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3215 (class 2606 OID 39352)
-- Name: cuenta_pagar cuenta_pagar_n_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_n_cuenta_fkey FOREIGN KEY (n_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3210 (class 2606 OID 39293)
-- Name: cuenta cuenta_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3229 (class 2606 OID 39512)
-- Name: detalle_pagos detalle_pagos_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3230 (class 2606 OID 39517)
-- Name: detalle_pagos detalle_pagos_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3212 (class 2606 OID 39329)
-- Name: gasto gasto_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3211 (class 2606 OID 39324)
-- Name: gasto gasto_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 3206 (class 2606 OID 39211)
-- Name: interes interes_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3226 (class 2606 OID 39476)
-- Name: mensaje mensaje_emisor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_emisor_fkey FOREIGN KEY (emisor) REFERENCES public.usuario(id);


--
-- TOC entry 3217 (class 2606 OID 39368)
-- Name: propietario propietario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3232 (class 2606 OID 39535)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3231 (class 2606 OID 39530)
-- Name: puente_asambleas_propietario puente_asambleas_propietario_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asambleas_propietario_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3234 (class 2606 OID 39556)
-- Name: puente_cobro_factura puente_cobro_factura_id_cobro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_id_cobro_fkey FOREIGN KEY (id_cobro) REFERENCES public.cobro_unidad(id);


--
-- TOC entry 3233 (class 2606 OID 39551)
-- Name: puente_cobro_factura puente_cobro_factura_id_recibo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_id_recibo_fkey FOREIGN KEY (id_recibo) REFERENCES public.recibo(id);


--
-- TOC entry 3236 (class 2606 OID 39574)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_concepto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_concepto_fkey FOREIGN KEY (id_concepto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3235 (class 2606 OID 39569)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3237 (class 2606 OID 39589)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_id_mensaje_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_id_mensaje_fkey FOREIGN KEY (id_mensaje) REFERENCES public.mensaje(id);


--
-- TOC entry 3238 (class 2606 OID 39594)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_receptor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_receptor_fkey FOREIGN KEY (receptor) REFERENCES public.usuario(id);


--
-- TOC entry 3239 (class 2606 OID 39607)
-- Name: puente_persona_condominio puente_persona_condominio_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3240 (class 2606 OID 39612)
-- Name: puente_persona_condominio puente_persona_condominio_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3241 (class 2606 OID 39625)
-- Name: puente_sancion_unidad puente_sancion_unidad_id_sancion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_id_sancion_fkey FOREIGN KEY (id_sancion) REFERENCES public.sancion(id);


--
-- TOC entry 3242 (class 2606 OID 39630)
-- Name: puente_sancion_unidad puente_sancion_unidad_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3246 (class 2606 OID 39668)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_funcion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_funcion_fkey FOREIGN KEY (id_funcion) REFERENCES public.funcion(id);


--
-- TOC entry 3245 (class 2606 OID 39663)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3243 (class 2606 OID 39645)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3244 (class 2606 OID 39650)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3223 (class 2606 OID 39439)
-- Name: recibo recibo_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT recibo_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3218 (class 2606 OID 39379)
-- Name: responsable responsable_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3219 (class 2606 OID 39397)
-- Name: unidad unidad_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_unidad(id);


--
-- TOC entry 3224 (class 2606 OID 39455)
-- Name: usuario usuario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3225 (class 2606 OID 39460)
-- Name: usuario usuario_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3227 (class 2606 OID 39491)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.persona(cedula);


--
-- TOC entry 3228 (class 2606 OID 39496)
-- Name: visita visita_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


-- Completed on 2020-07-28 18:14:23

--
-- PostgreSQL database dump complete
--

