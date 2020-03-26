
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
    public boolean registrar(Persona prs){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        
        
        String sql = "INSERT INTO persona (clave, nombre, domicilio, telefono, correo_electronico, fecha_nacimiento, genero,nacionalidad) VALUES(?,?,?,?,?,?,?,?)";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getClave());
            ps.setString(2, prs.getNombre());
            ps.setString(3, prs.getDomicilio());
            ps.setString(4, prs.getTelefono());
            ps.setString(5, prs.getCorreo_electronico());
            ps.setString(6, prs.getFecha_nacimiento());
            ps.setString(7, prs.getGenero());
            ps.setString(8, prs.getNacionalidad());
            
            ps.execute();
            return true;

        } catch (SQLException e) {
            
            System.err.println(e);
            return false;
            
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
       
    }
    /*aqui vamos a modificar mediantela sentencia sql. losdatos de la BD */
    public boolean modificar(Persona prs){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE persona SET clave=?, nombre=?, domicilio=?, telefono=?, correo_electronico=?, fecha_nacimiento=?, genero=?,nacionalidad=? WHERE id=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getClave());
            ps.setString(2, prs.getNombre());
            ps.setString(3, prs.getDomicilio());
            ps.setString(4, prs.getTelefono());
            ps.setString(5, prs.getCorreo_electronico());
            ps.setString(6, prs.getFecha_nacimiento());
            ps.setString(7, prs.getGenero());
            ps.setString(8, prs.getNacionalidad());
        
           
            ps.setInt(9, prs.getId());
            ps.execute();
            return true;

        } catch (SQLException e) {
            
            System.err.println(e);
            return false;
            
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
       
    }
    /*aqui decimos  mediante una sentencia sql que elimine el id que fue enviado desde la vista  por medio del controlador*/
    public boolean eliminar (Persona prs){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM persona WHERE id=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, prs.getId());
            ps.execute();
            return true;

        } catch (SQLException e) {
            
            System.err.println(e);
            return false;
            
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
       
    }
    /*aqui por medio de una sentencia sql buscamos los datos  por medio de lavariable clave=cedula*/
    public boolean buscar (Persona prs){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT * FROM persona WHERE Clave=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getClave());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                prs.setId( Integer.parseInt(rs.getString("id")));
                prs.setClave(rs.getString("clave"));
                prs.setNombre(rs.getString("nombre"));
                prs.setDomicilio(rs.getString("domicilio"));
                prs.setTelefono(rs.getString("telefono"));
                prs.setCorreo_electronico(rs.getString("correo_electronico"));
                prs.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                prs.setGenero(rs.getString("genero"));
                prs.setNacionalidad(rs.getString("nacionalidad"));
              
                return true;
            }
            
            return false;

        } catch (SQLException e) {
            
            System.err.println(e);
            return false;
            
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
    
       
    }

    
}