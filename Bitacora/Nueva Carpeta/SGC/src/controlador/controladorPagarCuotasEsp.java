package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import modelo.Cuenta;
import modelo.Gasto;
import modelo.Fondo;
import modelo.PagarCuotaEspecial;
import vista.Catalogo;
import vista.pagoCuotasEspeciales;

public class controladorPagarCuotasEsp implements ActionListener, MouseListener, KeyListener, WindowListener {

    private pagoCuotasEspeciales vista;
    private Catalogo vistaCatPagadas;
    private Catalogo vistaCatPagos;
    private PagarCuotaEspecial modPagarCuoE;

    private Gasto modCuotaEsp;
    private Fondo modFondo;
    private Cuenta modCuenta;
    private ArrayList<PagarCuotaEspecial> listaPagos;
    private ArrayList<Gasto> listaCuotaEspecial;
    private ArrayList<Fondo> listaFondo;
    private ArrayList<Cuenta> listaCuenta;

    int fila = 0;

    public controladorPagarCuotasEsp() {
        this.vista = new pagoCuotasEspeciales();
        this.modPagarCuoE = new PagarCuotaEspecial();
        this.modCuotaEsp = new Gasto();
        this.modFondo = new Fondo();
        this.modCuenta = new Cuenta();
        vista.btnProcesar.addActionListener(this);
        vista.btnPagado.addActionListener(this);
        vista.btnPagos.addActionListener(this);
        vista.tabla.addMouseListener(this);
        vista.addWindowListener(this);
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnProcesar) {
            if (validar()) {
                int indice;

                modPagarCuoE.setNumero_ref(vista.txtNumero_Ref.getText());
                modPagarCuoE.setForma_pago(vista.cbxFormaP.getSelectedItem().toString());
                indice = vista.cbxCuenta.getSelectedIndex() - 1;
                modPagarCuoE.setCuenta(listaCuenta.get(indice));
                modPagarCuoE.setDescripcion(vista.txtDescripcion.getText());
                modPagarCuoE.setMonto(Double.parseDouble(vista.txtMonto.getText()));
                modPagarCuoE.setFecha(Validacion.convert(vista.txtFecha.getDate()));
                indice = vista.cbxFondo.getSelectedIndex() - 1;
                modPagarCuoE.setFondo(listaFondo.get(indice));

                if (modPagarCuoE.registrar()) {
                    modPagarCuoE.getFondo().restarFondo(modPagarCuoE.getMonto().floatValue());
                    modCuotaEsp.setId(listaCuotaEspecial.get(fila).getId());
                    modCuotaEsp.restarSaldo(modPagarCuoE.getMonto().floatValue());
                    JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTAR");
                }

                listaCuotaEspecial = modCuotaEsp.listarCuotasEspeciales("Pendiente");
                llenarTablaCuota(vista.tabla, "Pendiente");
                vista.cbxFondo.removeAllItems();
                listaFondo = modFondo.listar(1);
                crearCbxFondo(listaFondo);
                limpiar();
            }
        }
        if (e.getSource() == vista.btnPagado) {
            vistaCatPagadas = new Catalogo();
            vistaCatPagadas.lblTitulo.setText("Cuotas Especiales Pagadas");

            vistaCatPagadas.btnNuevo.setVisible(false);
            vistaCatPagadas.setVisible(true);
            vistaCatPagadas.addWindowListener(this);
        }
        if (e.getSource() == vista.btnPagos) {
            vistaCatPagos = new Catalogo();
            vistaCatPagos.lblTitulo.setText("Pago Cuotas Especiales");

            vistaCatPagos.addWindowListener(this);
            vistaCatPagos.btnNuevo.setVisible(false);
            vistaCatPagos.setVisible(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.tabla) {
            fila = vista.tabla.getSelectedRow();
            modPagarCuoE.getCuo_especial().setId(listaCuotaEspecial.get(fila).getId());

            vista.txtCuota.setText(listaCuotaEspecial.get(fila).getNombre_Concepto());
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
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getSource() == vista) {
            listaFondo = modFondo.listar(1);
            crearCbxFondo(listaFondo);
            listaCuenta = modCuenta.listarcuenta();
            crearCbxCuenta(listaCuenta);
            llenarTablaCuota(vista.tabla, "Pendiente");
        }
        if (e.getSource() == vistaCatPagadas) {
            llenarTablaCuota(vistaCatPagadas.tabla, "Pagado");
        }
        if (e.getSource() == vistaCatPagos) {
            llenarTablaPagos(vistaCatPagos.tabla);
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

    private void crearCbxCuenta(ArrayList<Cuenta> datos) {
        vista.cbxCuenta.addItem("Seleccione...");

        if (datos != null) {
            for (Cuenta datosX : datos) {
                vista.cbxCuenta.addItem(datosX.getN_cuenta() + " - " + datosX.getNombre_banco());
            }

        }
    }

    private void crearCbxFondo(ArrayList<Fondo> datos) {
        vista.cbxFondo.addItem("Seleccione...");

        if (datos != null) {
            for (Fondo datosX : datos) {
                vista.cbxFondo.addItem(Validacion.formatoDecimal(datosX.getSaldo()) + " - " + datosX.getTipo());
            }

        }
    }

    private void limpiar() {
        modPagarCuoE.setId(null);
        vista.txtCuota.setText("");
        vista.txtNumero_Ref.setText("");
        vista.cbxFormaP.setSelectedIndex(0);
        vista.cbxCuenta.setSelectedIndex(0);
        vista.txtDescripcion.setText("");
        vista.txtMonto.setText("");
        vista.txtFecha.setDate(null);
        vista.cbxFondo.setSelectedIndex(0);
    }

    private void llenarTablaCuota(JTable tablaD, String status) {
        listaCuotaEspecial = modCuotaEsp.listarCuotasEspeciales(status);
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Cuota Especial</html>");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("<html>Comienzo de <br> Cobro</html>");
        modeloT.addColumn("<html>Monto <br> Inicial</html>");

        if (status == "Pendiente") {
            modeloT.addColumn("Saldo");
        }

        modeloT.addColumn("Asamblea");
        modeloT.addColumn("Observación");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Estado Pago");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaCuotaEspecial.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listaCuotaEspecial.get(i).getId();
            columna[ind++] = listaCuotaEspecial.get(i).getId_proveedor();
            columna[ind++] = listaCuotaEspecial.get(i).getNombre_Concepto();
            String fecha = String.valueOf(listaCuotaEspecial.get(i).getMes()) + "-" + listaCuotaEspecial.get(i).getAño();
            columna[ind++] = fecha;
            columna[ind++] = Validacion.formato1.format(listaCuotaEspecial.get(i).getMonto());

            if (status == "Pendiente") {
                columna[ind++] = Validacion.formato1.format(listaCuotaEspecial.get(i).getSaldo());
            }

            columna[ind++] = listaCuotaEspecial.get(i).getNombre_asamble();
            columna[ind++] = listaCuotaEspecial.get(i).getObservacion();
            columna[ind++] = listaCuotaEspecial.get(i).getEstado();
            columna[ind++] = listaCuotaEspecial.get(i).getPagado();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    private void llenarTablaPagos(JTable tablaD) {
        listaPagos = modPagarCuoE.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("N° Referencia");
        modeloT.addColumn("Forma de Pago");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Cuenta");
        modeloT.addColumn("Fondo");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Estado");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaCuotaEspecial.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listaPagos.get(i).getNumero_ref();
            columna[ind++] = listaPagos.get(i).getForma_pago();
            columna[ind++] = Validacion.formato1.format(listaPagos.get(i).getMonto());
            columna[ind++] = listaPagos.get(i).getFecha().toString();
            //String fecha = String.valueOf(listaPagos.get(i).getMes()) + "-" + listaPagos.get(i).getAño();
            //columna[ind++] = fecha;
            columna[ind++] = listaPagos.get(i).getDescripcion();

            char[] n_cuenta = listaPagos.get(i).getCuenta().getN_cuenta().toCharArray();

            for (int j = 4; j < n_cuenta.length - 4; j++) {
                n_cuenta[j] = '*';

            }

            listaPagos.get(i).getCuenta().setN_cuenta(String.valueOf(n_cuenta));

            columna[ind++] = listaPagos.get(i).getBanco().getNombre_banco() + " " + listaPagos.get(i).getCuenta().getN_cuenta();
            columna[ind++] = listaPagos.get(i).getFondo().getTipo();
            columna[ind++] = listaPagos.get(i).getProveedor().getNombre();
            columna[ind++] = listaPagos.get(i).getCuo_especial().getPagado();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    private boolean validar() {
        boolean resultado = true;
        String mensaje = "";

        if (vista.txtCuota.getText().isEmpty()) {
            mensaje += "Debe seleccionar una Cuota Especial a pagar\n";
            resultado = false;
        } else if (vista.txtMonto.getText().isEmpty()) {
            mensaje += "El campo Monto no puede estar vacío\n";
            resultado = false;
        } else if (Float.parseFloat(vista.txtMonto.getText()) <= 0) {
            resultado = false;
            mensaje += "El monto no puede ser 0\n";
        } else if (Float.parseFloat(vista.txtMonto.getText()) > listaCuotaEspecial.get(fila).getSaldo().floatValue()) {
            resultado = false;
            mensaje += "El monto no puede ser mayor al saldo restante de la factura\n";
        }
        if (vista.txtNumero_Ref.getText().isEmpty()) {
            mensaje += "El campo Número de Referencia no puede estar vacío\n";
            resultado = false;
        }
        if (vista.cbxCuenta.getSelectedIndex() == 0) {
            mensaje += "Debe seleccionar una Cuenta a transferir\n";
            resultado = false;
        }
        if (vista.txtDescripcion.getText().isEmpty()) {
            mensaje += "El campo Descripción no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtFecha.getDate() == null) {
            mensaje += "Debe seleccionar una fecha\n";
            resultado = false;
        }
        if (vista.cbxFondo.getSelectedIndex() == 0) {
            mensaje += "Debe seleccionar un Fondo\n";
            resultado = false;
        } else if (Float.parseFloat(vista.txtMonto.getText()) > listaFondo.get(vista.cbxFondo.getSelectedIndex() - 1).getSaldo()) {
            resultado = false;
            mensaje += "El monto no puede ser mayor al Fondo\n";
        }
        if (resultado == false) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }

        return resultado;
    }

}
