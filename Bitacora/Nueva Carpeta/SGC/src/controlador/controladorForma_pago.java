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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.FormaPago;
import modelo.Funcion;
import sgc.SGC;
import vista.catalogoFormaDePago;
import vista.formaDePago;

/**
 *
 * @author rma
 */
public class controladorForma_pago implements ActionListener, KeyListener, MouseListener, WindowListener {

    private FormaPago modfor;
    private formaDePago vista;
    Funcion permiso;
    private catalogoFormaDePago catalogo;
    DefaultTableModel dm;

    ArrayList<FormaPago> listaFormaPago;

    public controladorForma_pago() {
        this.modfor = new FormaPago();
        this.vista = new formaDePago();
        this.catalogo = new catalogoFormaDePago();
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.addWindowListener(this);
        this.catalogo.JTablaFormaPago.addMouseListener(this);
        this.catalogo.txtBuscarFormaPago.addKeyListener(this);
        catalogo.setVisible(true);

    }

    public void Llenartabla(JTable tablaD) {

        listaFormaPago = modfor.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Forma Pago");

        Object[] columna = new Object[1];

        int numRegistro = listaFormaPago.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaFormaPago.get(i).getForma_pago();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {

            this.vista.setVisible(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.txtid.setVisible(false);
            this.vista.btnModificar.setEnabled(false);
            vista.txtFormaPago.setText("");
            vista.txtid.setText("");

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modfor.setForma_pago(vista.txtFormaPago.getText());

                if (modfor.buscarInactivo(modfor)) {
                    modfor.activar(modfor);
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catalogo.JTablaFormaPago);
                    limpiar();
                } else {

                    if (modfor.registrar(modfor)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(catalogo.JTablaFormaPago);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modfor.setForma_pago(vista.txtFormaPago.getText());
                modfor.setId(Integer.parseInt(vista.txtid.getText()));
                if (modfor.buscarInactivo(modfor)) {
                    JOptionPane.showMessageDialog(null, "no puede colocar el nombre de un metodo de pago que ya existio, si quiere colocar este nombre debe registrarlo nuevamente");
                } else {

                    if (modfor.modificar(modfor)) {

                        JOptionPane.showMessageDialog(null, "Registro modificado");
                        vista.dispose();
                        Llenartabla(catalogo.JTablaFormaPago);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {

            modfor.setId(Integer.parseInt(vista.txtid.getText()));

            if (modfor.eliminar(modfor)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                vista.dispose();
                Llenartabla(catalogo.JTablaFormaPago);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
    }

    public void limpiar() {

        vista.txtFormaPago.setText(null);

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

        if (vista.txtFormaPago.getText().isEmpty()) {

            msj += "El campo nombre del banco no puede estar vacío\n";
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscarFormaPago) {

            filtro(catalogo.txtBuscarFormaPago.getText(), catalogo.JTablaFormaPago);
        } else {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.JTablaFormaPago.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.JTablaFormaPago.getSelectedColumn(); // luego, obtengo la columna seleccionada

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        String dato = String.valueOf(this.catalogo.JTablaFormaPago.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda

        modfor.setForma_pago(String.valueOf(dato));

        modfor.buscar(modfor);

        vista.setVisible(true);
        vista.txtFormaPago.setText(modfor.getForma_pago());

        vista.txtid.setText(Integer.toString(modfor.getId()));

        vista.btnGuardar.setEnabled(false);
        vista.txtid.setVisible(false);
        vista.btnModificar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
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
        Llenartabla(catalogo.JTablaFormaPago);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }
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
