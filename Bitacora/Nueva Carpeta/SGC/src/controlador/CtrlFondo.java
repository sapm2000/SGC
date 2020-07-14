package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
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
import modelo.Fondo;
import modelo.Funcion;
import sgc.SGC;
import vista.Catalogo;
import vista.VisFondo;

public class CtrlFondo implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private VisFondo vista;
    private Catalogo catalogo;
    private Fondo modelo;
    private ArrayList<Fondo> lista;

    private Funcion permiso;

    DefaultTableModel dm;
    double montoi;
    double saldo;

    public CtrlFondo() {
        this.vista = new VisFondo();
        this.catalogo = new Catalogo();
        this.modelo = new Fondo();

        catalogo.lblTitulo.setText("Fondo");
        vista.cbxMoneda.addItemListener(this);
        stylecombo(vista.cbxMoneda);

        vista.jDateChooser1.setMaxSelectableDate(new Date());

        llenarTabla(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtMontoInicial.addKeyListener(this);

        CtrlVentana.cambiarVista(catalogo);
    }

    public void llenarTabla(JTable tablaD) {
        lista = modelo.listar(3);

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

        modeloT.addColumn("Fecha");
        modeloT.addColumn("Tipo");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Observación");
        modeloT.addColumn("Monto Inicial");
        modeloT.addColumn("Saldo Actual");
        modeloT.addColumn("Moneda");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();
        int ind;
        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getFecha();
            columna[ind++] = lista.get(i).getTipo();
            columna[ind++] = lista.get(i).getDescripcion();
            columna[ind++] = lista.get(i).getObservacion();
            columna[ind++] = Validacion.formato1.format(lista.get(i).getMonto_inicial());
            columna[ind++] = Validacion.formato1.format(lista.get(i).getSaldo());
            columna[ind++] = lista.get(i).getMoneda();

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

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {

            if (funcionbtn.getNombre().equals("Fondo")) {
                permiso = funcionbtn;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtTipo.setEnabled(true);
            vista.jDateChooser1.setEnabled(true);
            vista.cbxMoneda.setEnabled(true);
            vista.txaDescripcion.setText("");
            vista.txaObservaciones.setText("");
            vista.txtMontoInicial.setText("");
            vista.txtTipo.setText("");
            vista.jDateChooser1.setDate(null);
            vista.cbxMoneda.setSelectedIndex(0);

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo.setTipo(vista.txtTipo.getText());
                if (vista.jDateChooser1.getDate() == null) {

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

                    JOptionPane.showMessageDialog(null, "Ingrese la fecha de creación del fondo ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                } else {
                    java.sql.Date sqlDate = convert(vista.jDateChooser1.getDate());
                    modelo.setFecha(sqlDate);
                    modelo.setDescripcion(vista.txaDescripcion.getText());
                    modelo.setObservacion(vista.txaObservaciones.getText());
                    modelo.setMonto_inicial(Double.parseDouble(vista.txtMontoInicial.getText()));
                    modelo.setMoneda(vista.cbxMoneda.getSelectedItem().toString());

                    if (modelo.buscar(modelo)) {

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

                        JOptionPane.showMessageDialog(null, "Este fondo ya está registrado ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    } else {

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
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modelo.setTipo(vista.txtTipo.getText());
                java.sql.Date sqlDate = convert(vista.jDateChooser1.getDate());
                modelo.setFecha(sqlDate);
                modelo.setDescripcion(vista.txaDescripcion.getText());
                modelo.setObservacion(vista.txaObservaciones.getText());
                modelo.setMoneda(vista.cbxMoneda.getSelectedItem().toString());

                int var7 = 0;
                var7 = modelo.getId();

                if (modelo.buscar1(modelo)) {
                    if (var7 == modelo.getId()) {
                        double var1 = Double.parseDouble(vista.txtMontoInicial.getText());
                        double var2 = var1 - montoi;
                        double total = var2 + saldo;
                        modelo.setSaldo(total);

                        if (total > 0) {
                            if (modelo.modificar(modelo)) {

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

                                JOptionPane.showMessageDialog(null, "Este registro ya existe ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                            }

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

                            JOptionPane.showMessageDialog(null, "El saldo no puede ser negativo ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                        }

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

                        JOptionPane.showMessageDialog(null, "No pudo ser modificado el fondo ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    }

                } else {

                    double var1 = Double.parseDouble(vista.txtMontoInicial.getText());
                    double var2 = var1 - montoi;
                    double total = var2 + saldo;
                    modelo.setSaldo(total);

                    if (total > 0) {
                        if (modelo.modificar(modelo)) {

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
                            Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                            UIManager.put("Button.background", Color.white);
                            UIManager.put("Button.font", Color.blue);
                            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                            UIManager.put("Label.background", Color.blue);
                            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                            JOptionPane.showMessageDialog(null, "Este registro ya existe ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                        }

                    } else {

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

                        JOptionPane.showMessageDialog(null, "El saldo no puede ser negativo ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modelo.setTipo(vista.txtTipo.getText());

            if (saldo == 0) {

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

                    JOptionPane.showMessageDialog(null, "Registro eliminado ", "Información", JOptionPane.INFORMATION_MESSAGE, p);
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

                    JOptionPane.showMessageDialog(null, "Error al eliminar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                }

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

                JOptionPane.showMessageDialog(null, "No se puede eliminar un fondo con saldo mayor a 0 ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
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

        vista.txtMontoInicial.setText(null);
        vista.txtTipo.setText(null);
        vista.txaDescripcion.setText(null);
        vista.txaObservaciones.setText(null);
        vista.jDateChooser1.setDate(null);
        vista.cbxMoneda.setSelectedIndex(0);
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtMontoInicial.getText().isEmpty()) {

            msj += "El campo monto inicial no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtTipo.getText().isEmpty()) {

            msj += "El campo tipo no puede estar vacío \n";
            resultado = false;
        }
        if (vista.cbxMoneda.getSelectedIndex() == 0) {

            msj += "Debe selccionar una moneda \n";
            resultado = false;
        }

        if (!resultado) {

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

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
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

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 1)); // por ultimo, obtengo el valor de la celda

        modelo.setTipo(String.valueOf(dato));
        vista.jDateChooser1.setEnabled(false);
        vista.cbxMoneda.setEnabled(false);
        vista.txtMontoInicial.setEnabled(false);
        vista.txtTipo.setEnabled(true);
        vista.btnGuardar.setEnabled(false);
        vista.btnEliminar.setEnabled(true);
        vista.btnModificar.setEnabled(true);

        modelo = lista.get(fila);

        vista.txaDescripcion.setText(modelo.getDescripcion());
        vista.txaObservaciones.setText(modelo.getObservacion());
        vista.txtTipo.setText(modelo.getTipo());
        vista.jDateChooser1.setDate(modelo.getFecha());
        vista.txtMontoInicial.setText(String.valueOf(Validacion.formato1.format(modelo.getMonto_inicial())));
        vista.cbxMoneda.setSelectedItem(modelo.getMoneda());
        saldo = modelo.getSaldo();
        montoi = modelo.getMonto_inicial();

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

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == vista.txtMontoInicial) {

            Validacion.Espacio(ke);
            Validacion.soloUnPunto(ke, vista.txtMontoInicial.getText());

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
    public void windowOpened(WindowEvent e) {

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtTipo, vista.txtMontoInicial
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxMoneda.setFocusable(false);
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }
}
