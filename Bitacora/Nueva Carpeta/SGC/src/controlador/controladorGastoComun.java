/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Component;
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
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;
import modelo.CerrarMes;
import modelo.GastoComun;
import modelo.ModeloConceptoGastos;
import modelo.Proveedores;
import vista.PantallaPrincipal1;
import vista.buscarProveedor;
import vista.catalogoGastoComun;
import vista.gastoComun;

/**
 *
 * @author rma
 */
public class controladorGastoComun implements ActionListener, MouseListener, KeyListener, WindowListener {

    private gastoComun gc;
    private catalogoGastoComun catagc;
    private GastoComun modgac;
    private Proveedores modpro;
    private ModeloConceptoGastos modcon;
    private PantallaPrincipal1 panta1;
    private CerrarMes modc;
    private buscarProveedor buscpro;
    ArrayList<Proveedores> listaProveedores;
    ArrayList<GastoComun> listagastocomun;
    ArrayList<ModeloConceptoGastos> listaConGas;
    DefaultTableModel dm;
    double montoi;
    double saldo;

    public controladorGastoComun(gastoComun gc, catalogoGastoComun gatagc, GastoComun modgac, Proveedores modpro, ModeloConceptoGastos modcon, PantallaPrincipal1 panta1, CerrarMes modc, buscarProveedor buscpro) {
        this.gc = gc;
        this.catagc = gatagc;
        this.modgac = modgac;
        this.modpro = modpro;
        this.modcon = modcon;
        this.panta1 = panta1;
        this.modc = modc;
        this.buscpro = buscpro;
        this.catagc.addWindowListener(this);

        this.catagc.jButton2.addActionListener(this);
        this.buscpro.jTable1.addMouseListener(this);
        this.catagc.jTable1.addMouseListener(this);
        this.catagc.jTextField1.addKeyListener(this);
        this.gc.btnBuscarproveedor.addActionListener(this);
        this.gc.btnGuardar.addActionListener(this);
        this.gc.btnLimpiar.addActionListener(this);
        this.gc.btnModificar.addActionListener(this);
        this.gc.btnEliminar.addActionListener(this);

        gc.txtNumerofactura.addKeyListener(this);
        gc.txtMonto.addKeyListener(this);
        gc.txaObservaciones.addKeyListener(this);
        listaConGas = modcon.listarConcepto();
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

        Object[] columna = new Object[3];

        int numRegistro = listaProveedores.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaProveedores.get(i).getCedula();
            columna[1] = listaProveedores.get(i).getNombre();
            columna[2] = listaProveedores.get(i).getTelefono();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void LlenartablaGastocomun(JTable tablaD) {

        listagastocomun = modgac.listarGastoComun(3);
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Gasto Común</html>");
        modeloT.addColumn("<html>Tipo de <br> Gasto</html>");
        modeloT.addColumn("Período");
        modeloT.addColumn("Monto");
        modeloT.addColumn("<html>Saldo <br> Restante</html>");
        modeloT.addColumn("<html>Nº de <br> Factura</html>");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Observaciones");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Estado");

        Object[] columna = new Object[12];

        int numRegistro = listagastocomun.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listagastocomun.get(i).getId();
            columna[1] = listagastocomun.get(i).getTipo_gasto();
            String fecha = String.valueOf(listagastocomun.get(i).getMes()) + "-" + listagastocomun.get(i).getAño();
            columna[2] = fecha;
            columna[3] = String.format("%.1f", listagastocomun.get(i).getMonto());
            columna[4] = Validacion.formato1.format(listagastocomun.get(i).getSaldo());
            columna[5] = listagastocomun.get(i).getNumero_factura();
            columna[6] = listagastocomun.get(i).getId_proveedor();
            columna[7] = listagastocomun.get(i).getNombre_Concepto();
            columna[8] = listagastocomun.get(i).getObservaciones();
            columna[9] = listagastocomun.get(i).getFecha();
            columna[10] = listagastocomun.get(i).getEstado();

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
        tablaD.getColumnModel().getColumn(8).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(9).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(10).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catagc.jButton2) {
            this.gc.setVisible(true);
            this.gc.btnModificar.setEnabled(false);
            this.gc.btnGuardar.setEnabled(true);
            this.gc.btnEliminar.setEnabled(false);
            this.gc.txtid.setVisible(false);
            gc.jcomboconcepto.removeAllItems();
            listaConGas = modcon.listarConcepto();
            crearCbxConcepto(listaConGas);
            gc.btnBuscarproveedor.setVisible(true);
        }
        if (e.getSource() == gc.btnBuscarproveedor) {
            this.buscpro.setVisible(true);
            Llenartabla(buscpro.jTable1);

        }

