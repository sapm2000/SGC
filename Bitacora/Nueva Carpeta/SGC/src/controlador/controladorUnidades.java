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
import modelo.CerrarMes;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.catalogoInactivoUnidades;
import vista.catalogoUnidades;
import vista.detalleRecibo;
import vista.unidades;
import vista.detallecuenta;
import vista.unidadesInactivas;

/**
 *
 * @author rma
 */
public class controladorUnidades implements ActionListener, MouseListener, KeyListener, WindowListener {

    private unidades uni;
    private catalogoUnidades catauni;
    private detallecuenta detacun;
    private detalleRecibo detare;
    private Unidades moduni;
    private PantallaPrincipal1 panta1;
    private catalogoInactivoUnidades cataiuni;
    private unidadesInactivas unii;

    private CerrarMes modc;
    ArrayList<Unidades> listapropietarios;
    ArrayList<Unidades> listaunidades;
    ArrayList<CerrarMes> listapagos;
    ArrayList<CerrarMes> listadetallegasto;
    ArrayList<CerrarMes> listadetallecuotas;
    ArrayList<CerrarMes> listadetallesancion;
    ArrayList<CerrarMes> listadetalleinteres;
    DefaultTableModel dm;

    public controladorUnidades(unidades uni, catalogoUnidades catauni, detallecuenta detacun, detalleRecibo detare, Unidades moduni, PantallaPrincipal1 panta1, CerrarMes modc, catalogoInactivoUnidades cataiuni, unidadesInactivas unii) {
        this.uni = uni;
        this.catauni = catauni;
        this.detacun = detacun;
        this.detare = detare;
        this.moduni = moduni;
        this.panta1 = panta1;
        this.cataiuni = cataiuni;
        this.unii = unii;
        this.unii.btnDesactivar.addActionListener(this);

        this.modc = modc;
        this.catauni.addWindowListener(this);
        this.catauni.btnActivar.addActionListener(this);
        this.catauni.jButton2.addActionListener(this);
        this.cataiuni.jTable1.addMouseListener(this);

        this.uni.txtArea.addKeyListener(this);

        this.uni.txtNumeroUnidad.addKeyListener(this);
        this.unii.jTable1.addMouseListener(this);

        this.catauni.jTable1.addMouseListener(this);

        this.uni.jTable1.addMouseListener(this);
        this.uni.btnGuardar.addActionListener(this);
        this.uni.btnLimpiar.addActionListener(this);
        this.uni.btnEliminar.addActionListener(this);
        this.uni.btnModificar.addActionListener(this);
        this.detacun.txtBuscar.addKeyListener(this);
        this.detacun.jTable1.addMouseListener(this);
    }

