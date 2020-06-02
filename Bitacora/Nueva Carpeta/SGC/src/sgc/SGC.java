package sgc;

import modelo.Condominio;
import modelo.Usuario;
import vista.VisInicioUsuario;
import vista.PantallaPrincipal;

public class SGC {

    public static Usuario usuarioActual;
    public static Condominio condominioActual;
    public static PantallaPrincipal panta;

    public static void main(String[] args) {
        VisInicioUsuario vistaU = new VisInicioUsuario();
        vistaU.setVisible(true);

    }

}
