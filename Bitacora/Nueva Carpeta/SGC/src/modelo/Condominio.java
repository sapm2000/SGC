package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Condominio extends ConexionBD {

    private String rif;
    private String razonS;
    private String telefono;
    private String correoElectro;
    public Cuenta cuen = new Cuenta();
   

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

    

    public boolean registrar(Condominio cond) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO condominio (rif, razon_social, telefono, correo_electronico, activo) VALUES(?,?,?,?,1)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, cond.getRif());
            ps.setString(2, cond.getRazonS());
            ps.setString(3, cond.getTelefono());
            ps.setString(4, cond.getCorreoElectro());

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

    public ArrayList<Condominio> lPerson() {
        ArrayList listaPersona = new ArrayList();
        Condominio Condominio;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT rif, razon_social, telefono, correo_electronico FROM condominio where activo=1;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Condominio = new Condominio();

                //prs = new Persona();
                Condominio.setRif(rs.getString(1));
                Condominio.setRazonS(rs.getString(2));
                Condominio.setTelefono(rs.getString(3));
                Condominio.setCorreoElectro(rs.getString(4));
                listaPersona.add(Condominio);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPersona;

    }
    
     public ArrayList<Condominio> lPersoni() {
        ArrayList listaPersona = new ArrayList();
        Condominio Condominio;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT rif, razon_social, telefono, correo_electronico FROM condominio where activo=0;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Condominio = new Condominio();

                //prs = new Persona();
                Condominio.setRif(rs.getString(1));
                Condominio.setRazonS(rs.getString(2));
                Condominio.setTelefono(rs.getString(3));
                Condominio.setCorreoElectro(rs.getString(4));
                listaPersona.add(Condominio);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPersona;

    }

    public boolean Buscar(Condominio cond) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM condominio WHERE rif=? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, cond.getRif());

            rs = ps.executeQuery();

            if (rs.next()) {

                cond.setRif(rs.getString("rif"));
                cond.setRazonS(rs.getString("razon_social"));
                cond.setTelefono(rs.getString("telefono"));
                cond.setCorreoElectro(rs.getString("correo_electronico"));

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

    public boolean modificar(Condominio cond) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE condominio SET razon_social=?, telefono=?, correo_electronico=? WHERE rif=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, cond.getRazonS());
            ps.setString(2, cond.getTelefono());
            ps.setString(3, cond.getCorreoElectro());
            ps.setString(4, cond.getRif());

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
    
    
    
     public boolean eliminarunidadcondominio(Condominio co) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE unidades SET activo=0 WHERE id_condominio=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getRif());
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
     
    public boolean eliminar(Condominio prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE condominio SET activo=0 WHERE rif=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getRif());
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
    
      public boolean activar(Condominio prs) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE condominio SET activo=1 WHERE rif=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prs.getRif());
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

    public ArrayList<Condominio> cuentacondominiomodificar() {
        ArrayList listaPersona = new ArrayList();
        Condominio Condominio = new Condominio();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT rif , razon_social, puente_condominio_cuenta.id_cuenta as cuenta FROM condominio left join puente_condominio_cuenta on puente_condominio_cuenta.id_condominio=condominio.rif and puente_condominio_cuenta.id_cuenta=? where condominio.activo=1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getRif());

            rs = ps.executeQuery();

            while (rs.next()) {
                Condominio = new Condominio();

                //prs = new Persona();
                Condominio.setRif(rs.getString("rif"));
                Condominio.setRazonS(rs.getString("razon_social"));
                Condominio.cuen.setN_cuenta(rs.getString("cuenta"));

                listaPersona.add(Condominio);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPersona;

    }
    
    public ArrayList<Condominio> propietariocondominiomodificar() {
        ArrayList listaPersona = new ArrayList();
        Condominio Condominio = new Condominio();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT rif , razon_social, puente_propietario_condominio.id_propietario as prop FROM condominio left join puente_propietario_condominio on puente_propietario_condominio.id_condominio=condominio.rif and puente_propietario_condominio.id_propietario=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getRif());

            rs = ps.executeQuery();

            while (rs.next()) {
                Condominio = new Condominio();

                //prs = new Persona();
                Condominio.setRif(rs.getString("rif"));
                Condominio.setRazonS(rs.getString("razon_social"));
                Condominio.cuen.setN_cuenta(rs.getString("prop"));

                listaPersona.add(Condominio);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPersona;

    }

}
