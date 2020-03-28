
package controlador;
import modelo.MOperadores;
import vista.vistaoperadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Anthony
 */
public class controladorm implements ActionListener{
    
    public vistaoperadores vistaOp;
    public MOperadores mod;

       public controladorm(vistaoperadores vistaOp, MOperadores mod)
    {
        this.vistaOp = vistaOp;
        this.mod = mod;
        this.vistaOp.btnSumar.addActionListener(this);
        this.vistaOp.btnRestar.addActionListener(this);
        this.vistaOp.btnMultiplicar.addActionListener(this);
        this.vistaOp.btnDividir.addActionListener(this);
    
    
    }
       public void iniciar()
       {
               
       
       }
       
      

    @Override
    public void actionPerformed(ActionEvent e) {
         
        //con los controladores podemos fijarnos en el polimorfismo. dependiendo de la accion que escuche cada boton buscara en el modelo y ejecutara dicha accion
          if (e.getSource() == vistaOp.btnSumar) {
        mod.setNumeroUno(Integer.parseInt(vistaOp.txtNumero1.getText()));
        mod.setNumeroDos(Integer.parseInt(vistaOp.txtNumero2.getText()));
        mod.sumar();
        vistaOp.txtResultado.setText(String.valueOf(mod.getResultado()));
          }
           if (e.getSource() == vistaOp.btnRestar) {
        mod.setNumeroUno(Integer.parseInt(vistaOp.txtNumero1.getText()));
        mod.setNumeroDos(Integer.parseInt(vistaOp.txtNumero2.getText()));
        mod.restar();
        vistaOp.txtResultado.setText(String.valueOf(mod.getResultado()));
          }
            if (e.getSource() == vistaOp.btnMultiplicar) {
        mod.setNumeroUno(Integer.parseInt(vistaOp.txtNumero1.getText()));
        mod.setNumeroDos(Integer.parseInt(vistaOp.txtNumero2.getText()));
        mod.multiplicar();
        vistaOp.txtResultado.setText(String.valueOf(mod.getResultado()));
          }
         if (e.getSource() == vistaOp.btnDividir) {
        mod.setNumeroUno(Integer.parseInt(vistaOp.txtNumero1.getText()));
        mod.setNumeroDos(Integer.parseInt(vistaOp.txtNumero2.getText()));
        mod.dividir();
        vistaOp.txtResultado.setText(String.valueOf(mod.getResultado()));
          }
          
    }
     
}