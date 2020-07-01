package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgc.SGC;

public class Propietarios extends Persona {

    private int cantidad;
    private java.sql.Date fecha_hasta;

    private Connection con;

    public Propietarios() {
    }

    public Propietarios(String cedula) {
        super(cedula);
    }

    public Propietarios(String cedula, String pNombre, String pApellido) {
        super(cedula, pNombre, pApellido);
    }

    public Propietarios(String cedula, String pNombre, String sNombre, String pApellido, String sApellido, String correo, String telefono) {
        super(cedula, pNombre, sNombre, pApellido, sApellido, correo, telefono);
    }

    public Boolean existe() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT ci_persona FROM v_propietario WHERE ci_persona = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getCedula());

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

        String sql = "SELECT ci_persona FROM v_propietario_inactivo WHERE ci_persona = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getCedula());

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

    public Boolean reactivar() {
        try {
            ps = null;
            con = getConexion();

            int ind;

            String sql = "UPDATE propietario SET activo = true WHERE ci_persona = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getCedula());

            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Propietarios.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);

            }
        }
    }

    public boolean registrar(Boolean existe) {
        try {
            con = getConexion();
            ps = null;

            String sql = "SELECT agregar_propietario(?,?,?,?,?,?,?,?,?);";

            int i = 1;

            ps = con.prepareStatement(sql);

            ps.setString(i++, getCedula());
            ps.setString(i++, getpNombre());
            ps.setString(i++, getsNombre());
            ps.setString(i++, getpApellido());
            ps.setString(i++, getsApellido());
            ps.setString(i++, getTelefono());
            ps.setString(i++, getCorreo());
            ps.setBoolean(i++, existe);
            ps.setInt(i++, SGC.usuarioActual.getId());

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

//    public boolean registrar(Boolean existe) {
//        try {
//            con = getConexion();
//            ps = null;
//
//            if (!existe) {
//
//                if (!registrarPersona()) {
//                    return false;
//                }
//            }
//
//            String sql = "INSERT INTO propietario (ci_persona) VALUES (?);";
//
//            int i = 1;
//
//            ps = con.prepareStatement(sql);
//
//            ps.setString(i++, getCedula());
//
//            ps.execute();
//
//            return true;
//
//        } catch (SQLException e) {
//            System.err.println(e);
//            return false;
//
//        } finally {
//            
//            try {
//                con.close();
//
//            } catch (SQLException e) {
//                System.err.println(e);
//            }
//        }
//    }
    public boolean eliminarpuenteuni(Propietarios modpro) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE puente_unidad_propietarios SET fecha_hasta=?, estado=0 WHERE id_propietario=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDate(1, getFecha_hasta());
            ps.setString(2, getCedula());

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

    public ArrayList<Propietarios> listar() {
        ArrayList listaPropietarios = new ArrayList();
        Propietarios pro;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT * FROM v_propietario";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int i;
            while (rs.next()) {
                pro = new Propietarios();
                i = 1;

                pro.setCedula(rs.getString(i++));
                pro.setpNombre(rs.getString(i++));
                pro.setsNombre(rs.getString(i++));
                pro.setpApellido(rs.getString(i++));
                pro.setsApellido(rs.getString(i++));
                pro.setTelefono(rs.getString(i++));
                pro.setCorreo(rs.getString(i++));

                listaPropietarios.add(pro);

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

        return listaPropietarios;
    }

    public ArrayList<Propietarios> listarxcon() {
        ArrayList listaPropietarios = new ArrayList();
        Propietarios modpro;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT cedula, nombre, apellido, telefono, correo FROM propietarios inner join puente_unidad_propietarios on propietarios.cedula=puente_unidad_propietarios.id_propietario inner join unidades on puente_unidad_propietarios.id_unidad=unidades.id where id_condominio=? and propietarios.activo=1 group by cedula";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());

            rs = ps.executeQuery();

            while (rs.next()) {

                modpro = new Propietarios();

                modpro.setCedula(rs.getString(1));
                modpro.setpNombre(rs.getString(2));
                modpro.setpApellido(rs.getString(3));
                modpro.setTelefono(rs.getString(4));
                modpro.setCorreo(rs.getString(5));

                listaPropietarios.add(modpro);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaPropietarios;
    }

    public boolean buscarunidadesasociadas(Propietarios modpro) {

        ps = null;
        rs = null;
        con = getConexion();
        String sql = "SELECT * FROM puente_unidad_propietarios where id_propietario=? and activo=1;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modpro.getCedula());
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

    public boolean eliminar() {
        ps = null;
        con = getConexion();

        String sql = "UPDATE propietario SET activo = false WHERE ci_persona = ?";

        try {
            int i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getCedula());

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

    public boolean activar(Propietarios modpro) {

        ps = null;
        con = getConexion();

        String sql = "UPDATE propietarios SET activo=1 WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getCedula());
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

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
