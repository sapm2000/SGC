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
import vista.catalogoFondo;
import vista.fondo;

/**
 *
 * @author rma
 */
public class controladorFondo implements ActionListener{
    
    private fondo fon;
    private catalogoFondo catafon;

    public controladorFondo(fondo fon, catalogoFondo catafon) {
        this.fon = fon;
        this.catafon = catafon;
       this.catafon.jButton1.addActionListener(this);
        this.catafon.jButton2.addActionListener(this);
        this.catafon.jButton4.addActionListener(this);
        this.catafon.jButton6.addActionListener(this);
        this.fon.btnGuardar.addActionListener(this);
        this.fon.btnLimpiar.addActionListener(this);
        this.fon.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catafon.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catafon.jButton4.setEnabled(true);
                this.catafon.jButton6.setEnabled(true);
                this.catafon.jButton2.setEnabled(false);
                this.catafon.jButton2.setForeground(Color.gray);
                this.catafon.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catafon.jButton6.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catafon.jButton2.setEnabled(true);
                this.catafon.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catafon.jButton4.setEnabled(false);
                this.catafon.jButton6.setEnabled(false);
                this.catafon.jButton4.setForeground(Color.gray);
                this.catafon.jButton6.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catafon.jButton2) {
            this.fon.setVisible(true);
            this.fon.btnModificar.setVisible(false);

        }

        if (e.getSource() == catafon.jButton4) {
            this.fon.setVisible(true);
            this.fon.btnGuardar.setVisible(false);
            this.fon.jTextField4.setEnabled(false);

        }

        if (e.getSource() == catafon.jButton6) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == fon.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == fon.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }
}
