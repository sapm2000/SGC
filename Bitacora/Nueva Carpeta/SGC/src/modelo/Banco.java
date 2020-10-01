package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Banco extends ConexionBD {

    private int id;
    private String nombre_banco;

    private Connection con;

    public boolean registrar(Banco modban) {

        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_banco(?,?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre_banco());
            ps.setInt(2, SGC.usuarioActual.getId());

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

    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM banco WHERE activo = true;";

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

    public ArrayList<Banco> listar() {
        ArrayList listaBanco = new ArrayList();
        Banco modban;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT * FROM banco where activo=true";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modban = new Banco();

                modban.setId(rs.getInt(1));
                modban.setNombre_banco(rs.getString(2));

                listaBanco.add(modban);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

            return listaBanco;
        }
    }

    public boolean buscar(Banco modban) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM banco WHERE nombre_banco=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modban.getNombre_banco());
            rs = ps.executeQuery();
            if (rs.next()) {

                modban.setId(rs.getInt("id"));
                modban.setNombre_banco(rs.getString("nombre_banco"));

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

    public boolean buscacuentas(Banco modban) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM cuenta where id_banco=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modban.getId());
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

    public boolean modificar(Banco modban) {
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_banco(?,?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setString(ind++, getNombre_banco());
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

    public boolean buscarInactivo(Banco modban) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM banco WHERE nombre_banco=? AND activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre_banco.toUpperCase());
            rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    public boolean eliminar(Banco modban) {

        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_banco(?,?);";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, getId());
            ps.setInt(2, SGC.usuarioActual.getId());

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

    public boolean activar(Banco modban) {

        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_banco(?,?)";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);

            ps.setString(ind++, getNombre_banco());
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

    public String getNombre_banco() {
        return nombre_banco;
    }

    public void setNombre_banco(String nombre_banco) {
        this.nombre_banco = nombre_banco;
    }

}
