-- banco
INSERT INTO banco (nombre_banco) VALUES ('Banco de Venezuela');
INSERT INTO banco (nombre_banco) VALUES ('Banco del Caribe');
INSERT INTO banco (nombre_banco) VALUES ('Banco Provincial');
INSERT INTO banco (nombre_banco) VALUES ('Banco Bicentenario');
INSERT INTO banco (nombre_banco) VALUES ('Banesco');
INSERT INTO banco (nombre_banco) VALUES ('Banco Exterior');
INSERT INTO banco (nombre_banco) VALUES ('Banco BOD');


-- forma_pago
INSERT INTO forma_pago(forma_pago) VALUES ('Pago móvil');
INSERT INTO forma_pago(forma_pago) VALUES ('Transferencia');
INSERT INTO forma_pago(forma_pago) VALUES ('Depósito');
INSERT INTO forma_pago(forma_pago) VALUES ('Efectivo');
INSERT INTO forma_pago(forma_pago) VALUES ('Cheque');
INSERT INTO forma_pago(forma_pago) VALUES ('Punto de venta');

-- funcion
INSERT INTO funcion (funcion) VALUES ('Asambleas');
INSERT INTO funcion (funcion) VALUES ('Banco');
INSERT INTO funcion (funcion) VALUES ('Categoria Gastos');
--INSERT INTO funcion (funcion) VALUES ('Comunicados');
INSERT INTO funcion (funcion) VALUES ('Concepto Gastos');
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
INSERT INTO funcion (funcion) VALUES ('Propietarios');
INSERT INTO funcion (funcion) VALUES ('Proveedores');
INSERT INTO funcion (funcion) VALUES ('Recibo');
INSERT INTO funcion (funcion) VALUES ('Responsables');
INSERT INTO funcion (funcion) VALUES ('Sanciones');
INSERT INTO funcion (funcion) VALUES ('Tipo de unidad');
INSERT INTO funcion (funcion) VALUES ('Tipo de usuario');
INSERT INTO funcion (funcion) VALUES ('Unidades');
INSERT INTO funcion (funcion) VALUES ('Visitas autorizadas');
INSERT INTO funcion (funcion) VALUES ('Bitacora');


-- persona
INSERT INTO persona (cedula, p_nombre, p_apellido, telefono, correo) VALUES ('V-00000000', 'Admin', 'Istrador', '0000-0000000', 'admin@admin.com');

-- tipo_usuario
INSERT INTO tipo_usuario (tipo) VALUES ('Administrador');

-- puente_tipo_funcion
INSERT INTO puente_tipo_funcion (id_tipo, id_funcion, ver, registrar, modificar, eliminar) VALUES
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
(1, 24, true, true, true, true);

-- usuario
INSERT INTO usuario (usuario, password, pregunta, respuesta, ci_persona, id_tipo_usuario) VALUES ('Ingresar Usuario', '455831477b82574f6bf871193f2f761d', 'pregunta', 'respuesta', 'V-00000000', 1);
