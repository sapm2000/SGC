package modelo;

import javax.swing.JOptionPane;


public class ModeloWhile {
    
    public int numeroA;

    public int getNumeroA() {
        return numeroA;
    }

    public void setNumeroA(int numeroA) {
        this.numeroA = numeroA;
    }
    
public int whiledo(){
    
       if (this.numeroA <= 15) {
           int i = 0;
                         do {        
               
                  JOptionPane.showMessageDialog(null,"numero "+i);
                    i++;
    } while (i<=0);
           return 0;
       }else{
           JOptionPane.showMessageDialog(null, "debe introducir un numero menor que 15");
       }
 return 0;
}    
    
}
