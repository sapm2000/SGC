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
import vista.catalogoGastoComun;
import vista.gastoComun;

/**
 *
 * @author rma
 */
public class controladorGastoComun implements ActionListener {

    private gastoComun gc;
    private catalogoGastoComun catagc;

    public controladorGastoComun(gastoComun gc, catalogoGastoComun gatagc) {
        this.gc = gc;
        this.catagc = gatagc;
        this.catagc.jButton1.addActionListener(this);
        this.catagc.jButton2.addActionListener(this);
        this.catagc.jButton4.addActionListener(this);
        this.catagc.jButton5.addActionListener(this);
        this.gc.btnGuardar.addActionListener(this);
        this.gc.btnLimpiar.addActionListener(this);
        this.gc.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catagc.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catagc.jButton4.setEnabled(true);
                this.catagc.jButton5.setEnabled(true);
                this.catagc.jButton2.setEnabled(false);
                this.catagc.jButton2.setForeground(Color.gray);
                this.catagc.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catagc.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catagc.jButton2.setEnabled(true);
                this.catagc.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catagc.jButton4.setEnabled(false);
                this.catagc.jButton5.setEnabled(false);
                this.catagc.jButton4.setForeground(Color.gray);
                this.catagc.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catagc.jButton2) {
            this.gc.setVisible(true);
            this.gc.btnModificar.setVisible(false);

        }

        if (e.getSource() == catagc.jButton4) {
            this.gc.setVisible(true);
            this.gc.btnGuardar.setVisible(false);

        }

        if (e.getSource() == catagc.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == gc.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == gc.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }
}
