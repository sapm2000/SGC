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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.GastoComun;
import modelo.ModeloConceptoGastos;
import modelo.Proveedores;
import vista.PantallaPrincipal1;
import vista.catalogoGastoComun;
import vista.gastoComun;

/**
 *
 * @author rma
 */
public class controladorGastoComun implements ActionListener, ItemListener, MouseListener, KeyListener, WindowListener {

    private gastoComun gc;
    private catalogoGastoComun catagc;
    private GastoComun modgac;
    private Proveedores modpro;
    private ModeloConceptoGastos modcon;
    private PantallaPrincipal1 panta1;
    private CerrarMes modc;
    ArrayList<GastoComun> listagastocomun;
    DefaultTableModel dm;
    double montoi;
    double saldo;

    public controladorGastoComun(gastoComun gc, catalogoGastoComun gatagc, GastoComun modgac, Proveedores modpro, ModeloConceptoGastos modcon, PantallaPrincipal1 panta1, CerrarMes modc) {
        this.gc = gc;
        this.catagc = gatagc;
        this.modgac = modgac;
        this.modpro = modpro;
        this.modcon = modcon;
        this.panta1 = panta1;
        this.modc = modc;
        this.catagc.addWindowListener(this);

        this.catagc.jButton2.addActionListener(this);
        this.gc.jcomboproveedor.addItemListener(this);
        this.catagc.jTable1.addMouseListener(this);
        this.catagc.jTextField1.addKeyListener(this);

        this.gc.btnGuardar.addActionListener(this);
        this.gc.btnLimpiar.addActionListener(this);
        this.gc.btnModificar.addActionListener(this);
        this.gc.btnEliminar.addActionListener(this);
    }

    public void LlenartablaGastocomun(JTable tablaD) {

        listagastocomun = modgac.listarGastoComun();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("<html>Nº de <br> Gasto Común</html>");
        modeloT.addColumn("<html>Tipo de <br> Gasto</html>");
        modeloT.addColumn("Periodo");
        modeloT.addColumn("Monto");
        modeloT.addColumn("<html>Saldo <br> Restante</html>");
        modeloT.addColumn("<html>Nº de <br> Factura</html>");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Observaciones");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Estado");

        Object[] columna = new Object[12];

