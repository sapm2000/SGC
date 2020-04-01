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
import vista.catalogoProveedores;
import vista.proveedores;

/**
 *
 * @author rma
 */
public class controladorProveedores implements ActionListener {

    private catalogoProveedores cataprov;
    private proveedores prov;

    public controladorProveedores(catalogoProveedores cataprov, proveedores prov) {
        this.cataprov = cataprov;
        this.prov = prov;
        this.cataprov.jButton1.addActionListener(this);
        this.cataprov.jButton2.addActionListener(this);
        this.cataprov.jButton8.addActionListener(this);
        this.cataprov.jButton5.addActionListener(this);
        this.prov.btnGuardar.addActionListener(this);
        this.prov.btnLimpiar.addActionListener(this);
        this.prov.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cataprov.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.cataprov.jButton8.setEnabled(true);
                this.cataprov.jButton5.setEnabled(true);
                this.cataprov.jButton2.setEnabled(false);
                this.cataprov.jButton2.setForeground(Color.gray);
                this.cataprov.jButton8.setForeground(new java.awt.Color(0, 94, 159));
                this.cataprov.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.cataprov.jButton2.setEnabled(true);
                this.cataprov.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.cataprov.jButton8.setEnabled(false);
                this.cataprov.jButton5.setEnabled(false);
                this.cataprov.jButton8.setForeground(Color.gray);
                this.cataprov.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == cataprov.jButton2) {
            this.prov.setVisible(true);
            this.prov.btnModificar.setVisible(false);

        }

        if (e.getSource() == cataprov.jButton8) {
            this.prov.setVisible(true);
            this.prov.btnGuardar.setVisible(false);

        }

        if (e.getSource() == cataprov.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == prov.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == prov.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

}
