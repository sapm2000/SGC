--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-06-11 10:38:38

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
-- TOC entry 297 (class 1255 OID 39131)
-- Name: actualizar_status(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.actualizar_status(id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.actualizar_status(id2 integer) OWNER TO postgres;

--
-- TOC entry 298 (class 1255 OID 39132)
-- Name: actualizar_status_cuotas(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.actualizar_status_cuotas(id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
	saldo2 int;
BEGIN
saldo2 := (SELECT saldo FROM cuotas_especiales WHERE id = id2);

IF saldo2 = 0 THEN
	UPDATE cuotas_especiales SET pagado = 'Pagado' WHERE id = id2;
END IF;
END;
$$;


ALTER FUNCTION public.actualizar_status_cuotas(id2 integer) OWNER TO postgres;

--
-- TOC entry 299 (class 1255 OID 39133)
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
-- TOC entry 300 (class 1255 OID 39134)
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
-- TOC entry 301 (class 1255 OID 39135)
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
-- TOC entry 302 (class 1255 OID 39136)
-- Name: registrar_unidad(character varying, character varying, double precision, character varying[], character varying[], integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_unidad(numero2 character varying, direccion2 character varying, area2 double precision, cedula2 character varying[], documento2 character varying[], id_condominio integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	cedula_bd character varying(10);
	id_unidad_bd int;
BEGIN
	INSERT INTO unidades(n_unidad, direccion, area, id_condominio) VALUES (numero2, direccion2, area2, documento2);
	
	FOR i IN 1 .. array_length(cedula2, 1) LOOP
		cedula_bd := (SELECT cedula FROM propietario WHERE cedula = cedula2[i]);
		id_unidad_bd := (SELECT max(id) FROM unidad);
		INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, documento) VALUES (cedula_bd, id_unidad_bd, documento2);
	END LOOP;
END;
$$;


ALTER FUNCTION public.registrar_unidad(numero2 character varying, direccion2 character varying, area2 double precision, cedula2 character varying[], documento2 character varying[], id_condominio integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 39137)
-- Name: asambleas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asambleas (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion text NOT NULL
);


ALTER TABLE public.asambleas OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 39143)
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
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 204 (class 1259 OID 39145)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(100) NOT NULL,
    activo boolean
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 39148)
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
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 206 (class 1259 OID 39150)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 39154)
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
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 208 (class 1259 OID 39156)
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
    id_unidad bigint
);


ALTER TABLE public.cobro_unidad OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 39162)
-- Name: cobro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cobro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cobro_id_seq OWNER TO postgres;

--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 209
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 210 (class 1259 OID 39164)
-- Name: comunicados; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comunicados (
    id integer NOT NULL,
    asunto character varying(300) NOT NULL,
    mensaje character varying(3000) NOT NULL,
    id_condominio character varying(15) NOT NULL
);


ALTER TABLE public.comunicados OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 39170)
-- Name: comunicados_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comunicados_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comunicados_id_seq OWNER TO postgres;

--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 211
-- Name: comunicados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id;


--
-- TOC entry 212 (class 1259 OID 39172)
-- Name: concepto_gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gasto (
    id integer NOT NULL,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.concepto_gasto OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 39176)
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
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 213
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 214 (class 1259 OID 39178)
-- Name: condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.condominio (
    rif character varying(15) NOT NULL,
    razon_social character varying(150) NOT NULL,
    telefono character varying(11) NOT NULL,
    correo_electronico character varying(70) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.condominio OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 39182)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    n_cuenta character varying(20) NOT NULL,
    tipo character varying(10) NOT NULL,
    id_banco bigint NOT NULL,
    ci_persona character varying(10) NOT NULL,
    rif_condominio character varying(15) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 39186)
-- Name: cuenta_pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta_pagar (
    id integer NOT NULL,
    num_ref character varying(10) NOT NULL,
    forma_pago character varying(25) NOT NULL,
    descripcion character varying(60) NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    id_proveedor character varying(10) NOT NULL,
    id_cuenta character varying(20) NOT NULL,
    id_fondo integer NOT NULL
);


ALTER TABLE public.cuenta_pagar OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 39189)
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
-- TOC entry 3417 (class 0 OID 0)
-- Dependencies: 217
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 218 (class 1259 OID 39191)
-- Name: detalle_pagos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_pagos (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto_dolar double precision NOT NULL,
    id_gasto bigint,
    id_unidad bigint,
    tipo_gasto character varying,
    monto_bolivar double precision,
    paridad double precision,
    moneda_dominante character varying
);


ALTER TABLE public.detalle_pagos OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 39197)
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
-- TOC entry 3418 (class 0 OID 0)
-- Dependencies: 219
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 220 (class 1259 OID 39199)
-- Name: fondos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fondos (
    tipo character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion character varying(200) NOT NULL,
    observaciones character varying(200),
    monto_inicial double precision NOT NULL,
    saldo double precision NOT NULL,
    id_condominio character varying(15) NOT NULL,
    id integer NOT NULL,
    moneda character varying,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.fondos OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 39206)
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
-- TOC entry 3419 (class 0 OID 0)
-- Dependencies: 221
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 222 (class 1259 OID 39208)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pago (
    id integer NOT NULL,
    forma_pago character varying NOT NULL,
    activo boolean
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 39214)
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
-- TOC entry 3420 (class 0 OID 0)
-- Dependencies: 223
-- Name: forma_pago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pago_id_seq OWNED BY public.forma_pago.id;


--
-- TOC entry 224 (class 1259 OID 39216)
-- Name: funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcion (
    id integer NOT NULL,
    funcion character varying NOT NULL
);


ALTER TABLE public.funcion OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 39222)
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
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 225
-- Name: funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcion_id_seq OWNED BY public.funcion.id;


--
-- TOC entry 226 (class 1259 OID 39224)
-- Name: gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gasto (
    id integer NOT NULL,
    tipo character varying(20) NOT NULL,
    id_proveedor character varying(15) NOT NULL,
    calcular_por character varying(20) NOT NULL,
    mes integer NOT NULL,
    anio integer NOT NULL,
    n_meses integer NOT NULL,
    id_asamblea integer,
    observacion text,
    meses_restantes integer NOT NULL,
    monto double precision NOT NULL,
    saldo double precision NOT NULL,
    estado character varying(30) DEFAULT 'Pendiente'::character varying NOT NULL,
    pagado character varying(9) DEFAULT 'Pendiente'::character varying NOT NULL,
    moneda character varying
);


