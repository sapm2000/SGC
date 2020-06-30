package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgc.SGC;

public class Unidades extends ConexionBD {

    private int id;
    private String N_unidad;
    private String documento;
    private String direccion;
    private float alicuota;
    private Condominio condominio;
    private ArrayList<Propietarios> propietario;
    private java.sql.Date fecha_desde;
    private java.sql.Date fecha_hasta;
    private TipoUnidad tipo_Unidad = new TipoUnidad();

    private Connection con;

    private int estatus;
    private int id_puente;

    public Unidades() {
        condominio = new Condominio();
        propietario = new ArrayList();

    }

    public boolean buscId(Unidades moduni) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT MAX(id) as id from unidades";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                moduni.setId(rs.getInt("id"));

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

    public Boolean reactivar() throws SQLException {
        try {
            ps = null;
            con = getConexion();

            if (vaciarPropietarios()) {
                int ind;

                String sql = "UPDATE unidad SET activo = true, n_documento = ? WHERE n_unidad = ?";

                ps = con.prepareStatement(sql);

                ind = 1;
                ps.setString(ind++, getDocumento());
                ps.setString(ind++, getN_unidad());

                ps.execute();

                //Se selecciona el id de la unidad que se acaba de registrar
                sql = "SELECT id FROM v_unidad WHERE n_unidad = ?";

                ps = con.prepareStatement(sql);

                ind = 1;

                ps.setString(ind++, getN_unidad());

                rs = ps.executeQuery();

                if (rs.next()) {
                    setId(rs.getInt(1));

                }

                //Registro de los datos del puente
                sql = "INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, estado) VALUES (?,?,?);";

                ps = con.prepareStatement(sql);

                for (int i = 0; i < getPropietario().size(); i++) {
                    ind = 1;
                    ps.setString(ind++, getPropietario().get(i).getCedula());
                    ps.setInt(ind++, getId());
                    ps.setInt(ind++, getEstatus());

                    ps.execute();

                }

                return true;

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

    public boolean registrar() {
        ps = null;
        con = getConexion();

        int ind;

        //Registro de los datos de la tabla unidad
        String sql = "SELECT agregar_unidad(?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;

            ps.setString(ind++, getN_unidad());
            ps.setString(ind++, getDocumento());
            ps.setString(ind++, getDireccion());
            ps.setInt(ind++, getTipo_Unidad().getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            ps.execute();

            //Se selecciona el id de la unidad que se acaba de registrar
            sql = "SELECT id FROM v_unidad WHERE n_unidad = ?";

            ps = con.prepareStatement(sql);

            ind = 1;

            ps.setString(ind++, getN_unidad());

            rs = ps.executeQuery();

            if (rs.next()) {
                setId(rs.getInt(1));

            }

            //Registro de los datos del puente
            sql = "INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, estado) VALUES (?,?,?);";

            ps = con.prepareStatement(sql);

            for (int j = 0; j < getPropietario().size(); j++) {
                ind = 1;
                ps.setString(ind++, getPropietario().get(j).getCedula());
                ps.setInt(ind++, getId());
                ps.setInt(ind++, getEstatus());

                ps.execute();

            }

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

    private Boolean agregarPropietario(String cedula) throws SQLException {
        ps = null;

        int ind;

        String sql = "INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, estado) VALUES (?,?,1);";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setString(ind++, cedula);
        ps.setInt(ind++, getId());

        ps.execute();

        return true;

    }

    public ArrayList<Unidades> listar() {
        ArrayList listaUnidades = new ArrayList();
        Unidades unidad = new Unidades();

        con = getConexion();
        ps = null;
        rs = null;
        ResultSet rs2 = null;

        String sql = "SELECT * FROM v_unidad";

        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            sql = "SELECT * FROM v_unidad_propietario WHERE id = ?";
            ps = con.prepareStatement(sql);

            while (rs.next()) {
                unidad = new Unidades();

                unidad.setId(rs.getInt("id"));
                unidad.setN_unidad(rs.getString("n_unidad"));
                unidad.setDocumento(rs.getString("n_documento"));
                unidad.setDireccion(rs.getString("direccion"));
                unidad.setAlicuota(rs.getFloat("alicuota"));
                unidad.getTipo_Unidad().setId(rs.getInt("id_tipo"));
                unidad.getTipo_Unidad().setNombre(rs.getString("tipo"));
                unidad.getTipo_Unidad().setArea(rs.getFloat("area"));

                ps.setInt(1, unidad.getId());

                rs2 = ps.executeQuery();

                while (rs2.next()) {
                    unidad.getPropietario().add(new Propietarios(rs2.getString("ci_persona"), rs2.getString("p_nombre"), rs2.getString("s_nombre"), rs2.getString("p_apellido"), rs2.getString("s_apellido"), rs2.getString("telefono"), rs2.getString("correo")));
                }

                listaUnidades.add(unidad);
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

        return listaUnidades;
    }

    public boolean buscarsancion(Unidades moduni) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM unidades inner join puente_sancion_unidad on puente_sancion_unidad.id_unidad=unidades.id inner join sancion on puente_sancion_unidad.id_sancion=sancion.id where unidades.id=? and estado='Pendiente'";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, moduni.getId());

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

    public boolean actualizarAlicuota(Unidades modelo) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE unidad SET alicuota=? WHERE id=?;";

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
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean modificar() {
        try {
            ps = null;
            con = getConexion();

            int ind;

            String sql = "SELECT modificar_unidad(?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getDocumento());
            ps.setString(ind++, getDireccion());
            ps.setInt(ind++, getTipo_Unidad().getId());
            ps.setInt(ind++, getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            ps.execute();

            if (modificarPropietarios()) {
                return true;

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

            numNuevos = getPropietario().size();
            numViejos = propietariosViejos.size();

            // Ciclo que recorre los propietarios nuevos
            for (int j = 0; j < numNuevos; j++) {
                // Ciclo que recorre los propietarios viejos
                for (int i = 0; i < numViejos; i++) {
                    // Si el propietario de la lista de nuevos coincide con el de la BD
                    if (getPropietario().get(j).getCedula().equals(propietariosViejos.get(i).getCedula())) {
                        // No se hace nada con él y se elimina de ambos arreglos para dejar de compararlos
                        getPropietario().remove(j);
                        numNuevos--;
                        propietariosViejos.remove(i);
                        numViejos--;
                        break;

                    }
                }

                // Si el propietario nuevo no está en la lista de viejos
                if (getPropietario().size() > 0) {
                    // Se agrega como nuevo propietario y se elimina del arreglo de nuevos
                    agregarPropietario(getPropietario().get(j).getCedula());
                    getPropietario().remove(j);
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
        ps.setString(ind++, getN_unidad());

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

    public boolean eliminarPuenteUnidad(Unidades moduni) {

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
            ps.execute();

            sql = "UPDATE puente_unidad_propietarios SET fecha_hasta = LOCALTIMESTAMP(0), estado = 0 WHERE fecha_hasta IS null AND id_unidad = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
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

    public Boolean existe() {
        try {
            con = getConexion();
            ps = null;
            rs = null;

            int ind;

            String sql = "SELECT n_unidad FROM v_unidad WHERE n_unidad = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_unidad());

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Unidades.class.getName()).log(Level.SEVERE, null, ex);
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

        String sql = "SELECT n_unidad FROM v_unidades_inactivas WHERE n_unidad = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_unidad());

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            } else {
                return false;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Unidades.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean activarUnidad(Unidades moduni) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE unidades SET activo=1 WHERE id=?";

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

    public ArrayList<Unidades> listarArea() {
        ArrayList listaUnidad = new ArrayList();
        Unidades uni;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT area, unidad.id FROM unidad inner join tipo_unidad ON tipo_unidad.id = unidad.id_tipo where unidad.activo=true";

        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                uni = new Unidades();
                uni.tipo_Unidad.setArea(rs.getFloat(1));
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

    public String getN_unidad() {
        return N_unidad;
    }

    public void setN_unidad(String N_unidad) {
        this.N_unidad = N_unidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoUnidad getTipo_Unidad() {
        return tipo_Unidad;
    }

    public void setTipo_Unidad(TipoUnidad tipo_Unidad) {
        this.tipo_Unidad = tipo_Unidad;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public ArrayList<Propietarios> getPropietario() {
        return propietario;
    }

    public void setPropietario(ArrayList<Propietarios> propietario) {
        this.propietario = propietario;
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
