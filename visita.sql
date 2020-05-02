--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

-- Started on 2020-05-01 22:59:16

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
-- TOC entry 250 (class 1259 OID 18174)
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
-- TOC entry 249 (class 1259 OID 18172)
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
-- TOC entry 2956 (class 0 OID 0)
-- Dependencies: 249
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 2822 (class 2604 OID 18177)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 2950 (class 0 OID 18174)
-- Dependencies: 250
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
-- TOC entry 2957 (class 0 OID 0)
-- Dependencies: 249
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 52, true);


--
-- TOC entry 2826 (class 2606 OID 18181)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 2827 (class 2606 OID 18182)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.visitante(cedula);


-- Completed on 2020-05-01 22:59:17

--
-- PostgreSQL database dump complete
--

