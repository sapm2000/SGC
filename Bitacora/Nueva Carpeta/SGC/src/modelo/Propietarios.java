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
import javax.swing.JOptionPane;

/**
 *
 * @author rma
 */
public class Propietarios extends ConexionBD {

    private String id2;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String id_condominio;
    private int cantidad;
    private java.sql.Date fecha_hasta;

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }
    
    

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
    

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

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

   
    
    

    public boolean registrar(Propietarios modpro) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO propietarios(cedula, nombre, apellido, telefono, correo, activo) VALUES (?, ?, ?, ?, ?, 1);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getNombre());
            ps.setString(3, getApellido());
            ps.setString(4, getTelefono());
            ps.setString(5, getCorreo());
          

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
    
    public boolean eliminarpuenteuni(Propietarios modpro) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE puente_unidad_propietarios SET fecha_hasta=?, estado=0 WHERE id_propietario=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDate(1, getFecha_hasta());
            ps.setString(2, getCedula());
            

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

        String sql = "SELECT cedula, nombre, apellido, telefono, correo FROM propietarios where activo=1";
        try {
            ps = con.prepareStatement(sql);
           
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Propietarios();

                modpro.setCedula(rs.getString(1));
                modpro.setNombre(rs.getString(2));
                modpro.setApellido(rs.getString(3));
                modpro.setTelefono(rs.getString(4));
                modpro.setCorreo(rs.getString(5));
               

                listaPropietarios.add(modpro);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaPropietarios;
    }
    
    public ArrayList<Propietarios> listarinactivos() {
        ArrayList listaPropietarios = new ArrayList();
        Propietarios modpro;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, nombre, apellido, telefono, correo FROM propietarios where activo=0";
        try {
            ps = con.prepareStatement(sql);
           
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Propietarios();

                modpro.setCedula(rs.getString(1));
                modpro.setNombre(rs.getString(2));
                modpro.setApellido(rs.getString(3));
                modpro.setTelefono(rs.getString(4));
                modpro.setCorreo(rs.getString(5));
               

                listaPropietarios.add(modpro);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaPropietarios;
    }
    
    public ArrayList<Propietarios> listarxcon() {
        ArrayList listaPropietarios = new ArrayList();
        Propietarios modpro;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, nombre, apellido, telefono, correo FROM propietarios inner join puente_unidad_propietarios on propietarios.cedula=puente_unidad_propietarios.id_propietario inner join unidades on puente_unidad_propietarios.id_unidad=unidades.id where id_condominio=? and propietarios.activo=1 group by cedula";
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
                

                listaPropietarios.add(modpro);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

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
    
    public boolean buscarunidadesasociadas(Propietarios modpro) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM puente_unidad_propietarios where id_propietario=? and activo=1;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedula());
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

    public boolean eliminar(Propietarios modpro) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE propietarios SET activo=0 WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
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
    
    public boolean activar(Propietarios modpro) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE propietarios SET activo=1 WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
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
