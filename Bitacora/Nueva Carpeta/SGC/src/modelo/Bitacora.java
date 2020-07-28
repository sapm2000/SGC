package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class Bitacora extends ConexionBD{
    
    private int id;
    private String operacion;
    private String tabla;
    private String valor_viejo;
    private String valor_nuevo;
    private Date fecha_hora;
    private Usuario usuario = new Usuario();
    
    private Connection con;
    
    public ArrayList<Bitacora>listar(){
        
        ArrayList<Bitacora> lista;
        Bitacora item;

        lista = new ArrayList();

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT * FROM v_bitacora;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            int ind = 1;

            while (rs.next()) {
                item = new Bitacora();

                item.setId(rs.getInt("id_bitacora"));
                item.setOperacion(rs.getString("operacion"));
                item.setTabla(rs.getString("tabla"));
                item.getUsuario().setUsuario(rs.getString("usuario"));
                item.setValor_viejo(rs.getString("valor_viejo"));
                item.setValor_nuevo(rs.getString("valor_nuevo"));
                item.setFecha_hora(rs.getDate("fecha"));

                lista.add(item);
            }

        } catch (SQLException e) {

            System.err.println(e);

        } finally {

            cerrar();

        }

        return lista;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getValor_viejo() {
        return valor_viejo;
    }

    public void setValor_viejo(String valor_viejo) {
        this.valor_viejo = valor_viejo;
    }

    public String getValor_nuevo() {
        return valor_nuevo;
    }

    public void setValor_nuevo(String valor_nuevo) {
        this.valor_nuevo = valor_nuevo;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
        
}
