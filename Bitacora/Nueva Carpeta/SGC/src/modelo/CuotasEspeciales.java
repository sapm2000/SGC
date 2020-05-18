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
import static sgc.SGC.condominioActual;
import sgc.SGC;

/**
 *
 * @author rma
 */
public class CuotasEspeciales extends ConexionBD {

    private int id;
    public Proveedores prov = new Proveedores();
    public ModeloConceptoGastos concep = new ModeloConceptoGastos();
    private String Calcular;
    private int mes;
    private int año;
    private Double monto;
    private Double saldo;
    private int n_meses;
    public Asambleas asa = new Asambleas();
    private String observacion;
    private String estado;
   
    private int n_meses_restantes;
    private java.sql.Date fecha;
    private String pagado;

    public boolean registrar_cuota_especial(CuotasEspeciales modcuo) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cuotas_especiales(id_proveedor, n_mese_restante, id_concepto, calcular, mes, anio, monto, saldo, n_meses, id_asamblea, observacion, estado, id_condominio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prov.getCedula());
            ps.setInt(2, getN_meses_restantes());
            ps.setInt(3, concep.getId());
            ps.setString(4, getCalcular());
            ps.setDouble(5, getMes());
            ps.setInt(6, getAño());
            ps.setDouble(7, getMonto());
            ps.setDouble(8, getSaldo());
            ps.setInt(9, getN_meses());
            ps.setInt(10, asa.getId());
            ps.setString(11, getObservacion());
            ps.setString(12, getEstado());
            ps.setString(13, SGC.condominioActual.getRif());
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

