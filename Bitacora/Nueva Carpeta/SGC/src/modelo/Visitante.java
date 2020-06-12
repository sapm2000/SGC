package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Visitante extends Persona {

    private Connection con;

    public boolean buscar(String cedula) {
        try {
            ps = null;
            rs = null;
            con = getConexion();

            String sql = "SELECT * FROM v_visitante WHERE cedula = ?;";

            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);

            rs = ps.executeQuery();

            if (rs.next()) {
                setCedula(rs.getString("cedula"));
                setpNombre(rs.getString("nombre"));
                setpApellido(rs.getString("apellido"));

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

    public Boolean existe() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT cedula FROM v_visitante WHERE cedula = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getCedula());

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Unidades.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public Boolean registrar(Boolean existe) {
        try {
            ps = null;
            con = getConexion();

            if (!existe) {
                if (!registrarPersona()) {
                    return false;
                }
            }

            String sql = "INSERT INTO visitante (ci_persona) VALUES (?);";

            int i = 1;

            ps = con.prepareStatement(sql);

            ps.setString(i++, getCedula());

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

    public ArrayList<Visitante> listar() {
        try {
            ArrayList listaVis = new ArrayList();
            Visitante visitante;

            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT * FROM v_visitante;";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                visitante = new Visitante();

                visitante.setCedula(rs.getString("cedula"));
                visitante.setpNombre(rs.getString("nombre"));
                visitante.setpApellido(rs.getString("apellido"));

                listaVis.add(visitante);
            }

            return listaVis;

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

}
