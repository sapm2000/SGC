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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author rma
 */
public class Unidades extends Propietarios {

    private String N_unidad;
    private String direccion;
    
    private int area;
   

    public String getN_unidad() {
        return N_unidad;
    }

    public void setN_unidad(String N_unidad) {
        this.N_unidad = N_unidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

   

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    

    public void llenar_propietarios(JComboBox Propietarios) {

//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT cedula FROM propietarios where id_condominio=?;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            pst.setString(1, getId_condominio());
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            Propietarios.addItem("Seleccione la cedula");

            while (result.next()) {

                Propietarios.addItem(result.getString("cedula"));

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        } finally {

            if (conectar != null) {

                try {

                    conectar.close();
                    result.close();

                    conectar = null;
                    result = null;

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex);

                }

            }

        }

    }

    public ArrayList<Unidades> buscarPropietario() {
        ArrayList listaPropietario = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, nombre, telefono, correo	FROM propietarios where id_condominio=?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());

            rs = ps.executeQuery();

            while (rs.next()) {
                Unidades = new Unidades();

                //prs = new Persona();
                Unidades.setCedula(rs.getString("cedula"));
                Unidades.setNombre(rs.getString("nombre"));
                Unidades.setTelefono(rs.getString("telefono"));
                Unidades.setCorreo(rs.getString("correo"));

                listaPropietario.add(Unidades);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPropietario;

    }
    
    public boolean registrarUnidades(Unidades moduni) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO unidades( n_unidad, direccion, area, id_propietario) VALUES (?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, moduni.getDireccion());
            ps.setInt(3, moduni.getArea());
            ps.setString(4, moduni.getCedula());

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
