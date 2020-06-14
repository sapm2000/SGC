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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cuenta;
import modelo.Cuenta_Pagar;
import modelo.Fondo;

import vista.Catalogo;
import vista.VisCuentaPorPagar;

public class CtrlCuentaPagar implements ActionListener, ItemListener, KeyListener, MouseListener {

    private VisCuentaPorPagar vista;
    private Cuenta_Pagar modelo;

    private Catalogo catCuenPro;
   

    private Catalogo catPagos;

    private Fondo modFondo;
    private Cuenta modCuenta;
    
    ArrayList<Fondo> listaFondo;
    ArrayList<Cuenta> listaCuenta;
    ArrayList<Cuenta_Pagar> listaPagar;
    DefaultTableModel dm;

    int fila;

    public CtrlCuentaPagar() {

        catPagos = new Catalogo();
        catCuenPro = new Catalogo();
        catCuenPro.lblTitulo.setText("Cuentas Procesadas");
        catPagos.lblTitulo.setText("Cuentas Pagadas");

        this.modelo = new Cuenta_Pagar();
        this.vista = new VisCuentaPorPagar();
        this.modFondo = new Fondo();
        this.modCuenta = new Cuenta();
       
        this.vista.btnProcesar.addActionListener(this);
        this.vista.jTable.addMouseListener(this);
        this.vista.btnMostrar.addActionListener(this);
        this.vista.btnPagos.addActionListener(this);
        this.catPagos.txtBuscar.addKeyListener(this);
        this.vista.setVisible(true);

        CtrlVentana.cambiarVista(vista);
        vista.cbxFondo.addItemListener(this);
        stylecombo(vista.cbxFondo);
        vista.cbxCuentaT.addItemListener(this);
        stylecombo(vista.cbxCuentaT);
        vista.cbxFormaP.addItemListener(this);
        stylecombo(vista.cbxFormaP);

        listaFondo = modFondo.listar(1);
        crearCbxFondo(listaFondo);
        listaCuenta = modCuenta.listarcuenta();
        crearCbxCuenta(listaCuenta);
       
        //listaGastoC = modGastoC.listarGastoComun();
        Llenartabla(vista.jTable, 2);

        Component[] components = vista.jPanel.getComponents();
        JComponent[] com = {vista.jDate, vista.txtDescripcion, vista.txtMonto, vista.txtProveedor, vista.txtReferencia};
        Validacion.copiar(components);
        Validacion.pegar(com);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnProcesar) {
            System.out.println("poli0");
            if (validar()) {
                System.out.println("poli1");
                modelo.setNum_ref(vista.txtReferencia.getText());
                modelo.setForma_pago(vista.cbxFormaP.getSelectedItem().toString());
                int ind = vista.cbxCuentaT.getSelectedIndex() - 1;
                modelo.getModCuenta().setN_cuenta(listaCuenta.get(ind).getN_cuenta());
                modelo.setDescripcion(vista.txtDescripcion.getText());
                java.sql.Date sqlDate = convert(vista.jDate.getDate());
                modelo.setFecha(sqlDate);
                ind = vista.cbxFondo.getSelectedIndex() - 1;
                modelo.getModFondo().setId(listaFondo.get(ind).getId());
                modelo.getModFondo().setSaldo(listaFondo.get(ind).getSaldo());
                //int fila = this.vistaCuentaP.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
                modelo.setMonto(Float.parseFloat(vista.txtMonto.getText()));

                if (modelo.registrarPago(modelo)) {
                    modelo.getModFondo().restarFondo(modelo.getMonto());
                  //  modGastoC.setId(listaGastoC.get(fila).getId());
                  //  modGastoC.restarSaldo(modelo.getMonto());
                    JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTAR");
                }
                //listaGastoC = modGastoC.listarGastoComun();
//                listaGastoC = modGastoC.listarGastoComun(2);
                Llenartabla(vista.jTable, 2);
                listaFondo = modFondo.listar(1);
                crearCbxFondo(listaFondo);
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
        vista.cbxFormaP.setFocusable(false);
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

//            modelo.cargarProveedor(listaGastoC.get(fila).getId());

            vista.setVisible(true);
          //  vista.txtProveedor.setText(modelo.getNom_proveedor());
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

    private void crearCbxCuenta(ArrayList<Cuenta> datos) {
        vista.cbxCuentaT.addItem("Seleccione...");
        vista.cbxCuentaT.setFocusable(false);

        if (datos != null) {
            for (Cuenta datosX : datos) {
//                vista.cbxCuentaT.addItem(datosX.getN_cuenta() + " - " + datosX.getNombre_banco());
            }

        }
    }

    private void crearCbxFondo(ArrayList<Fondo> datos) {
        vista.cbxFondo.removeAllItems();
        vista.cbxFondo.addItem("Seleccione...");
        vista.cbxFondo.setFocusable(false);
        if (datos != null) {
            for (Fondo datosX : datos) {
                vista.cbxFondo.addItem(Validacion.formatoDecimal(datosX.getSaldo()) + " - " + datosX.getTipo());
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

    public void Llenartabla(JTable tablaD, int status) {
//        listaGastoC = modGastoC.listarGastoComun(status);

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        // modeloT.addColumn("Selecciona");
        modeloT.addColumn("id");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Monto");

        if (status != 1) {
            modeloT.addColumn("Saldo Restante");
        }

        modeloT.addColumn("Estado");
        modeloT.addColumn("Tipo");
        Object[] columna = new Object[modeloT.getColumnCount()];

//        int num = listaGastoC.size();

  /*      System.out.println(modeloT.getColumnCount());
        System.out.println(num);
        for (int i = 0; i < num; i++) {
            int j = 0;
            columna[j++] = listaGastoC.get(i).getId();
            columna[j++] = listaGastoC.get(i).getFecha();
            columna[j++] = listaGastoC.get(i).getNombre_Concepto();
            columna[j++] = listaGastoC.get(i).getMonto();

            if (status != 1) {
                columna[j++] = listaGastoC.get(i).getSaldo();
            }

            columna[j++] = listaGastoC.get(i).getEstado();
            columna[j++] = listaGastoC.get(i).getTipo_gasto();
            modeloT.addRow(columna);
        }*/

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
    }

    public void llenarTablaPagos(JTable tablaD) {
        listaPagar = modelo.listar();

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

        int num = listaPagar.size();

        for (int i = 0; i < num; i++) {
            columna[0] = listaPagar.get(i).getNum_ref();
            columna[1] = listaPagar.get(i).getForma_pago();
            columna[2] = listaPagar.get(i).getDescripcion();
            columna[3] = listaPagar.get(i).getMonto();
            columna[4] = listaPagar.get(i).getFecha();
//            columna[5] = listaPagar.get(i).getNom_proveedor();
            columna[6] = listaPagar.get(i).getModCuenta().getN_cuenta();
            columna[7] = listaPagar.get(i).getModBanco().getNombre_banco();
            columna[8] = listaPagar.get(i).getModFondo().getTipo();
            modeloT.addRow(columna);

        }

    }

    /*public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }*/
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
        if (vista.txtReferencia.getText().isEmpty()) {
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
        if (vista.cbxFondo.getSelectedIndex() == 0) {
            resultado = false;
            mensaje += "Debe seleccionar un Fondo\n";
        } else if (Float.parseFloat(vista.txtMonto.getText()) > listaFondo.get(vista.cbxFondo.getSelectedIndex() - 1).getSaldo()) {
            resultado = false;
            mensaje += "El monto no puede ser mayor al Fondo\n";
        }
        if (resultado == false) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
        return resultado;
    }
    
    public void stylecombo (JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);
        
        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }
}