        if (e.getSource() == gc.btnGuardar) {
            if (validar()) {

                modgac.setTipo_gasto(gc.jcombotipo.getSelectedItem().toString());
                modgac.setMes(gc.jMonthChooser1.getMonth() + 1);
                modgac.setAño(gc.jYearChooser1.getYear());
                modgac.setMonto(Double.parseDouble(gc.txtMonto.getText()));
                modgac.setNumero_factura(gc.txtNumerofactura.getText());
                modgac.setId_proveedor(gc.txtProveedor.getText());
                modcon.setNombre_Concepto(gc.jcomboconcepto.getSelectedItem().toString());
                int ind = gc.jcomboconcepto.getSelectedIndex() - 1;
                modgac.setId_concepto(listaConGas.get(ind).getId());

                modgac.setObservaciones(gc.txaObservaciones.getText());
                modgac.setSaldo(Double.parseDouble(gc.txtMonto.getText()));
                if (modgac.getId_proveedor().equals("Seleccione el Proveedor")) {
                    JOptionPane.showMessageDialog(null, "seleccione un proveedor");
                } else {
                    if (modcon.getNombre_Concepto().equals("Seleccione el Concepto")) {
                        JOptionPane.showMessageDialog(null, "seleccione un concepto");
                    } else {
                        java.sql.Date sqlDate = convert(gc.jDateChooser1.getDate());
                        modgac.setFecha(sqlDate);
                        modgac.setEstado("Pendiente");
                        modgac.setId_condominio(panta1.rif.getText());
                        modc.setMes_cierre(gc.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(gc.jYearChooser1.getYear());
                        modc.setId_condominio(panta1.rif.getText());

                        if (modc.buscarfechas(modc)) {
                            JOptionPane.showMessageDialog(null, "no puede registrar gastos a un periodo ya cerrado");
                        } else {

                            if (modgac.registrar_gasto_comun(modgac)) {

                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                modgac.setId_condominio(panta1.rif.getText());
                                LlenartablaGastocomun(catagc.jTable1);
                            } else {

                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                            }
                        }

                    }
                }
            }
        }

        if (e.getSource() == gc.btnModificar) {
            if (validar()) {
                modgac.setTipo_gasto(gc.jcombotipo.getSelectedItem().toString());
                modgac.setMes(gc.jMonthChooser1.getMonth() + 1);
                modgac.setAño(gc.jYearChooser1.getYear());
                modgac.setMonto(Double.parseDouble(gc.txtMonto.getText()));
                modgac.setNumero_factura(gc.txtNumerofactura.getText());
                modgac.setId_proveedor(gc.txtProveedor.getText());
                modcon.setNombre_Concepto(gc.jcomboconcepto.getSelectedItem().toString());
                modcon.buscarid(modcon);
                if (modgac.getId_proveedor().equals("Seleccione el Proveedor")) {
                    JOptionPane.showMessageDialog(null, "seleccione un proveedor");
                } else {
                    if (modcon.getNombre_Concepto().equals("Seleccione el Concepto")) {
                        JOptionPane.showMessageDialog(null, "seleccione un concepto");
                    } else {
                        int ind = gc.jcomboconcepto.getSelectedIndex() - 1;
                        modgac.setId_concepto(listaConGas.get(ind).getId());
                        modgac.setObservaciones(gc.txaObservaciones.getText());
                        modgac.setId(Integer.parseInt(gc.txtid.getText()));
                        modc.setMes_cierre(gc.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(gc.jYearChooser1.getYear());
                        modc.setId_condominio(panta1.rif.getText());

                        if (modc.buscarfechas(modc)) {
                            JOptionPane.showMessageDialog(null, "no puede registrar gastos a un periodo ya cerrado");
                        } else {

                            java.sql.Date sqlDate = convert(gc.jDateChooser1.getDate());
                            modgac.setFecha(sqlDate);
                            modgac.setEstado("Pendiente");
                            modgac.setId_condominio(panta1.rif.getText());

                            double var1 = Double.parseDouble(gc.txtMonto.getText());
                            double var2 = var1 - montoi;
                            double total = var2 + saldo;
                            modgac.setSaldo(total);

                            if (total > 0) {
                                if (modgac.modificar_gasto_comun(modgac)) {

                                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                                    modgac.setId_condominio(panta1.rif.getText());
                                    LlenartablaGastocomun(catagc.jTable1);
                                    this.gc.dispose();

                                } else {

                                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El saldo de la deuda no puede ser negativo");
                            }

                        }
                    }
                }
            }

        }

        if (e.getSource() == gc.btnEliminar) {

            modgac.setId_condominio(panta1.rif.getText());
            modgac.setId(Integer.parseInt(gc.txtid.getText()));

            if (modgac.eliminar_gasto_comun(modgac)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                LlenartablaGastocomun(catagc.jTable1);
                this.gc.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
        if (e.getSource() == gc.btnLimpiar) {
            limpiar();
        }
    }

    public void limpiar() {

        gc.txtMonto.setText(null);
        gc.txaObservaciones.setText(null);
        gc.jcomboconcepto.setSelectedItem("Seleccione el Concepto");
        gc.txtProveedor.setText(null);
        gc.jMonthChooser1.setMonth(0);
        gc.jcombotipo.setSelectedItem("Ordinario");
        gc.jYearChooser1.setYear(0);
        gc.txtNumerofactura.setText(null);
        gc.jDateChooser1.setDate(null);

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (gc.txtMonto.getText().isEmpty()) {

            msj += "El campo monto no puede estar vacio\n";
            resultado = false;
        }

        if (gc.txtNumerofactura.getText().isEmpty()) {

            msj += "El campo número de factura no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catagc.jTable1) {

            int fila = this.catagc.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.catagc.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            modgac.setId(Integer.parseInt(dato));
            modgac.setId_condominio(panta1.rif.getText());
            modgac.buscargastoComun(modgac);
             gc.btnBuscarproveedor.setVisible(true);
            this.gc.setVisible(true);
            gc.txtid.setVisible(false);
            gc.txtid.setText(dato);

            gc.jcomboconcepto.removeAllItems();
            if (modgac.getEstado().equals("Procesado") || modgac.getEstado().equals("Pagado") || modgac.getEstado().equals("Procesado y pagado")) {
                gc.btnEliminar.setEnabled(false);
                gc.btnModificar.setEnabled(false);
                gc.btnGuardar.setEnabled(false);
                gc.txaObservaciones.setEditable(false);
                gc.txtMonto.setEditable(false);
                gc.txtNumerofactura.setEditable(false);
                gc.txtProveedor.setEditable(false);
                gc.jcombotipo.setEditable(false);
                gc.jcomboconcepto.setEditable(false);
                gc.jcomboconcepto.addItem(modgac.getNombre_Concepto());
                gc.btnBuscarproveedor.setVisible(false);

                JOptionPane.showMessageDialog(null, "los gastos procesados no pueden ser modificados ni eliminados");
            } else {
                gc.btnEliminar.setEnabled(true);
                gc.btnModificar.setEnabled(true);
                gc.btnGuardar.setEnabled(false);
                listaConGas = modcon.listarConcepto();
                crearCbxConcepto(listaConGas);

            }

            gc.txtProveedor.setText(modgac.getId_proveedor());
            gc.jcomboconcepto.setSelectedItem(modgac.getNombre_Concepto());
            gc.jMonthChooser1.setMonth(modgac.getMes() - 1);
            gc.jYearChooser1.setYear(modgac.getAño());
            gc.jDateChooser1.setDate(modgac.getFecha());
            gc.txtProveedor.setText(modgac.getId_proveedor());
            gc.jcombotipo.setSelectedItem(modgac.getTipo_gasto());
            gc.txaObservaciones.setText(modgac.getObservaciones());
            gc.txtMonto.setText(String.valueOf(Validacion.formato1.format(modgac.getMonto())));
            gc.txtNumerofactura.setText(modgac.getNumero_factura());

        }
        if (e.getSource() == buscpro.jTable1) {
            int fila1 = this.buscpro.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.buscpro.jTable1.getValueAt(fila1, 0)); // por ultimo, obtengo el valor de la celda
            gc.txtProveedor.setText(dato);
            buscpro.dispose();
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
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == gc.txaObservaciones) {

            Validacion.limite(e, gc.txaObservaciones.getText(), 500);
        }

        if (e.getSource() == gc.txtNumerofactura) {
            Validacion.soloNumeros(e);
            Validacion.Espacio(e);
            Validacion.limite(e, gc.txtNumerofactura.getText(), 50);
        }
        if (e.getSource() == gc.txtMonto) {

            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, gc.txtMonto.getText());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catagc.jTextField1) {

            filtro(catagc.jTextField1.getText(), catagc.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        modgac.setId_condominio(panta1.rif.getText());
        LlenartablaGastocomun(catagc.jTable1);

        Component[] components = gc.jPanel2.getComponents();
        JComponent[] com = {
            gc.txtNumerofactura, gc.txaObservaciones, gc.txtMonto
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

    private void crearCbxConcepto(ArrayList<ModeloConceptoGastos> datos) {
        gc.jcomboconcepto.addItem("Seleccione el Concepto");

        if (datos != null) {
            for (ModeloConceptoGastos datosX : datos) {
                modcon = datosX;
                gc.jcomboconcepto.addItem(modcon.getNombre_Concepto());
            }

        }
    }
}