ALTER TABLE public.gasto OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 39232)
-- Name: gasto_comun; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gasto_comun (
    id integer NOT NULL,
    tipo character varying(50) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    n_factura character varying(50) NOT NULL,
    id_proveedor character varying(15) NOT NULL,
    id_concepto bigint NOT NULL,
    observaciones character varying(200),
    fecha date NOT NULL,
    estado character varying(20) NOT NULL,
    id_condominio character varying(15),
    saldo double precision
);


ALTER TABLE public.gasto_comun OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 39235)
-- Name: gasto_comun_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.gasto_comun_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gasto_comun_id_seq OWNER TO postgres;

--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 228
-- Name: gasto_comun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_comun_id_seq OWNED BY public.gasto_comun.id;


--
-- TOC entry 229 (class 1259 OID 39237)
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
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 229
-- Name: gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_id_seq OWNED BY public.gasto.id;


--
-- TOC entry 230 (class 1259 OID 39239)
-- Name: interes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interes (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    factor double precision NOT NULL,
    activo boolean
);


ALTER TABLE public.interes OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 39242)
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
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 231
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 232 (class 1259 OID 39244)
-- Name: mensaje; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mensaje (
    id integer NOT NULL,
    asunto character varying(60) NOT NULL,
    contenido character varying(420) NOT NULL,
    estado boolean DEFAULT false NOT NULL,
    emisor integer
);


ALTER TABLE public.mensaje OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 39248)
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
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 233
-- Name: mensaje_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mensaje_id_seq OWNED BY public.mensaje.id;


--
-- TOC entry 234 (class 1259 OID 39250)
-- Name: pagar_cuota_especial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagar_cuota_especial (
    id integer NOT NULL,
    numero_ref character varying(10) NOT NULL,
    forma_pago character varying(30) NOT NULL,
    descripcion character varying(200) NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    id_cuenta character varying NOT NULL,
    id_fondo integer NOT NULL,
    id_cuota_e integer NOT NULL
);


ALTER TABLE public.pagar_cuota_especial OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 39256)
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagar_cuota_especial_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagar_cuota_especial_id_seq OWNER TO postgres;

--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 235
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagar_cuota_especial_id_seq OWNED BY public.pagar_cuota_especial.id;


--
-- TOC entry 236 (class 1259 OID 39258)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    cedula character varying(10) NOT NULL,
    p_nombre character varying(25) NOT NULL,
    s_nombre character varying(25) DEFAULT ''::character varying,
    p_apellido character varying(25) NOT NULL,
    s_apellido character varying(25) DEFAULT ''::character varying,
    telefono character varying(12),
    correo character varying(60),
    activo boolean DEFAULT true
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 39264)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 39268)
-- Name: proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proveedores (
    cedula character varying(15) NOT NULL,
    nombre character varying(40) NOT NULL,
    telefono character varying(15) NOT NULL,
    correo character varying(40) NOT NULL,
    contacto character varying(60) NOT NULL,
    direccion character varying(500) NOT NULL,
    activo bigint
);


ALTER TABLE public.proveedores OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 39274)
-- Name: puente_asambleas_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asambleas_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    ci_propietario character varying(10) NOT NULL
);


ALTER TABLE public.puente_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 39277)
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_asamblea_propietario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_asamblea_propietario_id_seq OWNER TO postgres;

--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 240
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asambleas_propietario.id;


--
-- TOC entry 241 (class 1259 OID 39279)
-- Name: puente_cobro_factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_cobro_factura (
    id integer NOT NULL,
    id_total bigint NOT NULL,
    id_cobro bigint NOT NULL,
    parte_monto double precision NOT NULL
);


ALTER TABLE public.puente_cobro_factura OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 39282)
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
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 243 (class 1259 OID 39284)
-- Name: puente_comunicado_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_comunicado_usuario (
    id integer NOT NULL,
    id_usuario character varying NOT NULL,
    id_comunicado bigint NOT NULL,
    leido bigint
);


ALTER TABLE public.puente_comunicado_usuario OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 39290)
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_comunicado_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_comunicado_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 245 (class 1259 OID 39292)
-- Name: puente_concepto_factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_concepto_factura (
    id integer NOT NULL,
    id_factura_proveedor bigint NOT NULL,
    id_concepto bigint NOT NULL,
    monto double precision NOT NULL
);


ALTER TABLE public.puente_concepto_factura OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 39295)
-- Name: puente_concepto_factura_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_concepto_factura_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_concepto_factura_id_seq OWNER TO postgres;

--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_concepto_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_concepto_factura_id_seq OWNED BY public.puente_concepto_factura.id;


--
-- TOC entry 247 (class 1259 OID 39297)
-- Name: puente_gasto_concepto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_gasto_concepto (
    id integer NOT NULL,
    id_gasto integer NOT NULL,
    id_concepto integer NOT NULL,
    monto double precision NOT NULL
);


ALTER TABLE public.puente_gasto_concepto OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 39300)
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
-- TOC entry 3431 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_gasto_concepto_id_seq OWNED BY public.puente_gasto_concepto.id;


--
-- TOC entry 249 (class 1259 OID 39302)
-- Name: puente_mensaje_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_mensaje_usuario (
    id integer NOT NULL,
    id_mensaje integer NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.puente_mensaje_usuario OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 39305)
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
-- TOC entry 3432 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_mensaje_usuario_id_seq OWNED BY public.puente_mensaje_usuario.id;


--
-- TOC entry 251 (class 1259 OID 39307)
-- Name: puente_persona_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_persona_condominio (
    id integer NOT NULL,
    ci_persona character varying(10) NOT NULL,
    rif_condominio character varying(15) NOT NULL
);


ALTER TABLE public.puente_persona_condominio OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 39310)
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
-- TOC entry 3433 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_persona_condominio_id_seq OWNED BY public.puente_persona_condominio.id;


--
-- TOC entry 253 (class 1259 OID 39312)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 39315)
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_sancion_unidad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_sancion_unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3434 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 255 (class 1259 OID 39317)
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
-- TOC entry 256 (class 1259 OID 39320)
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
-- TOC entry 3435 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_tipo_funcion_id_seq OWNED BY public.puente_tipo_funcion.id;


--
-- TOC entry 257 (class 1259 OID 39322)
-- Name: puente_unidad_propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_unidad_propietarios (
    id integer NOT NULL,
    ci_propietario character varying(15) NOT NULL,
    id_unidad bigint NOT NULL,
    fecha_desde date DEFAULT LOCALTIMESTAMP(0) NOT NULL,
    fecha_hasta date,
    estado bigint NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.puente_unidad_propietarios OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 39327)
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
-- TOC entry 3436 (class 0 OID 0)
-- Dependencies: 258
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 259 (class 1259 OID 39329)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 39333)
-- Name: sancion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sancion (
    id integer NOT NULL,
    tipo character varying(40) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(200) NOT NULL,
    estado character varying(20),
    moneda character varying(10)
);


