package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TipoUsuario extends ConexionBD {

    private Integer id;
    private String nombre;
    private ArrayList<Funcion> funciones;

    private Connection con;

    public TipoUsuario() {

        funciones = new ArrayList();

    }

    public TipoUsuario(Integer id, String nombre, ArrayList<Funcion> funciones) {

        this.id = id;
        this.nombre = nombre;
        this.funciones = funciones;

    }

    public boolean buscarId() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT id FROM tipo_usuario WHERE tipo = ?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre.toUpperCase());
            rs = ps.executeQuery();

            if (rs.next()) {

                id = rs.getInt("id");

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM tipo_usuario WHERE activo = true;";

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

            cerrar();

        }
    }

    public ArrayList<TipoUsuario> listar() {

        try {

            ArrayList<TipoUsuario> lista = new ArrayList();
            con = getConexion();
            ps = null;
            TipoUsuario item;

            String sql = "SELECT * FROM v_tipo_usuario;";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            ResultSet rs2;
            Funcion funcion;
            sql = "SELECT * FROM v_tipo_funcion WHERE id = ?;";
            ps = con.prepareStatement(sql);

            while (rs.next()) {

                item = new TipoUsuario();
                item.setId(rs.getInt("id"));
                item.setNombre(rs.getString("tipo"));

                ps.setInt(1, item.id);

                rs2 = ps.executeQuery();

                while (rs2.next()) {

                    funcion = new Funcion();
                    funcion.setId(rs2.getInt("id_funcion"));
                    funcion.setNombre(rs2.getString("funcion"));
                    funcion.setVer(rs2.getBoolean("ver"));
                    funcion.setRegistrar(rs2.getBoolean("registrar"));
                    funcion.setModificar(rs2.getBoolean("modificar"));
                    funcion.setEliminar(rs2.getBoolean("eliminar"));

                    item.funciones.add(funcion);

                }

                lista.add(item);

            }

            return lista;

        } catch (SQLException ex) {

            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {

            cerrar();

        }
    }

    public Boolean registrar() {

        try {

            con = getConexion();
            ps = null;

            int i;

            String sql = "INSERT INTO tipo_usuario(tipo) VALUES(?);";

            i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getNombre());
            ps.execute();

            if (buscarId()) {

                if (registrarFunciones()) {

                    return true;

                }
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    public boolean registrarFunciones() {

        try {

            con = getConexion();
            ps = null;

            int ind;

            String sql = "INSERT INTO puente_tipo_funcion(id_tipo, id_funcion, ver, registrar, modificar, eliminar) VALUES(?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);

            System.out.println("Se registraran " + getFunciones().size() + " funciones");

            for (int i = 0; i < getFunciones().size(); i++) {

                ind = 1;
                ps.setInt(ind++, getId());
                ps.setInt(ind++, getFunciones().get(i).getId());
                ps.setBoolean(ind++, getFunciones().get(i).getVer());
                ps.setBoolean(ind++, getFunciones().get(i).getRegistrar());
                ps.setBoolean(ind++, getFunciones().get(i).getModificar());
                ps.setBoolean(ind++, getFunciones().get(i).getEliminar());
                System.out.println("se registró la función " + getFunciones().get(i).getNombre());
                System.out.println("ver: " + getFunciones().get(i).getVer());
                System.out.println("registrar: " + getFunciones().get(i).getRegistrar());
                System.out.println("modificar: " + getFunciones().get(i).getModificar());
                System.out.println("eliminar: " + getFunciones().get(i).getEliminar());
                System.out.println("");

                if (ps.executeUpdate() != 1) {

                    return false;

                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

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

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }

}
