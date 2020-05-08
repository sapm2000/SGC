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
import javax.swing.table.DefaultTableModel;
import vista.catalogoConceptoGasto;
import vista.conceptoGasto;
import modelo.ModeloConceptoGastos;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;

public class controladorConceptoGasto implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoConceptoGasto catacga;
    private conceptoGasto cga;
    private ModeloConceptoGastos modCatGas;
    private CategoriaGasto modCat;
    ArrayList<ModeloConceptoGastos> listaConGas;
    ArrayList<CategoriaGasto> listaCatGas;
    DefaultTableModel dm;

    public controladorConceptoGasto(catalogoConceptoGasto catacga, conceptoGasto cga, ModeloConceptoGastos modCatGas, CategoriaGasto modCat) {

        this.catacga = catacga;
        this.cga = cga;
        this.modCatGas = modCatGas;
        this.modCat = modCat;
        this.catacga.btnNuevoRegistro.addActionListener(this);
        this.cga.btnGuardar.addActionListener(this);
        this.cga.btnLimpiar.addActionListener(this);
        this.cga.btnModificar.addActionListener(this);
        this.cga.txtNombreC.addKeyListener(this);
        this.cga.txtDescripcion.addKeyListener(this);
        this.cga.btnEliminar.addActionListener(this);
        this.catacga.addWindowListener(this);
        this.catacga.jTable.addMouseListener(this);
        this.catacga.txtBuscar.addKeyListener(this);
        listaCatGas = modCat.lCategGas();
        crearCbxCategoria(listaCatGas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cga.btnGuardar) {
            if (validar()) {
                modCatGas.setNombre_Concepto(cga.txtNombreC.getText());
                modCatGas.setDescripcion(cga.txtDescripcion.getText());
                int ind = cga.cbxCategoria.getSelectedIndex() - 1;
                modCatGas.setId_categoria(listaCatGas.get(ind).getId());

                if (modCatGas.registrarConcepto(modCatGas)) {

                    JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                    Llenartabla(catacga.jTable);

                } else {

                    JOptionPane.showMessageDialog(null, "Registro Duplicado");

                }
            }
        }

        if (e.getSource() == cga.btnModificar) {
            if (validar()) {
                modCatGas.setId(Integer.parseInt(cga.txtId.getText()));
                modCatGas.setNombre_Concepto(cga.txtNombreC.getText());
                modCatGas.setDescripcion(cga.txtDescripcion.getText());
                int ind = cga.cbxCategoria.getSelectedIndex() - 1;
                modCatGas.setId_categoria(listaCatGas.get(ind).getId());

                if (modCatGas.modificarConcepto(modCatGas)) {

                    JOptionPane.showMessageDialog(null, "Registro modificado");
                    cga.dispose();
                    Llenartabla(catacga.jTable);
                    limpiar();

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                }
            }
        }
        if (e.getSource() == cga.btnEliminar) {

            if (modCatGas.eliminar(modCatGas)) {
                modCatGas.setId(Integer.parseInt(cga.txtId.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                cga.dispose();
                Llenartabla(catacga.jTable);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }

        if (e.getSource() == cga.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == catacga.btnNuevoRegistro) {
            limpiar();
            this.cga.setVisible(true);
            cga.txtId.setVisible(false);
            this.cga.btnModificar.setEnabled(false);
            this.cga.btnGuardar.setEnabled(true);
            this.cga.btnEliminar.setEnabled(false);
            this.cga.txtNombreC.setEnabled(true);

        }

    }

    public void limpiar() {

        cga.txtNombreC.setText(null);
        cga.txtDescripcion.setText(null);
        cga.cbxCategoria.setSelectedItem(0);

    }

    public void Llenartabla(JTable tablaD) {

        listaConGas = modCatGas.listarConcepto();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Concepto");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Categoría");

        Object[] columna = new Object[3];

        int numRegistro = listaConGas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaConGas.get(i).getNombre_Concepto();
            columna[1] = listaConGas.get(i).getDescripcion();
            columna[2] = listaConGas.get(i).getNombreCategoria();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (cga.txtNombreC.getText().isEmpty()) {

            msj += "El campo nombre del concepto no puede estar vacío\n";
            resultado = false;
        }
        if (cga.txtDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private void crearCbxCategoria(ArrayList<CategoriaGasto> datos) {
        cga.cbxCategoria.addItem("Seleccione...");

        if (datos != null) {
            for (CategoriaGasto datosX : datos) {
                modCat = datosX;
                cga.cbxCategoria.addItem(modCat.getNombre());
            }

        }
    }

    private void filtro(String consulta, JTable jTableBuscar) {
        dm = (DefaultTableModel) jTableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jTableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catacga.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catacga.jTable.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catacga.jTable.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        Boolean resultado = true;
        String msj = "";

        modCatGas.setNombre_Concepto(String.valueOf(dato));

        modCatGas.buscarC(modCatGas);

        cga.setVisible(true);

        cga.txtId.setText(modCatGas.getId() + "");
        cga.txtNombreC.setText(modCatGas.getNombre_Concepto());
        cga.txtDescripcion.setText(modCatGas.getDescripcion());
        cga.cbxCategoria.setSelectedItem(modCatGas.getNombreCategoria());
        cga.txtId.setEnabled(false);
        cga.txtId.setVisible(false);
        cga.btnGuardar.setEnabled(false);
        cga.btnModificar.setEnabled(true);
        cga.btnEliminar.setEnabled(true);
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
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == cga.txtNombreC) {
            Validacion.soloLetras(ke);
            Validacion.limite(ke, cga.txtNombreC.getText(), 120);
        }
        if (ke.getSource() == cga.txtDescripcion) {

            Validacion.limite(ke, cga.txtDescripcion.getText(), 120);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catacga.txtBuscar) {
            filtro(catacga.txtBuscar.getText(), catacga.jTable);
        } else {
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catacga.jTable);
        
        Component[] components =cga.jPanel2.getComponents();
        JComponent[] com = {
            cga.txtNombreC
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