ALTER TABLE public.sancion OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 39336)
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
-- TOC entry 3437 (class 0 OID 0)
-- Dependencies: 261
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 262 (class 1259 OID 39338)
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
-- TOC entry 263 (class 1259 OID 39342)
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
-- TOC entry 3438 (class 0 OID 0)
-- Dependencies: 263
-- Name: tipo_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_unidad_id_seq OWNED BY public.tipo_unidad.id;


--
-- TOC entry 264 (class 1259 OID 39344)
-- Name: tipo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_usuario (
    id integer NOT NULL,
    tipo character varying NOT NULL
);


ALTER TABLE public.tipo_usuario OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 39350)
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
-- TOC entry 3439 (class 0 OID 0)
-- Dependencies: 265
-- Name: tipo_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_usuario_id_seq OWNED BY public.tipo_usuario.id;


--
-- TOC entry 266 (class 1259 OID 39352)
-- Name: unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidad (
    id integer NOT NULL,
    n_unidad character varying(10) NOT NULL,
    n_documento character varying(15) NOT NULL,
    direccion character varying(200) NOT NULL,
    activo boolean DEFAULT true,
    id_tipo integer NOT NULL,
    alicuota double precision
);


ALTER TABLE public.unidad OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 39356)
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
-- TOC entry 3440 (class 0 OID 0)
-- Dependencies: 267
-- Name: unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidad_id_seq OWNED BY public.unidad.id;


--
-- TOC entry 268 (class 1259 OID 39358)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    usuario character varying(25) NOT NULL,
    password character varying(32) NOT NULL,
    pregunta character varying(120) NOT NULL,
    respuesta character varying(120) NOT NULL,
    ci_persona character varying(10),
    id_tipo_usuario integer,
    activo boolean DEFAULT true
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 39362)
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
-- TOC entry 3441 (class 0 OID 0)
-- Dependencies: 269
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 270 (class 1259 OID 39364)
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
-- TOC entry 271 (class 1259 OID 39368)
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
-- TOC entry 272 (class 1259 OID 39372)
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
-- TOC entry 273 (class 1259 OID 39376)
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
-- TOC entry 274 (class 1259 OID 39380)
-- Name: v_condominio; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_condominio AS
 SELECT condominio.rif,
    condominio.razon_social,
    condominio.telefono,
    condominio.correo_electronico AS correo
   FROM public.condominio
  WHERE (condominio.activo = true);


ALTER TABLE public.v_condominio OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 39384)
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
    cue.activo
   FROM ((public.cuenta cue
     JOIN public.banco ban ON ((ban.id = cue.id_banco)))
     JOIN public.persona per ON (((per.cedula)::text = (cue.ci_persona)::text)))
  WHERE (cue.activo = true);


ALTER TABLE public.v_cuenta OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 39389)
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
-- TOC entry 277 (class 1259 OID 39394)
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
-- TOC entry 278 (class 1259 OID 39398)
-- Name: v_gasto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_gasto AS
 SELECT ga.id,
    ga.tipo,
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
-- TOC entry 279 (class 1259 OID 39403)
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
-- TOC entry 280 (class 1259 OID 39408)
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
-- TOC entry 281 (class 1259 OID 39413)
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
-- TOC entry 282 (class 1259 OID 39418)
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
-- TOC entry 283 (class 1259 OID 39422)
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
-- TOC entry 284 (class 1259 OID 39426)
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
-- TOC entry 285 (class 1259 OID 39430)
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
-- TOC entry 286 (class 1259 OID 39434)
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
-- TOC entry 287 (class 1259 OID 39438)
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
-- TOC entry 288 (class 1259 OID 39442)
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
-- TOC entry 289 (class 1259 OID 39447)
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
-- TOC entry 290 (class 1259 OID 39451)
-- Name: v_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_usuario AS
 SELECT usuario.id,
    usuario.usuario,
    usuario.ci_persona
   FROM public.usuario
  WHERE (usuario.activo = true);


ALTER TABLE public.v_usuario OWNER TO postgres;

--
-- TOC entry 291 (class 1259 OID 39455)
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
-- TOC entry 292 (class 1259 OID 39459)
-- Name: visita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visita (
    id integer NOT NULL,
    fecha date DEFAULT LOCALTIMESTAMP(0),
    hora time without time zone DEFAULT LOCALTIMESTAMP(0),
    placa character varying(8),
    modelo character varying(25),
    color character varying(15),
    ci_visitante character varying(10) NOT NULL,
    id_unidad integer NOT NULL
);


ALTER TABLE public.visita OWNER TO postgres;

--
-- TOC entry 293 (class 1259 OID 39464)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 39468)
-- Name: v_visita; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_visita AS
 SELECT vis.id,
    vis.id_unidad,
    u.n_unidad,
    vis.fecha,
    vis.hora,
    vis.placa AS matricula,
    vis.modelo,
    vis.color,
    vis.ci_visitante AS cedula,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido
   FROM (((public.visita vis
     JOIN public.unidad u ON ((u.id = vis.id_unidad)))
     JOIN public.visitante v ON (((v.ci_persona)::text = (vis.ci_visitante)::text)))
     JOIN public.persona per ON (((per.cedula)::text = (v.ci_persona)::text)));


ALTER TABLE public.v_visita OWNER TO postgres;

--
-- TOC entry 295 (class 1259 OID 39473)
-- Name: v_visitante; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_visitante AS
 SELECT v.ci_persona AS cedula,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido
   FROM (public.visitante v
     JOIN public.persona per ON (((per.cedula)::text = (v.ci_persona)::text)))
  WHERE (v.activo = true);


ALTER TABLE public.v_visitante OWNER TO postgres;

--
-- TOC entry 296 (class 1259 OID 39477)
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
-- TOC entry 3442 (class 0 OID 0)
-- Dependencies: 296
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 3015 (class 2604 OID 39479)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 3016 (class 2604 OID 39480)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 3018 (class 2604 OID 39481)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 3019 (class 2604 OID 39482)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 3020 (class 2604 OID 39483)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 3022 (class 2604 OID 39484)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 3025 (class 2604 OID 39485)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 3026 (class 2604 OID 39486)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 3028 (class 2604 OID 39487)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 3029 (class 2604 OID 39488)
-- Name: forma_pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago ALTER COLUMN id SET DEFAULT nextval('public.forma_pago_id_seq'::regclass);


