package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ModeloConceptoGastos extends CategoriaGasto {

    private int id;
    private String nombre_Concepto;
    private String descripcion;
    private int id_categoria;
    private String nombreCategoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_Concepto() {
        return nombre_Concepto;
    }

    public void setNombre_Concepto(String nombre_Concepto) {
        this.nombre_Concepto = nombre_Concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public boolean registrarConcepto(ModeloConceptoGastos modConGas) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO concepto_Gasto (nom_concepto, descripcion, id_Categoria) VALUES (?, ?, ?);";
        System.out.println(sql);
        try {

            ps = con.prepareStatement(sql);
            
            ps.setString(1, getNombre_Concepto());
            ps.setString(2, getDescripcion());
            ps.setInt(3, getId_categoria());

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

    public boolean modificarConcepto(ModeloConceptoGastos modConGas) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE concepto_gasto SET nom_concepto=?, descripcion=?, id_categoria=? WHERE id=? ";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, getNombre_Concepto());
            ps.setString(2, getDescripcion());
            ps.setInt(3, getId_categoria());
            ps.setInt(4, getId());
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

    public boolean eliminar(ModeloConceptoGastos modConGas) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM concepto_gasto WHERE id=? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

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
    
    public boolean buscarC(ModeloConceptoGastos modC){
    
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = getConexion();
            
            String sql = "SELECT concepto_gasto.id, concepto_gasto.nom_concepto, concepto_gasto.descripcion, categoriagasto.nombre"
                    + " FROM concepto_gasto INNER JOIN categoriagasto ON categoriagasto.id=concepto_gasto.id_categoria"
                    + " WHERE nom_concepto=? ";
            
            try {
            
                ps = con.prepareStatement(sql);
                ps.setString(1, getNombre_Concepto());
                
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    
                    setId(rs.getInt("id"));
                    setNombre_Concepto(rs.getString("nom_concepto"));
                    setDescripcion(rs.getString("descripcion"));
                    setNombreCategoria(rs.getString("nombre"));

                    
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

    public ArrayList<ModeloConceptoGastos> listarConcepto() {
        ArrayList listaPersona = new ArrayList();
        ModeloConceptoGastos modConGas;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT nom_concepto, concepto_gasto.descripcion, categoriagasto.nombre FROM concepto_gasto "
                + "INNER JOIN categoriagasto ON concepto_gasto.id_categoria=categoriagasto.id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                modConGas = new ModeloConceptoGastos();

                modConGas.setNombre_Concepto(rs.getString(1));
                modConGas.setDescripcion(rs.getString(2));
                modConGas.setNombreCategoria(rs.getString(3));

                listaPersona.add(modConGas);
            }

        } catch (Exception e) {
        }

        return listaPersona;

    }
    
    public void llenar_concepto(JComboBox Concepto) {

//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT nom_concepto FROM concepto_gasto;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            Concepto.addItem("Seleccione el Concepto");

            while (result.next()) {

                Concepto.addItem(result.getString("nom_concepto"));
                

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
    
    public boolean buscarid(ModeloConceptoGastos modcon) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT id FROM concepto_gasto where nom_concepto=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modcon.getNombre_Concepto());
            rs = ps.executeQuery();
            if (rs.next()) {

                modcon.setId(rs.getInt("id"));
               

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
    
    

    

}
