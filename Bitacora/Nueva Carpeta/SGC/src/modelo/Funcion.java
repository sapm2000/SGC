package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Funcion extends ConexionBD {
    
    private Integer id;
    private String nombre;
    private Boolean registrar;
    private Boolean modificar;
    private Boolean eliminar;
    private Boolean todo;
    
    private Connection con;

    public Funcion() {
    }

    public Funcion(String nombre) {
        this.nombre = nombre;
    }

    public Funcion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public ArrayList<Funcion> listar() {
        try {
            ArrayList<Funcion> listar = new ArrayList();
            con = getConexion();
            ps = null;
            Funcion mod;

            String sql = "SELECT * FROM funcion;";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                mod = new Funcion();
                mod.setId(rs.getInt("id"));
                mod.setNombre(rs.getString("funcion"));
                listar.add(mod);

            }

            return listar;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;

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

    public Boolean getRegistrar() {
        return registrar;
    }

    public void setRegistrar(Boolean registrar) {
        this.registrar = registrar;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Boolean getTodo() {
        return todo;
    }

    public void setTodo(Boolean todo) {
        this.todo = todo;
    }
    

}
