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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.Propietarios;
import modelo.Unidades;
import vista.catalogoInactivoUnidades;
import vista.catalogoUnidades;
import vista.detalleRecibo;
import vista.detallecuenta;
import vista.unidades;
import vista.unidadesInactivas;

public class controladorUnidades implements ActionListener, MouseListener, KeyListener, WindowListener {

    private unidades vista;
    private Unidades modelo;
    private catalogoUnidades catalogo;

    private Propietarios modPropietario;

    private detallecuenta detacun;
    private detalleRecibo detare;
    private catalogoInactivoUnidades cataiuni;
    private unidadesInactivas unii;

    ArrayList<Unidades> listaUnidades;
    ArrayList<Propietarios> listaPropietarios;

    private CerrarMes modc;
    ArrayList<CerrarMes> listapagos;
    ArrayList<CerrarMes> listadetallegasto;
    ArrayList<CerrarMes> listadetallecuotas;
    ArrayList<CerrarMes> listadetallesancion;
    ArrayList<CerrarMes> listadetalleinteres;
    DefaultTableModel dm;

    public controladorUnidades() {
        this.vista = new unidades();
        this.catalogo = new catalogoUnidades();
        this.detacun = new detallecuenta();
        this.detare = new detalleRecibo();
        this.modelo = new Unidades();
        this.cataiuni = new catalogoInactivoUnidades();
        this.unii = new unidadesInactivas();
        this.modc = new CerrarMes();
        modPropietario = new Propietarios();

        this.unii.btnDesactivar.addActionListener(this);
        this.catalogo.addWindowListener(this);
        this.catalogo.btnNuevo.addActionListener(this);
        this.cataiuni.jTable1.addMouseListener(this);

        this.vista.txtArea.addKeyListener(this);

        this.vista.txtNumeroUnidad.addKeyListener(this);
        this.unii.jTable1.addMouseListener(this);

        this.catalogo.tabla.addMouseListener(this);

        this.vista.tablaPropietarios.addMouseListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.detacun.txtBuscar.addKeyListener(this);
        this.detacun.jTable1.addMouseListener(this);
        this.catalogo.setVisible(true);
    }

