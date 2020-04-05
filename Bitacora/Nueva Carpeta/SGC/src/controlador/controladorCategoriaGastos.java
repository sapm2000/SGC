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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;
import vista.catalogoCategoriaGastos;
import vista.categoriaGastos;

/**
 *
 * @author rma
 */
public class controladorCategoriaGastos implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoCategoriaGastos catacg;
    private categoriaGastos cg;
    private CategoriaGasto modcg;
    DefaultTableModel dm;

    public controladorCategoriaGastos(catalogoCategoriaGastos catacg, categoriaGastos cg, CategoriaGasto modcg) {
        this.catacg = catacg;
        this.cg = cg;
        this.modcg = modcg;
        this.catacg.jButton1.addActionListener(this);
        this.catacg.jButton2.addActionListener(this);
        this.catacg.jButton4.addActionListener(this);
        this.catacg.jButton5.addActionListener(this);
        this.cg.btnGuardar.addActionListener(this);
        this.cg.btnLimpiar.addActionListener(this);
        this.cg.btnModificar.addActionListener(this);
        this.catacg.jTable2.addMouseListener(this);
        this.catacg.jTextField1.addKeyListener(this);
        this.catacg.addWindowListener(this);
        
    }
    
    public void Llenartabla(JTable tablaD) {

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Descripcion");
       

        Object[] columna = new Object[2];

        int numRegistro = modcg.lPerson().size();

        for (int i = 0; i < numRegistro; i++) {

          

            columna[0] = modcg.lPerson().get(i).getNombre();
            columna[1] = modcg.lPerson().get(i).getDescripcion();
            

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacg.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catacg.jButton4.setEnabled(true);
                this.catacg.jButton5.setEnabled(true);
                this.catacg.jButton2.setEnabled(false);
                this.catacg.jButton2.setForeground(Color.gray);
                this.catacg.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catacg.jButton5.setForeground(new java.awt.Color(0, 94, 159));
                Llenartabla(catacg.jTable2);

            } else {
                this.catacg.jButton2.setEnabled(true);
                this.catacg.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catacg.jButton4.setEnabled(false);
                this.catacg.jButton5.setEnabled(false);
                this.catacg.jButton4.setForeground(Color.gray);
                this.catacg.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catacg.jButton2) {
            this.cg.setVisible(true);
            this.cg.btnModificar.setVisible(false);
            this.cg.btnGuardar.setVisible(true);

        }

        if (e.getSource() == catacg.jButton4) {
            this.cg.setVisible(true);
            this.cg.btnGuardar.setVisible(false);
            this.cg.btnModificar.setVisible(true);

        }

        if (e.getSource() == catacg.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

        if (e.getSource() == cg.btnGuardar) {
            modcg.setNombre(cg.txtnombre.getText());
            modcg.setDescripcion(cg.txtdescripcion.getText());

            if (modcg.registrar(modcg)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Guardar");

            }

        }

        if (e.getSource() == cg.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.catacg.jTable2.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catacg.jTable2.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catacg.jTable2.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda
        catacg.jTextField1.setText(String.valueOf(dato));
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

    private void filtro(String consulta, JTable jtableBuscar) {
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
    public void keyReleased(KeyEvent e) {
        filtro(catacg.jTextField1.getText(), catacg.jTable2);
    }

    @Override
    public void windowOpened(WindowEvent e) {
       Llenartabla(catacg.jTable2);
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

}
