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
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Condominio;
import modelo.Propietarios;
import vista.Propietario;
import vista.catalogoPropietarios;

public class controladorPropietario implements ActionListener, MouseListener, KeyListener, WindowListener {

    private Propietario vista;
    private Propietarios modelo;
    private catalogoPropietarios catalogo;
    private Condominio modCon;

    private ArrayList<Propietarios> listaPropietarios;
    private ArrayList<Condominio> listaCondo;
    private DefaultTableModel dm;

    public controladorPropietario(Propietario pro, catalogoPropietarios catapro, Propietarios modpro, Condominio modcon) {
        this.vista = pro;
        this.catalogo = catapro;
        this.modelo = modpro;
        this.modCon = modcon;

        this.catalogo.btnActivar.addActionListener(this);
        this.catalogo.btn_NuevoPropietario.addActionListener(this);
        this.catalogo.addWindowListener(this);
        this.catalogo.txtBuscarPropietarios.addKeyListener(this);
        this.catalogo.TablaPropietarios.addMouseListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        pro.txtCedula.addKeyListener(this);
        pro.txtPnombre.addKeyListener(this);
        pro.txtSnombre.addKeyListener(this);
        pro.txtPapellido.addKeyListener(this);
        pro.txtSapellido.addKeyListener(this);
        pro.txtTelefono.addKeyListener(this);
        pro.txtCorreo.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnActivar) {
            this.cataipro.setVisible(true);
            Llenartablainactivos(cataipro.jTable1);
            addCheckBox(5, cataipro.jTable1);
        }
        if (e.getSource() == catalogo.btn_NuevoPropietario) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtCedula.setEnabled(true);
            this.catalogo.addWindowListener(this);

            vista.txtCedula.setText("");
            vista.txtApellido.setText("");
            vista.txtCorreo.setText("");
            vista.txtNombre.setText("");
            vista.txtTelefono.setText("");

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo.setCedula(vista.txtCedula.getText());
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setApellido(vista.txtApellido.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setTelefono(vista.txtTelefono.getText());

                if (modelo.registrar(modelo)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }

            }
        }

        if (e.getSource()
                == vista.btnModificar) {
            if (validar()) {
                modelo.setCedula(vista.txtCedula.getText());
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setApellido(vista.txtApellido.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setTelefono(vista.txtTelefono.getText());

                if (modelo.modificar(modelo)) {

                    vista.dispose();
                    limpiar();
                    llenarTabla(catalogo.TablaPropietarios);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }

            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modelo.setCedula(vista.txtCedula.getText());

            if (modelo.buscarunidadesasociadas(modelo)) {
                JOptionPane.showMessageDialog(null, "No puede eliminar un propietario con unidades asociadas");
            } else {
                java.util.Date fecha = new Date();
                java.sql.Date sqlDate = convert(fecha);
                modelo.setFecha_hasta(sqlDate);
                if (modelo.eliminar(modelo)) {
                    modelo.eliminarpuenteuni(modelo);
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    vista.dispose();
                    llenarTabla(catalogo.TablaPropietarios);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }
            }

        }

        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }
    }

    public void llenarTabla(JTable tablaD) {
        listaPropietarios = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Cédula/<br>Rif</html>");
        modeloT.addColumn("<html>Nombre/<br>Razón Social</html>");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("<html>Correo <br> Electrónico</html>");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaPropietarios.size();
        int col;

        for (int i = 0; i < numRegistro; i++) {
            col = 0;
            columna[col++] = listaPropietarios.get(i).getCedula();
            columna[col++] = listaPropietarios.get(i).getpNombre();
            columna[col++] = listaPropietarios.get(i).getsNombre();
            columna[col++] = listaPropietarios.get(i).getpApellido();
            columna[col++] = listaPropietarios.get(i).getsApellido();
            columna[col++] = listaPropietarios.get(i).getTelefono();
            columna[col++] = listaPropietarios.get(i).getCorreo();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);

        }
    }

    public void Llenartablainactivos(JTable tablaD) {

        listaPropietarios = modelo.listarinactivos();
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
                    resu = false;
                }
                if (column == 3) {
                    resu = false;
                }
                if (column == 4) {
                    resu = false;
                }
                if (column == 5) {
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Cédula/<br>Rif</html>");
        modeloT.addColumn("<html>Nombre/<br>Razón Social</html>");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("<html>Correo <br> Electrónico</html>");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[6];

        int numRegistro = listaPropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaPropietarios.get(i).getCedula();
            columna[1] = listaPropietarios.get(i).getNombre();
            columna[2] = listaPropietarios.get(i).getApellido();
            columna[3] = listaPropietarios.get(i).getTelefono();
            columna[4] = listaPropietarios.get(i).getCorreo();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);

    }

    public void limpiar() {

        vista.txtCedula.setText(null);
        vista.txtNombre.setText(null);
        vista.txtApellido.setText(null);
        vista.txtTelefono.setText(null);
        vista.txtCorreo.setText(null);
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtCedula.getText().isEmpty()) {

            msj += "El campo C.I./RIF. no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtNombre.getText().isEmpty()) {

            msj += "El campo nombre no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtApellido.getText().isEmpty()) {

            msj += "El campo apellido no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtTelefono.getText().isEmpty()) {

            msj += "El campo teléfono no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtCorreo.getText().isEmpty()) {

            msj += "El campo correo electrónico no puede estar vacío\n";
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
        int fila = this.catalogo.TablaPropietarios.getSelectedRow(); // primero, obtengo la fila seleccionada

        String dato = String.valueOf(this.catalogo.TablaPropietarios.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        catalogo.txtBuscarPropietarios.setText(String.valueOf(dato));

        modelo.setCedula(String.valueOf(dato));

        modelo.buscar(modelo);

        vista.setVisible(true);
        vista.txtCedula.setText(modelo.getCedula());
        vista.txtApellido.setText(modelo.getApellido());
        vista.txtCorreo.setText(modelo.getCorreo());
        vista.txtNombre.setText(modelo.getNombre());
        vista.txtTelefono.setText(modelo.getTelefono());

        vista.txtCedula.setEnabled(false);

        vista.btnGuardar.setEnabled(false);

        vista.btnModificar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        modCon.setRif(modelo.getCedula());

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
        if (ke.getSource() == vista.txtCedula) {
            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCedula.getText(), 8);
        }
        if (ke.getSource() == vista.txtNombre) {

            Validacion.soloLetras(ke);

            Validacion.limite(ke, vista.txtNombre.getText(), 30);
        }
        if (ke.getSource() == vista.txtApellido) {

            Validacion.soloLetras(ke);

            Validacion.limite(ke, vista.txtApellido.getText(), 100);
        }
        if (ke.getSource() == vista.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, vista.txtTelefono.getText(), 11);
        }
        if (ke.getSource() == vista.txtCorreo) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCorreo.getText(), 100);

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscarPropietarios) {

            filtro(catalogo.txtBuscarPropietarios.getText(), catalogo.TablaPropietarios);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

        llenarTabla(catalogo.TablaPropietarios);

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtCedula, vista.txtNombre, vista.txtApellido, vista.txtTelefono, vista.txtCorreo
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

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

}
