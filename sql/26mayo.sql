--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-05-26 18:38:03

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
-- TOC entry 284 (class 1255 OID 27242)
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
-- TOC entry 285 (class 1255 OID 27243)
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
-- TOC entry 286 (class 1255 OID 27244)
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
-- TOC entry 287 (class 1255 OID 27245)
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
-- TOC entry 202 (class 1259 OID 27246)
-- Name: asambleas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asambleas (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion character varying(500) NOT NULL,
    id_condominio character varying(15) NOT NULL
);


ALTER TABLE public.asambleas OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 27252)
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
-- TOC entry 3338 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 204 (class 1259 OID 27254)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo boolean
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 27257)
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
-- TOC entry 3339 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 206 (class 1259 OID 27259)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    activo boolean
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 27262)
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
-- TOC entry 3340 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 208 (class 1259 OID 27264)
-- Name: cierre_de_mes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cierre_de_mes (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    id_condominio character varying(15)
);


ALTER TABLE public.cierre_de_mes OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 27267)
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
-- TOC entry 3341 (class 0 OID 0)
-- Dependencies: 209
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cierre_de_mes_id_seq OWNED BY public.cierre_de_mes.id;


--
-- TOC entry 210 (class 1259 OID 27269)
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
-- TOC entry 211 (class 1259 OID 27275)
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
-- TOC entry 3342 (class 0 OID 0)
-- Dependencies: 211
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 212 (class 1259 OID 27277)
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
-- TOC entry 213 (class 1259 OID 27283)
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
-- TOC entry 3343 (class 0 OID 0)
-- Dependencies: 213
-- Name: comunicados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id;


--
-- TOC entry 214 (class 1259 OID 27285)
-- Name: concepto_gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gasto (
    id integer NOT NULL,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL,
    activo boolean
);


ALTER TABLE public.concepto_gasto OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 27288)
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
-- TOC entry 3344 (class 0 OID 0)
-- Dependencies: 215
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 216 (class 1259 OID 27290)
-- Name: condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.condominio (
    rif character varying(15) NOT NULL,
    razon_social character varying(150) NOT NULL,
    telefono character varying(11) NOT NULL,
    correo_electronico character varying(70) NOT NULL,
    activo bigint
);


ALTER TABLE public.condominio OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 27293)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    cedula character varying(8) NOT NULL,
    n_cuenta character varying(20) NOT NULL,
    beneficiario character varying(50) NOT NULL,
    tipo character varying(10) NOT NULL,
    id_banco bigint NOT NULL,
    activo bigint
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 27296)
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
-- TOC entry 219 (class 1259 OID 27299)
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
-- TOC entry 3345 (class 0 OID 0)
-- Dependencies: 219
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 220 (class 1259 OID 27301)
-- Name: facturas_proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.facturas_proveedores (
    id integer NOT NULL,
    id_proveedor character varying(15) NOT NULL,
    calcular character varying(200) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    saldo double precision NOT NULL,
    n_meses bigint NOT NULL,
    id_asamblea bigint,
    observacion character varying(500),
    estado character varying(100) NOT NULL,
    id_condominio character varying(15),
    n_mese_restante bigint,
    pagado character varying(15),
    tipo character varying(20)
);


ALTER TABLE public.facturas_proveedores OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 27307)
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuotas_especiales_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuotas_especiales_id_seq OWNER TO postgres;

--
-- TOC entry 3346 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.facturas_proveedores.id;


--
-- TOC entry 222 (class 1259 OID 27309)
-- Name: detalle_pagos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_pagos (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    id_gasto bigint,
    id_factura bigint,
    tipo_gasto character varying
);


ALTER TABLE public.detalle_pagos OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 27315)
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
-- TOC entry 3347 (class 0 OID 0)
-- Dependencies: 223
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 224 (class 1259 OID 27317)
-- Name: factura_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.factura_unidad (
    id integer NOT NULL,
    monto double precision NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    alicuota double precision NOT NULL,
    estado character varying(50) NOT NULL,
    saldo_restante double precision NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.factura_unidad OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 27320)
-- Name: detalle_total_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_total_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.detalle_total_id_seq OWNER TO postgres;

--
-- TOC entry 3348 (class 0 OID 0)
-- Dependencies: 225
-- Name: detalle_total_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_total_id_seq OWNED BY public.factura_unidad.id;


--
-- TOC entry 226 (class 1259 OID 27322)
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
    activo bigint
);


ALTER TABLE public.fondos OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 27328)
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
-- TOC entry 3349 (class 0 OID 0)
-- Dependencies: 227
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 283 (class 1259 OID 36259)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pago (
    id integer NOT NULL,
    forma_pago character varying NOT NULL,
    activo boolean
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 36257)
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
-- TOC entry 3350 (class 0 OID 0)
-- Dependencies: 282
-- Name: forma_pago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pago_id_seq OWNED BY public.forma_pago.id;


--
-- TOC entry 277 (class 1259 OID 28117)
-- Name: funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcion (
    id integer NOT NULL,
    funcion character varying NOT NULL
);


ALTER TABLE public.funcion OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 28115)
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
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 276
-- Name: funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcion_id_seq OWNED BY public.funcion.id;


--
-- TOC entry 228 (class 1259 OID 27330)
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
-- TOC entry 229 (class 1259 OID 27333)
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
-- TOC entry 3352 (class 0 OID 0)
-- Dependencies: 229
-- Name: gasto_comun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_comun_id_seq OWNED BY public.gasto_comun.id;


