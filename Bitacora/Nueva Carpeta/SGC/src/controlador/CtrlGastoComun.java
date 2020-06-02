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
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.GastoComun;
import modelo.ModeloConceptoGastos;
import modelo.Proveedores;
import vista.Catalogo;
import vista.buscarProveedor;
import vista.gastoComun;

public class CtrlGastoComun implements ActionListener, MouseListener, KeyListener, WindowListener {

    private gastoComun vista;
    private Catalogo catalogo;
    private GastoComun modgac;
    private Proveedores modpro;
    private ModeloConceptoGastos modcon;
    private CerrarMes modc;
    private buscarProveedor buscpro;
    ArrayList<Proveedores> listaProveedores;
    ArrayList<GastoComun> listagastocomun;
    ArrayList<ModeloConceptoGastos> listaConGas;
    DefaultTableModel dm;
    double montoi;
    double saldo;

    public CtrlGastoComun() {
        this.vista = new gastoComun();
        this.catalogo = new Catalogo();
        this.modgac = new GastoComun();
        this.modpro = new Proveedores();
        this.modcon = new ModeloConceptoGastos();

        this.modc = new CerrarMes();
        this.buscpro = new buscarProveedor();
        catalogo.lblTitulo.setText("Gasto Común");
        CtrlVentana.cambiarVista(catalogo);
        LlenartablaGastocomun(catalogo.tabla);

        this.catalogo.btnNuevo.addActionListener(this);
        this.buscpro.jTable1.addMouseListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.vista.btnBuscarproveedor.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);

