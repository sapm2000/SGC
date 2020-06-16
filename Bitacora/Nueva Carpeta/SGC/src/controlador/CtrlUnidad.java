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
import static java.lang.String.valueOf;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.Funcion;
import modelo.Propietarios;
import modelo.TipoUnidad;
import modelo.Unidades;
import sgc.SGC;
import vista.Catalogo;
import vista.VisUnidad;
import vista.detalleRecibo;
import vista.detallecuenta;

public class CtrlUnidad implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private VisUnidad vista;
    private Unidades modelo;
    private Catalogo catalogo;
    int fa = 0;
    private Propietarios modPropietario;
    private TipoUnidad tipoUnidad;
    private detallecuenta detacun;
    private detalleRecibo detare;
    ArrayList<CerrarMes> listaDominante;
    ArrayList<CerrarMes> listaCierremes;
    ArrayList<CerrarMes> listax;

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

    public CtrlUnidad() {
        this.catalogo = new Catalogo();
        this.vista = new VisUnidad();
        this.modelo = new Unidades();

        catalogo.lblTitulo.setText("Unidades");
        CtrlVentana.cambiarVista(catalogo);
        vista.cbxTipo.addItemListener(this);
        stylecombo(vista.cbxTipo);
        llenarTabla(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        tipoUnidad = new TipoUnidad();
        this.detacun = new detallecuenta();
        this.detare = new detalleRecibo();
        this.modc = new CerrarMes();
        modPropietario = new Propietarios();

        crearCbxTipoU();

        this.catalogo.btnNuevo.addActionListener(this);

        this.vista.txtNumeroUnidad.addKeyListener(this);

        this.catalogo.tabla.addMouseListener(this);

        this.vista.tablaPropietarios.addMouseListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.detacun.txtBuscar.addKeyListener(this);
        this.detacun.jTable1.addMouseListener(this);

        this.catalogo.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtNumeroUnidad.setEnabled(true);

            llenarTablaPropietarios(vista.tablaPropietarios, "Registrar");
            addCheckBox(4, vista.tablaPropietarios);
            limpiar();

            CtrlVentana.cambiarVista(vista);
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
                                CtrlVentana.cambiarVista(catalogo);
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
                            Logger.getLogger(CtrlUnidad.class.getName()).log(Level.SEVERE, null, ex);
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
                                CtrlVentana.cambiarVista(catalogo);
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
                        CtrlVentana.cambiarVista(catalogo);

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
                CtrlVentana.cambiarVista(catalogo);
                llenarTabla(catalogo.tabla);

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");

            }

        }

        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catalogo.tabla) {
            String[] options = {"Ver detalles de pago", "Modificar datos"};
            int result = JOptionPane.showOptionDialog(null, "Seleccione si desea ver detalles de pago o modificar datos", "MENÚ", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (result == 0) {
                String hola = "";
                do {
                    hola = "";
                    hola = JOptionPane.showInputDialog("ingrese la paridad a trabajar"); //ventana que se despliega para que ingresen la paridad 
                    modc.setParidad(Double.parseDouble(hola));
                    int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
                    listaUnidades = modelo.listar();
                    String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

                    String dato2 = String.valueOf(this.catalogo.tabla.getValueAt(fila, 3)); // por ultimo, obtengo el valor de la celda
                    detacun.setVisible(true);
                    modc.uni.setId(listaUnidades.get(fila).getId());
                    detacun.txtArea.setText(dato2);
                    listaDominante = modc.listarDominantes();
                    fa = 0;
                    Llenartabla(detacun.jTable1, listaDominante);
                    detacun.txtUnidad.setText(dato);

                    detacun.txtMesesdeuda.setText(String.valueOf(fa));

                    if (hola == null) {
                        break;
                    } else {

                    }
                } while (isValidDouble(hola) == false); //ciclo que repite mientras no ingresen un numero valido

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

                CtrlVentana.cambiarVista(vista);
            }
        }

        if (e.getSource()
                == vista.tablaPropietarios) {
            int fila = this.vista.tablaPropietarios.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.vista.tablaPropietarios.getValueAt(fila, 4)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                vista.tablaPropietarios.editCellAt(fila, 5);
            }
        }

        if (e.getSource()
                == detacun.jTable1) {

            int fila = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String datos = String.valueOf(this.detacun.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            int fila2 = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato2 = String.valueOf(this.detacun.jTable1.getValueAt(fila2, 1)); // por ultimo, obtengo el valor de la celda
            this.detare.setVisible(true);
            String dato3 = String.valueOf(this.detacun.jTable1.getValueAt(fila2, 6)); // por ultimo, obtengo el valor de la celda

            modc.setMes_cierre(Integer.parseInt(datos));
            modc.setAño_cierre(Integer.parseInt(dato2));
            llenardetallegasto(detare.tablagastos, dato3);

            llenardetallesancion(detare.tablasancion, dato3);
            llenardetalleinteres(detare.tablainteres, dato3);
            detare.txtMes.setText(datos + "-" + dato2);
            detare.txtUnidad.setText(detacun.txtUnidad.getText());
            detare.txtArea.setText(detacun.txtArea.getText());
            modc.bucaralicuota(modc);
            detare.txtAlicuota.setText(String.valueOf(Validacion.formatoalicuota.format(modc.getAlicuota())));

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

    public void Llenartabla(JTable tablaD, ArrayList listadominante) { //funcion para llenar la tabla de pagos pendientes

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { //indicamos que columnas son editables y cuales no

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
                    resu = false;
                }
                if (column == 6) {
                    resu = false;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Mes a <br> Cobrar</html>"); //añadimos el encabezado de la columna
        modeloT.addColumn("Año");
        modeloT.addColumn("<html>Monto <br> en $</html>");
        modeloT.addColumn("<html>Monto <br> en BsS</html>");
        modeloT.addColumn("<html>Saldo <br> Restante $</html>");
        modeloT.addColumn("<html>Saldo <br> Restante BsS</html>");
        modeloT.addColumn("<html>Mantener<br> Valor en</html>");

        Object[] columna = new Object[7];

        for (int s = 0; s < listaDominante.size(); s++) { //ciclo que busca los gastos por la moneda dominante, mes y año
            modc.setMes_cierre(listaDominante.get(s).getMes_cierre()); //seteamos los datos necesarios para la busqueda
            modc.setAño_cierre(listaDominante.get(s).getAño_cierre());
            modc.uni.setId(listaDominante.get(s).uni.getId());
            listax = modc.listarpagospendientes(listaDominante.get(s).getMoneda_dominante());
            fa = fa + listax.size();
            listaCierremes = modc.listarpagostotales(listaDominante.get(s).getMoneda_dominante()); // buscamos los datos por cada mes, año, unidad, se suman y multiplican/dividen por la paridad para obtener el total a mostrar
            int numRegistro = listaCierremes.size();

            for (int i = 0; i < numRegistro; i++) { //llenamos la columna con los datos obtenidos

                columna[0] = listaCierremes.get(i).getMes_cierre();
                columna[1] = listaCierremes.get(i).getAño_cierre();
                columna[2] = listaCierremes.get(i).getMonto_bolivar();
                columna[3] = listaCierremes.get(i).getMonto_dolar();
                columna[4] = Validacion.formatopago.format(listaCierremes.get(i).getSaldo_restante_dolar()); //limito los decimales
                columna[5] = Validacion.formatopago.format(listaCierremes.get(i).getSaldo_restante_bs());
                columna[6] = listaCierremes.get(i).getMoneda_dominante();

                modeloT.addRow(columna);

            }
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(6).setCellRenderer(tcr);

        TableColumnModel columnModel = tablaD.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25);
        columnModel.getColumn(1).setPreferredWidth(18);
        columnModel.getColumn(2).setPreferredWidth(35);
        columnModel.getColumn(3).setPreferredWidth(35);
        columnModel.getColumn(4).setPreferredWidth(35);
        columnModel.getColumn(5).setPreferredWidth(35);
        columnModel.getColumn(6).setPreferredWidth(35);

    }

    public void llenardetallegasto(JTable tablaD, String mone) {

        listadetallegasto = modc.listargastos(mone);
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Rif");
        modeloT.addColumn("Razón Social");
        modeloT.addColumn("monto bs");
        modeloT.addColumn("monto $");
        modeloT.addColumn("concepto");
        modeloT.addColumn("tipo de gasto");

        Object[] columna = new Object[6];

        int numRegistro = listadetallegasto.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetallegasto.get(i).prove.getCedula();
            columna[1] = listadetallegasto.get(i).prove.getNombre();
            columna[2] = listadetallegasto.get(i).getMonto_bolivar();
            columna[3] = listadetallegasto.get(i).getMonto_dolar();
            columna[4] = listadetallegasto.get(i).concep.getNombre();
            columna[5] = listadetallegasto.get(i).getTipo_gasto();

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

    public void llenardetalleinteres(JTable tablaD, String mone) {

        listadetalleinteres = modc.listardetallesinteres(mone);
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
        modeloT.addColumn("Monto $");
        modeloT.addColumn("Monto bs");

        Object[] columna = new Object[4];

        int numRegistro = listadetalleinteres.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetalleinteres.get(i).in.getNombre();
            String var6 = String.valueOf(listadetalleinteres.get(i).in.getFactor()) + "%";
            columna[1] = var6;
            columna[2] = listadetalleinteres.get(i).getMonto_dolar();
            columna[3] = listadetalleinteres.get(i).getMonto_bolivar();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
    }

    public void llenardetallesancion(JTable tablaD, String mone) {

        listadetallesancion = modc.listardetallessancion(mone);
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
        modeloT.addColumn("Monto $");
        modeloT.addColumn("Monto bs");

        Object[] columna = new Object[5];

        int numRegistro = listadetallesancion.size();
        

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listadetallesancion.get(i).san.getTipo();
            columna[1] = listadetallesancion.get(i).san.getDescripcion();
            if (listadetallesancion.get(i).san.getTipo().equals("Interes de mora")) {
                columna[2] = listadetallesancion.get(i).san.getMonto()+"%";
            } else {
                columna[2] = "";
            }
            columna[3] = listadetallesancion.get(i).getMonto_dolar();
            columna[4] = listadetallesancion.get(i).getMonto_bolivar();

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
            columna[ind++] = Validacion.formatoalicuota.format(listaUnidades.get(i).getAlicuota());

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
        tc.setCellEditor(table.getDefaultEditor(Boolean.class
        ));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class
        ));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxTipo.setFocusable(false);
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }

    private static boolean isValidDouble(String s) {//funcion para validar si un dato es numerico
        boolean isValid = true;

        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            isValid = false;
        }

        return isValid;
    }
}
