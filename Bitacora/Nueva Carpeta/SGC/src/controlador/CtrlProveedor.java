package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Funcion;
import modelo.Proveedores;
import sgc.SGC;
import vista.Catalogo;
import vista.VisProveedor;

public class CtrlProveedor implements ActionListener, WindowListener, KeyListener, MouseListener, ItemListener, FocusListener {

    private Catalogo catalogo;
    private VisProveedor vista;
    private Proveedores modelo;

    Funcion permiso;

    ArrayList<Proveedores> listaProveedores;
    DefaultTableModel dm;
    DefaultComboBoxModel dmCbx;

    public CtrlProveedor() {
        this.catalogo = new Catalogo();
        this.vista = new VisProveedor();
        this.modelo = new Proveedores();

        catalogo.lblTitulo.setText("Proveedores");
        CtrlVentana.cambiarVista(catalogo);
        llenarTabla(catalogo.tabla);
        vista.cbxCedulaRif.addItemListener(this);
        stylecombo(vista.cbxCedulaRif);
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
        vista.txtCedulaRif.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtContacto.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txtCorreo.addFocusListener(this);
        vista.txaDireccion.addKeyListener(this);
    }

    public void llenarTabla(JTable tablaD) {

        int ind;

        listaProveedores = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
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

            ind = 0;
            columna[ind++] = listaProveedores.get(i).getCedulaRif();
            columna[ind++] = listaProveedores.get(i).getNombre();
            columna[ind++] = listaProveedores.get(i).getTelefono();
            columna[ind++] = listaProveedores.get(i).getCorreo();
            columna[ind++] = listaProveedores.get(i).getContacto();
            columna[ind++] = listaProveedores.get(i).getDireccion();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {

            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            vista.cbxCedulaRif.setEnabled(true);
            this.vista.txtCedulaRif.setEnabled(true);

            limpiar();

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {

            if (validar()) {

                String letra;
                String cedulaRif;

                letra = vista.cbxCedulaRif.getSelectedItem().toString();
                cedulaRif = vista.txtCedulaRif.getText();

                modelo.setCedulaRif(letra + "-" + cedulaRif);
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setContacto(vista.txtContacto.getText());
                modelo.setDireccion(vista.txaDireccion.getText());

                if (modelo.registrar(modelo)) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Registro guardado ", "Registro de datos", JOptionPane.INFORMATION_MESSAGE, p);
                    llenarTabla(catalogo.tabla);
                    CtrlVentana.cambiarVista(catalogo);

                } else {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "No se pudo registrar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {

            if (validar()) {

                String letra;
                String cedulaRif;

                letra = vista.cbxCedulaRif.getSelectedItem().toString();
                cedulaRif = vista.txtCedulaRif.getText();

                modelo.setCedulaRif(letra + "-" + cedulaRif);
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setContacto(vista.txtContacto.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setDireccion(vista.txaDireccion.getText());

                if (modelo.modificar()) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Registro modificado ", "Modificación de datos", JOptionPane.INFORMATION_MESSAGE, p);
                    llenarTabla(catalogo.tabla);
                    CtrlVentana.cambiarVista(catalogo);

                } else {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "No se pudo modificar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            String letra;
            String cedulaRif;

            letra = vista.cbxCedulaRif.getSelectedItem().toString();
            cedulaRif = vista.txtCedulaRif.getText();

            modelo.setCedulaRif(letra + "-" + cedulaRif);

            if (modelo.buscarGasto(modelo)) {

                UIManager UI = new UIManager();
                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                int botonDialogo = JOptionPane.OK_OPTION;
                Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                JOptionPane.showMessageDialog(null, "No se puede eliminar si tiene gastos por procesar asignados ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);

            } else {

                if (modelo.eliminar(modelo)) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/multiplication-sign.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Registro eliminado ", "Eliminación de datos", JOptionPane.INFORMATION_MESSAGE, p);
                    llenarTabla(catalogo.tabla);
                    CtrlVentana.cambiarVista(catalogo);

                } else {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "No se pudo eliminar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
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

        vista.cbxCedulaRif.setSelectedIndex(0);
        vista.txtCedulaRif.setText(null);
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

        if (vista.txtCedulaRif.getText().isEmpty()) {

            msj += "El campo C.I./RIF no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtNombre.getText().isEmpty()) {

            msj += "El campo Nombre o Razón Social no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtTelefono.getText().isEmpty()) {

            msj += "El campo Teléfono no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtCorreo.getText().isEmpty()) {

            msj += "El campo Correo Electrónico no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtContacto.getText().isEmpty()) {

            msj += "El campo Nombre del Contanto no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txaDireccion.getText().isEmpty()) {

            msj += "El campo Dirección no puede estar vacío \n";
            resultado = false;
        }

        if (!resultado) {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            int botonDialogo = JOptionPane.OK_OPTION;
            Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;
    }

    @Override
    public void windowOpened(WindowEvent e) {

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtCedulaRif, vista.txtNombre, vista.txtContacto, vista.txtTelefono, vista.txtCorreo
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

        if (ke.getSource() == vista.txtCedulaRif) {
            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCedulaRif.getText(), 8);
        }
        if (ke.getSource() == vista.txtNombre) {

            Validacion.limite(ke, vista.txtNombre.getText(), 60);
        }
        if (ke.getSource() == vista.txtContacto) {

            Validacion.limite(ke, vista.txtContacto.getText(), 60);
        }
        if (ke.getSource() == vista.txaDireccion) {

            Validacion.limite(ke, vista.txaDireccion.getText(), 150);
        }
        if (ke.getSource() == vista.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, vista.txtTelefono.getText(), 12);
        }
        if (ke.getSource() == vista.txtCorreo) {
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCorreo.getText(), 80);

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

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        catalogo.txtBuscar.setText(String.valueOf(dato));

        modelo.setCedulaRif(String.valueOf(dato));

        modelo.buscar(modelo);

        vista.cbxCedulaRif.setSelectedItem(modelo.getCedulaRif().split("-")[0]);
        vista.txtCedulaRif.setText(modelo.getCedulaRif().split("-")[1]);
        vista.txtContacto.setText(modelo.getContacto());
        vista.txtCorreo.setText(modelo.getCorreo());
        vista.txtNombre.setText(modelo.getNombre());
        vista.txtTelefono.setText(modelo.getTelefono());
        vista.txaDireccion.setText(modelo.getDireccion());
        vista.txtCedulaRif.setEnabled(false);

        vista.btnGuardar.setEnabled(false);
        vista.btnModificar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.cbxCedulaRif.setEnabled(false);

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

    public void itemStateChanged(ItemEvent e) {
        vista.cbxCedulaRif.setFocusable(false);
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(Validacion.email(vista.txtCorreo.getText())){
        
        }else{
            JOptionPane.showMessageDialog(null, "Email incorrecto");
            vista.txtCorreo.requestFocus();
        }
    }

}
