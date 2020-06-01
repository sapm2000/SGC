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
import modelo.Funcion;
import modelo.Interes;
import sgc.SGC;
import vista.Catalogo;
import vista.interes;

/**
 *
 * @author rma
 */
public class controladorInteres implements ActionListener, MouseListener, KeyListener, WindowListener {

    private interes vista;
    private Catalogo catalogo;
    ArrayList<Condominio> listaCondo;

    private Interes modin;

    Funcion permiso;

    DefaultTableModel dm;
    ArrayList<Interes> listainteres;
    ArrayList<Interes> listainteresmod;

    public controladorInteres() {
        this.vista = new interes();
        this.catalogo = new Catalogo();
        this.catalogo.setVisible(true);
        this.modin = new Interes();

        catalogo.lblTitulo.setText("Interés");
        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.addWindowListener(this);

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        vista.txtNombreinteres.addKeyListener(this);
        vista.txtFactor.addKeyListener(this);

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

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);

            vista.txtId.setVisible(false);
            vista.txtFactor.setText("");
            vista.txtNombreinteres.setText("");
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modin.setNombre(vista.txtNombreinteres.getText());
                modin.setFactor(Integer.parseInt(vista.txtFactor.getText()));
                if (modin.buscarduplicados(modin)) {
                    JOptionPane.showMessageDialog(null, "este registro ya existe");
                } else {

                    if (modin.buscarInactivo(modin)) {
                        modin.activarInteres(modin);
                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartablainteres(catalogo.tabla);
                        limpiar();
                    } else {

                        if (modin.registrarinteres(modin)) {
                            JOptionPane.showMessageDialog(null, "Registro Guardado");
                            Llenartablainteres(catalogo.tabla);
                        }

                    }
                }
            }

        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {

                modin.setNombre(vista.txtNombreinteres.getText());
                modin.setFactor(Double.parseDouble(vista.txtFactor.getText()));

                modin.setId(Integer.parseInt(vista.txtId.getText()));
                int x = modin.getId();
                if (modin.buscarduplicados(modin) && modin.getId() != x) {
                    JOptionPane.showMessageDialog(null, "este registro ya existe");
                } else {
                    if (modin.buscarInactivo(modin)) {
                        JOptionPane.showMessageDialog(null, "no puede colocar un interes que ya existio, si quiere colocar este interes debe registrarlo nuevamente");

                    } else {
                        if (modin.modificar_Interes(modin)) {

                            JOptionPane.showMessageDialog(null, "Registro Modificado");

                            Llenartablainteres(catalogo.tabla);
                            vista.dispose();
                        }

                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modin.setId(Integer.parseInt(vista.txtId.getText()));

            modin.eliminarInteres(modin);
            JOptionPane.showMessageDialog(null, "registro eliminado");
            vista.dispose();
            Llenartablainteres(catalogo.tabla);
        }
        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }
    }

    public void limpiar() {

        vista.txtNombreinteres.setText(null);
        vista.txtFactor.setText(null);

    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtFactor.getText().isEmpty()) {

            msj += "El campo factor de interes no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txtNombreinteres.getText().isEmpty()) {

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
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        modin.setId(Integer.parseInt(String.valueOf(dato)));

        modin.buscarInteres(modin);

        vista.setVisible(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnGuardar.setEnabled(false);
        vista.btnModificar.setEnabled(true);

        vista.txtFactor.setText(String.valueOf(modin.getFactor()));
        vista.txtNombreinteres.setText(modin.getNombre());
        vista.txtId.setText(dato);
        vista.txtId.setVisible(false);

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
        if (e.getSource() == vista.txtFactor) {
            Validacion.limite(e, vista.txtFactor.getText(), 6);
            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, vista.txtFactor.getText());
        }
        if (e.getSource() == vista.txtNombreinteres) {
            Validacion.limite(e, vista.txtNombreinteres.getText(), 100);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartablainteres(catalogo.tabla);

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtFactor, vista.txtNombreinteres
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
