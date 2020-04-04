/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CategoriaGasto;
import vista.catalogoCategoriaGastos;
import vista.categoriaGastos;

/**
 *
 * @author rma
 */
public class controladorCategoriaGastos implements ActionListener {

    private catalogoCategoriaGastos catacg;
    private categoriaGastos cg;
    private CategoriaGasto modcg;
    DefaultTableModel dm;

    public controladorCategoriaGastos(catalogoCategoriaGastos catacg, categoriaGastos cg, CategoriaGasto modcg) {
        this.catacg = catacg;
        this.cg = cg;
        this.modcg = modcg;
        this.catacg.jButton1.addActionListener(this);
        this.catacg.jButton2.addActionListener(this);
        this.catacg.jButton4.addActionListener(this);
        this.catacg.jButton5.addActionListener(this);
        this.cg.btnGuardar.addActionListener(this);
        this.cg.btnLimpiar.addActionListener(this);
        this.cg.btnModificar.addActionListener(this);
        this.catacg.jTable2.addMouseListener(this);
        
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacg.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catacg.jButton4.setEnabled(true);
                this.catacg.jButton5.setEnabled(true);
                this.catacg.jButton2.setEnabled(false);
                this.catacg.jButton2.setForeground(Color.gray);
                this.catacg.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catacg.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catacg.jButton2.setEnabled(true);
                this.catacg.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catacg.jButton4.setEnabled(false);
                this.catacg.jButton5.setEnabled(false);
                this.catacg.jButton4.setForeground(Color.gray);
                this.catacg.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catacg.jButton2) {
            this.cg.setVisible(true);
            this.cg.btnModificar.setVisible(false);
            this.cg.btnGuardar.setVisible(true);

        }

        if (e.getSource() == catacg.jButton4) {
            this.cg.setVisible(true);
            this.cg.btnGuardar.setVisible(false);
            this.cg.btnModificar.setVisible(true);

        }

        if (e.getSource() == catacg.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == cg.btnGuardar) {
            modcg.setNombre(cg.txtnombre.getText());
            modcg.setDescripcion(cg.txtdescripcion.getText());
            
            if (modcg.registrar(modcg)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");
                

            } else {

                JOptionPane.showMessageDialog(null, "Error al Guardar");
                

            }

        }

        if (e.getSource() == cg.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
        
        
    }
    @Override
    public void mouseClicked(MouseEvent e) {
            // primero, obtengo la fila seleccionada

        int fila = this.catacg.jTable2.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catacg.jTable2.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catacg.jTable2.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda
        catacg.jTextField1.setText(String.valueOf(dato));
    }

}
