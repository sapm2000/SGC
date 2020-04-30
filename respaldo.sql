--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-29 21:28:19

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 213 (class 1259 OID 24686)
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
-- TOC entry 212 (class 1259 OID 24684)
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
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 212
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 205 (class 1259 OID 24617)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 24615)
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
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 204
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 222 (class 1259 OID 24743)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24741)
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
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 221
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 248 (class 1259 OID 24997)
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
-- TOC entry 247 (class 1259 OID 24995)
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
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 247
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cierre_de_mes_id_seq OWNED BY public.cierre_de_mes.id;


--
-- TOC entry 250 (class 1259 OID 25275)
-- Name: cobro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cobro (
    id integer NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(500) NOT NULL,
    id_unidad character varying(150) NOT NULL,
    id_cuenta character varying(20) NOT NULL,
    forma_pago character varying(150) NOT NULL,
    referencia character varying(50) NOT NULL,
    fecha date NOT NULL,
    id_fondo bigint NOT NULL
);


ALTER TABLE public.cobro OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 25273)
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
-- TOC entry 3140 (class 0 OID 0)
-- Dependencies: 249
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro.id;


--
-- TOC entry 228 (class 1259 OID 24777)
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
-- TOC entry 227 (class 1259 OID 24775)
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
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 227
-- Name: comunicados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id;


--
-- TOC entry 224 (class 1259 OID 24753)
-- Name: concepto_gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gasto (
    id integer NOT NULL,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL
);


ALTER TABLE public.concepto_gasto OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24751)
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
-- TOC entry 3142 (class 0 OID 0)
-- Dependencies: 223
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 203 (class 1259 OID 16449)
-- Name: condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.condominio (
    rif character varying NOT NULL,
    razon_social character varying NOT NULL,
    telefono character varying NOT NULL,
    correo_electronico character varying NOT NULL
);


ALTER TABLE public.condominio OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 24625)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    cedula character varying(8) NOT NULL,
    n_cuenta character varying(20) NOT NULL,
    beneficiario character varying(50) NOT NULL,
    tipo character varying(10) NOT NULL,
    id_banco bigint NOT NULL
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 24810)
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
    n_mese_restante bigint
);


ALTER TABLE public.cuotas_especiales OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 24808)
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
-- TOC entry 3143 (class 0 OID 0)
-- Dependencies: 231
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.cuotas_especiales.id;


--
-- TOC entry 240 (class 1259 OID 24845)
-- Name: detalle_cuotas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_cuotas (
    id integer NOT NULL,
    id_unidad character varying(150) NOT NULL,
    id_cuota bigint NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    id_condominio character varying(15) NOT NULL,
    monto double precision NOT NULL
);


ALTER TABLE public.detalle_cuotas OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 24843)
-- Name: detalle_cuotas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_cuotas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.detalle_cuotas_id_seq OWNER TO postgres;

--
-- TOC entry 3144 (class 0 OID 0)
-- Dependencies: 239
-- Name: detalle_cuotas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_cuotas_id_seq OWNED BY public.detalle_cuotas.id;


--
-- TOC entry 244 (class 1259 OID 24917)
-- Name: detalle_interes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_interes (
    id integer NOT NULL,
    id_unidad character varying(150) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    id_interes bigint NOT NULL,
    id_condominio character varying(15) NOT NULL
);


ALTER TABLE public.detalle_interes OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 24915)
-- Name: detalle_interes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_interes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.detalle_interes_id_seq OWNER TO postgres;

--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 243
-- Name: detalle_interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_interes_id_seq OWNED BY public.detalle_interes.id;


--
-- TOC entry 238 (class 1259 OID 24837)
-- Name: detalle_pagos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_pagos (
    id integer NOT NULL,
    id_unidad character varying(150) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    id_gasto bigint,
    id_condominio character varying(15)
);


ALTER TABLE public.detalle_pagos OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 24835)
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
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 237
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 242 (class 1259 OID 24861)
-- Name: detalle_sancion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_sancion (
    id integer NOT NULL,
    id_unidad character varying(150) NOT NULL,
    id_sancion bigint NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    id_condominio character varying(15) NOT NULL,
    monto double precision NOT NULL
);


ALTER TABLE public.detalle_sancion OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 24859)
-- Name: detalle_sancion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_sancion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.detalle_sancion_id_seq OWNER TO postgres;

