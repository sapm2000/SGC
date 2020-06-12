package modelo;

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

        String sql = "INSERT INTO fondos(tipo, fecha, descripcion, observaciones, monto_inicial, saldo, id_condominio, moneda) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try {

            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getTipo());
            ps.setDate(ind++, getFecha());
            ps.setString(ind++, getDescripcion());
            ps.setString(ind++, getObservacion());
            ps.setDouble(ind++, getMonto_inicial());
            ps.setDouble(ind++, getMonto_inicial());
            ps.setString(ind++, SGC.condominioActual.getRif());
            ps.setString(ind++, getMoneda());
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

    public ArrayList<Fondo> listar(int status) {
        ArrayList listaFondo = new ArrayList();
        Fondo modfon;

        con = getConexion();
        ps = null;
        rs = null;
        String sql = "";

        if (status == 1) {
            sql = "SELECT tipo, fecha, descripcion, observaciones, monto_inicial, saldo, id, moneda FROM fondos where id_condominio=? AND saldo > 0 and activo=true;";
        }
        if (status == 2) {
            sql = "SELECT tipo, fecha, descripcion, observaciones, monto_inicial, saldo, id, moneda FROM fondos where id_condominio=? and activo=true and moneda=?;";
        }
        if (status == 3) {
            sql = "SELECT tipo, fecha, descripcion, observaciones, monto_inicial, saldo, id, moneda FROM fondos where id_condominio=? and activo=false;";
        }
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            ps.setString(2, getMoneda());
            rs = ps.executeQuery();

            while (rs.next()) {

                modfon = new Fondo();

                modfon.setTipo(rs.getString("tipo"));
                modfon.setFecha(rs.getDate("fecha"));
                modfon.setDescripcion(rs.getString("descripcion"));
                modfon.setObservacion(rs.getString("observaciones"));
                modfon.setMonto_inicial(rs.getDouble("monto_inicial"));
                modfon.setSaldo(rs.getDouble("saldo"));
                modfon.setId(rs.getInt("id"));
                modfon.setMoneda(rs.getString("moneda"));

                listaFondo.add(modfon);
            }
        } catch (Exception e) {
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
        String sql = "SELECT id, fecha, descripcion, observaciones, monto_inicial, saldo, moneda FROM fondos where id_condominio=? and tipo=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            ps.setString(2, modfon.getTipo());
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
        String sql = "SELECT id, fecha, descripcion, observaciones, monto_inicial, saldo FROM fondos where id_condominio=? and tipo=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            ps.setString(2, modfon.getTipo());
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

        String sql = "UPDATE fondos SET  fecha=?, descripcion=?, observaciones=?, monto_inicial=?, saldo=?, tipo=?, moneda=? WHERE id=?";

        try {
            int ind;
            ind = 1;

            ps = con.prepareStatement(sql);
            ps.setDate(ind++, getFecha());
            ps.setString(ind++, getDescripcion());
            ps.setString(ind++, getObservacion());
            ps.setDouble(ind++, getMonto_inicial());
            ps.setDouble(ind++, getSaldo());

            ps.setString(ind++, getTipo());
            ps.setString(ind++, getMoneda());
            ps.setInt(ind++, getId());
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
// fondeas un carro aqui o que samuel??

    public boolean fondear(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE fondos SET  saldo=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setDouble(1, getSaldo());

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

        String sql = "UPDATE fondos SET activo=false WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

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

    public boolean activar(Fondo modfon) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE fondos SET activo=true WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

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
