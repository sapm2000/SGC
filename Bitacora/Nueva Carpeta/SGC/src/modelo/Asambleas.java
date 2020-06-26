package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Asambleas extends ConexionBD {

    private Integer id;
    private String nombre;
    private String descripcion;
    private java.sql.Date fecha;
    private ArrayList<Propietarios> asistentes = new ArrayList();

    private Connection con;

    public Boolean registrar() {
        try {
            int i;

            ps = null;
            con = getConexion();

            String sql = "SELECT agregar_asambleas(?,?,?,?);";

            i = 1;
            ps = con.prepareStatement(sql);
            ps.setString(i++, getNombre());
            ps.setDate(i++, getFecha());
            ps.setString(i++, getDescripcion());
            ps.setInt(i++, SGC.usuarioActual.getId());
            ps.execute();

            if (buscarId()) {

                if (registrarAsistentes()) {
                    return true;

                } else {
                    return false;
                }

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

    public Boolean buscarId() {
        try {
            ps = null;
            rs = null;
            con = getConexion();

            String sql = "SELECT MAX(id) AS id FROM v_asambleas";

            ps = con.prepareStatement(sql);
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

    public boolean registrarAsistentes() {
        try {
            int ind;

            ps = null;
            con = getConexion();

            String sql = "INSERT INTO puente_asambleas_propietario(id_asamblea, ci_propietario) VALUES (?,?);";

            ps = con.prepareStatement(sql);

            for (int i = 0; i < getAsistentes().size(); i++) {
                ind = 1;
                ps.setInt(ind++, getId());
                ps.setString(ind++, getAsistentes().get(i).getCedula());

                ps.execute();
            }

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

    public ArrayList<Asambleas> listar() {
        try {
            ArrayList lista = new ArrayList();
            Asambleas item;
            ResultSet rs2;

            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT * FROM v_asambleas;";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            sql = "SELECT * FROM v_asambleas_propietario WHERE id = ?";
            ps = con.prepareStatement(sql);

            while (rs.next()) {
                item = new Asambleas();

                item.setId(rs.getInt("id"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setFecha(rs.getDate("fecha"));

                ps.setInt(1, item.getId());
                rs2 = ps.executeQuery();

                while (rs2.next()) {
                    item.getAsistentes().add(new Propietarios(rs2.getString("cedula"), rs2.getString("nombre"), rs2.getString("apellido")));
                }

                lista.add(item);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Propietarios> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(ArrayList<Propietarios> asistentes) {
        this.asistentes = asistentes;
    }

}
