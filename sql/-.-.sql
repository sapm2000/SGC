

--
-- TOC entry 269 (class 1255 OID 33881)
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

--
-- TOC entry 270 (class 1255 OID 33882)
-- Name: actualizar_status_cuotas(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.actualizar_status_cuotas(id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
	saldo2 int;
BEGIN
saldo2 := (SELECT saldo FROM cuotas_especiales WHERE id = id2);

IF saldo2 = 0 THEN
	UPDATE cuotas_especiales SET pagado = 'Pagado' WHERE id = id2;
END IF;
END;
$$;


ALTER FUNCTION public.actualizar_status_cuotas(id2 integer) OWNER TO postgres;

--
-- TOC entry 271 (class 1255 OID 34211)
-- Name: login(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.login(usu character varying, pass character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
	DECLARE
	
	usu1 character varying;
	pass1 character varying;
	
	BEGIN
		
		usu1 := (SELECT usuario FROM usuario where usuario=usu AND password=pass);
		pass1 := (SELECT password FROM usuario where usuario=usu AND password=pass);

		IF usu = usu1 AND pass = pass1 THEN 
			RETURN TRUE;
		ELSE
			RETURN FALSE;
		END IF;
		END;
		$$;


ALTER FUNCTION public.login(usu character varying, pass character varying) OWNER TO postgres;

--
-- TOC entry 272 (class 1255 OID 34298)
-- Name: registrar_unidad(character varying, character varying, double precision, character varying[], character varying[], integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_unidad(numero2 character varying, direccion2 character varying, area2 double precision, cedula2 character varying[], documento2 character varying[], id_condominio integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	cedula_bd character varying(10);
	id_unidad_bd int;
BEGIN
	INSERT INTO unidades(n_unidad, direccion, area, id_condominio) VALUES (numero2, direccion2, area2, documento2);
	
	FOR i IN 1 .. array_length(cedula2, 1) LOOP
		cedula_bd := (SELECT cedula FROM propietario WHERE cedula = cedula2[i]);
		id_unidad_bd := (SELECT max(id) FROM unidad);
		INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, documento) VALUES (cedula_bd, id_unidad_bd, documento2);
	END LOOP;
END;
$$;


ALTER FUNCTION public.registrar_unidad(numero2 character varying, direccion2 character varying, area2 double precision, cedula2 character varying[], documento2 character varying[], id_condominio integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 33883)
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
-- TOC entry 203 (class 1259 OID 33889)
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
-- TOC entry 3257 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 204 (class 1259 OID 33891)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(30) NOT NULL,
    activo bigint
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 33894)
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
-- TOC entry 3258 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 206 (class 1259 OID 33896)
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
-- TOC entry 207 (class 1259 OID 33899)
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
-- TOC entry 3259 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 208 (class 1259 OID 33901)
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
-- TOC entry 209 (class 1259 OID 33904)
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
-- TOC entry 3260 (class 0 OID 0)
-- Dependencies: 209
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cierre_de_mes_id_seq OWNED BY public.cierre_de_mes.id;


--
-- TOC entry 210 (class 1259 OID 33906)
-- Name: cobro_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cobro_unidad (
    id integer NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(500) NOT NULL,
    id_cuenta character varying(20) NOT NULL,
    forma_pago character varying(150) NOT NULL,
    referencia character varying(50) NOT NULL,
    fecha date NOT NULL,
    id_fondo bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.cobro_unidad OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 33912)
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
-- TOC entry 3261 (class 0 OID 0)
-- Dependencies: 211
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 212 (class 1259 OID 33914)
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
-- TOC entry 213 (class 1259 OID 33920)
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
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 213
-- Name: comunicados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id;


--
-- TOC entry 214 (class 1259 OID 33922)
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
-- TOC entry 215 (class 1259 OID 33925)
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
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 215
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 216 (class 1259 OID 33927)
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
-- TOC entry 217 (class 1259 OID 33930)
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
-- TOC entry 218 (class 1259 OID 33933)
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
-- TOC entry 219 (class 1259 OID 33936)
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
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 219
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 220 (class 1259 OID 33938)
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
    n_mese_restante bigint,
    pagado character varying(15)
);


ALTER TABLE public.cuotas_especiales OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 33944)
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
-- TOC entry 3265 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuotas_especiales_id_seq OWNED BY public.cuotas_especiales.id;


--
-- TOC entry 222 (class 1259 OID 33946)
-- Name: detalle_pagos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_pagos (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    id_gasto bigint,
    id_factura bigint,
    tipo_gasto character varying
);


ALTER TABLE public.detalle_pagos OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 33952)
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
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 223
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.detalle_pagos.id;


--
-- TOC entry 224 (class 1259 OID 33954)
-- Name: factura_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.factura_unidad (
    id integer NOT NULL,
    monto double precision NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    alicuota double precision NOT NULL,
    estado character varying(50) NOT NULL,
    saldo_restante double precision NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.factura_unidad OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 33957)
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
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 225
-- Name: detalle_total_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_total_id_seq OWNED BY public.factura_unidad.id;


--
-- TOC entry 226 (class 1259 OID 33959)
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
    id integer NOT NULL,
    activo bigint
);


