/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

/**
 *
 * @author rma
 */
public class CerrarMes extends ConexionBD {

    private int id;
    private int mes_cierre;
    private int año_cierre;
    private double monto_dolar;
    private double monto_bolivar;
    public Unidades uni = new Unidades();
    public GastoComun gas = new GastoComun();
    public Sancion san = new Sancion();
    private double alicuota;
    private String estado;
    public Gasto cuo = new Gasto();
    public ModeloConceptoGastos concep = new ModeloConceptoGastos();
    public Proveedores prove = new Proveedores();
    private int meses_deuda;
    private double saldo_restante;
    private String tipo_gasto;
    public Gasto gasto = new Gasto();
    private double paridad;
    private String moneda_dominante;

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

    public double getSaldo_restante() {
        return saldo_restante;
    }

    public void setSaldo_restante(double saldo_restante) {
        this.saldo_restante = saldo_restante;
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

    public boolean registrar_cuota(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(id_gasto, mes, anio, id_unidad, monto_dolar,monto_bolivar, tipo_gasto, moneda_dominante, paridad) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, gasto.getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
            ps.setInt(4, uni.getId());
            ps.setDouble(5, getMonto_dolar());
            ps.setDouble(6, getMonto_bolivar());
            ps.setString(7, getTipo_gasto());
            ps.setString(8, getMoneda_dominante());
            ps.setDouble(9, getParidad());

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "";
        if (x == 1) {
            sql = "SELECT id_unidad, sum(monto_bolivar) as total FROM detalle_pagos where id_unidad=? and mes=? and anio=? and tipo_gasto != 'Interes' and tipo_gasto != 'Sancion'  group by id_unidad;";

        } else {
            sql = "SELECT id_unidad, sum(monto_dolar) as total FROM detalle_pagos where id_unidad=? and mes=? and anio=? and tipo_gasto != 'Interes' and tipo_gasto != 'Sancion'  group by id_unidad;";

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
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

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(id_unidad,id_gasto, mes, anio, monto_dolar,monto_bolivar, tipo_gasto, moneda_dominante, paridad) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?);";

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

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(id_unidad,id_gasto, mes, anio, monto_dolar,monto_bolivar, tipo_gasto, moneda_dominante, paridad) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?)";

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

        PreparedStatement ps = null;
        Connection con = getConexion();

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

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE gasto_comun SET estado=?	WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

            ps.setInt(2, gas.getId());
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

        PreparedStatement ps = null;
        Connection con = getConexion();

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

        PreparedStatement ps = null;
        Connection con = getConexion();

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

    public boolean actualizartotal(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE factura_unidad SET saldo_restante=?, estado=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1, getSaldo_restante());
            ps.setString(2, getEstado());

            ps.setInt(3, getId());
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

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT unidad.n_unidad, detalle_pagos.mes, detalle_pagos.anio, sum(detalle_pagos.monto_dolar), sum(detalle_pagos.monto_bolivar) FROM public.detalle_pagos inner join unidad on detalle_pagos.id_unidad=unidad.id group by unidad.n_unidad, detalle_pagos.mes, detalle_pagos.anio;";
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

    public boolean buscarfechas(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM detalle_pagos where mes=? and anio=?";

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

    public ArrayList<CerrarMes> listarpagos() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, monto, mes, anio, alicuota, estado, saldo_restante FROM factura_unidad where id_unidad=? order by anio,mes";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, uni.getId());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.gas.setId(rs.getInt(1));
                modc.setMonto(rs.getDouble(2));
                modc.setMes_cierre(rs.getInt(3));
                modc.setAño_cierre(rs.getInt(4));
                modc.setAlicuota(rs.getDouble(5));
                modc.setEstado(rs.getString(6));
                modc.setSaldo_restante(rs.getDouble(7));

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

    public ArrayList<CerrarMes> listarpagospendientes() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, monto, mes, anio, alicuota, estado, saldo_restante FROM factura_unidad where id_unidad=? and estado='Pendiente de Pago' order by anio,mes";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, uni.getId());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.gas.setId(rs.getInt(1));
                modc.setMonto(rs.getDouble(2));
                modc.setMes_cierre(rs.getInt(3));
                modc.setAño_cierre(rs.getInt(4));
                modc.setAlicuota(rs.getDouble(5));
                modc.setEstado(rs.getString(6));
                modc.setSaldo_restante(rs.getDouble(7));

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

    public ArrayList<CerrarMes> listarpagospagados() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, monto, mes, anio, alicuota, estado, saldo_restante FROM factura_unidad where id_unidad=? and estado='Pagado' order by anio,mes";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, uni.getId());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.gas.setId(rs.getInt(1));
                modc.setMonto(rs.getDouble(2));
                modc.setMes_cierre(rs.getInt(3));
                modc.setAño_cierre(rs.getInt(4));
                modc.setAlicuota(rs.getDouble(5));
                modc.setEstado(rs.getString(6));
                modc.setSaldo_restante(rs.getDouble(7));

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
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

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT concepto_gasto.nom_concepto, detalle_pagos.monto, proveedores.cedula, proveedores.nombre, gasto_comun.tipo  FROM detalle_pagos INNER join gasto_comun on detalle_pagos.id_gasto = gasto_comun.id and tipo_gasto='Gasto comun' INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto INNER join proveedores on gasto_comun.id_proveedor=proveedores.cedula where detalle_pagos.id_factura=? and detalle_pagos.mes=? and detalle_pagos.anio=? order by gasto_comun.tipo;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.concep.setNombre_Concepto(rs.getString(1));
                modc.setMonto(rs.getDouble(2));
                modc.prove.setCedula(rs.getString(3));
                modc.prove.setNombre(rs.getString(4));
                modc.gas.setTipo_gasto(rs.getString(5));

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

    public ArrayList<CerrarMes> listardetallescuotas() {
        ArrayList listadetallecuotas = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT concepto_gasto.nom_concepto, detalle_pagos.monto, proveedores.cedula, proveedores.nombre, facturas_proveedores.n_mese_restante FROM detalle_pagos inner join facturas_proveedores on detalle_pagos.id_gasto=facturas_proveedores.id  and tipo_gasto='Cuota especial' inner join concepto_gasto on facturas_proveedores.id_concepto=concepto_gasto.id inner join proveedores on proveedores.cedula=facturas_proveedores.id_proveedor where id_factura=?  and detalle_pagos.mes=? and detalle_pagos.anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.concep.setNombre_Concepto(rs.getString(1));
                modc.setMonto(rs.getDouble(2));
                modc.prove.setCedula(rs.getString(3));
                modc.prove.setNombre(rs.getString(4));
                modc.gasto.setMesesRestantes(rs.getInt(5));

                listadetallecuotas.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetallecuotas;
    }

    public ArrayList<CerrarMes> listardetallessancion() {
        ArrayList listadetallesancion = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT detalle_pagos.monto, sancion.monto, sancion.tipo, sancion.descripcion FROM detalle_pagos inner join sancion on detalle_pagos.id_gasto=sancion.id and tipo_gasto='Sancion' where detalle_pagos.id_factura=? and detalle_pagos.mes=? and detalle_pagos.anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMonto(rs.getDouble(1));
                modc.setAlicuota(rs.getDouble(2));
                modc.san.setTipo(rs.getString(3));
                modc.setEstado(rs.getString(4));

                listadetallesancion.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetallesancion;
    }

    public ArrayList<CerrarMes> listardetallesinteres() {
        ArrayList listadetalleinteres = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT  detalle_pagos.monto, interes.factor, interes.nombre FROM detalle_pagos INNER join interes on detalle_pagos.id_gasto = interes.id and tipo_gasto='Interes' where detalle_pagos.id_factura=? and detalle_pagos.mes=? and detalle_pagos.anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMonto(rs.getDouble(1));
                modc.setAlicuota(rs.getDouble(2));
                modc.setEstado(rs.getString(3));

                listadetalleinteres.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetalleinteres;
    }

    public boolean bucartotal(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT monto, alicuota FROM factura_unidad where id_unidad=? and mes=? and anio=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, uni.getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());

            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setMonto(rs.getDouble("monto"));
                modc.setAlicuota(rs.getDouble("alicuota"));

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
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

    public boolean guardarpuentepagos(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_cobro_factura(id_total, id_cobro, parte_monto) VALUES (?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, gas.getId());
            ps.setInt(2, getId());
            ps.setDouble(3, getSaldo_restante());

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
}
