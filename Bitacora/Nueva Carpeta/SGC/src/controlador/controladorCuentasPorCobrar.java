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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.cuentasPorCobrar;

/**
 *
 * @author rma
 */
public class controladorCuentasPorCobrar implements ActionListener, WindowListener, ItemListener, KeyListener {

    private cuentasPorCobrar cuenco;
    private CuentasPorCobrar modcuen;
    private Unidades moduni;
    private Fondo modfon;
    private Cuenta modcu;
    private CerrarMes modc;
    private PantallaPrincipal1 panta1;
    ArrayList<CerrarMes> listaCierremes;
    ArrayList<Unidades> listaunidades;
    ArrayList<Fondo> listafondo;
    ArrayList<Cuenta> listaCuenta;

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
        cuenco.txtMonto.addKeyListener(this);
        cuenco.txtDescripcion.addKeyListener(this);
        cuenco.txtReferencia.addKeyListener(this);
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

        modeloT.addColumn("<html>Nº de<br> Recibo</html>");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Alícuota");
        modeloT.addColumn("Monto");
        modeloT.addColumn("<html>Saldo <br> Restante</html>");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[8];

        int numRegistro = listaCierremes.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCierremes.get(i).getId_gasto();
            columna[1] = listaCierremes.get(i).getMes_cierre();
            columna[2] = listaCierremes.get(i).getAño_cierre();
            columna[3] = Validacion.formatoalicuota.format(listaCierremes.get(i).getAlicuota());
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
        if (e.getSource() == cuenco.btnGuardar) {
            if (validar()) {
                listaunidades = moduni.buscarUnidades();
                listafondo = modfon.listar(2);
                listaCuenta = modcu.listarcuenta();

                int j = 0;
                modcuen.setDescripcion(cuenco.txtDescripcion.getText());
                modcuen.setForma_pago(cuenco.jComboForma.getSelectedItem().toString());
                int ind2 = cuenco.jComboCuenta.getSelectedIndex() - 1;

                for (int i = 0; i < cuenco.jTable1.getRowCount(); i++) {
                    if (valueOf(cuenco.jTable1.getValueAt(i, 7)) == "true") {

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

                        int ind1 = cuenco.jComboFondo.getSelectedIndex() - 1;
                        if (ind1 == -1) {
                            JOptionPane.showMessageDialog(null, "seleccione el fondo a depositar");
                        } else {

                            modcuen.setId_fondo(listafondo.get(ind1).getId());

                            int ind = cuenco.jComboUnidad.getSelectedIndex() - 1;

                            if (ind == -1) {
                                JOptionPane.showMessageDialog(null, "seleccione el numero de la unidad");
                            } else {
                                modcuen.setId_unidad(listaunidades.get(ind).getId());
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
                                    modcuen.setId_condominio(panta1.rif.getText());
                                    if (modcuen.registrarCobro(modcuen)) {
                                        double var4 = listafondo.get(ind1).getSaldo()+ modcuen.getMonto();
                                        modfon.setId(listafondo.get(ind1).getId());
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
                                                        modc.setEstado("Pendiente de Pago");
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
                                        modcuen.setId_unidad(listaunidades.get(ind).getId());
                                        Llenartabla(cuenco.jTable1);
                                        addCheckBox(7, cuenco.jTable1);
                                        Llenartablapagados(cuenco.jTable2);
                                        cuenco.jComboFondo.removeAllItems();
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
        cuenco.jComboUnidad.addItem("Seleccione el numero de la unidad");

        if (datos != null) {
            for (Unidades datosX : datos) {
                moduni = datosX;
                cuenco.jComboUnidad.addItem(moduni.getN_unidad());
            }

        }
    }

    private void crearCbxFondo(ArrayList<Fondo> datos) {
        cuenco.jComboFondo.addItem("Seleccione el fondo a depositar");

        if (datos != null) {
            for (Fondo datosX : datos) {
                modfon = datosX;
                cuenco.jComboFondo.addItem(modfon.getTipo() + " " + modfon.getSaldo());
            }

        }
    }

    private void crearCbxCuenta(ArrayList<Cuenta> datos) {
        cuenco.jComboCuenta.addItem("Seleccione la cuenta depositada");

        if (datos != null) {
            for (Cuenta datosX : datos) {
                modcu = datosX;
                cuenco.jComboCuenta.addItem(modcu.getN_cuenta() + " " + modcu.getCedula() + " " + modcu.getBeneficiario());
            }

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        moduni.setId_condominio(panta1.rif.getText());
        modcu.setId_condominio(panta1.rif.getText());
        modfon.setId_condominio(panta1.rif.getText());
        listaunidades = moduni.buscarUnidades();
        crearCbxUnidad(listaunidades);
        listafondo = modfon.listar(2);
        crearCbxFondo(listafondo);
        listaCuenta = modcu.listarcuenta();
        crearCbxCuenta(listaCuenta);

        Component[] components = cuenco.jPanel2.getComponents();
        JComponent[] com = {
            cuenco.txtReferencia, cuenco.txtDescripcion, cuenco.txtMonto
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
             moduni.setId_condominio(panta1.rif.getText());
            listaunidades = moduni.buscarUnidades();
             int ind = cuenco.jComboUnidad.getSelectedIndex() - 1;
           if (ind == -1) {

            } else {
                 
               
                modc.setId_unidad(listaunidades.get(ind).getId());
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

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == cuenco.txtReferencia) {

            Validacion.Espacio(e);
            Validacion.limite(e, cuenco.txtReferencia.getText(), 50);
        }

        if (e.getSource() == cuenco.txtDescripcion) {

            Validacion.limite(e, cuenco.txtDescripcion.getText(), 500);
        }
        if (e.getSource() == cuenco.txtMonto) {

            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, cuenco.txtMonto.getText());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