--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 241
-- Name: detalle_sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_sancion_id_seq OWNED BY public.detalle_sancion.id;


--
-- TOC entry 246 (class 1259 OID 24946)
-- Name: detalle_total; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_total (
    id integer NOT NULL,
    id_unidad character varying(150) NOT NULL,
    monto double precision NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    alicuota double precision NOT NULL,
    estado character varying(50),
    id_condominio character varying(15),
    saldo_restante double precision
);


ALTER TABLE public.detalle_total OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 24944)
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
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 245
-- Name: detalle_total_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_total_id_seq OWNED BY public.detalle_total.id;


--
-- TOC entry 216 (class 1259 OID 24705)
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
    id integer NOT NULL
);


ALTER TABLE public.fondos OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 25284)
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
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 251
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 226 (class 1259 OID 24769)
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
    estado character varying(15) NOT NULL,
    id_condominio character varying(15),
    saldo double precision
);


ALTER TABLE public.gasto_comun OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 24767)
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
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 225
-- Name: gasto_comun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_comun_id_seq OWNED BY public.gasto_comun.id;


--
-- TOC entry 234 (class 1259 OID 24821)
-- Name: interes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interes (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    factor double precision NOT NULL,
    estado character varying(20) NOT NULL
);


ALTER TABLE public.interes OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 24819)
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
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 233
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 210 (class 1259 OID 24664)
-- Name: propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietarios (
    cedula character varying(8) NOT NULL,
    nombre character varying(30) NOT NULL,
    apellido character varying(30) NOT NULL,
    telefono character varying(12) NOT NULL,
    correo character varying(100) NOT NULL,
    id_condominio character varying(20)
);


ALTER TABLE public.propietarios OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 24654)
-- Name: proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proveedores (
    cedula character varying(15) NOT NULL,
    nombre character varying(40) NOT NULL,
    telefono character varying(15) NOT NULL,
    correo character varying(40) NOT NULL,
    contacto character varying(60) NOT NULL,
    direccion character varying(500) NOT NULL
);


ALTER TABLE public.proveedores OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24697)
-- Name: puente_asamblea_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asamblea_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.puente_asamblea_propietario OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 24695)
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
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 214
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


--
-- TOC entry 253 (class 1259 OID 25297)
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
-- TOC entry 252 (class 1259 OID 25295)
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
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 230 (class 1259 OID 24791)
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
-- TOC entry 229 (class 1259 OID 24789)
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
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 229
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 208 (class 1259 OID 24634)
-- Name: puente_condominio_cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_condominio_cuenta (
    id integer NOT NULL,
    id_cuenta character varying(20),
    id_condominio character varying(20)
);


ALTER TABLE public.puente_condominio_cuenta OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 24632)
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
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 207
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


--
-- TOC entry 236 (class 1259 OID 24829)
-- Name: puente_interes_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_interes_condominio (
    id integer NOT NULL,
    id_condominio character varying(15) NOT NULL,
    id_interes bigint NOT NULL
);


ALTER TABLE public.puente_interes_condominio OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 24827)
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
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 235
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_interes_condominio_id_seq OWNED BY public.puente_interes_condominio.id;


--
-- TOC entry 220 (class 1259 OID 24725)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad character varying(50) NOT NULL
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24723)
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
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 219
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 218 (class 1259 OID 24717)
-- Name: sancion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sancion (
    id integer NOT NULL,
    tipo character varying(40) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(200) NOT NULL,
    id_condominio character varying(15) NOT NULL,
    estado character varying(20)
);


ALTER TABLE public.sancion OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24715)
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
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 217
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 211 (class 1259 OID 24679)
-- Name: unidades; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidades (
    n_unidad character varying(10) NOT NULL,
    direccion character varying(200) NOT NULL,
    area bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.unidades OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16430)
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
-- TOC entry 2853 (class 2604 OID 24689)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2851 (class 2604 OID 24620)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2858 (class 2604 OID 24746)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 2871 (class 2604 OID 25000)
-- Name: cierre_de_mes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes ALTER COLUMN id SET DEFAULT nextval('public.cierre_de_mes_id_seq'::regclass);


