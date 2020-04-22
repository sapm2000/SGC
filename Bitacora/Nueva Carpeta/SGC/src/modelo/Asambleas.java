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
import java.util.Date;

/**
 *
 * @author rma
 */
public class Asambleas extends Propietarios {

    private int id;
    private String nombre_asamblea;
    private java.sql.Date fecha;
    private String descripcion;
    
    private String id_propietario;
    private int n_asistentes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_asamblea() {
        return nombre_asamblea;
    }

    public void setNombre_asamblea(String nombre_asamblea) {
        this.nombre_asamblea = nombre_asamblea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public String getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(String id_propietario) {
        this.id_propietario = id_propietario;
    }

    public int getN_asistentes() {
        return n_asistentes;
    }

    public void setN_asistentes(int n_asistentes) {
        this.n_asistentes = n_asistentes;
    }

    public boolean registrarAsambleas(Asambleas modasa) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO asambleas(nombre, fecha, descripcion, id_condominio) VALUES (?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modasa.getNombre_asamblea());
            ps.setDate(2, modasa.getFecha());
            ps.setString(3, modasa.getDescripcion());
            ps.setString(4, modasa.getId_condominio());

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

    public boolean buscId(Asambleas modasa) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from asambleas";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modasa.setId(rs.getInt("id"));

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

    public boolean registrar_asamblea_propietario(Asambleas modasa) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_asamblea_propietario(id_asamblea, id_propietario) VALUES (?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setString(2, getId_propietario());

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

    public ArrayList<Asambleas> listarAsambleas() {
        ArrayList listaasambleas = new ArrayList();
        Asambleas modasa;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT asambleas.id, nombre, fecha, descripcion, count(id_asamblea) as nº FROM asambleas inner join puente_asamblea_propietario on puente_asamblea_propietario.id_asamblea=asambleas.id where id_condominio=? group by puente_asamblea_propietario.id_asamblea, asambleas.id";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            rs = ps.executeQuery();

            while (rs.next()) {

                modasa = new Asambleas();

                modasa.setId(rs.getInt(1));
                modasa.setNombre_asamblea(rs.getString(2));
                modasa.setFecha(rs.getDate(3));
                modasa.setDescripcion(rs.getString(4));
                modasa.setN_asistentes(rs.getInt(5));

                listaasambleas.add(modasa);
            }
        } catch (Exception e) {
        }

        return listaasambleas;
    }

    public boolean buscarAsambleas(Asambleas modasa) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT nombre, fecha, descripcion FROM asambleas where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modasa.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modasa.setNombre_asamblea(rs.getString("nombre"));
                modasa.setFecha(rs.getDate("fecha"));
                modasa.setDescripcion(rs.getString("descripcion"));

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

    public ArrayList<Asambleas> listarpropietariosmod() {
        ArrayList listapropmod = new ArrayList();
        Asambleas modasa = new Asambleas();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select propietarios.cedula, propietarios.nombre, propietarios.apellido, propietarios.telefono, propietarios.correo, id_asamblea from propietarios left join puente_asamblea_propietario on puente_asamblea_propietario.id_propietario=propietarios.cedula and puente_asamblea_propietario.id_asamblea=? where id_condominio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setString(2,getId_condominio());
            rs = ps.executeQuery();

            while (rs.next()) {
                modasa = new Asambleas();

                //prs = new Persona();
                modasa.setCedula(rs.getString("cedula"));
                modasa.setNombre(rs.getString("nombre"));
                modasa.setApellido(rs.getString("apellido"));
                modasa.setTelefono(rs.getString("telefono"));
                modasa.setCorreo(rs.getString("correo"));
                modasa.setId(rs.getInt("id_asamblea"));

                listapropmod.add(modasa);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listapropmod;

    }
    
    public boolean modificarAsamblea(Asambleas modasa) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE asambleas SET nombre=?, fecha=?, descripcion=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre_asamblea());
            ps.setDate(2, getFecha());
            ps.setString(3, getDescripcion());
            ps.setInt(4, getId());

            
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
    
    public boolean borrarpuenteasamblea(Asambleas modasa){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM puente_asamblea_propietario WHERE id_asamblea=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
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

