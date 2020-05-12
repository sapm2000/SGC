--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-05-11 21:26:45

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
-- TOC entry 262 (class 1255 OID 26737)
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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 26738)
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
-- TOC entry 203 (class 1259 OID 26744)
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
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 204 (class 1259 OID 26746)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo bigint
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 26749)
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
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 206 (class 1259 OID 26751)
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
-- TOC entry 207 (class 1259 OID 26754)
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
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 208 (class 1259 OID 26756)
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
-- TOC entry 209 (class 1259 OID 26759)
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
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 209
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cierre_de_mes_id_seq OWNED BY public.cierre_de_mes.id;


--
-- TOC entry 210 (class 1259 OID 26761)
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
    id_fondo bigint NOT NULL,
    id_condominio character varying(15)
);


ALTER TABLE public.cobro OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 26767)
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
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 211
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro.id;


--
-- TOC entry 212 (class 1259 OID 26769)
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
-- TOC entry 213 (class 1259 OID 26775)
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
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 213
-- Name: comunicados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id;


--
-- TOC entry 214 (class 1259 OID 26777)
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
-- TOC entry 215 (class 1259 OID 26780)
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
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 215
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 216 (class 1259 OID 26782)
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
-- TOC entry 217 (class 1259 OID 26785)
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
-- TOC entry 218 (class 1259 OID 26788)
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
-- TOC entry 219 (class 1259 OID 26791)
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
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 219
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 220 (class 1259 OID 26793)
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
-- TOC entry 221 (class 1259 OID 26799)
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
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.cuotas_especiales.id;


--
-- TOC entry 222 (class 1259 OID 26801)
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
-- TOC entry 223 (class 1259 OID 26804)
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
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 223
-- Name: detalle_cuotas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_cuotas_id_seq OWNED BY public.detalle_cuotas.id;


--
-- TOC entry 224 (class 1259 OID 26806)
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
-- TOC entry 225 (class 1259 OID 26809)
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
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 225
-- Name: detalle_interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_interes_id_seq OWNED BY public.detalle_interes.id;


--
-- TOC entry 226 (class 1259 OID 26811)
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
-- TOC entry 227 (class 1259 OID 26814)
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
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 227
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 228 (class 1259 OID 26816)
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
-- TOC entry 229 (class 1259 OID 26819)
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
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 229
-- Name: detalle_sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_sancion_id_seq OWNED BY public.detalle_sancion.id;


--
-- TOC entry 230 (class 1259 OID 26821)
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
-- TOC entry 231 (class 1259 OID 26824)
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
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 231
-- Name: detalle_total_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_total_id_seq OWNED BY public.detalle_total.id;


--
-- TOC entry 232 (class 1259 OID 26826)
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
-- TOC entry 233 (class 1259 OID 26832)
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
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 233
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 234 (class 1259 OID 26834)
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
-- TOC entry 235 (class 1259 OID 26837)
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
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 235
-- Name: gasto_comun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_comun_id_seq OWNED BY public.gasto_comun.id;


--
-- TOC entry 236 (class 1259 OID 26839)
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
-- TOC entry 237 (class 1259 OID 26842)
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
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 237
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 238 (class 1259 OID 26844)
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
-- TOC entry 239 (class 1259 OID 26847)
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
-- TOC entry 240 (class 1259 OID 26853)
-- Name: puente_asamblea_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asamblea_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.puente_asamblea_propietario OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 26856)
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
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 241
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


--
-- TOC entry 242 (class 1259 OID 26858)
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
-- TOC entry 243 (class 1259 OID 26861)
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
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 243
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 244 (class 1259 OID 26863)
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
-- TOC entry 245 (class 1259 OID 26869)
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
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 245
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 246 (class 1259 OID 26871)
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
-- TOC entry 247 (class 1259 OID 26874)
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
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 247
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


--
-- TOC entry 248 (class 1259 OID 26876)
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
-- TOC entry 249 (class 1259 OID 26879)
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
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 249
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_interes_condominio_id_seq OWNED BY public.puente_interes_condominio.id;


--
-- TOC entry 250 (class 1259 OID 26881)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 26884)
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
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 251
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 252 (class 1259 OID 26886)
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
-- TOC entry 253 (class 1259 OID 26889)
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
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 253
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 254 (class 1259 OID 26891)
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
-- TOC entry 255 (class 1259 OID 26894)
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
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 255
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 256 (class 1259 OID 26896)
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
-- TOC entry 257 (class 1259 OID 26899)
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
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 257
-- Name: unidades_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidades_id_seq OWNED BY public.unidades.id;


--
-- TOC entry 258 (class 1259 OID 26901)
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
-- TOC entry 259 (class 1259 OID 26904)
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
-- TOC entry 260 (class 1259 OID 26909)
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
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 260
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 261 (class 1259 OID 26911)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    cedula character varying(10) NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 2875 (class 2604 OID 26914)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2876 (class 2604 OID 26915)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2877 (class 2604 OID 26916)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 2878 (class 2604 OID 26917)
-- Name: cierre_de_mes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes ALTER COLUMN id SET DEFAULT nextval('public.cierre_de_mes_id_seq'::regclass);


--
-- TOC entry 2879 (class 2604 OID 26918)
-- Name: cobro id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 2880 (class 2604 OID 26919)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 2881 (class 2604 OID 26920)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 2882 (class 2604 OID 26921)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 2883 (class 2604 OID 26922)
-- Name: cuotas_especiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 2884 (class 2604 OID 26923)
-- Name: detalle_cuotas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_cuotas ALTER COLUMN id SET DEFAULT nextval('public.detalle_cuotas_id_seq'::regclass);


--
-- TOC entry 2885 (class 2604 OID 26924)
-- Name: detalle_interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_interes ALTER COLUMN id SET DEFAULT nextval('public.detalle_interes_id_seq'::regclass);


--
-- TOC entry 2886 (class 2604 OID 26925)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 2887 (class 2604 OID 26926)
-- Name: detalle_sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_sancion ALTER COLUMN id SET DEFAULT nextval('public.detalle_sancion_id_seq'::regclass);


--
-- TOC entry 2888 (class 2604 OID 26927)
-- Name: detalle_total id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_total ALTER COLUMN id SET DEFAULT nextval('public.detalle_total_id_seq'::regclass);


--
-- TOC entry 2889 (class 2604 OID 26928)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 2890 (class 2604 OID 26929)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 2891 (class 2604 OID 26930)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 2892 (class 2604 OID 26931)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2893 (class 2604 OID 26932)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 2894 (class 2604 OID 26933)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 2895 (class 2604 OID 26934)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2896 (class 2604 OID 26935)
-- Name: puente_interes_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_interes_condominio_id_seq'::regclass);


--
-- TOC entry 2897 (class 2604 OID 26936)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 2898 (class 2604 OID 26937)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 2899 (class 2604 OID 26938)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 2900 (class 2604 OID 26939)
-- Name: unidades id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades ALTER COLUMN id SET DEFAULT nextval('public.unidades_id_seq'::regclass);


--
-- TOC entry 2903 (class 2604 OID 26940)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3117 (class 0 OID 26738)
-- Dependencies: 202
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (33, 'Porton', '2020-04-26', 'Se realizo esta reunion para hablar sobre el porton ', 'J-2540768143');
INSERT INTO public.asambleas VALUES (51, 'Pintura de Aceras', '2020-04-08', '-', 'J-2540768143');
INSERT INTO public.asambleas VALUES (52, 'Parque Infantil', '2020-04-13', '-', 'J-2540768143');
INSERT INTO public.asambleas VALUES (53, 'Piscina', '2020-04-08', '-', 'J-2540768143');
INSERT INTO public.asambleas VALUES (54, 'Bombillos', '2020-04-06', '-', 'J-2540768143');
INSERT INTO public.asambleas VALUES (55, 'Cercado', '2020-04-01', '-', 'J-5784968566654');
INSERT INTO public.asambleas VALUES (56, 'Limpieza', '2020-04-09', '-', 'J-5784968566654');
INSERT INTO public.asambleas VALUES (57, 'Piscina', '2020-04-07', '-', 'J-5784968566654');
INSERT INTO public.asambleas VALUES (58, 'Parque', '2020-04-02', '-', 'J-5784968566654');
INSERT INTO public.asambleas VALUES (59, 'Gastos', '2020-04-14', '-', 'J-5784968566654');
INSERT INTO public.asambleas VALUES (60, 'Gastos', '2020-04-01', '-', 'J-7774122010236');
INSERT INTO public.asambleas VALUES (61, 'Ascensor', '2020-04-06', '-', 'J-7774122010236');
INSERT INTO public.asambleas VALUES (62, 'Piscina', '2020-04-09', '-', 'J-7774122010236');
INSERT INTO public.asambleas VALUES (63, 'Camaras', '2020-05-21', '-', 'J-7774122010236');
INSERT INTO public.asambleas VALUES (64, 'Oficina', '2020-04-09', '-', 'J-7774122010236');
INSERT INTO public.asambleas VALUES (65, 'Limpieza', '2020-04-01', '-', 'J-77741220004');
INSERT INTO public.asambleas VALUES (66, 'Gastos Oficina', '2020-04-08', '-', 'J-77741220004');
INSERT INTO public.asambleas VALUES (67, 'Piscina', '2020-04-01', '-', 'J-77741220004');
INSERT INTO public.asambleas VALUES (68, 'Pintura', '2020-04-15', '-', 'J-77741220004');
INSERT INTO public.asambleas VALUES (69, 'Parque', '2020-04-18', '-', 'J-77741220004');
INSERT INTO public.asambleas VALUES (70, 'Limpieza', '2020-04-01', '-', 'J-77663250004');
INSERT INTO public.asambleas VALUES (71, 'Vigilancia', '2020-04-06', '-', 'J-77663250004');
INSERT INTO public.asambleas VALUES (72, 'Areas Verdes', '2020-05-12', '-', 'J-77663250004');
INSERT INTO public.asambleas VALUES (73, 'Camaras', '2020-05-15', '-', 'J-77663250004');
INSERT INTO public.asambleas VALUES (74, 'Bombillos', '2020-05-26', '-', 'J-77663250004');
INSERT INTO public.asambleas VALUES (75, 'Ventanas', '2020-04-01', '-', 'J-9854762000');
INSERT INTO public.asambleas VALUES (76, 'Parque', '2020-04-04', '-', 'J-9854762000');
INSERT INTO public.asambleas VALUES (77, 'Limpieza', '2020-04-13', '-', 'J-9854762000');
INSERT INTO public.asambleas VALUES (78, 'Vigilancia', '2020-04-16', '-', 'J-9854762000');
INSERT INTO public.asambleas VALUES (79, 'Ascensor', '2020-04-21', '-', 'J-9854762000');
INSERT INTO public.asambleas VALUES (24, 'Ascensor', '2020-05-01', 'Se realizo la siguiente asamblea para hablar sobre el mantenimiento del ascensor', 'J-4512698007');
INSERT INTO public.asambleas VALUES (35, 'Vigilancia', '2020-05-01', 'Se hablo sobre la vigilancia', 'J-4512698007');
INSERT INTO public.asambleas VALUES (36, 'Basura', '2020-04-20', 'Se hablo sobre la basura dejada en la puerta del edificio', 'J-4512698007');
INSERT INTO public.asambleas VALUES (37, 'Gastos Hechos', '2020-04-29', 'Se hablo sobre los gastos que se han realizado en el mes de abril', 'J-4512698007');
INSERT INTO public.asambleas VALUES (38, 'Morosos', '2020-04-21', 'Se trato sobre el tema de los morosos', 'J-4512698007');
INSERT INTO public.asambleas VALUES (80, 'dsad', '2020-05-21', 'sadas', 'J-4512698007');
INSERT INTO public.asambleas VALUES (39, 'Camaras', '2020-04-15', 'Se hablo sobre las camaras', 'J-0145232547');
INSERT INTO public.asambleas VALUES (40, 'Vigilancia', '2020-04-08', 'Vigilancia', 'J-0145232547');
INSERT INTO public.asambleas VALUES (41, 'Piscina', '2020-04-15', 'Piscina', 'J-0145232547');
INSERT INTO public.asambleas VALUES (26, 'Calles limpias', '2020-04-30', 'Se realiza la siguiente asamblea con la intencion de contratar a una persona para limpiar las calles', 'J-0145232547');
INSERT INTO public.asambleas VALUES (42, 'Bombillos', '2020-04-08', '-', 'J-0145232547');
INSERT INTO public.asambleas VALUES (44, 'Porton', '2020-04-15', 'Porton', 'J-9685745568');
INSERT INTO public.asambleas VALUES (46, 'Mascotas', '2020-05-23', 'Mascotas', 'J-9685745568');
INSERT INTO public.asambleas VALUES (43, 'Propietarios', '2020-04-01', 'Propietarios', 'J-9685745568');
INSERT INTO public.asambleas VALUES (29, 'Pintar la casera de vigilancia', '2020-04-29', 'Se realiza esta reunion para hablar sobre pintar la caseta de vigilancia', 'J-9685745568');
INSERT INTO public.asambleas VALUES (45, 'Camaras', '2020-04-13', 'Camaras', 'J-9685745568');
INSERT INTO public.asambleas VALUES (47, 'Puertas', '2020-04-08', 'Puertas de planta baja', 'J-0024514563');
INSERT INTO public.asambleas VALUES (49, 'Pintura del Edificio', '2020-04-09', '-', 'J-0024514563');
INSERT INTO public.asambleas VALUES (48, 'Ascensor', '2020-05-12', '-', 'J-0024514563');
INSERT INTO public.asambleas VALUES (31, 'Ventanas', '2020-04-28', 'Se realizo esta asamblea para hablar sobre las ventanas de planta baja', 'J-0024514563');
INSERT INTO public.asambleas VALUES (50, 'Estacionamiento', '2020-04-18', '-', 'J-0024514563');
INSERT INTO public.asambleas VALUES (81, 'dfgd', '2020-05-21', 'dfgd', 'J-4512698007');


--
-- TOC entry 3119 (class 0 OID 26746)
-- Dependencies: 204
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
-- TOC entry 3121 (class 0 OID 26751)
-- Dependencies: 206
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (2, 'Mantenimiento', 'Conjunto de conceptos de mantenimiento', 1);
INSERT INTO public.categoriagasto VALUES (5, 'fsfsfdsfs', 'Conjunto de conceptos administrativo', 1);
INSERT INTO public.categoriagasto VALUES (3, 'Reparaciones', 'Conjunto de conceptos de reparaciones', 1);
INSERT INTO public.categoriagasto VALUES (4, 'Uso comun', 'Conjunto de conceptos de uso comun y consumo', 0);
INSERT INTO public.categoriagasto VALUES (6, 'asda', 'ddasda', 0);
INSERT INTO public.categoriagasto VALUES (1, 'Administrativo', 'Conjunto de conceptos administrativos', 1);


--
-- TOC entry 3123 (class 0 OID 26756)
-- Dependencies: 208
-- Data for Name: cierre_de_mes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cierre_de_mes VALUES (41, 4, 2020, 'J-4512698007');
INSERT INTO public.cierre_de_mes VALUES (42, 4, 2020, 'J-0145232547');
INSERT INTO public.cierre_de_mes VALUES (43, 5, 2020, 'J-0145232547');
INSERT INTO public.cierre_de_mes VALUES (44, 4, 2020, 'J-9685745568');
INSERT INTO public.cierre_de_mes VALUES (45, 4, 2020, 'J-0024514563');
INSERT INTO public.cierre_de_mes VALUES (46, 4, 2020, 'J-2540768143');
INSERT INTO public.cierre_de_mes VALUES (47, 4, 2020, 'J-5784968566654');
INSERT INTO public.cierre_de_mes VALUES (48, 4, 2020, 'J-7774122010236');
INSERT INTO public.cierre_de_mes VALUES (49, 4, 2020, 'J-77741220004');
INSERT INTO public.cierre_de_mes VALUES (50, 4, 2020, 'J-77663250004');


--
-- TOC entry 3125 (class 0 OID 26761)
-- Dependencies: 210
-- Data for Name: cobro; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cobro VALUES (8, 7597, 'ninguna', 'A1', '01020045120268985654', 'Deposito', '12334557', '2020-05-22', 5, 'J-4512698007');
INSERT INTO public.cobro VALUES (9, 7697, 'ninguna', 'A2', '01020045120268985654', 'Deposito', '12334557', '2020-05-22', 5, 'J-4512698007');
INSERT INTO public.cobro VALUES (10, 1377, 'hola', '1', '01025487596584758945', 'Transferencia', '12345', '2020-05-16', 6, 'J-0145232547');


