/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.banco;
import vista.catalogoBanco;

/**
 *
 * @author rma
 */
public class controladorBanco implements ActionListener, MouseListener, KeyListener{

    private banco ban;
    private catalogoBanco cban;
    DefaultTableModel dm;

    public controladorBanco(banco ban, catalogoBanco cban) {
        this.ban = ban;
        this.cban = cban;
        this.cban.jButton1.addActionListener(this);
        this.cban.jButton2.addActionListener(this);
       
        this.ban.btnGuardar.addActionListener(this);
        this.ban.btnLimpiar.addActionListener(this);
        this.ban.btnModificar.addActionListener(this);
        this.cban.jTable1.addMouseListener(this);
        this.cban.jTable1.addKeyListener(this);
        this.cban.jTextField1.addKeyListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        

        if (e.getSource() == cban.jButton2) {
            this.ban.setVisible(true);
            this.ban.btnModificar.setVisible(false);
            this.ban.btnGuardar.setVisible(true);

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
    
    
        private void filtro(String consulta, JTable jtableBuscar){
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
}


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        filtro(cban.jTextField1.getText(), cban.jTable1);
        
    }
    
}
