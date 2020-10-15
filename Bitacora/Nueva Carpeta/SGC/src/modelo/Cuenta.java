package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgc.SGC;

public class Cuenta extends ConexionBD {

    private String n_cuenta;
    private String tipo;
    private Banco banco = new Banco();
    private Persona beneficiario = new Persona();
    private Condominio condominio = new Condominio();

    private Connection con;

    public boolean buscarInactivo(Cuenta modcu) {
        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM cuenta WHERE n_cuenta=? and activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getN_cuenta());
            rs = ps.executeQuery();
            if (rs.next()) {

                modcu.setN_cuenta(rs.getString("n_cuenta"));

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

    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM cuenta WHERE activo = true;";

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

    public Boolean existeInactivo() {
        try {
            con = getConexion();
            ps = null;
            rs = null;

            int ind;

            String sql = "SELECT n_cuenta FROM v_usuario_inactivo WHERE n_cuenta = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_cuenta());

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

            String sql = "SELECT reactivar_cuenta(?,?)";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_cuenta());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                return rs.getBoolean(1);

            } else {
                return false;
            }

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

    public Boolean registrar() {
        ps = null;
        con = getConexion();
        int ind;

        String sql = "SELECT agregar_cuenta(?,?,?,?,?);";

        try {
            ind = 1;

            ps = con.prepareStatement(sql);

            ps.setString(ind++, getN_cuenta());
            ps.setString(ind++, getTipo());
            ps.setInt(ind++, getBanco().getId());

            if (getBeneficiario().getCedula() != null) {
                ps.setString(ind++, getBeneficiario().getCedula());

            } else {
                ps.setString(ind++, SGC.condominioActual.getRif());
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

    public ArrayList<Cuenta> listarcuenta() {
        ArrayList listaCuenta = new ArrayList();
        Cuenta cuenta;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT * FROM v_cuenta where activo = true;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                cuenta = new Cuenta();

                cuenta.setN_cuenta(rs.getString("n_cuenta"));
                cuenta.setTipo(rs.getString("tipo"));
                cuenta.banco.setId(rs.getInt("id_banco"));
                cuenta.banco.setNombre_banco(rs.getString("banco"));
                cuenta.getBeneficiario().setCedula(rs.getString("ci_persona"));
                cuenta.getBeneficiario().setpNombre(rs.getString("nombre"));
                cuenta.getBeneficiario().setpApellido(rs.getString("apellido"));
                cuenta.getCondominio().setRif(rs.getString("rif_condominio"));
                cuenta.getCondominio().setRazonS(rs.getString("razon_social"));

                listaCuenta.add(cuenta);
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

        return listaCuenta;
    }

    public Boolean buscarPersona(String cedula) {
        try {
            ps = null;
            rs = null;
            con = getConexion();
            String sql = "SELECT p_nombre, p_apellido FROM persona WHERE cedula=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();

            if (rs.next()) {
                getBeneficiario().setpNombre(rs.getString("p_nombre"));
                getBeneficiario().setpApellido(rs.getString("p_apellido"));

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
        try {
            ps = null;
            con = getConexion();
            int ind;

            String sql = "SELECT modificar_cuenta(?,?,?,?,?)";

            ind = 1;

            ps = con.prepareStatement(sql);
            ps.setString(ind++, getTipo());
            ps.setInt(ind++, banco.getId());
            ps.setString(ind++, getBeneficiario().getCedula());
            ps.setString(ind++, getN_cuenta());
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

    public boolean eliminar() {

        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_cuenta(?,?)";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getN_cuenta());
            ps.setInt(ind++, SGC.usuarioActual.getId());
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

    public Boolean existe() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT n_cuenta FROM v_cuenta WHERE n_cuenta = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_cuenta());

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

    public boolean activarcuenta(Cuenta modcun) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE cuenta SET activo=true WHERE n_cuenta=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getN_cuenta());
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

    public String getN_cuenta() {
        return n_cuenta;
    }

    public void setN_cuenta(String n_cuenta) {
        this.n_cuenta = n_cuenta;
    }

    public Persona getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Persona beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

}
