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
public class Propietarios extends ConexionBD {

    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String id_condominio;

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId_condominio() {
        return id_condominio;
    }

    public void setId_condominio(String id_condominio) {
        this.id_condominio = id_condominio;
    }

    public boolean registrar(Propietarios modpro) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO propietarios(cedula, nombre, apellido, telefono, correo,id_condominio) VALUES (?, ?, ?, ?, ?,?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getNombre());
            ps.setString(3, getApellido());
            ps.setString(4, getTelefono());
            ps.setString(5, getCorreo());
            ps.setString(6, getId_condominio());

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

    public ArrayList<Propietarios> listar() {
        ArrayList listaPropietarios = new ArrayList();
        Propietarios modpro;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, nombre, apellido, telefono, correo, id_condominio FROM propietarios WHERE id_condominio=?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Propietarios();

                modpro.setCedula(rs.getString(1));
                modpro.setNombre(rs.getString(2));
                modpro.setApellido(rs.getString(3));
                modpro.setTelefono(rs.getString(4));
                modpro.setCorreo(rs.getString(5));
                modpro.setId_condominio(rs.getString(6));

                listaPropietarios.add(modpro);
            }
        } catch (Exception e) {
        }

        return listaPropietarios;
    }
    
    public boolean buscar(Propietarios modpro) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM propietarios WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedula());
            rs = ps.executeQuery();
            if (rs.next()) {

                modpro.setNombre(rs.getString("nombre"));
                modpro.setCorreo(rs.getString("correo"));
                modpro.setApellido(rs.getString("apellido"));
                
                modpro.setTelefono(rs.getString("telefono"));

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
    
    public boolean modificar(Propietarios modpro) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE propietarios SET nombre=?, apellido=?, telefono=?, correo=? WHERE cedula=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getApellido());
            ps.setString(3, getTelefono());
            ps.setString(4, getCorreo());
            
            

            ps.setString(5, getCedula());
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
    
    public boolean eliminar(Propietarios modpro){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM propietarios WHERE cedula=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            
           System.err.println(e);
           return false;
            
        }finally{
            try {
                
                con.close();
                
            }catch (SQLException e) {
            
           System.err.println(e);
           
            }
        
        }
        
     }  

}