--
-- TOC entry 230 (class 1259 OID 27335)
-- Name: interes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interes (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    factor double precision NOT NULL,
    estado character varying(20) NOT NULL,
    activo bigint
);


ALTER TABLE public.interes OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 27338)
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
-- TOC entry 3353 (class 0 OID 0)
-- Dependencies: 231
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 232 (class 1259 OID 27340)
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
-- TOC entry 233 (class 1259 OID 27346)
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
-- TOC entry 3354 (class 0 OID 0)
-- Dependencies: 233
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagar_cuota_especial_id_seq OWNED BY public.pagar_cuota_especial.id;


--
-- TOC entry 255 (class 1259 OID 27905)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    cedula character varying(10) NOT NULL,
    p_nombre character varying(25) NOT NULL,
    s_nombre character varying(25) DEFAULT ''::character varying NOT NULL,
    p_apellido character varying(25) NOT NULL,
    s_apellido character varying(25) DEFAULT ''::character varying NOT NULL,
    telefono character varying(12) NOT NULL,
    correo character varying(60) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 27928)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 27363)
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
-- TOC entry 235 (class 1259 OID 27369)
-- Name: puente_asamblea_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asamblea_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.puente_asamblea_propietario OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 27372)
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
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 236
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


--
-- TOC entry 237 (class 1259 OID 27374)
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
-- TOC entry 238 (class 1259 OID 27377)
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
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 238
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 239 (class 1259 OID 27379)
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
-- TOC entry 240 (class 1259 OID 27385)
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
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 240
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 254 (class 1259 OID 27659)
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
-- TOC entry 253 (class 1259 OID 27657)
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
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 253
-- Name: puente_concepto_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_concepto_factura_id_seq OWNED BY public.puente_concepto_factura.id;


--
-- TOC entry 241 (class 1259 OID 27387)
-- Name: puente_condominio_cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_condominio_cuenta (
    id integer NOT NULL,
    id_cuenta character varying(20),
    id_condominio character varying(20),
    activo bigint
);


ALTER TABLE public.puente_condominio_cuenta OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 27390)
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_condomino_cuenta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_condomino_cuenta_id_seq OWNER TO postgres;

--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


--
-- TOC entry 243 (class 1259 OID 27392)
-- Name: puente_interes_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_interes_condominio (
    id integer NOT NULL,
    id_condominio character varying(15) NOT NULL,
    id_interes bigint NOT NULL,
    activo bigint
);


ALTER TABLE public.puente_interes_condominio OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 27395)
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_interes_condominio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_interes_condominio_id_seq OWNER TO postgres;

--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_interes_condominio_id_seq OWNED BY public.puente_interes_condominio.id;


--
-- TOC entry 261 (class 1259 OID 27955)
-- Name: puente_persona_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_persona_condominio (
    id integer NOT NULL,
    ci_persona character varying(10) NOT NULL,
    rif_condominio character varying(15) NOT NULL
);


ALTER TABLE public.puente_persona_condominio OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 27953)
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
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 260
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_persona_condominio_id_seq OWNED BY public.puente_persona_condominio.id;


--
-- TOC entry 245 (class 1259 OID 27397)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 27400)
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
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 279 (class 1259 OID 28130)
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
-- TOC entry 278 (class 1259 OID 28128)
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
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 278
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_tipo_funcion_id_seq OWNED BY public.puente_tipo_funcion.id;


--
-- TOC entry 263 (class 1259 OID 27973)
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
-- TOC entry 262 (class 1259 OID 27971)
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
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 262
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 256 (class 1259 OID 27917)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 27415)
-- Name: sancion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sancion (
    id integer NOT NULL,
    tipo character varying(40) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(200) NOT NULL,
    estado character varying(20)
);


ALTER TABLE public.sancion OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 27418)
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
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 248
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 273 (class 1259 OID 28083)
-- Name: tipo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_usuario (
    id integer NOT NULL,
    tipo character varying NOT NULL
);


ALTER TABLE public.tipo_usuario OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 28081)
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
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 272
-- Name: tipo_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_usuario_id_seq OWNED BY public.tipo_usuario.id;


--
-- TOC entry 259 (class 1259 OID 27941)
-- Name: unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidad (
    id integer NOT NULL,
    n_unidad character varying(10) NOT NULL,
    n_documento character varying(15) NOT NULL,
    direccion character varying(200) NOT NULL,
    area double precision NOT NULL,
    id_condominio character varying(15),
    activo boolean DEFAULT true
);


ALTER TABLE public.unidad OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 27939)
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
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 258
-- Name: unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidad_id_seq OWNED BY public.unidad.id;


--
-- TOC entry 275 (class 1259 OID 28096)
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
-- TOC entry 274 (class 1259 OID 28094)
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
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 274
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 249 (class 1259 OID 27434)
-- Name: v_cuenta_pagar; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta_pagar AS
 SELECT cp.num_ref,
    cp.forma_pago,
    cp.descripcion,
    cp.monto,
    cp.fecha,
    prov.nombre,
    cu.n_cuenta,
    ba.nombre_banco,
    fon.tipo
   FROM ((((public.cuenta_pagar cp
     JOIN public.proveedores prov ON (((prov.cedula)::text = (cp.id_proveedor)::text)))
     JOIN public.cuenta cu ON (((cu.n_cuenta)::text = (cp.id_cuenta)::text)))
     JOIN public.fondos fon ON ((fon.id = cp.id_fondo)))
     JOIN public.banco ba ON ((ba.id = cu.id_banco)))
  ORDER BY cp.fecha DESC;


