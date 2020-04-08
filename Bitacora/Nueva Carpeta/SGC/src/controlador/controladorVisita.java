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
import vista.registroVisitas;
import vista.visitasAutorizadas;

/**
 *
 * @author rma
 */
public class controladorVisita implements ActionListener {

    private registroVisitas regv;
    private visitasAutorizadas visi;

    public controladorVisita(registroVisitas regv, visitasAutorizadas visi) {
        this.regv = regv;
        this.visi = visi;
        this.regv.jButton1.addActionListener(this);
        this.regv.jButton2.addActionListener(this);
        this.regv.jButton3.addActionListener(this);

        this.visi.jButton1.addActionListener(this);
        

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == regv.jButton2) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.regv.jButton1.setEnabled(true);
                this.regv.jButton3.setEnabled(false);
                this.regv.jButton3.setForeground(Color.gray);
                this.regv.jButton1.setForeground(Color.white);

            } else {
                this.regv.jButton3.setEnabled(true);
                this.regv.jButton1.setEnabled(false);
                this.regv.jButton1.setForeground(Color.gray);
                this.regv.jButton3.setForeground(Color.white);

            }
        }

        if (e.getSource() == regv.jButton1) {
             JOptionPane.showMessageDialog(null,"registro guardado");

        }

        if (e.getSource() == regv.jButton3) {
            this.visi.setVisible(true);
           

        }

       

        if (e.getSource() == visi.jButton1) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

    }

}
