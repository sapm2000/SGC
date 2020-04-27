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
import javax.swing.JOptionPane;

/**
 *
 * @author rma
 */
public class Sancion extends Unidades {

    private String tipo;
    private int mes;
    private int año;
    private double monto;
    private String descripcion;
    private int id;
    private int cantidad_de_unidades;
    private String estado;
    private int id_sancion;

    public int getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(int id_sancion) {
        this.id_sancion = id_sancion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad_de_unidades() {
        return cantidad_de_unidades;
    }

    public void setCantidad_de_unidades(int cantidad_de_unidades) {
        this.cantidad_de_unidades = cantidad_de_unidades;
    }

    public boolean registrarsancion(Sancion modsan) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO sancion(tipo, mes, anio, monto, descripcion, id_condominio, estado) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modsan.getTipo());
            ps.setInt(2, modsan.getMes());
            ps.setInt(3, modsan.getAño());
            ps.setDouble(4, modsan.getMonto());
            ps.setString(5, modsan.getDescripcion());
            ps.setString(6, modsan.getId_condominio());
            ps.setString(7, modsan.getEstado());

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

    public boolean buscId(Sancion modsan) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from sancion";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modsan.setId(rs.getInt("id"));

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

    public boolean registrar_sancion_unidad(Sancion modsan) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_sancion_unidad(id_sancion, id_unidad) VALUES (?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setString(2, getN_unidad());

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

    public ArrayList<Sancion> listarSanciones() {
        ArrayList listaSancion = new ArrayList();
        Sancion modsan;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT sancion.id, tipo, mes, anio, monto, descripcion, id_condominio, count(id_sancion) as total, estado FROM sancion inner join puente_sancion_unidad on puente_sancion_unidad.id_sancion=sancion.id where sancion.id_condominio=? group by sancion.id";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            
            rs = ps.executeQuery();

            while (rs.next()) {

                modsan = new Sancion();

                modsan.setId(rs.getInt(1));
                modsan.setTipo(rs.getString(2));
                modsan.setMes(rs.getInt(3));
                modsan.setAño(rs.getInt(4));
                modsan.setMonto(rs.getDouble(5));
                modsan.setDescripcion(rs.getString(6));
                modsan.setId_condominio(rs.getString(7));
                modsan.setCantidad_de_unidades(rs.getInt(8));
                modsan.setEstado(rs.getString(9));
                

                listaSancion.add(modsan);
            }
        } catch (Exception e) {
        }

        return listaSancion;
    }

    public ArrayList<Sancion> listarSancionesCerrarmes() {
        ArrayList listaSancion = new ArrayList();
        Sancion modsan;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id_sancion, id_unidad, tipo, monto FROM puente_sancion_unidad inner join sancion on puente_sancion_unidad.id_sancion=sancion.id where sancion.id_condominio=? and mes=? and anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            ps.setInt(2, getMes());
            ps.setInt(3, getAño());
            rs = ps.executeQuery();

            while (rs.next()) {

                modsan = new Sancion();

                modsan.setId(rs.getInt(1));
                modsan.setN_unidad(rs.getString(2));
                modsan.setTipo(rs.getString(3));
                modsan.setMonto(rs.getDouble(4));
                

                listaSancion.add(modsan);
            }
        } catch (Exception e) {
        }

        return listaSancion;
    }

    public boolean buscarSancion(Sancion modsan) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM sancion where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modsan.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modsan.setTipo(rs.getString("tipo"));
                modsan.setMes(rs.getInt("mes"));
                modsan.setAño(rs.getInt("anio"));
                modsan.setMonto(rs.getDouble("monto"));
                modsan.setDescripcion(rs.getString("descripcion"));

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

    public ArrayList<Sancion> listarunidadesmod() {
        ArrayList listaunimod = new ArrayList();
        Sancion modsan = new Sancion();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select unidades.n_unidad, puente_sancion_unidad.id_sancion from unidades left join puente_sancion_unidad on puente_sancion_unidad.id_unidad=unidades.n_unidad and puente_sancion_unidad.id_sancion=? left join propietarios on propietarios.cedula=unidades.id_propietario where propietarios.id_condominio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

            ps.setString(2, getId_condominio());

            rs = ps.executeQuery();

            while (rs.next()) {
                modsan = new Sancion();

                //prs = new Persona();
                modsan.setN_unidad(rs.getString("n_unidad"));

                modsan.setId_sancion(rs.getInt("id_sancion"));

                listaunimod.add(modsan);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaunimod;

    }

    public boolean modificarSancion(Sancion modsan) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE sancion SET tipo=?, mes=?, anio=?, monto=?, descripcion=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getTipo());
            ps.setInt(2, getMes());
            ps.setInt(3, getAño());
            ps.setDouble(4, getMonto());
            ps.setString(5, getDescripcion());
            ps.setInt(6, getId());

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

    public boolean borrarpuentesancion(Sancion modsan) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM puente_sancion_unidad WHERE id_sancion=?";

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

    public boolean eliminarsancion(Sancion modsan) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM sancion WHERE id=?";

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

}
