package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import sgc.SGC;
import vista.VisMensaje;
import vista.VisPerfil;

public class CtrlPerfil implements ActionListener, ItemListener {

    private VisPerfil vista;
    private Usuario usuario;

    private VisMensaje visMensaje;

    public CtrlPerfil() {
        this.vista = new VisPerfil();
        visMensaje = new VisMensaje();

        usuario = SGC.usuarioActual;

        this.vista.btnProcesarPregunta.addActionListener(this);
        this.vista.btnProcesarPassword.addActionListener(this);
        this.vista.btnMensaje.addActionListener(this);
        this.vista.cbxConfigurar.addItemListener(this);

        this.visMensaje.btnSalir.addActionListener(this);

        usuario.consultarPerfil();
        mostrarPerfil();

        CtrlVentana.cambiarVista(vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnProcesarPregunta) {
            modificarPregunta();
        }

        if (e.getSource() == vista.btnProcesarPassword) {
            modificarClave();
        }

        if (e.getSource() == vista.btnMensaje) {
            vista.jTabbedPane1.setComponentAt(1, visMensaje);
            vista.repaint();
        }

        if (e.getSource() == visMensaje.btnSalir) {
            vista.jTabbedPane1.setComponentAt(1, vista.panelMensaje);
            vista.repaint();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == vista.cbxConfigurar) {

            if (vista.cbxConfigurar.getSelectedIndex() == 1) {
                this.vista.jPanelPregunta.setVisible(true);
                this.vista.jPanelClave.setVisible(false);
            } else if (vista.cbxConfigurar.getSelectedIndex() == 2) {
                this.vista.jPanelClave.setVisible(true);
                this.vista.jPanelPregunta.setVisible(false);

            }
        }
    }

    public void modificarPregunta() {
        String pass;
        String respuesta;
        pass = Validacion.encriptar(vista.txtPassActualPregunta.getText());
        respuesta = Validacion.encriptar(vista.txtRespuesta.getText());

        if (usuario.modificarPregunta(vista.txtPregunta.getText(), respuesta, pass)) {
            JOptionPane.showMessageDialog(vista, "Datos modificados");
        } else {
            JOptionPane.showMessageDialog(vista, "Datos no modificados");

        }

    }

    public void modificarClave() {

        if (vista.txtPassNuevo.getText().equals(vista.txtConfirmar.getText())) {

            String passNuevo;
            passNuevo = Validacion.encriptar(vista.txtPassNuevo.getText());
            String passActual;
            passActual = Validacion.encriptar(vista.txtPassActual.getText());

            if (usuario.modificarClave(passNuevo, passActual)) {
                JOptionPane.showMessageDialog(vista, "Datos modificados");
            } else {
                JOptionPane.showMessageDialog(vista, "Datos no modificados");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Contraseñas no coinciden");
        }

    }

    public void mostrarPerfil() {

        vista.txtNombres.setText(usuario.getPersona().getpNombre() + " " + usuario.getPersona().getsNombre());
        vista.txtApellidos.setText(usuario.getPersona().getpApellido() + " " + usuario.getPersona().getsApellido());
        vista.txtTelefono.setText(usuario.getPersona().getTelefono());
        vista.txtCorreo.setText(usuario.getPersona().getCorreo());
        vista.txtUsuario.setText(usuario.getTipoU().getNombre());
        vista.txtPregunta.setText(usuario.getPregunta());

    }

}
