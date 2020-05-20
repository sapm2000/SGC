--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

-- Started on 2020-05-14 12:15:13

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
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 253 (class 1255 OID 20069)
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
-- TOC entry 254 (class 1255 OID 20070)
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

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 20071)
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
-- TOC entry 197 (class 1259 OID 20077)
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
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 197
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 198 (class 1259 OID 20079)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo bigint
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 20082)
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
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 199
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 200 (class 1259 OID 20084)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    activo bigint
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 20087)
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
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 201
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 202 (class 1259 OID 20089)
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
-- TOC entry 203 (class 1259 OID 20092)
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
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 203
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cierre_de_mes_id_seq OWNED BY public.cierre_de_mes.id;


--
-- TOC entry 204 (class 1259 OID 20094)
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
-- TOC entry 205 (class 1259 OID 20100)
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
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 205
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 206 (class 1259 OID 20102)
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
-- TOC entry 207 (class 1259 OID 20108)
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
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 207
-- Name: comunicados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id;


--
-- TOC entry 208 (class 1259 OID 20110)
-- Name: concepto_gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gasto (
    id integer NOT NULL,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL,
    activo bigint
);


ALTER TABLE public.concepto_gasto OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 20113)
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
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 209
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 210 (class 1259 OID 20115)
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
-- TOC entry 211 (class 1259 OID 20118)
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
-- TOC entry 212 (class 1259 OID 20121)
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
-- TOC entry 213 (class 1259 OID 20124)
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
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 213
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 214 (class 1259 OID 20126)
-- Name: cuotas_especiales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuotas_especiales (
    id integer NOT NULL,
    id_proveedor character varying(15) NOT NULL,
    id_concepto bigint NOT NULL,
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
    pagado character varying(15)
);


ALTER TABLE public.cuotas_especiales OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 20132)
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
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 215
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.cuotas_especiales.id;


--
-- TOC entry 216 (class 1259 OID 20134)
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
-- TOC entry 217 (class 1259 OID 20140)
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
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 217
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 218 (class 1259 OID 20142)
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
-- TOC entry 219 (class 1259 OID 20145)
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
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 219
-- Name: detalle_total_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_total_id_seq OWNED BY public.factura_unidad.id;


--
-- TOC entry 220 (class 1259 OID 20147)
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
-- TOC entry 221 (class 1259 OID 20153)
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
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 221
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 222 (class 1259 OID 20155)
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
-- TOC entry 223 (class 1259 OID 20158)
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
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 223
-- Name: gasto_comun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_comun_id_seq OWNED BY public.gasto_comun.id;


--
-- TOC entry 224 (class 1259 OID 20160)
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
-- TOC entry 225 (class 1259 OID 20163)
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
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 225
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 226 (class 1259 OID 20165)
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
-- TOC entry 227 (class 1259 OID 20171)
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
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 227
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagar_cuota_especial_id_seq OWNED BY public.pagar_cuota_especial.id;


--
-- TOC entry 228 (class 1259 OID 20173)
-- Name: propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietarios (
    cedula character varying(8) NOT NULL,
    nombre character varying(30) NOT NULL,
    apellido character varying(30) NOT NULL,
    telefono character varying(12) NOT NULL,
    correo character varying(100) NOT NULL,
    activo bigint
);


ALTER TABLE public.propietarios OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 20176)
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
-- TOC entry 230 (class 1259 OID 20182)
-- Name: puente_asamblea_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asamblea_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.puente_asamblea_propietario OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 20185)
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
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 231
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


--
-- TOC entry 232 (class 1259 OID 20187)
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
-- TOC entry 233 (class 1259 OID 20190)
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
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 233
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 234 (class 1259 OID 20192)
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
-- TOC entry 235 (class 1259 OID 20198)
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
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 235
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 236 (class 1259 OID 20200)
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
-- TOC entry 237 (class 1259 OID 20203)
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
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 237
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


--
-- TOC entry 238 (class 1259 OID 20205)
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
-- TOC entry 239 (class 1259 OID 20208)
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
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 239
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_interes_condominio_id_seq OWNED BY public.puente_interes_condominio.id;


--
-- TOC entry 240 (class 1259 OID 20210)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 20213)
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
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 241
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 242 (class 1259 OID 20215)
-- Name: puente_unidad_propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_unidad_propietarios (
    id integer NOT NULL,
    id_propietario character varying(15) NOT NULL,
    id_unidad bigint NOT NULL,
    fecha_desde date NOT NULL,
    fecha_hasta date,
    documento character varying(100) NOT NULL,
    estado bigint NOT NULL,
    activo bigint
);


ALTER TABLE public.puente_unidad_propietarios OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 20218)
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
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 243
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 244 (class 1259 OID 20220)
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
-- TOC entry 245 (class 1259 OID 20223)
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
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 245
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 246 (class 1259 OID 20225)
-- Name: unidades; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidades (
    n_unidad character varying(10) NOT NULL,
    direccion character varying(200) NOT NULL,
    area bigint NOT NULL,
    id integer NOT NULL,
    id_condominio character varying(15),
    activo bigint
);


ALTER TABLE public.unidades OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 20228)
-- Name: unidades_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.unidades_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidades_id_seq OWNER TO postgres;

--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 247
-- Name: unidades_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidades_id_seq OWNED BY public.unidades.id;


--
-- TOC entry 248 (class 1259 OID 20230)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    cedula character varying(8) NOT NULL,
    usuario character varying(10),
    password character varying(12),
    nombre character varying(15),
    apellido character varying(15),
    tipo character varying(50),
    ntelefono character varying(50)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 20233)
