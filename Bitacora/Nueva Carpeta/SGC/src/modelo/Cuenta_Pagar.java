package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Cuenta_Pagar extends ConexionBD{
    
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
    
    public boolean registrarPago(Cuenta_Pagar modCuentaPa){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO cuenta_pagar(num_ref, forma_pago, descripcion, monto, fecha, id_proveedor, id_cuenta, id_fondo) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps=con.prepareStatement(sql);
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
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    
    }
    public boolean cargarProveedor(int id){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT gc.id_proveedor, pr.nombre FROM gasto_comun AS gc INNER JOIN proveedores AS pr ON pr.cedula=gc.id_proveedor WHERE id=?;";
        try {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                setId_proveedor(rs.getString(1));
                setNom_proveedor(rs.getString(2));
                return true;
                
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
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
