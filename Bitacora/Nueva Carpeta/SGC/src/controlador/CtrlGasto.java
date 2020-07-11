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
import java.util.ArrayList;
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
import modelo.Asambleas;
import modelo.CerrarMes;
import modelo.ConceptoGasto;
import modelo.Funcion;
import modelo.Gasto;
import modelo.Proveedores;
import sgc.SGC;
import vista.Catalogo;
import vista.VisGasto;
import vista.buscarProveedor;

public class CtrlGasto implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private Catalogo catalogo;
    private VisGasto vista;
    private Gasto modelo;
    private ArrayList<Gasto> lista;

    private ConceptoGasto modConcepto;
    private Proveedores modProveedor;
    private Asambleas modAsamblea;
    private CerrarMes modc;

    private buscarProveedor catProveedores;

    private ArrayList<Proveedores> listaProveedores;
    private ArrayList<Asambleas> listaAsambleas;
    private ArrayList<ConceptoGasto> listaConcepto;
    DefaultTableModel dm;
    private Funcion permiso;

    public CtrlGasto() {
        this.catalogo = new Catalogo();
        this.vista = new VisGasto();
        this.modelo = new Gasto();

        this.modConcepto = new ConceptoGasto();
        this.modProveedor = new Proveedores();
        this.modAsamblea = new Asambleas();
        this.modc = new CerrarMes();
        this.catProveedores = new buscarProveedor();

        catalogo.lblTitulo.setText("Gasto");

        llenarTabla(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catProveedores.jTable1.addMouseListener(this);

        crearCbxAsamblea();

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnBuscarproveedor.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.jcombotipo.addItemListener(this);
        this.vista.si.addItemListener(this);
        this.vista.no.addItemListener(this);
        this.vista.jTable.addMouseListener(this);
        this.vista.jTable.addKeyListener(this);
        vista.txtNmeses.addKeyListener(this);
        vista.txaObservaciones.addKeyListener(this);

        CtrlVentana.cambiarVista(catalogo);
        vista.cbxMoneda.addItemListener(this);
        stylecombo(vista.cbxMoneda);
        vista.jAsamblea.addItemListener(this);
        stylecombo(vista.jAsamblea);
        vista.jCalcular.addItemListener(this);
        stylecombo(vista.jCalcular);
        vista.jcombotipo.addItemListener(this);
        stylecombo(vista.jcombotipo);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            llenarTablaConcepto(vista.jTable, "Registrar");
            addCheckBox(2, vista.jTable);

            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.jCalcular.setEnabled(true);

            vista.btnBuscarproveedor.setVisible(true);

            limpiar();

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnBuscarproveedor) {
            this.catProveedores.setVisible(true);
            llenarTablaProveedores(catProveedores.jTable1);
        }

        if (e.getSource() == vista.btnGuardar) {

            if (validar()) {
                //Se guardan en el modelo los datos básicos
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setTipo(vista.jcombotipo.getSelectedItem().toString());
                modelo.setProveedor(modProveedor);
                modelo.setCalcular(vista.jCalcular.getSelectedItem().toString());
                modelo.setMes(vista.jMonthChooser1.getMonth() + 1);
                modelo.setAnio(vista.jYearChooser1.getYear());
                modelo.setObservacion(vista.txaObservaciones.getText());
                modelo.setMoneda(vista.cbxMoneda.getSelectedItem().toString());

                //Se verifica si el monto es ordinario o extraordinario
                if (modelo.getTipo().equals("Ordinario")) {
                    //Si es ordinario, el número de meses que aplica es == 1
                    modelo.setNumMeses(1);
                    modelo.setMesesRestantes(1);

                } else if (modelo.getTipo().equals("Extraordinario")) {
                    //Si es extraordinario, el número de meses que aplica se obtiene del usuario
                    modelo.setNumMeses(Integer.parseInt(vista.txtNmeses.getText()));
                    modelo.setMesesRestantes(modelo.getNumMeses());

                    //Se verifica si el gasto fue seleccionado en una asamblea y se guarda la asamblea en ese caso
                    if (vista.si.isSelected()) {
                        modelo.setAsamblea(listaAsambleas.get(vista.jAsamblea.getSelectedIndex() - 1));
                    }
                }

                int mes = 0;
                int bre = 0;
                mes = modelo.getMes();

                for (int i = 0; i < modelo.getNumMeses(); i++) {

                    if (mes + i > 12) {
                        mes = mes - 12;

                        if (mes + 1 > 24) {
                            mes = mes - 12;
                        }
                    }

                    modc.setMes_cierre(mes + i);
                    modc.setAño_cierre(modelo.getAnio());

                    if (modc.buscarfechas(modc)) {
                        bre = 1;

                    } else {
                    }
                }

                if (bre == 1) {
                    JOptionPane.showMessageDialog(null, "No puede registrar gastos que incluyan un periodo ya cerrado");

                } else {
                    Double monto;
                    Double montoTotal = 0.;

                    //Se recorre cada concepto de la tabla
                    for (int i = 0; i < vista.jTable.getRowCount(); i++) {

                        //Si el concepto actual fue seleccionado
                        if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                            //Se obtiene el monto
                            monto = Double.parseDouble(vista.jTable.getValueAt(i, 3).toString());
                            //Se suma el monto al monto total
                            montoTotal += monto;
                            //Se guarda el concepto actual
                            modelo.getConceptos().add(listaConcepto.get(i));
                            //Y se guarda el monto individual de cada concepto
                            modelo.getMontoConceptos().add(monto);
                        }
                    }

                    //Se guardan en el modelo el monto y el saldo
                    modelo.setMonto(montoTotal);
                    modelo.setSaldo(montoTotal);

                    //Si se logró el registro
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

        if (e.getSource() == vista.btnModificar) {

            if (validar()) {

                //Se guardan en el modelo los datos básicos
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setTipo(vista.jcombotipo.getSelectedItem().toString());
                modelo.setProveedor(modProveedor);
                modelo.setCalcular(vista.jCalcular.getSelectedItem().toString());
                modelo.setMes(vista.jMonthChooser1.getMonth() + 1);
                modelo.setAnio(vista.jYearChooser1.getYear());
                modelo.setObservacion(vista.txaObservaciones.getText());
                modelo.setMoneda(vista.cbxMoneda.getSelectedItem().toString());

                //Se verifica si el monto es ordinario o extraordinario
                if (modelo.getTipo().equals("Ordinario")) {
                    //Si es ordinario, el número de meses que aplica es == 1
                    modelo.setNumMeses(1);
                    modelo.setMesesRestantes(1);
                    modelo.setAsamblea(null);

                } else if (modelo.getTipo().equals("Extraordinario")) {
                    //Si es extraordinario, el número de meses que aplica se obtiene del usuario
                    modelo.setNumMeses(Integer.parseInt(vista.txtNmeses.getText()));
                    modelo.setMesesRestantes(modelo.getNumMeses());

                    //Se verifica si el gasto fue seleccionado en una asamblea y se guarda la asamblea en ese caso
                    if (vista.si.isSelected()) {
                        modelo.setAsamblea(listaAsambleas.get(vista.jAsamblea.getSelectedIndex() - 1));

                    } else if (vista.no.isSelected()) {
                        modelo.setAsamblea(null);
                    }
                }

                int mes = 0;
                int bre = 0;
                mes = modelo.getMes();

                for (int i = 0; i < modelo.getNumMeses(); i++) {

                    if (mes + i > 12) {
                        mes = mes - 12;

                        if (mes + 1 > 24) {
                            mes = mes - 12;
                        }
                    }

                    modc.setMes_cierre(mes + i);
                    modc.setAño_cierre(modelo.getAnio());

                    if (modc.buscarfechas(modc)) {
                        bre = 1;

                    } else {
                    }
                }

                if (bre == 1) {
                    JOptionPane.showMessageDialog(null, "No pueden modificar cuotas especiales que incluyan un periodo ya cerrado");

                } else {
                    Double monto;
                    Double montoTotal = 0.;
                    modelo.getConceptos().clear();
                    modelo.getMontoConceptos().clear();

                    //Se recorre cada concepto de la tabla
                    for (int i = 0; i < vista.jTable.getRowCount(); i++) {

                        //Si el concepto actual fue seleccionado
                        if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                            //Se obtiene el monto
                            monto = Double.parseDouble(vista.jTable.getValueAt(i, 3).toString());
                            //Se suma el monto al monto total
                            montoTotal += monto;
                            //Se guarda el concepto actual
                            modelo.getConceptos().add(listaConcepto.get(i));
                            //Y se guarda el monto individual de cada concepto
                            modelo.getMontoConceptos().add(monto);
                        }
                    }

                    //Se guardan en el modelo el monto y el saldo
                    modelo.setMonto(montoTotal);
                    modelo.setSaldo(montoTotal);

                    //Si se logró la modificación
                    if (modelo.modificar()) {
                        JOptionPane.showMessageDialog(null, "Registro modificado");
                        llenarTabla(catalogo.tabla);
                        CtrlVentana.cambiarVista(catalogo);

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar");
                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    public void limpiar() {
        vista.txtNombre.setText(null);
        vista.jcombotipo.setSelectedIndex(0);
        vista.txtProveedor.setText(null);
        vista.jLabel2.setText(null);
        vista.jCalcular.setSelectedIndex(0);
        vista.cbxMoneda.setSelectedIndex(0);
        vista.txtNmeses.setText(null);

        vista.txtNmeses.setText(null);
        vista.jAsamblea.setSelectedIndex(0);
        vista.txaObservaciones.setText(null);
    }

    public void llenarTabla(JTable tablaD) {
        lista = modelo.listar("");

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br>Gasto</html>");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("<html>Calcular <br>por</html>");
        modeloT.addColumn("<html>Comienzo <br>de cobro</html>");
        modeloT.addColumn("<html>Monto <br>Inicial</html>");
        modeloT.addColumn("Saldo");
        modeloT.addColumn("Moneda");
        modeloT.addColumn("Asamblea");
        modeloT.addColumn("<html>Meses <br>iniciales</html>");
        modeloT.addColumn("<html>Meses <br>restantes</html>");
        modeloT.addColumn("Observación");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Estado de pago");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();
        int ind;
        String fecha;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getId();
            columna[ind++] = lista.get(i).getNombre();
            columna[ind++] = lista.get(i).getProveedor().getNombre();
            columna[ind++] = lista.get(i).getCalcular();
            fecha = String.valueOf(lista.get(i).getMes()) + " - " + lista.get(i).getAnio();
            columna[ind++] = fecha;
            columna[ind++] = Validacion.formato1.format(lista.get(i).getMonto());
            columna[ind++] = Validacion.formato1.format(lista.get(i).getSaldo());
            columna[ind++] = lista.get(i).getMoneda();

            if (lista.get(i).getAsamblea() != null) {
                columna[ind++] = lista.get(i).getAsamblea().getNombre();

            } else {
                columna[ind++] = null;
            }

            columna[ind++] = lista.get(i).getNumMeses();
            columna[ind++] = lista.get(i).getMesesRestantes();
            columna[ind++] = lista.get(i).getObservacion();
            columna[ind++] = lista.get(i).getEstado();
            columna[ind++] = lista.get(i).getPagado();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = tablaD.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25);
        columnModel.getColumn(1).setPreferredWidth(60);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(35);
        columnModel.getColumn(5).setPreferredWidth(35);
        columnModel.getColumn(7).setPreferredWidth(30);
        columnModel.getColumn(8).setPreferredWidth(35);
        columnModel.getColumn(10).setPreferredWidth(30);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    public void llenarTablaConcepto(JTable tablaD, String accion) {
        int ind;

        listaConcepto = modConcepto.listar();

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
                    case 3:
                        resu = true;
                        break;
                    default:
                        break;
                }

                return resu;
            }
        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Categoría");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Monto");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaConcepto.size();

        //Para cada concepto de la BD
        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            //Agrega los datos a la tabla de concepto
            columna[ind++] = listaConcepto.get(i).getCategoria().getNombre();
            columna[ind++] = listaConcepto.get(i).getNombre();

            if (accion.equals("Consultar")) {

                //Para cada concepto en el modelo
                for (int j = 0; j < modelo.getConceptos().size(); j++) {
                    ind = 2;

                    //Si el concepto actual de la tabla coincide con el del modelo
                    if (listaConcepto.get(i).getId().equals(modelo.getConceptos().get(j).getId())) {
                        //Se selecciona el checkbox y se detiene el ciclo
                        columna[ind++] = Boolean.TRUE;
                        columna[ind++] = modelo.getMontoConceptos().get(j);
                        break;

                    } else {
                        //No se selecciona el checkbox
                        columna[ind++] = Boolean.FALSE;
                        columna[ind++] = null;
                    }
                }
            }

            //Se añade la fila a la tabla
            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
//        String[] moneda = {"Bolívares","Dólares","Petros"};
//        JComboBox cbx = new JComboBox(moneda);
//        TableColumn tc = tablaD.getColumnModel().getColumn(4);
//        TableCellEditor tce = new DefaultCellEditor(cbx);
//        tc.setCellEditor(tce);
//        cbx.setSelectedIndex(0);
    }

    public void llenarTablaProveedores(JTable tablaD) {
        int ind;

        listaProveedores = modProveedor.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula/RIF");
        modeloT.addColumn("Nombre/Razón Social");
        modeloT.addColumn("Teléfono");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaProveedores.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listaProveedores.get(i).getCedulaRif();
            columna[ind++] = listaProveedores.get(i).getNombre();
            columna[ind++] = listaProveedores.get(i).getTelefono();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {

            if (funcionbtn.getNombre().equals("Gasto")) {
                permiso = funcionbtn;
            }
        }
    }

    private Boolean validar() {
        Boolean resultado = true;
        String msj = "";

        if (vista.txtProveedor.getText().isEmpty()) {
            msj += "Debe seleccionar un Proveedor\n";
            resultado = false;
        }

        if (vista.txtNombre.getText().isEmpty()) {
            msj += "El campo Nombre no debe estar vacío\n";
            resultado = false;
        }

        if (vista.jcombotipo.getSelectedItem().equals("Extraordinario")) {

            if (vista.txtNmeses.getText().isEmpty()) {
                msj += "El campo Número de meses no puede estar vacío\n";
                resultado = false;

            } else if (Integer.parseInt(vista.txtNmeses.getText()) > 20) {
                msj += "El máximo de meses para dividir el pago es 20\n";
                resultado = false;
            }

            if (!vista.si.isSelected() && !vista.no.isSelected()) {
                msj += "Debe indicar si el gasto fue escogido en una Asamblea\n";
                resultado = false;

            } else if (vista.si.isSelected()) {

                if (vista.jAsamblea.getSelectedIndex() == 0) {
                    msj += "Debe seleccionar una Asamblea\n";
                    resultado = false;
                }
            }
        }
        if (vista.cbxMoneda.getSelectedIndex() == 0) {
            msj += "Debe seleccionar una Moneda\n";
            resultado = false;
        }

        //Si se esta edtando la tabla
        if (vista.jTable.isEditing()) {
            //Detener la edición
            vista.jTable.getCellEditor().stopCellEditing();
        }

        int numConceptoSeleccionado = 0;
        boolean esValido;
        boolean esCero;

        esValido = true;
        esCero = true;

        //Se recorre cada concepto de la tabla
        for (int i = 0; i < vista.jTable.getRowCount(); i++) {

            //Si el concepto actual fue seleccionado
            if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                numConceptoSeleccionado++;

                //Si el monto está vacío
                if (valueOf(vista.jTable.getValueAt(i, 3)).equals("")) {
                    msj += "Debe ingresar el monto de cada concepto seleccionado\n";
                    resultado = false;
                    break;

                } else {

                    //Si el monto es válido como tipo Double
                    if (!isValidDouble(vista.jTable.getValueAt(i, 3).toString())) {

                        if (!esValido) {
                            break;
                        }

                        msj += "Ingrese solo números en el monto\n";
                        resultado = false;
                        esValido = false;

                    } else {

                        //Si el monto ingresado es menor o igual que 0
                        if (Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3))) <= 0) {

                            if (!esCero) {
                                break;
                            }

                            msj += "El monto no puede ser menor o igual que 0\n";
                            resultado = false;
                            esCero = false;
                        }
                    }
                }
            }
        }

        //Si no se seleccionó ningún concepto
        if (numConceptoSeleccionado == 0) {
            msj += "Debe seleccionar al menos un concepto en la tabla\n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == catalogo.tabla) {
            int fila;

            fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada

            if (permiso.getModificar()) {
                vista.btnModificar.setEnabled(true);
            }

            if (permiso.getEliminar()) {
                vista.btnEliminar.setEnabled(true);
            }

            //Se obtiene el modelo seleccionado en la tabla del catálogo
            modelo = lista.get(fila);

            //Se muestran los datos básicos en el formulario
            vista.txtNombre.setText(modelo.getNombre());
            vista.jcombotipo.setSelectedItem(modelo.getTipo());
            modProveedor = modelo.getProveedor();
            vista.txtProveedor.setText(modProveedor.getNombre());
            vista.jCalcular.setSelectedItem(modelo.getCalcular());
            vista.cbxMoneda.setSelectedItem(modelo.getMoneda());
            vista.jMonthChooser1.setMonth(modelo.getMes() - 1);
            vista.jYearChooser1.setYear(modelo.getAnio());
            vista.txaObservaciones.setText(modelo.getObservacion());
            vista.txtNmeses.setText(String.valueOf(modelo.getNumMeses()));

            llenarTablaConcepto(vista.jTable, "Consultar");
            addCheckBox(2, vista.jTable);

            //Si el modelo no tiene asamblea
            if (modelo.getAsamblea() == null) {
                //Se selecciona que no fue elegido en asamblea
                vista.no.setSelected(true);

            } else {
                //Se selecciona que fue elegido en asamblea
                vista.si.setSelected(true);
                //Y se escoge la asamblea de la lista
                vista.jAsamblea.setSelectedItem(modelo.getAsamblea().getNombre() + " " + modelo.getAsamblea().getFecha());
            }

            //Si el gasto está pendiente
            if (modelo.getEstado().equals("Pendiente")) {
                vista.btnGuardar.setEnabled(false);
                vista.btnModificar.setEnabled(true);
                vista.btnEliminar.setEnabled(true);

            } else {
                vista.btnGuardar.setEnabled(false);
                vista.btnModificar.setEnabled(false);
                vista.btnEliminar.setEnabled(false);

                vista.btnBuscarproveedor.setVisible(false);

                JOptionPane.showMessageDialog(vista, "Las cuotas especiales en proceso no puenden ser modificadas ni eliminadas");
            }

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == catProveedores.jTable1) {
            int fila = this.catProveedores.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            modProveedor = listaProveedores.get(fila);
            vista.txtProveedor.setText(modProveedor.getNombre());
            catProveedores.dispose();
        }

        if (e.getSource() == vista.jTable) {
            int fila;
            fila = this.vista.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            String seleccionado = String.valueOf(this.vista.jTable.getValueAt(fila, 2)); // por ultimo, obtengo el valor de la celda

            if (seleccionado.equals("true")) {
                vista.jTable.editCellAt(fila, 3);
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

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.txaObservaciones) {
            Validacion.limite(e, vista.txaObservaciones.getText(), 500);
        }

        if (e.getSource() == vista.txtNmeses) {
            Validacion.Espacio(e);
            Validacion.soloNumeros(e);
            Validacion.limite(e, vista.txtNmeses.getText(), 2);
        }

        if (e.getSource() == vista.jTable) {
            int fila = this.vista.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            String pepe = String.valueOf(this.vista.jTable.getValueAt(fila, 3));
            System.out.println(pepe);
            Validacion.soloUnPunto(e, pepe);
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
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Component[] components = vista.jPanel2.getComponents();

        JComponent[] com = {
            vista.txtNmeses, vista.txaObservaciones
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

    private void crearCbxAsamblea() {
        listaAsambleas = modAsamblea.listar();
        vista.jAsamblea.addItem("Seleccione...");

        if (listaAsambleas != null) {

            for (Asambleas item : listaAsambleas) {
                vista.jAsamblea.addItem(item.getNombre() + " " + item.getFecha());
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxMoneda.setFocusable(false);
        vista.jcombotipo.setFocusable(false);
        vista.jCalcular.setFocusable(false);
        vista.jAsamblea.setFocusable(false);
        if (e.getSource() == vista.jcombotipo) {

            if (vista.jcombotipo.getSelectedItem().equals("Ordinario")) {
                vista.panelTipo.setVisible(false);
                vista.no.setSelected(true);

            } else if (vista.jcombotipo.getSelectedItem().equals("Extraordinario")) {
                vista.panelTipo.setVisible(true);
            }
        }

        if (e.getSource() == vista.si) {

            if (vista.si.isSelected()) {
                vista.panelAsamblea.setVisible(true);
            }
        }

        if (e.getSource() == vista.no) {

            if (vista.no.isSelected()) {
                vista.panelAsamblea.setVisible(false);
                vista.jAsamblea.setSelectedIndex(0);
            }
        }

    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    private static boolean isValidDouble(String s) {
        boolean isValid = true;

        try {
            Double.parseDouble(s);

        } catch (NumberFormatException nfe) {
            System.err.println(nfe);
            isValid = false;
        }

        return isValid;
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }
}
