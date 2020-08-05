-- banco
INSERT INTO banco(nombre_banco) VALUES ('BANCO DE VENEZUELA');
UPDATE banco SET nombre_banco='BANESCO' WHERE id = 1;
UPDATE banco SET activo='False' WHERE id = 1;

INSERT INTO banco(nombre_banco) VALUES ('BANCO INDUSTRIAL');
UPDATE banco SET nombre_banco='BBVA BANCO PROVINCIAL' WHERE id = 2;
UPDATE banco SET activo='False' WHERE id = 2;

INSERT INTO banco(nombre_banco) VALUES ('BANCO DEL TESORO');
UPDATE banco SET nombre_banco='BANCO MERCANTIL' WHERE id = 3;
UPDATE banco SET activo='False' WHERE id = 3;

INSERT INTO banco(nombre_banco) VALUES ('BANCO DE LAS FUERZAS ARMADAS');
UPDATE banco SET nombre_banco='BANCO EXTERIOR' WHERE id = 4;
UPDATE banco SET activo='False' WHERE id = 4;

INSERT INTO banco(nombre_banco) VALUES ('BANCO BICENTENARIO');
UPDATE banco SET nombre_banco='B.O.D' WHERE id = 5;
UPDATE banco SET activo='False' WHERE id = 5;

-- categoriaGasto
INSERT INTO categoriagasto(nombre, descripcion) VALUES ('PIRCIPALES', 'NINGUNO');
UPDATE categoriagasto SET nombre='PRINCIPALES', descripcion=' GASTOS PRINCIPALES' WHERE id = 1;
UPDATE categoriagasto SET activo=false WHERE id = 1;

INSERT INTO public.categoriagasto(nombre, descripcion) VALUES ('REPRAACIONES', 'NINGUNO');
UPDATE public.categoriagasto SET nombre='REPARACIONES', descripcion='GASTO DE REPARACIONES' WHERE id = 2;
UPDATE categoriagasto SET activo=false WHERE id = 2;

INSERT INTO categoriagasto(nombre, descripcion) VALUES ('MANTINIMITNTO', 'NINGUNO');
UPDATE categoriagasto SET nombre='MANTENIMIENTO', descripcion='GASTO DE MANTENIMIENTOS' WHERE id = 3;
UPDATE categoriagasto SET activo=false WHERE id = 3;

INSERT INTO categoriagasto(nombre, descripcion) VALUES ('ADMINISTRASION', 'NINGUNO');
UPDATE categoriagasto SET nombre='ADMINISTRACION', descripcion='GASTO DE ADMINISTRACION' WHERE id = 4;
UPDATE categoriagasto SET activo=false WHERE id = 4;

INSERT INTO categoriagasto(nombre, descripcion) VALUES ('COLABRORACION', 'NINGUNO');
UPDATE categoriagasto SET nombre='COLABORACIONES', descripcion='GASTO DE COLABORACIONES' WHERE id = 5;
UPDATE categoriagasto SET activo=false WHERE id = 5;

-- concepto_gasto
INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES ('Reparacio del porton', 'ninguno', 3);
UPDATE concepto_gasto SET nom_concepto='REPARACION DEL PORTON', descripcion='NINGUNO', id_categoria=2 WHERE id = 1;
UPDATE concepto_gasto SET activo=False WHERE id=1;

INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES ('cerco eledectrico', 'ninguno', 1);
UPDATE concepto_gasto SET nom_concepto='CERCO ELECTRICO', descripcion='INSTALACION', id_categoria=1 WHERE id = 2;
UPDATE concepto_gasto SET activo=False WHERE id=2;

INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES ('danaciones para ong', 'ninguno', 4);
UPDATE concepto_gasto SET nom_concepto='DONACIONES', descripcion='DONACIONES A ORGANIZACIONES O PERSONAS', id_categoria=5 WHERE id =3 ;
UPDATE concepto_gasto SET activo=False WHERE id=3;

INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES ('pago seniat', 'ninguno', 2);
UPDATE concepto_gasto SET nom_concepto='PAGO SENIAT', descripcion='PAGO DE SENIAT', id_categoria=4 WHERE id = 4;
UPDATE concepto_gasto SET activo=False WHERE id=4;

INSERT INTO concepto_gasto(nom_concepto, descripcion, id_categoria) VALUES ('canchas nueva', 'ninguno', 5);
UPDATE concepto_gasto SET nom_concepto='CANCHA NUEVA', descripcion='CONSTRUCCION DE CANCHA', id_categoria=1 WHERE id = 5;
UPDATE concepto_gasto SET activo=False WHERE id=5;

-- fondos

INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda) VALUES ('gasto principales','2020-07-28', 'ninguna', 'ninguna',1500.0,1500.0,'bolivar');


INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda) VALUES ('fondo en dolares','2020-07-28', 'ninguno', 'ninguno',500.0,500.0,'dolores');


INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda) VALUES ('','2020-07-28', '', '',,,'');


INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda) VALUES ('','2020-07-28', '', '',,,'');


INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda) VALUES ('','2020-07-28', '', '',,,'');


INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda) VALUES ('','2020-07-28', '', '',,,'');


