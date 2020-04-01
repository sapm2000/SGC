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
import vista.catalogoTipoUsuario;
import vista.tipoUsuario;

/**
 *
 * @author rma
 */
public class controladorTipoUsuario implements ActionListener {

    private catalogoTipoUsuario catati;
    private tipoUsuario ti;

    public controladorTipoUsuario(catalogoTipoUsuario catati, tipoUsuario ti) {
        this.catati = catati;
        this.ti = ti;
        this.catati.jButton1.addActionListener(this);
        this.catati.jButton2.addActionListener(this);
        this.catati.jButton4.addActionListener(this);
        this.catati.jButton5.addActionListener(this);
        this.ti.btnGuardar.addActionListener(this);

        this.ti.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catati.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catati.jButton4.setEnabled(true);
                this.catati.jButton5.setEnabled(true);
                this.catati.jButton2.setEnabled(false);
                this.catati.jButton2.setForeground(Color.gray);
                this.catati.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catati.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catati.jButton2.setEnabled(true);
                this.catati.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catati.jButton4.setEnabled(false);
                this.catati.jButton5.setEnabled(false);
                this.catati.jButton4.setForeground(Color.gray);
                this.catati.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catati.jButton2) {
            this.ti.setVisible(true);
            this.ti.btnModificar.setVisible(false);

        }

        if (e.getSource() == catati.jButton4) {
            this.ti.setVisible(true);
            this.ti.btnGuardar.setVisible(false);

        }

        if (e.getSource() == catati.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == ti.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == ti.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }

    }
}
