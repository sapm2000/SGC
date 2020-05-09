package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaGasto extends ConexionBD {

    private int id;
    private String nombre;
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean registrar(CategoriaGasto cga) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO categoriagasto (nombre, descripcion, activo) VALUES(?,?,1)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, cga.getNombre());
            ps.setString(2, cga.getDescripcion());

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

    public ArrayList<CategoriaGasto> lCategGas() {
        ArrayList listaPersona = new ArrayList();
        CategoriaGasto CategoriaGasto;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, nombre, descripcion FROM categoriagasto where activo=1;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaGasto = new CategoriaGasto();

                //prs = new Persona();
                CategoriaGasto.setId(rs.getInt(1));
                CategoriaGasto.setNombre(rs.getString(2));
                CategoriaGasto.setDescripcion(rs.getString(3));

                listaPersona.add(CategoriaGasto);
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
    
     public ArrayList<CategoriaGasto> lCategGasi() {
        ArrayList listaPersona = new ArrayList();
        CategoriaGasto CategoriaGasto;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, nombre, descripcion FROM categoriagasto where activo=0;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaGasto = new CategoriaGasto();

                //prs = new Persona();
                CategoriaGasto.setId(rs.getInt(1));
                CategoriaGasto.setNombre(rs.getString(2));
                CategoriaGasto.setDescripcion(rs.getString(3));

                listaPersona.add(CategoriaGasto);
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

    public boolean Buscar(CategoriaGasto catagc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM categoriagasto WHERE nombre=? and activo=1 ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, catagc.getNombre());

            rs = ps.executeQuery();

            if (rs.next()) {

                catagc.setId(rs.getInt("id"));
                catagc.setNombre(rs.getString("nombre"));
                catagc.setDescripcion(rs.getString("descripcion"));

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

    public boolean modificar(CategoriaGasto catacg) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE categoriagasto SET nombre=?, descripcion=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(3, catacg.getId());
            ps.setString(2, catacg.getDescripcion());
            ps.setString(1, catacg.getNombre());

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

    public boolean eliminar(CategoriaGasto prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE categoriagasto SET activo=0 WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, prs.getId());
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
    
    public boolean activar(CategoriaGasto prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE categoriagasto SET activo=1 WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, prs.getId());
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
