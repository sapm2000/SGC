package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import sgc.SGC;

public class Unidades extends ConexionBD {

    private int id;
    private String N_unidad;
    private String documento;
    private String direccion;
    private double area;
    private Condominio condominio;
    private ArrayList<Propietarios> propietario;
    private java.sql.Date fecha_desde;
    private java.sql.Date fecha_hasta;

    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con = getConexion();

    private int estatus;
    private int id_puente;

    public Unidades() {
        condominio = new Condominio();
        propietario = new ArrayList();

    }

    public boolean buscId(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
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

//    public boolean registrar_propietario_unidad(Unidades moduni) {
//        PreparedStatement ps = null;
//        Connection con = getConexion();
//
//        String sql = "INSERT INTO puente_unidad_propietarios(id_propietario, id_unidad, fecha_desde, documento, estado, activo) VALUES (?, ?, ?, ?, ?, 1);";
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, getCedula());
//            ps.setInt(2, getId());
//            ps.setDate(3, getFecha_desde());
//
//            ps.setString(4, getDocumento());
//            ps.setInt(5, getEstatus());
//
//            ps.execute();
//
//            return true;
//
//        } catch (SQLException e) {
//
//            System.err.println(e);
//            return false;
//
//        } finally {
//            try {
//
//                con.close();
//
//            } catch (SQLException e) {
//
//                System.err.println(e);
//
//            }
//
//        }
//
//    }

    /*public ArrayList<Unidades> buscarPropietario() {
        ArrayList listaPropietario = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "	SELECT cedula, nombre, telefono, correo	FROM propietarios where activo=1;";
        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                Unidades = new Unidades();

                //prs = new Persona();
                Unidades.setCedula(rs.getString("cedula"));
                Unidades.setNombre(rs.getString("nombre"));
                Unidades.setTelefono(rs.getString("telefono"));
                Unidades.setCorreo(rs.getString("correo"));

                listaPropietario.add(Unidades);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaPropietario;

    }*/
//    public ArrayList<Unidades> buscarPropietariomod() {
//        ArrayList listaPropietario = new ArrayList();
//        Unidades Unidades = new Unidades();
//
//        Connection con = getConexion();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String sql = "SELECT id, cedula, nombre, telefono, correo, id_unidad, fecha_desde, fecha_hasta, estado, documento FROM propietarios left join puente_unidad_propietarios on puente_unidad_propietarios.id_propietario=propietarios.cedula  and  id_unidad=? and not estado=0 where propietarios.activo=1";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, getId());
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Unidades = new Unidades();
//
//                //prs = new Persona();
//                Unidades.setId_puente(rs.getInt("id"));
//                Unidades.propietario.get(id).setCedula(rs.getString("cedula"));
//                Unidades.setNombre(rs.getString("nombre"));
//                Unidades.setTelefono(rs.getString("telefono"));
//                Unidades.setCorreo(rs.getString("correo"));
//                Unidades.setId(rs.getInt("id_unidad"));
//                Unidades.setFecha_desde(rs.getDate("fecha_desde"));
//                Unidades.setFecha_hasta(rs.getDate("fecha_hasta"));
//                Unidades.setEstatus(rs.getInt("estado"));
//                Unidades.setDocumento(rs.getString("documento"));
//
//                listaPropietario.add(Unidades);
//            }
//
//        } catch (Exception e) {
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//
//        return listaPropietario;
//
//    }
<<<<<<< HEAD
    public Boolean reactivar() throws SQLException {
        ps = null;
        con = getConexion();

        if (vaciarPropietarios()) {
            int ind;

            String sql = "UPDATE unidad SET activo = true WHERE n_unidad = ? AND id_condominio = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getN_unidad());
            ps.setString(ind++, SGC.condominioActual.getRif());
            
            ps.execute();

            return true;

        } else {
            return false;

        }
    }
=======
>>>>>>> parent of 2b78246... listp

