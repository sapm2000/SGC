/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import vista.banco;
import vista.catalogoBanco;

/**
 *
 * @author rma
 */
public class controladorBanco implements ActionListener, MouseListener{

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
        this.cban.jTable1.addMouseListener(this);

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

    @Override
    public void mouseClicked(MouseEvent e) {
            // primero, obtengo la fila seleccionada

        int fila = this.cban.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.cban.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.cban.jTable1.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda
        cban.jTextField1.setText(String.valueOf(dato));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