ALTER TABLE public.fondos OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 33965)
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
-- TOC entry 3268 (class 0 OID 0)
-- Dependencies: 227
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 228 (class 1259 OID 33967)
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
-- TOC entry 229 (class 1259 OID 33970)
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
-- TOC entry 3269 (class 0 OID 0)
-- Dependencies: 229
-- Name: gasto_comun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_comun_id_seq OWNED BY public.gasto_comun.id;


--
-- TOC entry 230 (class 1259 OID 33972)
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
-- TOC entry 231 (class 1259 OID 33975)
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
-- TOC entry 3270 (class 0 OID 0)
-- Dependencies: 231
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 232 (class 1259 OID 33977)
-- Name: pagar_cuota_especial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagar_cuota_especial (
    id integer NOT NULL,
    numero_ref character varying(10) NOT NULL,
    forma_pago character varying(30) NOT NULL,
    descripcion character varying(200) NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    id_cuenta character varying NOT NULL,
    id_fondo integer NOT NULL,
    id_cuota_e integer NOT NULL
);


ALTER TABLE public.pagar_cuota_especial OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 33983)
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagar_cuota_especial_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagar_cuota_especial_id_seq OWNER TO postgres;

--
-- TOC entry 3271 (class 0 OID 0)
-- Dependencies: 233
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagar_cuota_especial_id_seq OWNED BY public.pagar_cuota_especial.id;


--
-- TOC entry 258 (class 1259 OID 34232)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    cedula character varying(10) NOT NULL,
    p_nombre character varying(25) NOT NULL,
    s_nombre character varying(25) DEFAULT ''::character varying NOT NULL,
    p_apellido character varying(25) NOT NULL,
    s_apellido character varying(25) DEFAULT ''::character varying NOT NULL,
    telefono character varying(12) NOT NULL,
    correo character varying(60) NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 34260)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
)
INHERITS (public.persona);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 33985)
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
-- TOC entry 235 (class 1259 OID 33988)
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
-- TOC entry 236 (class 1259 OID 33994)
-- Name: puente_asamblea_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asamblea_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    id_propietario character varying(15) NOT NULL
);


ALTER TABLE public.puente_asamblea_propietario OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 33997)
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
-- TOC entry 3272 (class 0 OID 0)
-- Dependencies: 237
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asamblea_propietario.id;


--
-- TOC entry 238 (class 1259 OID 33999)
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
-- TOC entry 239 (class 1259 OID 34002)
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
-- TOC entry 3273 (class 0 OID 0)
-- Dependencies: 239
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 240 (class 1259 OID 34004)
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
-- TOC entry 241 (class 1259 OID 34010)
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
-- TOC entry 3274 (class 0 OID 0)
-- Dependencies: 241
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 242 (class 1259 OID 34012)
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
-- TOC entry 243 (class 1259 OID 34015)
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
-- TOC entry 3275 (class 0 OID 0)
-- Dependencies: 243
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_condomino_cuenta_id_seq OWNED BY public.puente_condominio_cuenta.id;


--
-- TOC entry 244 (class 1259 OID 34017)
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
-- TOC entry 245 (class 1259 OID 34020)
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
-- TOC entry 3276 (class 0 OID 0)
-- Dependencies: 245
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_interes_condominio_id_seq OWNED BY public.puente_interes_condominio.id;


--
-- TOC entry 246 (class 1259 OID 34022)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 34025)
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
-- TOC entry 3277 (class 0 OID 0)
-- Dependencies: 247
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 266 (class 1259 OID 34301)
-- Name: puente_unidad_propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_unidad_propietarios (
    id integer NOT NULL,
    ci_propietario character varying(15) NOT NULL,
    id_unidad bigint NOT NULL,
    fecha_desde date DEFAULT LOCALTIMESTAMP(0) NOT NULL,
    fecha_hasta date,
    documento character varying(30) NOT NULL,
    estado bigint NOT NULL,
    activo boolean DEFAULT true
);


ALTER TABLE public.puente_unidad_propietarios OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 34299)
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
-- TOC entry 3278 (class 0 OID 0)
-- Dependencies: 265
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 259 (class 1259 OID 34244)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
)
INHERITS (public.persona);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 34032)
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
-- TOC entry 249 (class 1259 OID 34035)
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
-- TOC entry 3279 (class 0 OID 0)
-- Dependencies: 249
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 264 (class 1259 OID 34278)
-- Name: unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidad (
    id integer NOT NULL,
    n_unidad character varying(10) NOT NULL,
    direccion character varying(200) NOT NULL,
    area bigint NOT NULL,
    id_condominio character varying(15),
    activo boolean DEFAULT true
);


