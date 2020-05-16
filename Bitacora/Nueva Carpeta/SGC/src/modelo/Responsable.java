package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Responsable extends Persona {

    public Responsable() {
        super();
    }
    
    public Responsable(String cedula, String pNombre, String sNombre, String pApellido, String sApellido, String correo, String telefono) {
        super(cedula, pNombre, sNombre, pApellido, sApellido, correo, telefono);
    }

    public boolean eliminar() {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE responsable SET activo = false WHERE cedula = ?";

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

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM v_responsable;";
        
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
    
    public boolean modificar() {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE responsable SET p_nombre = ?, s_nombre = ?, p_apellido = ?, s_apellido = ?, correo = ?, telefono = ? WHERE cedula = ?";

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
    
    public boolean registrar() {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO responsable (cedula, p_nombre, s_nombre, p_apellido, s_apellido, correo, telefono) VALUES(?,?,?,?,?,?,?)";

        try {
            int i = 1;
            
            ps = con.prepareStatement(sql);
            ps.setString(i++, getCedula());
            ps.setString(i++, getpNombre());
            ps.setString(i++, getsNombre());
            ps.setString(i++, getpApellido());
            ps.setString(i++, getsApellido());
            ps.setString(i++, getCorreo());
            ps.setString(i++, getTelefono());

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
