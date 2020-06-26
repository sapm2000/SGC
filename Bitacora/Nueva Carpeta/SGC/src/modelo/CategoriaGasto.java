package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class CategoriaGasto extends ConexionBD {

    private int id;
    private String nombre;
    private String descripcion;

    private Connection con;

    public boolean registrar(CategoriaGasto cga) {
        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_categoria(?,?,?);";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setString(ind++, cga.getNombre());
            ps.setString(ind++, cga.getDescripcion());

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

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id, nombre, descripcion FROM categoriagasto where activo=true;";
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

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id, nombre, descripcion FROM categoriagasto where activo=false;";
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

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM categoriagasto WHERE nombre=? and activo=true ";

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

    public boolean Buscarcon(CategoriaGasto catagc) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT id, nom_concepto, descripcion, id_categoria FROM concepto_gasto where id_categoria=? and activo=true;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, catagc.getId());

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

    public boolean modificar(CategoriaGasto catacg) {

        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_categoria(?,?,?,?);";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setString(ind++, catacg.getNombre());
            ps.setString(ind++, catacg.getDescripcion());
            ps.setInt(ind++, catacg.getId());

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

    public boolean buscarInactivo(CategoriaGasto modcg) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM categoriagasto WHERE nombre=? and activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {

                modcg.setId(rs.getInt("id"));

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

    public boolean eliminar(CategoriaGasto prs) {
        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_categoria(?,?)";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setInt(ind++, prs.getId());
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
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_categoria(?,?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, prs.getNombre());
            ps.setString(ind++, prs.getDescripcion());
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