ALTER TABLE public.unidad OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 34276)
-- Name: unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.unidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3280 (class 0 OID 0)
-- Dependencies: 263
-- Name: unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidad_id_seq OWNED BY public.unidad.id;


--
-- TOC entry 250 (class 1259 OID 34037)
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
-- TOC entry 251 (class 1259 OID 34040)
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
-- TOC entry 3281 (class 0 OID 0)
-- Dependencies: 251
-- Name: unidades_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidades_id_seq OWNED BY public.unidades.id;


--
-- TOC entry 252 (class 1259 OID 34042)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    cedula character varying(8) NOT NULL,
    usuario character varying(25),
    password character varying(32),
    nombre character varying(15),
    apellido character varying(15),
    tipo character varying(50),
    ntelefono character varying(50)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 34204)
-- Name: v_cuenta_pagar; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta_pagar AS
 SELECT cp.num_ref,
    cp.forma_pago,
    cp.descripcion,
    cp.monto,
    cp.fecha,
    prov.nombre,
    cu.n_cuenta,
    ba.nombre_banco,
    fon.tipo
   FROM ((((public.cuenta_pagar cp
     JOIN public.proveedores prov ON (((prov.cedula)::text = (cp.id_proveedor)::text)))
     JOIN public.cuenta cu ON (((cu.n_cuenta)::text = (cp.id_cuenta)::text)))
     JOIN public.fondos fon ON ((fon.id = cp.id_fondo)))
     JOIN public.banco ba ON ((ba.id = cu.id_banco)))
  ORDER BY cp.fecha DESC;


ALTER TABLE public.v_cuenta_pagar OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 34045)
-- Name: v_pagar_cuota; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_pagar_cuota AS
 SELECT pce.numero_ref,
    pce.forma_pago,
    pce.monto,
    pce.fecha,
    cu.n_cuenta,
    ban.nombre_banco,
    fon.tipo,
    coga.nom_concepto,
    cues.pagado,
    cues.id_condominio,
    pr.nombre
   FROM ((((((public.pagar_cuota_especial pce
     JOIN public.cuotas_especiales cues ON ((pce.id_cuota_e = cues.id)))
     JOIN public.concepto_gasto coga ON ((cues.id_concepto = coga.id)))
     JOIN public.fondos fon ON ((pce.id_fondo = fon.id)))
     JOIN public.cuenta cu ON (((pce.id_cuenta)::text = (cu.n_cuenta)::text)))
     JOIN public.banco ban ON ((cu.id_banco = ban.id)))
     JOIN public.proveedores pr ON (((cues.id_proveedor)::text = (pr.cedula)::text)))
  WHERE ((cues.pagado)::text = 'Pagado'::text);


ALTER TABLE public.v_pagar_cuota OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 34272)
-- Name: v_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_propietario AS
 SELECT propietario.cedula,
    propietario.p_nombre,
    propietario.s_nombre,
    propietario.p_apellido,
    propietario.s_apellido,
    propietario.telefono,
    propietario.correo
   FROM public.propietario
  WHERE (propietario.activo = true);


ALTER TABLE public.v_propietario OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 34256)
-- Name: v_responsable; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_responsable AS
 SELECT responsable.cedula,
    responsable.p_nombre,
    responsable.s_nombre,
    responsable.p_apellido,
    responsable.s_apellido,
    responsable.telefono,
    responsable.correo
   FROM public.responsable
  WHERE (responsable.activo = true);


ALTER TABLE public.v_responsable OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 34325)
-- Name: v_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad AS
 SELECT unidad.id,
    unidad.n_unidad,
    unidad.direccion,
    unidad.area,
    unidad.id_condominio
   FROM public.unidad
  WHERE (unidad.activo = true);


ALTER TABLE public.v_unidad OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 34329)
-- Name: v_unidad_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad_propietario AS
 SELECT u.id,
    u.id_condominio,
    pro.cedula,
    pro.p_nombre,
    pro.s_nombre,
    pro.p_apellido,
    pro.s_apellido,
    pro.telefono,
    pro.correo,
    puente.fecha_desde,
    puente.fecha_hasta,
    puente.estado,
    puente.documento
   FROM ((public.v_propietario pro
     JOIN public.puente_unidad_propietarios puente ON (((puente.ci_propietario)::text = (pro.cedula)::text)))
     JOIN public.unidad u ON ((u.id = puente.id_unidad)))
  WHERE (u.activo = true);


ALTER TABLE public.v_unidad_propietario OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 34050)
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
-- TOC entry 255 (class 1259 OID 34055)
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
-- TOC entry 3282 (class 0 OID 0)
-- Dependencies: 255
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 256 (class 1259 OID 34057)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    cedula character varying(10) NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 2910 (class 2604 OID 34060)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 2911 (class 2604 OID 34061)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 2912 (class 2604 OID 34062)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 2913 (class 2604 OID 34063)
-- Name: cierre_de_mes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes ALTER COLUMN id SET DEFAULT nextval('public.cierre_de_mes_id_seq'::regclass);


