package controlador;

import java.awt.Component;
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
import modelo.Gasto;
import modelo.Funcion;
import modelo.ModeloConceptoGastos;
import modelo.Proveedores;
import sgc.SGC;
import vista.Catalogo;
import vista.buscarProveedor;
import vista.cuotasEspeciales;

public class controladorCuotasEspeciales implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {

    private cuotasEspeciales vista;
    private Catalogo catalogo;
    private Proveedores modpro;
    private ModeloConceptoGastos modcon;
    private Asambleas modasa;
    private Gasto modcuo;
    private CerrarMes modc;
    private buscarProveedor buscpro;

    Funcion permiso;

    ArrayList<Proveedores> listaProveedores;
    DefaultTableModel dm;
    ArrayList<Gasto> listacuotasEspeciales;
    ArrayList<ModeloConceptoGastos> listaConGas;
    ArrayList<Asambleas> listaasambleas;
    ArrayList<ModeloConceptoGastos> listaConcepto;
    ArrayList<Gasto> listaConceptomod;

    public controladorCuotasEspeciales() {
        this.vista = new cuotasEspeciales();
        this.catalogo = new Catalogo();
        this.modcon = new ModeloConceptoGastos();
        this.modpro = new Proveedores();
        this.modasa = new Asambleas();

        this.modcuo = new Gasto();
        this.modc = new CerrarMes();
        this.buscpro = new buscarProveedor();

        catalogo.lblTitulo.setText("Cuotas Especiales");

        llenartablaCuotasEspeciales(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.buscpro.jTable1.addMouseListener(this);
        this.vista.btnBuscarproveedor.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.si.addMouseListener(this);
        this.vista.no.addMouseListener(this);
        this.vista.jTable.addMouseListener(this);
        this.vista.jTable.addKeyListener(this);
        this.vista.jcombotipo.addItemListener(this);
        vista.txtNmeses.addKeyListener(this);

        vista.txaObservaciones.addKeyListener(this);
        listaConGas = modcon.listarConcepto();
        listaasambleas = modasa.listar();
        this.catalogo.setVisible(true);
        
        CtrlVentana.cambiarVista(catalogo);
    }

    public void llenartablaCuotasEspeciales(JTable tablaD) {

        listacuotasEspeciales = modcuo.listarCuotasEspeciales("");
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Cuota Especial</html>");
        modeloT.addColumn("Proveedor");

        modeloT.addColumn("<html>Calcular <br> Por</html>");
        modeloT.addColumn("<html>Comienzo de <br> Cobro</html>");
        modeloT.addColumn("<html>Monto <br> Inicial</html>");
        modeloT.addColumn("Saldo");
        modeloT.addColumn("Asamblea");
        modeloT.addColumn("<html>Meses <br> Iniciales</html>");
        modeloT.addColumn("<html>Meses <br> Restantes</html>");
        modeloT.addColumn("Observación");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Estado Pago");

        Object[] columna = new Object[13];

        int numRegistro = listacuotasEspeciales.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listacuotasEspeciales.get(i).getId();
            columna[ind++] = listacuotasEspeciales.get(i).proveedor.getCedula();

            String fecha = String.valueOf(listacuotasEspeciales.get(i).getMes()) + "-" + listacuotasEspeciales.get(i).getAño();
            columna[ind++] = listacuotasEspeciales.get(i).getCalcular();
            columna[ind++] = fecha;
            columna[ind++] = Validacion.formato1.format(listacuotasEspeciales.get(i).getMonto());
            columna[ind++] = Validacion.formato1.format(listacuotasEspeciales.get(i).getSaldo());
            columna[ind++] = listacuotasEspeciales.get(i).asamblea.getNombre();
            columna[ind++] = listacuotasEspeciales.get(i).getN_meses();
            columna[ind++] = listacuotasEspeciales.get(i).getN_meses_restantes();
            columna[ind++] = listacuotasEspeciales.get(i).getObservacion();
            columna[ind++] = listacuotasEspeciales.get(i).getEstado();
            columna[ind++] = listacuotasEspeciales.get(i).getPagado();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = tablaD.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(90);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(140);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    public void Llenartablaconcepto(JTable tablaD) {

        listaConcepto = modcon.listarConcepto();
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
                    resu = true;
                }
                if (column == 3) {
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Categoría");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Monto");

        Object[] columna = new Object[4];

        int numRegistro = listaConcepto.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaConcepto.get(i).cate.getNombre();
            columna[1] = listaConcepto.get(i).getNombre_Concepto();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void Llenartablaconceptomodificar(JTable tablaD, int x) {

        if (x == 0) {
            listaConceptomod = modcuo.listarconceptosmodificar(0);
        }
        if (x == 1) {
            listaConceptomod = modcuo.listarconceptosmodificar(1);
        }

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
                    resu = true;
                }
                if (column == 3) {
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Categoría");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Monto");

        Object[] columna = new Object[4];

        int numRegistro = listaConceptomod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaConceptomod.get(i).cate.getNombre();
            columna[1] = listaConceptomod.get(i).concep.getNombre_Concepto();
            if (listaConceptomod.get(i).concep.getId() == 0) {
                columna[2] = Boolean.FALSE;
            } else {
                columna[2] = Boolean.TRUE;
            }
            if (listaConceptomod.get(i).getMonto() == 0) {
                columna[3] = "";
            } else {
                columna[3] = listaConceptomod.get(i).getMonto();
            }

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void Llenartabla(JTable tablaD) {

        listaProveedores = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula/Rif");
        modeloT.addColumn("Nombre/Razón Social");
        modeloT.addColumn("Teléfono");

        Object[] columna = new Object[3];

        int numRegistro = listaProveedores.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaProveedores.get(i).getCedula();
            columna[1] = listaProveedores.get(i).getNombre();
            columna[2] = listaProveedores.get(i).getTelefono();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);
            modcuo.setEstado("Pendiente");
            this.vista.jCalcular.setEnabled(true);
            Llenartablaconcepto(vista.jTable);
            addCheckBox(2, vista.jTable);
            vista.jAsamblea.removeAllItems();

            listaasambleas = modasa.listar();
            listaConGas = modcon.listarConcepto();

            crearCbxAsamblea(listaasambleas);
            this.vista.txtid.setVisible(false);
            vista.txaObservaciones.setText("");

            vista.txtNmeses.setText("");
            vista.txtid.setText("");
            vista.txtProveedor.setText("");
            vista.btnBuscarproveedor.setVisible(true);
            vista.jLabel2.setText("");

        }
        if (e.getSource() == vista.btnBuscarproveedor) {
            this.buscpro.setVisible(true);
            Llenartabla(buscpro.jTable1);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                if (vista.jTable.isEditing()) {//si se esta edtando la tabla
                    vista.jTable.getCellEditor().stopCellEditing();//detenga la edicion
                }
                String var10 = vista.jCalcular.getSelectedItem().toString();
                if (var10.equals("Total de Inmuebles")) {
                    var10 = "Total de Inmuebles";
                }
                modasa.setNombre(vista.jAsamblea.getSelectedItem().toString());
                if (modasa.getNombre().equals("Seleccione la Asamblea") && vista.si.isSelected()) {
                    JOptionPane.showMessageDialog(null, "seleccione una asamblea");
                } else {
                    modcuo.setTipo(vista.jcombotipo.getSelectedItem().toString());
                    if (modcuo.getTipo().equals("Ordinario")) {
                        modcuo.setN_meses(1);
                        modcuo.setN_meses_restantes(1);
                    } else {
                        if (vista.txtNmeses.getText().equals("")) {
                            modcuo.setN_meses(1);
                            modcuo.setN_meses_restantes(1);
                        } else {
                            modcuo.setN_meses(Integer.parseInt(vista.txtNmeses.getText()));
                            modcuo.setN_meses_restantes(Integer.parseInt(vista.txtNmeses.getText()));
                        }
                    }
                    modcuo.setCalcular(var10);
                    modcuo.proveedor.setCedula(vista.txtProveedor.getText());
                    modcuo.setMes(vista.jMonthChooser1.getMonth() + 1);
                    modcuo.setAño(vista.jYearChooser1.getYear());

                    if (modcuo.getN_meses() > 20) {
                        JOptionPane.showMessageDialog(null, "El maximo de meses para dividir el pago es 20");
                    } else {

                        modcuo.setObservacion(vista.txaObservaciones.getText());
                        modcuo.setEstado("Pendiente");

                        int var1 = 0;
                        int bre = 0;
                        var1 = modcuo.getMes();

                        for (int i = 0; i < modcuo.getN_meses(); i++) {

                            if (var1 + i > 12) {
                                var1 = var1 - 12;
                                if (var1 + 1 > 24) {
                                    var1 = var1 - 12;
                                }
                            }

                            modc.setMes_cierre(var1 + i);

                            modc.setAño_cierre(modcuo.getAño());
                            if (modc.buscarfechas(modc)) {
                                bre = 1;
                            } else {
                            }
                        }

                        if (bre == 1) {
                            JOptionPane.showMessageDialog(null, "no puede registrar cuotas especiales que incluyan un periodo ya cerrado");

                        } else {

                            if (vista.jAsamblea.getSelectedItem().toString() == "Seleccione la Asamblea") {
                                modcuo.setId(0);
                            } else {
                                int ind1 = vista.jAsamblea.getSelectedIndex() - 1;
                                listaasambleas = modasa.listar();
                                modcuo.setId(listaasambleas.get(ind1).getId());
                            }
                            int j = 0;
                            int x = 0;
                            int l = 0;
                            int xd = 0;
                            double monto = 0;
                            for (int i = 0; i < vista.jTable.getRowCount(); i++) {
                                if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                                    j = j + 1;

                                    if (!valueOf(vista.jTable.getValueAt(i, 3)).equals("")) {
                                        x = x + 1;
                                        String numero = valueOf(vista.jTable.getValueAt(i, 3));
                                        if (isValidDouble(numero)) {
                                            l = l + 1;
                                            monto = monto + Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3)));
                                            if (Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3))) == 0) {
                                                xd = xd + 1;
                                            }
                                        }
                                    }

                                }
                            }
                            if (j == 0) {
                                JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                            } else {
                                if (j != x) {
                                    JOptionPane.showMessageDialog(null, "debe ingresar el monto al concepto seleccionado");
                                } else {
                                    if (l != x) {
                                        JOptionPane.showMessageDialog(null, "por favor ingrese solo numeros en la tabla");
                                    } else {
                                        if (xd >= 1) {
                                            JOptionPane.showMessageDialog(null, "0 no es un monto valido");
                                        } else {
                                            modcuo.setMonto(monto);
                                            modcuo.setSaldo(monto);
                                            if (modcuo.registrar_cuota_especial(modcuo)) {
                                                modcuo.buscId(modcuo);
                                                listaConcepto = modcon.listarConcepto();

                                                for (int i = 0; i < vista.jTable.getRowCount(); i++) {

                                                    if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                                                        modcuo.concep.setId(listaConcepto.get(i).getId());
                                                        modcuo.setMonto(Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3))));
                                                        modcuo.registrar_puente(modcuo);
                                                    }

                                                    llenartablaCuotasEspeciales(catalogo.tabla);

                                                }
                                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                            } else {

                                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                if (vista.jTable.isEditing()) {//si se esta edtando la tabla
                    vista.jTable.getCellEditor().stopCellEditing();//detenga la edicion
                }
                modasa.setNombre(vista.jAsamblea.getSelectedItem().toString());
                if (modasa.getNombre().equals("Seleccione la Asamblea") && vista.si.isSelected()) {
                    JOptionPane.showMessageDialog(null, "seleccione una asamblea");
                } else {

                    modcuo.setCalcular(vista.jCalcular.getSelectedItem().toString());
                    modcuo.setMes(vista.jMonthChooser1.getMonth() + 1);
                    modcuo.setAño(vista.jYearChooser1.getYear());

                    modcuo.setTipo(vista.jcombotipo.getSelectedItem().toString());
                    if (modcuo.getTipo().equals("Ordinario")) {
                        modcuo.setN_meses(1);
                        modcuo.setN_meses_restantes(1);
                    } else {
                        if (vista.txtNmeses.getText().equals("")) {
                            modcuo.setN_meses(1);
                            modcuo.setN_meses_restantes(1);
                        } else {
                            modcuo.setN_meses(Integer.parseInt(vista.txtNmeses.getText()));
                            modcuo.setN_meses_restantes(Integer.parseInt(vista.txtNmeses.getText()));
                        }
                    }
                    modcuo.proveedor.setCedula(vista.txtProveedor.getText());
                    if (modcuo.getN_meses() > 20) {
                        JOptionPane.showMessageDialog(null, "El maximo de meses para dividir el pago es 20");
                    } else {

                        modcuo.setObservacion(vista.txaObservaciones.getText());
                        modcuo.setEstado("Pendiente");
                        modcuo.setId(Integer.parseInt(vista.txtid.getText()));
                        int var1 = 0;
                        int bre = 0;
                        var1 = modcuo.getMes();

                        for (int i = 0; i < modcuo.getN_meses(); i++) {

                            if (var1 + i > 12) {
                                var1 = var1 - 12;
                                if (var1 + 1 > 24) {
                                    var1 = var1 - 12;
                                }
                            }

                            modc.setMes_cierre(var1 + i);

                            modc.setAño_cierre(modcuo.getAño());
                            if (modc.buscarfechas(modc)) {
                                bre = 1;
                            } else {
                            }
                        }

                        if (bre == 1) {
                            JOptionPane.showMessageDialog(null, "no puede registrar cuotas especiales que incluyan un periodo ya cerrado");

                        } else {
                            if (vista.jAsamblea.getSelectedItem().toString() == "Seleccione la Asamblea") {
                                modcuo.asamblea.setId(0);
                            } else {
                                listaasambleas = modasa.listar();
                                int ind1 = vista.jAsamblea.getSelectedIndex() - 1;
                                JOptionPane.showMessageDialog(null, ind1);
                                JOptionPane.showMessageDialog(null, listaasambleas.get(ind1).getId());
                                modcuo.asamblea.setId(listaasambleas.get(ind1).getId());
                            }
                            int j = 0;
                            int x = 0;
                            int l = 0;
                            int xd = 0;
                            double monto = 0;
                            for (int i = 0; i < vista.jTable.getRowCount(); i++) {
                                if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                                    j = j + 1;

                                    if (!valueOf(vista.jTable.getValueAt(i, 3)).equals("")) {
                                        x = x + 1;
                                        String numero = valueOf(vista.jTable.getValueAt(i, 3));
                                        if (isValidDouble(numero)) {
                                            l = l + 1;
                                            monto = monto + Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3)));
                                            if (Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3))) == 0) {
                                                xd = xd + 1;
                                            }
                                        }
                                    }

                                }
                            }
                            if (j == 0) {
                                JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                            } else {
                                if (j != x) {
                                    JOptionPane.showMessageDialog(null, "debe ingresar el monto al concepto seleccionado");
                                } else {
                                    if (l != x) {
                                        JOptionPane.showMessageDialog(null, "por favor ingrese solo numeros en la tabla");
                                    } else {
                                        if (xd >= 1) {
                                            JOptionPane.showMessageDialog(null, "0 no es un monto valido");
                                        } else {
                                            modcuo.setMonto(monto);
                                            modcuo.setSaldo(monto);

                                            if (modcuo.modificar_cuota_especial(modcuo)) {
                                                modcuo.eliminar_puente(modcuo);
                                                listaConcepto = modcon.listarConcepto();

                                                for (int i = 0; i < vista.jTable.getRowCount(); i++) {

                                                    if (valueOf(vista.jTable.getValueAt(i, 2)) == "true") {
                                                        modcuo.concep.setId(listaConcepto.get(i).getId());
                                                        modcuo.setMonto(Double.parseDouble(valueOf(vista.jTable.getValueAt(i, 3))));
                                                        modcuo.registrar_puente(modcuo);
                                                    }

                                                }
                                                JOptionPane.showMessageDialog(null, "registro modificado");
                                                llenartablaCuotasEspeciales(catalogo.tabla);
                                                this.vista.dispose();
                                            } else {

                                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                                            }

                                        }
                                    }

                                }

                            }

                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {

            modcuo.setId(Integer.parseInt(vista.txtid.getText()));

            if (modcuo.eliminar_cuotas_especiales(modcuo)) {
                modcuo.eliminar_puente(modcuo);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                llenartablaCuotasEspeciales(catalogo.tabla);
                this.vista.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }

    public void limpiar() {

        vista.txtNmeses.setText(null);

        vista.txaObservaciones.setText(null);
        vista.jAsamblea.setSelectedItem("Seleccione la Asamblea");

        vista.jMonthChooser1.setMonth(0);
        vista.txtProveedor.setText(null);
        vista.jYearChooser1.setYear(0);

    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.si) {
            if (vista.si.isSelected()) {
                if (modcuo.getEstado().equals("Pendiente")) {
                    vista.jAsamblea.setVisible(true);

                    listaasambleas = modasa.listar();
                    vista.jAsamblea.removeAllItems();
                    crearCbxAsamblea(listaasambleas);
                    vista.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                }
            }
        }
        if (e.getSource() == vista.no) {
            if (vista.no.isSelected()) {
                if (modcuo.getEstado().equals("Pendiente")) {
                    vista.jAsamblea.setVisible(false);
                    vista.jAsamblea.setSelectedItem("Seleccione la Asamblea");

                }
            }
        }

        if (e.getSource() == catalogo.tabla) {
            vista.si.setEnabled(true);
            vista.no.setEnabled(true);

            int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

            if (permiso.getModificar()) {
                vista.btnModificar.setEnabled(true);
            }
            if (permiso.getEliminar()) {
                vista.btnEliminar.setEnabled(true);
            }

            modcuo.setId(Integer.parseInt(dato));

            modcuo.buscarCuotaEspecial(modcuo);
            this.vista.setVisible(true);
            vista.btnBuscarproveedor.setVisible(true);
            vista.txtid.setVisible(false);
            vista.txtid.setText(dato);
            vista.jAsamblea.removeAllItems();
            vista.jLabel2.setText(modcuo.proveedor.getNombre());

            vista.txtProveedor.setText(modcuo.proveedor.getCedula());

            vista.jCalcular.setSelectedItem(modcuo.getCalcular());

            vista.jMonthChooser1.setMonth(modcuo.getMes() - 1);
            vista.jYearChooser1.setYear(modcuo.getAño());
            vista.txaObservaciones.setText(modcuo.getObservacion());

            vista.txtNmeses.setText(String.valueOf(modcuo.getN_meses()));
            if (modcuo.getEstado().equals("Pendiente")) {
                vista.btnEliminar.setEnabled(true);
                vista.btnModificar.setEnabled(true);
                vista.btnGuardar.setEnabled(false);
                listaasambleas = modasa.listar();
                Llenartablaconceptomodificar(vista.jTable, 0);
                addCheckBox(2, vista.jTable);

                crearCbxAsamblea(listaasambleas);
                if (modcuo.asamblea.getNombre() == null) {
                    vista.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                    vista.jAsamblea.setVisible(false);
                    vista.no.setSelected(true);
                } else {
                    vista.jAsamblea.setSelectedItem(modcuo.asamblea.getNombre() + " " + modcuo.getFecha());
                    vista.si.setSelected(true);
                    vista.jAsamblea.setVisible(true);
                }

            } else {
                Llenartablaconceptomodificar(vista.jTable, 1);
                addCheckBox(2, vista.jTable);
                if (modcuo.asamblea.getNombre() == null) {
                    vista.jAsamblea.addItem("Seleccione la Asamblea");
                    vista.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                    vista.jAsamblea.setVisible(false);
                    vista.no.setSelected(true);
                    vista.no.setEnabled(false);
                    vista.si.setEnabled(false);
                } else {
                    vista.jAsamblea.addItem(modcuo.asamblea.getNombre() + " " + modcuo.getFecha());
                    vista.jAsamblea.setSelectedItem(modcuo.asamblea.getNombre() + " " + modcuo.getFecha());
                    vista.si.setSelected(true);
                    vista.si.setEnabled(false);
                    vista.no.setEnabled(false);
                }
                vista.btnEliminar.setEnabled(false);
                vista.btnModificar.setEnabled(false);
                vista.btnGuardar.setEnabled(false);
                vista.btnBuscarproveedor.setVisible(false);
                JOptionPane.showMessageDialog(null, "las cuotas especiales en proceso no puenden ser modificadas ni eliminadas");

            }

        }
        if (e.getSource() == buscpro.jTable1) {
            int fila1 = this.buscpro.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.buscpro.jTable1.getValueAt(fila1, 0)); // por ultimo, obtengo el valor de la celda
            String dato1 = String.valueOf(this.buscpro.jTable1.getValueAt(fila1, 1)); // por ultimo, obtengo el valor de la celda
            vista.txtProveedor.setText(dato);
            vista.jLabel2.setText(dato1);
            buscpro.dispose();
        }
        if (e.getSource() == vista.jTable) {
            int fila = this.vista.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.vista.jTable.getValueAt(fila, 2)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
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

    private void crearCbxAsamblea(ArrayList<Asambleas> datos) {
        vista.jAsamblea.addItem("Seleccione la Asamblea");

        if (datos != null) {
            for (Asambleas datosX : datos) {
                modasa = datosX;
                vista.jAsamblea.addItem(modasa.getNombre() + " " + modasa.getFecha());
            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == vista.jcombotipo) {
            int q = vista.jcombotipo.getSelectedIndex();
            if (q == 1) {
                vista.barrita.setVisible(false);
                vista.barritahorizontal.setVisible(false);
                vista.labelAsamblea.setVisible(false);
                vista.txtNmeses.setVisible(false);
                vista.labelmense.setVisible(false);
                vista.fue_elegido.setVisible(false);
                vista.no.setVisible(false);
                vista.si.setVisible(false);
                vista.jAsamblea.setVisible(false);
            }
            if (q == 0) {
                vista.barrita.setVisible(true);
                vista.barritahorizontal.setVisible(true);
                vista.labelAsamblea.setVisible(true);
                vista.txtNmeses.setVisible(true);
                vista.labelmense.setVisible(true);
                vista.fue_elegido.setVisible(true);
                vista.no.setVisible(true);
                vista.si.setVisible(true);
                vista.jAsamblea.setVisible(true);
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
            isValid = false;
        }

        return isValid;
    }
}
