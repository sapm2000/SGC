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
import javax.swing.table.TableRowSorter;
import modelo.Banco;
import modelo.Cuenta;
import modelo.Funcion;
import modelo.Persona;
import sgc.SGC;
import vista.Catalogo;
import vista.VisCuenta;

public class CtrlCuenta implements ActionListener, ItemListener, MouseListener, KeyListener {

    private Catalogo catalogo;
    private VisCuenta vista;
    private Cuenta modelo;
    private ArrayList<Cuenta> lista;
    DefaultTableModel dm;
    private Persona modPersona;
    private Banco modBanco;
    private ArrayList<Banco> listaBanco;

    private Funcion permiso;

    public CtrlCuenta() {
        this.catalogo = new Catalogo();
        this.vista = new VisCuenta();
        this.modelo = new Cuenta();

        this.modBanco = new Banco();
        this.modPersona = new Persona();

        catalogo.lblTitulo.setText("Cuenta");

        stylecombo(vista.cbxCedula);
        stylecombo(vista.cbxBanco);
        stylecombo(vista.cbxTipo);

        crearCbxBanco();
        llenarTabla(catalogo.tabla);

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        Component[] components = vista.jPanel2.getComponents();

        JComponent[] com = {
            vista.txtCedula, vista.txtN_cuenta, vista.txtBeneficiario
        };

        Validacion.copiar(components);
        Validacion.pegar(com);

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);

