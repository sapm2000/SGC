package sgc;

import controlador.CtrlUsuario;
import modelo.CrudUsuario;
import modelo.Usuario;
import vista.GestionarUsuario;


public class SGC {

    public static void main(String[] args) {

        GestionarUsuario vistaGu = new GestionarUsuario();
        Usuario mod = new Usuario();
        CrudUsuario modC = new CrudUsuario();
        
        CtrlUsuario ctrl = new CtrlUsuario(mod, modC, vistaGu);
        vistaGu.iniciar();
        vistaGu.setVisible(true);
    }
    
}
