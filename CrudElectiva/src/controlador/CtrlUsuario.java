package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import modelo.CrudUsuario;
import modelo.Usuario;
import vista.GestionarUsuario;

public class CtrlUsuario implements ActionListener, ItemListener{
    
    private Usuario mod;
    private CrudUsuario modC;
    private GestionarUsuario vistaGU;

    //Constructor de inicializacion de variables. Desde la linea 16 a la 26
    
    public CtrlUsuario(Usuario mod, CrudUsuario modC, GestionarUsuario vistaGU){
        //ponesmos atentos las vistas y los botones
        this.mod = mod;
        this.modC = modC;
        this.vistaGU = vistaGU;
        this.vistaGU.btnBuscar.addActionListener(this);
        this.vistaGU.btnGuardar.addActionListener(this);
        this.vistaGU.btnEliminar.addActionListener(this);
        this.vistaGU.btnModificar.addActionListener(this);
        this.vistaGU.btnLimpiar.addActionListener(this);
    }
    //Fin del constructor


    //En este condicional decimos que segun lo que btn escuhe edjecute la accion y contacte al modelo
    @Override
    public void actionPerformed(ActionEvent e){
    
        if (e.getSource() == vistaGU.btnGuardar) {
            
            mod.setId(vistaGU.txtID.getText());
            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());

            
            if (modC.registrar(mod)) {
                
                JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                limpiar();
                       
            }else{
            
                JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR");
                limpiar();
                
            }
        }
            
            if (e.getSource() == vistaGU.btnModificar) {
                      
            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());

                                
            if (modC.modificar(mod)) {
                
                JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
               
                limpiar();
                                
            }else{
            
                JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
                limpiar();
                
            }
            
        }
    
            if (e.getSource() == vistaGU.btnEliminar) {
            
            mod.setId(vistaGU.txtID.getText());
          
            if (modC.eliminar(mod)) {
                
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
                limpiar();

            }else{
            
                JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR");
                limpiar();
                
            }
            
        }
            if (e.getSource() == vistaGU.btnBuscar) {
            
            mod.setId(vistaGU.txtID.getText());

            if (modC.buscar(mod)) {
                
                 vistaGU.txtID.setText(mod.getId());
                vistaGU.txtUsuario.setText(mod.getUsuario());
                vistaGU.jpPassword.setText(mod.getPassword());


            }else{
            
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO REGISTRO");
              
            }
            
        }
            
            if (e.getSource() == vistaGU.btnLimpiar) {
                limpiar();
        }
        
    }
    //para limpiar
        public void limpiar(){
        
            vistaGU.txtID.setText(null);
            vistaGU.txtUsuario.setText(null);
            vistaGU.jpPassword.setText(null);

            
        }
    
             @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
        
}
