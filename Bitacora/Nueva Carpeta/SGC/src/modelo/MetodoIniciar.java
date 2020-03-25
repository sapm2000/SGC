package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class MetodoIniciar extends ConexionBD{
  
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;

public ArrayList<Login> ini(String usuario, String password){
  
    Login login = null;
    ArrayList list = new ArrayList(); 
    
    try {
        
            Connection con = getConexion();
            
            if(con!=null){
            
                String sql = "SELECT usuario, password FROM usuario WHERE usuario=? AND password=?";
                
                ps = con.prepareStatement(sql);
                 ps.setString(1, usuario);
                ps.setString(2, password);
            
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    
            
                login.setUsuario(rs.getString("usuario"));
                login.setPassword(rs.getString("password"));
                    
                     list.add(login);
                    
                }
                
            }
        
    } catch (Exception e) {
        
        JOptionPane.showInputDialog(null, "error"+e, "error", JOptionPane.ERROR_MESSAGE );
        
    }finally{
    
        try {
            
            con.close();
            
        } catch (Exception e) {
        }
        
    }
    
    
    return list;
}
       
}
