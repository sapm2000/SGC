package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import modelo.Condominio;
import modelo.Usuario;
import sgc.SGC;
import vista.InicioUsuario;
import vista.PantallaPrincipal;

public class CtrlUsuarioL implements ActionListener, FocusListener {

    private InicioUsuario vistaU;
    //private MetodosUsuario modelo = new MetodosUsuario();

    PantallaPrincipal pp = new PantallaPrincipal();
    private controladorCondominio condominio;
    private Condominio modCondominio;

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

    public void enter() {

        char p[] = vistaU.txtPassword.getPassword();
        String pass = new String(p);

        if (vistaU.txtUsuario.getText().isEmpty() || pass.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Debe Digitar un Usuario y una contraseña", "Error en la operacion", JOptionPane.ERROR_MESSAGE);

        } else {

            String user = vistaU.txtUsuario.getText();
            Usuario usu = new Usuario();
            usu.setUsuario(user);
            usu.setPassword(Validacion.encriptar(pass));

            if (usu.login()) {
                //usu.setPassword("");
                SGC.usuarioActual = usu;
                vistaU.dispose();

                modCondominio = new Condominio();

                if (modCondominio.existe()) {
                    modCondominio.buscar();
                    SGC.condominioActual = modCondominio;
                    
                    controladorMenu ctrlMenu = new controladorMenu();

                } else {
                    JOptionPane.showMessageDialog(null, "Los datos del condominio no están configurados\nIngrese los datos a continuación", "Información", JOptionPane.INFORMATION_MESSAGE);
                    condominio = new controladorCondominio(false);
                    
                }

            } else {
                JOptionPane.showMessageDialog(null, "Acceso Denegado", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
    }

    public void eventos() {

        vistaU.btnEnviar.addActionListener(this);
        vistaU.txtUsuario.addActionListener(this);
        vistaU.txtPassword.addActionListener(this);
        vistaU.checkViewPass.addActionListener(this);
        vistaU.txtUsuario.addFocusListener(this);
        vistaU.txtPassword.addFocusListener(this);

    }

}
