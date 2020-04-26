--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-26 10:16:07

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

DROP DATABASE proyecto;
--
-- TOC entry 3045 (class 1262 OID 16407)
-- Name: proyecto; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE proyecto WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';


ALTER DATABASE proyecto OWNER TO postgres;

\connect proyecto

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
-- TOC entry 3046 (class 0 OID 0)
-- Name: proyecto; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE proyecto CONNECTION LIMIT = 100;


\connect proyecto

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
-- TOC entry 3047 (class 0 OID 0)
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
-- TOC entry 3048 (class 0 OID 0)
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
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 221
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


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
-- TOC entry 3050 (class 0 OID 0)
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
-- TOC entry 3051 (class 0 OID 0)
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
    calcular character varying(20) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    saldo double precision NOT NULL,
    n_meses bigint NOT NULL,
    id_asamblea bigint,
    observacion character varying(500),
    estado character varying(100) NOT NULL,
    id_condominio character varying(15)
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
-- TOC entry 3052 (class 0 OID 0)
-- Dependencies: 231
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.cuotas_especiales.id;


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
    id_condominio character varying(15) NOT NULL
);


ALTER TABLE public.fondos OWNER TO postgres;

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
-- TOC entry 3053 (class 0 OID 0)
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
-- TOC entry 3054 (class 0 OID 0)
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
-- TOC entry 3055 (class 0 OID 0)
-- Dependencies: 214
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


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
-- TOC entry 3056 (class 0 OID 0)
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
-- TOC entry 3057 (class 0 OID 0)
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
-- TOC entry 3058 (class 0 OID 0)
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
-- TOC entry 3059 (class 0 OID 0)
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
-- TOC entry 3060 (class 0 OID 0)
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
-- TOC entry 2802 (class 2604 OID 24689)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2800 (class 2604 OID 24620)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2806 (class 2604 OID 24746)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 2809 (class 2604 OID 24780)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 2807 (class 2604 OID 24756)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 2811 (class 2604 OID 24813)
-- Name: cuotas_especiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 2808 (class 2604 OID 24772)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 2812 (class 2604 OID 24824)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 2803 (class 2604 OID 24700)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2810 (class 2604 OID 24794)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 2801 (class 2604 OID 24637)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2813 (class 2604 OID 24832)
-- Name: puente_interes_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_interes_condominio_id_seq'::regclass);


--
-- TOC entry 2805 (class 2604 OID 24728)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 2804 (class 2604 OID 24720)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3016 (class 0 OID 24686)
-- Dependencies: 213
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (23, 'da', '2020-04-04', 'd', '111');


--
-- TOC entry 3008 (class 0 OID 24617)
-- Dependencies: 205
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banco VALUES (1, 'sfs');
INSERT INTO public.banco VALUES (3, 'caribe');
INSERT INTO public.banco VALUES (5, 'dsfs');
INSERT INTO public.banco VALUES (4, 'provincial');
INSERT INTO public.banco VALUES (6, 'venezuela');


--
-- TOC entry 3025 (class 0 OID 24743)
-- Dependencies: 222
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (1, 'dadasd', 'asda');


--
-- TOC entry 3031 (class 0 OID 24777)
-- Dependencies: 228
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.comunicados VALUES (1, 'a', 's', '111');
INSERT INTO public.comunicados VALUES (2, 'asdaa', 'asdadad', '111');
INSERT INTO public.comunicados VALUES (3, 'sad', 'dsd', '111');


--
-- TOC entry 3027 (class 0 OID 24753)
-- Dependencies: 224
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (1, 'de', 'sadds', 1);


--
-- TOC entry 3006 (class 0 OID 16449)
-- Dependencies: 203
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('13131312', 'sdadad', '123132', 'asdasdasdadsa');
INSERT INTO public.condominio VALUES ('111', 'holas', '24334324', 'sa');


--
-- TOC entry 3009 (class 0 OID 24625)
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
-- TOC entry 3035 (class 0 OID 24810)
-- Dependencies: 232
-- Data for Name: cuotas_especiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuotas_especiales VALUES (1, '231231', 1, 'Alicuota', 5, 2021, 200000, 200000, 3, 23, 'dsa', 'Pendiente', '111');
INSERT INTO public.cuotas_especiales VALUES (3, '231231', 1, 'Total de Inmuebles', 4, 2020, 2000000, 2000000, 1, 0, '132132', 'Pendiente', '13131312');
INSERT INTO public.cuotas_especiales VALUES (4, '342', 1, 'Alicuota', 5, 2020, 5000000, 5000000, 2, 0, 'xcccccc', 'Pendiente', '13131312');
INSERT INTO public.cuotas_especiales VALUES (5, '231231', 1, 'Total de Inmuebles', 7, 2020, 65700000, 65700000, 1, 0, 'dddddd', 'Pendiente', '13131312');
INSERT INTO public.cuotas_especiales VALUES (6, '342', 1, 'Alicuota', 8, 2020, 120000000, 120000000, 1, 0, 'rrrrrr', 'Pendiente', '13131312');
INSERT INTO public.cuotas_especiales VALUES (7, '231231', 1, 'Total de Inmuebles', 9, 2020, 300000000, 300000000, 1, 0, 'hhjjhhg', 'Pendiente', '13131312');


