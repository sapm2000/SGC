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
import vista.catalogoUnidades;
import vista.detalleRecibo;
import vista.unidades;
import vista.detallecuenta;

/**
 *
 * @author rma
 */
public class controladorUnidades implements ActionListener {

    private unidades uni;
    private catalogoUnidades catauni;
    private detallecuenta detacun;
    private detalleRecibo detare;
    
 public controladorUnidades(unidades uni, catalogoUnidades catauni, detallecuenta detacun,detalleRecibo detare) {
      this.uni = uni;
        this.catauni = catauni;
        this.detacun = detacun;
        this.detare = detare;
        
        this.catauni.jButton1.addActionListener(this);
        this.catauni.jButton2.addActionListener(this);
        this.catauni.jButton4.addActionListener(this);
        this.catauni.jButton5.addActionListener(this);
        this.uni.btnGuardar.addActionListener(this);
        this.uni.btnLimpiar.addActionListener(this);
        this.uni.btnModificar.addActionListener(this);
        this.catauni.jButton7.addActionListener(this);
        this.detacun.jButton1.addActionListener(this);
        this.detacun.jButton2.addActionListener(this);
    }

   
    
    

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catauni.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catauni.jButton4.setEnabled(true);
                this.catauni.jButton5.setEnabled(true);
                this.catauni.jButton7.setEnabled(true);
                this.catauni.jButton2.setEnabled(false);
                this.catauni.jButton2.setForeground(Color.gray);
                this.catauni.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catauni.jButton5.setForeground(new java.awt.Color(0, 94, 159));
                this.catauni.jButton7.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catauni.jButton2.setEnabled(true);
                this.catauni.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catauni.jButton4.setEnabled(false);
                this.catauni.jButton5.setEnabled(false);
                this.catauni.jButton7.setEnabled(false);
                this.catauni.jButton4.setForeground(Color.gray);
                this.catauni.jButton5.setForeground(Color.gray);
                this.catauni.jButton7.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catauni.jButton2) {
            this.uni.setVisible(true);
            this.uni.btnModificar.setVisible(false);
            

        }
        
        if (e.getSource() == catauni.jButton7) {
            this.detacun.setVisible(true);
            this.detacun.jButton1.setEnabled(false);
            this.detacun.jButton1.setForeground(Color.gray);

        }

        if (e.getSource() == catauni.jButton4) {
            this.uni.setVisible(true);
            this.uni.btnGuardar.setVisible(false);
            this.uni.jTextField1.setEnabled(false);

        }

        if (e.getSource() == catauni.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == uni.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == uni.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
        
         if (e.getSource() == detacun.jButton2) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.detacun.jButton1.setEnabled(true);
                
                this.detacun.jButton1.setForeground(Color.WHITE);

            } else {
                

            }
        }
         
           if (e.getSource() == detacun.jButton1) {
            this.detare.setVisible(true);

        }
        
    }

}