        vista.txtNumerofactura.addKeyListener(this);
        vista.txtMonto.addKeyListener(this);
        vista.txaObservaciones.addKeyListener(this);
        listaConGas = modcon.listarConcepto();
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

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtid.setVisible(false);
            vista.jcomboconcepto.removeAllItems();
            listaConGas = modcon.listarConcepto();
            crearCbxConcepto(listaConGas);
            vista.btnBuscarproveedor.setVisible(true);
        }
        if (e.getSource() == vista.btnBuscarproveedor) {
            this.buscpro.setVisible(true);
            Llenartabla(buscpro.jTable1);

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {

                modgac.setTipo_gasto(vista.jcombotipo.getSelectedItem().toString());
                modgac.setMes(vista.jMonthChooser1.getMonth() + 1);
                modgac.setAño(vista.jYearChooser1.getYear());
                modgac.setMonto(Double.parseDouble(vista.txtMonto.getText()));
                modgac.setNumero_factura(vista.txtNumerofactura.getText());
                modgac.setId_proveedor(vista.txtProveedor.getText());
                modcon.setNombre_Concepto(vista.jcomboconcepto.getSelectedItem().toString());
                int ind = vista.jcomboconcepto.getSelectedIndex() - 1;

                modgac.setObservaciones(vista.txaObservaciones.getText());
                modgac.setSaldo(Double.parseDouble(vista.txtMonto.getText()));
                if (modgac.getId_proveedor().equals("Seleccione el Proveedor")) {
                    JOptionPane.showMessageDialog(null, "seleccione un proveedor");
                } else {
                    if (ind == -1) {
                        JOptionPane.showMessageDialog(null, "seleccione un concepto");
                    } else {
                        modgac.setId_concepto(listaConGas.get(ind).getId());
                        java.sql.Date sqlDate = convert(vista.jDateChooser1.getDate());
                        modgac.setFecha(sqlDate);
                        modgac.setEstado("Pendiente");

                        modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(vista.jYearChooser1.getYear());

                        if (modc.buscarfechas(modc)) {
                            JOptionPane.showMessageDialog(null, "no puede registrar gastos a un periodo ya cerrado");
                        } else {

                            if (modgac.registrar_gasto_comun(modgac)) {

                                JOptionPane.showMessageDialog(null, "Registro Guardado");

                                LlenartablaGastocomun(catalogo.tabla);
                            } else {

                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                            }
                        }

                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modgac.setTipo_gasto(vista.jcombotipo.getSelectedItem().toString());
                modgac.setMes(vista.jMonthChooser1.getMonth() + 1);
                modgac.setAño(vista.jYearChooser1.getYear());
                modgac.setMonto(Double.parseDouble(vista.txtMonto.getText()));
                modgac.setNumero_factura(vista.txtNumerofactura.getText());
                modgac.setId_proveedor(vista.txtProveedor.getText());
                modcon.setNombre_Concepto(vista.jcomboconcepto.getSelectedItem().toString());
                modcon.buscarid(modcon);
                if (modgac.getId_proveedor().equals("Seleccione el Proveedor")) {
                    JOptionPane.showMessageDialog(null, "seleccione un proveedor");
                } else {
                    if (modcon.getNombre_Concepto().equals("Seleccione el Concepto")) {
                        JOptionPane.showMessageDialog(null, "seleccione un concepto");
                    } else {
                        int ind = vista.jcomboconcepto.getSelectedIndex() - 1;
                        modgac.setId_concepto(listaConGas.get(ind).getId());
                        modgac.setObservaciones(vista.txaObservaciones.getText());
                        modgac.setId(Integer.parseInt(vista.txtid.getText()));
                        modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(vista.jYearChooser1.getYear());

                        if (modc.buscarfechas(modc)) {
                            JOptionPane.showMessageDialog(null, "no puede registrar gastos a un periodo ya cerrado");
                        } else {

                            java.sql.Date sqlDate = convert(vista.jDateChooser1.getDate());
                            modgac.setFecha(sqlDate);
                            modgac.setEstado("Pendiente");

                            double var1 = Double.parseDouble(vista.txtMonto.getText());
                            double var2 = var1 - montoi;
                            double total = var2 + saldo;
                            modgac.setSaldo(total);

                            if (total > 0) {
                                if (modgac.modificar_gasto_comun(modgac)) {

                                    JOptionPane.showMessageDialog(null, "Registro Modificado");

                                    LlenartablaGastocomun(catalogo.tabla);
                                    this.vista.dispose();

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

        if (e.getSource() == vista.btnEliminar) {

            modgac.setId(Integer.parseInt(vista.txtid.getText()));

            if (modgac.eliminar_gasto_comun(modgac)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                LlenartablaGastocomun(catalogo.tabla);
                this.vista.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }

    public void limpiar() {

        vista.txtMonto.setText(null);
        vista.txaObservaciones.setText(null);
        vista.jcomboconcepto.setSelectedItem("Seleccione el Concepto");
        vista.txtProveedor.setText(null);
        vista.jMonthChooser1.setMonth(0);
        vista.jcombotipo.setSelectedItem("Ordinario");
        vista.jYearChooser1.setYear(0);
        vista.txtNumerofactura.setText(null);
        vista.jDateChooser1.setDate(null);

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtMonto.getText().isEmpty()) {

            msj += "El campo monto no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txtNumerofactura.getText().isEmpty()) {

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
        if (e.getSource() == catalogo.tabla) {

            int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            modgac.setId(Integer.parseInt(dato));

            modgac.buscargastoComun(modgac);
            vista.btnBuscarproveedor.setVisible(true);
            this.vista.setVisible(true);
            vista.txtid.setVisible(false);
            vista.txtid.setText(dato);

            vista.jcomboconcepto.removeAllItems();
            if (modgac.getEstado().equals("Procesado") || modgac.getEstado().equals("Pagado") || modgac.getEstado().equals("Procesado y pagado")) {
                vista.btnEliminar.setEnabled(false);
                vista.btnModificar.setEnabled(false);
                vista.btnGuardar.setEnabled(false);
                vista.txaObservaciones.setEditable(false);
                vista.txtMonto.setEditable(false);
                vista.txtNumerofactura.setEditable(false);
                vista.txtProveedor.setEditable(false);
                vista.jcombotipo.setEditable(false);
                vista.jcomboconcepto.setEditable(false);
                vista.jcomboconcepto.addItem(modgac.getNombre_Concepto());
                vista.btnBuscarproveedor.setVisible(false);

                JOptionPane.showMessageDialog(null, "los gastos procesados no pueden ser modificados ni eliminados");
            } else {
                vista.btnEliminar.setEnabled(true);
                vista.btnModificar.setEnabled(true);
                vista.btnGuardar.setEnabled(false);
                listaConGas = modcon.listarConcepto();
                crearCbxConcepto(listaConGas);

            }

            vista.txtProveedor.setText(modgac.getId_proveedor());
            vista.jcomboconcepto.setSelectedItem(modgac.getNombre_Concepto());
            vista.jMonthChooser1.setMonth(modgac.getMes() - 1);
            vista.jYearChooser1.setYear(modgac.getAño());
            vista.jDateChooser1.setDate(modgac.getFecha());
            vista.txtProveedor.setText(modgac.getId_proveedor());
            vista.jcombotipo.setSelectedItem(modgac.getTipo_gasto());
            vista.txaObservaciones.setText(modgac.getObservaciones());
            vista.txtMonto.setText(String.valueOf(Validacion.formato1.format(modgac.getMonto())));
            vista.txtNumerofactura.setText(modgac.getNumero_factura());

        }
        if (e.getSource() == buscpro.jTable1) {
            int fila1 = this.buscpro.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.buscpro.jTable1.getValueAt(fila1, 0)); // por ultimo, obtengo el valor de la celda
            vista.txtProveedor.setText(dato);
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
        if (e.getSource() == vista.txaObservaciones) {

            Validacion.limite(e, vista.txaObservaciones.getText(), 500);
        }

        if (e.getSource() == vista.txtNumerofactura) {
            Validacion.soloNumeros(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtNumerofactura.getText(), 50);
        }
        if (e.getSource() == vista.txtMonto) {

            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, vista.txtMonto.getText());
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

    @Override
    public void windowOpened(WindowEvent e) {

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtNumerofactura, vista.txaObservaciones, vista.txtMonto
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
        vista.jcomboconcepto.addItem("Seleccione el Concepto");

        if (datos != null) {
            for (ModeloConceptoGastos datosX : datos) {
                modcon = datosX;
                vista.jcomboconcepto.addItem(modcon.getNombre_Concepto());
            }

        }
    }
}
