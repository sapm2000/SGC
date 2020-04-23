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
      
        this.catasan.jButton2.addActionListener(this);
        
        this.san.btnGuardar.addActionListener(this);
        this.san.btnLimpiar.addActionListener(this);
        this.san.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        

        if (e.getSource() == catasan.jButton2) {
            this.san.setVisible(true);
            this.san.btnModificar.setEnabled(false);
            this.san.btnGuardar.setEnabled(true);

        }

       

        if (e.getSource() == san.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == san.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }
}
