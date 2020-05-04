package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.CrudUsuario;
import vista.GestionarUsuario;
import vista.catalogoUsuario;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CtrlUsuario implements ActionListener, ItemListener, MouseListener, KeyListener, WindowListener {

    private CrudUsuario modC;
    private GestionarUsuario vistaGU;
    private catalogoUsuario catausu;
    ArrayList<CrudUsuario> listaUsu;
    DefaultTableModel dm;

    //Constructor de inicializacion de variables. Desde la linea 16 a la 26
    public CtrlUsuario(CrudUsuario modC, GestionarUsuario vistaGU, catalogoUsuario catausu) {

        this.modC = modC;
        this.vistaGU = vistaGU;
        this.catausu = catausu;
        this.vistaGU.btnGuardar.addActionListener(this);
        this.vistaGU.btnModificar.addActionListener(this);
        this.vistaGU.btnLimpiar.addActionListener(this);
        this.catausu.btnNuevoUsuario.addActionListener(this);
        this.vistaGU.txtCedula.addKeyListener(this);
        this.vistaGU.txtUsuario.addKeyListener(this);
        this.vistaGU.jpPassword.addKeyListener(this);
        this.vistaGU.txtNombre.addKeyListener(this);
        this.vistaGU.txtApellido.addKeyListener(this);
        this.vistaGU.txtTelefono.addKeyListener(this);
        this.catausu.txtBuscar.addKeyListener(this);
        this.catausu.jtable.addMouseListener(this);
        this.catausu.addWindowListener(this);
        this.vistaGU.btnEliminar.addActionListener(this);

    }
    //Fin del constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaGU.btnGuardar) {
            if (validar()) {
                modC.setCedula(vistaGU.txtCedula.getText());
                modC.setUsuario(vistaGU.txtUsuario.getText());
                modC.setPassword(vistaGU.jpPassword.getText());
                modC.setNombre(vistaGU.txtNombre.getText());
                modC.setApellido(vistaGU.txtApellido.getText());
                modC.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
                if (modC.getTipo().equals("Seleccione un tipo de usuario")) {
                    JOptionPane.showMessageDialog(null, "seleccione un tipo de usuario");
                } else {
                    modC.setNtelefono(vistaGU.txtTelefono.getText());

                    if (modC.registrar(modC)) {

                        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                        Llenartabla(catausu.jtable);

                    } else {

                        JOptionPane.showMessageDialog(null, "Registro Duplicado");

                    }

                }
            }
        }

        if (e.getSource() == vistaGU.btnModificar) {
            if (validar()) {
                modC.setUsuario(vistaGU.txtUsuario.getText());
                modC.setNombre(vistaGU.txtNombre.getText());
                modC.setPassword(vistaGU.jpPassword.getText());
                modC.setApellido(vistaGU.txtApellido.getText());
                modC.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
                if (modC.getTipo().equals("Seleccione un tipo de usuario")) {
                    JOptionPane.showMessageDialog(null, "seleccione un tipo de usuario");
                } else {
                    modC.setNtelefono(vistaGU.txtTelefono.getText());

                    if (vistaGU.jpPassword.getText().isEmpty()) {
                        if (modC.modificar(modC)) {

                            JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
                            Llenartabla(catausu.jtable);
                            this.vistaGU.dispose();
                            limpiar();
                        } else {
                            JOptionPane.showMessageDialog(null, "El nombre de usuario ya esta siendo utilizado");

                        }

                    } else if (modC.modificarC(modC)) {

                        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
                        Llenartabla(catausu.jtable);
                        this.vistaGU.dispose();
                        limpiar();
                    }
                }

            }
        }
        if (e.getSource() == vistaGU.btnEliminar) {

            if (modC.eliminar(modC)) {
                modC.setUsuario(vistaGU.txtUsuario.getText());
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                vistaGU.dispose();
                Llenartabla(catausu.jtable);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }

        if (e.getSource() == vistaGU.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == catausu.btnNuevoUsuario) {
            limpiar();
            this.vistaGU.setVisible(true);
            this.vistaGU.btnModificar.setEnabled(false);
            this.vistaGU.btnGuardar.setEnabled(true);
            this.vistaGU.btnEliminar.setEnabled(false);
            this.vistaGU.txtCedula.setEnabled(true);

        }
    }

    public void limpiar() {

        vistaGU.txtCedula.setText(null);
        vistaGU.txtUsuario.setText(null);
        vistaGU.jpPassword.setText(null);
        vistaGU.txtNombre.setText(null);
        vistaGU.txtApellido.setText(null);
        vistaGU.cbxTipo.setSelectedItem(0);
        vistaGU.txtTelefono.setText(null);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catausu.jtable.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catausu.jtable.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catausu.jtable.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        Boolean resultado = true;
        String msj = "";

        modC.setUsuario(String.valueOf(dato));

        modC.buscar(modC);

        vistaGU.setVisible(true);
        vistaGU.txtCedula.setText(modC.getCedula());
        vistaGU.txtUsuario.setText(modC.getUsuario());
        vistaGU.txtNombre.setText(modC.getNombre());
        vistaGU.txtApellido.setText(modC.getApellido());
        vistaGU.txtTelefono.setText(modC.getNtelefono());
        vistaGU.cbxTipo.setSelectedItem(modC.getTipo());
        vistaGU.txtCedula.setEnabled(false);
        vistaGU.btnGuardar.setEnabled(false);
        vistaGU.btnModificar.setEnabled(true);
        vistaGU.btnEliminar.setEnabled(true);
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

            Validacion.soloLetras(ke);

            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtUsuario.getText(), 20);
        }
        if (ke.getSource() == vistaGU.jpPassword) {
            Validacion.Espacio(ke);

            Validacion.limite(ke, vistaGU.jpPassword.getText(), 15);
        }
        if (ke.getSource() == vistaGU.txtNombre) {
            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtNombre.getText(), 20);

        }
        if (ke.getSource() == vistaGU.txtApellido) {

            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtApellido.getText(), 20);
        }
        if (ke.getSource() == vistaGU.txtTelefono) {

            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtTelefono.getText(), 11);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == catausu.txtBuscar) {
            filtro(catausu.txtBuscar.getText(), catausu.jtable);
        } else {
        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vistaGU.txtCedula.getText().isEmpty()) {

            msj += "El campo Cédula no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtUsuario.getText().isEmpty()) {

            msj += "El campo Usuario no puede estar vacío\n";
            resultado = false;
        }

        if (vistaGU.txtNombre.getText().isEmpty()) {

            msj += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtApellido.getText().isEmpty()) {

            msj += "El campo Apellido no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtTelefono.getText().isEmpty()) {

            msj += "El campo Password no puede estar vacío\n";
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    public void Llenartabla(JTable tablaD) {

        listaUsu = modC.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Usuario");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Telefono");
        modeloT.addColumn("Tipo");

        Object[] columna = new Object[5];

        int numRegistro = listaUsu.size();
        

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaUsu.get(i).getUsuario();
            columna[1] = listaUsu.get(i).getNombre();
           
            columna[2] = listaUsu.get(i).getApellido();
            columna[3] = listaUsu.get(i).getNtelefono();
            columna[4] = listaUsu.get(i).getTipo();

            modeloT.addRow(columna);

        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catausu.jtable);
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
