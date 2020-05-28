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
import vista.catalogoFormaDePago;
import vista.formaDePago;

/**
 *
 * @author rma
 */
public class controladorForma_pago implements ActionListener, KeyListener, MouseListener, WindowListener {

    private FormaPago modfor;
    private formaDePago vfor;
    private catalogoFormaDePago catafor;
    DefaultTableModel dm;

    ArrayList<FormaPago> listaFormaPago;

    public controladorForma_pago() {
        this.modfor = new FormaPago();
        this.vfor = new formaDePago();
        this.catafor = new catalogoFormaDePago();
        this.vfor.btnGuardar.addActionListener(this);
        this.vfor.btnLimpiar.addActionListener(this);
        this.vfor.btnEliminar.addActionListener(this);
        this.vfor.btnModificar.addActionListener(this);
        this.catafor.btnNueva_formaPago.addActionListener(this);
        this.catafor.addWindowListener(this);
        this.catafor.JTablaFormaPago.addMouseListener(this);
        this.catafor.txtBuscarFormaPago.addKeyListener(this);
        catafor.setVisible(true);

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

        if (e.getSource() == catafor.btnNueva_formaPago) {

            this.vfor.setVisible(true);
            this.vfor.btnEliminar.setEnabled(false);
            this.vfor.btnGuardar.setEnabled(true);
            this.vfor.txtid.setVisible(false);
            this.vfor.btnModificar.setEnabled(false);
            vfor.txtFormaPago.setText("");
            vfor.txtid.setText("");

        }

        if (e.getSource() == vfor.btnGuardar) {
            if (validar()) {
                modfor.setForma_pago(vfor.txtFormaPago.getText());

                if (modfor.buscarInactivo(modfor)) {
                    modfor.activar(modfor);
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catafor.JTablaFormaPago);
                    limpiar();
                } else {

                    if (modfor.registrar(modfor)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(catafor.JTablaFormaPago);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }
        }

        if (e.getSource() == vfor.btnModificar) {
            if (validar()) {
                modfor.setForma_pago(vfor.txtFormaPago.getText());
                modfor.setId(Integer.parseInt(vfor.txtid.getText()));
                if (modfor.buscarInactivo(modfor)) {
                    JOptionPane.showMessageDialog(null, "no puede colocar el nombre de un metodo de pago que ya existio, si quiere colocar este nombre debe registrarlo nuevamente");
                } else {

                    if (modfor.modificar(modfor)) {

                        JOptionPane.showMessageDialog(null, "Registro modificado");
                        vfor.dispose();
                        Llenartabla(catafor.JTablaFormaPago);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                    }
                }
            }
        }

        if (e.getSource() == vfor.btnEliminar) {

            modfor.setId(Integer.parseInt(vfor.txtid.getText()));

            if (modfor.eliminar(modfor)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                vfor.dispose();
                Llenartabla(catafor.JTablaFormaPago);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
    }

    public void limpiar() {

        vfor.txtFormaPago.setText(null);

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vfor.txtFormaPago.getText().isEmpty()) {

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
        if (e.getSource() == catafor.txtBuscarFormaPago) {

            filtro(catafor.txtBuscarFormaPago.getText(), catafor.JTablaFormaPago);
        } else {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catafor.JTablaFormaPago.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catafor.JTablaFormaPago.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catafor.JTablaFormaPago.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda

        modfor.setForma_pago(String.valueOf(dato));

        modfor.buscar(modfor);

        vfor.setVisible(true);
        vfor.txtFormaPago.setText(modfor.getForma_pago());

        vfor.txtid.setText(Integer.toString(modfor.getId()));

        vfor.btnGuardar.setEnabled(false);
        vfor.txtid.setVisible(false);
        vfor.btnModificar.setEnabled(true);
        vfor.btnEliminar.setEnabled(true);
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
        Llenartabla(catafor.JTablaFormaPago);
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
