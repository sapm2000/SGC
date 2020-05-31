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
import java.util.ArrayList;
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
import sgc.SGC;
import vista.Catalogo;
import vista.cuenta;

public class controladorCuenta implements ActionListener, MouseListener, KeyListener, WindowListener {

    private Catalogo catalogo;
    private cuenta vista;
    private Cuenta modelo;
    private Banco modBanco;
    Funcion permiso;

    private ArrayList<Cuenta> lista;
    private ArrayList<Banco> listaBanco;

    public controladorCuenta() {
        this.catalogo = new Catalogo();
        this.vista = new cuenta();
        this.modelo = new Cuenta();
        this.modBanco = new Banco();

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.addWindowListener(this);

        crearCbxBanco();

        this.vista.txtCedula.addActionListener(this);
        this.vista.btnCedula.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.txtCedula.addKeyListener(this);
        this.vista.txtN_cuenta.addKeyListener(this);
        this.vista.txtBeneficiario.addKeyListener(this);
        this.vista.addWindowListener(this);

        this.catalogo.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            limpiar();

            vista.txtN_cuenta.setEditable(true);

            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnEliminar.setEnabled(false);

            this.vista.setVisible(true);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                int ind;
                String cedula;

                modelo.setN_cuenta(vista.txtN_cuenta.getText());
                cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();
                modelo.getBeneficiario().setCedula(cedula);
                modelo.setTipo(vista.cbxTipo.getSelectedItem().toString());
                ind = vista.cbxBanco.getSelectedIndex() - 1;
                modelo.getBanco().setId(listaBanco.get(ind).getId());

                if (modelo.buscarInactivo(modelo)) {
                    JOptionPane.showMessageDialog(null, "Esta cuenta ya está registrada en la BD, se recuperarán los datos");

                    if (modelo.reactivar()) {
                        JOptionPane.showMessageDialog(null, "Cuenta habilitada");
                        llenarTabla(catalogo.tabla);
                        vista.dispose();
                        limpiar();

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo habilitar la cuenta");
                    }

                } else {
                    if (!modelo.existe()) {
                        if (modelo.registrar()) {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            llenarTabla(catalogo.tabla);
                            vista.dispose();
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
                    vista.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar");
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modelo.setN_cuenta(vista.txtN_cuenta.getText());

            modelo.eliminar();
            JOptionPane.showMessageDialog(null, "Registro eliminado");
            vista.dispose();
            llenarTabla(catalogo.tabla);
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == vista.txtCedula || e.getSource() == vista.btnCedula) {
            String cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();

            if (modelo.buscarPersona(cedula)) {
                vista.txtBeneficiario.setText(modelo.getBeneficiario().getpNombre() + " " + modelo.getBeneficiario().getpApellido());

            } else {
                JOptionPane.showMessageDialog(vista, "No se ha encontrado una persona con esta cédula");
            }
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

            vista.cbxCedula.setSelectedItem(modelo.getBeneficiario().getCedula().split("-")[0]);
            vista.txtCedula.setText(modelo.getBeneficiario().getCedula().split("-")[1]);
            vista.cbxBanco.setSelectedItem(modelo.getBanco().getNombre_banco());
            vista.cbxTipo.setSelectedItem(modelo.getTipo());
            vista.txtBeneficiario.setText(modelo.getBeneficiario().getpNombre() + " " + modelo.getBeneficiario().getpApellido());
            vista.txtN_cuenta.setText(modelo.getN_cuenta());

            vista.txtN_cuenta.setEditable(false);

            vista.btnGuardar.setEnabled(false);
            vista.btnModificar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);

            vista.setVisible(true);
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

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getSource() == catalogo) {
            llenarTabla(catalogo.tabla);
            permisoBtn();

            if (permiso.getRegistrar()) {
                catalogo.btnNuevo.setEnabled(true);
            }
        }

        if (e.getSource() == vista) {
            Component[] components = vista.jPanel2.getComponents();

            JComponent[] com = {
                vista.txtCedula, vista.txtN_cuenta, vista.txtBeneficiario
            };

            Validacion.copiar(components);
            Validacion.pegar(com);
        }
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
        DefaultTableModel dm = (DefaultTableModel) jtableBuscar.getModel();
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
            columna[ind++] = lista.get(i).getBeneficiario().getCedula();
            columna[ind++] = lista.get(i).getBeneficiario().getpNombre() + " " + lista.get(i).getBeneficiario().getpApellido();
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

}
