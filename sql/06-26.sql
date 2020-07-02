--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-06-26 10:25:21


--
-- TOC entry 295 (class 1255 OID 90636)
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
-- TOC entry 296 (class 1255 OID 90637)
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
-- TOC entry 302 (class 1255 OID 91247)
-- Name: agregar_asambleas(character varying, date, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_asambleas(nombre2 character varying, fecha2 date, descripcion2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO asambleas(nombre, descripcion, fecha) VALUES (nombre2, descripcion2, fecha2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_asambleas(nombre2 character varying, fecha2 date, descripcion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 303 (class 1255 OID 91248)
-- Name: agregar_banco(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_banco(nombre2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO banco (nombre_banco) VALUES (nombre2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_banco(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 306 (class 1255 OID 91251)
-- Name: agregar_categoria(integer, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO categoriagasto (nombre, descripcion) VALUES (nombre2, descripcion2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying) OWNER TO postgres;

--
-- TOC entry 322 (class 1255 OID 91255)
-- Name: agregar_concepto(character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_concepto(nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES (nombre2, descripcion2, id_categoria2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_concepto(nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 326 (class 1255 OID 91259)
-- Name: agregar_condominio(integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO condominio (rif, razon_social, telefono, correo_electronico)
	VALUES(rif2, razon_social2, telefono2, correo_electronico2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) OWNER TO postgres;

--
-- TOC entry 328 (class 1255 OID 91261)
-- Name: agregar_cuenta(character varying, character varying, integer, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_cuenta(n_cuenta2 character varying, tipo2 character varying, id_banco2 integer, ci_persona2 character varying, rif_condominio2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO cuenta (n_cuenta, tipo, id_banco, ci_persona, rif_condominio) VALUES (n_cuenta2, tipo2, 	id_banco2, ci_persona2, rif_condominio2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_cuenta(n_cuenta2 character varying, tipo2 character varying, id_banco2 integer, ci_persona2 character varying, rif_condominio2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 332 (class 1255 OID 91265)
-- Name: agregar_cuenta_pagar(character varying, character varying, character varying, double precision, date, double precision, integer, integer, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_cuenta_pagar(descripcion2 character varying, num_ref2 character varying, moneda2 character varying, monto2 double precision, fecha2 date, tasa_cambio2 double precision, id_gasto2 integer, id_forma_pago2 integer, n_cuenta2 character varying, id_fondo2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO cuenta_pagar (descripcion, num_ref, moneda, monto, fecha, tasa_cambio, id_gasto,
	id_forma_pago, n_cuenta, id_fondo) VALUES (descripcion2, num_ref2, moneda2, monto2, fecha2,
	tasa_cambio2, id_gasto2, id_forma_pago2, n_cuenta2, id_fondo2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_cuenta_pagar(descripcion2 character varying, num_ref2 character varying, moneda2 character varying, monto2 double precision, fecha2 date, tasa_cambio2 double precision, id_gasto2 integer, id_forma_pago2 integer, n_cuenta2 character varying, id_fondo2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 334 (class 1255 OID 91292)
-- Name: agregar_fondos(integer, character varying, date, character varying, character varying, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_fondos(id_usuario2 integer, tipo2 character varying, fecha2 date, descripcion2 character varying, observaciones2 character varying, monto_inicial2 double precision, moneda2 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda)
	VALUES (tipo2, fecha2, descripcion2, observaciones2, monto_inicial2, monto_inicial2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_fondos(id_usuario2 integer, tipo2 character varying, fecha2 date, descripcion2 character varying, observaciones2 character varying, monto_inicial2 double precision, moneda2 character varying) OWNER TO postgres;

--
-- TOC entry 337 (class 1255 OID 91297)
-- Name: agregar_forma_pago(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_forma_pago(nombre2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO forma_pago (forma_pago) VALUES (nombre2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_forma_pago(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 343 (class 1255 OID 91307)
-- Name: agregar_gasto(character varying, character varying, character varying, character varying, integer, integer, integer, integer, text, integer, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_gasto(nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, moneda2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO gasto(nombre, tipo, id_proveedor, calcular_por, mes, anio, n_meses, observacion, id_asamblea, meses_restantes, monto, saldo, moneda) VALUES (nombre2, tipo2, id_proveedor2, calcular_por2, mes2, anio2, n_meses2, observacion2, id_asamblea2, meses_restantes2, monto2, monto2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_gasto(nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 341 (class 1255 OID 91305)
-- Name: agregar_gasto(character varying, character varying, character varying, character varying, integer, integer, integer, integer, text, integer, double precision, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_gasto(nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, saldo2 double precision, moneda2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO gasto(nombre, tipo, id_proveedor, calcular_por, mes, anio, n_meses, observacion, id_asamblea, meses_restantes, monto, saldo, moneda) VALUES (nombre2, tipo2, id_proveedor2, calcular_por2, mes2, anio2, n_meses2, observacion2, id_asamblea2, meses_restantes2, monto2, saldo2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_gasto(nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, saldo2 double precision, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 346 (class 1255 OID 91313)
-- Name: agregar_interes(character varying, double precision, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_interes(nombre2 character varying, factor2 double precision, id_condominio2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO interes (nombre, factor, id_condominio) VALUES (nombre2, factor2, id_condominio2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_interes(nombre2 character varying, factor2 double precision, id_condominio2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 350 (class 1255 OID 91316)
-- Name: agregar_interes(character varying, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_interes(nombre2 character varying, factor2 double precision, id_condominio2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO interes (nombre, factor, id_condominio) VALUES (nombre2, factor2, id_condominio2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_interes(nombre2 character varying, factor2 double precision, id_condominio2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 351 (class 1255 OID 91317)
-- Name: agregar_proveedor(character varying, character varying, character varying, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO proveedores(cedula, nombre, telefono, correo, contacto, direccion) VALUES (cedula2, nombre2, telefono2, correo2, contacto2, direccion2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 355 (class 1255 OID 91323)
-- Name: agregar_sancion(character varying, integer, integer, double precision, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, estado2 character varying, moneda2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO sancion(tipo, mes, anio, monto, descripcion,  estado, moneda) VALUES (tipo2, mes2, anio2, monto2, descripcion2,  estado2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, estado2 character varying, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 358 (class 1255 OID 91328)
-- Name: agregar_tipo_unidad(character varying, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_tipo_unidad(tipo2 character varying, area2 double precision, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO tipo_unidad(tipo, area) VALUES (tipo2,area2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_tipo_unidad(tipo2 character varying, area2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 364 (class 1255 OID 91337)
-- Name: agregar_unidad(character varying, character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.agregar_unidad(n_unidad2 character varying, n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO unidad(n_unidad, n_documento, direccion, id_tipo) VALUES (n_unidad2, n_documento2, direccion2, id_tipo2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.agregar_unidad(n_unidad2 character varying, n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 297 (class 1255 OID 90638)
-- Name: cambiar_clave(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cambiar_clave(usuario2 character varying, password_nuevo character varying, password_actual character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$ 
DECLARE
	password_bd character varying;
	usuario_bd character varying;

BEGIN
		usuario_bd := (SELECT usuario FROM usuario where usuario=usuario2 AND password=password_actual);
		password_bd := (SELECT password FROM usuario where usuario=usuario2 AND password=password_actual);
	
	IF password_actual = password_bd AND usuario2 = usuario_bd THEN
		UPDATE usuario SET password = password_nuevo WHERE usuario = usuario2;
		RETURN TRUE;
	
	ELSE 
		RETURN FALSE;
		
	END IF;
	END;
$$;


ALTER FUNCTION public.cambiar_clave(usuario2 character varying, password_nuevo character varying, password_actual character varying) OWNER TO postgres;

--
-- TOC entry 298 (class 1255 OID 90639)
-- Name: cambiar_pregunta(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cambiar_pregunta(usuario2 character varying, pregunta2 character varying, respuesta2 character varying, password2 character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
	password_bd character varying;
	usuario_bd character varying;

BEGIN
	usuario_bd := (SELECT usuario FROM usuario where usuario=usuario2 AND password=password2);
	password_bd := (SELECT password FROM usuario where usuario=usuario2 AND password=password2);
	
	IF password2 = password_bd AND usuario2 = usuario_bd THEN
		UPDATE usuario SET pregunta = pregunta2, respuesta = respuesta2 WHERE usuario = usuario2;
		RETURN TRUE;
	
	ELSE 
		RETURN FALSE;
		
	END IF;
	END;
$$;


ALTER FUNCTION public.cambiar_pregunta(usuario2 character varying, pregunta2 character varying, respuesta2 character varying, password2 character varying) OWNER TO postgres;

--
-- TOC entry 305 (class 1255 OID 91250)
-- Name: eliminar_banco(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_banco(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE banco SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_banco(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 308 (class 1255 OID 91253)
-- Name: eliminar_categoria(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_categoria(id_usuario2 integer, id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE categoriagasto SET activo=false WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_categoria(id_usuario2 integer, id2 integer) OWNER TO postgres;

--
-- TOC entry 324 (class 1255 OID 91257)
-- Name: eliminar_concepto(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_concepto(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE concepto_gasto SET activo=false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_concepto(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 330 (class 1255 OID 91263)
-- Name: eliminar_cuenta(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_cuenta(n_cuenta2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE cuenta SET activo=false WHERE n_cuenta = n_cuenta2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_cuenta(n_cuenta2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 336 (class 1255 OID 91294)
-- Name: eliminar_fondos(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_fondos(id_usuario2 integer, id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE fondos SET activo=false WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_fondos(id_usuario2 integer, id2 integer) OWNER TO postgres;

--
-- TOC entry 339 (class 1255 OID 91299)
-- Name: eliminar_forma_pago(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_forma_pago(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE forma_pago SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_forma_pago(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 348 (class 1255 OID 91314)
-- Name: eliminar_interes(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_interes(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE interes SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_interes(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 353 (class 1255 OID 91319)
-- Name: eliminar_proveedor(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_proveedor(cedula2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE proveedores SET activo = false WHERE cedula = cedula2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_proveedor(cedula2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 357 (class 1255 OID 91325)
-- Name: eliminar_sancion(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_sancion(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	DELETE FROM sancion WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_sancion(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 360 (class 1255 OID 91330)
-- Name: eliminar_tipo_unidad(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_tipo_unidad(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE tipo_unidad SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_tipo_unidad(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 362 (class 1255 OID 91336)
-- Name: eliminar_unidad(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.eliminar_unidad(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE unidad SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.eliminar_unidad(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 333 (class 1255 OID 91266)
-- Name: llenar_bitacora(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.llenar_bitacora() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF TG_OP = 'INSERT' THEN
		INSERT INTO bitacora (operacion, tabla, valor_nuevo)
		VALUES ('Registro', TG_TABLE_NAME, NEW);
		RETURN NEW;
		END IF;
	IF TG_OP = 'UPDATE' THEN
		INSERT INTO bitacora (operacion, tabla, valor_viejo, valor_nuevo)
		VALUES (TG_OP, TG_TABLE_NAME, OLD, NEW);
		RETURN NEW;
	END IF;
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.llenar_bitacora() OWNER TO postgres;

--
-- TOC entry 299 (class 1255 OID 90640)
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
-- TOC entry 304 (class 1255 OID 91249)
-- Name: modificar_banco(integer, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_banco(id2 integer, nombre2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE banco SET nombre_banco = nombre2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_banco(id2 integer, nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 307 (class 1255 OID 91252)
-- Name: modificar_categoria(integer, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying, id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE categoriagasto SET nombre=nombre2, descripcion=descripcion2 WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_categoria(id_usuario2 integer, nombre2 character varying, descripcion2 character varying, id2 integer) OWNER TO postgres;

--
-- TOC entry 323 (class 1255 OID 91256)
-- Name: modificar_concepto(integer, character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_concepto(id2 integer, nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE concepto_gasto SET nom_concepto = nombre2, descripcion = descripcion2, id_categoria = id_categoria2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_concepto(id2 integer, nombre2 character varying, descripcion2 character varying, id_categoria2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 327 (class 1255 OID 91260)
-- Name: modificar_condominio(integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE condominio SET razon_social=razon_social2, telefono=telefono2, correo_electronico=correo_electronico2     WHERE rif=rif2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_condominio(id_usuario2 integer, rif2 character varying, razon_social2 character varying, telefono2 character varying, correo_electronico2 character varying) OWNER TO postgres;

--
-- TOC entry 329 (class 1255 OID 91262)
-- Name: modificar_cuenta(character varying, integer, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_cuenta(tipo2 character varying, id_banco2 integer, ci_persona2 character varying, n_cuenta2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE cuenta SET tipo=tipo2, id_banco=id_banco2, ci_persona=ci_persona2
	WHERE n_cuenta=n_cuenta2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_cuenta(tipo2 character varying, id_banco2 integer, ci_persona2 character varying, n_cuenta2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 335 (class 1255 OID 91293)
-- Name: modificar_fondos(integer, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_fondos(id_usuario2 integer, tipo2 character varying, descripcion2 character varying, observaciones2 character varying, id2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE fondos SET descripcion=descripcion2, observaciones=observaciones2, 
	tipo=tipo2 WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_fondos(id_usuario2 integer, tipo2 character varying, descripcion2 character varying, observaciones2 character varying, id2 integer) OWNER TO postgres;

--
-- TOC entry 338 (class 1255 OID 91298)
-- Name: modificar_forma_pago(integer, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_forma_pago(id2 integer, nombre2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE forma_pago SET forma_pago = nombre2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_forma_pago(id2 integer, nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 342 (class 1255 OID 91306)
-- Name: modificar_gasto(integer, character varying, character varying, character varying, character varying, integer, integer, integer, integer, text, integer, double precision, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_gasto(id2 integer, nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, saldo2 double precision, moneda2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE gasto SET nombre = nombre2, tipo = tipo2, id_proveedor = id_proveedor2, calcular_por = calcular_por2, mes = mes2, anio = anio2, n_meses = n_meses2, id_asamblea =id_asamblea2, observacion = observacion2, meses_restantes = meses_restantes2, monto = monto2, saldo = saldo2, moneda = moneda2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_gasto(id2 integer, nombre2 character varying, tipo2 character varying, id_proveedor2 character varying, calcular_por2 character varying, mes2 integer, anio2 integer, n_meses2 integer, id_asamblea2 integer, observacion2 text, meses_restantes2 integer, monto2 double precision, saldo2 double precision, moneda2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 347 (class 1255 OID 91312)
-- Name: modificar_interes(integer, character varying, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_interes(id2 integer, nombre2 character varying, factor2 double precision, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE interes SET nombre = nombre2, factor = factor2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_interes(id2 integer, nombre2 character varying, factor2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 352 (class 1255 OID 91318)
-- Name: modificar_proveedor(character varying, character varying, character varying, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE proveedores SET nombre = nombre2, telefono = telefono2, correo = correo2, contacto = contacto2, direccion = direccion2 WHERE cedula = cedula2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_proveedor(cedula2 character varying, nombre2 character varying, telefono2 character varying, correo2 character varying, contacto2 character varying, direccion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 356 (class 1255 OID 91324)
-- Name: modificar_sancion(character varying, integer, integer, double precision, character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, moneda2 character varying, id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE sancion SET tipo=tipo2, mes=mes2, anio=anio2, monto=monto2, descripcion=descripcion2, moneda=moneda2 WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_sancion(tipo2 character varying, mes2 integer, anio2 integer, monto2 double precision, descripcion2 character varying, moneda2 character varying, id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 359 (class 1255 OID 91329)
-- Name: modificar_tipo_unidad(character varying, double precision, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_tipo_unidad(tipo2 character varying, area2 double precision, id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE tipo_unidad SET tipo = tipo2, area = area2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_tipo_unidad(tipo2 character varying, area2 double precision, id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 365 (class 1255 OID 91338)
-- Name: modificar_unidad(character varying, character varying, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.modificar_unidad(n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE unidad SET n_documento = n_documento2, direccion = direccion2, id_tipo=id_tipo2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.modificar_unidad(n_documento2 character varying, direccion2 character varying, id_tipo2 integer, id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 300 (class 1255 OID 90641)
-- Name: pagar_gasto(integer, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.pagar_gasto(id2 integer, monto2 double precision) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
	saldo_bd double precision;
BEGIN
	UPDATE gasto SET saldo = saldo - monto2 WHERE id = id2;
	
	saldo_bd := (SELECT saldo FROM gasto WHERE id = id2);

	IF saldo_bd = 0 THEN
		UPDATE gasto SET pagado = 'Pagado' WHERE id = id2;
	END IF;
END;
$$;


ALTER FUNCTION public.pagar_gasto(id2 integer, monto2 double precision) OWNER TO postgres;

--
-- TOC entry 309 (class 1255 OID 91254)
-- Name: reactivar_categoria(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_categoria(nombre2 character varying, descripcion2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE categoriagasto SET descripcion = descripcion2, activo = true WHERE nombre = nombre2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_categoria(nombre2 character varying, descripcion2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 325 (class 1255 OID 91258)
-- Name: reactivar_concepto(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_concepto(nombre2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE concepto_gasto SET activo = true WHERE nom_concepto = nombre2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_concepto(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 331 (class 1255 OID 91264)
-- Name: reactivar_cuenta(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_cuenta(n_cuenta2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE cuenta SET activo = true WHERE n_cuenta = n_cuenta2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_cuenta(n_cuenta2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 340 (class 1255 OID 91300)
-- Name: reactivar_forma_pago(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_forma_pago(nombre2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE forma_pago SET activo = true WHERE forma_pago = nombre2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_forma_pago(nombre2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 349 (class 1255 OID 91315)
-- Name: reactivar_interes(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_interes(id2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE interes SET activo = true WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_interes(id2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 354 (class 1255 OID 91320)
-- Name: reactivar_proveedor(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_proveedor(cedula2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE proveedores SET activo = true WHERE cedula = cedula2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_proveedor(cedula2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 361 (class 1255 OID 91331)
-- Name: reactivar_tipo_unidad(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_tipo_unidad(id2 integer, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE tipo_unidad SET activo = true WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_tipo_unidad(id2 integer, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 363 (class 1255 OID 91332)
-- Name: reactivar_tipo_unidad(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reactivar_tipo_unidad(tipo2 character varying, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE tipo_unidad SET activo = true WHERE tipo = tipo2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.reactivar_tipo_unidad(tipo2 character varying, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 344 (class 1255 OID 91308)
-- Name: registrar_cuota(integer, integer, integer, integer, double precision, double precision, character varying, character varying, double precision, double precision, double precision, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_cuota(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO recibo (
		id_unidad, id_gasto, mes, anio, monto_dolar, monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota
	) VALUES (
		id_unidad2, id_gasto2, mes2, anio2, monto_dolar2, monto_bolivar2, tipo_gasto2, moneda_dominante2, paridad2, saldo_restante_bolivar2, saldo_restante_dolar2, alicuota2
	);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.registrar_cuota(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 345 (class 1255 OID 91309)
-- Name: registrar_interes(integer, integer, integer, integer, double precision, double precision, character varying, character varying, double precision, double precision, double precision, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_interes(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO recibo (
		id_unidad, id_gasto, mes, anio, monto_dolar, monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota
	) VALUES (
		id_unidad2, id_gasto2, mes2, anio2, monto_dolar2, monto_bolivar2, tipo_gasto2, moneda_dominante2, paridad2, saldo_restante_bolivar2, saldo_restante_dolar2, alicuota2
	);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$;


ALTER FUNCTION public.registrar_interes(id_unidad2 integer, id_gasto2 integer, mes2 integer, anio2 integer, monto_dolar2 double precision, monto_bolivar2 double precision, tipo_gasto2 character varying, moneda_dominante2 character varying, paridad2 double precision, saldo_restante_bolivar2 double precision, saldo_restante_dolar2 double precision, alicuota2 double precision, id_usuario2 integer) OWNER TO postgres;

--
-- TOC entry 301 (class 1255 OID 90642)
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
-- TOC entry 202 (class 1259 OID 90643)
-- Name: asambleas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asambleas (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion text NOT NULL
);


ALTER TABLE public.asambleas OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 90649)
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
-- TOC entry 3479 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asambleas_id_seq OWNED BY public.asambleas.id;


--
-- TOC entry 204 (class 1259 OID 90651)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco (
    id integer NOT NULL,
    nombre_banco character varying(100) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 90654)
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
-- TOC entry 3480 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banco_id_seq OWNED BY public.banco.id;


--
-- TOC entry 294 (class 1259 OID 91277)
-- Name: bitacora; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bitacora (
    id_bitacora integer NOT NULL,
    operacion text NOT NULL,
    tabla text NOT NULL,
    id_usuario integer,
    valor_viejo text,
    valor_nuevo text,
    fecha_hora timestamp without time zone DEFAULT LOCALTIMESTAMP(0)
);


ALTER TABLE public.bitacora OWNER TO postgres;

--
-- TOC entry 293 (class 1259 OID 91275)
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bitacora_id_bitacora_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bitacora_id_bitacora_seq OWNER TO postgres;

--
-- TOC entry 3481 (class 0 OID 0)
-- Dependencies: 293
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bitacora_id_bitacora_seq OWNED BY public.bitacora.id_bitacora;


--
-- TOC entry 206 (class 1259 OID 90656)
-- Name: categoriagasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoriagasto (
    id integer NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.categoriagasto OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 90660)
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
-- TOC entry 3482 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoriagasto_id_seq OWNED BY public.categoriagasto.id;


--
-- TOC entry 208 (class 1259 OID 90662)
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
    id_unidad bigint,
    moneda character varying,
    paridad double precision
);


ALTER TABLE public.cobro_unidad OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 90668)
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
-- TOC entry 3483 (class 0 OID 0)
-- Dependencies: 209
-- Name: cobro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cobro_id_seq OWNED BY public.cobro_unidad.id;


--
-- TOC entry 210 (class 1259 OID 90670)
-- Name: concepto_gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.concepto_gasto (
    id integer NOT NULL,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.concepto_gasto OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 90674)
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
-- TOC entry 3484 (class 0 OID 0)
-- Dependencies: 211
-- Name: concepto_gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.concepto_gasto_id_seq OWNED BY public.concepto_gasto.id;


--
-- TOC entry 212 (class 1259 OID 90676)
-- Name: condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.condominio (
    rif character varying(15) NOT NULL,
    razon_social character varying(150) NOT NULL,
    telefono character varying(11) NOT NULL,
    correo_electronico character varying(70) NOT NULL
);


ALTER TABLE public.condominio OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 90680)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    n_cuenta character varying(20) NOT NULL,
    tipo character varying(10) NOT NULL,
    id_banco bigint NOT NULL,
    ci_persona character varying(10) NOT NULL,
    rif_condominio character varying(15) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 90684)
-- Name: cuenta_pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta_pagar (
    id integer NOT NULL,
    num_ref character varying(10),
    descripcion character varying(60) NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    moneda character varying(10) NOT NULL,
    tasa_cambio double precision,
    id_forma_pago integer NOT NULL,
    id_gasto integer NOT NULL,
    n_cuenta character varying(20),
    id_fondo integer NOT NULL
);


ALTER TABLE public.cuenta_pagar OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 90687)
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
-- TOC entry 3485 (class 0 OID 0)
-- Dependencies: 215
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_pagar_id_seq OWNED BY public.cuenta_pagar.id;


--
-- TOC entry 216 (class 1259 OID 90689)
-- Name: recibo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recibo (
    id integer NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto_dolar double precision NOT NULL,
    id_gasto bigint,
    id_unidad bigint,
    tipo_gasto character varying,
    monto_bolivar double precision,
    paridad double precision,
    moneda_dominante character varying,
    saldo_restante_bolivar double precision,
    saldo_restante_dolar double precision,
    alicuota double precision
);


ALTER TABLE public.recibo OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 90695)
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
-- TOC entry 3486 (class 0 OID 0)
-- Dependencies: 217
-- Name: detalle_pagos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_pagos_id_seq OWNED BY public.recibo.id;


--
-- TOC entry 218 (class 1259 OID 90697)
-- Name: fondos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fondos (
    tipo character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion character varying(200) NOT NULL,
    observaciones character varying(200),
    monto_inicial double precision NOT NULL,
    saldo double precision NOT NULL,
    id integer NOT NULL,
    moneda character varying,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.fondos OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 90704)
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
-- TOC entry 3487 (class 0 OID 0)
-- Dependencies: 219
-- Name: fondos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fondos_id_seq OWNED BY public.fondos.id;


--
-- TOC entry 220 (class 1259 OID 90706)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pago (
    id integer NOT NULL,
    forma_pago character varying NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 90713)
-- Name: forma_pago_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.forma_pago_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pago_id_seq OWNER TO postgres;

--
-- TOC entry 3488 (class 0 OID 0)
-- Dependencies: 221
-- Name: forma_pago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pago_id_seq OWNED BY public.forma_pago.id;


--
-- TOC entry 222 (class 1259 OID 90715)
-- Name: funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcion (
    id integer NOT NULL,
    funcion character varying NOT NULL
);


ALTER TABLE public.funcion OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 90721)
-- Name: funcion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.funcion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.funcion_id_seq OWNER TO postgres;

--
-- TOC entry 3489 (class 0 OID 0)
-- Dependencies: 223
-- Name: funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcion_id_seq OWNED BY public.funcion.id;


--
-- TOC entry 224 (class 1259 OID 90723)
-- Name: gasto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gasto (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    id_proveedor character varying(15) NOT NULL,
    calcular_por character varying(20) NOT NULL,
    mes integer NOT NULL,
    anio integer NOT NULL,
    n_meses integer NOT NULL,
    id_asamblea integer,
    observacion text DEFAULT ''::text,
    meses_restantes integer NOT NULL,
    monto double precision NOT NULL,
    saldo double precision NOT NULL,
    estado character varying DEFAULT 'Pendiente'::character varying NOT NULL,
    pagado character varying DEFAULT 'Pendiente'::character varying NOT NULL,
    moneda character varying NOT NULL
);


ALTER TABLE public.gasto OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 90732)
-- Name: gasto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.gasto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gasto_id_seq OWNER TO postgres;

--
-- TOC entry 3490 (class 0 OID 0)
-- Dependencies: 225
-- Name: gasto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gasto_id_seq OWNED BY public.gasto.id;


--
-- TOC entry 226 (class 1259 OID 90734)
-- Name: interes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interes (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    factor double precision NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    id_condominio character varying(20)
);


ALTER TABLE public.interes OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 90737)
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
-- TOC entry 3491 (class 0 OID 0)
-- Dependencies: 227
-- Name: interes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interes_id_seq OWNED BY public.interes.id;


--
-- TOC entry 228 (class 1259 OID 90739)
-- Name: mensaje; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mensaje (
    id integer NOT NULL,
    asunto character varying(60) DEFAULT 'Sin Asunto'::character varying,
    contenido character varying(420) NOT NULL,
    emisor integer NOT NULL,
    fecha timestamp without time zone DEFAULT LOCALTIMESTAMP(0) NOT NULL
);


ALTER TABLE public.mensaje OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 90744)
-- Name: mensaje_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mensaje_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mensaje_id_seq OWNER TO postgres;

--
-- TOC entry 3492 (class 0 OID 0)
-- Dependencies: 229
-- Name: mensaje_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mensaje_id_seq OWNED BY public.mensaje.id;


--
-- TOC entry 230 (class 1259 OID 90746)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    cedula character varying(10) NOT NULL,
    p_nombre character varying(25) NOT NULL,
    s_nombre character varying(25) DEFAULT ''::character varying,
    p_apellido character varying(25) NOT NULL,
    s_apellido character varying(25) DEFAULT ''::character varying,
    telefono character varying(12),
    correo character varying(60),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 90752)
-- Name: propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.propietario (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.propietario OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 90756)
-- Name: proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proveedores (
    cedula character varying(15) NOT NULL,
    nombre character varying(40) NOT NULL,
    telefono character varying(15) NOT NULL,
    correo character varying(40) NOT NULL,
    contacto character varying(60) NOT NULL,
    direccion character varying(500) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.proveedores OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 90762)
-- Name: puente_asambleas_propietario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_asambleas_propietario (
    id integer NOT NULL,
    id_asamblea bigint NOT NULL,
    ci_propietario character varying(10) NOT NULL
);


ALTER TABLE public.puente_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 90765)
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
-- TOC entry 3493 (class 0 OID 0)
-- Dependencies: 234
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_asamblea_propietario_id_seq OWNED BY public.puente_asambleas_propietario.id;


--
-- TOC entry 235 (class 1259 OID 90767)
-- Name: puente_cobro_factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_cobro_factura (
    id integer NOT NULL,
    id_recibo bigint NOT NULL,
    id_cobro bigint NOT NULL,
    parte_monto double precision NOT NULL,
    moneda character varying
);


ALTER TABLE public.puente_cobro_factura OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 90773)
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
-- TOC entry 3494 (class 0 OID 0)
-- Dependencies: 236
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_cobro_factura_id_seq OWNED BY public.puente_cobro_factura.id;


--
-- TOC entry 237 (class 1259 OID 90775)
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
-- TOC entry 238 (class 1259 OID 90781)
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
-- TOC entry 3495 (class 0 OID 0)
-- Dependencies: 238
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_comunicado_usuario_id_seq OWNED BY public.puente_comunicado_usuario.id;


--
-- TOC entry 239 (class 1259 OID 90783)
-- Name: puente_gasto_concepto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_gasto_concepto (
    id integer NOT NULL,
    id_gasto integer,
    id_concepto integer,
    monto double precision NOT NULL
);


ALTER TABLE public.puente_gasto_concepto OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 90786)
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_gasto_concepto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_gasto_concepto_id_seq OWNER TO postgres;

--
-- TOC entry 3496 (class 0 OID 0)
-- Dependencies: 240
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_gasto_concepto_id_seq OWNED BY public.puente_gasto_concepto.id;


--
-- TOC entry 241 (class 1259 OID 90788)
-- Name: puente_mensaje_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_mensaje_usuario (
    id integer NOT NULL,
    id_mensaje integer NOT NULL,
    receptor integer NOT NULL,
    leido boolean DEFAULT false NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.puente_mensaje_usuario OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 90793)
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_mensaje_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_mensaje_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3497 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_mensaje_usuario_id_seq OWNED BY public.puente_mensaje_usuario.id;


--
-- TOC entry 243 (class 1259 OID 90795)
-- Name: puente_persona_condominio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_persona_condominio (
    id integer NOT NULL,
    ci_persona character varying(10) NOT NULL,
    rif_condominio character varying(15) NOT NULL
);


ALTER TABLE public.puente_persona_condominio OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 90798)
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_persona_condominio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_persona_condominio_id_seq OWNER TO postgres;

--
-- TOC entry 3498 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_persona_condominio_id_seq OWNED BY public.puente_persona_condominio.id;


--
-- TOC entry 245 (class 1259 OID 90800)
-- Name: puente_sancion_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_sancion_unidad (
    id bigint NOT NULL,
    id_sancion bigint NOT NULL,
    id_unidad bigint
);


ALTER TABLE public.puente_sancion_unidad OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 90803)
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
-- TOC entry 3499 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_sancion_unidad_id_seq OWNED BY public.puente_sancion_unidad.id;


--
-- TOC entry 247 (class 1259 OID 90805)
-- Name: puente_tipo_funcion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_tipo_funcion (
    id integer NOT NULL,
    id_tipo integer NOT NULL,
    id_funcion integer NOT NULL,
    registrar boolean,
    modificar boolean,
    eliminar boolean,
    todo boolean
);


ALTER TABLE public.puente_tipo_funcion OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 90808)
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puente_tipo_funcion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puente_tipo_funcion_id_seq OWNER TO postgres;

--
-- TOC entry 3500 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_tipo_funcion_id_seq OWNED BY public.puente_tipo_funcion.id;


--
-- TOC entry 249 (class 1259 OID 90810)
-- Name: puente_unidad_propietarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puente_unidad_propietarios (
    id integer NOT NULL,
    ci_propietario character varying(15) NOT NULL,
    id_unidad bigint NOT NULL,
    fecha_desde date DEFAULT LOCALTIMESTAMP(0) NOT NULL,
    fecha_hasta date,
    estado bigint NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.puente_unidad_propietarios OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 90815)
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
-- TOC entry 3501 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puente_unidad_propietarios_id_seq OWNED BY public.puente_unidad_propietarios.id;


--
-- TOC entry 251 (class 1259 OID 90817)
-- Name: responsable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsable (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.responsable OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 90821)
-- Name: sancion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sancion (
    id integer NOT NULL,
    tipo character varying(40) NOT NULL,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto double precision NOT NULL,
    descripcion character varying(200) NOT NULL,
    estado character varying(20),
    moneda character varying(10)
);


ALTER TABLE public.sancion OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 90824)
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
-- TOC entry 3502 (class 0 OID 0)
-- Dependencies: 253
-- Name: sancion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sancion_id_seq OWNED BY public.sancion.id;


--
-- TOC entry 254 (class 1259 OID 90826)
-- Name: tipo_unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_unidad (
    id integer NOT NULL,
    area double precision NOT NULL,
    tipo character varying(60),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tipo_unidad OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 90830)
-- Name: tipo_unidad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_unidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_unidad_id_seq OWNER TO postgres;

--
-- TOC entry 3503 (class 0 OID 0)
-- Dependencies: 255
-- Name: tipo_unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_unidad_id_seq OWNED BY public.tipo_unidad.id;


--
-- TOC entry 256 (class 1259 OID 90832)
-- Name: tipo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_usuario (
    id integer NOT NULL,
    tipo character varying NOT NULL
);


ALTER TABLE public.tipo_usuario OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 90838)
-- Name: tipo_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3504 (class 0 OID 0)
-- Dependencies: 257
-- Name: tipo_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_usuario_id_seq OWNED BY public.tipo_usuario.id;


--
-- TOC entry 258 (class 1259 OID 90840)
-- Name: unidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidad (
    id integer NOT NULL,
    n_unidad character varying(10) NOT NULL,
    n_documento character varying(15) NOT NULL,
    direccion character varying(200) NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    id_tipo integer NOT NULL,
    alicuota double precision
);


ALTER TABLE public.unidad OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 90844)
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
-- TOC entry 3505 (class 0 OID 0)
-- Dependencies: 259
-- Name: unidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidad_id_seq OWNED BY public.unidad.id;


--
-- TOC entry 260 (class 1259 OID 90846)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    usuario character varying(25) NOT NULL,
    password character varying(32) NOT NULL,
    pregunta character varying(120) NOT NULL,
    respuesta character varying(120) NOT NULL,
    ci_persona character varying(10),
    id_tipo_usuario integer,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 90850)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3506 (class 0 OID 0)
-- Dependencies: 261
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 262 (class 1259 OID 90852)
-- Name: v_asambleas; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_asambleas AS
 SELECT asa.id,
    asa.nombre,
    asa.descripcion,
    asa.fecha
   FROM public.asambleas asa;


ALTER TABLE public.v_asambleas OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 90856)
-- Name: v_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_propietario AS
 SELECT pro.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.propietario pro
     JOIN public.persona per ON (((per.cedula)::text = (pro.ci_persona)::text)))
  WHERE (pro.activo = true);


ALTER TABLE public.v_propietario OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 90860)
-- Name: v_asambleas_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_asambleas_propietario AS
 SELECT asa.id,
    pr.ci_persona AS cedula,
    pr.p_nombre AS nombre,
    pr.p_apellido AS apellido,
    pu.id AS id_puente
   FROM ((public.v_propietario pr
     JOIN public.puente_asambleas_propietario pu ON (((pu.ci_propietario)::text = (pr.ci_persona)::text)))
     JOIN public.asambleas asa ON ((asa.id = pu.id_asamblea)));


ALTER TABLE public.v_asambleas_propietario OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 90864)
-- Name: v_bandeja_entrada; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_bandeja_entrada AS
 SELECT me.id,
    me.asunto,
    me.contenido,
    me.fecha,
    me.emisor AS id_emisor,
    u1.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido,
    pu.receptor,
    pu.leido,
    pu.activo
   FROM ((((public.mensaje me
     JOIN public.usuario u1 ON ((u1.id = me.emisor)))
     JOIN public.puente_mensaje_usuario pu ON ((pu.id_mensaje = me.id)))
     JOIN public.usuario u2 ON ((u2.id = pu.receptor)))
     JOIN public.persona pe ON (((pe.cedula)::text = (u1.ci_persona)::text)));


ALTER TABLE public.v_bandeja_entrada OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 90869)
-- Name: v_bandeja_salida; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_bandeja_salida AS
 SELECT me.id,
    me.asunto,
    me.contenido,
    me.fecha,
    me.emisor AS id_emisor,
    u.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido
   FROM ((public.mensaje me
     JOIN public.usuario u ON ((u.id = me.emisor)))
     JOIN public.persona pe ON (((pe.cedula)::text = (u.ci_persona)::text)));


ALTER TABLE public.v_bandeja_salida OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 90874)
-- Name: v_concepto_gasto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_concepto_gasto AS
 SELECT cg.id,
    cg.nom_concepto AS nombre,
    cg.descripcion,
    cat.id AS id_categoria,
    cat.nombre AS nombre_categoria,
    cg.activo
   FROM (public.concepto_gasto cg
     JOIN public.categoriagasto cat ON ((cat.id = cg.id_categoria)));


ALTER TABLE public.v_concepto_gasto OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 91241)
-- Name: v_condominio; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_condominio AS
 SELECT condominio.rif,
    condominio.razon_social,
    condominio.telefono,
    condominio.correo_electronico AS correo
   FROM public.condominio;


ALTER TABLE public.v_condominio OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 90882)
-- Name: v_cuenta; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta AS
 SELECT cue.n_cuenta,
    cue.tipo,
    cue.id_banco,
    ban.nombre_banco AS banco,
    cue.ci_persona,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido,
    cue.rif_condominio,
    cue.activo
   FROM ((public.cuenta cue
     JOIN public.banco ban ON ((ban.id = cue.id_banco)))
     JOIN public.persona per ON (((per.cedula)::text = (cue.ci_persona)::text)))
  WHERE (cue.activo = true);


ALTER TABLE public.v_cuenta OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 90887)
-- Name: v_cuenta_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta_inactivo AS
 SELECT cue.n_cuenta,
    cue.tipo,
    cue.id_banco,
    ban.nombre_banco AS banco,
    cue.ci_persona,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido,
    cue.rif_condominio,
    cue.activo
   FROM ((public.cuenta cue
     JOIN public.banco ban ON ((ban.id = cue.id_banco)))
     JOIN public.persona per ON (((per.cedula)::text = (cue.ci_persona)::text)))
  WHERE (cue.activo = false);


ALTER TABLE public.v_cuenta_inactivo OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 90892)
-- Name: v_cuenta_pagar; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_cuenta_pagar AS
 SELECT cp.id,
    cp.num_ref,
    cp.descripcion,
    cp.monto,
    cp.moneda,
    cp.tasa_cambio,
    cp.fecha,
    cp.id_gasto,
    ga.nombre AS gasto,
    cp.n_cuenta,
    cu.id_banco,
    b.nombre_banco AS banco,
    cp.id_fondo,
    f.tipo AS fondo,
    cp.id_forma_pago,
    fp.forma_pago
   FROM (((((public.cuenta_pagar cp
     JOIN public.gasto ga ON ((ga.id = cp.id_gasto)))
     JOIN public.cuenta cu ON (((cu.n_cuenta)::text = (cp.n_cuenta)::text)))
     JOIN public.banco b ON ((b.id = cu.id_banco)))
     JOIN public.fondos f ON ((f.id = cp.id_fondo)))
     JOIN public.forma_pago fp ON ((fp.id = cp.id_forma_pago)))
  ORDER BY cp.fecha DESC;


ALTER TABLE public.v_cuenta_pagar OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 90897)
-- Name: v_dueno_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_dueno_unidad AS
 SELECT prop.ci_persona,
    puente.id,
    puente.id_unidad,
    puente.fecha_hasta
   FROM (public.propietario prop
     LEFT JOIN public.puente_unidad_propietarios puente ON (((prop.ci_persona)::text = (puente.ci_propietario)::text)))
  WHERE ((prop.activo = true) AND (puente.fecha_hasta IS NULL));


ALTER TABLE public.v_dueno_unidad OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 90901)
-- Name: v_gasto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_gasto AS
 SELECT ga.id,
    ga.tipo,
    ga.nombre,
    ga.id_proveedor,
    ga.moneda,
    pr.nombre AS proveedor,
    ga.calcular_por,
    ga.mes,
    ga.anio,
    ga.monto,
    ga.saldo,
    ga.n_meses,
    ga.meses_restantes,
    asa.id AS id_asamblea,
    asa.nombre AS asamblea,
    asa.fecha AS fecha_asamblea,
    ga.observacion,
    ga.estado,
    ga.pagado
   FROM ((public.gasto ga
     JOIN public.proveedores pr ON (((pr.cedula)::text = (ga.id_proveedor)::text)))
     LEFT JOIN public.asambleas asa ON ((asa.id = ga.id_asamblea)));


ALTER TABLE public.v_gasto OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 90906)
-- Name: v_gasto_concepto; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_gasto_concepto AS
 SELECT ga.id AS id_gasto,
    cg.id AS id_concepto,
    cg.nombre,
    pu.monto,
    pu.id AS id_puente
   FROM ((public.v_concepto_gasto cg
     JOIN public.puente_gasto_concepto pu ON ((pu.id_concepto = cg.id)))
     JOIN public.gasto ga ON ((ga.id = pu.id_gasto)));


ALTER TABLE public.v_gasto_concepto OWNER TO postgres;

--
-- TOC entry 274 (class 1259 OID 90911)
-- Name: v_mensaje_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_mensaje_usuario AS
 SELECT pu.id_mensaje,
    pu.receptor AS id_receptor,
    u.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido
   FROM ((public.puente_mensaje_usuario pu
     JOIN public.usuario u ON ((u.id = pu.receptor)))
     JOIN public.persona pe ON (((pe.cedula)::text = (u.ci_persona)::text)));


ALTER TABLE public.v_mensaje_usuario OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 90916)
-- Name: v_perfil; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_perfil AS
 SELECT per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo,
    usu.id,
    usu.usuario,
    usu.pregunta,
    tip.id AS id_tipo,
    tip.tipo
   FROM ((public.persona per
     JOIN public.usuario usu ON (((usu.ci_persona)::text = (per.cedula)::text)))
     JOIN public.tipo_usuario tip ON ((tip.id = usu.id_tipo_usuario)));


ALTER TABLE public.v_perfil OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 90921)
-- Name: v_permisos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_permisos AS
 SELECT u.usuario,
    tipo.id AS id_tipo,
    tipo.tipo,
    puente.id_funcion,
    f.funcion,
    puente.registrar,
    puente.modificar,
    puente.eliminar,
    puente.todo
   FROM (((public.puente_tipo_funcion puente
     JOIN public.tipo_usuario tipo ON ((tipo.id = puente.id_tipo)))
     JOIN public.funcion f ON ((f.id = puente.id_funcion)))
     JOIN public.usuario u ON ((u.id_tipo_usuario = puente.id_tipo)));


ALTER TABLE public.v_permisos OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 90926)
-- Name: v_propietario_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_propietario_inactivo AS
 SELECT pro.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.propietario pro
     JOIN public.persona per ON (((per.cedula)::text = (pro.ci_persona)::text)))
  WHERE (pro.activo = false);


ALTER TABLE public.v_propietario_inactivo OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 90930)
-- Name: v_responsable; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_responsable AS
 SELECT r.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.responsable r
     JOIN public.persona per ON (((per.cedula)::text = (r.ci_persona)::text)))
  WHERE (r.activo = true);


ALTER TABLE public.v_responsable OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 90934)
-- Name: v_responsable_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_responsable_inactivo AS
 SELECT r.ci_persona,
    per.p_nombre,
    per.s_nombre,
    per.p_apellido,
    per.s_apellido,
    per.telefono,
    per.correo
   FROM (public.responsable r
     JOIN public.persona per ON (((per.cedula)::text = (r.ci_persona)::text)))
  WHERE (r.activo = false);


ALTER TABLE public.v_responsable_inactivo OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 90938)
-- Name: v_tipo_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_unidad AS
 SELECT tu.id,
    tu.tipo,
    tu.area
   FROM public.tipo_unidad tu
  WHERE (tu.activo = true);


ALTER TABLE public.v_tipo_unidad OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 90942)
-- Name: v_tipo_unidad_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_tipo_unidad_inactivo AS
 SELECT tu.id,
    tu.tipo,
    tu.area
   FROM public.tipo_unidad tu
  WHERE (tu.activo = false);


ALTER TABLE public.v_tipo_unidad_inactivo OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 90946)
-- Name: v_unidad; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad AS
 SELECT u.id,
    u.n_unidad,
    u.n_documento,
    u.direccion,
    u.alicuota,
    tu.id AS id_tipo,
    tu.tipo,
    tu.area
   FROM (public.unidad u
     JOIN public.tipo_unidad tu ON ((tu.id = u.id_tipo)))
  WHERE (u.activo = true);


ALTER TABLE public.v_unidad OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 90950)
-- Name: v_unidad_propietario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidad_propietario AS
 SELECT u.id,
    pro.ci_persona,
    pro.p_nombre,
    pro.s_nombre,
    pro.p_apellido,
    pro.s_apellido,
    pro.telefono,
    pro.correo,
    puente.id AS id_puente,
    puente.fecha_desde,
    puente.fecha_hasta,
    puente.estado
   FROM ((public.v_propietario pro
     JOIN public.puente_unidad_propietarios puente ON (((puente.ci_propietario)::text = (pro.ci_persona)::text)))
     JOIN public.unidad u ON ((u.id = puente.id_unidad)))
  WHERE ((u.activo = true) AND (puente.estado = 1));


ALTER TABLE public.v_unidad_propietario OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 90955)
-- Name: v_unidades_inactivas; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_unidades_inactivas AS
 SELECT unidad.id,
    unidad.n_unidad,
    unidad.n_documento,
    unidad.direccion
   FROM public.unidad
  WHERE (unidad.activo = false);


ALTER TABLE public.v_unidades_inactivas OWNER TO postgres;

--
-- TOC entry 285 (class 1259 OID 90959)
-- Name: v_usuario; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_usuario AS
 SELECT u.id,
    u.usuario,
    u.ci_persona AS cedula,
    pe.p_nombre AS nombre,
    pe.p_apellido AS apellido
   FROM (public.usuario u
     JOIN public.persona pe ON (((pe.cedula)::text = (u.ci_persona)::text)))
  WHERE (u.activo = true);


ALTER TABLE public.v_usuario OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 90963)
-- Name: v_usuario_inactivo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_usuario_inactivo AS
 SELECT usuario.id,
    usuario.usuario,
    usuario.ci_persona
   FROM public.usuario
  WHERE (usuario.activo = false);


ALTER TABLE public.v_usuario_inactivo OWNER TO postgres;

--
-- TOC entry 287 (class 1259 OID 90967)
-- Name: visita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visita (
    id integer NOT NULL,
    fecha date DEFAULT LOCALTIMESTAMP(0),
    hora time without time zone DEFAULT LOCALTIMESTAMP(0),
    placa character varying(8),
    modelo character varying(25),
    color character varying(15),
    ci_visitante character varying(10) NOT NULL,
    id_unidad integer NOT NULL
);


ALTER TABLE public.visita OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 90972)
-- Name: visitante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visitante (
    ci_persona character varying(10) NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.visitante OWNER TO postgres;

--
-- TOC entry 289 (class 1259 OID 90976)
-- Name: v_visita; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_visita AS
 SELECT vis.id,
    vis.id_unidad,
    u.n_unidad,
    vis.fecha,
    vis.hora,
    vis.placa AS matricula,
    vis.modelo,
    vis.color,
    vis.ci_visitante AS cedula,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido
   FROM (((public.visita vis
     JOIN public.unidad u ON ((u.id = vis.id_unidad)))
     JOIN public.visitante v ON (((v.ci_persona)::text = (vis.ci_visitante)::text)))
     JOIN public.persona per ON (((per.cedula)::text = (v.ci_persona)::text)));


ALTER TABLE public.v_visita OWNER TO postgres;

--
-- TOC entry 290 (class 1259 OID 90981)
-- Name: v_visitante; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_visitante AS
 SELECT v.ci_persona AS cedula,
    per.p_nombre AS nombre,
    per.p_apellido AS apellido
   FROM (public.visitante v
     JOIN public.persona per ON (((per.cedula)::text = (v.ci_persona)::text)))
  WHERE (v.activo = true);


ALTER TABLE public.v_visitante OWNER TO postgres;

--
-- TOC entry 291 (class 1259 OID 90985)
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
-- TOC entry 3507 (class 0 OID 0)
-- Dependencies: 291
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visita_id_seq OWNED BY public.visita.id;


--
-- TOC entry 3066 (class 2604 OID 90987)
-- Name: asambleas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas ALTER COLUMN id SET DEFAULT nextval('public.asambleas_id_seq'::regclass);


--
-- TOC entry 3067 (class 2604 OID 90988)
-- Name: banco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco ALTER COLUMN id SET DEFAULT nextval('public.banco_id_seq'::regclass);


--
-- TOC entry 3122 (class 2604 OID 91280)
-- Name: bitacora id_bitacora; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora ALTER COLUMN id_bitacora SET DEFAULT nextval('public.bitacora_id_bitacora_seq'::regclass);


--
-- TOC entry 3070 (class 2604 OID 90989)
-- Name: categoriagasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto ALTER COLUMN id SET DEFAULT nextval('public.categoriagasto_id_seq'::regclass);


--
-- TOC entry 3071 (class 2604 OID 90990)
-- Name: cobro_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad ALTER COLUMN id SET DEFAULT nextval('public.cobro_id_seq'::regclass);


--
-- TOC entry 3073 (class 2604 OID 90991)
-- Name: concepto_gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto ALTER COLUMN id SET DEFAULT nextval('public.concepto_gasto_id_seq'::regclass);


--
-- TOC entry 3075 (class 2604 OID 90992)
-- Name: cuenta_pagar id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar ALTER COLUMN id SET DEFAULT nextval('public.cuenta_pagar_id_seq'::regclass);


--
-- TOC entry 3078 (class 2604 OID 90993)
-- Name: fondos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos ALTER COLUMN id SET DEFAULT nextval('public.fondos_id_seq'::regclass);


--
-- TOC entry 3080 (class 2604 OID 90994)
-- Name: forma_pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago ALTER COLUMN id SET DEFAULT nextval('public.forma_pago_id_seq'::regclass);


--
-- TOC entry 3081 (class 2604 OID 90995)
-- Name: funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion ALTER COLUMN id SET DEFAULT nextval('public.funcion_id_seq'::regclass);


--
-- TOC entry 3085 (class 2604 OID 90996)
-- Name: gasto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto ALTER COLUMN id SET DEFAULT nextval('public.gasto_id_seq'::regclass);


--
-- TOC entry 3086 (class 2604 OID 90997)
-- Name: interes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes ALTER COLUMN id SET DEFAULT nextval('public.interes_id_seq'::regclass);


--
-- TOC entry 3090 (class 2604 OID 90998)
-- Name: mensaje id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje ALTER COLUMN id SET DEFAULT nextval('public.mensaje_id_seq'::regclass);


--
-- TOC entry 3096 (class 2604 OID 90999)
-- Name: puente_asambleas_propietario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario ALTER COLUMN id SET DEFAULT nextval('public.puente_asamblea_propietario_id_seq'::regclass);


--
-- TOC entry 3097 (class 2604 OID 91000)
-- Name: puente_cobro_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura ALTER COLUMN id SET DEFAULT nextval('public.puente_cobro_factura_id_seq'::regclass);


--
-- TOC entry 3098 (class 2604 OID 91001)
-- Name: puente_comunicado_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_comunicado_usuario_id_seq'::regclass);


--
-- TOC entry 3099 (class 2604 OID 91002)
-- Name: puente_gasto_concepto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto ALTER COLUMN id SET DEFAULT nextval('public.puente_gasto_concepto_id_seq'::regclass);


--
-- TOC entry 3102 (class 2604 OID 91003)
-- Name: puente_mensaje_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario ALTER COLUMN id SET DEFAULT nextval('public.puente_mensaje_usuario_id_seq'::regclass);


--
-- TOC entry 3103 (class 2604 OID 91004)
-- Name: puente_persona_condominio id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio ALTER COLUMN id SET DEFAULT nextval('public.puente_persona_condominio_id_seq'::regclass);


--
-- TOC entry 3104 (class 2604 OID 91005)
-- Name: puente_sancion_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad ALTER COLUMN id SET DEFAULT nextval('public.puente_sancion_unidad_id_seq'::regclass);


--
-- TOC entry 3105 (class 2604 OID 91006)
-- Name: puente_tipo_funcion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion ALTER COLUMN id SET DEFAULT nextval('public.puente_tipo_funcion_id_seq'::regclass);


--
-- TOC entry 3108 (class 2604 OID 91007)
-- Name: puente_unidad_propietarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios ALTER COLUMN id SET DEFAULT nextval('public.puente_unidad_propietarios_id_seq'::regclass);


--
-- TOC entry 3076 (class 2604 OID 91008)
-- Name: recibo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo ALTER COLUMN id SET DEFAULT nextval('public.detalle_pagos_id_seq'::regclass);


--
-- TOC entry 3110 (class 2604 OID 91009)
-- Name: sancion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion ALTER COLUMN id SET DEFAULT nextval('public.sancion_id_seq'::regclass);


--
-- TOC entry 3112 (class 2604 OID 91010)
-- Name: tipo_unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad ALTER COLUMN id SET DEFAULT nextval('public.tipo_unidad_id_seq'::regclass);


--
-- TOC entry 3113 (class 2604 OID 91011)
-- Name: tipo_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario ALTER COLUMN id SET DEFAULT nextval('public.tipo_usuario_id_seq'::regclass);


--
-- TOC entry 3115 (class 2604 OID 91012)
-- Name: unidad id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad ALTER COLUMN id SET DEFAULT nextval('public.unidad_id_seq'::regclass);


--
-- TOC entry 3117 (class 2604 OID 91013)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3120 (class 2604 OID 91014)
-- Name: visita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita ALTER COLUMN id SET DEFAULT nextval('public.visita_id_seq'::regclass);


--
-- TOC entry 3409 (class 0 OID 90643)
-- Dependencies: 202
-- Data for Name: asambleas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.asambleas VALUES (86, 'Agua y gas', '2020-06-11', 'nadanadanada');


--
-- TOC entry 3411 (class 0 OID 90651)
-- Dependencies: 204
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banco VALUES (4, 'Provincial', true);
INSERT INTO public.banco VALUES (6, 'Venezuela', true);
INSERT INTO public.banco VALUES (1, 'Banesco', true);
INSERT INTO public.banco VALUES (5, 'Mercantil', true);
INSERT INTO public.banco VALUES (7, 'BOD', true);
INSERT INTO public.banco VALUES (8, 'Fondo Comun', true);
INSERT INTO public.banco VALUES (10, 'Fuerzas Armadas', true);
INSERT INTO public.banco VALUES (11, 'Venezolana de Credito', true);
INSERT INTO public.banco VALUES (9, 'Banplus', true);
INSERT INTO public.banco VALUES (3, 'Caribe', true);


--
-- TOC entry 3473 (class 0 OID 91277)
-- Dependencies: 294
-- Data for Name: bitacora; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bitacora VALUES (64, 'Registro', 'unidad', 1, NULL, '(13,2525,1234,"calle maple",t,1,)', '2020-06-26 10:13:00');
INSERT INTO public.bitacora VALUES (65, 'UPDATE', 'unidad', NULL, '(10,25,2524,jkl,t,1,0.5)', '(10,25,2524,jkl,t,1,0.3333333432674408)', '2020-06-26 10:13:03');
INSERT INTO public.bitacora VALUES (66, 'UPDATE', 'unidad', NULL, '(11,jkjl,jlj,jljkl,t,1,0.5)', '(11,jkjl,jlj,jljkl,t,1,0.3333333432674408)', '2020-06-26 10:13:04');
INSERT INTO public.bitacora VALUES (67, 'UPDATE', 'unidad', NULL, '(13,2525,1234,"calle maple",t,1,)', '(13,2525,1234,"calle maple",t,1,0.3333333432674408)', '2020-06-26 10:13:04');
INSERT INTO public.bitacora VALUES (68, 'Modificado', 'unidad', 1, '(13,2525,1234,"calle maple",t,1,0.3333333432674408)', '(13,2525,1234,"calle maple",t,3,0.3333333432674408)', '2020-06-26 10:14:18');
INSERT INTO public.bitacora VALUES (69, 'UPDATE', 'unidad', NULL, '(10,25,2524,jkl,t,1,0.3333333432674408)', '(10,25,2524,jkl,t,1,0.12121212482452393)', '2020-06-26 10:14:20');
INSERT INTO public.bitacora VALUES (70, 'UPDATE', 'unidad', NULL, '(11,jkjl,jlj,jljkl,t,1,0.3333333432674408)', '(11,jkjl,jlj,jljkl,t,1,0.12121212482452393)', '2020-06-26 10:14:20');
INSERT INTO public.bitacora VALUES (71, 'UPDATE', 'unidad', NULL, '(13,2525,1234,"calle maple",t,3,0.3333333432674408)', '(13,2525,1234,"calle maple",t,3,0.7575757503509521)', '2020-06-26 10:14:20');
INSERT INTO public.bitacora VALUES (72, 'Registro', 'unidad', 1, NULL, '(14,963,789,jola,t,5,)', '2020-06-26 10:14:47');
INSERT INTO public.bitacora VALUES (73, 'UPDATE', 'unidad', NULL, '(10,25,2524,jkl,t,1,0.12121212482452393)', '(10,25,2524,jkl,t,1,0.10526315867900848)', '2020-06-26 10:14:48');
INSERT INTO public.bitacora VALUES (74, 'UPDATE', 'unidad', NULL, '(11,jkjl,jlj,jljkl,t,1,0.12121212482452393)', '(11,jkjl,jlj,jljkl,t,1,0.10526315867900848)', '2020-06-26 10:14:48');
INSERT INTO public.bitacora VALUES (75, 'UPDATE', 'unidad', NULL, '(13,2525,1234,"calle maple",t,3,0.7575757503509521)', '(13,2525,1234,"calle maple",t,3,0.6578947305679321)', '2020-06-26 10:14:49');
INSERT INTO public.bitacora VALUES (76, 'UPDATE', 'unidad', NULL, '(14,963,789,jola,t,5,)', '(14,963,789,jola,t,5,0.1315789520740509)', '2020-06-26 10:14:49');
INSERT INTO public.bitacora VALUES (77, 'UPDATE', 'tipo_unidad', NULL, '(6,512,putas,t)', '(6,512,bonito,t)', '2020-06-26 10:24:39');


--
-- TOC entry 3413 (class 0 OID 90656)
-- Dependencies: 206
-- Data for Name: categoriagasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoriagasto VALUES (4, 'Uso comun', 'Conjunto de conceptos de uso comun y consumo', true);
INSERT INTO public.categoriagasto VALUES (6, 'asda', 'ddasda', true);
INSERT INTO public.categoriagasto VALUES (5, 'fsfsfdsfs', 'Conjunto de conceptos administrativo', true);
INSERT INTO public.categoriagasto VALUES (7, 'baba', 'baba', true);


--
-- TOC entry 3415 (class 0 OID 90662)
-- Dependencies: 208
-- Data for Name: cobro_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cobro_unidad VALUES (80, 800000, 'fdsfsdf', '44444444444444444444', 'Transferencia', 'sfdsfds', '2020-06-12', 57, 11, 'Bolívar', 200000);
INSERT INTO public.cobro_unidad VALUES (81, 17000, 'fdsfsdf', '44444444444444444444', 'Transferencia', 'sfdsfds', '2020-06-12', 57, 11, 'Bolívar', 200000);
INSERT INTO public.cobro_unidad VALUES (82, 13, 'vxvcx', '44444444444444444444', 'Efectivo', 'xcvx', '2020-06-06', 58, 10, 'Dólar', 100000);
INSERT INTO public.cobro_unidad VALUES (83, 130000, 'vxvcx', '44444444444444444444', 'Efectivo', 'xcvx', '2020-06-06', 57, 10, 'Bolívar', 100000);
INSERT INTO public.cobro_unidad VALUES (84, 7250, 'vxvcx', '44444444444444444444', 'Efectivo', 'xcvx', '2020-06-06', 57, 10, 'Bolívar', 100000);


--
-- TOC entry 3417 (class 0 OID 90670)
-- Dependencies: 210
-- Data for Name: concepto_gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.concepto_gasto VALUES (40, 'HidroCapital', 'Consumo de agua', 4, true);
INSERT INTO public.concepto_gasto VALUES (41, 'Vigilancia', 'Gastos por vigilancia', 4, true);
INSERT INTO public.concepto_gasto VALUES (39, 'Cantv', 'sad', 4, true);
INSERT INTO public.concepto_gasto VALUES (11, 'Provisión utilidades ', 'Utilidades del trabajador', 5, true);
INSERT INTO public.concepto_gasto VALUES (10, 'Liquidación ', 'Liquidacion trabajador', 5, true);
INSERT INTO public.concepto_gasto VALUES (12, 'Provisión vacaciones ', 'Vacaciones trabajador', 5, true);
INSERT INTO public.concepto_gasto VALUES (13, 'Ley de alimentación', 'Pago alimentación', 5, true);
INSERT INTO public.concepto_gasto VALUES (14, 'FAHO', 'Fondo de ahorro obligatorio de la vivienda', 5, true);
INSERT INTO public.concepto_gasto VALUES (15, 'Caja chica', 'Caja chica', 5, true);
INSERT INTO public.concepto_gasto VALUES (3, 'Honorarios administradora', 'Honorarios administradora del condominio', 5, true);
INSERT INTO public.concepto_gasto VALUES (4, 'Gastos oficina', 'Gastos en material de oficina', 5, true);
INSERT INTO public.concepto_gasto VALUES (9, 'Bono Vacacional ', 'Bono vacacional trabajador', 5, true);
INSERT INTO public.concepto_gasto VALUES (7, 'Prestaciones soc.', 'Prestaciones sociales trabajador', 5, true);
INSERT INTO public.concepto_gasto VALUES (8, 'Seguro social', 'Seguro social trabajador', 5, true);
INSERT INTO public.concepto_gasto VALUES (5, 'Gastos varios', 'Gastos fotocopias, impresiones, internet', 5, true);
INSERT INTO public.concepto_gasto VALUES (6, 'Retiro prestaciones', 'Retiro de prestaciones sociales', 5, true);
INSERT INTO public.concepto_gasto VALUES (16, 'Mant. ascensores', 'Mantenimiento de los asecensores', 6, true);
INSERT INTO public.concepto_gasto VALUES (17, 'Mant. hidroneumatico', 'Mantenimiento del hidroneumatico', 6, true);
INSERT INTO public.concepto_gasto VALUES (18, 'Mant. porton electrico', 'Mantenimiento del porton electrico', 6, true);
INSERT INTO public.concepto_gasto VALUES (19, 'Mant. cerco electrico', 'Mantenimiento del cerco electrico', 6, true);
INSERT INTO public.concepto_gasto VALUES (20, 'Mant. areas verdes', 'Mantenimiento de areas verdes', 6, true);
INSERT INTO public.concepto_gasto VALUES (21, 'Mant. piscina', 'Mantenimiento de la piscina', 6, true);
INSERT INTO public.concepto_gasto VALUES (22, 'Mant. parque infantil', 'Mantenimiento del parque infantil', 6, true);
INSERT INTO public.concepto_gasto VALUES (23, 'Mant. camaras vigilancia', 'Mantenimiento camaras de vigilancia', 6, true);
INSERT INTO public.concepto_gasto VALUES (24, 'Mant. tuberias', 'Mantenimiento de tuberias ', 6, true);
INSERT INTO public.concepto_gasto VALUES (25, 'Mant. estacionamiento', 'Mantenimiento del estacionamiento ', 6, true);
INSERT INTO public.concepto_gasto VALUES (26, 'Mant. pintura edif. ', 'Pintura de areas comunes ', 6, true);
INSERT INTO public.concepto_gasto VALUES (27, 'Mant. alumbrado elect. ', 'Mantenimiento del alumbrado elect.', 6, true);
INSERT INTO public.concepto_gasto VALUES (28, 'Mant. compra prod. limp.', 'Gastos productos de limpieza', 6, true);
INSERT INTO public.concepto_gasto VALUES (29, 'Mant. prod. limpieza ', 'Gasto otros productos', 6, true);
INSERT INTO public.concepto_gasto VALUES (30, 'Repar. ascensores', 'Reparacion de los asecensores', 6, true);
INSERT INTO public.concepto_gasto VALUES (31, 'Repar. tuberia', 'Reparacion de tuberias de aguas', 6, true);
INSERT INTO public.concepto_gasto VALUES (32, 'Repar. porton', 'Reparacion del porton electrico', 6, true);
INSERT INTO public.concepto_gasto VALUES (33, 'Repar. cerco elect.', 'Reparacion del cerco electrico', 6, true);
INSERT INTO public.concepto_gasto VALUES (34, 'Repar. hidroneumatico.', 'Reparacion la bomba de agua', 6, true);
INSERT INTO public.concepto_gasto VALUES (35, 'Repar. camaras.', 'Reparacion de camaras', 6, true);
INSERT INTO public.concepto_gasto VALUES (36, 'Repar. electricas.', 'Reparacion/Sustitucion de componentes electricos', 6, true);
INSERT INTO public.concepto_gasto VALUES (37, 'Consumo de electricidad', 'Consumo de electricidad', 6, true);
INSERT INTO public.concepto_gasto VALUES (38, 'jkkjkjk', 'jkjkjkjkjk', 4, true);
INSERT INTO public.concepto_gasto VALUES (42, 'qw', 'qw', 4, true);
INSERT INTO public.concepto_gasto VALUES (43, 'baba', 'baba', 7, true);
INSERT INTO public.concepto_gasto VALUES (44, 'bobo', 'bobo', 7, true);


--
-- TOC entry 3419 (class 0 OID 90676)
-- Dependencies: 212
-- Data for Name: condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.condominio VALUES ('J22318939', 'CAMPO GUATACARO', '04140542620', 'AJHENSUAREZ@GMAIL.COM');


--
-- TOC entry 3420 (class 0 OID 90680)
-- Dependencies: 213
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta VALUES ('44444444444444444444', 'Corriente', 5, 'V-11276626', 'J22318939', true);


--
-- TOC entry 3421 (class 0 OID 90684)
-- Dependencies: 214
-- Data for Name: cuenta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta_pagar VALUES (5, '2121', 'pago 1', 85.5, '2020-06-07', 'Dólar', NULL, 4, 6, '44444444444444444444', 61);
INSERT INTO public.cuenta_pagar VALUES (6, '2122', 'pago 2', 4.5, '2020-06-07', 'Dólar', NULL, 4, 6, '44444444444444444444', 61);


--
-- TOC entry 3425 (class 0 OID 90697)
-- Dependencies: 218
-- Data for Name: fondos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fondos VALUES ('Bolívares', '2020-06-04', 'lolo', 'lolo', 14000000, 14000000, 62, 'Bolívar', true);
INSERT INTO public.fondos VALUES ('Dólares', '2020-06-01', 'whatever', 'nothing', 1400, 1310, 61, 'Dólar', true);


--
-- TOC entry 3427 (class 0 OID 90706)
-- Dependencies: 220
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.forma_pago VALUES (1, 'dsfsdf', false);
INSERT INTO public.forma_pago VALUES (2, 'td', false);
INSERT INTO public.forma_pago VALUES (3, 'Efectivo', true);
INSERT INTO public.forma_pago VALUES (4, 'Transferencia', true);
INSERT INTO public.forma_pago VALUES (5, 'Depósito', true);
INSERT INTO public.forma_pago VALUES (6, 'Cheque', true);
INSERT INTO public.forma_pago VALUES (7, 'hhjj', false);
INSERT INTO public.forma_pago VALUES (8, 'uuii', false);


--
-- TOC entry 3429 (class 0 OID 90715)
-- Dependencies: 222
-- Data for Name: funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.funcion VALUES (1, 'Asambleas');
INSERT INTO public.funcion VALUES (2, 'Banco');
INSERT INTO public.funcion VALUES (3, 'Categoria Gastos');
INSERT INTO public.funcion VALUES (4, 'Concepto Gastos');
INSERT INTO public.funcion VALUES (5, 'Comunicados');
INSERT INTO public.funcion VALUES (6, 'Condominio');
INSERT INTO public.funcion VALUES (7, 'Cuenta');
INSERT INTO public.funcion VALUES (8, 'Cuentas por cobrar');
INSERT INTO public.funcion VALUES (9, 'Cuentas por pagar');
INSERT INTO public.funcion VALUES (10, 'Fondo');
INSERT INTO public.funcion VALUES (11, 'Gasto');
INSERT INTO public.funcion VALUES (12, 'Generar recibo');
INSERT INTO public.funcion VALUES (13, 'Gestionar Usuario');
INSERT INTO public.funcion VALUES (14, 'Intereses');
INSERT INTO public.funcion VALUES (16, 'Propietarios');
INSERT INTO public.funcion VALUES (17, 'Proveedores');
INSERT INTO public.funcion VALUES (18, 'Recibo');
INSERT INTO public.funcion VALUES (19, 'Responsables');
INSERT INTO public.funcion VALUES (20, 'Sanciones');
INSERT INTO public.funcion VALUES (21, 'Tipo de unidad');
INSERT INTO public.funcion VALUES (22, 'Tipo de usuario');
INSERT INTO public.funcion VALUES (23, 'Unidades');
INSERT INTO public.funcion VALUES (24, 'Registro de visitas');
INSERT INTO public.funcion VALUES (25, 'Visitas autorizadas');
INSERT INTO public.funcion VALUES (28, 'Forma de pago');


--
-- TOC entry 3431 (class 0 OID 90723)
-- Dependencies: 224
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gasto VALUES (9, 'opop', 'Extraordinario', '17102635', 'Alicuota', 6, 2021, 7, NULL, 'opop', 7, 80, 80, 'Pendiente', 'Pendiente', 'Dólar');
INSERT INTO public.gasto VALUES (6, 'Reparación tuberías', 'Extraordinario', '24666587', 'Total de Inmuebles', 8, 2020, 4, 86, 'obs', 4, 100, 100, 'Pendiente', 'Pagado', 'Dólar');


--
-- TOC entry 3433 (class 0 OID 90734)
-- Dependencies: 226
-- Data for Name: interes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interes VALUES (26, 'reserva', 30, true, NULL);
INSERT INTO public.interes VALUES (27, 'inflacion', 30, true, NULL);
INSERT INTO public.interes VALUES (28, 'momoj', 40, false, 'J22318939');
INSERT INTO public.interes VALUES (29, 'momoj', 12, false, 'J22318939');


--
-- TOC entry 3435 (class 0 OID 90739)
-- Dependencies: 228
-- Data for Name: mensaje; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.mensaje VALUES (1, 'asunto1', 'prueba leido', 1, '2020-06-11 19:05:39');
INSERT INTO public.mensaje VALUES (2, 'asunto2', 'prueba no leido', 1, '2020-06-11 19:05:53');
INSERT INTO public.mensaje VALUES (3, 'guebo', 'sdfsf', 1, '2020-06-11 23:05:58');


--
-- TOC entry 3437 (class 0 OID 90746)
-- Dependencies: 230
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.persona VALUES ('V-11276626', 'Dana', '', 'Montilla', '', '99999999999', '77777', true);
INSERT INTO public.persona VALUES ('V-22318939', 'anthony', 'jhen', 'suarez', 'leal', '04145371749', 'ajhen', true);
INSERT INTO public.persona VALUES ('V-22318938', 'jhosbert', 'angelys', 'suarez', 'rodriguez', '04145371744', 'jhos', true);
INSERT INTO public.persona VALUES ('V-7552887', 'hilda', 'carmen', 'suarez', 'rodriguez', '04141414141', 'sdadasd', true);
INSERT INTO public.persona VALUES ('V-9602345', 'jenny', 'coromoto', 'leal', 'gil', '31231455423', 'asdadadd', true);
INSERT INTO public.persona VALUES ('V-9602344', 'yaneth', 'margarita', 'leal', 'leal', '96855242224', 'dsdadgggccc', true);
INSERT INTO public.persona VALUES ('V-16111353', 'johil', 'aisbel', 'suarez', 'rodriguez', '14257595854', 'johil', true);
INSERT INTO public.persona VALUES ('V-23654789', 'dsfsdf', NULL, 'sdf', NULL, NULL, NULL, true);
INSERT INTO public.persona VALUES ('V-14523698', 'cghc', NULL, 'ghcg', NULL, NULL, NULL, true);
INSERT INTO public.persona VALUES ('V-12365478', 'vhn', NULL, 'gv', NULL, NULL, NULL, true);
INSERT INTO public.persona VALUES ('V-32547896', 'hgjgh', 'ghfgh', 'fgjhf', 'fhfgh', '66666666666', 'fhh', true);
INSERT INTO public.persona VALUES ('V-12345678', 'admin', '', 'istrador', '', NULL, NULL, true);
INSERT INTO public.persona VALUES ('V-26942316', 'Diego', '', 'Rodríguez', 'mendoza', '04120520962', 'diegordgz8@outlook.com', true);


--
-- TOC entry 3438 (class 0 OID 90752)
-- Dependencies: 231
-- Data for Name: propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.propietario VALUES ('V-7552887', true);
INSERT INTO public.propietario VALUES ('V-9602345', true);
INSERT INTO public.propietario VALUES ('V-9602344', true);
INSERT INTO public.propietario VALUES ('V-26942316', true);


--
-- TOC entry 3439 (class 0 OID 90756)
-- Dependencies: 232
-- Data for Name: proveedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedores VALUES ('J-1001245215', 'Ferreteria Todo', '02542458796', 'Ferreteriatodo@gmail.com', 'Señor José', 'Av. 4, centro comercial aris, local 3', true);
INSERT INTO public.proveedores VALUES ('J-2457021456', 'Carpinteria Madera Blanca', '0254217965', 'CarpinteriaMB@gmail.com', 'Señora Luisa', '8 Av, esquina de la calle 12', true);
INSERT INTO public.proveedores VALUES ('J-2457903215', 'Cristaleria Azul', '0254789635', 'Cristaleria_Azul@hotmail.com', 'Señor Armando', 'Calle 3 con esquina 7', true);
INSERT INTO public.proveedores VALUES ('24666587', 'Hermanos Fontaneros', '04245886335', 'Hfontaneros@gmail.com', 'Señor Juan', '30 Av. entre calle 3 y 4  ', true);
INSERT INTO public.proveedores VALUES ('17102635', 'Servicios de Limpieza', '04165048963', 'Luisa1@hotmail.com', 'Señora Luisa', '-', true);
INSERT INTO public.proveedores VALUES ('J-54785696', 'Libreria la Rosa', '04125789654', 'Libreria_laRosa@hotmail.com', 'Juan', 'Calle 2', true);
INSERT INTO public.proveedores VALUES ('12457896', 'Reparacion de Ascensor', '0414157896', '-', 'Julio', 'Calle 6', true);
INSERT INTO public.proveedores VALUES ('J-215479658', 'Reparacion Electrica', '04141544400', '-', 'Alvaro', 'Calle 14', true);
INSERT INTO public.proveedores VALUES ('20111045', 'Reparacion de Porton', '04167247578', '-', 'Julian', 'Calle 12', true);
INSERT INTO public.proveedores VALUES ('J-547859655', 'Reparacion de Camaras', '04162488965', 'RC@hotmail.com', 'Jose', 'Calle 34', true);
INSERT INTO public.proveedores VALUES ('10000000', 'qwerty', '00004444444', 'fgfgfgfg', 'fgfgfgfg', 'fgfgfgfg', false);
INSERT INTO public.proveedores VALUES ('11111111', 'tyty', '41201111111', 'v', 'hjhj', 'cv', false);


--
-- TOC entry 3440 (class 0 OID 90762)
-- Dependencies: 233
-- Data for Name: puente_asambleas_propietario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_asambleas_propietario VALUES (269, 86, 'V-26942316');
INSERT INTO public.puente_asambleas_propietario VALUES (270, 86, 'V-7552887');
INSERT INTO public.puente_asambleas_propietario VALUES (271, 86, 'V-9602345');
INSERT INTO public.puente_asambleas_propietario VALUES (272, 86, 'V-9602344');


--
-- TOC entry 3442 (class 0 OID 90767)
-- Dependencies: 235
-- Data for Name: puente_cobro_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_cobro_factura VALUES (15, 1585, 80, 5000, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (16, 1587, 80, 500000, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (17, 1590, 80, 1000, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (18, 1592, 80, 151500, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (19, 1594, 80, 142500, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (24, 1594, 81, 9000, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (25, 1596, 81, 5000, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (26, 1598, 81, 1500, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (27, 1600, 81, 1500, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (28, 1595, 82, 0.05, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (29, 1597, 82, 0.015, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (30, 1599, 82, 0.015, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (31, 1591, 82, 0.7575, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (32, 1593, 82, 0.7575, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (33, 1584, 82, 0.025, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (34, 1586, 82, 2.5, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (35, 1588, 82, 0.2525, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (36, 1589, 82, 8.6275, 'Dólar');
INSERT INTO public.puente_cobro_factura VALUES (37, 1589, 83, 130000, 'Bolívar');
INSERT INTO public.puente_cobro_factura VALUES (38, 1589, 84, 7250, 'Bolívar');


--
-- TOC entry 3444 (class 0 OID 90775)
-- Dependencies: 237
-- Data for Name: puente_comunicado_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3446 (class 0 OID 90783)
-- Dependencies: 239
-- Data for Name: puente_gasto_concepto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_gasto_concepto VALUES (8, 6, 40, 90);
INSERT INTO public.puente_gasto_concepto VALUES (13, 9, 40, 70);
INSERT INTO public.puente_gasto_concepto VALUES (14, 9, 41, 10);
INSERT INTO public.puente_gasto_concepto VALUES (15, 6, 41, 10);


--
-- TOC entry 3448 (class 0 OID 90788)
-- Dependencies: 241
-- Data for Name: puente_mensaje_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_mensaje_usuario VALUES (2, 2, 9, false, true);
INSERT INTO public.puente_mensaje_usuario VALUES (1, 1, 9, true, true);
INSERT INTO public.puente_mensaje_usuario VALUES (3, 3, 9, false, true);


--
-- TOC entry 3450 (class 0 OID 90795)
-- Dependencies: 243
-- Data for Name: puente_persona_condominio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3452 (class 0 OID 90800)
-- Dependencies: 245
-- Data for Name: puente_sancion_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_sancion_unidad VALUES (215, 92, 10);
INSERT INTO public.puente_sancion_unidad VALUES (216, 93, 10);
INSERT INTO public.puente_sancion_unidad VALUES (217, 94, 11);
INSERT INTO public.puente_sancion_unidad VALUES (223, 97, 10);


--
-- TOC entry 3454 (class 0 OID 90805)
-- Dependencies: 247
-- Data for Name: puente_tipo_funcion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_tipo_funcion VALUES (39, 1, 1, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (40, 1, 2, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (41, 1, 3, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (42, 1, 4, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (43, 1, 5, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (44, 1, 6, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (45, 1, 7, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (46, 1, 8, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (47, 1, 9, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (48, 1, 10, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (49, 1, 11, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (50, 1, 12, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (51, 1, 13, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (52, 1, 14, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (54, 1, 16, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (55, 1, 17, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (56, 1, 18, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (57, 1, 19, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (58, 1, 20, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (59, 1, 21, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (60, 1, 22, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (61, 1, 23, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (62, 1, 24, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (63, 1, 25, true, true, true, true);
INSERT INTO public.puente_tipo_funcion VALUES (66, 1, 28, true, true, true, true);


--
-- TOC entry 3456 (class 0 OID 90810)
-- Dependencies: 249
-- Data for Name: puente_unidad_propietarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.puente_unidad_propietarios VALUES (28, 'V-26942316', 10, '2020-06-11', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (29, 'V-26942316', 11, '2020-06-11', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (31, 'V-9602345', 12, '2020-06-26', '2020-06-26', 0, true);
INSERT INTO public.puente_unidad_propietarios VALUES (32, 'V-9602345', 12, '2020-06-26', '2020-06-26', 0, true);
INSERT INTO public.puente_unidad_propietarios VALUES (30, 'V-7552887', 12, '2020-06-26', '2020-06-26', 0, true);
INSERT INTO public.puente_unidad_propietarios VALUES (33, 'V-7552887', 13, '2020-06-26', NULL, 1, true);
INSERT INTO public.puente_unidad_propietarios VALUES (34, 'V-7552887', 14, '2020-06-26', NULL, 1, true);


--
-- TOC entry 3423 (class 0 OID 90689)
-- Dependencies: 216
-- Data for Name: recibo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.recibo VALUES (1593, 6, 2020, 0.7575, 27, 10, 'Interes', 151500, 200000, 'Dólar', 151500, 0, NULL);
INSERT INTO public.recibo VALUES (1584, 6, 2020, 0.025, 21, 10, 'Ordinario', 5000, 200000, 'Dólar', 5000, 0, NULL);
INSERT INTO public.recibo VALUES (1586, 6, 2020, 2.5, 20, 10, 'Extraordinario', 500000, 200000, 'Dólar', 500000, 0, NULL);
INSERT INTO public.recibo VALUES (1588, 6, 2020, 0.2525, 92, 10, 'Sancion', 50500, 200000, 'Dólar', 50500, 0, NULL);
INSERT INTO public.recibo VALUES (1589, 6, 2020, 10, 93, 10, 'Sancion', 2000000, 200000, 'Dólar', 2000000, 0, NULL);
INSERT INTO public.recibo VALUES (1591, 6, 2020, 0.7575, 26, 10, 'Interes', 151500, 200000, 'Dólar', 151500, 0, NULL);
INSERT INTO public.recibo VALUES (1601, 4, 2020, 10, 23, 10, 'Ordinario', 2000000, 200000, 'Dólar', 2000000, 10, NULL);
INSERT INTO public.recibo VALUES (1602, 4, 2020, 10, 23, 11, 'Ordinario', 2000000, 200000, 'Dólar', 2000000, 10, NULL);
INSERT INTO public.recibo VALUES (1603, 4, 2020, 3, 26, 10, 'Interes', 600000, 200000, 'Dólar', 600000, 3, NULL);
INSERT INTO public.recibo VALUES (1604, 4, 2020, 3, 26, 11, 'Interes', 600000, 200000, 'Dólar', 600000, 3, NULL);
INSERT INTO public.recibo VALUES (1605, 4, 2020, 3, 27, 10, 'Interes', 600000, 200000, 'Dólar', 600000, 3, NULL);
INSERT INTO public.recibo VALUES (1606, 4, 2020, 3, 27, 11, 'Interes', 600000, 200000, 'Dólar', 600000, 3, NULL);
INSERT INTO public.recibo VALUES (1585, 6, 2020, 0.025, 21, 11, 'Ordinario', 5000, 200000, 'Dólar', 5000, 0, NULL);
INSERT INTO public.recibo VALUES (1587, 6, 2020, 2.5, 20, 11, 'Extraordinario', 500000, 200000, 'Dólar', 500000, 0, NULL);
INSERT INTO public.recibo VALUES (1590, 6, 2020, 0.005, 94, 11, 'Sancion', 1000, 200000, 'Dólar', 1000, 0, NULL);
INSERT INTO public.recibo VALUES (1592, 6, 2020, 0.7575, 26, 11, 'Interes', 151500, 200000, 'Dólar', 151500, 0, NULL);
INSERT INTO public.recibo VALUES (1594, 6, 2020, 0.7575, 27, 11, 'Interes', 151500, 200000, 'Dólar', 151500, 0, NULL);
INSERT INTO public.recibo VALUES (1596, 5, 2020, 0.027777777777777776, 22, 11, 'Ordinario', 5000, 180000, 'Bolívar', 0, 0.027777777777777776, NULL);
INSERT INTO public.recibo VALUES (1598, 5, 2020, 0.008333333333333333, 26, 11, 'Interes', 1500, 180000, 'Bolívar', 0, 0.008333333333333333, NULL);
INSERT INTO public.recibo VALUES (1600, 5, 2020, 0.008333333333333333, 27, 11, 'Interes', 1500, 180000, 'Bolívar', 0, 0.008333333333333333, NULL);
INSERT INTO public.recibo VALUES (1595, 5, 2020, 0.027777777777777776, 22, 10, 'Ordinario', 5000, 180000, 'Bolívar', 0, 0.027777777777777776, NULL);
INSERT INTO public.recibo VALUES (1597, 5, 2020, 0.008333333333333333, 26, 10, 'Interes', 1500, 180000, 'Bolívar', 0, 0.008333333333333333, NULL);
INSERT INTO public.recibo VALUES (1599, 5, 2020, 0.008333333333333333, 27, 10, 'Interes', 1500, 180000, 'Bolívar', 0, 0.008333333333333333, NULL);
INSERT INTO public.recibo VALUES (1607, 1, 2020, 7.5, 1, 10, 'Ordinario', 150000, 20000, 'Dólar', 150000, 7.5, 0);
INSERT INTO public.recibo VALUES (1608, 1, 2020, 7.5, 1, 11, 'Ordinario', 150000, 20000, 'Dólar', 150000, 7.5, 0);
INSERT INTO public.recibo VALUES (1609, 1, 2020, 17.5, 2, 10, 'Ordinario', 350000, 20000, 'Dólar', 350000, 17.5, 0);
INSERT INTO public.recibo VALUES (1610, 1, 2020, 17.5, 2, 11, 'Ordinario', 350000, 20000, 'Dólar', 350000, 17.5, 0);
INSERT INTO public.recibo VALUES (1611, 1, 2020, 12.5, 3, 10, 'Ordinario', 250000, 20000, 'Dólar', 250000, 12.5, 0);
INSERT INTO public.recibo VALUES (1612, 1, 2020, 12.5, 3, 11, 'Ordinario', 250000, 20000, 'Dólar', 250000, 12.5, 0);
INSERT INTO public.recibo VALUES (1613, 1, 2020, 11.25, 26, 10, 'Interes', 225000, 20000, 'Dólar', 225000, 11.25, 0);
INSERT INTO public.recibo VALUES (1614, 1, 2020, 11.25, 26, 11, 'Interes', 225000, 20000, 'Dólar', 225000, 11.25, 0);
INSERT INTO public.recibo VALUES (1615, 1, 2020, 11.25, 27, 10, 'Interes', 225000, 20000, 'Dólar', 225000, 11.25, 0);
INSERT INTO public.recibo VALUES (1616, 1, 2020, 11.25, 27, 11, 'Interes', 225000, 20000, 'Dólar', 225000, 11.25, 0);
INSERT INTO public.recibo VALUES (1617, 2, 2020, 21, 4, 10, 'Ordinario', 210000, 10000, 'Dólar', 210000, 21, 0.5);
INSERT INTO public.recibo VALUES (1618, 2, 2020, 21, 4, 11, 'Ordinario', 210000, 10000, 'Dólar', 210000, 21, 0.5);
INSERT INTO public.recibo VALUES (1619, 2, 2020, 6.3, 26, 10, 'Interes', 63000, 10000, 'Dólar', 63000, 6.3, 0.5);
INSERT INTO public.recibo VALUES (1620, 2, 2020, 6.3, 26, 11, 'Interes', 63000, 10000, 'Dólar', 63000, 6.3, 0.5);
INSERT INTO public.recibo VALUES (1621, 2, 2020, 6.3, 27, 10, 'Interes', 63000, 10000, 'Dólar', 63000, 6.3, 0.5);
INSERT INTO public.recibo VALUES (1622, 2, 2020, 6.3, 27, 11, 'Interes', 63000, 10000, 'Dólar', 63000, 6.3, 0.5);


--
-- TOC entry 3458 (class 0 OID 90817)
-- Dependencies: 251
-- Data for Name: responsable; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.responsable VALUES ('V-22318938', false);
INSERT INTO public.responsable VALUES ('V-16111353', true);
INSERT INTO public.responsable VALUES ('V-32547896', true);


--
-- TOC entry 3459 (class 0 OID 90821)
-- Dependencies: 252
-- Data for Name: sancion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sancion VALUES (92, 'Interes de mora', 6, 2020, 10, 'ghjg', 'Procesado', 'Bolívar');
INSERT INTO public.sancion VALUES (93, 'Multa', 6, 2020, 10, 'ghjg', 'Procesado', 'Dólar');
INSERT INTO public.sancion VALUES (94, 'Multa', 6, 2020, 1000, 'ghjg', 'Procesado', 'Bolívar');
INSERT INTO public.sancion VALUES (97, 'Multa', 7, 2020, 999999900, '200', 'Pendiente', 'Dólar');


--
-- TOC entry 3461 (class 0 OID 90826)
-- Dependencies: 254
-- Data for Name: tipo_unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_unidad VALUES (1, 80, 'Esquina', true);
INSERT INTO public.tipo_unidad VALUES (3, 500, 'Mediano', true);
INSERT INTO public.tipo_unidad VALUES (5, 100, 'Pequeño', true);
INSERT INTO public.tipo_unidad VALUES (2, 85, 'No esquina', true);
INSERT INTO public.tipo_unidad VALUES (6, 512, 'bonito', true);


--
-- TOC entry 3463 (class 0 OID 90832)
-- Dependencies: 256
-- Data for Name: tipo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_usuario VALUES (1, 'Administrador');


--
-- TOC entry 3465 (class 0 OID 90840)
-- Dependencies: 258
-- Data for Name: unidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidad VALUES (12, '234', '36363', 'clear positivo
', false, 3, 0.7575757503509521);
INSERT INTO public.unidad VALUES (10, '25', '2524', 'jkl', true, 1, 0.10526315867900848);
INSERT INTO public.unidad VALUES (11, 'jkjl', 'jlj', 'jljkl', true, 1, 0.10526315867900848);
INSERT INTO public.unidad VALUES (13, '2525', '1234', 'calle maple', true, 3, 0.6578947305679321);
INSERT INTO public.unidad VALUES (14, '963', '789', 'jola', true, 5, 0.1315789520740509);


--
-- TOC entry 3467 (class 0 OID 90846)
-- Dependencies: 260
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (1, 'Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 'V-12345678', 1, true);
INSERT INTO public.usuario VALUES (9, 'ajhen', '455831477b82574f6bf871193f2f761d', 'question', 'a363b8d13575101a0226e8d0d054f2e7', 'V-22318939', 1, true);


--
-- TOC entry 3469 (class 0 OID 90967)
-- Dependencies: 287
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3470 (class 0 OID 90972)
-- Dependencies: 288
-- Data for Name: visitante; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.visitante VALUES ('V-26942316', true);
INSERT INTO public.visitante VALUES ('V-11276626', true);
INSERT INTO public.visitante VALUES ('V-23654789', true);
INSERT INTO public.visitante VALUES ('V-14523698', true);
INSERT INTO public.visitante VALUES ('V-12365478', true);


--
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 203
-- Name: asambleas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asambleas_id_seq', 86, true);


--
-- TOC entry 3509 (class 0 OID 0)
-- Dependencies: 205
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banco_id_seq', 1, false);


--
-- TOC entry 3510 (class 0 OID 0)
-- Dependencies: 293
-- Name: bitacora_id_bitacora_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bitacora_id_bitacora_seq', 77, true);


--
-- TOC entry 3511 (class 0 OID 0)
-- Dependencies: 207
-- Name: categoriagasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoriagasto_id_seq', 7, true);


--
-- TOC entry 3512 (class 0 OID 0)
-- Dependencies: 209
-- Name: cobro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cobro_id_seq', 84, true);


--
-- TOC entry 3513 (class 0 OID 0)
-- Dependencies: 211
-- Name: concepto_gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.concepto_gasto_id_seq', 44, true);


--
-- TOC entry 3514 (class 0 OID 0)
-- Dependencies: 215
-- Name: cuenta_pagar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_pagar_id_seq', 6, true);


--
-- TOC entry 3515 (class 0 OID 0)
-- Dependencies: 217
-- Name: detalle_pagos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_pagos_id_seq', 1622, true);


--
-- TOC entry 3516 (class 0 OID 0)
-- Dependencies: 219
-- Name: fondos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fondos_id_seq', 62, true);


--
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 221
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pago_id_seq', 8, true);


--
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 223
-- Name: funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcion_id_seq', 28, true);


--
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 225
-- Name: gasto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gasto_id_seq', 9, true);


--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 227
-- Name: interes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interes_id_seq', 29, true);


--
-- TOC entry 3521 (class 0 OID 0)
-- Dependencies: 229
-- Name: mensaje_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mensaje_id_seq', 3, true);


--
-- TOC entry 3522 (class 0 OID 0)
-- Dependencies: 234
-- Name: puente_asamblea_propietario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_asamblea_propietario_id_seq', 272, true);


--
-- TOC entry 3523 (class 0 OID 0)
-- Dependencies: 236
-- Name: puente_cobro_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_cobro_factura_id_seq', 38, true);


--
-- TOC entry 3524 (class 0 OID 0)
-- Dependencies: 238
-- Name: puente_comunicado_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_comunicado_usuario_id_seq', 503, true);


--
-- TOC entry 3525 (class 0 OID 0)
-- Dependencies: 240
-- Name: puente_gasto_concepto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_gasto_concepto_id_seq', 15, true);


--
-- TOC entry 3526 (class 0 OID 0)
-- Dependencies: 242
-- Name: puente_mensaje_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_mensaje_usuario_id_seq', 3, true);


--
-- TOC entry 3527 (class 0 OID 0)
-- Dependencies: 244
-- Name: puente_persona_condominio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_persona_condominio_id_seq', 2, true);


--
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 246
-- Name: puente_sancion_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_sancion_unidad_id_seq', 223, true);


--
-- TOC entry 3529 (class 0 OID 0)
-- Dependencies: 248
-- Name: puente_tipo_funcion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_tipo_funcion_id_seq', 66, true);


--
-- TOC entry 3530 (class 0 OID 0)
-- Dependencies: 250
-- Name: puente_unidad_propietarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.puente_unidad_propietarios_id_seq', 34, true);


--
-- TOC entry 3531 (class 0 OID 0)
-- Dependencies: 253
-- Name: sancion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sancion_id_seq', 97, true);


--
-- TOC entry 3532 (class 0 OID 0)
-- Dependencies: 255
-- Name: tipo_unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_unidad_id_seq', 6, true);


--
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 257
-- Name: tipo_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_usuario_id_seq', 7, true);


--
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 259
-- Name: unidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidad_id_seq', 14, true);


--
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 261
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 9, true);


--
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 291
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visita_id_seq', 56, true);


--
-- TOC entry 3125 (class 2606 OID 91016)
-- Name: asambleas asambleas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asambleas
    ADD CONSTRAINT asambleas_pkey PRIMARY KEY (id);


--
-- TOC entry 3127 (class 2606 OID 91018)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 91286)
-- Name: bitacora bitacora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_pkey PRIMARY KEY (id_bitacora);


--
-- TOC entry 3129 (class 2606 OID 91020)
-- Name: categoriagasto categoriagasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoriagasto
    ADD CONSTRAINT categoriagasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3131 (class 2606 OID 91022)
-- Name: cobro_unidad cobro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cobro_unidad
    ADD CONSTRAINT cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 3133 (class 2606 OID 91024)
-- Name: concepto_gasto concepto_gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT concepto_gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3135 (class 2606 OID 91026)
-- Name: condominio condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_pkey PRIMARY KEY (rif);


--
-- TOC entry 3137 (class 2606 OID 91028)
-- Name: condominio condominio_rif_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condominio
    ADD CONSTRAINT condominio_rif_key UNIQUE (rif);


--
-- TOC entry 3141 (class 2606 OID 91030)
-- Name: cuenta_pagar cuenta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3139 (class 2606 OID 91032)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (n_cuenta);


--
-- TOC entry 3143 (class 2606 OID 91034)
-- Name: recibo detalle_pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recibo
    ADD CONSTRAINT detalle_pagos_pkey PRIMARY KEY (id);


--
-- TOC entry 3145 (class 2606 OID 91036)
-- Name: fondos fondos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fondos
    ADD CONSTRAINT fondos_pkey PRIMARY KEY (id);


--
-- TOC entry 3147 (class 2606 OID 91038)
-- Name: forma_pago forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id);


--
-- TOC entry 3149 (class 2606 OID 91040)
-- Name: funcion funcion_funcion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_funcion_key UNIQUE (funcion);


--
-- TOC entry 3151 (class 2606 OID 91042)
-- Name: funcion funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcion
    ADD CONSTRAINT funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3153 (class 2606 OID 91044)
-- Name: gasto gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);


--
-- TOC entry 3155 (class 2606 OID 91046)
-- Name: interes interes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interes
    ADD CONSTRAINT interes_pkey PRIMARY KEY (id);


--
-- TOC entry 3157 (class 2606 OID 91048)
-- Name: mensaje mensaje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (id);


--
-- TOC entry 3159 (class 2606 OID 91050)
-- Name: persona persona_correo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_correo_key UNIQUE (correo);


--
-- TOC entry 3161 (class 2606 OID 91052)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3163 (class 2606 OID 91054)
-- Name: persona persona_telefono_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_telefono_key UNIQUE (telefono);


--
-- TOC entry 3165 (class 2606 OID 91056)
-- Name: propietario propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3167 (class 2606 OID 91058)
-- Name: proveedores proveedores_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_nombre_key UNIQUE (nombre);


--
-- TOC entry 3169 (class 2606 OID 91060)
-- Name: proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedores
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY (cedula);


--
-- TOC entry 3171 (class 2606 OID 91062)
-- Name: puente_asambleas_propietario puente_asamblea_propietario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT puente_asamblea_propietario_pkey PRIMARY KEY (id);


--
-- TOC entry 3173 (class 2606 OID 91064)
-- Name: puente_cobro_factura puente_cobro_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_cobro_factura
    ADD CONSTRAINT puente_cobro_factura_pkey PRIMARY KEY (id);


--
-- TOC entry 3175 (class 2606 OID 91066)
-- Name: puente_comunicado_usuario puente_comunicado_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_comunicado_usuario
    ADD CONSTRAINT puente_comunicado_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3177 (class 2606 OID 91068)
-- Name: puente_gasto_concepto puente_gasto_concepto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_pkey PRIMARY KEY (id);


--
-- TOC entry 3179 (class 2606 OID 91070)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3181 (class 2606 OID 91072)
-- Name: puente_persona_condominio puente_persona_condominio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_pkey PRIMARY KEY (id);


--
-- TOC entry 3183 (class 2606 OID 91074)
-- Name: puente_sancion_unidad puente_sancion_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_sancion_unidad
    ADD CONSTRAINT puente_sancion_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3185 (class 2606 OID 91076)
-- Name: puente_tipo_funcion puente_tipo_funcion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_pkey PRIMARY KEY (id);


--
-- TOC entry 3187 (class 2606 OID 91078)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3189 (class 2606 OID 91080)
-- Name: responsable responsable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3191 (class 2606 OID 91082)
-- Name: sancion sancion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sancion
    ADD CONSTRAINT sancion_pkey PRIMARY KEY (id);


--
-- TOC entry 3193 (class 2606 OID 91084)
-- Name: tipo_unidad tipo_unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_unidad
    ADD CONSTRAINT tipo_unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 91086)
-- Name: tipo_usuario tipo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 91088)
-- Name: tipo_usuario tipo_usuario_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_tipo_key UNIQUE (tipo);


--
-- TOC entry 3199 (class 2606 OID 91090)
-- Name: unidad unidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 91092)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3203 (class 2606 OID 91094)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 3205 (class 2606 OID 91096)
-- Name: visita visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- TOC entry 3207 (class 2606 OID 91098)
-- Name: visitante visitante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_pkey PRIMARY KEY (ci_persona);


--
-- TOC entry 3239 (class 2620 OID 91267)
-- Name: asambleas tg_asamblea; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_asamblea BEFORE INSERT ON public.asambleas FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3240 (class 2620 OID 91268)
-- Name: banco tg_banco; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_banco BEFORE INSERT OR UPDATE ON public.banco FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3241 (class 2620 OID 91269)
-- Name: categoriagasto tg_categoria_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_categoria_gasto BEFORE INSERT OR UPDATE ON public.categoriagasto FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3242 (class 2620 OID 91270)
-- Name: concepto_gasto tg_concepto_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_concepto_gasto BEFORE INSERT OR UPDATE ON public.concepto_gasto FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3243 (class 2620 OID 91271)
-- Name: condominio tg_condominio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_condominio BEFORE INSERT OR UPDATE ON public.condominio FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3244 (class 2620 OID 91272)
-- Name: cuenta tg_cuenta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta BEFORE INSERT OR UPDATE ON public.cuenta FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3245 (class 2620 OID 91273)
-- Name: cuenta_pagar tg_cuenta_pagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_cuenta_pagar BEFORE INSERT OR UPDATE ON public.cuenta_pagar FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3247 (class 2620 OID 91274)
-- Name: fondos tg_fondos; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_fondos BEFORE INSERT OR UPDATE ON public.fondos FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3248 (class 2620 OID 91301)
-- Name: forma_pago tg_forma_pago; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_forma_pago BEFORE INSERT OR UPDATE ON public.forma_pago FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3249 (class 2620 OID 91302)
-- Name: gasto tg_gasto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_gasto BEFORE INSERT OR UPDATE ON public.gasto FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3250 (class 2620 OID 91310)
-- Name: interes tg_interes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_interes BEFORE INSERT OR UPDATE ON public.interes FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3251 (class 2620 OID 91321)
-- Name: proveedores tg_proveedores; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_proveedores BEFORE INSERT OR UPDATE ON public.proveedores FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3246 (class 2620 OID 91311)
-- Name: recibo tg_recibo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_recibo BEFORE INSERT OR UPDATE ON public.recibo FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3252 (class 2620 OID 91326)
-- Name: sancion tg_sancion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_sancion BEFORE INSERT OR UPDATE ON public.sancion FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3253 (class 2620 OID 91327)
-- Name: tipo_unidad tg_tipo_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_tipo_unidad BEFORE INSERT OR UPDATE ON public.tipo_unidad FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3254 (class 2620 OID 91333)
-- Name: unidad tg_unidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tg_unidad BEFORE INSERT OR UPDATE ON public.unidad FOR EACH ROW EXECUTE FUNCTION public.llenar_bitacora();


--
-- TOC entry 3221 (class 2606 OID 91099)
-- Name: puente_asambleas_propietario asambleas_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_asambleas_propietario
    ADD CONSTRAINT asambleas_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona) NOT VALID;


--
-- TOC entry 3238 (class 2606 OID 91287)
-- Name: bitacora bitacora_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT bitacora_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3211 (class 2606 OID 91104)
-- Name: cuenta cuenta_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3212 (class 2606 OID 91109)
-- Name: cuenta cuenta_id_banco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_id_banco_fkey FOREIGN KEY (id_banco) REFERENCES public.banco(id);


--
-- TOC entry 3214 (class 2606 OID 91114)
-- Name: cuenta_pagar cuenta_pagar_id_fondo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_fondo_fkey FOREIGN KEY (id_fondo) REFERENCES public.fondos(id);


--
-- TOC entry 3215 (class 2606 OID 91119)
-- Name: cuenta_pagar cuenta_pagar_id_forma_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_forma_pago_fkey FOREIGN KEY (id_forma_pago) REFERENCES public.forma_pago(id);


--
-- TOC entry 3216 (class 2606 OID 91124)
-- Name: cuenta_pagar cuenta_pagar_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.gasto(id);


--
-- TOC entry 3217 (class 2606 OID 91129)
-- Name: cuenta_pagar cuenta_pagar_n_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta_pagar
    ADD CONSTRAINT cuenta_pagar_n_cuenta_fkey FOREIGN KEY (n_cuenta) REFERENCES public.cuenta(n_cuenta);


--
-- TOC entry 3213 (class 2606 OID 91134)
-- Name: cuenta cuenta_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3218 (class 2606 OID 91139)
-- Name: gasto gasto_id_asamblea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gasto
    ADD CONSTRAINT gasto_id_asamblea_fkey FOREIGN KEY (id_asamblea) REFERENCES public.asambleas(id);


--
-- TOC entry 3210 (class 2606 OID 91144)
-- Name: concepto_gasto id_categoria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.concepto_gasto
    ADD CONSTRAINT id_categoria FOREIGN KEY (id_categoria) REFERENCES public.categoriagasto(id) NOT VALID;


--
-- TOC entry 3219 (class 2606 OID 91149)
-- Name: mensaje mensaje_emisor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensaje
    ADD CONSTRAINT mensaje_emisor_fkey FOREIGN KEY (emisor) REFERENCES public.usuario(id);


--
-- TOC entry 3220 (class 2606 OID 91154)
-- Name: propietario propietario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.propietario
    ADD CONSTRAINT propietario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3222 (class 2606 OID 91159)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_concepto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_concepto_fkey FOREIGN KEY (id_concepto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3223 (class 2606 OID 91164)
-- Name: puente_gasto_concepto puente_gasto_concepto_id_gasto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_gasto_concepto
    ADD CONSTRAINT puente_gasto_concepto_id_gasto_fkey FOREIGN KEY (id_gasto) REFERENCES public.concepto_gasto(id);


--
-- TOC entry 3224 (class 2606 OID 91169)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_id_mensaje_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_id_mensaje_fkey FOREIGN KEY (id_mensaje) REFERENCES public.mensaje(id);


--
-- TOC entry 3225 (class 2606 OID 91174)
-- Name: puente_mensaje_usuario puente_mensaje_usuario_receptor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_mensaje_usuario
    ADD CONSTRAINT puente_mensaje_usuario_receptor_fkey FOREIGN KEY (receptor) REFERENCES public.usuario(id);


--
-- TOC entry 3226 (class 2606 OID 91179)
-- Name: puente_persona_condominio puente_persona_condominio_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3227 (class 2606 OID 91184)
-- Name: puente_persona_condominio puente_persona_condominio_rif_condominio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_persona_condominio
    ADD CONSTRAINT puente_persona_condominio_rif_condominio_fkey FOREIGN KEY (rif_condominio) REFERENCES public.condominio(rif);


--
-- TOC entry 3228 (class 2606 OID 91189)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_funcion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_funcion_fkey FOREIGN KEY (id_funcion) REFERENCES public.funcion(id);


--
-- TOC entry 3229 (class 2606 OID 91194)
-- Name: puente_tipo_funcion puente_tipo_funcion_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_tipo_funcion
    ADD CONSTRAINT puente_tipo_funcion_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3230 (class 2606 OID 91199)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_ci_propietario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_ci_propietario_fkey FOREIGN KEY (ci_propietario) REFERENCES public.propietario(ci_persona);


--
-- TOC entry 3231 (class 2606 OID 91204)
-- Name: puente_unidad_propietarios puente_unidad_propietarios_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puente_unidad_propietarios
    ADD CONSTRAINT puente_unidad_propietarios_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id);


--
-- TOC entry 3232 (class 2606 OID 91209)
-- Name: responsable responsable_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsable
    ADD CONSTRAINT responsable_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3233 (class 2606 OID 91214)
-- Name: unidad unidad_id_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidad
    ADD CONSTRAINT unidad_id_tipo_fkey FOREIGN KEY (id_tipo) REFERENCES public.tipo_unidad(id);


--
-- TOC entry 3234 (class 2606 OID 91219)
-- Name: usuario usuario_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


--
-- TOC entry 3235 (class 2606 OID 91224)
-- Name: usuario usuario_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES public.tipo_usuario(id);


--
-- TOC entry 3236 (class 2606 OID 91229)
-- Name: visita visita_id_unidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visita
    ADD CONSTRAINT visita_id_unidad_fkey FOREIGN KEY (id_unidad) REFERENCES public.unidad(id) NOT VALID;


--
-- TOC entry 3237 (class 2606 OID 91234)
-- Name: visitante visitante_ci_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visitante
    ADD CONSTRAINT visitante_ci_persona_fkey FOREIGN KEY (ci_persona) REFERENCES public.persona(cedula);


-- Completed on 2020-06-26 10:25:28

--
-- PostgreSQL database dump complete
--

