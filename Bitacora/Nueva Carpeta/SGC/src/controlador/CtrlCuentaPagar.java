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
import javax.swing.table.TableRowSorter;
import modelo.Cuenta;
import modelo.CuentaPagar;
import modelo.Fondo;
import modelo.FormaPago;
import modelo.Gasto;
import vista.Catalogo;
import vista.VisCuentaPorPagar;

public class CtrlCuentaPagar implements ActionListener, ItemListener, KeyListener, MouseListener {

    private VisCuentaPorPagar vista;
    private CuentaPagar modelo;
    private ArrayList<CuentaPagar> lista;

    private Catalogo catPagos;
    private Catalogo catGastosProcesados;

    private FormaPago modFormaPago;
    private ArrayList<FormaPago> listaFormaPago;

    private Cuenta modCuenta;
    private ArrayList<Cuenta> listaCuenta;

    private Gasto modGasto;
    private ArrayList<Gasto> listaGasto;

    private Fondo modFondo;
    private ArrayList<Fondo> listaFondo;
    DefaultTableModel dm;
    int fila;

    public CtrlCuentaPagar() {

        this.vista = new VisCuentaPorPagar();
        this.modelo = new CuentaPagar();

        this.modGasto = new Gasto();
        this.modFormaPago = new FormaPago();
        this.modFondo = new Fondo();
        this.modCuenta = new Cuenta();

        this.catPagos = new Catalogo();
        this.catGastosProcesados = new Catalogo();

        this.vista.panelReferencia.setVisible(false);
        this.vista.panelPariedad.setVisible(false);

        this.catPagos.lblTitulo.setText("Gastos pagados");

        this.catGastosProcesados.lblTitulo.setText("Cuentas Procesadas");

        this.vista.btnProcesar.addActionListener(this);
        this.vista.btnMostrarProcesados.addActionListener(this);
        this.vista.btnPagos.addActionListener(this);
        this.vista.cbxFormaPago.addItemListener(this);
        this.vista.cbxMoneda.addItemListener(this);
        this.vista.cbxFondo.addItemListener(this);
        this.vista.tablaGastos.addMouseListener(this);
        this.vista.txtPariedad.addKeyListener(this);
        this.vista.setVisible(true);

        this.catPagos.txtBuscar.addKeyListener(this);

        crearCbxFormaPago();
        crearCbxCuenta();
        llenarTablaGastos(vista.tablaGastos, "Pendiente");

        Component[] components = vista.jPanel.getComponents();
        JComponent[] com = {vista.fecha, vista.txtDescripcion, vista.txtMonto, vista.txtGasto, vista.txtPariedad};
        Validacion.copiar(components);
        Validacion.pegar(com);

        CtrlVentana.cambiarVista(vista);
        vista.cbxCuenta.addItemListener(this);
        stylecombo(vista.cbxCuenta);
        vista.cbxFondo.addItemListener(this);
        stylecombo(vista.cbxFondo);
        vista.cbxFormaPago.addItemListener(this);
        stylecombo(vista.cbxFormaPago);
        vista.cbxMoneda.addItemListener(this);
        stylecombo(vista.cbxMoneda);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnProcesar) {

            if (validar()) {
                int ind;
                Double pariedad;
                Double montoARestar = 0.;
                modelo = new CuentaPagar();

                // Se guardan en el modelo los datos básicos
                modelo.setGasto(modGasto);
                ind = vista.cbxFormaPago.getSelectedIndex() - 1;
                modelo.setFormaPago(listaFormaPago.get(ind));

                if (vista.panelReferencia.isVisible()) {
                    modelo.setReferencia(vista.txtReferencia.getText());
                    ind = vista.cbxCuenta.getSelectedIndex() - 1;
                    modelo.setCuenta(listaCuenta.get(ind));
                }

                modelo.setDescripcion(vista.txtDescripcion.getText());
                modelo.setMonto(Double.parseDouble(vista.txtMonto.getText()));
                modelo.setFecha(new java.sql.Date(vista.fecha.getDate().getTime()));
                ind = vista.cbxFondo.getSelectedIndex() - 1;
                modelo.setFondo(listaFondo.get(ind));
                modelo.setMoneda(vista.cbxMoneda.getSelectedItem().toString());

                if (!modelo.getMoneda().equals(modGasto.getMoneda())) {
                    pariedad = Double.parseDouble(vista.txtPariedad.getText());
                    modelo.setTasaCambio(pariedad);
                    montoARestar = cambioMoneda(modelo.getMonto(), modelo.getMoneda(), pariedad);

                } else {
                    montoARestar = modelo.getMonto();
                }

                if (modelo.registrar()) {

                    if (modelo.getFondo().restarFondo(modelo.getMonto().floatValue())) {

                        if (modGasto.restarSaldo(montoARestar)) {

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

                            JOptionPane.showMessageDialog(null, "Pago registrado ", "Pagado", JOptionPane.INFORMATION_MESSAGE, p);

                        } else {
                            System.out.println("Error al restar el saldo del gasto");
                        }

                    } else {
                        System.out.println("Error al restar el saldo del fondo");
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

                    JOptionPane.showMessageDialog(null, "Error al registrar el pago ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                }

                llenarTablaGastos(vista.tablaGastos, "Pendiente");
                vista.cbxFondo.removeAllItems();
                crearCbxFondo();
            }
        }
        if (e.getSource() == vista.btnPagos) {
            catPagos.btnNuevo.setVisible(false);
            llenarTablaPagos(catPagos.tabla);
            CtrlVentana.cambiarVista(catPagos);
        }

        if (e.getSource() == vista.btnMostrarProcesados) {
            catGastosProcesados.btnNuevo.setVisible(false);
            llenarTablaGastos(catGastosProcesados.tabla, "Pendiente");
            CtrlVentana.cambiarVista(catGastosProcesados);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxCuenta.setFocusable(false);
        vista.cbxFondo.setFocusable(false);
        vista.cbxFormaPago.setFocusable(false);
        vista.cbxMoneda.setFocusable(false);

        if (e.getSource() == vista.cbxFormaPago) {

            if (vista.cbxFormaPago.getSelectedIndex() != 0) {

                if (!vista.cbxFormaPago.getSelectedItem().toString().equals("Efectivo")) {
                    vista.panelReferencia.setVisible(true);

                } else {
                    vista.panelReferencia.setVisible(false);
                }

            } else {
                vista.panelReferencia.setVisible(false);
            }

        } else if (e.getSource() == vista.cbxMoneda) {

            if (vista.cbxMoneda.getSelectedIndex() != 0) {
                String moneda;

                moneda = vista.cbxMoneda.getSelectedItem().toString();
                modFondo.setMoneda(moneda);
                vista.cbxFondo.removeAllItems();
                crearCbxFondo();
                vista.cbxFondo.setSelectedIndex(0);
                vista.cbxFondo.setEnabled(true);

                if (!vista.txtGasto.getText().isEmpty()) {

                    if (moneda.equals(modGasto.getMoneda()) || vista.cbxMoneda.getSelectedIndex() == 0) {
                        vista.panelPariedad.setVisible(false);

                    } else {
                        vista.panelPariedad.setVisible(true);
                    }
                }

            } else {
                vista.panelPariedad.setVisible(false);
                vista.cbxFondo.setEnabled(false);
                vista.cbxFondo.addItem("Seleccione una moneda");
                vista.cbxFondo.setSelectedItem("Seleccione una moneda");
            }

        } else if (e.getSource() == vista.cbxFondo) {

            if (vista.cbxFondo.getSelectedIndex() == 0) {
                vista.txtMonto.setEnabled(false);
                vista.txtMonto.setText("Seleccione un fondo");

            } else {
                vista.txtMonto.setText(null);
                vista.txtMonto.setEnabled(true);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == vista.tablaGastos) {
            fila = this.vista.tablaGastos.getSelectedRow(); // primero, obtengo la fila seleccionada
            modGasto = listaGasto.get(fila);
            vista.txtGasto.setText(modGasto.getNombre());

            if (vista.cbxMoneda.getSelectedIndex() != 0) {

                if (vista.cbxMoneda.getSelectedItem().toString().equals(modGasto.getMoneda())) {
                    vista.panelPariedad.setVisible(false);

                } else {
                    vista.panelPariedad.setVisible(true);
                }

            } else {
                vista.panelPariedad.setVisible(false);
            }

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
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == vista.txtPariedad) {

            if (vista.txtPariedad.getText().isEmpty()) {
                vista.txtMonto.setEnabled(false);
                vista.txtMonto.setText(null);

            } else if (vista.cbxFondo.getSelectedIndex() == 0) {
                vista.txtMonto.setEnabled(true);
            }
        }

        if (e.getSource() == catPagos.txtBuscar) {
            filtro(catPagos.txtBuscar.getText(), catPagos.tabla);
        }
    }

    private Double cambioMoneda(Double monto, String moneda, Double pariedad) {
        Double montoNuevo = 0.;

        if (moneda.equals("Bolívar")) {
            montoNuevo = monto / pariedad;

        } else if (moneda.equals("Dólar")) {
            montoNuevo = monto * pariedad;
        }

        return montoNuevo;
    }

    private void crearCbxCuenta() {
        listaCuenta = modCuenta.listarcuenta();
        vista.cbxCuenta.addItem("Seleccione...");

        if (listaCuenta != null) {

            for (Cuenta item : listaCuenta) {
                vista.cbxCuenta.addItem(Validacion.formatoNumeroCuenta(item.getN_cuenta() + " - " + item.getBanco().getNombre_banco()));
            }
        }
    }

    private void crearCbxFondo() {
        String simbolo = "";

        listaFondo = modFondo.listar(1);

        if (modFondo.getMoneda().equals("Bolívar")) {
            simbolo = "Bs.";

        } else if (modFondo.getMoneda().equals("Dólar")) {
            simbolo = "$";
        }

        vista.cbxFondo.addItem("Seleccione...");

        if (listaFondo != null) {

            for (Fondo item : listaFondo) {
                vista.cbxFondo.addItem(Validacion.formatoDecimal(item.getSaldo()) + simbolo + " - " + item.getTipo());
            }
        }
    }

    private void crearCbxFormaPago() {
        listaFormaPago = modFormaPago.listar();
        vista.cbxFormaPago.addItem("Seleccione...");

        if (listaFormaPago != null) {

            for (FormaPago item : listaFormaPago) {
                vista.cbxFormaPago.addItem(item.getForma_pago());
            }
        }
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
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

        modeloT.addColumn("Proveedor");
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
            int j = 0;
            columna[j++] = listaGasto.get(i).getProveedor().getNombre();
            columna[j++] = listaGasto.get(i).getNombre();
            columna[j++] = listaGasto.get(i).getMes() + " - " + listaGasto.get(i).getAnio();
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
    }

    public void llenarTablaPagos(JTable tablaD) {
        int ind;
        int numeroRegistros;

        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);

        modeloT.addColumn("Referencia");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Moneda");
        modeloT.addColumn("Tasa de Cambio");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Gasto");
        modeloT.addColumn("Nro Cuenta");
        modeloT.addColumn("Banco");
        modeloT.addColumn("Tipo de Fondo");
        modeloT.addColumn("Forma de Pago");

        Object[] columna = new Object[modeloT.getColumnCount()];

        numeroRegistros = lista.size();

        for (int i = 0; i < numeroRegistros; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getReferencia();
            columna[ind++] = lista.get(i).getDescripcion();
            columna[ind++] = lista.get(i).getMonto();
            columna[ind++] = lista.get(i).getMoneda();
            columna[ind++] = lista.get(i).getTasaCambio();
            columna[ind++] = lista.get(i).getFecha();
            columna[ind++] = lista.get(i).getGasto().getNombre();
            columna[ind++] = lista.get(i).getCuenta().getN_cuenta();
            columna[ind++] = lista.get(i).getCuenta().getBanco().getNombre_banco();
            columna[ind++] = lista.get(i).getFondo().getTipo();
            columna[ind++] = lista.get(i).getFormaPago().getForma_pago();

            modeloT.addRow(columna);
        }
    }

    private boolean validar() {
        boolean resultado = true;
        String mensaje = "";
        boolean gastoEstaSeleccionado = false;
        boolean formaPagoEstaSeleccionado = false;
        boolean monedaEstaSeleccionado = false;
        String moneda = "";
        boolean fondoEstaSeleccionado = false;
        Double monto = 0.;
        Double montoNuevo = 0.;
        Double saldoFactura = 0.;
        Double saldoFondo = 0.;

        if (vista.txtGasto.getText().isEmpty()) {
            resultado = false;
            mensaje += "Debe seleccionar un Gasto \n";

        } else {
            gastoEstaSeleccionado = true;
            saldoFactura = Double.parseDouble(vista.tablaGastos.getValueAt(fila, 4).toString());
        }

        if (vista.txtDescripcion.getText().isEmpty()) {
            resultado = false;
            mensaje += "El campo Descripción no puede estar vacío \n";
        }

        if (vista.cbxFormaPago.getSelectedIndex() == 0) {
            resultado = false;
            mensaje += "Debe seleccionar una Forma de Pago \n";

        } else {
            formaPagoEstaSeleccionado = true;
        }

        if (vista.fecha.getDate() == null) {
            resultado = false;
            mensaje += "Debe seleccionar una Fecha de pago \n";
        }

        if (vista.cbxMoneda.getSelectedIndex() == 0) {
            resultado = false;
            mensaje += "Debe seleccionar una Moneda a pagar \n";

        } else {
            monedaEstaSeleccionado = true;
            moneda = vista.cbxMoneda.getSelectedItem().toString();

            if (vista.cbxFondo.getSelectedIndex() == 0) {
                resultado = false;
                mensaje += "Debe seleccionar un Fondo \n";

            } else {
                saldoFondo = listaFondo.get(vista.cbxFondo.getSelectedIndex() - 1).getSaldo();
                fondoEstaSeleccionado = true;
            }
        }

        if (fondoEstaSeleccionado && vista.txtMonto.getText().isEmpty()) {
            resultado = false;
            mensaje += "El campo Monto no puede estar vacío \n";

        } else if (fondoEstaSeleccionado) {
            monto = Double.parseDouble(vista.txtMonto.getText());

            if (monto <= 0) {
                resultado = false;
                mensaje += "El monto debe ser mayor que 0 \n";

            } else {
                if (moneda.equals(modGasto.getMoneda())) {
                    if (gastoEstaSeleccionado && monto > saldoFactura) {
                        resultado = false;
                        mensaje += "El monto no puede ser mayor al saldo restante de la factura \n";
                    }

                } else {
                    montoNuevo = cambioMoneda(monto, vista.cbxMoneda.getSelectedItem().toString(), Double.parseDouble(vista.txtPariedad.getText()));

                    if (gastoEstaSeleccionado && montoNuevo > saldoFactura) {
                        resultado = false;
                        mensaje += "El monto no puede ser mayor al saldo restante de la factura \n";
                    }
                }

                if (gastoEstaSeleccionado && monto > saldoFondo) {
                    resultado = false;
                    mensaje += "El monto no puede ser mayor al Fondo \n";
                }
            }
        }

        if (formaPagoEstaSeleccionado && !vista.cbxFormaPago.getSelectedItem().toString().equals("Efectivo")) {

            if (vista.txtReferencia.getText().isEmpty()) {
                resultado = false;
                mensaje += "El campo Número de Referencia no puede estar vacío \n";
            }

            if (vista.cbxCuenta.getSelectedIndex() == 0) {
                resultado = false;
                mensaje += "Debe seleccionar una Cuenta a transferir \n";
            }
        }

        if (monedaEstaSeleccionado) {

            if (!vista.cbxMoneda.getSelectedItem().toString().equals(modGasto.getMoneda()) && vista.txtPariedad.getText().isEmpty()) {
                resultado = false;
                mensaje += "El campo Tasa de cambio no puede estar vacío \n";
            }
        }

        if (resultado == false) {

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

            JOptionPane.showMessageDialog(vista, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }

}
