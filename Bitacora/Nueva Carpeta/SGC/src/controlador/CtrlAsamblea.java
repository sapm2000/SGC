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
    DefaultTableModel dm;
    ArrayList<Propietarios> listaPropietarios;
    ArrayList<Asambleas> listaasambleas;
    ArrayList<Asambleas> listapropmod;
    Funcion permiso;
    private Asambleas modasa;
    private Propietarios modpro;

    public CtrlAsamblea() {
        this.catalogo = new Catalogo();
        this.vista = new VisAsamblea();
        this.modasa = new Asambleas();
        this.modpro = new Propietarios();

        CtrlVentana.cambiarVista(catalogo);
        catalogo.lblTitulo.setText("Asambleas");
        Llenartablaasambleas(catalogo.tabla);

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.btnNuevo.addActionListener(this);
        vista.btnSalir.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.txtBuscarPropietarios.addKeyListener(this);

        catalogo.setVisible(true);
    }

    public void Llenartabla(JTable tablaD) {

        listaPropietarios = modpro.listarxcon();
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

        modeloT.addColumn("<html>Cédula/ <br>Rif</html>");
        modeloT.addColumn("<html>Nombre/ <br> Razón Social</html>");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("<html>Correo <br> Electrónico</html>");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[5];

        int numRegistro = listaPropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaPropietarios.get(i).getCedula();
            columna[1] = listaPropietarios.get(i).getpNombre();
            columna[2] = listaPropietarios.get(i).getpApellido();
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
    }

    public void Llenartablaasambleas(JTable tablaD) {

        listaasambleas = modasa.listarAsambleas();
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
                return resu;
            }
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de<br> Asamblea</html>");
        modeloT.addColumn("<html>Nombre de <br> Asamblea</html>");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("<html>Nº de <br> Asistentes</html>");

        Object[] columna = new Object[5];

        int numRegistro = listaasambleas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaasambleas.get(i).getId();
            columna[1] = listaasambleas.get(i).getNombre_asamblea();
            columna[2] = listaasambleas.get(i).getFecha();
            columna[3] = listaasambleas.get(i).getDescripcion();
            columna[4] = listaasambleas.get(i).getN_asistentes();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
    }

    public void llenartablapropietariomodificar(JTable tablaD) {
        listapropmod = modasa.listarpropietariosmod();
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
        modeloT.addColumn("<html>Correo<br>Electrónico</html>");
        modeloT.addColumn("Selecione");

        Object[] columna = new Object[6];

        int numRegistro = listapropmod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listapropmod.get(i).getCedula();
            columna[1] = listapropmod.get(i).getNombre();
            columna[2] = listapropmod.get(i).getNombre();
            columna[3] = listapropmod.get(i).getTelefono();
            columna[4] = listapropmod.get(i).getCorreo();

            if (listapropmod.get(i).getId() != 0) {
                columna[5] = Boolean.TRUE;
            } else {
                columna[5] = Boolean.FALSE;
            }

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

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnGuardar.setEnabled(true);

            Llenartabla(vista.jTable1);
            addCheckBox(5, vista.jTable1);
            vista.txtNombreAsamblea.setText("");
            vista.txaDescripcion.setText("");
            vista.jDateChooser2.setDate(null);
            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                int j = 0;
                modasa.setNombre_asamblea(vista.txtNombreAsamblea.getText());
                modasa.setDescripcion(vista.txaDescripcion.getText());

                java.sql.Date sqlDate = convert(vista.jDateChooser2.getDate());
                modasa.setFecha(sqlDate);
                for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                    if (valueOf(vista.jTable1.getValueAt(i, 5)) == "true") {

                        j = j + 1;

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {

                    if (modasa.registrarAsambleas(modasa)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");

                        modasa.buscId(modasa);

                        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                            if (valueOf(vista.jTable1.getValueAt(i, 5)) == "true") {

                                String valor = String.valueOf(vista.jTable1.getValueAt(i, 0));
                                modasa.setId_propietario(valor);

                                modasa.registrar_asamblea_propietario(modasa);

                            }
                        }
                        Llenartablaasambleas(catalogo.tabla);
                        CtrlVentana.cambiarVista(catalogo);

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }
        }
        
        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }

        /*        if (e.getSource() == as.btnModificar) {
            if (validar()) {
                int j = 0;

                modasa.setNombre_asamblea(as.txtNombreAsamblea.getText());
                modasa.setDescripcion(as.txaDescripcion.getText());
               
                modasa.setId(Integer.parseInt(as.txtid.getText()));

                java.sql.Date sqlDate = convert(as.jDateChooser2.getDate());
                modasa.setFecha(sqlDate);
                for (int i = 0; i < as.tabla.getRowCount(); i++) {
                    if (valueOf(as.tabla.getValueAt(i, 5)) == "true") {

                        j = j + 1;

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {

                    if (modasa.modificarAsamblea(modasa)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");

                        modasa.borrarpuenteasamblea(modasa);

                        for (int i = 0; i < as.tabla.getRowCount(); i++) {
                            if (valueOf(as.tabla.getValueAt(i, 5)) == "true") {

                                String valor = String.valueOf(as.tabla.getValueAt(i, 0));
                                modasa.setId_propietario(valor);

                                modasa.registrar_asamblea_propietario(modasa);

                            }
                        }
                        Llenartablaasambleas(cataa.tabla);
                        as.dispose();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }
        }
         */
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txtBuscarPropietarios) {

            filtro(vista.txtBuscarPropietarios.getText(), vista.jTable1);
        }
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.tabla.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        modasa.setId(Integer.parseInt(dato));

        modasa.buscarAsambleas(modasa);
        vista.txtid.setVisible(false);
        vista.txtid.setText(dato);
        vista.txtNombreAsamblea.setText(modasa.getNombre_asamblea());
        vista.txaDescripcion.setText(modasa.getDescripcion());
        vista.jDateChooser2.setDate(modasa.getFecha());
        llenartablapropietariomodificar(vista.jTable1);
        addCheckBox(5, vista.jTable1);;
        vista.btnGuardar.setEnabled(false);
//        as.btnModificar.setEnabled(true);
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
    public void windowOpened(WindowEvent e) {

        Component[] components = vista.jPanel4.getComponents();
        JComponent[] com = {
            vista.txtNombreAsamblea, vista.jDateChooser2
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

    private void filtro(String consulta, JTable tablaBuscar) {
        dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtNombreAsamblea.getText().isEmpty()) {

            msj += "El campo nombre de la asamblea no puede estar vacio\n";
            resultado = false;
        }
        if (vista.txaDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

}