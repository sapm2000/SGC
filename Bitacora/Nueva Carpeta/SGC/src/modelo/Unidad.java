package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgc.SGC;

public class Unidad extends ConexionBD {

    private int id;
    private String numeroUnidad;
    private String documento;
    private String direccion;
    private float alicuota;
    private Condominio condominio;
    private ArrayList<Propietarios> propietarios;
    private java.sql.Date fecha_desde;
    private java.sql.Date fecha_hasta;
    private TipoUnidad tipo = new TipoUnidad();

    private Connection con;

    private int estatus;
    private int id_puente;

    public Unidad() {
        condominio = new Condominio();
        propietarios = new ArrayList();

    }

    public boolean buscarId() {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT id from unidad WHERE n_unidad = ?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, numeroUnidad);
            rs = ps.executeQuery();

            if (rs.next()) {

                id = rs.getInt("id");

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

    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM unidad WHERE activo = true;";

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

    public Boolean reactivar() throws SQLException {

        try {

            ps = null;
            con = getConexion();
            boolean resul = false;
            int ind;

            if (vaciarPropietarios()) {

                String sql = "SELECT reactivar_unidad(?,?)";

                ps = con.prepareStatement(sql);

                ind = 1;
                ps.setString(ind++, getNumeroUnidad());
                ps.setInt(ind++, SGC.usuarioActual.getId());

                if (ps.execute()) {

                    rs = ps.getResultSet();
                    rs.next();
                    resul = rs.getBoolean(1);

                } else {

                    return false;
                }

                if (buscarId()) {

                    for (int i = 0; i < propietarios.size(); i++) {

                        agregarPropietario(propietarios.get(i).getCedula());
                    }
                }
            }

            return resul;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();
        }
    }

    public boolean registrar() {

        ps = null;
        con = getConexion();

        int ind;
        boolean resul = false;

        //Registro de los datos de la tabla unidad
        String sql = "SELECT agregar_unidad(?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);

            ind = 1;

            ps.setString(ind++, getNumeroUnidad());
            ps.setString(ind++, getDocumento());
            ps.setString(ind++, getDireccion());
            ps.setInt(ind++, getTipo().getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            if (ps.execute()) {

                rs = ps.getResultSet();
                rs.next();
                resul = rs.getBoolean(1);

            } else {

                return false;
            }

            if (buscarId()) {

                for (int i = 0; i < getPropietarios().size(); i++) {

                    agregarPropietario(propietarios.get(i).getCedula());
                }
            }

            return resul;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();
        }
    }

    private Boolean agregarPropietario(String cedula) throws SQLException {

        ps = null;

        int ind;

        String sql = "INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, estado) VALUES (?,?,1);";

        con = getConexion();
        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setString(ind++, cedula);
        ps.setInt(ind++, getId());
        ps.execute();

        cerrar();

        return true;
    }

    public ArrayList<Unidad> listar() {

        ArrayList listaUnidades = new ArrayList();
        Unidad unidad = new Unidad();

        con = getConexion();
        ps = null;
        rs = null;
        ResultSet rs2 = null;

        String sql = "SELECT * FROM v_unidad WHERE activo = true";

        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            sql = "SELECT * FROM v_unidad_propietario WHERE id = ?";
            ps = con.prepareStatement(sql);

            while (rs.next()) {
                unidad = new Unidad();

                unidad.setId(rs.getInt("id"));
                unidad.setNumeroUnidad(rs.getString("n_unidad"));
                unidad.setDocumento(rs.getString("n_documento"));
                unidad.setDireccion(rs.getString("direccion"));
                unidad.setAlicuota(rs.getFloat("alicuota"));
                unidad.getTipo().setId(rs.getInt("id_tipo"));
                unidad.getTipo().setNombre(rs.getString("tipo"));
                unidad.getTipo().setArea(rs.getFloat("area"));

                ps.setInt(1, unidad.getId());

                rs2 = ps.executeQuery();

                while (rs2.next()) {
                    unidad.getPropietarios().add(new Propietarios(rs2.getString("ci_persona"), rs2.getString("p_nombre"), rs2.getString("s_nombre"), rs2.getString("p_apellido"), rs2.getString("s_apellido"), rs2.getString("telefono"), rs2.getString("correo")));
                }

                listaUnidades.add(unidad);
            }

        } catch (SQLException e) {
            System.err.println(e);

        } finally {

            cerrar();
        }

        return listaUnidades;
    }

