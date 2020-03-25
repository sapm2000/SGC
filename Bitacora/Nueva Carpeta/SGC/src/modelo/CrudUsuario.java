package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUsuario extends ConexionBD {
    
    //Registrar. Desde la linea 11 a la  50  tenemos el metodo para insertar registro a la base de datos.
    
    public boolean registrar(Usuario usu){
    
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            String sql = "INSERT INTO usuario (cedula, usuario, password, nombre, apellido, tipo) VALUES(?,?,?,?,?,?)";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setInt(1, usu.getCedula());
                ps.setString(2, usu.getUsuario());
                ps.setString(3, usu.getPassword());
                ps.setString(4, usu.getNombre());
                ps.setString(5, usu.getApellido());
                ps.setString(6, usu.getTipo());
                
                ps.execute();
                
                return true;
                
        } catch (SQLException e) {
            
                System.err.println("e");
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println("e");
                
                }
                
            }

    }
    //Finaliza el metodo de registrar.
    //Modificar. Desde la linea 54 a la 93, Encontramos el Metodo de Modificar  los registro de la base de datos.
    
    public boolean modificar(Usuario usu){
    
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            String sql = "UPDATE usuario SET usuario=?, password=?, nombre=?, apellido=?, tipo=? WHERE cedula=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setInt(1, usu.getCedula());
                ps.setString(2, usu.getUsuario());
                ps.setString(3, usu.getPassword());
                ps.setString(4, usu.getNombre());
                ps.setString(5, usu.getApellido());
                ps.setString(6, usu.getTipo());
                
                ps.execute();
                
                return true;
                
        } catch (SQLException e) {
            
                System.err.println("e");
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println("e");
                
                }
                
            }

    }
     //fin de metodo Modificar;
     //Eliminar. Desde la linea 97 a la 131, Encontramos el Metodo de Eliminar registro de la base de datos.
    
     public boolean eliminar(Usuario usu){
    
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            String sql = "DELETE FROM usuario WHERE cedula=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setInt(1, usu.getCedula());
                                
                ps.execute();
                
                return true;
                
        } catch (SQLException e) {
            
                System.err.println("e");
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println("e");
                
                }
                
            }

    }
    //Fin del metodo Eliminar.
     //Buscar. Desde la linea 135 a la 183. Se encuentra el metodo Buscar.
     
      public boolean buscar(Usuario usu){
    
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = getConexion();
            
            String sql = "SELECT * FROM usuario WHERE cedula=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setInt(1, usu.getCedula());
             
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    
                    usu.setCedula( Integer.parseInt(rs.getString("cedula")));
                    usu.setUsuario(rs.getString("usuario"));
                    usu.setPassword(rs.getString("password"));
                    usu.setNombre(rs.getString("nombre"));
                    usu.setApellido(rs.getString("apellido"));
                    usu.setTipo(rs.getString("tipo"));
                    
                    return true;
                    
                }
                
                return false;
                
        } catch (SQLException e) {
            
                System.err.println("e");
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println("e");
                
                }
                
            }

    }
      //Fin del Metodo Buscar.
      
      }
