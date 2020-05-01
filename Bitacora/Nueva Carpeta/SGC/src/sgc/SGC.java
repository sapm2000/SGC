package sgc;

import modelo.Condominio;
import vista.InicioUsuario;


public class SGC {
public static Condominio condominioActual;

    public static void main(String[] args) {


        
        InicioUsuario vistaU = new InicioUsuario();
        vistaU.setVisible(true);
    }
    
}
