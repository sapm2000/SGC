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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.registroVisitas;
import vista.visitasAutorizadas;
import modelo.Registro_visitante;

/**
 *
 * @author pbas
 */
public class controladorVisitante implements ActionListener, ItemListener, MouseListener, KeyListener, WindowListener {

    private visitasAutorizadas visi;
    private Registro_visitante modRvis;
    ArrayList<Registro_visitante> listaVis;
    DefaultTableModel dm;

    public controladorVisitante(registroVisitas regv, visitasAutorizadas visi, Registro_visitante modRvis) {
        this.visi = visi;
        this.modRvis = modRvis;
        this.visi.btnAgregar.addActionListener(this);
        this.visi.txtCedula.addKeyListener(this);
        this.visi.txtNombre.addKeyListener(this);
        this.visi.txtApellido.addKeyListener(this);
        this.visi.tabla.addMouseListener(this);
        this.visi.addWindowListener(this);

    }

    public void limpiar() {
        visi.txtCedula.setText(null);
        visi.txtNombre.setText(null);
        visi.txtApellido.setText(null);

    }

    private Boolean validar() {
        Boolean resultado = true;
        String msj = "";

        if (visi.txtCedula.getText().isEmpty()) {

            msj += "El campo Cédula no puede estar vacío\n";
            resultado = false;
        }
        if (visi.txtNombre.getText().isEmpty()) {

            msj += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }
        if (visi.txtApellido.getText().isEmpty()) {

            msj += "El campo Apellido no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    public void Llenartabla(JTable tablaD) {
        listaVis = modRvis.listarVisitante();

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");

        Object[] columna = new Object[3];

        int numRegistro = listaVis.size();

        for (int i = 0; i < numRegistro; i++) {
            columna[0] = listaVis.get(i).getCedula();
            columna[1] = listaVis.get(i).getNombre();
            columna[2] = listaVis.get(i).getApellido();

            modeloT.addRow(columna);

        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == visi.btnAgregar) {
            if (validar()) {
                String cedula;

                if (visi.cbxCedula.getSelectedItem() != "--") {

                    cedula = visi.cbxCedula.getSelectedItem() + "-" + visi.txtCedula.getText();
                    modRvis.setCedula(cedula);
                    modRvis.setNombre(visi.txtNombre.getText());
                    modRvis.setApellido(visi.txtApellido.getText());

                    if (modRvis.registrarVisitante(modRvis)) {

                        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                        Llenartabla(visi.tabla);

                    } else {

                        JOptionPane.showMessageDialog(null, "Registro Duplicado");

                    }
                } else if (visi.cbxCedula.getSelectedItem() == "--") {

                    JOptionPane.showMessageDialog(null, "SELECCIONE NACIONALIDAD");

                }

            }
            
            Llenartabla(visi.tabla);
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
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.visi.tabla) {
            int fila = this.visi.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.visi.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

            String[] arregloCedula;
            String nacionalidad;
            String cedula;

            modRvis.setCedula(String.valueOf(dato));
            modRvis.buscarVisitante(modRvis);

            visi.setVisible(true);
            
            cedula = modRvis.getCedula();
            arregloCedula = cedula.split("-");
            nacionalidad = arregloCedula[0];
            cedula = arregloCedula[1];

            visi.cbxCedula.setSelectedItem(nacionalidad);
            visi.txtCedula.setText(cedula);
            visi.txtNombre.setText(modRvis.getNombre());
            visi.txtApellido.setText(modRvis.getApellido());
            visi.txtCedula.setEnabled(false);

            visi.btnAgregar.setEnabled(true);
        }
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
        Llenartabla(visi.tabla);
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
