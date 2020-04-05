--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-04 14:11:00

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
-- TOC entry 2828 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."categoriaGasto_id_seq" OWNED BY public.categoriagasto.id;


--
-- TOC entry 202 (class 1259 OID 16430)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    cedula character varying(8),
    usuario character varying(10),
    password character varying(12),
    nombre character varying(15),
    apellido character varying(15),
    tipo character varying(50),
    ntelefono character varying(50)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 2691 (class 2604 OID 16444)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public."categoriaGasto_id_seq"'::regclass);


--
-- TOC entry 2822 (class 0 OID 16441)
-- Dependencies: 204
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoriagasto (id, nombre, descripcion) FROM stdin;
18	nombre	desc
\.


--
-- TOC entry 2820 (class 0 OID 16430)
-- Dependencies: 202
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (cedula, usuario, password, nombre, apellido, tipo, ntelefono) FROM stdin;
212121	jun	jun	juj	juj	Administrador	\N
212121	junio	jun	juj	juj	Administrador	\N
123	ansuaaaa	22	22	22	Propietario	\N
26943430	samuel	220489	samuel	perez	Administrador	04245222312
2323	dasdsa	asdadasd	sdasd	asda	Administrador	sad
\.


--
-- TOC entry 2829 (class 0 OID 0)
-- Dependencies: 203
-- Name: categoriaGasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."categoriaGasto_id_seq"', 18, true);


--
-- TOC entry 2693 (class 2606 OID 16446)
-- Name: categoriagasto categoriaGasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT "categoriaGasto_pkey" PRIMARY KEY (id);


-- Completed on 2020-04-04 14:11:02

--
-- PostgreSQL database dump complete
--

