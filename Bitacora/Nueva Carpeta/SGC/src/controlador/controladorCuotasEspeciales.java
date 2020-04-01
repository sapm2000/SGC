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
import vista.catalogoCuotasEspeciales;
import vista.cuotasEspeciales;

/**
 *
 * @author rma
 */
public class controladorCuotasEspeciales implements ActionListener {

    private cuotasEspeciales cuotae;
    private catalogoCuotasEspeciales catacuoe;

    public controladorCuotasEspeciales(cuotasEspeciales cuotae, catalogoCuotasEspeciales catacuoe) {
        this.cuotae = cuotae;
        this.catacuoe = catacuoe;
        this.catacuoe.jButton1.addActionListener(this);
        this.catacuoe.jButton2.addActionListener(this);
        this.catacuoe.jButton4.addActionListener(this);
        this.catacuoe.jButton5.addActionListener(this);
        this.cuotae.btnGuardar.addActionListener(this);
        this.cuotae.btnLimpiar.addActionListener(this);
        this.cuotae.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacuoe.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catacuoe.jButton4.setEnabled(true);
                this.catacuoe.jButton5.setEnabled(true);
                this.catacuoe.jButton2.setEnabled(false);
                this.catacuoe.jButton2.setForeground(Color.gray);
                this.catacuoe.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catacuoe.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catacuoe.jButton2.setEnabled(true);
                this.catacuoe.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catacuoe.jButton4.setEnabled(false);
                this.catacuoe.jButton5.setEnabled(false);
                this.catacuoe.jButton4.setForeground(Color.gray);
                this.catacuoe.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catacuoe.jButton2) {
            this.cuotae.setVisible(true);
            this.cuotae.btnModificar.setVisible(false);

        }

        if (e.getSource() == catacuoe.jButton4) {
            this.cuotae.setVisible(true);
            this.cuotae.btnGuardar.setVisible(false);
            this.cuotae.jComboBox4.setEnabled(false);
            this.cuotae.jComboBox1.setEnabled(false);
            this.cuotae.jTextField3.setEnabled(false);

        }

        if (e.getSource() == catacuoe.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == cuotae.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == cuotae.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

}
