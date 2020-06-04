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
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import modelo.Condominio;
import modelo.Funcion;
import modelo.Unidades;
import sgc.SGC;
import vista.PantallaPrincipal;
import vista.VisCondominio;

public class CtrlCondominio implements ActionListener, MouseListener, KeyListener, WindowListener {

//    private catalogoCondominio catalogo;
    private VisCondominio vista;
    private Condominio modelo;

    private PantallaPrincipal panta;
    private Unidades moduni;
//    private catalogoInactivoCondominio cataico;

    private Funcion permiso;
//    ArrayList<Condominio> listaCondo;
//    ArrayList<Unidades> listaunidades;

    public CtrlCondominio() {
//        this.catalogo = new catalogoCondominio();
        this.vista = new VisCondominio();
        this.panta = new PantallaPrincipal();
        this.modelo = new Condominio();
        this.moduni = new Unidades();
//        this.cataico = new catalogoInactivoCondominio();

//        this.catalogo.btnActivar.addActionListener(this);
//        this.cataico.btnActivar.addActionListener(this);
//        this.catalogo.btnNuevo.addActionListener(this);
//        this.catalogo.jTable1.addMouseListener(this);
//        this.catalogo.txtBuscar.addKeyListener(this);
//        this.catalogo.addWindowListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.txtRif.addKeyListener(this);
        this.vista.txtRazonS.addKeyListener(this);
        this.vista.txtTelefono.addKeyListener(this);
        this.vista.txtCorreo.addKeyListener(this);

        if (modelo.buscar()) {
            vista.txtRif.setEditable(false);

            vista.txtRif.setText(modelo.getRif());
            vista.txtRazonS.setText(modelo.getRazonS());
            vista.txtTelefono.setText(modelo.getTelefono());
            vista.txtCorreo.setText(modelo.getCorreoElectro());
        }

        CtrlVentana.cambiarVista(vista);

//        catalogo.setVisible(true);
    }

    public CtrlCondominio(Boolean boo) {
        this.vista = new VisCondominio();
        this.modelo = new Condominio();

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.txtRif.addKeyListener(this);
        this.vista.txtRazonS.addKeyListener(this);
        this.vista.txtTelefono.addKeyListener(this);
        this.vista.txtCorreo.addKeyListener(this);

        vista.setVisible(true);
    }

//    public void Llenartabla(JTable tablaD) {
//
//        listaCondo = co.lPerson();
//        DefaultTableModel modeloT = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//
//                return false;
//            }
//
//        };
//        tablaD.setModel(modeloT);
//        tablaD.getTableHeader().setReorderingAllowed(false);
//        tablaD.getTableHeader().setResizingAllowed(false);
//
//        modeloT.addColumn("Rif");
//        modeloT.addColumn("Razón Social");
//        modeloT.addColumn("Teléfono");
//        modeloT.addColumn("Correo Electrónico");
//
//        Object[] columna = new Object[4];
//
//        int num = listaCondo.size();
//
//        for (int i = 0; i < num; i++) {
//
//            columna[0] = listaCondo.get(i).getRif();
//            columna[1] = listaCondo.get(i).getRazonS();
//            columna[2] = listaCondo.get(i).getTelefono();
//            columna[3] = listaCondo.get(i).getCorreoElectro();
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
//    public void Llenartablainactivos(JTable tablaD) {
//
//        listaCondo = co.lPersoni();
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
//                if (column == 2) {
//                    resu = false;
//                }
//                if (column == 3) {
//                    resu = false;
//                }
//                if (column == 4) {
//                    resu = true;
//                }
//
//                return resu;
//            }
//
//        };
//        tablaD.setModel(modeloT);
//        tablaD.getTableHeader().setReorderingAllowed(false);
//        tablaD.getTableHeader().setResizingAllowed(false);
//
//        modeloT.addColumn("Rif");
//        modeloT.addColumn("Razón Social");
//        modeloT.addColumn("Teléfono");
//        modeloT.addColumn("Correo Electrónico");
//        modeloT.addColumn("Seleccione");
//
//        Object[] columna = new Object[5];
//
//        int num = listaCondo.size();
//
//        for (int i = 0; i < num; i++) {
//
//            columna[0] = listaCondo.get(i).getRif();
//            columna[1] = listaCondo.get(i).getRazonS();
//            columna[2] = listaCondo.get(i).getTelefono();
//            columna[3] = listaCondo.get(i).getCorreoElectro();
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
//        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
//    }
//
    public void actionPerformed(ActionEvent e) {

//        if (e.getSource() == catalogo.btnNuevo) {
//            this.vista.setVisible(true);
//            this.vista.btnModificar.setEnabled(false);
//            this.vista.btnGuardar.setEnabled(true);
//            this.vista.txtRif.setEnabled(true);
//            vista.txtRif.setText("");
//            vista.txtRazonS.setText("");
//            vista.txtTelefono.setText("");
//            vista.txtCorreo.setText("");
//        }
//        if (e.getSource() == cataico.btnActivar) {
//            listaCondo = co.lPersoni();
//
//            for (int i = 0; i < cataico.jTable1.getRowCount(); i++) {
//                if (valueOf(cataico.jTable1.getValueAt(i, 4)) == "true") {
//
//                    co.setRif(listaCondo.get(i).getRif());
//                    co.activar(co);
//
//                }
//            }
//            Llenartablainactivos(cataico.jTable1);
//            addCheckBox(4, cataico.jTable1);
//            Llenartabla(cataco.jTable1);
//        }
//        if (e.getSource() == cataco.btnActivar) {
//            this.cataico.setVisible(true);
//            Llenartablainactivos(cataico.jTable1);
//            addCheckBox(4, cataico.jTable1);
//        }
        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo.setRif(vista.txtRif.getText());
                modelo.setRazonS(vista.txtRazonS.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setCorreoElectro(vista.txtCorreo.getText());

                if (modelo.existe()) {
                    if (modelo.modificar()) {
                        JOptionPane.showMessageDialog(null, "Datos actualizados");
                    }

                } else {
                    if (modelo.registrar()) {
                        JOptionPane.showMessageDialog(null, "Datos registrados");
                        SGC.condominioActual = modelo;

                        CtrlVentana ctrlMenu = new CtrlVentana();

                        //                    Llenartabla(catalogo.jTable1);
                        //                } else {
                        //                    JOptionPane.showMessageDialog(null, "Este registro ya existe");
                        //                }
                    }
                }
            }

//            if (e.getSource() == vista.btnModificar) {
//                if (validar()) {
//                    modelo.setRif(vista.txtRif.getText());
//                    modelo.setRazonS(vista.txtRazonS.getText());
//                    modelo.setTelefono(vista.txtTelefono.getText());
//                    modelo.setCorreoElectro(vista.txtCorreo.getText());
//
//                    if (modelo.modificar()) {
//
//                        JOptionPane.showMessageDialog(null, "Registro modificado");
//                        vista.dispose();
//                    Llenartabla(catalogo.jTable1);
//
//                    } else {
//
//                        JOptionPane.showMessageDialog(null, "Error al Modificar");
//
//                    }
//                }
//            }
//        if (e.getSource() == vista.btnEliminar) {
//            co.setRif(vista.txtRif.getText());
//            if (co.Buscargas(co) || co.Buscarcuo(co)) {
//                JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene gastos por procesar asignados");
//
//            } else {
//                if (co.Buscarsan(co)) {
//                    JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene Sanciones por procesar");
//                } else {
//                    if (co.Buscarcuen(co)) {
//                        JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene Cuentas asignadas");
//                    } else {
//                        if (co.Buscarin(co)) {
//                            JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene Interese asignados");
//                        } else {
//                            if (co.eliminar(co)) {
//                                co.eliminarunidadcondominio(co);
//
//                                listaunidades = moduni.buscar();
//                                int q = listaunidades.size();
//
//                                for (int i = 0; i < q; i++) {
//                                    moduni.setId(listaunidades.get(i).getId());
//                                    moduni.eliminarPuenteUnidad(moduni);
//                                }
//
//                                JOptionPane.showMessageDialog(null, "Registro Eliminado");
//                                vista.dispose();
//                                Llenartabla(catalogo.jTable1);
//
//                            } else {
//
//                                JOptionPane.showMessageDialog(null, "Error al Eliminar");
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }

