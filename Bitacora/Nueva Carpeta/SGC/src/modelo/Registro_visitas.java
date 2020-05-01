package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Registro_visitas extends ConexionBD {

    private int id;
    private String fecha;
    private String hora;
    private String matricula;
    private String modelo;
    private String color;
    private String id_unidad;
    private String ci_visitante;
    private Registro_visitante modReVi;

    public Registro_visitas() {
    }

    public Registro_visitante getModReVi() {
        return modReVi;
    }

    public void setModReVi(Registro_visitante modReVi) {
        this.modReVi = modReVi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(String id_unidad) {
        this.id_unidad = id_unidad;
    }

    public String getCi_visitante() {
        return ci_visitante;
    }

    public void setCi_visitante(String ci_visitante) {
        this.ci_visitante = ci_visitante;
    }

    public boolean registrarVisitas() {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO visita(placa, modelo, color, id_unidad, ci_visitante) VALUES(?, ?, ?, ?, ?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getMatricula());
            ps.setString(2, getModelo());
            ps.setString(3, getColor());
            ps.setString(4, getId_unidad());
            ps.setString(5, getCi_visitante());

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

    public ArrayList<Registro_visitas> listarVisitas() {
        ArrayList<Registro_visitas> listaVisita = new ArrayList();
        Registro_visitas modRvisita;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT vis.id_unidad, vis.placa, vis.modelo, vis.color, vis.fecha, vis.hora, vis.ci_visitante, visitante.nombre, "
                + "visitante.apellido FROM visita AS vis INNER JOIN visitante ON visitante.cedula=vis.ci_visitante;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                modRvisita = new Registro_visitas();
                modRvisita.modReVi = new Registro_visitante();

                //prs = new Persona();
                modRvisita.setId_unidad(rs.getString(1));
                modRvisita.setMatricula(rs.getString(2));
                modRvisita.setModelo(rs.getString(3));
                modRvisita.setColor(rs.getString(4));
                modRvisita.setFecha(rs.getString(5));
                modRvisita.setHora(rs.getString(6));
                modRvisita.setCi_visitante(rs.getString(7));
                modRvisita.modReVi.setNombre(rs.getString(8));
                modRvisita.modReVi.setApellido(rs.getString(9));

                listaVisita.add(modRvisita);
            }

        } catch (Exception e) {

        } finally {
            try {
                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaVisita;

    }

}
