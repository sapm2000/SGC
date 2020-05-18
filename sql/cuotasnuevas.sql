--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-05-18 12:24:26

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
-- TOC entry 220 (class 1259 OID 27301)
-- Name: facturas_proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.facturas_proveedores (
    id integer NOT NULL,
    id_proveedor character varying(15) NOT NULL,
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
    n_mese_restante bigint,
    pagado character varying(15),
    tipo character varying(20)
);


ALTER TABLE public.facturas_proveedores OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 27307)
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
-- TOC entry 3012 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.facturas_proveedores.id;


--
-- TOC entry 2871 (class 2604 OID 27479)
-- Name: facturas_proveedores id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas_proveedores ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 3005 (class 0 OID 27301)
-- Dependencies: 220
-- Data for Name: facturas_proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.facturas_proveedores VALUES (77, '17102635', 'Total de Inmuebles', 5, 2020, 2000, 2000, 1, 0, '', 'Mensualidad Completada', '21321312', 0, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (76, 'J-1001245215', 'Alicuota', 5, 2020, 10000, 10000, 2, 0, '', 'Mensualidad Completada', '21321312', 0, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (78, 'J-1001245215', 'Alicuota', 6, 2020, 20000, 20000, 2, 0, 'asad', 'Pendiente', '21321312', 2, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (79, 'J-1001245215', 'Alicuota', 6, 2020, 20000, 20000, 1, 0, 'asad', 'Pendiente', '21321312', 1, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (80, 'J-2457021456', 'Alicuota', 6, 2020, 30000, 30000, 1, 0, '', 'Pendiente', '21321312', 1, NULL, NULL);
INSERT INTO public.facturas_proveedores VALUES (81, 'J-2457021456', 'Alicuota', 5, 2021, 1323, 1323, 2, 0, 'sada', 'Pendiente', '21321312', 2, NULL, NULL);


--
-- TOC entry 3013 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 81, true);


--
-- TOC entry 2873 (class 2606 OID 27534)
-- Name: facturas_proveedores cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas_proveedores
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


-- Completed on 2020-05-18 12:24:32

--
-- PostgreSQL database dump complete
--

