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
import static java.lang.String.valueOf;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.CerrarMes;
import modelo.Cuenta;
import modelo.CuentasPorCobrar;
import modelo.Fondo;
import modelo.FormaPago;
import modelo.Unidades;
import vista.VisCuentaPorCobrar;

/**
 *
 * @author rma
 */
public class CtrlCuentaPorCobrar implements ActionListener, ItemListener, KeyListener {

    private VisCuentaPorCobrar vista;
    private CuentasPorCobrar modcuen;
    private Unidades moduni;
    private Fondo modfon;
    private Cuenta modcu;
    private CerrarMes modc;
    private FormaPago modfor;
    ArrayList<CerrarMes> listaCierremes;
    ArrayList<CerrarMes> listaDominante;
    ArrayList<Unidades> listaunidades;
    ArrayList<Fondo> listafondo;
    ArrayList<Cuenta> listaCuenta;
    ArrayList<FormaPago> listaformapago;
    int x;

    public CtrlCuentaPorCobrar() {
        this.vista = new VisCuentaPorCobrar();
        this.modcuen = new CuentasPorCobrar();
        this.moduni = new Unidades();
        this.modfon = new Fondo();
        this.modcu = new Cuenta();
        this.modc = new CerrarMes();
        this.modfor = new FormaPago();
        String hola = JOptionPane.showInputDialog("ingrese la paridad a trabajar");
        double x = Double.parseDouble(hola);
        modc.setParidad(x);

        vista.jComboUnidad.addItemListener(this);
        vista.btnGuardar.addActionListener(this);
        vista.txtMonto.addKeyListener(this);
        vista.txtDescripcion.addKeyListener(this);
        vista.txtReferencia.addKeyListener(this);
        vista.cbxMoneda.addItemListener(this);
        CtrlVentana.cambiarVista(vista);

        vista.jComboFondo.removeAllItems();
        modfon.setMoneda("Bolívar");
        listafondo = modfon.listar(2);
        crearCbxFondo(listafondo);

        listaunidades = moduni.listar();
        crearCbxUnidad(listaunidades);

        listaCuenta = modcu.listarcuenta();
        crearCbxCuenta(listaCuenta);
        
        listaformapago = modfor.listar();
        crearCbxFormadePago(listaformapago);

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtReferencia, vista.txtDescripcion, vista.txtMonto
        };
        Validacion.copiar(components);
        Validacion.pegar(com);

    }

    public void Llenartabla(JTable tablaD, ArrayList listadominante) {

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;

                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = false;
                }
                if (column == 3) {
                    resu = false;
                }
                if (column == 4) {
                    resu = false;
                }
                if (column == 5) {
                    resu = false;
                }
                if (column == 6) {
                    resu = false;
                }
                if (column == 7) {
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Monto en $");
        modeloT.addColumn("Monto en BsS");
        modeloT.addColumn("Saldo Restante $");
        modeloT.addColumn("Saldo Restante BsS");
        modeloT.addColumn("Mantener valor en");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[8];

        for (int s = 0; s < listaDominante.size(); s++) {
            modc.setMes_cierre(listaDominante.get(s).getMes_cierre());
            modc.setAño_cierre(listaDominante.get(s).getAño_cierre());
            modc.uni.setId(listaDominante.get(s).uni.getId());
            listaCierremes = modc.listarpagospendientes(listaDominante.get(s).getMoneda_dominante());
            int numRegistro = listaCierremes.size();

            for (int i = 0; i < numRegistro; i++) {

                columna[0] = listaCierremes.get(i).getMes_cierre();
                columna[1] = listaCierremes.get(i).getAño_cierre();
                columna[2] = listaCierremes.get(i).getMonto_bolivar();
                columna[3] = listaCierremes.get(i).getMonto_dolar();
                columna[4] = listaCierremes.get(i).getSaldo_restante_dolar();
                columna[5] = listaCierremes.get(i).getSaldo_restante_bs();
                columna[6] = listaCierremes.get(i).getMoneda_dominante();

                modeloT.addRow(columna);

            }
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
    }

    public void Llenartablapagados(JTable tablaD) {

        listaCierremes = modc.listarpagospagados();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;

                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = false;
                }
                if (column == 3) {
                    resu = false;
                }
                if (column == 4) {
                    resu = false;
                }
                if (column == 5) {
                    resu = false;
                }
                if (column == 6) {
                    resu = false;
                }
                if (column == 7) {
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de<br> Recibo</html>");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Alícuota");
        modeloT.addColumn("Monto");
        modeloT.addColumn("<html>Saldo <br> Restante</html>");
        modeloT.addColumn("Estado");

        Object[] columna = new Object[7];

        int numRegistro = listaCierremes.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCierremes.get(i).getId_gasto();
            columna[1] = listaCierremes.get(i).getMes_cierre();
            columna[2] = listaCierremes.get(i).getAño_cierre();
            double var4 = listaCierremes.get(i).getAlicuota() * 100;
            String var5 = var4 + "%";
            columna[3] = var5;
            columna[4] = Validacion.formato1.format(listaCierremes.get(i).getMonto());
            columna[5] = Validacion.formato1.format(listaCierremes.get(i).getSaldo_restante());
            columna[6] = listaCierremes.get(i).getEstado();

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

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                listaunidades = moduni.listar();
                listafondo = modfon.listar(2);
                listaCuenta = modcu.listarcuenta();

                int j = 0;
                modcuen.setDescripcion(vista.txtDescripcion.getText());
                modcuen.setForma_pago(vista.jComboForma.getSelectedItem().toString());
                int ind2 = vista.jComboCuenta.getSelectedIndex() - 1;

                for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                    if (valueOf(vista.jTable1.getValueAt(i, 7)) == "true") {

                        j = j + 1;

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {
                    if (ind2 == -1) {
                        JOptionPane.showMessageDialog(null, "seleccione una cuenta");
                    } else {
                        modcuen.setId_cuenta(listaCuenta.get(ind2).getN_cuenta());

                        int ind1 = vista.jComboFondo.getSelectedIndex() - 1;
                        if (ind1 == -1) {
                            JOptionPane.showMessageDialog(null, "seleccione el fondo a depositar");
                        } else {

                            modcuen.setId_fondo(listafondo.get(ind1).getId());

                            int ind = vista.jComboUnidad.getSelectedIndex() - 1;

                            if (ind == -1) {
                                JOptionPane.showMessageDialog(null, "seleccione el numero de la unidad");
                            } else {
                                modcuen.setId_unidad(listaunidades.get(ind).getId());
                                modcuen.setMonto(Double.parseDouble(vista.txtMonto.getText()));
                                modcuen.setReferencia(vista.txtReferencia.getText());
                                java.sql.Date sqlDate = convert(vista.jDateChooser1.getDate());
                                modcuen.setFecha(sqlDate);
                                double monto = modcuen.getMonto();
                                double total = 0;
                                for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                                    if (valueOf(vista.jTable1.getValueAt(i, 7)) == "true") {

                                        double dato = Double.parseDouble(String.valueOf(this.vista.jTable1.getValueAt(i, 5)));
                                        total = total + dato;

                                    }
                                }

                                if (modcuen.getMonto() > total) {
                                    JOptionPane.showMessageDialog(null, "No puede ingresar mas dinero de lo que debe");
                                } else {

                                    if (modcuen.registrarCobro(modcuen)) {
                                        double var4 = listafondo.get(ind1).getSaldo() + modcuen.getMonto();
                                        modfon.setId(listafondo.get(ind1).getId());
                                        modfon.setSaldo(var4);
                                        modfon.fondear(modfon);

                                        JOptionPane.showMessageDialog(null, "registro guardado");
                                        modc.buscId(modc);
                                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                                            if (valueOf(vista.jTable1.getValueAt(i, 7)) == "true") {

                                                double dato = Double.parseDouble(String.valueOf(this.vista.jTable1.getValueAt(i, 5)));
                                                double parte = dato - monto;
                                                double va1 = dato - parte;
                                                if (parte <= 0) {
                                                    parte = 0;
                                                    va1 = dato;
                                                }

                                                if (monto <= 0) {

                                                } else {
                                                    if (parte == 0) {
                                                        modc.setEstado("Pagado");

                                                    } else {
                                                        modc.setEstado("Pendiente de Pago");
                                                    }
                                                    modc.setSaldo_restante(parte);

                                                    modc.setId_gasto(Integer.parseInt(String.valueOf(this.vista.jTable1.getValueAt(i, 0))));
                                                    modc.actualizartotal(modc);
                                                    modc.setSaldo_restante(va1);
                                                    modc.guardarpuentepagos(modc);
                                                    modc.setSaldo_restante(0);
                                                }
                                                monto = monto - dato;

                                            }
                                        }

                                        modcuen.setId_unidad(listaunidades.get(ind).getId());
                                        Llenartabla(vista.jTable1);
                                        addCheckBox(7, vista.jTable1);
                                        Llenartablapagados(vista.jTable2);
                                        vista.jComboFondo.removeAllItems();
                                        listafondo = modfon.listar(2);
                                        crearCbxFondo(listafondo);

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private void crearCbxUnidad(ArrayList<Unidades> datos) {
        vista.jComboUnidad.addItem("Seleccione el numero de la unidad");

        if (datos != null) {
            for (Unidades datosX : datos) {
                moduni = datosX;
                vista.jComboUnidad.addItem(moduni.getN_unidad());
            }

        }
    }
    
    private void crearCbxFormadePago(ArrayList<FormaPago> datos) {
        

        if (datos != null) {
            for (FormaPago datosX : datos) {
                modfor = datosX;
                vista.jComboForma.addItem(modfor.getForma_pago());
            }

        }
    }

    private void crearCbxFondo(ArrayList<Fondo> datos) {
        vista.jComboFondo.addItem("Seleccione el fondo a depositar");

        if (datos != null) {
            for (Fondo datosX : datos) {
                modfon = datosX;
                vista.jComboFondo.addItem(modfon.getTipo() + " " + modfon.getSaldo()+ " " +modfon.getMoneda());
            }

        }
    }

    private void crearCbxCuenta(ArrayList<Cuenta> datos) {
        vista.jComboCuenta.addItem("Seleccione la cuenta depositada");
        vista.jComboCuenta.addItem("Otros");

        if (datos != null) {
            for (Cuenta datosX : datos) {
                modcu = datosX;
                vista.jComboCuenta.addItem(modcu.getN_cuenta() + " " + modcu.getBeneficiario().getCedula() + " " + modcu.getBeneficiario());
            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == vista.jComboUnidad) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                listaunidades = moduni.listar();
                int ind = vista.jComboUnidad.getSelectedIndex() - 1;
                if (ind == -1) {

                } else {
                    modc.uni.setId(listaunidades.get(ind).getId());

                    listaDominante = modc.listarDominantes();
                    x = listaDominante.size();

                    Llenartabla(vista.jTable1, listaDominante);
                    addCheckBox(7, vista.jTable1);
                    Llenartablapagados(vista.jTable2);
                }
            }
        }
        if (e.getSource() == vista.cbxMoneda) {
            vista.jComboFondo.removeAllItems();
            modfon.setMoneda(vista.cbxMoneda.getSelectedItem().toString());
            listafondo = modfon.listar(2);
            crearCbxFondo(listafondo);
        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtMonto.getText().isEmpty()) {

            msj += "El campo monto no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txtReferencia.getText().isEmpty()) {

            msj += "El campo de número de referencia no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.txtReferencia) {

            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtReferencia.getText(), 50);
        }

        if (e.getSource() == vista.txtDescripcion) {

            Validacion.limite(e, vista.txtDescripcion.getText(), 500);
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

    }

}