-- Name: v_pagar_cuota; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_pagar_cuota AS
 SELECT pce.numero_ref,
    pce.forma_pago,
    pce.monto,
    pce.fecha,
    cu.n_cuenta,
    ban.nombre_banco,
    fon.tipo,
    coga.nom_concepto,
    cues.pagado,
    cues.id_condominio,
    pr.nombre
   FROM ((((((public.pagar_cuota_especial pce
     JOIN public.cuotas_especiales cues ON ((pce.id_cuota_e = cues.id)))
     JOIN public.concepto_gasto coga ON ((cues.id_concepto = coga.id)))
     JOIN public.fondos fon ON ((pce.id_fondo = fon.id)))
     JOIN public.cuenta cu ON (((pce.id_cuenta)::text = (cu.n_cuenta)::text)))
     JOIN public.banco ban ON ((cu.id_banco = ban.id)))
     JOIN public.proveedores pr ON (((cues.id_proveedor)::text = (pr.cedula)::text)))
  WHERE ((cues.pagado)::text = 'Pagado'::text);


ALTER TABLE public.v_pagar_cuota OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 20238)
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
-- TOC entry 251 (class 1259 OID 20243)
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
-- TOC entry 3176 (class 0 OID 0)
-- Dependencies: 251
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 252 (class 1259 OID 20245)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    cedula character varying(10) NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 2853 (class 2604 OID 20248)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2854 (class 2604 OID 20249)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2855 (class 2604 OID 20250)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 2856 (class 2604 OID 20251)
-- Name: cierre_de_mes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes ALTER COLUMN id SET DEFAULT nextval('public.cierre_de_mes_id_seq'::regclass);


--
-- TOC entry 2857 (class 2604 OID 20252)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 2858 (class 2604 OID 20253)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 2859 (class 2604 OID 20254)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 2860 (class 2604 OID 20255)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 2861 (class 2604 OID 20256)
-- Name: cuotas_especiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 2862 (class 2604 OID 20257)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 2863 (class 2604 OID 20258)
-- Name: factura_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura_unidad ALTER COLUMN id SET DEFAULT nextval('public.detalle_total_id_seq'::regclass);


--
-- TOC entry 2864 (class 2604 OID 20259)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 2865 (class 2604 OID 20260)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 2866 (class 2604 OID 20261)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 2867 (class 2604 OID 20262)
-- Name: pagar_cuota_especial id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial ALTER COLUMN id SET DEFAULT nextval('public.pagar_cuota_especial_id_seq'::regclass);


--
-- TOC entry 2868 (class 2604 OID 20263)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2869 (class 2604 OID 20264)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 2870 (class 2604 OID 20265)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 2871 (class 2604 OID 20266)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2872 (class 2604 OID 20267)
-- Name: puente_interes_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_interes_condominio_id_seq'::regclass);


--
-- TOC entry 2873 (class 2604 OID 20268)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 2874 (class 2604 OID 20269)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 2875 (class 2604 OID 20270)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 2876 (class 2604 OID 20271)
-- Name: unidades id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades ALTER COLUMN id SET DEFAULT nextval('public.unidades_id_seq'::regclass);


--
-- TOC entry 2879 (class 2604 OID 20272)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3088 (class 0 OID 20071)
-- Dependencies: 196
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (82, 'Porton', '2020-05-01', 'Se hablo sobre el porton del condominio', '0012345678');
INSERT INTO public.asambleas VALUES (83, 'Vigilancia', '2020-05-04', 'Se hablo sobre la vigilancia', '0012345678');
INSERT INTO public.asambleas VALUES (84, 'Limpieza', '2020-05-06', 'Se hablo sobre la limpieza', '0012345678');
INSERT INTO public.asambleas VALUES (85, 'Camaras', '2020-05-09', 'Se hablo sobre las camaras', '0012345678');
INSERT INTO public.asambleas VALUES (86, 'Parque', '2020-05-11', 'Se hablo sobre el parque', '0012345678');
INSERT INTO public.asambleas VALUES (87, 'Niños en la calle', '2020-05-02', 'Se hablo sobre los niños en la calle', '0123456789');
INSERT INTO public.asambleas VALUES (88, 'Piscina', '2020-05-04', 'Se hablo sobre la piscina', '0123456789');
INSERT INTO public.asambleas VALUES (89, 'Limpieza', '2020-05-06', 'Se hablo sobre la limpieza', '0123456789');
INSERT INTO public.asambleas VALUES (90, 'Vigilancia', '2020-05-08', 'Se hablo sobre la vigilancia', '0123456789');
INSERT INTO public.asambleas VALUES (91, 'Camaras', '2020-05-06', 'Se hablo sobre las camaras', '0123456789');


--
-- TOC entry 3090 (class 0 OID 20079)
-- Dependencies: 198
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banco VALUES (3, 'Caribe', 1);
INSERT INTO public.banco VALUES (4, 'Provincial', 1);
INSERT INTO public.banco VALUES (6, 'Venezuela', 1);
INSERT INTO public.banco VALUES (1, 'Banesco', 1);
INSERT INTO public.banco VALUES (5, 'Mercantil', 1);
INSERT INTO public.banco VALUES (7, 'BOD', 1);
INSERT INTO public.banco VALUES (8, 'Fondo Comun', 1);
INSERT INTO public.banco VALUES (10, 'Fuerzas Armadas', 0);
INSERT INTO public.banco VALUES (11, 'Venezolana de Credito', 1);
INSERT INTO public.banco VALUES (9, 'Banplus', 1);