    public ArrayList<CuotasEspeciales> listarCuotasEspeciales(String status) {
        ArrayList listacuotasEspeciales = new ArrayList();
        CuotasEspeciales modcuo;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        if (status == "Pendiente" || status == "Pagado") {

            sql = "SELECT cuotas_especiales.id, id_proveedor, concepto_gasto.nom_concepto, calcular, mes, anio, monto, saldo, n_meses,"
                    + " asambleas.nombre, observacion, estado, n_mese_restante, pagado "
                    + "FROM cuotas_especiales inner join proveedores on proveedores.cedula=cuotas_especiales.id_proveedor "
                    + "inner join concepto_gasto on concepto_gasto.id=cuotas_especiales.id_concepto "
                    + "left join asambleas on asambleas.id = cuotas_especiales.id_asamblea where cuotas_especiales.id_condominio=? "
                    + "AND (pagado = '" + status + "')";

        } else if (status == "") {

            sql = "SELECT cuotas_especiales.id, id_proveedor, concepto_gasto.nom_concepto, calcular, mes, anio, monto, saldo, n_meses,"
                    + " asambleas.nombre, observacion, estado, n_mese_restante, pagado "
                    + "FROM cuotas_especiales inner join proveedores on proveedores.cedula=cuotas_especiales.id_proveedor "
                    + "inner join concepto_gasto on concepto_gasto.id=cuotas_especiales.id_concepto "
                    + "left join asambleas on asambleas.id = cuotas_especiales.id_asamblea where cuotas_especiales.id_condominio=? ";

        }

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {
                int i = 1;
                modcuo = new CuotasEspeciales();

                modcuo.setId(rs.getInt(i++));
                modcuo.prov.setCedula(rs.getString(i++));
                modcuo.concep.setNombre_Concepto(rs.getString(i++));
                modcuo.setCalcular(rs.getString(i++));
                modcuo.setMes(rs.getInt(i++));
                modcuo.setAño(rs.getInt(i++));
                modcuo.setMonto(rs.getDouble(i++));
                modcuo.setSaldo(rs.getDouble(i++));
                modcuo.setN_meses(rs.getInt(i++));
                modcuo.asa.setNombre_asamblea(rs.getString(i++));
                modcuo.setObservacion(rs.getString(i++));
                modcuo.setEstado(rs.getString(i++));
                modcuo.setN_meses_restantes(rs.getInt(i++));
                modcuo.setPagado(rs.getString(i++));

                listacuotasEspeciales.add(modcuo);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listacuotasEspeciales;
    }

    public ArrayList<CuotasEspeciales> listarCuotasEspecialescerrarmes() {
        ArrayList listacuotasEspeciales = new ArrayList();
        CuotasEspeciales modcuo;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cuotas_especiales.id, id_proveedor, concepto_gasto.nom_concepto, calcular, mes, anio, monto, saldo, n_meses, asambleas.nombre, observacion, estado, n_mese_restante FROM cuotas_especiales inner join proveedores on proveedores.cedula=cuotas_especiales.id_proveedor inner join concepto_gasto on concepto_gasto.id=cuotas_especiales.id_concepto left join asambleas on asambleas.id = cuotas_especiales.id_asamblea where cuotas_especiales.id_condominio=? and cuotas_especiales.n_mese_restante !=0";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {

                modcuo = new CuotasEspeciales();

                modcuo.setId(rs.getInt(1));
                modcuo.prov.setCedula(rs.getString(2));
                modcuo.concep.setNombre_Concepto(rs.getString(3));
                modcuo.setCalcular(rs.getString(4));
                modcuo.setMes(rs.getInt(5));
                modcuo.setAño(rs.getInt(6));
                modcuo.setMonto(rs.getDouble(7));
                modcuo.setSaldo(rs.getDouble(8));
                modcuo.setN_meses(rs.getInt(9));
                modcuo.asa.setNombre_asamblea(rs.getString(10));
                modcuo.setObservacion(rs.getString(11));
                modcuo.setEstado(rs.getString(12));
                modcuo.setN_meses_restantes(rs.getInt(13));

                listacuotasEspeciales.add(modcuo);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listacuotasEspeciales;
    }

    public boolean buscarCuotaEspecial(CuotasEspeciales modcuo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT id_proveedor, concepto_gasto.nom_concepto, calcular, mes, anio, monto, saldo, n_meses, asambleas.nombre, asambleas.fecha, observacion, estado FROM cuotas_especiales inner join proveedores on proveedores.cedula=cuotas_especiales.id_proveedor inner join concepto_gasto on concepto_gasto.id=cuotas_especiales.id_concepto left join asambleas on asambleas.id = cuotas_especiales.id_asamblea where cuotas_especiales.id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modcuo.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modcuo.prov.setCedula(rs.getString("id_proveedor"));
                modcuo.concep.setNombre_Concepto(rs.getString("nom_concepto"));
                modcuo.setCalcular(rs.getString("calcular"));
                modcuo.setMes(rs.getInt("mes"));
                modcuo.setAño(rs.getInt("anio"));
                modcuo.setMonto(rs.getDouble("monto"));
                modcuo.setSaldo(rs.getDouble("saldo"));
                modcuo.setN_meses(rs.getInt("n_meses"));
                modcuo.asa.setNombre_asamblea(rs.getString("nombre"));
                modcuo.setObservacion(rs.getString("observacion"));
                modcuo.setEstado(rs.getString("estado"));
                modcuo.setFecha(rs.getDate("fecha"));

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

    public boolean modificar_cuota_especial(CuotasEspeciales modcuo) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE cuotas_especiales SET id_proveedor=?, n_mese_restante=?, id_concepto=?, calcular=?, mes=?, anio=?, monto=?, saldo=?, n_meses=?, id_asamblea=?, observacion=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prov.getCedula());
            ps.setInt(2, getN_meses_restantes());
            ps.setInt(3, concep.getId());
            ps.setString(4, getCalcular());
            ps.setInt(5, getMes());
            ps.setInt(6, getAño());
            ps.setDouble(7, getMonto());
            ps.setDouble(8, getSaldo());
            ps.setInt(9, getN_meses());
            ps.setInt(10, asa.getId());
            ps.setString(11, getObservacion());
            ps.setInt(12, getId());

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

    public boolean eliminar_cuotas_especiales(CuotasEspeciales modcuo) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM cuotas_especiales WHERE id=?;";

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

        String sql = "UPDATE cuotas_especiales SET saldo = saldo - ? WHERE id = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, saldoNuevo);
            ps.setInt(2, getId());
            ps.execute();

            sql = "SELECT actualizar_status_cuotas(?)";
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getN_meses_restantes() {
        return n_meses_restantes;
    }

    public void setN_meses_restantes(int n_meses_restantes) {
        this.n_meses_restantes = n_meses_restantes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public String getCalcular() {
        return Calcular;
    }

    public void setCalcular(String Calcular) {
        this.Calcular = Calcular;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public int getN_meses() {
        return n_meses;
    }

    public void setN_meses(int n_meses) {
        this.n_meses = n_meses;
    }

   
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

 

  

    public String getPagado() {
        return pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

}