    public boolean registrar() {
        ps = null;
        Connection con = getConexion();

        //Registro de los datos de la tabla unidad
        String sql = "INSERT INTO unidad(n_unidad, n_documento, direccion, area, id_condominio) VALUES (?,?,?,?,?);";

        try {
            ps = con.prepareStatement(sql);

            int i = 1;

            ps.setString(i++, getN_unidad());
            ps.setString(i++, getDocumento());
            ps.setString(i++, getDireccion());
            ps.setDouble(i++, getArea());
            ps.setString(i++, SGC.condominioActual.getRif());

            ps.execute();

            //Se selecciona el id de la unidad que se acaba de registrar
            sql = "SELECT id FROM v_unidad WHERE n_unidad = ? AND id_condominio = ?";

            ps = con.prepareStatement(sql);

            i = 1;

            ps.setString(i++, getN_unidad());
            ps.setString(i++, SGC.condominioActual.getRif());

            rs = ps.executeQuery();

            if (rs.next()) {
                setId(rs.getInt(1));

            }

            //Registro de los datos del puente
            sql = "INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, estado) VALUES (?,?,?);";

            ps = con.prepareStatement(sql);

            for (int j = 0; j < getPropietario().size(); j++) {
                i = 1;
                ps.setString(i++, getPropietario().get(j).getCedula());
                ps.setInt(i++, getId());
                ps.setInt(i++, getEstatus());

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
        Connection con = getConexion();

        int ind;

        String sql = "INSERT INTO puente_unidad_propietarios(ci_propietario, id_unidad, estado) VALUES (?,?,1);";

        ps = con.prepareStatement(sql);

        for (int j = 0; j < getPropietario().size(); j++) {
            ind = 1;
            ps.setString(ind++, cedula);
            ps.setInt(ind++, getId());

            ps.execute();

        }

        return true;

    }

    public ArrayList<Unidades> listar() {
        ArrayList listaUnidades = new ArrayList();
        Unidades unidad = new Unidades();

        Connection con = getConexion();
        ps = null;
        rs = null;
        ResultSet rs2 = null;

        String sql = "SELECT * FROM v_unidad WHERE id_condominio=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());

            rs = ps.executeQuery();

            sql = "SELECT * FROM v_unidad_propietario WHERE id_condominio = ? AND id = ?";
            ps = con.prepareStatement(sql);

            while (rs.next()) {
                unidad = new Unidades();

                unidad.setId(rs.getInt("id"));
                unidad.setN_unidad(rs.getString("n_unidad"));
                unidad.setDocumento(rs.getString("n_documento"));
                unidad.setDireccion(rs.getString("direccion"));
                unidad.setArea(rs.getInt("area"));

                ps.setString(1, SGC.condominioActual.getRif());
                ps.setInt(2, unidad.getId());

                rs2 = ps.executeQuery();

                while (rs2.next()) {
                    unidad.getPropietario().add(new Propietarios(rs2.getString("cedula"), rs2.getString("p_nombre"), rs2.getString("s_nombre"), rs2.getString("p_apellido"), rs2.getString("s_apellido"), rs2.getString("telefono"), rs2.getString("correo")));

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

    public ArrayList<Unidades> buscar() {
        ArrayList listaUnidades = new ArrayList();
        Unidades Unidades = new Unidades();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select id from unidades where id_condominio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());

            rs = ps.executeQuery();

            while (rs.next()) {
                Unidades = new Unidades();

                Unidades.setId(rs.getInt("id"));

                listaUnidades.add(Unidades);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listaUnidades;

    }

    public boolean buscarUnidad() {
        ps = null;
        rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, n_unidad, direccion, area FROM unidades where n_unidad=? AND id_condominio=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getN_unidad());
            ps.setString(2, SGC.condominioActual.getRif());

            rs = ps.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("id"));
                setDireccion(rs.getString("direccion"));
                setArea(rs.getInt("area"));
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

    public boolean buscarsancion(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
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

    public boolean buscarepe(Unidades moduni) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT n_unidad, direccion, area FROM unidad where n_unidad=? and id_condominio=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, moduni.getN_unidad());
            ps.setString(2, SGC.condominioActual.getRif());
            rs = ps.executeQuery();
            if (rs.next()) {
                moduni.setN_unidad(rs.getString("n_unidad"));
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

    public boolean modificar() {

        ps = null;
        con = getConexion();

        int ind;

        String sql = "UPDATE n_documento = ?, unidad SET direccion = ?, area = ? WHERE id = ? AND id_condominio = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getDocumento());
            ps.setString(ind++, getDireccion());
            ps.setDouble(ind++, getArea());
            ps.setInt(ind++, getId());
            ps.setString(ind++, SGC.condominioActual.getRif());

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
        con = getConexion();

        int numNuevos;
        int numViejos;

        String sql = "SELECT cedula FROM v_dueno_unidad WHERE id_unidad = ?;";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, getId());

            rs = ps.executeQuery();

            ArrayList<Propietarios> propietariosViejos;
            propietariosViejos = new ArrayList();

            while (rs.next()) {
<<<<<<< HEAD
                propietariosViejos.add(new Propietarios(rs.getString("cedula")));

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
=======
                prop.add(new Propietarios(rs.getString("cedula")));

            }

            boolean encontrado = false;

            System.out.println(prop.size());
            System.out.println(getPropietario().size());
            for (int k = 0; k < getPropietario().size(); k++) {
                for (int j = 0; j < prop.size(); j++) {
                    if (prop.get(j).getCedula().equals(getPropietario().get(k).getCedula())) {
                        encontrado = true;
>>>>>>> parent of 2b78246... listp
                        break;

                    }
                }
<<<<<<< HEAD

                // Si el propietario nuevo no está en la lista de viejos
                if (getPropietario().size() >= 0) {
                    // Se agrega como nuevo propietario y se elimina del arreglo de nuevos
                    agregarPropietario(getPropietario().get(j).getCedula());
                    getPropietario().remove(j);
                    numNuevos--;
                    j--;
=======

                if (encontrado == false) {
                    sql = "UPDATE puente_unidad_propietarios SET fecha_hasta = LOCALTIMESTAMP(0), activo = 0 WHERE id = ? AND cedula = ?";

                    ps = con.prepareStatement(sql);

                    ps.setInt(1, getId());
                    ps.setString(2, propietario.get(k).getCedula());

                    ps.execute();
>>>>>>> parent of 2b78246... listp

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

//    public boolean actualizardocumento(Unidades moduni) {
//
//        PreparedStatement ps = null;
//        Connection con = getConexion();
//
//        String sql = "UPDATE puente_unidad_propietarios SET documento=?, activo=1 WHERE id=?;";
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, getDocumento());
//            ps.setInt(2, getId_puente());
//
//            ps.execute();
//
//            return true;
//
//        } catch (SQLException e) {
//
//            System.err.println(e);
//            return false;
//        } finally {
//            try {
//
//                con.close();
//
//            } catch (SQLException e) {
//
//                System.err.println(e);
//
//            }
//
//        }
//
//    }
<<<<<<< HEAD
    private Boolean retirarPropietarios(ArrayList<Propietarios> propietarios) throws SQLException {
        ps = null;
        con = getConexion();

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
        con = getConexion();

        int ind;

        String sql = "SELECT id FROM v_unidad WHERE n_unidad = ? AND id_condominio = ?";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setString(ind++, getN_unidad());
        ps.setString(ind++, SGC.condominioActual.getRif());

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

    /*public boolean retirarpropietario(Unidades moduni) {
=======

    public boolean retirarpropietario(Unidades moduni) {
>>>>>>> parent of 2b78246... listp

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE puente_unidad_propietarios SET fecha_hasta=?, estado=?, activo=0 WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDate(1, getFecha_hasta());
            ps.setInt(2, getEstatus());
            ps.setInt(3, getId_puente());

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

    }*/
    public boolean eliminarPuenteUnidad(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

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

        String sql = "UPDATE unidad SET activo = false WHERE id = ? AND id_condominio = ?";

        try {
            ind = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(ind++, getId());
            ps.setString(ind++, SGC.condominioActual.getRif());
            ps.execute();

            sql = "UPDATE puente_unidad_propietarios SET fecha_hasta = LOCALTIMESTAMP(0) WHERE fecha_hasta = null AND id_unidad = ? AND id_condominio = ?;";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setInt(ind++, getId());
            ps.setString(ind++, SGC.condominioActual.getRif());

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

    public Boolean existeInactivo() {
        Connection con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT n_unidad FROM v_unidades_inactivas WHERE id_condominio = ? AND n_unidad = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, SGC.condominioActual.getRif());
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

        }
    }

    public boolean activarUnidad(Unidades moduni) {

        PreparedStatement ps = null;
        Connection con = getConexion();

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

    public void llenar_unidades(JComboBox Unidades) {

//Creamos objeto tipo Connection    
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

//Creamos la Consulta SQL
        String sql = "SELECT n_unidad FROM unidades where id_condominio=?;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            //Ejecutamos la consulta
            rs = ps.executeQuery();

            //LLenamos nuestro ComboBox
            Unidades.addItem("Seleccione el numero de la unidad");

            while (rs.next()) {

                Unidades.addItem(rs.getString("n_unidad"));

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

    }

    public ArrayList<Unidades> listarCbxUnidad() {
        ArrayList listaUnidad = new ArrayList();
        Unidades uni;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT n_unidad FROM unidad "
                + "WHERE unidad.id_condominio = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {
                uni = new Unidades();
                uni.setN_unidad(rs.getString("n_unidad"));
//                uni.setNombre(rs.getString("nombre"));
//                uni.setApellido(rs.getString("apellido"));

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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
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

}