--
-- TOC entry 2914 (class 2604 OID 34064)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 2915 (class 2604 OID 34065)
-- Name: comunicados id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass);


--
-- TOC entry 2916 (class 2604 OID 34066)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 2917 (class 2604 OID 34067)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 2918 (class 2604 OID 34068)
-- Name: cuotas_especiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales ALTER COLUMN id SET DEFAULT nextval('public.cuotas_especiales_id_seq'::regclass);


--
-- TOC entry 2919 (class 2604 OID 34069)
-- Name: detalle_pagos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 2920 (class 2604 OID 34070)
-- Name: factura_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura_unidad ALTER COLUMN id SET DEFAULT nextval('public.detalle_total_id_seq'::regclass);


--
-- TOC entry 2921 (class 2604 OID 34071)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 2922 (class 2604 OID 34072)
-- Name: gasto_comun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun ALTER COLUMN id SET DEFAULT nextval('public.gasto_comun_id_seq'::regclass);


--
-- TOC entry 2923 (class 2604 OID 34073)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 2924 (class 2604 OID 34074)
-- Name: pagar_cuota_especial id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial ALTER COLUMN id SET DEFAULT nextval('public.pagar_cuota_especial_id_seq'::regclass);


--
-- TOC entry 2942 (class 2604 OID 34263)
-- Name: propietario s_nombre; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario ALTER COLUMN s_nombre SET DEFAULT ''::character varying;


--
-- TOC entry 2943 (class 2604 OID 34264)
-- Name: propietario s_apellido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario ALTER COLUMN s_apellido SET DEFAULT ''::character varying;


--
-- TOC entry 2944 (class 2604 OID 34265)
-- Name: propietario activo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario ALTER COLUMN activo SET DEFAULT true;


--
-- TOC entry 2925 (class 2604 OID 34075)
-- Name: puente_asamblea_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 2926 (class 2604 OID 34076)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 2927 (class 2604 OID 34077)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 2928 (class 2604 OID 34078)
-- Name: puente_condominio_cuenta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta ALTER COLUMN id SET DEFAULT nextval('public.puente_condomino_cuenta_id_seq'::regclass);


--
-- TOC entry 2929 (class 2604 OID 34079)
-- Name: puente_interes_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_interes_condominio_id_seq'::regclass);


--
-- TOC entry 2930 (class 2604 OID 34080)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 2947 (class 2604 OID 34304)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 2939 (class 2604 OID 34247)
-- Name: responsable s_nombre; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable ALTER COLUMN s_nombre SET DEFAULT ''::character varying;


--
-- TOC entry 2940 (class 2604 OID 34248)
-- Name: responsable s_apellido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable ALTER COLUMN s_apellido SET DEFAULT ''::character varying;


--
-- TOC entry 2941 (class 2604 OID 34249)
-- Name: responsable activo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable ALTER COLUMN activo SET DEFAULT true;


--
-- TOC entry 2931 (class 2604 OID 34082)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 2945 (class 2604 OID 34281)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 2932 (class 2604 OID 34083)
-- Name: unidades id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades ALTER COLUMN id SET DEFAULT nextval('public.unidades_id_seq'::regclass);


--
-- TOC entry 2935 (class 2604 OID 34084)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3191 (class 0 OID 33883)
-- Dependencies: 202
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3193 (class 0 OID 33891)
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
-- TOC entry 3195 (class 0 OID 33896)
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
-- TOC entry 3197 (class 0 OID 33901)
-- Dependencies: 208
-- Data for Name: cierre_de_mes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cierre_de_mes VALUES (57, 5, 2020, '21321312');


--
-- TOC entry 3199 (class 0 OID 33906)
-- Dependencies: 210
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cobro_unidad VALUES (11, 12500, 'asdas', '01020045120268985654', 'Transferencia', 'asdsad', '2020-05-09', 55, 66);
INSERT INTO public.cobro_unidad VALUES (12, 10000, 'fdf', '01020045120268985654', 'Transferencia', '12313', '2020-05-09', 55, 66);


--
-- TOC entry 3201 (class 0 OID 33914)
-- Dependencies: 212
-- Data for Name: comunicados; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3203 (class 0 OID 33922)
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
-- TOC entry 3205 (class 0 OID 33927)
-- Dependencies: 216
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('21321312', 'asdasd', '12313', 'asdasdad', 1);


--
-- TOC entry 3206 (class 0 OID 33930)
-- Dependencies: 217
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('J-285856', '01145247946596656485', 'Condominio Portal del Este', 'Corriente', 3, 1);
INSERT INTO public.cuenta VALUES ('v-358963', '01052458795254653322', 'Condominio Ciudad Roca', 'Corriente', 5, 1);
INSERT INTO public.cuenta VALUES ('J-245698', '01085698745685232540', 'Urbanizacion La Ascension', 'Corriente', 4, 1);
INSERT INTO public.cuenta VALUES ('J-102457', '01020045120268985654', 'Urbanizacion Pardos del Norte', 'Corriente', 6, 1);
INSERT INTO public.cuenta VALUES ('J-254551', '01025487596584758945', 'Condominio Estrella', 'Corriente', 1, 1);


