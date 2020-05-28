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
public class Interes extends ConexionBD {

    private int id;
    private String nombre;
    private double factor;

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

   

    public boolean registrarinteres(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO interes(nombre, factor, activo) VALUES (?, ?, true);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());

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

    public boolean buscId(Interes modin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from interes";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modin.setId(rs.getInt("id"));

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

    public ArrayList<Interes> listarInteres() {
        ArrayList listainteres = new ArrayList();
        Interes modin;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, nombre, factor FROM interes where activo=true;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modin = new Interes();

                modin.setId(rs.getInt(1));
                modin.setNombre(rs.getString(2));
                modin.setFactor(rs.getDouble(3));

                

                listainteres.add(modin);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listainteres;
    }

    public ArrayList<Interes> listarInteresCerrames() {
        ArrayList listainteres = new ArrayList();
        Interes modin;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select interes.id, interes.nombre, interes.factor, from interes inner join puente_interes_condominio on interes.id=puente_interes_condominio.id_interes where puente_interes_condominio.id_condominio=? and interes.activo=true group by interes.id,puente_interes_condominio.id_condominio";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {

                modin = new Interes();

                modin.setId(rs.getInt(1));
                modin.setNombre(rs.getString(2));
                modin.setFactor(rs.getDouble(3));

                listainteres.add(modin);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listainteres;
    }

    public boolean buscarInteres(Interes modin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT nombre, factor FROM interes where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modin.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modin.setNombre(rs.getString("nombre"));
                modin.setFactor(rs.getDouble("factor"));

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

    public boolean modificar_Interes(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE interes SET nombre=?, factor=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());

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

    public boolean buscarInactivo(Interes modin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM interes WHERE nombre=? and factor=? and activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());
            rs = ps.executeQuery();
            if (rs.next()) {
                 modin.setId(rs.getInt("id"));
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
    
    
    public boolean buscarduplicados(Interes modin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM interes WHERE nombre=? and factor=? and activo=true";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());
            rs = ps.executeQuery();
            if (rs.next()) {
                 modin.setId(rs.getInt("id"));
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

    public boolean eliminarInteres(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE interes SET activo=false WHERE id=?";

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

    public boolean activarInteres(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE interes SET activo=true WHERE id=?";

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
}
