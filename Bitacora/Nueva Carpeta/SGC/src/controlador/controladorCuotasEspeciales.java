/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Asambleas;
import modelo.CerrarMes;
import modelo.CuotasEspeciales;
import modelo.ModeloConceptoGastos;
import modelo.Proveedores;
import vista.PantallaPrincipal1;
import vista.catalogoCuotasEspeciales;
import vista.cuotasEspeciales;

/**
 *
 * @author rma
 */
public class controladorCuotasEspeciales implements ActionListener, MouseListener, KeyListener, WindowListener {

    private cuotasEspeciales cuotae;
    private catalogoCuotasEspeciales catacuoe;
    private Proveedores modpro;
    private ModeloConceptoGastos modcon;
    private Asambleas modasa;
    private PantallaPrincipal1 panta1;
    private CuotasEspeciales modcuo;
    private CerrarMes modc;
    DefaultTableModel dm;
    ArrayList<CuotasEspeciales> listacuotasEspeciales;

    public controladorCuotasEspeciales(cuotasEspeciales cuotae, catalogoCuotasEspeciales catacuoe, Proveedores modpro, ModeloConceptoGastos modcon, Asambleas modasa, PantallaPrincipal1 panta1, CuotasEspeciales modcuo, CerrarMes modc) {
        this.cuotae = cuotae;
        this.catacuoe = catacuoe;
        this.modcon = modcon;
        this.modpro = modpro;
        this.modasa = modasa;
        this.panta1 = panta1;
        this.modcuo = modcuo;
        this.modc = modc;

        this.catacuoe.jButton2.addActionListener(this);
        this.catacuoe.jTable1.addMouseListener(this);
        this.catacuoe.jTextField1.addKeyListener(this);
        this.catacuoe.addWindowListener(this);
        this.cuotae.btnEliminar.addActionListener(this);

        this.cuotae.btnGuardar.addActionListener(this);
        this.cuotae.btnLimpiar.addActionListener(this);
        this.cuotae.btnModificar.addActionListener(this);
        this.cuotae.si.addMouseListener(this);
        this.cuotae.no.addMouseListener(this);
    }

    public void llenartablaCuotasEspeciales(JTable tablaD) {

        listacuotasEspeciales = modcuo.listarCuotasEspeciales();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
            

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Cuota Especial</html>");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("<html>Calcular <br> Por</html>");
        modeloT.addColumn("<html>Comienzo de <br> Cobro</html>");
        modeloT.addColumn("<html>Monto <br> Inicial</html>");
        modeloT.addColumn("Saldo");
        modeloT.addColumn("Asamblea");

        modeloT.addColumn("<html>Meses <br> Iniciales</html>");
        modeloT.addColumn("<html>Meses <br> Restantes</html>");
        modeloT.addColumn("Observación");

        modeloT.addColumn("Estado");

        Object[] columna = new Object[13];

        int numRegistro = listacuotasEspeciales.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listacuotasEspeciales.get(i).getId();
            columna[1] = listacuotasEspeciales.get(i).getId_proveedor();
            String fecha = String.valueOf(listacuotasEspeciales.get(i).getMes()) + "-" + listacuotasEspeciales.get(i).getAño();

            columna[2] = listacuotasEspeciales.get(i).getNombre_Concepto();
            columna[3] = listacuotasEspeciales.get(i).getCalcular();
            columna[4] = fecha;
            columna[5] = listacuotasEspeciales.get(i).getMonto();
            columna[6] = listacuotasEspeciales.get(i).getSaldo();
            columna[7] = listacuotasEspeciales.get(i).getNombre_asamble();
            columna[8] = listacuotasEspeciales.get(i).getN_meses();
            columna[9] = listacuotasEspeciales.get(i).getN_meses_restantes();
            columna[10] = listacuotasEspeciales.get(i).getObservacion();
            columna[11] = listacuotasEspeciales.get(i).getEstado();

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
        tablaD.getColumnModel().getColumn(11).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacuoe.jButton2) {
            this.cuotae.setVisible(true);
            this.cuotae.btnModificar.setEnabled(false);
            this.cuotae.btnGuardar.setEnabled(true);
            this.cuotae.btnEliminar.setEnabled(false);
            this.cuotae.jProveedor.setEnabled(true);
            this.cuotae.jCalcular.setEnabled(true);
            modasa.setId_condominio(panta1.rif.getText());
            cuotae.jAsamblea.removeAllItems();
            cuotae.jConcepto.removeAllItems();
            cuotae.jProveedor.removeAllItems();
            modpro.llenar_proveedores(cuotae.jProveedor);
            modcon.llenar_concepto(cuotae.jConcepto);
            modasa.llenar_Asamblea(cuotae.jAsamblea);
            this.cuotae.txtid.setVisible(false);
            cuotae.txaObservaciones.setText("");
            cuotae.txtMonto.setText("");
            cuotae.txtNmeses.setText("");
            cuotae.txtid.setText("");

        }

