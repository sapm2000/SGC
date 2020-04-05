package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrudUsuario extends ConexionBD {
    private String cedula;
    private String usuario;
    private String password;
    private String nombre;
    private String apellido;
    private String tipo;
    private String ntelefono;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNtelefono() {
        return ntelefono;
    }

    public void setNtelefono(String ntelefono) {
        this.ntelefono = ntelefono;
    }
    
    //Registrar. Desde la linea 11 a la  50  tenemos el metodo para insertar registro a la base de datos.
    
    public boolean registrar(CrudUsuario modC){
    
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            String sql = "INSERT INTO usuario (cedula, usuario, password, nombre, apellido, tipo, ntelefono) VALUES(?,?,?,?,?,?,?)";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setString(1, getCedula());
                ps.setString(2, getUsuario());
                ps.setString(3, getPassword());
                ps.setString(4, getNombre());
                ps.setString(5, getApellido());
                ps.setString(6, getTipo());
                ps.setString(7, getNtelefono());
                
                ps.execute();
                
                return true;
                
        } catch (SQLException e) {
            
                System.err.println(e);
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println(e);
                
                }
                
            }

    }
    //Finaliza el metodo de registrar.
    //Modificar. Desde la linea 54 a la 93, Encontramos el Metodo de Modificar  los registro de la base de datos.
    
    public boolean modificar(CrudUsuario modC){
    
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            String sql = "UPDATE usuario SET usuario=?, password=?, nombre=?, apellido=?, tipo=?, ntelefono=? WHERE cedula=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                
                ps.setString(1, getUsuario());
                ps.setString(2, getPassword());
                ps.setString(3, getNombre());
                ps.setString(4, getApellido());
                ps.setString(5, getTipo());
                ps.setString(6, getNtelefono());
                ps.setString(7, getCedula());
                ps.execute();
                
                return true;
                
        } catch (SQLException e) {
            
                System.err.println(e);
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println(e);
                
                }
                
            }

    }
     //fin de metodo Modificar;
     //Eliminar. Desde la linea 97 a la 131, Encontramos el Metodo de Eliminar registro de la base de datos.
    
     public boolean eliminar(CrudUsuario modC){
    
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            String sql = "DELETE FROM usuario WHERE cedula=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setString(1, getCedula());
                                
                ps.execute();
                
                return true;
                
        } catch (SQLException e) {
            
                System.err.println(e);
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println(e);
                
                }
                
            }

    }
    //Fin del metodo Eliminar.
     //Buscar. Desde la linea 135 a la 183. Se encuentra el metodo Buscar.
     
      public boolean buscar(CrudUsuario modC){
    
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = getConexion();
            
            String sql = "SELECT * FROM usuario WHERE cedula=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setString(1, getCedula());
                
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    
                    setCedula(rs.getString("cedula"));
                    setUsuario(rs.getString("usuario"));
                    setPassword(rs.getString("password"));
                    setNombre(rs.getString("nombre"));
                    setApellido(rs.getString("apellido"));
                    setTipo(rs.getString("tipo"));
                    setNtelefono(rs.getString("ntelefono"));
                    
                    return true;
                    
                }
                
                return false;
                
        } catch (SQLException e) {
            
                System.err.println(e);
                return false;
                
        } finally{
                try {
                    
                    con.close();
                    
                } catch (SQLException e) {
                
                    System.err.println(e);
                
                }
                
            }

    }
      //Fin del Metodo Buscar.
      
          public ArrayList<CrudUsuario> listar() {
        ArrayList listaPersona = new ArrayList();
        CrudUsuario modUsu;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, usuario, password, nombre, apellido, tipo, ntelefono FROM usuario;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
               modUsu = new CrudUsuario();

                //prs = new Persona();
                modUsu.setCedula(rs.getString(1));
                modUsu.setUsuario(rs.getString(2));
                modUsu.setPassword(rs.getString(3));
                modUsu.setNombre(rs.getString(4));
                modUsu.setApellido(rs.getString(5));
                modUsu.setNtelefono(rs.getString(6));
                modUsu.setTipo(rs.getString(7));

                listaPersona.add(modUsu);
            }

        } catch (Exception e) {
        }

        return listaPersona;

    }
      
      }
