/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

/**
 *
 * @author rma
 */
public class GastoComun extends ModeloConceptoGastos {

    private String tipo_gasto;
    private int mes;
    private int año;
    private double monto;
    private String numero_factura;
    private java.sql.Date fecha;
    private int id_concepto;
    private String id_proveedor;
    private String observaciones;
    private String estado;
    private String id_condominio;
    private double saldo;

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getId_condominio() {
        return id_condominio;
    }

    public void setId_condominio(String id_condominio) {
        this.id_condominio = id_condominio;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }

    public void setTipo_gasto(String tipo_gasto) {
        this.tipo_gasto = tipo_gasto;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean registrar_gasto_comun(GastoComun modgac) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO gasto_comun(tipo, mes, anio, monto, n_factura, id_proveedor, id_concepto, observaciones, fecha, estado,id_condominio,saldo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getTipo_gasto());
            ps.setInt(2, getMes());
            ps.setInt(3, getAño());
            ps.setDouble(4, getMonto());
            ps.setString(5, getNumero_factura());
            ps.setString(6, getId_proveedor());
            ps.setInt(7, getId_concepto());
            ps.setString(8, getObservaciones());
            ps.setDate(9, getFecha());
            ps.setString(10, getEstado());
            ps.setString(11, getId_condominio());
            ps.setDouble(12, getSaldo());
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

    public ArrayList<GastoComun> listarGastoComun(int status) {
        ArrayList listagastocomun = new ArrayList();
        GastoComun modgac;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        
        if (status == 1) { //Muestra los retgistro pagados
            sql = "SELECT gasto_comun.id, tipo, mes, anio, monto, n_factura, proveedores.cedula,  concepto_gasto.nom_concepto, observaciones, fecha, estado, saldo FROM gasto_comun inner join proveedores on proveedores.cedula=gasto_comun.id_proveedor INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto where gasto_comun.id_condominio=? AND estado = 'Pagado' OR estado = 'Procesado y pagado' ORDER by tipo desc, mes, anio;";
        } else if (status == 2) { //Muestra los registros sin pagar
            sql = "SELECT gasto_comun.id, tipo, mes, anio, monto, n_factura, proveedores.cedula,  concepto_gasto.nom_concepto, observaciones, fecha, estado, saldo FROM gasto_comun inner join proveedores on proveedores.cedula=gasto_comun.id_proveedor INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto where gasto_comun.id_condominio=? AND estado = 'Pendiente' OR estado = 'Procesado' ORDER by tipo desc, mes, anio;";
        }
        else if(status==3){ //Muewstra todos los registros
            sql = "SELECT gasto_comun.id, tipo, mes, anio, monto, n_factura, proveedores.cedula,  concepto_gasto.nom_concepto, observaciones, fecha, estado, saldo FROM gasto_comun inner join proveedores on proveedores.cedula=gasto_comun.id_proveedor INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto where gasto_comun.id_condominio=? ORDER by tipo desc, mes, anio;";
        }
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {

                modgac = new GastoComun();

                modgac.setId(rs.getInt(1));
                modgac.setTipo_gasto(rs.getString(2));
                modgac.setMes(rs.getInt(3));
                modgac.setAño(rs.getInt(4));
                modgac.setMonto(rs.getDouble(5));
                modgac.setNumero_factura(rs.getString(6));
                modgac.setId_proveedor(rs.getString(7));
                modgac.setNombre_Concepto(rs.getString(8));
                modgac.setObservaciones(rs.getString(9));
                modgac.setFecha(rs.getDate(10));
                modgac.setEstado(rs.getString(11));
                modgac.setSaldo(rs.getDouble(12));

                listagastocomun.add(modgac);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listagastocomun;
    }

    public ArrayList<GastoComun> listarGastoComuncierremes() {
        ArrayList listagastocomun = new ArrayList();
        GastoComun modgac;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT gasto_comun.id, tipo, mes, anio, monto, n_factura, proveedores.cedula,  concepto_gasto.nom_concepto, observaciones, fecha, estado, saldo FROM gasto_comun inner join proveedores on proveedores.cedula=gasto_comun.id_proveedor INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto where gasto_comun.id_condominio=? and mes=? and anio=?  ORDER by tipo desc, mes, anio;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            ps.setInt(2, getMes());
            ps.setInt(3, getAño());
            rs = ps.executeQuery();

            while (rs.next()) {

                modgac = new GastoComun();

                modgac.setId(rs.getInt(1));
                modgac.setTipo_gasto(rs.getString(2));
                modgac.setMes(rs.getInt(3));
                modgac.setAño(rs.getInt(4));
                modgac.setMonto(rs.getDouble(5));
                modgac.setNumero_factura(rs.getString(6));
                modgac.setId_proveedor(rs.getString(7));
                modgac.setNombre_Concepto(rs.getString(8));
                modgac.setObservaciones(rs.getString(9));
                modgac.setFecha(rs.getDate(10));
                modgac.setEstado(rs.getString(11));
                modgac.setSaldo(rs.getDouble(12));

                listagastocomun.add(modgac);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listagastocomun;
    }

    public boolean buscargastoComun(GastoComun modgac) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT gasto_comun.id, tipo, mes, anio, monto, n_factura, proveedores.cedula,  concepto_gasto.nom_concepto, observaciones, fecha, estado, saldo  FROM gasto_comun inner join proveedores on proveedores.cedula=gasto_comun.id_proveedor INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto where gasto_comun.id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modgac.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modgac.setTipo_gasto(rs.getString("tipo"));
                modgac.setMes(rs.getInt("mes"));
                modgac.setAño(rs.getInt("anio"));
                modgac.setMonto(rs.getDouble("monto"));
                modgac.setNumero_factura(rs.getString("n_factura"));
                modgac.setId_proveedor(rs.getString("cedula"));
                modgac.setNombre_Concepto(rs.getString("nom_concepto"));
                modgac.setObservaciones(rs.getString("observaciones"));
                modgac.setFecha(rs.getDate("fecha"));
                modgac.setEstado(rs.getString("estado"));
                modgac.setSaldo(rs.getDouble("saldo"));

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

    public boolean modificar_gasto_comun(GastoComun modgac) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE gasto_comun SET tipo=?, mes=?, anio=?, monto=?, n_factura=?, id_proveedor=?, id_concepto=?, observaciones=?, fecha=?, saldo=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getTipo_gasto());
            ps.setInt(2, getMes());
            ps.setInt(3, getAño());
            ps.setDouble(4, getMonto());
            ps.setString(5, getNumero_factura());
            ps.setString(6, getId_proveedor());
            ps.setInt(7, getId_concepto());
            ps.setString(8, getObservaciones());
            ps.setDate(9, getFecha());
            ps.setDouble(10, getSaldo());
            ps.setInt(11, getId());

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

    public boolean eliminar_gasto_comun(GastoComun modgac) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM gasto_comun WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

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

    public boolean restarSaldo(Float saldoNuevo) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE gasto_comun SET saldo = saldo - ? WHERE id = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, saldoNuevo);
            ps.setInt(2, getId());
            ps.execute();

            sql = "SELECT actualizar_status(?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }

}
