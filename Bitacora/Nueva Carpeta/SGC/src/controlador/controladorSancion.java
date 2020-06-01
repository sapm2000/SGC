/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import static java.lang.String.valueOf;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.Funcion;
import modelo.Sancion;
import modelo.Unidades;
import sgc.SGC;
import vista.Catalogo;
import vista.sancion;

/**
 *
 * @author rma
 */
public class controladorSancion implements ActionListener, MouseListener, KeyListener, WindowListener {

    private sancion vista;
    private Catalogo catalogo;
    private Sancion modsan;
    private CerrarMes modc;
    private Unidades moduni;

    Funcion permiso;

    ArrayList<Unidades> listaunidades;
    ArrayList<Sancion> listaSancion;
    ArrayList<Sancion> listaunimod;
    DefaultTableModel dm;

    public controladorSancion() {
        this.vista = new sancion();
        this.catalogo = new Catalogo();
        this.modsan = new Sancion();
        this.moduni = new Unidades();
        this.modc = new CerrarMes();

        catalogo.lblTitulo.setText("Sanciones");
        CtrlVentana.cambiarVista(catalogo);
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
        vista.txtmonto.addKeyListener(this);
        vista.txaDescripcion.addKeyListener(this);
        this.catalogo.setVisible(true);

    }

    public void LlenartablaSancion(JTable tablaD) {

        listaSancion = modsan.listarSanciones();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
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

        Object[] columna = new Object[7];

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
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Número de Unidad");
        modeloT.addColumn("seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunimod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunimod.get(i).uni.getN_unidad();

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
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Número de Unidad");
        modeloT.addColumn("seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunimod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunimod.get(i).uni.getN_unidad();

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
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Número Unidad");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunidades.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunidades.get(i).getN_unidad();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            vista.txtId.setVisible(false);
            llenartablaunidades(vista.jTable1);
            addCheckBox(1, vista.jTable1);

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                int j = 0;
                modsan.setMes(vista.jMonthChooser1.getMonth() + 1);
                modsan.setAño(vista.jYearChooser1.getYear());
                if (modsan.getAño() < 2000 || modsan.getAño() > 2100) {
                    JOptionPane.showMessageDialog(null, "seleccione un año valido");
                } else {
                    modsan.setTipo(vista.jComboBox1.getSelectedItem().toString());
                    if (modsan.getTipo().equals("Seleccione el Tipo de Deuda")) {
                        JOptionPane.showMessageDialog(null, "seleccione un tipo de deuda");
                    } else {
                        modsan.setDescripcion(vista.txaDescripcion.getText());
                        modsan.setMonto(Double.parseDouble(vista.txtmonto.getText()));
                        modsan.setEstado("Pendiente");
                        modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(vista.jYearChooser1.getYear());

                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                            if (valueOf(vista.jTable1.getValueAt(i, 1)) == "true") {

                                j = j + 1;

                            }
                        }
                        if (j == 0) {
                            JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                        } else {

                            if (modc.buscarfechas(modc)) {
                                JOptionPane.showMessageDialog(null, "no puede registrar Sanciones a un periodo ya cerrado");
                            } else {

                                if (modsan.buscarSancionRepetido(modsan) && modsan.getTipo().equals("Interes de mora")) {
                                    JOptionPane.showMessageDialog(null, "No puede guardar mas de un interes de mora al mes");
                                } else {
                                    if (modsan.registrarsancion(modsan)) {
                                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                                        modsan.buscId(modsan);
                                        listaunidades = moduni.listar();

                                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                                            if (valueOf(vista.jTable1.getValueAt(i, 1)) == "true") {

                                                modsan.uni.setId(listaunidades.get(i).getId());

                                                modsan.registrar_sancion_unidad(modsan);

                                            }
                                        }
                                        LlenartablaSancion(catalogo.tabla);
                                    } else {

                                        JOptionPane.showMessageDialog(null, "error al guardar");

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
                    JOptionPane.showMessageDialog(null, "seleccione un año valido");
                } else {
                    modsan.setTipo(vista.jComboBox1.getSelectedItem().toString());
                    if (modsan.getTipo().equals("Seleccione el Tipo de Deuda")) {
                        JOptionPane.showMessageDialog(null, "seleccione un tipo de deuda");
                    } else {
                        modsan.setDescripcion(vista.txaDescripcion.getText());
                        modsan.setId(Integer.parseInt(vista.txtId.getText()));
                        modsan.setMonto(Double.parseDouble(vista.txtmonto.getText()));

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
                            JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                        } else {

                            if (modc.buscarfechas(modc)) {
                                JOptionPane.showMessageDialog(null, "no puede registrar Sanciones a un periodo ya cerrado");
                            } else {

                                if (x == 0 || x == modsan.getId()) {
                                    if (modsan.modificarSancion(modsan)) {
                                        JOptionPane.showMessageDialog(null, "Registro Modificado");

                                        modsan.borrarpuentesancion(modsan);
                                        listaunimod = modsan.listarunidadesmod();
                                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                                            if (valueOf(vista.jTable1.getValueAt(i, 1)).equals("true")) {

                                                modsan.uni.setId(listaunimod.get(i).uni.getId());

                                                modsan.registrar_sancion_unidad(modsan);

                                            }
                                        }
                                        LlenartablaSancion(catalogo.tabla);
                                        this.vista.dispose();
                                    } else {

                                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existes");
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
            JOptionPane.showMessageDialog(null, "registro eliminado");
            vista.dispose();
            LlenartablaSancion(catalogo.tabla);
        }
        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

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
    public void mouseClicked(MouseEvent e
    ) {
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

        this.vista.setVisible(true);
        vista.txtId.setVisible(false);
        vista.txtId.setText(dato);
        vista.txaDescripcion.setText(modsan.getDescripcion());
        vista.txtmonto.setText(String.valueOf(modsan.getMonto()));
        vista.jComboBox1.setSelectedItem(modsan.getTipo());
        int mes = modsan.getMes() - 1;
        vista.jMonthChooser1.setMonth(mes);
        vista.jYearChooser1.setYear(modsan.getAño());
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
            JOptionPane.showMessageDialog(null, "las sanciones procesadas no pueden ser modificados ni eliminados");
        } else {
            vista.btnEliminar.setEnabled(true);
            vista.btnModificar.setEnabled(true);
            vista.btnGuardar.setEnabled(false);
        }

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

            msj += "El campo monto no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txaDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacio\n";
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
}