ALTER TABLE public.v_cuenta_pagar OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 28020)
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
-- TOC entry 266 (class 1259 OID 27999)
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
-- TOC entry 267 (class 1259 OID 28003)
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
-- TOC entry 264 (class 1259 OID 27991)
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
-- TOC entry 265 (class 1259 OID 27995)
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
-- TOC entry 268 (class 1259 OID 28007)
-- Name: v_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad AS
 SELECT unidad.id,
    unidad.n_unidad,
    unidad.n_documento,
    unidad.direccion,
    unidad.area,
    unidad.id_condominio
   FROM public.unidad
  WHERE (unidad.activo = true);


ALTER TABLE public.v_unidad OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 28015)
-- Name: v_unidad_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad_propietario AS
 SELECT u.id,
    u.id_condominio,
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
-- TOC entry 269 (class 1259 OID 28011)
-- Name: v_unidades_inactivas; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidades_inactivas AS
 SELECT unidad.id,
    unidad.n_unidad,
    unidad.n_documento,
    unidad.direccion,
    unidad.area,
    unidad.id_condominio
   FROM public.unidad
  WHERE (unidad.activo = false);


ALTER TABLE public.v_unidades_inactivas OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 28146)
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
-- TOC entry 281 (class 1259 OID 28150)
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
-- TOC entry 250 (class 1259 OID 27461)
-- Name: visita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visita (
    id integer NOT NULL,
    fecha date DEFAULT LOCALTIMESTAMP(0),
    hora time without time zone DEFAULT LOCALTIMESTAMP(0),
    placa character varying(8),
    modelo character varying(25),
    color character varying(15),
    id_unidad character varying(5) NOT NULL,
    ci_visitante character varying(10) NOT NULL
);


ALTER TABLE public.visita OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 27466)
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
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 251
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 252 (class 1259 OID 27468)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    cedula character varying(10) NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 2961 (class 2604 OID 27471)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2962 (class 2604 OID 27472)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2963 (class 2604 OID 27473)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 2964 (class 2604 OID 27474)
-- Name: cierre_de_mes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes ALTER COLUMN id SET DEFAULT nextval('public.cierre_de_mes_id_seq'::regclass);


--
-- TOC entry 2965 (class 2604 OID 27475)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 2966 (class 2604 OID 27476)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 2967 (class 2604 OID 27477)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 2968 (class 2604 OID 27478)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 2970 (class 2604 OID 27480)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 2971 (class 2604 OID 27481)
-- Name: factura_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura_unidad ALTER COLUMN id SET DEFAULT nextval('public.detalle_total_id_seq'::regclass);


--
-- TOC entry 2969 (class 2604 OID 27479)
-- Name: facturas_proveedores id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas_proveedores ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 2972 (class 2604 OID 27482)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 3003 (class 2604 OID 36262)
-- Name: forma_pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago ALTER COLUMN id SET DEFAULT nextval('public.forma_pago_id_seq'::regclass);


--
-- TOC entry 3001 (class 2604 OID 28120)
-- Name: funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion ALTER COLUMN id SET DEFAULT nextval('public.funcion_id_seq'::regclass);


--
-- TOC entry 2973 (class 2604 OID 27483)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 2974 (class 2604 OID 27484)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 2975 (class 2604 OID 27485)
-- Name: pagar_cuota_especial id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial ALTER COLUMN id SET DEFAULT nextval('public.pagar_cuota_especial_id_seq'::regclass);


--
-- TOC entry 2976 (class 2604 OID 27489)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2977 (class 2604 OID 27490)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 2978 (class 2604 OID 27491)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 2986 (class 2604 OID 27662)
-- Name: puente_concepto_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_concepto_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_concepto_factura_id_seq'::regclass);


--
-- TOC entry 2979 (class 2604 OID 27492)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2980 (class 2604 OID 27493)
-- Name: puente_interes_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_interes_condominio_id_seq'::regclass);


--
-- TOC entry 2994 (class 2604 OID 27958)
-- Name: puente_persona_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_persona_condominio_id_seq'::regclass);


--
-- TOC entry 2981 (class 2604 OID 27494)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 3002 (class 2604 OID 28133)
-- Name: puente_tipo_funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion ALTER COLUMN id SET DEFAULT nextval('public.puente_tipo_funcion_id_seq'::regclass);


--
-- TOC entry 2995 (class 2604 OID 27976)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 2982 (class 2604 OID 27499)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 2998 (class 2604 OID 28086)
-- Name: tipo_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario ALTER COLUMN id SET DEFAULT nextval('public.tipo_usuario_id_seq'::regclass);


--
-- TOC entry 2992 (class 2604 OID 27944)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 2999 (class 2604 OID 28099)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 2985 (class 2604 OID 27502)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3262 (class 0 OID 27246)
-- Dependencies: 202
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3264 (class 0 OID 27254)
-- Dependencies: 204
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banco VALUES (3, 'Caribe', NULL);
INSERT INTO public.banco VALUES (4, 'Provincial', NULL);
INSERT INTO public.banco VALUES (6, 'Venezuela', NULL);
INSERT INTO public.banco VALUES (1, 'Banesco', NULL);
INSERT INTO public.banco VALUES (5, 'Mercantil', NULL);
INSERT INTO public.banco VALUES (7, 'BOD', NULL);
INSERT INTO public.banco VALUES (8, 'Fondo Comun', NULL);
INSERT INTO public.banco VALUES (10, 'Fuerzas Armadas', NULL);
INSERT INTO public.banco VALUES (11, 'Venezolana de Credito', NULL);
INSERT INTO public.banco VALUES (9, 'Banplus', NULL);


