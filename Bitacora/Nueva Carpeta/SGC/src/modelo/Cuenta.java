package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import sgc.SGC;

public class Cuenta extends ConexionBD {

    private String n_cuenta;
    private Persona beneficiario = new Persona();
    private String tipo;
    private Banco banco = new Banco();

    Connection con = getConexion();

    public boolean buscarInactivo(Cuenta modcu) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
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

    public Boolean existeInactivo() {
        try {
            Connection con = getConexion();
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
            Logger.getLogger(Unidades.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void llenar_banco(JComboBox banco) {

//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT nombre_banco FROM banco where activo=true order by nombre_banco";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            banco.addItem("Seleccione el Banco");

            while (result.next()) {

                banco.addItem(result.getString("nombre_banco"));

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        } finally {

            if (conectar != null) {

                try {

                    conectar.close();
                    result.close();

                    conectar = null;
                    result = null;

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex);

                }

            }

        }

    }

    public Boolean reactivar() {
        try {
            ps = null;
            con = getConexion();

            int ind;

            String sql = "UPDATE cuenta SET activo = true WHERE n_cuenta = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_cuenta());

            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Propietarios.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean registrar() {

        ps = null;
        Connection con = getConexion();
        int ind;

        String sql = "INSERT INTO cuenta (n_cuenta, tipo, id_banco, ci_persona) VALUES (?,?,?,?);";

        try {
            ind = 1;

            ps = con.prepareStatement(sql);

            ps.setString(ind++, getN_cuenta());
            ps.setString(ind++, getTipo());
            ps.setInt(ind++, getBanco().getId());
            ps.setString(ind++, getBeneficiario().getCedula());

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

    public ArrayList<Cuenta> listarcuenta() {
        ArrayList listaCuenta = new ArrayList();
        Cuenta modcu;

        Connection con = getConexion();
        ps = null;
        rs = null;
        int ind;

        String sql = "SELECT * FROM v_cuenta;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ind = 1;

            while (rs.next()) {

                modcu = new Cuenta();

                modcu.setN_cuenta(rs.getString(ind++));
                modcu.setTipo(rs.getString(ind++));
                modcu.banco.setId(rs.getInt(ind++));
                modcu.banco.setNombre_banco(rs.getString(ind++));
                modcu.getBeneficiario().setCedula(rs.getString(ind++));
                modcu.getBeneficiario().setpNombre(rs.getString(ind++));
                modcu.getBeneficiario().setpApellido(rs.getString(ind++));

                listaCuenta.add(modcu);
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

//    public boolean buscarcuenta(Cuenta modcun) {
//
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        Connection con = getConexion();
//        String sql = "SELECT cuenta.*, banco.* FROM cuenta inner join banco on cuenta.id_banco=banco.id WHERE n_cuenta=?";
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, modcun.getN_cuenta());
//            rs = ps.executeQuery();
//            if (rs.next()) {
//
//                modcun.setCedula(rs.getString("cedula"));
//                modcun.setBeneficiario(rs.getString("beneficiario"));
//                modcun.setTipo(rs.getString("tipo"));
//                modcun.banco.setNombre_banco(rs.getString("nombre_banco"));
//
//                return true;
//            }
//
//            return false;
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
    public Boolean buscarPersona(String cedula) {
        try {
            ps = null;
            rs = null;
            Connection con = getConexion();
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
        ps = null;
        Connection con = getConexion();
        int ind;

        String sql = "UPDATE cuenta SET tipo=?, id_banco=?, ci_persona=? WHERE n_cuenta=?";

        try {
            ind = 1;

            ps = con.prepareStatement(sql);
            ps.setString(ind++, getTipo());
            ps.setInt(ind++, banco.getId());
            ps.setString(ind++, getBeneficiario().getCedula());
            ps.setString(ind++, getN_cuenta());
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

    public boolean eliminar() {

        ps = null;
        Connection con = getConexion();

        String sql = "UPDATE cuenta SET activo=false WHERE n_cuenta=?";

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

    public Boolean existe() {
        Connection con = getConexion();
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
            Logger.getLogger(Unidades.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    public boolean activarcuenta(Cuenta modcun) {

        PreparedStatement ps = null;
        Connection con = getConexion();

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

    public void llenar_cuentas(JComboBox Cuentas) {
//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT id_cuenta FROM public.puente_condominio_cuenta inner join cuenta on cuenta.n_cuenta=puente_condominio_cuenta.id_cuenta where id_condominio=?;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            pst.setString(1, SGC.condominioActual.getRif());
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            Cuentas.addItem("Seleccione la cuenta depositada");

            while (result.next()) {
                Cuentas.addItem(result.getString("id_cuenta"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        } finally {
            if (conectar != null) {
                try {
                    conectar.close();
                    result.close();

                    conectar = null;
                    result = null;

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
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

}