--
-- TOC entry 3030 (class 2604 OID 39489)
-- Name: funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion ALTER COLUMN id SET DEFAULT nextval('public.funcion_id_seq'::regclass);


--
-- TOC entry 3033 (class 2604 OID 39490)
-- Name: gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto ALTER COLUMN id SET DEFAULT nextval('public.gasto_id_seq'::regclass);


--
-- TOC entry 3034 (class 2604 OID 39491)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 3035 (class 2604 OID 39492)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 3037 (class 2604 OID 39493)
-- Name: mensaje id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje ALTER COLUMN id SET DEFAULT nextval('public.mensaje_id_seq'::regclass);


--
-- TOC entry 3038 (class 2604 OID 39494)
-- Name: pagar_cuota_especial id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial ALTER COLUMN id SET DEFAULT nextval('public.pagar_cuota_especial_id_seq'::regclass);


--
-- TOC entry 3043 (class 2604 OID 39495)
-- Name: puente_asambleas_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 3044 (class 2604 OID 39496)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 3045 (class 2604 OID 39497)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 3046 (class 2604 OID 39498)
-- Name: puente_concepto_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_concepto_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_concepto_factura_id_seq'::regclass);


--
-- TOC entry 3047 (class 2604 OID 39499)
-- Name: puente_gasto_concepto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto ALTER COLUMN id SET DEFAULT nextval('public.puente_gasto_concepto_id_seq'::regclass);


--
-- TOC entry 3048 (class 2604 OID 39500)
-- Name: puente_mensaje_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_mensaje_usuario_id_seq'::regclass);


--
-- TOC entry 3049 (class 2604 OID 39501)
-- Name: puente_persona_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_persona_condominio_id_seq'::regclass);


--
-- TOC entry 3050 (class 2604 OID 39502)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 3051 (class 2604 OID 39503)
-- Name: puente_tipo_funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion ALTER COLUMN id SET DEFAULT nextval('public.puente_tipo_funcion_id_seq'::regclass);


--
-- TOC entry 3054 (class 2604 OID 39504)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 3056 (class 2604 OID 39505)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3058 (class 2604 OID 39506)
-- Name: tipo_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad ALTER COLUMN id SET DEFAULT nextval('public.tipo_unidad_id_seq'::regclass);


--
-- TOC entry 3059 (class 2604 OID 39507)
-- Name: tipo_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario ALTER COLUMN id SET DEFAULT nextval('public.tipo_usuario_id_seq'::regclass);


--
-- TOC entry 3061 (class 2604 OID 39508)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 3063 (class 2604 OID 39509)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3066 (class 2604 OID 39510)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3335 (class 0 OID 39137)
-- Dependencies: 202
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.asambleas (id, nombre, fecha, descripcion) FROM stdin;
82	jdfgfg	1969-12-31	sfsdfsd
83	fgjh	1969-12-31	fghfgh
84	kñk	1969-12-31	kñklñ
85	ghjf	1969-12-31	fghfgh
\.


--
-- TOC entry 3337 (class 0 OID 39145)
-- Dependencies: 204
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.banco (id, nombre_banco, activo) FROM stdin;
4	Provincial	t
6	Venezuela	t
1	Banesco	t
5	Mercantil	t
7	BOD	t
8	Fondo Comun	t
10	Fuerzas Armadas	t
11	Venezolana de Credito	t
9	Banplus	t
3	Caribe	t
\.


--
-- TOC entry 3339 (class 0 OID 39150)
-- Dependencies: 206
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoriagasto (id, nombre, descripcion, activo) FROM stdin;
4	Uso comun	Conjunto de conceptos de uso comun y consumo	t
6	asda	ddasda	t
5	fsfsfdsfs	Conjunto de conceptos administrativo	t
7	baba	baba	t
\.


--
-- TOC entry 3341 (class 0 OID 39156)
-- Dependencies: 208
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cobro_unidad (id, monto, descripcion, id_cuenta, forma_pago, referencia, fecha, id_fondo, id_unidad) FROM stdin;
11	12500	asdas	01020045120268985654	Transferencia	asdsad	2020-05-09	55	66
12	10000	fdf	01020045120268985654	Transferencia	12313	2020-05-09	55	66
\.


--
-- TOC entry 3343 (class 0 OID 39164)
-- Dependencies: 210
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comunicados (id, asunto, mensaje, id_condominio) FROM stdin;
\.


--
-- TOC entry 3345 (class 0 OID 39172)
-- Dependencies: 212
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.concepto_gasto (id, nom_concepto, descripcion, id_categoria, activo) FROM stdin;
40	HidroCapital	Consumo de agua	4	t
41	Vigilancia	Gastos por vigilancia	4	t
39	Cantv	sad	4	t
11	Provisión utilidades 	Utilidades del trabajador	5	t
10	Liquidación 	Liquidacion trabajador	5	t
12	Provisión vacaciones 	Vacaciones trabajador	5	t
13	Ley de alimentación	Pago alimentación	5	t
14	FAHO	Fondo de ahorro obligatorio de la vivienda	5	t
15	Caja chica	Caja chica	5	t
3	Honorarios administradora	Honorarios administradora del condominio	5	t
4	Gastos oficina	Gastos en material de oficina	5	t
9	Bono Vacacional 	Bono vacacional trabajador	5	t
7	Prestaciones soc.	Prestaciones sociales trabajador	5	t
8	Seguro social	Seguro social trabajador	5	t
5	Gastos varios	Gastos fotocopias, impresiones, internet	5	t
6	Retiro prestaciones	Retiro de prestaciones sociales	5	t
16	Mant. ascensores	Mantenimiento de los asecensores	6	t
17	Mant. hidroneumatico	Mantenimiento del hidroneumatico	6	t
18	Mant. porton electrico	Mantenimiento del porton electrico	6	t
19	Mant. cerco electrico	Mantenimiento del cerco electrico	6	t
20	Mant. areas verdes	Mantenimiento de areas verdes	6	t
21	Mant. piscina	Mantenimiento de la piscina	6	t
22	Mant. parque infantil	Mantenimiento del parque infantil	6	t
23	Mant. camaras vigilancia	Mantenimiento camaras de vigilancia	6	t
24	Mant. tuberias	Mantenimiento de tuberias 	6	t
25	Mant. estacionamiento	Mantenimiento del estacionamiento 	6	t
26	Mant. pintura edif. 	Pintura de areas comunes 	6	t
27	Mant. alumbrado elect. 	Mantenimiento del alumbrado elect.	6	t
28	Mant. compra prod. limp.	Gastos productos de limpieza	6	t
29	Mant. prod. limpieza 	Gasto otros productos	6	t
30	Repar. ascensores	Reparacion de los asecensores	6	t
31	Repar. tuberia	Reparacion de tuberias de aguas	6	t
32	Repar. porton	Reparacion del porton electrico	6	t
33	Repar. cerco elect.	Reparacion del cerco electrico	6	t
34	Repar. hidroneumatico.	Reparacion la bomba de agua	6	t
35	Repar. camaras.	Reparacion de camaras	6	t
36	Repar. electricas.	Reparacion/Sustitucion de componentes electricos	6	t
37	Consumo de electricidad	Consumo de electricidad	6	t
38	jkkjkjk	jkjkjkjkjk	4	t
42	qw	qw	4	t
43	baba	baba	7	t
44	bobo	bobo	7	t
\.