--
-- TOC entry 3207 (class 0 OID 33933)
-- Dependencies: 218
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3209 (class 0 OID 33938)
-- Dependencies: 220
-- Data for Name: cuotas_especiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuotas_especiales VALUES (77, '17102635', 26, 'Total de Inmuebles', 5, 2020, 2000, 2000, 1, 0, '', 'Mensualidad Completada', '21321312', 0, NULL);
INSERT INTO public.cuotas_especiales VALUES (76, 'J-1001245215', 28, 'Alicuota', 5, 2020, 10000, 10000, 2, 0, '', 'Mensualidad Completada', '21321312', 0, NULL);


--
-- TOC entry 3211 (class 0 OID 33946)
-- Dependencies: 222
-- Data for Name: detalle_pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.detalle_pagos VALUES (1316, 5, 2020, 2000, 79, 157, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1317, 5, 2020, 5000, 80, 157, 'Gasto comun');
INSERT INTO public.detalle_pagos VALUES (1318, 5, 2020, 5000, 76, 157, 'Cuota especial');
INSERT INTO public.detalle_pagos VALUES (1319, 5, 2020, 1000, 80, 157, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1320, 5, 2020, 100, 82, 157, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1321, 5, 2020, 1000, 81, 157, 'Sancion');
INSERT INTO public.detalle_pagos VALUES (1322, 5, 2020, 7200, 8, 157, 'Interes');
INSERT INTO public.detalle_pagos VALUES (1323, 5, 2020, 1200, 9, 157, 'Interes');


--
-- TOC entry 3213 (class 0 OID 33954)
-- Dependencies: 224
-- Data for Name: factura_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.factura_unidad VALUES (157, 22500, 5, 2020, 1, 'Pagado', 0, 66);


--
-- TOC entry 3215 (class 0 OID 33959)
-- Dependencies: 226
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('asdads', '2020-05-09', 'adsasd', 'asda', 31231, 31231, '21321312', 56, 1);
INSERT INTO public.fondos VALUES ('sdadsad', '2020-05-16', 'fsdfds', 'sdfsd', 0, 0, '21321312', 58, 0);
INSERT INTO public.fondos VALUES ('asdad', '2020-05-16', 'dadsad', 'sadas', 21313, 0, '21321312', 55, 1);
INSERT INTO public.fondos VALUES ('asdsad', '2020-05-10', 'fsf', 'sfdsd', 0, 0, '21321312', 57, 1);


--
-- TOC entry 3217 (class 0 OID 33967)
-- Dependencies: 228
-- Data for Name: gasto_comun; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto_comun VALUES (79, 'Ordinario', 5, 2020, 2000, '2727722', '24666587', 25, '', '2020-05-14', 'Pendiente de Pago', '21321312', 2000);
INSERT INTO public.gasto_comun VALUES (80, 'Ordinario', 5, 2020, 5000, '2727722', 'J-54785696', 26, '', '2020-05-14', 'Pendiente de Pago', '21321312', 5000);
INSERT INTO public.gasto_comun VALUES (81, 'Ordinario', 6, 2020, 100, '1212', 'J-1001245215', 29, 'perro', '2020-05-12', 'Pendiente', '21321312', 100);


