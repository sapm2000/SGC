package crudelectiva;
import controlador.CtrlUsuarioL;
import modelo.UsuarioL;
import modelo.metodosUsuario;
import vista.InicioUsuario;


public class CrudElectiva {

    public static void main(String[] args) {
        
        InicioUsuario vistaU = new InicioUsuario();
        UsuarioL mod = new UsuarioL();
        metodosUsuario modU = new metodosUsuario();
        CtrlUsuarioL ctrlU = new CtrlUsuarioL(vistaU);
        
        vistaU.setVisible(true);
    }
    
}
