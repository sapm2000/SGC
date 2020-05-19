CREATE TABLE persona(
	cedula character varying(10) NOT NULL PRIMARY KEY,
	p_nombre character varying(25) NOT NULL,
	s_nombre character varying(25) NOT NULL DEFAULT '',
 	p_apellido character varying(25) NOT NULL,
	s_apellido character varying(25) NOT NULL DEFAULT '',
	telefono character varying(12) NOT NULL UNIQUE,
	correo character varying(60) NOT NULL UNIQUE,
	activo boolean DEFAULT true
);

CREATE TABLE responsable(
	ci_persona character varying(10) NOT null REFERENCES persona (cedula),
	activo boolean DEFAULT true
);

CREATE TABLE propietario(
	ci_persona character varying(10) NOT null REFERENCES persona (cedula),
	activo boolean DEFAULT true
);

CREATE TABLE unidad (
	id serial NOT null PRIMARY KEY,
	n_unidad character varying(10) NOT NULL,
	n_documento character varying(15) NOT NULL,
    direccion character varying(200) NOT NULL,
    area double precision NOT NULL,
    id_condominio character varying(15) REFERENCES condominio (rif),
    activo boolean DEFAULT true
);

CREATE TABLE puente_persona_condominio (
	id serial NOT null PRIMARY KEY,
	ci_persona character varying(10) NOT null REFERENCES persona (cedula),
	rif_condominio character varying(15) NOT null REFERENCES condominio (rif)
);

CREATE TABLE puente_unidad_propietarios(
	id serial NOT NULL PRIMARY KEY,
    ci_propietario character varying(15) NOT NULL REFERENCES propietario (cedula),
    id_unidad bigint NOT NULL REFERENCES unidad (id),
    fecha_desde date NOT NULL DEFAULT LOCALTIMESTAMP(0),
    fecha_hasta date,
    estado bigint NOT NULL,
    activo boolean DEFAULT true
);

CREATE OR REPLACE VIEW v_responsable AS
	SELECT r.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM responsable AS r
	INNER JOIN persona AS per ON per.cedula = r.ci_persona
	WHERE r.activo = true;

CREATE OR REPLACE VIEW v_responsable_inactivo AS
	SELECT r.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM responsable AS r
	INNER JOIN persona AS per ON per.cedula = r.ci_persona
	WHERE r.activo = false;

CREATE OR REPLACE VIEW v_propietario AS
	SELECT pro.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM propietario AS pro
	INNER JOIN persona AS per ON per.cedula = pro.ci_persona
	WHERE pro.activo = true;

CREATE OR REPLACE VIEW v_propietario_inactivo AS
	SELECT pro.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM propietario AS pro
	INNER JOIN persona AS per ON per.cedula = pro.ci_persona
	WHERE pro.activo = false;

CREATE OR REPLACE VIEW v_unidad AS
	SELECT id, n_unidad, n_documento, direccion, area, id_condominio
	FROM unidad
	WHERE activo = true;

CREATE OR REPLACE VIEW v_unidades_inactivas AS
	SELECT id, n_unidad, n_documento, direccion, area, id_condominio
	FROM unidad
	WHERE activo = false;

CREATE OR REPLACE VIEW v_unidad_propietario AS
	SELECT u.id, u.id_condominio, pro.ci_persona, pro.p_nombre, pro.s_nombre, pro.p_apellido, pro.s_apellido, pro.telefono, pro.correo, puente.id AS id_puente, puente.fecha_desde, puente.fecha_hasta, puente.estado
	FROM v_propietario AS pro
	INNER JOIN puente_unidad_propietarios AS puente ON puente.ci_propietario = pro.ci_persona
	INNER JOIN unidad AS u ON u.id = puente.id_unidad
	WHERE u.activo = true  AND estado = 1;

CREATE OR REPLACE VIEW v_dueno_unidad AS
	SELECT prop.ci_persona, puente.id, puente.id_unidad, puente.fecha_hasta
	FROM propietario AS prop
	LEFT JOIN puente_unidad_propietarios AS puente ON prop.ci_persona = puente.ci_propietario
	WHERE prop.activo = true AND puente.fecha_hasta IS null;
	