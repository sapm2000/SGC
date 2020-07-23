package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario extends ConexionBD {

    private Integer id;
    private String usuario;
    private String password;
    private String pregunta;
    private String respuesta;
    private TipoUsuario tipoU;
    private Persona persona;

    private Connection con;

    public Usuario() {
        tipoU = new TipoUsuario();
        persona = new Persona();

    }

    private Boolean consultarPermisos() {
        try {
            con = getConexion();
            ps = null;
            ArrayList<Funcion> funciones = new ArrayList();

            String sql = "SELECT * FROM v_permisos WHERE usuario = ?;";

            ps = con.prepareStatement(sql);
            ps.setString(1, getUsuario());
            rs = ps.executeQuery();

            rs.next();
            setTipoU(new TipoUsuario());
            getTipoU().setId(rs.getInt("id_tipo"));
            getTipoU().setNombre(rs.getString("tipo"));

            do {
                funciones.add(new Funcion(rs.getInt("id_funcion"), rs.getString("funcion"), rs.getBoolean("registrar"), rs.getBoolean("modificar"), rs.getBoolean("eliminar"), rs.getBoolean("todo")));
            } while (rs.next());

            getTipoU().setFunciones(funciones);

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    

    public Boolean existe() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT usuario FROM v_usuario WHERE usuario = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getUsuario());

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

        String sql = "SELECT ci_persona FROM v_usuario_inactivo WHERE ci_persona = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getPersona().getCedula());

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

    public ArrayList<Usuario> listar() {
        try {
            ArrayList<Usuario> listarUsu = new ArrayList();
            con = getConexion();
            ps = null;
            Usuario usu;

            String sql = "SELECT * FROM v_usuario;";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                usu = new Usuario();
                usu.setId(rs.getInt("id"));
                usu.setUsuario(rs.getString("usuario"));
                usu.setPersona(new Persona());
                usu.persona.setCedula(rs.getString("cedula"));
                usu.persona.setpNombre(rs.getString("nombre"));
                usu.persona.setpApellido(rs.getString("apellido"));
                listarUsu.add(usu);

            }

            return listarUsu;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean login() {

        boolean result;

        try {
            con = getConexion();
            ps = null;
            rs = null;

            String sql = "SELECT login(?,?)";

            int i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getUsuario());
            ps.setString(i++, getPassword());

            rs = ps.executeQuery();

            result = false;

            if (rs.next()) {
                result = rs.getBoolean(1);
                setPassword(null);
                consultarPermisos();
            }

            return result;

        } catch (SQLException e) {
            System.err.println(e);
            return false;

        } finally {
            
            try {
                con.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean reactivar() {
        try {
            ps = null;
            con = getConexion();

            int ind;

            String sql = "UPDATE usuario SET activo = true, password = ?, pregunta = ?, respuesta = ?, id_tipo_usuario = ? WHERE ci_persona = ?";

            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getPassword());
            ps.setString(ind++, getPregunta());
            ps.setString(ind++, getRespuesta());
            ps.setInt(ind++, getTipoU().getId());
            ps.setString(ind++, getPersona().getCedula());

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

    public boolean registrar() {

        con = getConexion();
        ps = null;

        String sql = "INSERT INTO usuario(usuario, password, pregunta, respuesta, ci_persona, id_tipo_usuario) VALUES(?,?,?,?,?,?);";

        try {

            int i;
            i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getUsuario());
            ps.setString(i++, getPassword());
            ps.setString(i++, getPregunta());
            ps.setString(i++, getRespuesta());
            ps.setString(i++, getPersona().getCedula());
            ps.setInt(i++, getTipoU().getId());

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

    public Boolean tieneUsuario() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT cedula FROM v_usuario WHERE cedula = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getPersona().getCedula());

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

    public boolean modificar() {

        con = getConexion();
        ps = null;

        String sql = "UPDATE usuario SET password=?, pregunta=?, respuesta=? WHERE id=?;";

        try {

            int i;
            i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getPassword());
            ps.setString(i++, getPregunta());
            ps.setString(i++, getRespuesta());
            ps.setInt(i++, getId());
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

        con = getConexion();
        ps = null;

        String sql = "UPDATE usuario SET activo = false WHERE id=?;";

        try {
            int i;
            i = 1;
            ps = con.prepareStatement(sql);
            ps.setInt(i++, id);
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

    public Boolean consultarPerfil() {
        con = getConexion();
        ps = null;
        rs = null;

        int ind;

        String sql = "SELECT * FROM v_perfil WHERE usuario = ?;";

        try {
            ps = con.prepareStatement(sql);

            ind = 1;
            ps.setString(ind++, getUsuario());
            rs = ps.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("id"));
                setPregunta(rs.getString("pregunta"));

                setPersona(new Persona());
                getPersona().setpNombre(rs.getString("p_nombre"));
                getPersona().setsNombre(rs.getString("s_nombre"));
                getPersona().setpApellido(rs.getString("p_apellido"));
                getPersona().setsApellido(rs.getString("s_apellido"));
                getPersona().setTelefono(rs.getString("telefono"));
                getPersona().setCorreo(rs.getString("correo"));

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

    public boolean modificarPregunta(String pregunta, String respuesta, String password) {

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT cambiar_pregunta(?,?,?,?)";

        try {

            int i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getUsuario());
            ps.setString(i++, pregunta);
            ps.setString(i++, respuesta);
            ps.setString(i++, password);

            rs = ps.executeQuery();

            boolean result = false;

            while (rs.next()) {
                result = rs.getBoolean(1);

            }

            return result;

        } catch (SQLException e) {
            System.err.println(e);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean modificarClave(String passwordNuevo, String passwordActual) {

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT cambiar_clave(?,?,?)";

        try {

            int i = 1;

            ps = con.prepareStatement(sql);
            ps.setString(i++, getUsuario());
            ps.setString(i++, passwordNuevo);
            ps.setString(i++, passwordActual);

            rs = ps.executeQuery();

            boolean result = false;

            while (rs.next()) {
                result = rs.getBoolean(1);

            }

            return result;

        } catch (SQLException e) {
            System.err.println(e);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoUsuario getTipoU() {
        return tipoU;
    }

    public void setTipoU(TipoUsuario tipoU) {
        this.tipoU = tipoU;
    }

}
