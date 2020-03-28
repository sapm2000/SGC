package modelo;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Anthony
 */
/*en esta clase modelo. consultamos, modificamos, registramos y eliminamos datos desde la base de datos.*/
public class ConsultaPersona extends Conexion {

    /*aqui vamos a registrar los datos en la base de datos. enviadosdesde lavista  por medio del controlador.*/
    public boolean registrar(Persona prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO persona (clave, nombre, apellido, domicilio, telefono, fecha_nacimiento, genero,nacionalidad) VALUES(?,?,?,?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getClave());
            ps.setString(2, prs.getNombre());
            ps.setString(3, prs.getApellido());
            ps.setString(4, prs.getDomicilio());
            ps.setString(5, prs.getTelefono());
            ps.setString(6, prs.getFecha_nacimiento());
            ps.setString(7, prs.getGenero());
            ps.setString(8, prs.getNacionalidad());
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

    /*aqui vamos a modificar mediantela sentencia sql. losdatos de la BD */
    public boolean modificar(Persona prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE persona SET nombre=?, apellido=?, domicilio=?, telefono=?, fecha_nacimiento=?, genero=?, nacionalidad=? WHERE clave=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, prs.getNombre());
            ps.setString(2, prs.getApellido());
            ps.setString(3, prs.getDomicilio());
            ps.setString(4, prs.getTelefono());
            ps.setString(5, prs.getFecha_nacimiento());
            ps.setString(6, prs.getGenero());
            ps.setString(7, prs.getNacionalidad());

            ps.setString(8, prs.getClave());
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

    /*aqui decimos  mediante una sentencia sql que elimine el id que fue enviado desde la vista  por medio del controlador*/
    public boolean eliminar(Persona prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM persona WHERE clave=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getClave());
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

    /*aqui por medio de una sentencia sql buscamos los datos  por medio de lavariable clave=cedula*/
    public boolean buscar(Persona prs) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM persona WHERE Clave=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getClave());
            rs = ps.executeQuery();

            if (rs.next()) {

                prs.setClave(rs.getString("clave"));
                prs.setNombre(rs.getString("nombre"));
                prs.setApellido(rs.getString("apellido"));
                prs.setDomicilio(rs.getString("domicilio"));
                prs.setTelefono(rs.getString("telefono"));
                prs.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                prs.setGenero(rs.getString("genero"));
                prs.setNacionalidad(rs.getString("nacionalidad"));

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

    public ArrayList<Persona> lPerson(Persona prs) {
        ArrayList listaPersona = new ArrayList();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT clave, nombre, apellido, domicilio FROM persona";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                //prs = new Persona();
                prs.setClave(rs.getString(1));
                prs.setNombre(rs.getString(2));
                prs.setApellido(rs.getString(3));
                prs.setDomicilio(rs.getString(4));
                listaPersona.add(prs);
            }

        } catch (Exception e) {
        }

        return listaPersona;

    }

}