--
-- TOC entry 3347 (class 0 OID 39178)
-- Dependencies: 214
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.condominio (rif, razon_social, telefono, correo_electronico, activo) FROM stdin;
J22318939	CAMPO GUATACARO	04140542620	AJHENSUAREZ@GMAIL.COM	t
\.


--
-- TOC entry 3348 (class 0 OID 39182)
-- Dependencies: 215
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuenta (n_cuenta, tipo, id_banco, ci_persona, rif_condominio, activo) FROM stdin;
44444444444444444444	Corriente	5	V-11276626	J22318939	t
\.


--
-- TOC entry 3349 (class 0 OID 39186)
-- Dependencies: 216
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuenta_pagar (id, num_ref, forma_pago, descripcion, monto, fecha, id_proveedor, id_cuenta, id_fondo) FROM stdin;
\.


--
-- TOC entry 3351 (class 0 OID 39191)
-- Dependencies: 218
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.detalle_pagos (id, mes, anio, monto_dolar, id_gasto, id_unidad, tipo_gasto, monto_bolivar, paridad, moneda_dominante) FROM stdin;
\.


--
-- TOC entry 3353 (class 0 OID 39199)
-- Dependencies: 220
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fondos (tipo, fecha, descripcion, observaciones, monto_inicial, saldo, id_condominio, id, moneda, activo) FROM stdin;
asdads	2020-05-09	adsasd	asda	31231	31231	J22318939	56	Bolívar	t
asdad	2020-05-16	dadsad	sadas	21313	0	J22318939	55	Bolívar	t
asdsad	2020-05-10	fsf	sfdsd	0	0	J22318939	57	Bolívar	t
casa blanca	2020-06-08	puto	11111\t\t\t\t	10000	10000	J22318939	59	Bolívar	t
sdadsad	2020-05-16	fsdfds	sdfsd	1	1	J22318939	58	Dólar	t
\.


--
-- TOC entry 3355 (class 0 OID 39208)
-- Dependencies: 222
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.forma_pago (id, forma_pago, activo) FROM stdin;
1	dsfsdf	t
2	td	t
\.


--
-- TOC entry 3357 (class 0 OID 39216)
-- Dependencies: 224
-- Data for Name: funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.funcion (id, funcion) FROM stdin;
1	Asambleas
2	Banco
3	Categoria Gastos
4	Concepto Gastos
5	Comunicados
6	Condominio
7	Cuenta
8	Cuentas por cobrar
9	Cuentas por pagar
10	Fondo
11	Gasto
12	Generar recibo
13	Gestionar Usuario
14	Intereses
15	Pago de cuotas especiales
16	Propietarios
17	Proveedores
18	Recibo
19	Responsables
20	Sanciones
21	Tipo de unidad
22	Tipo de usuario
23	Unidades
24	Registro de visitas
25	Visitas autorizadas
26	Cuotas especiales\n
27	Cuotas especiales
\.


--
-- TOC entry 3359 (class 0 OID 39224)
-- Dependencies: 226
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gasto (id, tipo, id_proveedor, calcular_por, mes, anio, n_meses, id_asamblea, observacion, meses_restantes, monto, saldo, estado, pagado, moneda) FROM stdin;
\.


--
-- TOC entry 3360 (class 0 OID 39232)
-- Dependencies: 227
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gasto_comun (id, tipo, mes, anio, monto, n_factura, id_proveedor, id_concepto, observaciones, fecha, estado, id_condominio, saldo) FROM stdin;
79	Ordinario	5	2020	2000	2727722	24666587	25		2020-05-14	Pendiente de Pago	21321312	2000
80	Ordinario	5	2020	5000	2727722	J-54785696	26		2020-05-14	Pendiente de Pago	21321312	5000
81	Ordinario	6	2020	100	1212	J-1001245215	29	perro	2020-05-12	Pendiente	21321312	100
\.


--
-- TOC entry 3363 (class 0 OID 39239)
-- Dependencies: 230
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.interes (id, nombre, factor, activo) FROM stdin;
26	reserva	30	t
27	inflacion	30	t
\.


--
-- TOC entry 3365 (class 0 OID 39244)
-- Dependencies: 232
-- Data for Name: mensaje; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mensaje (id, asunto, contenido, estado, emisor) FROM stdin;
\.


--
-- TOC entry 3367 (class 0 OID 39250)
-- Dependencies: 234
-- Data for Name: pagar_cuota_especial; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pagar_cuota_especial (id, numero_ref, forma_pago, descripcion, monto, fecha, id_cuenta, id_fondo, id_cuota_e) FROM stdin;
\.


--
-- TOC entry 3369 (class 0 OID 39258)
-- Dependencies: 236
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona (cedula, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo, activo) FROM stdin;
V-26942316	Diego		Rodríguez		04120520962	diegordgz8@outlook.com	t
V-11276626	Dana		Montilla		99999999999	77777	t
V-22318939	anthony	jhen	suarez	leal	04145371749	ajhen	t
V-22318938	jhosbert	angelys	suarez	rodriguez	04145371744	jhos	t
V-7552887	hilda	carmen	suarez	rodriguez	04141414141	sdadasd	t
V-9602345	jenny	coromoto	leal	gil	31231455423	asdadadd	t
V-9602344	yaneth	margarita	leal	leal	96855242224	dsdadgggccc	t
V-16111353	johil	aisbel	suarez	rodriguez	14257595854	johil	t
V-23654789	dsfsdf	\N	sdf	\N	\N	\N	t
V-14523698	cghc	\N	ghcg	\N	\N	\N	t
V-12365478	vhn	\N	gv	\N	\N	\N	t
V-32547896	hgjgh	ghfgh	fgjhf	fhfgh	66666666666	fhh	t
\.


