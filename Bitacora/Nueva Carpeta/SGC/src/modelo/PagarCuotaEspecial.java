package modelo;

import controlador.Validacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import sgc.SGC;

public class PagarCuotaEspecial extends ConexionBD {

    private Integer id;
    private String numero_ref;
    private String forma_pago;
    private String descripcion;
    private Double monto;
    private Date fecha;
    private Cuenta cuenta;
    private Fondo fondo;
    private Gasto cuo_especial;
    private Proveedores proveedor;
    private Banco banco;

    public PagarCuotaEspecial() {
        cuenta = new Cuenta();
        fondo = new Fondo();
        cuo_especial = new Gasto();
        proveedor = new Proveedores();
        banco = new Banco();
    }

    public boolean registrar() {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO pagar_cuota_especial (numero_ref, forma_pago, descripcion, monto, fecha,"
                + " id_cuenta, id_fondo, id_cuota_e)"
                + " VALUES (?,?,?,?,?,?,?,?)";
        try {
            int i = 1;
            ps = con.prepareStatement(sql);
            ps.setString(i++, getNumero_ref());
            ps.setString(i++, getForma_pago());
            ps.setString(i++, getDescripcion());
            ps.setDouble(i++, getMonto());
            ps.setDate(i++, Validacion.convert(getFecha()));
            ps.setString(i++, getCuenta().getN_cuenta());
            ps.setInt(i++, getFondo().getId());
            ps.setInt(i++, getCuo_especial().getId());
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

    public ArrayList<PagarCuotaEspecial> listar() {
        ArrayList listaPagar = new ArrayList();
        PagarCuotaEspecial modPagarCuo;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM v_pagar_cuota WHERE id_condominio = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {
                modPagarCuo = new PagarCuotaEspecial();

                modPagarCuo.setNumero_ref(rs.getString("numero_ref"));
                modPagarCuo.setForma_pago(rs.getString("forma_pago"));
                modPagarCuo.setDescripcion(rs.getString("nom_concepto"));
                modPagarCuo.setMonto(rs.getDouble("monto"));
                modPagarCuo.setFecha(rs.getDate("fecha"));
                modPagarCuo.getProveedor().setNombre(rs.getString("nombre"));
                modPagarCuo.getCuenta().setN_cuenta(rs.getString("n_cuenta"));
                modPagarCuo.getBanco().setNombre_banco(rs.getString("nombre_banco"));
                modPagarCuo.getFondo().setTipo(rs.getString("tipo"));
                modPagarCuo.getCuo_especial().setPagado(rs.getString("pagado"));

                listaPagar.add(modPagarCuo);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaPagar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero_ref() {
        return numero_ref;
    }

    public void setNumero_ref(String numero_ref) {
        this.numero_ref = numero_ref;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Fondo getFondo() {
        return fondo;
    }

    public void setFondo(Fondo fondo) {
        this.fondo = fondo;
    }

    public Gasto getCuo_especial() {
        return cuo_especial;
    }

    public void setCuo_especial(Gasto cuo_especial) {
        this.cuo_especial = cuo_especial;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

}
