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
import static java.lang.String.valueOf;
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
import modelo.Asambleas;
import modelo.Funcion;
import modelo.Propietarios;
import sgc.SGC;
import vista.Catalogo;
import vista.VisAsamblea;

public class CtrlAsamblea implements ActionListener, KeyListener, MouseListener, WindowListener {

    private Catalogo catalogo;
    private VisAsamblea vista;
    private Asambleas modelo;
    private ArrayList<Asambleas> lista;

    private Propietarios modPropietario;
    private ArrayList<Propietarios> listaPropietario;

    private Funcion permiso;

    public CtrlAsamblea() {
        this.catalogo = new Catalogo();
        this.vista = new VisAsamblea();
        this.modelo = new Asambleas();
        this.modPropietario = new Propietarios();

        catalogo.lblTitulo.setText("Asambleas");

        llenarTabla(catalogo.tabla);

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        vista.btnSalir.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.txtBuscarPropietario.addKeyListener(this);

        CtrlVentana.cambiarVista(catalogo);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnGuardar.setEnabled(true);

            vista.txtNombre.setText("");
            vista.txaDescripcion.setText("");
            vista.txtFecha.setDate(null);

            llenarTablaPropietarios(vista.tablaAsistentes, "Registrar");
            addCheckBox(2, vista.tablaAsistentes);

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                int j;

                modelo = new Asambleas();
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setDescripcion(vista.txaDescripcion.getText());
                modelo.setFecha(new java.sql.Date(vista.txtFecha.getDate().getTime()));

                j = 0;

                for (int i = 0; i < vista.tablaAsistentes.getRowCount(); i++) {
                    if (valueOf(vista.tablaAsistentes.getValueAt(i, 2)) == "true") {
                        j++;
                    }
                }

                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos 1 registro de la tabla");

                } else {
                    for (int fila = 0; fila < vista.tablaAsistentes.getRowCount(); fila++) {

                        if (valueOf(vista.tablaAsistentes.getValueAt(fila, 2)) == "true") {
                            modelo.getAsistentes().add(listaPropietario.get(fila));
                        }
                    }

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

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila;
        fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada

        modelo = lista.get(fila);

        vista.txtid.setVisible(false);

        vista.txtNombre.setText(modelo.getNombre());
        vista.txaDescripcion.setText(modelo.getDescripcion());
        vista.txtFecha.setDate(modelo.getFecha());

        llenarTablaPropietarios(vista.tablaAsistentes, "Consultar");

        vista.btnGuardar.setEnabled(false);

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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txtBuscarPropietario) {

            filtro(vista.txtBuscarPropietario.getText(), vista.tablaAsistentes);
        }
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Component[] components = vista.jPanel4.getComponents();

        JComponent[] com = {
            vista.txtNombre, vista.txtFecha
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

    public void addCheckBox(int column, JTable tabla) {
        TableColumn tc = tabla.getColumnModel().getColumn(column);

        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }

    private void filtro(String consulta, JTable tablaBuscar) {
        DefaultTableModel dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);

        tablaBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    public void llenarTabla(JTable tablaD) {
        int ind;

        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                boolean resu = false;

                switch (column) {
                    case 0:
                    case 1:
                    case 2:
                        resu = false;
                        break;
                    default:
                        break;
                }

                return resu;
            }
        };

        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de<br>Asamblea</html>");
        modeloT.addColumn("<html>Nombre de <br>Asamblea</html>");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("<html>Nº de<br>Asistentes</html>");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getId();
            columna[ind++] = lista.get(i).getNombre();
            columna[ind++] = lista.get(i).getDescripcion();
            columna[ind++] = lista.get(i).getFecha();
            columna[ind++] = lista.get(i).getAsistentes().size();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    public void llenarTablaPropietarios(JTable tablaD, String accion) {
        int ind;
        int numRegistro;

        listaPropietario = modPropietario.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;

                switch (column) {
                    case 0:
                    case 1:
                        resu = false;
                        break;
                    case 2:
                        if (accion.equals("Registrar")) {
                            resu = true;

                        } else if (accion.equals("Consultar")) {
                            resu = false;

                        }

                        break;
                    default:
                        break;
                }

                return resu;
            }
        };

        tablaD.setModel(modeloT);

        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        if (accion.equals("Registrar")) {
            modeloT.addColumn("Seleccione");
        }

        Object[] columna = new Object[modeloT.getColumnCount()];

        if (accion.equals("Registrar")) {
            numRegistro = listaPropietario.size();

            for (int i = 0; i < numRegistro; i++) {
                ind = 0;

                columna[ind++] = listaPropietario.get(i).getCedula();
                columna[ind++] = listaPropietario.get(i).getpNombre() + " " + listaPropietario.get(i).getpApellido();

                modeloT.addRow(columna);
            }

        } else if (accion.equals("Consultar")) {
            numRegistro = modelo.getAsistentes().size();

            for (int i = 0; i < numRegistro; i++) {
                ind = 0;

                columna[ind++] = modelo.getAsistentes().get(i).getCedula();
                columna[ind++] = modelo.getAsistentes().get(i).getpNombre() + " " + modelo.getAsistentes().get(i).getpApellido();

                modeloT.addRow(columna);
            }
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    private void permisoBtn() {
        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Asambleas")) {
                permiso = funcionbtn;
            }
        }
    }

    private Boolean validar() {
        Boolean resultado = true;
        String msj = "";

        if (vista.txtNombre.getText().isEmpty()) {
            msj += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txaDescripcion.getText().isEmpty()) {
            msj += "El campo Descripción no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