--
-- TOC entry 3266 (class 0 OID 27259)
-- Dependencies: 206
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (3, 'Reparaciones', 'Conjunto de conceptos de reparaciones', true);
INSERT INTO public.categoriagasto VALUES (4, 'Uso comun', 'Conjunto de conceptos de uso comun y consumo', true);
INSERT INTO public.categoriagasto VALUES (6, 'asda', 'ddasda', true);
INSERT INTO public.categoriagasto VALUES (1, 'Administrativo', 'Conjunto de conceptos administrativos', true);
INSERT INTO public.categoriagasto VALUES (2, 'Mantenimiento', 'Conjunto de conceptos de mantenimiento', true);
INSERT INTO public.categoriagasto VALUES (5, 'fsfsfdsfs', 'Conjunto de conceptos administrativo', true);


--
-- TOC entry 3268 (class 0 OID 27264)
-- Dependencies: 208
-- Data for Name: cierre_de_mes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cierre_de_mes VALUES (57, 5, 2020, '21321312');


--
-- TOC entry 3270 (class 0 OID 27269)
-- Dependencies: 210
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cobro_unidad VALUES (11, 12500, 'asdas', '01020045120268985654', 'Transferencia', 'asdsad', '2020-05-09', 55, 66);
INSERT INTO public.cobro_unidad VALUES (12, 10000, 'fdf', '01020045120268985654', 'Transferencia', '12313', '2020-05-09', 55, 66);


--
-- TOC entry 3272 (class 0 OID 27277)
-- Dependencies: 212
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3274 (class 0 OID 27285)
-- Dependencies: 214
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (11, 'Provisión utilidades ', 'Utilidades del trabajador', 1, true);
INSERT INTO public.concepto_gasto VALUES (10, 'Liquidación ', 'Liquidacion trabajador', 1, true);
INSERT INTO public.concepto_gasto VALUES (12, 'Provisión vacaciones ', 'Vacaciones trabajador', 1, true);
INSERT INTO public.concepto_gasto VALUES (13, 'Ley de alimentación', 'Pago alimentación', 1, true);
INSERT INTO public.concepto_gasto VALUES (14, 'FAHO', 'Fondo de ahorro obligatorio de la vivienda', 1, true);
INSERT INTO public.concepto_gasto VALUES (15, 'Caja chica', 'Caja chica', 1, true);
INSERT INTO public.concepto_gasto VALUES (16, 'Mant. ascensores', 'Mantenimiento de los asecensores', 2, true);
INSERT INTO public.concepto_gasto VALUES (17, 'Mant. hidroneumatico', 'Mantenimiento del hidroneumatico', 2, true);
INSERT INTO public.concepto_gasto VALUES (18, 'Mant. porton electrico', 'Mantenimiento del porton electrico', 2, true);
INSERT INTO public.concepto_gasto VALUES (19, 'Mant. cerco electrico', 'Mantenimiento del cerco electrico', 2, true);
INSERT INTO public.concepto_gasto VALUES (20, 'Mant. areas verdes', 'Mantenimiento de areas verdes', 2, true);
INSERT INTO public.concepto_gasto VALUES (21, 'Mant. piscina', 'Mantenimiento de la piscina', 2, true);
INSERT INTO public.concepto_gasto VALUES (22, 'Mant. parque infantil', 'Mantenimiento del parque infantil', 2, true);
INSERT INTO public.concepto_gasto VALUES (23, 'Mant. camaras vigilancia', 'Mantenimiento camaras de vigilancia', 2, true);
INSERT INTO public.concepto_gasto VALUES (24, 'Mant. tuberias', 'Mantenimiento de tuberias ', 2, true);
INSERT INTO public.concepto_gasto VALUES (25, 'Mant. estacionamiento', 'Mantenimiento del estacionamiento ', 2, true);
INSERT INTO public.concepto_gasto VALUES (26, 'Mant. pintura edif. ', 'Pintura de areas comunes ', 2, true);
INSERT INTO public.concepto_gasto VALUES (27, 'Mant. alumbrado elect. ', 'Mantenimiento del alumbrado elect.', 2, true);
INSERT INTO public.concepto_gasto VALUES (28, 'Mant. compra prod. limp.', 'Gastos productos de limpieza', 2, true);
INSERT INTO public.concepto_gasto VALUES (29, 'Mant. prod. limpieza ', 'Gasto otros productos', 2, true);
INSERT INTO public.concepto_gasto VALUES (30, 'Repar. ascensores', 'Reparacion de los asecensores', 3, true);
INSERT INTO public.concepto_gasto VALUES (31, 'Repar. tuberia', 'Reparacion de tuberias de aguas', 3, true);
INSERT INTO public.concepto_gasto VALUES (32, 'Repar. porton', 'Reparacion del porton electrico', 3, true);
INSERT INTO public.concepto_gasto VALUES (33, 'Repar. cerco elect.', 'Reparacion del cerco electrico', 3, true);
INSERT INTO public.concepto_gasto VALUES (34, 'Repar. hidroneumatico.', 'Reparacion la bomba de agua', 3, true);
INSERT INTO public.concepto_gasto VALUES (35, 'Repar. camaras.', 'Reparacion de camaras', 3, true);
INSERT INTO public.concepto_gasto VALUES (36, 'Repar. electricas.', 'Reparacion/Sustitucion de componentes electricos', 3, true);
INSERT INTO public.concepto_gasto VALUES (37, 'Consumo de electricidad', 'Consumo de electricidad', 3, true);
INSERT INTO public.concepto_gasto VALUES (40, 'HidroCapital', 'Consumo de agua', 4, true);
INSERT INTO public.concepto_gasto VALUES (41, 'Vigilancia', 'Gastos por vigilancia', 4, true);
INSERT INTO public.concepto_gasto VALUES (2, 'Salario conserje', 'Salario del conserje del condominio', 1, true);
INSERT INTO public.concepto_gasto VALUES (3, 'Honorarios administradora', 'Honorarios administradora del condominio', 1, true);
INSERT INTO public.concepto_gasto VALUES (4, 'Gastos oficina', 'Gastos en material de oficina', 1, true);
INSERT INTO public.concepto_gasto VALUES (9, 'Bono Vacacional ', 'Bono vacacional trabajador', 1, true);
INSERT INTO public.concepto_gasto VALUES (7, 'Prestaciones soc.', 'Prestaciones sociales trabajador', 1, true);
INSERT INTO public.concepto_gasto VALUES (8, 'Seguro social', 'Seguro social trabajador', 1, true);
INSERT INTO public.concepto_gasto VALUES (5, 'Gastos varios', 'Gastos fotocopias, impresiones, internet', 1, true);
INSERT INTO public.concepto_gasto VALUES (6, 'Retiro prestaciones', 'Retiro de prestaciones sociales', 1, true);
INSERT INTO public.concepto_gasto VALUES (1, 'Honorarios administrador', 'Honorarios del administrador del condominio', 1, true);
INSERT INTO public.concepto_gasto VALUES (39, 'Cantv', 'sad', 4, true);


