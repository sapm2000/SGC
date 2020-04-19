package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConceptoGastos extends ConexionBD{
    
    private int idConG;
    private String nombreConcepto;
    private String descripcion;
    private String categoria;

    public int getIdConG() {
        return idConG;
    }

    public void setIdConG(int idCong) {
        this.idConG = idConG;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
    public boolean registrar(ConceptoGastos modCg){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO ConceptoGastos (nombreConcepto, descripcion, categoria) VALUES(?, ?, ?);";
        
        try {
            
            
            ps = con.prepareStatement(sql);
            ps.setString(1, getNombreConcepto());
            ps.setString(2, getDescripcion());
            ps.setString(3, getCategoria());
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

public boolean modificar(ConceptoGastos modCg){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE ConceptoGastos SET nombreConcepto=?, descripcion=?, categoria=? WHERE idConG";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, getNombreConcepto());
            ps.setString(2, getDescripcion());
            ps.setString(3, getCategoria());
            ps.setInt(4, getIdConG());
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

     public boolean eliminar(ConceptoGastos modCg){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM ConceptoGastos WHERE idConG=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, getIdConG());
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
    
        public boolean buscar(ConceptoGastos modCg) {

        PreparedStatement ps = null;
             ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM ConceptoGastos WHERE nombreConcepto=?";
        
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, modCg.getNombreConcepto());
            rs = ps.executeQuery();
            if (rs.next()) {
            
                modCg.setIdConG(rs.getInt("idConG"));
                modCg.setNombreConcepto(rs.getString("nombreConcepto"));
                modCg.setDescripcion(rs.getString("descripcion"));
                modCg.setCategoria(rs.getString("categoria"));
                
                return true;
        } 
            
            return false;
            
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
     public ArrayList<ConceptoGastos> listar() {
            ArrayList listaConcepto = new ArrayList();
            ConceptoGastos modCg;
            
            Connection con = getConexion();
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            String sql = "SELECT idConG, nombreConcepto, descripcion, categoria FROM ConceptoGastos";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
         
                while (rs.next()) {
         
             
             modCg = new ConceptoGastos();
             
             modCg.setIdConG(rs.getInt(1));
             modCg.setNombreConcepto(rs.getString(2));
             modCg.setDescripcion(rs.getString(3));
             modCg.setCategoria(rs.getString(4));
             
             listaConcepto.add(modCg);
            }
        } catch (Exception e) {
        }
        
        return listaConcepto;
     }

 }