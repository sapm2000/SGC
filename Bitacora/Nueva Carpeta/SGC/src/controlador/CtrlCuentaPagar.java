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
import modelo.Cuenta;
import modelo.Cuenta_Pagar;
import modelo.Fondo;
import modelo.FormaPago;
import modelo.Gasto;
import vista.Catalogo;
import vista.VisCuentaPorPagar;

public class CtrlCuentaPagar implements ActionListener, ItemListener, KeyListener, MouseListener {

    private VisCuentaPorPagar vista;
    private Cuenta_Pagar modelo;
    private ArrayList<Cuenta_Pagar> lista;

    private Catalogo catCuenPro;
    private Catalogo catPagos;
    
    private FormaPago modFormaPago;
    private ArrayList<FormaPago> listaFormaPago;

    private Cuenta modCuenta;
    private ArrayList<Cuenta> listaCuenta;

    private Gasto modGasto;
    private ArrayList<Gasto> listaGasto;

    private Fondo modFondo;
    private ArrayList<Fondo> listaFondo;

    int fila;

    public CtrlCuentaPagar() {

        this.vista = new VisCuentaPorPagar();
        this.modelo = new Cuenta_Pagar();

        this.modGasto = new Gasto();
        this.modFormaPago = new FormaPago();
        this.modFondo = new Fondo();
        this.modCuenta = new Cuenta();

        this.catPagos = new Catalogo();
        this.catCuenPro = new Catalogo();

        this.catCuenPro.lblTitulo.setText("Cuentas Procesadas");
        this.catPagos.lblTitulo.setText("Cuentas Pagadas");

        this.vista.btnProcesar.addActionListener(this);
        this.vista.jTable.addMouseListener(this);
        this.vista.btnMostrar.addActionListener(this);
        this.vista.btnPagos.addActionListener(this);
        this.catPagos.txtBuscar.addKeyListener(this);
        this.vista.setVisible(true);

        CtrlVentana.cambiarVista(vista);

        crearCbxFormaPago();
        crearCbxCuenta();
        crearCbxFondo();
        llenarTablaGastos(vista.jTable, "Pendiente");

        Component[] components = vista.jPanel.getComponents();
        JComponent[] com = {vista.jDate, vista.txtDescripcion, vista.txtMonto, vista.txtProveedor, vista.txtPariedad};
        Validacion.copiar(components);
        Validacion.pegar(com);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnProcesar) {
            System.out.println("poli0");
            if (validar()) {
                System.out.println("poli1");
                modelo.setNum_ref(vista.txtPariedad.getText());
                modelo.setForma_pago(vista.cbxFormaP.getSelectedItem().toString());
                int ind = vista.cbxCuentaT.getSelectedIndex() - 1;
                modelo.getModCuenta().setN_cuenta(listaCuenta.get(ind).getN_cuenta());
                modelo.setDescripcion(vista.txtDescripcion.getText());
                java.sql.Date sqlDate = convert(vista.jDate.getDate());
                modelo.setFecha(sqlDate);
                ind = vista.cbxMoneda.getSelectedIndex() - 1;
                modelo.getModFondo().setId(listaFondo.get(ind).getId());
                modelo.getModFondo().setSaldo(listaFondo.get(ind).getSaldo());
                //int fila = this.vistaCuentaP.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
                modelo.setMonto(Float.parseFloat(vista.txtMonto.getText()));

                if (modelo.registrarPago(modelo)) {
                    modelo.getModFondo().restarFondo(modelo.getMonto());
                    // modGastoC.setId(listaGastoC.get(fila).getId());
                    // modGastoC.restarSaldo(modelo.getMonto());
                    JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTAR");
                }
                //listaGastoC = modGastoC.listarGastoComun();
                //listaGastoC = modGastoC.listarGastoComun(2);
                llenarTablaGastos(vista.jTable, "Pendiente");
                crearCbxFondo();
            }
        }
        if (e.getSource() == vista.btnMostrar) {
            catCuenPro.btnNuevo.setVisible(false);
            Llenartabla(catCuenPro.tabla, 1);
            this.catCuenPro.setVisible(true);
        }
        if (e.getSource() == vista.btnPagos) {
            catPagos.btnNuevo.setVisible(false);
            llenarTablaPagos(catPagos.tabla);
            this.catPagos.setVisible(true);

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catPagos.txtBuscar) {
            filtro(catPagos.txtBuscar.getText(), catPagos.tabla);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.jTable) {
            //Llenartabla(vista.jTable, 2);
            fila = this.vista.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            //Boolean resultado = true;
            //String msj = "";

            // modelo.cargarProveedor(listaGastoC.get(fila).getId());
            vista.setVisible(true);
            //vista.txtProveedor.setText(modelo.getNom_proveedor());
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

    private void crearCbxCuenta() {
        listaCuenta = modCuenta.listarcuenta();
        vista.cbxCuentaT.addItem("Seleccione...");

        if (listaCuenta != null) {

            for (Cuenta item : listaCuenta) {
                vista.cbxCuentaT.addItem(item.getN_cuenta() + " - " + item.getBanco().getNombre_banco());
            }
        }
    }

    private void crearCbxFondo() {
        listaFondo = modFondo.listar(1);
        vista.cbxMoneda.addItem("Seleccione...");

        if (listaFondo != null) {

            for (Fondo item : listaFondo) {
                vista.cbxMoneda.addItem(Validacion.formatoDecimal(item.getSaldo()) + " - " + item.getTipo());
            }
        }
    }

    private void crearCbxFormaPago() {
        listaFormaPago = modFormaPago.listar();
        vista.cbxFormaP.addItem("Seleccione...");

        if (listaFormaPago != null) {

            for (FormaPago item : listaFormaPago) {
                vista.cbxFormaP.addItem(item.getForma_pago());
            }
        }
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        DefaultTableModel dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getDate());
        return sDate;
    }