--
-- TOC entry 2872 (class 2604 OID 25278)
-- Name: cobro id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 2861 (class 2604 OID 24780)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 2859 (class 2604 OID 24756)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 2863 (class 2604 OID 24813)
-- Name: cuotas_especiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 2867 (class 2604 OID 24848)
-- Name: detalle_cuotas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_cuotas ALTER COLUMN id SET DEFAULT nextval('public.detalle_cuotas_id_seq'::regclass);


--
-- TOC entry 2869 (class 2604 OID 24920)
-- Name: detalle_interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_interes ALTER COLUMN id SET DEFAULT nextval('public.detalle_interes_id_seq'::regclass);


--
-- TOC entry 2866 (class 2604 OID 24840)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 2868 (class 2604 OID 24864)
-- Name: detalle_sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_sancion ALTER COLUMN id SET DEFAULT nextval('public.detalle_sancion_id_seq'::regclass);


--
-- TOC entry 2870 (class 2604 OID 24949)
-- Name: detalle_total id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_total ALTER COLUMN id SET DEFAULT nextval('public.detalle_total_id_seq'::regclass);


--
-- TOC entry 2855 (class 2604 OID 25286)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 2860 (class 2604 OID 24772)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 2864 (class 2604 OID 24824)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 2854 (class 2604 OID 24700)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2873 (class 2604 OID 25300)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 2862 (class 2604 OID 24794)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 2852 (class 2604 OID 24637)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2865 (class 2604 OID 24832)
-- Name: puente_interes_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_interes_condominio_id_seq'::regclass);


--
-- TOC entry 2857 (class 2604 OID 24728)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 2856 (class 2604 OID 24720)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3090 (class 0 OID 24686)
-- Dependencies: 213
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (23, 'da', '2020-04-04', 'd', '111');


--
-- TOC entry 3082 (class 0 OID 24617)
-- Dependencies: 205
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banco VALUES (1, 'sfs');
INSERT INTO public.banco VALUES (3, 'caribe');
INSERT INTO public.banco VALUES (5, 'dsfs');
INSERT INTO public.banco VALUES (4, 'provincial');
INSERT INTO public.banco VALUES (6, 'venezuela');


--
-- TOC entry 3099 (class 0 OID 24743)
-- Dependencies: 222
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (1, 'dadasd', 'asda');


--
-- TOC entry 3125 (class 0 OID 24997)
-- Dependencies: 248
-- Data for Name: cierre_de_mes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cierre_de_mes VALUES (36, 3, 2020, '111');
INSERT INTO public.cierre_de_mes VALUES (37, 4, 2020, '111');
INSERT INTO public.cierre_de_mes VALUES (38, 2, 2020, '111');


--
-- TOC entry 3127 (class 0 OID 25275)
-- Dependencies: 250
-- Data for Name: cobro; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cobro VALUES (7, 1500, '231', '2-013', 'sdfsf', 'Transferencia', '2132', '2020-04-10', 2);
INSERT INTO public.cobro VALUES (8, 15000, 'pago meses atrasados', '123', '423424423', 'Transferencia', '256450', '2020-04-29', 1);


--
-- TOC entry 3105 (class 0 OID 24777)
-- Dependencies: 228
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.comunicados VALUES (1, 'a', 's', '111');
INSERT INTO public.comunicados VALUES (2, 'asdaa', 'asdadad', '111');
INSERT INTO public.comunicados VALUES (3, 'sad', 'dsd', '111');


--
-- TOC entry 3101 (class 0 OID 24753)
-- Dependencies: 224
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (1, 'de', 'sadds', 1);


--
-- TOC entry 3080 (class 0 OID 16449)
-- Dependencies: 203
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('13131312', 'sdadad', '123132', 'asdasdasdadsa');
INSERT INTO public.condominio VALUES ('111', 'holas', '24334324', 'sa');


