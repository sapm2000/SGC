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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author rma
 */
public class Unidades extends Propietarios {

    private String N_unidad;
    private String direccion;
    private String telefono;
    private String area;
    private String id_propietario;
    
    
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(String id_propietario) {
        this.id_propietario = id_propietario;
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
    
    public boolean buscarnombre(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT nombre,correo FROM propietarios where cedula=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getId_propietario());
            rs = ps.executeQuery();
            if (rs.next()) {

                moduni.setNombre(rs.getString("nombre"));
                moduni.setCorreo(rs.getString("correo"));
                

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
    
}
