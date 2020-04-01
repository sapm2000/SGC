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
import vista.catalogoConceptoGasto;
import vista.conceptoGasto;

/**
 *
 * @author rma
 */
public class controladorConceptoGasto implements ActionListener {

    private catalogoConceptoGasto catacga;
    private conceptoGasto cga;

    public controladorConceptoGasto(catalogoConceptoGasto catacga, conceptoGasto cga) {
        this.catacga = catacga;
        this.cga = cga;
        this.catacga.jButton1.addActionListener(this);
        this.catacga.jButton2.addActionListener(this);
        this.catacga.jButton4.addActionListener(this);
        this.catacga.jButton5.addActionListener(this);
        this.cga.btnGuardar.addActionListener(this);
        this.cga.btnLimpiar.addActionListener(this);
        this.cga.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacga.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catacga.jButton4.setEnabled(true);
                this.catacga.jButton5.setEnabled(true);
                this.catacga.jButton2.setEnabled(false);
                this.catacga.jButton2.setForeground(Color.gray);
                this.catacga.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catacga.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catacga.jButton2.setEnabled(true);
                this.catacga.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catacga.jButton4.setEnabled(false);
                this.catacga.jButton5.setEnabled(false);
                this.catacga.jButton4.setForeground(Color.gray);
                this.catacga.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catacga.jButton2) {
            this.cga.setVisible(true);
            this.cga.btnModificar.setVisible(false);
            this.cga.btnGuardar.setVisible(true);

        }

        if (e.getSource() == catacga.jButton4) {
            this.cga.setVisible(true);
            this.cga.btnGuardar.setVisible(false);
            this.cga.btnModificar.setVisible(true);

        }

        if (e.getSource() == catacga.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == cga.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == cga.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

}
