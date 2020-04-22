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
import modelo.CrudUsuario;
import vista.catalogoConceptoGasto;
import vista.conceptoGasto;
import modelo.ModeloConceptoGastos;
import controlador.Validacion;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;

/**
 *
 * @author rma
 */
public class controladorConceptoGasto implements ActionListener, ItemListener, MouseListener, KeyListener, WindowListener {

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
        this.catacga.addWindowListener(this);
        this.catacga.jTable.addMouseListener(this);
        this.catacga.txtBuscar.addKeyListener(this);

        crearCbxCategoria(modCat.lCategGas());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cga.btnGuardar) {
            if (validar()) {
                modCatGas.setNombre_Concepto(cga.txtNombreC.getText());
                modCatGas.setDescripcion(cga.txtDescripcion.getText());
                modCatGas.setNombreCategoria(cga.cbxCategoria.getSelectedItem().toString());

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
                modCatGas.setNombre_Concepto(cga.txtNombreC.getText());
                modCatGas.setDescripcion(cga.txtDescripcion.getText());
                modCatGas.setNombreCategoria(cga.cbxCategoria.getSelectedItem().toString());

            }
            if (modCatGas.modificar(modCatGas)) {

                JOptionPane.showMessageDialog(null, "Registro modificado");
                cga.dispose();
                Llenartabla(catacga.jTable);
                limpiar();

            } else {

                JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

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
            System.out.println("puto funciona");
            this.cga.setVisible(true);
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

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

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

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (cga.txtNombreC.getText().isEmpty()) {

            msj += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }
        if (cga.txtDescripcion.getText().isEmpty()) {

            msj += "El campo Descripción no puede estar vacío\n";
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

            System.out.println("ComboBox Categoría creado");
        }
    }

    private void filtro(String consulta, JTable jTableBuscar) {
        dm = (DefaultTableModel) jTableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jTableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
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

            Validacion.soloLetras(ke);
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