--
-- TOC entry 3083 (class 0 OID 24625)
-- Dependencies: 206
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('sdsd', 'dasdasa', 'dsad', 'Corriente', 4);
INSERT INTO public.cuenta VALUES ('dsfdsfsf', 'fsf', 'fdf', 'Ahorro', 5);
INSERT INTO public.cuenta VALUES ('sfdsfs', 'sdfsf', 'f', 'Ahorro', 4);
INSERT INTO public.cuenta VALUES ('3213d', 'ddfsf', 'f', 'Corriente', 5);
INSERT INTO public.cuenta VALUES ('255325f', '423424423', 'sd', 'Ahorro', 4);
INSERT INTO public.cuenta VALUES ('31313e', 'qweq', 'ee', 'Ahorro', 3);
INSERT INTO public.cuenta VALUES ('werr', '09876543', 'dfg', 'Ahorro', 5);
INSERT INTO public.cuenta VALUES ('sadd', 'dsa', 'd', 'Ahorro', 1);
INSERT INTO public.cuenta VALUES ('dsad', 'dsaddsd', 'ddd', 'Ahorro', 1);
INSERT INTO public.cuenta VALUES ('sdff', 'fsflkjhgfds', 'ff', 'Ahorro', 5);
INSERT INTO public.cuenta VALUES ('sdada', 'dsad', 'ddd', 'Ahorro', 4);
INSERT INTO public.cuenta VALUES ('sdd', 'sdsasdd', 'dsddsd', 'Ahorro', 5);


--
-- TOC entry 3109 (class 0 OID 24810)
-- Dependencies: 232
-- Data for Name: cuotas_especiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuotas_especiales VALUES (3, '231231', 1, 'Total de Inmuebles', 4, 2020, 2000000, 2000000, 1, 0, '132132', 'Pendiente', '13131312', NULL);
INSERT INTO public.cuotas_especiales VALUES (4, '342', 1, 'Alicuota', 5, 2020, 5000000, 5000000, 2, 0, 'xcccccc', 'Pendiente', '13131312', NULL);
INSERT INTO public.cuotas_especiales VALUES (5, '231231', 1, 'Total de Inmuebles', 7, 2020, 65700000, 65700000, 1, 0, 'dddddd', 'Pendiente', '13131312', NULL);
INSERT INTO public.cuotas_especiales VALUES (6, '342', 1, 'Alicuota', 8, 2020, 120000000, 120000000, 1, 0, 'rrrrrr', 'Pendiente', '13131312', NULL);
INSERT INTO public.cuotas_especiales VALUES (15, '231231', 1, 'Alicuota', 4, 2020, 10000, 10000, 1, 0, '', 'Mensualidad completada', '111', 0);
INSERT INTO public.cuotas_especiales VALUES (8, '342', 1, 'Total de Inmuebles', 3, 2020, 100, 100, 2, 23, 'nt', 'Mensualidad completada', '111', 0);
INSERT INTO public.cuotas_especiales VALUES (16, '231231', 0, '<html>Total <br> de Inmuebles</html>', 5, 2020, 200, 200, 3, 0, '', 'Pendiente', '111', 3);
INSERT INTO public.cuotas_especiales VALUES (17, '342', 1, '<html>Total <br> de Inmuebles</html>', 5, 2020, 200, 200, 2, 0, 'dfsd', 'Pendiente', '111', 2);
INSERT INTO public.cuotas_especiales VALUES (1, '231231', 1, 'Alicuota', 2, 2020, 1000, 1000, 2, 23, 'dsa', '<html>Mensualidad <br> Completada</html>', '111', 0);
INSERT INTO public.cuotas_especiales VALUES (7, '231231', 1, 'Total de Inmuebles', 9, 2020, 300000000, 300000000, 3, 0, 'hhjjhhg', 'Pendiente', '13131312', 3);


--
-- TOC entry 3117 (class 0 OID 24845)
-- Dependencies: 240
-- Data for Name: detalle_cuotas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_cuotas VALUES (189, '2-013', 8, 3, 2020, '111', 25);
INSERT INTO public.detalle_cuotas VALUES (190, '123', 8, 3, 2020, '111', 25);
INSERT INTO public.detalle_cuotas VALUES (191, '2-013', 1, 3, 2020, '111', 250);
INSERT INTO public.detalle_cuotas VALUES (192, '123', 1, 3, 2020, '111', 250);
INSERT INTO public.detalle_cuotas VALUES (193, '2-013', 15, 4, 2020, '111', 5000);
INSERT INTO public.detalle_cuotas VALUES (194, '123', 15, 4, 2020, '111', 5000);
INSERT INTO public.detalle_cuotas VALUES (195, '2-013', 8, 4, 2020, '111', 25);
INSERT INTO public.detalle_cuotas VALUES (196, '123', 8, 4, 2020, '111', 25);
INSERT INTO public.detalle_cuotas VALUES (197, '2-013', 1, 2, 2020, '111', 250);
INSERT INTO public.detalle_cuotas VALUES (198, '123', 1, 2, 2020, '111', 250);


