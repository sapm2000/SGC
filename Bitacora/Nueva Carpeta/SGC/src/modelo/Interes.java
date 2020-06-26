package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Interes extends ConexionBD {

    private int id;
    private String nombre;
    private double factor;

    private Connection con;

    public boolean registrarinteres(Interes modin) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_interes(?,?,?,?);";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getNombre());
            ps.setDouble(ind++, getFactor());
            ps.setString(ind++, SGC.condominioActual.getRif());
            ps.setInt(ind++, SGC.usuarioActual.getId());
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

        ps = null;
        rs = null;
        con = getConexion();
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

    public ArrayList<Interes> listarInteres() {
        ArrayList listainteres = new ArrayList();
        Interes modin;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id, nombre, factor FROM interes where activo=true;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modin = new Interes();

                modin.setId(rs.getInt(1));
                modin.setNombre(rs.getString(2));
                modin.setFactor(rs.getDouble(3));

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

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT nombre, factor FROM interes where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modin.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modin.setNombre(rs.getString("nombre"));
                modin.setFactor(rs.getDouble("factor"));

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

    public boolean modificar_Interes(Interes modin) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_interes(?,?,?,?);";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setString(ind++, getNombre());
            ps.setDouble(ind++, getFactor());
            ps.setInt(ind++, SGC.usuarioActual.getId());
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

    public boolean buscarInactivo(Interes modin) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM interes WHERE nombre=? and factor=? and activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());
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

    public boolean buscarduplicados(Interes modin) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM interes WHERE nombre=? and factor=? and activo=true";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getFactor());
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

    public boolean eliminarInteres(Interes modin) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_interes(?,?)";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());
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

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_interes(?,?);";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());
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

}
