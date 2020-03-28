package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.UsuarioL;


public class metodosUsuario {
    
    Conexion con;
    
    public metodosUsuario(){
    
        con = new Conexion();
            
    }
    
    public ArrayList<UsuarioL> login(String usuario, String pass){
    
        Connection conectar=null;
        PreparedStatement ps;
        ResultSet rs;
        UsuarioL usuarioL;//nos permite la conexion de de los get y set.
        ArrayList list = new ArrayList();
        
                try {
                //Connection con = getConexion();
                 conectar = con.getConexion();
                 
                 if(conectar!=null){
                 
                            String  sql = "SELECT usuario, password FROM usuario WHERE usuario=? AND password=?";
                            
                            ps = conectar.prepareStatement(sql);
                            
                            ps.setString(1, usuario);
                            ps.setString(2, pass);
                            
                            rs = ps.executeQuery();
                            //aqui espesificamos que si los datos obtenidos por  el sql corresponde entre al sistema si no ejecutar hasta que los datos coincidadn con el sql
                            while(rs.next()){
                            
                                    usuarioL = new UsuarioL();
                                    
                                    usuarioL.setUsuario(rs.getString("usuario"));
                                    usuarioL.setUsuario(rs.getString("password"));
                                    
                                    list.add(usuarioL);
                                    
                            }
                 
                 }
                    
        } catch (Exception e) {
            
                    JOptionPane.showConfirmDialog(null, "error"+e, "error", JOptionPane.ERROR_MESSAGE );
            
        }finally{
                
                    try {
                            conectar.close();
                    } catch (SQLException e) {}
               
                    
                }
        
    
    
    return list;
}
    
}  