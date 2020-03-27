
package modelo;

import javax.swing.JOptionPane;


public class MOperadores {
    
    public int numeroUno;
    public int numeroDos;
    public int resultado;  

    public int getNumeroUno() {
        return numeroUno;
    }

    public void setNumeroUno(int numeroUno) {
        this.numeroUno = numeroUno;
    }

    public int getNumeroDos() {
        return numeroDos;
    }

    public void setNumeroDos(int numeroDos) {
        this.numeroDos = numeroDos;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
    
   
    public int multiplicar()
    {
        
       this.resultado = this.numeroUno * this.numeroDos;  
        return this.resultado; 
    
    }
  
    
    public int sumar(){
    
        this.resultado = this.numeroUno + this.numeroDos;  
        return this.resultado; 
        
    }
    public int restar(){
    this.resultado = this.numeroUno - this.numeroDos;  
        return this.resultado;
    }
        public int dividir(){
    
     if(numeroDos!=0){
     this.resultado = this.numeroUno / this.numeroDos;  
     return this.resultado; 
     }else{
         JOptionPane.showMessageDialog(null, "No se puede dividir entre 0");
         return 0;
     }
        


    }
}

     
        