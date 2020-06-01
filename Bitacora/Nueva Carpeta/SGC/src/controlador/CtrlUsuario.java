package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Persona;
import modelo.TipoUsuario;
import modelo.Usuario;
import vista.Catalogo;
import vista.GestionarUsuario;

public class CtrlUsuario implements ActionListener, MouseListener, KeyListener, WindowListener {

    private Usuario modelo;
    private GestionarUsuario vistaGU;
    private Catalogo catalogo;

    private Persona modPersona;
    private ArrayList<Usuario> listaUsu;
    private ArrayList<Persona> listaPersona;
    private ArrayList<TipoUsuario> listaTipo;
    private TipoUsuario modTipo;
    private DefaultTableModel dm;

    //Constructor de inicializacion de variables
    public CtrlUsuario() {

        this.modelo = new Usuario();
        this.vistaGU = new GestionarUsuario();
        this.catalogo = new Catalogo();
        this.modPersona = new Persona();
        this.modTipo = new TipoUsuario();
        
        catalogo.lblTitulo.setText("Usuario");
        this.vistaGU.btnGuardar.addActionListener(this);
        this.vistaGU.btnLimpiar.addActionListener(this);
        this.catalogo.btnNuevo.addActionListener(this);
        this.vistaGU.txtCedula.addKeyListener(this);
        this.vistaGU.txtUsuario.addKeyListener(this);
        this.vistaGU.txtClave.addKeyListener(this);
        this.vistaGU.txtClave2.addKeyListener(this);
        this.vistaGU.txtPregunta.addKeyListener(this);
        this.vistaGU.txtRespuesta.addKeyListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.addWindowListener(this);
        this.vistaGU.addWindowListener(this);
        this.catalogo.setVisible(true);
        this.vistaGU.jTable.addMouseListener(this);

    }
    //Fin del constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaGU.btnGuardar) {
            if (validar()) {
                modelo.setPersona(new Persona(vistaGU.txtCedula.getText()));
                modelo.setUsuario(vistaGU.txtUsuario.getText());
                String claveSegura = Validacion.encriptar(String.valueOf(vistaGU.txtClave.getPassword()));
                modelo.setPassword(claveSegura);
                modelo.setPregunta(vistaGU.txtPregunta.getText());
                modelo.setRespuesta(vistaGU.txtRespuesta.getText());
                int ind = vistaGU.cbxTipo.getSelectedIndex() - 1;
                modelo.getTipoU().setId(listaTipo.get(ind).getId());

                if (modelo.existeInactivo()) {
                    JOptionPane.showMessageDialog(null, "Esta persona ya tiene un usuario en la BD, se recuperarán los datos");

                    if (modelo.reactivar()) {
                        JOptionPane.showMessageDialog(null, "Usuario habilitado");
                        llenarTabla(catalogo.tabla);
                        vistaGU.dispose();
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo habilitar el usuario");

                    }
                } else {
                    if (modelo.tieneUsuario()) {
                        JOptionPane.showMessageDialog(null, "Esta persona ya tiene un usuario");

                    } else {
                        if (modelo.existe()) {
                            JOptionPane.showMessageDialog(null, "Este nombre de usuario ya existe");
                        } else {

                            if (modelo.registrar()) {
                                JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                                llenarTabla(catalogo.tabla);
                                vistaGU.dispose();
                                limpiar();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al Registrar Usuario");

                            }
                        }
                    }
                }
            }
        }

        if (e.getSource() == vistaGU.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == catalogo.btnNuevo) {
            limpiar();
            llenarTablaPersona();
            this.vistaGU.setVisible(true);
            this.vistaGU.btnGuardar.setEnabled(true);
            this.vistaGU.txtCedula.setEnabled(true);

        }
    }

    private void crearCbxTipoU() {
        listaTipo = modTipo.listar();
        vistaGU.cbxTipo.addItem("Seleccione...");

        if (listaTipo != null) {
            for (TipoUsuario datosX : listaTipo) {
                vistaGU.cbxTipo.addItem(datosX.getNombre());
            }
        
        }
    }

    public void limpiar() {

        vistaGU.txtCedula.setText(null);
        vistaGU.txtUsuario.setText(null);
        vistaGU.txtClave.setText(null);
        vistaGU.txtPregunta.setText(null);
        vistaGU.txtClave2.setText(null);
        vistaGU.cbxTipo.setSelectedItem(0);
        vistaGU.txtRespuesta.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catalogo.tabla) {

            int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            modelo = listaUsu.get(fila);

            int result = JOptionPane.showConfirmDialog(catalogo, "¿Desea Eliminar Usuario?", "ELIMINAR USUARIO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == 0) {
                modelo.eliminar();
                llenarTabla(catalogo.tabla);
            }
        }
        if (e.getSource() == vistaGU.jTable) {

            int fila = this.vistaGU.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            modPersona = listaPersona.get(fila);

            vistaGU.txtCedula.setText(modPersona.getCedula());

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == vistaGU.txtCedula) {
            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtCedula.getText(), 12);
        }
        if (ke.getSource() == vistaGU.txtUsuario) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtUsuario.getText(), 20);
        }
        if (ke.getSource() == vistaGU.txtClave) {
            Validacion.Espacio(ke);

            Validacion.limite(ke, vistaGU.txtClave.getText(), 15);
        }
        if (ke.getSource() == vistaGU.txtPregunta) {
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtPregunta.getText(), 20);

        }
        if (ke.getSource() == vistaGU.txtClave2) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtClave2.getText(), 20);
        }
        if (ke.getSource() == vistaGU.txtRespuesta) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtRespuesta.getText(), 11);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == catalogo.txtBuscar) {
            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vistaGU.txtCedula.getText().isEmpty()) {

            msj += "Debe seleccionar una persona\n";
            resultado = false;
        }
        if (vistaGU.txtUsuario.getText().isEmpty()) {

            msj += "El campo Usuario no puede estar vacío\n";
            resultado = false;
        }

        if (String.valueOf(vistaGU.txtClave.getPassword()).isEmpty()) {

            msj += "El campo Contraseña no puede estar vacío\n";
            resultado = false;
        } else if (String.valueOf(vistaGU.txtClave2.getPassword()).isEmpty()) {
            msj += "El campo Repetir Contraseña no puede estar vacío\n";
            resultado = false;

        } else if (!String.valueOf(vistaGU.txtClave.getPassword()).equals(String.valueOf(vistaGU.txtClave2.getPassword()))) {
            System.out.println(String.valueOf(vistaGU.txtClave.getPassword()) + " " + vistaGU.txtClave2.getPassword().toString());
            msj += "Las Contraseñas no coinciden\n";
            resultado = false;

        }
        if (vistaGU.txtPregunta.getText().isEmpty()) {

            msj += "El campo Pregunta de Seguridad no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtRespuesta.getText().isEmpty()) {

            msj += "El campo Respuesta secreta no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.cbxTipo.getSelectedItem() == null) {

            msj += "Debe seleccionar Tipo de Usuario";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private void filtro(String consulta, JTable tablaBuscar) {
        dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    public void llenarTabla(JTable tablaD) {

        listaUsu = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Usuario");
        modeloT.addColumn("Cédula");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaUsu.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listaUsu.get(i).getUsuario();
            columna[ind++] = listaUsu.get(i).getPersona().getCedula();

            modeloT.addRow(columna);

        }

    }

    public void llenarTablaPersona() {

        listaPersona = modPersona.listarP();

        DefaultTableModel modeloT = new DefaultTableModel();
        vistaGU.jTable.setModel(modeloT);

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Teléfono");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaPersona.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listaPersona.get(i).getCedula();
            columna[ind++] = listaPersona.get(i).getpNombre() + " " + listaPersona.get(i).getpApellido();
            columna[ind++] = listaPersona.get(i).getCorreo();
            columna[ind++] = listaPersona.get(i).getTelefono();
            modeloT.addRow(columna);

        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
        llenarTabla(catalogo.tabla);
        if (e.getSource() == vistaGU) {
            crearCbxTipoU();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
