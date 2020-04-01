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
import vista.PantallaPrincipal;
import vista.PantallaPrincipal1;
import vista.catalogoCondominio;
import vista.condominio;

/**
 *
 * @author rma
 */
public class controladorCondominio implements ActionListener {
    
    private catalogoCondominio cataco;
    private condominio condo;
    private PantallaPrincipal1 panta1;
    private PantallaPrincipal panta;
    
    public controladorCondominio(catalogoCondominio cataco, condominio condo, PantallaPrincipal1 panta1, PantallaPrincipal panta) {
        this.cataco = cataco;
        this.condo = condo;
        this.panta1 = panta1;
        this.panta= panta;
        this.cataco.jButton1.addActionListener(this);
        this.cataco.jButton2.addActionListener(this);
        this.cataco.jButton4.addActionListener(this);
        this.cataco.jButton5.addActionListener(this);
        this.cataco.jButton7.addActionListener(this);
        this.condo.btnGuardar.addActionListener(this);
        
        this.condo.btnModificar.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == cataco.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {
                
                this.cataco.jButton4.setEnabled(true);
                this.cataco.jButton5.setEnabled(true);
                this.cataco.jButton7.setEnabled(true);
                this.cataco.jButton2.setEnabled(false);
                this.cataco.jButton2.setForeground(Color.gray);
                this.cataco.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.cataco.jButton5.setForeground(new java.awt.Color(0, 94, 159));
                this.cataco.jButton7.setForeground(new java.awt.Color(0, 94, 159));
                
            } else {
                this.cataco.jButton2.setEnabled(true);
                this.cataco.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.cataco.jButton4.setEnabled(false);
                this.cataco.jButton5.setEnabled(false);
                this.cataco.jButton7.setEnabled(false);
                this.cataco.jButton4.setForeground(Color.gray);
                this.cataco.jButton5.setForeground(Color.gray);
                this.cataco.jButton7.setForeground(Color.gray);
                
            }
        }
        
        if (e.getSource() == cataco.jButton2) {
            this.condo.setVisible(true);
            this.condo.btnModificar.setVisible(false);
            this.condo.btnGuardar.setVisible(true);
            
        }
        
        if (e.getSource() == cataco.jButton4) {
            this.condo.setVisible(true);
            this.condo.btnGuardar.setVisible(false);
            this.condo.btnModificar.setVisible(true);
            
        }
        
        if (e.getSource() == cataco.jButton7) {
            this.panta1.setVisible(true);
            this.panta.dispose();
            this.cataco.dispose();
        }
        
        if (e.getSource() == cataco.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {
                
            }
            
        }
        
        if (e.getSource() == condo.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");
            
        }
        
        if (e.getSource() == condo.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");
            
        }
        
    }
}
