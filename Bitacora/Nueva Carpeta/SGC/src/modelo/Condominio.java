//21321312 ; asdasd ; 12313 ; asdasdad
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Condominio extends ConexionBD {

    private String rif;
    private String razonS;
    private String telefono;
    private String correoElectro;

    Connection con;

    public boolean registrar() {
        PreparedStatement ps = null;
        Connection con = getConexion();
        int ind;

        String sql = "INSERT INTO condominio (rif, razon_social, telefono, correo_electronico) VALUES(?,?,?,?)";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
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

//    public ArrayList<Condominio> lPerson() {
//        ArrayList lista = new ArrayList();
//        Condominio condominio;
//
//        Connection con = getConexion();
//        ps = null;
//        rs = null;
//
//        String sql = "SELECT * FROM v_condominio;";
//
//        try {
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                condominio = new Condominio();
//
//                condominio.setRif(rs.getString("rif"));
//                condominio.setRazonS(rs.getString("razon_social"));
//                condominio.setTelefono(rs.getString("telefono"));
//                condominio.setCorreoElectro(rs.getString("correo"));
//
//                lista.add(condominio);
//            }
//
//        } catch (SQLException e) {
//            System.err.println(e);
//
//        } finally {
//            try {
//                con.close();
//
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//        return lista;
//
//    }
//    public ArrayList<Condominio> lPersoni() {
//        ArrayList listaPersona = new ArrayList();
//        Condominio Condominio;
//
//        Connection con = getConexion();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String sql = "SELECT rif, razon_social, telefono, correo_electronico FROM condominio where activo=0;";
//        try {
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Condominio = new Condominio();
//
//                //prs = new Persona();
//                Condominio.setRif(rs.getString(1));
//                Condominio.setRazonS(rs.getString(2));
//                Condominio.setTelefono(rs.getString(3));
//                Condominio.setCorreoElectro(rs.getString(4));
//                listaPersona.add(Condominio);
//            }
//
//        } catch (Exception e) {
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//        return listaPersona;
//
//    }
//
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
        }
    }

    public boolean modificar() {
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "UPDATE condominio SET razon_social=?, telefono=?, correo_electronico=? WHERE rif=?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getRazonS());
            ps.setString(ind++, getTelefono());
            ps.setString(ind++, getCorreoElectro());
            ps.setString(ind++, getRif());

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

//    public boolean eliminarunidadcondominio(Condominio co) {
//
//        PreparedStatement ps = null;
//        Connection con = getConexion();
//
//        String sql = "UPDATE unidades SET activo=0 WHERE id_condominio=?";
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, getRif());
//            ps.execute();
//
//            return true;
//
//        } catch (SQLException e) {
//
//            System.err.println(e);
//            return false;
//
//        } finally {
//            try {
//
//                con.close();
//
//            } catch (SQLException e) {
//
//                System.err.println(e);
//
//            }
//
//        }
//
//    }
//
    public boolean Buscarsan(Condominio co) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

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

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

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

//    public boolean eliminar(Condominio prs) {
//        PreparedStatement ps = null;
//        Connection con = getConexion();
//
//        String sql = "UPDATE condominio SET activo=0 WHERE rif=?";
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, prs.getRif());
//            ps.execute();
//            return true;
//
//        } catch (SQLException e) {
//
//            System.err.println(e);
//            return false;
//
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//    }
//
//    public boolean activar(Condominio prs) {
//        PreparedStatement ps = null;
//        Connection con = getConexion();
//
//        String sql = "UPDATE condominio SET activo=1 WHERE rif=?";
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, prs.getRif());
//            ps.execute();
//            return true;
//
//        } catch (SQLException e) {
//
//            System.err.println(e);
//            return false;
//
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//    }
//    public ArrayList<Condominio> cuentacondominiomodificar() {
//        ArrayList listaPersona = new ArrayList();
//        Condominio Condominio = new Condominio();
//
//        Connection con = getConexion();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String sql = "SELECT rif , razon_social, puente_condominio_cuenta.id_cuenta as cuenta FROM condominio left join puente_condominio_cuenta on puente_condominio_cuenta.id_condominio=condominio.rif and puente_condominio_cuenta.id_cuenta=? where condominio.activo=1";
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setString(1, getRif());
//
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Condominio = new Condominio();
//
//                //prs = new Persona();
//                Condominio.setRif(rs.getString("rif"));
//                Condominio.setRazonS(rs.getString("razon_social"));
//                Condominio.cuen.setN_cuenta(rs.getString("cuenta"));
//
//                listaPersona.add(Condominio);
//            }
//
//        } catch (Exception e) {
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//        return listaPersona;
//
//    }
//    public ArrayList<Condominio> propietariocondominiomodificar() {
//        ArrayList listaPersona = new ArrayList();
//        Condominio Condominio = new Condominio();
//
//        Connection con = getConexion();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String sql = "SELECT rif , razon_social, puente_propietario_condominio.id_propietario as prop FROM condominio left join puente_propietario_condominio on puente_propietario_condominio.id_condominio=condominio.rif and puente_propietario_condominio.id_propietario=?";
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setString(1, getRif());
//
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Condominio = new Condominio();
//
//                //prs = new Persona();
//                Condominio.setRif(rs.getString("rif"));
//                Condominio.setRazonS(rs.getString("razon_social"));
//                Condominio.cuen.setN_cuenta(rs.getString("prop"));
//
//                listaPersona.add(Condominio);
//            }
//
//        } catch (Exception e) {
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//        return listaPersona;
//
//    }
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
