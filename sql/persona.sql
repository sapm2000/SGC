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
	CONSTRAINT responsable_pkey PRIMARY KEY (cedula),
	CONSTRAINT responsable_telefono_key UNIQUE (telefono),
	CONSTRAINT responsable_correo_key UNIQUE (correo)
) INHERITS (persona);

CREATE TABLE propietario(
	CONSTRAINT propietario_pkey PRIMARY KEY(cedula),
	CONSTRAINT propietario_telefono_key UNIQUE (telefono),
	CONSTRAINT propietario_correo_key UNIQUE (correo)
) INHERITS (persona);

CREATE TABLE unidad (
	id serial NOT null PRIMARY KEY,
	n_unidad character varying(10) NOT NULL UNIQUE,
    direccion character varying(200) NOT NULL,
    area double precision NOT NULL,
    id_condominio character varying(15) REFERENCES condominio (rif),
    activo boolean DEFAULT true
);

CREATE TABLE puente_unidad_propietarios(
	id serial NOT NULL PRIMARY KEY,
    ci_propietario character varying(15) NOT NULL REFERENCES propietario (cedula),
    id_unidad bigint NOT NULL REFERENCES unidad (id),
    fecha_desde date NOT NULL DEFAULT LOCALTIMESTAMP(0),
    fecha_hasta date,
    documento character varying(30) NOT NULL,
    estado bigint NOT NULL,
    activo boolean DEFAULT true
);

CREATE OR REPLACE VIEW v_responsable AS
	SELECT cedula, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM responsable
	WHERE activo = true;
	
CREATE OR REPLACE VIEW v_propietario AS
	SELECT cedula, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo
	FROM propietario
	WHERE activo = true;

CREATE OR REPLACE VIEW v_unidad AS
	SELECT id, n_unidad, direccion, area, id_condominio
	FROM unidad
	WHERE activo = true;

CREATE OR REPLACE VIEW v_unidad_propietario AS
	SELECT u.id, u.id_condominio, pro.cedula, pro.p_nombre, pro.s_nombre, pro.p_apellido, pro.s_apellido, pro.telefono, pro.correo, puente.id AS id_puente, puente.fecha_desde, puente.fecha_hasta, puente.estado, puente.documento
	FROM v_propietario AS pro
	INNER JOIN puente_unidad_propietarios AS puente ON puente.ci_propietario = pro.cedula
	INNER JOIN unidad AS u ON u.id = puente.id_unidad
	WHERE u.activo = true;
