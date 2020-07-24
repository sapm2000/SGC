package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Sancion extends ConexionBD {

    private String tipo;
    private int mes;
    private int año;
    private double monto;
    private String descripcion;
    public Unidades uni = new Unidades();
    private int cantidad_de_unidades;
    private String estado;
    private int id;
    private String moneda;

    private Connection con;

    public boolean registrarsancion(Sancion modsan) {
        ps = null;
        con = getConexion();

        String sql = "SELECT agregar_sancion(?,?,?,?,?,?,?,?)";

        try {
            int ind;
            ind = 1;

            ps = con.prepareStatement(sql);
            ps.setString(ind++, modsan.getTipo());
            ps.setInt(ind++, modsan.getMes());
            ps.setInt(ind++, modsan.getAño());
            ps.setDouble(ind++, modsan.getMonto());
            ps.setString(ind++, modsan.getDescripcion());
            ps.setString(ind++, modsan.getEstado());
            ps.setString(ind++, modsan.getMoneda());
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

    public boolean buscId(Sancion modsan) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT MAX(id) as id from sancion";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modsan.setId(rs.getInt("id"));

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

    public boolean registrar_sancion_unidad(Sancion modsan) {

        ps = null;
        con = getConexion();

        String sql = "INSERT INTO puente_sancion_unidad(id_sancion, id_unidad) VALUES (?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, uni.getId());

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

    public ArrayList<Sancion> listarSanciones() {
        ArrayList listaSancion = new ArrayList();
        Sancion modsan;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT sancion.id, tipo, mes, anio, monto, descripcion, count(id_sancion) as total, estado, moneda FROM sancion inner join puente_sancion_unidad on puente_sancion_unidad.id_sancion=sancion.id inner join unidad on puente_sancion_unidad.id_unidad=unidad.id group by sancion.id";
        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                int i;
                i = 1;
                modsan = new Sancion();

                modsan.setId(rs.getInt(i++));
                modsan.setTipo(rs.getString(i++));
                modsan.setMes(rs.getInt(i++));
                modsan.setAño(rs.getInt(i++));
                modsan.setMonto(rs.getDouble(i++));
                modsan.setDescripcion(rs.getString(i++));
                modsan.setCantidad_de_unidades(rs.getInt(i++));
                modsan.setEstado(rs.getString(i++));
                modsan.setMoneda(rs.getString(i++));

                listaSancion.add(modsan);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaSancion;
    }

    public ArrayList<Sancion> listarSancionesCerrarmes() {
        ArrayList listaSancion = new ArrayList();
        Sancion modsan;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id_sancion, tipo, sancion.monto, id_unidad, moneda FROM puente_sancion_unidad inner join sancion on puente_sancion_unidad.id_sancion=sancion.id inner join unidad on puente_sancion_unidad.id_unidad=unidad.id where sancion.mes=? and sancion.anio=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, getMes());
            ps.setInt(2, getAño());
            rs = ps.executeQuery();

            while (rs.next()) {

                modsan = new Sancion();

                modsan.setId(rs.getInt(1));

                modsan.setTipo(rs.getString(2));
                modsan.setMonto(rs.getDouble(3));
                modsan.uni.setId(rs.getInt(4));
                modsan.setMoneda(rs.getString(5));

                listaSancion.add(modsan);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaSancion;
    }

    public boolean buscarSancion(Sancion modsan) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM sancion where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modsan.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modsan.setTipo(rs.getString("tipo"));
                modsan.setMes(rs.getInt("mes"));
                modsan.setAño(rs.getInt("anio"));
                modsan.setMonto(rs.getDouble("monto"));
                modsan.setDescripcion(rs.getString("descripcion"));
                modsan.setEstado(rs.getString("estado"));
                modsan.setMoneda(rs.getString("moneda"));

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

    public boolean buscarSancionRepetido(Sancion modsan) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT sancion.id FROM sancion inner join puente_sancion_unidad on puente_sancion_unidad.id_sancion=sancion.id inner join unidad on puente_sancion_unidad.id_unidad=unidad.id where tipo='Interes de mora' and mes=? and anio=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, modsan.getMes());
            ps.setInt(2, modsan.getAño());

            rs = ps.executeQuery();
            if (rs.next()) {

                modsan.setId(rs.getInt("id"));

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

    public ArrayList<Sancion> listarunidadesmod() {
        ArrayList listaunimod = new ArrayList();
        Sancion modsan = new Sancion();

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "select unidad.id, unidad.n_unidad, puente_sancion_unidad.id_sancion from unidad left join puente_sancion_unidad on puente_sancion_unidad.id_unidad=unidad.id and puente_sancion_unidad.id_sancion=?  where  unidad.activo=true";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

            rs = ps.executeQuery();

            while (rs.next()) {
                modsan = new Sancion();

                //prs = new Persona();
                modsan.uni.setNumeroUnidad(rs.getString("n_unidad"));
                modsan.uni.setId(rs.getInt("id"));
                modsan.setId(rs.getInt("id_sancion"));

                listaunimod.add(modsan);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaunimod;

    }

    public ArrayList<Sancion> listarunidadesmodprocesadas() {
        ArrayList listaunimod = new ArrayList();
        Sancion modsan = new Sancion();

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "select unidad.id, unidad.n_unidad, puente_sancion_unidad.id_sancion from unidad left join puente_sancion_unidad on puente_sancion_unidad.id_unidad=unidad.id and puente_sancion_unidad.id_sancion=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());

           

            rs = ps.executeQuery();

            while (rs.next()) {
                modsan = new Sancion();

                //prs = new Persona();
                modsan.uni.setNumeroUnidad(rs.getString("n_unidad"));
                modsan.uni.setId(rs.getInt("id"));
                modsan.setId(rs.getInt("id_sancion"));

                listaunimod.add(modsan);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaunimod;

    }

    public boolean modificarSancion(Sancion modsan) {

        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_sancion(?,?,?,?,?,?,?,?);";

        try {
            int ind;
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getTipo());
            ps.setInt(ind++, getMes());
            ps.setInt(ind++, getAño());
            ps.setDouble(ind++, getMonto());
            ps.setString(ind++, getDescripcion());
            ps.setString(ind++, getMoneda());
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

    public boolean borrarpuentesancion(Sancion modsan) {

        ps = null;
        con = getConexion();

        String sql = "DELETE FROM puente_sancion_unidad WHERE id_sancion=?";

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

    public boolean eliminarsancion(Sancion modsan) {

        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_sancion(?,?)";

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad_de_unidades() {
        return cantidad_de_unidades;
    }

    public void setCantidad_de_unidades(int cantidad_de_unidades) {
        this.cantidad_de_unidades = cantidad_de_unidades;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