--
-- TOC entry 3019 (class 0 OID 24705)
-- Dependencies: 216
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('prestaciones', '2020-04-15', 'madsodiadjada', '', 200000, 0, '111');
INSERT INTO public.fondos VALUES ('ad', '2020-04-04', 'madsodiadjada', 'yes', 200000, 1000000, '111');


--
-- TOC entry 3029 (class 0 OID 24769)
-- Dependencies: 226
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto_comun VALUES (1, 'Ordinario', 6, 2021, 100000, '52', '231231', 0, '23', '2020-04-17', 'Pendiente', '111', NULL);
INSERT INTO public.gasto_comun VALUES (2, 'Extraordinario', 6, 2021, 213, 'sd', '342', 1, 'd', '2020-04-03', 'Pendiente', '111', NULL);
INSERT INTO public.gasto_comun VALUES (4, 'Ordinario', 4, 2020, 23, 'sadsa', '342', 0, '', '2020-04-10', 'Pendiente', '111', 23);
INSERT INTO public.gasto_comun VALUES (6, 'Ordinario', 8, 2021, 100000, '1231', '342', 1, 'asdada', '2020-04-24', 'Pendiente', '111', 100000);
INSERT INTO public.gasto_comun VALUES (3, 'Extraordinario', 6, 2020, 1004000, 'sd1231', '231231', 1, 'd', '2020-04-03', 'Pendiente', '111', 1004000);
INSERT INTO public.gasto_comun VALUES (7, 'Extraordinario', 4, 2020, 20000, '024596545465', '231231', 1, 'sasasasa', '2020-04-09', 'Pendiente', '13131312', 20000);
INSERT INTO public.gasto_comun VALUES (10, 'Extraordinario', 6, 2020, 10200000, '654987321524', '342', 1, 'sggggggggg', '2020-06-11', 'Pendiente', '13131312', 10200000);
INSERT INTO public.gasto_comun VALUES (8, 'Ordinario', 5, 2020, 20000000, '02422', '342', 1, 'sasasasassddddd', '2020-05-13', 'Pendiente', '13131312', 20000000);
INSERT INTO public.gasto_comun VALUES (11, 'Extraordinario', 9, 2020, 10200000000, '656798765434567', '231231', 1, 'sgggggggggzxxzxzx', '2020-09-09', 'Pendiente', '13131312', 10200000000);
INSERT INTO public.gasto_comun VALUES (9, 'Ordinario', 7, 2020, 50000000, '02422667543', '231231', 1, 'sggggggggg', '2020-07-14', 'Pendiente', '13131312', 50000000);


--
-- TOC entry 3037 (class 0 OID 24821)
-- Dependencies: 234
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (2, 'adad', 23, 'Activo');
INSERT INTO public.interes VALUES (3, 'No se', 50, 'Activo');
INSERT INTO public.interes VALUES (4, 'Cosas', 10, 'Activo');
INSERT INTO public.interes VALUES (5, 'Cositas', 20, 'Activo');
INSERT INTO public.interes VALUES (6, 'Cosotas', 5, 'Activo');


--
-- TOC entry 3013 (class 0 OID 24664)
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


--
-- TOC entry 3012 (class 0 OID 24654)
-- Dependencies: 209
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('231231', 'asdas', '234234', 'asdad', 'sadasdasd', 'ffsfsdfsfsfsf');
INSERT INTO public.proveedores VALUES ('342', 'asdassd', '234234', 'asdad', 'sadasdasdasd', 'ffsfsdfsfsfsf');


--
-- TOC entry 3018 (class 0 OID 24697)
-- Dependencies: 215
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asamblea_propietario VALUES (29, 23, '26943430');
INSERT INTO public.puente_asamblea_propietario VALUES (30, 23, '31212');


--
-- TOC entry 3033 (class 0 OID 24791)
-- Dependencies: 230
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_comunicado_usuario VALUES (1, '26843430', 2, 0);
INSERT INTO public.puente_comunicado_usuario VALUES (2, '26943430', 2, 1);
INSERT INTO public.puente_comunicado_usuario VALUES (3, '26843430', 3, 0);


