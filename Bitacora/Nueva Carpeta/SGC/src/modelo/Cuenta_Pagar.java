package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Cuenta_Pagar extends ConexionBD {

    private int id;
    private String num_ref;
    private String forma_pago;
    private String descripcion;
    private float monto;
    private Date fecha;
    private String id_proveedor;
    private String id_cuenta;
    private int id_fondo;
    private String nom_proveedor;
    private Cuenta modCuenta = new Cuenta();
    private Fondo modFondo = new Fondo();
    private Banco modBanco = new Banco();

    public Banco getModBanco() {
        return modBanco;
    }

    public void setModBanco(Banco modBanco) {
        this.modBanco = modBanco;
    }
    
    public String getNom_proveedor() {
        return nom_proveedor;
    }

    public void setNom_proveedor(String nom_proveedor) {
        this.nom_proveedor = nom_proveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum_ref() {
        return num_ref;
    }

    public void setNum_ref(String num_ref) {
        this.num_ref = num_ref;
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public int getId_fondo() {
        return id_fondo;
    }

    public void setId_fondo(int id_fondo) {
        this.id_fondo = id_fondo;
    }

    public boolean registrarPago(Cuenta_Pagar modCuentaPa) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cuenta_pagar(num_ref, forma_pago, descripcion, monto, fecha, id_proveedor, id_cuenta, id_fondo) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getNum_ref());
            ps.setString(2, getForma_pago());
            ps.setString(3, getDescripcion());
            ps.setFloat(4, getMonto());
            ps.setDate(5, getFecha());
            ps.setString(6, getId_proveedor());
            ps.setString(7, getModCuenta().getN_cuenta());
            ps.setInt(8, getModFondo().getId());
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

    public boolean cargarProveedor(int id) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT gc.id_proveedor, pr.nombre FROM gasto_comun AS gc INNER JOIN proveedores AS pr ON pr.cedula=gc.id_proveedor WHERE id=?;";
        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                setId_proveedor(rs.getString(1));
                setNom_proveedor(rs.getString(2));
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

    public ArrayList<Cuenta_Pagar> listar() {
        ArrayList listaPagar = new ArrayList();
        Cuenta_Pagar modPagar;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM v_cuenta_pagar WHERE id_condominio = ?;";
        /*String sql = "SELECT cp.num_ref, cp.forma_pago, cp.descripcion, cp.monto, cp.fecha, prov.nombre, cu.n_cuenta, ba.nombre_banco,"
                + " fon.tipo  FROM cuenta_pagar AS cp INNER JOIN proveedores AS prov ON prov.cedula = cp.id_proveedor INNER JOIN"
                + " cuenta AS cu ON cu.n_cuenta = cp.id_cuenta INNER JOIN fondos AS fon ON fon.id = cp.id_fondo INNER JOIN banco AS ba "
                + "ON ba.id=cu.id_banco WHERE fon.id_condominio=? ORDER BY cp.fecha desc;";*/
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {
                modPagar = new Cuenta_Pagar();

                modPagar.setNum_ref(rs.getString("num_ref"));
                modPagar.setForma_pago(rs.getString("forma_pago"));
                modPagar.setDescripcion(rs.getString("descripcion"));
                modPagar.setMonto(rs.getFloat("monto"));
                modPagar.setFecha(rs.getDate("fecha"));
                modPagar.setNom_proveedor(rs.getString("nombre"));
                modPagar.modCuenta.setN_cuenta(rs.getString("n_cuenta"));
                modPagar.modBanco.setNombre_banco(rs.getString("nombre_banco"));
                modPagar.modFondo.setTipo(rs.getString("tipo"));

                listaPagar.add(modPagar);
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

    public Cuenta getModCuenta() {
        return modCuenta;
    }

    public void setModCuenta(Cuenta modCuenta) {
        this.modCuenta = modCuenta;
    }

    public Fondo getModFondo() {
        return modFondo;
    }

    public void setModFondo(Fondo modFondo) {
        this.modFondo = modFondo;
    }

}