--
-- TOC entry 3121 (class 0 OID 24917)
-- Dependencies: 244
-- Data for Name: detalle_interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_interes VALUES (139, '2-013', 3, 2020, 52.5, 4, '111');
INSERT INTO public.detalle_interes VALUES (140, '123', 3, 2020, 52.5, 4, '111');
INSERT INTO public.detalle_interes VALUES (141, '2-013', 3, 2020, 105, 5, '111');
INSERT INTO public.detalle_interes VALUES (142, '123', 3, 2020, 105, 5, '111');
INSERT INTO public.detalle_interes VALUES (143, '2-013', 4, 2020, 1002.5, 4, '111');
INSERT INTO public.detalle_interes VALUES (144, '123', 4, 2020, 1002.5, 4, '111');
INSERT INTO public.detalle_interes VALUES (145, '2-013', 4, 2020, 2005, 5, '111');
INSERT INTO public.detalle_interes VALUES (146, '123', 4, 2020, 2005, 5, '111');
INSERT INTO public.detalle_interes VALUES (147, '2-013', 2, 2020, 25, 4, '111');
INSERT INTO public.detalle_interes VALUES (148, '123', 2, 2020, 25, 4, '111');
INSERT INTO public.detalle_interes VALUES (149, '2-013', 2, 2020, 50, 5, '111');
INSERT INTO public.detalle_interes VALUES (150, '123', 2, 2020, 50, 5, '111');


--
-- TOC entry 3115 (class 0 OID 24837)
-- Dependencies: 238
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_pagos VALUES (791, '2-013', 3, 2020, 50, 12, '111');
INSERT INTO public.detalle_pagos VALUES (792, '123', 3, 2020, 50, 12, '111');
INSERT INTO public.detalle_pagos VALUES (793, '2-013', 3, 2020, 50, 8, '111');
INSERT INTO public.detalle_pagos VALUES (794, '123', 3, 2020, 50, 8, '111');
INSERT INTO public.detalle_pagos VALUES (795, '2-013', 3, 2020, 50, 10, '111');
INSERT INTO public.detalle_pagos VALUES (796, '123', 3, 2020, 50, 10, '111');
INSERT INTO public.detalle_pagos VALUES (797, '2-013', 3, 2020, 50, 2, '111');
INSERT INTO public.detalle_pagos VALUES (798, '123', 3, 2020, 50, 2, '111');
INSERT INTO public.detalle_pagos VALUES (799, '2-013', 3, 2020, 50, 11, '111');
INSERT INTO public.detalle_pagos VALUES (800, '123', 3, 2020, 50, 11, '111');
INSERT INTO public.detalle_pagos VALUES (801, '2-013', 4, 2020, 5000, 13, '111');
INSERT INTO public.detalle_pagos VALUES (802, '123', 4, 2020, 5000, 13, '111');


--
-- TOC entry 3119 (class 0 OID 24861)
-- Dependencies: 242
-- Data for Name: detalle_sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_sancion VALUES (134, '123', 21, 3, 2020, '111', 1000);
INSERT INTO public.detalle_sancion VALUES (135, '2-013', 21, 3, 2020, '111', 1000);
INSERT INTO public.detalle_sancion VALUES (136, '123', 18, 3, 2020, '111', 105);
INSERT INTO public.detalle_sancion VALUES (137, '2-013', 18, 3, 2020, '111', 105);


--
-- TOC entry 3123 (class 0 OID 24946)
-- Dependencies: 246
-- Data for Name: detalle_total; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_total VALUES (81, '2-013', 13032.5, 4, 2020, 0.5, 'Pendiente de pago', '111', 13032.5);
INSERT INTO public.detalle_total VALUES (83, '2-013', 325, 2, 2020, 0.5, 'Pagado', '111', 0);
INSERT INTO public.detalle_total VALUES (79, '2-013', 1787.5, 3, 2020, 0.5, 'Pendiente de pago', '111', 612.5);
INSERT INTO public.detalle_total VALUES (82, '123', 13032.5, 4, 2019, 0.5, 'Pagado', '111', 0);
INSERT INTO public.detalle_total VALUES (80, '123', 1787.5, 1, 2020, 0.5, 'Pagado', '111', 0);
INSERT INTO public.detalle_total VALUES (84, '123', 325, 2, 2020, 0.5, 'Pendiente de pago', '111', 145);