--
-- TOC entry 3370 (class 0 OID 39264)
-- Dependencies: 237
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.propietario (ci_persona, activo) FROM stdin;
V-7552887	t
V-9602345	t
V-9602344	t
V-26942316	t
\.


--
-- TOC entry 3371 (class 0 OID 39268)
-- Dependencies: 238
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.proveedores (cedula, nombre, telefono, correo, contacto, direccion, activo) FROM stdin;
J-1001245215	Ferreteria Todo	02542458796	Ferreteriatodo@gmail.com	Señor José	Av. 4, centro comercial aris, local 3	1
J-2457021456	Carpinteria Madera Blanca	0254217965	CarpinteriaMB@gmail.com	Señora Luisa	8 Av, esquina de la calle 12	1
J-2457903215	Cristaleria Azul	0254789635	Cristaleria_Azul@hotmail.com	Señor Armando	Calle 3 con esquina 7	1
24666587	Hermanos Fontaneros	04245886335	Hfontaneros@gmail.com	Señor Juan	30 Av. entre calle 3 y 4  	1
17102635	Servicios de Limpieza	04165048963	Luisa1@hotmail.com	Señora Luisa	-	1
J-54785696	Libreria la Rosa	04125789654	Libreria_laRosa@hotmail.com	Juan	Calle 2	1
12457896	Reparacion de Ascensor	0414157896	-	Julio	Calle 6	1
J-215479658	Reparacion Electrica	04141544400	-	Alvaro	Calle 14	1
20111045	Reparacion de Porton	04167247578	-	Julian	Calle 12	1
J-547859655	Reparacion de Camaras	04162488965	RC@hotmail.com	Jose	Calle 34	1
\.


--
-- TOC entry 3372 (class 0 OID 39274)
-- Dependencies: 239
-- Data for Name: puente_asambleas_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_asambleas_propietario (id, id_asamblea, ci_propietario) FROM stdin;
259	82	V-7552887
260	82	V-9602345
261	83	V-26942316
262	83	V-9602344
263	84	V-7552887
264	84	V-9602345
265	84	V-9602344
266	85	V-26942316
267	85	V-7552887
268	85	V-9602345
\.


--
-- TOC entry 3374 (class 0 OID 39279)
-- Dependencies: 241
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_cobro_factura (id, id_total, id_cobro, parte_monto) FROM stdin;
8	157	11	12500
9	157	12	10000
\.


--
-- TOC entry 3376 (class 0 OID 39284)
-- Dependencies: 243
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_comunicado_usuario (id, id_usuario, id_comunicado, leido) FROM stdin;
\.


--
-- TOC entry 3378 (class 0 OID 39292)
-- Dependencies: 245
-- Data for Name: puente_concepto_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_concepto_factura (id, id_factura_proveedor, id_concepto, monto) FROM stdin;
1	81	29	1223
2	81	28	100
\.


--
-- TOC entry 3380 (class 0 OID 39297)
-- Dependencies: 247
-- Data for Name: puente_gasto_concepto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_gasto_concepto (id, id_gasto, id_concepto, monto) FROM stdin;
44	13	41	100
46	14	40	1000
61	15	40	10
63	15	39	30
73	15	41	20
74	17	40	100
75	16	40	50
\.


--
-- TOC entry 3382 (class 0 OID 39302)
-- Dependencies: 249
-- Data for Name: puente_mensaje_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_mensaje_usuario (id, id_mensaje, id_usuario) FROM stdin;
\.


--
-- TOC entry 3384 (class 0 OID 39307)
-- Dependencies: 251
-- Data for Name: puente_persona_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_persona_condominio (id, ci_persona, rif_condominio) FROM stdin;
\.


--
-- TOC entry 3386 (class 0 OID 39312)
-- Dependencies: 253
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_sancion_unidad (id, id_sancion, id_unidad) FROM stdin;
208	90	5
209	89	2
210	91	5
211	91	2
212	91	9
\.


--
-- TOC entry 3388 (class 0 OID 39317)
-- Dependencies: 255
-- Data for Name: puente_tipo_funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_tipo_funcion (id, id_tipo, id_funcion, registrar, modificar, eliminar, todo) FROM stdin;
39	1	1	t	t	t	t
40	1	2	t	t	t	t
41	1	3	t	t	t	t
42	1	4	t	t	t	t
43	1	5	t	t	t	t
44	1	6	t	t	t	t
45	1	7	t	t	t	t
46	1	8	t	t	t	t
47	1	9	t	t	t	t
48	1	10	t	t	t	t
49	1	11	t	t	t	t
50	1	12	t	t	t	t
51	1	13	t	t	t	t
52	1	14	t	t	t	t
53	1	15	t	t	t	t
54	1	16	t	t	t	t
55	1	17	t	t	t	t
56	1	18	t	t	t	t
57	1	19	t	t	t	t
58	1	20	t	t	t	t
59	1	21	t	t	t	t
60	1	22	t	t	t	t
61	1	23	t	t	t	t
62	1	24	t	t	t	t
63	1	25	t	t	t	t
64	1	26	t	t	t	t
65	1	27	t	t	t	t
\.


--
-- TOC entry 3390 (class 0 OID 39322)
-- Dependencies: 257
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_unidad_propietarios (id, ci_propietario, id_unidad, fecha_desde, fecha_hasta, estado, activo) FROM stdin;
1	V-9602345	1	2020-05-31	\N	1	t
3	V-7552887	2	2020-05-31	\N	1	t
4	V-9602345	2	2020-05-31	\N	1	t
9	V-26942316	1	2020-05-31	\N	1	t
10	V-7552887	1	2020-05-31	\N	1	t
2	V-9602344	1	2020-05-31	2020-05-31	0	t
11	V-7552887	3	2020-05-31	2020-05-31	0	t
12	V-9602345	3	2020-05-31	2020-05-31	0	t
13	V-9602344	3	2020-05-31	2020-05-31	0	t
14	V-26942316	3	2020-05-31	2020-05-31	0	t
15	V-9602345	3	2020-05-31	2020-05-31	0	t
16	V-9602344	3	2020-05-31	2020-05-31	0	t
17	V-26942316	3	2020-05-31	2020-05-31	0	t
18	V-26942316	3	2020-05-31	2020-05-31	0	t
20	V-26942316	5	2020-05-31	\N	1	t
21	V-7552887	5	2020-05-31	\N	1	t
22	V-9602345	5	2020-05-31	\N	1	t
23	V-26942316	6	2020-05-31	\N	1	t
24	V-7552887	6	2020-05-31	\N	1	t
25	V-26942316	7	2020-05-31	\N	1	t
27	V-26942316	9	2020-05-31	\N	1	t
19	V-9602345	3	2020-05-31	2020-05-31	0	t
26	V-26942316	8	2020-05-31	2020-05-31	0	t
\.


