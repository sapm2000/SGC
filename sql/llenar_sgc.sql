-- funcion
INSERT INTO funcion (funcion) VALUES ('Asambleas');
INSERT INTO funcion (funcion) VALUES ('Banco');
INSERT INTO funcion (funcion) VALUES ('Categoria Gastos');
INSERT INTO funcion (funcion) VALUES ('Concepto Gastos');
INSERT INTO funcion (funcion) VALUES ('Comunicados');
INSERT INTO funcion (funcion) VALUES ('Condominio');
INSERT INTO funcion (funcion) VALUES ('Cuenta');
INSERT INTO funcion (funcion) VALUES ('Cuentas por cobrar');
INSERT INTO funcion (funcion) VALUES ('Cuentas por pagar');
INSERT INTO funcion (funcion) VALUES ('Fondo');
INSERT INTO funcion (funcion) VALUES ('Forma de pago');
INSERT INTO funcion (funcion) VALUES ('Gasto');
INSERT INTO funcion (funcion) VALUES ('Generar recibo');
INSERT INTO funcion (funcion) VALUES ('Gestionar Usuario');
INSERT INTO funcion (funcion) VALUES ('Intereses');
INSERT INTO funcion (funcion) VALUES ('Pago de cuotas especiales');
INSERT INTO funcion (funcion) VALUES ('Propietarios');
INSERT INTO funcion (funcion) VALUES ('Proveedores');
INSERT INTO funcion (funcion) VALUES ('Recibo');
INSERT INTO funcion (funcion) VALUES ('Responsables');
INSERT INTO funcion (funcion) VALUES ('Sanciones');
INSERT INTO funcion (funcion) VALUES ('Tipo de unidad');
INSERT INTO funcion (funcion) VALUES ('Tipo de usuario');
INSERT INTO funcion (funcion) VALUES ('Unidades');
INSERT INTO funcion (funcion) VALUES ('Registro de visitas');
INSERT INTO funcion (funcion) VALUES ('Visitas autorizadas');

-- tipo_usuario
INSERT INTO tipo_usuario (tipo) VALUES ('Administrador');

-- puente_tipo_funcion
INSERT INTO puente_tipo_funcion (id_tipo, id_funcion, registrar, modificar, eliminar, todo) VALUES
(1, 1, true, true, true, true),
(1, 2, true, true, true, true),
(1, 3, true, true, true, true),
(1, 4, true, true, true, true),
(1, 5, true, true, true, true),
(1, 6, true, true, true, true),
(1, 7, true, true, true, true),
(1, 8, true, true, true, true),
(1, 9, true, true, true, true),
(1, 10, true, true, true, true),
(1, 11, true, true, true, true),
(1, 12, true, true, true, true),
(1, 13, true, true, true, true),
(1, 14, true, true, true, true),
(1, 15, true, true, true, true),
(1, 16, true, true, true, true),
(1, 17, true, true, true, true),
(1, 18, true, true, true, true),
(1, 19, true, true, true, true),
(1, 20, true, true, true, true),
(1, 21, true, true, true, true),
(1, 22, true, true, true, true),
(1, 23, true, true, true, true),
(1, 24, true, true, true, true),
(1, 25, true, true, true, true),
(1, 26, true, true, true, true);

-- usuario
INSERT INTO usuario (usuario, password, pregunta, respuesta, id_tipo_usuario) VALUES ('Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 1);

