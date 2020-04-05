package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Condominio extends ConexionBD {

    private String rif;
    private String razonS;
    private String telefono;
    private String correoElectro;

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getRazonS() {
        return razonS;
    }

    public void setRazonS(String razonS) {
        this.razonS = razonS;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectro() {
        return correoElectro;
    }

    public void setCorreoElectro(String correoElectro) {
        this.correoElectro = correoElectro;
    }

    public boolean registrar(Condominio cond) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO condominio (rif, razon_social, telefono, correo_electronico) VALUES(?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, cond.getRif());
            ps.setString(2, cond.getRazonS());
            ps.setString(3, cond.getTelefono());
            ps.setString(4, cond.getCorreoElectro());

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

    public ArrayList<Condominio> lPerson() {
        ArrayList listaPersona = new ArrayList();
        Condominio Condominio;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT rif, razon_social, telefono, correo_electronico FROM condominio;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Condominio = new Condominio();

                //prs = new Persona();
                Condominio.setRif(rs.getString(1));
                Condominio.setRazonS(rs.getString(2));
                Condominio.setTelefono(rs.getString(3));
                Condominio.setCorreoElectro(rs.getString(4));
                listaPersona.add(Condominio);
            }

        } catch (Exception e) {
        }

        return listaPersona;

    }

    public boolean modificar(Condominio cond) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE condominio SET razon_social=?, telefono=?, correo_electronico=? WHERE rif=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, cond.getRazonS());
            ps.setString(2, cond.getTelefono());
            ps.setString(3, cond.getCorreoElectro());

            ps.setString(4, cond.getRif());
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