        this.vista.txtCedula.addActionListener(this);
        this.vista.btnCedula.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        vista.cbxCedula.addItemListener(this);
        vista.cbxBanco.addItemListener(this);
        vista.cbxTipo.addItemListener(this);
        this.vista.txtCedula.addKeyListener(this);
        this.vista.txtN_cuenta.addKeyListener(this);
        this.vista.txtBeneficiario.addKeyListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);

        CtrlVentana.cambiarVista(catalogo);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            limpiar();

            vista.txtN_cuenta.setEditable(true);
            vista.cbxCedula.setEnabled(true);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnEliminar.setEnabled(false);

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                int ind;
                String cedula;

                cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();
                modelo.getBeneficiario().setCedula(cedula);
                ind = vista.cbxBanco.getSelectedIndex() - 1;
                modelo.getBanco().setId(listaBanco.get(ind).getId());
                modelo.setTipo(vista.cbxTipo.getSelectedItem().toString());
                modelo.setN_cuenta(vista.txtN_cuenta.getText());

                if (modelo.buscarInactivo(modelo)) {
                    JOptionPane.showMessageDialog(null, "Esta cuenta ya está registrada en la BD, se recuperarán los datos");

                    if (modelo.reactivar()) {
                        JOptionPane.showMessageDialog(null, "Cuenta habilitada");
                        llenarTabla(catalogo.tabla);
                        CtrlVentana.cambiarVista(catalogo);
                        limpiar();

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo habilitar la cuenta");
                    }

                } else {
                    if (!modelo.existe()) {
                        if (modelo.registrar()) {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            llenarTabla(catalogo.tabla);
                            CtrlVentana.cambiarVista(catalogo);
                            limpiar();

                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo registrar");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Este número de cuenta ya existe");
                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                int ind;
                String cedula;

                modelo.setN_cuenta(vista.txtN_cuenta.getText());
                cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();
                modelo.getBeneficiario().setCedula(cedula);
                modelo.setTipo(vista.cbxTipo.getSelectedItem().toString());
                ind = vista.cbxBanco.getSelectedIndex() - 1;
                modelo.getBanco().setId(listaBanco.get(ind).getId());

                String j = modelo.getN_cuenta();

                if (modelo.modificar()) {
                    JOptionPane.showMessageDialog(null, "Registro modificado");
                    llenarTabla(catalogo.tabla);
                    CtrlVentana.cambiarVista(catalogo);

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar");
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modelo.setN_cuenta(vista.txtN_cuenta.getText());

            modelo.eliminar();
            JOptionPane.showMessageDialog(null, "Registro eliminado");
            llenarTabla(catalogo.tabla);
            CtrlVentana.cambiarVista(catalogo);
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == vista.txtCedula || e.getSource() == vista.btnCedula) {
            String letra = vista.cbxCedula.getSelectedItem().toString();
            String documento = letra + "-" + vista.txtCedula.getText();

            if (letra == "V" || letra == "E") {
                modPersona.setCedula(documento);

                if (modPersona.buscar()) {
                    vista.txtBeneficiario.setText(modPersona.getpNombre() + " " + modPersona.getpApellido());

                } else {
                    JOptionPane.showMessageDialog(vista, "No se ha encontrado una persona con esta cédula");
                }
            }
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catalogo.tabla) {
            int fila;

            fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            modelo = lista.get(fila);

            if (permiso.getModificar()) {
                vista.btnModificar.setEnabled(true);
            }
            if (permiso.getEliminar()) {
                vista.btnEliminar.setEnabled(true);
            }
            if (modelo.getBeneficiario().getCedula() != null) {
                vista.cbxCedula.setSelectedItem(modelo.getBeneficiario().getCedula().split("-")[0]);
                vista.txtCedula.setText(modelo.getBeneficiario().getCedula().split("-")[1]);
                vista.txtBeneficiario.setText(modelo.getBeneficiario().getpNombre() + " " + modelo.getBeneficiario().getpApellido());
            } else {
                vista.cbxCedula.setEnabled(false);
                vista.cbxCedula.setSelectedItem("J");
                vista.txtCedula.setText(modelo.getCondominio().getRif().split("-")[1]);
                vista.txtBeneficiario.setText(modelo.getCondominio().getRazonS());

            }
            vista.cbxBanco.setSelectedItem(modelo.getBanco().getNombre_banco());
            vista.cbxTipo.setSelectedItem(modelo.getTipo());
            vista.txtN_cuenta.setText(modelo.getN_cuenta());

            vista.txtN_cuenta.setEditable(false);

            vista.btnGuardar.setEnabled(false);
            vista.btnModificar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);

            CtrlVentana.cambiarVista(vista);
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

        if (ke.getSource() == vista.txtCedula) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCedula.getText(), 8);
        }
        if (ke.getSource() == vista.txtN_cuenta) {

            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtN_cuenta.getText(), 20);
        }
        if (ke.getSource() == vista.txtBeneficiario) {

            Validacion.soloLetras(ke);
            Validacion.limite(ke, vista.txtBeneficiario.getText(), 50);
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

    private void crearCbxBanco() {
        listaBanco = modBanco.listar();
        vista.cbxBanco.addItem("Seleccione...");

        if (listaBanco != null) {
            for (Banco item : listaBanco) {
                vista.cbxBanco.addItem(item.getNombre_banco());
            }

        }
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    public void limpiar() {
        vista.cbxCedula.setSelectedIndex(0);
        vista.txtCedula.setText("");
        vista.cbxBanco.setSelectedIndex(0);
        vista.cbxTipo.setSelectedIndex(0);
        vista.txtN_cuenta.setText("");
        vista.txtBeneficiario.setText("");
    }

    public void llenarTabla(JTable tablaD) {
        lista = modelo.listarcuenta();
        int ind;

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Banco");
        modeloT.addColumn("Cuenta");
        modeloT.addColumn("CI/RIF");
        modeloT.addColumn("Beneficiario");
        modeloT.addColumn("Tipo");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getBanco().getNombre_banco();
            columna[ind++] = lista.get(i).getN_cuenta();

            if (lista.get(i).getBeneficiario().getCedula() != null) {
                columna[ind++] = lista.get(i).getBeneficiario().getCedula();
                columna[ind++] = lista.get(i).getBeneficiario().getpNombre() + " " + lista.get(i).getBeneficiario().getpApellido();

            } else {
                columna[ind++] = lista.get(i).getCondominio().getRif();
                columna[ind++] = lista.get(i).getCondominio().getRazonS();
            }

            columna[ind++] = lista.get(i).getTipo();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.cbxBanco.getSelectedIndex() == 0) {
            msj += "Debe seleccionar un Banco\n";
            resultado = false;
        }

        if (vista.txtN_cuenta.getText().isEmpty()) {
            msj += "El campo N° de Cuenta no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtBeneficiario.getText().isEmpty()) {
            msj += "El campo Beneficiario no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxCedula.setFocusable(false);
        vista.cbxBanco.setFocusable(false);
        vista.cbxTipo.setFocusable(false);

        if (e.getSource() == vista.cbxCedula) {

            if (vista.cbxCedula.getSelectedItem().equals("J")) {
                vista.txtCedula.setEnabled(false);
                vista.txtBeneficiario.setEnabled(false);
                vista.txtCedula.setText(SGC.condominioActual.getRif().split("-")[1]);
                vista.txtBeneficiario.setText(SGC.condominioActual.getRazonS());

            } else {
                vista.txtCedula.setEnabled(true);
                vista.txtBeneficiario.setEnabled(true);
            }
        }
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }

}