    public void llenarTablaPropietarios(JTable tablaD, String accion) {
        listaPropietarios = modPropietario.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;

                switch (column) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        resu = false;
                        break;
                    case 4:
                    case 5:
                        resu = true;
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
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Seleccione");
        addCheckBox(4, tablaD);

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro;
        int ind;

        if (accion.equals("Registrar")) {
            numRegistro = listaPropietarios.size();

            for (int i = 0; i < numRegistro; i++) {
                ind = 0;

                columna[ind++] = listaPropietarios.get(i).getCedula();
                columna[ind++] = listaPropietarios.get(i).getpNombre() + " " + listaPropietarios.get(i).getpApellido();
                columna[ind++] = listaPropietarios.get(i).getTelefono();
                columna[ind++] = listaPropietarios.get(i).getCorreo();

                modeloT.addRow(columna);

            }
        } else if (accion.equals("Modificar")) {
            numRegistro = listaPropietarios.size();

            for (int i = 0; i < numRegistro; i++) {
                ind = 0;

                columna[ind++] = listaPropietarios.get(i).getCedula();
                columna[ind++] = listaPropietarios.get(i).getpNombre() + " " + listaPropietarios.get(i).getpApellido();
                columna[ind++] = listaPropietarios.get(i).getTelefono();
                columna[ind++] = listaPropietarios.get(i).getCorreo();

                for (int j = 0; j < modelo.getPropietario().size(); j++) {
                    ind = 4;

                    if (listaPropietarios.get(i).getCedula().equals(modelo.getPropietario().get(j).getCedula())) {
                        columna[ind++] = Boolean.TRUE;
                        break;

                    } else {
                        columna[ind++] = Boolean.FALSE;

                    }
                }

                modeloT.addRow(columna);

            }
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);

        }
    }

    public void llenartablapagos(JTable tablaD) {

        listapagos = modc.listarpagos();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº <br>de Recibo</html>");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Monto");
        modeloT.addColumn("Alícuota");
        modeloT.addColumn("Estado");

        Object[] columna = new Object[6];

        int numRegistro = listapagos.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listapagos.get(i).gas.getId();
            columna[1] = listapagos.get(i).getMes_cierre();
            columna[2] = listapagos.get(i).getAño_cierre();
            columna[3] = Validacion.formato1.format(listapagos.get(i).getMonto());
            columna[4] = Validacion.formatoalicuota.format(listapagos.get(i).getAlicuota());
            columna[5] = listapagos.get(i).getEstado();

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

    public void llenardetallegasto(JTable tablaD) {

        listadetallegasto = modc.listardetallesgastos();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Tipo");
        modeloT.addColumn("Rif");
        modeloT.addColumn("Razón Social");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Monto");

        Object[] columna = new Object[5];

        int numRegistro = listadetallegasto.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetallegasto.get(i).gas.getTipo_gasto();
            columna[1] = listadetallegasto.get(i).prove.getCedula();
            columna[2] = listadetallegasto.get(i).prove.getNombre();
            columna[3] = listadetallegasto.get(i).concep.getNombre_Concepto();
            columna[4] = Validacion.formato1.format(listadetallegasto.get(i).getMonto());

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

    public void llenardetalleinteres(JTable tablaD) {

        listadetalleinteres = modc.listardetallesinteres();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Tipo de Interes ");
        modeloT.addColumn("Factor");
        modeloT.addColumn("Monto");

        Object[] columna = new Object[5];

        int numRegistro = listadetalleinteres.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetalleinteres.get(i).getEstado();
            String var6 = String.valueOf(listadetalleinteres.get(i).getAlicuota()) + "%";
            columna[1] = var6;
            columna[2] = Validacion.formato1.format(listadetalleinteres.get(i).getMonto());

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
    }

    public void llenardetallecuotas(JTable tablaD) {

        listadetallecuotas = modc.listardetallescuotas();
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
        modeloT.addColumn("<html>Razón <br> Social</html>");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Monto");
        modeloT.addColumn("<html>Meses <br> Restantes</html>");

        Object[] columna = new Object[5];

        int numRegistro = listadetallecuotas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetallecuotas.get(i).prove.getCedula();
            columna[1] = listadetallecuotas.get(i).prove.getNombre();
            columna[2] = listadetallecuotas.get(i).concep.getNombre_Concepto();
            columna[3] = Validacion.formato1.format(listadetallecuotas.get(i).getMonto());
            columna[4] = listadetallecuotas.get(i).cuo.getN_meses_restantes();

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

    public void llenardetallesancion(JTable tablaD) {

        listadetallesancion = modc.listardetallessancion();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Tipo ");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Factor");
        modeloT.addColumn("Monto");

        Object[] columna = new Object[4];

        int numRegistro = listadetallesancion.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetallesancion.get(i).san.getTipo();
            columna[1] = listadetallesancion.get(i).getEstado();
            if (listadetallesancion.get(i).san.getTipo().equals("Interes de mora")) {
                String var4 = listadetallesancion.get(i).getAlicuota() + "%";
                columna[2] = var4;
            } else {
                columna[2] = "";
            }

            columna[3] = Validacion.formato1.format(listadetallesancion.get(i).getMonto());

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
    }

    public void llenarTabla(JTable tablaD) {
        listaUnidades = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Número de <br> Unidad</html>");
        modeloT.addColumn("Dirección");
        modeloT.addColumn("Área (mts2)");
        modeloT.addColumn("<HTML>Número de <BR>Documento</HTML>");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaUnidades.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;

            columna[ind++] = listaUnidades.get(i).getN_unidad();
            columna[ind++] = listaUnidades.get(i).getDireccion();
            columna[ind++] = listaUnidades.get(i).getArea();
            columna[ind++] = listaUnidades.get(i).getDocumento();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);

        }

    }

