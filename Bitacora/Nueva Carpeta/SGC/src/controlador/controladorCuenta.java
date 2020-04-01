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
import vista.catalogoCuenta;
import vista.cuenta;

/**
 *
 * @author rma
 */
public class controladorCuenta implements ActionListener{
    
    private catalogoCuenta catacu;
    private cuenta cu;

    public controladorCuenta(catalogoCuenta catacu, cuenta cu) {
        this.catacu = catacu;
        this.cu = cu;
         this.catacu.jButton1.addActionListener(this);
        this.catacu.jButton2.addActionListener(this);
        this.catacu.jButton4.addActionListener(this);
        this.catacu.jButton5.addActionListener(this);
        this.cu.btnGuardar.addActionListener(this);
        this.cu.btnLimpiar.addActionListener(this);
        this.cu.btnModificar.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacu.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catacu.jButton4.setEnabled(true);
                this.catacu.jButton5.setEnabled(true);
                this.catacu.jButton2.setEnabled(false);
                this.catacu.jButton2.setForeground(Color.gray);
                this.catacu.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catacu.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catacu.jButton2.setEnabled(true);
                this.catacu.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catacu.jButton4.setEnabled(false);
                this.catacu.jButton5.setEnabled(false);
                this.catacu.jButton4.setForeground(Color.gray);
                this.catacu.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catacu.jButton2) {
            this.cu.setVisible(true);
            this.cu.btnModificar.setVisible(false);

        }

        if (e.getSource() == catacu.jButton4) {
            this.cu.setVisible(true);
            this.cu.btnGuardar.setVisible(false);

        }

        if (e.getSource() == catacu.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == cu.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == cu.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }
    
    
    
}
