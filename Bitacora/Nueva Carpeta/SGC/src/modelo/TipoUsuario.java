package modelo;

import java.sql.Connection;
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

    public ArrayList<TipoUsuario> listar() {
        try {
            ArrayList<TipoUsuario> listar = new ArrayList();
            con = getConexion();
            ps = null;
            TipoUsuario mod;

            String sql = "SELECT * FROM tipo_usuario;";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                mod = new TipoUsuario();
                mod.setId(rs.getInt("id"));
                mod.setNombre(rs.getString("tipo"));
                listar.add(mod);

            }

            return listar;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;

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

            sql = "SELECT id FROM tipo_usuario WHERE tipo = ?;";

            i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getNombre());
            rs = ps.executeQuery();

            while (rs.next()) {
                setId(rs.getInt("id"));

            }

            if (registrarFunciones()) {
                return true;

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

    public Boolean registrarFunciones() {
        try {
            con = getConexion();
            ps = null;

            int ind;

            String sql = "INSERT INTO puente_tipo_funcion(id_tipo, id_funcion, registrar, modificar, eliminar, todo) VALUES(?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);

            System.out.println("se registraran " + getFunciones().size() + " funciones");
            for (int i = 0; i < getFunciones().size(); i++) {
                ind = 1;
                ps.setInt(ind++, getId());
                ps.setInt(ind++, getFunciones().get(i).getId());
                ps.setBoolean(ind++, getFunciones().get(i).getRegistrar());
                ps.setBoolean(ind++, getFunciones().get(i).getModificar());
                ps.setBoolean(ind++, getFunciones().get(i).getEliminar());
                ps.setBoolean(ind++, getFunciones().get(i).getTodo());
                System.out.println("se registra la funcion " + getFunciones().get(i).getNombre());
                System.out.println("registrar: " + getFunciones().get(i).getRegistrar());
                System.out.println("modificar: " + getFunciones().get(i).getModificar());
                System.out.println("eliminar: " + getFunciones().get(i).getEliminar());
                System.out.println("todo: " + getFunciones().get(i).getTodo());
                System.out.println("");
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