--
-- TOC entry 3392 (class 0 OID 39329)
-- Dependencies: 259
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.responsable (ci_persona, activo) FROM stdin;
V-22318938	f
V-16111353	t
V-32547896	t
\.


--
-- TOC entry 3393 (class 0 OID 39333)
-- Dependencies: 260
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sancion (id, tipo, mes, anio, monto, descripcion, estado, moneda) FROM stdin;
90	Multa	6	2020	100000	dfgdg	Pendiente	Dólar
89	Multa	6	2020	100000	dfgdg	Pendiente	Dólar
91	Interes de mora	6	2020	10	dfgdg	Pendiente	Bolívar
\.


--
-- TOC entry 3395 (class 0 OID 39338)
-- Dependencies: 262
-- Data for Name: tipo_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipo_unidad (id, area, tipo, activo) FROM stdin;
1	80	Esquina	t
3	500	Mediano	t
5	100	Pequeño	t
2	85	No esquina	t
\.


--
-- TOC entry 3397 (class 0 OID 39344)
-- Dependencies: 264
-- Data for Name: tipo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipo_usuario (id, tipo) FROM stdin;
1	Administrador
2	prueba 1
3	prueba 2 
4	prueba 3
5	prueba4
6	prueba5
7	prueba 6
\.


--
-- TOC entry 3399 (class 0 OID 39352)
-- Dependencies: 266
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.unidad (id, n_unidad, n_documento, direccion, activo, id_tipo, alicuota) FROM stdin;
3	33	33	lkhkljbhkj	f	5	0.06872852146625519
8	zxcz	zxcz	zxzxc	f	3	0.36900368332862854
5	2342	sdfsdf	sdfdsf	t	1	0.08743169158697128
2	11	5555	kjgkb	t	1	0.08743169158697128
9	dsfs	sdfs	sdfsd	t	3	0.5464481115341187
7	sdfsdf	sdfs	sdfsdf	t	2	0.09289617836475372
6	sdfdsfs	sdfs	sdfsf	t	2	0.09289617836475372
1	22	3333	kvkgh	t	2	0.09289617836475372
\.


--
-- TOC entry 3401 (class 0 OID 39358)
-- Dependencies: 268
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, usuario, password, pregunta, respuesta, ci_persona, id_tipo_usuario, activo) FROM stdin;
1	Ingresar Usuario	455831477b82574f6bf871193f2f761d	pregunta	respuesta	\N	1	t
2	ansua92	628bfed9ce559d754c3eabcfca44366b	hola	chao	V-22318939	2	t
3	jhensu	628bfed9ce559d754c3eabcfca44366b	hola	chao	V-22318938	3	t
4	jhos	426fd6712d44e2dee024ca47bb9ccfca	hhh	hhh	V-11276626	4	t
5	yan	911f6332e7f90b94b87f15377263995c	yan	yan	V-9602344	5	t
6	coro	bd721a246bd2c364e6bc6c1daa4503de	coro	coro	V-9602345	6	t
7	hilda	ad31b478525413f0b1b1d8bf0aebeb7c	hilda	hilda	V-7552887	7	t
8	bjklk	934b535800b1cba8f96a5d72f72f1611	dfs	sdfsd	V-14523698	3	t
\.


--
-- TOC entry 3403 (class 0 OID 39459)
-- Dependencies: 292
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visita (id, fecha, hora, placa, modelo, color, ci_visitante, id_unidad) FROM stdin;
54	2020-05-31	16:36:56	dgh	dgdfg	dasfsa	V-26942316	1
55	2020-05-31	17:26:15	565	ghgh	fgfg	V-11276626	1
56	2020-05-31	17:32:55	ghc	gh	gh	V-14523698	3
\.


--
-- TOC entry 3404 (class 0 OID 39464)
-- Dependencies: 293
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visitante (ci_persona, activo) FROM stdin;
V-26942316	t
V-11276626	t
V-23654789	t
V-14523698	t
V-12365478	t
\.


--
-- TOC entry 3443 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 85, true);


--
-- TOC entry 3444 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 1, false);


--
-- TOC entry 3445 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 7, true);


--
-- TOC entry 3446 (class 0 OID 0)
-- Dependencies: 209
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 12, true);


--
-- TOC entry 3447 (class 0 OID 0)
-- Dependencies: 211
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 53, true);


--
-- TOC entry 3448 (class 0 OID 0)
-- Dependencies: 213
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 44, true);


--
-- TOC entry 3449 (class 0 OID 0)
-- Dependencies: 217
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3450 (class 0 OID 0)
-- Dependencies: 219
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1474, true);


--
-- TOC entry 3451 (class 0 OID 0)
-- Dependencies: 221
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 59, true);


--
-- TOC entry 3452 (class 0 OID 0)
-- Dependencies: 223
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pago_id_seq', 1, false);


--
-- TOC entry 3453 (class 0 OID 0)
-- Dependencies: 225
-- Name: funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcion_id_seq', 27, true);


--
-- TOC entry 3454 (class 0 OID 0)
-- Dependencies: 228
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 81, true);


--
-- TOC entry 3455 (class 0 OID 0)
-- Dependencies: 229
-- Name: gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_id_seq', 17, true);


--
-- TOC entry 3456 (class 0 OID 0)
-- Dependencies: 231
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 27, true);


--
-- TOC entry 3457 (class 0 OID 0)
-- Dependencies: 233
-- Name: mensaje_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mensaje_id_seq', 1, false);


--
-- TOC entry 3458 (class 0 OID 0)
-- Dependencies: 235
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagar_cuota_especial_id_seq', 1, false);


--
-- TOC entry 3459 (class 0 OID 0)
-- Dependencies: 240
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 268, true);


--
-- TOC entry 3460 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 9, true);


--
-- TOC entry 3461 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 503, true);


--
-- TOC entry 3462 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_concepto_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_concepto_factura_id_seq', 2, true);


--
-- TOC entry 3463 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_gasto_concepto_id_seq', 75, true);


--
-- TOC entry 3464 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_mensaje_usuario_id_seq', 1, false);


--
-- TOC entry 3465 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_persona_condominio_id_seq', 2, true);


--
-- TOC entry 3466 (class 0 OID 0)
-- Dependencies: 254
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 212, true);


--
-- TOC entry 3467 (class 0 OID 0)
-- Dependencies: 256
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_tipo_funcion_id_seq', 65, true);