    public void llenarTablaGastos(JTable tablaD, String status) {
        listaGasto = modGasto.listar(status);

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Monto");

        if (!status.equals("Pagado")) {
            modeloT.addColumn("Saldo Restante");
        }

        modeloT.addColumn("Moneda");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Tipo");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int num = listaGasto.size();

        for (int i = 0; i < num; i++) {
            int j = 1;
            columna[j++] = listaGasto.get(i).getNombre();
            columna[j++] = listaGasto.get(i).getFecha();
            columna[j++] = listaGasto.get(i).getMonto();

            if (!status.equals("Pagado")) {
                columna[j++] = listaGasto.get(i).getSaldo();
            }

            columna[j++] = listaGasto.get(i).getMoneda();
            columna[j++] = listaGasto.get(i).getEstado();
            columna[j++] = listaGasto.get(i).getTipo();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }

        addCheckBox(0, tablaD);
    }

    public void llenarTablaPagos(JTable tablaD) {
        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);

        // modeloT.addColumn("Selecciona");
        modeloT.addColumn("Referencia");
        modeloT.addColumn("Pago");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Nro Cuenta");
        modeloT.addColumn("Banco");
        modeloT.addColumn("Tipo");

        Object[] columna = new Object[9];

        int num = lista.size();

        for (int i = 0; i < num; i++) {
            columna[0] = lista.get(i).getNum_ref();
            columna[1] = lista.get(i).getForma_pago();
            columna[2] = lista.get(i).getDescripcion();
            columna[3] = lista.get(i).getMonto();
            columna[4] = lista.get(i).getFecha();
            // columna[5] = listaPagar.get(i).getNom_proveedor();
            columna[6] = lista.get(i).getModCuenta().getN_cuenta();
            columna[7] = lista.get(i).getModBanco().getNombre_banco();
            columna[8] = lista.get(i).getModFondo().getTipo();

            modeloT.addRow(columna);
        }
    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    private boolean validar() {
        boolean resultado = true;
        String mensaje = "";

        if (vista.txtProveedor.getText().isEmpty()) {
            resultado = false;
            mensaje += "El campo Proveedor no puede estar vacío\n";
        } else if (vista.txtMonto.getText().isEmpty()) {
            resultado = false;
            mensaje += "El campo Monto no puede estar vacío\n";
        } else if (Float.parseFloat(vista.txtMonto.getText()) <= 0) {
            resultado = false;
            mensaje += "El monto no puede ser 0\n";
        } else if (Float.parseFloat(vista.txtMonto.getText()) > Float.parseFloat(vista.jTable.getValueAt(fila, 4).toString())) {
            resultado = false;
            mensaje += "El monto no puede ser mayor al saldo restante de la factura\n";
        }
        if (vista.txtPariedad.getText().isEmpty()) {
            resultado = false;
            mensaje += "El campo Número de Referencia no puede estar vacío\n";
        }
        if (vista.cbxCuentaT.getSelectedIndex() == 0) {
            resultado = false;
            mensaje += "Debe seleccionar una Cuenta a transferir\n";
        }
        if (vista.txtDescripcion.getText().isEmpty()) {
            resultado = false;
            mensaje += "El campo Descripción no puede estar vacío\n";
        }
        if (vista.jDate.getDate() == null) {
            resultado = false;
            mensaje += "Debe seleccionar una Fecha de pago\n";
        }
        if (vista.cbxMoneda.getSelectedIndex() == 0) {
            resultado = false;
            mensaje += "Debe seleccionar un Fondo\n";
        } else if (Float.parseFloat(vista.txtMonto.getText()) > listaFondo.get(vista.cbxMoneda.getSelectedIndex() - 1).getSaldo()) {
            resultado = false;
            mensaje += "El monto no puede ser mayor al Fondo\n";
        }
        if (resultado == false) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
        return resultado;
    }
}