--
-- TOC entry 3219 (class 0 OID 33972)
-- Dependencies: 230
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (14, 'Compensatorios', 6, 'Activo', 1);
INSERT INTO public.interes VALUES (10, 'Fondo de Reserva', 30, 'Activo', 1);
INSERT INTO public.interes VALUES (15, 'Legales', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (12, 'Anatosismo', 4, 'Activo', 1);
INSERT INTO public.interes VALUES (13, 'Convencional', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (16, 'indexacion', 2, 'Activo', 1);
INSERT INTO public.interes VALUES (11, 'Liquidacion', 5, 'Activo', 1);
INSERT INTO public.interes VALUES (17, 'Moratorios', 3, 'Activo', 1);
INSERT INTO public.interes VALUES (8, 'Inflacionario', 60, 'Activo', 1);
INSERT INTO public.interes VALUES (9, 'Prestaciones Sociales', 10, 'Activo', 1);


--
-- TOC entry 3221 (class 0 OID 33977)
-- Dependencies: 232
-- Data for Name: pagar_cuota_especial; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3245 (class 0 OID 34232)
-- Dependencies: 258
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3247 (class 0 OID 34260)
-- Dependencies: 261
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietario VALUES ('V-26942316', 'Diegolll', '', 'Rod', '', '04125698745', 'di', true);
INSERT INTO public.propietario VALUES ('V-25686626', 'Giu', '', 'Gii', '', '04125698746', 'gg', true);
INSERT INTO public.propietario VALUES ('V-26356487', 'ff', '', 'ff', '', '11111111111', 'll', true);


--
-- TOC entry 3223 (class 0 OID 33985)
-- Dependencies: 234
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
-- TOC entry 3224 (class 0 OID 33988)
-- Dependencies: 235
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
-- TOC entry 3225 (class 0 OID 33994)
-- Dependencies: 236
-- Data for Name: puente_asamblea_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3227 (class 0 OID 33999)
-- Dependencies: 238
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_cobro_factura VALUES (8, 157, 11, 12500);
INSERT INTO public.puente_cobro_factura VALUES (9, 157, 12, 10000);


--
-- TOC entry 3229 (class 0 OID 34004)
-- Dependencies: 240
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3231 (class 0 OID 34012)
-- Dependencies: 242
-- Data for Name: puente_condominio_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_condominio_cuenta VALUES (139, '01020045120268985654', '21321312', 1);
INSERT INTO public.puente_condominio_cuenta VALUES (140, '01025487596584758945', '21321312', 1);


--
-- TOC entry 3233 (class 0 OID 34017)
-- Dependencies: 244
-- Data for Name: puente_interes_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_interes_condominio VALUES (95, '21321312', 8, 1);
INSERT INTO public.puente_interes_condominio VALUES (96, '21321312', 9, 1);


--
-- TOC entry 3235 (class 0 OID 34022)
-- Dependencies: 246
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (192, 80, 66);
INSERT INTO public.puente_sancion_unidad VALUES (193, 82, 66);
INSERT INTO public.puente_sancion_unidad VALUES (194, 81, 66);
INSERT INTO public.puente_sancion_unidad VALUES (195, 83, 66);


--
-- TOC entry 3251 (class 0 OID 34301)
-- Dependencies: 266
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (1, 'V-26942316', 5, '2020-05-16', NULL, '111', 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (2, 'V-25686626', 5, '2020-05-16', NULL, '222', 1, true);


--
-- TOC entry 3246 (class 0 OID 34244)
-- Dependencies: 259
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.responsable VALUES ('E-23123145', 'dd', '', 'dd', '', 'ss44', 'dd', false);
INSERT INTO public.responsable VALUES ('V-22318939', 'anthony', 'jhen', 'Suarez', 'leal', '0414253', 'yuju', true);
INSERT INTO public.responsable VALUES ('V-26942316', 'Diego', '', 'Rod', '', '0412544563', 'di@', false);


--
-- TOC entry 3237 (class 0 OID 34032)
-- Dependencies: 248
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (80, 'Multa', 5, 2020, 1000, 'nu', 'Procesado');
INSERT INTO public.sancion VALUES (82, 'Multa', 5, 2020, 100, 'nu', 'Procesado');
INSERT INTO public.sancion VALUES (81, 'Multa', 5, 2020, 1000, 'nu', 'Procesado');
INSERT INTO public.sancion VALUES (83, 'Multa', 6, 2020, 1000, 'nu', 'Pendiente');


--
-- TOC entry 3249 (class 0 OID 34278)
-- Dependencies: 264
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidad VALUES (5, '22', 'jjjjj', 500, '21321312', true);


--
-- TOC entry 3239 (class 0 OID 34037)
-- Dependencies: 250
-- Data for Name: unidades; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidades VALUES ('34', 'asdada', 100, 66, '21321312', 1);
INSERT INTO public.unidades VALUES ('5b', 'asdada', 200, 67, '21321312', 1);
INSERT INTO public.unidades VALUES ('2323232323', 'ff', 200, 68, '21321312', NULL);


--
-- TOC entry 3241 (class 0 OID 34042)
-- Dependencies: 252
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES ('26843430', 'sapm', '12345', 'samuel', 'perez', 'Administrador', '2312313');
INSERT INTO public.usuario VALUES ('21963231', 'mmm', '2222', 'Maria', 'Suarez', 'Propietario', '04125084544');
INSERT INTO public.usuario VALUES ('17789654', 'asas', '14', 'Dana', 'Lucas', 'Propietario', '0413247856');
INSERT INTO public.usuario VALUES ('8475896', 'qqqqq', '124587', 'Maria', 'Alvarado', 'Propietario', '04125478965');
INSERT INTO public.usuario VALUES ('7894563', 'asrrrr', '7845', 'Lucas', 'Alvarado', 'Propietario', '04125477555');
INSERT INTO public.usuario VALUES ('6457896', 'ddddd', '', 'Marta', 'Solis', 'Propietario', '04125478963');
INSERT INTO public.usuario VALUES ('6454565', 'dddddqqqq', '', 'Mario', 'Salas', 'Propietario', '04121078963');
INSERT INTO public.usuario VALUES ('15246325', 'mdjfas', '', 'Daniel', 'Aguirre', 'Propietario', '04121078100');
INSERT INTO public.usuario VALUES ('26943430', 'Ingresar Usuario', '****', 'Sam', 'Lugo', 'Propietario', '123');
INSERT INTO public.usuario VALUES ('20122354', 'admin', 'admin', 'Juan', 'Lopez', 'Propietario', '04121145278');


--
-- TOC entry 3242 (class 0 OID 34050)
-- Dependencies: 254
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3244 (class 0 OID 34057)
-- Dependencies: 256
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3283 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 81, true);


--
-- TOC entry 3284 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 11, true);


--
-- TOC entry 3285 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 6, true);