--
-- TOC entry 3093 (class 0 OID 24705)
-- Dependencies: 216
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('prestacioness', '2020-04-11', 'ffdsf', 'dsf', 20, 20, '111', 3);
INSERT INTO public.fondos VALUES ('prestaciones', '2020-04-15', 'madsodiadjada', 'da', 2e+19, 2.0000000000000016e+19, '111', 1);
INSERT INTO public.fondos VALUES ('asads', '2020-04-18', 'madsodiadjada', 'yes', 20000000, 20000000, '111', 2);


--
-- TOC entry 3103 (class 0 OID 24769)
-- Dependencies: 226
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto_comun VALUES (4, 'Ordinario', 3, 2020, 23, 'sadsa', '342', 0, '', '2020-04-10', 'Pendiente', '111', 23);
INSERT INTO public.gasto_comun VALUES (1, 'Ordinario', 3, 2020, 100000, '52', '231231', 0, '23', '2020-04-17', 'Pendiente', '111', NULL);
INSERT INTO public.gasto_comun VALUES (12, 'Ordinario', 3, 2020, 100, 'sdf', '231231', 1, 'fdsf', '2020-04-09', 'Procesado', '111', 100);
INSERT INTO public.gasto_comun VALUES (8, 'Ordinario', 3, 2020, 100, '02422', '342', 1, 'sasasasassddddd', '2020-05-13', 'Procesado', '111', 100);
INSERT INTO public.gasto_comun VALUES (10, 'Extraordinario', 3, 2020, 100, '654987321524', '342', 1, 'sggggggggg', '2020-06-11', 'Procesado', '111', 100);
INSERT INTO public.gasto_comun VALUES (2, 'Extraordinario', 3, 2020, 100, 'sd', '342', 1, 'd', '2020-04-03', 'Procesado', '111', 100);
INSERT INTO public.gasto_comun VALUES (11, 'Extraordinario', 3, 2020, 100, '656798765434567', '231231', 1, 'sgggggggggzxxzxzx', '2020-09-09', 'Procesado', '111', 100);
INSERT INTO public.gasto_comun VALUES (13, 'Ordinario', 4, 2020, 10000, '10', '231231', 1, '', '2020-04-09', 'Procesado', '111', 10000);


--
-- TOC entry 3111 (class 0 OID 24821)
-- Dependencies: 234
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (4, 'Cosas', 10, 'Activo');
INSERT INTO public.interes VALUES (5, 'Cositas', 20, 'Activo');


--
-- TOC entry 3087 (class 0 OID 24664)
-- Dependencies: 210
-- Data for Name: propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietarios VALUES ('26943430', 'samuels', 'perezs', '04245222312s', 'sa@sa.coms', '111');
INSERT INTO public.propietarios VALUES ('31212', '1312', 'dfghj', 'dfghjk', 'hg', '111');
INSERT INTO public.propietarios VALUES ('27328852', 'Maryorith', 'Singer', '04125084544', 'maryorith1@hotmail.com', '13131312');
INSERT INTO public.propietarios VALUES ('2456325', 'Jose', 'Perez', '04245222312', 'sapm2000@hotmail.com', '13131312');
INSERT INTO public.propietarios VALUES ('8517596', 'Blanca', 'Singer', '04127616516', 'blanca1@hotmail.com', '13131312');
INSERT INTO public.propietarios VALUES ('732463', 'Juan', 'Lugo', '04125847963', 'jl@hotmail.com', '13131312');
INSERT INTO public.propietarios VALUES ('24578463', 'Maria', 'Alvarez', '04241578963', 'maria03@hotmail.com', '13131312');
INSERT INTO public.propietarios VALUES ('24332', 'dsfsfs', 'fsd', '6546', 'sads', '111');


--
-- TOC entry 3086 (class 0 OID 24654)
-- Dependencies: 209
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('231231', 'asdas', '234234', 'asdad', 'sadasdasd', 'ffsfsdfsfsfsf');
INSERT INTO public.proveedores VALUES ('342', 'asdassd', '234234', 'asdad', 'sadasdasdasd', 'ffsfsdfsfsfsf');


