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
import sgc.SGC;

/**
 *
 * @author rma
 */
public class Comunicados extends CrudUsuario {

    private int id;
    private String asunto;
    private String mensaje;
    private Usuario usu = new Usuario();
   
   
    private int leido;
    private int enviado;

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }

    public int getLeido() {
        return leido;
    }

    public void setLeido(int leido) {
        this.leido = leido;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public boolean registrarcomunicados(Comunicados modco) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO comunicados (asunto, mensaje, id_condominio) VALUES (?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getAsunto());
            ps.setString(2, getMensaje());
            ps.setString(3, SGC.condominioActual.getRif());
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

    public boolean buscId(Comunicados modco) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from comunicados";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modco.setId(rs.getInt("id"));

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

    public boolean registrar_comunicados_usuarios(Comunicados modcon) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_comunicado_usuario(id_usuario, id_comunicado,leido) VALUES (?, ?,?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, usu.getUsuario());
            ps.setInt(2, getId());
            ps.setInt(3, getLeido());

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

    public ArrayList<Comunicados> listarComunicado() {
        ArrayList listacomunicados = new ArrayList();
        Comunicados modco;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id_comunicado, comunicados.asunto, count(id_usuario) as enviado, SUM(leido) as leido FROM puente_comunicado_usuario inner join comunicados on comunicados.id = puente_comunicado_usuario.id_comunicado where comunicados.id_condominio=? group by id_comunicado, comunicados.asunto;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, SGC.condominioActual.getRif());
            rs = ps.executeQuery();

            while (rs.next()) {

                modco = new Comunicados();

                modco.setId(rs.getInt(1));
                modco.setAsunto(rs.getString(2));
                modco.setEnviado(rs.getInt(3));
                modco.setLeido(rs.getInt(4));

                listacomunicados.add(modco);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listacomunicados;
    }

    public boolean buscarComunicado(Comunicados modco) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT asunto, mensaje FROM comunicados where id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modco.getId());
            rs = ps.executeQuery();
            if (rs.next()) {

                modco.setAsunto(rs.getString("asunto"));
                modco.setMensaje(rs.getString("mensaje"));

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

    public ArrayList<Comunicados> listarmod() {
        ArrayList listausuariosmod = new ArrayList();
        Comunicados modco;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select usuario.cedula, usuario.usuario, usuario.nombre, usuario.apellido, usuario.ntelefono, id_comunicado, tipo from usuario left join puente_comunicado_usuario on puente_comunicado_usuario.id_usuario=usuario.cedula and puente_comunicado_usuario.id_comunicado=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                modco = new Comunicados();

                //prs = new Persona();
                modco.setCedula(rs.getString(1));

                modco.setUsuario(rs.getString(2));
                modco.setNombre(rs.getString(3));
                modco.setApellido(rs.getString(4));
                modco.setNtelefono(rs.getString(5));
                modco.setId(rs.getInt(6));
                modco.setTipo(rs.getString(7));

                listausuariosmod.add(modco);
            }

        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listausuariosmod;

    }

}
