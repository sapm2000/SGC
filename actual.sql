--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-19 22:16:47

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
-- TOC entry 208 (class 1259 OID 24617)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 24615)
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
-- TOC entry 2886 (class 0 OID 0)
-- Dependencies: 207
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 204 (class 1259 OID 16441)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(30) NOT NULL,
    descripcion character varying(100) NOT NULL
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16439)
-- Name: categoriaGasto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."categoriaGasto_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."categoriaGasto_id_seq" OWNER TO postgres;

--
-- TOC entry 2887 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."categoriaGasto_id_seq" OWNED BY public.categoriagasto.id;


--
-- TOC entry 206 (class 1259 OID 16478)
-- Name: conceptoGastos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."conceptoGastos" (
    "idConG" bigint NOT NULL,
    "nombreConcepto" character varying(100) NOT NULL,
    descripcion character varying(100) NOT NULL,
    categoria character varying(100) NOT NULL
);


ALTER TABLE public."conceptoGastos" OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16449)
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
-- TOC entry 209 (class 1259 OID 24625)
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
-- TOC entry 211 (class 1259 OID 24634)
-- Name: puente_condominio_cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_condominio_cuenta (
    id integer NOT NULL,
    id_cuenta character varying(20),
    id_condominio character varying(20)
);


ALTER TABLE public.puente_condominio_cuenta OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24632)
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
-- TOC entry 2888 (class 0 OID 0)
-- Dependencies: 210
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


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
-- TOC entry 2717 (class 2604 OID 24620)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2716 (class 2604 OID 16444)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public."categoriaGasto_id_seq"'::regclass);


--
-- TOC entry 2718 (class 2604 OID 24637)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2877 (class 0 OID 24617)
-- Dependencies: 208
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.banco (id, nombre_banco) FROM stdin;
1	sfs
3	caribe
5	dsfs
4	provincial
\.


--
-- TOC entry 2873 (class 0 OID 16441)
-- Dependencies: 204
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoriagasto (id, nombre, descripcion) FROM stdin;
54	dfg	f
19	administrativo	conjunto de gastos administrativos
52	add	fg
21	true	este registro se ha 
57	wer	rw
\.


--
-- TOC entry 2875 (class 0 OID 16478)
-- Dependencies: 206
-- Data for Name: conceptoGastos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."conceptoGastos" ("idConG", "nombreConcepto", descripcion, categoria) FROM stdin;
\.


--
-- TOC entry 2874 (class 0 OID 16449)
-- Dependencies: 205
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.condominio (rif, razon_social, telefono, correo_electronico) FROM stdin;
13131312	sdadad	123132	asdasdasdadsa
111	holas	24334324	sa
\.


--
-- TOC entry 2878 (class 0 OID 24625)
-- Dependencies: 209
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuenta (cedula, n_cuenta, beneficiario, tipo, id_banco) FROM stdin;
sdsd	dasdasa	dsad	Corriente	4
dsfdsfsf	fsf	fdf	Ahorro	5
sfdsfs	sdfsf	f	Ahorro	4
3213d	ddfsf	f	Corriente	5
255325f	423424423	sd	Ahorro	4
31313e	qweq	ee	Ahorro	3
werr	09876543	dfg	Ahorro	5
sdd	sdsasdd	dsddsd	Ahorro	5
sdada	dsad	ddd	Ahorro	4
sadd	dsa	d	Ahorro	1
dsad	dsaddsd	ddd	Ahorro	1
sdff	fsflkjhgfds	ff	Ahorro	5
\.


--
-- TOC entry 2880 (class 0 OID 24634)
-- Dependencies: 211
-- Data for Name: puente_condominio_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_condominio_cuenta (id, id_cuenta, id_condominio) FROM stdin;
10	sdfsf	13131312
11	sdfsf	111
21	ddfsf	13131312
22	ddfsf	111
24	423424423	13131312
25	423424423	111
27	qweq	13131312
28	qweq	111
30	09876543	13131312
31	09876543	111
36	sdsasdd	13131312
37	sdsasdd	111
39	dsad	13131312
40	dsad	111
42	dsaddsd	13131312
43	dsaddsd	111
50	fsflkjhgfds	13131312
51	fsflkjhgfds	111
\.


--
-- TOC entry 2871 (class 0 OID 16430)
-- Dependencies: 202
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (cedula, usuario, password, nombre, apellido, tipo, ntelefono) FROM stdin;
26843430	sapm	12345	samuel	perez	Administrador	2312313
26943430	sapmmm	1234	adag	adasad	Propietario	123
\.


--
-- TOC entry 2889 (class 0 OID 0)
-- Dependencies: 207
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 5, true);


--
-- TOC entry 2890 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."categoriaGasto_id_seq"', 57, true);


--
-- TOC entry 2891 (class 0 OID 0)
-- Dependencies: 210
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 52, true);


--
-- TOC entry 2736 (class 2606 OID 24624)
-- Name: banco banco_nombre_banco_nombre_banco1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_nombre_banco1_key UNIQUE (nombre_banco) INCLUDE (nombre_banco);


--
-- TOC entry 2738 (class 2606 OID 24622)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2728 (class 2606 OID 16446)
-- Name: categoriagasto categoriaGasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT "categoriaGasto_pkey" PRIMARY KEY (id);


--
-- TOC entry 2730 (class 2606 OID 16458)
-- Name: categoriagasto categoriagasto_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2732 (class 2606 OID 16456)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2734 (class 2606 OID 16460)
-- Name: condominio condominio_rif_rif1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_rif1_key UNIQUE (rif) INCLUDE (rif);


--
-- TOC entry 2740 (class 2606 OID 24631)
-- Name: cuenta cuenta_n_cuenta_n_cuenta1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_n_cuenta1_key UNIQUE (n_cuenta) INCLUDE (n_cuenta);


--
-- TOC entry 2742 (class 2606 OID 24641)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta) INCLUDE (n_cuenta);


--
-- TOC entry 2744 (class 2606 OID 24639)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 2720 (class 2606 OID 16469)
-- Name: usuario usuario_cedula_cedula1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_cedula1_key UNIQUE (cedula) INCLUDE (cedula);


--
-- TOC entry 2722 (class 2606 OID 16465)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula) INCLUDE (cedula);


--
-- TOC entry 2724 (class 2606 OID 16467)
-- Name: usuario usuario_usuario_usuario1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key UNIQUE (usuario) INCLUDE (usuario);


--
-- TOC entry 2726 (class 2606 OID 16471)
-- Name: usuario usuario_usuario_usuario1_key1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key1 UNIQUE (usuario) INCLUDE (usuario);


-- Completed on 2020-04-19 22:16:48

--
-- PostgreSQL database dump complete
--

