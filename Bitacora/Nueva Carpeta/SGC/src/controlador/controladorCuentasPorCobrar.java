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

        listaCierremes = modc.listarpagos();
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

        modeloT.addColumn("Nº de recibo");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Alicuota");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Saldo restante");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[8];

        int numRegistro = listaCierremes.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCierremes.get(i).getId_gasto();
            columna[1] = listaCierremes.get(i).getMes_cierre();
            columna[2] = listaCierremes.get(i).getAño_cierre();
            columna[3] = listaCierremes.get(i).getAlicuota();
            columna[4] = listaCierremes.get(i).getMonto();
            columna[5] = listaCierremes.get(i).getSaldo_restante();
            columna[6] = listaCierremes.get(i).getEstado();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cuenco.btnGuardar) {
            modcuen.setDescripcion(cuenco.txtDescripcion.getText());
            modcuen.setForma_pago(cuenco.jComboForma.getSelectedItem().toString());
            modcuen.setId_cuenta(cuenco.jComboCuenta.getSelectedItem().toString());
            modfon.setTipo(cuenco.jComboFondo.getSelectedItem().toString());
            modfon.setId_condominio(panta1.rif.getText());
            modfon.buscar1(modfon);
            modcuen.setId_fondo(modfon.getId());
            modcuen.setId_unidad(cuenco.jComboUnidad.getSelectedItem().toString());
            modcuen.setMonto(Double.parseDouble(cuenco.txtMonto.getText()));
            modcuen.setReferencia(cuenco.txtReferencia.getText());
            java.sql.Date sqlDate = convert(cuenco.jDateChooser1.getDate());
            modcuen.setFecha(sqlDate);
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
                    JOptionPane.showMessageDialog(null, "registro guardado");

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
            modc.setId_condominio(panta1.rif.getText());
            modc.setId_unidad(cuenco.jComboUnidad.getSelectedItem().toString());
            Llenartabla(cuenco.jTable1);
            addCheckBox(7, cuenco.jTable1);
        }

    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

}
