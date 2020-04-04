--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-04-03 19:50:34

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
-- TOC entry 2810 (class 0 OID 16430)
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


-- Completed on 2020-04-03 19:50:34

--
-- PostgreSQL database dump complete
--

