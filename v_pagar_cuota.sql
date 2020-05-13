CREATE OR REPLACE VIEW v_pagar_cuota AS
SELECT pce.numero_ref, pce.forma_pago, pce.monto, pce.fecha, cu.n_cuenta, ban.nombre_banco, fon.tipo, coga.nom_concepto, cues.pagado, cues.id_condominio, pr.nombre
FROM pagar_cuota_especial AS pce
INNER JOIN cuotas_especiales AS cues ON pce.id_cuota_e = cues.id
INNER JOIN concepto_gasto AS coga ON cues.id_concepto = coga.id
INNER JOIN fondos AS fon ON pce.id_fondo = fon.id
INNER JOIN cuenta AS cu ON id_cuenta = cu.n_cuenta
INNER JOIN banco AS ban ON cu.id_banco = ban.id
INNER JOIN proveedores AS pr ON cues.id_proveedor = pr.cedula
WHERE cues.pagado = 'Pagado';
