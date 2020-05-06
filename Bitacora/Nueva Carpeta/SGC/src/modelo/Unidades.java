/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.Date;
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
    private java.sql.Date fecha_desde;
    private java.sql.Date fecha_hasta;
    private int estatus;
    private String documento;
    private int id_puente;

    private double area;

    public int getId_puente() {
        return id_puente;
    }

    public void setId_puente(int id_puente) {
        this.id_puente = id_puente;
    }

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

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean buscId(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from unidades";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                moduni.setId(rs.getInt("id"));

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

    public boolean registrar_propietario_unidad(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_unidad_propietarios(id_propietario, id_unidad, fecha_desde, documento, estado) VALUES (?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setInt(2, getId());
            ps.setDate(3, getFecha_desde());

            ps.setString(4, getDocumento());
            ps.setInt(5, getEstatus());

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

    public ArrayList<Unidades> buscarPropietario() {
        ArrayList listaPropietario = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "	SELECT cedula, nombre, telefono, correo	FROM propietarios;";
        try {
            ps = con.prepareStatement(sql);

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

    public ArrayList<Unidades> buscarPropietariomod() {
        ArrayList listaPropietario = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, cedula, nombre, telefono, correo, id_unidad, fecha_desde, fecha_hasta, estado, documento FROM propietarios left join puente_unidad_propietarios on puente_unidad_propietarios.id_propietario=propietarios.cedula and id_unidad=? and not estado=0";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Unidades = new Unidades();

                //prs = new Persona();
                Unidades.setId_puente(rs.getInt("id"));
                Unidades.setCedula(rs.getString("cedula"));
                Unidades.setNombre(rs.getString("nombre"));
                Unidades.setTelefono(rs.getString("telefono"));
                Unidades.setCorreo(rs.getString("correo"));
                Unidades.setId(rs.getInt("id_unidad"));
                Unidades.setFecha_desde(rs.getDate("fecha_desde"));
                Unidades.setFecha_hasta(rs.getDate("fecha_hasta"));
                Unidades.setEstatus(rs.getInt("estado"));
                Unidades.setDocumento(rs.getString("documento"));

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

        String sql = "INSERT INTO unidades( n_unidad, direccion, area, id_condominio) VALUES (?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, moduni.getDireccion());
            ps.setDouble(3, moduni.getArea());

            ps.setString(4, moduni.getId_condominio());

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
        String sql = "SELECT id, n_unidad, direccion, area from unidades where n_unidad=? and id_condominio=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, moduni.getId_condominio());
            rs = ps.executeQuery();
            if (rs.next()) {
                moduni.setId(rs.getInt("id"));
                moduni.setDireccion(rs.getString("direccion"));

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
        String sql = "SELECT n_unidad, direccion, area from unidades  where n_unidad=? and id_condominio=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, moduni.getId_condominio());
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

    public boolean modificarUnidades(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE unidades SET direccion=?, area=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getDireccion());
            ps.setDouble(2, getArea());

            ps.setInt(3, getId());

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

    public boolean actualizardocumento(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE puente_unidad_propietarios SET documento=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getDocumento());
            ps.setInt(2, getId_puente());

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

    public boolean retirarpropietario(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE puente_unidad_propietarios SET fecha_hasta=?, estado=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDate(1, getFecha_hasta());
            ps.setInt(2, getEstatus());
            ps.setInt(3, getId_puente());

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
