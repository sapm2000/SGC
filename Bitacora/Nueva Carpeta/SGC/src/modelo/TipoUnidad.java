package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TipoUnidad extends ConexionBD {

    private Integer id;
    private String nombre;
    private Float area;

    private Connection con;

    public TipoUnidad() {
    }

    public boolean buscarInactivo() {
        try {
            ps = null;
            rs = null;
            con = getConexion();

            String sql = "SELECT * FROM v_tipo_unidad_inactivo WHERE tipo=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            rs = ps.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("id"));
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

    public boolean eliminar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "UPDATE tipo_unidad SET activo = false WHERE id = ?";

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
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

    public Boolean existe() {
        try {
            con = getConexion();
            ps = null;
            rs = null;

            int ind;

            String sql = "SELECT tipo FROM v_tipo_unidad WHERE tipo = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getNombre());

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Unidades.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<TipoUnidad> listar() {
        try {
            ArrayList lista = new ArrayList();
            TipoUnidad tipo;

            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT * FROM v_tipo_unidad";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                tipo = new TipoUnidad();

                tipo.setId(rs.getInt("id"));
                tipo.setNombre(rs.getString("tipo"));
                tipo.setArea(rs.getFloat("area"));

                lista.add(tipo);
            }

            return lista;

        } catch (SQLException e) {
            System.err.println(e);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public Boolean modificar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "UPDATE tipo_unidad SET tipo = ?, area = ? WHERE id = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getNombre());
            ps.setDouble(ind++, getArea());
            ps.setInt(ind++, getId());

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

    public Boolean reactivar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "UPDATE tipo_unidad SET activo = true WHERE id = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setInt(ind++, getId());

            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Propietarios.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean registrar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "INSERT INTO tipo_unidad(tipo, area) VALUES (?,?);";

            ps = con.prepareStatement(sql);

            ind = 1;

            ps.setString(ind++, getNombre());
            ps.setDouble(ind++, getArea());

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

}
