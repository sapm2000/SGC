/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Asambleas;
import modelo.Propietarios;
import vista.PantallaPrincipal1;
import vista.asambleas;
import vista.catalogoAsambleas;

/**
 *
 * @author rma
 */
public class controladorAsambleas implements ActionListener, KeyListener, MouseListener, WindowListener {

    private catalogoAsambleas cataa;
    private asambleas as;
    DefaultTableModel dm;
    ArrayList<Propietarios> listaPropietarios;
    private Asambleas modasa;
    private Propietarios modpro;
    private PantallaPrincipal1 panta1;

    public controladorAsambleas(catalogoAsambleas cataa, asambleas as, Asambleas modasa, Propietarios modpro, PantallaPrincipal1 panta1) {
        this.cataa = cataa;
        this.as = as;
        this.modasa = modasa;
        this.modpro = modpro;
        this.panta1 = panta1;

        this.cataa.jButton2.addActionListener(this);

        this.as.btnGuardar.addActionListener(this);

        this.as.btnModificar.addActionListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        listaPropietarios = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Cedula/Rif");
        modeloT.addColumn("Nombre/razon social");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Telefono");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[5];

        int numRegistro = listaPropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaPropietarios.get(i).getCedula();
            columna[1] = listaPropietarios.get(i).getNombre();
            columna[2] = listaPropietarios.get(i).getApellido();
            columna[3] = listaPropietarios.get(i).getTelefono();
            columna[4] = listaPropietarios.get(i).getCorreo();

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cataa.jButton2) {
            this.as.setVisible(true);
            this.as.btnModificar.setVisible(false);
            this.as.btnGuardar.setVisible(true);
            modpro.setId_condominio(panta1.rif.getText());
            Llenartabla(as.jTable1);
            addCheckBox(5, as.jTable1);

        }

        if (e.getSource() == as.btnGuardar) {
            if (validar()) {
                modasa.setNombre_asamblea(as.txtNombreAsamblea.getText());
                modasa.setDescripcion(as.txaDescripcion.getText());
                modasa.setId_condominio(panta1.rif.getText());

                java.sql.Date sqlDate = convert(as.jDateChooser2.getDate());
                modasa.setFecha(sqlDate);
                JOptionPane.showMessageDialog(null, modasa.getFecha());
                
                as.jDateChooser2.setDate(null);
                
                as.jDateChooser2.setDate(sqlDate);

                if (modasa.registrar(modasa)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == as.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public boolean IsSelected(int row, int column, JTable table) {

        return table.getValueAt(row, column) != null;
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (as.txtNombreAsamblea.getText().isEmpty()) {

            msj += "El campo numero de Cuenta no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

}
