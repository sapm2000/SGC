/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.Sancion;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.catalogoSancion;
import vista.sancion;

/**
 *
 * @author rma
 */
public class controladorSancion implements ActionListener, MouseListener, KeyListener, WindowListener {

    private sancion san;
    private catalogoSancion catasan;
    private Sancion modsan;
    private PantallaPrincipal1 panta1;
    private CerrarMes modc;
    ArrayList<Unidades> listaunidades;
    ArrayList<Sancion> listaSancion;
    ArrayList<Sancion> listaunimod;
    DefaultTableModel dm;

    public controladorSancion(sancion san, catalogoSancion catasan, Sancion modsan, PantallaPrincipal1 panta1, CerrarMes modc) {
        this.san = san;
        this.catasan = catasan;
        this.modsan = modsan;
        this.panta1 = panta1;
        this.modc = modc;

        this.catasan.jButton2.addActionListener(this);
        this.catasan.addWindowListener(this);
        this.catasan.jTextField1.addKeyListener(this);
        this.catasan.jTable1.addMouseListener(this);
        this.san.btnEliminar.addActionListener(this);

        this.san.btnGuardar.addActionListener(this);
        this.san.btnLimpiar.addActionListener(this);
        this.san.btnModificar.addActionListener(this);

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
        modeloT.addColumn("Estatus");

        Object[] columna = new Object[7];

        int numRegistro = listaSancion.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaSancion.get(i).getId();
            columna[1] = listaSancion.get(i).getDescripcion();
            columna[2] = listaSancion.get(i).getTipo();
            columna[3] = listaSancion.get(i).getMonto();
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

            columna[0] = listaunimod.get(i).getN_unidad();

            if (listaunimod.get(i).getId_sancion() != 0) {
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

        listaunidades = modsan.buscarUnidades();
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

        if (e.getSource() == catasan.jButton2) {
            this.san.setVisible(true);
            this.san.btnModificar.setEnabled(false);
            this.san.btnGuardar.setEnabled(true);
            this.san.btnEliminar.setEnabled(false);
            san.txtId.setVisible(false);
            modsan.setId_condominio(panta1.rif.getText());
            llenartablaunidades(san.jTable1);
            addCheckBox(1, san.jTable1);

        }

        if (e.getSource() == san.btnGuardar) {
            if (validar()) {
                modsan.setMes(san.jMonthChooser1.getMonth() + 1);
                modsan.setAño(san.jYearChooser1.getYear());
                modsan.setTipo(san.jComboBox1.getSelectedItem().toString());
                modsan.setDescripcion(san.txaDescripcion.getText());
                modsan.setId_condominio(panta1.rif.getText());
                modsan.setMonto(Double.parseDouble(san.txtmonto.getText()));
                modsan.setEstado("Pendiente");
                modc.setMes_cierre(san.jMonthChooser1.getMonth() + 1);
                modc.setAño_cierre(san.jYearChooser1.getYear());
                modc.setId_condominio(panta1.rif.getText());

                if (modc.buscarfechas(modc)) {
                    JOptionPane.showMessageDialog(null, "no puede registrar Sanciones a un periodo ya cerrado");
                } else {

                    modsan.buscarSancionRepetido(modsan);
                    int x = modsan.getId_sancion();

                    if (x == 0) {
                        if (modsan.registrarsancion(modsan)) {
                            JOptionPane.showMessageDialog(null, "Registro Guardado");
                            modsan.buscId(modsan);

                            for (int i = 0; i < san.jTable1.getRowCount(); i++) {
                                if (valueOf(san.jTable1.getValueAt(i, 1)) == "true") {

                                    String valor = String.valueOf(san.jTable1.getValueAt(i, 0));
                                    modsan.setN_unidad(valor);

                                    modsan.registrar_sancion_unidad(modsan);

                                }
                            }
                            LlenartablaSancion(catasan.jTable1);
                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");
                    }
                }

            }
        }

        if (e.getSource() == san.btnModificar) {
            if (validar()) {
                modsan.setMes(san.jMonthChooser1.getMonth() + 1);
                modsan.setAño(san.jYearChooser1.getYear());
                modsan.setTipo(san.jComboBox1.getSelectedItem().toString());
                modsan.setDescripcion(san.txaDescripcion.getText());
                modsan.setId(Integer.parseInt(san.txtId.getText()));
                modsan.setMonto(Double.parseDouble(san.txtmonto.getText()));
                modsan.setId_condominio(panta1.rif.getText());

                int x = 0;
                if (modsan.buscarSancionRepetido(modsan)) {
                    x = modsan.getId_sancion();

                }

                modc.setMes_cierre(san.jMonthChooser1.getMonth() + 1);
                modc.setAño_cierre(san.jYearChooser1.getYear());
                modc.setId_condominio(panta1.rif.getText());

                if (modc.buscarfechas(modc)) {
                    JOptionPane.showMessageDialog(null, "no puede registrar Sanciones a un periodo ya cerrado");
                } else {

                    if (x == 0 || x == modsan.getId()) {
                        if (modsan.modificarSancion(modsan)) {
                            JOptionPane.showMessageDialog(null, "Registro Modificado");

                            modsan.borrarpuentesancion(modsan);

                            for (int i = 0; i < san.jTable1.getRowCount(); i++) {
                                if (valueOf(san.jTable1.getValueAt(i, 1)) == "true") {

                                    String valor = String.valueOf(san.jTable1.getValueAt(i, 0));
                                    modsan.setN_unidad(valor);

                                    modsan.registrar_sancion_unidad(modsan);

                                }
                            }
                            LlenartablaSancion(catasan.jTable1);
                            this.san.dispose();
                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");
                    }
                }

            }

        }

        if (e.getSource() == san.btnEliminar) {
            modsan.setId(Integer.parseInt(san.txtId.getText()));
            modsan.borrarpuentesancion(modsan);
            modsan.eliminarsancion(modsan);
            JOptionPane.showMessageDialog(null, "registro eliminado");
            san.dispose();
            LlenartablaSancion(catasan.jTable1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {
        int fila = this.catasan.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada

        String dato = String.valueOf(this.catasan.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        modsan.setId(Integer.parseInt(dato));

        modsan.buscarSancion(modsan);
        
        this.san.setVisible(true);
        san.txtId.setVisible(false);
        san.txtId.setText(dato);
        san.txaDescripcion.setText(modsan.getDescripcion());
        san.txtmonto.setText(String.valueOf(modsan.getMonto()));
        san.jComboBox1.setSelectedItem(modsan.getTipo());
        int mes = modsan.getMes() - 1;
        san.jMonthChooser1.setMonth(mes);
        san.jYearChooser1.setYear(modsan.getAño());
        modsan.setId_condominio(panta1.rif.getText());
        llenartablaunidadesmod(san.jTable1);
        addCheckBox(1, san.jTable1);
         if (modsan.getEstado().equals("Procesado")) {
            san.btnEliminar.setEnabled(false);
            san.btnModificar.setEnabled(false);
            san.btnGuardar.setEnabled(false);
            JOptionPane.showMessageDialog(null, "las sanciones procesadas no pueden ser modificados ni eliminados");
        } else {
            san.btnEliminar.setEnabled(true);
            san.btnModificar.setEnabled(true);
            san.btnGuardar.setEnabled(false);
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
    public void keyTyped(KeyEvent e
    ) {

    }

    @Override
    public void keyPressed(KeyEvent e
    ) {

    }

    @Override
    public void keyReleased(KeyEvent e
    ) {
        if (e.getSource() == catasan.jTextField1) {

            filtro(catasan.jTextField1.getText(), catasan.jTable1);

        }
    }

    @Override
    public void windowOpened(WindowEvent e
    ) {
        modsan.setId_condominio(panta1.rif.getText());
        LlenartablaSancion(catasan.jTable1);
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

        if (san.txtmonto.getText().isEmpty()) {

            msj += "El campo monto no puede estar vacio\n";
            resultado = false;
        }
        
        if (san.txaDescripcion.getText().isEmpty()) {

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
