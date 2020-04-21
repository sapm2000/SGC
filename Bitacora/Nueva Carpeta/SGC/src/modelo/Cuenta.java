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
public class Cuenta extends Banco {

    private String cedula;
    private String n_cuenta;
    private String beneficiario;
    private String tipo;
    private int id_banco;
    private String id_condominio;
    private int cantidad;

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

    public String getId_condominio() {
        return id_condominio;
    }

    public void setId_condominio(String id_condominio) {
        this.id_condominio = id_condominio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public boolean registrar_cuenta_condominio(Cuenta modcu) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_condominio_cuenta(id_condominio, id_cuenta) VALUES (?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            ps.setString(2, getN_cuenta());

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

    public ArrayList<Cuenta> listarcuenta() {
        ArrayList listaCuenta = new ArrayList();
        Cuenta modcu;
        

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select cuenta.cedula, cuenta.n_cuenta, cuenta.beneficiario, cuenta.tipo, banco.nombre_banco, count(puente_condominio_cuenta.id_cuenta) as condominio  from cuenta inner join banco on banco.id=cuenta.id_banco inner join puente_condominio_cuenta on cuenta.n_cuenta=puente_condominio_cuenta.id_cuenta group by cuenta.n_cuenta,banco.nombre_banco";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modcu = new Cuenta();
               

                modcu.setCedula(rs.getString(1));
                modcu.setN_cuenta(rs.getString(2));
                modcu.setBeneficiario(rs.getString(3));
                modcu.setTipo(rs.getString(4));
                modcu.setNombre_banco(rs.getString(5));
                modcu.setCantidad(rs.getInt(6));

                listaCuenta.add(modcu);
            }
        } catch (Exception e) {
        }

        return listaCuenta;
    }

   public boolean buscarcuenta(Cuenta modcun) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT cuenta.*, banco.* FROM cuenta inner join banco on cuenta.id_banco=banco.id WHERE n_cuenta=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modcun.getN_cuenta());
            rs = ps.executeQuery();
            if (rs.next()) {

                modcun.setCedula(rs.getString("cedula"));
                modcun.setBeneficiario(rs.getString("beneficiario"));
                modcun.setTipo(rs.getString("tipo"));
                modcun.setNombre_banco(rs.getString("nombre_banco"));

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
   
    public boolean modificarcuenta(Cuenta modcu) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE cuenta SET cedula=?, beneficiario=?, tipo=?, id_banco=? WHERE n_cuenta=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getBeneficiario());
            ps.setString(3, getTipo());
            ps.setInt(4, getId_banco());

            ps.setString(5, getN_cuenta());
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
    
    public boolean borrarpuente(Cuenta modcun){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM puente_condominio_cuenta WHERE id_cuenta=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, getN_cuenta());
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
    
    public boolean eliminarcuenta(Cuenta modcun){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM cuenta WHERE n_cuenta=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, getN_cuenta());
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
