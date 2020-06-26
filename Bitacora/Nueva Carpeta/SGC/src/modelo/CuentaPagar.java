package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class CuentaPagar extends ConexionBD {

    private Integer id;
    private String descripcion;
    private Gasto gasto = new Gasto();
    private String referencia;
    private FormaPago formaPago = new FormaPago();
    private Cuenta cuenta = new Cuenta();
    private String moneda;
    private Double monto;
    private Date fecha;
    private Fondo fondo = new Fondo();
    private Double tasaCambio = 0.;

    private Connection con;

    public ArrayList<CuentaPagar> listar() {
        ArrayList lista = new ArrayList();
        CuentaPagar item;

        con = getConexion();
        ps = null;
        rs = null;
        String sql = "SELECT * FROM v_cuenta_pagar;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                item = new CuentaPagar();

                item.id = rs.getInt("id");
                item.setReferencia(rs.getString("num_ref"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setMonto(rs.getDouble("monto"));
                item.setMoneda(rs.getString("moneda"));
                item.setTasaCambio(rs.getDouble("tasa_cambio"));
                item.setFecha(rs.getDate("fecha"));
                item.gasto.setId(rs.getInt("id_gasto"));
                item.gasto.setNombre(rs.getString("gasto"));
                item.cuenta.setN_cuenta(rs.getString("n_cuenta"));
                item.cuenta.getBanco().setId(rs.getInt("id_banco"));
                item.cuenta.getBanco().setNombre_banco(rs.getString("banco"));
                item.fondo.setId(rs.getInt("id_fondo"));
                item.fondo.setTipo(rs.getString("fondo"));
                item.formaPago.setId(rs.getInt("id_forma_pago"));
                item.formaPago.setForma_pago(rs.getString("forma_pago"));

                lista.add(item);
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

        return lista;
    }

    public Boolean registrar() {

        ps = null;
        con = getConexion();
        int ind;

        String sql = "SELECT agregar_cuenta_pagar(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getDescripcion());
            ps.setString(ind++, getReferencia());
            ps.setString(ind++, getMoneda());
            ps.setDouble(ind++, getMonto());
            ps.setDate(ind++, getFecha());

            if (tasaCambio != 0.) {
                ps.setDouble(ind++, getTasaCambio());

            } else {
                ps.setNull(ind++, java.sql.Types.NULL);
            }

            ps.setInt(ind++, gasto.getId());
            ps.setInt(ind++, formaPago.getId());
            ps.setString(ind++, cuenta.getN_cuenta());
            ps.setInt(ind++, fondo.getId());
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(Double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

}