--
-- TOC entry 3092 (class 0 OID 20084)
-- Dependencies: 200
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (2, 'Mantenimiento', 'Conjunto de conceptos de mantenimiento', 1);
INSERT INTO public.categoriagasto VALUES (5, 'fsfsfdsfs', 'Conjunto de conceptos administrativo', 1);
INSERT INTO public.categoriagasto VALUES (3, 'Reparaciones', 'Conjunto de conceptos de reparaciones', 1);
INSERT INTO public.categoriagasto VALUES (4, 'Uso comun', 'Conjunto de conceptos de uso comun y consumo', 0);
INSERT INTO public.categoriagasto VALUES (6, 'asda', 'ddasda', 0);
INSERT INTO public.categoriagasto VALUES (1, 'Administrativo', 'Conjunto de conceptos administrativos', 1);


--
-- TOC entry 3094 (class 0 OID 20089)
-- Dependencies: 202
-- Data for Name: cierre_de_mes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cierre_de_mes VALUES (62, 5, 2020, '0123456789');


--
-- TOC entry 3096 (class 0 OID 20094)
-- Dependencies: 204
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3098 (class 0 OID 20102)
-- Dependencies: 206
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3100 (class 0 OID 20110)
-- Dependencies: 208
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (11, 'Provisión utilidades ', 'Utilidades del trabajador', 1, 1);
INSERT INTO public.concepto_gasto VALUES (10, 'Liquidación ', 'Liquidacion trabajador', 1, 1);
INSERT INTO public.concepto_gasto VALUES (12, 'Provisión vacaciones ', 'Vacaciones trabajador', 1, 1);
INSERT INTO public.concepto_gasto VALUES (13, 'Ley de alimentación', 'Pago alimentación', 1, 1);
INSERT INTO public.concepto_gasto VALUES (14, 'FAHO', 'Fondo de ahorro obligatorio de la vivienda', 1, 1);
INSERT INTO public.concepto_gasto VALUES (15, 'Caja chica', 'Caja chica', 1, 1);
INSERT INTO public.concepto_gasto VALUES (16, 'Mant. ascensores', 'Mantenimiento de los asecensores', 2, 1);
INSERT INTO public.concepto_gasto VALUES (17, 'Mant. hidroneumatico', 'Mantenimiento del hidroneumatico', 2, 1);
INSERT INTO public.concepto_gasto VALUES (18, 'Mant. porton electrico', 'Mantenimiento del porton electrico', 2, 1);
INSERT INTO public.concepto_gasto VALUES (19, 'Mant. cerco electrico', 'Mantenimiento del cerco electrico', 2, 1);
INSERT INTO public.concepto_gasto VALUES (20, 'Mant. areas verdes', 'Mantenimiento de areas verdes', 2, 1);
INSERT INTO public.concepto_gasto VALUES (21, 'Mant. piscina', 'Mantenimiento de la piscina', 2, 1);
INSERT INTO public.concepto_gasto VALUES (22, 'Mant. parque infantil', 'Mantenimiento del parque infantil', 2, 1);
INSERT INTO public.concepto_gasto VALUES (23, 'Mant. camaras vigilancia', 'Mantenimiento camaras de vigilancia', 2, 1);
INSERT INTO public.concepto_gasto VALUES (24, 'Mant. tuberias', 'Mantenimiento de tuberias ', 2, 1);
INSERT INTO public.concepto_gasto VALUES (25, 'Mant. estacionamiento', 'Mantenimiento del estacionamiento ', 2, 1);
INSERT INTO public.concepto_gasto VALUES (26, 'Mant. pintura edif. ', 'Pintura de areas comunes ', 2, 1);
INSERT INTO public.concepto_gasto VALUES (27, 'Mant. alumbrado elect. ', 'Mantenimiento del alumbrado elect.', 2, 1);
INSERT INTO public.concepto_gasto VALUES (28, 'Mant. compra prod. limp.', 'Gastos productos de limpieza', 2, 1);
INSERT INTO public.concepto_gasto VALUES (29, 'Mant. prod. limpieza ', 'Gasto otros productos', 2, 1);
INSERT INTO public.concepto_gasto VALUES (30, 'Repar. ascensores', 'Reparacion de los asecensores', 3, 1);
INSERT INTO public.concepto_gasto VALUES (31, 'Repar. tuberia', 'Reparacion de tuberias de aguas', 3, 1);
INSERT INTO public.concepto_gasto VALUES (32, 'Repar. porton', 'Reparacion del porton electrico', 3, 1);
INSERT INTO public.concepto_gasto VALUES (33, 'Repar. cerco elect.', 'Reparacion del cerco electrico', 3, 1);
INSERT INTO public.concepto_gasto VALUES (34, 'Repar. hidroneumatico.', 'Reparacion la bomba de agua', 3, 1);
INSERT INTO public.concepto_gasto VALUES (35, 'Repar. camaras.', 'Reparacion de camaras', 3, 1);
INSERT INTO public.concepto_gasto VALUES (36, 'Repar. electricas.', 'Reparacion/Sustitucion de componentes electricos', 3, 1);
INSERT INTO public.concepto_gasto VALUES (37, 'Consumo de electricidad', 'Consumo de electricidad', 3, 1);
INSERT INTO public.concepto_gasto VALUES (39, 'Cantv', 'Consumo de telefonos', 4, 1);
INSERT INTO public.concepto_gasto VALUES (40, 'HidroCapital', 'Consumo de agua', 4, 1);
INSERT INTO public.concepto_gasto VALUES (41, 'Vigilancia', 'Gastos por vigilancia', 4, 1);
INSERT INTO public.concepto_gasto VALUES (2, 'Salario conserje', 'Salario del conserje del condominio', 1, 1);
INSERT INTO public.concepto_gasto VALUES (3, 'Honorarios administradora', 'Honorarios administradora del condominio', 1, 1);
INSERT INTO public.concepto_gasto VALUES (4, 'Gastos oficina', 'Gastos en material de oficina', 1, 1);
INSERT INTO public.concepto_gasto VALUES (9, 'Bono Vacacional ', 'Bono vacacional trabajador', 1, 1);
INSERT INTO public.concepto_gasto VALUES (7, 'Prestaciones soc.', 'Prestaciones sociales trabajador', 1, 1);
INSERT INTO public.concepto_gasto VALUES (8, 'Seguro social', 'Seguro social trabajador', 1, 1);
INSERT INTO public.concepto_gasto VALUES (5, 'Gastos varios', 'Gastos fotocopias, impresiones, internet', 1, 1);
INSERT INTO public.concepto_gasto VALUES (6, 'Retiro prestaciones', 'Retiro de prestaciones sociales', 1, 1);
INSERT INTO public.concepto_gasto VALUES (1, 'Honorarios administrador', 'Honorarios del administrador del condominio', 1, 1);