        if (e.getSource() == cuotae.btnGuardar) {
            if (validar()) {
                modcuo.setId_proveedor(cuotae.jProveedor.getSelectedItem().toString());
                modcon.setNombre_Concepto(cuotae.jConcepto.getSelectedItem().toString());
                modcon.buscarid(modcon);
                modcuo.setId_concepto(modcon.getId());
                String var10 = cuotae.jCalcular.getSelectedItem().toString();
                if (var10.equals("Total de Inmuebles")) {
                    var10 = "Total de Inmuebles";
                }
                modcuo.setCalcular(var10);
                modcuo.setMes(cuotae.jMonthChooser1.getMonth() + 1);
                modcuo.setAño(cuotae.jYearChooser1.getYear());
                modcuo.setMonto(Double.parseDouble(cuotae.txtMonto.getText()));
                modcuo.setSaldo(Double.parseDouble(cuotae.txtMonto.getText()));
                modcuo.setN_meses(Integer.parseInt(cuotae.txtNmeses.getText()));
                modcuo.setN_meses_restantes(Integer.parseInt(cuotae.txtNmeses.getText()));

                modcuo.setObservacion(cuotae.txaObservaciones.getText());
                modcuo.setEstado("Pendiente");
                modcuo.setId_condominio(panta1.rif.getText());

                int var1 = 0;
                int bre = 0;
                var1 = modcuo.getMes();
                modc.setId_condominio(panta1.rif.getText());
                for (int i = 0; i < modcuo.getN_meses(); i++) {

                    if (var1 + i > 12) {
                        var1 = var1 - 12;
                        if (var1 + 1 > 24) {
                            var1 = var1 - 12;
                        }
                    }

                    modc.setMes_cierre(var1 + i);

                    modc.setAño_cierre(modcuo.getAño());
                    if (modc.buscarfechas(modc)) {
                        bre = 1;
                    } else {
                    }
                }

                if (bre == 1) {
                    JOptionPane.showMessageDialog(null, "no puede registrar cuotas especiales que incluyan un periodo ya cerrado");

                } else {

                    if (cuotae.jAsamblea.getSelectedItem().toString() == "Seleccione la Asamblea") {
                        modcuo.setId_asamblea(0);
                    } else {
                        modasa.setNombre(cuotae.jAsamblea.getSelectedItem().toString());
                        modasa.buscarid(modasa);
                        modcuo.setId_asamblea(modasa.getId());
                    }

                    if (modcuo.registrar_cuota_especial(modcuo)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        modcuo.setId_condominio(panta1.rif.getText());
                        llenartablaCuotasEspeciales(catacuoe.jTable1);

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }

                }
            }

        }

        if (e.getSource() == cuotae.btnModificar) {
            if (validar()) {
                modcuo.setId_proveedor(cuotae.jProveedor.getSelectedItem().toString());
                modcon.setNombre_Concepto(cuotae.jConcepto.getSelectedItem().toString());
                modcon.buscarid(modcon);
                modcuo.setId_concepto(modcon.getId());
                modcuo.setCalcular(cuotae.jCalcular.getSelectedItem().toString());
                modcuo.setMes(cuotae.jMonthChooser1.getMonth() + 1);
                modcuo.setAño(cuotae.jYearChooser1.getYear());
                modcuo.setMonto(Double.parseDouble(cuotae.txtMonto.getText()));
                modcuo.setSaldo(Double.parseDouble(cuotae.txtMonto.getText()));
                modcuo.setN_meses(Integer.parseInt(cuotae.txtNmeses.getText()));
                modcuo.setN_meses_restantes(Integer.parseInt(cuotae.txtNmeses.getText()));

                modcuo.setObservacion(cuotae.txaObservaciones.getText());
                modcuo.setEstado("Pendiente");
                modcuo.setId(Integer.parseInt(cuotae.txtid.getText()));
                int var1 = 0;
                int bre = 0;
                var1 = modcuo.getMes();
                modc.setId_condominio(panta1.rif.getText());
                for (int i = 0; i < modcuo.getN_meses(); i++) {

                    if (var1 + i > 12) {
                        var1 = var1 - 12;
                        if (var1 + 1 > 24) {
                            var1 = var1 - 12;
                        }
                    }

                    modc.setMes_cierre(var1 + i);

                    modc.setAño_cierre(modcuo.getAño());
                    if (modc.buscarfechas(modc)) {
                        bre = 1;
                    } else {
                    }
                }

                if (bre == 1) {
                    JOptionPane.showMessageDialog(null, "no puede registrar cuotas especiales que incluyan un periodo ya cerrado");

                } else {
                    if (cuotae.jAsamblea.getSelectedItem().toString() == "Seleccione la Asamblea") {
                        modcuo.setId_asamblea(0);
                    } else {
                        modasa.setNombre(cuotae.jAsamblea.getSelectedItem().toString());
                        modasa.buscarid(modasa);
                        modcuo.setId_asamblea(modasa.getId());
                    }

                    if (modcuo.modificar_cuota_especial(modcuo)) {

                        JOptionPane.showMessageDialog(null, "Registro Modificado");
                        modcuo.setId_condominio(panta1.rif.getText());
                        llenartablaCuotasEspeciales(catacuoe.jTable1);
                        this.cuotae.dispose();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }

                }
            }

        }

