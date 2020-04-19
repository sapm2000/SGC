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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
    }

    public void Llenartabla(JTable tablaD) {

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Banco");
        modeloT.addColumn("Cuenta");
        modeloT.addColumn("CI/RIF");
        modeloT.addColumn("Beneficiario");
        modeloT.addColumn("Tipo");
        modeloT.addColumn("Condominio");

        Object[] columna = new Object[6];

        int numRegistro = modcu.listarcuenta().size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = modcu.listarcuenta().get(i).getNombre_banco();
            columna[1] = modcu.listarcuenta().get(i).getN_cuenta();
            columna[2] = modcu.listarcuenta().get(i).getCedula();
            columna[3] = modcu.listarcuenta().get(i).getBeneficiario();
            columna[4] = modcu.listarcuenta().get(i).getTipo();

            modeloT.addRow(columna);

        }

    }

    public void Llenartablacondominio(JTable tablaD) {

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Rif");
        modeloT.addColumn("Razon social");
        modeloT.addColumn("Accion");

        Object[] columna = new Object[2];

        int numRegistro = modcon.lPerson().size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = modcon.lPerson().get(i).getRif();
            columna[1] = modcon.lPerson().get(i).getRazonS();

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
                            modcu.setId_condominio(Integer.parseInt(valor));
                            modcu.setN_cuenta(cu.txtN_cuenta.getText());
                            modcu.registrar_cuenta_condominio(modcu);

                        }
                    }
                    cu.dispose();
                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == cu.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

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
}
