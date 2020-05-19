CREATE TABLE usuario (
	id serial NOT null PRIMARY KEY,
	usuario character varying(25) NOT null UNIQUE,
	password character varying(32) NOT null,
	pregunta character varying(120) NOT null,
	respuesta character varying(120) NOT null,
	tipo character varying(50),
	ci_persona character varying(10) REFERENCES persona (cedula),
	--id_tipo_usuario int REFERENCES tipo_usuario (id),
	activo boolean DEFAULT true
);

--INSERT INTO usuario (usuario, password, pregunta, respuesta) VALUES ('Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta');

CREATE OR REPLACE VIEW v_usuario AS	
	SELECT id, usuario, ci_persona
	FROM usuario
	WHERE activo = true;
	
CREATE OR REPLACE VIEW v_usuario_inactivo AS	
	SELECT id, usuario, ci_persona
	FROM usuario
	WHERE activo = false;
