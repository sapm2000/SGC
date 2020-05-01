package controlador;

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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Cuenta;
import modelo.Cuenta_Pagar;
import modelo.Fondo;
import modelo.GastoComun;
import vista.catalogoCuentas_procesada;
import vista.cuentasPorPagar;

public class controladorCuenta_Pagar implements ActionListener, WindowListener, ItemListener, KeyListener, MouseListener {

    private Cuenta_Pagar modCuentaP;
    private cuentasPorPagar vistaCuentaP;
    private Fondo modFondo;
    private Cuenta modCuenta;
    private GastoComun modGastoC;
    private catalogoCuentas_procesada catCuenPro;
    ArrayList<GastoComun> listaGastoC;
    ArrayList<Fondo> listaFondo;
    ArrayList<Cuenta> listaCuenta;
    DefaultTableModel dm;

    int fila;

    public controladorCuenta_Pagar(Cuenta_Pagar modCuentaP, cuentasPorPagar vistaCuentaP, Fondo modFondo, Cuenta modCuenta, GastoComun modGastoC, catalogoCuentas_procesada catCuenPro) {

        this.modCuentaP = modCuentaP;
        this.vistaCuentaP = vistaCuentaP;
        this.modFondo = modFondo;
        this.modCuenta = modCuenta;
        this.modGastoC = modGastoC;
        this.catCuenPro = catCuenPro;
        this.vistaCuentaP.btnProcesar.addActionListener(this);
        this.vistaCuentaP.addWindowListener(this);
        this.vistaCuentaP.jTable.addMouseListener(this);
        this.vistaCuentaP.btnMostrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCuentaP.btnProcesar) {

            modCuentaP.setNum_ref(vistaCuentaP.txtReferencia.getText());
            modCuentaP.setForma_pago(vistaCuentaP.cbxFormaP.getSelectedItem().toString());
            int ind = vistaCuentaP.cbxCuentaT.getSelectedIndex() - 1;
            modCuentaP.getModCuenta().setN_cuenta(listaCuenta.get(ind).getN_cuenta());
            modCuentaP.setDescripcion(vistaCuentaP.txtDescripcion.getText());
            java.sql.Date sqlDate = convert(vistaCuentaP.jDate.getDate());
            modCuentaP.setFecha(sqlDate);
            ind = vistaCuentaP.cbxFondo.getSelectedIndex() - 1;
            modCuentaP.getModFondo().setId(listaFondo.get(ind).getId());
            modCuentaP.getModFondo().setSaldo(listaFondo.get(ind).getSaldo());
            //int fila = this.vistaCuentaP.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            float montoTabla;
            float montoX;
            montoX = Float.parseFloat(vistaCuentaP.txtMonto.getText());
            montoTabla = Float.parseFloat(vistaCuentaP.jTable.getValueAt(fila, 3).toString());

            if (montoX <= montoTabla) {
                if (montoX <= modCuentaP.getModFondo().getSaldo()) {
                    modCuentaP.setMonto(Float.parseFloat(vistaCuentaP.txtMonto.getText()));

                    if (modCuentaP.registrarPago(modCuentaP)) {
                        modCuentaP.getModFondo().restarFondo(modCuentaP.getMonto());
                        modGastoC.setId(listaGastoC.get(fila).getId());
                        modGastoC.restarSaldo(modCuentaP.getMonto());
                        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTAR");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El monto ingreso no puede ser mayor al Fondo");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El monto ingreso no puede ser mayor al monto de la factura");
            }

            //listaGastoC = modGastoC.listarGastoComun();
            Llenartabla(vistaCuentaP.jTable, 2);
            listaFondo = modFondo.listar();
            crearCbxFondo(listaFondo);
        }
        if(e.getSource()==vistaCuentaP.btnMostrar){
            catCuenPro.addWindowListener(this);
            this.catCuenPro.setVisible(true);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getSource()==vistaCuentaP) {
            
        listaFondo = modFondo.listar();
        crearCbxFondo(listaFondo);
        listaCuenta = modCuenta.listarcuenta();
        crearCbxCuenta(listaCuenta);
        modGastoC = new GastoComun();
        //listaGastoC = modGastoC.listarGastoComun();
        Llenartabla(vistaCuentaP.jTable, 2);
        //addCheckBox(0, vistaCuentaP.jTable);
        }else if (e.getSource()==catCuenPro) {
        Llenartabla(catCuenPro.jTable1, 1);
            
        }
        

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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        fila = this.vistaCuentaP.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
        Boolean resultado = true;
        String msj = "";

        modCuentaP.cargarProveedor(listaGastoC.get(fila).getId());

        vistaCuentaP.setVisible(true);
        vistaCuentaP.txtProveedor.setText(modCuentaP.getNom_proveedor());
        System.out.println(modCuentaP.getNom_proveedor());

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
        vistaCuentaP.cbxCuentaT.addItem("Seleccione...");

        if (datos != null) {
            for (Cuenta datosX : datos) {
                modCuenta = datosX;
                vistaCuentaP.cbxCuentaT.addItem(modCuenta.getN_cuenta());
            }

        }
    }

    private void crearCbxFondo(ArrayList<Fondo> datos) {
        vistaCuentaP.cbxFondo.removeAllItems();
        vistaCuentaP.cbxFondo.addItem("Seleccione...");

        if (datos != null) {
            for (Fondo datosX : datos) {
                modFondo = datosX;

                vistaCuentaP.cbxFondo.addItem(String.valueOf(modFondo.getSaldo()));
            }

        }
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getDate());
        return sDate;
    }

    public void Llenartabla(JTable tablaD, int status) {
        listaGastoC = modGastoC.listarGastoComun(status);

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        // modeloT.addColumn("Selecciona");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Saldo Restante");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Tipo");
        Object[] columna = new Object[6];

        int num = listaGastoC.size();

        for (int i = 0; i < num; i++) {

            columna[0] = listaGastoC.get(i).getFecha();
            columna[1] = listaGastoC.get(i).getNombre_Concepto();
            columna[2] = listaGastoC.get(i).getMonto();
            columna[3] = listaGastoC.get(i).getSaldo();
            columna[4] = listaGastoC.get(i).getEstado();
            columna[5] = listaGastoC.get(i).getTipo_gasto();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
    }

    /*public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }*/
}
