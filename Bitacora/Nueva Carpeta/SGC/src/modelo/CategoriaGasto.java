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
import javax.swing.JOptionPane;

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

    public ArrayList<CategoriaGasto> lPerson() {
        ArrayList listaPersona = new ArrayList();
        CategoriaGasto CategoriaGasto;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, nombre, descripcion FROM categoriagasto;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaGasto = new CategoriaGasto();
                

                //prs = new Persona();
                CategoriaGasto.setId(rs.getInt(1));
                CategoriaGasto.setNombre(rs.getString(2));
                CategoriaGasto.setDescripcion(rs.getString(3));

                listaPersona.add(CategoriaGasto);
            }

        } catch (Exception e) {
        }

        return listaPersona;

    }

}
