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

/**
 *
 * @author rma
 */
public class FormaPago extends ConexionBD {

    private int id;
    private String forma_pago;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public boolean registrar(FormaPago modfor) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO forma_pago (forma_pago, activo) VALUES(?,TRUE);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getForma_pago());
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

    public ArrayList<FormaPago> listar() {
        ArrayList listaFormaPago = new ArrayList();
        FormaPago modfor;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM forma_pago where activo=TRUE";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modfor = new FormaPago();

                modfor.setId(rs.getInt(1));
                modfor.setForma_pago(rs.getString(2));

                listaFormaPago.add(modfor);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

            return listaFormaPago;
        }
    }

    public boolean buscar(FormaPago modfor) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM forma_pago WHERE forma_pago=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getForma_pago());
            rs = ps.executeQuery();
            if (rs.next()) {

                modfor.setId(rs.getInt("id"));
                modfor.setForma_pago(rs.getString("forma_pago"));

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
    
     public boolean buscarInactivo(FormaPago modfor) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM forma_pago WHERE forma_pago=? and activo=FALSE";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getForma_pago());
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

    public boolean modificar(FormaPago modfor) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE forma_pago SET forma_pago=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getForma_pago());

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

    public boolean eliminar(FormaPago modfor) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE forma_pago SET activo=FALSE WHERE id=?";

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

    public boolean activar(FormaPago modfor) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE forma_pago SET activo=TRUE WHERE forma_pago=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, getForma_pago());
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
