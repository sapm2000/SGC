package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persona extends ConexionBD {

    private String cedula;
    private String pNombre;
    private String sNombre;
    private String pApellido;
    private String sApellido;
    private String correo;
    private String telefono;
    private Condominio[] condominio;

    private Connection con;

    public Persona() {
    }

    public Persona(String cedula) {
        this.cedula = cedula;
    }

    public Persona(String cedula, String pNombre, String pApellido) {
        this.cedula = cedula;
        this.pNombre = pNombre;
        this.pApellido = pApellido;
    }

    public Persona(String cedula, String pNombre, String sNombre, String pApellido, String sApellido, String correo, String telefono) {
        this.cedula = cedula;
        this.pNombre = pNombre;
        this.sNombre = sNombre;
        this.pApellido = pApellido;
        this.sApellido = sApellido;
        this.correo = correo;
        this.telefono = telefono;
    }
    
        public Boolean buscar() {
        try {
            rs = null;
            ps = null;
            con = getConexion();

            int ind;

            String sql = "SELECT cedula, p_nombre, s_nombre, p_apellido, s_apellido, correo, telefono FROM persona WHERE cedula = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getCedula());
            rs = ps.executeQuery();

            if (rs.next()) {
                setpNombre(rs.getString("p_nombre"));
                setsNombre(rs.getString("s_nombre"));
                setpApellido(rs.getString("p_apellido"));
                setsApellido(rs.getString("s_apellido"));
                setCorreo(rs.getString("correo"));
                setTelefono(rs.getString("telefono"));
                return true;

            } else {
                return false;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public Boolean existePersona() {
        try {
            rs = null;
            ps = null;
            con = getConexion();

            int ind;

            String sql = "SELECT cedula FROM persona WHERE cedula = ?";

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
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public ArrayList<Persona> listarP() {

        rs = null;
        con = getConexion();
        ps = null;

        ArrayList<Persona> personas = new ArrayList();
        Persona mod;

        String sql = "SELECT cedula, p_nombre, p_apellido, correo, telefono FROM persona";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            int ind;

            while (rs.next()) {
                mod = new Persona();
                ind = 1;
                mod.setCedula(rs.getString(ind++));
                mod.setpNombre(rs.getString(ind++));
                mod.setpApellido(rs.getString(ind++));
                mod.setCorreo(rs.getString(ind++));
                mod.setTelefono(rs.getString(ind++));
                personas.add(mod);
            }
            return personas;

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

    public boolean modificar() {
        ps = null;
        con = getConexion();

        String sql = "UPDATE persona SET p_nombre = ?, s_nombre = ?, p_apellido = ?, s_apellido = ?, correo = ?, telefono = ? WHERE cedula = ?";

        try {
            int i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getpNombre());
            ps.setString(i++, getsNombre());
            ps.setString(i++, getpApellido());
            ps.setString(i++, getsApellido());
            ps.setString(i++, getCorreo());
            ps.setString(i++, getTelefono());
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

    public Boolean registrarPersona() {
        try {
            ps = null;
            con = getConexion();

            String sql = "INSERT INTO persona(cedula, p_nombre, s_nombre, p_apellido, s_apellido, telefono, correo) VALUES (?,?,?,?,?,?,?);";

            int i = 1;

            ps = con.prepareStatement(sql);

            ps.setString(i++, getCedula());
            ps.setString(i++, getpNombre());
            ps.setString(i++, getsNombre());
            ps.setString(i++, getpApellido());
            ps.setString(i++, getsApellido());
            ps.setString(i++, getTelefono());
            ps.setString(i++, getCorreo());

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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getpNombre() {
        return pNombre;
    }

    public void setpNombre(String pNombre) {
        this.pNombre = pNombre;
    }

    public String getsNombre() {
        return sNombre;
    }

    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public String getpApellido() {
        return pApellido;
    }

    public void setpApellido(String pApellido) {
        this.pApellido = pApellido;
    }

    public String getsApellido() {
        return sApellido;
    }

    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Condominio[] getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio[] condominio) {
        this.condominio = condominio;
    }

}