--
-- TOC entry 3102 (class 0 OID 20115)
-- Dependencies: 210
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('0123456789', 'Condominio las Estaciones', '02540875963', 'CondolasEstaciones@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('0012345678', 'Condominio las Flores', '02540023568', 'CondolasFlores@hotmail.com', 1);


--
-- TOC entry 3103 (class 0 OID 20118)
-- Dependencies: 211
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('01234567', '01021554168465363997', 'Condominio las Estaciones', 'Corriente', 1, 1);
INSERT INTO public.cuenta VALUES ('00123456', '01032584545876546865', 'Condominio las Flores', 'Corriente', 9, 1);


--
-- TOC entry 3104 (class 0 OID 20121)
-- Dependencies: 212
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3106 (class 0 OID 20126)
-- Dependencies: 214
-- Data for Name: cuotas_especiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuotas_especiales VALUES (78, '17102635', 29, 'Alicuota', 5, 2020, 500, 500, 1, 89, '-', 'Mensualidad Completada', '0123456789', 0, NULL);
INSERT INTO public.cuotas_especiales VALUES (80, '17102635', 2, 'Alicuota', 5, 2020, 1000, 1000, 1, 0, '-', 'Mensualidad Completada', '0123456789', 0, NULL);
INSERT INTO public.cuotas_especiales VALUES (76, 'J-1001245215', 28, 'Alicuota', 5, 2020, 10000, 10000, 2, 0, '', 'Mensualidad en Proceso', '0123456789', 1, NULL);
INSERT INTO public.cuotas_especiales VALUES (79, 'J-1001245215', 26, 'Alicuota', 5, 2020, 1500, 1500, 1, 0, '-', 'Mensualidad Completada', '0123456789', 0, NULL);
INSERT INTO public.cuotas_especiales VALUES (77, '17102635', 26, 'Total de Inmuebles', 5, 2020, 2000, 2000, 1, 0, '', 'Mensualidad Completada', '0123456789', 0, NULL);


--
-- TOC entry 3108 (class 0 OID 20134)
-- Dependencies: 216
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_pagos VALUES (1593, 5, 2020, 312.5, 82, 183, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1594, 5, 2020, 300, 82, 184, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1595, 5, 2020, 300, 82, 185, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1596, 5, 2020, 300, 82, 186, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1597, 5, 2020, 287.5, 82, 187, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1598, 5, 2020, 104.16666666666667, 81, 183, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1599, 5, 2020, 100, 81, 184, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1600, 5, 2020, 100, 81, 185, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1601, 5, 2020, 100, 81, 186, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1602, 5, 2020, 95.833333333333343, 81, 187, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1603, 5, 2020, 312.5, 83, 183, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1604, 5, 2020, 300, 83, 184, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1605, 5, 2020, 300, 83, 185, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1606, 5, 2020, 300, 83, 186, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1607, 5, 2020, 287.5, 83, 187, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1608, 5, 2020, 104.16666666666667, 78, 183, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1609, 5, 2020, 100, 78, 184, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1610, 5, 2020, 100, 78, 185, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1611, 5, 2020, 100, 78, 186, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1612, 5, 2020, 95.833333333333343, 78, 187, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1613, 5, 2020, 208.33333333333334, 80, 183, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1614, 5, 2020, 200, 80, 184, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1615, 5, 2020, 200, 80, 185, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1616, 5, 2020, 200, 80, 186, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1617, 5, 2020, 191.66666666666669, 80, 187, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1618, 5, 2020, 1041.6666666666667, 76, 183, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1619, 5, 2020, 1000, 76, 184, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1620, 5, 2020, 1000, 76, 185, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1621, 5, 2020, 1000, 76, 186, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1622, 5, 2020, 958.33333333333337, 76, 187, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1623, 5, 2020, 312.5, 79, 183, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1624, 5, 2020, 300, 79, 184, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1625, 5, 2020, 300, 79, 185, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1626, 5, 2020, 300, 79, 186, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1627, 5, 2020, 287.5, 79, 187, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1628, 5, 2020, 400, 77, 183, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1629, 5, 2020, 400, 77, 184, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1630, 5, 2020, 400, 77, 185, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1631, 5, 2020, 400, 77, 186, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1632, 5, 2020, 400, 77, 187, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1633, 5, 2020, 150, 84, 183, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1634, 5, 2020, 100, 85, 187, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1635, 5, 2020, 250, 86, 184, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1636, 5, 2020, 50, 87, 185, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1637, 5, 2020, 270, 88, 185, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1638, 5, 2020, 270, 88, 186, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1639, 5, 2020, 83.875000000000014, 18, 183, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1640, 5, 2020, 81, 18, 184, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1641, 5, 2020, 81, 18, 185, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1642, 5, 2020, 81, 18, 186, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1643, 5, 2020, 78.125, 18, 187, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1644, 5, 2020, 167.75000000000003, 19, 183, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1645, 5, 2020, 162, 19, 184, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1646, 5, 2020, 162, 19, 185, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1647, 5, 2020, 162, 19, 186, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1648, 5, 2020, 156.25, 19, 187, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1649, 5, 2020, 55.916666666666679, 20, 183, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1650, 5, 2020, 54, 20, 184, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1651, 5, 2020, 54, 20, 185, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1652, 5, 2020, 54, 20, 186, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1653, 5, 2020, 52.083333333333343, 20, 187, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1654, 5, 2020, 55.916666666666679, 21, 183, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1655, 5, 2020, 54, 21, 184, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1656, 5, 2020, 54, 21, 185, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1657, 5, 2020, 54, 21, 186, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1658, 5, 2020, 52.083333333333343, 21, 187, 'Interes');


--
-- TOC entry 3110 (class 0 OID 20142)
-- Dependencies: 218
-- Data for Name: factura_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.factura_unidad VALUES (183, 3309.2916666666674, 5, 2020, 0.20833333333333334, 'Pendiente de Pago', 3309.2916666666674, 68);
INSERT INTO public.factura_unidad VALUES (184, 3301, 5, 2020, 0.20000000000000001, 'Pendiente de Pago', 3301, 70);
INSERT INTO public.factura_unidad VALUES (185, 3371, 5, 2020, 0.20000000000000001, 'Pendiente de Pago', 3371, 71);
INSERT INTO public.factura_unidad VALUES (186, 3321, 5, 2020, 0.20000000000000001, 'Pendiente de Pago', 3321, 72);
INSERT INTO public.factura_unidad VALUES (187, 3042.7083333333339, 5, 2020, 0.19166666666666668, 'Pendiente de Pago', 3042.7083333333339, 69);


--
-- TOC entry 3112 (class 0 OID 20147)
-- Dependencies: 220
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('Fondo de reserva', '2020-05-01', '-', '-', 25000, 25000, '0123456789', 59, 1);
INSERT INTO public.fondos VALUES ('Fondo de prestaciones sociales', '2020-05-01', '-', '-', 20000, 20000, '0123456789', 60, 1);
INSERT INTO public.fondos VALUES ('Fondo inicial', '2020-05-01', '-', '-', 20000, 20000, '0123456789', 61, 1);
INSERT INTO public.fondos VALUES ('Fondo de trabajo', '2020-05-01', '-', '-', 10000, 10000, '0123456789', 62, 1);
INSERT INTO public.fondos VALUES ('Fondo de gastos varios', '2020-05-01', '-', '-', 10000, 10000, '0012345678', 64, 1);
INSERT INTO public.fondos VALUES ('Fondo de trabajo', '2020-05-01', '-', '-', 15000, 15000, '0012345678', 65, 1);
INSERT INTO public.fondos VALUES ('Fondo de inicio', '2020-05-01', '-', '-', 15000, 15000, '0012345678', 66, 1);
INSERT INTO public.fondos VALUES ('Fondo de prestaciones sociales', '2020-05-01', '-', '-', 20000, 20000, '0012345678', 67, 1);
INSERT INTO public.fondos VALUES ('Fondo de reserva', '2020-05-01', '-', '-', 30000, 30000, '0012345678', 68, 1);
INSERT INTO public.fondos VALUES ('Fondo de gastos varios', '2020-05-01', '-', '-', 10000, 13309.2917, '0123456789', 63, 1);


--
-- TOC entry 3114 (class 0 OID 20155)
-- Dependencies: 222
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto_comun VALUES (84, 'Ordinario', 6, 2020, 123456, '67776', 'J-2457903215', 28, '', '2020-05-15', 'Pendiente', '0123456789', 123456);
INSERT INTO public.gasto_comun VALUES (82, 'Ordinario', 5, 2020, 1500, '0633564895', 'J-547859655', 23, '-', '2020-05-04', 'Procesado', '0123456789', 1500);
INSERT INTO public.gasto_comun VALUES (81, 'Ordinario', 5, 2020, 500, '012450024165', 'J-1001245215', 26, '-', '2020-05-01', 'Procesado', '0123456789', 500);
INSERT INTO public.gasto_comun VALUES (83, 'Ordinario', 5, 2020, 1500, '0633564895', '17102635', 2, '-', '2020-05-04', 'Procesado', '0123456789', 1500);


--
-- TOC entry 3116 (class 0 OID 20160)
-- Dependencies: 224
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (18, 'Interes Compensatorios', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (19, 'Interes Moratorios', 6, 'Activo', 1);
INSERT INTO public.interes VALUES (20, 'Interes Legal', 2, 'Activo', 1);
INSERT INTO public.interes VALUES (21, 'Interes Convencional', 2, 'Activo', 1);


--
-- TOC entry 3118 (class 0 OID 20165)
-- Dependencies: 226
-- Data for Name: pagar_cuota_especial; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3120 (class 0 OID 20173)
-- Dependencies: 228
-- Data for Name: propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietarios VALUES ('8517596', 'Blanca', 'Singer', '04127616516', 'BRSM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('20888725', 'Maria', 'Osorio', '04127909117', 'MariaO@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('18965742', 'Maix', 'Osorio', '04127845963', 'MJOS@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('1424801', 'Blanca', 'Mujica', '04245789654', 'BlancaM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('24578966', 'Jose', 'Mujica', '04147859623', 'JM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('9245638', 'Anna', 'Guerra', '04265489654', 'AG@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('7451289', 'Jose', 'Ramirez', '04125048965', 'JR@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('24589635', 'Alejandro', 'Guerra', '04245896574', 'AG@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('10245015', 'Josefa', 'Arteaga', '04145789642', 'JA@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('7458965', 'Pedro', 'Alvarado', '04265896547', 'PA@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('14256895', 'Johanna', 'Perez', '04125487965', 'J_P@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('10921542', 'Andrea', 'Suarez', '04123214569', 'AREZ@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('27328852', 'Maryorith', 'Singer', '04125084544', 'MS@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('26943430', 'Samuel', 'Perez', '04245222312', 'SP@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('1545698', 'Juan', 'Moreno', '04124587963', 'JMM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('9456874', 'Luis', 'Garcia', '04124548756', 'LG9@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('5484633', 'Alvaro', 'Garcia', '04145753524', 'AG9@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('15211450', 'Sofia', 'Nuñez', '04140281401', 'SN@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('8854237', 'Soraida', 'Alvarado', '04165855431', 'SA@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('18346152', 'Sofia', 'Hernandez', '04126958756', 'SH@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('1425368', 'Alberto', 'Contreras', '04247589000', 'AC@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('4236500', 'Luisangel', 'Montaner', '04267845963', 'LM@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('9578450', 'Angel', 'Montalgo', '04160020354', 'AM@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('4125868', 'Valeria', 'Santander', '04147895662', 'VS@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('16254700', 'Alex', 'Gutierrez', '04169012145', 'AG@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('18547895', 'Alexa', 'Hernandez', '04169011234', 'AH@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('15457896', 'Carolina', 'Herrera', '04168050211', 'CH1@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('16547896', 'Fernando', 'Rodriguez', '04142010258', 'FR@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('20145271', 'Axel', 'Osorio', '04142000146', 'AO@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('2343', 'sdsf', 'sfdf', '424', 'sdfs', 1);
INSERT INTO public.propietarios VALUES ('23545478', 'Josefa', 'Camejo', '04245478624', 'JC@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('12354875', 'Carlos', 'Rodriguez', '04165470021', 'CR@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('3321554', 'Julian', 'Gomez', '04125081201', 'JG@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('14528796', 'Anais', 'Escudero', '04127845963', 'AE@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('24578965', 'Alejandro', 'Perez', '04248569354', 'APerez@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('14156247', 'Maria', 'Mujica', '04124863259', 'MM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('6254789', 'Pablo', 'Bastardo', '04247877956', 'PB@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('9457854', 'Linda', 'Morillo', '04165484795', 'LM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('13245789', 'Valentina', 'Morillo', '04125896321', 'VM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('52145785', 'Marisol', 'Puertas', '04162547020', 'MP@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('10145236', 'Martin', 'Regalado', '04142563258', 'MR@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('4321554', 'Juana', 'Perez', '04125084544', 'JPP@hotmail.com', 0);
INSERT INTO public.propietarios VALUES ('24154789', 'Anthony', 'Suarez', '04123625244', 'AS@gmail.com', 0);
INSERT INTO public.propietarios VALUES ('1245637', 'Juan', 'Barrios', '0', 'JB@ho.com', 0);
INSERT INTO public.propietarios VALUES ('3556789', 'Maria', 'Orteaga', '0', 'm', 0);
INSERT INTO public.propietarios VALUES ('17548569', 'Jorge', 'Sanchez', '04245896354', 'JS@gmail.com', 0);
INSERT INTO public.propietarios VALUES ('11444254', 'Jose', 'Vargas', '04142040896', 'JC@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('73211450', 'Carlos', 'Ramirez', '04240081201', 'CR1@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('20457896', 'Monica', 'Montalban', '04128569354', 'MM@hotmail.com', 1);
INSERT INTO public.propietarios VALUES ('19306051', 'Simon', 'Herrera', '04240058756', 'SH@gmail.com', 1);
INSERT INTO public.propietarios VALUES ('5478965', 'Marisol', 'Souza', '04124002259', 'MS1@hotmail.com', 0);


--
-- TOC entry 3121 (class 0 OID 20176)
-- Dependencies: 229
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
-- TOC entry 3122 (class 0 OID 20182)
-- Dependencies: 230
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asamblea_propietario VALUES (259, 82, '10245015');
INSERT INTO public.puente_asamblea_propietario VALUES (260, 82, '24589635');
INSERT INTO public.puente_asamblea_propietario VALUES (261, 83, '10245015');
INSERT INTO public.puente_asamblea_propietario VALUES (262, 83, '24589635');
INSERT INTO public.puente_asamblea_propietario VALUES (263, 83, '7451289');
INSERT INTO public.puente_asamblea_propietario VALUES (264, 83, '9245638');
INSERT INTO public.puente_asamblea_propietario VALUES (265, 84, '24589635');
INSERT INTO public.puente_asamblea_propietario VALUES (266, 84, '7451289');
INSERT INTO public.puente_asamblea_propietario VALUES (267, 84, '9245638');
INSERT INTO public.puente_asamblea_propietario VALUES (268, 85, '10245015');
INSERT INTO public.puente_asamblea_propietario VALUES (269, 85, '24589635');
INSERT INTO public.puente_asamblea_propietario VALUES (270, 85, '9245638');
INSERT INTO public.puente_asamblea_propietario VALUES (271, 86, '10245015');
INSERT INTO public.puente_asamblea_propietario VALUES (272, 86, '24589635');
INSERT INTO public.puente_asamblea_propietario VALUES (273, 86, '7451289');
INSERT INTO public.puente_asamblea_propietario VALUES (274, 86, '9245638');
INSERT INTO public.puente_asamblea_propietario VALUES (275, 87, '18965742');
INSERT INTO public.puente_asamblea_propietario VALUES (276, 87, '20888725');
INSERT INTO public.puente_asamblea_propietario VALUES (277, 87, '24578966');
INSERT INTO public.puente_asamblea_propietario VALUES (278, 88, '1424801');
INSERT INTO public.puente_asamblea_propietario VALUES (279, 88, '18965742');
INSERT INTO public.puente_asamblea_propietario VALUES (280, 88, '20888725');
INSERT INTO public.puente_asamblea_propietario VALUES (281, 88, '24578966');
INSERT INTO public.puente_asamblea_propietario VALUES (282, 88, '8517596');
INSERT INTO public.puente_asamblea_propietario VALUES (283, 89, '1424801');
INSERT INTO public.puente_asamblea_propietario VALUES (284, 89, '18965742');
INSERT INTO public.puente_asamblea_propietario VALUES (285, 89, '20888725');
INSERT INTO public.puente_asamblea_propietario VALUES (286, 89, '24578966');
INSERT INTO public.puente_asamblea_propietario VALUES (287, 89, '8517596');
INSERT INTO public.puente_asamblea_propietario VALUES (288, 90, '1424801');
INSERT INTO public.puente_asamblea_propietario VALUES (289, 90, '18965742');
INSERT INTO public.puente_asamblea_propietario VALUES (290, 90, '20888725');
INSERT INTO public.puente_asamblea_propietario VALUES (291, 90, '8517596');
INSERT INTO public.puente_asamblea_propietario VALUES (292, 91, '18965742');
INSERT INTO public.puente_asamblea_propietario VALUES (293, 91, '20888725');
INSERT INTO public.puente_asamblea_propietario VALUES (294, 91, '24578966');


--
-- TOC entry 3124 (class 0 OID 20187)
-- Dependencies: 232
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3126 (class 0 OID 20192)
-- Dependencies: 234
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3128 (class 0 OID 20200)
-- Dependencies: 236
-- Data for Name: puente_condominio_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_condominio_cuenta VALUES (142, '01025487596584758945', '0123456789', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (143, '01052458795254653322', '0123456789', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (141, '01020045120268985654', '0123456789', 0);
INSERT INTO public.puente_condominio_cuenta VALUES (144, '01020045120268985654', '0123456789', 0);
INSERT INTO public.puente_condominio_cuenta VALUES (145, '01020045120268985654', '0012345678', 0);
INSERT INTO public.puente_condominio_cuenta VALUES (146, '01020045120268985654', '0123456789', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (147, '01020045120268985654', '0012345678', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (148, '01021554168465363997', '0123456789', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (149, '01032584545876546865', '0012345678', 1);


--
-- TOC entry 3130 (class 0 OID 20205)
-- Dependencies: 238
-- Data for Name: puente_interes_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_interes_condominio VALUES (97, '0123456789', 18, 1);
INSERT INTO public.puente_interes_condominio VALUES (98, '0012345678', 18, 1);
INSERT INTO public.puente_interes_condominio VALUES (99, '0123456789', 19, 1);
INSERT INTO public.puente_interes_condominio VALUES (100, '0012345678', 19, 1);
INSERT INTO public.puente_interes_condominio VALUES (101, '0123456789', 20, 1);
INSERT INTO public.puente_interes_condominio VALUES (102, '0012345678', 20, 1);
INSERT INTO public.puente_interes_condominio VALUES (103, '0123456789', 21, 1);
INSERT INTO public.puente_interes_condominio VALUES (104, '0012345678', 21, 1);


--
-- TOC entry 3132 (class 0 OID 20210)
-- Dependencies: 240
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (196, 84, 68);
INSERT INTO public.puente_sancion_unidad VALUES (197, 85, 69);
INSERT INTO public.puente_sancion_unidad VALUES (198, 86, 70);
INSERT INTO public.puente_sancion_unidad VALUES (199, 87, 71);
INSERT INTO public.puente_sancion_unidad VALUES (200, 88, 71);
INSERT INTO public.puente_sancion_unidad VALUES (201, 88, 72);


--
-- TOC entry 3134 (class 0 OID 20215)
-- Dependencies: 242
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (89, '8517596', 68, '2020-05-13', NULL, '1', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (90, '20888725', 69, '2020-05-13', NULL, '2', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (91, '18965742', 70, '2020-05-13', NULL, '3', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (92, '1424801', 71, '2020-05-13', NULL, '4', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (93, '24578966', 72, '2020-05-13', NULL, '5', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (94, '9245638', 73, '2020-05-13', NULL, '6', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (95, '7451289', 73, '2020-05-13', NULL, '6', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (97, '9245638', 75, '2020-05-13', NULL, '6', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (98, '7451289', 75, '2020-05-13', NULL, '6', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (96, '7451289', 74, '2020-05-13', NULL, '8', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (99, '24589635', 76, '2020-05-13', NULL, '9', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (100, '10245015', 77, '2020-05-13', NULL, '10', 1, 1);


--
-- TOC entry 3136 (class 0 OID 20220)
-- Dependencies: 244
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (84, 'Multa', 5, 2020, 150, '-', 'Procesado');
INSERT INTO public.sancion VALUES (85, 'Multa', 5, 2020, 100, '-', 'Procesado');
INSERT INTO public.sancion VALUES (86, 'Multa', 5, 2020, 250, '-', 'Procesado');
INSERT INTO public.sancion VALUES (87, 'Multa', 5, 2020, 50, '-', 'Procesado');
INSERT INTO public.sancion VALUES (88, 'Interes de mora', 5, 2020, 10, '-', 'Procesado');


--
-- TOC entry 3138 (class 0 OID 20225)
-- Dependencies: 246
-- Data for Name: unidades; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidades VALUES ('1', 'conjunto verano, casa 1', 250, 68, '0123456789', 1);
INSERT INTO public.unidades VALUES ('3', 'conjunto primavera, casa 3', 240, 70, '0123456789', 1);
INSERT INTO public.unidades VALUES ('4', 'conjunto primavera, casa 4', 240, 71, '0123456789', 1);
INSERT INTO public.unidades VALUES ('5', 'conjunto invierno, casa 5', 240, 72, '0123456789', 1);
INSERT INTO public.unidades VALUES ('2', 'conjunto primavera, casa 2', 230, 69, '0123456789', 1);
INSERT INTO public.unidades VALUES ('01', 'calle 3, casa 01', 200, 73, '0012345678', 1);
INSERT INTO public.unidades VALUES ('02', 'calle 2, casa 02', 215, 75, '0012345678', 1);
INSERT INTO public.unidades VALUES ('03', 'calle 3, casa 03', 200, 74, '0012345678', 1);
INSERT INTO public.unidades VALUES ('04', 'calle 2, casa 04', 200, 76, '0012345678', 1);
INSERT INTO public.unidades VALUES ('05', 'calle 5, casa 05', 200, 77, '0012345678', 1);


--
-- TOC entry 3140 (class 0 OID 20230)
-- Dependencies: 248
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3141 (class 0 OID 20238)
-- Dependencies: 250
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3143 (class 0 OID 20245)
-- Dependencies: 252
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3177 (class 0 OID 0)
-- Dependencies: 197
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 91, true);


--
-- TOC entry 3178 (class 0 OID 0)
-- Dependencies: 199
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 11, true);


--
-- TOC entry 3179 (class 0 OID 0)
-- Dependencies: 201
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 6, true);


--
-- TOC entry 3180 (class 0 OID 0)
-- Dependencies: 203
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cierre_de_mes_id_seq', 62, true);


--
-- TOC entry 3181 (class 0 OID 0)
-- Dependencies: 205
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 14, true);


--
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 207
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 53, true);


--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 209
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 41, true);


--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 213
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 215
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 80, true);


--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 217
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1658, true);


--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 219
-- Name: detalle_total_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_total_id_seq', 187, true);


--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 221
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 68, true);


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 223
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 84, true);


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 225
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 21, true);


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 227
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagar_cuota_especial_id_seq', 1, false);


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 231
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 294, true);


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 233
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 11, true);


--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 235
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 503, true);


--
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 237
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 149, true);


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 239
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_interes_condominio_id_seq', 104, true);


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 241
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 201, true);


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 243
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 100, true);


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 245
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 88, true);


--
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 247
-- Name: unidades_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidades_id_seq', 77, true);


--
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 251
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 52, true);


--
-- TOC entry 2881 (class 2606 OID 20274)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 2883 (class 2606 OID 20276)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 2885 (class 2606 OID 20278)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2887 (class 2606 OID 20280)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 2889 (class 2606 OID 20282)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2891 (class 2606 OID 20284)
-- Name: cierre_de_mes cierre_de_mes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_pkey PRIMARY KEY (id);


--
-- TOC entry 2893 (class 2606 OID 20286)
-- Name: cobro_unidad cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 2895 (class 2606 OID 20288)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 2897 (class 2606 OID 20290)
-- Name: concepto_gasto concepto_gasto_nom_concepto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_nom_concepto_key UNIQUE (nom_concepto);


--
-- TOC entry 2899 (class 2606 OID 20292)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2901 (class 2606 OID 20294)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2903 (class 2606 OID 20296)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 2905 (class 2606 OID 20298)
-- Name: cuenta cuenta_n_cuenta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_key UNIQUE (n_cuenta);


--
-- TOC entry 2909 (class 2606 OID 20300)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 2907 (class 2606 OID 20302)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 2911 (class 2606 OID 20304)
-- Name: cuotas_especiales cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2913 (class 2606 OID 20306)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 2915 (class 2606 OID 20308)
-- Name: factura_unidad detalle_total_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura_unidad
    ADD CONSTRAINT detalle_total_pkey PRIMARY KEY (id);


--
-- TOC entry 2917 (class 2606 OID 20310)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 2919 (class 2606 OID 20312)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 2921 (class 2606 OID 20314)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2923 (class 2606 OID 20316)
-- Name: pagar_cuota_especial pagar_cuota_especial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_pkey PRIMARY KEY (id);


--
-- TOC entry 2925 (class 2606 OID 20318)
-- Name: propietarios propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietarios
    ADD CONSTRAINT propietarios_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2927 (class 2606 OID 20320)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 2929 (class 2606 OID 20322)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2931 (class 2606 OID 20324)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 2933 (class 2606 OID 20326)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 2935 (class 2606 OID 20328)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2937 (class 2606 OID 20330)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 2939 (class 2606 OID 20332)
-- Name: puente_interes_condominio puente_interes_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio
    ADD CONSTRAINT puente_interes_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 20334)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 20336)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2945 (class 2606 OID 20338)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 20340)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (id);


--
-- TOC entry 2949 (class 2606 OID 20342)
-- Name: usuario usuario_cedula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_key UNIQUE (cedula);


--
-- TOC entry 2951 (class 2606 OID 20344)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2953 (class 2606 OID 20346)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 2955 (class 2606 OID 20348)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 2957 (class 2606 OID 20350)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2958 (class 2606 OID 20351)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 2959 (class 2606 OID 20356)
-- Name: cuenta_pagar cuenta_pagar_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 2960 (class 2606 OID 20361)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 2961 (class 2606 OID 20366)
-- Name: cuenta_pagar cuenta_pagar_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 2962 (class 2606 OID 20371)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 2963 (class 2606 OID 20376)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_cuota_e_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_cuota_e_fkey FOREIGN KEY (id_cuota_e) REFERENCES public.cuotas_especiales(id);


--
-- TOC entry 2964 (class 2606 OID 20381)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 2965 (class 2606 OID 20386)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.visitante(cedula);


-- Completed on 2020-05-14 12:15:18

--
-- PostgreSQL database dump complete
--

