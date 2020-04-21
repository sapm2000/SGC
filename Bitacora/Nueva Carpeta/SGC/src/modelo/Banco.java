package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Banco extends ConexionBD {

    private int id;
    private String nombre_banco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_banco() {
        return nombre_banco;
    }

    public void setNombre_banco(String nombre_banco) {
        this.nombre_banco = nombre_banco;
    }

    public boolean registrar(Banco modban) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO banco (nombre_banco) VALUES(?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre_banco());
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

    public ArrayList<Banco> listar() {
        ArrayList listaBanco = new ArrayList();
        Banco modban;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM banco";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modban = new Banco();

                modban.setId(rs.getInt(1));
                modban.setNombre_banco(rs.getString(2));

                listaBanco.add(modban);
            }
        } catch (Exception e) {
        }

        return listaBanco;
    }

    public boolean buscar(Banco modban) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM banco WHERE nombre_banco=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modban.getNombre_banco());
            rs = ps.executeQuery();
            if (rs.next()) {

                modban.setId(rs.getInt("id"));
                modban.setNombre_banco(rs.getString("nombre_banco"));

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

    public boolean modificar(Banco modban) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE banco SET nombre_banco=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre_banco());

            ps.setInt(2, getId());
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
    
    public boolean eliminar(Banco modban){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM banco WHERE id=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
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