--
-- TOC entry 3286 (class 0 OID 0)
-- Dependencies: 209
-- Name: cierre_de_mes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cierre_de_mes_id_seq', 57, true);


--
-- TOC entry 3287 (class 0 OID 0)
-- Dependencies: 211
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 12, true);


--
-- TOC entry 3288 (class 0 OID 0)
-- Dependencies: 213
-- Name: comunicados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 53, true);


--
-- TOC entry 3289 (class 0 OID 0)
-- Dependencies: 215
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 41, true);


--
-- TOC entry 3290 (class 0 OID 0)
-- Dependencies: 219
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 1, false);


--
-- TOC entry 3291 (class 0 OID 0)
-- Dependencies: 221
-- Name: cuotas_especiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuotas_especiales_id_seq', 77, true);


--
-- TOC entry 3292 (class 0 OID 0)
-- Dependencies: 223
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1323, true);


--
-- TOC entry 3293 (class 0 OID 0)
-- Dependencies: 225
-- Name: detalle_total_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_total_id_seq', 157, true);


--
-- TOC entry 3294 (class 0 OID 0)
-- Dependencies: 227
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 58, true);


--
-- TOC entry 3295 (class 0 OID 0)
-- Dependencies: 229
-- Name: gasto_comun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_comun_id_seq', 81, true);


--
-- TOC entry 3296 (class 0 OID 0)
-- Dependencies: 231
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 17, true);


--
-- TOC entry 3297 (class 0 OID 0)
-- Dependencies: 233
-- Name: pagar_cuota_especial_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagar_cuota_especial_id_seq', 1, false);


--
-- TOC entry 3298 (class 0 OID 0)
-- Dependencies: 237
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 258, true);


--
-- TOC entry 3299 (class 0 OID 0)
-- Dependencies: 239
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 9, true);


--
-- TOC entry 3300 (class 0 OID 0)
-- Dependencies: 241
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 503, true);


--
-- TOC entry 3301 (class 0 OID 0)
-- Dependencies: 243
-- Name: puente_condomino_cuenta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_condomino_cuenta_id_seq', 140, true);


--
-- TOC entry 3302 (class 0 OID 0)
-- Dependencies: 245
-- Name: puente_interes_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_interes_condominio_id_seq', 96, true);


--
-- TOC entry 3303 (class 0 OID 0)
-- Dependencies: 247
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 195, true);


--
-- TOC entry 3304 (class 0 OID 0)
-- Dependencies: 265
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 7, true);


--
-- TOC entry 3305 (class 0 OID 0)
-- Dependencies: 249
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 83, true);


--
-- TOC entry 3306 (class 0 OID 0)
-- Dependencies: 263
-- Name: unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidad_id_seq', 7, true);


--
-- TOC entry 3307 (class 0 OID 0)
-- Dependencies: 251
-- Name: unidades_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidades_id_seq', 68, true);


--
-- TOC entry 3308 (class 0 OID 0)
-- Dependencies: 255
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 52, true);


--
-- TOC entry 2951 (class 2606 OID 34086)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 2953 (class 2606 OID 34088)
-- Name: banco banco_nombre_banco_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_nombre_banco_key UNIQUE (nombre_banco);


--
-- TOC entry 2955 (class 2606 OID 34090)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2957 (class 2606 OID 34092)
-- Name: categoriagasto categoriagasto_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_nombre_key UNIQUE (nombre);


--
-- TOC entry 2959 (class 2606 OID 34094)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2961 (class 2606 OID 34096)
-- Name: cierre_de_mes cierre_de_mes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cierre_de_mes
    ADD CONSTRAINT cierre_de_mes_pkey PRIMARY KEY (id);


--
-- TOC entry 2963 (class 2606 OID 34098)
-- Name: cobro_unidad cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 2965 (class 2606 OID 34100)
-- Name: comunicados comunicados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id);


--
-- TOC entry 2967 (class 2606 OID 34102)
-- Name: concepto_gasto concepto_gasto_nom_concepto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_nom_concepto_key UNIQUE (nom_concepto);


--
-- TOC entry 2969 (class 2606 OID 34104)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 2971 (class 2606 OID 34106)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 2973 (class 2606 OID 34108)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 2975 (class 2606 OID 34110)
-- Name: cuenta cuenta_n_cuenta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_n_cuenta_key UNIQUE (n_cuenta);


