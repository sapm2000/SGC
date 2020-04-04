/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author rma
 */
public class CategoriaGasto extends ConexionBD {

    private int id;
    private String nombre;
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean registrar(CategoriaGasto cga) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO categoriagasto (nombre, descripcion) VALUES(?,?)";
        
         try {

        ps = con.prepareStatement(sql);
        ps.setString(1, cga.getNombre());
        ps.setString(2, cga.getDescripcion());
        
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
