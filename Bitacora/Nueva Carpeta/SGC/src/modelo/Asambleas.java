/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import sgc.SGC;

/**
 *
 * @author rma
 */
public class Asambleas extends ConexionBD {

    private int id;
    private String nombre_asamblea;
    private java.sql.Date fecha;
    private String descripcion;
    private Propietarios modpro = new Propietarios();
   
    private int n_asistentes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_asamblea() {
        return nombre_asamblea;
    }

    public void setNombre_asamblea(String nombre_asamblea) {
        this.nombre_asamblea = nombre_asamblea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

   

    public int getN_asistentes() {
        return n_asistentes;
    }

    public void setN_asistentes(int n_asistentes) {
        this.n_asistentes = n_asistentes;
    }

    public boolean registrarAsambleas(Asambleas modasa) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO asambleas(nombre, fecha, descripcion, id_condominio) VALUES (?, ?, ?, ?);";

        try {
            int i;
            i=1;
            
            ps = con.prepareStatement(sql);
            ps.setString(i++, modasa.getNombre_asamblea());
            ps.setDate(i++, modasa.getFecha());
            ps.setString(i++, modasa.getDescripcion());
            ps.setString(i++, SGC.condominioActual.getRif());

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

    public boolean buscId(Asambleas modasa) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from asambleas";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modasa.setId(rs.getInt("id"));

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

    public boolean registrar_asamblea_propietario(Asambleas modasa) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_asamblea_propietario(id_asamblea, id_propietario) VALUES (?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setString(2, modasa.modpro.getCedula());

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

    public ArrayList<Asambleas> listarAsambleas() {
        ArrayList listaasambleas = new ArrayList();
        Asambleas modasa;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT asambleas.id, nombre, fecha, descripcion, count(id_asamblea) as nº FROM asambleas inner join puente_asamblea_propietario on puente_asamblea_propietario.id_asamblea=asambleas.id where id_condominio=? group by puente_asamblea_propietario.id_asamblea, asambleas.id";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {

                modasa = new Asambleas();

                modasa.setId(rs.getInt(1));
                modasa.setNombre_asamblea(rs.getString(2));
                modasa.setFecha(rs.getDate(3));
                modasa.setDescripcion(rs.getString(4));
                modasa.setN_asistentes(rs.getInt(5));

                listaasambleas.add(modasa);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaasambleas;
    }

    public boolean buscarAsambleas(Asambleas modasa) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT nombre, fecha, descripcion FROM asambleas where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modasa.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modasa.setNombre_asamblea(rs.getString("nombre"));
                modasa.setFecha(rs.getDate("fecha"));
                modasa.setDescripcion(rs.getString("descripcion"));

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

    public ArrayList<Asambleas> listarpropietariosmod() {
        ArrayList listapropmod = new ArrayList();
        Asambleas modasa = new Asambleas();

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select propietarios.cedula, propietarios.nombre, propietarios.apellido, propietarios.telefono, propietarios.correo, puente_asamblea_propietario.id_asamblea from propietarios left join puente_asamblea_propietario on puente_asamblea_propietario.id_propietario=propietarios.cedula and puente_asamblea_propietario.id_asamblea=? inner join puente_unidad_propietarios on puente_unidad_propietarios.id_propietario=propietarios.cedula inner join unidades on puente_unidad_propietarios.id_unidad=unidades.id where id_condominio=? group by cedula, puente_asamblea_propietario.id_asamblea";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setString(2, SGC.condominioActual.getRif());    
            rs = ps.executeQuery();

            while (rs.next()) {
                modasa = new Asambleas();

                //prs = new Persona();
                modasa.modpro.setCedula(rs.getString("cedula"));
                modasa.modpro.setpNombre(rs.getString("nombre"));
                modasa.modpro.setpApellido(rs.getString("apellido"));
                modasa.modpro.setTelefono(rs.getString("telefono"));
                modasa.modpro.setCorreo(rs.getString("correo"));
                modasa.setId(rs.getInt("id_asamblea"));

                listapropmod.add(modasa);
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listapropmod;

    }

    public boolean modificarAsamblea(Asambleas modasa) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE asambleas SET nombre=?, fecha=?, descripcion=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getNombre_asamblea());
            ps.setDate(2, getFecha());
            ps.setString(3, getDescripcion());
            ps.setInt(4, getId());

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

    public boolean borrarpuenteasamblea(Asambleas modasa) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM puente_asamblea_propietario WHERE id_asamblea=?";

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

    public void llenar_Asamblea(JComboBox Asamblea) {

//Creamos objeto tipo Connection    
        java.sql.Connection conectar = null;
        PreparedStatement pst = null;
        ResultSet result = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT nombre FROM asambleas WHERE id_condominio=?;";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            conectar = getConexion();
            //Preparamos la consulta SQL
            pst = conectar.prepareStatement(SSQL);
            pst.setString(1, SGC.condominioActual.getRif());
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            Asamblea.addItem("Seleccione la Asamblea");

            while (result.next()) {

                Asamblea.addItem(result.getString("nombre"));

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        } finally {

            if (conectar != null) {

                try {

                    conectar.close();
                    result.close();

                    conectar = null;
                    result = null;

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex);

                }

            }

        }

    }

    public boolean buscarid(Asambleas modasa) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT id FROM asambleas where nombre=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, modasa.modpro.getpApellido());
            rs = ps.executeQuery();
            if (rs.next()) {

                modasa.setId(rs.getInt("id"));

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

}
