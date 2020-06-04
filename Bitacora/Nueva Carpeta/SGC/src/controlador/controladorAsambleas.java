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
import vista.asambleas;

/**
 *
 * @author rma
 */
public class controladorAsambleas implements ActionListener, KeyListener, MouseListener, WindowListener {

    private Catalogo catalogo;
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
    private VisAsamblea vista;
    private Asambleas modelo;
    private ArrayList<Asambleas> lista;

    private Propietarios modPropietario;
    private ArrayList<Propietarios> listaPropietario;

    private Funcion permiso;
=======
    private asambleas vista;
    DefaultTableModel dm;
    ArrayList<Propietarios> listaPropietarios;
    ArrayList<Asambleas> listaasambleas;
    ArrayList<Asambleas> listapropmod;
    Funcion permiso;
    private Asambleas modasa;
    private Propietarios modpro;
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java

    public controladorAsambleas() {
        this.catalogo = new Catalogo();
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
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
=======
        this.vista = new asambleas();
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
        this.vista.txtBuscarPropietarios.addKeyListener(this);

        this.vista.btnGuardar.addActionListener(this);
          catalogo.setVisible(true);

        //this.as.btnModificar.addActionListener(this);
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
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java

        CtrlVentana.cambiarVista(catalogo);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
//            this.as.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java

            vista.txtNombre.setText("");
            vista.txaDescripcion.setText("");
            vista.txtFecha.setDate(null);

            llenarTablaPropietarios(vista.tablaAsistentes, "Registrar");
            addCheckBox(2, vista.tablaAsistentes);

            CtrlVentana.cambiarVista(vista);
=======
           
            Llenartabla(vista.jTable1);
            addCheckBox(5, vista.jTable1);
            vista.txtNombreAsamblea.setText("");
            vista.txaDescripcion.setText("");
            vista.jDateChooser2.setDate(null);

>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
                int j;
=======
                int j = 0;
                modasa.setNombre_asamblea(vista.txtNombreAsamblea.getText());
                modasa.setDescripcion(vista.txaDescripcion.getText());
             
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java

                modelo = new Asambleas();
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setDescripcion(vista.txaDescripcion.getText());
                modelo.setFecha(Validacion.convert(vista.txtFecha.getDate()));

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
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
                    }

                    if (modelo.registrar()) {
                        JOptionPane.showMessageDialog(null, "Registro guardado");
                        llenarTabla(catalogo.tabla);
                        CtrlVentana.cambiarVista(catalogo);
=======
                        Llenartablaasambleas(catalogo.tabla);
                        vista.dispose();
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar");

                    }
                }
            }
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
=======

>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java
        }
    }

<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
    @Override
    public void mouseClicked(MouseEvent e) {
        int fila;
        fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada

        modelo = lista.get(fila);

        vista.txtid.setVisible(false);
=======
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
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java

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
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
    public void mouseExited(MouseEvent e) {

=======
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.tabla.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        modasa.setId(Integer.parseInt(dato));
       
        modasa.buscarAsambleas(modasa);
        this.vista.setVisible(true);
        vista.txtid.setVisible(false);
        vista.txtid.setText(dato);
        vista.txtNombreAsamblea.setText(modasa.getNombre_asamblea());
        vista.txaDescripcion.setText(modasa.getDescripcion());
        vista.jDateChooser2.setDate(modasa.getFecha());
        llenartablapropietariomodificar(vista.jTable1);
        addCheckBox(5, vista.jTable1);;
        vista.btnGuardar.setEnabled(false);
//        as.btnModificar.setEnabled(true);
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java
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
<<<<<<< HEAD:Bitacora/Nueva Carpeta/SGC/src/controlador/CtrlAsamblea.java
        Component[] components = vista.jPanel4.getComponents();

=======
   
        Component[] components =vista.jPanel4.getComponents();
>>>>>>> parent of a9ecd90... Dañe un coñazo de vista.:Bitacora/Nueva Carpeta/SGC/src/controlador/controladorAsambleas.java
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
            if (funcionbtn.getNombre().equals("Asamblea")) {
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
