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
import modelo.Condominio;
import vista.PantallaPrincipal;
import vista.PantallaPrincipal1;
import vista.catalogoCondominio;
import vista.condominio;

/**
 *
 * @author rma
 */
public class controladorCondominio implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoCondominio cataco;
    private condominio condo;
    private PantallaPrincipal1 panta1;
    private PantallaPrincipal panta;
    DefaultTableModel dm;
    private Condominio co;

    public controladorCondominio(catalogoCondominio cataco, condominio condo, PantallaPrincipal1 panta1, PantallaPrincipal panta, Condominio co) {
        this.cataco = cataco;
        this.condo = condo;
        this.panta1 = panta1;
        this.panta = panta;
        this.co = co;
        this.cataco.jButton2.addActionListener(this);
        this.condo.btnGuardar.addActionListener(this);
        this.condo.btnEliminar.addActionListener(this);
        this.condo.btnModificar.addActionListener(this);
        this.condo.btnLimpiar.addActionListener(this);
        this.cataco.jTable1.addMouseListener(this);
        this.cataco.txtBuscar.addKeyListener(this);
        this.cataco.addWindowListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Rif.");
        modeloT.addColumn("Razon Social");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo Electrónico");

        Object[] columna = new Object[4];

        int num = co.lPerson().size();

        for (int i = 0; i < num; i++) {

            columna[0] = co.lPerson().get(i).getRif();
            columna[1] = co.lPerson().get(i).getRazonS();
            columna[2] = co.lPerson().get(i).getTelefono();
            columna[3] = co.lPerson().get(i).getCorreoElectro();

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {



        if (e.getSource() == cataco.jButton2) {
            this.condo.setVisible(true);
            this.condo.btnModificar.setEnabled(false);
            this.condo.btnGuardar.setEnabled(true);
            this.condo.btnEliminar.setEnabled(false);
            condo.txtRif.setText("");
            condo.txtRazonS.setText("");
            condo.txtTelefono.setText("");
            condo.txtCorreo.setText("");
        }



        if (e.getSource() == condo.btnGuardar) {
            co.setRif(condo.txtRif.getText());
            co.setRazonS(condo.txtRazonS.getText());
            co.setTelefono(condo.txtTelefono.getText());
            co.setCorreoElectro(condo.txtCorreo.getText());

            if (co.registrar(co)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");
                Llenartabla(cataco.jTable1);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Guardar");

            }

        }

        if (e.getSource() == condo.btnModificar) {
             co.setRif(condo.txtRif.getText());
             co.setRazonS(condo.txtRazonS.getText());
             co.setTelefono(condo.txtTelefono.getText());
             co.setCorreoElectro(condo.txtCorreo.getText());
             
             
             
             if (co.modificar(co)) {

                JOptionPane.showMessageDialog(null, "Registro modificado");
                condo.dispose();
                Llenartabla(cataco.jTable1);
                

            } else {

                JOptionPane.showMessageDialog(null, "Error al Modificar");
                
            }
        }

         if (e.getSource() == condo.btnEliminar) {
           
           

            if (co.eliminar(co)) {

                co.setRif(condo.txtRif.getText());
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                condo.dispose();
                Llenartabla(cataco.jTable1);
                

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
         
        if (e.getSource() == condo.btnLimpiar) {
             
             condo.txtRif.setText("");
             condo.txtRazonS.setText("");
             condo.txtTelefono.setText("");
             condo.txtCorreo.setText("");
             
             
            
        }
        
    }

    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.cataco.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.cataco.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.cataco.jTable1.getValueAt(fila,0)); // por ultimo, obtengo el valor de la celda
        co.setRif(String.valueOf(dato));
        
        co.Buscar(co);

        condo.setVisible(true);
        condo.txtRif.setText(co.getRif());
        condo.txtRazonS.setText(co.getRazonS());
        condo.txtTelefono.setText(co.getTelefono());
        condo.txtCorreo.setText(co.getCorreoElectro());
        
        condo.btnGuardar.setEnabled(false);
        
        condo.btnModificar.setEnabled(true);
        condo.btnEliminar.setEnabled(true);
        
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
        filtro(cataco.txtBuscar.getText(), cataco.jTable1);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(cataco.jTable1);
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
