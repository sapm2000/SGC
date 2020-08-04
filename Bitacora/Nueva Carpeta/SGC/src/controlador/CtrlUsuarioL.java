package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import modelo.Condominio;
import modelo.Mensaje;
import modelo.Usuario;
import sgc.SGC;
import vista.PantallaPrincipal;
import vista.VisInicioUsuario;

public class CtrlUsuarioL implements ActionListener, FocusListener {

    private VisInicioUsuario vistaU;

    PantallaPrincipal pp = new PantallaPrincipal();
    private CtrlCondominio condominio;
    private Condominio modCondominio;
    
    private Mensaje modMsj;

    public CtrlUsuarioL(VisInicioUsuario vistau) {

        this.vistaU = vistau;
        
        modMsj = new Mensaje();
        
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
            
            UIManager UI = new UIManager();

            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            int botonDialogo = JOptionPane.YES_NO_OPTION;
            Icon y = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, "Debe ingresar un Usuario y una Contraseña", "ERROR EN LA OPERACIÓN", JOptionPane.ERROR_MESSAGE, y);

        } else {

            String user = vistaU.txtUsuario.getText();
            Usuario usu = new Usuario();
            usu.setUsuario(user);
            usu.setPassword(Validacion.encriptar(pass));

            if (usu.login()) {
                
                usu.consultarPerfil();
                SGC.usuarioActual = usu;
                vistaU.dispose();

                modCondominio = new Condominio();
                modMsj.eliminarMensaje();
                
                if (modCondominio.existe()) {
                    modCondominio.buscar();
                    SGC.condominioActual = modCondominio;

                    CtrlVentana ctrlMenu = new CtrlVentana();

                } else {
                    
                    UIManager UI = new UIManager();

                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.YES_NO_OPTION;
                    Icon y = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));
                    
                    JOptionPane.showMessageDialog(null, "Los datos del condominio no están configurados,\npor favor ingrese los datos a continuación", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE, y);
                    condominio = new CtrlCondominio(false);
                }

            } else {
                
                UIManager UI = new UIManager();

                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                int botonDialogo = JOptionPane.YES_NO_OPTION;
                Icon y = new ImageIcon(getClass().getResource("/img/no.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));
                
                JOptionPane.showMessageDialog(null, "Acceso Denegado", "ERROR", JOptionPane.ERROR_MESSAGE, y);
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
