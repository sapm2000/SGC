package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import vista.VisUsuario;

public class CtrlUsuario implements ActionListener, MouseListener, KeyListener {

    private Usuario modelo;
    private VisUsuario vista;
    private Catalogo catalogo;

    private Persona modPersona;
    private ArrayList<Usuario> listaUsu;
    private ArrayList<Persona> listaPersona;
    private ArrayList<TipoUsuario> listaTipo;
    private TipoUsuario modTipo;
    DefaultTableModel dm;

    //Constructor de inicializacion de variables
    public CtrlUsuario() {

        this.modelo = new Usuario();
        this.vista = new VisUsuario();
        this.catalogo = new Catalogo();
        this.modPersona = new Persona();
        this.modTipo = new TipoUsuario();

        crearCbxTipoU();
        llenarTabla(catalogo.tabla);
        CtrlVentana.cambiarVista(catalogo);
        catalogo.lblTitulo.setText("Usuario");
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.catalogo.btnNuevo.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtCedula.addKeyListener(this);
        this.vista.txtUsuario.addKeyListener(this);
        this.vista.txtClave.addKeyListener(this);
        this.vista.txtClave2.addKeyListener(this);
        this.vista.txtPregunta.addKeyListener(this);
        this.vista.txtRespuesta.addKeyListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.setVisible(true);
        this.vista.jTable.addMouseListener(this);

    }
    //Fin del constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo.setPersona(new Persona(vista.txtCedula.getText()));
                modelo.setUsuario(vista.txtUsuario.getText());
                String claveSegura = Validacion.encriptar(String.valueOf(vista.txtClave.getPassword()));
                modelo.setPassword(claveSegura);
                modelo.setPregunta(vista.txtPregunta.getText());
                String respuestaSegura = Validacion.encriptar(String.valueOf(vista.txtRespuesta.getText()));
                modelo.setRespuesta(respuestaSegura);
                int ind = vista.cbxTipo.getSelectedIndex() - 1;
                modelo.getTipoU().setId(listaTipo.get(ind).getId());

                if (modelo.existeInactivo()) {
                    JOptionPane.showMessageDialog(null, "Esta persona ya tiene un usuario en la BD, se recuperarán los datos");

                    if (modelo.reactivar()) {
                        JOptionPane.showMessageDialog(null, "Usuario habilitado");
                        llenarTabla(catalogo.tabla);
                        CtrlVentana.cambiarVista(catalogo);
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
                                CtrlVentana.cambiarVista(catalogo);
                                limpiar();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al Registrar Usuario");

                            }
                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == catalogo.btnNuevo) {
            limpiar();
            llenarTablaPersona();
            this.vista.btnGuardar.setEnabled(true);
            this.vista.txtCedula.setEnabled(true);

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    private void crearCbxTipoU() {
        listaTipo = modTipo.listar();
        vista.cbxTipo.addItem("Seleccione...");

        if (listaTipo != null) {
            for (TipoUsuario datosX : listaTipo) {
                vista.cbxTipo.addItem(datosX.getNombre());
            }

        }
    }

    public void limpiar() {

        vista.txtCedula.setText(null);
        vista.txtUsuario.setText(null);
        vista.txtClave.setText(null);
        vista.txtPregunta.setText(null);
        vista.txtClave2.setText(null);
        vista.cbxTipo.setSelectedIndex(0);
        vista.txtRespuesta.setText(null);

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
        if (e.getSource() == vista.jTable) {

            int fila = this.vista.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            modPersona = listaPersona.get(fila);

            vista.txtCedula.setText(modPersona.getCedula());

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
        if (ke.getSource() == vista.txtCedula) {
            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCedula.getText(), 12);
        }
        if (ke.getSource() == vista.txtUsuario) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtUsuario.getText(), 20);
        }
        if (ke.getSource() == vista.txtClave) {
            Validacion.Espacio(ke);

            Validacion.limite(ke, vista.txtClave.getText(), 15);
        }
        if (ke.getSource() == vista.txtPregunta) {
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtPregunta.getText(), 20);

        }
        if (ke.getSource() == vista.txtClave2) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtClave2.getText(), 20);
        }
        if (ke.getSource() == vista.txtRespuesta) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtRespuesta.getText(), 11);
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

        if (vista.txtCedula.getText().isEmpty()) {

            msj += "Debe seleccionar una persona\n";
            resultado = false;
        }
        if (vista.txtUsuario.getText().isEmpty()) {

            msj += "El campo Usuario no puede estar vacío\n";
            resultado = false;
        }

        if (String.valueOf(vista.txtClave.getPassword()).isEmpty()) {

            msj += "El campo Contraseña no puede estar vacío\n";
            resultado = false;
        } else if (String.valueOf(vista.txtClave2.getPassword()).isEmpty()) {
            msj += "El campo Repetir Contraseña no puede estar vacío\n";
            resultado = false;

        } else if (!String.valueOf(vista.txtClave.getPassword()).equals(String.valueOf(vista.txtClave2.getPassword()))) {
            System.out.println(String.valueOf(vista.txtClave.getPassword()) + " " + vista.txtClave2.getPassword().toString());
            msj += "Las Contraseñas no coinciden\n";
            resultado = false;

        }
        if (vista.txtPregunta.getText().isEmpty()) {

            msj += "El campo Pregunta de Seguridad no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtRespuesta.getText().isEmpty()) {

            msj += "El campo Respuesta secreta no puede estar vacío\n";
            resultado = false;
        }
        if (vista.cbxTipo.getSelectedItem() == null) {

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
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaD.setRowSorter(tr);
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
        vista.jTable.setModel(modeloT);

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

}
