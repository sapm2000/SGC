-- asambleas
CREATE TABLE asambleas(
	id serial NOT NULL PRIMARY KEY,
	nombre character varying(60) NOT NULL,
	descripcion text NOT NULL,
	fecha date NOT NULL
);

-- banco
CREATE TABLE banco (
	id serial NOT NULL PRIMARY KEY,
	nombre_banco character varying(30) NOT NULL UNIQUE,
	activo boolean DEFAULT true
);

-- bitacora
CREATE TABLE IF NOT EXISTS bitacora (
	id_bitacora serial NOT NULL PRIMARY KEY,
	operacion text NOT NULL,
	tabla text NOT NULL,
	id_usuario int REFERENCES usuario(id),
	valor_viejo text,
	valor_nuevo text,
	fecha_hora timestamp without time zone DEFAULT LOCALTIMESTAMP(0)
);

-- categoriagasto
CREATE TABLE categoriagasto(
	id serial NOT NULL PRIMARY KEY,
	nombre character varying(50) NOT NULL,
	descripcion character varying(200),
	activo boolean NOT NULL DEFAULT true
);

-- condominio
CREATE TABLE condominio(
	rif character varying(10) NOT NULL PRIMARY KEY,
	razon_social character varying(200) NOT null,
	telefono character varying(11) NOT null,
	correo_electronico character varying(80) DEFAULT '',
	activo boolean NOT NULL DEFAULT true
);

-- forma_pago
-- DROP TABLE forma_pago;
CREATE TABLE forma_pago (
    id serial NOT NULL PRIMARY KEY,
    forma_pago character varying NOT NULL,
    activo boolean NOT NULL
);

-- funcion
CREATE TABLE funcion (
	id serial NOT null PRIMARY KEY,
	funcion character varying NOT null UNIQUE
);

-- interes
CREATE TABLE interes (
	id serial NOT NULL PRIMARY KEY,
	nombre character varying(50) NOT NULL,
	factor double precision NOT NULL,
	activo bigint NOT NULL
);

-- persona
CREATE TABLE persona(
	cedula character varying(10) NOT NULL PRIMARY KEY,
	p_nombre character varying(25) NOT NULL,
	s_nombre character varying(25) DEFAULT '',
 	p_apellido character varying(25) NOT NULL,
	s_apellido character varying(25) DEFAULT '',
	telefono character varying(12) UNIQUE,
	correo character varying(60) UNIQUE,
	activo boolean DEFAULT true
);

-- proveedores
CREATE TABLE proveedores(
	cedula character varying(10) NOT NULL PRIMARY KEY,
	nombre character varying(25) NOT NULL,
	telefono character varying(12) NOT NULL,	
	correo character varying(80) NOT NULL,
	contacto character varying(60)NOT NULL,
	direccion character varying(150) NOT NULL,
	activo boolean NOT NULL
);

-- sancion
CREATE TABLE sancion (
	id serial NOT NULL PRIMARY KEY,
	tipo character varying(40) NOT NULL,
	mes bigint NOT NULL,
	anio bigint NOT NULL,
	monto double precision NOT NULL,
	descripcion character varying(120) NOT NULL,
	estado character varying(20) NOT NULL,
	moneda character varying(10)
);

-- tipo_unidad
CREATE TABLE tipo_unidad(
	id serial PRIMARY KEY,
	area double precision NOT NULL,
	tipo character varying(60),
	activo boolean NOT null DEFAULT true
);

-- tipo_usuario
CREATE TABLE tipo_usuario (
	id serial NOT null PRIMARY KEY,
	tipo character varying NOT null UNIQUE,
	activo boolean NOT null DEFAULT true
);

-------- Tablas con Claves Foráneas --------
--cierre_de_mes
CREATE TABLE cierre_de_mes(
	id serial NOT NULL PRIMARY KEY,	
	mes bigint NOT NULL,
	anio bigint NOT NULL,
	id_condominio character varying(15) NOT NULL REFERENCES condominio(rif)
);

-- concepto_gasto
CREATE TABLE concepto_gasto(
	id serial NOT null PRIMARY KEY,
    nom_concepto character varying(120) NOT NULL,
    descripcion character varying(120) NOT NULL,
    id_categoria integer NOT NULL REFERENCES categoriagasto (id),
    activo boolean DEFAULT true
);

-- cuenta
CREATE TABLE cuenta (
    n_cuenta character varying(20) NOT NULL PRIMARY KEY,
    tipo character varying(10) NOT NULL,
    id_banco bigint NOT NULL REFERENCES banco (id),
    ci_persona character varying(10) NOT NULL REFERENCES persona (cedula),
	rif_condominio character varying(10) NOT null REFERENCES condominio(rif),
    activo boolean DEFAULT true
);

-- fondo
CREATE TABLE fondos (
    tipo character varying(100) NOT NULL,
    fecha date NOT NULL,
    descripcion character varying(200) NOT NULL,
    observaciones character varying(200),
    monto_inicial double precision NOT NULL,
    saldo double precision NOT NULL,
    id serial NOT NULL PRIMARY KEY,
    activo boolean NOT NULL DEFAULT true,
    moneda character varying NOT NULL
);