    private void permisoBtn() {
        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;
            }
        }
    }

    public void limpiar() {
        vista.txtRazonS.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

//        String[] options = {"Entrar al menu", "Modificar datos"};
//        int result = JOptionPane.showOptionDialog(null, "Seleccione si desea entrar al menu o modificar datos", "MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//        if (result == 0) {
//            int fila = this.catalogo.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
//            int columna = this.catalogo.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
//            String dato = String.valueOf(this.catalogo.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
//            co.setRif(String.valueOf(dato));
//            SGC.condominioActual = co;
//
//            this.catalogo.dispose();
//            controladorMenuInterno ctrlMenuinterno = new controladorMenuInterno();
//            this.panta.dispose();
//
//        }
//        if (result == 1) {
//
//            int fila = this.catalogo.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
//            int columna = this.catalogo.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
//
//            if (permiso.getModificar()) {
//                vista.btnModificar.setEnabled(true);
//            }
//            String dato = String.valueOf(this.catalogo.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
//            co.setRif(String.valueOf(dato));
//
//            co.Buscar(co);
//
//            vista.setVisible(true);
//            vista.txtRif.setText(co.getRif());
//            vista.txtRazonS.setText(co.getRazonS());
//            vista.txtTelefono.setText(co.getTelefono());
//            vista.txtCorreo.setText(co.getCorreoElectro());
//            vista.txtRif.setEnabled(false);
//
//            vista.btnGuardar.setEnabled(false);
//
//            vista.btnModificar.setEnabled(true);
//        } else {
//
//        }
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

//    private void filtro(String consulta, JTable jtableBuscar) {
//        dm = (DefaultTableModel) jtableBuscar.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
//        jtableBuscar.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(consulta));
//    }
    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == vista.txtRif) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtRif.getText(), 15);
        }
        if (ke.getSource() == vista.txtRazonS) {

            Validacion.limite(ke, vista.txtRazonS.getText(), 150);
        }
        if (ke.getSource() == vista.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, vista.txtTelefono.getText(), 11);
        }
        if (ke.getSource() == vista.txtCorreo) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCorreo.getText(), 70);

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getSource() == catalogo.txtBuscar) {
//            filtro(catalogo.txtBuscar.getText(), catalogo.jTable1);
//        } else {
//
//        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
//        Llenartabla(catalogo.jTable1);
        permisoBtn();

//        if (permiso.getRegistrar()) {
//            catalogo.btnNuevo.setEnabled(true);
//        }
        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtRif, vista.txtRazonS, vista.txtTelefono, vista.txtCorreo
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

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtRif.getText().isEmpty()) {
            msj += "El campo RIF no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtRazonS.getText().isEmpty()) {
            msj += "El campo razón social no puede estar vacío\n";
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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
}
