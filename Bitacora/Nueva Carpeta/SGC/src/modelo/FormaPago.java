package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class FormaPago extends ConexionBD {

    private int id;
    private String forma_pago;

    private Connection con;

    public boolean registrar(FormaPago modfor) {
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_forma_pago(?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getForma_pago());
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

    public ArrayList<FormaPago> listar() {
        ArrayList listaFormaPago = new ArrayList();
        FormaPago modfor;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT * FROM forma_pago where activo=true";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                modfor = new FormaPago();

                modfor.setId(rs.getInt(1));
                modfor.setForma_pago(rs.getString(2));

                listaFormaPago.add(modfor);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

            return listaFormaPago;
        }
    }

    public boolean buscar(FormaPago modfor) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM forma_pago WHERE forma_pago=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getForma_pago());
            rs = ps.executeQuery();
            if (rs.next()) {

                modfor.setId(rs.getInt("id"));
                modfor.setForma_pago(rs.getString("forma_pago"));

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

    public boolean buscarInactivo(FormaPago modfor) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM forma_pago WHERE forma_pago=? and activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getForma_pago());
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

    public boolean modificar(FormaPago modfor) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_forma_pago(?,?,?)";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setString(ind++, getForma_pago());
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

    public boolean eliminar(FormaPago modfor) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_forma_pago(?,?)";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
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

    public boolean activar(FormaPago modfor) {

        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_forma_pago(?,?)";

        try {

            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getForma_pago());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

}