-- gasto
CREATE TABLE gasto(
    id serial NOT NULL PRIMARY KEY,
    nombre character varying(50) NOT null,
    tipo character varying(20) NOT null,
    id_proveedor character varying(15) NOT NULL,
    calcular_por character varying(20) NOT NULL,
    mes int NOT NULL,
    anio int NOT NULL,
    n_meses int NOT NULL,
    id_asamblea int REFERENCES asambleas (id),
    observacion text DEFAULT '',
    meses_restantes int NOT null,
	monto double precision NOT null,
	saldo double precision NOT null,
    estado character varying NOT null DEFAULT 'Pendiente',
    pagado character varying NOT null DEFAULT 'Pendiente',
    moneda character varying NOT null 
);

-- cuenta_pagar
-- DROP TABLE cuenta_pagar;
CREATE TABLE cuenta_pagar (
    id serial NOT null PRIMARY KEY,
    num_ref character varying(10),
    descripcion character varying(60) NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
	moneda character varying(10) NOT NULL,
	tasa_cambio double precision,
	id_forma_pago integer NOT NULL REFERENCES forma_pago(id),
    id_gasto integer NOT NULL REFERENCES gasto (id),
    n_cuenta character varying(20) REFERENCES cuenta (n_cuenta),
    id_fondo integer NOT NULL REFERENCES fondos (id)
);

-- propietario
CREATE TABLE propietario(
	ci_persona character varying(10) NOT null PRIMARY KEY REFERENCES persona (cedula),
	activo boolean DEFAULT true
);

-- responsable
CREATE TABLE responsable(
	ci_persona character varying(10) NOT null PRIMARY KEY REFERENCES persona (cedula),
	activo boolean DEFAULT true
);

-- unidad
CREATE TABLE unidad (
	id serial NOT null PRIMARY KEY,
	n_unidad character varying(10) NOT NULL,
	n_documento character varying(15) NOT NULL,
    direccion character varying(200) NOT NULL,
    area double precision NOT NULL,
    activo boolean DEFAULT true,
    alicuota double precision,
    id_tipo integer NOT NULL REFERENCES tipo_unidad (id)
);

-- cobro_unidad
CREATE TABLE cobro_unidad(
    id serial NOT NULL PRIMARY KEY,
    monto double precision NOT NULL,
    descripcion character varying(500) NOT NULL,
    id_cuenta character varying(20) NOT NULL REFERENCES cuenta (n_cuenta),
    forma_pago character varying(150) NOT NULL,
    referencia character varying(50) NOT NULL,
    fecha date NOT NULL,
    id_fondo bigint NOT NULL REFERENCES fondos(id),
    id_unidad bigint NOT NULL REFERENCES unidad(id),
	moneda character varying NOT NULL,
    paridad double precision NOT NULL
);

-- usuario
CREATE TABLE usuario (
	id serial NOT null PRIMARY KEY,
	usuario character varying(25) NOT null UNIQUE,
	password character varying(32) NOT null,
	pregunta character varying(120) NOT null,
	respuesta character varying(120) NOT null,
	ci_persona character varying(10) REFERENCES persona (cedula),
	id_tipo_usuario int REFERENCES tipo_usuario (id),
	activo boolean DEFAULT true
);

-- mensaje
CREATE TABLE mensaje (
	id serial NOT null PRIMARY KEY,
    asunto character varying(60) DEFAULT 'Sin Asunto',
    contenido character varying(420) NOT NULL,
    emisor integer NOT NULL REFERENCES usuario (id),
    fecha timestamp without time zone NOT NULL DEFAULT LOCALTIMESTAMP(0),
	activo_emisor boolean NOT null DEFAULT true
);

-- visitante
CREATE TABLE visitante (
	ci_persona character varying(10) NOT null PRIMARY KEY REFERENCES persona (cedula),
	activo boolean DEFAULT true
);

-- visita
CREATE TABLE visita(
    id serial NOT NULL PRIMARY KEY,
    id_unidad integer NOT NULL REFERENCES unidad (id),
    fecha date DEFAULT LOCALTIMESTAMP(0),
    hora time without time zone DEFAULT LOCALTIMESTAMP(0),
    placa character varying(8),
    modelo character varying(25),
    color character varying(15),
    ci_visitante character varying(10) NOT NULL REFERENCES visitante (ci_persona)
);

-- detalle_pagos
-- DROP TABLE detalle_pagos;
CREATE TABLE detalle_pagos (
    id serial NOT NULL PRIMARY KEY,
    mes bigint NOT NULL,
    anio bigint NOT NULL,
    monto_dolar double precision NOT NULL,
    id_gasto integer NOT NULL REFERENCES gasto (id),
    id_unidad integer NOT NULL REFERENCES unidad (id),
    tipo_gasto character varying NOT NULL,
    monto_bolivar double precision NOT NULL,
    paridad double precision NOT NULL,
    moneda_dominante character varying NOT NULL,
	saldo_restante_bolivar double precision NOT NULL,
    saldo_restante_dolar double precision
);


-------- Tablas puente --------
-- puente_asambleas_propietario
CREATE TABLE puente_asambleas_propietario(
	id serial NOT NULL PRIMARY KEY,
    id_asamblea int NOT NULL REFERENCES asambleas (id),
    ci_propietario character varying(10) NOT NULL REFERENCES propietario (ci_persona)
);

-- puente_cobro_factura
-- DROP TABLE puente_cobro_factura;
CREATE TABLE puente_cobro_factura (
	id serial NOT NULL PRIMARY KEY,
    id_detalle_pagos bigint NOT NULL REFERENCES detalle_pagos (id),
    id_cobro bigint NOT NULL REFERENCES cobro_unidad (id),
    parte_monto double precision NOT NULL,
    moneda character varying 
);

