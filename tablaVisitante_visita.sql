CREATE TABLE visitante(
	cedula character varying(10) NOT NULL PRIMARY KEY,
	nombre character varying (25) NOT NULL,
	apellido character varying (25) NOT NULL
	
);
CREATE TABLE visita (
	id serial NOT NULL PRIMARY KEY,
	fecha date DEFAULT LOCALTIMESTAMP(0),
	hora time without time zone DEFAULT LOCALTIMESTAMP(0),
	placa character varying(8),
	modelo character varying(25),
	color character varying(15),
	id_unidad character varying(5) NOT NULL,
	ci_visitante character varying(10) NOT NULL REFERENCES visitante(cedula)
);