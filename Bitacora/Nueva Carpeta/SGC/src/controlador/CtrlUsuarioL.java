package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.InicioUsuario;
import vista.PantallaPrincipal;

public class CtrlUsuarioL implements ActionListener, FocusListener {

    private InicioUsuario vistaU;
    //private MetodosUsuario modelo = new MetodosUsuario();

    PantallaPrincipal pp = new PantallaPrincipal();

    public void eventos() {

        vistaU.btnEnviar.addActionListener(this);
        vistaU.txtUsuario.addActionListener(this);
        vistaU.txtPassword.addActionListener(this);
        vistaU.checkViewPass.addActionListener(this);
        vistaU.txtUsuario.addFocusListener(this);
        vistaU.txtPassword.addFocusListener(this);

    }

    public CtrlUsuarioL(InicioUsuario vistau) {

        this.vistaU = vistau;
        eventos();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object evt = e.getSource();
        if (evt.equals(vistaU.txtUsuario)) {
            enter();
        }
        if (evt.equals(vistaU.txtPassword)) {
            enter();
        }

        if (evt.equals(vistaU.btnEnviar)) {

            enter();

        } else if (evt.equals(vistaU.checkViewPass)) {

            if (vistaU.checkViewPass.isSelected()) {

                vistaU.txtPassword.setEchoChar((char) 0);

            } else {

                vistaU.txtPassword.setEchoChar('*');//coloca * en la contrasenha;

            }

        }

    }

    public void enter() {

        char p[] = vistaU.txtPassword.getPassword();
        String pass = new String(p);

        if (vistaU.txtUsuario.getText().isEmpty() || pass.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Debe Digitar un Usuario y una contrasenia", "Error en la operacion", JOptionPane.ERROR_MESSAGE);

        } else {

            String user = vistaU.txtUsuario.getText();
            Usuario usu = new Usuario();
            usu.setUsuario(user);
            usu.setPassword(pass);

            if (usu.login()) {
                if ("sapm".equals(usu.getUsuario())) { //No lo borres uwu
                    JOptionPane.showMessageDialog(null, "Samuel te quiero mucho uwu", "<3", JOptionPane.INFORMATION_MESSAGE);
                }

                vistaU.dispose();
                controladorMenu ctrlMenu = new controladorMenu();
                //pp.setVisible(true);

            } else {

                JOptionPane.showMessageDialog(null, "Acceso Denegado", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == vistaU.txtUsuario) {
            vistaU.txtUsuario.setText("");
            vistaU.txtUsuario.removeFocusListener(this);
        }
        if (e.getSource() == vistaU.txtPassword) {
            vistaU.txtPassword.setText("");
            vistaU.txtPassword.removeFocusListener(this);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

}
