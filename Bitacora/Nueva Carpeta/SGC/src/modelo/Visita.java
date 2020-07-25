package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Visita extends ConexionBD {

    private Integer id;
    private Unidad unidad = new Unidad();
    private Persona visitante = new Persona();
    private Integer numPeronas;
    private String fechaEntrada;
    private String horaEntrada;
    private String fechaSalida;
    private String horaSalida;
    private String matricula;
    private String modelo;
    private String color;

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
                visita.getUnidad().setNumeroUnidad(rs.getString("n_unidad"));
                visita.getVisitante().setCedula(rs.getString("cedula"));
                visita.getVisitante().setpNombre(rs.getString("nombre"));
                visita.getVisitante().setpApellido(rs.getString("apellido"));
                visita.setNumPeronas(rs.getInt("n_personas"));
                visita.setFechaEntrada(rs.getString("fecha_entrada"));
                visita.setHoraEntrada(rs.getString("hora_entrada"));
                visita.setFechaSalida(rs.getString("fecha_salida"));
                visita.setHoraSalida(rs.getString("hora_salida"));
                visita.setMatricula(rs.getString("matricula"));
                visita.setModelo(rs.getString("modelo"));
                visita.setColor(rs.getString("color"));

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

    public boolean registrar(Boolean tieneAuto) {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "SELECT agregar_visita(?,?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setInt(ind++, getUnidad().getId());
            ps.setString(ind++, getVisitante().getCedula());

            if (tieneAuto) {
                ps.setInt(ind++, getNumPeronas());
                ps.setString(ind++, getMatricula());
                ps.setString(ind++, getModelo());
                ps.setString(ind++, getColor());

            } else {
                ps.setNull(ind++, java.sql.Types.NULL);
                ps.setNull(ind++, java.sql.Types.NULL);
                ps.setNull(ind++, java.sql.Types.NULL);
                ps.setNull(ind++, java.sql.Types.NULL);
            }

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

    public boolean registrarSalida() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "SELECT registrar_salida(?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setInt(ind++, getId());
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Persona getVisitante() {
        return visitante;
    }

    public void setVisitante(Persona visitante) {
        this.visitante = visitante;
    }

    public Integer getNumPeronas() {
        return numPeronas;
    }

    public void setNumPeronas(Integer numPeronas) {
        this.numPeronas = numPeronas;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
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

}