-- puente_gasto_concepto
CREATE TABLE puente_gasto_concepto (
	id serial NOT null PRIMARY KEY,
	id_gasto int REFERENCES concepto_gasto (id),
	id_concepto int REFERENCES concepto_gasto (id),
	monto double precision NOT null
);

-- puente_mensaje_usuario
-- DROP TABLE puente_mensaje_usuario CASCADE;
CREATE TABLE puente_mensaje_usuario (
    id serial NOT NULL PRIMARY KEY,
    id_mensaje integer NOT NULL REFERENCES mensaje (id),
    receptor integer NOT NULL REFERENCES usuario (id),
	leido boolean NOT null DEFAULT false,
	activo_receptor boolean NOT null DEFAULT true
);

-- puente_persona_condominio
CREATE TABLE puente_persona_condominio (
	id serial NOT null PRIMARY KEY,
	ci_persona character varying(10) NOT null REFERENCES persona (cedula),
	rif_condominio character varying(15) NOT null REFERENCES condominio (rif)
);

-- puente_sancion_unidad
-- DROP TABLE puente_sancion_unidad
CREATE TABLE puente_sancion_unidad (
    id serial NOT NULL PRIMARY KEY,
    id_sancion integer NOT NULL REFERENCES sancion (id),
    id_unidad bigint NOT NULL REFERENCES unidad (id)
);

-- puente_unidad_propietarios
CREATE TABLE puente_unidad_propietarios(
	id serial NOT NULL PRIMARY KEY,
    ci_propietario character varying(15) NOT NULL REFERENCES propietario (ci_persona),
    id_unidad bigint NOT NULL REFERENCES unidad (id),
    fecha_desde date NOT NULL DEFAULT LOCALTIMESTAMP(0),
    fecha_hasta date,
    estado bigint NOT NULL,
    activo boolean DEFAULT true
);

-- puente_tipo_funcion
CREATE TABLE puente_tipo_funcion (
	id serial NOT null PRIMARY KEY,
	id_tipo int NOT null REFERENCES tipo_usuario (id),
	id_funcion int NOT null REFERENCES funcion (id),
	registrar boolean,
	modificar boolean,
	eliminar boolean,
	todo boolean
);

-------- Funciones --------
-- pagar_gasto
CREATE OR REPLACE FUNCTION pagar_gasto(id2 integer, monto2 double precision) RETURNS void AS $$
DECLARE
	saldo_bd double precision;
BEGIN
	UPDATE gasto SET saldo = saldo - monto2 WHERE id = id2;
	
	saldo_bd := (SELECT saldo FROM gasto WHERE id = id2);

	IF saldo_bd = 0 THEN
		UPDATE gasto SET pagado = 'Pagado' WHERE id = id2;
	END IF;
END;
$$ LANGUAGE plpgsql;

-- cambiar_pregunta
CREATE OR REPLACE FUNCTION cambiar_pregunta(usuario2 character varying, pregunta2 character varying, respuesta2 character varying, password2 character varying) RETURNS boolean AS $$
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
$$ LANGUAGE plpgsql;
	
-- cambiar_clave
CREATE OR REPLACE FUNCTION cambiar_clave(usuario2 character varying, password_nuevo character varying, password_actual character varying) RETURNS boolean AS $$ 
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
$$ LANGUAGE plpgsql;
	
-- login
CREATE FUNCTION login(usu character varying, pass character varying) RETURNS BOOLEAN AS $$
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
$$ LANGUAGE plpgsql;

