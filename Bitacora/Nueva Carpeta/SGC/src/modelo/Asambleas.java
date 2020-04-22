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
public class Asambleas extends ConexionBD {

    private int id;
    private String nombre_asamblea;
    private java.sql.Date fecha;
    private String descripcion;
    private String id_condominio;

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

    public String getId_condominio() {
        return id_condominio;
    }

    public void setId_condominio(String id_condominio) {
        this.id_condominio = id_condominio;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

   
    
    
    
   public boolean registrar(Asambleas modasa) {
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
}