--
-- TOC entry 3092 (class 0 OID 24697)
-- Dependencies: 215
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asamblea_propietario VALUES (29, 23, '26943430');
INSERT INTO public.puente_asamblea_propietario VALUES (30, 23, '31212');


--
-- TOC entry 3130 (class 0 OID 25297)
-- Dependencies: 253
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_cobro_factura VALUES (3, 83, 7, 325);
INSERT INTO public.puente_cobro_factura VALUES (4, 79, 7, 1175);
INSERT INTO public.puente_cobro_factura VALUES (5, 82, 8, 13032.5);
INSERT INTO public.puente_cobro_factura VALUES (6, 80, 8, 1787.5);
INSERT INTO public.puente_cobro_factura VALUES (7, 84, 8, 180);


--
-- TOC entry 3107 (class 0 OID 24791)
-- Dependencies: 230
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_comunicado_usuario VALUES (1, '26843430', 2, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (2, '26943430', 2, 1);
INSERT INTO public.puente_comunicado_usuario VALUES (3, '26843430', 3, 0);


--
-- TOC entry 3085 (class 0 OID 24634)
-- Dependencies: 208
-- Data for Name: puente_condominio_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_condominio_cuenta VALUES (10, 'sdfsf', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (11, 'sdfsf', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (21, 'ddfsf', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (22, 'ddfsf', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (24, '423424423', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (25, '423424423', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (27, 'qweq', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (28, 'qweq', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (30, '09876543', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (31, '09876543', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (42, 'dsaddsd', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (43, 'dsaddsd', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (50, 'fsflkjhgfds', '13131312');
INSERT INTO public.puente_condominio_cuenta VALUES (51, 'fsflkjhgfds', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (54, 'dsad', '111');
INSERT INTO public.puente_condominio_cuenta VALUES (55, 'sdsasdd', '13131312');


--
-- TOC entry 3113 (class 0 OID 24829)
-- Dependencies: 236
-- Data for Name: puente_interes_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_interes_condominio VALUES (11, '111', 4);
INSERT INTO public.puente_interes_condominio VALUES (12, '111', 5);


--
-- TOC entry 3097 (class 0 OID 24725)
-- Dependencies: 220
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (38, 18, '123');
INSERT INTO public.puente_sancion_unidad VALUES (39, 18, '2-013');
INSERT INTO public.puente_sancion_unidad VALUES (40, 21, '123');
INSERT INTO public.puente_sancion_unidad VALUES (41, 21, '2-013');


--
-- TOC entry 3095 (class 0 OID 24717)
-- Dependencies: 218
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (21, 'Multa', 3, 2020, 1000, '', '111', 'Procesado');
INSERT INTO public.sancion VALUES (18, 'Interes de mora', 3, 2020, 20, '', '111', 'Procesado');


--
-- TOC entry 3088 (class 0 OID 24679)
-- Dependencies: 211
-- Data for Name: unidades; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidades VALUES ('1', 'no se', 300, '27328852');
INSERT INTO public.unidades VALUES ('2', 'no se', 300, '2456325');
INSERT INTO public.unidades VALUES ('3', 'no se', 300, '8517596');
INSERT INTO public.unidades VALUES ('4', 'no se', 300, '732463');
INSERT INTO public.unidades VALUES ('5', 'no se', 300, '24578463');
INSERT INTO public.unidades VALUES ('2-013', 'adasd', 50, '26943430');
INSERT INTO public.unidades VALUES ('123', 'sadsad', 50, '31212');


--
-- TOC entry 3079 (class 0 OID 16430)
-- Dependencies: 202
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES ('26843430', 'sapm', '12345', 'samuel', 'perez', 'Administrador', '2312313');
INSERT INTO public.usuario VALUES ('26943430', 'sapmmm', '1234', 'adag', 'adasad', 'Propietario', '123');


--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 212
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 23, true);


--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 204
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 6, true);


--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 221
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 1, true);


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 247
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cierre_de_mes_id_seq', 38, true);


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 249
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 8, true);


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 227
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 3, true);


--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 223
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 1, true);


--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 231
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 17, true);


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 239
-- Name: detalle_cuotas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_cuotas_id_seq', 198, true);


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 243
-- Name: detalle_interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_interes_id_seq', 150, true);


--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 237
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 802, true);


--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 241
-- Name: detalle_sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_sancion_id_seq', 137, true);


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 245
-- Name: detalle_total_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_total_id_seq', 84, true);


--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 251
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 3, true);


--
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 225
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 13, true);


--
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 233
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 6, true);


--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 214
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 30, true);


--
-- TOC entry 3176 (class 0 OID 0)
-- Dependencies: 252
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 7, true);


--
-- TOC entry 3177 (class 0 OID 0)
-- Dependencies: 229
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 3, true);


