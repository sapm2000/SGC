/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.FormaPago;
import vista.formaDePago;

/**
 *
 * @author rma
 */
public class controladorForma_pago implements ActionListener {

    private FormaPago modfor;
    private formaDePago vfor;

    ArrayList<FormaPago> listaFormaPago;

    public controladorForma_pago() {
        this.modfor = new FormaPago();
        this.vfor = new formaDePago();
        this.vfor.btnGuardar.addActionListener(this);
        this.vfor.btnLimpiar.addActionListener(this);
        this.vfor.btnEliminar.addActionListener(this);
        this.vfor.btnModificar.addActionListener(this);

    }

    public void Llenartabla(JTable tablaD) {

        listaFormaPago = modfor.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Forma Pago");

        Object[] columna = new Object[1];

        int numRegistro = listaFormaPago.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaFormaPago.get(i).getForma_pago();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vfor.btnGuardar) {
            if (validar()) {
                modfor.setForma_pago(vfor.txtnombre_banco.getText());

                if (modfor.buscarInactivo(modfor)) {
                    modfor.activar(modfor);
                } else {

                    if (modfor.registrar(modfor)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(cban.tabla_bancos);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }
        }

        if (e.getSource() == vfor.btnModificar) {
            if (validar()) {
                modfor.setForma_pago(vfor.txtnombre_banco.getText());
                modfor.setId(Integer.parseInt(vfor.txtid.getText()));
                if (modfor.buscarInactivo(modfor)) {
                    JOptionPane.showMessageDialog(null, "no puede colocar el nombre de un metodo de pago que ya existio, si quiere colocar este nombre debe registrarlo nuevamente");
                } else {

                    if (modfor.modificar(modfor)) {

                        JOptionPane.showMessageDialog(null, "Registro modificado");
                        vfor.dispose();
                        Llenartabla(cban.tabla_bancos);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                    }
                }
            }
        }
        
        if (e.getSource() == vfor.btnEliminar) {

            modfor.setId(Integer.parseInt(vfor.txtid.getText()));

           

                if (modfor.eliminar(modfor)) {

                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    vfor.dispose();
                    Llenartabla(cban.tabla_bancos);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }

            }
        }
    

    public void limpiar() {

        vfor.txtnombre_banco.setText(null);

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vfor.txtnombre_banco.getText().isEmpty()) {

            msj += "El campo nombre del banco no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