--
-- TOC entry 2979 (class 2606 OID 34112)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 2977 (class 2606 OID 34114)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 2981 (class 2606 OID 34116)
-- Name: cuotas_especiales cuotas_especiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuotas_especiales
    ADD CONSTRAINT cuotas_especiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2983 (class 2606 OID 34118)
-- Name: detalle_pagos detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pagos
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 2985 (class 2606 OID 34120)
-- Name: factura_unidad detalle_total_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura_unidad
    ADD CONSTRAINT detalle_total_pkey PRIMARY KEY (id);


--
-- TOC entry 2987 (class 2606 OID 34122)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 2989 (class 2606 OID 34124)
-- Name: gasto_comun gasto_comun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto_comun
    ADD CONSTRAINT gasto_comun_pkey PRIMARY KEY (id);


--
-- TOC entry 2991 (class 2606 OID 34126)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 2993 (class 2606 OID 34128)
-- Name: pagar_cuota_especial pagar_cuota_especial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_pkey PRIMARY KEY (id);


--
-- TOC entry 3027 (class 2606 OID 34243)
-- Name: persona persona_correo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_correo_key UNIQUE (correo);


--
-- TOC entry 3029 (class 2606 OID 34239)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3031 (class 2606 OID 34241)
-- Name: persona persona_telefono_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_telefono_key UNIQUE (telefono);


--
-- TOC entry 3039 (class 2606 OID 34271)
-- Name: propietario propietario_correo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_correo_key UNIQUE (correo);


--
-- TOC entry 3041 (class 2606 OID 34267)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3043 (class 2606 OID 34269)
-- Name: propietario propietario_telefono_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_telefono_key UNIQUE (telefono);


--
-- TOC entry 2995 (class 2606 OID 34130)
-- Name: propietarios propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietarios
    ADD CONSTRAINT propietarios_pkey PRIMARY KEY (cedula);


--
-- TOC entry 2997 (class 2606 OID 34132)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 2999 (class 2606 OID 34134)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3001 (class 2606 OID 34136)
-- Name: puente_asamblea_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asamblea_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3003 (class 2606 OID 34138)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3005 (class 2606 OID 34140)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3007 (class 2606 OID 34142)
-- Name: puente_condominio_cuenta puente_condomino_cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_condominio_cuenta
    ADD CONSTRAINT puente_condomino_cuenta_pkey PRIMARY KEY (id);


--
-- TOC entry 3009 (class 2606 OID 34144)
-- Name: puente_interes_condominio puente_interes_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_interes_condominio
    ADD CONSTRAINT puente_interes_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3011 (class 2606 OID 34146)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3047 (class 2606 OID 34308)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3033 (class 2606 OID 34255)
-- Name: responsable responsable_correo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_correo_key UNIQUE (correo);


--
-- TOC entry 3035 (class 2606 OID 34251)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3037 (class 2606 OID 34253)
-- Name: responsable responsable_telefono_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_telefono_key UNIQUE (telefono);


--
-- TOC entry 3013 (class 2606 OID 34150)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3045 (class 2606 OID 34284)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3015 (class 2606 OID 34152)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (id);


--
-- TOC entry 3017 (class 2606 OID 34154)
-- Name: usuario usuario_cedula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cedula_key UNIQUE (cedula);


--
-- TOC entry 3019 (class 2606 OID 34156)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3021 (class 2606 OID 34213)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3023 (class 2606 OID 34160)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3025 (class 2606 OID 34162)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3048 (class 2606 OID 34163)
-- Name: concepto_gasto concepto_gasto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id);


--
-- TOC entry 3049 (class 2606 OID 34168)
-- Name: cuenta_pagar cuenta_pagar_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3050 (class 2606 OID 34173)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3051 (class 2606 OID 34178)
-- Name: cuenta_pagar cuenta_pagar_id_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedores(cedula);


--
-- TOC entry 3052 (class 2606 OID 34183)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_cuenta_fkey FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3053 (class 2606 OID 34188)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_cuota_e_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_cuota_e_fkey FOREIGN KEY (id_cuota_e) REFERENCES public.cuotas_especiales(id);


--
-- TOC entry 3054 (class 2606 OID 34193)
-- Name: pagar_cuota_especial pagar_cuota_especial_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagar_cuota_especial
    ADD CONSTRAINT pagar_cuota_especial_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3057 (class 2606 OID 34309)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(cedula);


--
-- TOC entry 3058 (class 2606 OID 34314)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3056 (class 2606 OID 34285)
-- Name: unidad unidad_id_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_condominio_fkey FOREIGN KEY (id_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3055 (class 2606 OID 34198)
-- Name: visita visita_ci_visitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_ci_visitante_fkey FOREIGN KEY (ci_visitante) REFERENCES public.visitante(cedula);


-- Completed on 2020-05-17 10:18:33

--
-- PostgreSQL database dump complete
--

