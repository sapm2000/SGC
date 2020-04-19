/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Cuenta;
import vista.catalogoCuenta;
import vista.cuenta;

/**
 *
 * @author rma
 */
public class controladorCuenta implements ActionListener {

    private catalogoCuenta catacu;
    private cuenta cu;
    private Cuenta modcu;

    public controladorCuenta(catalogoCuenta catacu, cuenta cu, Cuenta modcu) {
        this.catacu = catacu;
        this.cu = cu;
        this.modcu = modcu;
        this.catacu.btn_nuevaCuenta.addActionListener(this);
        this.cu.btnGuardar.addActionListener(this);
        this.cu.btnLimpiar.addActionListener(this);
        this.cu.btnModificar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacu.btn_nuevaCuenta) {

            modcu.llenar_banco(cu.jComboBox1);
            this.cu.setVisible(true);
            this.cu.btnEliminar.setEnabled(false);
            this.cu.btnGuardar.setEnabled(true);

            this.cu.btnModificar.setEnabled(false);
            cu.jTextField1.setText("");
            cu.jTextField2.setText("");
            cu.jTextField3.setText("");

        }

        if (e.getSource() == cu.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == cu.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

}
