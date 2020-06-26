package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class CerrarMes extends ConexionBD {

    private int id;
    private int mes_cierre;
    private int año_cierre;
    private double monto_dolar;
    private double monto_bolivar;
    public Unidades uni = new Unidades();
    public ConceptoGasto concep = new ConceptoGasto();
    public Sancion san = new Sancion();
    public Interes in = new Interes();
    private double alicuota;
    private String estado;
    public Gasto cuo = new Gasto();

    public Proveedores prove = new Proveedores();
    private int meses_deuda;
    private double saldo_restante_bs;
    private double saldo_restante_dolar;
    private String tipo_gasto;
    public Gasto gasto = new Gasto();
    private double paridad;
    private String moneda_dominante;

    private Connection con;

    public boolean registrar_cuota(CerrarMes modc) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT registrar_cuota(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, uni.getId());
            ps.setInt(ind++, gasto.getId());
            ps.setInt(ind++, getMes_cierre());
            ps.setInt(ind++, getAño_cierre());
            ps.setDouble(ind++, getMonto_dolar());
            ps.setDouble(ind++, getMonto_bolivar());
            ps.setString(ind++, getTipo_gasto());
            ps.setString(ind++, getMoneda_dominante());
            ps.setDouble(ind++, getParidad());
            ps.setDouble(ind++, getMonto_dolar());
            ps.setDouble(ind++, getMonto_bolivar());
            ps.setDouble(ind++, uni.getAlicuota());
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean buscartotal(CerrarMes modc, int x) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "";
        if (x == 1) {
            sql = "SELECT id_unidad, sum(monto_bolivar) as total FROM recibo where id_unidad=? and mes=? and anio=? and tipo_gasto != 'Interes' and tipo_gasto != 'Sancion'  group by id_unidad;";

        } else {
            sql = "SELECT id_unidad, sum(monto_dolar) as total FROM recibo where id_unidad=? and mes=? and anio=? and tipo_gasto != 'Interes' and tipo_gasto != 'Sancion'  group by id_unidad;";

        }

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modc.uni.getId());
            ps.setInt(2, modc.getMes_cierre());
            ps.setInt(3, modc.getAño_cierre());

            rs = ps.executeQuery();
            if (rs.next()) {
                if (x == 1) {
                    modc.setMonto_bolivar(rs.getDouble("total"));
                } else {
                    modc.setMonto_dolar(rs.getDouble("total"));
                }

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean buscaultimo(CerrarMes modc) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT max(id) as id FROM factura_unidad;";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setId(rs.getInt("id"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean guardarsancionpro(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "INSERT INTO recibo(id_unidad,id_gasto, mes, anio, monto_dolar,monto_bolivar, tipo_gasto, moneda_dominante, paridad, saldo_restante_bolivar, saldo_restante_dolar, alicuota) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, uni.getId());
            ps.setInt(2, gasto.getId());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());

            ps.setDouble(5, getMonto_dolar());
            ps.setDouble(6, getMonto_bolivar());
            ps.setString(7, getTipo_gasto());
            ps.setString(8, getMoneda_dominante());
            ps.setDouble(9, getParidad());
            ps.setDouble(11, getMonto_dolar());
            ps.setDouble(10, getMonto_bolivar());
            ps.setDouble(12, uni.getAlicuota());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean registrar_interes(CerrarMes modc) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT registrar_interes(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, uni.getId());
            ps.setInt(ind++, gasto.getId());
            ps.setInt(ind++, getMes_cierre());
            ps.setInt(ind++, getAño_cierre());
            ps.setDouble(ind++, getMonto_dolar());
            ps.setDouble(ind++, getMonto_bolivar());
            ps.setString(ind++, getTipo_gasto());
            ps.setString(ind++, getMoneda_dominante());
            ps.setDouble(ind++, getParidad());
            ps.setDouble(ind++, getMonto_dolar());
            ps.setDouble(ind++, getMonto_bolivar());
            ps.setDouble(ind++, uni.getAlicuota());
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);
            }
        }
    }

    public boolean borrarnulo(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "DELETE FROM factura_unidad WHERE mes=? and anio=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getMes_cierre());
            ps.setInt(2, getAño_cierre());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizarGasto(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE gasto_comun SET estado=?	WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

//            ps.setInt(2, gas.getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizar_cuota(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE gasto SET estado=?, meses_restantes=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

            ps.setInt(2, gasto.getMesesRestantes());
            ps.setInt(3, gasto.getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizarSancion(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE sancion SET estado=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

            ps.setInt(2, gasto.getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizarTotalBolivar(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE recibo SET saldo_restante_bolivar=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1, getSaldo_restante_bs());

            ps.setInt(2, getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizarTotalDolar(CerrarMes modc) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE recibo SET saldo_restante_dolar=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1, getSaldo_restante_dolar());

            ps.setInt(2, getId());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public ArrayList<CerrarMes> listar() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT unidad.n_unidad, recibo.mes, recibo.anio, sum(recibo.monto_dolar), sum(recibo.monto_bolivar) FROM public.recibo inner join unidad on recibo.id_unidad=unidad.id group by unidad.n_unidad, recibo.mes, recibo.anio;";
        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.uni.setN_unidad(rs.getString(1));
                modc.setMes_cierre(rs.getInt(2));
                modc.setAño_cierre(rs.getInt(3));
                modc.setMonto_dolar(rs.getDouble(4));
                modc.setMonto_bolivar(rs.getDouble(5));

                listaCierremes.add(modc);
            }
        } catch (SQLException e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listarDominantes() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id_unidad,mes, anio, moneda_dominante FROM recibo where id_unidad=? group by mes, anio, moneda_dominante, id_unidad;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, uni.getId());
            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();
                modc.uni.setId(rs.getInt(1));
                modc.setMes_cierre(rs.getInt(2));
                modc.setAño_cierre(rs.getInt(3));
                modc.setMoneda_dominante(rs.getString(4));

                listaCierremes.add(modc);
            }
        } catch (SQLException e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public boolean buscarfechas(CerrarMes modc) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM recibo where mes=? and anio=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, modc.getMes_cierre());
            ps.setInt(2, modc.getAño_cierre());

            rs = ps.executeQuery();
            if (rs.next()) {

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public ArrayList<CerrarMes> listarpagospendientes(String moneda) {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;
        int x = 0;
        String sql = "";
        if (moneda.equals("Bolívar")) {
            sql = "SELECT mes, anio,sum(monto_bolivar),sum(monto_bolivar/?), sum(saldo_restante_bolivar),sum(saldo_restante_bolivar/?), moneda_dominante FROM recibo where id_unidad=? and mes=? and anio=? group by mes, anio, moneda_dominante having sum(saldo_restante_bolivar)>0;";
        } else {
            sql = "SELECT mes, anio,sum(monto_dolar*?),sum(monto_dolar), sum(saldo_restante_dolar*?),sum(saldo_restante_dolar), moneda_dominante FROM recibo where id_unidad=? and mes=? and anio=? group by mes, anio, moneda_dominante having sum(saldo_restante_dolar)>0;";

        }
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, getParidad());

            ps.setDouble(2, getParidad());
            ps.setInt(3, uni.getId());

            ps.setInt(4, getMes_cierre());

            ps.setInt(5, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMes_cierre(rs.getInt(1));
                modc.setAño_cierre(rs.getInt(2));
                modc.setMonto_dolar(rs.getDouble(3));
                modc.setMonto_bolivar(rs.getDouble(4));
                modc.setSaldo_restante_bs(rs.getDouble(5));
                modc.setSaldo_restante_dolar(rs.getDouble(6));
                modc.setMoneda_dominante(rs.getString(7));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listargastos(String moneda) {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;
        int x = 0;
        String sql = "";
        if (moneda.equals("Bolívar")) {
            sql = "SELECT recibo.mes, recibo.anio, proveedores.nombre, monto_bolivar/?, monto_bolivar, concepto_gasto.nom_concepto, proveedores.cedula, recibo.tipo_gasto  FROM public.recibo inner join gasto on gasto.id = recibo.id_gasto inner join proveedores on proveedores.cedula=gasto.id_proveedor  inner join puente_gasto_concepto on gasto.id = puente_gasto_concepto.id_gasto inner join concepto_gasto ON concepto_gasto.id = puente_gasto_concepto.id_concepto where recibo.id_unidad=? and recibo.mes=? and recibo.anio=? and (recibo.tipo_gasto='Ordinario' or recibo.tipo_gasto='Extraordinario' );";
        } else {
            sql = "SELECT recibo.mes, recibo.anio, proveedores.nombre, monto_dolar, monto_dolar*?, concepto_gasto.nom_concepto, proveedores.cedula, recibo.tipo_gasto  FROM public.recibo inner join gasto on gasto.id = recibo.id_gasto inner join proveedores on proveedores.cedula=gasto.id_proveedor  inner join puente_gasto_concepto on gasto.id = puente_gasto_concepto.id_gasto inner join concepto_gasto ON concepto_gasto.id = puente_gasto_concepto.id_concepto where recibo.id_unidad=? and recibo.mes=? and recibo.anio=? and (recibo.tipo_gasto='Ordinario' or recibo.tipo_gasto='Extraordinario' );";

        }
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, getParidad());

            ps.setInt(2, uni.getId());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMes_cierre(rs.getInt(1));
                modc.setAño_cierre(rs.getInt(2));
                modc.prove.setNombre(rs.getString(3));
                modc.setMonto_dolar(rs.getDouble(4));
                modc.setMonto_bolivar(rs.getDouble(5));
                modc.concep.setNombre(rs.getString(6));
                modc.prove.setCedula(rs.getString(7));
                modc.setTipo_gasto(rs.getString(8));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listarpagostotales(String moneda) {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;
        int x = 0;
        String sql = "";
        if (moneda.equals("Bolívar")) {
            sql = "SELECT mes, anio,sum(monto_bolivar),sum(monto_bolivar/?), sum(saldo_restante_bolivar),sum(saldo_restante_bolivar/?), moneda_dominante FROM recibo where id_unidad=? and mes=? and anio=? group by mes, anio, moneda_dominante;";
        } else {
            sql = "SELECT mes, anio,sum(monto_dolar*?),sum(monto_dolar), sum(saldo_restante_dolar*?),sum(saldo_restante_dolar), moneda_dominante FROM recibo where id_unidad=? and mes=? and anio=? group by mes, anio, moneda_dominante;";

        }
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, getParidad());

            ps.setDouble(2, getParidad());
            ps.setInt(3, uni.getId());

            ps.setInt(4, getMes_cierre());

            ps.setInt(5, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMes_cierre(rs.getInt(1));
                modc.setAño_cierre(rs.getInt(2));
                modc.setMonto_dolar(rs.getDouble(3));
                modc.setMonto_bolivar(rs.getDouble(4));
                modc.setSaldo_restante_bs(rs.getDouble(5));
                modc.setSaldo_restante_dolar(rs.getDouble(6));
                modc.setMoneda_dominante(rs.getString(7));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listarpagospagados(String moneda) {

        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;
        int x = 0;
        String sql = "";
        if (moneda.equals("Bolívar")) {
            sql = "SELECT mes, anio,sum(monto_bolivar),sum(monto_bolivar/?), sum(saldo_restante_bolivar),sum(saldo_restante_bolivar/?), moneda_dominante FROM recibo where id_unidad=? and mes=? and anio=? group by mes, anio, moneda_dominante having sum(saldo_restante_bolivar)=0;";
        } else {
            sql = "SELECT mes, anio,sum(monto_dolar*?),sum(monto_dolar), sum(saldo_restante_dolar*?),sum(saldo_restante_dolar), moneda_dominante FROM recibo where id_unidad=? and mes=? and anio=? group by mes, anio, moneda_dominante having sum(saldo_restante_dolar)=0;";

        }
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, getParidad());

            ps.setDouble(2, getParidad());
            ps.setInt(3, uni.getId());

            ps.setInt(4, getMes_cierre());

            ps.setInt(5, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMes_cierre(rs.getInt(1));
                modc.setAño_cierre(rs.getInt(2));
                modc.setMonto_dolar(rs.getDouble(3));
                modc.setMonto_bolivar(rs.getDouble(4));
                modc.setSaldo_restante_bs(rs.getDouble(5));
                modc.setSaldo_restante_dolar(rs.getDouble(6));
                modc.setMoneda_dominante(rs.getString(7));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public boolean buscarmesesdedeuda(CerrarMes modc) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT count(estado) as estado FROM public.factura_unidad where estado='Pendiente de Pago' and id_unidad=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, uni.getId());

            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setMeses_deuda(rs.getInt("estado"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public ArrayList<CerrarMes> listardetallesgastos() {
        ArrayList listadetallegasto = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id, mes, anio, monto_dolar, monto_bolivar, paridad, moneda_dominante, saldo_restante_bolivar, saldo_restante_dolar FROM recibo where id_unidad=? and mes=? and anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, uni.getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setId(rs.getInt(1));
                modc.setMes_cierre(rs.getInt(2));
                modc.setAño_cierre(rs.getInt(3));
                modc.setMonto_dolar(rs.getDouble(4));
                modc.setMonto_bolivar(rs.getDouble(5));
                modc.setParidad(rs.getDouble(6));
                modc.setMoneda_dominante(rs.getString(7));
                modc.setSaldo_restante_bs(rs.getDouble(8));
                modc.setSaldo_restante_dolar(rs.getDouble(9));

                listadetallegasto.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetallegasto;
    }

    public ArrayList<CerrarMes> listardetallessancion(String moneda) {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;
        int x = 0;
        String sql = "";
        if (moneda.equals("Bolívar")) {
            sql = "SELECT sancion.descripcion, sancion.tipo,  monto_bolivar/?, monto_bolivar,   sancion.monto  FROM public.recibo inner join sancion on recibo.id_gasto = sancion.id where recibo.id_unidad=? and recibo.mes=? and recibo.anio=? and recibo.tipo_gasto='Sancion';";
        } else {
            sql = "SELECT sancion.descripcion,sancion.tipo, monto_dolar, monto_dolar*?,   sancion.monto  FROM public.recibo inner join sancion on recibo.id_gasto = sancion.id where recibo.id_unidad=? and recibo.mes=? and recibo.anio=? and recibo.tipo_gasto='Sancion'";

        }
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, getParidad());

            ps.setInt(2, uni.getId());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.san.setDescripcion(rs.getString(1));
                modc.san.setTipo(rs.getString(2));
                modc.setMonto_dolar(rs.getDouble(3));
                modc.setMonto_bolivar(rs.getDouble(4));
                modc.san.setMonto(rs.getDouble(5));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listardetallesinteres(String moneda) {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        con = getConexion();
        ps = null;
        rs = null;
        int x = 0;
        String sql = "";
        if (moneda.equals("Bolívar")) {
            sql = "SELECT  interes.nombre, interes.factor,  monto_bolivar/?, monto_bolivar,  recibo.tipo_gasto  FROM public.recibo inner join interes on recibo.id_gasto = interes.id where recibo.id_unidad=? and recibo.mes=? and recibo.anio=? and recibo.tipo_gasto='Interes';";
        } else {
            sql = "SELECT interes.nombre, interes.factor,  monto_dolar, monto_dolar*?,  recibo.tipo_gasto  FROM public.recibo inner join interes on recibo.id_gasto = interes.id where recibo.id_unidad=? and recibo.mes=? and recibo.anio=? and recibo.tipo_gasto='Interes'";

        }
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, getParidad());

            ps.setInt(2, uni.getId());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.in.setNombre(rs.getString(1));
                modc.in.setFactor(rs.getDouble(2));
                modc.setMonto_dolar(rs.getDouble(3));
                modc.setMonto_bolivar(rs.getDouble(4));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public boolean bucaralicuota(CerrarMes modc) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT max(alicuota) FROM recibo where id_unidad=? and mes=? and anio=? ";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, uni.getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setAlicuota(rs.getDouble(1));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean buscId(CerrarMes modc) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT MAX(id) as id from cobro_unidad";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setId(rs.getInt("id"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public int getMeses_deuda() {
        return meses_deuda;
    }

    public void setMeses_deuda(int meses_deuda) {
        this.meses_deuda = meses_deuda;
    }

    public double getParidad() {
        return paridad;
    }

    public void setParidad(double paridad) {
        this.paridad = paridad;
    }

    public String getMoneda_dominante() {
        return moneda_dominante;
    }

    public void setMoneda_dominante(String moneda_dominante) {
        this.moneda_dominante = moneda_dominante;
    }

    public double getMonto_dolar() {
        return monto_dolar;
    }

    public void setMonto_dolar(double monto_dolar) {
        this.monto_dolar = monto_dolar;
    }

    public double getMonto_bolivar() {
        return monto_bolivar;
    }

    public void setMonto_bolivar(double monto_bolivar) {
        this.monto_bolivar = monto_bolivar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }

    public void setTipo_gasto(String tipo_gasto) {
        this.tipo_gasto = tipo_gasto;
    }

    public int getMes_cierre() {
        return mes_cierre;
    }

    public void setMes_cierre(int mes_cierre) {
        this.mes_cierre = mes_cierre;
    }

    public int getAño_cierre() {
        return año_cierre;
    }

    public void setAño_cierre(int año_cierre) {
        this.año_cierre = año_cierre;
    }

    public double getAlicuota() {
        return alicuota;
    }

    public void setAlicuota(double alicuota) {
        this.alicuota = alicuota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSaldo_restante_bs() {
        return saldo_restante_bs;
    }

    public void setSaldo_restante_bs(double saldo_restante_bs) {
        this.saldo_restante_bs = saldo_restante_bs;
    }

    public double getSaldo_restante_dolar() {
        return saldo_restante_dolar;
    }

    public void setSaldo_restante_dolar(double saldo_restante_dolar) {
        this.saldo_restante_dolar = saldo_restante_dolar;
    }

}
