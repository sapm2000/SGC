/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.catalogoTipoUsuario;
import vista.tipoUsuario;

/**
 *
 * @author rma
 */
public class controladorTipoUsuario implements ActionListener {

    private catalogoTipoUsuario catati;
    private tipoUsuario ti;

    public controladorTipoUsuario() {
        this.catati = new catalogoTipoUsuario();
        this.ti = new tipoUsuario();
        this.catati.jButton2.addActionListener(this);
        this.ti.btnGuardar.addActionListener(this);

        this.ti.btnModificar.addActionListener(this);
        this.catati.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catati.jButton2) {
            this.ti.setVisible(true);
            this.ti.btnModificar.setVisible(false);
            this.ti.btnGuardar.setVisible(true);

        }

        if (e.getSource() == ti.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == ti.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }

    }
}
