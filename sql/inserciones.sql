-- correr primero desde la linea 4 hasta la 11
-- correr con la bd vacia
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

