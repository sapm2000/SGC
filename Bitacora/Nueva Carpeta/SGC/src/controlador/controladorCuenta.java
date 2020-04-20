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
import static java.lang.String.valueOf;
import java.util.ArrayList;
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

/**
 *
 * @author rma
 */
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

    public controladorCuenta(catalogoCuenta catacu, cuenta cu, Cuenta modcu, Banco modban, Condominio modcon) {
        this.catacu = catacu;
        this.cu = cu;
        this.modcu = modcu;
        this.modban = modban;
        this.modcon = modcon;
        this.catacu.btn_nuevaCuenta.addActionListener(this);
        this.cu.btnGuardar.addActionListener(this);
        this.cu.btnLimpiar.addActionListener(this);
        this.cu.btnModificar.addActionListener(this);
        this.catacu.addWindowListener(this);
        this.catacu.jTable1.addMouseListener(this);
        this.cu.btnEliminar.addActionListener(this);
    }

    public void Llenartabla(JTable tablaD) {
        
        listaCuenta = modcu.listarcuenta();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Banco");
        modeloT.addColumn("Cuenta");
        modeloT.addColumn("CI/RIF");
        modeloT.addColumn("Beneficiario");
        modeloT.addColumn("Tipo");
        modeloT.addColumn("Condominio");

        Object[] columna = new Object[6];

        int numRegistro = listaCuenta.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCuenta.get(i).getNombre_banco();
            columna[1] = listaCuenta.get(i).getN_cuenta();
            columna[2] = listaCuenta.get(i).getCedula();
            columna[3] = listaCuenta.get(i).getBeneficiario();
            columna[4] = listaCuenta.get(i).getTipo();
            columna[5] = listaCuenta.get(i).getCantidad();

            modeloT.addRow(columna);

        }

    }

    public void Llenartablacondominio(JTable tablaD) {
        
        listaCondo = modcon.lPerson();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Rif");
        modeloT.addColumn("Razon social");
        modeloT.addColumn("Accion");

        Object[] columna = new Object[2];

        int numRegistro = listaCondo.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCondo.get(i).getRif();
            columna[1] = listaCondo.get(i).getRazonS();

            modeloT.addRow(columna);

        }

    }

    public void Llenartablacondominiomodificar(JTable tablaD) {
        listaCondo = modcon.cuentacondominiomodificar();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Rif");
        modeloT.addColumn("Razon social");
        modeloT.addColumn("Accion");

        Object[] columna = new Object[3];

        int numRegistro = listaCondo.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCondo.get(i).getRif();
            columna[1] = listaCondo.get(i).getRazonS();
            columna[2] = listaCondo.get(i).getId_cuenta();
            if (listaCondo.get(i).getId_cuenta() != null) {
                columna[2] = Boolean.TRUE;
            } else {
                columna[2] = Boolean.FALSE;
            }

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacu.btn_nuevaCuenta) {

            modcu.llenar_banco(cu.jComboBox1);
            this.cu.setVisible(true);
            this.cu.btnEliminar.setEnabled(false);
            this.cu.btnGuardar.setEnabled(true);

            this.cu.btnModificar.setEnabled(false);
            cu.txtN_cuenta.setText("");
            cu.txtN_cuenta.setEnabled(true);
            cu.txtBeneficiario.setText("");
            cu.txtCedula.setText("");
            Llenartablacondominio(cu.jTable1);
            addCheckBox(2, cu.jTable1);

        }

        if (e.getSource() == cu.btnGuardar) {
            if (validar()) {
                modcu.setCedula(cu.txtCedula.getText());
                modcu.setBeneficiario(cu.txtBeneficiario.getText());
                modcu.setN_cuenta(cu.txtN_cuenta.getText());
                modcu.setTipo(cu.jComboBox2.getSelectedItem().toString());
                modban.setNombre_banco(cu.jComboBox1.getSelectedItem().toString());
                modban.buscar(modban);
                modcu.setId_banco(modban.getId());

                if (modcu.registrar(modcu)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                    for (int i = 0; i < cu.jTable1.getRowCount(); i++) {
                        if (IsSelected(i, 2, cu.jTable1)) {
                            String valor = String.valueOf(cu.jTable1.getValueAt(i, 0));
                            modcu.setId_condominio(valor);
                            modcu.setN_cuenta(cu.txtN_cuenta.getText());
                            modcu.registrar_cuenta_condominio(modcu);

                        }
                    }
                    Llenartabla(catacu.jTable1);
                    cu.dispose();
                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == cu.btnModificar) {
            if (validar()) {
                modcu.setCedula(cu.txtCedula.getText());
                modcu.setBeneficiario(cu.txtBeneficiario.getText());
                modcu.setN_cuenta(cu.txtN_cuenta.getText());
                modcu.setTipo(cu.jComboBox2.getSelectedItem().toString());
                modban.setNombre_banco(cu.jComboBox1.getSelectedItem().toString());
                modban.buscar(modban);
                modcu.setId_banco(modban.getId());

                if (modcu.modificarcuenta(modcu)) {

                    JOptionPane.showMessageDialog(null, "Registro modificado");

                    modcu.borrarpuente(modcu);

                    for (int i = 0; i < cu.jTable1.getRowCount(); i++) {

                        if (valueOf(cu.jTable1.getValueAt(i, 2)) == "true") {
                            String valor = String.valueOf(cu.jTable1.getValueAt(i, 0));
                            modcu.setId_condominio(valor);
                            modcu.setN_cuenta(cu.txtN_cuenta.getText());
                            modcu.registrar_cuenta_condominio(modcu);
                        } else {
                        }
                    }
                    Llenartablacondominiomodificar(cu.jTable1);
                    addCheckBox(2, cu.jTable1);
                    Llenartabla(catacu.jTable1);
                    cu.dispose();
                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }
        
        if (e.getSource() == cu.btnEliminar) {
            modcu.setN_cuenta(cu.txtN_cuenta.getText());
            modcu.borrarpuente(modcu);
            modcu.eliminarcuenta(modcu);
            JOptionPane.showMessageDialog(null, "registro eliminado");
            cu.dispose();
            Llenartabla(catacu.jTable1);
        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (cu.txtN_cuenta.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }

        if (cu.txtBeneficiario.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }

        if (cu.txtCedula.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
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
        cu.jComboBox1.setSelectedItem(modcu.getNombre_banco());
        cu.jComboBox2.setSelectedItem(modcu.getTipo());
        modcon.setRif(modcu.getN_cuenta());
        cu.btnGuardar.setEnabled(false);
        cu.btnModificar.setEnabled(true);
        cu.btnEliminar.setEnabled(true);

        Llenartablacondominiomodificar(cu.jTable1);
        addCheckBox(2, cu.jTable1);

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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public boolean IsSelected(int row, int column, JTable table) {

        return table.getValueAt(row, column) != null;
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }
}
