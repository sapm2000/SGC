--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-22 17:43:35

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
-- TOC entry 216 (class 1259 OID 24686)
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
-- TOC entry 215 (class 1259 OID 24684)
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
-- TOC entry 2935 (class 0 OID 0)
-- Dependencies: 215
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


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
-- TOC entry 2936 (class 0 OID 0)
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
-- TOC entry 2937 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."categoriaGasto_id_seq" OWNED BY public.categoriagasto.id;


--
-- TOC entry 206 (class 1259 OID 16478)
-- Name: concepto_gastos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gastos (
    id bigint NOT NULL,
    nom_concepto character varying(100) NOT NULL,
    descripcion character varying(100) NOT NULL,
    id_categoria bigint
);


ALTER TABLE public.concepto_gastos OWNER TO postgres;

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
-- TOC entry 213 (class 1259 OID 24664)
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
-- TOC entry 212 (class 1259 OID 24654)
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
-- TOC entry 218 (class 1259 OID 24697)
-- Name: puente_asamblea_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asamblea_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.puente_asamblea_propietario OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24695)
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
-- TOC entry 2938 (class 0 OID 0)
-- Dependencies: 217
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


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
-- TOC entry 2939 (class 0 OID 0)
-- Dependencies: 210
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


--
-- TOC entry 214 (class 1259 OID 24679)
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
-- TOC entry 2745 (class 2604 OID 24689)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2743 (class 2604 OID 24620)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2742 (class 2604 OID 16444)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public."categoriaGasto_id_seq"'::regclass);


--
-- TOC entry 2746 (class 2604 OID 24700)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2744 (class 2604 OID 24637)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2927 (class 0 OID 24686)
-- Dependencies: 216
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.asambleas (id, nombre, fecha, descripcion, id_condominio) FROM stdin;
2	sfd0	2000-01-01	sfs	123
1	asda	2020-04-22	asdsa	111
3	adsd	2020-04-23	dasd	111
4	asd	2020-04-18	asd	111
5	asd	2020-04-19	asd	111
6	adasda	2020-04-23	sadasd	111
7	asd	2020-04-23	adsd	111
9	asdsada	2020-04-16	dasadasdasdada	111
10	asd	2020-04-17	sd	111
8	da	2020-04-18	da	111
11	sasdad	2020-04-17	sadsda	111
12	asdasd	2020-04-09	asdd	111
13	asd	2020-04-16	asdasd	111
14	sad	2020-04-11	asdd	111
15	sadsd	2020-04-22	dasd	111
16	sads	2020-04-10	adda	111
18	asda	2020-04-22	dasd	111
17	asda	2020-04-18	ddsasd	111
19	asd	2020-04-10	sdad	111
20	s	2020-04-18	sd	111
21	sdfsdf	2020-04-18	ssdfsf	111
22	sdf	2020-04-03	dsf	111
\.


--
-- TOC entry 2919 (class 0 OID 24617)
-- Dependencies: 208
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.banco (id, nombre_banco) FROM stdin;
1	sfs
3	caribe
5	dsfs
4	provincial
6	venezuela
\.


--
-- TOC entry 2915 (class 0 OID 16441)
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
-- TOC entry 2917 (class 0 OID 16478)
-- Dependencies: 206
-- Data for Name: concepto_gastos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.concepto_gastos (id, nom_concepto, descripcion, id_categoria) FROM stdin;
\.


--
-- TOC entry 2916 (class 0 OID 16449)
-- Dependencies: 205
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.condominio (rif, razon_social, telefono, correo_electronico) FROM stdin;
13131312	sdadad	123132	asdasdasdadsa
111	holas	24334324	sa
\.


--
-- TOC entry 2920 (class 0 OID 24625)
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
sadd	dsa	d	Ahorro	1
dsad	dsaddsd	ddd	Ahorro	1
sdff	fsflkjhgfds	ff	Ahorro	5
sdada	dsad	ddd	Ahorro	4
sdd	sdsasdd	dsddsd	Ahorro	5
\.


--
-- TOC entry 2924 (class 0 OID 24664)
-- Dependencies: 213
-- Data for Name: propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.propietarios (cedula, nombre, apellido, telefono, correo, id_condominio) FROM stdin;
26943430	samuels	perezs	04245222312s	sa@sa.coms	111
732463	skfhsdjfkdsjfh	kllfkjkgh	ksglkjwl	sdkfsjflsd	13131312
31212	1312	dfghj	dfghjk	hg	111
\.