-------- Funciones Asambleas --------
-- agregar_asambleas
CREATE OR REPLACE FUNCTION agregar_asambleas(
	nombre2 character varying,
	fecha2 date,
	descripcion2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO asambleas(nombre, descripcion, fecha) VALUES (nombre2, descripcion2, fecha2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Banco --------
-- agregar_banco
CREATE OR REPLACE FUNCTION agregar_banco(
	nombre2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO banco (nombre_banco) VALUES (nombre2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_banco
CREATE OR REPLACE FUNCTION modificar_banco(
	id2 int,
	nombre2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE banco SET nombre_banco = nombre2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_banco
CREATE OR REPLACE FUNCTION eliminar_banco(
	id2 int,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE banco SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Categoria Gasto --------
-- agregar_categoria
CREATE OR REPLACE FUNCTION agregar_categoria(
	id_usuario2 integer,
	nombre2 character varying,
	descripcion2 character varying
) RETURNS void AS $$
BEGIN
	INSERT INTO categoriagasto (nombre, descripcion) VALUES (nombre2, descripcion2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_categoria
CREATE OR REPLACE FUNCTION modificar_categoria(
	id_usuario2 integer,
	nombre2 character varying,
	descripcion2 character varying,
	id2 integer
) RETURNS void AS $$
BEGIN
	UPDATE categoriagasto SET nombre=nombre2, descripcion=descripcion2 WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_categoria
CREATE OR REPLACE FUNCTION eliminar_categoria(
	id_usuario2 integer,
	id2 integer
) RETURNS void AS $$
BEGIN
	UPDATE categoriagasto SET activo=false WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_categoria
CREATE OR REPLACE FUNCTION reactivar_categoria(
	nombre2 character varying,
	descripcion2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE categoriagasto SET descripcion = descripcion2, activo = true WHERE nombre = nombre2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Concepto Gasto --------
-- agregar_concepto
CREATE OR REPLACE FUNCTION agregar_concepto(
	nombre2 character varying,
	descripcion2 character varying,
	id_categoria2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES (nombre2, descripcion2, id_categoria2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_concepto
CREATE OR REPLACE FUNCTION modificar_concepto(
	id2 int,
	nombre2 character varying,
	descripcion2 character varying,
	id_categoria2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE concepto_gasto SET nom_concepto = nombre2, descripcion = descripcion2, id_categoria = id_categoria2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_concepto
CREATE OR REPLACE FUNCTION eliminar_concepto(
	id2 int,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE concepto_gasto SET activo=false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_concepto
CREATE OR REPLACE FUNCTION reactivar_concepto(
	nombre2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE concepto_gasto SET activo = true WHERE nom_concepto = nombre2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- funciones condominio --------
-- agregar_condominio
CREATE OR REPLACE FUNCTION agregar_condominio(
	id_usuario2 integer,
	rif2 character varying,
	razon_social2 character varying,
	telefono2 character varying,
	correo_electronico2 character varying
) RETURNS void AS $$
BEGIN
	INSERT INTO condominio (rif, razon_social, telefono, correo_electronico)
	VALUES(rif2, razon_social2, telefono2, correo_electronico2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_condominio
CREATE OR REPLACE FUNCTION modificar_condominio(
	id_usuario2 integer,
	rif2 character varying,
	razon_social2 character varying,
	telefono2 character varying,
	correo_electronico2 character varying
) RETURNS void AS $$
BEGIN
	UPDATE condominio SET razon_social=razon_social2, telefono=telefono2, correo_electronico=correo_electronico2     WHERE rif=rif2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Cuenta --------
-- agregar_cuenta
CREATE OR REPLACE FUNCTION agregar_cuenta(
	n_cuenta2 character varying,
	tipo2 character varying,
	id_banco2 integer,
	ci_persona2 character varying,
	rif_condominio2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO cuenta (n_cuenta, tipo, id_banco, ci_persona, rif_condominio) VALUES (n_cuenta2, tipo2, 	id_banco2, ci_persona2, rif_condominio2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_cuenta
CREATE OR REPLACE FUNCTION modificar_cuenta(
	
	tipo2 character varying,
	id_banco2 integer,
	ci_persona2 character varying,
	n_cuenta2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE cuenta SET tipo=tipo2, id_banco=id_banco2, ci_persona=ci_persona2
	WHERE n_cuenta=n_cuenta2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_cuenta
CREATE OR REPLACE FUNCTION eliminar_cuenta(
	n_cuenta2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE cuenta SET activo=false WHERE n_cuenta = n_cuenta2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_cuenta
CREATE OR REPLACE FUNCTION reactivar_cuenta(
	n_cuenta2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE cuenta SET activo = true WHERE n_cuenta = n_cuenta2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Cuenta Pagar --------
-- agregar_cuenta_pagar
CREATE OR REPLACE FUNCTION agregar_cuenta_pagar(
	descripcion2 character varying,
	num_ref2 character varying,
	moneda2 character varying,
	monto2 double precision,
	fecha2 date,
	tasa_cambio2 double precision,
	id_gasto2 integer,
	id_forma_pago2 integer,
	n_cuenta2 character varying,
	id_fondo2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO cuenta_pagar (descripcion, num_ref, moneda, monto, fecha, tasa_cambio, id_gasto,
	id_forma_pago, n_cuenta, id_fondo) VALUES (descripcion2, num_ref2, moneda2, monto2, fecha2,
	tasa_cambio2, id_gasto2, id_forma_pago2, n_cuenta2, id_fondo2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Fondos --------
--agregar_fondos
CREATE OR REPLACE FUNCTION agregar_fondos(
	id_usuario2 integer,
	tipo2 character varying,
	fecha2 date,
	descripcion2 character varying,
	observaciones2 character varying,
	monto_inicial2 double precision,
	moneda2 character varying
) RETURNS void AS $$
BEGIN
	INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda)
	VALUES (tipo2, fecha2, descripcion2, observaciones2, monto_inicial2, monto_inicial2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

--modificar_fondos
CREATE OR REPLACE FUNCTION modificar_fondos(
	id_usuario2 integer,
	tipo2 character varying,
	descripcion2 character varying,
	observaciones2 character varying,
	id2 integer
) RETURNS void AS $$
BEGIN
	UPDATE fondos SET descripcion=descripcion2, observaciones=observaciones2, 
	tipo=tipo2 WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_fondos
CREATE OR REPLACE FUNCTION eliminar_fondos(
	id_usuario2 integer,
	id2 integer
) RETURNS void AS $$
BEGIN
	UPDATE fondos SET activo=false WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Forma de pago --------
-- agregar_forma_pago
CREATE OR REPLACE FUNCTION agregar_forma_pago(
	nombre2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO forma_pago (forma_pago) VALUES (nombre2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_forma_pago
CREATE OR REPLACE FUNCTION modificar_forma_pago(
	id2 int,
	nombre2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE forma_pago SET forma_pago = nombre2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_forma_pago
CREATE OR REPLACE FUNCTION eliminar_forma_pago(
	id2 int,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE forma_pago SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_forma_pago
CREATE OR REPLACE FUNCTION reactivar_forma_pago(
	nombre2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE forma_pago SET activo = true WHERE forma_pago = nombre2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Gasto --------
-- agregar_gasto
CREATE OR REPLACE FUNCTION agregar_gasto(
	nombre2 character varying,
	tipo2 character varying,
	id_proveedor2 character varying(15),
	calcular_por2 character varying,
	mes2 integer,
	anio2 integer,
	n_meses2 integer,
	id_asamblea2 integer,
	observacion2 text,
	meses_restantes2 integer,
	monto2 double precision,
	moneda2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO gasto(nombre, tipo, id_proveedor, calcular_por, mes, anio, n_meses, observacion, id_asamblea, meses_restantes, monto, saldo, moneda) VALUES (nombre2, tipo2, id_proveedor2, calcular_por2, mes2, anio2, n_meses2, observacion2, id_asamblea2, meses_restantes2, monto2, monto2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_gasto
CREATE OR REPLACE FUNCTION modificar_gasto(
	id2 int,
	nombre2 character varying,
	tipo2 character varying,
	id_proveedor2 character varying(15),
	calcular_por2 character varying,
	mes2 integer,
	anio2 integer,
	n_meses2 integer,
	id_asamblea2 integer,
	observacion2 text,
	meses_restantes2 integer,
	monto2 double precision,
	saldo2 double precision,
	moneda2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE gasto SET nombre = nombre2, tipo = tipo2, id_proveedor = id_proveedor2, calcular_por = calcular_por2, mes = mes2, anio = anio2, n_meses = n_meses2, id_asamblea =id_asamblea2, observacion = observacion2, meses_restantes = meses_restantes2, monto = monto2, saldo = saldo2, moneda = moneda2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Cerrar mes --------
-- registrar_cuota
CREATE OR REPLACE FUNCTION registrar_cuota(
	id_unidad2 integer,
	id_gasto2 integer,
	mes2 integer,
	anio2 integer,
	monto_dolar2 double precision,
	monto_bolivar2 double precision,
	tipo_gasto2 character varying,
	moneda_dominante2 character varying,
	paridad2 double precision,
	saldo_restante_bolivar2 double precision,
	saldo_restante_dolar2 double precision,
	alicuota2 double precision,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO recibo (
		id_unidad, id_gasto, mes, anio, monto_dolar, monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota
	) VALUES (
		id_unidad2, id_gasto2, mes2, anio2, monto_dolar2, monto_bolivar2, tipo_gasto2, moneda_dominante2, paridad2, saldo_restante_bolivar2, saldo_restante_dolar2, alicuota2
	);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- registrar_interes
CREATE OR REPLACE FUNCTION registrar_interes(
	id_unidad2 integer,
	id_gasto2 integer,
	mes2 integer,
	anio2 integer,
	monto_dolar2 double precision,
	monto_bolivar2 double precision,
	tipo_gasto2 character varying,
	moneda_dominante2 character varying,
	paridad2 double precision,
	saldo_restante_bolivar2 double precision,
	saldo_restante_dolar2 double precision,
	alicuota2 double precision,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO recibo (
		id_unidad, id_gasto, mes, anio, monto_dolar, monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota
	) VALUES (
		id_unidad2, id_gasto2, mes2, anio2, monto_dolar2, monto_bolivar2, tipo_gasto2, moneda_dominante2, paridad2, saldo_restante_bolivar2, saldo_restante_dolar2, alicuota2
	);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Interés --------
-- agregar_interes
CREATE OR REPLACE FUNCTION agregar_interes(
	nombre2 character varying,
	factor2 double precision,
	id_condominio2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO interes (nombre, factor, id_condominio) VALUES (nombre2, factor2, id_condominio2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_interes
CREATE OR REPLACE FUNCTION modificar_interes(
	id2 integer,
	nombre2 character varying,
	factor2 double precision,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE interes SET nombre = nombre2, factor = factor2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_interes
CREATE OR REPLACE FUNCTION eliminar_interes(
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE interes SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_interes
CREATE OR REPLACE FUNCTION reactivar_interes(
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE interes SET activo = true WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Proveedor --------
-- agregar_proveedor
CREATE OR REPLACE FUNCTION agregar_proveedor(
	cedula2 character varying,
	nombre2 character varying,
	telefono2 character varying,
	correo2 character varying,
	contacto2 character varying,
	direccion2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO proveedores(cedula, nombre, telefono, correo, contacto, direccion) VALUES (cedula2, nombre2, telefono2, correo2, contacto2, direccion2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_proveedor
CREATE OR REPLACE FUNCTION modificar_proveedor(
	cedula2 character varying,
	nombre2 character varying,
	telefono2 character varying,
	correo2 character varying,
	contacto2 character varying,
	direccion2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE proveedores SET nombre = nombre2, telefono = telefono2, correo = correo2, contacto = contacto2, direccion = direccion2 WHERE cedula = cedula2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_proveedor
CREATE OR REPLACE FUNCTION eliminar_proveedor(
	cedula2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE proveedores SET activo = false WHERE cedula = cedula2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_proveedor
CREATE OR REPLACE FUNCTION reactivar_proveedor(
	cedula2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE proveedores SET activo = true WHERE cedula = cedula2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Sanciones --------
-- agregar_sancion
CREATE OR REPLACE FUNCTION agregar_sancion(
	tipo2 character varying,
	mes2 integer,
	anio2 integer,
	monto2 double precision,
	descripcion2 character varying,
	estado2 character varying,
	moneda2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO sancion(tipo, mes, anio, monto, descripcion,  estado, moneda) VALUES (tipo2, mes2, anio2, monto2, descripcion2,  estado2, moneda2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_sancion
CREATE OR REPLACE FUNCTION modificar_sancion(
	tipo2 character varying,
	mes2 integer,
	anio2 integer,
	monto2 double precision,
	descripcion2 character varying,
	moneda2 character varying,
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE sancion SET tipo=tipo2, mes=mes2, anio=anio2, monto=monto2, descripcion=descripcion2, moneda=moneda2 WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_sancion
CREATE OR REPLACE FUNCTION eliminar_sancion(
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	DELETE FROM sancion WHERE id=id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Tipo Unidad --------
-- agregar_tipo_unidad
CREATE OR REPLACE FUNCTION agregar_tipo_unidad(
	tipo2 character varying,
	area2 double precision,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO tipo_unidad(tipo, area) VALUES (tipo2,area2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_tipo_unidad
CREATE OR REPLACE FUNCTION modificar_tipo_unidad(
	tipo2 character varying,
	area2 double precision,
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE tipo_unidad SET tipo = tipo2, area = area2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_tipo_unidad
CREATE OR REPLACE FUNCTION eliminar_tipo_unidad(
	tipo2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE tipo_unidad SET activo = false WHERE id = tipo2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_tipo_unidad
CREATE OR REPLACE FUNCTION reactivar_tipo_unidad(
	tipo2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE tipo_unidad SET activo = true WHERE tipo = tipo2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-------- Funciones Unidades --------
-- agregar_unidad
-- DROP FUNCTION agregar_unidad;
CREATE OR REPLACE FUNCTION agregar_unidad(
	n_unidad2 character varying,
	n_documento2 character varying,
	direccion2 character varying,
	id_tipo2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	INSERT INTO unidad(n_unidad, n_documento, direccion, id_tipo) VALUES (n_unidad2, n_documento2, direccion2, id_tipo2);
	
 	UPDATE bitacora SET id_usuario = id_usuario2
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- modificar_unidad
-- DROP FUNCTION modificar_unidad
CREATE OR REPLACE FUNCTION modificar_unidad(
	n_documento2 character varying,
	direccion2 character varying,
	id_tipo2 integer,
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE unidad SET n_documento = n_documento2, direccion = direccion2, id_tipo=id_tipo2 WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Modificado'
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- eliminar_unidad
CREATE OR REPLACE FUNCTION eliminar_unidad(
	id2 integer,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE unidad SET activo = false WHERE id = id2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Eliminado', valor_nuevo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;

-- reactivar_tipo_unidad
CREATE OR REPLACE FUNCTION reactivar_tipo_unidad(
	tipo2 character varying,
	id_usuario2 integer
) RETURNS void AS $$
BEGIN
	UPDATE tipo_unidad SET activo = true WHERE tipo = tipo2;
	
 	UPDATE bitacora SET id_usuario = id_usuario2, operacion = 'Registro', valor_viejo = null
	WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
END;
$$ LANGUAGE plpgsql;


-------- funciones trigger --------
-- llenar_bitacora
CREATE OR REPLACE FUNCTION llenar_bitacora() RETURNS TRIGGER AS $$
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
$$ LANGUAGE plpgsql;

-------- Triggers ---------
--tg_asamblea
CREATE TRIGGER tg_asamblea
BEFORE INSERT
ON asambleas
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_banco
CREATE TRIGGER tg_banco
BEFORE INSERT OR UPDATE
ON banco
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_categoria_gasto
CREATE TRIGGER tg_categoria_gasto
BEFORE INSERT OR UPDATE
ON categoriagasto
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_concepto_gasto
CREATE TRIGGER tg_concepto_gasto
BEFORE INSERT OR UPDATE
ON concepto_gasto
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_condominio
CREATE TRIGGER tg_condominio
BEFORE INSERT OR  UPDATE
ON condominio
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_cuenta
CREATE TRIGGER tg_cuenta
BEFORE INSERT OR  UPDATE
ON cuenta
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_cuenta_pagar
CREATE TRIGGER tg_cuenta_pagar
BEFORE INSERT OR  UPDATE
ON cuenta_pagar
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_fondos
CREATE TRIGGER tg_fondos
BEFORE INSERT OR UPDATE
ON fondos
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_forma_pago
CREATE TRIGGER tg_forma_pago
BEFORE INSERT OR UPDATE
ON forma_pago
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_gasto
CREATE TRIGGER tg_gasto
BEFORE INSERT OR UPDATE
ON gasto
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_interes
CREATE TRIGGER tg_interes
BEFORE INSERT OR UPDATE
ON interes
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_proveedores
CREATE TRIGGER tg_proveedores
BEFORE INSERT OR UPDATE
ON proveedores
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_recibo
CREATE TRIGGER tg_recibo
BEFORE INSERT OR UPDATE
ON recibo
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_sancion
CREATE TRIGGER tg_sancion
BEFORE INSERT OR UPDATE
ON sancion
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_tipo_unidad
CREATE TRIGGER tg_tipo_unidad
BEFORE INSERT OR UPDATE
ON tipo_unidad
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();

--tg_unidad
CREATE TRIGGER tg_unidad
BEFORE INSERT OR UPDATE
ON unidad
FOR EACH ROW
EXECUTE PROCEDURE llenar_bitacora();


-------- Vistas --------
-- v_propietario
CREATE OR REPLACE VIEW v_propietario AS
	SELECT pro.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM propietario AS pro
	INNER JOIN persona AS per ON per.cedula = pro.ci_persona
	WHERE pro.activo = true;

-- v_propietario_inactivo
CREATE OR REPLACE VIEW v_propietario_inactivo AS
	SELECT pro.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM propietario AS pro
	INNER JOIN persona AS per ON per.cedula = pro.ci_persona
	WHERE pro.activo = false;

-- asambleas
-- DROP VIEW v_asambleas;
CREATE OR REPLACE VIEW v_asambleas AS
	SELECT asa.id, asa.nombre, asa.descripcion, asa.fecha
	FROM asambleas AS asa;

-- DROP VIEW v_asambleas_propietario;
-- v_asambleas_propietario
CREATE OR REPLACE VIEW v_asambleas_propietario AS
	SELECT asa.id, pr.ci_persona AS cedula, pr.p_nombre AS nombre, pr.p_apellido AS apellido, pu.id AS id_puente
	FROM v_propietario AS pr
	INNER JOIN puente_asambleas_propietario AS pu ON pu.ci_propietario = pr.ci_persona
	INNER JOIN asambleas AS asa ON asa.id = pu.id_asamblea;

-- v_concepto_gasto
CREATE OR REPLACE VIEW v_concepto_gasto AS
	SELECT cg.id, cg.nom_concepto AS nombre, cg.descripcion, cat.id AS id_categoria, cat.nombre AS nombre_categoria, cg.activo
	FROM concepto_gasto AS cg
	INNER JOIN categoriagasto AS cat ON cat.id = cg.id_categoria;

-- v_condominio
CREATE OR REPLACE VIEW v_condominio AS
	SELECT rif, razon_social, telefono, correo_electronico AS correo
	FROM condominio;

-- v_cuenta
-- DROP VIEW v_cuenta;
CREATE OR REPLACE VIEW v_cuenta AS
	SELECT n_cuenta, tipo, id_banco, nombre_banco AS banco, ci_persona, per.p_nombre AS nombre, per.p_apellido AS apellido, rif_condominio, cue.activo
	FROM cuenta AS cue
	INNER JOIN banco AS ban ON ban.id = cue.id_banco
	INNER JOIN persona AS per ON per.cedula = cue.ci_persona
	WHERE cue.activo = true;

-- v_cuenta_inactivo
-- DROP VIEW v_cuenta_inactivo;
CREATE OR REPLACE VIEW v_cuenta_inactivo AS
	SELECT n_cuenta, tipo, id_banco, nombre_banco AS banco, ci_persona, per.p_nombre AS nombre, per.p_apellido AS apellido, rif_condominio, cue.activo
	FROM cuenta AS cue
	INNER JOIN banco AS ban ON ban.id = cue.id_banco
	INNER JOIN persona AS per ON per.cedula = cue.ci_persona
	WHERE cue.activo = false;

-- v_cuenta_pagar
-- DROP VIEW v_cuenta_pagar;
CREATE OR REPLACE VIEW v_cuenta_pagar AS
	SELECT cp.id, cp.num_ref, cp.descripcion, cp.monto, cp.moneda, cp.tasa_cambio, cp.fecha, cp.id_gasto, ga.nombre AS gasto, cp.n_cuenta, cu.id_banco, b.nombre_banco AS banco, cp.id_fondo, f.tipo AS fondo, cp.id_forma_pago, fp.forma_pago
	FROM cuenta_pagar AS cp
	INNER JOIN gasto AS ga ON ga.id = cp.id_gasto
	INNER JOIN cuenta AS cu ON cu.n_cuenta = cp.n_cuenta
	INNER JOIN banco AS b ON b.id = cu.id_banco
	INNER JOIN fondos AS f ON f.id = cp.id_fondo
	INNER JOIN forma_pago AS fp ON fp.id = cp.id_forma_pago
	ORDER BY fecha DESC;

-- v_dueno_unidad
CREATE OR REPLACE VIEW v_dueno_unidad AS
	SELECT prop.ci_persona, puente.id, puente.id_unidad, puente.fecha_hasta
	FROM propietario AS prop
	LEFT JOIN puente_unidad_propietarios AS puente ON prop.ci_persona = puente.ci_propietario
	WHERE prop.activo = true AND puente.fecha_hasta IS null;

-- v_gasto
-- DROP VIEW v_gasto;
CREATE OR REPLACE VIEW v_gasto AS
	SELECT ga.id, ga.tipo, ga.nombre, id_proveedor, ga.moneda, pr.nombre AS proveedor, calcular_por, mes, anio, monto, saldo, n_meses, meses_restantes, asa.id AS id_asamblea, asa.nombre AS asamblea, asa.fecha AS fecha_asamblea, observacion, estado, pagado
	FROM gasto AS ga 
	INNER JOIN proveedores AS pr ON pr.cedula = ga.id_proveedor
	LEFT JOIN asambleas as asa ON asa.id = ga.id_asamblea;
	
-- v_gasto_concepto
-- DROP VIEW v_gasto_concepto;
CREATE OR REPLACE VIEW v_gasto_concepto AS
	SELECT ga.id AS id_gasto, cg.id AS id_concepto, cg.nombre, pu.monto, pu.id AS id_puente
	FROM v_concepto_gasto AS cg
	INNER JOIN puente_gasto_concepto AS pu ON pu.id_concepto = cg.id
	INNER JOIN gasto AS ga ON ga.id = pu.id_gasto;

-- v_bandeja_salida
-- DROP VIEW v_bandeja_salida;
CREATE OR REPLACE VIEW v_bandeja_salida AS
	SELECT me.id, me.asunto, me.contenido, me.fecha, me.emisor AS id_emisor, u.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido
	FROM mensaje AS me
	INNER JOIN usuario AS u ON u.id = me.emisor
	INNER JOIN persona AS pe ON pe.cedula = u.ci_persona
	WHERE activo_emisor = true;

-- v_bandeja_entrada
-- DROP VIEW v_bandeja_entrada;
CREATE OR REPLACE VIEW v_bandeja_entrada AS
	SELECT me.id, me.asunto, me.contenido, me.fecha, me.emisor AS id_emisor, u1.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido, pu.receptor, pu.leido, pu.activo_receptor
	FROM mensaje AS me
	INNER JOIN usuario AS u1 ON u1.id = me.emisor
	INNER JOIN puente_mensaje_usuario AS pu ON pu.id_mensaje = me.id
	INNER JOIN usuario AS u2 ON u2.id = pu.receptor
	INNER JOIN persona AS pe ON pe.cedula = u1.ci_persona
	WHERE activo_receptor = true;

-- v_mensaje_usuario
-- DROP VIEW v_mensaje_usuario;
CREATE VIEW v_mensaje_usuario AS
	SELECT pu.id_mensaje, pu.receptor AS id_receptor, u.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido
	FROM puente_mensaje_usuario AS pu
	INNER JOIN usuario AS u ON u.id = pu.receptor
	INNER JOIN persona AS pe ON pe.cedula = u.ci_persona;

-- v_perfil
CREATE OR REPLACE VIEW v_perfil AS
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
	FROM persona per
	INNER JOIN usuario AS usu ON usu.ci_persona = per.cedula
	INNER JOIN tipo_usuario AS tip ON tip.id = usu.id_tipo_usuario;

-- v_permisos
-- DROP VIEW v_permisos;
CREATE OR REPLACE VIEW v_permisos AS
	SELECT usuario, tipo.id AS id_tipo, tipo.tipo, id_funcion, funcion, registrar, modificar, eliminar, todo
	FROM puente_tipo_funcion AS puente
	INNER JOIN tipo_usuario AS tipo ON tipo.id = puente.id_tipo
	INNER JOIN funcion AS f ON f.id = puente.id_funcion
	INNER JOIN usuario AS u ON u.id_tipo_usuario = puente.id_tipo;

-- v_responsable
CREATE OR REPLACE VIEW v_responsable AS
	SELECT r.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM responsable AS r
	INNER JOIN persona AS per ON per.cedula = r.ci_persona
	WHERE r.activo = true;

-- v_responsable_inactivo
CREATE OR REPLACE VIEW v_responsable_inactivo AS
	SELECT r.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM responsable AS r
	INNER JOIN persona AS per ON per.cedula = r.ci_persona
	WHERE r.activo = false;

-- v_tipo_unidad
-- DROP v_tipo_unidad
CREATE OR REPLACE VIEW v_tipo_unidad AS
	SELECT tu.id, tu.tipo, tu.area
	FROM tipo_unidad AS tu
	WHERE tu.activo = true;

-- v_tipo_unidad_inactivo
-- DROP v_tipo_unidad_inactivo
CREATE OR REPLACE VIEW v_tipo_unidad_inactivo AS
	SELECT tu.id, tu.tipo, tu.area
	FROM tipo_unidad AS tu
	WHERE tu.activo = false;

-- v_unidad
-- DROP VIEW v_unidad;
CREATE OR REPLACE VIEW v_unidad AS
	SELECT u.id, n_unidad, n_documento, direccion, alicuota, tu.id AS id_tipo, tu.tipo, tu.area
	FROM unidad AS u
	INNER JOIN tipo_unidad AS tu ON tu.id = u.id_tipo
	WHERE u.activo = true;

-- v_unidad_propietario
CREATE OR REPLACE VIEW v_unidad_propietario AS
	SELECT u.id, pro.ci_persona, pro.p_nombre, pro.s_nombre, pro.p_apellido, pro.s_apellido, pro.telefono, pro.correo, puente.id AS id_puente, puente.fecha_desde, puente.fecha_hasta, puente.estado
	FROM v_propietario AS pro
	INNER JOIN puente_unidad_propietarios AS puente ON puente.ci_propietario = pro.ci_persona
	INNER JOIN unidad AS u ON u.id = puente.id_unidad
	WHERE u.activo = true  AND estado = 1;

-- v_unidades_inactivas
CREATE OR REPLACE VIEW v_unidades_inactivas AS
	SELECT id, n_unidad, n_documento, direccion, area
	FROM unidad
	WHERE activo = false;

-- v_usuario
-- DROP VIEW v_usuario;
CREATE OR REPLACE VIEW v_usuario AS	
	SELECT u.id, u.usuario, u.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido
	FROM usuario AS u
	INNER JOIN persona AS pe ON pe.cedula = u.ci_persona
	WHERE u.activo = true;

-- v_usuario_inactivo
CREATE OR REPLACE VIEW v_usuario_inactivo AS	
	SELECT id, usuario, ci_persona
	FROM usuario
	WHERE activo = false;

-- v_visita
-- DROP VIEW v_visita;
CREATE OR REPLACE VIEW v_visita AS
	SELECT vis.id, vis.id_unidad, u.n_unidad, vis.fecha, vis.hora, vis.placa AS matricula, vis.modelo, vis.color, vis.ci_visitante AS cedula, per.p_nombre AS nombre, per.p_apellido AS apellido
	FROM visita AS vis
	INNER JOIN unidad AS u ON u.id = vis.id_unidad
	INNER JOIN visitante AS v ON v.ci_persona=vis.ci_visitante
	INNER JOIN persona AS per ON per.cedula = v.ci_persona;

-- v_visitante
CREATE OR REPLACE VIEW v_visitante AS
	SELECT v.ci_persona AS cedula, p_nombre AS nombre, p_apellido AS apellido
	FROM visitante AS v
	INNER JOIN persona AS per ON per.cedula = v.ci_persona
	WHERE v.activo = true;