--
-- TOC entry 3011 (class 0 OID 24634)
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
-- TOC entry 3039 (class 0 OID 24829)
-- Dependencies: 236
-- Data for Name: puente_interes_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_interes_condominio VALUES (6, '13131312', 2);
INSERT INTO public.puente_interes_condominio VALUES (7, '13131312', 3);
INSERT INTO public.puente_interes_condominio VALUES (8, '13131312', 4);
INSERT INTO public.puente_interes_condominio VALUES (9, '13131312', 5);
INSERT INTO public.puente_interes_condominio VALUES (10, '13131312', 6);


--
-- TOC entry 3023 (class 0 OID 24725)
-- Dependencies: 220
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (1, 6, '2-013');
INSERT INTO public.puente_sancion_unidad VALUES (2, 6, '123');
INSERT INTO public.puente_sancion_unidad VALUES (3, 7, '2-013');
INSERT INTO public.puente_sancion_unidad VALUES (4, 8, '2-013');
INSERT INTO public.puente_sancion_unidad VALUES (5, 9, '2-013');
INSERT INTO public.puente_sancion_unidad VALUES (6, 10, '2-013');
INSERT INTO public.puente_sancion_unidad VALUES (12, 12, '1');
INSERT INTO public.puente_sancion_unidad VALUES (15, 14, '2');
INSERT INTO public.puente_sancion_unidad VALUES (18, 13, '1');
INSERT INTO public.puente_sancion_unidad VALUES (19, 13, '3');
INSERT INTO public.puente_sancion_unidad VALUES (20, 15, '4');
INSERT INTO public.puente_sancion_unidad VALUES (21, 16, '5');


--
-- TOC entry 3021 (class 0 OID 24717)
-- Dependencies: 218
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (1, 'Multa', 7, 2021, 2000, 'dsfsfsdfdsf', '111', NULL);
INSERT INTO public.sancion VALUES (2, 'Multa', 7, 2021, 2000, 'dsfsfsdfdsf', '111', NULL);
INSERT INTO public.sancion VALUES (3, 'Multa', 7, 2021, 2000, 'dsfsfsdfdsf', '111', NULL);
INSERT INTO public.sancion VALUES (4, 'Multa', 7, 2021, 2000, 'dsfsfsdfdsf', '111', NULL);
INSERT INTO public.sancion VALUES (5, 'Multa', 7, 2021, 2000, 'dsfsfsdfdsf', '111', NULL);
INSERT INTO public.sancion VALUES (6, 'Multa', 4, 2020, 2020, '', '111', NULL);
INSERT INTO public.sancion VALUES (7, 'Multa', 4, 2020, 2020, '', '111', NULL);
INSERT INTO public.sancion VALUES (8, 'Multa', 4, 2020, 12312312, '', '111', NULL);
INSERT INTO public.sancion VALUES (9, 'Multa', 4, 2020, 10000, 'sdf', '111', 'Pendiente');
INSERT INTO public.sancion VALUES (10, 'Interes de mora', 4, 2020, 10000, 'sdf', '111', 'Pendiente');
INSERT INTO public.sancion VALUES (12, 'Multa', 4, 2020, 500000, 'El niño rompio las tuberias de agua', '13131312', 'Pendiente');
INSERT INTO public.sancion VALUES (14, 'Multa', 5, 2020, 455000, 'El señor de la casa dejo el fogon encendido e incendio los cables', '13131312', 'Pendiente');
INSERT INTO public.sancion VALUES (13, 'Interes de mora', 4, 2020, 5, 'interes', '13131312', 'Pendiente');
INSERT INTO public.sancion VALUES (15, 'Multa', 7, 2020, 30000, 'no se', '13131312', 'Pendiente');
INSERT INTO public.sancion VALUES (16, 'Interes de mora', 7, 2020, 10, 'no se', '13131312', 'Pendiente');


--
-- TOC entry 3014 (class 0 OID 24679)
-- Dependencies: 211
-- Data for Name: unidades; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidades VALUES ('2-013', 'adasd', 323, '26943430');
INSERT INTO public.unidades VALUES ('123', 'sadsad', 213, '31212');
INSERT INTO public.unidades VALUES ('1', 'no se', 300, '27328852');
INSERT INTO public.unidades VALUES ('2', 'no se', 300, '2456325');
INSERT INTO public.unidades VALUES ('3', 'no se', 300, '8517596');
INSERT INTO public.unidades VALUES ('4', 'no se', 300, '732463');
INSERT INTO public.unidades VALUES ('5', 'no se', 300, '24578463');


--
-- TOC entry 3005 (class 0 OID 16430)
-- Dependencies: 202
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES ('26843430', 'sapm', '12345', 'samuel', 'perez', 'Administrador', '2312313');
INSERT INTO public.usuario VALUES ('26943430', 'sapmmm', '1234', 'adag', 'adasad', 'Propietario', '123');


--
-- TOC entry 3061 (class 0 OID 0)
-- Dependencies: 212
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 23, true);


