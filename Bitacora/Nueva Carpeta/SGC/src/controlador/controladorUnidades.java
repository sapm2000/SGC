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
import modelo.Funcion;
import modelo.Propietarios;
import modelo.TipoUnidad;
import modelo.Unidades;
import sgc.SGC;
import vista.Catalogo;
import vista.detalleRecibo;
import vista.detallecuenta;
import vista.unidades;

public class controladorUnidades implements ActionListener, MouseListener, KeyListener, WindowListener {

    private unidades vista;
    private Unidades modelo;
    private Catalogo catalogo;

    private Propietarios modPropietario;
    private TipoUnidad tipoUnidad;
    private detallecuenta detacun;
    private detalleRecibo detare;

    private ArrayList<Unidades> listaUnidades;
    private ArrayList<Propietarios> listaPropietarios;
    private ArrayList<TipoUnidad> listaTipoUnidad;

    private Funcion permiso;

    private CerrarMes modc;
    private ArrayList<CerrarMes> listapagos;
    private ArrayList<CerrarMes> listadetallegasto;
    private ArrayList<CerrarMes> listadetallecuotas;
    private ArrayList<CerrarMes> listadetallesancion;
    private ArrayList<CerrarMes> listadetalleinteres;

    public controladorUnidades() {
        this.catalogo = new Catalogo();
        this.vista = new unidades();
        this.modelo = new Unidades();
        catalogo.lblTitulo.setText("Unidades");
        tipoUnidad = new TipoUnidad();
        this.detacun = new detallecuenta();
        this.detare = new detalleRecibo();
        this.modc = new CerrarMes();
        modPropietario = new Propietarios();

        crearCbxTipoU();

        this.catalogo.addWindowListener(this);
        this.catalogo.btnNuevo.addActionListener(this);

        this.vista.txtNumeroUnidad.addKeyListener(this);

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtNumeroUnidad.setEnabled(true);
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
                int ind = vista.cbxTipo.getSelectedIndex() - 1;
                modelo.getTipo_Unidad().setId(listaTipoUnidad.get(ind).getId());
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
                                JOptionPane.showMessageDialog(null, "Unidad reactivada");
                                vista.dispose();
                                tipoUnidad.areaTotal();
                                float area = tipoUnidad.getArea();
                                listaUnidades = modelo.listarArea();
                                int x = listaUnidades.size();
                                for (int i = 0; i < x; i++) {
                                    float total = listaUnidades.get(i).tipo_Unidad.getArea() / area;
                                    modelo.setAlicuota(total);
                                    modelo.setId(listaUnidades.get(i).getId());
                                    modelo.actualizarAlicuota(modelo);
                                }
                                llenarTabla(catalogo.tabla);

                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo reactivar");
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(controladorUnidades.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        if (modelo.existe()) {
                            JOptionPane.showMessageDialog(null, "Este número de unidad ya existe");

                        } else {
                            if (modelo.registrar()) {
                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                tipoUnidad.areaTotal();
                                float area = tipoUnidad.getArea();
                                
                                listaUnidades = modelo.listarArea();
                                int x = listaUnidades.size();
                                for (int i = 0; i < x; i++) {
                                    float total = listaUnidades.get(i).tipo_Unidad.getArea() / area;
                                    modelo.setAlicuota(total);
                                    
                                    modelo.setId(listaUnidades.get(i).getId());
                                    modelo.actualizarAlicuota(modelo);
                                }
                                vista.dispose();
                                llenarTabla(catalogo.tabla);

                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo registrar");
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

                modelo.setN_unidad(vista.txtNumeroUnidad.getText());
                modelo.setDocumento(vista.txtDocumento.getText());
                int ind = vista.cbxTipo.getSelectedIndex() - 1;
                modelo.getTipo_Unidad().setId(listaTipoUnidad.get(ind).getId());
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
                    modelo.getPropietario().clear();

                    for (int i = 0; i < vista.tablaPropietarios.getRowCount(); i++) {
                        if (valueOf(vista.tablaPropietarios.getValueAt(i, 4)) == "true") {
                            modelo.getPropietario().add(new Propietarios(vista.tablaPropietarios.getValueAt(i, 0).toString()));

                        }
                    }

                    if (modelo.modificar()) {
                        JOptionPane.showMessageDialog(null, "Registro Modificado");
                        tipoUnidad.areaTotal();
                        float area = tipoUnidad.getArea();
                        listaUnidades = modelo.listarArea();
                        int x = listaUnidades.size();
                        for (int i = 0; i < x; i++) {
                            float total = listaUnidades.get(i).tipo_Unidad.getArea() / area;
                            modelo.setAlicuota(total);
                            modelo.setId(listaUnidades.get(i).getId());
                            modelo.actualizarAlicuota(modelo);
                        }
                        llenarTabla(catalogo.tabla);
                        vista.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar");

                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            if (modelo.eliminar()) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                tipoUnidad.areaTotal();
                float area = tipoUnidad.getArea();
                listaUnidades = modelo.listarArea();
                int x = listaUnidades.size();
                for (int i = 0; i < x; i++) {
                    float total = listaUnidades.get(i).tipo_Unidad.getArea() / area;
                    modelo.setAlicuota(total);
                    modelo.setId(listaUnidades.get(i).getId());
                    modelo.actualizarAlicuota(modelo);
                }
                vista.dispose();
                llenarTabla(catalogo.tabla);

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");

            }

        }

        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }
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

                if (permiso.getModificar()) {
                    vista.btnModificar.setEnabled(true);
                }
                if (permiso.getEliminar()) {
                    vista.btnEliminar.setEnabled(true);
                }

                modelo = listaUnidades.get(fila);

                vista.txtNumeroUnidad.setText(modelo.getN_unidad());
                vista.cbxTipo.setSelectedItem(modelo.getTipo_Unidad().getNombre());
                vista.txtDocumento.setText(modelo.getDocumento());
                vista.txtDireccion.setText(modelo.getDireccion());

                vista.txtNumeroUnidad.setEnabled(false);

                vista.btnGuardar.setEnabled(false);

                llenarTablaPropietarios(vista.tablaPropietarios, "Modificar");
                addCheckBox(4, vista.tablaPropietarios);

                vista.setVisible(true);
            }
        }

