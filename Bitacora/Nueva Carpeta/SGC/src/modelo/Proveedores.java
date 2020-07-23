package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Proveedores extends ConexionBD {

    private String cedulaRif;
    private String nombre;
    private String telefono;
    private String correo;
    private String contacto;
    private String direccion;

    private Connection con;

    public boolean registrar(Proveedores modpro) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_proveedor(?,?,?,?,?,?,?);";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getCedulaRif());
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getTelefono());
            ps.setString(ind++, getCorreo());
            ps.setString(ind++, getContacto());
            ps.setString(ind++, getDireccion());
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
    
     public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM proveedores WHERE activo = true;";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            rs.next();

            int count = rs.getInt("count");

            return count;

        } catch (SQLException e) {

            System.err.println(e);
            return 0;

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

        String sql = "SELECT cedula, nombre, telefono, correo, contacto, direccion FROM proveedores where activo = true;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Proveedores();

                modpro.setCedulaRif(rs.getString(1));
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

        String sql = "SELECT cedula, nombre, telefono, correo, contacto, direccion FROM proveedores where activo = false;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Proveedores();

                modpro.setCedulaRif(rs.getString(1));
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
            ps.setString(1, modpro.getCedulaRif());
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

    public boolean modificar() {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_proveedor(?,?,?,?,?,?,?);";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getCedulaRif());
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getTelefono());
            ps.setString(ind++, getCorreo());
            ps.setString(ind++, getContacto());
            ps.setString(ind++, getDireccion());
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

    public boolean eliminar(Proveedores modpro) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_proveedor(?,?);";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getCedulaRif());
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

    public boolean activar(Proveedores modpro) {

        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_proveedor(?,?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedulaRif());
            
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

    public boolean buscarGasto(Proveedores modpro) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM gasto WHERE id_proveedor = ? AND estado = 'Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedulaRif());

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

    public String getCedulaRif() {
        return cedulaRif;
    }

    public void setCedulaRif(String cedulaRif) {
        this.cedulaRif = cedulaRif;
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
