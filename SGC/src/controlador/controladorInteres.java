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
import vista.catalogoInteres;
import vista.interes;

/**
 *
 * @author rma
 */
public class controladorInteres implements ActionListener {

    private interes in;
    private catalogoInteres catain;

    public controladorInteres(interes in, catalogoInteres catain) {
        this.in = in;
        this.catain = catain;
        this.catain.jButton1.addActionListener(this);
        this.catain.jButton2.addActionListener(this);
        this.catain.jButton4.addActionListener(this);
        this.catain.jButton5.addActionListener(this);
        this.in.btnGuardar.addActionListener(this);
        this.in.btnLimpiar.addActionListener(this);
        this.in.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catain.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catain.jButton4.setEnabled(true);
                this.catain.jButton5.setEnabled(true);
                this.catain.jButton2.setEnabled(false);
                this.catain.jButton2.setForeground(Color.gray);
                this.catain.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catain.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catain.jButton2.setEnabled(true);
                this.catain.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catain.jButton4.setEnabled(false);
                this.catain.jButton5.setEnabled(false);
                this.catain.jButton4.setForeground(Color.gray);
                this.catain.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catain.jButton2) {
            this.in.setVisible(true);
            this.in.btnModificar.setVisible(false);
            this.in.btnGuardar.setVisible(true);

        }

        if (e.getSource() == catain.jButton4) {
            this.in.setVisible(true);
            this.in.btnGuardar.setVisible(false);
            this.in.btnModificar.setVisible(true);

        }

        if (e.getSource() == catain.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == in.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == in.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

}
