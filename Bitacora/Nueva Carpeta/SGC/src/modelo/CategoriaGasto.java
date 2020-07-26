package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                return rs.getBoolean(1);

            } else {
                return false;
            }

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

    public ArrayList<CategoriaGasto> listar() {

        ArrayList<CategoriaGasto> lista;
        CategoriaGasto item;

        lista = new ArrayList();

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id, nombre, descripcion FROM categoriagasto WHERE activo = true;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                item = new CategoriaGasto();

                item.setId(rs.getInt(1));
                item.setNombre(rs.getString(2));
                item.setDescripcion(rs.getString(3));

                lista.add(item);
            }

        } catch (SQLException e) {

            System.err.println(e);

        } finally {

            cerrar();

        }

        return lista;
    }

    public boolean buscar(CategoriaGasto catagc) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM categoriagasto WHERE nombre = ? AND activo = true ";

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

    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM categoriagasto WHERE activo = true;";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            rs.next();

            int count = rs.getInt("count");

            return count;

        } catch (SQLException e) {

            System.err.println(e);
            return 0;

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

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                return rs.getBoolean(1);

            } else {
                return false;
            }

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

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                return rs.getBoolean(1);

            } else {
                return false;
            }

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

    public boolean existe(boolean activo) {

        int ind;

        ps = null;
        rs = null;
        ind = 1;

        String sql = "SELECT * FROM categoriagasto WHERE nombre  = ? AND activo = ?";

        con = getConexion();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(ind++, nombre.toUpperCase());
            ps.setBoolean(ind++, activo);
            rs = ps.executeQuery();
            
            return rs.next();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(CategoriaGasto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        } finally {
            
            cerrar();
        }
    }

    public boolean reactivar(CategoriaGasto prs) {
        
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_categoria(?,?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, prs.getNombre().toUpperCase());
            ps.setString(ind++, prs.getDescripcion());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                return rs.getBoolean(1);

            } else {
                return false;
            }

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
