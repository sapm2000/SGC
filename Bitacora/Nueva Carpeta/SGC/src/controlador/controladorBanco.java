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
import vista.banco;
import vista.catalogoBanco;

/**
 *
 * @author rma
 */
public class controladorBanco implements ActionListener {

    private banco ban;
    private catalogoBanco cban;

    public controladorBanco(banco ban, catalogoBanco cban) {
        this.ban = ban;
        this.cban = cban;
        this.cban.jButton1.addActionListener(this);
        this.cban.jButton2.addActionListener(this);
        this.cban.jButton4.addActionListener(this);
        this.cban.jButton5.addActionListener(this);
        this.ban.btnGuardar.addActionListener(this);
        this.ban.btnLimpiar.addActionListener(this);
        this.ban.btnModificar.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cban.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.cban.jButton4.setEnabled(true);
                this.cban.jButton5.setEnabled(true);
                this.cban.jButton2.setEnabled(false);
                this.cban.jButton2.setForeground(Color.gray);
                this.cban.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.cban.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.cban.jButton2.setEnabled(true);
                this.cban.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.cban.jButton4.setEnabled(false);
                this.cban.jButton5.setEnabled(false);
                this.cban.jButton4.setForeground(Color.gray);
                this.cban.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == cban.jButton2) {
            this.ban.setVisible(true);
            this.ban.btnModificar.setVisible(false);
            this.ban.btnGuardar.setVisible(true);

        }

        if (e.getSource() == cban.jButton4) {
            this.ban.setVisible(true);
            this.ban.btnGuardar.setVisible(false);
            this.ban.btnModificar.setVisible(true);
        }

        if (e.getSource() == cban.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == ban.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == ban.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }

    }
}
