package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
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
import modelo.ConceptoGasto;
import modelo.Funcion;
import sgc.SGC;
import vista.Catalogo;
import vista.VisConceptoGasto;

public class CtrlConceptoGasto implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private Catalogo catalogo;
    private VisConceptoGasto vista;
    private ConceptoGasto modelo;
    private ArrayList<ConceptoGasto> lista;
    DefaultTableModel dm;
    private CategoriaGasto modCategoria;
    private ArrayList<CategoriaGasto> listaCategoria;

    private Funcion permiso;

    public CtrlConceptoGasto() {

        this.catalogo = new Catalogo();
        this.vista = new VisConceptoGasto();
        this.modelo = new ConceptoGasto();

        this.modCategoria = new CategoriaGasto();

        catalogo.lblTitulo.setText("Concepto Gasto");

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        llenarTabla(catalogo.tabla);

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.tabla.addMouseListener(this);

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtNombreC.addKeyListener(this);
        this.vista.txtDescripcion.addKeyListener(this);

        CtrlVentana.cambiarVista(catalogo);
        vista.cbxCategoria.addItemListener(this);
        stylecombo(vista.cbxCategoria);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnGuardar) {

            if (validar()) {
                modelo.setNombre(vista.txtNombreC.getText());
                modelo.setDescripcion(vista.txtDescripcion.getText());
                int ind = vista.cbxCategoria.getSelectedIndex() - 1;
                modelo.getCategoria().setId(listaCategoria.get(ind).getId());

                if (ind == -1) {
                    JOptionPane.showMessageDialog(null, "por favor seleccione una categoria");

                } else {

                    if (modelo.buscarInactivo(modelo)) {
                        if (modelo.activar(modelo)) {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            llenarTabla(catalogo.tabla);
                            CtrlVentana.cambiarVista(catalogo);

                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo registrar");
                        }

                    } else {

                        if (modelo.registrar()) {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            llenarTabla(catalogo.tabla);
                            CtrlVentana.cambiarVista(catalogo);

                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo registrar");
                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
                modelo.setNombre(vista.txtNombreC.getText());
                modelo.setDescripcion(vista.txtDescripcion.getText());
                int ind = vista.cbxCategoria.getSelectedIndex() - 1;
                modelo.getCategoria().setId(listaCategoria.get(ind).getId());
                if (ind == -1) {
                    JOptionPane.showMessageDialog(null, "por favor seleccione una categoria");
                } else {

                    if (modelo.buscarInactivo(modelo)) {

                        JOptionPane.showMessageDialog(null, "no puede colocar un concepto que ya existio, si quiere colocar este concepto debe registrarlo nuevamente");

                    } else {

                        if (modelo.modificar(modelo)) {

                            JOptionPane.showMessageDialog(null, "Registro modificado");
                            llenarTabla(catalogo.tabla);
                            limpiar();
                            CtrlVentana.cambiarVista(catalogo);

                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                        }
                    }
                }
            }
        }
        if (e.getSource() == vista.btnEliminar) {
//            if (modelo.Buscarcuo(modelo)) {
//                JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene gastos por procesar asignados");
//            } else {
            if (modelo.eliminar(modelo)) {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                CtrlVentana.cambiarVista(catalogo);
                llenarTabla(catalogo.tabla);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }
//            }

        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }

        if (e.getSource() == catalogo.btnNuevo) {
            limpiar();
            vista.txtId.setVisible(false);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtNombreC.setEnabled(true);
            vista.cbxCategoria.removeAllItems();
            listaCategoria = modCategoria.lCategGas();
            crearCbxCategoria(listaCategoria);

            CtrlVentana.cambiarVista(vista);
        }

    }

    public void limpiar() {

        vista.txtNombreC.setText(null);
        vista.txtDescripcion.setText(null);
        vista.cbxCategoria.setSelectedItem(0);

    }

    public void llenarTabla(JTable tablaD) {

        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Concepto");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Categoría");

        Object[] columna = new Object[3];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = lista.get(i).getNombre();
            columna[1] = lista.get(i).getDescripcion();
            columna[2] = lista.get(i).getCategoria().getNombre();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
    }

//    public void Llenartabla1(JTable tablaD) {
//
//        listaConGas = modelo.listarConcepto1();
//
//        DefaultTableModel modeloT = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//
//                boolean resu = false;
//                if (column == 0) {
//                    resu = false;
//                }
//                if (column == 1) {
//                    resu = false;
//                }
//
//                if (column == 2) {
//                    resu = false;
//                }
//                if (column == 3) {
//                    resu = true;
//                }
//                return resu;
//            }
//
//        };
//         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);        tablaD.setRowSorter(tr);                        tablaD.setModel(modeloT);
//        tablaD.getTableHeader().setReorderingAllowed(false);
//        tablaD.getTableHeader().setResizingAllowed(false);
//
//        modeloT.addColumn("Concepto");
//        modeloT.addColumn("Descripción");
//        modeloT.addColumn("Categoría");
//        modeloT.addColumn("Seleccione");
//
//        Object[] columna = new Object[4];
//
//        int numRegistro = listaConGas.size();
//
//        for (int i = 0; i < numRegistro; i++) {
//
//            columna[0] = listaConGas.get(i).getNombre_Concepto();
//            columna[1] = listaConGas.get(i).getDescripcion();
//            columna[2] = listaConGas.get(i).cate.getNombre();
//
//            modeloT.addRow(columna);
//
//        }
//        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
//        tcr.setHorizontalAlignment(SwingConstants.CENTER);
//        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
//        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
//        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
//        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
//    }
    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtNombreC.getText().isEmpty()) {

            msj += "El campo nombre del concepto no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private void crearCbxCategoria(ArrayList<CategoriaGasto> datos) {
        vista.cbxCategoria.addItem("Seleccione...");

        if (datos != null) {
            for (CategoriaGasto datosX : datos) {
                modCategoria = datosX;
                vista.cbxCategoria.addItem(modCategoria.getNombre());
            }

        }
    }

    private void filtro(String consulta, JTable tablaBuscar) {
        dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.tabla.getSelectedColumn(); // luego, obtengo la columna seleccionada

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        Boolean resultado = true;
        String msj = "";
        vista.cbxCategoria.removeAllItems();

        modelo.setNombre(String.valueOf(dato));

        modelo.buscarC(modelo);

        listaCategoria = modCategoria.lCategGas();
        crearCbxCategoria(listaCategoria);
        vista.txtId.setText(modelo.getId() + "");
        vista.txtNombreC.setText(modelo.getNombre());
        vista.txtDescripcion.setText(modelo.getDescripcion());
        vista.cbxCategoria.setSelectedItem(modelo.getCategoria().getNombre());
        vista.txtId.setEnabled(false);
        vista.txtId.setVisible(false);
        vista.btnGuardar.setEnabled(false);
        vista.btnModificar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);

        CtrlVentana.cambiarVista(vista);
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
        if (ke.getSource() == vista.txtNombreC) {
            Validacion.soloLetras(ke);
            Validacion.limite(ke, vista.txtNombreC.getText(), 120);
        }
        if (ke.getSource() == vista.txtDescripcion) {

            Validacion.limite(ke, vista.txtDescripcion.getText(), 120);
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

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtNombreC
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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxCategoria.setFocusable(false);
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }
}
