package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Funcion;
import modelo.Proveedores;
import sgc.SGC;
import vista.Catalogo;
import vista.VisProveedor;

public class CtrlProveedor implements ActionListener, WindowListener, KeyListener, MouseListener {

    private Catalogo catalogo;
    private VisProveedor vista;
    private Proveedores modpro;

    Funcion permiso;

    ArrayList<Proveedores> listaProveedores;
    DefaultTableModel dm;
    DefaultComboBoxModel dmCbx;

    public CtrlProveedor() {
        this.catalogo = new Catalogo();
        this.vista = new VisProveedor();
        this.modpro = new Proveedores();

        catalogo.lblTitulo.setText("Proveedores");
        CtrlVentana.cambiarVista(catalogo);
        Llenartabla(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        vista.txtCedula.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtContacto.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txaDireccion.addKeyListener(this);
        this.catalogo.setVisible(true);
    }

    public void Llenartabla(JTable tablaD) {

        listaProveedores = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula/Rif");
        modeloT.addColumn("Nombre/Razón Social");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo Electrónico");
        modeloT.addColumn("Contacto");
        modeloT.addColumn("Dirección");

        Object[] columna = new Object[6];

        int numRegistro = listaProveedores.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaProveedores.get(i).getCedula();
            columna[1] = listaProveedores.get(i).getNombre();
            columna[2] = listaProveedores.get(i).getTelefono();
            columna[3] = listaProveedores.get(i).getCorreo();
            columna[4] = listaProveedores.get(i).getContacto();
            columna[5] = listaProveedores.get(i).getDireccion();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);
    }

    public void Llenartablainactivos(JTable tablaD) {

        listaProveedores = modpro.listarinactivos();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = false;
                }
                if (column == 3) {
                    resu = false;
                }
                if (column == 4) {
                    resu = false;
                }
                if (column == 5) {
                    resu = false;
                }
                if (column == 6) {
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula/Rif");
        modeloT.addColumn("Nombre/Razón Social");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo Electrónico");
        modeloT.addColumn("Contacto");
        modeloT.addColumn("Dirección");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[7];

        int numRegistro = listaProveedores.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaProveedores.get(i).getCedula();
            columna[1] = listaProveedores.get(i).getNombre();
            columna[2] = listaProveedores.get(i).getTelefono();
            columna[3] = listaProveedores.get(i).getCorreo();
            columna[4] = listaProveedores.get(i).getContacto();
            columna[5] = listaProveedores.get(i).getDireccion();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(6).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtCedula.setEnabled(true);
            vista.txtCedula.setText("");
            vista.txtContacto.setText("");
            vista.txtCorreo.setText("");
            vista.txtNombre.setText("");
            vista.txtTelefono.setText("");
            vista.txaDireccion.setText("");

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modpro.setCedula(vista.txtCedula.getText());
                modpro.setNombre(vista.txtNombre.getText());
                modpro.setContacto(vista.txtContacto.getText());
                modpro.setCorreo(vista.txtCorreo.getText());
                modpro.setTelefono(vista.txtTelefono.getText());
                modpro.setDireccion(vista.txaDireccion.getText());

                if (modpro.registrar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catalogo.tabla);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modpro.setCedula(vista.txtCedula.getText());
                modpro.setNombre(vista.txtNombre.getText());
                modpro.setContacto(vista.txtContacto.getText());
                modpro.setCorreo(vista.txtCorreo.getText());
                modpro.setTelefono(vista.txtTelefono.getText());
                modpro.setDireccion(vista.txaDireccion.getText());

                if (modpro.modificar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    Llenartabla(catalogo.tabla);
                    CtrlVentana.cambiarVista(catalogo);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == vista.btnEliminar) {
            modpro.setCedula(vista.txtCedula.getText());
            if (modpro.Buscargas(modpro) || modpro.Buscarcuo(modpro)) {
                JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene gastos por procesar asignados");
            } else {
                if (modpro.eliminar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    CtrlVentana.cambiarVista(catalogo);
                    Llenartabla(catalogo.tabla);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }
            }

        }
        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    public void limpiar() {

        vista.txtCedula.setText(null);
        vista.txtNombre.setText(null);
        vista.txtContacto.setText(null);
        vista.txtTelefono.setText(null);
        vista.txtCorreo.setText(null);
        vista.txaDireccion.setText(null);
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtCedula.getText().isEmpty()) {

            msj += "El campo C.I./RIF. no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtNombre.getText().isEmpty()) {

            msj += "El campo nombre no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtTelefono.getText().isEmpty()) {

            msj += "El campo teléfono no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtCorreo.getText().isEmpty()) {

            msj += "El campo correo electrónico no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtContacto.getText().isEmpty()) {

            msj += "El campo contanto no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txaDireccion.getText().isEmpty()) {

            msj += "El campo dirección no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void windowOpened(WindowEvent e) {

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtCedula, vista.txtNombre, vista.txtContacto, vista.txtTelefono, vista.txtCorreo
        };
        Validacion.copiar(components);
        Validacion.pegar(com);
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

    @Override
    public void keyTyped(KeyEvent ke) {

        if (ke.getSource() == vista.txtCedula) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCedula.getText(), 15);
        }
        if (ke.getSource() == vista.txtNombre) {

            Validacion.limite(ke, vista.txtNombre.getText(), 100);
        }
        if (ke.getSource() == vista.txtContacto) {

            Validacion.limite(ke, vista.txtContacto.getText(), 60);
        }
        if (ke.getSource() == vista.txaDireccion) {

            Validacion.limite(ke, vista.txaDireccion.getText(), 60);
        }
        if (ke.getSource() == vista.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, vista.txtTelefono.getText(), 15);
        }
        if (ke.getSource() == vista.txtCorreo) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCorreo.getText(), 40);

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        } else {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.tabla.getSelectedColumn(); // luego, obtengo la columna seleccionada

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        catalogo.txtBuscar.setText(String.valueOf(dato));

        modpro.setCedula(String.valueOf(dato));

        modpro.buscar(modpro);

        vista.txtCedula.setText(modpro.getCedula());
        vista.txtContacto.setText(modpro.getContacto());
        vista.txtCorreo.setText(modpro.getCorreo());
        vista.txtNombre.setText(modpro.getNombre());
        vista.txtTelefono.setText(modpro.getTelefono());
        vista.txaDireccion.setText(modpro.getDireccion());
        vista.txtCedula.setEnabled(false);

        vista.btnGuardar.setEnabled(false);

        vista.btnModificar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);

        CtrlVentana.cambiarVista(vista);
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

}
