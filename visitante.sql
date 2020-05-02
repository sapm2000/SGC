--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

-- Started on 2020-05-01 23:00:46

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

SET default_with_oids = false;

--
-- TOC entry 248 (class 1259 OID 18167)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    cedula character varying(10) NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 2945 (class 0 OID 18167)
-- Dependencies: 248
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.visitante VALUES ('V-28563254', 'Maria', 'Guedez');
INSERT INTO public.visitante VALUES ('V-27328852', 'Marta', 'Rodriguez');
INSERT INTO public.visitante VALUES ('V-26943430', 'Samuel', 'Mora');
INSERT INTO public.visitante VALUES ('V-8517596', 'Blanca', 'Silva');
INSERT INTO public.visitante VALUES ('V-1434801', 'Blanca', 'Ovija');


--
-- TOC entry 2823 (class 2606 OID 18171)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (cedula);


-- Completed on 2020-05-01 23:00:47

--
-- PostgreSQL database dump complete
--

