-- correr primero desde la linea 4 hasta la 11

-- persona
INSERT INTO persona (cedula, p_nombre, p_apellido, telefono, correo) VALUES ('V-00000000', 'Admin', 'Istrador', '0000-0000000', 'admin@admin.com');

-- tipo_usuario
INSERT INTO tipo_usuario (tipo) VALUES ('Administrador');

-- usuario
INSERT INTO usuario (usuario, password, pregunta, respuesta, ci_persona, id_tipo_usuario) VALUES ('Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 'V-00000000', 1);

-- Banco
SELECT agregar_banco('BANCO DE VENEZUELA',1);
SELECT modificar_banco(1,'BANESCO',1);
SELECT eliminar_banco(1,1);
SELECT reactivar_banco('BANESCO',1);

SELECT agregar_banco('BANCO INDUSTRIAL',1);
SELECT modificar_banco(2,'BBVA BANCO PROCINCIAL',1);
SELECT eliminar_banco(2,1);
SELECT reactivar_banco('BBVA BANCO PROCINCIAL',1);

SELECT * FROM banco;
SELECT * FROM bitacora WHERE tabla='banco' ORDER BY id_bitacora;

-- categoriaGasto
SELECT agregar_categoria(1,'PIRCIPALES', 'NINGUNO');
SELECT modificar_categoria(1,'PRINCIPALES','GASTOS PRINCIPALES',1);
SELECT eliminar_categoria(1,1);
SELECT reactivar_categoria('PRINCIPALES',1);

SELECT agregar_categoria(1,'REPRAACIONES', 'NINGUNO');
SELECT modificar_categoria(1,'REPARACIONES','GASTO DE REPARACIONES',2);
SELECT eliminar_categoria(1,2);
SELECT reactivar_categoria('REPARACIONES',1);

SELECT * FROM categoriagasto;
SELECT * FROM bitacora WHERE tabla='categoriagasto' ORDER BY id_bitacora;

-- concepto_gasto
SELECT agregar_concepto('Reparacio del porton', 'ninguno',1,1);
SELECT modificar_concepto(1,'REPARACION DEL PORTON','NINGUNO',2,1);
SELECT eliminar_concepto(1,1);
SELECT reactivar_concepto('REPARACION DEL PORTON',1);

SELECT agregar_concepto('cerco eledectrico', 'ninguno', 2,1);
SELECT modificar_concepto(2,'CERCO ELECTRICO','INSTALACION',1,1);
SELECT eliminar_concepto(2,1);
SELECT reactivar_concepto('CERCO ELECTRICO',1);

SELECT * FROM concepto_gasto;
SELECT * FROM bitacora WHERE tabla='concepto_gasto' ORDER BY id_bitacora;

-- condominio
SELECT agregar_condominio(1,'J-223189390', 'URB LLANO ALTO', '04145371749', 'AJHENSUAREZ@GMAIL.COM');
SELECT  modificar_condominio(1,'J-223189390','URB CAMPO GUATACARO','04145371748','AJHENSUAREZ@GMAIL.COM');

SELECT * FROM condominio;
SELECT * FROM bitacora WHERE tabla='condominio' ORDER BY id_bitacora;

-- fondos
    SELECT agregar_fondos(1,'PRINCIPAL', '2020-06-01', 'CUENTA EN BOLIVARES', 'NINGUNO', 15000000.0, 'BOLIVAR');
    SELECT modificar_fondos(1,'PRINCIPAL EN BOLIVARES','CUENTA EN BOLIVARES PARA GASTOS','NINGUNA',1);
    SELECT eliminar_fondos(1,1);
    SELECT reactivar_fondo(1,1);

    SELECT agregar_fondos(1,'CAJACHICA', '2020-06-02', 'CUENTA EN DOLARES', 'NINGUNO', 15000.0, 'DOLAR');
    SELECT modificar_fondos(1,'CAJA CHICA EN DOLARES','CUENTA EN DOLARES PARA GASTOS','NINGUNA',2);
    SELECT eliminar_fondos(1,2);
    SELECT reactivar_fondo(2,1);

    SELECT * FROM fondos;
    SELECT * FROM bitacora WHERE tabla='fondos' ORDER BY id_bitacora;

-- forma pago
SELECT agregar_forma_pago('PAGO MOVIL',1);
SELECT modificar_forma_pago(1,'EFECTIVO',1);
SELECT eliminar_forma_pago(1,1);
SELECT reactivar_forma_pago('EFECTIVO',1);

SELECT agregar_forma_pago('TRANSFERENCIA',1);
SELECT modificar_forma_pago(2,'DEPOSITO',1);
SELECT eliminar_forma_pago(2,1);
SELECT reactivar_forma_pago('DEPOSITO',1);

SELECT * FROM forma_pago;
SELECT * FROM bitacora WHERE tabla='forma_pago' ORDER BY id_bitacora;

-- interes
SELECT agregar_interes('INTERES DE MORA',2.0,'J-223189390',1);
SELECT modificar_interes(1,'INTERES POR MORA',2.0,1);
SELECT eliminar_interes(1,1);
SELECT reactivar_interes(1,1);

SELECT agregar_interes('INTERES DE INFRACCION',22.0,'J-223189390',1);
SELECT modificar_interes(2,'INTERES POR INFRACCION',2.2,1);
SELECT eliminar_interes(2,1);
SELECT reactivar_interes(2,1);

SELECT * FROM interes;
SELECT * FROM bitacora WHERE tabla='interes' ORDER BY id_bitacora;

-- proveedores
SELECT agregar_proveedor('V-21118999','HERMANO FONTANEROS','04145371749','FONTANEROS@GMAIL.COM', 'ANDRES LOPEZ', 'CALLE PRINCIPAL',1);
SELECT modificar_proveedor('V-21118999','HERMANOS FONTANEROS C.A','04145371746','HFONTANEROS@GMAIL.COM', 'ANDRES LOPEZ', 'CALLE 1 AV PRINCIPAL',1);
SELECT eliminar_proveedor('V-21118999',1);
SELECT reactivar_proveedor('V-21118999',1);

SELECT agregar_proveedor('V-21118888','REPARA TODO','04145371749','REPARAREROS@GMAIL.COM', 'JUAN CALI', 'CALLE HOEN',1);
SELECT modificar_proveedor('V-21118888','REPARAMOS TODO C.A','04145331749','REPARA@GMAIL.COM', 'JUAN CALI', 'CALLE 12 AV HOEN',1);
SELECT eliminar_proveedor('V-21118888',1);
SELECT reactivar_proveedor('V-21118888',1);

SELECT * FROM proveedores;
SELECT * FROM bitacora WHERE tabla='proveedores' ORDER BY id_bitacora;

-- sancion
SELECT agregar_sancion('MUSICA ALTA',2,2020,1500000,'NINGUNA','PENDIENTE','BOLIVAR',1);
SELECT modificar_sancion('MUSICA MUY ALTA',3,2020,15,'NINGUNA','BOLIVAR',1,1);
SELECT eliminar_sancion(1,1);

SELECT agregar_sancion('NORMAS DE URB',3,2020,15,'NINGUNA','PENDIENTE','DOLAR',1);
SELECT modificar_sancion('ENSUCIAR AREAS VERDES',4,2020,3500000.0,'NINGUNA','BOLIVAR',2,1);
SELECT eliminar_sancion(2,1);

SELECT * FROM sancion;
SELECT * FROM bitacora WHERE tabla='sancion' ORDER BY id_bitacora;

-- tipo_unidad
SELECT agregar_tipo_unidad('CASA CENTRAL',150.0,1);
SELECT modificar_tipo_unidad('CASA CENTRAL TIPO 1',160.0,1,1);
SELECT eliminar_tipo_unidad('CASA CENTRAL TIPO 1',1);
SELECT reactivar_tipo_unidad('CASA CENTRAL TIPO 1',1);

SELECT agregar_tipo_unidad('CASA ESQUINA',200.0,1);
SELECT modificar_tipo_unidad('CASA ESQUINA TIPO 1',220.0,2,1);
SELECT eliminar_tipo_unidad('CASA ESQUINA TIPO 1',1);
SELECT reactivar_tipo_unidad('CASA ESQUINA TIPO 1',1);

SELECT * FROM tipo_unidad;
SELECT * FROM bitacora WHERE tabla='tipo_unidad' ORDER BY id_bitacora;

-- propietario 
SELECT agregar_propietario('V-22318938','JOSBERT', 'ANGELIS', 'SUAREZ','RODRIGUEZ','04145001155','JASR@GMAIL.COM', false, 1);
SELECT modificar_propietario('V-22318938','JHOSBERT', 'ANGELYS', 'SUAREZ','RODRIGUEZ','04145001155','JASR@GMAIL.COM',1);
SELECT eliminar_propietario('V-22318938',1);
SELECT reactivar_propietario('V-22318938',1);

SELECT agregar_propietario('V-25200369','CARLOS', 'JOSE', 'LOPEZ','OBRADOR','04145369574','CJLO@GMAIL.COM', false, 1);
SELECT modificar_propietario('V-25200369','CARLOS', 'ANDRES', 'OBRADOR','LOPEZ','04145221155','CAOL@GMAIL.COM',1);
SELECT eliminar_propietario('V-25200369',1);
SELECT reactivar_propietario('V-25200369',1);

SELECT * FROM propietario;
SELECT * FROM bitacora WHERE tabla='propietario' ORDER BY id_bitacora;

-- responsable
SELECT agregar_responsable('V-9602346','JENY', 'COROMOTO', 'GIL','LEAL','04145371749','JCGL@GMAIL.COM', false, 1);
SELECT modificar_responsable('V-9602346','JENNY', 'COROMOTO', 'LEAL','GIL','04145371749','JCLG@GMAIL.COM',1);
SELECT eliminar_responsable('V-9602346',1);
SELECT reactivar_responsable('V-9602346',1);

SELECT agregar_responsable('V-9602343','ELIZA', 'MAGDALENA', 'GIL','LEAL','04145151749','EMGL@GMAIL.COM', false, 1);
SELECT modificar_responsable('V-9602343','ELISA', 'MAGDALENA', 'LEAL','GIL','04145381749','EMLG@GMAIL.COM',1);
SELECT eliminar_responsable('V-9602343',1);
SELECT reactivar_responsable('V-9602343',1);

SELECT * FROM responsable;
SELECT * FROM bitacora WHERE tabla='responsable' ORDER BY id_bitacora;

-- unidad
SELECT agregar_unidad('UFO-01', '010174653287639', 'CALLE 4, ENTRE AVENIDA 8 Y 9', 1, 1);
SELECT modificar_unidad('010174653287839', 'CALLE 4, ENTRE AVENIDA 9 Y 10', 2, 1, 1);
SELECT eliminar_unidad(1, 1);
SELECT reactivar_unidad('UFO-01', 1);

SELECT agregar_unidad('UFO-02', '010187355261060', 'ESQUINA CALLE 10 CON AVENIDA 2', 2, 1);
SELECT modificar_unidad('010187355261062', 'CALLE 4, ENTRE AVENIDA 9 Y 10', 1, 2, 1);
SELECT eliminar_unidad(2, 1);
SELECT reactivar_unidad('UFO-02', 1);

SELECT * FROM unidad;
SELECT * FROM bitacora WHERE tabla='unidad' ORDER BY id_bitacora;

-- asambleas
SELECT agregar_asambleas('VECINOS RUIDOSOS', '2020-02-01', 'DISCUSIÓN SOBRE PROBLEMAS CON VECINOS RUIDOSOS', 1);
SELECT agregar_asambleas('CERCADO ROTO', '2020-04-21', 'DISCUSIÓN SOBRE REPARACIÓN DE CERCADO ROTO Y OTROS DAÑOS', 1);

SELECT * FROM asambleas;
SELECT * FROM bitacora WHERE tabla='asambleas' ORDER BY id_bitacora;

-- cuenta
SELECT agregar_cuenta('01020456930981735203', 'CORRIENTE', 2, 'V-9602346', 1);
SELECT modificar_cuenta('AHORRO', 1, 'V-9602346', '01020456930981735203', 1);
SELECT eliminar_cuenta('01020456930981735203', 1);
SELECT reactivar_cuenta('01020456930981735203', 1);

SELECT agregar_cuenta('01638594938485746340', 'AHORRO', 1, 'V-9602343', 1);
SELECT modificar_cuenta('AHORRO', 2, 'V-9602346', '01638594938485746340', 1);
SELECT eliminar_cuenta('01638594938485746340', 1);
SELECT reactivar_cuenta('01638594938485746340', 1);

SELECT * FROM cuenta;
SELECT * FROM bitacora WHERE tabla='cuenta' ORDER BY id_bitacora;

-- tipo_usuario
SELECT agregar_tipo_usuario('MODERATOR', 1);
SELECT modificar_tipo_usuario('MODERADOR', 2, 1);
SELECT eliminar_tipo_usuario('MODERADOR', 1);
SELECT reactivar_tipo_usuario('MODERADOR', 1);

SELECT agregar_tipo_usuario('OPERATOR', 1);
SELECT modificar_tipo_usuario('OPERADOR', 3, 1);
SELECT eliminar_tipo_usuario('OPERADOR', 1);
SELECT reactivar_tipo_usuario('OPERADOR', 1);

SELECT * FROM tipo_usuario;
SELECT * FROM bitacora WHERE tabla='tipo_usuario' ORDER BY id_bitacora;

-- usuario
SELECT agregar_usuario('jhosua', '123entrar2020', 'Nombre de primer colegio', 'Arístides Rojas', 'V-22318938', 2, 1);
SELECT modificar_usuario(2, 1, 1);
SELECT eliminar_usuario(2, 1);
SELECT reactivar_usuario(2, 1);

SELECT agregar_usuario('cjlo', '2020vzla', 'Nombre de mascota', 'Cobra', 'V-22318938', 1, 1);
SELECT modificar_usuario(3, 2, 1);
SELECT eliminar_usuario(3, 1);
SELECT reactivar_usuario(3, 1);

SELECT * FROM usuario;
SELECT * FROM bitacora WHERE tabla='usuario' ORDER BY id_bitacora;

-- 5 funciones logicas

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
CREATE FUNCTION login(usu character varying, pass character varying) RETURNS boolean AS $$
DECLARE
	usu1 character varying;
	pass1 character varying;
	--_id_usuario

BEGIN
	usu1 := (SELECT usuario FROM usuario where usuario=usu AND password=pass);
	pass1 := (SELECT password FROM usuario where usuario=usu AND password=pass);

	IF usu = usu1 AND pass = pass1 THEN 
	
	--INSERT INTO sesion_usuario (fecha_entrada, id_usuario) VALUES (default,1);
	
		RETURN TRUE;
		
	ELSE
		RETURN FALSE;
	END IF;
END;
$$ LANGUAGE plpgsql;

-- eliminar_mensaje

CREATE OR REPLACE FUNCTION eliminar_mensaje() RETURNS TRIGGER AS $$
DECLARE
	emisor_bd boolean;
	receptor_bd integer;
BEGIN
	emisor_bd := true;
	--RAISE INFO 'Consultando si el emisor eliminó el mensaje con id: %...', OLD.id;
	emisor_bd := (SELECT activo_emisor FROM mensaje AS msj WHERE msj.id = NEW.id);
	--RAISE INFO 'El resultado es %', emisor_bd;

	IF emisor_bd = true THEN
		--RAISE INFO 'Emisor no ha eliminado el mensaje';
		RETURN null;
	
	ELSE
		--RAISE INFO 'Emisor eliminó el mensaje. Buscando en receptores...';
		receptor_bd := (SELECT COUNT(*) FROM puente_mensaje_usuario WHERE activo_receptor = true AND id_mensaje = OLD.id);
	
		IF receptor_bd = 0 THEN
			--RAISE INFO 'Todos los receptores eliminaron el mensaje';
			DELETE FROM puente_mensaje_usuario WHERE id_mensaje = OLD.id;
			DELETE FROM mensaje WHERE id = old.id;
			
			RETURN NEW;
			
		ELSE
			RETURN null;
		END IF;
	END IF;
END;

-------- Funciones Asambleas --------
-- agregar_asambleas
-- DROP FUNCTION agregar_asambleas;
CREATE OR REPLACE FUNCTION agregar_asambleas(
	nombre2 character varying,
	fecha2 date,
	descripcion2 character varying,
	id_usuario2 integer
) RETURNS boolean AS $$
DECLARE
	resul int;
BEGIN
	INSERT INTO asambleas(nombre, descripcion, fecha) VALUES (nombre2, descripcion2, fecha2);
	GET DIAGNOSTICS resul = ROW_COUNT;
	
	IF resul>0 THEN
 		UPDATE bitacora SET id_usuario = id_usuario2
		WHERE bitacora.id_bitacora = (SELECT MAX(id_bitacora) FROM bitacora);
	RETURN true;
	
	ELSE
		RETURN false;
	END IF;
END;
$$ LANGUAGE plpgsql;

-- funciones matematicas

-- limpiar_mensaje
-- DROP FUNCTION limpiar_mensaje;

	CREATE FUNCTION limpiar_mensaje() RETURNS void AS $$
	BEGIN

		DELETE FROM puente_mensaje_usuario AS pu WHERE (SELECT id FROM mensaje AS me WHERE pu.id_mensaje = me.id) = id_mensaje AND ((LOCALTIMESTAMP(0)::DATE) - (SELECT fecha FROM mensaje AS me WHERE pu.id_mensaje = me.id)::DATE > 90) ;
		
		DELETE FROM mensaje WHERE (LOCALTIMESTAMP(0)::DATE - fecha::DATE) > 90;
	 
	END;
	$$ LANGUAGE plpgsql;
		

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

-- calcular alicuota

CREATE OR REPLACE FUNCTION calcular_alicuota() RETURNS TRIGGER AS $$
DECLARE
	area_total double precision;
BEGIN
	area_total := (SELECT SUM(tp.area) FROM unidad AS u INNER JOIN tipo_unidad AS tp ON u.id_tipo = tp.id WHERE 	 u.activo = true);
		
	UPDATE unidad SET alicuota = (SELECT area FROM tipo_unidad WHERE id = id_tipo) / area_total;
	
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- pagar gasto

CREATE OR REPLACE FUNCTION pagar_gasto() RETURNS TRIGGER AS $$
DECLARE

	saldo_bd double precision;
	
BEGIN
	
	UPDATE fondos SET saldo = saldo - NEW.monto WHERE id = NEW.id_fondo;

	UPDATE gasto SET saldo = saldo - NEW.monto WHERE id = NEW.id_gasto;
	
	saldo_bd := (SELECT saldo FROM gasto WHERE id = NEW.id_gasto);

	IF saldo_bd = 0 THEN
	
		UPDATE gasto SET pagado = 'Pagado' WHERE id = NEW.id_gasto;
		
	END IF;
	
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 10 inner joins
--1
SELECT pro.ci_persona, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo, pro.activo 
	FROM propietario AS pro
	INNER JOIN persona AS per ON per.cedula = pro.ci_persona;

--2
SELECT asa.id, pr.ci_persona AS cedula, pr.p_nombre AS nombre, pr.p_apellido AS apellido, pu.id AS id_puente
	FROM v_propietario AS pr
	INNER JOIN puente_asambleas_propietario AS pu ON pu.ci_propietario = pr.ci_persona
	INNER JOIN asambleas AS asa ON asa.id = pu.id_asamblea;
--3
SELECT id_bitacora, operacion, tabla, usu.usuario, usu.ci_persona AS cedula,
	CONCAT(per.p_nombre,' ', per.p_apellido) AS persona, valor_viejo, valor_nuevo, fecha_hora AS fecha FROM bitacora 
	INNER JOIN usuario AS usu ON id_usuario = usu.id 
	INNER JOIN persona AS per ON cedula = usu.ci_persona;
--4
	SELECT cg.id, cg.nom_concepto AS nombre, cg.descripcion, cat.id AS id_categoria, cat.nombre AS nombre_categoria, cg.activo
	FROM concepto_gasto AS cg
	INNER JOIN categoriagasto AS cat ON cat.id = cg.id_categoria;
--5
SELECT n_cuenta, tipo, id_banco, nombre_banco AS banco, ci_persona, per.p_nombre AS nombre, per.p_apellido AS apellido, rif_condominio, razon_social, cue.activo
	FROM cuenta AS cue
	INNER JOIN banco AS ban ON ban.id = cue.id_banco
	LEFT JOIN persona AS per ON per.cedula = cue.ci_persona
	LEFT JOIN condominio AS co ON co.rif = cue.rif_condominio
	WHERE cue.activo = true;
--6
	SELECT cp.id, cp.num_ref, cp.descripcion, cp.monto, cp.moneda, cp.tasa_cambio, cp.fecha, cp.id_gasto, ga.nombre AS gasto, cp.n_cuenta, cu.id_banco, b.nombre_banco AS banco, cp.id_fondo, f.tipo AS fondo, cp.id_forma_pago, fp.forma_pago
	FROM cuenta_pagar AS cp
	INNER JOIN gasto AS ga ON ga.id = cp.id_gasto
	INNER JOIN cuenta AS cu ON cu.n_cuenta = cp.n_cuenta
	INNER JOIN banco AS b ON b.id = cu.id_banco
	INNER JOIN fondos AS f ON f.id = cp.id_fondo
	INNER JOIN forma_pago AS fp ON fp.id = cp.id_forma_pago
	ORDER BY fecha DESC;
--7
SELECT prop.ci_persona, puente.id, puente.id_unidad, puente.fecha_hasta
	FROM propietario AS prop
	LEFT JOIN puente_unidad_propietarios AS puente ON prop.ci_persona = puente.ci_propietario
	WHERE prop.activo = true AND puente.fecha_hasta IS null;
--8
SELECT me.id, me.asunto, me.contenido, me.fecha, me.emisor AS id_emisor, u.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido
	FROM mensaje AS me
	INNER JOIN usuario AS u ON u.id = me.emisor
	INNER JOIN persona AS pe ON pe.cedula = u.ci_persona
	WHERE activo_emisor = true;
--9
SELECT me.id, me.asunto, me.contenido, me.fecha, me.emisor AS id_emisor, u1.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido, pu.receptor, pu.leido, pu.activo_receptor
	FROM mensaje AS me
	INNER JOIN usuario AS u1 ON u1.id = me.emisor
	INNER JOIN puente_mensaje_usuario AS pu ON pu.id_mensaje = me.id
	INNER JOIN usuario AS u2 ON u2.id = pu.receptor
	INNER JOIN persona AS pe ON pe.cedula = u1.ci_persona
	WHERE activo_receptor = true;
--10
SELECT pu.id_mensaje, pu.receptor AS id_receptor, u.ci_persona AS cedula, pe.p_nombre AS nombre, pe.p_apellido AS apellido
	FROM puente_mensaje_usuario AS pu
	INNER JOIN usuario AS u ON u.id = pu.receptor
	INNER JOIN persona AS pe ON pe.cedula = u.ci_persona;