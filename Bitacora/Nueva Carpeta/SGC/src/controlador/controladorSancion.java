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
import vista.catalogoSancion;
import vista.sancion;

/**
 *
 * @author rma
 */
public class controladorSancion implements ActionListener{
    
    private sancion san;
    private catalogoSancion catasan;

    public controladorSancion(sancion san, catalogoSancion catasan) {
        this.san = san;
        this.catasan = catasan;
       this.catasan.jButton1.addActionListener(this);
        this.catasan.jButton2.addActionListener(this);
        this.catasan.jButton4.addActionListener(this);
        this.catasan.jButton5.addActionListener(this);
        this.san.btnGuardar.addActionListener(this);
        this.san.btnLimpiar.addActionListener(this);
        this.san.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catasan.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catasan.jButton4.setEnabled(true);
                this.catasan.jButton5.setEnabled(true);
                this.catasan.jButton2.setEnabled(false);
                this.catasan.jButton2.setForeground(Color.gray);
                this.catasan.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catasan.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catasan.jButton2.setEnabled(true);
                this.catasan.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catasan.jButton4.setEnabled(false);
                this.catasan.jButton5.setEnabled(false);
                this.catasan.jButton4.setForeground(Color.gray);
                this.catasan.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catasan.jButton2) {
            this.san.setVisible(true);
            this.san.btnModificar.setVisible(false);
            this.san.btnGuardar.setVisible(true);

        }

        if (e.getSource() == catasan.jButton4) {
            this.san.setVisible(true);
            this.san.btnGuardar.setVisible(false);
            this.san.btnModificar.setVisible(true);

        }

        if (e.getSource() == catasan.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == san.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == san.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }
}