--
-- TOC entry 3276 (class 0 OID 27290)
-- Dependencies: 216
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('21321312', 'asdasd', '12313', 'asdasdad', 1);


--
-- TOC entry 3277 (class 0 OID 27293)
-- Dependencies: 217
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('J-285856', '01145247946596656485', 'Condominio Portal del Este', 'Corriente', 3, 1);
INSERT INTO public.cuenta VALUES ('v-358963', '01052458795254653322', 'Condominio Ciudad Roca', 'Corriente', 5, 1);
INSERT INTO public.cuenta VALUES ('J-245698', '01085698745685232540', 'Urbanizacion La Ascension', 'Corriente', 4, 1);
INSERT INTO public.cuenta VALUES ('J-102457', '01020045120268985654', 'Urbanizacion Pardos del Norte', 'Corriente', 6, 1);
INSERT INTO public.cuenta VALUES ('J-254551', '01025487596584758945', 'Condominio Estrella', 'Corriente', 1, 1);


--
-- TOC entry 3278 (class 0 OID 27296)
-- Dependencies: 218
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3282 (class 0 OID 27309)
-- Dependencies: 222
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_pagos VALUES (1316, 5, 2020, 2000, 79, 157, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1317, 5, 2020, 5000, 80, 157, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1318, 5, 2020, 5000, 76, 157, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1319, 5, 2020, 1000, 80, 157, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1320, 5, 2020, 100, 82, 157, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1321, 5, 2020, 1000, 81, 157, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1322, 5, 2020, 7200, 8, 157, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1323, 5, 2020, 1200, 9, 157, 'Interes');


--
-- TOC entry 3284 (class 0 OID 27317)
-- Dependencies: 224
-- Data for Name: factura_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.factura_unidad VALUES (157, 22500, 5, 2020, 1, 'Pagado', 0, 66);


