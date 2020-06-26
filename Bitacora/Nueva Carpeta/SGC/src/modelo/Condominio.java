package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgc.SGC;

public class Condominio extends ConexionBD {

    private String rif;
    private String razonS;
    private String telefono;
    private String correoElectro;

    private Connection con;

    public boolean registrar() {
        ps = null;
        con = getConexion();
        int ind;

        String sql = "SELECT agregar_condominio(?,?,?,?,?)";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setString(ind++, getRif());
            ps.setString(ind++, getRazonS());
            ps.setString(ind++, getTelefono());
            ps.setString(ind++, getCorreoElectro());

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

    public Boolean buscar() {
        try {
            ps = null;
            rs = null;
            con = getConexion();

            String sql = "SELECT * FROM v_condominio";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                setRif(rs.getString("rif"));
                setRazonS(rs.getString("razon_social"));
                setTelefono(rs.getString("telefono"));
                setCorreoElectro(rs.getString("correo"));

                return true;

            } else {
                return false;
            }

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

    public Boolean existe() {
        try {
            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT rif FROM v_condominio;";

            ps = con.prepareStatement(sql);

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

    public boolean modificar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "SELECT modificar_condominio(?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setString(ind++, getRif());
            ps.setString(ind++, getRazonS());
            ps.setString(ind++, getTelefono());
            ps.setString(ind++, getCorreoElectro());

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

    public boolean Buscarsan(Condominio co) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT *  FROM sancion inner join puente_sancion_unidad on puente_sancion_unidad.id_sancion=sancion.id inner join unidades on puente_sancion_unidad.id_unidad=unidades.id where unidades.id_condominio=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, co.getRif());

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

    public boolean Buscarcuo(Condominio co) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM facturas_proveedores where id_condominio=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, co.getRif());

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

    public boolean Buscarcuen(Condominio co) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM puente_condominio_cuenta where id_condominio=? and activo=1";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, co.getRif());

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

    public boolean Buscarin(Condominio co) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM puente_interes_condominio where id_condominio=? and activo=1";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, co.getRif());

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

    public boolean Buscargas(Condominio co) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM gasto_comun where id_condominio=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, co.getRif());

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

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getRazonS() {
        return razonS;
    }

    public void setRazonS(String razonS) {
        this.razonS = razonS;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectro() {
        return correoElectro;
    }

    public void setCorreoElectro(String correoElectro) {
        this.correoElectro = correoElectro;
    }

}