        if (e.getSource() == vista.tablaPropietarios) {
            int fila = this.vista.tablaPropietarios.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.vista.tablaPropietarios.getValueAt(fila, 4)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                vista.tablaPropietarios.editCellAt(fila, 5);
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
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtNumeroUnidad, vista.txtDireccion
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

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

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
        modeloT.addColumn("<html>Tipo de <br> Unidad</html>");
        modeloT.addColumn("Área (mts2)");
        modeloT.addColumn("<HTML>Número de <BR>Documento</HTML>");
        modeloT.addColumn("Alicuota");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaUnidades.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;

            columna[ind++] = listaUnidades.get(i).getN_unidad();
            columna[ind++] = listaUnidades.get(i).getDireccion();
            columna[ind++] = listaUnidades.get(i).getTipo_Unidad().getNombre();
            columna[ind++] = listaUnidades.get(i).getTipo_Unidad().getArea();
            columna[ind++] = listaUnidades.get(i).getDocumento();
            columna[ind++] = listaUnidades.get(i).getAlicuota();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);

        }

    }

    public void limpiar() {

        vista.txtNumeroUnidad.setText(null);
        vista.txtDocumento.setText(null);
        vista.cbxTipo.setSelectedIndex(0);
        vista.txtDireccion.setText(null);

    }

    private void crearCbxTipoU() {
        listaTipoUnidad = tipoUnidad.listar();
        vista.cbxTipo.addItem("Seleccione...");

        if (listaTipoUnidad != null) {
            for (TipoUnidad datosX : listaTipoUnidad) {
                vista.cbxTipo.addItem(datosX.getNombre());
            }
        }
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        DefaultTableModel dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    private Boolean validar() {
        Boolean resultado = true;
        String msj = "";

        if (vista.txtNumeroUnidad.getText().isEmpty()) {
            msj += "El campo número de unidad no puede estar vacío\n";
            resultado = false;
        }

        if (vista.cbxTipo.getSelectedIndex() == 0) {
            msj += "Debe seleccionar un Tipo de unidad\n";
            resultado = false;
        }

        if (vista.txtDireccion.getText().isEmpty()) {
            msj += "El campo dirección no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtDocumento.getText().isEmpty()) {
            msj += "El campo número de documento no puede estar vacío\n";
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