--
-- TOC entry 3127 (class 0 OID 26769)
-- Dependencies: 212
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.comunicados VALUES (4, 'Reunion Urgente', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-4512698007');
INSERT INTO public.comunicados VALUES (5, 'Reunion ', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-4512698007');
INSERT INTO public.comunicados VALUES (6, 'Reunion Por Cosas Importantes', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-4512698007');
INSERT INTO public.comunicados VALUES (7, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-4512698007');
INSERT INTO public.comunicados VALUES (8, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-4512698007');
INSERT INTO public.comunicados VALUES (9, 'Reunion', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0145232547');
INSERT INTO public.comunicados VALUES (10, 'Reunion Necesaria', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0145232547');
INSERT INTO public.comunicados VALUES (11, 'Reunion Importante', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0145232547');
INSERT INTO public.comunicados VALUES (12, 'Reunion para Comunicar Quejas', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0145232547');
INSERT INTO public.comunicados VALUES (13, 'Reunion para Hablar del Ascensor', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0145232547');
INSERT INTO public.comunicados VALUES (14, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-9685745568');
INSERT INTO public.comunicados VALUES (15, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-9685745568');
INSERT INTO public.comunicados VALUES (16, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-9685745568');
INSERT INTO public.comunicados VALUES (17, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-9685745568');
INSERT INTO public.comunicados VALUES (18, 'Reunion 5', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-9685745568');
INSERT INTO public.comunicados VALUES (19, 'Reunion', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0024514563');
INSERT INTO public.comunicados VALUES (20, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0024514563');
INSERT INTO public.comunicados VALUES (21, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0024514563');
INSERT INTO public.comunicados VALUES (22, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0024514563');
INSERT INTO public.comunicados VALUES (23, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-0024514563');
INSERT INTO public.comunicados VALUES (24, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-2540768143');
INSERT INTO public.comunicados VALUES (25, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-2540768143');
INSERT INTO public.comunicados VALUES (26, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-2540768143');
INSERT INTO public.comunicados VALUES (27, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-2540768143');
INSERT INTO public.comunicados VALUES (28, 'Reunion 5', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-2540768143');
INSERT INTO public.comunicados VALUES (29, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-5784968566654');
INSERT INTO public.comunicados VALUES (30, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-5784968566654');
INSERT INTO public.comunicados VALUES (31, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-5784968566654');
INSERT INTO public.comunicados VALUES (32, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-5784968566654');
INSERT INTO public.comunicados VALUES (33, 'Reunion 5', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-5784968566654');
INSERT INTO public.comunicados VALUES (34, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-7774122010236');
INSERT INTO public.comunicados VALUES (35, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-7774122010236');
INSERT INTO public.comunicados VALUES (36, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-7774122010236');
INSERT INTO public.comunicados VALUES (37, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-7774122010236');
INSERT INTO public.comunicados VALUES (38, 'Reunion 5', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-7774122010236');
INSERT INTO public.comunicados VALUES (39, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77741220004');
INSERT INTO public.comunicados VALUES (40, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77741220004');
INSERT INTO public.comunicados VALUES (41, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77741220004');
INSERT INTO public.comunicados VALUES (42, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77741220004');
INSERT INTO public.comunicados VALUES (43, 'Reunion 5', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77741220004');
INSERT INTO public.comunicados VALUES (44, 'Reunion 1', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77663250004');
INSERT INTO public.comunicados VALUES (45, 'Reunion 2', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77663250004');
INSERT INTO public.comunicados VALUES (46, 'Reunion 3', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77663250004');
INSERT INTO public.comunicados VALUES (47, 'Reunion 4', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77663250004');
INSERT INTO public.comunicados VALUES (48, 'Reunion 5', 'Se solicita una reunion para hablar sobre cosas importantes sobre el condominio', 'J-77663250004');
INSERT INTO public.comunicados VALUES (49, 'Reunion 1', 'Por favor asistir a la reunion urgente', 'J-9854762000');
INSERT INTO public.comunicados VALUES (50, 'Reunion 2', 'Por favor asistir a la reunion urgente', 'J-9854762000');
INSERT INTO public.comunicados VALUES (51, 'Reunion 3', 'Por favor asistir a la reunion urgente', 'J-9854762000');
INSERT INTO public.comunicados VALUES (52, 'Reunion 4', 'Por favor asistir a la reunion urgente', 'J-9854762000');
INSERT INTO public.comunicados VALUES (53, 'Reunion 5', 'Por favor asistir a la reunion urgente', 'J-9854762000');


--
-- TOC entry 3129 (class 0 OID 26777)
-- Dependencies: 214
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
-- TOC entry 3131 (class 0 OID 26782)
-- Dependencies: 216
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('J-0145232547', 'Urbanización La Ascención', '02542457896', 'UrbLaAscencion@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-9685745568', 'Urbanización Prados del Norte', '0254689563', 'UrbPradosdelNorte@gmail.com', 1);
INSERT INTO public.condominio VALUES ('J-0024514563', 'Condominio Estrella', '02546033402', 'condominioEstrella@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-2540768143', 'Condominio Ciudad Roca', '02544268945', 'condominioCiudadRoca@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-5784968566654', 'La Cruz', '04145796589', 'LC@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-7774122010236', 'Condominio Las Estaciones', '04245632154', 'Estaciones@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-77741220004', 'Urbanizacion Flores', '04247896541', 'Flores@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-77663250004', 'Urbanizacion Sol', '04247896541', 'Sol@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-9854762000', 'Urbanizacion Cisnes', '04246563110', 'Cisnes@hotmail.com', 1);
INSERT INTO public.condominio VALUES ('J-4512698007', 'Condominio Portal del Este', '02545962485', 'condominioPortalDelEste@hotmail.com', 1);


--
-- TOC entry 3132 (class 0 OID 26785)
-- Dependencies: 217
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('J-102457', '01020045120268985654', 'Urbanizacion Pardos del Norte', 'Corriente', 6, 1);
INSERT INTO public.cuenta VALUES ('J-254551', '01025487596584758945', 'Condominio Estrella', 'Corriente', 1, 1);
INSERT INTO public.cuenta VALUES ('J-285856', '01145247946596656485', 'Condominio Portal del Este', 'Corriente', 3, 1);
INSERT INTO public.cuenta VALUES ('v-358963', '01052458795254653322', 'Condominio Ciudad Roca', 'Corriente', 5, 1);
INSERT INTO public.cuenta VALUES ('J-245698', '01085698745685232540', 'Urbanizacion La Ascension', 'Corriente', 4, 1);


--
-- TOC entry 3133 (class 0 OID 26788)
-- Dependencies: 218
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3135 (class 0 OID 26793)
-- Dependencies: 220
-- Data for Name: cuotas_especiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuotas_especiales VALUES (69, 'J-2457903215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 31, '', 'Pendiente', 'J-9854762000', 1);
INSERT INTO public.cuotas_especiales VALUES (70, '17102635', 5, 'Total de Inmuebles', 4, 2020, 3000, 3000, 2, 56, '', 'Pendiente', 'J-9854762000', 2);
INSERT INTO public.cuotas_especiales VALUES (71, '17102635', 9, 'Total de Inmuebles', 4, 2020, 2000, 2000, 2, 56, '', 'Pendiente', 'J-9854762000', 2);
INSERT INTO public.cuotas_especiales VALUES (72, '12457896', 31, 'Alicuota', 4, 2020, 4000, 4000, 1, 0, '', 'Pendiente', 'J-9854762000', 1);
INSERT INTO public.cuotas_especiales VALUES (73, 'J-54785696', 16, 'Total de Inmuebles', 4, 2020, 5000, 5000, 1, 0, '', 'Pendiente', 'J-9854762000', 1);
INSERT INTO public.cuotas_especiales VALUES (24, '20111045', 32, 'Alicuota', 4, 2020, 2000, 2000, 1, 0, '', 'Mensualidad Completada', 'J-4512698007', 0);
INSERT INTO public.cuotas_especiales VALUES (26, '12457896', 41, 'Total de Inmuebles', 4, 2020, 4000, 4000, 2, 35, '', 'Mensualidad en Proceso', 'J-4512698007', 1);
INSERT INTO public.cuotas_especiales VALUES (36, '24666587', 24, 'Alicuota', 4, 2020, 3000, 3000, 2, 0, '', 'Mensualidad en Proceso', 'J-9685745568', 1);
INSERT INTO public.cuotas_especiales VALUES (25, 'J-54785696', 4, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 0, '', 'Mensualidad Completada', 'J-4512698007', 0);
INSERT INTO public.cuotas_especiales VALUES (33, 'J-547859655', 35, 'Total de Inmuebles', 4, 2020, 5000, 5000, 2, 39, '-', 'Mensualidad Completada', 'J-0145232547', 0);
INSERT INTO public.cuotas_especiales VALUES (29, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 42, '-', 'Mensualidad Completada', 'J-0145232547', 0);
INSERT INTO public.cuotas_especiales VALUES (32, 'J-215479658', 36, 'Alicuota', 4, 2020, 4000, 4000, 2, 0, '-', 'Mensualidad Completada', 'J-0145232547', 0);
INSERT INTO public.cuotas_especiales VALUES (31, '24666587', 31, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 0, '-', 'Mensualidad Completada', 'J-0145232547', 0);
INSERT INTO public.cuotas_especiales VALUES (30, 'J-2457021456', 5, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 0, '-', 'Mensualidad Completada', 'J-0145232547', 0);
INSERT INTO public.cuotas_especiales VALUES (38, 'J-547859655', 23, 'Total de Inmuebles', 4, 2020, 5000, 5000, 2, 39, '', 'Mensualidad en Proceso', 'J-9685745568', 1);
INSERT INTO public.cuotas_especiales VALUES (46, 'J-1001245215', 22, 'Alicuota', 4, 2020, 3000, 3000, 1, 52, '', 'Mensualidad Completada', 'J-2540768143', 0);
INSERT INTO public.cuotas_especiales VALUES (35, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 0, '', 'Mensualidad Completada', 'J-9685745568', 0);
INSERT INTO public.cuotas_especiales VALUES (37, 'J-54785696', 4, 'Total de Inmuebles', 4, 2020, 4000, 4000, 2, 0, '', 'Mensualidad en Proceso', 'J-9685745568', 1);
INSERT INTO public.cuotas_especiales VALUES (41, '12457896', 16, 'Total de Inmuebles', 4, 2020, 3000, 3000, 2, 24, '', 'Mensualidad en Proceso', 'J-0024514563', 1);
INSERT INTO public.cuotas_especiales VALUES (39, 'J-2457021456', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 31, '', 'Mensualidad Completada', 'J-0024514563', 0);
INSERT INTO public.cuotas_especiales VALUES (40, 'J-2457903215', 5, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 47, '', 'Mensualidad Completada', 'J-0024514563', 0);
INSERT INTO public.cuotas_especiales VALUES (42, 'J-1001245215', 26, 'Total de Inmuebles', 4, 2020, 5000, 5000, 1, 49, '', 'Mensualidad Completada', 'J-0024514563', 0);
INSERT INTO public.cuotas_especiales VALUES (43, 'J-1001245215', 25, 'Total de Inmuebles', 4, 2020, 4000, 4000, 1, 50, '', 'Mensualidad Completada', 'J-0024514563', 0);
INSERT INTO public.cuotas_especiales VALUES (47, 'J-1001245215', 32, 'Total de Inmuebles', 4, 2020, 4000, 4000, 1, 33, '', 'Mensualidad Completada', 'J-2540768143', 0);
INSERT INTO public.cuotas_especiales VALUES (45, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 51, '', 'Mensualidad Completada', 'J-2540768143', 0);
INSERT INTO public.cuotas_especiales VALUES (51, '17102635', 26, 'Alicuota', 4, 2020, 3000, 3000, 1, 56, '', 'Mensualidad Completada', 'J-5784968566654', 0);
INSERT INTO public.cuotas_especiales VALUES (48, '17102635', 2, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 0, '', 'Mensualidad Completada', 'J-2540768143', 0);
INSERT INTO public.cuotas_especiales VALUES (52, '17102635', 9, 'Total de Inmuebles', 4, 2020, 3000, 3000, 2, 56, '', 'Mensualidad en Proceso', 'J-5784968566654', 1);
INSERT INTO public.cuotas_especiales VALUES (49, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 59, '', 'Mensualidad Completada', 'J-5784968566654', 0);
INSERT INTO public.cuotas_especiales VALUES (50, 'J-1001245215', 2, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 59, '', 'Mensualidad Completada', 'J-5784968566654', 0);
INSERT INTO public.cuotas_especiales VALUES (53, 'J-54785696', 4, 'Total de Inmuebles', 4, 2020, 4000, 4000, 1, 0, '', 'Mensualidad Completada', 'J-5784968566654', 0);
INSERT INTO public.cuotas_especiales VALUES (55, '12457896', 30, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 24, '', 'Mensualidad Completada', 'J-7774122010236', 0);
INSERT INTO public.cuotas_especiales VALUES (58, 'J-547859655', 35, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 39, '', 'Mensualidad Completada', 'J-7774122010236', 0);
INSERT INTO public.cuotas_especiales VALUES (56, '17102635', 21, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 41, '', 'Mensualidad Completada', 'J-7774122010236', 0);
INSERT INTO public.cuotas_especiales VALUES (54, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 59, '', 'Mensualidad Completada', 'J-7774122010236', 0);
INSERT INTO public.cuotas_especiales VALUES (57, 'J-54785696', 4, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 64, '', 'Mensualidad Completada', 'J-7774122010236', 0);
INSERT INTO public.cuotas_especiales VALUES (60, '17102635', 2, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 56, '', 'Mensualidad Completada', 'J-77741220004', 0);
INSERT INTO public.cuotas_especiales VALUES (62, 'J-54785696', 4, 'Total de Inmuebles', 4, 2020, 4000, 4000, 1, 66, '', 'Mensualidad Completada', 'J-77741220004', 0);
INSERT INTO public.cuotas_especiales VALUES (59, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 0, '', 'Mensualidad Completada', 'J-77741220004', 0);
INSERT INTO public.cuotas_especiales VALUES (63, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 5000, 5000, 1, 0, '', 'Mensualidad Completada', 'J-77741220004', 0);
INSERT INTO public.cuotas_especiales VALUES (64, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 42, '', 'Mensualidad Completada', 'J-77663250004', 0);
INSERT INTO public.cuotas_especiales VALUES (65, '17102635', 2, 'Total de Inmuebles', 4, 2020, 2000, 2000, 1, 56, '', 'Mensualidad Completada', 'J-77663250004', 0);
INSERT INTO public.cuotas_especiales VALUES (66, '17102635', 20, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 72, '', 'Mensualidad Completada', 'J-77663250004', 0);
INSERT INTO public.cuotas_especiales VALUES (67, 'J-2457021456', 5, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 0, '', 'Mensualidad Completada', 'J-77663250004', 0);
INSERT INTO public.cuotas_especiales VALUES (68, 'J-54785696', 4, 'Total de Inmuebles', 4, 2020, 5000, 5000, 1, 0, '', 'Mensualidad Completada', 'J-77663250004', 0);
INSERT INTO public.cuotas_especiales VALUES (27, '12457896', 16, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 24, '', 'Mensualidad Completada', 'J-4512698007', 0);
INSERT INTO public.cuotas_especiales VALUES (28, 'J-1001245215', 26, 'Total de Inmuebles', 4, 2020, 5000, 5000, 2, 0, '', 'Mensualidad en Proceso', 'J-4512698007', 1);
INSERT INTO public.cuotas_especiales VALUES (34, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 0, '', 'Mensualidad Completada', 'J-9685745568', 0);
INSERT INTO public.cuotas_especiales VALUES (44, 'J-1001245215', 5, 'Total de Inmuebles', 4, 2020, 1000, 1000, 1, 42, '', 'Mensualidad Completada', 'J-2540768143', 0);
INSERT INTO public.cuotas_especiales VALUES (61, '17102635', 9, 'Total de Inmuebles', 4, 2020, 3000, 3000, 1, 56, '', 'Mensualidad Completada', 'J-77741220004', 0);
INSERT INTO public.cuotas_especiales VALUES (75, 'J-1001245215', 10, 'Alicuota', 8, 2020, 231, 231, 3, 35, '', 'Pendiente', 'J-4512698007', 3);
INSERT INTO public.cuotas_especiales VALUES (74, '24666587', 2, 'Alicuota', 5, 2020, 1231231, 1231231, 3, 24, '', 'Pendiente', 'J-4512698007', 3);


--
-- TOC entry 3137 (class 0 OID 26801)
-- Dependencies: 222
-- Data for Name: detalle_cuotas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_cuotas VALUES (199, 'A1', 27, 4, 2020, 'J-4512698007', 600);
INSERT INTO public.detalle_cuotas VALUES (200, 'A2', 27, 4, 2020, 'J-4512698007', 600);
INSERT INTO public.detalle_cuotas VALUES (201, 'B2', 27, 4, 2020, 'J-4512698007', 600);
INSERT INTO public.detalle_cuotas VALUES (202, 'E2', 27, 4, 2020, 'J-4512698007', 600);
INSERT INTO public.detalle_cuotas VALUES (203, 'D1', 27, 4, 2020, 'J-4512698007', 600);
INSERT INTO public.detalle_cuotas VALUES (204, 'A1', 26, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (205, 'A2', 26, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (206, 'B2', 26, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (207, 'E2', 26, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (208, 'D1', 26, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (209, 'A1', 24, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (210, 'A2', 24, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (211, 'B2', 24, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (212, 'E2', 24, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (213, 'D1', 24, 4, 2020, 'J-4512698007', 400);
INSERT INTO public.detalle_cuotas VALUES (214, 'A1', 28, 4, 2020, 'J-4512698007', 500);
INSERT INTO public.detalle_cuotas VALUES (215, 'A2', 28, 4, 2020, 'J-4512698007', 500);
INSERT INTO public.detalle_cuotas VALUES (216, 'B2', 28, 4, 2020, 'J-4512698007', 500);
INSERT INTO public.detalle_cuotas VALUES (217, 'E2', 28, 4, 2020, 'J-4512698007', 500);
INSERT INTO public.detalle_cuotas VALUES (218, 'D1', 28, 4, 2020, 'J-4512698007', 500);
INSERT INTO public.detalle_cuotas VALUES (219, 'A1', 25, 4, 2020, 'J-4512698007', 200);
INSERT INTO public.detalle_cuotas VALUES (220, 'A2', 25, 4, 2020, 'J-4512698007', 200);
INSERT INTO public.detalle_cuotas VALUES (221, 'B2', 25, 4, 2020, 'J-4512698007', 200);
INSERT INTO public.detalle_cuotas VALUES (222, 'E2', 25, 4, 2020, 'J-4512698007', 200);
INSERT INTO public.detalle_cuotas VALUES (223, 'D1', 25, 4, 2020, 'J-4512698007', 200);
INSERT INTO public.detalle_cuotas VALUES (224, '1', 33, 4, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (225, '2', 33, 4, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (226, '3', 33, 4, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (227, '4', 33, 4, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (228, '5', 33, 4, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (229, '1', 29, 4, 2020, 'J-0145232547', 200);
INSERT INTO public.detalle_cuotas VALUES (230, '2', 29, 4, 2020, 'J-0145232547', 200);
INSERT INTO public.detalle_cuotas VALUES (231, '3', 29, 4, 2020, 'J-0145232547', 200);
INSERT INTO public.detalle_cuotas VALUES (232, '4', 29, 4, 2020, 'J-0145232547', 200);
INSERT INTO public.detalle_cuotas VALUES (233, '5', 29, 4, 2020, 'J-0145232547', 200);
INSERT INTO public.detalle_cuotas VALUES (234, '1', 32, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (235, '2', 32, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (236, '3', 32, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (237, '4', 32, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (238, '5', 32, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (239, '1', 31, 4, 2020, 'J-0145232547', 600);
INSERT INTO public.detalle_cuotas VALUES (240, '2', 31, 4, 2020, 'J-0145232547', 600);
INSERT INTO public.detalle_cuotas VALUES (241, '3', 31, 4, 2020, 'J-0145232547', 600);
INSERT INTO public.detalle_cuotas VALUES (242, '4', 31, 4, 2020, 'J-0145232547', 600);
INSERT INTO public.detalle_cuotas VALUES (243, '5', 31, 4, 2020, 'J-0145232547', 600);
INSERT INTO public.detalle_cuotas VALUES (244, '1', 30, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (245, '2', 30, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (246, '3', 30, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (247, '4', 30, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (248, '5', 30, 4, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (249, '1', 33, 5, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (250, '2', 33, 5, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (251, '3', 33, 5, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (252, '4', 33, 5, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (253, '5', 33, 5, 2020, 'J-0145232547', 500);
INSERT INTO public.detalle_cuotas VALUES (254, '1', 32, 5, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (255, '2', 32, 5, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (256, '3', 32, 5, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (257, '4', 32, 5, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (258, '5', 32, 5, 2020, 'J-0145232547', 400);
INSERT INTO public.detalle_cuotas VALUES (259, '01', 38, 4, 2020, 'J-9685745568', 500);
INSERT INTO public.detalle_cuotas VALUES (260, '02', 38, 4, 2020, 'J-9685745568', 500);
INSERT INTO public.detalle_cuotas VALUES (261, '03', 38, 4, 2020, 'J-9685745568', 500);
INSERT INTO public.detalle_cuotas VALUES (262, '04', 38, 4, 2020, 'J-9685745568', 500);
INSERT INTO public.detalle_cuotas VALUES (263, '05', 38, 4, 2020, 'J-9685745568', 500);
INSERT INTO public.detalle_cuotas VALUES (264, '01', 36, 4, 2020, 'J-9685745568', 300);
INSERT INTO public.detalle_cuotas VALUES (265, '02', 36, 4, 2020, 'J-9685745568', 300);
INSERT INTO public.detalle_cuotas VALUES (266, '03', 36, 4, 2020, 'J-9685745568', 300);
INSERT INTO public.detalle_cuotas VALUES (267, '04', 36, 4, 2020, 'J-9685745568', 300);
INSERT INTO public.detalle_cuotas VALUES (268, '05', 36, 4, 2020, 'J-9685745568', 300);
INSERT INTO public.detalle_cuotas VALUES (269, '01', 34, 4, 2020, 'J-9685745568', 200);
INSERT INTO public.detalle_cuotas VALUES (270, '02', 34, 4, 2020, 'J-9685745568', 200);
INSERT INTO public.detalle_cuotas VALUES (271, '03', 34, 4, 2020, 'J-9685745568', 200);
INSERT INTO public.detalle_cuotas VALUES (272, '04', 34, 4, 2020, 'J-9685745568', 200);
INSERT INTO public.detalle_cuotas VALUES (273, '05', 34, 4, 2020, 'J-9685745568', 200);
INSERT INTO public.detalle_cuotas VALUES (274, '01', 35, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (275, '02', 35, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (276, '03', 35, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (277, '04', 35, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (278, '05', 35, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (279, '01', 37, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (280, '02', 37, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (281, '03', 37, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (282, '04', 37, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (283, '05', 37, 4, 2020, 'J-9685745568', 400);
INSERT INTO public.detalle_cuotas VALUES (284, '1A', 41, 4, 2020, 'J-0024514563', 300);
INSERT INTO public.detalle_cuotas VALUES (285, '2B', 41, 4, 2020, 'J-0024514563', 300);
INSERT INTO public.detalle_cuotas VALUES (286, '4A', 41, 4, 2020, 'J-0024514563', 300);
INSERT INTO public.detalle_cuotas VALUES (287, '4B', 41, 4, 2020, 'J-0024514563', 300);
INSERT INTO public.detalle_cuotas VALUES (288, 'PH', 41, 4, 2020, 'J-0024514563', 300);
INSERT INTO public.detalle_cuotas VALUES (289, '1A', 39, 4, 2020, 'J-0024514563', 200);
INSERT INTO public.detalle_cuotas VALUES (290, '2B', 39, 4, 2020, 'J-0024514563', 200);
INSERT INTO public.detalle_cuotas VALUES (291, '4A', 39, 4, 2020, 'J-0024514563', 200);
INSERT INTO public.detalle_cuotas VALUES (292, '4B', 39, 4, 2020, 'J-0024514563', 200);
INSERT INTO public.detalle_cuotas VALUES (293, 'PH', 39, 4, 2020, 'J-0024514563', 200);
INSERT INTO public.detalle_cuotas VALUES (294, '1A', 40, 4, 2020, 'J-0024514563', 400);
INSERT INTO public.detalle_cuotas VALUES (295, '2B', 40, 4, 2020, 'J-0024514563', 400);
INSERT INTO public.detalle_cuotas VALUES (296, '4A', 40, 4, 2020, 'J-0024514563', 400);
INSERT INTO public.detalle_cuotas VALUES (297, '4B', 40, 4, 2020, 'J-0024514563', 400);
INSERT INTO public.detalle_cuotas VALUES (298, 'PH', 40, 4, 2020, 'J-0024514563', 400);
INSERT INTO public.detalle_cuotas VALUES (299, '1A', 42, 4, 2020, 'J-0024514563', 1000);
INSERT INTO public.detalle_cuotas VALUES (300, '2B', 42, 4, 2020, 'J-0024514563', 1000);
INSERT INTO public.detalle_cuotas VALUES (301, '4A', 42, 4, 2020, 'J-0024514563', 1000);
INSERT INTO public.detalle_cuotas VALUES (302, '4B', 42, 4, 2020, 'J-0024514563', 1000);
INSERT INTO public.detalle_cuotas VALUES (303, 'PH', 42, 4, 2020, 'J-0024514563', 1000);
INSERT INTO public.detalle_cuotas VALUES (304, '1A', 43, 4, 2020, 'J-0024514563', 800);
INSERT INTO public.detalle_cuotas VALUES (305, '2B', 43, 4, 2020, 'J-0024514563', 800);
INSERT INTO public.detalle_cuotas VALUES (306, '4A', 43, 4, 2020, 'J-0024514563', 800);
INSERT INTO public.detalle_cuotas VALUES (307, '4B', 43, 4, 2020, 'J-0024514563', 800);
INSERT INTO public.detalle_cuotas VALUES (308, 'PH', 43, 4, 2020, 'J-0024514563', 800);
INSERT INTO public.detalle_cuotas VALUES (309, '6', 47, 4, 2020, 'J-2540768143', 800);
INSERT INTO public.detalle_cuotas VALUES (310, '7', 47, 4, 2020, 'J-2540768143', 800);
INSERT INTO public.detalle_cuotas VALUES (311, '8', 47, 4, 2020, 'J-2540768143', 800);
INSERT INTO public.detalle_cuotas VALUES (312, '9', 47, 4, 2020, 'J-2540768143', 800);
INSERT INTO public.detalle_cuotas VALUES (313, '10', 47, 4, 2020, 'J-2540768143', 800);
INSERT INTO public.detalle_cuotas VALUES (314, '6', 44, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (315, '7', 44, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (316, '8', 44, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (317, '9', 44, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (318, '10', 44, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (319, '6', 45, 4, 2020, 'J-2540768143', 400);
INSERT INTO public.detalle_cuotas VALUES (320, '7', 45, 4, 2020, 'J-2540768143', 400);
INSERT INTO public.detalle_cuotas VALUES (321, '8', 45, 4, 2020, 'J-2540768143', 400);
INSERT INTO public.detalle_cuotas VALUES (322, '9', 45, 4, 2020, 'J-2540768143', 400);
INSERT INTO public.detalle_cuotas VALUES (323, '10', 45, 4, 2020, 'J-2540768143', 400);
INSERT INTO public.detalle_cuotas VALUES (324, '6', 46, 4, 2020, 'J-2540768143', 600);
INSERT INTO public.detalle_cuotas VALUES (325, '7', 46, 4, 2020, 'J-2540768143', 600);
INSERT INTO public.detalle_cuotas VALUES (326, '8', 46, 4, 2020, 'J-2540768143', 600);
INSERT INTO public.detalle_cuotas VALUES (327, '9', 46, 4, 2020, 'J-2540768143', 600);
INSERT INTO public.detalle_cuotas VALUES (328, '10', 46, 4, 2020, 'J-2540768143', 600);
INSERT INTO public.detalle_cuotas VALUES (329, '6', 48, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (330, '7', 48, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (331, '8', 48, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (332, '9', 48, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (333, '10', 48, 4, 2020, 'J-2540768143', 200);
INSERT INTO public.detalle_cuotas VALUES (334, '003', 51, 4, 2020, 'J-5784968566654', 600);
INSERT INTO public.detalle_cuotas VALUES (335, '001', 51, 4, 2020, 'J-5784968566654', 600);
INSERT INTO public.detalle_cuotas VALUES (336, '002', 51, 4, 2020, 'J-5784968566654', 600);
INSERT INTO public.detalle_cuotas VALUES (337, '004', 51, 4, 2020, 'J-5784968566654', 600);
INSERT INTO public.detalle_cuotas VALUES (338, '005', 51, 4, 2020, 'J-5784968566654', 600);
INSERT INTO public.detalle_cuotas VALUES (339, '003', 52, 4, 2020, 'J-5784968566654', 300);
INSERT INTO public.detalle_cuotas VALUES (340, '001', 52, 4, 2020, 'J-5784968566654', 300);
INSERT INTO public.detalle_cuotas VALUES (341, '002', 52, 4, 2020, 'J-5784968566654', 300);
INSERT INTO public.detalle_cuotas VALUES (342, '004', 52, 4, 2020, 'J-5784968566654', 300);
INSERT INTO public.detalle_cuotas VALUES (343, '005', 52, 4, 2020, 'J-5784968566654', 300);
INSERT INTO public.detalle_cuotas VALUES (344, '003', 49, 4, 2020, 'J-5784968566654', 200);
INSERT INTO public.detalle_cuotas VALUES (345, '001', 49, 4, 2020, 'J-5784968566654', 200);
INSERT INTO public.detalle_cuotas VALUES (346, '002', 49, 4, 2020, 'J-5784968566654', 200);
INSERT INTO public.detalle_cuotas VALUES (347, '004', 49, 4, 2020, 'J-5784968566654', 200);
INSERT INTO public.detalle_cuotas VALUES (348, '005', 49, 4, 2020, 'J-5784968566654', 200);
INSERT INTO public.detalle_cuotas VALUES (349, '003', 50, 4, 2020, 'J-5784968566654', 400);
INSERT INTO public.detalle_cuotas VALUES (350, '001', 50, 4, 2020, 'J-5784968566654', 400);
INSERT INTO public.detalle_cuotas VALUES (351, '002', 50, 4, 2020, 'J-5784968566654', 400);
INSERT INTO public.detalle_cuotas VALUES (352, '004', 50, 4, 2020, 'J-5784968566654', 400);
INSERT INTO public.detalle_cuotas VALUES (353, '005', 50, 4, 2020, 'J-5784968566654', 400);
INSERT INTO public.detalle_cuotas VALUES (354, '003', 53, 4, 2020, 'J-5784968566654', 800);
INSERT INTO public.detalle_cuotas VALUES (355, '001', 53, 4, 2020, 'J-5784968566654', 800);
INSERT INTO public.detalle_cuotas VALUES (356, '002', 53, 4, 2020, 'J-5784968566654', 800);
INSERT INTO public.detalle_cuotas VALUES (357, '004', 53, 4, 2020, 'J-5784968566654', 800);
INSERT INTO public.detalle_cuotas VALUES (358, '005', 53, 4, 2020, 'J-5784968566654', 800);
INSERT INTO public.detalle_cuotas VALUES (359, '11', 55, 4, 2020, 'J-7774122010236', 400);
INSERT INTO public.detalle_cuotas VALUES (360, '12', 55, 4, 2020, 'J-7774122010236', 400);
INSERT INTO public.detalle_cuotas VALUES (361, '13', 55, 4, 2020, 'J-7774122010236', 400);
INSERT INTO public.detalle_cuotas VALUES (362, '15', 55, 4, 2020, 'J-7774122010236', 400);
INSERT INTO public.detalle_cuotas VALUES (363, '14', 55, 4, 2020, 'J-7774122010236', 400);
INSERT INTO public.detalle_cuotas VALUES (364, '11', 58, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (365, '12', 58, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (366, '13', 58, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (367, '15', 58, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (368, '14', 58, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (369, '11', 56, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (370, '12', 56, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (371, '13', 56, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (372, '15', 56, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (373, '14', 56, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (374, '11', 54, 4, 2020, 'J-7774122010236', 200);
INSERT INTO public.detalle_cuotas VALUES (375, '12', 54, 4, 2020, 'J-7774122010236', 200);
INSERT INTO public.detalle_cuotas VALUES (376, '13', 54, 4, 2020, 'J-7774122010236', 200);
INSERT INTO public.detalle_cuotas VALUES (377, '15', 54, 4, 2020, 'J-7774122010236', 200);
INSERT INTO public.detalle_cuotas VALUES (378, '14', 54, 4, 2020, 'J-7774122010236', 200);
INSERT INTO public.detalle_cuotas VALUES (379, '11', 57, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (380, '12', 57, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (381, '13', 57, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (382, '15', 57, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (383, '14', 57, 4, 2020, 'J-7774122010236', 600);
INSERT INTO public.detalle_cuotas VALUES (384, '01', 61, 4, 2020, 'J-77741220004', 600);
INSERT INTO public.detalle_cuotas VALUES (385, '02', 61, 4, 2020, 'J-77741220004', 600);
INSERT INTO public.detalle_cuotas VALUES (386, '03', 61, 4, 2020, 'J-77741220004', 600);
INSERT INTO public.detalle_cuotas VALUES (387, '04', 61, 4, 2020, 'J-77741220004', 600);
INSERT INTO public.detalle_cuotas VALUES (388, '05', 61, 4, 2020, 'J-77741220004', 600);
INSERT INTO public.detalle_cuotas VALUES (389, '01', 60, 4, 2020, 'J-77741220004', 400);
INSERT INTO public.detalle_cuotas VALUES (390, '02', 60, 4, 2020, 'J-77741220004', 400);
INSERT INTO public.detalle_cuotas VALUES (391, '03', 60, 4, 2020, 'J-77741220004', 400);
INSERT INTO public.detalle_cuotas VALUES (392, '04', 60, 4, 2020, 'J-77741220004', 400);
INSERT INTO public.detalle_cuotas VALUES (393, '05', 60, 4, 2020, 'J-77741220004', 400);
INSERT INTO public.detalle_cuotas VALUES (394, '01', 62, 4, 2020, 'J-77741220004', 800);
INSERT INTO public.detalle_cuotas VALUES (395, '02', 62, 4, 2020, 'J-77741220004', 800);
INSERT INTO public.detalle_cuotas VALUES (396, '03', 62, 4, 2020, 'J-77741220004', 800);
INSERT INTO public.detalle_cuotas VALUES (397, '04', 62, 4, 2020, 'J-77741220004', 800);
INSERT INTO public.detalle_cuotas VALUES (398, '05', 62, 4, 2020, 'J-77741220004', 800);
INSERT INTO public.detalle_cuotas VALUES (399, '01', 59, 4, 2020, 'J-77741220004', 200);
INSERT INTO public.detalle_cuotas VALUES (400, '02', 59, 4, 2020, 'J-77741220004', 200);
INSERT INTO public.detalle_cuotas VALUES (401, '03', 59, 4, 2020, 'J-77741220004', 200);
INSERT INTO public.detalle_cuotas VALUES (402, '04', 59, 4, 2020, 'J-77741220004', 200);
INSERT INTO public.detalle_cuotas VALUES (403, '05', 59, 4, 2020, 'J-77741220004', 200);
INSERT INTO public.detalle_cuotas VALUES (404, '01', 63, 4, 2020, 'J-77741220004', 1000);
INSERT INTO public.detalle_cuotas VALUES (405, '02', 63, 4, 2020, 'J-77741220004', 1000);
INSERT INTO public.detalle_cuotas VALUES (406, '03', 63, 4, 2020, 'J-77741220004', 1000);
INSERT INTO public.detalle_cuotas VALUES (407, '04', 63, 4, 2020, 'J-77741220004', 1000);
INSERT INTO public.detalle_cuotas VALUES (408, '05', 63, 4, 2020, 'J-77741220004', 1000);
INSERT INTO public.detalle_cuotas VALUES (409, '2', 64, 4, 2020, 'J-77663250004', 200);
INSERT INTO public.detalle_cuotas VALUES (410, '3', 64, 4, 2020, 'J-77663250004', 200);
INSERT INTO public.detalle_cuotas VALUES (411, '4', 64, 4, 2020, 'J-77663250004', 200);
INSERT INTO public.detalle_cuotas VALUES (412, '5', 64, 4, 2020, 'J-77663250004', 200);
INSERT INTO public.detalle_cuotas VALUES (413, '1', 64, 4, 2020, 'J-77663250004', 200);
INSERT INTO public.detalle_cuotas VALUES (414, '2', 65, 4, 2020, 'J-77663250004', 400);
INSERT INTO public.detalle_cuotas VALUES (415, '3', 65, 4, 2020, 'J-77663250004', 400);
INSERT INTO public.detalle_cuotas VALUES (416, '4', 65, 4, 2020, 'J-77663250004', 400);
INSERT INTO public.detalle_cuotas VALUES (417, '5', 65, 4, 2020, 'J-77663250004', 400);
INSERT INTO public.detalle_cuotas VALUES (418, '1', 65, 4, 2020, 'J-77663250004', 400);
INSERT INTO public.detalle_cuotas VALUES (419, '2', 66, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (420, '3', 66, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (421, '4', 66, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (422, '5', 66, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (423, '1', 66, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (424, '2', 67, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (425, '3', 67, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (426, '4', 67, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (427, '5', 67, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (428, '1', 67, 4, 2020, 'J-77663250004', 600);
INSERT INTO public.detalle_cuotas VALUES (429, '2', 68, 4, 2020, 'J-77663250004', 1000);
INSERT INTO public.detalle_cuotas VALUES (430, '3', 68, 4, 2020, 'J-77663250004', 1000);
INSERT INTO public.detalle_cuotas VALUES (431, '4', 68, 4, 2020, 'J-77663250004', 1000);
INSERT INTO public.detalle_cuotas VALUES (432, '5', 68, 4, 2020, 'J-77663250004', 1000);
INSERT INTO public.detalle_cuotas VALUES (433, '1', 68, 4, 2020, 'J-77663250004', 1000);


--
-- TOC entry 3139 (class 0 OID 26806)
-- Dependencies: 224
-- Data for Name: detalle_interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_interes VALUES (181, 'A1', 4, 2020, 510, 9, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (182, 'A2', 4, 2020, 510, 9, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (183, 'B2', 4, 2020, 510, 9, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (184, 'E2', 4, 2020, 510, 9, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (185, 'D1', 4, 2020, 510, 9, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (186, 'A1', 4, 2020, 1530, 10, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (187, 'A2', 4, 2020, 1530, 10, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (188, 'B2', 4, 2020, 1530, 10, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (189, 'E2', 4, 2020, 1530, 10, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (190, 'D1', 4, 2020, 1530, 10, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (191, 'A1', 4, 2020, 204, 12, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (192, 'A2', 4, 2020, 204, 12, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (193, 'B2', 4, 2020, 204, 12, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (194, 'E2', 4, 2020, 204, 12, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (195, 'D1', 4, 2020, 204, 12, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (196, 'A1', 4, 2020, 153, 15, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (197, 'A2', 4, 2020, 153, 15, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (198, 'B2', 4, 2020, 153, 15, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (199, 'E2', 4, 2020, 153, 15, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (200, 'D1', 4, 2020, 153, 15, 'J-4512698007');
INSERT INTO public.detalle_interes VALUES (201, '1', 4, 2020, 510, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (202, '2', 4, 2020, 510, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (203, '3', 4, 2020, 510, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (204, '4', 4, 2020, 510, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (205, '5', 4, 2020, 510, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (206, '1', 4, 2020, 1530, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (207, '2', 4, 2020, 1530, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (208, '3', 4, 2020, 1530, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (209, '4', 4, 2020, 1530, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (210, '5', 4, 2020, 1530, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (211, '1', 4, 2020, 204, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (212, '2', 4, 2020, 204, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (213, '3', 4, 2020, 204, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (214, '4', 4, 2020, 204, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (215, '5', 4, 2020, 204, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (216, '1', 4, 2020, 306, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (217, '2', 4, 2020, 306, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (218, '3', 4, 2020, 306, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (219, '4', 4, 2020, 306, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (220, '5', 4, 2020, 306, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (221, '1', 4, 2020, 153, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (222, '2', 4, 2020, 153, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (223, '3', 4, 2020, 153, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (224, '4', 4, 2020, 153, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (225, '5', 4, 2020, 153, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (226, '1', 5, 2020, 90, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (227, '2', 5, 2020, 90, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (228, '3', 5, 2020, 90, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (229, '4', 5, 2020, 90, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (230, '5', 5, 2020, 90, 9, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (231, '1', 5, 2020, 270, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (232, '2', 5, 2020, 270, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (233, '3', 5, 2020, 270, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (234, '4', 5, 2020, 270, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (235, '5', 5, 2020, 270, 10, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (236, '1', 5, 2020, 36, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (237, '2', 5, 2020, 36, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (238, '3', 5, 2020, 36, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (239, '4', 5, 2020, 36, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (240, '5', 5, 2020, 36, 12, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (241, '1', 5, 2020, 54, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (242, '2', 5, 2020, 54, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (243, '3', 5, 2020, 54, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (244, '4', 5, 2020, 54, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (245, '5', 5, 2020, 54, 14, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (246, '1', 5, 2020, 27, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (247, '2', 5, 2020, 27, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (248, '3', 5, 2020, 27, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (249, '4', 5, 2020, 27, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (250, '5', 5, 2020, 27, 15, 'J-0145232547');
INSERT INTO public.detalle_interes VALUES (251, '01', 4, 2020, 2820, 8, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (252, '02', 4, 2020, 2820, 8, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (253, '03', 4, 2020, 2820, 8, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (254, '04', 4, 2020, 2820, 8, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (255, '05', 4, 2020, 2820, 8, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (256, '01', 4, 2020, 235, 11, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (257, '02', 4, 2020, 235, 11, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (258, '03', 4, 2020, 235, 11, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (259, '04', 4, 2020, 235, 11, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (260, '05', 4, 2020, 235, 11, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (261, '01', 4, 2020, 141, 13, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (262, '02', 4, 2020, 141, 13, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (263, '03', 4, 2020, 141, 13, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (264, '04', 4, 2020, 141, 13, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (265, '05', 4, 2020, 141, 13, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (266, '01', 4, 2020, 282, 14, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (267, '02', 4, 2020, 282, 14, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (268, '03', 4, 2020, 282, 14, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (269, '04', 4, 2020, 282, 14, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (270, '05', 4, 2020, 282, 14, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (271, '01', 4, 2020, 94, 16, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (272, '02', 4, 2020, 94, 16, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (273, '03', 4, 2020, 94, 16, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (274, '04', 4, 2020, 94, 16, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (275, '05', 4, 2020, 94, 16, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (276, '01', 4, 2020, 141, 17, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (277, '02', 4, 2020, 141, 17, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (278, '03', 4, 2020, 141, 17, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (279, '04', 4, 2020, 141, 17, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (280, '05', 4, 2020, 141, 17, 'J-9685745568');
INSERT INTO public.detalle_interes VALUES (281, '1A', 4, 2020, 2972.2314049586776, 8, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (282, '2B', 4, 2020, 2972.2314049586776, 8, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (283, '4A', 4, 2020, 2972.2314049586776, 8, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (284, '4B', 4, 2020, 2972.2314049586776, 8, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (285, 'PH', 4, 2020, 4311.074380165289, 8, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (286, '1A', 4, 2020, 247.68595041322317, 11, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (287, '2B', 4, 2020, 247.68595041322317, 11, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (288, '4A', 4, 2020, 247.68595041322317, 11, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (289, '4B', 4, 2020, 247.68595041322317, 11, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (290, 'PH', 4, 2020, 359.25619834710744, 11, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (291, '1A', 4, 2020, 148.61157024793388, 13, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (292, '2B', 4, 2020, 148.61157024793388, 13, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (293, '4A', 4, 2020, 148.61157024793388, 13, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (294, '4B', 4, 2020, 148.61157024793388, 13, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (295, 'PH', 4, 2020, 215.55371900826444, 13, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (296, '1A', 4, 2020, 99.07438016528926, 16, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (297, '2B', 4, 2020, 99.07438016528926, 16, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (298, '4A', 4, 2020, 99.07438016528926, 16, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (299, '4B', 4, 2020, 99.07438016528926, 16, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (300, 'PH', 4, 2020, 143.70247933884298, 16, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (301, '1A', 4, 2020, 148.61157024793388, 17, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (302, '2B', 4, 2020, 148.61157024793388, 17, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (303, '4A', 4, 2020, 148.61157024793388, 17, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (304, '4B', 4, 2020, 148.61157024793388, 17, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (305, 'PH', 4, 2020, 215.55371900826444, 17, 'J-0024514563');
INSERT INTO public.detalle_interes VALUES (306, '6', 4, 2020, 2700, 8, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (307, '7', 4, 2020, 2700, 8, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (308, '8', 4, 2020, 2700, 8, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (309, '9', 4, 2020, 2700, 8, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (310, '10', 4, 2020, 2700, 8, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (311, '6', 4, 2020, 225, 11, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (312, '7', 4, 2020, 225, 11, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (313, '8', 4, 2020, 225, 11, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (314, '9', 4, 2020, 225, 11, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (315, '10', 4, 2020, 225, 11, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (316, '6', 4, 2020, 135, 13, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (317, '7', 4, 2020, 135, 13, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (318, '8', 4, 2020, 135, 13, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (319, '9', 4, 2020, 135, 13, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (320, '10', 4, 2020, 135, 13, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (321, '6', 4, 2020, 90, 16, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (322, '7', 4, 2020, 90, 16, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (323, '8', 4, 2020, 90, 16, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (324, '9', 4, 2020, 90, 16, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (325, '10', 4, 2020, 90, 16, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (326, '6', 4, 2020, 135, 17, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (327, '7', 4, 2020, 135, 17, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (328, '8', 4, 2020, 135, 17, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (329, '9', 4, 2020, 135, 17, 'J-2540768143');
INSERT INTO public.detalle_interes VALUES (330, '10', 4, 2020, 135, 17, 'J-2540768143');


--
-- TOC entry 3141 (class 0 OID 26811)
-- Dependencies: 226
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_pagos VALUES (1043, 'A1', 4, 2020, 600, 28, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1044, 'A2', 4, 2020, 600, 28, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1045, 'B2', 4, 2020, 600, 28, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1046, 'E2', 4, 2020, 600, 28, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1047, 'D1', 4, 2020, 600, 28, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1048, 'A1', 4, 2020, 200, 24, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1049, 'A2', 4, 2020, 200, 24, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1050, 'B2', 4, 2020, 200, 24, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1051, 'E2', 4, 2020, 200, 24, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1052, 'D1', 4, 2020, 200, 24, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1053, 'A1', 4, 2020, 1000, 27, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1054, 'A2', 4, 2020, 1000, 27, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1055, 'B2', 4, 2020, 1000, 27, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1056, 'E2', 4, 2020, 1000, 27, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1057, 'D1', 4, 2020, 1000, 27, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1058, 'A1', 4, 2020, 1000, 26, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1059, 'A2', 4, 2020, 1000, 26, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1060, 'B2', 4, 2020, 1000, 26, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1061, 'E2', 4, 2020, 1000, 26, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1062, 'D1', 4, 2020, 1000, 26, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1063, 'A1', 4, 2020, 200, 25, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1064, 'A2', 4, 2020, 200, 25, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1065, 'B2', 4, 2020, 200, 25, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1066, 'E2', 4, 2020, 200, 25, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1067, 'D1', 4, 2020, 200, 25, 'J-4512698007');
INSERT INTO public.detalle_pagos VALUES (1068, '1', 4, 2020, 1000, 33, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1069, '2', 4, 2020, 1000, 33, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1070, '3', 4, 2020, 1000, 33, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1071, '4', 4, 2020, 1000, 33, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1072, '5', 4, 2020, 1000, 33, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1073, '1', 4, 2020, 400, 30, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1074, '2', 4, 2020, 400, 30, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1075, '3', 4, 2020, 400, 30, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1076, '4', 4, 2020, 400, 30, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1077, '5', 4, 2020, 400, 30, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1078, '1', 4, 2020, 800, 32, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1079, '2', 4, 2020, 800, 32, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1080, '3', 4, 2020, 800, 32, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1081, '4', 4, 2020, 800, 32, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1082, '5', 4, 2020, 800, 32, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1083, '1', 4, 2020, 200, 29, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1084, '2', 4, 2020, 200, 29, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1085, '3', 4, 2020, 200, 29, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1086, '4', 4, 2020, 200, 29, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1087, '5', 4, 2020, 200, 29, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1088, '1', 4, 2020, 600, 31, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1089, '2', 4, 2020, 600, 31, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1090, '3', 4, 2020, 600, 31, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1091, '4', 4, 2020, 600, 31, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1092, '5', 4, 2020, 600, 31, 'J-0145232547');
INSERT INTO public.detalle_pagos VALUES (1093, '01', 4, 2020, 500, 36, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1094, '02', 4, 2020, 500, 36, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1095, '03', 4, 2020, 500, 36, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1096, '04', 4, 2020, 500, 36, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1097, '05', 4, 2020, 500, 36, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1098, '01', 4, 2020, 400, 35, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1099, '02', 4, 2020, 400, 35, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1100, '03', 4, 2020, 400, 35, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1101, '04', 4, 2020, 400, 35, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1102, '05', 4, 2020, 400, 35, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1103, '01', 4, 2020, 200, 34, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1104, '02', 4, 2020, 200, 34, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1105, '03', 4, 2020, 200, 34, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1106, '04', 4, 2020, 200, 34, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1107, '05', 4, 2020, 200, 34, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1108, '01', 4, 2020, 1000, 37, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1109, '02', 4, 2020, 1000, 37, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1110, '03', 4, 2020, 1000, 37, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1111, '04', 4, 2020, 1000, 37, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1112, '05', 4, 2020, 1000, 37, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1113, '01', 4, 2020, 800, 38, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1114, '02', 4, 2020, 800, 38, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1115, '03', 4, 2020, 800, 38, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1116, '04', 4, 2020, 800, 38, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1117, '05', 4, 2020, 800, 38, 'J-9685745568');
INSERT INTO public.detalle_pagos VALUES (1118, '1A', 4, 2020, 500.8264462809918, 41, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1119, '2B', 4, 2020, 500.8264462809918, 41, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1120, '4A', 4, 2020, 500.8264462809918, 41, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1121, '4B', 4, 2020, 500.8264462809918, 41, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1122, 'PH', 4, 2020, 996.6942148760331, 41, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1123, '1A', 4, 2020, 667.7685950413223, 42, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1124, '2B', 4, 2020, 667.7685950413223, 42, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1125, '4A', 4, 2020, 667.7685950413223, 42, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1126, '4B', 4, 2020, 667.7685950413223, 42, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1127, 'PH', 4, 2020, 1328.925619834711, 42, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1128, '1A', 4, 2020, 333.88429752066116, 40, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1129, '2B', 4, 2020, 333.88429752066116, 40, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1130, '4A', 4, 2020, 333.88429752066116, 40, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1131, '4B', 4, 2020, 333.88429752066116, 40, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1132, 'PH', 4, 2020, 664.4628099173555, 40, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1133, '1A', 4, 2020, 166.94214876033058, 39, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1134, '2B', 4, 2020, 166.94214876033058, 39, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1135, '4A', 4, 2020, 166.94214876033058, 39, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1136, '4B', 4, 2020, 166.94214876033058, 39, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1137, 'PH', 4, 2020, 332.23140495867773, 39, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1138, '1A', 4, 2020, 584.297520661157, 43, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1139, '2B', 4, 2020, 584.297520661157, 43, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1140, '4A', 4, 2020, 584.297520661157, 43, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1141, '4B', 4, 2020, 584.297520661157, 43, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1142, 'PH', 4, 2020, 1162.809917355372, 43, 'J-0024514563');
INSERT INTO public.detalle_pagos VALUES (1143, '6', 4, 2020, 200, 44, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1144, '7', 4, 2020, 200, 44, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1145, '8', 4, 2020, 200, 44, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1146, '9', 4, 2020, 200, 44, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1147, '10', 4, 2020, 200, 44, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1148, '6', 4, 2020, 400, 46, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1149, '7', 4, 2020, 400, 46, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1150, '8', 4, 2020, 400, 46, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1151, '9', 4, 2020, 400, 46, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1152, '10', 4, 2020, 400, 46, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1153, '6', 4, 2020, 800, 48, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1154, '7', 4, 2020, 800, 48, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1155, '8', 4, 2020, 800, 48, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1156, '9', 4, 2020, 800, 48, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1157, '10', 4, 2020, 800, 48, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1158, '6', 4, 2020, 600, 47, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1159, '7', 4, 2020, 600, 47, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1160, '8', 4, 2020, 600, 47, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1161, '9', 4, 2020, 600, 47, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1162, '10', 4, 2020, 600, 47, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1163, '6', 4, 2020, 300, 45, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1164, '7', 4, 2020, 300, 45, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1165, '8', 4, 2020, 300, 45, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1166, '9', 4, 2020, 300, 45, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1167, '10', 4, 2020, 300, 45, 'J-2540768143');
INSERT INTO public.detalle_pagos VALUES (1168, '003', 4, 2020, 200, 50, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1169, '001', 4, 2020, 200, 50, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1170, '002', 4, 2020, 200, 50, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1171, '004', 4, 2020, 200, 50, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1172, '005', 4, 2020, 200, 50, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1173, '003', 4, 2020, 400, 51, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1174, '001', 4, 2020, 400, 51, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1175, '002', 4, 2020, 400, 51, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1176, '004', 4, 2020, 400, 51, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1177, '005', 4, 2020, 400, 51, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1178, '003', 4, 2020, 600, 52, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1179, '001', 4, 2020, 600, 52, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1180, '002', 4, 2020, 600, 52, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1181, '004', 4, 2020, 600, 52, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1182, '005', 4, 2020, 600, 52, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1183, '003', 4, 2020, 600, 53, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1184, '001', 4, 2020, 600, 53, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1185, '002', 4, 2020, 600, 53, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1186, '004', 4, 2020, 600, 53, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1187, '005', 4, 2020, 600, 53, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1188, '003', 4, 2020, 600, 49, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1189, '001', 4, 2020, 600, 49, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1190, '002', 4, 2020, 600, 49, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1191, '004', 4, 2020, 600, 49, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1192, '005', 4, 2020, 600, 49, 'J-5784968566654');
INSERT INTO public.detalle_pagos VALUES (1193, '11', 4, 2020, 800, 57, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1194, '12', 4, 2020, 800, 57, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1195, '13', 4, 2020, 800, 57, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1196, '15', 4, 2020, 800, 57, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1197, '14', 4, 2020, 800, 57, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1198, '11', 4, 2020, 400, 55, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1199, '12', 4, 2020, 400, 55, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1200, '13', 4, 2020, 400, 55, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1201, '15', 4, 2020, 400, 55, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1202, '14', 4, 2020, 400, 55, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1203, '11', 4, 2020, 200, 54, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1204, '12', 4, 2020, 200, 54, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1205, '13', 4, 2020, 200, 54, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1206, '15', 4, 2020, 200, 54, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1207, '14', 4, 2020, 200, 54, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1208, '11', 4, 2020, 600, 56, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1209, '12', 4, 2020, 600, 56, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1210, '13', 4, 2020, 600, 56, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1211, '15', 4, 2020, 600, 56, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1212, '14', 4, 2020, 600, 56, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1213, '11', 4, 2020, 1000, 58, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1214, '12', 4, 2020, 1000, 58, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1215, '13', 4, 2020, 1000, 58, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1216, '15', 4, 2020, 1000, 58, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1217, '14', 4, 2020, 1000, 58, 'J-7774122010236');
INSERT INTO public.detalle_pagos VALUES (1218, '01', 4, 2020, 185.18518518518516, 62, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1219, '02', 4, 2020, 185.18518518518516, 62, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1220, '03', 4, 2020, 222.2222222222222, 62, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1221, '04', 4, 2020, 222.2222222222222, 62, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1222, '05', 4, 2020, 185.18518518518516, 62, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1223, '01', 4, 2020, 555.5555555555555, 61, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1224, '02', 4, 2020, 555.5555555555555, 61, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1225, '03', 4, 2020, 666.6666666666666, 61, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1226, '04', 4, 2020, 666.6666666666666, 61, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1227, '05', 4, 2020, 555.5555555555555, 61, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1228, '01', 4, 2020, 925.9259259259259, 60, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1229, '02', 4, 2020, 925.9259259259259, 60, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1230, '03', 4, 2020, 1111.111111111111, 60, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1231, '04', 4, 2020, 1111.111111111111, 60, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1232, '05', 4, 2020, 925.9259259259259, 60, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1233, '01', 4, 2020, 185.18518518518516, 59, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1234, '02', 4, 2020, 185.18518518518516, 59, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1235, '03', 4, 2020, 222.2222222222222, 59, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1236, '04', 4, 2020, 222.2222222222222, 59, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1237, '05', 4, 2020, 185.18518518518516, 59, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1238, '01', 4, 2020, 370.3703703703703, 63, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1239, '02', 4, 2020, 370.3703703703703, 63, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1240, '03', 4, 2020, 444.4444444444444, 63, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1241, '04', 4, 2020, 444.4444444444444, 63, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1242, '05', 4, 2020, 370.3703703703703, 63, 'J-77741220004');
INSERT INTO public.detalle_pagos VALUES (1243, '2', 4, 2020, 400, 64, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1244, '3', 4, 2020, 400, 64, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1245, '4', 4, 2020, 400, 64, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1246, '5', 4, 2020, 400, 64, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1247, '1', 4, 2020, 400, 64, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1248, '2', 4, 2020, 1000, 65, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1249, '3', 4, 2020, 1000, 65, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1250, '4', 4, 2020, 1000, 65, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1251, '5', 4, 2020, 1000, 65, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1252, '1', 4, 2020, 1000, 65, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1253, '2', 4, 2020, 600, 67, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1254, '3', 4, 2020, 600, 67, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1255, '4', 4, 2020, 600, 67, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1256, '5', 4, 2020, 600, 67, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1257, '1', 4, 2020, 600, 67, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1258, '2', 4, 2020, 200, 66, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1259, '3', 4, 2020, 200, 66, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1260, '4', 4, 2020, 200, 66, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1261, '5', 4, 2020, 200, 66, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1262, '1', 4, 2020, 200, 66, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1263, '2', 4, 2020, 600, 68, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1264, '3', 4, 2020, 600, 68, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1265, '4', 4, 2020, 600, 68, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1266, '5', 4, 2020, 600, 68, 'J-77663250004');
INSERT INTO public.detalle_pagos VALUES (1267, '1', 4, 2020, 600, 68, 'J-77663250004');


--
-- TOC entry 3143 (class 0 OID 26816)
-- Dependencies: 228
-- Data for Name: detalle_sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_sancion VALUES (138, 'A1', 26, 4, 2020, 'J-4512698007', 100);
INSERT INTO public.detalle_sancion VALUES (139, 'A2', 28, 4, 2020, 'J-4512698007', 200);
INSERT INTO public.detalle_sancion VALUES (140, 'B2', 29, 4, 2020, 'J-4512698007', 250);
INSERT INTO public.detalle_sancion VALUES (141, 'E2', 29, 4, 2020, 'J-4512698007', 250);
INSERT INTO public.detalle_sancion VALUES (142, 'D1', 30, 4, 2020, 'J-4512698007', 50);
INSERT INTO public.detalle_sancion VALUES (143, '1', 31, 4, 2020, 'J-0145232547', 50);
INSERT INTO public.detalle_sancion VALUES (144, '2', 32, 4, 2020, 'J-0145232547', 100);
INSERT INTO public.detalle_sancion VALUES (145, '3', 33, 4, 2020, 'J-0145232547', 150);
INSERT INTO public.detalle_sancion VALUES (146, '4', 34, 4, 2020, 'J-0145232547', 200);
INSERT INTO public.detalle_sancion VALUES (147, '05', 36, 4, 2020, 'J-9685745568', 50);
INSERT INTO public.detalle_sancion VALUES (148, '04', 37, 4, 2020, 'J-9685745568', 100);
INSERT INTO public.detalle_sancion VALUES (149, '03', 38, 4, 2020, 'J-9685745568', 150);
INSERT INTO public.detalle_sancion VALUES (150, '02', 39, 4, 2020, 'J-9685745568', 200);
INSERT INTO public.detalle_sancion VALUES (151, '1A', 41, 4, 2020, 'J-0024514563', 50);
INSERT INTO public.detalle_sancion VALUES (152, '2B', 42, 4, 2020, 'J-0024514563', 100);
INSERT INTO public.detalle_sancion VALUES (153, '4A', 43, 4, 2020, 'J-0024514563', 150);
INSERT INTO public.detalle_sancion VALUES (154, '4B', 44, 4, 2020, 'J-0024514563', 200);
INSERT INTO public.detalle_sancion VALUES (155, '6', 46, 4, 2020, 'J-2540768143', 20);
INSERT INTO public.detalle_sancion VALUES (156, '7', 47, 4, 2020, 'J-2540768143', 40);
INSERT INTO public.detalle_sancion VALUES (157, '8', 48, 4, 2020, 'J-2540768143', 60);
INSERT INTO public.detalle_sancion VALUES (158, '9', 49, 4, 2020, 'J-2540768143', 80);
INSERT INTO public.detalle_sancion VALUES (159, '003', 51, 4, 2020, 'J-5784968566654', 100);
INSERT INTO public.detalle_sancion VALUES (160, '001', 52, 4, 2020, 'J-5784968566654', 150);
INSERT INTO public.detalle_sancion VALUES (161, '002', 53, 4, 2020, 'J-5784968566654', 200);
INSERT INTO public.detalle_sancion VALUES (162, '004', 54, 4, 2020, 'J-5784968566654', 250);
INSERT INTO public.detalle_sancion VALUES (163, '11', 56, 4, 2020, 'J-7774122010236', 50);
INSERT INTO public.detalle_sancion VALUES (164, '12', 57, 4, 2020, 'J-7774122010236', 100);
INSERT INTO public.detalle_sancion VALUES (165, '13', 58, 4, 2020, 'J-7774122010236', 150);
INSERT INTO public.detalle_sancion VALUES (166, '15', 59, 4, 2020, 'J-7774122010236', 200);
INSERT INTO public.detalle_sancion VALUES (167, '01', 61, 4, 2020, 'J-77741220004', 100);
INSERT INTO public.detalle_sancion VALUES (168, '02', 62, 4, 2020, 'J-77741220004', 100);
INSERT INTO public.detalle_sancion VALUES (169, '03', 63, 4, 2020, 'J-77741220004', 200);
INSERT INTO public.detalle_sancion VALUES (170, '04', 64, 4, 2020, 'J-77741220004', 300);
INSERT INTO public.detalle_sancion VALUES (171, '1', 66, 4, 2020, 'J-77663250004', 50);
INSERT INTO public.detalle_sancion VALUES (172, '2', 67, 4, 2020, 'J-77663250004', 100);
INSERT INTO public.detalle_sancion VALUES (173, '4', 68, 4, 2020, 'J-77663250004', 160);
INSERT INTO public.detalle_sancion VALUES (174, '5', 70, 4, 2020, 'J-77663250004', 80);


--
-- TOC entry 3145 (class 0 OID 26821)
-- Dependencies: 230
-- Data for Name: detalle_total; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_total VALUES (97, 'B2', 7747, 4, 2020, 0.2, 'Pendiente de Pago', 'J-4512698007', 7747);
INSERT INTO public.detalle_total VALUES (98, 'E2', 7747, 4, 2020, 0.2, 'Pendiente de Pago', 'J-4512698007', 7747);
INSERT INTO public.detalle_total VALUES (99, 'D1', 7547, 4, 2020, 0.2, 'Pendiente de Pago', 'J-4512698007', 7547);
INSERT INTO public.detalle_total VALUES (95, 'A1', 7597, 4, 2020, 0.2, 'Pagado', 'J-4512698007', 0);
INSERT INTO public.detalle_total VALUES (96, 'A2', 7697, 4, 2020, 0.2, 'Pagado', 'J-4512698007', 0);
INSERT INTO public.detalle_total VALUES (100, '1', 7853, 4, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 7853);
INSERT INTO public.detalle_total VALUES (101, '2', 7903, 4, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 7903);
INSERT INTO public.detalle_total VALUES (102, '3', 7953, 4, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 7953);
INSERT INTO public.detalle_total VALUES (103, '4', 8003, 4, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 8003);
INSERT INTO public.detalle_total VALUES (104, '5', 7803, 4, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 7803);
INSERT INTO public.detalle_total VALUES (106, '2', 1377, 5, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 1377);
INSERT INTO public.detalle_total VALUES (107, '3', 1377, 5, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 1377);
INSERT INTO public.detalle_total VALUES (108, '4', 1377, 5, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 1377);
INSERT INTO public.detalle_total VALUES (109, '5', 1377, 5, 2020, 0.2, 'Pendiente de Pago', 'J-0145232547', 1377);
INSERT INTO public.detalle_total VALUES (105, '1', 1377, 5, 2020, 0.2, 'Pagado', 'J-0145232547', 0);
INSERT INTO public.detalle_total VALUES (110, '01', 8413, 4, 2020, 0.2, 'Pendiente de Pago', 'J-9685745568', 8413);
INSERT INTO public.detalle_total VALUES (111, '02', 8613, 4, 2020, 0.2, 'Pendiente de Pago', 'J-9685745568', 8613);
INSERT INTO public.detalle_total VALUES (112, '03', 8563, 4, 2020, 0.2, 'Pendiente de Pago', 'J-9685745568', 8563);
INSERT INTO public.detalle_total VALUES (113, '04', 8513, 4, 2020, 0.2, 'Pendiente de Pago', 'J-9685745568', 8513);
INSERT INTO public.detalle_total VALUES (114, '05', 8463, 4, 2020, 0.2, 'Pendiente de Pago', 'J-9685745568', 8463);
INSERT INTO public.detalle_total VALUES (115, '1A', 8619.93388429752, 4, 2020, 0.1669421487603306, 'Pendiente de Pago', 'J-0024514563', 8619.93388429752);
INSERT INTO public.detalle_total VALUES (116, '2B', 8669.93388429752, 4, 2020, 0.1669421487603306, 'Pendiente de Pago', 'J-0024514563', 8669.93388429752);
INSERT INTO public.detalle_total VALUES (117, '4A', 8719.93388429752, 4, 2020, 0.1669421487603306, 'Pendiente de Pago', 'J-0024514563', 8719.93388429752);
INSERT INTO public.detalle_total VALUES (118, '4B', 8769.93388429752, 4, 2020, 0.1669421487603306, 'Pendiente de Pago', 'J-0024514563', 8769.93388429752);
INSERT INTO public.detalle_total VALUES (119, 'PH', 12430.264462809915, 4, 2020, 0.3322314049586777, 'Pendiente de Pago', 'J-0024514563', 12430.264462809915);
INSERT INTO public.detalle_total VALUES (120, '6', 7805, 4, 2020, 0.2, 'Pendiente de Pago', 'J-2540768143', 7805);
INSERT INTO public.detalle_total VALUES (121, '7', 7825, 4, 2020, 0.2, 'Pendiente de Pago', 'J-2540768143', 7825);
INSERT INTO public.detalle_total VALUES (122, '8', 7845, 4, 2020, 0.2, 'Pendiente de Pago', 'J-2540768143', 7845);
INSERT INTO public.detalle_total VALUES (123, '9', 7865, 4, 2020, 0.2, 'Pendiente de Pago', 'J-2540768143', 7865);
INSERT INTO public.detalle_total VALUES (124, '10', 7785, 4, 2020, 0.2, 'Pendiente de Pago', 'J-2540768143', 7785);
INSERT INTO public.detalle_total VALUES (125, '003', 4800, 4, 2020, 0.2, 'Pendiente de Pago', 'J-5784968566654', 4800);
INSERT INTO public.detalle_total VALUES (126, '001', 4850, 4, 2020, 0.2, 'Pendiente de Pago', 'J-5784968566654', 4850);
INSERT INTO public.detalle_total VALUES (127, '002', 4900, 4, 2020, 0.2, 'Pendiente de Pago', 'J-5784968566654', 4900);
INSERT INTO public.detalle_total VALUES (128, '004', 4950, 4, 2020, 0.2, 'Pendiente de Pago', 'J-5784968566654', 4950);
INSERT INTO public.detalle_total VALUES (129, '005', 4700, 4, 2020, 0.2, 'Pendiente de Pago', 'J-5784968566654', 4700);
INSERT INTO public.detalle_total VALUES (130, '11', 5450, 4, 2020, 0.2, 'Pendiente de Pago', 'J-7774122010236', 5450);
INSERT INTO public.detalle_total VALUES (131, '12', 5500, 4, 2020, 0.2, 'Pendiente de Pago', 'J-7774122010236', 5500);
INSERT INTO public.detalle_total VALUES (132, '13', 5550, 4, 2020, 0.2, 'Pendiente de Pago', 'J-7774122010236', 5550);
INSERT INTO public.detalle_total VALUES (133, '15', 5600, 4, 2020, 0.2, 'Pendiente de Pago', 'J-7774122010236', 5600);
INSERT INTO public.detalle_total VALUES (134, '14', 5400, 4, 2020, 0.2, 'Pendiente de Pago', 'J-7774122010236', 5400);
INSERT INTO public.detalle_total VALUES (135, '01', 5322.222222222223, 4, 2020, 0.18518518518518517, 'Pendiente de Pago', 'J-77741220004', 5322.222222222223);
INSERT INTO public.detalle_total VALUES (136, '02', 5322.222222222223, 4, 2020, 0.18518518518518517, 'Pendiente de Pago', 'J-77741220004', 5322.222222222223);
INSERT INTO public.detalle_total VALUES (137, '03', 5866.666666666666, 4, 2020, 0.2222222222222222, 'Pendiente de Pago', 'J-77741220004', 5866.666666666666);
INSERT INTO public.detalle_total VALUES (138, '04', 5966.666666666666, 4, 2020, 0.2222222222222222, 'Pendiente de Pago', 'J-77741220004', 5966.666666666666);
INSERT INTO public.detalle_total VALUES (139, '05', 5222.222222222223, 4, 2020, 0.18518518518518517, 'Pendiente de Pago', 'J-77741220004', 5222.222222222223);
INSERT INTO public.detalle_total VALUES (140, '2', 5700, 4, 2020, 0.2, 'Pendiente de Pago', 'J-77663250004', 5700);
INSERT INTO public.detalle_total VALUES (141, '3', 5600, 4, 2020, 0.2, 'Pendiente de Pago', 'J-77663250004', 5600);
INSERT INTO public.detalle_total VALUES (142, '4', 5760, 4, 2020, 0.2, 'Pendiente de Pago', 'J-77663250004', 5760);
INSERT INTO public.detalle_total VALUES (143, '5', 5680, 4, 2020, 0.2, 'Pendiente de Pago', 'J-77663250004', 5680);
INSERT INTO public.detalle_total VALUES (144, '1', 5650, 4, 2020, 0.2, 'Pendiente de Pago', 'J-77663250004', 5650);


--
-- TOC entry 3147 (class 0 OID 26826)
-- Dependencies: 232
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-05-01', '-', '-', 4000, 4000, 'J-9685745568', 7);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-06-29', '-', '-', 5000, 5000, 'J-0024514563', 8);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-06-25', '', '', 8000, 8000, 'J-2540768143', 9);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-30', '-', '-', 10000, 10000, 'J-4512698007', 10);
INSERT INTO public.fondos VALUES ('Fondo Especial', '2020-05-01', '-', '-', 5000, 5000, 'J-4512698007', 11);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-29', '-', '-', 6000, 6000, 'J-4512698007', 12);
INSERT INTO public.fondos VALUES ('Fondo de Gastos Comunes', '2020-04-29', '-', '-', 9000, 9000, 'J-4512698007', 13);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 10000, 10000, 'J-0145232547', 14);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 15000, 15000, 'J-0145232547', 15);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 8000, 8000, 'J-0145232547', 16);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 10000, 10000, 'J-0145232547', 17);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 15000, 15000, 'J-9685745568', 18);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-02', '', '', 20000, 20000, 'J-9685745568', 19);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-02', '', '', 10000, 10000, 'J-9685745568', 20);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-02', '', '', 5000, 5000, 'J-9685745568', 21);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-14', '', '', 15000, 15000, 'J-0024514563', 22);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-14', '', '', 10000, 10000, 'J-0024514563', 23);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-14', '', '', 10000, 10000, 'J-0024514563', 24);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-07', '', '', 8000, 8000, 'J-0024514563', 25);
INSERT INTO public.fondos VALUES ('Fondo Incial', '2020-04-01', '', '', 20000, 20000, 'J-2540768143', 26);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 25000, 25000, 'J-2540768143', 27);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 10000, 10000, 'J-2540768143', 28);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 8000, 8000, 'J-2540768143', 29);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-04-01', '', '', 15000, 15000, 'J-5784968566654', 30);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 20000, 20000, 'J-5784968566654', 31);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 25000, 25000, 'J-5784968566654', 32);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 10000, 10000, 'J-5784968566654', 33);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 10000, 10000, 'J-5784968566654', 34);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-04-01', '', '', 5000, 5000, 'J-7774122010236', 35);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 20000, 20000, 'J-7774122010236', 36);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 15000, 15000, 'J-7774122010236', 37);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 18000, 18000, 'J-7774122010236', 38);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 25000, 25000, 'J-7774122010236', 39);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-04-01', '', '', 10000, 10000, 'J-77741220004', 40);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 20000, 20000, 'J-77741220004', 41);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 15000, 15000, 'J-77741220004', 42);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 15000, 15000, 'J-77741220004', 43);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 25000, 25000, 'J-77741220004', 44);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-04-01', '', '', 5000, 5000, 'J-77663250004', 45);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 20000, 20000, 'J-77663250004', 46);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 25000, 25000, 'J-77663250004', 47);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 15000, 15000, 'J-77663250004', 48);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 10000, 10000, 'J-77663250004', 49);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-04-01', '', '', 10000, 10000, 'J-9854762000', 50);
INSERT INTO public.fondos VALUES ('Fondo Inicial', '2020-04-01', '', '', 20000, 20000, 'J-9854762000', 51);
INSERT INTO public.fondos VALUES ('Fondo de Trabajo', '2020-04-01', '', '', 10000, 10000, 'J-9854762000', 52);
INSERT INTO public.fondos VALUES ('Fondo de Prestaciones Sociales', '2020-04-01', '', '', 10000, 10000, 'J-9854762000', 53);
INSERT INTO public.fondos VALUES ('Fondo de Reserva', '2020-04-01', '', '', 25000, 25000, 'J-9854762000', 54);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-05-01', '-', '-', 3000, 18294, 'J-4512698007', 5);
INSERT INTO public.fondos VALUES ('Gastos Varios', '2020-05-01', '-', '-', 3000, 4377, 'J-0145232547', 6);


--
-- TOC entry 3149 (class 0 OID 26834)
-- Dependencies: 234
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto_comun VALUES (69, 'Ordinario', 4, 2020, 1000, '65412357', 'J-1001245215', 5, '', '2020-04-01', 'Pendiente', 'J-9854762000', 1000);
INSERT INTO public.gasto_comun VALUES (70, 'Ordinario', 4, 2020, 2000, '01245256560', 'J-1001245215', 5, '', '2020-04-06', 'Pendiente', 'J-9854762000', 2000);
INSERT INTO public.gasto_comun VALUES (71, 'Extraordinario', 4, 2020, 2000, '65749546', '17102635', 9, '', '2020-04-30', 'Pendiente', 'J-9854762000', 2000);
INSERT INTO public.gasto_comun VALUES (73, 'Ordinario', 4, 2020, 5000, '01144252330', '24666587', 24, '', '2020-04-18', 'Pendiente', 'J-9854762000', 5000);
INSERT INTO public.gasto_comun VALUES (72, 'Ordinario', 4, 2020, 3000, '6545496846', '17102635', 2, '', '2020-04-07', 'Pendiente', 'J-9854762000', 3000);
INSERT INTO public.gasto_comun VALUES (28, 'Ordinario', 4, 2020, 3000, '32547896', 'J-2457903215', 5, 'Se compraron vidrios para planta baja', '2020-05-25', 'Procesado', 'J-4512698007', 3000);
INSERT INTO public.gasto_comun VALUES (24, 'Ordinario', 4, 2020, 1000, '1024530152', 'J-1001245215', 5, 'Se compraron bombillos para la planta baja y el sotano', '2020-04-14', 'Procesado', 'J-4512698007', 1000);
INSERT INTO public.gasto_comun VALUES (27, 'Ordinario', 4, 2020, 5000, '1247778546', 'J-1001245215', 5, 'Se compro pintura en aceite para el porton del edificio', '2020-05-10', 'Procesado', 'J-4512698007', 5000);
INSERT INTO public.gasto_comun VALUES (26, 'Ordinario', 4, 2020, 5000, '124504862', '24666587', 31, 'Se repararon las tuberias de aguas blancas', '2020-05-13', 'Procesado', 'J-4512698007', 5000);
INSERT INTO public.gasto_comun VALUES (25, 'Extraordinario', 4, 2020, 1000, '146578632', '17102635', 2, 'Bono vacacional', '2020-04-11', 'Procesado', 'J-4512698007', 1000);
INSERT INTO public.gasto_comun VALUES (33, 'Ordinario', 4, 2020, 5000, '6897456333', 'J-54785696', 4, '', '2020-04-20', 'Procesado', 'J-0145232547', 5000);
INSERT INTO public.gasto_comun VALUES (30, 'Ordinario', 4, 2020, 2000, '6587946251', 'J-2457021456', 5, '', '2020-04-08', 'Procesado', 'J-0145232547', 2000);
INSERT INTO public.gasto_comun VALUES (32, 'Ordinario', 4, 2020, 4000, '598798765', '12457896', 16, '', '2020-04-10', 'Procesado', 'J-0145232547', 4000);
INSERT INTO public.gasto_comun VALUES (29, 'Ordinario', 4, 2020, 1000, '564789', 'J-1001245215', 32, '', '2020-04-08', 'Procesado', 'J-0145232547', 1000);
INSERT INTO public.gasto_comun VALUES (31, 'Extraordinario', 4, 2020, 3000, '4785963', '17102635', 29, '', '2020-04-07', 'Procesado', 'J-0145232547', 3000);
INSERT INTO public.gasto_comun VALUES (36, 'Ordinario', 4, 2020, 2500, '6587968', 'J-54785696', 4, '', '2020-04-09', 'Procesado', 'J-9685745568', 2500);
INSERT INTO public.gasto_comun VALUES (35, 'Ordinario', 4, 2020, 2000, '6587968', '17102635', 5, '', '2020-04-09', 'Procesado', 'J-9685745568', 2000);
INSERT INTO public.gasto_comun VALUES (34, 'Ordinario', 4, 2020, 1000, '587456', '24666587', 24, '', '2020-04-09', 'Procesado', 'J-9685745568', 1000);
INSERT INTO public.gasto_comun VALUES (37, 'Ordinario', 4, 2020, 5000, '654796565', 'J-547859655', 35, '', '2020-04-14', 'Procesado', 'J-9685745568', 5000);
INSERT INTO public.gasto_comun VALUES (38, 'Extraordinario', 4, 2020, 4000, '654796565', '17102635', 2, '', '2020-04-11', 'Procesado', 'J-9685745568', 4000);
INSERT INTO public.gasto_comun VALUES (41, 'Ordinario', 4, 2020, 3000, '658794566', '17102635', 2, '', '2020-04-08', 'Procesado', 'J-0024514563', 3000);
INSERT INTO public.gasto_comun VALUES (42, 'Ordinario', 4, 2020, 4000, '664657896', 'J-54785696', 4, '', '2020-04-14', 'Procesado', 'J-0024514563', 4000);
INSERT INTO public.gasto_comun VALUES (40, 'Ordinario', 4, 2020, 2000, '12548525465', 'J-2457021456', 5, '', '2020-04-10', 'Procesado', 'J-0024514563', 2000);
INSERT INTO public.gasto_comun VALUES (39, 'Ordinario', 4, 2020, 1000, '698574', 'J-1001245215', 5, '', '2020-04-02', 'Procesado', 'J-0024514563', 1000);
INSERT INTO public.gasto_comun VALUES (43, 'Extraordinario', 4, 2020, 3500, '69696589', '17102635', 8, '', '2020-04-07', 'Procesado', 'J-0024514563', 3500);
INSERT INTO public.gasto_comun VALUES (44, 'Ordinario', 4, 2020, 1000, '646216984', '17102635', 2, '', '2020-04-01', 'Procesado', 'J-2540768143', 1000);
INSERT INTO public.gasto_comun VALUES (46, 'Ordinario', 4, 2020, 2000, '4746984968', 'J-54785696', 4, '', '2020-04-06', 'Procesado', 'J-2540768143', 2000);
INSERT INTO public.gasto_comun VALUES (48, 'Ordinario', 4, 2020, 4000, '56568944516', 'J-1001245215', 5, '', '2020-04-20', 'Procesado', 'J-2540768143', 4000);
INSERT INTO public.gasto_comun VALUES (47, 'Ordinario', 4, 2020, 3000, '4789652', '24666587', 31, '', '2020-04-15', 'Procesado', 'J-2540768143', 3000);
INSERT INTO public.gasto_comun VALUES (45, 'Extraordinario', 4, 2020, 1500, '64626555', '17102635', 8, '', '2020-04-04', 'Procesado', 'J-2540768143', 1500);
INSERT INTO public.gasto_comun VALUES (50, 'Ordinario', 4, 2020, 1000, '69855966756', 'J-1001245215', 5, '', '2020-04-17', 'Procesado', 'J-5784968566654', 1000);
INSERT INTO public.gasto_comun VALUES (51, 'Ordinario', 4, 2020, 2000, '69855966756', 'J-1001245215', 5, '', '2020-04-06', 'Procesado', 'J-5784968566654', 2000);
INSERT INTO public.gasto_comun VALUES (52, 'Ordinario', 4, 2020, 3000, '69855966756', 'J-1001245215', 19, '', '2020-04-06', 'Procesado', 'J-5784968566654', 3000);
INSERT INTO public.gasto_comun VALUES (53, 'Ordinario', 4, 2020, 3000, '6774454545', 'J-1001245215', 22, '', '2020-04-12', 'Procesado', 'J-5784968566654', 3000);
INSERT INTO public.gasto_comun VALUES (49, 'Extraordinario', 4, 2020, 3000, '654164631631', '17102635', 9, '', '2020-04-08', 'Procesado', 'J-5784968566654', 3000);
INSERT INTO public.gasto_comun VALUES (57, 'Ordinario', 4, 2020, 4000, '47878874563000', 'J-54785696', 4, '', '2020-04-12', 'Procesado', 'J-7774122010236', 4000);
INSERT INTO public.gasto_comun VALUES (55, 'Ordinario', 4, 2020, 2000, '32569877', 'J-2457021456', 5, '', '2020-04-16', 'Procesado', 'J-7774122010236', 2000);
INSERT INTO public.gasto_comun VALUES (54, 'Ordinario', 4, 2020, 1000, '5465169861632', 'J-1001245215', 5, '', '2020-04-01', 'Procesado', 'J-7774122010236', 1000);
INSERT INTO public.gasto_comun VALUES (56, 'Ordinario', 4, 2020, 3000, '5789655856', '17102635', 9, '', '2020-04-13', 'Procesado', 'J-7774122010236', 3000);
INSERT INTO public.gasto_comun VALUES (58, 'Ordinario', 4, 2020, 5000, '36528745124', '12457896', 16, '', '2020-04-29', 'Procesado', 'J-7774122010236', 5000);
INSERT INTO public.gasto_comun VALUES (62, 'Ordinario', 4, 2020, 1000, '002454263', '17102635', 2, '', '2020-04-04', 'Procesado', 'J-77741220004', 1000);
INSERT INTO public.gasto_comun VALUES (61, 'Ordinario', 4, 2020, 3000, '0012453642312', 'J-54785696', 4, '', '2020-04-20', 'Procesado', 'J-77741220004', 3000);
INSERT INTO public.gasto_comun VALUES (60, 'Ordinario', 4, 2020, 5000, '36657893642312', 'J-1001245215', 5, '', '2020-04-15', 'Procesado', 'J-77741220004', 5000);
INSERT INTO public.gasto_comun VALUES (59, 'Ordinario', 4, 2020, 1000, '3665984654', 'J-1001245215', 5, '', '2020-04-07', 'Procesado', 'J-77741220004', 1000);
INSERT INTO public.gasto_comun VALUES (63, 'Extraordinario', 4, 2020, 2000, '002454263', '17102635', 9, '', '2020-04-29', 'Procesado', 'J-77741220004', 2000);
INSERT INTO public.gasto_comun VALUES (64, 'Ordinario', 4, 2020, 2000, '9795463521', '17102635', 2, '', '2020-04-02', 'Procesado', 'J-77663250004', 2000);
INSERT INTO public.gasto_comun VALUES (65, 'Ordinario', 4, 2020, 5000, '9795463521', 'J-54785696', 4, '', '2020-04-16', 'Procesado', 'J-77663250004', 5000);
INSERT INTO public.gasto_comun VALUES (67, 'Ordinario', 4, 2020, 3000, '97946513', 'J-2457021456', 5, '', '2020-04-14', 'Procesado', 'J-77663250004', 3000);
INSERT INTO public.gasto_comun VALUES (66, 'Ordinario', 4, 2020, 1000, '9795463521', 'J-1001245215', 5, '', '2020-04-16', 'Procesado', 'J-77663250004', 1000);
INSERT INTO public.gasto_comun VALUES (68, 'Extraordinario', 4, 2020, 3000, '979878650000', '17102635', 9, '', '2020-04-29', 'Procesado', 'J-77663250004', 3000);
INSERT INTO public.gasto_comun VALUES (74, 'Ordinario', 5, 2020, 123123, '2344', 'J-1001245215', 1, 'fdsdff', '2020-05-09', 'Pendiente', 'J-4512698007', 123123);
INSERT INTO public.gasto_comun VALUES (75, 'Ordinario', 9, 2020, 213123, '213123', 'J-1001245215', 1, '', '2020-05-16', 'Pendiente', 'J-4512698007', 213123);
INSERT INTO public.gasto_comun VALUES (76, 'Ordinario', 5, 2020, 2133, '1231', 'J-2457021456', 0, 'saasd', '2020-05-09', 'Pendiente', 'J-4512698007', 2133);
INSERT INTO public.gasto_comun VALUES (77, 'Ordinario', 5, 2020, 123123, '21231', 'J-2457021456', 0, '', '2020-05-08', 'Pendiente', 'J-4512698007', 123123);
INSERT INTO public.gasto_comun VALUES (78, 'Ordinario', 10, 2020, 123123, '2132', 'J-2457903215', 14, 'sasd', '2020-05-09', 'Pendiente', 'J-0145232547', 123123);


--
-- TOC entry 3151 (class 0 OID 26839)
-- Dependencies: 236
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (14, 'Compensatorios', 6, 'Activo', 1);
INSERT INTO public.interes VALUES (10, 'Fondo de Reserva', 30, 'Activo', 1);
INSERT INTO public.interes VALUES (15, 'Legales', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (12, 'Anatosismo', 4, 'Activo', 1);
INSERT INTO public.interes VALUES (13, 'Convencional', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (16, 'indexacion', 2, 'Activo', 1);
INSERT INTO public.interes VALUES (11, 'Liquidacion', 5, 'Activo', 1);
INSERT INTO public.interes VALUES (8, 'Inflacionario', 60, 'Activo', 1);
INSERT INTO public.interes VALUES (9, 'Prestaciones Sociales', 10, 'Activo', 1);
INSERT INTO public.interes VALUES (17, 'Moratorios', 3, 'Activo', 1);


--
-- TOC entry 3153 (class 0 OID 26844)
-- Dependencies: 238
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
-- TOC entry 3154 (class 0 OID 26847)
-- Dependencies: 239
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
-- TOC entry 3155 (class 0 OID 26853)
-- Dependencies: 240
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asamblea_propietario VALUES (46, 33, '27328852');
INSERT INTO public.puente_asamblea_propietario VALUES (47, 33, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (48, 33, '26943430');
INSERT INTO public.puente_asamblea_propietario VALUES (49, 33, '24154789');
INSERT INTO public.puente_asamblea_propietario VALUES (109, 51, '10921542');
INSERT INTO public.puente_asamblea_propietario VALUES (110, 51, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (111, 51, '24154789');
INSERT INTO public.puente_asamblea_propietario VALUES (112, 52, '10921542');
INSERT INTO public.puente_asamblea_propietario VALUES (113, 52, '27328852');
INSERT INTO public.puente_asamblea_propietario VALUES (114, 52, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (115, 52, '26943430');
INSERT INTO public.puente_asamblea_propietario VALUES (116, 52, '24154789');
INSERT INTO public.puente_asamblea_propietario VALUES (117, 53, '10921542');
INSERT INTO public.puente_asamblea_propietario VALUES (118, 53, '27328852');
INSERT INTO public.puente_asamblea_propietario VALUES (119, 53, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (120, 53, '26943430');
INSERT INTO public.puente_asamblea_propietario VALUES (121, 54, '27328852');
INSERT INTO public.puente_asamblea_propietario VALUES (122, 54, '26943430');
INSERT INTO public.puente_asamblea_propietario VALUES (123, 54, '24154789');
INSERT INTO public.puente_asamblea_propietario VALUES (124, 55, '1245637');
INSERT INTO public.puente_asamblea_propietario VALUES (125, 55, '3556789');
INSERT INTO public.puente_asamblea_propietario VALUES (126, 55, '1545698');
INSERT INTO public.puente_asamblea_propietario VALUES (127, 55, '9456874');
INSERT INTO public.puente_asamblea_propietario VALUES (128, 56, '1245637');
INSERT INTO public.puente_asamblea_propietario VALUES (129, 56, '3556789');
INSERT INTO public.puente_asamblea_propietario VALUES (130, 56, '1545698');
INSERT INTO public.puente_asamblea_propietario VALUES (131, 56, '9456874');
INSERT INTO public.puente_asamblea_propietario VALUES (132, 56, '5484633');
INSERT INTO public.puente_asamblea_propietario VALUES (133, 57, '1245637');
INSERT INTO public.puente_asamblea_propietario VALUES (134, 57, '3556789');
INSERT INTO public.puente_asamblea_propietario VALUES (135, 57, '1545698');
INSERT INTO public.puente_asamblea_propietario VALUES (136, 57, '9456874');
INSERT INTO public.puente_asamblea_propietario VALUES (137, 57, '5484633');
INSERT INTO public.puente_asamblea_propietario VALUES (138, 58, '3556789');
INSERT INTO public.puente_asamblea_propietario VALUES (139, 58, '9456874');
INSERT INTO public.puente_asamblea_propietario VALUES (140, 59, '1245637');
INSERT INTO public.puente_asamblea_propietario VALUES (141, 59, '1545698');
INSERT INTO public.puente_asamblea_propietario VALUES (142, 59, '9456874');
INSERT INTO public.puente_asamblea_propietario VALUES (143, 59, '5484633');
INSERT INTO public.puente_asamblea_propietario VALUES (144, 60, '4321554');
INSERT INTO public.puente_asamblea_propietario VALUES (145, 60, '3321554');
INSERT INTO public.puente_asamblea_propietario VALUES (146, 60, '73211450');
INSERT INTO public.puente_asamblea_propietario VALUES (147, 60, '15211450');
INSERT INTO public.puente_asamblea_propietario VALUES (148, 60, '8854237');
INSERT INTO public.puente_asamblea_propietario VALUES (149, 61, '3321554');
INSERT INTO public.puente_asamblea_propietario VALUES (150, 61, '73211450');
INSERT INTO public.puente_asamblea_propietario VALUES (151, 61, '15211450');
INSERT INTO public.puente_asamblea_propietario VALUES (152, 62, '3321554');
INSERT INTO public.puente_asamblea_propietario VALUES (153, 62, '15211450');
INSERT INTO public.puente_asamblea_propietario VALUES (154, 62, '8854237');
INSERT INTO public.puente_asamblea_propietario VALUES (155, 63, '4321554');
INSERT INTO public.puente_asamblea_propietario VALUES (156, 63, '3321554');
INSERT INTO public.puente_asamblea_propietario VALUES (157, 63, '15211450');
INSERT INTO public.puente_asamblea_propietario VALUES (158, 64, '3321554');
INSERT INTO public.puente_asamblea_propietario VALUES (159, 64, '73211450');
INSERT INTO public.puente_asamblea_propietario VALUES (160, 64, '15211450');
INSERT INTO public.puente_asamblea_propietario VALUES (161, 65, '18346152');
INSERT INTO public.puente_asamblea_propietario VALUES (162, 65, '19306051');
INSERT INTO public.puente_asamblea_propietario VALUES (163, 65, '4236500');
INSERT INTO public.puente_asamblea_propietario VALUES (164, 66, '18346152');
INSERT INTO public.puente_asamblea_propietario VALUES (165, 66, '19306051');
INSERT INTO public.puente_asamblea_propietario VALUES (166, 66, '1425368');
INSERT INTO public.puente_asamblea_propietario VALUES (167, 67, '18346152');
INSERT INTO public.puente_asamblea_propietario VALUES (168, 67, '19306051');
INSERT INTO public.puente_asamblea_propietario VALUES (169, 67, '1425368');
INSERT INTO public.puente_asamblea_propietario VALUES (170, 67, '4236500');
INSERT INTO public.puente_asamblea_propietario VALUES (171, 67, '9578450');
INSERT INTO public.puente_asamblea_propietario VALUES (172, 68, '18346152');
INSERT INTO public.puente_asamblea_propietario VALUES (173, 68, '19306051');
INSERT INTO public.puente_asamblea_propietario VALUES (174, 68, '1425368');
INSERT INTO public.puente_asamblea_propietario VALUES (175, 69, '18346152');
INSERT INTO public.puente_asamblea_propietario VALUES (176, 69, '19306051');
INSERT INTO public.puente_asamblea_propietario VALUES (177, 69, '1425368');
INSERT INTO public.puente_asamblea_propietario VALUES (178, 69, '4236500');
INSERT INTO public.puente_asamblea_propietario VALUES (179, 69, '9578450');
INSERT INTO public.puente_asamblea_propietario VALUES (180, 70, '13245789');
INSERT INTO public.puente_asamblea_propietario VALUES (181, 70, '16254700');
INSERT INTO public.puente_asamblea_propietario VALUES (182, 70, '18547895');
INSERT INTO public.puente_asamblea_propietario VALUES (183, 70, '15457896');
INSERT INTO public.puente_asamblea_propietario VALUES (184, 71, '13245789');
INSERT INTO public.puente_asamblea_propietario VALUES (185, 71, '4125868');
INSERT INTO public.puente_asamblea_propietario VALUES (186, 71, '16254700');
INSERT INTO public.puente_asamblea_propietario VALUES (187, 71, '18547895');
INSERT INTO public.puente_asamblea_propietario VALUES (188, 72, '4125868');
INSERT INTO public.puente_asamblea_propietario VALUES (189, 72, '16254700');
INSERT INTO public.puente_asamblea_propietario VALUES (190, 72, '18547895');
INSERT INTO public.puente_asamblea_propietario VALUES (191, 73, '13245789');
INSERT INTO public.puente_asamblea_propietario VALUES (192, 73, '4125868');
INSERT INTO public.puente_asamblea_propietario VALUES (193, 73, '16254700');
INSERT INTO public.puente_asamblea_propietario VALUES (194, 73, '18547895');
INSERT INTO public.puente_asamblea_propietario VALUES (195, 73, '15457896');
INSERT INTO public.puente_asamblea_propietario VALUES (196, 74, '13245789');
INSERT INTO public.puente_asamblea_propietario VALUES (197, 74, '4125868');
INSERT INTO public.puente_asamblea_propietario VALUES (198, 74, '18547895');
INSERT INTO public.puente_asamblea_propietario VALUES (199, 75, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (200, 75, '5478965');
INSERT INTO public.puente_asamblea_propietario VALUES (201, 75, '16547896');
INSERT INTO public.puente_asamblea_propietario VALUES (202, 76, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (203, 76, '10145236');
INSERT INTO public.puente_asamblea_propietario VALUES (204, 76, '20145271');
INSERT INTO public.puente_asamblea_propietario VALUES (205, 77, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (206, 77, '5478965');
INSERT INTO public.puente_asamblea_propietario VALUES (207, 77, '10145236');
INSERT INTO public.puente_asamblea_propietario VALUES (208, 77, '16547896');
INSERT INTO public.puente_asamblea_propietario VALUES (209, 77, '20145271');
INSERT INTO public.puente_asamblea_propietario VALUES (210, 78, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (211, 78, '10145236');
INSERT INTO public.puente_asamblea_propietario VALUES (212, 79, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (213, 79, '5478965');
INSERT INTO public.puente_asamblea_propietario VALUES (214, 79, '10145236');
INSERT INTO public.puente_asamblea_propietario VALUES (215, 79, '16547896');
INSERT INTO public.puente_asamblea_propietario VALUES (216, 79, '20145271');
INSERT INTO public.puente_asamblea_propietario VALUES (220, 24, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (221, 24, '2343');
INSERT INTO public.puente_asamblea_propietario VALUES (222, 24, '14528796');
INSERT INTO public.puente_asamblea_propietario VALUES (223, 24, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (224, 35, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (225, 35, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (226, 36, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (227, 36, '14528796');
INSERT INTO public.puente_asamblea_propietario VALUES (228, 37, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (229, 37, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (230, 37, '9457854');
INSERT INTO public.puente_asamblea_propietario VALUES (231, 38, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (232, 38, '14528796');
INSERT INTO public.puente_asamblea_propietario VALUES (233, 38, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (234, 38, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (235, 80, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (236, 80, '2343');
INSERT INTO public.puente_asamblea_propietario VALUES (237, 80, '14528796');
INSERT INTO public.puente_asamblea_propietario VALUES (238, 80, '14156247');
INSERT INTO public.puente_asamblea_propietario VALUES (239, 80, '6254789');
INSERT INTO public.puente_asamblea_propietario VALUES (240, 80, '9457854');
INSERT INTO public.puente_asamblea_propietario VALUES (241, 39, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (242, 40, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (243, 41, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (244, 26, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (245, 42, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (246, 44, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (247, 46, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (248, 43, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (249, 29, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (250, 45, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (251, 47, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (252, 49, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (253, 48, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (254, 31, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (255, 50, '24578965');
INSERT INTO public.puente_asamblea_propietario VALUES (256, 81, '1424801');
INSERT INTO public.puente_asamblea_propietario VALUES (257, 81, '18965742');
INSERT INTO public.puente_asamblea_propietario VALUES (258, 81, '20888725');


--
-- TOC entry 3157 (class 0 OID 26858)
-- Dependencies: 242
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_cobro_factura VALUES (5, 95, 8, 7597);
INSERT INTO public.puente_cobro_factura VALUES (6, 96, 9, 7697);
INSERT INTO public.puente_cobro_factura VALUES (7, 105, 10, 1377);


--
-- TOC entry 3159 (class 0 OID 26863)
-- Dependencies: 244
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_comunicado_usuario VALUES (4, '26843430', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (5, '26943430', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (6, '21963231', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (7, '17789654', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (8, '8475896', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (9, '7894563', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (10, '6457896', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (11, '6454565', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (12, '15246325', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (13, '20122354', 4, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (14, '26843430', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (15, '26943430', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (16, '21963231', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (17, '17789654', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (18, '8475896', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (19, '7894563', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (20, '6457896', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (21, '6454565', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (22, '15246325', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (23, '20122354', 5, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (24, '26843430', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (25, '26943430', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (26, '21963231', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (27, '17789654', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (28, '8475896', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (29, '7894563', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (30, '6457896', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (31, '6454565', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (32, '15246325', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (33, '20122354', 6, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (34, '26843430', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (35, '26943430', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (36, '21963231', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (37, '17789654', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (38, '8475896', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (39, '7894563', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (40, '6457896', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (41, '6454565', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (42, '15246325', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (43, '20122354', 7, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (44, '26843430', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (45, '26943430', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (46, '21963231', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (47, '17789654', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (48, '8475896', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (49, '7894563', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (50, '6457896', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (51, '6454565', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (52, '15246325', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (53, '20122354', 8, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (54, '26843430', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (55, '26943430', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (56, '21963231', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (57, '17789654', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (58, '8475896', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (59, '7894563', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (60, '6457896', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (61, '6454565', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (62, '15246325', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (63, '20122354', 9, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (64, '26843430', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (65, '26943430', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (66, '21963231', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (67, '17789654', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (68, '8475896', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (69, '7894563', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (70, '6457896', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (71, '6454565', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (72, '15246325', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (73, '20122354', 10, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (74, '26843430', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (75, '26943430', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (76, '21963231', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (77, '17789654', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (78, '8475896', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (79, '7894563', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (80, '6457896', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (81, '6454565', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (82, '15246325', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (83, '20122354', 11, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (84, '26843430', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (85, '26943430', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (86, '21963231', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (87, '17789654', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (88, '8475896', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (89, '7894563', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (90, '6457896', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (91, '6454565', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (92, '15246325', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (93, '20122354', 12, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (94, '26843430', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (95, '26943430', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (96, '21963231', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (97, '17789654', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (98, '8475896', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (99, '7894563', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (100, '6457896', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (101, '6454565', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (102, '15246325', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (103, '20122354', 13, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (104, '26843430', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (105, '26943430', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (106, '21963231', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (107, '17789654', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (108, '8475896', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (109, '7894563', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (110, '6457896', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (111, '6454565', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (112, '15246325', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (113, '20122354', 14, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (114, '26843430', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (115, '26943430', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (116, '21963231', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (117, '17789654', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (118, '8475896', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (119, '7894563', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (120, '6457896', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (121, '6454565', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (122, '15246325', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (123, '20122354', 15, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (124, '26843430', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (125, '26943430', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (126, '21963231', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (127, '17789654', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (128, '8475896', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (129, '7894563', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (130, '6457896', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (131, '6454565', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (132, '15246325', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (133, '20122354', 16, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (134, '26843430', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (135, '26943430', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (136, '21963231', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (137, '17789654', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (138, '8475896', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (139, '7894563', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (140, '6457896', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (141, '6454565', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (142, '15246325', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (143, '20122354', 17, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (144, '26843430', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (145, '26943430', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (146, '21963231', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (147, '17789654', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (148, '8475896', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (149, '7894563', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (150, '6457896', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (151, '6454565', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (152, '15246325', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (153, '20122354', 18, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (154, '26843430', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (155, '26943430', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (156, '21963231', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (157, '17789654', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (158, '8475896', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (159, '7894563', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (160, '6457896', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (161, '6454565', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (162, '15246325', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (163, '20122354', 19, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (164, '26843430', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (165, '26943430', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (166, '21963231', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (167, '17789654', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (168, '8475896', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (169, '7894563', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (170, '6457896', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (171, '6454565', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (172, '15246325', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (173, '20122354', 20, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (174, '26843430', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (175, '26943430', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (176, '21963231', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (177, '17789654', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (178, '8475896', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (179, '7894563', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (180, '6457896', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (181, '6454565', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (182, '15246325', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (183, '20122354', 21, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (184, '26843430', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (185, '26943430', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (186, '21963231', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (187, '17789654', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (188, '8475896', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (189, '7894563', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (190, '6457896', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (191, '6454565', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (192, '15246325', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (193, '20122354', 22, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (194, '26843430', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (195, '26943430', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (196, '21963231', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (197, '17789654', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (198, '8475896', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (199, '7894563', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (200, '6457896', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (201, '6454565', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (202, '15246325', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (203, '20122354', 23, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (204, '26843430', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (205, '26943430', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (206, '21963231', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (207, '17789654', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (208, '8475896', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (209, '7894563', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (210, '6457896', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (211, '6454565', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (212, '15246325', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (213, '20122354', 24, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (214, '26843430', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (215, '26943430', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (216, '21963231', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (217, '17789654', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (218, '8475896', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (219, '7894563', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (220, '6457896', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (221, '6454565', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (222, '15246325', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (223, '20122354', 25, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (224, '26843430', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (225, '26943430', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (226, '21963231', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (227, '17789654', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (228, '8475896', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (229, '7894563', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (230, '6457896', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (231, '6454565', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (232, '15246325', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (233, '20122354', 26, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (234, '26843430', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (235, '26943430', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (236, '21963231', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (237, '17789654', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (238, '8475896', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (239, '7894563', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (240, '6457896', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (241, '6454565', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (242, '15246325', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (243, '20122354', 27, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (244, '26843430', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (245, '26943430', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (246, '21963231', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (247, '17789654', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (248, '8475896', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (249, '7894563', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (250, '6457896', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (251, '6454565', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (252, '15246325', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (253, '20122354', 28, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (254, '26843430', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (255, '26943430', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (256, '21963231', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (257, '17789654', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (258, '8475896', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (259, '7894563', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (260, '6457896', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (261, '6454565', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (262, '15246325', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (263, '20122354', 29, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (264, '26843430', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (265, '26943430', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (266, '21963231', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (267, '17789654', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (268, '8475896', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (269, '7894563', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (270, '6457896', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (271, '6454565', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (272, '15246325', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (273, '20122354', 30, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (274, '26843430', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (275, '26943430', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (276, '21963231', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (277, '17789654', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (278, '8475896', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (279, '7894563', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (280, '6457896', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (281, '6454565', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (282, '15246325', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (283, '20122354', 31, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (284, '26843430', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (285, '26943430', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (286, '21963231', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (287, '17789654', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (288, '8475896', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (289, '7894563', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (290, '6457896', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (291, '6454565', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (292, '15246325', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (293, '20122354', 32, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (294, '26843430', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (295, '26943430', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (296, '21963231', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (297, '17789654', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (298, '8475896', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (299, '7894563', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (300, '6457896', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (301, '6454565', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (302, '15246325', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (303, '20122354', 33, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (304, '26843430', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (305, '26943430', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (306, '21963231', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (307, '17789654', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (308, '8475896', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (309, '7894563', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (310, '6457896', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (311, '6454565', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (312, '15246325', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (313, '20122354', 34, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (314, '26843430', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (315, '26943430', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (316, '21963231', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (317, '17789654', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (318, '8475896', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (319, '7894563', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (320, '6457896', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (321, '6454565', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (322, '15246325', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (323, '20122354', 35, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (324, '26843430', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (325, '26943430', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (326, '21963231', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (327, '17789654', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (328, '8475896', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (329, '7894563', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (330, '6457896', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (331, '6454565', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (332, '15246325', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (333, '20122354', 36, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (334, '26843430', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (335, '26943430', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (336, '21963231', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (337, '17789654', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (338, '8475896', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (339, '7894563', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (340, '6457896', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (341, '6454565', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (342, '15246325', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (343, '20122354', 37, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (344, '26843430', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (345, '26943430', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (346, '21963231', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (347, '17789654', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (348, '8475896', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (349, '7894563', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (350, '6457896', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (351, '6454565', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (352, '15246325', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (353, '20122354', 38, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (354, '26843430', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (355, '26943430', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (356, '21963231', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (357, '17789654', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (358, '8475896', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (359, '7894563', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (360, '6457896', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (361, '6454565', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (362, '15246325', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (363, '20122354', 39, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (364, '26843430', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (365, '26943430', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (366, '21963231', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (367, '17789654', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (368, '8475896', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (369, '7894563', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (370, '6457896', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (371, '6454565', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (372, '15246325', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (373, '20122354', 40, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (374, '26843430', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (375, '26943430', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (376, '21963231', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (377, '17789654', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (378, '8475896', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (379, '7894563', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (380, '6457896', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (381, '6454565', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (382, '15246325', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (383, '20122354', 41, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (384, '26843430', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (385, '26943430', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (386, '21963231', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (387, '17789654', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (388, '8475896', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (389, '7894563', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (390, '6457896', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (391, '6454565', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (392, '15246325', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (393, '20122354', 42, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (394, '26843430', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (395, '26943430', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (396, '21963231', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (397, '17789654', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (398, '8475896', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (399, '7894563', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (400, '6457896', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (401, '6454565', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (402, '15246325', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (403, '20122354', 43, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (404, '26843430', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (405, '26943430', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (406, '21963231', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (407, '17789654', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (408, '8475896', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (409, '7894563', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (410, '6457896', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (411, '6454565', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (412, '15246325', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (413, '20122354', 44, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (414, '26843430', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (415, '26943430', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (416, '21963231', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (417, '17789654', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (418, '8475896', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (419, '7894563', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (420, '6457896', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (421, '6454565', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (422, '15246325', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (423, '20122354', 45, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (424, '26843430', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (425, '26943430', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (426, '21963231', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (427, '17789654', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (428, '8475896', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (429, '7894563', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (430, '6457896', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (431, '6454565', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (432, '15246325', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (433, '20122354', 46, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (434, '26843430', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (435, '26943430', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (436, '21963231', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (437, '17789654', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (438, '8475896', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (439, '7894563', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (440, '6457896', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (441, '6454565', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (442, '15246325', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (443, '20122354', 47, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (444, '26843430', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (445, '26943430', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (446, '21963231', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (447, '17789654', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (448, '8475896', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (449, '7894563', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (450, '6457896', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (451, '6454565', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (452, '15246325', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (453, '20122354', 48, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (454, '26843430', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (455, '26943430', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (456, '21963231', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (457, '17789654', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (458, '8475896', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (459, '7894563', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (460, '6457896', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (461, '6454565', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (462, '15246325', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (463, '20122354', 49, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (464, '26843430', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (465, '26943430', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (466, '21963231', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (467, '17789654', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (468, '8475896', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (469, '7894563', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (470, '6457896', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (471, '6454565', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (472, '15246325', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (473, '20122354', 50, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (474, '26843430', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (475, '26943430', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (476, '21963231', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (477, '17789654', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (478, '8475896', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (479, '7894563', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (480, '6457896', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (481, '6454565', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (482, '15246325', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (483, '20122354', 51, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (484, '26843430', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (485, '26943430', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (486, '21963231', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (487, '17789654', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (488, '8475896', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (489, '7894563', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (490, '6457896', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (491, '6454565', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (492, '15246325', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (493, '20122354', 52, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (494, '26843430', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (495, '26943430', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (496, '21963231', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (497, '17789654', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (498, '8475896', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (499, '7894563', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (500, '6457896', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (501, '6454565', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (502, '15246325', 53, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (503, '20122354', 53, 0);


--
-- TOC entry 3161 (class 0 OID 26871)
-- Dependencies: 246
-- Data for Name: puente_condominio_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_condominio_cuenta VALUES (71, '01020045120268985654', 'J-4512698007', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (72, '01020045120268985654', 'J-0145232547', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (73, '01020045120268985654', 'J-9685745568', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (74, '01020045120268985654', 'J-0024514563', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (75, '01020045120268985654', 'J-2540768143', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (76, '01020045120268985654', 'J-5784968566654', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (77, '01020045120268985654', 'J-7774122010236', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (78, '01020045120268985654', 'J-77741220004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (79, '01020045120268985654', 'J-77663250004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (80, '01020045120268985654', 'J-9854762000', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (81, '01025487596584758945', 'J-4512698007', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (82, '01025487596584758945', 'J-0145232547', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (83, '01025487596584758945', 'J-9685745568', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (84, '01025487596584758945', 'J-0024514563', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (85, '01025487596584758945', 'J-2540768143', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (86, '01025487596584758945', 'J-5784968566654', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (87, '01025487596584758945', 'J-7774122010236', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (88, '01025487596584758945', 'J-77741220004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (89, '01025487596584758945', 'J-77663250004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (90, '01025487596584758945', 'J-9854762000', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (129, '01145247946596656485', 'J-4512698007', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (130, '01145247946596656485', 'J-0145232547', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (131, '01145247946596656485', 'J-9685745568', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (132, '01145247946596656485', 'J-0024514563', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (133, '01145247946596656485', 'J-2540768143', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (134, '01145247946596656485', 'J-5784968566654', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (135, '01145247946596656485', 'J-7774122010236', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (136, '01145247946596656485', 'J-77741220004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (137, '01145247946596656485', 'J-77663250004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (138, '01145247946596656485', 'J-9854762000', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (100, '01052458795254653322', 'J-4512698007', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (101, '01052458795254653322', 'J-0145232547', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (102, '01052458795254653322', 'J-9685745568', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (103, '01052458795254653322', 'J-0024514563', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (104, '01052458795254653322', 'J-2540768143', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (105, '01052458795254653322', 'J-5784968566654', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (106, '01052458795254653322', 'J-7774122010236', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (107, '01052458795254653322', 'J-77741220004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (108, '01052458795254653322', 'J-77663250004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (109, '01052458795254653322', 'J-9854762000', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (119, '01085698745685232540', 'J-4512698007', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (120, '01085698745685232540', 'J-0145232547', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (121, '01085698745685232540', 'J-9685745568', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (122, '01085698745685232540', 'J-0024514563', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (123, '01085698745685232540', 'J-2540768143', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (124, '01085698745685232540', 'J-5784968566654', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (125, '01085698745685232540', 'J-7774122010236', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (126, '01085698745685232540', 'J-77741220004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (127, '01085698745685232540', 'J-77663250004', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (128, '01085698745685232540', 'J-9854762000', 1);


--
-- TOC entry 3163 (class 0 OID 26876)
-- Dependencies: 248
-- Data for Name: puente_interes_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_interes_condominio VALUES (55, 'J-0145232547', 14, NULL);
INSERT INTO public.puente_interes_condominio VALUES (56, 'J-9685745568', 14, NULL);
INSERT INTO public.puente_interes_condominio VALUES (68, 'J-4512698007', 10, NULL);
INSERT INTO public.puente_interes_condominio VALUES (69, 'J-0145232547', 10, NULL);
INSERT INTO public.puente_interes_condominio VALUES (70, 'J-4512698007', 15, NULL);
INSERT INTO public.puente_interes_condominio VALUES (71, 'J-0145232547', 15, NULL);
INSERT INTO public.puente_interes_condominio VALUES (72, 'J-4512698007', 12, NULL);
INSERT INTO public.puente_interes_condominio VALUES (73, 'J-0145232547', 12, NULL);
INSERT INTO public.puente_interes_condominio VALUES (79, 'J-9685745568', 13, NULL);
INSERT INTO public.puente_interes_condominio VALUES (80, 'J-0024514563', 13, NULL);
INSERT INTO public.puente_interes_condominio VALUES (81, 'J-2540768143', 13, NULL);
INSERT INTO public.puente_interes_condominio VALUES (82, 'J-9685745568', 16, NULL);
INSERT INTO public.puente_interes_condominio VALUES (83, 'J-0024514563', 16, NULL);
INSERT INTO public.puente_interes_condominio VALUES (84, 'J-2540768143', 16, NULL);
INSERT INTO public.puente_interes_condominio VALUES (92, 'J-9685745568', 11, NULL);
INSERT INTO public.puente_interes_condominio VALUES (93, 'J-0024514563', 11, NULL);
INSERT INTO public.puente_interes_condominio VALUES (94, 'J-2540768143', 11, NULL);
INSERT INTO public.puente_interes_condominio VALUES (85, 'J-9685745568', 8, 1);
INSERT INTO public.puente_interes_condominio VALUES (86, 'J-0024514563', 8, 1);
INSERT INTO public.puente_interes_condominio VALUES (87, 'J-2540768143', 8, 1);
INSERT INTO public.puente_interes_condominio VALUES (66, 'J-4512698007', 9, 1);
INSERT INTO public.puente_interes_condominio VALUES (67, 'J-0145232547', 9, 1);
INSERT INTO public.puente_interes_condominio VALUES (76, 'J-9685745568', 17, 1);
INSERT INTO public.puente_interes_condominio VALUES (77, 'J-0024514563', 17, 1);
INSERT INTO public.puente_interes_condominio VALUES (78, 'J-2540768143', 17, 1);


--
-- TOC entry 3165 (class 0 OID 26881)
-- Dependencies: 250
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (179, 79, 3);
INSERT INTO public.puente_sancion_unidad VALUES (180, 79, 5);
INSERT INTO public.puente_sancion_unidad VALUES (181, 79, 4);
INSERT INTO public.puente_sancion_unidad VALUES (182, 79, 2);
INSERT INTO public.puente_sancion_unidad VALUES (183, 79, 1);
INSERT INTO public.puente_sancion_unidad VALUES (184, 79, 62);


--
-- TOC entry 3167 (class 0 OID 26886)
-- Dependencies: 252
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (35, '10921542', 6, '2020-05-11', NULL, '9', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (36, '27328852', 7, '2020-05-11', NULL, '10', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (37, '26943430', 8, '2020-05-11', NULL, '12', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (38, '1545698', 9, '2020-05-11', NULL, '13', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (39, '9456874', 10, '2020-05-11', NULL, '14', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (40, '5484633', 11, '2020-05-11', NULL, '15', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (41, '15211450', 12, '2020-05-11', NULL, '16', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (42, '8854237', 13, '2020-05-11', NULL, '17', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (43, '18346152', 14, '2020-05-11', NULL, '18', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (44, '1425368', 15, '2020-05-11', NULL, '19', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (45, '1425368', 16, '2020-05-11', NULL, '20', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (46, '4236500', 18, '2020-05-11', NULL, '21', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (47, '9578450', 19, '2020-05-11', NULL, '22', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (48, '4125868', 20, '2020-05-11', NULL, '23', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (49, '16254700', 17, '2020-05-11', NULL, '24', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (50, '18547895', 21, '2020-05-11', NULL, '25', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (51, '15457896', 22, '2020-05-11', NULL, '26', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (52, '16547896', 23, '2020-05-11', NULL, '27', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (53, '2343', 24, '2020-05-11', NULL, '28', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (54, '23545478', 25, '2020-05-11', NULL, '29', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (55, '12354875', 27, '2020-05-11', NULL, '30', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (56, '3321554', 28, '2020-05-11', NULL, '31', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (57, '14528796', 26, '2020-05-11', NULL, '32', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (58, '24578965', 29, '2020-05-11', NULL, '33', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (59, '14156247', 30, '2020-05-11', NULL, '34', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (60, '6254789', 33, '2020-05-11', NULL, '35', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (61, '9457854', 65, '2020-05-11', NULL, '36', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (62, '13245789', 35, '2020-05-11', NULL, '37', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (63, '52145785', 36, '2020-05-11', NULL, '38', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (64, '10145236', 37, '2020-05-11', NULL, '39', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (65, '10145236', 38, '2020-05-11', NULL, '40', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (66, '11444254', 39, '2020-05-11', NULL, '41', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (67, '73211450', 40, '2020-05-11', NULL, '42', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (68, '20457896', 41, '2020-05-11', NULL, '43', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (69, '19306051', 42, '2020-05-11', NULL, '44', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (70, '19306051', 43, '2020-05-11', NULL, '45', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (71, '20457896', 44, '2020-05-11', NULL, '46', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (72, '73211450', 45, '2020-05-11', NULL, '47', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (73, '11444254', 46, '2020-05-11', NULL, '48', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (74, '10145236', 47, '2020-05-11', NULL, '49', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (75, '52145785', 48, '2020-05-11', NULL, '50', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (76, '13245789', 49, '2020-05-11', NULL, '51', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (77, '9457854', 50, '2020-05-11', NULL, '52', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (78, '6254789', 51, '2020-05-11', NULL, '53', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (79, '14156247', 52, '2020-05-11', NULL, '54', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (80, '24578965', 53, '2020-05-11', NULL, '55', 1, 1);
INSERT INTO public.puente_unidad_propietarios VALUES (4, '18965742', 62, '2020-05-06', NULL, 'asdsa', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (5, '1424801', 62, '2020-05-06', NULL, 'sad', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (6, '24578966', 62, '2020-05-06', NULL, 'sada', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (2, '8517596', 62, '2020-05-06', '2020-05-06', 'asda', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (3, '20888725', 62, '2020-05-06', '2020-05-06', 'asadas', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (7, '8517596', 63, '2020-05-06', '2020-05-06', '1', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (8, '20888725', 63, '2020-05-06', '2020-05-06', '1', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (15, '8517596', 63, '2020-05-06', NULL, 'qwewq', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (16, '20888725', 63, '2020-05-06', NULL, 'qweqew', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (11, '18965742', 63, '2020-05-06', NULL, '2', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (12, '1424801', 63, '2020-05-06', NULL, '2', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (13, '24578966', 63, '2020-05-06', NULL, '2', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (14, '9245638', 63, '2020-05-06', NULL, '2', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (18, '8517596', 60, '2020-05-10', NULL, 'xzc', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (19, '1424801', 60, '2020-05-10', NULL, 'xzcc', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (20, '24578966', 60, '2020-05-10', NULL, 'xzc', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (23, '20888725', 60, '2020-05-10', NULL, 'null', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (25, '8517596', 3, '2020-05-11', NULL, '1', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (26, '20888725', 1, '2020-05-11', NULL, '2', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (27, '18965742', 5, '2020-05-11', NULL, '3', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (28, '1424801', 4, '2020-05-11', NULL, '4', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (29, '24578966', 2, '2020-05-11', NULL, '5', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (30, '24578966', 61, '2020-05-11', NULL, '6', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (31, '9245638', 61, '2020-05-11', NULL, '6', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (9, '24578966', 64, '2020-05-06', NULL, 'asda', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (10, '9245638', 64, '2020-05-06', NULL, 'asd', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (32, '24589635', 64, '2020-05-11', NULL, '7', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (17, '4321554', 59, '2020-05-10', '2020-05-10', 'sdf', 0, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (21, '8517596', 59, '2020-05-10', NULL, 'asd', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (22, '20888725', 59, '2020-05-10', NULL, 'nullasd', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (24, '18965742', 59, '2020-05-10', NULL, 'null', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (33, '18547895', 59, '2020-05-11', NULL, '8', 1, 0);
INSERT INTO public.puente_unidad_propietarios VALUES (34, '15457896', 59, '2020-05-11', NULL, '8', 1, 0);


--
-- TOC entry 3169 (class 0 OID 26891)
-- Dependencies: 254
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (76, 'Multa', 5, 2020, 2000, 'cvbc', 'Pendiente');
INSERT INTO public.sancion VALUES (78, 'Interes de mora', 9, 2020, 123213, 'asdad', 'Pendiente');
INSERT INTO public.sancion VALUES (77, 'Interes de mora', 5, 2020, 2.131, 'adad', 'Pendiente');
INSERT INTO public.sancion VALUES (79, 'Interes de mora', 8, 2020, 1233, 'ads', 'Pendiente');


--
-- TOC entry 3171 (class 0 OID 26896)
-- Dependencies: 256
-- Data for Name: unidades; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidades VALUES ('6', 'Conjunto Onix, casa numero 6', 270, 21, 'J-2540768143', 1);
INSERT INTO public.unidades VALUES ('7', 'Conjunto Onix, casa numero 7', 270, 22, 'J-2540768143', 1);
INSERT INTO public.unidades VALUES ('8', 'Conjunto Onix, casa numero 8', 270, 23, 'J-2540768143', 1);
INSERT INTO public.unidades VALUES ('9', 'Conjunto Onix, casa numero 9', 270, 24, 'J-2540768143', 1);
INSERT INTO public.unidades VALUES ('10', 'Conjunto Onix, casa numero 10', 270, 25, 'J-2540768143', 1);
INSERT INTO public.unidades VALUES ('2', 'Calle 1, casa 2', 200, 7, 'J-0145232547', 1);
INSERT INTO public.unidades VALUES ('3', 'Calle 2, casa 3', 200, 8, 'J-0145232547', 1);
INSERT INTO public.unidades VALUES ('4', 'Calle 2, casa 4', 200, 9, 'J-0145232547', 1);
INSERT INTO public.unidades VALUES ('5', 'Calle 2, casa 5', 200, 10, 'J-0145232547', 1);
INSERT INTO public.unidades VALUES ('1', 'Calle 1, casa 1', 200, 6, 'J-0145232547', 1);
INSERT INTO public.unidades VALUES ('01', 'Calle 1', 250, 11, 'J-9685745568', 1);
INSERT INTO public.unidades VALUES ('02', 'Calle 1', 250, 12, 'J-9685745568', 1);
INSERT INTO public.unidades VALUES ('03', 'Calle 2', 250, 13, 'J-9685745568', 1);
INSERT INTO public.unidades VALUES ('04', 'Calle 3', 250, 14, 'J-9685745568', 1);
INSERT INTO public.unidades VALUES ('05', 'Calle 1', 250, 15, 'J-9685745568', 1);
INSERT INTO public.unidades VALUES ('1A', 'Piso 1', 101, 16, 'J-0024514563', 1);
INSERT INTO public.unidades VALUES ('4A', 'Piso 4', 101, 18, 'J-0024514563', 1);
INSERT INTO public.unidades VALUES ('4B', 'Piso 4', 101, 19, 'J-0024514563', 1);
INSERT INTO public.unidades VALUES ('PH', 'Piso 10', 201, 20, 'J-0024514563', 1);
INSERT INTO public.unidades VALUES ('001', '-', 200, 27, 'J-5784968566654', 1);
INSERT INTO public.unidades VALUES ('2B', 'Piso 2', 101, 17, 'J-0024514563', 1);
INSERT INTO public.unidades VALUES ('002', '-', 200, 28, 'J-5784968566654', 1);
INSERT INTO public.unidades VALUES ('003', '-', 200, 26, 'J-5784968566654', 1);
INSERT INTO public.unidades VALUES ('004', '-', 200, 29, 'J-5784968566654', 1);
INSERT INTO public.unidades VALUES ('005', '-', 200, 30, 'J-5784968566654', 1);
INSERT INTO public.unidades VALUES ('11', '-', 200, 33, 'J-7774122010236', 1);
INSERT INTO public.unidades VALUES ('12', '-', 200, 65, 'J-7774122010236', 1);
INSERT INTO public.unidades VALUES ('13', '-', 200, 35, 'J-7774122010236', 1);
INSERT INTO public.unidades VALUES ('14', '-', 200, 36, 'J-7774122010236', 1);
INSERT INTO public.unidades VALUES ('15', '-', 200, 37, 'J-7774122010236', 1);
INSERT INTO public.unidades VALUES ('01', '-', 250, 38, 'J-77741220004', 1);
INSERT INTO public.unidades VALUES ('02', '-', 250, 39, 'J-77741220004', 1);
INSERT INTO public.unidades VALUES ('03', '-', 300, 40, 'J-77741220004', 1);
INSERT INTO public.unidades VALUES ('04', '-', 300, 41, 'J-77741220004', 1);
INSERT INTO public.unidades VALUES ('05', '-', 250, 42, 'J-77741220004', 1);
INSERT INTO public.unidades VALUES ('1', '-', 150, 43, 'J-77663250004', 1);
INSERT INTO public.unidades VALUES ('2', '-', 150, 44, 'J-77663250004', 1);
INSERT INTO public.unidades VALUES ('3', '-', 150, 45, 'J-77663250004', 1);
INSERT INTO public.unidades VALUES ('4', '-', 150, 46, 'J-77663250004', 1);
INSERT INTO public.unidades VALUES ('5', '-', 150, 47, 'J-77663250004', 1);
INSERT INTO public.unidades VALUES ('010', '-', 200, 48, 'J-9854762000', 1);
INSERT INTO public.unidades VALUES ('011', '-', 200, 49, 'J-9854762000', 1);
INSERT INTO public.unidades VALUES ('012', '-', 200, 50, 'J-9854762000', 1);
INSERT INTO public.unidades VALUES ('013', '-', 200, 51, 'J-9854762000', 1);
INSERT INTO public.unidades VALUES ('014', '-', 200, 52, 'J-9854762000', 1);
INSERT INTO public.unidades VALUES ('015', '-', 200, 53, 'J-9854762000', 1);
INSERT INTO public.unidades VALUES ('asda', 'asd', 123, 63, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('1212231', 'asdads', 123, 60, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('B2', 'Piso 2, apartamento B2', 150, 3, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('A1', 'Piso 1, apartamento A1', 150, 1, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('D1', 'piso 4, apartamento D1', 150, 5, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('E2', 'piso 5, apartamento E2', 150, 4, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('A2', 'Piso 1, apartamento A2', 150, 2, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('1312312', 'dasda', 123, 61, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('adada', 'asda', 123, 64, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('12', '12', 12, 59, 'J-4512698007', 0);
INSERT INTO public.unidades VALUES ('asdad', 'asdadsa', 231, 62, 'J-4512698007', 1);


--
-- TOC entry 3173 (class 0 OID 26901)
-- Dependencies: 258
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES ('26843430', 'sapm', '12345', 'samuel', 'perez', 'Administrador', '2312313');
INSERT INTO public.usuario VALUES ('26943430', 'sapmmm', '1234', 'Sam', 'Lugo', 'Propietario', '123');
INSERT INTO public.usuario VALUES ('21963231', 'mmm', '2222', 'Maria', 'Suarez', 'Propietario', '04125084544');
INSERT INTO public.usuario VALUES ('17789654', 'asas', '14', 'Dana', 'Lucas', 'Propietario', '0413247856');
INSERT INTO public.usuario VALUES ('8475896', 'qqqqq', '124587', 'Maria', 'Alvarado', 'Propietario', '04125478965');
INSERT INTO public.usuario VALUES ('7894563', 'asrrrr', '7845', 'Lucas', 'Alvarado', 'Propietario', '04125477555');
INSERT INTO public.usuario VALUES ('6457896', 'ddddd', '', 'Marta', 'Solis', 'Propietario', '04125478963');
INSERT INTO public.usuario VALUES ('6454565', 'dddddqqqq', '', 'Mario', 'Salas', 'Propietario', '04121078963');
INSERT INTO public.usuario VALUES ('15246325', 'mdjfas', '', 'Daniel', 'Aguirre', 'Propietario', '04121078100');
INSERT INTO public.usuario VALUES ('20122354', 'jhklgff', '444', 'Juan', 'Lopez', 'Propietario', '04121145278');


--
-- TOC entry 3174 (class 0 OID 26904)
-- Dependencies: 259
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.visita VALUES (1, '2020-05-01', '17:33:43', '23154j', 'Toyota', 'Azul', 'A1', 'V-27328852');
INSERT INTO public.visita VALUES (2, '2020-05-01', '22:33:41', '12345fs2', 'Chevrolet', 'Rojo', 'A2', 'V-28563254');
INSERT INTO public.visita VALUES (3, '2020-05-01', '22:36:18', 'kgy145', 'Toyota', 'Blanco', 'B2', 'V-1434801');
INSERT INTO public.visita VALUES (4, '2020-05-01', '22:49:06', '21da358', 'Toyota', 'Vinotinto', 'E2', 'V-26943430');
INSERT INTO public.visita VALUES (5, '2020-05-01', '22:50:13', '366ede', 'Toyota', 'Azul', 'D1', 'V-8517596');
INSERT INTO public.visita VALUES (6, '2020-05-01', '22:52:36', '2345fs2', 'Corolla', 'Verde', '1', 'V-28563254');
INSERT INTO public.visita VALUES (7, '2020-05-01', '22:55:02', 'sad23', 'Corolla', 'Blanco', '2', 'V-27328852');
INSERT INTO public.visita VALUES (8, '2020-05-01', '22:55:57', 'asda567', 'Corolla', 'Verde', '3', 'V-26943430');
INSERT INTO public.visita VALUES (9, '2020-05-01', '22:56:34', 'qeqwe333', '-', 'Amarillo', '4', 'V-1434801');
INSERT INTO public.visita VALUES (10, '2020-05-01', '22:57:45', 'asdd44', '-', 'Amarillo', '5', 'V-8517596');
INSERT INTO public.visita VALUES (11, '2020-05-01', '22:59:45', 'dsa145', 'Corolla', 'Magenta', '01', 'V-28563254');
INSERT INTO public.visita VALUES (12, '2020-05-01', '23:00:18', '32saa', '-', 'Blanco', '02', 'V-27328852');
INSERT INTO public.visita VALUES (13, '2020-05-01', '23:00:40', '42eqa', '-', 'Negro', '03', 'V-27328852');
INSERT INTO public.visita VALUES (14, '2020-05-01', '23:01:07', 'ssad3', '-', 'Negro', '04', 'V-26943430');
INSERT INTO public.visita VALUES (15, '2020-05-01', '23:01:31', '14gdd', '-', 'Verde', '05', 'V-8517596');
INSERT INTO public.visita VALUES (16, '2020-05-01', '23:02:26', 'asd145', '-', 'Rojo', '1A', 'V-27328852');
INSERT INTO public.visita VALUES (17, '2020-05-01', '23:03:04', 'dasd232', '-', 'Verde', '2B', 'V-28563254');
INSERT INTO public.visita VALUES (18, '2020-05-01', '23:03:58', 'dsd23', '-', 'Azul', '4A', 'V-27328852');
INSERT INTO public.visita VALUES (19, '2020-05-01', '23:04:26', 'dss33', '-', 'Azul', '4B', 'V-8517596');
INSERT INTO public.visita VALUES (20, '2020-05-01', '23:04:44', 'asqw', '-', 'Negro', 'PH', 'V-27328852');
INSERT INTO public.visita VALUES (21, '2020-05-01', '23:05:40', 'dsds32', '-', 'Blanco', '6', 'V-27328852');
INSERT INTO public.visita VALUES (22, '2020-05-01', '23:06:00', 'asde2', '-', 'Magenta', '7', 'V-27328852');
INSERT INTO public.visita VALUES (23, '2020-05-01', '23:06:32', 'gabn', '-', 'Azul', '8', 'V-8517596');
INSERT INTO public.visita VALUES (24, '2020-05-01', '23:06:55', 'jjhff3', '-', 'Turquesa', '9', 'V-26943430');
INSERT INTO public.visita VALUES (25, '2020-05-01', '23:07:41', 'wwe12', '-', 'Turquesa', '10', 'V-1434801');
INSERT INTO public.visita VALUES (26, '2020-05-01', '23:08:46', 'ffas23', '-', 'Verde', '001', 'V-27328852');
INSERT INTO public.visita VALUES (27, '2020-05-01', '23:09:31', 'asqwe', '-', 'Negro', '002', 'V-1434801');
INSERT INTO public.visita VALUES (28, '2020-05-01', '23:10:14', 'fgh52', '-', 'Verde', '003', 'V-8517596');
INSERT INTO public.visita VALUES (29, '2020-05-01', '23:10:45', 'asd12', '-', 'Rojo', '004', 'V-26943430');
INSERT INTO public.visita VALUES (30, '2020-05-01', '23:11:10', 'mas25', '-', 'Blanco', '005', 'V-27328852');
INSERT INTO public.visita VALUES (31, '2020-05-01', '23:12:45', 'sa23', '-', 'Azul', '11', 'V-27328852');
INSERT INTO public.visita VALUES (32, '2020-05-01', '23:13:11', 'asa21', '-', 'Magenta', '12', 'V-8517596');
INSERT INTO public.visita VALUES (33, '2020-05-01', '23:13:40', 'dad23', '-', 'Verde', '13', 'V-26943430');
INSERT INTO public.visita VALUES (34, '2020-05-01', '23:14:23', 'sa2w2', '-', 'Rojo', '14', 'V-28563254');
INSERT INTO public.visita VALUES (35, '2020-05-01', '23:14:41', '254sdd', '-', 'Rojo', '015', 'V-28563254');
INSERT INTO public.visita VALUES (36, '2020-05-01', '23:15:35', 'asd23', '-', 'Amarillo', '01', 'V-28563254');
INSERT INTO public.visita VALUES (37, '2020-05-01', '23:15:53', '44hg', '-', 'Azul', '02', 'V-27328852');
INSERT INTO public.visita VALUES (38, '2020-05-01', '23:16:11', 'asa421', '-', 'Rojo', '03', 'V-26943430');
INSERT INTO public.visita VALUES (39, '2020-05-01', '23:17:04', 's421', '-', 'Naranja', '04', 'V-1434801');
INSERT INTO public.visita VALUES (40, '2020-05-01', '23:17:34', 'sa33', '-', 'Rojo', '05', 'V-8517596');
INSERT INTO public.visita VALUES (41, '2020-05-01', '23:18:42', 'bnn23', '-', 'Rojo', '1', 'V-28563254');
INSERT INTO public.visita VALUES (42, '2020-05-01', '23:19:05', 'asq21', '-', 'Amarillo', '2', 'V-27328852');
INSERT INTO public.visita VALUES (43, '2020-05-01', '23:19:34', '124fdw', '-', 'Azul', '3', 'V-26943430');
INSERT INTO public.visita VALUES (44, '2020-05-01', '23:20:03', 'asd', '-', 'Verde', '3', 'V-8517596');
INSERT INTO public.visita VALUES (45, '2020-05-01', '23:20:43', 'sa3we', '-', 'Magenta', '4', 'V-8517596');
INSERT INTO public.visita VALUES (46, '2020-05-01', '23:21:09', 'asa2', '-', 'Rojo', '5', 'V-26943430');
INSERT INTO public.visita VALUES (47, '2020-05-01', '23:22:02', 'asd321', '-', 'Amarillo', '010', 'V-28563254');
INSERT INTO public.visita VALUES (48, '2020-05-01', '23:22:28', '254kjg', '-', 'Azul', '011', 'V-27328852');
INSERT INTO public.visita VALUES (49, '2020-05-01', '23:22:55', 'm145a', '-', 'Negro', '012', 'V-26943430');
INSERT INTO public.visita VALUES (50, '2020-05-01', '23:23:14', 'sada24', '-', 'Verde', '013', 'V-8517596');
INSERT INTO public.visita VALUES (51, '2020-05-01', '23:23:41', 'asd10', '-', 'Naranja', '014', 'V-1434801');
INSERT INTO public.visita VALUES (52, '2020-05-01', '23:24:03', 'mjgl3', '-', 'Azul', '015', 'V-28563254');


--
-- TOC entry 3176 (class 0 OID 26911)
-- Dependencies: 261
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.visitante VALUES ('V-28563254', 'Maria', 'Guedez');
INSERT INTO public.visitante VALUES ('V-27328852', 'Marta', 'Rodriguez');
INSERT INTO public.visitante VALUES ('V-26943430', 'Samuel', 'Mora');
INSERT INTO public.visitante VALUES ('V-8517596', 'Blanca', 'Silva');
INSERT INTO public.visitante VALUES ('V-1434801', 'Blanca', 'Ovija');


--
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 81, true);


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 11, true);


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 6, true);


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 209
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cierre_de_mes_id_seq', 50, true);


--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 211
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 10, true);


--
-- TOC entry 3214 (class 0 OID 0)
-- Dependencies: 213
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 53, true);


--
-- TOC entry 3215 (class 0 OID 0)
-- Dependencies: 215
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 41, true);


--
-- TOC entry 3216 (class 0 OID 0)
-- Dependencies: 219
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3217 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 75, true);


--
-- TOC entry 3218 (class 0 OID 0)
-- Dependencies: 223
-- Name: detalle_cuotas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_cuotas_id_seq', 433, true);


--
-- TOC entry 3219 (class 0 OID 0)
-- Dependencies: 225
-- Name: detalle_interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_interes_id_seq', 330, true);


--
-- TOC entry 3220 (class 0 OID 0)
-- Dependencies: 227
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1267, true);


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 229
-- Name: detalle_sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_sancion_id_seq', 174, true);


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 231
-- Name: detalle_total_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_total_id_seq', 144, true);


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 233
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 54, true);


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 235
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 78, true);


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 237
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 17, true);


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 241
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 258, true);


--
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 243
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 7, true);


--
-- TOC entry 3228 (class 0 OID 0)
-- Dependencies: 245
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 503, true);


--
-- TOC entry 3229 (class 0 OID 0)
-- Dependencies: 247
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 138, true);


--
-- TOC entry 3230 (class 0 OID 0)
-- Dependencies: 249
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_interes_condominio_id_seq', 94, true);


--
-- TOC entry 3231 (class 0 OID 0)
-- Dependencies: 251
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 184, true);


--
-- TOC entry 3232 (class 0 OID 0)
-- Dependencies: 253
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 80, true);


--
-- TOC entry 3233 (class 0 OID 0)
-- Dependencies: 255
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 79, true);


--
-- TOC entry 3234 (class 0 OID 0)
-- Dependencies: 257
-- Name: unidades_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidades_id_seq', 65, true);


--
-- TOC entry 3235 (class 0 OID 0)
-- Dependencies: 260
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 52, true);


--
-- TOC entry 2905 (class 2606 OID 26942)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 2907 (class 2606 OID 26944)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 2909 (class 2606 OID 26946)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2911 (class 2606 OID 26948)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 2913 (class 2606 OID 26950)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2915 (class 2606 OID 26952)
-- Name: cierre_de_mes cierre_de_mes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_pkey PRIMARY KEY (id);


--
-- TOC entry 2917 (class 2606 OID 26954)
-- Name: cobro cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 2919 (class 2606 OID 26956)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 2921 (class 2606 OID 26958)
-- Name: concepto_gasto concepto_gasto_nom_concepto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_nom_concepto_key UNIQUE (nom_concepto);


--
-- TOC entry 2923 (class 2606 OID 26960)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2925 (class 2606 OID 26962)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2927 (class 2606 OID 26964)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 2929 (class 2606 OID 26966)
-- Name: cuenta cuenta_n_cuenta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_key UNIQUE (n_cuenta);


--
-- TOC entry 2933 (class 2606 OID 26968)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 2931 (class 2606 OID 26970)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 2935 (class 2606 OID 26972)
-- Name: cuotas_especiales cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2937 (class 2606 OID 26974)
-- Name: detalle_cuotas detalle_cuotas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_cuotas
    ADD CONSTRAINT detalle_cuotas_pkey PRIMARY KEY (id);


--
-- TOC entry 2939 (class 2606 OID 26976)
-- Name: detalle_interes detalle_interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_interes
    ADD CONSTRAINT detalle_interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 26978)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 26980)
-- Name: detalle_sancion detalle_sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_sancion
    ADD CONSTRAINT detalle_sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 2945 (class 2606 OID 26982)
-- Name: detalle_total detalle_total_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_total
    ADD CONSTRAINT detalle_total_pkey PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 26984)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 2949 (class 2606 OID 26986)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 2951 (class 2606 OID 26988)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2953 (class 2606 OID 26990)
-- Name: propietarios propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietarios
    ADD CONSTRAINT propietarios_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2955 (class 2606 OID 26992)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 2957 (class 2606 OID 26994)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2959 (class 2606 OID 26996)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 2961 (class 2606 OID 26998)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 2963 (class 2606 OID 27000)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2965 (class 2606 OID 27002)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 2967 (class 2606 OID 27004)
-- Name: puente_interes_condominio puente_interes_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio
    ADD CONSTRAINT puente_interes_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 2969 (class 2606 OID 27006)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 2971 (class 2606 OID 27008)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2973 (class 2606 OID 27010)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 2975 (class 2606 OID 27012)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (id);


--
-- TOC entry 2977 (class 2606 OID 27014)
-- Name: usuario usuario_cedula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_key UNIQUE (cedula);


--
-- TOC entry 2979 (class 2606 OID 27016)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2981 (class 2606 OID 27018)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 2983 (class 2606 OID 27020)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 2985 (class 2606 OID 27022)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2986 (class 2606 OID 27023)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 2987 (class 2606 OID 27028)
-- Name: cuenta_pagar cuenta_pagar_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 2988 (class 2606 OID 27033)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 2989 (class 2606 OID 27038)
-- Name: cuenta_pagar cuenta_pagar_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 2990 (class 2606 OID 27043)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.visitante(cedula);


-- Completed on 2020-05-11 21:26:47

--
-- PostgreSQL database dump complete
--

