package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Funcion;
import modelo.TipoUsuario;
import sgc.SGC;
import vista.Catalogo;
import vista.VisTipoUsuario;

public class CtrlTipoUsuario implements ActionListener, MouseListener, KeyListener {

    private VisTipoUsuario vista;
    private Catalogo catalogo;
    private TipoUsuario modelo;

    private Funcion modFuncion;
    private Funcion permiso;
    DefaultTableModel dm;
    private ArrayList<TipoUsuario> lista;
    private ArrayList<Funcion> listaFuncion;

    public CtrlTipoUsuario() {

        this.catalogo = new Catalogo();
        this.vista = new VisTipoUsuario();
        this.modelo = new TipoUsuario();
        this.modFuncion = new Funcion();

        catalogo.lblTitulo.setText("Tipo Usuario");

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.tabla.addMouseListener(this);

        llenarTabla();
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        CtrlVentana.cambiarVista(catalogo);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnGuardar.setVisible(true);
            this.vista.btnModificar.setVisible(false);
            CtrlVentana.cambiarVista(vista);

            llenarTablaFuncion("Registrar");

        }

        if (e.getSource() == vista.btnGuardar) {

            if (validar()) {

                registrar();
            }
        }

        if (e.getSource() == vista.btnModificar) {

        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    private void registrar() {

        //Si se está edtando la tabla
        if (vista.tabla.isEditing()) {

            //Detiene la edición
            vista.tabla.getCellEditor().stopCellEditing();
        }

        modelo = new TipoUsuario();
        modelo.setNombre(vista.txtTipo.getText());

        boolean ver = false;
        boolean registrar = false;
        boolean modificar = false;
        boolean eliminar = false;

        // Por cada fila de la tabla
        for (int i = 0, indFunciones = 0; i < vista.tabla.getRowCount(); i++) {

            //Si Ver fue seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 1)) == "true") {

                ver = true;

            } else {

                ver = false;
            }

            //Si Registrar fue seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 2)) == "true") {

                registrar = true;

            } else {

                registrar = false;
            }

            //Si Modificar fue seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 3)) == "true") {

                modificar = true;

            } else {

                modificar = false;
            }

            //Si Eliminar fue seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 4)) == "true") {

                eliminar = true;

            } else {

                eliminar = false;
            }

            if (ver) {

                modelo.getFunciones().add(listaFuncion.get(i));
                modelo.getFunciones().get(indFunciones).setVer(true);
                modelo.getFunciones().get(indFunciones).setRegistrar(registrar);
                modelo.getFunciones().get(indFunciones).setModificar(modificar);
                modelo.getFunciones().get(indFunciones).setEliminar(eliminar);

                indFunciones++;
            }
        }

        if (modelo.registrar()) {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, "Registro guardado ", "Registro de datos", JOptionPane.INFORMATION_MESSAGE, p);
            llenarTabla();
            CtrlVentana.cambiarVista(catalogo);
            vista.txtTipo.setText("");

        } else {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, "Error al registrar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == catalogo.tabla) {

            int fila = catalogo.tabla.getSelectedRow();

            modelo = lista.get(fila);

            vista.txtTipo.setText(modelo.getNombre());

            vista.btnGuardar.setEnabled(false);

            llenarTablaFuncion("Modificar");

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.tabla) {

            int fila = vista.tabla.getSelectedRow();

            if (String.valueOf(vista.tabla.getValueAt(fila, 2)) == "true") {

                vista.tabla.setValueAt(true, fila, 1);

            }

            if (String.valueOf(vista.tabla.getValueAt(fila, 3)) == "true") {

                vista.tabla.setValueAt(true, fila, 1);
                vista.tabla.setValueAt(true, fila, 2);

            }

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
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        catalogo.tabla.setRowSorter(tr);
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

            msj += "El campo Tipo de usuario no puede estar vacío\n";
            resultado = false;
        }

        //Contadores para los permisos
        int numVer = 0;
        int numRegistrar = 0;
        int numModificar = 0;
        int numEliminar = 0;

        //Por cada fila de la tabla
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {

            //Si Ver está seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 1)) == "true") {
                numVer++;
            }

            //Si Registrar está seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 2)) == "true") {
                numRegistrar++;
            }

            //Si Modificar está seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 3)) == "true") {
                numModificar++;
            }

            //Si Eliminar está seleccionado
            if (String.valueOf(vista.tabla.getValueAt(i, 4)) == "true") {
                numEliminar++;
            }
        }

        //Si no fue seleccionado ningún permiso
        if ((numVer + numRegistrar + numModificar + numEliminar) == 0) {

            msj += "Debe seleccionar al menos una Función\n";
            resultado = false;

        }

        if (!resultado) {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;

    }

    private void llenarTablaFuncion(String accion) {

        // Consulta la tabla funcion de la BD
        listaFuncion = modFuncion.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        vista.tabla.setModel(modeloT);

        // Añade las columnas a la tabla
        modeloT.addColumn("Función");
        modeloT.addColumn("Ver");
        modeloT.addColumn("Registrar");
        modeloT.addColumn("Modificar");
        modeloT.addColumn("Eliminar");

        // Crea un arreglo para ordenar las filas de la tabla
        Object[] fila = new Object[modeloT.getColumnCount()];

        int numRegistro = listaFuncion.size();
        int ind;

        // Si la acción es Registrar
        if (accion == "Registrar") {

            // Por cada función de la BD
            for (int i = 0; i < numRegistro; i++) {

                ind = 0;
                fila[ind++] = listaFuncion.get(i).getNombre();

                modeloT.addRow(fila);
            }

            // Si la acción es Modificar
        } else if (accion == "Modificar") {

            // Por cada función de la BD
            for (int i = 0; i < numRegistro; i++) {
                System.out.println("revisando la función: " + listaFuncion.get(i).getNombre());

                ind = 0;
                // Añade el nombre de la función a la tabla
                fila[ind++] = listaFuncion.get(i).getNombre();

                // Por cada función del tipo de usuario seleccionado de la tabla
                for (int j = 0; j < modelo.getFunciones().size(); j++) {

                    ind = 1;

                    // Si la función actual de la lista corresponde a la función actual del tipo de usuario seleccionado
                    if (listaFuncion.get(i).getId().equals(modelo.getFunciones().get(j).getId())) {
                        System.out.println("la funcion: " + modelo.getFunciones().get(j).getNombre() + " tiene permisos");

                        // Añade el valor de los checkbox
                        System.out.println(modelo.getFunciones().get(j).getVer());
                        System.out.println(modelo.getFunciones().get(j).getRegistrar());
                        System.out.println(modelo.getFunciones().get(j).getModificar());
                        System.out.println(modelo.getFunciones().get(j).getEliminar());
                        fila[ind++] = modelo.getFunciones().get(j).getVer();
                        fila[ind++] = modelo.getFunciones().get(j).getRegistrar();
                        fila[ind++] = modelo.getFunciones().get(j).getModificar();
                        fila[ind++] = modelo.getFunciones().get(j).getEliminar();
                        break;

                    } else {

                        fila[ind++] = Boolean.FALSE;
                        fila[ind++] = Boolean.FALSE;
                        fila[ind++] = Boolean.FALSE;
                        fila[ind++] = Boolean.FALSE;
                    }
                }

                modeloT.addRow(fila);

            }
        }

        addCheckBox(1, vista.tabla);
        addCheckBox(2, vista.tabla);
        addCheckBox(3, vista.tabla);
        addCheckBox(4, vista.tabla);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        }
    }

    private void filtro(String consulta, JTable tablaBuscar) {

        dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);

        tablaBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }
}