--
-- TOC entry 3062 (class 0 OID 0)
-- Dependencies: 204
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 6, true);


--
-- TOC entry 3063 (class 0 OID 0)
-- Dependencies: 221
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 1, true);


--
-- TOC entry 3064 (class 0 OID 0)
-- Dependencies: 227
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 3, true);


--
-- TOC entry 3065 (class 0 OID 0)
-- Dependencies: 223
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 1, true);


--
-- TOC entry 3066 (class 0 OID 0)
-- Dependencies: 231
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 7, true);


--
-- TOC entry 3067 (class 0 OID 0)
-- Dependencies: 225
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 11, true);


--
-- TOC entry 3068 (class 0 OID 0)
-- Dependencies: 233
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 6, true);


--
-- TOC entry 3069 (class 0 OID 0)
-- Dependencies: 214
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 30, true);


--
-- TOC entry 3070 (class 0 OID 0)
-- Dependencies: 229
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 3, true);


--
-- TOC entry 3071 (class 0 OID 0)
-- Dependencies: 207
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 55, true);


--
-- TOC entry 3072 (class 0 OID 0)
-- Dependencies: 235
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_interes_condominio_id_seq', 10, true);


--
-- TOC entry 3073 (class 0 OID 0)
-- Dependencies: 219
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 21, true);


--
-- TOC entry 3074 (class 0 OID 0)
-- Dependencies: 217
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 16, true);


--
-- TOC entry 2845 (class 2606 OID 24807)
-- Name: asambleas asambleas_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2847 (class 2606 OID 24694)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 2827 (class 2606 OID 24624)
-- Name: banco banco_nombre_banco_nombre_banco1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_nombre_banco1_key UNIQUE (nombre_banco) INCLUDE (nombre_banco);


--
-- TOC entry 2829 (class 2606 OID 24622)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2859 (class 2606 OID 24750)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 2861 (class 2606 OID 24748)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2869 (class 2606 OID 24785)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 2863 (class 2606 OID 24760)
-- Name: concepto_gasto concepto_gasto_nom_concepto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_nom_concepto_key UNIQUE (nom_concepto);


--
-- TOC entry 2865 (class 2606 OID 24758)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2823 (class 2606 OID 16456)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2825 (class 2606 OID 16460)
-- Name: condominio condominio_rif_rif1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_rif1_key UNIQUE (rif) INCLUDE (rif);


--
-- TOC entry 2831 (class 2606 OID 24631)
-- Name: cuenta cuenta_n_cuenta_n_cuenta1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_n_cuenta1_key UNIQUE (n_cuenta) INCLUDE (n_cuenta);


--
-- TOC entry 2833 (class 2606 OID 24641)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta) INCLUDE (n_cuenta);


--
-- TOC entry 2873 (class 2606 OID 24818)
-- Name: cuotas_especiales cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2851 (class 2606 OID 24712)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (tipo);


--
-- TOC entry 2853 (class 2606 OID 24714)
-- Name: fondos fondos_tipo_tipo1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_tipo_tipo1_key UNIQUE (tipo) INCLUDE (tipo);


--
-- TOC entry 2867 (class 2606 OID 24774)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 2875 (class 2606 OID 24826)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2841 (class 2606 OID 24668)
-- Name: propietarios propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietarios
    ADD CONSTRAINT propietarios_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2837 (class 2606 OID 24663)
-- Name: proveedores proveedores_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2839 (class 2606 OID 24661)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula) INCLUDE (cedula);


--
-- TOC entry 2849 (class 2606 OID 24702)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 2871 (class 2606 OID 24799)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2835 (class 2606 OID 24639)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 2877 (class 2606 OID 24834)
-- Name: puente_interes_condominio puente_interes_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio
    ADD CONSTRAINT puente_interes_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 2857 (class 2606 OID 24730)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 2855 (class 2606 OID 24722)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 2843 (class 2606 OID 24683)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (n_unidad);


--
-- TOC entry 2815 (class 2606 OID 16469)
-- Name: usuario usuario_cedula_cedula1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_cedula1_key UNIQUE (cedula) INCLUDE (cedula);


--
-- TOC entry 2817 (class 2606 OID 16465)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula) INCLUDE (cedula);


--
-- TOC entry 2819 (class 2606 OID 16467)
-- Name: usuario usuario_usuario_usuario1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key UNIQUE (usuario) INCLUDE (usuario);


--
-- TOC entry 2821 (class 2606 OID 16471)
-- Name: usuario usuario_usuario_usuario1_key1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key1 UNIQUE (usuario) INCLUDE (usuario);


--
-- TOC entry 2878 (class 2606 OID 24761)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


-- Completed on 2020-04-26 10:16:08

--
-- PostgreSQL database dump complete
--

