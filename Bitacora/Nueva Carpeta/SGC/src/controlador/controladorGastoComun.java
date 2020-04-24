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
import javax.swing.table.DefaultTableModel;
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
    ArrayList<GastoComun> listagastocomun;

    public controladorGastoComun(gastoComun gc, catalogoGastoComun gatagc, GastoComun modgac, Proveedores modpro, ModeloConceptoGastos modcon, PantallaPrincipal1 panta1) {
        this.gc = gc;
        this.catagc = gatagc;
        this.modgac = modgac;
        this.modpro = modpro;
        this.modcon = modcon;
        this.panta1 = panta1;
        this.catagc.addWindowListener(this);

        this.catagc.jButton2.addActionListener(this);
        this.gc.jcomboproveedor.addItemListener(this);

        this.gc.btnGuardar.addActionListener(this);
        this.gc.btnLimpiar.addActionListener(this);
        this.gc.btnModificar.addActionListener(this);
    }

    public void LlenartablaGastocomun(JTable tablaD) {

        listagastocomun = modgac.listarGastoComun();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Nº gasto comun");
        modeloT.addColumn("Tipo Gasto");
        modeloT.addColumn("Periodo");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Saldo Restante");
        modeloT.addColumn("Nº factura");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Observaciones");
        modeloT.addColumn("fecha");
        modeloT.addColumn("estado");

        Object[] columna = new Object[12];

        int numRegistro = listagastocomun.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listagastocomun.get(i).getId();
            columna[1] = listagastocomun.get(i).getTipo_gasto();
            String fecha = String.valueOf(listagastocomun.get(i).getMes())+"-"+listagastocomun.get(i).getAño();
            columna[2] = fecha;
            columna[3] = listagastocomun.get(i).getMonto();
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

                if (modgac.registrar_gasto_comun(modgac)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }

            }

            if (e.getSource() == gc.btnModificar) {
                JOptionPane.showMessageDialog(null, "registro modificado");

            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        modpro.setCedula(gc.jcomboproveedor.getSelectedItem().toString());
        modpro.buscar(modpro);
        gc.txtnombreprov.setText(modpro.getNombre());
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
}
