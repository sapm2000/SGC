package modelo;

import controlador.Validacion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Fondo extends ConexionBD {

    private String tipo;
    private int id;
    private java.sql.Date fecha;
    private double monto_inicial;
    private String observacion;
    private String descripcion;
    private double saldo;
    private String moneda;

    private Connection con;

    public boolean registrar(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_fondos(?,?,?,?,?,?,?);";

        try {

            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setString(ind++, getTipo());
            ps.setDate(ind++, getFecha());
            ps.setString(ind++, getDescripcion());
            ps.setString(ind++, getObservacion());
            ps.setDouble(ind++, getMonto_inicial());
            ps.setString(ind++, getMoneda());

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
    
    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM fondos WHERE activo = true;";

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

    public ArrayList<Fondo> listar(int status) {
        ArrayList listaFondo = new ArrayList();
        Fondo item;

        con = getConexion();
        ps = null;
        rs = null;
        String sql = "";

        if (status == 1) {
            sql = "SELECT id, tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda FROM fondos WHERE saldo > 0 AND moneda = ? AND activo = true;";
        }
        if (status == 2) {
            sql = "SELECT id, tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda FROM fondos WHERE activo=true AND moneda=?;";
        }
        if (status == 3) {
            sql = "SELECT id, tipo, fecha, descripcion, observaciones, monto_inicial, saldo, moneda FROM fondos WHERE activo=true;";
        }

        try {
            ps = con.prepareStatement(sql);

            if (status != 3) {
                ps.setString(1, getMoneda());
            }

            rs = ps.executeQuery();

            while (rs.next()) {

                item = new Fondo();

                item.setId(rs.getInt("id"));
                item.setTipo(rs.getString("tipo"));
                item.setFecha(rs.getDate("fecha"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setObservacion(rs.getString("observaciones"));
                item.setMonto_inicial(rs.getDouble("monto_inicial"));
                item.setSaldo(rs.getDouble("saldo"));
                item.setMoneda(rs.getString("moneda"));

                listaFondo.add(item);
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

        return listaFondo;
    }

    public boolean buscar(Fondo modfon) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT id, fecha, descripcion, observaciones, monto_inicial, saldo, moneda FROM fondos where tipo=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modfon.getTipo());
            rs = ps.executeQuery();
            if (rs.next()) {
                modfon.setId(rs.getInt("id"));
                modfon.setFecha(rs.getDate("fecha"));
                modfon.setDescripcion(rs.getString("descripcion"));
                modfon.setObservacion(rs.getString("observaciones"));
                modfon.setMonto_inicial(rs.getInt("monto_inicial"));
                modfon.setSaldo(rs.getDouble("saldo"));
                modfon.setMoneda(rs.getString("moneda"));

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

    public boolean buscar1(Fondo modfon) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT id, fecha, descripcion, observaciones, monto_inicial, saldo FROM fondos where tipo=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modfon.getTipo());
            rs = ps.executeQuery();
            if (rs.next()) {
                modfon.setId(rs.getInt("id"));
                modfon.setSaldo(rs.getDouble("saldo"));

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

    public boolean modificar(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_fondos(?,?,?,?,?);";

        try {
            int ind;
            ind = 1;

            ps = con.prepareStatement(sql);
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setString(ind++, getTipo());
            ps.setString(ind++, getDescripcion());
            ps.setString(ind++, getObservacion());
            ps.setInt(ind++, getId());

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
// fondeas un carro aqui o que samuel??

    public boolean fondear(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE fondos SET  saldo=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setDouble(1, Double.parseDouble(Validacion.formatopago.format(getSaldo())));

            ps.setInt(2, getId());
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

    public boolean eliminar(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_fondos(?,?)";

        try {

            ps = con.prepareStatement(sql);
            int ind;
            ind = 1;
            ps.setInt(ind++, SGC.usuarioActual.getId());
            ps.setInt(ind++, getId());

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

    public boolean activar(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_fondo(?,?);";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
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

    public boolean restarFondo(Float saldoNuevo) {
        ps = null;
        con = getConexion();

        String sql = "UPDATE fondos SET saldo = saldo - ? WHERE id = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, saldoNuevo);
            ps.setInt(2, getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
