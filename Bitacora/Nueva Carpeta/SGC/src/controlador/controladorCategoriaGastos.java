/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.catalogoCategoriaGastos;
import vista.categoriaGastos;

/**
 *
 * @author rma
 */
public class controladorCategoriaGastos implements ActionListener {

    private catalogoCategoriaGastos catacg;
    private categoriaGastos cg;

    public controladorCategoriaGastos(catalogoCategoriaGastos catacg, categoriaGastos cg) {
        this.catacg = catacg;
        this.cg = cg;
        this.catacg.jButton1.addActionListener(this);
        this.catacg.jButton2.addActionListener(this);
        this.catacg.jButton4.addActionListener(this);
        this.catacg.jButton5.addActionListener(this);
        this.cg.btnGuardar.addActionListener(this);
        this.cg.btnLimpiar.addActionListener(this);
        this.cg.btnModificar.addActionListener(this);
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
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == cg.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

}
