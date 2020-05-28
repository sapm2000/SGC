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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Banco;
import modelo.Condominio;
import modelo.Cuenta;
import vista.catalogoCuenta;
import vista.cuenta;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class controladorCuenta implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoCuenta catacu;
    private cuenta cu;
    private Cuenta modcu;
    private Banco modban;
    private Condominio modcon;

    DefaultTableModel dm;
    ArrayList<Banco> listaBanco;
    ArrayList<Cuenta> listaCuenta;
    ArrayList<Condominio> listaCondo;

    public controladorCuenta() {
        this.catacu = new catalogoCuenta();
        this.cu = new cuenta();
        this.modcu = new Cuenta();
        this.modban = new Banco();
        this.modcon = new Condominio();

        this.catacu.btn_nuevaCuenta.addActionListener(this);
        this.cu.btnGuardar.addActionListener(this);
        this.cu.btnLimpiar.addActionListener(this);
        this.cu.btnModificar.addActionListener(this);
        this.catacu.addWindowListener(this);
        this.catacu.jTable1.addMouseListener(this);
        this.cu.btnEliminar.addActionListener(this);
        this.cu.txtCedula.addKeyListener(this);
        this.cu.txtN_cuenta.addKeyListener(this);
        this.cu.txtBeneficiario.addKeyListener(this);

        this.catacu.setVisible(true);
    }

    public void Llenartabla(JTable tablaD) {

        listaCuenta = modcu.listarcuenta();
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

        Object[] columna = new Object[6];

        int numRegistro = listaCuenta.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCuenta.get(i).ban.getNombre_banco();
            columna[1] = listaCuenta.get(i).getN_cuenta();
            columna[2] = listaCuenta.get(i).getCedula();
            columna[3] = listaCuenta.get(i).getBeneficiario();
            columna[4] = listaCuenta.get(i).getTipo();

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

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacu.btn_nuevaCuenta) {
            cu.jComboBox1.removeAllItems();
            modcu.llenar_banco(cu.jComboBox1);
            this.cu.setVisible(true);
            this.cu.btnEliminar.setEnabled(false);
            this.cu.btnGuardar.setEnabled(true);

            this.cu.btnModificar.setEnabled(false);
            cu.txtN_cuenta.setText("");
            cu.txtN_cuenta.setEnabled(true);
            cu.txtBeneficiario.setText("");
            cu.txtCedula.setText("");

        }

        if (e.getSource() == cu.btnGuardar) {
            if (validar()) {
                modcu.setCedula(cu.txtCedula.getText());
                modcu.setBeneficiario(cu.txtBeneficiario.getText());
                modcu.setN_cuenta(cu.txtN_cuenta.getText());
                modcu.setTipo(cu.jComboBox2.getSelectedItem().toString());
                modban.setNombre_banco(cu.jComboBox1.getSelectedItem().toString());

                if (modban.getNombre_banco().equals("Seleccione el Banco")) {
                    JOptionPane.showMessageDialog(null, "seleccione un banco");
                } else {
                    modban.buscar(modban);
                    modcu.ban.setId(modban.getId());
                    if (modcu.buscarInactivo(modcu)) {
                        modcu.activarcuenta(modcu);
                        modcu.modificarcuenta(modcu);
                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(catacu.jTable1);

                    } else {

                        if (modcu.registrar(modcu)) {

                            JOptionPane.showMessageDialog(null, "Registro Guardado");

                            Llenartabla(catacu.jTable1);
                            cu.dispose();
                            limpiar();
                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }
                    }

                }

            }
        }

        if (e.getSource() == cu.btnModificar) {
            if (validar()) {

                modcu.setCedula(cu.txtCedula.getText());
                modcu.setBeneficiario(cu.txtBeneficiario.getText());
                modcu.setN_cuenta(cu.txtN_cuenta.getText());
                String j = modcu.getN_cuenta();

                modcu.setTipo(cu.jComboBox2.getSelectedItem().toString());
                modban.setNombre_banco(cu.jComboBox1.getSelectedItem().toString());
                if (modban.getNombre_banco().equals("Seleccione el Banco")) {
                    JOptionPane.showMessageDialog(null, "seleccione un banco");
                } else {
                    modban.buscar(modban);
                    modcu.ban.setId(modban.getId());

                    if (modcu.buscarInactivo(modcu)&&modcu.getN_cuenta().equals(j)) {
                        
                       JOptionPane.showMessageDialog(null, "no puede colocar un numero de cuenta que ya existio, si quiere colocar este numero de cuenta debe registrarlo nuevamente");

                       

                    } else {

                        if (modcu.modificarcuenta(modcu)) {

                            JOptionPane.showMessageDialog(null, "Registro modificado");

                            Llenartabla(catacu.jTable1);
                            cu.dispose();
                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }}
                    }
                }
            }

            if (e.getSource() == cu.btnEliminar) {
                modcu.setN_cuenta(cu.txtN_cuenta.getText());

                modcu.eliminarcuenta(modcu);
                JOptionPane.showMessageDialog(null, "registro eliminado");
                cu.dispose();
                Llenartabla(catacu.jTable1);
            }

            if (e.getSource() == cu.btnLimpiar) {
                limpiar();
            }
        }

    

    

    public void limpiar() {

        cu.txtCedula.setText(null);
        cu.txtN_cuenta.setText(null);
        cu.txtBeneficiario.setText(null);
        cu.jComboBox1.setSelectedItem(0);
        cu.jComboBox2.setSelectedItem(0);

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (cu.txtN_cuenta.getText().isEmpty()) {

            msj += "El campo número de cuenta no puede estar vacio\n";
            resultado = false;
        }

        if (cu.txtBeneficiario.getText().isEmpty()) {

            msj += "El campo beneficiario no puede estar vacío\n";
            resultado = false;
        }

        if (cu.txtCedula.getText().isEmpty()) {

            msj += "El campo rif/cédula no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catacu.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catacu.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catacu.jTable1.getValueAt(fila, 1)); // por ultimo, obtengo el valor de la celda

        modcu.setN_cuenta(String.valueOf(dato));

        modcu.buscarcuenta(modcu);

        cu.setVisible(true);
        cu.jComboBox1.removeAllItems();
        modcu.llenar_banco(cu.jComboBox1);

        cu.txtCedula.setText(modcu.getCedula());
        cu.txtBeneficiario.setText(modcu.getBeneficiario());
        cu.txtN_cuenta.setText(modcu.getN_cuenta());
        cu.txtN_cuenta.setEnabled(false);
        cu.jComboBox1.setSelectedItem(modcu.ban.getNombre_banco());
        cu.jComboBox2.setSelectedItem(modcu.getTipo());
        modcon.setRif(modcu.getN_cuenta());
        cu.btnGuardar.setEnabled(false);
        cu.btnModificar.setEnabled(true);
        cu.btnEliminar.setEnabled(true);

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

        if (ke.getSource() == cu.txtCedula) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, cu.txtCedula.getText(), 8);
        }
        if (ke.getSource() == cu.txtN_cuenta) {

            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, cu.txtN_cuenta.getText(), 20);
        }
        if (ke.getSource() == cu.txtBeneficiario) {

            Validacion.soloLetras(ke);
            Validacion.limite(ke, cu.txtBeneficiario.getText(), 50);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catacu.txtBuscarCuenta) {

            filtro(catacu.txtBuscarCuenta.getText(), catacu.jTable1);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catacu.jTable1);

        Component[] components = cu.jPanel2.getComponents();
        JComponent[] com = {
            cu.txtCedula, cu.txtN_cuenta, cu.txtBeneficiario
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

}
