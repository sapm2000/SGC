package sgc;

import modelo.Condominio;
import vista.InicioUsuario;
import vista.PantallaPrincipal;


public class SGC {
    
public static Condominio condominioActual;
public static PantallaPrincipal panta;

    public static void main(String[] args) {


        InicioUsuario vistaU = new InicioUsuario();
        vistaU.setVisible(true);
    }
    
}
