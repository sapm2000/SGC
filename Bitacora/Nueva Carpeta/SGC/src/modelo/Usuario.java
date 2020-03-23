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

/**
 *
 * @author rma
 */
public class Usuario extends ConexionBD {

    private String cedula;
    private String usuario;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String telefono;
    private String tipo;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean registrar(Usuario prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO usuario (cedula, usuario, contraseña, nombre, apellido, telefono, tipo) VALUES(?,?,?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getCedula());
            ps.setString(2, prs.getUsuario());
            ps.setString(3, prs.getContraseña());
            ps.setString(4, prs.getNombre());
            ps.setString(5, prs.getApellido());
            ps.setString(6, prs.getTelefono());
            ps.setString(7, prs.getTipo());

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

    public boolean modificar(Usuario prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE usuario SET usuario=?, contraseña=?, nombre=?, apellido=?, telefono=?, tipo=? WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, prs.getUsuario());
            ps.setString(2, prs.getContraseña());
            ps.setString(3, prs.getNombre());
            ps.setString(4, prs.getApellido());
            ps.setString(5, prs.getTelefono());
            ps.setString(6, prs.getTipo());

            ps.setString(7, prs.getCedula());


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
    
    public boolean eliminar (Usuario prs){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM usuario WHERE cedula=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getCedula());
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
    
    public boolean buscar (Usuario prs){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT * FROM usuario WHERE cedula=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getCedula());
            rs = ps.executeQuery();
            
            if (rs.next()) {
             
                prs.setUsuario(rs.getString("usuario"));
                prs.setContraseña(rs.getString("contraseña"));
                prs.setNombre(rs.getString("nombre"));
                prs.setApellido(rs.getString("apellido"));
                prs.setTelefono(rs.getString("telefono"));
                prs.setTipo(rs.getString("tipo"));
             
              
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
