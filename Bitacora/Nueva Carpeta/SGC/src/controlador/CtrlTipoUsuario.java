package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Funcion;
import modelo.TipoUsuario;
import vista.catalogoTipoUsuario;
import vista.tipoUsuario;

public class CtrlTipoUsuario implements ActionListener, MouseListener {

    private tipoUsuario vista;
    private catalogoTipoUsuario catalogo;
    private TipoUsuario modelo;

    private Funcion modFuncion;

    private ArrayList<TipoUsuario> lista;
    private ArrayList<Funcion> listaFuncion;

    public CtrlTipoUsuario() {
        this.catalogo = new catalogoTipoUsuario();
        this.vista = new tipoUsuario();
        this.modelo = new TipoUsuario();
        this.modFuncion = new Funcion();

        this.catalogo.btnNuevo.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.tabla.addMouseListener(this);

        llenarTabla();

        this.catalogo.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnGuardar.setVisible(true);
            this.vista.btnModificar.setVisible(false);
            llenarTablaFuncion();

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                if (vista.tabla.isEditing()) {//si se esta edtando la tabla
                    vista.tabla.getCellEditor().stopCellEditing();//detenga la edicion
                }

                modelo = new TipoUsuario();
                modelo.setNombre(vista.txtTipo.getText());

                int j = 0;
                int k = 0;
                int l = 0;
                int m = 0;

                for (int i = 0; i < vista.tabla.getRowCount(); i++) {
                    if (String.valueOf(vista.tabla.getValueAt(i, 1)) == "true") {
                        j++;

                    }
                    if (String.valueOf(vista.tabla.getValueAt(i, 2)) == "true") {
                        k++;

                    }
                    if (String.valueOf(vista.tabla.getValueAt(i, 3)) == "true") {
                        l++;

                    }
                    if (String.valueOf(vista.tabla.getValueAt(i, 4)) == "true") {
                        m++;

                    }
                }

                if ((j + k + l + m) == 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos una Función");

                } else {
                    boolean registrar = false, modificar = false, eliminar = false, todo = false;

                    for (int i = 0, n = 0; i < vista.tabla.getRowCount(); i++) {
                        if (String.valueOf(vista.tabla.getValueAt(i, 4)) == "true") {
                            todo = true;

                        } else {
                            todo = false;

                        }
                        if (String.valueOf(vista.tabla.getValueAt(i, 1)) == "true") {
                            registrar = true;

                        } else {
                            registrar = false;

                        }
                        if (String.valueOf(vista.tabla.getValueAt(i, 2)) == "true") {
                            modificar = true;

                        } else {
                            modificar = false;

                        }
                        if (String.valueOf(vista.tabla.getValueAt(i, 3)) == "true") {
                            eliminar = true;

                        } else {
                            eliminar = false;

                        }

                        if (todo) {
                            modelo.getFunciones().add(listaFuncion.get(i));
                            modelo.getFunciones().get(n).setRegistrar(true);
                            modelo.getFunciones().get(n).setModificar(true);
                            modelo.getFunciones().get(n).setEliminar(true);
                            modelo.getFunciones().get(n).setTodo(true);
                            n++;

                        } else if (registrar || modificar || eliminar) {
                            modelo.getFunciones().add(listaFuncion.get(i));

                            modelo.getFunciones().get(n).setRegistrar(registrar);

                            modelo.getFunciones().get(n).setModificar(modificar);

                            modelo.getFunciones().get(n).setEliminar(eliminar);

                            modelo.getFunciones().get(n).setTodo(false);
                            n++;

                        }
                    }

                    if (modelo.registrar()) {
                        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                        llenarTabla();
                        vista.dispose();
                        vista.txtTipo.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");

                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.tabla) {
            int fila = vista.tabla.getSelectedRow();
            if (String.valueOf(vista.tabla.getValueAt(fila, 4)) == "true") {
                vista.tabla.setValueAt(true, fila, 1);
                vista.tabla.setValueAt(true, fila, 2);
                vista.tabla.setValueAt(true, fila, 3);

            }
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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    private void llenarTabla() {
        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        catalogo.tabla.setModel(modeloT);

        modeloT.addColumn("Tipo de Usuario");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getNombre();

            modeloT.addRow(columna);

        }

    }

    private Boolean validar() {

        String msj = "";
        Boolean resultado = true;

        if (vista.txtTipo.getText().isEmpty()) {

            msj += "El campo Tipo de usuario No puede estar vacío \n";
            resultado = false;
        }
        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;

    }

    private void llenarTablaFuncion() {
        listaFuncion = modFuncion.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        vista.tabla.setModel(modeloT);

        modeloT.addColumn("Función");
        modeloT.addColumn("Registrar");
        modeloT.addColumn("Modificar");
        modeloT.addColumn("Eliminar");
        modeloT.addColumn("Todo");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaFuncion.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listaFuncion.get(i).getNombre();

            modeloT.addRow(columna);

        }

        addCheckBox(1, vista.tabla);
        addCheckBox(2, vista.tabla);
        addCheckBox(3, vista.tabla);
        addCheckBox(4, vista.tabla);

    }

}