    public void llenartablapropietarios(JTable tablaD) {

        listapropietarios = moduni.buscarPropietario();
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
                    resu = true;
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

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Nº documento");

        Object[] columna = new Object[6];

        int numRegistro = listapropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listapropietarios.get(i).getCedula();
            columna[1] = listapropietarios.get(i).getNombre();
            columna[2] = listapropietarios.get(i).getTelefono();
            columna[3] = listapropietarios.get(i).getCorreo();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);

        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);

    }

    public void llenartablapropietariosmod(JTable tablaD) {

        listapropietarios = moduni.buscarPropietariomod();
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
                    resu = true;
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

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Nº documento");

        Object[] columna = new Object[10];

        int numRegistro = listapropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listapropietarios.get(i).getCedula();
            columna[1] = listapropietarios.get(i).getNombre();
            columna[2] = listapropietarios.get(i).getTelefono();
            columna[3] = listapropietarios.get(i).getCorreo();
            if (listapropietarios.get(i).getId() != 0) {
                columna[4] = Boolean.TRUE;
            } else {
                columna[4] = Boolean.FALSE;
            }
            columna[5] = listapropietarios.get(i).getDocumento();
            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);

        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);

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

            columna[0] = listapagos.get(i).getId_gasto();
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

            columna[0] = listadetallegasto.get(i).getTipo();
            columna[1] = listadetallegasto.get(i).getCedula();
            columna[2] = listadetallegasto.get(i).getNom_proveedor();
            columna[3] = listadetallegasto.get(i).getNom_concepto();
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

            columna[0] = listadetallecuotas.get(i).getCedula();
            columna[1] = listadetallecuotas.get(i).getNom_proveedor();
            columna[2] = listadetallecuotas.get(i).getNom_concepto();
            columna[3] = Validacion.formato1.format(listadetallecuotas.get(i).getMonto());
            columna[4] = listadetallecuotas.get(i).getMeses_res();

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

            columna[0] = listadetallesancion.get(i).getTipo();
            columna[1] = listadetallesancion.get(i).getEstado();
            if (listadetallesancion.get(i).getTipo().equals("Interes de mora")) {
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

    public void llenartablaunidades(JTable tablaD) {

        listaunidades = moduni.buscarUnidades();
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

        modeloT.addColumn("Area (mts2)");

        Object[] columna = new Object[3];

        int numRegistro = listaunidades.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunidades.get(i).getN_unidad();

            columna[1] = listaunidades.get(i).getDireccion();

            columna[2] = listaunidades.get(i).getArea();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void llenartablaunidadesinactivas(JTable tablaD) {

        listaunidades = moduni.buscarUnidadesinactivas();
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

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Número de <br> Unidad</html>");

        modeloT.addColumn("Dirección");

        modeloT.addColumn("Area (mts2)");

        Object[] columna = new Object[3];

        int numRegistro = listaunidades.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunidades.get(i).getN_unidad();

            columna[1] = listaunidades.get(i).getDireccion();

            columna[2] = listaunidades.get(i).getArea();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catauni.btnActivar) {
            this.cataiuni.setVisible(true);
            llenartablaunidadesinactivas(cataiuni.jTable1);

        }
        if (e.getSource() == catauni.jButton2) {
            this.uni.setVisible(true);
            this.uni.btnModificar.setEnabled(false);
            this.uni.btnGuardar.setEnabled(true);
            this.uni.btnEliminar.setEnabled(false);
            this.uni.txtNumeroUnidad.setEnabled(true);
            this.uni.txtId.setVisible(false);
            llenartablapropietarios(uni.jTable1);
            addCheckBox(4, uni.jTable1);
            limpiar();

            moduni.setId_condominio(panta1.rif.getText());

        }

        if (e.getSource() == uni.btnGuardar) {
            if (validar()) {
                if (uni.jTable1.isEditing()) {//si se esta edtando la tabla
                    uni.jTable1.getCellEditor().stopCellEditing();//detenga la edicion
                }
                java.util.Date fecha = new Date();
                java.sql.Date sqlDate = convert(fecha);

                moduni.setN_unidad(uni.txtNumeroUnidad.getText());
                moduni.setArea(Double.parseDouble(uni.txtArea.getText()));
                moduni.setDireccion(uni.txadireccion.getText());
                moduni.setId_condominio(panta1.rif.getText());
                int j = 0;
                int x = 0;
                for (int i = 0; i < uni.jTable1.getRowCount(); i++) {
                    if (valueOf(uni.jTable1.getValueAt(i, 4)) == "true") {
                        j = j + 1;

                        if (!valueOf(uni.jTable1.getValueAt(i, 5)).equals("")) {
                            x = x + 1;
                        }

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {
                    if (j != x) {
                        JOptionPane.showMessageDialog(null, "debe ingresar el numero de documento en la tabla");
                    } else {

                        if (moduni.buscarepe(moduni)) {
                            JOptionPane.showMessageDialog(null, "Este condiminio ya tiene este numero de unidad asignada");
                        } else {

                            if (moduni.registrarUnidades(moduni)) {
                                moduni.buscId(moduni);

                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                for (int i = 0; i < uni.jTable1.getRowCount(); i++) {
                                    if (valueOf(uni.jTable1.getValueAt(i, 4)) == "true") {
                                        moduni.setCedula(valueOf(uni.jTable1.getValueAt(i, 0)));
                                        moduni.setDocumento(valueOf(uni.jTable1.getValueAt(i, 5)));
                                        moduni.setFecha_desde(sqlDate);
                                        moduni.setEstatus(1);
                                        moduni.registrar_propietario_unidad(moduni);

                                    }
                                }
                                llenartablaunidades(catauni.jTable1);

                            } else {

                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                            }
                        }

                    }
                }
            }
        }

        if (e.getSource() == uni.btnModificar) {
            if (validar()) {
                if (uni.jTable1.isEditing()) {//si se esta edtando la tabla
                    uni.jTable1.getCellEditor().stopCellEditing();//detenga la edicion
                }
                java.util.Date fecha = new Date();
                java.sql.Date sqlDate = convert(fecha);
                moduni.setN_unidad(uni.txtNumeroUnidad.getText());
                moduni.setArea(Double.parseDouble(uni.txtArea.getText()));
                moduni.setDireccion(uni.txadireccion.getText());
                moduni.setId_condominio(panta1.rif.getText());
                moduni.setId(Integer.parseInt(uni.txtId.getText()));
                int j = 0;
                int x = 0;
                for (int i = 0; i < uni.jTable1.getRowCount(); i++) {
                    if (valueOf(uni.jTable1.getValueAt(i, 4)) == "true") {
                        j = j + 1;

                        if (!valueOf(uni.jTable1.getValueAt(i, 5)).equals("")) {
                            x = x + 1;
                        }

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {
                    if (j != x) {
                        JOptionPane.showMessageDialog(null, "debe ingresar el numero de documento en la tabla");
                    } else {

                        if (moduni.buscarepe(moduni) && !moduni.getN_unidad().equals(uni.txtNumeroUnidad.getText())) {
                            JOptionPane.showMessageDialog(null, "Este condiminio ya tiene este numero de unidad asignada");
                        } else {

                            if (moduni.modificarUnidades(moduni)) {
                                listapropietarios = moduni.buscarPropietariomod();
                                for (int i = 0; i < uni.jTable1.getRowCount(); i++) {

                                    if (listapropietarios.get(i).getId() != 0 && valueOf(uni.jTable1.getValueAt(i, 4)) == "true") {
                                        if (!listapropietarios.get(i).getDocumento().equals(valueOf(uni.jTable1.getValueAt(i, 5)))) {
                                            moduni.setDocumento(String.valueOf(uni.jTable1.getValueAt(i, 5)));
                                            moduni.setId_puente(listapropietarios.get(i).getId_puente());
                                            moduni.actualizardocumento(moduni);

                                        }
                                    }

                                    if (listapropietarios.get(i).getId() != 0 && valueOf(uni.jTable1.getValueAt(i, 4)).equals("false")) {
                                        moduni.setFecha_hasta(sqlDate);
                                        moduni.setEstatus(0);
                                        moduni.setId_puente(listapropietarios.get(i).getId_puente());
                                        moduni.retirarpropietario(moduni);
                                    }

                                    if (listapropietarios.get(i).getId() == 0 && valueOf(uni.jTable1.getValueAt(i, 4)).equals("true")) {
                                        moduni.setCedula(valueOf(uni.jTable1.getValueAt(i, 0)));
                                        moduni.setDocumento(valueOf(uni.jTable1.getValueAt(i, 5)));
                                        moduni.setFecha_desde(sqlDate);
                                        moduni.setEstatus(1);
                                        moduni.registrar_propietario_unidad(moduni);
                                    }

                                }

                                JOptionPane.showMessageDialog(null, "Registro Modificado");
                                llenartablaunidades(catauni.jTable1);

                                uni.dispose();

                            } else {

                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                            }
                        }
                    }
                }
            }
        }

        if (e.getSource() == uni.btnEliminar) {
            if (moduni.buscarsancion(moduni)) {
                JOptionPane.showMessageDialog(null, "No puede eliminar unidades que tengan sanciones pendientes");
            } else {
                if (uni.jTable1.isEditing()) {//si se esta edtando la tabla
                    uni.jTable1.getCellEditor().stopCellEditing();//detenga la edicion
                }
                java.util.Date fecha = new Date();
                java.sql.Date sqlDate = convert(fecha);
                moduni.setN_unidad(uni.txtNumeroUnidad.getText());
                moduni.setArea(Double.parseDouble(uni.txtArea.getText()));
                moduni.setDireccion(uni.txadireccion.getText());
                moduni.setId_condominio(panta1.rif.getText());
                moduni.setId(Integer.parseInt(uni.txtId.getText()));
                int j = 0;
                int x = 0;
                for (int i = 0; i < uni.jTable1.getRowCount(); i++) {
                    if (valueOf(uni.jTable1.getValueAt(i, 4)) == "true") {
                        j = j + 1;

                        if (!valueOf(uni.jTable1.getValueAt(i, 5)).equals("")) {
                            x = x + 1;
                        }

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {
                    if (j != x) {
                        JOptionPane.showMessageDialog(null, "debe ingresar el numero de documento en la tabla");
                    } else {

                        if (moduni.buscarepe(moduni) && !moduni.getN_unidad().equals(uni.txtNumeroUnidad.getText())) {
                            JOptionPane.showMessageDialog(null, "Este condiminio ya tiene este numero de unidad asignada");
                        } else {

                            if (moduni.modificarUnidades(moduni)) {
                                listapropietarios = moduni.buscarPropietariomod();
                                for (int i = 0; i < uni.jTable1.getRowCount(); i++) {

                                    if (listapropietarios.get(i).getId() != 0 && valueOf(uni.jTable1.getValueAt(i, 4)) == "true") {
                                        if (!listapropietarios.get(i).getDocumento().equals(valueOf(uni.jTable1.getValueAt(i, 5)))) {
                                            moduni.setDocumento(String.valueOf(uni.jTable1.getValueAt(i, 5)));
                                            moduni.setId_puente(listapropietarios.get(i).getId_puente());
                                            moduni.actualizardocumento(moduni);

                                        }
                                    }

                                    if (listapropietarios.get(i).getId() != 0 && valueOf(uni.jTable1.getValueAt(i, 4)).equals("false")) {
                                        moduni.setFecha_hasta(sqlDate);
                                        moduni.setEstatus(0);
                                        moduni.setId_puente(listapropietarios.get(i).getId_puente());
                                        moduni.retirarpropietario(moduni);
                                    }

                                    if (listapropietarios.get(i).getId() == 0 && valueOf(uni.jTable1.getValueAt(i, 4)).equals("true")) {
                                        moduni.setCedula(valueOf(uni.jTable1.getValueAt(i, 0)));
                                        moduni.setDocumento(valueOf(uni.jTable1.getValueAt(i, 5)));
                                        moduni.setFecha_desde(sqlDate);
                                        moduni.setEstatus(1);
                                        moduni.registrar_propietario_unidad(moduni);
                                    }

                                }

                                moduni.setId(Integer.parseInt(uni.txtId.getText()));
                                moduni.eliminarUnidad(moduni);
                                moduni.eliminarPuenteUnidad(moduni);

                                llenartablaunidades(catauni.jTable1);
                                uni.dispose();

                                JOptionPane.showMessageDialog(null, "Registro Modificado");
                                llenartablaunidades(catauni.jTable1);

                                uni.dispose();

                            } else {

                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                            }

                        }
                    }
                }

            }

        }
        if (e.getSource() == unii.btnDesactivar) {
            if (unii.jTable1.isEditing()) {//si se esta edtando la tabla
                unii.jTable1.getCellEditor().stopCellEditing();//detenga la edicion
            }
            java.util.Date fecha = new Date();
            java.sql.Date sqlDate = convert(fecha);
            moduni.setId(Integer.parseInt(unii.txtId.getText()));
            int j = 0;
            int x = 0;
            for (int i = 0; i < unii.jTable1.getRowCount(); i++) {
                if (valueOf(unii.jTable1.getValueAt(i, 4)) == "true") {
                    j = j + 1;

                    if (!valueOf(unii.jTable1.getValueAt(i, 5)).equals("")) {
                        x = x + 1;

                    }

                }
            }
            if (j == 0) {
                JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
            } else {
                if (j != x) {
                    JOptionPane.showMessageDialog(null, "debe ingresar el numero de documento en la tabla");
                } else {
                    listapropietarios = moduni.buscarPropietariomod();
                    if (moduni.activarUnidad(moduni)) {
                        JOptionPane.showMessageDialog(null, "Unidad restaurada");
                        for (int w = 0; w < unii.jTable1.getRowCount(); w++) {

                            if (listapropietarios.get(w).getId() != 0 && valueOf(unii.jTable1.getValueAt(w, 4)) == "true") {
                                if (!listapropietarios.get(w).getDocumento().equals(valueOf(unii.jTable1.getValueAt(w, 5)))) {
                                    moduni.setDocumento(String.valueOf(unii.jTable1.getValueAt(w, 5)));
                                    moduni.setId_puente(listapropietarios.get(w).getId_puente());
                                    moduni.actualizardocumento(moduni);
                                }
                            }

                            if (listapropietarios.get(w).getId() != 0 && valueOf(unii.jTable1.getValueAt(w, 4)).equals("false")) {
                                moduni.setFecha_hasta(sqlDate);
                                moduni.setEstatus(0);
                                moduni.setId_puente(listapropietarios.get(w).getId_puente());
                                moduni.retirarpropietario(moduni);
                            }

                            if (listapropietarios.get(w).getId() == 0 && valueOf(unii.jTable1.getValueAt(w, 4)).equals("true")) {
                                moduni.setCedula(valueOf(unii.jTable1.getValueAt(w, 0)));
                                moduni.setDocumento(valueOf(unii.jTable1.getValueAt(w, 5)));
                                moduni.setFecha_desde(sqlDate);
                                moduni.setEstatus(1);
                                moduni.registrar_propietario_unidad(moduni);
                            }

                        }
                        llenartablaunidadesinactivas(cataiuni.jTable1);
                        llenartablaunidades(catauni.jTable1);
                        unii.dispose();
                    }

                }
            }

        }

        if (e.getSource() == uni.btnLimpiar) {

            limpiar();

        }
    }

    public void limpiar() {

        uni.txtNumeroUnidad.setText(null);
        uni.txtArea.setText(null);

        uni.txadireccion.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == uni.jTable1) {
            int fila = this.uni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.uni.jTable1.getValueAt(fila, 4)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                uni.jTable1.editCellAt(fila, 5);
            }
        }

        if (e.getSource() == unii.jTable1) {
            int fila = this.unii.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.unii.jTable1.getValueAt(fila, 4)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                unii.jTable1.editCellAt(fila, 5);
            }
        }

        if (e.getSource() == catauni.jTable1) {
            String[] options = {"Ver detalles de pago", "Modificar datos"};
            int result = JOptionPane.showOptionDialog(null, "Seleccione si desea ver detalles de pagp o modificar datos", "MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                int fila = this.catauni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
               listaunidades = moduni.buscarUnidades();
                String dato = String.valueOf(this.catauni.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
               
                String dato2 = String.valueOf(this.catauni.jTable1.getValueAt(fila, 2)); // por ultimo, obtengo el valor de la celda
                detacun.setVisible(true);
                modc.setId_unidad(listaunidades.get(fila).getId());
                modc.setId_condominio(panta1.rif.getText());
                detacun.txtArea.setText(dato2);
                llenartablapagos(detacun.jTable1);
                detacun.txtUnidad.setText(dato);
                modc.buscarmesesdedeuda(modc);
                detacun.txtMesesdeuda.setText(String.valueOf(modc.getMeses_deuda()));
            }
            if (result == 1) {
                int fila = this.catauni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
                String dato = String.valueOf(this.catauni.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

                moduni.setN_unidad(String.valueOf(dato));
                uni.setVisible(true);
                moduni.setId_condominio(panta1.rif.getText());
                moduni.buscarUnidad(moduni);

                uni.txadireccion.setText(moduni.getDireccion());
                uni.txtArea.setText(String.valueOf(moduni.getArea()));
                uni.txtId.setVisible(false);
                uni.txtId.setText(String.valueOf(moduni.getId()));
                uni.txtNumeroUnidad.setText(moduni.getN_unidad());

                uni.txtNumeroUnidad.setEnabled(false);
                uni.btnEliminar.setEnabled(true);
                uni.btnModificar.setEnabled(true);
                uni.btnGuardar.setEnabled(false);
                llenartablapropietariosmod(uni.jTable1);
                addCheckBox(4, uni.jTable1);
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
            modc.setId_condominio(panta1.rif.getText());
            llenardetallegasto(detare.tablagastos);
            llenardetallecuotas(detare.tablacuotas);
            llenardetallesancion(detare.tablasancion);
            llenardetalleinteres(detare.tablainteres);
            detare.txtMes.setText(datos+"-"+dato2);
            detare.txtUnidad.setText(detacun.txtUnidad.getText());
            detare.txtPropietarios.setText(detacun.txtArea.getText());
            detare.txtId.setText(dato3);
            modc.bucartotal(modc);

            detare.txtAlicuota.setText(String.valueOf(Validacion.formatoalicuota.format(modc.getAlicuota())));
            detare.txtTotal.setText(String.valueOf(Validacion.formato1.format(modc.getMonto())));
        }

        if (e.getSource() == cataiuni.jTable1) {
            int fila = this.cataiuni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.cataiuni.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

            moduni.setN_unidad(String.valueOf(dato));
            unii.setVisible(true);
            unii.txtId.setVisible(false);
            moduni.setId_condominio(panta1.rif.getText());
            moduni.buscarUnidad(moduni);
            unii.txadireccion.setText(moduni.getDireccion());
            unii.txtArea.setText(String.valueOf(moduni.getArea()));

            unii.txtId.setText(String.valueOf(moduni.getId()));
            unii.txtNumeroUnidad.setText(moduni.getN_unidad());
            llenartablapropietariosmod(unii.jTable1);
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
        if (ke.getSource() == uni.txtNumeroUnidad) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, uni.txtNumeroUnidad.getText(), 10);
        }
        if (ke.getSource() == uni.txtArea) {

            Validacion.soloUnPunto(ke, uni.txtArea.getText());
            Validacion.Espacio(ke);
            Validacion.limite(ke, uni.txtArea.getText(), 6);
        }
        if (ke.getSource() == uni.txadireccion) {

            Validacion.limite(ke, uni.txadireccion.getText(), 200);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catauni.jTextField1) {

            filtro(catauni.jTextField1.getText(), catauni.jTable1);
        }

        if (e.getSource() == detacun.txtBuscar) {
            filtro(detacun.txtBuscar.getText(), detacun.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        moduni.setId_condominio(panta1.rif.getText());
        llenartablaunidades(catauni.jTable1);

        Component[] components = uni.jPanel2.getComponents();
        JComponent[] com = {
            uni.txtNumeroUnidad, uni.txtArea, uni.txadireccion
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

        if (uni.txtNumeroUnidad.getText().isEmpty()) {

            msj += "El campo numero de unidad no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txtArea.getText().isEmpty()) {

            msj += "El campo área no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txadireccion.getText().isEmpty()) {

            msj += "El campo dirección no puede estar vacio\n";
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

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
