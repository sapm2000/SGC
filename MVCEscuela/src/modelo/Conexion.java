package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anthony
 */
public class Conexion {
    
    private final String base = "escuela";
    private final String user = "postgres";
    final String clave = "root";
    private final String url = "jdbc:postgresql://localhost:5432/" + base;
    private Connection con = null;
    
    public Connection getConexion(){
        
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(this.url, this.user, this.clave);
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
        
        
    }     
}
    