--
-- TOC entry 3468 (class 0 OID 0)
-- Dependencies: 258
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 27, true);


--
-- TOC entry 3469 (class 0 OID 0)
-- Dependencies: 261
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 91, true);


--
-- TOC entry 3470 (class 0 OID 0)
-- Dependencies: 263
-- Name: tipo_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_unidad_id_seq', 5, true);


--
-- TOC entry 3471 (class 0 OID 0)
-- Dependencies: 265
-- Name: tipo_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_usuario_id_seq', 7, true);


--
-- TOC entry 3472 (class 0 OID 0)
-- Dependencies: 267
-- Name: unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidad_id_seq', 9, true);


--
-- TOC entry 3473 (class 0 OID 0)
-- Dependencies: 269
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 8, true);


--
-- TOC entry 3474 (class 0 OID 0)
-- Dependencies: 296
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 56, true);


--
-- TOC entry 3069 (class 2606 OID 39512)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 3071 (class 2606 OID 39514)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 3073 (class 2606 OID 39516)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3075 (class 2606 OID 39518)
-- Name: cobro_unidad cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 3077 (class 2606 OID 39520)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 3079 (class 2606 OID 39522)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3081 (class 2606 OID 39524)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 3083 (class 2606 OID 39526)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 3087 (class 2606 OID 39528)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3085 (class 2606 OID 39530)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 3089 (class 2606 OID 39532)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 3091 (class 2606 OID 39534)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 3093 (class 2606 OID 39536)
-- Name: funcion funcion_funcion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_funcion_key UNIQUE (funcion);


--
-- TOC entry 3095 (class 2606 OID 39538)
-- Name: funcion funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3099 (class 2606 OID 39540)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 3097 (class 2606 OID 39542)
-- Name: gasto gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3101 (class 2606 OID 39544)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 3103 (class 2606 OID 39546)
-- Name: mensaje mensaje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (id);


--
-- TOC entry 3105 (class 2606 OID 39548)
-- Name: pagar_cuota_especial pagar_cuota_especial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_pkey PRIMARY KEY (id);


--
-- TOC entry 3107 (class 2606 OID 39550)
-- Name: persona persona_correo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_correo_key UNIQUE (correo);


--
-- TOC entry 3109 (class 2606 OID 39552)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3111 (class 2606 OID 39554)
-- Name: persona persona_telefono_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_telefono_key UNIQUE (telefono);


--
-- TOC entry 3113 (class 2606 OID 39556)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3115 (class 2606 OID 39558)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 3117 (class 2606 OID 39560)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3119 (class 2606 OID 39562)
-- Name: puente_asambleas_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3121 (class 2606 OID 39564)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3123 (class 2606 OID 39566)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3125 (class 2606 OID 39568)
-- Name: puente_concepto_factura puente_concepto_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_concepto_factura
    ADD CONSTRAINT puente_concepto_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3127 (class 2606 OID 39570)
-- Name: puente_gasto_concepto puente_gasto_concepto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_pkey PRIMARY KEY (id);


--
-- TOC entry 3129 (class 2606 OID 39572)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3131 (class 2606 OID 39574)
-- Name: puente_persona_condominio puente_persona_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3133 (class 2606 OID 39576)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3135 (class 2606 OID 39578)
-- Name: puente_tipo_funcion puente_tipo_funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3137 (class 2606 OID 39580)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3139 (class 2606 OID 39582)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3141 (class 2606 OID 39584)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3143 (class 2606 OID 39586)
-- Name: tipo_unidad tipo_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3145 (class 2606 OID 39588)
-- Name: tipo_usuario tipo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3147 (class 2606 OID 39590)
-- Name: tipo_usuario tipo_usuario_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_tipo_key UNIQUE (tipo);


--
-- TOC entry 3149 (class 2606 OID 39592)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3151 (class 2606 OID 39594)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3153 (class 2606 OID 39596)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3155 (class 2606 OID 39598)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3157 (class 2606 OID 39600)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3168 (class 2606 OID 39601)
-- Name: puente_asambleas_propietario asambleas_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT asambleas_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona) NOT VALID;


--
-- TOC entry 3159 (class 2606 OID 39606)
-- Name: cuenta cuenta_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3160 (class 2606 OID 39611)
-- Name: cuenta cuenta_id_banco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_id_banco_fkey FOREIGN KEY (id_banco) REFERENCES public.banco(id);


--
-- TOC entry 3162 (class 2606 OID 39616)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3163 (class 2606 OID 39621)
-- Name: cuenta_pagar cuenta_pagar_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 3161 (class 2606 OID 39626)
-- Name: cuenta cuenta_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3164 (class 2606 OID 39631)
-- Name: gasto gasto_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3158 (class 2606 OID 39636)
-- Name: concepto_gasto id_categoria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT id_categoria FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id) NOT VALID;


--
-- TOC entry 3165 (class 2606 OID 39641)
-- Name: mensaje mensaje_emisor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_emisor_fkey FOREIGN KEY (emisor) REFERENCES public.usuario(id);


--
-- TOC entry 3166 (class 2606 OID 39646)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3167 (class 2606 OID 39651)
-- Name: propietario propietario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3169 (class 2606 OID 39656)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_concepto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_concepto_fkey FOREIGN KEY (id_concepto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3170 (class 2606 OID 39661)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3171 (class 2606 OID 39666)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_id_mensaje_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_id_mensaje_fkey FOREIGN KEY (id_mensaje) REFERENCES public.mensaje(id);


--
-- TOC entry 3172 (class 2606 OID 39671)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3173 (class 2606 OID 39676)
-- Name: puente_persona_condominio puente_persona_condominio_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3174 (class 2606 OID 39681)
-- Name: puente_persona_condominio puente_persona_condominio_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3175 (class 2606 OID 39686)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_funcion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_funcion_fkey FOREIGN KEY (id_funcion) REFERENCES public.funcion(id);


--
-- TOC entry 3176 (class 2606 OID 39691)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3177 (class 2606 OID 39696)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3178 (class 2606 OID 39701)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3179 (class 2606 OID 39706)
-- Name: responsable responsable_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3180 (class 2606 OID 39711)
-- Name: unidad unidad_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_unidad(id);


--
-- TOC entry 3181 (class 2606 OID 39716)
-- Name: usuario usuario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3182 (class 2606 OID 39721)
-- Name: usuario usuario_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3183 (class 2606 OID 39726)
-- Name: visita visita_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id) NOT VALID;


--
-- TOC entry 3184 (class 2606 OID 39731)
-- Name: visitante visitante_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


-- Completed on 2020-06-11 10:38:41

--
-- PostgreSQL database dump complete
--

