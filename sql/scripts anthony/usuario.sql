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

CREATE TABLE tipo_usuario (
	id serial NOT null PRIMARY KEY,
	tipo character varying NOT null UNIQUE
);

CREATE TABLE funcion (
	id serial NOT null PRIMARY KEY,
	funcion character varying NOT null UNIQUE
);

CREATE TABLE puente_tipo_funcion (
	id serial NOT null PRIMARY KEY,
	id_tipo int NOT null REFERENCES tipo_usuario (id),
	id_funcion int NOT null REFERENCES funcion (id),
	registrar boolean,
	modificar boolean,
	eliminar boolean,
	todo boolean
);

INSERT INTO funcion (funcion) VALUES ('Asambleas');
INSERT INTO funcion (funcion) VALUES ('Banco');
INSERT INTO funcion (funcion) VALUES ('Comunicados');
INSERT INTO funcion (funcion) VALUES ('Condominio');
INSERT INTO funcion (funcion) VALUES ('Cuenta');
INSERT INTO funcion (funcion) VALUES ('Cuentas por cobrar');
INSERT INTO funcion (funcion) VALUES ('Cuentas por pagar');
INSERT INTO funcion (funcion) VALUES ('Cuotas especiales');
INSERT INTO funcion (funcion) VALUES ('Fondo');
INSERT INTO funcion (funcion) VALUES ('Gastos comunes');
INSERT INTO funcion (funcion) VALUES ('Intereses');
INSERT INTO funcion (funcion) VALUES ('Pago de cuotas especiales');
INSERT INTO funcion (funcion) VALUES ('Propietarios');
INSERT INTO funcion (funcion) VALUES ('Proveedores');
INSERT INTO funcion (funcion) VALUES ('Recibo');
INSERT INTO funcion (funcion) VALUES ('Sanciones');
INSERT INTO funcion (funcion) VALUES ('Tipo de usuario');
INSERT INTO funcion (funcion) VALUES ('Unidades');
INSERT INTO funcion (funcion) VALUES ('Registro de visitas');
INSERT INTO funcion (funcion) VALUES ('Visitas autorizadas');

--INSERT INTO usuario (usuario, password, pregunta, respuesta) VALUES ('Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta');

CREATE OR REPLACE VIEW v_usuario AS	
	SELECT id, usuario, ci_persona
	FROM usuario
	WHERE activo = true;
	
CREATE OR REPLACE VIEW v_usuario_inactivo AS	
	SELECT id, usuario, ci_persona
	FROM usuario
	WHERE activo = false;
