package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mensaje extends ConexionBD {

    private int id;
    private String asunto;
    private String contenido;
    private Boolean estado;
    private Usuario emisor;
    private ArrayList<Usuario> receptor;
    
    private Connection con;
    
    public boolean registrar() {

        con = getConexion();
        ps = null;

        String sql = "INSERT INTO mensaje(asunto, contenido, emisor) VALUES (?, ?, ?);";

        try {

            int i;
            i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getAsunto());
            ps.setString(i++, getContenido());
            ps.setInt(i++, getEmisor().getId());

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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public ArrayList<Usuario> getReceptor() {
        return receptor;
    }

    public void setReceptor(ArrayList<Usuario> receptor) {
        this.receptor = receptor;
    }

}
