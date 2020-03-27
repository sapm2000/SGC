package MVCEscuela;



import vista.*;
import controlador.*;

/**
 *
 * @author Anthony
 */
public class MVCEscuela {

    
    public static void main(String[] args) {
        
  
        Principal vista = new Principal();
  
        
        CtrlPrincipal ctrl = new CtrlPrincipal(vista);
        
        ctrl.iniciar();
        
        vista.setVisible(true);
      
        
               
        
    }
    
}