--
-- TOC entry 3178 (class 0 OID 0)
-- Dependencies: 207
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 55, true);


--
-- TOC entry 3179 (class 0 OID 0)
-- Dependencies: 235
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_interes_condominio_id_seq', 12, true);


--
-- TOC entry 3180 (class 0 OID 0)
-- Dependencies: 219
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 41, true);


--
-- TOC entry 3181 (class 0 OID 0)
-- Dependencies: 217
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 21, true);


--
-- TOC entry 2903 (class 2606 OID 25018)
-- Name: asambleas asambleas_fecha_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_fecha_key UNIQUE (fecha);


--
-- TOC entry 2905 (class 2606 OID 25020)
-- Name: asambleas asambleas_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_nombre_key UNIQUE (nombre);


--
-- TOC entry 2907 (class 2606 OID 24694)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 2885 (class 2606 OID 25022)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 2887 (class 2606 OID 24622)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2917 (class 2606 OID 24750)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 2919 (class 2606 OID 24748)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 25002)
-- Name: cierre_de_mes cierre_de_mes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_pkey PRIMARY KEY (id);


--
-- TOC entry 2949 (class 2606 OID 25283)
-- Name: cobro cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 2927 (class 2606 OID 24785)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 2921 (class 2606 OID 24760)
-- Name: concepto_gasto concepto_gasto_nom_concepto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_nom_concepto_key UNIQUE (nom_concepto);


--
-- TOC entry 2923 (class 2606 OID 24758)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2881 (class 2606 OID 16456)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2883 (class 2606 OID 25024)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 2889 (class 2606 OID 25026)
-- Name: cuenta cuenta_n_cuenta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_key UNIQUE (n_cuenta);


--
-- TOC entry 2891 (class 2606 OID 25040)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 2931 (class 2606 OID 24818)
-- Name: cuotas_especiales cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2939 (class 2606 OID 24850)
-- Name: detalle_cuotas detalle_cuotas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_cuotas
    ADD CONSTRAINT detalle_cuotas_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 24922)
-- Name: detalle_interes detalle_interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_interes
    ADD CONSTRAINT detalle_interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2937 (class 2606 OID 24842)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 24866)
-- Name: detalle_sancion detalle_sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_sancion
    ADD CONSTRAINT detalle_sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 2945 (class 2606 OID 24951)
-- Name: detalle_total detalle_total_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_total
    ADD CONSTRAINT detalle_total_pkey PRIMARY KEY (id);


--
-- TOC entry 2911 (class 2606 OID 25304)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 2925 (class 2606 OID 24774)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 2933 (class 2606 OID 24826)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2899 (class 2606 OID 24668)
-- Name: propietarios propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietarios
    ADD CONSTRAINT propietarios_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2895 (class 2606 OID 25030)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 2897 (class 2606 OID 25036)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2909 (class 2606 OID 24702)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 2951 (class 2606 OID 25302)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 2929 (class 2606 OID 24799)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2893 (class 2606 OID 24639)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 2935 (class 2606 OID 24834)
-- Name: puente_interes_condominio puente_interes_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio
    ADD CONSTRAINT puente_interes_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 2915 (class 2606 OID 24730)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 2913 (class 2606 OID 24722)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 2901 (class 2606 OID 24683)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (n_unidad);


--
-- TOC entry 2875 (class 2606 OID 25032)
-- Name: usuario usuario_cedula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_key UNIQUE (cedula);


--
-- TOC entry 2877 (class 2606 OID 25038)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2879 (class 2606 OID 25034)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 2952 (class 2606 OID 24761)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


-- Completed on 2020-04-29 21:28:25

--
-- PostgreSQL database dump complete
--

