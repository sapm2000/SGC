/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static java.lang.String.valueOf;
import java.util.ArrayList;
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
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.cuentasPorCobrar;

/**
 *
 * @author rma
 */
public class controladorCuentasPorCobrar implements ActionListener, WindowListener, ItemListener {

    private cuentasPorCobrar cuenco;
    private CuentasPorCobrar modcuen;
    private Unidades moduni;
    private Fondo modfon;
    private Cuenta modcu;
    private CerrarMes modc;
    private PantallaPrincipal1 panta1;
    ArrayList<CerrarMes> listaCierremes;

    public controladorCuentasPorCobrar(cuentasPorCobrar cuenco, CuentasPorCobrar modcuen, Unidades moduni, Fondo modfon, Cuenta modcu, CerrarMes modc, PantallaPrincipal1 panta1) {
        this.cuenco = cuenco;
        this.modcuen = modcuen;
        this.moduni = moduni;
        this.modfon = modfon;
        this.modcu = modcu;
        this.modc = modc;
        this.panta1 = panta1;
        cuenco.addWindowListener(this);
        cuenco.jComboUnidad.addItemListener(this);
        cuenco.btnGuardar.addActionListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        listaCierremes = modc.listarpagospendientes();
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

        modeloT.addColumn("Nº de Recibo");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Alícuota");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Saldo Restante");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[8];

        int numRegistro = listaCierremes.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCierremes.get(i).getId_gasto();
            columna[1] = listaCierremes.get(i).getMes_cierre();
            columna[2] = listaCierremes.get(i).getAño_cierre();
            double var4 = listaCierremes.get(i).getAlicuota() * 100;
            String var5 = var4 + "%";
            columna[3] = var5;
            columna[4] = listaCierremes.get(i).getMonto();
            columna[5] = listaCierremes.get(i).getSaldo_restante();
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

        modeloT.addColumn("Nº de Recibo");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Alícuota");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Saldo restante");
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
            columna[4] = listaCierremes.get(i).getMonto();
            columna[5] = listaCierremes.get(i).getSaldo_restante();
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
        if (e.getSource() == cuenco.btnGuardar) {
            if (validar()) {
                modcuen.setDescripcion(cuenco.txtDescripcion.getText());
                modcuen.setForma_pago(cuenco.jComboForma.getSelectedItem().toString());
                modcuen.setId_cuenta(cuenco.jComboCuenta.getSelectedItem().toString());
                if (modcuen.getId_cuenta().equals("Seleccione la cuenta depositada")) {
                    JOptionPane.showMessageDialog(null, "seleccione una cuenta");
                } else {
                    modfon.setTipo(cuenco.jComboFondo.getSelectedItem().toString());
                    if (modfon.getTipo().equals("Seleccione el fondo a depositar")) {
                        JOptionPane.showMessageDialog(null, "seleccione el fondo a depositar");
                    } else {
                        modfon.setId_condominio(panta1.rif.getText());
                        modfon.buscar1(modfon);
                        modcuen.setId_fondo(modfon.getId());
                        modcuen.setId_unidad(cuenco.jComboUnidad.getSelectedItem().toString());
                        if (modcuen.getId_unidad().equals("Seleccione el numero de la unidad")) {
                            JOptionPane.showMessageDialog(null, "seleccione el numero de la unidad");
                        } else {
                            modcuen.setMonto(Double.parseDouble(cuenco.txtMonto.getText()));
                            modcuen.setReferencia(cuenco.txtReferencia.getText());
                            java.sql.Date sqlDate = convert(cuenco.jDateChooser1.getDate());
                            modcuen.setFecha(sqlDate);
                            double monto = modcuen.getMonto();
                            double total = 0;
                            for (int i = 0; i < cuenco.jTable1.getRowCount(); i++) {
                                if (valueOf(cuenco.jTable1.getValueAt(i, 7)) == "true") {

                                    double dato = Double.parseDouble(String.valueOf(this.cuenco.jTable1.getValueAt(i, 5)));
                                    total = total + dato;

                                }
                            }

                            if (modcuen.getMonto() > total) {
                                JOptionPane.showMessageDialog(null, "No puede ingresar mas dinero de lo que debe");
                            } else {
                                if (modcuen.registrarCobro(modcuen)) {
                                    double var4 = modfon.getSaldo() + modcuen.getMonto();
                                    modfon.setSaldo(var4);
                                    modfon.fondear(modfon);

                                    JOptionPane.showMessageDialog(null, "registro guardado");
                                    modc.buscId(modc);
                                    for (int i = 0; i < cuenco.jTable1.getRowCount(); i++) {
                                        if (valueOf(cuenco.jTable1.getValueAt(i, 7)) == "true") {

                                            double dato = Double.parseDouble(String.valueOf(this.cuenco.jTable1.getValueAt(i, 5)));
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
                                                    modc.setEstado("Pendiente de pago");
                                                }
                                                modc.setSaldo_restante(parte);

                                                modc.setId_gasto(Integer.parseInt(String.valueOf(this.cuenco.jTable1.getValueAt(i, 0))));
                                                modc.actualizartotal(modc);
                                                modc.setSaldo_restante(va1);
                                                modc.guardarpuentepagos(modc);
                                                modc.setSaldo_restante(0);
                                            }
                                            monto = monto - dato;

                                        }
                                    }
                                    modc.setId_condominio(panta1.rif.getText());
                                    modc.setId_unidad(cuenco.jComboUnidad.getSelectedItem().toString());
                                    Llenartabla(cuenco.jTable1);
                                    addCheckBox(7, cuenco.jTable1);
                                    Llenartablapagados(cuenco.jTable2);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        moduni.setId_condominio(panta1.rif.getText());
        modcu.setId_condominio(panta1.rif.getText());
        modfon.setId_condominio(panta1.rif.getText());
        moduni.llenar_unidades(cuenco.jComboUnidad);
        modcu.llenar_cuentas(cuenco.jComboCuenta);
        modfon.llenar_fondo(cuenco.jComboFondo);
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
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (cuenco.jComboUnidad.getSelectedItem().toString().equals("Seleccione el numero de la unidad")) {

            } else {

                modc.setId_condominio(panta1.rif.getText());
                modc.setId_unidad(cuenco.jComboUnidad.getSelectedItem().toString());
                Llenartabla(cuenco.jTable1);
                addCheckBox(7, cuenco.jTable1);
                Llenartablapagados(cuenco.jTable2);
            }
        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (cuenco.txtMonto.getText().isEmpty()) {

            msj += "El campo monto no puede estar vacio\n";
            resultado = false;
        }

        if (cuenco.txtReferencia.getText().isEmpty()) {

            msj += "El campo de número de referencia no puede estar vacío\n";
            resultado = false;
        }

        if (cuenco.txtDescripcion.getText().isEmpty()) {

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

}
