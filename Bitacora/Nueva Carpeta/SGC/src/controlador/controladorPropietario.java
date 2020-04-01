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
import vista.catalogoPropietarios;
import vista.propietarios;

/**
 *
 * @author rma
 */
public class controladorPropietario implements  ActionListener{
    private propietarios pro;
    private catalogoPropietarios catapro;

    public controladorPropietario(propietarios pro, catalogoPropietarios catapro) {
        this.pro = pro;
        this.catapro = catapro;
       this.catapro.jButton1.addActionListener(this);
        this.catapro.jButton2.addActionListener(this);
        this.catapro.jButton4.addActionListener(this);
        this.catapro.jButton5.addActionListener(this);
        this.pro.btnGuardar.addActionListener(this);
        this.pro.btnLimpiar.addActionListener(this);
        this.pro.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catapro.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catapro.jButton4.setEnabled(true);
                this.catapro.jButton5.setEnabled(true);
                this.catapro.jButton2.setEnabled(false);
                this.catapro.jButton2.setForeground(Color.gray);
                this.catapro.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catapro.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catapro.jButton2.setEnabled(true);
                this.catapro.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catapro.jButton4.setEnabled(false);
                this.catapro.jButton5.setEnabled(false);
                this.catapro.jButton4.setForeground(Color.gray);
                this.catapro.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catapro.jButton2) {
            this.pro.setVisible(true);
            this.pro.btnModificar.setVisible(false);

        }

        if (e.getSource() == catapro.jButton4) {
            this.pro.setVisible(true);
            this.pro.btnGuardar.setVisible(false);
            this.pro.jTextField2.setEnabled(false);

        }

        if (e.getSource() == catapro.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == pro.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == pro.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }
    
}
