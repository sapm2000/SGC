package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Visita extends ConexionBD {

    private Integer id;
    private Unidades unidad = new Unidades();
    private String fecha;
    private String hora;
    private String matricula;
    private String modelo;
    private String color;
    private Visitante visitante = new Visitante();

    private Connection con;

    public Visita() {
    }

    public ArrayList<Visita> listar() {
        try {
            ArrayList<Visita> lista = new ArrayList();
            Visita visita;

            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT * FROM v_visita;";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                visita = new Visita();

                visita.setId(rs.getInt("id"));
                visita.getUnidad().setId(rs.getInt("id_unidad"));
                visita.getUnidad().setN_unidad(rs.getString("n_unidad"));
                visita.setFecha(rs.getString("fecha"));
                visita.setHora(rs.getString("hora"));
                visita.setMatricula(rs.getString("matricula"));
                visita.setModelo(rs.getString("modelo"));
                visita.setColor(rs.getString("color"));
                visita.getVisitante().setCedula(rs.getString("cedula"));
                visita.getVisitante().setpNombre(rs.getString("nombre"));
                visita.getVisitante().setpApellido(rs.getString("apellido"));

                lista.add(visita);
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

    public boolean registrar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "INSERT INTO visita(placa, modelo, color, id_unidad, ci_visitante) VALUES(?,?,?,?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setString(ind++, getMatricula());
            ps.setString(ind++, getModelo());
            ps.setString(ind++, getColor());
            ps.setInt(ind++, getUnidad().getId());
            ps.setString(ind++, getVisitante().getCedula());

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

    public Unidades getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidades unidad) {
        this.unidad = unidad;
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

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

}
