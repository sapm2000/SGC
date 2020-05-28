/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Condominio;
import modelo.Interes;

import vista.catalogoInteres;
import vista.interes;

/**
 *
 * @author rma
 */
public class controladorInteres implements ActionListener, MouseListener, KeyListener, WindowListener {

    private interes in;
    private catalogoInteres catain;
    ArrayList<Condominio> listaCondo;

    private Interes modin;

    DefaultTableModel dm;
    ArrayList<Interes> listainteres;
    ArrayList<Interes> listainteresmod;

    public controladorInteres() {
        this.in = new interes();
        this.catain = new catalogoInteres();
        this.catain.setVisible(true);
        this.modin = new Interes();

        this.catain.btnNuevoInteres.addActionListener(this);
        this.catain.jTable1.addMouseListener(this);
        this.catain.jTextField1.addKeyListener(this);
        this.catain.addWindowListener(this);

        this.in.btnGuardar.addActionListener(this);
        this.in.btnLimpiar.addActionListener(this);
        this.in.btnModificar.addActionListener(this);
        this.in.btnEliminar.addActionListener(this);
        in.txtNombreinteres.addKeyListener(this);
        in.txtFactor.addKeyListener(this);

    }

    public void Llenartablainteres(JTable tablaD) {

        listainteres = modin.listarInteres();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Interes</html>");
        modeloT.addColumn("<html>Nombre de<br> Interes</html>");
        modeloT.addColumn("Factor");

        Object[] columna = new Object[5];

        int numRegistro = listainteres.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listainteres.get(i).getId();
            columna[1] = listainteres.get(i).getNombre();
            columna[2] = listainteres.get(i).getFactor();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catain.btnNuevoInteres) {
            this.in.setVisible(true);
            this.in.btnModificar.setEnabled(false);
            this.in.btnGuardar.setEnabled(true);
            this.in.btnEliminar.setEnabled(false);

            in.txtId.setVisible(false);
            in.txtFactor.setText("");
            in.txtNombreinteres.setText("");
        }

        if (e.getSource() == in.btnGuardar) {
            if (validar()) {
                modin.setNombre(in.txtNombreinteres.getText());
                modin.setFactor(Integer.parseInt(in.txtFactor.getText()));
                if (modin.buscarduplicados(modin)) {
                    JOptionPane.showMessageDialog(null, "este registro ya existe");
                } else {

                    if (modin.buscarInactivo(modin)) {
                        modin.activarInteres(modin);
                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartablainteres(catain.jTable1);
                        limpiar();
                    } else {

                        if (modin.registrarinteres(modin)) {
                            JOptionPane.showMessageDialog(null, "Registro Guardado");
                            Llenartablainteres(catain.jTable1);
                        }

                    }
                }
            }

        }

        if (e.getSource() == in.btnModificar) {
            if (validar()) {

                modin.setNombre(in.txtNombreinteres.getText());
                modin.setFactor(Double.parseDouble(in.txtFactor.getText()));

                modin.setId(Integer.parseInt(in.txtId.getText()));
                int x = modin.getId();
                if (modin.buscarduplicados(modin) && modin.getId() != x) {
                    JOptionPane.showMessageDialog(null, "este registro ya existe");
                } else {
                    if (modin.buscarInactivo(modin)) {
                        JOptionPane.showMessageDialog(null, "no puede colocar un interes que ya existio, si quiere colocar este interes debe registrarlo nuevamente");

                    } else {
                        if (modin.modificar_Interes(modin)) {

                            JOptionPane.showMessageDialog(null, "Registro Modificado");

                            Llenartablainteres(catain.jTable1);
                            in.dispose();
                        }

                    }
                }
            }
        }

        if (e.getSource() == in.btnEliminar) {
            modin.setId(Integer.parseInt(in.txtId.getText()));

            modin.eliminarInteres(modin);
            JOptionPane.showMessageDialog(null, "registro eliminado");
            in.dispose();
            Llenartablainteres(catain.jTable1);
        }
        if (e.getSource() == in.btnLimpiar) {

            limpiar();

        }
    }

    public void limpiar() {

        in.txtNombreinteres.setText(null);
        in.txtFactor.setText(null);

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (in.txtFactor.getText().isEmpty()) {

            msj += "El campo factor de interes no puede estar vacio\n";
            resultado = false;
        }

        if (in.txtNombreinteres.getText().isEmpty()) {

            msj += "El campo nombre de interes no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catain.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada

        String dato = String.valueOf(this.catain.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        modin.setId(Integer.parseInt(String.valueOf(dato)));

        modin.buscarInteres(modin);

        in.setVisible(true);
        in.btnEliminar.setEnabled(true);
        in.btnGuardar.setEnabled(false);
        in.btnModificar.setEnabled(true);

        in.txtFactor.setText(String.valueOf(modin.getFactor()));
        in.txtNombreinteres.setText(modin.getNombre());
        in.txtId.setText(dato);
        in.txtId.setVisible(false);

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
        if (e.getSource() == in.txtFactor) {
            Validacion.limite(e, in.txtFactor.getText(), 6);
            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, in.txtFactor.getText());
        }
        if (e.getSource() == in.txtNombreinteres) {
            Validacion.limite(e, in.txtNombreinteres.getText(), 100);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catain.jTextField1) {

            filtro(catain.jTextField1.getText(), catain.jTable1);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartablainteres(catain.jTable1);

        Component[] components = in.jPanel2.getComponents();
        JComponent[] com = {
            in.txtFactor, in.txtNombreinteres
        };
        Validacion.copiar(components);
        Validacion.pegar(com);
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
