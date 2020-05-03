/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import static sgc.SGC.condominioActual;

/**
 *
 * @author rma
 */
public class Unidades extends Propietarios {

    private String N_unidad;
    private String direccion;
    private int id;

    private double area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getN_unidad() {
        return N_unidad;
    }

    public void setN_unidad(String N_unidad) {
        this.N_unidad = N_unidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void llenar_propietarios(JComboBox Propietarios) {

//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT cedula FROM propietarios where id_condominio=?;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            pst.setString(1, getId_condominio());
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            Propietarios.addItem("Seleccione la cedula");

            while (result.next()) {

                Propietarios.addItem(result.getString("cedula"));

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

    public ArrayList<Unidades> buscarPropietario() {
        ArrayList listaPropietario = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT cedula, nombre, telefono, correo	FROM propietarios where id_condominio=?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());

            rs = ps.executeQuery();

            while (rs.next()) {
                Unidades = new Unidades();

                //prs = new Persona();
                Unidades.setCedula(rs.getString("cedula"));
                Unidades.setNombre(rs.getString("nombre"));
                Unidades.setTelefono(rs.getString("telefono"));
                Unidades.setCorreo(rs.getString("correo"));

                listaPropietario.add(Unidades);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPropietario;

    }

    public boolean registrarUnidades(Unidades moduni) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO unidades( n_unidad, direccion, area, id_propietario, id_condominio) VALUES (?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, moduni.getDireccion());
            ps.setDouble(3, moduni.getArea());
            ps.setString(4, moduni.getCedula());
            ps.setString(5, moduni.getId_condominio());

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

    public ArrayList<Unidades> buscarUnidades() {
        ArrayList listaUnidades = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT unidades.n_unidad, unidades.direccion, unidades.area  from unidades  where unidades.id_condominio=?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());

            rs = ps.executeQuery();

            while (rs.next()) {
                Unidades = new Unidades();

                //prs = new Persona();
                Unidades.setN_unidad(rs.getString("n_unidad"));
                Unidades.setDireccion(rs.getString("direccion"));

                Unidades.setArea(rs.getInt("area"));

                listaUnidades.add(Unidades);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaUnidades;

    }

    public boolean buscarUnidad(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT unidades.n_unidad, unidades.direccion, unidades.area, propietarios.cedula, propietarios.nombre, propietarios.telefono, propietarios.correo from unidades inner join propietarios on unidades.id_propietario=propietarios.cedula where n_unidad=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            rs = ps.executeQuery();
            if (rs.next()) {

                moduni.setDireccion(rs.getString("direccion"));
                moduni.setCedula(rs.getString("cedula"));
                moduni.setNombre(rs.getString("nombre"));
                moduni.setTelefono(rs.getString("telefono"));
                moduni.setCorreo(rs.getString("correo"));
                moduni.setArea(rs.getInt("area"));

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

    public boolean buscarepe(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT unidades.n_unidad, unidades.direccion, unidades.area, propietarios.cedula, propietarios.nombre, propietarios.telefono, propietarios.correo from unidades inner join propietarios on unidades.id_propietario=propietarios.cedula where n_unidad=? and unidades.id_condominio=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, moduni.getId_condominio());
            rs = ps.executeQuery();
            if (rs.next()) {

                moduni.setId(rs.getInt("area"));

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

    public boolean modificarUnidades(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE unidades SET direccion=?, area=?, id_propietario=? WHERE n_unidad=? and id_condominio=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getDireccion());
            ps.setDouble(2, getArea());
            ps.setString(3, getCedula());

            ps.setString(4, getN_unidad());
            ps.setString(5, getId_condominio());
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

    public boolean eliminarUnidad(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM unidades WHERE n_unidad=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getN_unidad());
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

    public void llenar_unidades(JComboBox Unidades) {

//Creamos objeto tipo Connection    
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

//Creamos la Consulta SQL
        String sql = "SELECT n_unidad FROM unidades where id_condominio=?;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            //Ejecutamos la consulta
            rs = ps.executeQuery();

            //LLenamos nuestro ComboBox
            Unidades.addItem("Seleccione el numero de la unidad");

            while (rs.next()) {

                Unidades.addItem(rs.getString("n_unidad"));

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

    }

    public ArrayList<Unidades> listarCbxUnidad() {
        ArrayList listaUnidad = new ArrayList();
        Unidades uni;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT n_unidad, nombre, apellido FROM unidades "
                + "INNER JOIN propietarios ON id_propietario = cedula "
                + "WHERE unidades.id_condominio = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {
                uni = new Unidades();
                uni.setN_unidad(rs.getString("n_unidad"));
                uni.setNombre(rs.getString("nombre"));
                uni.setApellido(rs.getString("apellido"));

                listaUnidad.add(uni);
            }

        } catch (Exception e) {

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);

            }
        }

        return listaUnidad;

    }

}
