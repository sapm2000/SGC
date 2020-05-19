package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.catalogoTipoUsuario;
import vista.tipoUsuario;

public class controladorTipoUsuario implements ActionListener {

    private tipoUsuario vista;
    private catalogoTipoUsuario catalogo;
    private TipoUsuario modelo;

    public controladorTipoUsuario() {
        this.catalogo = new catalogoTipoUsuario();
        this.vista = new tipoUsuario();
        this.catalogo.btnNuevo.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);

        this.vista.btnModificar.addActionListener(this);
        this.catalogo.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {
            this.vista.setVisible(true);
            this.vista.btnModificar.setVisible(false);
            this.vista.btnGuardar.setVisible(true);

        }

        if (e.getSource() == vista.btnGuardar) {
            JOptionPane.showMessageDialog(null, "registro guardado");

        }

        if (e.getSource() == vista.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }

    }
}
