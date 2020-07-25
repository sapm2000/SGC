package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgc.SGC;

public class TipoUsuario extends ConexionBD {

    private Integer id;
    private String nombre;
    private ArrayList<Funcion> funciones;

    private Connection con;

    public TipoUsuario() {

        funciones = new ArrayList();

    }

    public TipoUsuario(Integer id, String nombre, ArrayList<Funcion> funciones) {

        this.id = id;
        this.nombre = nombre;
        this.funciones = funciones;

    }

    private Boolean agregarFuncion(Funcion funcion) throws SQLException {

        ps = null;

        int ind;

        String sql = "INSERT INTO puente_tipo_funcion (id_tipo, id_funcion, ver, registrar, modificar, eliminar) VALUES (?,?,?,?,?,?);";

        con = getConexion();
        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setInt(ind++, id);
        ps.setInt(ind++, funcion.getId());
        ps.setBoolean(ind++, funcion.getVer());
        ps.setBoolean(ind++, funcion.getRegistrar());
        ps.setBoolean(ind++, funcion.getModificar());
        ps.setBoolean(ind++, funcion.getEliminar());
        ps.execute();

        return true;
    }

    public boolean buscarId() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT id FROM tipo_usuario WHERE tipo = ?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre.toUpperCase());
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

            cerrar();

        }
    }

    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM tipo_usuario WHERE activo = true;";

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

            cerrar();

        }
    }

    public ArrayList<TipoUsuario> listar() {

        try {

            ArrayList<TipoUsuario> lista = new ArrayList();
            con = getConexion();
            ps = null;
            TipoUsuario item;

            String sql = "SELECT * FROM v_tipo_usuario;";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            ResultSet rs2;
            Funcion funcion;
            sql = "SELECT * FROM v_tipo_funcion WHERE id = ?;";
            ps = con.prepareStatement(sql);

            while (rs.next()) {

                item = new TipoUsuario();
                item.setId(rs.getInt("id"));
                item.setNombre(rs.getString("tipo"));

                ps.setInt(1, item.id);

                rs2 = ps.executeQuery();

                while (rs2.next()) {

                    funcion = new Funcion();
                    funcion.setId(rs2.getInt("id_funcion"));
                    funcion.setNombre(rs2.getString("funcion"));
                    funcion.setVer(rs2.getBoolean("ver"));
                    funcion.setRegistrar(rs2.getBoolean("registrar"));
                    funcion.setModificar(rs2.getBoolean("modificar"));
                    funcion.setEliminar(rs2.getBoolean("eliminar"));

                    item.funciones.add(funcion);

                }

                lista.add(item);

            }

            return lista;

        } catch (SQLException ex) {

            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;

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

            String sql = "SELECT modificar_tipo_usuario(?,?,?);";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getNombre());
            ps.setInt(ind++, getId());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            if (ps.execute()) {

                rs = ps.getResultSet();
                rs.next();
                resul = rs.getBoolean(1);

            } else {

                return false;

            }

            if (modificarFunciones()) {

                return resul;

            } else {

                return false;

            }

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    private Boolean modificarFunciones() {

        Funcion item;
        ArrayList<Funcion> funcionesViejas;
        int numNuevos;
        int numViejos;

        ps = null;

        String sql = "SELECT id_funcion, funcion, ver, registrar, modificar, eliminar FROM v_tipo_funcion WHERE id = ?;";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, getId());

            rs = ps.executeQuery();

            funcionesViejas = new ArrayList();

            while (rs.next()) {

                item = new Funcion();
                item.setId(rs.getInt("id_funcion"));
                item.setNombre(rs.getString("funcion"));
                item.setVer(rs.getBoolean("ver"));
                item.setRegistrar(rs.getBoolean("registrar"));
                item.setModificar(rs.getBoolean("modificar"));
                item.setEliminar(rs.getBoolean("eliminar"));
                funcionesViejas.add(item);

            }

            numNuevos = getFunciones().size();
            numViejos = funcionesViejas.size();
            
            boolean procesado;

            // Por cada función nueva
            for (int j = 0; j < numNuevos; j++) {
                
                procesado = false;

                // Por cada función vieja
                for (int i = 0; i < numViejos; i++) {

                    // Si la función de la lista de nuevos y sus permisos coincide con la de la BD
                    if (getFunciones().get(j).equals(funcionesViejas.get(i))) {

                        // No se hace nada con ella y se elimina de ambos arreglos para dejar de compararlos
                        getFunciones().remove(j);
                        numNuevos--;
                        funcionesViejas.remove(i);
                        numViejos--;
                        break;

                    // En cambio, si la función coincide, pero no sus permisos
                    } else if (getFunciones().get(j).getId() == funcionesViejas.get(i).getId()) {
                        
                        // Se modifica la función del tipo de usuario y se elimina de ambos arreglos para dejar de compararlos
                        modificarPermisosFuncion(getFunciones().get(j));
                        
                        getFunciones().remove(j);
                        numNuevos--;
                        funcionesViejas.remove(i);
                        numViejos--;
                        break;

                    }
                }

                // Si la función nueva no ha sido procesada
                if (!procesado) {

                    // Se agrega como nueva función y se elimina del arreglo de nuevas
                    agregarFuncion(getFunciones().get(j));
                    getFunciones().remove(j);
                    numNuevos--;
                    j--;

                } else {
                    
                    //Se reduce el índice que recorre las funciones nuevas
                    j--;
                }
            }

            // Se retiran las funciones viejas que quedaron en el arreglo de viejas (ya que fueron eliminadas)
            retirarFunciones(funcionesViejas);

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    private boolean modificarPermisosFuncion(Funcion funcion) throws SQLException {
        
        int ind;
        ps = null;

        String sql = "UPDATE puente_tipo_funcion SET ver = ?, registrar = ?, modificar = ?, eliminar = ? WHERE id_tipo = ? AND id_funcion = ?;";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setBoolean(ind++, funcion.getVer());
        ps.setBoolean(ind++, funcion.getRegistrar());
        ps.setBoolean(ind++, funcion.getModificar());
        ps.setBoolean(ind++, funcion.getEliminar());
        ps.setInt(ind++, id);
        ps.setInt(ind++, funcion.getId());

        return ps.executeUpdate() == 1;
    }

    public Boolean registrar() {

        try {

            con = getConexion();
            ps = null;

            int i;

            String sql = "INSERT INTO tipo_usuario(tipo) VALUES(?);";

            i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getNombre());
            ps.execute();

            if (buscarId()) {

                if (registrarFunciones()) {

                    return true;

                }
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    public boolean registrarFunciones() {

        try {

            con = getConexion();
            ps = null;

            int ind;

            String sql = "INSERT INTO puente_tipo_funcion(id_tipo, id_funcion, ver, registrar, modificar, eliminar) VALUES(?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);

            System.out.println("Se registraran " + getFunciones().size() + " funciones");

            for (int i = 0; i < getFunciones().size(); i++) {

                ind = 1;
                ps.setInt(ind++, getId());
                ps.setInt(ind++, getFunciones().get(i).getId());
                ps.setBoolean(ind++, getFunciones().get(i).getVer());
                ps.setBoolean(ind++, getFunciones().get(i).getRegistrar());
                ps.setBoolean(ind++, getFunciones().get(i).getModificar());
                ps.setBoolean(ind++, getFunciones().get(i).getEliminar());
                System.out.println("se registró la función " + getFunciones().get(i).getNombre());
                System.out.println("ver: " + getFunciones().get(i).getVer());
                System.out.println("registrar: " + getFunciones().get(i).getRegistrar());
                System.out.println("modificar: " + getFunciones().get(i).getModificar());
                System.out.println("eliminar: " + getFunciones().get(i).getEliminar());
                System.out.println("");

                if (ps.executeUpdate() != 1) {

                    return false;

                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {

            cerrar();

        }
    }

    private Boolean retirarFunciones(ArrayList<Funcion> funciones) throws SQLException {
        ps = null;

        int ind;

        String sql = "DELETE FROM puente_tipo_funcion WHERE id_tipo = ? AND id_funcion = ?";

        ps = con.prepareStatement(sql);

        for (int i = 0; i < funciones.size(); i++) {
            ind = 1;
            ps.setInt(ind++, getId());
            ps.setInt(ind++, funciones.get(i).getId());
            ps.execute();

        }

        return true;
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

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }

}
