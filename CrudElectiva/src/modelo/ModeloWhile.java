package modelo;

import javax.swing.JOptionPane;
import sun.java2d.Disposer;


public class ModeloWhile {
    
    public int numeroA;

    public int getNumeroA() {
        return numeroA;
    }

    public void setNumeroA(int numeroA) {
        this.numeroA = numeroA;
    }
    
public int whiledo(){
    //aqui decimos que de los datos enviados desde lacaja de texto. solo deben ser menor o igual a 15. caso contrario
    // mostrara un mensaje. en el do while. hacemos que la ventana de dialogo se muestre hasta llegar a 15
       if (this.numeroA <= 15) {
           int i = this.numeroA;
                         do {        
               
                JOptionPane.showMessageDialog(null,"numero "+i++);
                   
    } while (i<=15);
                         
           
                    return 0;
       }else{
           JOptionPane.showMessageDialog(null, "debe introducir un numero menor que 15");
       }
 return 0;
}    
    
}