--
-- TOC entry 3280 (class 0 OID 27301)
-- Dependencies: 220
-- Data for Name: facturas_proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.facturas_proveedores VALUES (77, '17102635', 'Total de Inmuebles', 5, 2020, 2000, 2000, 1, 0, '', 'Mensualidad Completada', '21321312', 0, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (76, 'J-1001245215', 'Alicuota', 5, 2020, 10000, 10000, 2, 0, '', 'Mensualidad Completada', '21321312', 0, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (78, 'J-1001245215', 'Alicuota', 6, 2020, 20000, 20000, 2, 0, 'asad', 'Pendiente', '21321312', 2, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (79, 'J-1001245215', 'Alicuota', 6, 2020, 20000, 20000, 1, 0, 'asad', 'Pendiente', '21321312', 1, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (80, 'J-2457021456', 'Alicuota', 6, 2020, 30000, 30000, 1, 0, '', 'Pendiente', '21321312', 1, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (81, 'J-2457021456', 'Alicuota', 5, 2021, 1323, 1323, 2, 0, 'sada', 'Pendiente', '21321312', 2, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (82, 'J-2457021456', 'Alicuota', 6, 2020, 234513533, 234513533, 1, 0, '', 'Pendiente', '21321312', 1, NULL, 'Ordinario');


--
-- TOC entry 3286 (class 0 OID 27322)
-- Dependencies: 226
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('asdads', '2020-05-09', 'adsasd', 'asda', 31231, 31231, '21321312', 56, 1);
INSERT INTO public.fondos VALUES ('sdadsad', '2020-05-16', 'fsdfds', 'sdfsd', 0, 0, '21321312', 58, 0);
INSERT INTO public.fondos VALUES ('asdad', '2020-05-16', 'dadsad', 'sadas', 21313, 0, '21321312', 55, 1);
INSERT INTO public.fondos VALUES ('asdsad', '2020-05-10', 'fsf', 'sfdsd', 0, 0, '21321312', 57, 1);


--
-- TOC entry 3332 (class 0 OID 36259)
-- Dependencies: 283
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.forma_pago VALUES (1, 'dsfsdf', true);
INSERT INTO public.forma_pago VALUES (2, 'td', true);


--
-- TOC entry 3328 (class 0 OID 28117)
-- Dependencies: 277
-- Data for Name: funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.funcion VALUES (1, 'Asambleas');
INSERT INTO public.funcion VALUES (2, 'Banco');
INSERT INTO public.funcion VALUES (3, 'Comunicados');
INSERT INTO public.funcion VALUES (4, 'Condominio');
INSERT INTO public.funcion VALUES (5, 'Cuenta');
INSERT INTO public.funcion VALUES (6, 'Cuentas por cobrar');
INSERT INTO public.funcion VALUES (7, 'Cuentas por pagar');
INSERT INTO public.funcion VALUES (8, 'Cuotas especiales');
INSERT INTO public.funcion VALUES (9, 'Fondo');
INSERT INTO public.funcion VALUES (10, 'Gastos comunes');
INSERT INTO public.funcion VALUES (11, 'Intereses');
INSERT INTO public.funcion VALUES (12, 'Pago de cuotas especiales');
INSERT INTO public.funcion VALUES (13, 'Propietarios');
INSERT INTO public.funcion VALUES (14, 'Proveedores');
INSERT INTO public.funcion VALUES (15, 'Recibo');
INSERT INTO public.funcion VALUES (16, 'Sanciones');
INSERT INTO public.funcion VALUES (17, 'Tipo de usuario');
INSERT INTO public.funcion VALUES (18, 'Unidades');
INSERT INTO public.funcion VALUES (19, 'Registro de visitas');
INSERT INTO public.funcion VALUES (20, 'Visitas autorizadas');


--
-- TOC entry 3288 (class 0 OID 27330)
-- Dependencies: 228
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto_comun VALUES (79, 'Ordinario', 5, 2020, 2000, '2727722', '24666587', 25, '', '2020-05-14', 'Pendiente de Pago', '21321312', 2000);
INSERT INTO public.gasto_comun VALUES (80, 'Ordinario', 5, 2020, 5000, '2727722', 'J-54785696', 26, '', '2020-05-14', 'Pendiente de Pago', '21321312', 5000);
INSERT INTO public.gasto_comun VALUES (81, 'Ordinario', 6, 2020, 100, '1212', 'J-1001245215', 29, 'perro', '2020-05-12', 'Pendiente', '21321312', 100);


--
-- TOC entry 3290 (class 0 OID 27335)
-- Dependencies: 230
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (14, 'Compensatorios', 6, 'Activo', 1);
INSERT INTO public.interes VALUES (10, 'Fondo de Reserva', 30, 'Activo', 1);
INSERT INTO public.interes VALUES (15, 'Legales', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (12, 'Anatosismo', 4, 'Activo', 1);
INSERT INTO public.interes VALUES (13, 'Convencional', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (16, 'indexacion', 2, 'Activo', 1);
INSERT INTO public.interes VALUES (11, 'Liquidacion', 5, 'Activo', 1);
INSERT INTO public.interes VALUES (17, 'Moratorios', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (8, 'Inflacionario', 60, 'Activo', 1);
INSERT INTO public.interes VALUES (9, 'Prestaciones Sociales', 10, 'Activo', 1);


--
-- TOC entry 3292 (class 0 OID 27340)
-- Dependencies: 232
-- Data for Name: pagar_cuota_especial; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3314 (class 0 OID 27905)
-- Dependencies: 255
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3316 (class 0 OID 27928)
-- Dependencies: 257
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3294 (class 0 OID 27363)
-- Dependencies: 234
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('J-1001245215', 'Ferreteria Todo', '02542458796', 'Ferreteriatodo@gmail.com', 'Señor José', 'Av. 4, centro comercial aris, local 3', 1);
INSERT INTO public.proveedores VALUES ('J-2457021456', 'Carpinteria Madera Blanca', '0254217965', 'CarpinteriaMB@gmail.com', 'Señora Luisa', '8 Av, esquina de la calle 12', 1);
INSERT INTO public.proveedores VALUES ('J-2457903215', 'Cristaleria Azul', '0254789635', 'Cristaleria_Azul@hotmail.com', 'Señor Armando', 'Calle 3 con esquina 7', 1);
INSERT INTO public.proveedores VALUES ('24666587', 'Hermanos Fontaneros', '04245886335', 'Hfontaneros@gmail.com', 'Señor Juan', '30 Av. entre calle 3 y 4  ', 1);
INSERT INTO public.proveedores VALUES ('17102635', 'Servicios de Limpieza', '04165048963', 'Luisa1@hotmail.com', 'Señora Luisa', '-', 1);
INSERT INTO public.proveedores VALUES ('J-54785696', 'Libreria la Rosa', '04125789654', 'Libreria_laRosa@hotmail.com', 'Juan', 'Calle 2', 1);
INSERT INTO public.proveedores VALUES ('12457896', 'Reparacion de Ascensor', '0414157896', '-', 'Julio', 'Calle 6', 1);
INSERT INTO public.proveedores VALUES ('J-215479658', 'Reparacion Electrica', '04141544400', '-', 'Alvaro', 'Calle 14', 1);
INSERT INTO public.proveedores VALUES ('20111045', 'Reparacion de Porton', '04167247578', '-', 'Julian', 'Calle 12', 1);
INSERT INTO public.proveedores VALUES ('J-547859655', 'Reparacion de Camaras', '04162488965', 'RC@hotmail.com', 'Jose', 'Calle 34', 1);


--
-- TOC entry 3295 (class 0 OID 27369)
-- Dependencies: 235
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3297 (class 0 OID 27374)
-- Dependencies: 237
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_cobro_factura VALUES (8, 157, 11, 12500);
INSERT INTO public.puente_cobro_factura VALUES (9, 157, 12, 10000);


--
-- TOC entry 3299 (class 0 OID 27379)
-- Dependencies: 239
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3313 (class 0 OID 27659)
-- Dependencies: 254
-- Data for Name: puente_concepto_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_concepto_factura VALUES (1, 81, 29, 1223);
INSERT INTO public.puente_concepto_factura VALUES (2, 81, 28, 100);
INSERT INTO public.puente_concepto_factura VALUES (16, 82, 21, 12312);
INSERT INTO public.puente_concepto_factura VALUES (17, 82, 20, 231);
INSERT INTO public.puente_concepto_factura VALUES (18, 82, 19, 32432);
INSERT INTO public.puente_concepto_factura VALUES (19, 82, 18, 234234);
INSERT INTO public.puente_concepto_factura VALUES (20, 82, 35, 234234324);


--
-- TOC entry 3301 (class 0 OID 27387)
-- Dependencies: 241
-- Data for Name: puente_condominio_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_condominio_cuenta VALUES (139, '01020045120268985654', '21321312', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (140, '01025487596584758945', '21321312', 1);


--
-- TOC entry 3303 (class 0 OID 27392)
-- Dependencies: 243
-- Data for Name: puente_interes_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_interes_condominio VALUES (95, '21321312', 8, 1);
INSERT INTO public.puente_interes_condominio VALUES (96, '21321312', 9, 1);


--
-- TOC entry 3320 (class 0 OID 27955)
-- Dependencies: 261
-- Data for Name: puente_persona_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3305 (class 0 OID 27397)
-- Dependencies: 245
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (192, 80, 66);
INSERT INTO public.puente_sancion_unidad VALUES (193, 82, 66);
INSERT INTO public.puente_sancion_unidad VALUES (194, 81, 66);
INSERT INTO public.puente_sancion_unidad VALUES (195, 83, 66);


--
-- TOC entry 3330 (class 0 OID 28130)
-- Dependencies: 279
-- Data for Name: puente_tipo_funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3322 (class 0 OID 27973)
-- Dependencies: 263
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3315 (class 0 OID 27917)
-- Dependencies: 256
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3307 (class 0 OID 27415)
-- Dependencies: 247
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (80, 'Multa', 5, 2020, 1000, 'nu', 'Procesado');
INSERT INTO public.sancion VALUES (82, 'Multa', 5, 2020, 100, 'nu', 'Procesado');
INSERT INTO public.sancion VALUES (81, 'Multa', 5, 2020, 1000, 'nu', 'Procesado');
INSERT INTO public.sancion VALUES (83, 'Multa', 6, 2020, 1000, 'nu', 'Pendiente');


--
-- TOC entry 3324 (class 0 OID 28083)
-- Dependencies: 273
-- Data for Name: tipo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3318 (class 0 OID 27941)
-- Dependencies: 259
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3326 (class 0 OID 28096)
-- Dependencies: 275
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (1, 'Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', NULL, NULL, true);


--
-- TOC entry 3309 (class 0 OID 27461)
-- Dependencies: 250
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3311 (class 0 OID 27468)
-- Dependencies: 252
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 81, true);


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 11, true);


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 6, true);


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 209
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cierre_de_mes_id_seq', 57, true);


--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 211
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 12, true);


--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 213
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 53, true);


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 215
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 41, true);


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 219
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 82, true);


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 223
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1323, true);


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 225
-- Name: detalle_total_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_total_id_seq', 157, true);


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 227
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 58, true);


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 282
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pago_id_seq', 4, true);


--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 276
-- Name: funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcion_id_seq', 20, true);


--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 229
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 81, true);


