package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ModeloConceptoGastos extends ConexionBD {

    private int id;
    private String nombre_Concepto;
    private String descripcion;
    private CategoriaGasto cate = new CategoriaGasto();
    

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

   

    public boolean registrarConcepto(ModeloConceptoGastos modConGas) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO concepto_Gasto (nom_concepto, descripcion, id_Categoria, activo) VALUES (?, ?, ?, 1);";
        
        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, getNombre_Concepto());
            ps.setString(2, getDescripcion());
            ps.setInt(3, cate.getId());

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
            ps.setInt(3, cate.getId());
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

        String sql = "UPDATE concepto_gasto SET activo=0 WHERE id=? ";

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

    public boolean activar(ModeloConceptoGastos modConGas) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE concepto_gasto SET activo=1 WHERE id=? ";

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

    public boolean buscarC(ModeloConceptoGastos modC) {

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
                cate.setNombre(rs.getString("nombre"));

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

    public ArrayList<ModeloConceptoGastos> listarConcepto() {
        ArrayList listaPersona = new ArrayList();
        ModeloConceptoGastos modConGas;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT concepto_gasto.id, nom_concepto, concepto_gasto.descripcion, categoriagasto.nombre FROM concepto_gasto "
                + "INNER JOIN categoriagasto ON concepto_gasto.id_categoria=categoriagasto.id where concepto_gasto.activo=1 ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                modConGas = new ModeloConceptoGastos();

                modConGas.setId(rs.getInt(1));
                modConGas.setNombre_Concepto(rs.getString(2));
                modConGas.setDescripcion(rs.getString(3));
                modConGas.cate.setNombre(rs.getString(4));

                listaPersona.add(modConGas);
            }

        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaPersona;

    }
    
     public boolean Buscargas(ModeloConceptoGastos modCatGas) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM gasto_comun where id_concepto=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modCatGas.getId());

            rs = ps.executeQuery();

            if (rs.next()) {


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
     
      public boolean Buscarcuo(ModeloConceptoGastos modCatGas) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM cuotas_especiales where id_concepto=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modCatGas.getId());

            rs = ps.executeQuery();

            if (rs.next()) {


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
     
     public boolean Buscarconcepto(ModeloConceptoGastos modCatGas) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM categoriagasto where id=? and activo=1;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modCatGas.cate.getId());

            rs = ps.executeQuery();

            if (rs.next()) {


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


    public ArrayList<ModeloConceptoGastos> listarConcepto1() {
        ArrayList listaPersona = new ArrayList();
        ModeloConceptoGastos modConGas;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT concepto_gasto.id, nom_concepto, concepto_gasto.descripcion, categoriagasto.nombre, categoriagasto.id FROM concepto_gasto "
                + "INNER JOIN categoriagasto ON concepto_gasto.id_categoria=categoriagasto.id where concepto_gasto.activo=0";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                modConGas = new ModeloConceptoGastos();
                modConGas.setId(rs.getInt(1));
                modConGas.setNombre_Concepto(rs.getString(2));
                modConGas.setDescripcion(rs.getString(3));
                modConGas.cate.setNombre(rs.getString(4));
                 modConGas.cate.setId(rs.getInt(5));

                listaPersona.add(modConGas);
            }

        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

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
