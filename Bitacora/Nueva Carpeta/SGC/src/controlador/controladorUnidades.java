/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.buscarPropietario;
import vista.catalogoUnidades;
import vista.detalleRecibo;
import vista.unidades;
import vista.detallecuenta;

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
    private buscarPropietario buscp;
    private CerrarMes modc;
    ArrayList<Unidades> listapropietarios;
    ArrayList<Unidades> listaunidades;
    ArrayList<CerrarMes> listapagos;
    ArrayList<CerrarMes> listadetallegasto;
    ArrayList<CerrarMes> listadetallecuotas;
    ArrayList<CerrarMes> listadetallesancion;
    ArrayList<CerrarMes> listadetalleinteres;
    DefaultTableModel dm;

    public controladorUnidades(unidades uni, catalogoUnidades catauni, detallecuenta detacun, detalleRecibo detare, Unidades moduni, PantallaPrincipal1 panta1, buscarPropietario buscp, CerrarMes modc) {
        this.uni = uni;
        this.catauni = catauni;
        this.detacun = detacun;
        this.detare = detare;
        this.moduni = moduni;
        this.panta1 = panta1;
        this.buscp = buscp;
        this.modc = modc;
        this.catauni.addWindowListener(this);

        this.catauni.jButton2.addActionListener(this);
        this.buscp.txtBuscarProp.addKeyListener(this);
        this.uni.txtArea.addKeyListener(this);
        this.uni.txtCedula.addKeyListener(this);
        this.uni.txtNumeroUnidad.addKeyListener(this);
        this.buscp.tablaprop.addMouseListener(this);
        this.catauni.jTable1.addMouseListener(this);

        this.uni.btnBuscarpropietarios.addActionListener(this);
        this.uni.btnGuardar.addActionListener(this);
        this.uni.btnLimpiar.addActionListener(this);
        this.uni.btnEliminar.addActionListener(this);
        this.uni.btnModificar.addActionListener(this);
        this.catauni.jButton7.addActionListener(this);
        this.detacun.txtBuscar.addKeyListener(this);
        this.detacun.jTable1.addMouseListener(this);
    }

    public void llenartablapropietarios(JTable tablaD) {

        listapropietarios = moduni.buscarPropietario();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo");

        Object[] columna = new Object[4];

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
        modeloT.addColumn("<html>Propietario ó <br> inquilino</html>");
        modeloT.addColumn("CI/RIF");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Dirección");
        modeloT.addColumn("<html>Correo <br> Electrónico</html>");
        modeloT.addColumn("Area (mts2)");

        Object[] columna = new Object[7];

        int numRegistro = listaunidades.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunidades.get(i).getN_unidad();
            columna[1] = listaunidades.get(i).getNombre();
            columna[2] = listaunidades.get(i).getCedula();
            columna[3] = listaunidades.get(i).getTelefono();
            columna[4] = listaunidades.get(i).getDireccion();
            columna[5] = listaunidades.get(i).getCorreo();
            columna[6] = listaunidades.get(i).getArea();

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
        tablaD.getColumnModel().getColumn(6).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catauni.jButton2) {
            this.uni.setVisible(true);
            this.uni.btnModificar.setEnabled(false);
            this.uni.btnGuardar.setEnabled(true);
            this.uni.btnEliminar.setEnabled(false);
            this.uni.txtNumeroUnidad.setEnabled(true);

            moduni.setId_condominio(panta1.rif.getText());

        }

        if (e.getSource() == uni.btnBuscarpropietarios) {
            this.buscp.setVisible(true);
            llenartablapropietarios(buscp.tablaprop);
        }

        if (e.getSource() == catauni.jButton7) {
            this.detacun.setVisible(true);

        }

        if (e.getSource() == uni.btnGuardar) {
            if (validar()) {
                moduni.setCedula(uni.txtCedula.getText());
                moduni.setN_unidad(uni.txtNumeroUnidad.getText());
                moduni.setArea(Double.parseDouble(uni.txtArea.getText()));
                moduni.setDireccion(uni.txadireccion.getText());

                if (moduni.registrarUnidades(moduni)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    llenartablaunidades(catauni.jTable1);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == uni.btnModificar) {
            if (validar()) {
                moduni.setCedula(uni.txtCedula.getText());
                moduni.setN_unidad(uni.txtNumeroUnidad.getText());
                moduni.setArea(Double.parseDouble(uni.txtArea.getText()));
                moduni.setDireccion(uni.txadireccion.getText());

                if (moduni.modificarUnidades(moduni)) {

                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    llenartablaunidades(catauni.jTable1);
                    uni.dispose();

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == uni.btnEliminar) {
            moduni.setCedula(uni.txtCedula.getText());

            moduni.eliminarUnidad(moduni);
            JOptionPane.showMessageDialog(null, "registro eliminado");
            llenartablaunidades(catauni.jTable1);
            uni.dispose();
        }

        if (e.getSource() == uni.btnLimpiar) {

            limpiar();

        }
    }

    public void limpiar() {

        uni.txtNumeroUnidad.setText(null);
        uni.txtArea.setText(null);
        uni.txtCedula.setText(null);
        uni.txtTelefono.setText(null);
        uni.txtCorreo.setText(null);
        uni.txadireccion.setText(null);
        uni.txtNombrePropietario.setText(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == buscp.tablaprop) {

            int fila = this.buscp.tablaprop.getSelectedRow(); // primero, obtengo la fila seleccionada
            uni.txtCedula.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 0)));
            uni.txtNombrePropietario.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 1)));
            uni.txtCorreo.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 2)));
            uni.txtTelefono.setText(String.valueOf(this.buscp.tablaprop.getValueAt(fila, 3)));
            buscp.dispose();
        }

        if (e.getSource() == catauni.jTable1) {
            String[] options = {"Ver detalles de pago", "Modificar datos"};
            int result = JOptionPane.showOptionDialog(null, "Seleccione si desea ver detalles de pagp o modificar datos", "MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                int fila = this.catauni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
                String dato = String.valueOf(this.catauni.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
                int fila2 = this.catauni.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
                String dato2 = String.valueOf(this.catauni.jTable1.getValueAt(fila2, 2)); // por ultimo, obtengo el valor de la celda
                detacun.setVisible(true);
                modc.setId_unidad(dato);
                modc.setId_condominio(panta1.rif.getText());
                detacun.txtPropietarios.setText(dato2);
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
                moduni.buscarUnidad(moduni);

                uni.txadireccion.setText(moduni.getDireccion());
                uni.txtArea.setText(String.valueOf(moduni.getArea()));
                uni.txtCedula.setText(moduni.getCedula());
                uni.txtCorreo.setText(moduni.getCorreo());
                uni.txtNombrePropietario.setText(moduni.getNombre());
                uni.txtNumeroUnidad.setText(moduni.getN_unidad());
                uni.txtTelefono.setText(moduni.getTelefono());
                uni.txtNumeroUnidad.setEnabled(false);
                uni.btnEliminar.setEnabled(true);
                uni.btnModificar.setEnabled(true);
                uni.btnGuardar.setEnabled(false);
            }
        }

        if (e.getSource() == detacun.jTable1) {
            modc.setId_unidad(detacun.txtUnidad.getText());
            int fila = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String datos = String.valueOf(this.detacun.jTable1.getValueAt(fila, 1)); // por ultimo, obtengo el valor de la celda
            int fila2 = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato2 = String.valueOf(this.detacun.jTable1.getValueAt(fila2, 2)); // por ultimo, obtengo el valor de la celda
            int fila3 = this.detacun.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato3 = String.valueOf(this.detacun.jTable1.getValueAt(fila3, 0)); // por ultimo, obtengo el valor de la celda
            this.detare.setVisible(true);

            modc.setMes_cierre(Integer.parseInt(datos));
            modc.setAño_cierre(Integer.parseInt(dato2));
            modc.setId_condominio(panta1.rif.getText());
            llenardetallegasto(detare.tablagastos);
            llenardetallecuotas(detare.tablacuotas);
            llenardetallesancion(detare.tablasancion);
            llenardetalleinteres(detare.tablainteres);
            detare.txtMes.setText(datos);
            detare.txtUnidad.setText(detacun.txtUnidad.getText());
            detare.txtPropietarios.setText(detacun.txtPropietarios.getText());
            detare.txtId.setText(dato3);
            modc.bucartotal(modc);
            double var10 = modc.getAlicuota() * 100;
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
        if (e.getSource() == buscp.txtBuscarProp) {
            filtro(buscp.txtBuscarProp.getText(), buscp.tablaprop);
        }
        if (e.getSource() == detacun.txtBuscar) {
            filtro(detacun.txtBuscar.getText(), detacun.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        moduni.setId_condominio(panta1.rif.getText());
        llenartablaunidades(catauni.jTable1);
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

        if (uni.txtCedula.getText().isEmpty()) {

            msj += "El campo CI/RIF no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txtTelefono.getText().isEmpty()) {

            msj += "El campo teléfono no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txtArea.getText().isEmpty()) {

            msj += "El campo área no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txtCorreo.getText().isEmpty()) {

            msj += "El campo correo electrónico no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txadireccion.getText().isEmpty()) {

            msj += "El campo dirección no puede estar vacio\n";
            resultado = false;
        }

        if (uni.txtNombrePropietario.getText().isEmpty()) {

            msj += "El campo propietario/inquilino no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
