package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class ConceptoGasto extends ConexionBD {

    private Integer id;
    private String nombre;
    private String descripcion;
    private CategoriaGasto categoria = new CategoriaGasto();

    private Connection con;

    public boolean registrar() {

        try {
            int ind;
            ps = null;
            con = getConexion();

            String sql = "SELECT agregar_concepto(?,?,?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getDescripcion());
            ps.setInt(ind++, categoria.getId());
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

    public boolean modificar(ConceptoGasto modConGas) {
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT modificar_concepto(?,?,?,?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getDescripcion());
            ps.setInt(ind++, categoria.getId());
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

    public boolean eliminar(ConceptoGasto modConGas) {
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_concepto(?,?);";

        try {
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

    public boolean buscarInactivo(ConceptoGasto modConGas) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM concepto_gasto WHERE nom_concepto=? and activo=false";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {

                modConGas.setId(rs.getInt("id"));

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

    public boolean activar(ConceptoGasto modConGas) {
        int ind;
        ps = null;
        con = getConexion();

        String sql = "SELECT reactivar_concepto(?,?,?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getDescripcion());
            ps.setInt(ind++, categoria.getId());
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

    public boolean buscarC(ConceptoGasto modC) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT concepto_gasto.id, concepto_gasto.nom_concepto, concepto_gasto.descripcion, categoriagasto.nombre"
                + " FROM concepto_gasto INNER JOIN categoriagasto ON categoriagasto.id=concepto_gasto.id_categoria"
                + " WHERE nom_concepto=? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());

            rs = ps.executeQuery();

            if (rs.next()) {

                setId(rs.getInt("id"));
                setNombre(rs.getString("nom_concepto"));
                setDescripcion(rs.getString("descripcion"));
                categoria.setNombre(rs.getString("nombre"));

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

    public ArrayList<ConceptoGasto> listar() {
        try {
            ArrayList lista = new ArrayList();
            ConceptoGasto item;

            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT * FROM v_concepto_gasto WHERE activo = true;";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                item = new ConceptoGasto();

                item.setId(rs.getInt("id"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.categoria.setId(rs.getInt("id_categoria"));
                item.categoria.setNombre(rs.getString("nombre_categoria"));

                lista.add(item);
            }

            return lista;

        } catch (SQLException e) {
            System.err.println(e);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean Buscargas(ConceptoGasto modCatGas) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM gasto_comun where id_concepto=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modCatGas.getId());

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

    public boolean Buscarcuo(ConceptoGasto modCatGas) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * from puente_concepto_factura  inner join facturas_proveedores on puente_concepto_factura.id_factura_proveedor = facturas_proveedores.id where id_concepto=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modCatGas.getId());

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

    public boolean Buscarconcepto(ConceptoGasto modCatGas) {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT * FROM categoriagasto where id=? and activo=true;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modCatGas.categoria.getId());

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

    public ArrayList<ConceptoGasto> listarConcepto1() {
        ArrayList listaPersona = new ArrayList();
        ConceptoGasto modConGas;

        con = getConexion();
        ps = null;
        rs = null;
        String sql = "SELECT concepto_gasto.id, nom_concepto, concepto_gasto.descripcion, categoriagasto.nombre, categoriagasto.id FROM concepto_gasto "
                + "INNER JOIN categoriagasto ON concepto_gasto.id_categoria=categoriagasto.id where concepto_gasto.activo=false";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                modConGas = new ConceptoGasto();
                modConGas.setId(rs.getInt(1));
                modConGas.setNombre(rs.getString(2));
                modConGas.setDescripcion(rs.getString(3));
                modConGas.categoria.setNombre(rs.getString(4));
                modConGas.categoria.setId(rs.getInt(5));

                listaPersona.add(modConGas);
            }

        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaPersona;

    }

    public boolean buscarid(ConceptoGasto modcon) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT id FROM concepto_gasto where nom_concepto=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modcon.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {

                modcon.setId(rs.getInt("id"));

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaGasto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaGasto categoria) {
        this.categoria = categoria;
    }

}
