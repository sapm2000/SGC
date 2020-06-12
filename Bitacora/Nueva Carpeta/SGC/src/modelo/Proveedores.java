package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Proveedores extends ConexionBD {

    private String cedula;
    private String nombre;
    private String telefono;
    private String correo;
    private String contacto;
    private String direccion;

    private Connection con;

    public boolean registrar(Proveedores modpro) {

        ps = null;
        con = getConexion();

        String sql = "INSERT INTO proveedores(cedula, nombre, telefono, correo, contacto, direccion, activo) VALUES (?, ?, ?, ?, ?, ?, 1);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getNombre());
            ps.setString(3, getTelefono());
            ps.setString(4, getCorreo());
            ps.setString(5, getContacto());
            ps.setString(6, getDireccion());
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

    public ArrayList<Proveedores> listar() {
        ArrayList listaProveedores = new ArrayList();
        Proveedores modpro;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT cedula, nombre, telefono, correo, contacto, direccion FROM proveedores where activo=1;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Proveedores();

                modpro.setCedula(rs.getString(1));
                modpro.setNombre(rs.getString(2));
                modpro.setTelefono(rs.getString(3));
                modpro.setCorreo(rs.getString(4));
                modpro.setContacto(rs.getString(5));
                modpro.setDireccion(rs.getString(6));

                listaProveedores.add(modpro);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaProveedores;
    }

    public ArrayList<Proveedores> listarinactivos() {
        ArrayList listaProveedores = new ArrayList();
        Proveedores modpro;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT cedula, nombre, telefono, correo, contacto, direccion FROM proveedores where activo=0;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Proveedores();

                modpro.setCedula(rs.getString(1));
                modpro.setNombre(rs.getString(2));
                modpro.setTelefono(rs.getString(3));
                modpro.setCorreo(rs.getString(4));
                modpro.setContacto(rs.getString(5));
                modpro.setDireccion(rs.getString(6));

                listaProveedores.add(modpro);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaProveedores;
    }

    public boolean buscar(Proveedores modpro) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM proveedores WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedula());
            rs = ps.executeQuery();
            if (rs.next()) {

                modpro.setContacto(rs.getString("contacto"));
                modpro.setCorreo(rs.getString("correo"));
                modpro.setDireccion(rs.getString("direccion"));
                modpro.setNombre(rs.getString("nombre"));
                modpro.setTelefono(rs.getString("telefono"));

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

    public boolean modificar(Proveedores modpro) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE proveedores SET nombre=?, telefono=?, correo=?, contacto=?, direccion=? WHERE cedula=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getTelefono());
            ps.setString(3, getCorreo());
            ps.setString(4, getContacto());
            ps.setString(5, getDireccion());

            ps.setString(6, getCedula());
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

    public boolean eliminar(Proveedores modpro) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE proveedores SET activo=0 WHERE cedula=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
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

    public boolean activar(Proveedores modpro) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE proveedores SET activo=1 WHERE cedula=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
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

    public boolean Buscargas(Proveedores modpro) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM gasto_comun where id_proveedor=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedula());

            rs = ps.executeQuery();

            if (rs.next()) {

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

    public boolean Buscarcuo(Proveedores modpro) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM facturas_proveedores where id_proveedor=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedula());

            rs = ps.executeQuery();

            if (rs.next()) {

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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