//    public void llenartablaunidadesinactivas(JTable tablaD) {
//
//        listaUnidades = modelo.buscarUnidadesinactivas();
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
//
//                return resu;
//            }
//
//        };
//        tablaD.setModel(modeloT);
//        tablaD.getTableHeader().setReorderingAllowed(false);
//        tablaD.getTableHeader().setResizingAllowed(false);
//
//        modeloT.addColumn("<html>Número de <br> Unidad</html>");
//
//        modeloT.addColumn("Dirección");
//
//        modeloT.addColumn("Area (mts2)");
//
//        Object[] columna = new Object[3];
//
//        int numRegistro = listaUnidades.size();
//
//        for (int i = 0; i < numRegistro; i++) {
//
//            columna[0] = listaUnidades.get(i).getN_unidad();
//
//            columna[1] = listaUnidades.get(i).getDireccion();
//
//            columna[2] = listaUnidades.get(i).getArea();
//
//            modeloT.addRow(columna);
//
//        }
//        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
//        tcr.setHorizontalAlignment(SwingConstants.CENTER);
//        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
//        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
//        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
//
//    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtNumeroUnidad.setEnabled(true);
            this.vista.txtId.setVisible(false);
            llenarTablaPropietarios(vista.tablaPropietarios, "Registrar");
            addCheckBox(4, vista.tablaPropietarios);
            limpiar();

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                if (vista.tablaPropietarios.isEditing()) {//si se esta edtando la tabla
                    vista.tablaPropietarios.getCellEditor().stopCellEditing();//detenga la edicion
                }

                modelo = new Unidades();
                modelo.setN_unidad(vista.txtNumeroUnidad.getText());
                modelo.setDocumento(vista.txtDocumento.getText());
                modelo.setArea(Double.parseDouble(vista.txtArea.getText()));
                modelo.setDireccion(vista.txtDireccion.getText());

                int j = 0;

                for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
                    if (valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
                        j = j + 1;

                    }
                }

                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos 1 registro de la tabla");

                } else {
                    for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
                        if (valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
                            modelo.getPropietario().add(new Propietarios(vista.tablaPropietarios.getValueAt(i, 0).toString()));
                            modelo.setEstatus(1);

                        }
                    }

                    if (modelo.existeInactivo()) {
                        JOptionPane.showMessageDialog(null, "Este número de únidad ya existe en la BD, se recuperarán los datos para el nuevo registro");

                        try {
                            if (modelo.reactivar()) {
                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                vista.dispose();
                                llenarTabla(catalogo.tabla);

                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(controladorUnidades.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "No se pudo reactivar");

                        }

                    } else {
                        if (modelo.buscarepe(modelo)) {
                            JOptionPane.showMessageDialog(null, "Este condiminio ya tiene este número de unidad asignado");

                        } else {
                            if (modelo.registrar()) {
                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                vista.dispose();
                                llenarTabla(catalogo.tabla);

                            } else {
                                JOptionPane.showMessageDialog(null, "Este Registro ya existe");

                            }
                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                if (vista.tablaPropietarios.isEditing()) {//si se esta edtando la tabla
                    vista.tablaPropietarios.getCellEditor().stopCellEditing();//detenga la edicion
                }

                modelo = new Unidades();
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
                modelo.setN_unidad(vista.txtNumeroUnidad.getText());
                modelo.setDocumento(vista.txtDocumento.getText());
                modelo.setArea(Double.parseDouble(vista.txtArea.getText()));
                modelo.setDireccion(vista.txtDireccion.getText());

                int j = 0;

                for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
                    if (valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
                        j = j + 1;

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {
                    if (modelo.buscarepe(modelo) && !modelo.getN_unidad().equals(vista.txtNumeroUnidad.getText())) {
                        JOptionPane.showMessageDialog(null, "Este condiminio ya tiene este numero de unidad asignada");
                    } else {
                        modelo.getPropietario().clear();

                        for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
                            if (valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
                                modelo.getPropietario().add(new Propietarios(vista.tablaPropietarios.getValueAt(i, 0).toString()));

                            }
                        }

                        if (modelo.modificar()) {
                            JOptionPane.showMessageDialog(null, "Registro Modificado");
                            llenarTabla(catalogo.tabla);
                            vista.dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            if (modelo.eliminar()) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                vista.dispose();
                llenarTabla(catalogo.tabla);

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");

            }
//            if (modelo.buscarsancion(modelo)) {
//                JOptionPane.showMessageDialog(null, "No puede eliminar unidades que tengan sanciones pendientes");
//            } else {
//                if (vista.tablaPropietarios.isEditing()) {//si se esta edtando la tabla
//                    vista.tablaPropietarios.getCellEditor().stopCehumllEditing();//detenga la edicion
//                }
//                java.util.Date fecha = new Date();
//                java.sql.Date sqlDate = Validacion.convert(fecha);
//                modelo.setN_unidad(vista.txtNumeroUnidad.getText());
//                modelo.setArea(Double.parseDouble(vista.txtArea.getText()));
//                modelo.setDireccion(vista.txtDireccion.getText());
//                modelo.setId(Integer.parseInt(vista.txtId.getText()));
//                int j = 0;
//                int x = 0;
//                for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
//                    if (valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
//                        j = j + 1;
//
//                        if (!valueOf(vista.tablaPropietarios.getValueAt(i, 5)).equals("")) {
//                            x = x + 1;
//                        }
//
//                    }
//                }
//                if (j == 0) {
//                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
//                } else {
//                    if (j != x) {
//                        JOptionPane.showMessageDialog(null, "debe ingresar el numero de documento en la tabla");
//                    } else {
//
//                        if (modelo.buscarepe(modelo) && !modelo.getN_unidad().equals(vista.txtNumeroUnidad.getText())) {
//                            JOptionPane.showMessageDialog(null, "Este condiminio ya tiene este numero de unidad asignada");
//                        } else {
//
//                            if (modelo.modificarUnidades(modelo)) {
//                                listaPropietarios = modelo.buscarPropietariomod();
//                                for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
//
//                                    if (listaPropietarios.get(i).getId() != 0 && valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
//                                        if (!listaPropietarios.get(i).getDocumento().equals(valueOf(vista.tablaPropietarios.getValueAt(i, 5)))) {
//                                            modelo.setDocumento(String.valueOf(vista.tablaPropietarios.getValueAt(i, 5)));
//                                            modelo.setId_puente(listaPropietarios.get(i).getId_puente());
//                                            modelo.actualizardocumento(modelo);
//
//                                        }
//                                    }
//
//                                    if (listaPropietarios.get(i).getId() != 0 && valueOf(vista.tablaPropietarios.getValueAt(i, 4)).equals("false")) {
//                                        modelo.setFecha_hasta(sqlDate);
//                                        modelo.setEstatus(0);
//                                        modelo.setId_puente(listaPropietarios.get(i).getId_puente());
//                                        modelo.retirarpropietario(modelo);
//                                    }
//
//                                    if (listaPropietarios.get(i).getId() == 0 && valueOf(vista.tablaPropietarios.getValueAt(i, 4)).equals("true")) {
//                                        modelo.setCedula(valueOf(vista.tablaPropietarios.getValueAt(i, 0)));
//                                        modelo.setDocumento(valueOf(vista.tablaPropietarios.getValueAt(i, 5)));
//                                        modelo.setFecha_desde(sqlDate);
//                                        modelo.setEstatus(1);
//                                        modelo.registrar_propietario_unidad(modelo);
//                                    }
//
//                                }
//
//                                modelo.setId(Integer.parseInt(vista.txtId.getText()));
//                                modelo.eliminarUnidad(modelo);
//                                modelo.eliminarPuenteUnidad(modelo);
//
//                                llenarTabla(catalogo.tabla);
//                                vista.dispose();
//
//                                JOptionPane.showMessageDialog(null, "Registro Modificado");
//                                llenarTabla(catalogo.tabla);
//
//                                vista.dispose();
//
//                            } else {
//
//                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");
//
//                            }
//
//                        }
//                    }
//                }
//
//            }
//
        }
//        if (e.getSource() == unii.btnDesactivar) {
//            if (unii.jTable1.isEditing()) {//si se esta edtando la tabla
//                unii.jTable1.getCellEditor().stopCellEditing();//detenga la edicion
//            }
//            java.util.Date fecha = new Date();
//            java.sql.Date sqlDate = Validacion.convert(fecha);
//            modelo.setId(Integer.parseInt(unii.txtId.getText()));
//            int j = 0;
//            int x = 0;
//            for (int i = 0; i < unii.jTable1.getRowCount(); i++) {
//                if (valueOf(unii.jTable1.getValueAt(i, 4)) == "true") {
//                    j = j + 1;
//
//                    if (!valueOf(unii.jTable1.getValueAt(i, 5)).equals("")) {
//                        x = x + 1;
//
//                    }
//
//                }
//            }
//            if (j == 0) {
//                JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
//            } else {
//                if (j != x) {
//                    JOptionPane.showMessageDialog(null, "debe ingresar el numero de documento en la tabla");
//                } else {
//                    listaPropietarios = modelo.buscarPropietariomod();
//                    if (modelo.activarUnidad(modelo)) {
//                        JOptionPane.showMessageDialog(null, "Unidad restaurada");
//                        for (int w = 0; w < unii.jTable1.getRowCount(); w++) {
//
//                            if (listaPropietarios.get(w).getId() != 0 && valueOf(unii.jTable1.getValueAt(w, 4)) == "true") {
//                                if (!listaPropietarios.get(w).getDocumento().equals(valueOf(unii.jTable1.getValueAt(w, 5)))) {
//                                    modelo.setDocumento(String.valueOf(unii.jTable1.getValueAt(w, 5)));
//                                    modelo.setId_puente(listaPropietarios.get(w).getId_puente());
//                                    modelo.actualizardocumento(modelo);
//                                }
//                            }
//
//                            if (listaPropietarios.get(w).getId() != 0 && valueOf(unii.jTable1.getValueAt(w, 4)).equals("false")) {
//                                modelo.setFecha_hasta(sqlDate);
//                                modelo.setEstatus(0);
//                                modelo.setId_puente(listaPropietarios.get(w).getId_puente());
//                                modelo.retirarpropietario(modelo);
//                            }
//
//                            if (listaPropietarios.get(w).getId() == 0 && valueOf(unii.jTable1.getValueAt(w, 4)).equals("true")) {
//                                modelo.setCedula(valueOf(unii.jTable1.getValueAt(w, 0)));
//                                modelo.setDocumento(valueOf(unii.jTable1.getValueAt(w, 5)));
//                                modelo.setFecha_desde(sqlDate);
//                                modelo.setEstatus(1);
//                                modelo.registrar_propietario_unidad(modelo);
//                            }
//
//                        }
//                        llenartablaunidadesinactivas(cataiuni.jTable1);
//                        llenarTabla(catalogo.tabla);
//                        unii.dispose();
//                    }
//
//                }
//            }
//
//        }
        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }
    }

    public void limpiar() {

        vista.txtNumeroUnidad.setText(null);
        vista.txtDocumento.setText(null);
        vista.txtArea.setText(null);
        vista.txtDireccion.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == catalogo.tabla) {
            String[] options = {"Ver detalles de pago", "Modificar datos"};
            int result = JOptionPane.showOptionDialog(null, "Seleccione si desea ver detalles de pago o modificar datos", "MENÚ", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (result == 0) {
                int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
                listaUnidades = modelo.listar();
                String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

                String dato2 = String.valueOf(this.catalogo.tabla.getValueAt(fila, 2)); // por ultimo, obtengo el valor de la celda
                detacun.setVisible(true);
                modc.uni.setId(listaUnidades.get(fila).getId());
                detacun.txtArea.setText(dato2);
                llenartablapagos(detacun.jTable1);
                detacun.txtUnidad.setText(dato);
                modc.buscarmesesdedeuda(modc);
                detacun.txtMesesdeuda.setText(String.valueOf(modc.getMeses_deuda()));
            }

            if (result == 1) {
                // Obtengo la fila seleccionada
                int fila = this.catalogo.tabla.getSelectedRow();

                modelo = listaUnidades.get(fila);
                vista.setVisible(true);

                vista.txtNumeroUnidad.setText(modelo.getN_unidad());
                vista.txtDocumento.setText(modelo.getDocumento());
                vista.txtDireccion.setText(modelo.getDireccion());
                vista.txtArea.setText(String.valueOf(modelo.getArea()));

                vista.txtId.setVisible(false);
                vista.txtId.setText(String.valueOf(modelo.getId()));

                vista.txtNumeroUnidad.setEnabled(false);

                vista.btnEliminar.setEnabled(true);
                vista.btnModificar.setEnabled(true);
                vista.btnGuardar.setEnabled(false);

                llenarTablaPropietarios(vista.tablaPropietarios, "Modificar");
                addCheckBox(4, vista.tablaPropietarios);
            }
        }

        if (e.getSource() == vista.tablaPropietarios) {
            int fila = this.vista.tablaPropietarios.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.vista.tablaPropietarios.getValueAt(fila, 4)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                vista.tablaPropietarios.editCellAt(fila, 5);
            }
        }

        if (e.getSource() == unii.jTable1) {
            int fila = this.unii.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.unii.jTable1.getValueAt(fila, 4)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                unii.jTable1.editCellAt(fila, 5);
            }
        }

        if (e.getSource() == detacun.jTable1) {

            int fila = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String datos = String.valueOf(this.detacun.jTable1.getValueAt(fila, 1)); // por ultimo, obtengo el valor de la celda
            int fila2 = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato2 = String.valueOf(this.detacun.jTable1.getValueAt(fila2, 2)); // por ultimo, obtengo el valor de la celda
            int fila3 = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato3 = String.valueOf(this.detacun.jTable1.getValueAt(fila3, 0)); // por ultimo, obtengo el valor de la celda
            this.detare.setVisible(true);
            modc.setId(Integer.parseInt(dato3));

            modc.setMes_cierre(Integer.parseInt(datos));
            modc.setAño_cierre(Integer.parseInt(dato2));
            llenardetallegasto(detare.tablagastos);
            llenardetallecuotas(detare.tablacuotas);
            llenardetallesancion(detare.tablasancion);
            llenardetalleinteres(detare.tablainteres);
            detare.txtMes.setText(datos + "-" + dato2);
            detare.txtUnidad.setText(detacun.txtUnidad.getText());
            detare.txtArea.setText(detacun.txtArea.getText());
            detare.txtId.setText(dato3);
            modc.bucartotal(modc);

            detare.txtAlicuota.setText(String.valueOf(Validacion.formatoalicuota.format(modc.getAlicuota())));
            detare.txtTotal.setText(String.valueOf(Validacion.formato1.format(modc.getMonto())));
        }

        if (e.getSource() == cataiuni.jTable1) {
            int fila = this.cataiuni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.cataiuni.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

            modelo.setN_unidad(String.valueOf(dato));
            unii.setVisible(true);
            unii.txtId.setVisible(false);
            //modelo.buscarUnidad(modelo);
            unii.txadireccion.setText(modelo.getDireccion());
            unii.txtArea.setText(String.valueOf(modelo.getArea()));

            unii.txtId.setText(String.valueOf(modelo.getId()));
            unii.txtNumeroUnidad.setText(modelo.getN_unidad());
            //llenartablapropietariosmod(unii.jTable1);
            addCheckBox(4, unii.jTable1);
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

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == vista.txtNumeroUnidad) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtNumeroUnidad.getText(), 10);
        }
        if (ke.getSource() == vista.txtArea) {

            Validacion.soloUnPunto(ke, vista.txtArea.getText());
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtArea.getText(), 6);
        }
        if (ke.getSource() == vista.txtDireccion) {

            Validacion.limite(ke, vista.txtDireccion.getText(), 200);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        }

        if (e.getSource() == detacun.txtBuscar) {
            filtro(detacun.txtBuscar.getText(), detacun.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        llenarTabla(catalogo.tabla);

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtNumeroUnidad, vista.txtArea, vista.txtDireccion
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtNumeroUnidad.getText().isEmpty()) {

            msj += "El campo número de unidad no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txtArea.getText().isEmpty()) {

            msj += "El campo área no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txtDireccion.getText().isEmpty()) {

            msj += "El campo dirección no puede estar vacio\n";
            resultado = false;
        }

        if (vista.txtDocumento.getText().isEmpty()) {

            msj += "El campo número de documento no puede estar vacio\n";
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
