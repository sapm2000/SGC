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

/**
 *
 * @author rma
 */
public class CuentasPorCobrar extends ConexionBD {

    private int id;
    public Unidades uni = new Unidades();
    private double monto;
    private String descripcion;
    private String id_cuenta;
    private String forma_pago;
    private String referencia;
    private int id_fondo;
    private java.sql.Date fecha;
    private double paridad;

    public double getParidad() {
        return paridad;
    }

    public void setParidad(double paridad) {
        this.paridad = paridad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getId_fondo() {
        return id_fondo;
    }

    public void setId_fondo(int id_fondo) {
        this.id_fondo = id_fondo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean registrarCobro(CuentasPorCobrar modcuen) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cobro_unidad(monto, descripcion, id_unidad, id_cuenta, forma_pago, referencia, fecha, id_fondo) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1, getMonto());
            ps.setString(2, getDescripcion());
            ps.setInt(3, uni.getId());
            ps.setString(4, getId_cuenta());
            ps.setString(5, getForma_pago());
            ps.setString(6, getReferencia());
            ps.setDate(7, getFecha());
            ps.setInt(8, getId_fondo());

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