--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 231
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 17, true);


--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 233
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagar_cuota_especial_id_seq', 1, false);


--
-- TOC entry 3387 (class 0 OID 0)
-- Dependencies: 236
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 258, true);


--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 238
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 9, true);


--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 240
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 503, true);


--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 253
-- Name: puente_concepto_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_concepto_factura_id_seq', 20, true);


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 140, true);


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_interes_condominio_id_seq', 96, true);


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 260
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_persona_condominio_id_seq', 1, false);


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 195, true);


--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 278
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_tipo_funcion_id_seq', 1, false);


--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 262
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 1, false);


--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 248
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 83, true);


--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 272
-- Name: tipo_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_usuario_id_seq', 1, false);


--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 258
-- Name: unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidad_id_seq', 1, false);


--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 274
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 1, true);


--
-- TOC entry 3401 (class 0 OID 0)
-- Dependencies: 251
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 52, true);


--
-- TOC entry 3005 (class 2606 OID 27504)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 3007 (class 2606 OID 27506)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 3009 (class 2606 OID 27508)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 3011 (class 2606 OID 27510)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 3013 (class 2606 OID 27512)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3015 (class 2606 OID 27514)
-- Name: cierre_de_mes cierre_de_mes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_pkey PRIMARY KEY (id);


--
-- TOC entry 3017 (class 2606 OID 27516)
-- Name: cobro_unidad cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 3019 (class 2606 OID 27518)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 3021 (class 2606 OID 27520)
-- Name: concepto_gasto concepto_gasto_nom_concepto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_nom_concepto_key UNIQUE (nom_concepto);


