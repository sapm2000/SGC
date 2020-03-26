package sgc;

import controlador.CtrlUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.CrudUsuario;
import modelo.Usuario;
import sun.util.logging.PlatformLogger;
import vista.GestionarUsuario;
import vista.InicioUsuario;


public class SGC {

    public static void main(String[] args) {

        /**GestionarUsuario vistaGu = new GestionarUsuario();
        Usuario mod = new Usuario();
        CrudUsuario modC = new CrudUsuario();
        
        CtrlUsuario ctrl = new CtrlUsuario(mod, modC, vistaGu);
        vistaGu.iniciar();
        vistaGu.setVisible(true);*/
        
      
        
        InicioUsuario vistaU = new InicioUsuario();
        vistaU.setVisible(true);
    }
    
}