        if (e.getSource() == cuotae.btnEliminar) {

            modcuo.setId_condominio(panta1.rif.getText());
            modcuo.setId(Integer.parseInt(cuotae.txtid.getText()));

            if (modcuo.eliminar_cuotas_especiales(modcuo)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                llenartablaCuotasEspeciales(catacuoe.jTable1);
                this.cuotae.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (cuotae.txtMonto.getText().isEmpty()) {

            msj += "El campo numero de Cuenta no puede estar vacio\n";
            resultado = false;
        }
        
        if (cuotae.txtNmeses.getText().isEmpty()) {

            msj += "El campo numero de N° de meses no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == cuotae.si) {
            cuotae.jAsamblea.setVisible(true);
            cuotae.jAsamblea.removeAllItems();
            modasa.llenar_Asamblea(cuotae.jAsamblea);
            cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
        }
        if (e.getSource() == cuotae.no) {
            cuotae.jAsamblea.setVisible(false);
            cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");

        }

        if (e.getSource() == catacuoe.jTable1) {
            int fila = this.catacuoe.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.catacuoe.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            modcuo.setId(Integer.parseInt(dato));
            modcuo.setId_condominio(panta1.rif.getText());
            modcuo.buscarCuotaEspecial(modcuo);
            this.cuotae.setVisible(true);

            cuotae.txtid.setVisible(false);
            cuotae.txtid.setText(dato);
            cuotae.jAsamblea.removeAllItems();
            cuotae.jConcepto.removeAllItems();
            cuotae.jProveedor.removeAllItems();
            modasa.setId_condominio(panta1.rif.getText());
            modpro.llenar_proveedores(cuotae.jProveedor);
            modcon.llenar_concepto(cuotae.jConcepto);
            modasa.llenar_Asamblea(cuotae.jAsamblea);
            cuotae.jProveedor.setSelectedItem(modcuo.getId_proveedor());
            cuotae.jConcepto.setSelectedItem(modcuo.getNombre_Concepto());
            cuotae.jCalcular.setSelectedItem(modcuo.getCalcular());
            if (modcuo.getNombre_asamble() == null) {
                cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                cuotae.jAsamblea.setVisible(false);
                cuotae.no.setSelected(true);
            } else {
                cuotae.jAsamblea.setSelectedItem(modcuo.getNombre_asamble());
                cuotae.si.setSelected(true);
            }
            cuotae.jMonthChooser1.setMonth(modcuo.getMes() - 1);
            cuotae.jYearChooser1.setYear(modcuo.getAño());
            cuotae.txaObservaciones.setText(modcuo.getObservacion());
            cuotae.txtMonto.setText(String.valueOf(modcuo.getMonto()));
            cuotae.txtNmeses.setText(String.valueOf(modcuo.getN_meses()));
            if (modcuo.getEstado().equals("Pendiente")) {
                cuotae.btnEliminar.setEnabled(true);
                cuotae.btnModificar.setEnabled(true);
                cuotae.btnGuardar.setEnabled(false);

            } else {
                cuotae.btnEliminar.setEnabled(false);
                cuotae.btnModificar.setEnabled(false);
                cuotae.btnGuardar.setEnabled(false);
                JOptionPane.showMessageDialog(null, "las cuotas especiales en proceso no puenden ser modificadas ni eliminadas");
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
        if (e.getSource() == catacuoe.jTextField1) {

            filtro(catacuoe.jTextField1.getText(), catacuoe.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        modcuo.setId_condominio(panta1.rif.getText());
        llenartablaCuotasEspeciales(catacuoe.jTable1);
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
}
