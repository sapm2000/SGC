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
import static java.lang.String.valueOf;
import java.util.ArrayList;
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
import modelo.CerrarMes;
import modelo.Funcion;
import modelo.Sancion;
import modelo.Unidad;
import sgc.SGC;
import vista.Catalogo;
import vista.VisSancion;

public class CtrlSancion implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private VisSancion vista;
    private Catalogo catalogo;
    private Sancion modsan;
    private CerrarMes modc;
    private Unidad moduni;

    Funcion permiso;

    ArrayList<Unidad> listaunidades;
    ArrayList<Sancion> listaSancion;
    ArrayList<Sancion> listaunimod;
    DefaultTableModel dm;

    public CtrlSancion() {
        
        this.moduni = new Unidad();
        
        if (moduni.contar() == 0) {

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

            JOptionPane.showMessageDialog(null, "No existen unidades, debe registrar una para continuar ", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE, p);

            new CtrlUnidad();
            
        } else {
        
        this.vista = new VisSancion();
        this.catalogo = new Catalogo();
        this.modsan = new Sancion();
        this.modc = new CerrarMes();

        catalogo.lblTitulo.setText("Sanciones");
        CtrlVentana.cambiarVista(catalogo);
        vista.cbxMoneda.addItemListener(this);
        stylecombo(vista.cbxMoneda);
        vista.jComboBox1.addItemListener(this);
        stylecombo(vista.jComboBox1);
        LlenartablaSancion(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.jYearChooser1.addKeyListener(this);

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        vista.txtmonto.addKeyListener(this);
        vista.txaDescripcion.addKeyListener(this);
        this.catalogo.setVisible(true);

        }
    }

    public void LlenartablaSancion(JTable tablaD) {

        listaSancion = modsan.listarSanciones();
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

        modeloT.addColumn("<html>Número de <br> Sanción</html>");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("<html>Tipo de <br> Deuda</html>");
        modeloT.addColumn("<html>Monto/ <br> Porcentaje</html>");
        modeloT.addColumn("Mes a aplicar");
        modeloT.addColumn("Nº de Unidades");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Moneda");

        Object[] columna = new Object[8];

        int numRegistro = listaSancion.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaSancion.get(i).getId();
            columna[1] = listaSancion.get(i).getDescripcion();
            columna[2] = listaSancion.get(i).getTipo();
            columna[3] = Validacion.formato1.format(listaSancion.get(i).getMonto());
            String fecha = String.valueOf(listaSancion.get(i).getMes()) + "-" + String.valueOf(listaSancion.get(i).getAño());
            columna[4] = fecha;
            columna[5] = listaSancion.get(i).getCantidad_de_unidades();
            columna[6] = listaSancion.get(i).getEstado();
            columna[7] = listaSancion.get(i).getMoneda();

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
        tablaD.getColumnModel().getColumn(7).setCellRenderer(tcr);
    }

    public void llenartablaunidadesmod(JTable tablaD) {
        listaunimod = modsan.listarunidadesmod();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = true;
                }
                return resu;
            }
        };
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Número de Unidad");
        modeloT.addColumn("seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunimod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunimod.get(i).uni.getNumeroUnidad();

            if (listaunimod.get(i).getId() != 0) {
                columna[1] = Boolean.TRUE;
            } else {
                columna[1] = Boolean.FALSE;
            }

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
    }

    public void llenartablaunidadesmodprocesadas(JTable tablaD) {
        listaunimod = modsan.listarunidadesmodprocesadas();
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

        modeloT.addColumn("Número de Unidad");
        modeloT.addColumn("seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunimod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunimod.get(i).uni.getNumeroUnidad();

            if (listaunimod.get(i).getId() != 0) {
                columna[1] = Boolean.TRUE;
            } else {
                columna[1] = Boolean.FALSE;
            }

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
    }

    public void llenartablaunidades(JTable tablaD) {

        listaunidades = moduni.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = true;
                }
                return resu;
            }
        };
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Número Unidad");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunidades.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunidades.get(i).getNumeroUnidad();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            vista.txtId.setVisible(false);
            llenartablaunidades(vista.jTable1);
            addCheckBox(1, vista.jTable1);

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                int j = 0;
                modsan.setMes(vista.jMonthChooser1.getMonth() + 1);
                modsan.setAño(vista.jYearChooser1.getYear());
                if (modsan.getAño() < 2000 || modsan.getAño() > 2100) {

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

                    JOptionPane.showMessageDialog(null, "seleccione un año valido", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                } else {
                    modsan.setTipo(vista.jComboBox1.getSelectedItem().toString());
                    if (modsan.getTipo().equals("Seleccione el Tipo de Deuda")) {

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

                        JOptionPane.showMessageDialog(null, "seleccione un tipo de deuda ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    } else {
                        modsan.setDescripcion(vista.txaDescripcion.getText());
                        modsan.setMonto(Double.parseDouble(vista.txtmonto.getText()));
                        modsan.setEstado("Pendiente");
                        modsan.setMoneda(vista.cbxMoneda.getSelectedItem().toString());
                        modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(vista.jYearChooser1.getYear());

                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                            if (valueOf(vista.jTable1.getValueAt(i, 1)) == "true") {

                                j = j + 1;

                            }
                        }
                        if (j == 0) {

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

                            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos 1 registro de la tabla ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                        } else {

                            if (modc.buscarfechas(modc)) {

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

                                JOptionPane.showMessageDialog(null, "No puede registrar Sanciones a un periodo ya cerrado ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                            } else {

                                if (modsan.buscarSancionRepetido(modsan) && modsan.getTipo().equals("INTERES DE MORA")) {

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

                                    JOptionPane.showMessageDialog(null, "No puede guardar mas de un interes de mora al mes ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                                } else {
                                    if (modsan.registrarsancion(modsan)) {

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

                                        JOptionPane.showMessageDialog(null, "Registro Guardado ", "Registro de datos", JOptionPane.INFORMATION_MESSAGE, p);
                                        modsan.buscId(modsan);
                                        listaunidades = moduni.listar();

                                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                                            if (valueOf(vista.jTable1.getValueAt(i, 1)) == "true") {

                                                modsan.uni.setId(listaunidades.get(i).getId());

                                                modsan.registrar_sancion_unidad(modsan);

                                            }
                                        }
                                        LlenartablaSancion(catalogo.tabla);
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

                                        JOptionPane.showMessageDialog(null, "Error al guardar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);

                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                int j = 0;
                modsan.setMes(vista.jMonthChooser1.getMonth() + 1);
                modsan.setAño(vista.jYearChooser1.getYear());
                if (modsan.getAño() < 2000 || modsan.getAño() > 2100) {

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

                    JOptionPane.showMessageDialog(null, "Seleccione un año valido ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                } else {
                    modsan.setTipo(vista.jComboBox1.getSelectedItem().toString());
                    if (modsan.getTipo().equals("Seleccione el Tipo de Deuda")) {

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

                        JOptionPane.showMessageDialog(null, "Seleccione un tipo de deuda ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    } else {
                        modsan.setDescripcion(vista.txaDescripcion.getText());
                        modsan.setId(Integer.parseInt(vista.txtId.getText()));
                        modsan.setMonto(Double.parseDouble(vista.txtmonto.getText()));
                        modsan.setMoneda(vista.cbxMoneda.getSelectedItem().toString());
                        int x = 0;
                        if (modsan.buscarSancionRepetido(modsan)) {
                            x = modsan.getId();

                        }

                        modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(vista.jYearChooser1.getYear());

                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                            if (valueOf(vista.jTable1.getValueAt(i, 1)) == "true") {

                                j = j + 1;

                            }
                        }
                        if (j == 0) {

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

                            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos 1 registro de la tabla ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                        } else {

                            if (modc.buscarfechas(modc)) {

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

                                JOptionPane.showMessageDialog(null, "No puede registrar Sanciones a un periodo ya cerrado ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                            } else {

                                if (x == 0 || x == modsan.getId()) {
                                    if (modsan.modificarSancion(modsan)) {

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

                                        JOptionPane.showMessageDialog(null, "Registro Modificado ", "Modificación de datos", JOptionPane.INFORMATION_MESSAGE, p);

                                        modsan.borrarpuentesancion(modsan);
                                        listaunimod = modsan.listarunidadesmod();
                                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                                            if (valueOf(vista.jTable1.getValueAt(i, 1)).equals("true")) {

                                                modsan.uni.setId(listaunimod.get(i).uni.getId());

                                                modsan.registrar_sancion_unidad(modsan);

                                            }
                                        }
                                        LlenartablaSancion(catalogo.tabla);
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

                                    JOptionPane.showMessageDialog(null, "Este registro ya existe ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                                }
                            }

                        }
                    }
                }

            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modsan.setId(Integer.parseInt(vista.txtId.getText()));
            modsan.borrarpuentesancion(modsan);
            modsan.eliminarsancion(modsan);

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
            CtrlVentana.cambiarVista(catalogo);
            LlenartablaSancion(catalogo.tabla);
        }
        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    public void limpiar() {

        vista.txtmonto.setText(null);
        vista.txaDescripcion.setText(null);
        vista.jComboBox1.setSelectedItem("Seleccione el Tipo de Deuda");
        vista.jMonthChooser1.setMonth(0);
        vista.jYearChooser1.setYear(0);
        llenartablaunidadesmod(vista.jTable1);
        addCheckBox(1, vista.jTable1);

    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        modsan.setId(Integer.parseInt(dato));

        String estado = String.valueOf(this.catalogo.tabla.getValueAt(fila, 6)); // por ultimo, obtengo el valor de la celda
        modsan.setEstado(estado);

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        modsan.buscarSancion(modsan);

        vista.txtId.setVisible(false);
        vista.txtId.setText(dato);
        vista.txaDescripcion.setText(modsan.getDescripcion());
        vista.txtmonto.setText(String.valueOf(modsan.getMonto()));
        vista.jComboBox1.setSelectedItem(modsan.getTipo());
        int mes = modsan.getMes() - 1;
        vista.jMonthChooser1.setMonth(mes);
        vista.jYearChooser1.setYear(modsan.getAño());
        vista.cbxMoneda.setSelectedItem(modsan.getMoneda());
        if (modsan.getEstado().equals("Pendiente")) {
            llenartablaunidadesmod(vista.jTable1);
            addCheckBox(1, vista.jTable1);
        } else {
            llenartablaunidadesmodprocesadas(vista.jTable1);
            addCheckBox(1, vista.jTable1);
        }
        if (modsan.getEstado().equals("Procesado")) {
            vista.btnEliminar.setEnabled(false);
            vista.btnModificar.setEnabled(false);
            vista.btnGuardar.setEnabled(false);

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

            JOptionPane.showMessageDialog(null, "Las sanciones procesadas no pueden ser modificados ni eliminados ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        } else {
            vista.btnEliminar.setEnabled(true);
            vista.btnModificar.setEnabled(true);
            vista.btnGuardar.setEnabled(false);
        }

        CtrlVentana.cambiarVista(vista);
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {

    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {

    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.txtmonto) {
            Validacion.limite(e, vista.txtmonto.getText(), 20);
            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, vista.txtmonto.getText());
        }
        if (e.getSource() == vista.txaDescripcion) {
            Validacion.limite(e, vista.txaDescripcion.getText(), 200);
        }
        if (e.getSource() == vista.jYearChooser1) {
            Validacion.limite(e, String.valueOf(vista.jYearChooser1.getYear()), 4);
            Validacion.soloNumeros(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e
    ) {

    }

    @Override
    public void keyReleased(KeyEvent e
    ) {
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);

        }
    }

    @Override
    public void windowOpened(WindowEvent e
    ) {

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtmonto
        };
        Validacion.copiar(components);
        Validacion.pegar(com);
    }

    @Override
    public void windowClosing(WindowEvent e
    ) {

    }

    @Override
    public void windowClosed(WindowEvent e
    ) {

    }

    @Override
    public void windowIconified(WindowEvent e
    ) {

    }

    @Override
    public void windowDeiconified(WindowEvent e
    ) {

    }

    @Override
    public void windowActivated(WindowEvent e
    ) {

    }

    @Override
    public void windowDeactivated(WindowEvent e
    ) {

    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtmonto.getText().isEmpty()) {

            msj += "El campo monto no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txaDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacío \n";
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxMoneda.setFocusable(false);
        vista.jComboBox1.setFocusable(false);
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }
}
