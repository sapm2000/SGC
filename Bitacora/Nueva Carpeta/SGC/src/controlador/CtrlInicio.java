package controlador;

import modelo.Login;
import modelo.MetodoIniciar;
import vista.InicioUsuario;


public class CtrlInicio {
    
    private InicioUsuario vistaI;
    private Login mod;
    private MetodoIniciar modI;
    
    public CtrlInicio(InicioUsuario vistaI, Login mod, MetodoIniciar modI){
    
        this.vistaI=vistaI;
        this.mod=mod;
        this.modI=modI;
        
        
        
        
    }
    
}
