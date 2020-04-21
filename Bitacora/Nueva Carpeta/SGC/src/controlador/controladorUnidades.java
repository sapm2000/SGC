/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.catalogoUnidades;
import vista.detalleRecibo;
import vista.unidades;
import vista.detallecuenta;

/**
 *
 * @author rma
 */
public class controladorUnidades implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private unidades uni;
    private catalogoUnidades catauni;
    private detallecuenta detacun;
    private detalleRecibo detare;
    private Unidades moduni;
    private PantallaPrincipal1 panta1;

    public controladorUnidades(unidades uni, catalogoUnidades catauni, detallecuenta detacun, detalleRecibo detare, Unidades moduni, PantallaPrincipal1 panta1) {
        this.uni = uni;
        this.catauni = catauni;
        this.detacun = detacun;
        this.detare = detare;
        this.moduni = moduni;
        this.panta1 = panta1;

        this.uni.jComboBox1.addItemListener(this);
        
        this.catauni.jButton2.addActionListener(this);
        
        
        this.uni.btnGuardar.addActionListener(this);
        this.uni.btnLimpiar.addActionListener(this);
        this.uni.btnModificar.addActionListener(this);
        this.catauni.jButton7.addActionListener(this);
        this.detacun.jButton1.addActionListener(this);
        this.detacun.jButton2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        

        if (e.getSource() == catauni.jButton2) {
            this.uni.setVisible(true);
            this.uni.btnModificar.setVisible(false);
            this.uni.btnGuardar.setVisible(true);
            this.uni.txtNumeroUnidad.setEnabled(true);
            uni.jComboBox1.removeAllItems();
            moduni.setId_condominio(panta1.rif.getText());
            moduni.llenar_propietarios(uni.jComboBox1);

        }

        if (e.getSource() == catauni.jButton7) {
            this.detacun.setVisible(true);
            this.detacun.jButton1.setEnabled(false);
            this.detacun.jButton1.setForeground(Color.gray);

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

    @Override
    public void mouseClicked(MouseEvent e) {
        
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

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

    
    @Override
    public void itemStateChanged(ItemEvent e) {
        switch (e.getStateChange()) {
        case ItemEvent.SELECTED:
         if (e.getSource()== uni.jComboBox1) {
            moduni.setId_propietario(uni.jComboBox1.getSelectedItem().toString());
            moduni.buscarnombre(moduni);
            uni.txtNombrePropietario.setText(moduni.getNombre());
            uni.txtCorreo.setText(moduni.getCorreo());
        }
        break;
        
        
        
    }
    }

}
