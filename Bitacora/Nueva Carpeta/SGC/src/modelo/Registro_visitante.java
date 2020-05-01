package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Registro_visitante extends ConexionBD {

    private String cedula;
    private String nombre;
    private String apellido;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean registrarVisitante(Registro_visitante modRvis) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO visitante( cedula, nombre, apellido  ) VALUES(?, ?, ?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getNombre());
            ps.setString(3, getApellido());

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

    public ArrayList<Registro_visitante> listarVisitante() {
        ArrayList listaVis = new ArrayList();
        Registro_visitante modRvis;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, nombre, apellido FROM visitante;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                modRvis = new Registro_visitante();

                modRvis.setCedula(rs.getString(1));
                modRvis.setNombre(rs.getString(2));
                modRvis.setApellido(rs.getString(3));

                listaVis.add(modRvis);
            }

        } catch (Exception e) {

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);

            }
        }

        return listaVis;
    }

    public boolean buscarVisitante(Registro_visitante modRvis) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM visitante WHERE cedula LIKE ? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + getCedula());

            rs = ps.executeQuery();

            if (rs.next()) {

                setCedula(rs.getString("cedula"));
                setNombre(rs.getString("nombre"));
                setApellido(rs.getString("apellido"));

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

}
