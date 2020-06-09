package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import modelo.Usuario;
import vista.VisPerfil;

public class CtrlPerfil implements ActionListener, ItemListener {

    private VisPerfil vista;
    private Usuario modelo;

    public CtrlPerfil() {

        this.vista = new VisPerfil();
        this.modelo = new Usuario();
        this.vista.cbxConfigurar.addItemListener(this);
        CtrlVentana.cambiarVista(vista);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == vista.cbxConfigurar) {

            if (vista.cbxConfigurar.getSelectedIndex() == 1) {
                this.vista.jpanePregunta.setVisible(true);
                this.vista.jPanelClave.setVisible(false);
            } else if (vista.cbxConfigurar.getSelectedIndex() == 2) {
                this.vista.jPanelClave.setVisible(true);
                this.vista.jpanePregunta.setVisible(false);

            }
        }
    }

}
