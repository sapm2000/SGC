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

/**
 *
 * @author rma
 */
public class Interes extends Condominio {

    private int id;
    private String nombre;
    private double factor;
    private String estado;
    private String id_condominio;
    private int n_condominios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_condominio() {
        return id_condominio;
    }

    public void setId_condominio(String id_condominio) {
        this.id_condominio = id_condominio;
    }

    public int getN_condominios() {
        return n_condominios;
    }

    public void setN_condominios(int n_condominios) {
        this.n_condominios = n_condominios;
    }

    public boolean registrarinteres(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO interes(nombre, factor, estado, activo) VALUES (?, ?, ?, 1);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());

            ps.setString(3, getEstado());

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

    public boolean buscId(Interes modin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from interes";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modin.setId(rs.getInt("id"));

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

    public boolean registrar_interes_condominio(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_interes_condominio(id_condominio, id_interes, activo) VALUES (?, ?, 1);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
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

    public ArrayList<Interes> listarInteres() {
        ArrayList listainteres = new ArrayList();
        Interes modin;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select interes.id, interes.nombre, interes.factor, interes.estado, count(puente_interes_condominio.id_interes) as condominio  from interes inner join puente_interes_condominio on interes.id=puente_interes_condominio.id_interes where interes.activo=1 group by interes.id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modin = new Interes();

                modin.setId(rs.getInt(1));
                modin.setNombre(rs.getString(2));
                modin.setFactor(rs.getDouble(3));
                modin.setEstado(rs.getString(4));
                modin.setN_condominios(rs.getInt(5));

                listainteres.add(modin);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listainteres;
    }
    
    public ArrayList<Interes> listarInteresinactivo() {
        ArrayList listainteres = new ArrayList();
        Interes modin;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select interes.id, interes.nombre, interes.factor, interes.estado, count(puente_interes_condominio.id_interes) as condominio  from interes inner join puente_interes_condominio on interes.id=puente_interes_condominio.id_interes where interes.activo=0 group by interes.id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modin = new Interes();

                modin.setId(rs.getInt(1));
                modin.setNombre(rs.getString(2));
                modin.setFactor(rs.getDouble(3));
                modin.setEstado(rs.getString(4));
                modin.setN_condominios(rs.getInt(5));

                listainteres.add(modin);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listainteres;
    }

    public ArrayList<Interes> listarInteresCerrames() {
        ArrayList listainteres = new ArrayList();
        Interes modin;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select interes.id, interes.nombre, interes.factor, interes.estado from interes inner join puente_interes_condominio on interes.id=puente_interes_condominio.id_interes where puente_interes_condominio.id_condominio=? group by interes.id,puente_interes_condominio.id_condominio";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            rs = ps.executeQuery();

            while (rs.next()) {

                modin = new Interes();

                modin.setId(rs.getInt(1));
                modin.setNombre(rs.getString(2));
                modin.setFactor(rs.getDouble(3));
                modin.setEstado(rs.getString(4));

                listainteres.add(modin);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listainteres;
    }

    public boolean buscarInteres(Interes modin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT nombre, factor, estado FROM interes where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modin.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modin.setNombre(rs.getString("nombre"));
                modin.setFactor(rs.getInt("factor"));
                modin.setEstado(rs.getString("estado"));

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

    public ArrayList<Interes> interescondominiomodificar() {
        ArrayList listainteresmod = new ArrayList();
        Interes modin;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT rif , razon_social, puente_interes_condominio.id_interes as cuenta FROM condominio left join puente_interes_condominio on puente_interes_condominio.id_condominio=condominio.rif and puente_interes_condominio.id_interes=? where condominio.activo=1";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

            rs = ps.executeQuery();

            while (rs.next()) {
                modin = new Interes();

                //prs = new Persona();
                modin.setRif(rs.getString("rif"));

                modin.setRazonS(rs.getString("razon_social"));
                modin.setN_condominios(rs.getInt("cuenta"));

                listainteresmod.add(modin);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listainteresmod;

    }

    public boolean modificar_Interes(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE interes SET nombre=?, factor=?, estado=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());
            ps.setString(3, getEstado());
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

    public boolean borrarpuente(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE puente_interes_condominio SET activo=0 WHERE id_interes=?";

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

    public boolean eliminarInteres(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE interes SET activo=0, estado='Inactivo' WHERE id=?";

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
    
     public boolean activarpuente(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE puente_interes_condominio SET activo=1 WHERE id_interes=?";

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

    public boolean activarInteres(Interes modin) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE interes SET activo=1, estado='Activo' WHERE id=?";

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
