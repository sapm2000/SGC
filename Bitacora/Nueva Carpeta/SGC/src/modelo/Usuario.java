package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario extends ConexionBD {

    private int id;
    private String usuario;
    private String password;
    private String pregunta;
    private String respuesta;
    private Persona persona;

    public boolean login() {

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT login(?,?)";

        try {

            int i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getUsuario());
            ps.setString(i++, getPassword());

            rs = ps.executeQuery();
            
            boolean result = false;

            while (rs.next()) {
                
                result = rs.getBoolean(1);
                
            }

         return result;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
            
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
