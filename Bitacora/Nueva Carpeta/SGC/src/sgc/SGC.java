package sgc;

import modelo.Condominio;
import modelo.Usuario;
import vista.InicioUsuario;
import vista.PantallaPrincipal;

public class SGC {

    public static Usuario usuarioActual;
    public static Condominio condominioActual;
    public static PantallaPrincipal panta;

    public static void main(String[] args) {
        InicioUsuario vistaU = new InicioUsuario();
        vistaU.setVisible(true);

    }

}
