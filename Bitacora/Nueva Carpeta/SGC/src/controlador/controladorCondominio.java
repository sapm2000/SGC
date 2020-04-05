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
        this.cataco.jButton1.addActionListener(this);
        this.cataco.jButton2.addActionListener(this);
        this.cataco.jButton4.addActionListener(this);
        this.cataco.jButton5.addActionListener(this);
        this.cataco.jButton7.addActionListener(this);
        this.condo.btnGuardar.addActionListener(this);

        this.condo.btnModificar.addActionListener(this);
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
            co.setRif(condo.txtRif.getText());
            co.setRazonS(condo.txtRazonS.getText());
            co.setTelefono(condo.txtTelefono.getText());
            co.setCorreoElectro(condo.txtCorreo.getText());

            if (co.registrar(co)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Guardar");

            }

        }

        if (e.getSource() == condo.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.cataco.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.cataco.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.cataco.jTable1.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda
        cataco.txtBuscar.setText(String.valueOf(dato));
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