--
-- TOC entry 2923 (class 0 OID 24654)
-- Dependencies: 212
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.proveedores (cedula, nombre, telefono, correo, contacto, direccion) FROM stdin;
\.


--
-- TOC entry 2929 (class 0 OID 24697)
-- Dependencies: 218
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.puente_asamblea_propietario (id, id_asamblea, id_propietario) FROM stdin;
21	16	26943430
25	17	31212
26	19	26943430
27	20	26943430
28	22	26943430
\.


--
-- TOC entry 2922 (class 0 OID 24634)
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
42	dsaddsd	13131312
43	dsaddsd	111
50	fsflkjhgfds	13131312
51	fsflkjhgfds	111
54	dsad	111
55	sdsasdd	13131312
\.


--
-- TOC entry 2925 (class 0 OID 24679)
-- Dependencies: 214
-- Data for Name: unidades; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.unidades (n_unidad, direccion, area, id_propietario) FROM stdin;
\.


--
-- TOC entry 2913 (class 0 OID 16430)
-- Dependencies: 202
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (cedula, usuario, password, nombre, apellido, tipo, ntelefono) FROM stdin;
26843430	sapm	12345	samuel	perez	Administrador	2312313
26943430	sapmmm	1234	adag	adasad	Propietario	123
\.


--
-- TOC entry 2940 (class 0 OID 0)
-- Dependencies: 215
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 22, true);


--
-- TOC entry 2941 (class 0 OID 0)
-- Dependencies: 207
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 6, true);


--
-- TOC entry 2942 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."categoriaGasto_id_seq"', 57, true);


--
-- TOC entry 2943 (class 0 OID 0)
-- Dependencies: 217
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 28, true);


--
-- TOC entry 2944 (class 0 OID 0)
-- Dependencies: 210
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 55, true);


--
-- TOC entry 2784 (class 2606 OID 24694)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 2766 (class 2606 OID 24624)
-- Name: banco banco_nombre_banco_nombre_banco1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_nombre_banco1_key UNIQUE (nombre_banco) INCLUDE (nombre_banco);


--
-- TOC entry 2768 (class 2606 OID 24622)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2756 (class 2606 OID 16446)
-- Name: categoriagasto categoriaGasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT "categoriaGasto_pkey" PRIMARY KEY (id);


--
-- TOC entry 2758 (class 2606 OID 16458)
-- Name: categoriagasto categoriagasto_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2764 (class 2606 OID 24678)
-- Name: concepto_gastos concepto_gastos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gastos
    ADD CONSTRAINT concepto_gastos_pkey PRIMARY KEY (id);


--
-- TOC entry 2760 (class 2606 OID 16456)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2762 (class 2606 OID 16460)
-- Name: condominio condominio_rif_rif1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_rif1_key UNIQUE (rif) INCLUDE (rif);


--
-- TOC entry 2770 (class 2606 OID 24631)
-- Name: cuenta cuenta_n_cuenta_n_cuenta1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_n_cuenta1_key UNIQUE (n_cuenta) INCLUDE (n_cuenta);


--
-- TOC entry 2772 (class 2606 OID 24641)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta) INCLUDE (n_cuenta);


--
-- TOC entry 2780 (class 2606 OID 24668)
-- Name: propietarios propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietarios
    ADD CONSTRAINT propietarios_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2776 (class 2606 OID 24663)
-- Name: proveedores proveedores_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2778 (class 2606 OID 24661)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula) INCLUDE (cedula);


--
-- TOC entry 2786 (class 2606 OID 24702)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 2774 (class 2606 OID 24639)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 2782 (class 2606 OID 24683)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (n_unidad);


--
-- TOC entry 2748 (class 2606 OID 16469)
-- Name: usuario usuario_cedula_cedula1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_cedula1_key UNIQUE (cedula) INCLUDE (cedula);


--
-- TOC entry 2750 (class 2606 OID 16465)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula) INCLUDE (cedula);


--
-- TOC entry 2752 (class 2606 OID 16467)
-- Name: usuario usuario_usuario_usuario1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key UNIQUE (usuario) INCLUDE (usuario);


--
-- TOC entry 2754 (class 2606 OID 16471)
-- Name: usuario usuario_usuario_usuario1_key1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key1 UNIQUE (usuario) INCLUDE (usuario);


-- Completed on 2020-04-22 17:43:37

--
-- PostgreSQL database dump complete
--

