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
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;
import modelo.Funcion;
import sgc.SGC;
import vista.catalogoCategoriaGastos;
import vista.categoriaGastos;

/**
 *
 * @author rma
 */
public class controladorCategoriaGastos implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoCategoriaGastos catalogo;

    private categoriaGastos vista;
    private CategoriaGasto modcg;
    DefaultTableModel dm;
    Funcion permiso;
    ArrayList<CategoriaGasto> listaCatGas;

    public controladorCategoriaGastos() {
        this.catalogo = new catalogoCategoriaGastos();
        this.vista = new categoriaGastos();
        this.modcg = new CategoriaGasto();

        this.catalogo.btn_nuevaCategoriaGasto.addActionListener(this);
        this.catalogo.btnActivar.addActionListener(this);

        this.vista.btnModificar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.catalogo.tabla_categoria_gastos.addMouseListener(this);
        this.catalogo.txt_buscarCategoriaGasto.addKeyListener(this);
        this.catalogo.addWindowListener(this);
        this.vista.txtnombre.addKeyListener(this);
        this.vista.txtdescripcion.addKeyListener(this);
        this.catalogo.setVisible(true);

    }

    public void Llenartabla(JTable tablaD) {

        listaCatGas = modcg.lCategGas();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Nombre");
        modeloT.addColumn("Descripcion");

        Object[] columna = new Object[2];

        int numRegistro = listaCatGas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCatGas.get(i).getNombre();
            columna[1] = listaCatGas.get(i).getDescripcion();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);

    }

    public void Llenartablai(JTable tablaD) {

        listaCatGas = modcg.lCategGasi();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }

                if (column == 2) {
                    resu = true;
                }
                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Nombre");
        modeloT.addColumn("Descripcion");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[3];

        int numRegistro = listaCatGas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCatGas.get(i).getNombre();
            columna[1] = listaCatGas.get(i).getDescripcion();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btn_nuevaCategoriaGasto) {
            this.vista.setVisible(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.txtId.setVisible(false);
            this.vista.btnModificar.setEnabled(false);
            vista.txtnombre.setText("");
            vista.txtdescripcion.setText("");
            vista.txtId.setText("");

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modcg.setNombre(vista.txtnombre.getText());
                modcg.setDescripcion(vista.txtdescripcion.getText());

                if (modcg.buscarInactivo(modcg)) {
                    modcg.activar(modcg);
                    modcg.modificar(modcg);
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catalogo.tabla_categoria_gastos);

                } else {

                    if (modcg.registrar(modcg)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(catalogo.tabla_categoria_gastos);

                    } else {

                        JOptionPane.showMessageDialog(null, "Registro Duplicado");

                    }
                }

            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modcg.setId(Integer.parseInt(vista.txtId.getText()));
            if (modcg.Buscarcon(modcg)) {
                JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene conceptos asignados");
            } else {
                if (modcg.eliminar(modcg)) {

                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    vista.dispose();
                    Llenartabla(catalogo.tabla_categoria_gastos);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }

            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modcg.setNombre(vista.txtnombre.getText());
                modcg.setDescripcion(vista.txtdescripcion.getText());
                modcg.setId(Integer.parseInt(vista.txtId.getText()));

                if (modcg.buscarInactivo(modcg)) {

                    JOptionPane.showMessageDialog(null, "no puede colocar el nombre de una categoria que ya existio, si quiere colocar este nombre debe registrarlo nuevamente");

                } else {
                    if (modcg.modificar(modcg)) {

                        JOptionPane.showMessageDialog(null, "Registro modificado");
                        vista.dispose();
                        Llenartabla(catalogo.tabla_categoria_gastos);

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                    }
                }
            }
        }

        if (e.getSource() == vista.btnLimpiar) {

            vista.txtnombre.setText("");
            vista.txtdescripcion.setText("");

        }

    }

    public void limpiar() {

        vista.txtnombre.setText(null);
        vista.txtdescripcion.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.catalogo.tabla_categoria_gastos.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.tabla_categoria_gastos.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catalogo.tabla_categoria_gastos.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        modcg.setNombre(String.valueOf(dato));

        modcg.Buscar(modcg);

        vista.setVisible(true);
        vista.txtnombre.setText(modcg.getNombre());

        vista.txtdescripcion.setText(modcg.getDescripcion());
        vista.txtId.setText(Integer.toString(modcg.getId()));

        vista.btnGuardar.setEnabled(false);
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == vista.txtnombre) {
            Validacion.soloLetras(ke);
            Validacion.limite(ke, vista.txtnombre.getText(), 120);
        }
        if (ke.getSource() == vista.txtdescripcion) {
            Validacion.limite(ke, vista.txtdescripcion.getText(), 120);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txt_buscarCategoriaGasto) {

            filtro(catalogo.txt_buscarCategoriaGasto.getText(), catalogo.tabla_categoria_gastos);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catalogo.tabla_categoria_gastos);
                permisoBtn();
        
        if (permiso.getRegistrar()) {
            catalogo.btn_nuevaCategoriaGasto.setEnabled(true);
        }

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtnombre
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

        if (vista.txtnombre.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtdescripcion.getText().isEmpty()) {

            msj += "El campo Descripcion no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

}
