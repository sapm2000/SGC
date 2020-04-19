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
public class Cuenta extends ConexionBD {

    private String cedula;
    private String n_cuenta;
    private String beneficiario;
    private String tipo;
    private int id_banco;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getN_cuenta() {
        return n_cuenta;
    }

    public void setN_cuenta(String n_cuenta) {
        this.n_cuenta = n_cuenta;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public void llenar_banco(JComboBox banco) {

//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT nombre_banco FROM banco order by nombre_banco";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            banco.addItem("Seleccione el Banco");

            while (result.next()) {

                banco.addItem(result.getString("nombre_banco"));

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

    public boolean registrar(Cuenta modcu) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cuenta (cedula, n_cuenta, beneficiario, tipo, id_banco) VALUES (?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getN_cuenta());
            ps.setString(3, getBeneficiario());
            ps.setString(4, getTipo());
            ps.setInt(5, getId_banco());

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