        int numRegistro = listagastocomun.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listagastocomun.get(i).getId();
            columna[1] = listagastocomun.get(i).getTipo_gasto();
            String fecha = String.valueOf(listagastocomun.get(i).getMes()) + "-" + listagastocomun.get(i).getAño();
            columna[2] = fecha;
            columna[3] = String.format("%.1f", listagastocomun.get(i).getMonto());
            columna[4] = listagastocomun.get(i).getSaldo();
            columna[5] = listagastocomun.get(i).getNumero_factura();
            columna[6] = listagastocomun.get(i).getId_proveedor();
            columna[7] = listagastocomun.get(i).getNombre_Concepto();
            columna[8] = listagastocomun.get(i).getObservaciones();
            columna[9] = listagastocomun.get(i).getFecha();
            columna[10] = listagastocomun.get(i).getEstado();

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catagc.jButton2) {
            this.gc.setVisible(true);
            this.gc.btnModificar.setEnabled(false);
            this.gc.btnGuardar.setEnabled(true);
            this.gc.btnEliminar.setEnabled(false);
            this.gc.txtid.setVisible(false);
            gc.jcomboproveedor.removeAllItems();
            gc.jcomboconcepto.removeAllItems();
            modpro.llenar_proveedores(gc.jcomboproveedor);
            modcon.llenar_concepto(gc.jcomboconcepto);

        }

        if (e.getSource() == gc.btnGuardar) {
            if (validar()) {
                modgac.setTipo_gasto(gc.jcombotipo.getSelectedItem().toString());
                modgac.setMes(gc.jMonthChooser1.getMonth() + 1);
                modgac.setAño(gc.jYearChooser1.getYear());
                modgac.setMonto(Double.parseDouble(gc.txtMonto.getText()));
                modgac.setNumero_factura(gc.txtNumerofactura.getText());
                modgac.setId_proveedor(gc.jcomboproveedor.getSelectedItem().toString());
                modcon.setNombre_Concepto(gc.jcomboconcepto.getSelectedItem().toString());
                modcon.buscarid(modcon);
                modgac.setId_concepto(modcon.getId());
                modgac.setObservaciones(gc.txaObservaciones.getText());
                modgac.setSaldo(Double.parseDouble(gc.txtMonto.getText()));

                java.sql.Date sqlDate = convert(gc.jDateChooser1.getDate());
                modgac.setFecha(sqlDate);
                modgac.setEstado("Pendiente");
                modgac.setId_condominio(panta1.rif.getText());
                modc.setMes_cierre(gc.jMonthChooser1.getMonth() + 1);
                modc.setAño_cierre(gc.jYearChooser1.getYear());
                modc.setId_condominio(panta1.rif.getText());

                if (modc.buscarfechas(modc)) {
                    JOptionPane.showMessageDialog(null, "no puede registrar gastos a un periodo ya cerrado");
                } else {

                    if (modgac.registrar_gasto_comun(modgac)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        modgac.setId_condominio(panta1.rif.getText());
                        LlenartablaGastocomun(catagc.jTable1);
                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }

            }
        }

        if (e.getSource() == gc.btnModificar) {
            if (validar()) {
                modgac.setTipo_gasto(gc.jcombotipo.getSelectedItem().toString());
                modgac.setMes(gc.jMonthChooser1.getMonth() + 1);
                modgac.setAño(gc.jYearChooser1.getYear());
                modgac.setMonto(Double.parseDouble(gc.txtMonto.getText()));
                modgac.setNumero_factura(gc.txtNumerofactura.getText());
                modgac.setId_proveedor(gc.jcomboproveedor.getSelectedItem().toString());
                modcon.setNombre_Concepto(gc.jcomboconcepto.getSelectedItem().toString());
                modcon.buscarid(modcon);
                modgac.setId_concepto(modcon.getId());
                modgac.setObservaciones(gc.txaObservaciones.getText());
                modgac.setId(Integer.parseInt(gc.txtid.getText()));
                modc.setMes_cierre(gc.jMonthChooser1.getMonth() + 1);
                modc.setAño_cierre(gc.jYearChooser1.getYear());
                modc.setId_condominio(panta1.rif.getText());

                if (modc.buscarfechas(modc)) {
                    JOptionPane.showMessageDialog(null, "no puede registrar gastos a un periodo ya cerrado");
                } else {

                    java.sql.Date sqlDate = convert(gc.jDateChooser1.getDate());
                    modgac.setFecha(sqlDate);
                    modgac.setEstado("Pendiente");
                    modgac.setId_condominio(panta1.rif.getText());

                    double var1 = Double.parseDouble(gc.txtMonto.getText());
                    double var2 = var1 - montoi;
                    double total = var2 + saldo;
                    modgac.setSaldo(total);

                    if (total > 0) {
                        if (modgac.modificar_gasto_comun(modgac)) {

                            JOptionPane.showMessageDialog(null, "Registro Modificado");
                            modgac.setId_condominio(panta1.rif.getText());
                            LlenartablaGastocomun(catagc.jTable1);
                            this.gc.dispose();

                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El saldo de la deuda no puede ser negativo");
                    }

                }
            }

        }

        if (e.getSource() == gc.btnEliminar) {

            modgac.setId_condominio(panta1.rif.getText());
            modgac.setId(Integer.parseInt(gc.txtid.getText()));

            if (modgac.eliminar_gasto_comun(modgac)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                LlenartablaGastocomun(catagc.jTable1);
                this.gc.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            modpro.setCedula(gc.jcomboproveedor.getSelectedItem().toString());
            modpro.buscar(modpro);
            gc.txtnombreprov.setText(modpro.getNombre());
        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (gc.txtMonto.getText().isEmpty()) {

            msj += "El campo numero de Cuenta no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int fila = this.catagc.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
        String dato = String.valueOf(this.catagc.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        modgac.setId(Integer.parseInt(dato));
        modgac.setId_condominio(panta1.rif.getText());
        modgac.buscargastoComun(modgac);

        this.gc.setVisible(true);
        gc.txtid.setVisible(false);
        gc.txtid.setText(dato);
        gc.jcomboproveedor.removeAllItems();
        gc.jcomboconcepto.removeAllItems();
        modpro.llenar_proveedores(gc.jcomboproveedor);
        modcon.llenar_concepto(gc.jcomboconcepto);
        gc.jcomboproveedor.setSelectedItem(modgac.getId_proveedor());
        gc.jcomboconcepto.setSelectedItem(modgac.getNombre_Concepto());
        gc.jMonthChooser1.setMonth(modgac.getMes() - 1);
        gc.jYearChooser1.setYear(modgac.getAño());
        gc.jDateChooser1.setDate(modgac.getFecha());
        gc.jcombotipo.setSelectedItem(modgac.getTipo_gasto());
        gc.txaObservaciones.setText(modgac.getObservaciones());
        gc.txtMonto.setText(String.valueOf(modgac.getMonto()));
        gc.txtNumerofactura.setText(modgac.getNumero_factura());
        modpro.setCedula(gc.jcomboproveedor.getSelectedItem().toString());
        modpro.buscar(modpro);
        gc.txtnombreprov.setText(modpro.getNombre());
        if (modgac.getEstado().equals("Procesado")) {
            gc.btnEliminar.setEnabled(false);
            gc.btnModificar.setEnabled(false);
            gc.btnGuardar.setEnabled(false);
            JOptionPane.showMessageDialog(null, "los gastos procesados no pueden ser modificados ni eliminados");
        } else {
            gc.btnEliminar.setEnabled(true);
            gc.btnModificar.setEnabled(true);
            gc.btnGuardar.setEnabled(false);
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
        if (e.getSource() == catagc.jTextField1) {

            filtro(catagc.jTextField1.getText(), catagc.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        modgac.setId_condominio(panta1.rif.getText());
        LlenartablaGastocomun(catagc.jTable1);
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
