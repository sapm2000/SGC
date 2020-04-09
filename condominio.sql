--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-08 23:02:12

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
-- TOC entry 2853 (class 0 OID 0)
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
-- TOC entry 2700 (class 2604 OID 16444)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public."categoriaGasto_id_seq"'::regclass);


--
-- TOC entry 2845 (class 0 OID 16441)
-- Dependencies: 204
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoriagasto (id, nombre, descripcion) FROM stdin;
54	dfg	f
19	administrativo	conjunto de gastos administrativos
52	add	fg
21	true	este registro se ha 
55	s	s
34	ad	asd
\.


--
-- TOC entry 2847 (class 0 OID 16478)
-- Dependencies: 206
-- Data for Name: conceptoGastos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."conceptoGastos" ("idConG", "nombreConcepto", descripcion, categoria) FROM stdin;
\.


--
-- TOC entry 2846 (class 0 OID 16449)
-- Dependencies: 205
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.condominio (rif, razon_social, telefono, correo_electronico) FROM stdin;
13131312	sdadad	123132	asdasdasdadsa
111	holas	24334324	sa
123345	sdfsf	31231313	prueba
\.


--
-- TOC entry 2843 (class 0 OID 16430)
-- Dependencies: 202
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (cedula, usuario, password, nombre, apellido, tipo, ntelefono) FROM stdin;
26843430	sapm	12345	samuel	perez	Administrador	2312313
26943430	sapmmm	1234	adag	adasad	Propietario	123
123213	sdfdsfs	dfsfs	sdf	dsf	Propietario	12313
\.


--
-- TOC entry 2854 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."categoriaGasto_id_seq"', 55, true);


--
-- TOC entry 2710 (class 2606 OID 16446)
-- Name: categoriagasto categoriaGasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT "categoriaGasto_pkey" PRIMARY KEY (id);


--
-- TOC entry 2712 (class 2606 OID 16458)
-- Name: categoriagasto categoriagasto_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2714 (class 2606 OID 16456)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2716 (class 2606 OID 16460)
-- Name: condominio condominio_rif_rif1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_rif1_key UNIQUE (rif) INCLUDE (rif);


--
-- TOC entry 2702 (class 2606 OID 16469)
-- Name: usuario usuario_cedula_cedula1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_cedula1_key UNIQUE (cedula) INCLUDE (cedula);


--
-- TOC entry 2704 (class 2606 OID 16465)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula) INCLUDE (cedula);


--
-- TOC entry 2706 (class 2606 OID 16467)
-- Name: usuario usuario_usuario_usuario1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key UNIQUE (usuario) INCLUDE (usuario);


--
-- TOC entry 2708 (class 2606 OID 16471)
-- Name: usuario usuario_usuario_usuario1_key1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_usuario1_key1 UNIQUE (usuario) INCLUDE (usuario);


-- Completed on 2020-04-08 23:02:13

--
-- PostgreSQL database dump complete
--

