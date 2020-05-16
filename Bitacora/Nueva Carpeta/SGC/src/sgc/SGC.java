package sgc;

import controlador.controladorMenu;
import modelo.Condominio;
import vista.InicioUsuario;


public class SGC {
    
public static Condominio condominioActual;

    public static void main(String[] args) {


        controladorMenu conme = new controladorMenu();
        InicioUsuario vistaU = new InicioUsuario();
        vistaU.setVisible(true);
    }
    
}