    public boolean actualizarAlicuota(Unidad modelo) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE unidad SET alicuota = ? WHERE id = ?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setFloat(1, getAlicuota());
            ps.setInt(2, getId());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();
        }
    }

    public boolean modificar() {
        try {
            ps = null;
            con = getConexion();

            int ind;
            boolean resul = false;
            String sql = "SELECT modificar_unidad(?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getDocumento());
            ps.setString(ind++, getDireccion());
            ps.setInt(ind++, getTipo().getId());
            ps.setInt(ind++, getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                resul = rs.getBoolean(1);

            } else {
                return false;
            }

            if (modificarPropietarios()) {
                return resul;
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

    private Boolean modificarPropietarios() {
        ps = null;

        int numNuevos;
        int numViejos;

        String sql = "SELECT ci_persona FROM v_dueno_unidad WHERE id_unidad = ?;";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, getId());

            rs = ps.executeQuery();

            ArrayList<Propietarios> propietariosViejos;
            propietariosViejos = new ArrayList();

            while (rs.next()) {
                propietariosViejos.add(new Propietarios(rs.getString("ci_persona")));

            }

            numNuevos = getPropietarios().size();
            numViejos = propietariosViejos.size();

            // Ciclo que recorre los propietarios nuevos
            for (int j = 0; j < numNuevos; j++) {
                // Ciclo que recorre los propietarios viejos
                for (int i = 0; i < numViejos; i++) {
                    // Si el propietario de la lista de nuevos coincide con el de la BD
                    if (getPropietarios().get(j).getCedula().equals(propietariosViejos.get(i).getCedula())) {
                        // No se hace nada con él y se elimina de ambos arreglos para dejar de compararlos
                        getPropietarios().remove(j);
                        numNuevos--;
                        propietariosViejos.remove(i);
                        numViejos--;
                        break;

                    }
                }

                // Si el propietario nuevo no está en la lista de viejos
                if (getPropietarios().size() > 0) {
                    // Se agrega como nuevo propietario y se elimina del arreglo de nuevos
                    agregarPropietario(getPropietarios().get(j).getCedula());
                    getPropietarios().remove(j);
                    numNuevos--;
                    j--;

                }
            }

            // Se retiran los viejos propietarios que quedaron en el arreglo de viejos (ya que fueron eliminados)
            retirarPropietarios(propietariosViejos);

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

    private Boolean retirarPropietarios(ArrayList<Propietarios> propietarios) throws SQLException {
        ps = null;

        int ind;

        String sql = "UPDATE puente_unidad_propietarios SET fecha_hasta = LOCALTIMESTAMP(0), estado = 0 WHERE id_unidad = ? AND ci_propietario = ?";

        ps = con.prepareStatement(sql);

        for (int i = 0; i < propietarios.size(); i++) {
            ind = 1;
            ps.setInt(ind++, getId());
            ps.setString(ind++, propietarios.get(i).getCedula());
            ps.execute();

        }

        return true;
    }

    private Boolean vaciarPropietarios() throws SQLException {
        ps = null;

        int ind;

        String sql = "SELECT id FROM v_unidad WHERE n_unidad = ?";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setString(ind++, getNumeroUnidad());

        rs = ps.executeQuery();

        while (rs.next()) {
            setId(rs.getInt("id"));

        }

        sql = "UPDATE puente_unidad_propietarios SET activo = false WHERE id_unidad = ?";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setInt(ind++, getId());

        ps.execute();

        return true;
    }

    public boolean eliminarPuenteUnidad(Unidad moduni) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE puente_unidad_propietarios SET activo=0 WHERE id_unidad=?;";

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

    public boolean eliminar() {
        ps = null;
        con = getConexion();

        int ind;

        String sql = "SELECT eliminar_unidad(?,?);";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());
            if (ps.execute()) {
                rs = ps.getResultSet();

            } else {
                return false;
            }

            sql = "UPDATE puente_unidad_propietarios SET fecha_hasta = LOCALTIMESTAMP(0), estado = 0 WHERE fecha_hasta IS null AND id_unidad = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setInt(ind++, getId());

            ps.execute();

            rs.next();
            return rs.getBoolean(1);

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
        try {
            con = getConexion();
            ps = null;
            rs = null;

            int ind;

            String sql = "SELECT n_unidad FROM v_unidad WHERE n_unidad = ? AND activo = true;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getNumeroUnidad());

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {

            Logger.getLogger(Unidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {

            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);
            }
        }
    }

    public Boolean existeInactivo() {

        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT n_unidad FROM v_unidad WHERE n_unidad = ? AND activo = false;";

        try {

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getNumeroUnidad());

            rs = ps.executeQuery();

            if (rs.next()) {

                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {

            Logger.getLogger(Unidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {

            try {
                con.close();

            } catch (SQLException e) {

                System.err.println(e);
            }
        }
    }

    public ArrayList<Unidad> listarArea() {
        ArrayList listaUnidad = new ArrayList();
        Unidad uni;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT area, unidad.id FROM unidad inner join tipo_unidad ON tipo_unidad.id = unidad.id_tipo where unidad.activo=true";

        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                uni = new Unidad();
                uni.tipo.setArea(rs.getFloat(1));
                uni.setId(rs.getInt(2));

                listaUnidad.add(uni);
            }

        } catch (Exception e) {

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);

            }
        }

        return listaUnidad;

    }

    public int getId_puente() {
        return id_puente;
    }

    public void setId_puente(int id_puente) {
        this.id_puente = id_puente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroUnidad() {
        return numeroUnidad;
    }

    public void setNumeroUnidad(String numeroUnidad) {
        this.numeroUnidad = numeroUnidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoUnidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoUnidad tipo) {
        this.tipo = tipo;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public ArrayList<Propietarios> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(ArrayList<Propietarios> propietarios) {
        this.propietarios = propietarios;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public float getAlicuota() {
        return alicuota;
    }

    public void setAlicuota(float alicuota) {
        this.alicuota = alicuota;
    }

}
