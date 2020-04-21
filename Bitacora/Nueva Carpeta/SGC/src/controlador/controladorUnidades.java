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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.buscarPropietario;
import vista.catalogoUnidades;
import vista.detalleRecibo;
import vista.unidades;
import vista.detallecuenta;

/**
 *
 * @author rma
 */
public class controladorUnidades implements ActionListener, MouseListener, KeyListener, WindowListener {

    private unidades uni;
    private catalogoUnidades catauni;
    private detallecuenta detacun;
    private detalleRecibo detare;
    private Unidades moduni;
    private PantallaPrincipal1 panta1;
    private buscarPropietario buscp;
    ArrayList<Unidades> listapropietarios;
    DefaultTableModel dm;

    public controladorUnidades(unidades uni, catalogoUnidades catauni, detallecuenta detacun, detalleRecibo detare, Unidades moduni, PantallaPrincipal1 panta1, buscarPropietario buscp) {
        this.uni = uni;
        this.catauni = catauni;
        this.detacun = detacun;
        this.detare = detare;
        this.moduni = moduni;
        this.panta1 = panta1;
        this.buscp = buscp;

        this.catauni.jButton2.addActionListener(this);
        this.buscp.txtBuscarProp.addKeyListener(this);
        this.buscp.tablaprop.addMouseListener(this);

        this.uni.btnBuscarpropietarios.addActionListener(this);
        this.uni.btnGuardar.addActionListener(this);
        this.uni.btnLimpiar.addActionListener(this);
        this.uni.btnModificar.addActionListener(this);
        this.catauni.jButton7.addActionListener(this);
        this.detacun.jButton1.addActionListener(this);
        this.detacun.jButton2.addActionListener(this);
    }

    public void llenartablapropietarios(JTable tablaD) {

        listapropietarios = moduni.buscarPropietario();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Cedula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Telefoo");
        modeloT.addColumn("Correo");

        Object[] columna = new Object[4];

        int numRegistro = listapropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listapropietarios.get(i).getCedula();
            columna[1] = listapropietarios.get(i).getNombre();
            columna[2] = listapropietarios.get(i).getTelefono();
            columna[3] = listapropietarios.get(i).getCorreo();

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catauni.jButton2) {
            this.uni.setVisible(true);
            this.uni.btnModificar.setVisible(false);
            this.uni.btnGuardar.setVisible(true);
            this.uni.txtNumeroUnidad.setEnabled(true);

            moduni.setId_condominio(panta1.rif.getText());

        }

        if (e.getSource() == uni.btnBuscarpropietarios) {
            this.buscp.setVisible(true);
            llenartablapropietarios(buscp.tablaprop);
        }

        if (e.getSource() == catauni.jButton7) {
            this.detacun.setVisible(true);
            this.detacun.jButton1.setEnabled(false);
            this.detacun.jButton1.setForeground(Color.gray);

        }

        if (e.getSource() == uni.btnGuardar) {
            if (validar()) {
                moduni.setCedula(uni.txtCedula.getText());
                moduni.setN_unidad(uni.txtNumeroUnidad.getText());
                moduni.setArea(Integer.parseInt(uni.txtArea.getText()));
                moduni.setDireccion(uni.txadireccion.getText());

                if (moduni.registrarUnidades(moduni)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

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
        int fila = this.buscp.tablaprop.getSelectedRow(); // primero, obtengo la fila seleccionada
        uni.txtCedula.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 0)));
        uni.txtNombrePropietario.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 1)));
        uni.txtCorreo.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 2)));
        uni.txtTelefono.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 3)));
        buscp.dispose();
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
        if (e.getSource() == catauni.jTextField1) {

            filtro(catauni.jTextField1.getText(), catauni.jTable1);
        }
        if (e.getSource() == buscp.txtBuscarProp) {
            filtro(buscp.txtBuscarProp.getText(), buscp.tablaprop);
        }
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (uni.txtCedula.getText().isEmpty()) {

            msj += "El campo numero de Cuenta no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