--
-- TOC entry 3023 (class 2606 OID 27522)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3025 (class 2606 OID 27524)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 3027 (class 2606 OID 27526)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 3029 (class 2606 OID 27528)
-- Name: cuenta cuenta_n_cuenta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_key UNIQUE (n_cuenta);


--
-- TOC entry 3033 (class 2606 OID 27530)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3031 (class 2606 OID 27532)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 3035 (class 2606 OID 27534)
-- Name: facturas_proveedores cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas_proveedores
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


--
-- TOC entry 3037 (class 2606 OID 27536)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 3039 (class 2606 OID 27538)
-- Name: factura_unidad detalle_total_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura_unidad
    ADD CONSTRAINT detalle_total_pkey PRIMARY KEY (id);


--
-- TOC entry 3041 (class 2606 OID 27540)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 3103 (class 2606 OID 36269)
-- Name: forma_pago forma_pago_forma_pago_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_forma_pago_key UNIQUE (forma_pago);


--
-- TOC entry 3105 (class 2606 OID 36267)
-- Name: forma_pago forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id);


--
-- TOC entry 3097 (class 2606 OID 28127)
-- Name: funcion funcion_funcion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_funcion_key UNIQUE (funcion);


--
-- TOC entry 3099 (class 2606 OID 28125)
-- Name: funcion funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3043 (class 2606 OID 27542)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 3045 (class 2606 OID 27544)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 3047 (class 2606 OID 27546)
-- Name: pagar_cuota_especial pagar_cuota_especial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_pkey PRIMARY KEY (id);


--
-- TOC entry 3073 (class 2606 OID 27916)
-- Name: persona persona_correo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_correo_key UNIQUE (correo);


--
-- TOC entry 3075 (class 2606 OID 27912)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3077 (class 2606 OID 27914)
-- Name: persona persona_telefono_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_telefono_key UNIQUE (telefono);


--
-- TOC entry 3081 (class 2606 OID 27933)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3049 (class 2606 OID 27562)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 3051 (class 2606 OID 27564)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3053 (class 2606 OID 27566)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3055 (class 2606 OID 27568)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3057 (class 2606 OID 27570)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3071 (class 2606 OID 27664)
-- Name: puente_concepto_factura puente_concepto_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_concepto_factura
    ADD CONSTRAINT puente_concepto_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3059 (class 2606 OID 27572)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 3061 (class 2606 OID 27574)
-- Name: puente_interes_condominio puente_interes_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio
    ADD CONSTRAINT puente_interes_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3085 (class 2606 OID 27960)
-- Name: puente_persona_condominio puente_persona_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3063 (class 2606 OID 27576)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3101 (class 2606 OID 28135)
-- Name: puente_tipo_funcion puente_tipo_funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3087 (class 2606 OID 27980)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3079 (class 2606 OID 27922)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3065 (class 2606 OID 27586)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3089 (class 2606 OID 28091)
-- Name: tipo_usuario tipo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3091 (class 2606 OID 28093)
-- Name: tipo_usuario tipo_usuario_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_tipo_key UNIQUE (tipo);


--
-- TOC entry 3083 (class 2606 OID 27947)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3093 (class 2606 OID 28102)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3095 (class 2606 OID 28104)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3067 (class 2606 OID 27598)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3069 (class 2606 OID 27600)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3106 (class 2606 OID 27601)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 3107 (class 2606 OID 27606)
-- Name: cuenta_pagar cuenta_pagar_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3108 (class 2606 OID 27611)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3109 (class 2606 OID 27616)
-- Name: cuenta_pagar cuenta_pagar_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 3110 (class 2606 OID 27621)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3111 (class 2606 OID 27626)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_cuota_e_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_cuota_e_fkey FOREIGN KEY (id_cuota_e) REFERENCES public.facturas_proveedores(id);


--
-- TOC entry 3112 (class 2606 OID 27631)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3115 (class 2606 OID 27934)
-- Name: propietario propietario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3117 (class 2606 OID 27961)
-- Name: puente_persona_condominio puente_persona_condominio_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3118 (class 2606 OID 27966)
-- Name: puente_persona_condominio puente_persona_condominio_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3124 (class 2606 OID 28141)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_funcion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_funcion_fkey FOREIGN KEY (id_funcion) REFERENCES public.funcion(id);


--
-- TOC entry 3123 (class 2606 OID 28136)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3119 (class 2606 OID 27981)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3120 (class 2606 OID 27986)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3114 (class 2606 OID 27923)
-- Name: responsable responsable_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3116 (class 2606 OID 27948)
-- Name: unidad unidad_id_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_condominio_fkey FOREIGN KEY (id_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3121 (class 2606 OID 28105)
-- Name: usuario usuario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3122 (class 2606 OID 28110)
-- Name: usuario usuario_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3113 (class 2606 OID 27651)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.visitante(cedula);


-- Completed on 2020-05-26 18:38:07

--
-- PostgreSQL database dump complete
--

