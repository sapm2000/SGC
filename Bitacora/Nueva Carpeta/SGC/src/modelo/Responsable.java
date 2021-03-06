package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Responsable extends Persona {

    private Connection con;

    public Responsable() {
        super();
    }

    public Responsable(String cedula, String pNombre, String sNombre, String pApellido, String sApellido, String correo, String telefono) {
        super(cedula, pNombre, sNombre, pApellido, sApellido, correo, telefono);
    }

    public boolean eliminar() {
        ps = null;
        con = getConexion();

        String sql = "UPDATE responsable SET activo = false WHERE ci_persona = ?";

        try {
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

    public ArrayList<Responsable> listar() {
        ArrayList listaResponsable = new ArrayList();
        Responsable res;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT * FROM v_responsable WHERE activo = true;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int i;

            while (rs.next()) {
                res = new Responsable();
                i = 1;

                res.setCedula(rs.getString(i++));
                res.setpNombre(rs.getString(i++));
                res.setsNombre(rs.getString(i++));
                res.setpApellido(rs.getString(i++));
                res.setsApellido(rs.getString(i++));
                res.setTelefono(rs.getString(i++));
                res.setCorreo(rs.getString(i++));

                listaResponsable.add(res);
            }

        } catch (SQLException e) {
            System.err.println(e);

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);

            }

        }

        return listaResponsable;
    }

    public Boolean existe() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT ci_persona FROM v_responsable WHERE ci_persona = ? AND activo = true;";

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
            Logger.getLogger(Unidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public Boolean tieneUsuario() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT ci_persona FROM usuario WHERE ci_persona = ?;";

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
            Logger.getLogger(Unidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public Boolean existeInactivo() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT ci_persona FROM v_responsable WHERE ci_persona = ? AND activo = false;";

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
            Logger.getLogger(Unidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;

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

            String sql = "UPDATE responsable SET activo = true WHERE ci_persona = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getCedula());

            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Propietarios.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean registrar(Boolean existe) {
        try {
            ps = null;
            con = getConexion();

            if (!existe) {
                if (!registrarPersona()) {
                    return false;

                }
            }

            String sql = "INSERT INTO responsable (ci_persona) VALUES(?)";

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
}
