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
import vista.catalogocomunicados;
import vista.comunicados;

/**
 *
 * @author rma
 */
public class controladorComunicados implements ActionListener{
    private catalogocomunicados catacom;
    private comunicados com;

    public controladorComunicados(catalogocomunicados catacom, comunicados com) {
        this.catacom = catacom;
        this.com = com;
       this.catacom.jButton1.addActionListener(this);
        this.catacom.jButton2.addActionListener(this);
        
        this.com.btnGuardar.addActionListener(this);
       
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacom.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

               
                this.catacom.jButton2.setEnabled(false);
                this.catacom.jButton2.setForeground(Color.gray);
   
            } else {
                this.catacom.jButton2.setEnabled(true);
                this.catacom.jButton2.setForeground(new java.awt.Color(0, 94, 159));
              
            }
        }

        if (e.getSource() == catacom.jButton2) {
            this.com.setVisible(true);
           

        }


        if (e.getSource() == com.btnGuardar) {
            JOptionPane.showMessageDialog(null, "mensaje enviado");

        }

      
    }

}
