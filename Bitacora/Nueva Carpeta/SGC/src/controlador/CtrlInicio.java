package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Login;
import modelo.MetodoIniciar;
import vista.InicioUsuario;


public class CtrlInicio implements ActionListener{
    
    private InicioUsuario vistaI;
    private Login mod;
    private MetodoIniciar modI;
    
    public void eventos(){
    
    vistaI.btnEnviar.addActionListener(this);
    
    }
    
    public CtrlInicio(InicioUsuario vistaI, Login mod, MetodoIniciar modI){
    
        this.vistaI=vistaI;
        this.mod=mod;
        this.modI=modI;
        eventos();
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        Object evt = e.getSource();
        
        if (evt.equals(vistaI.btnEnviar)) {
            
            char p [] = vistaI.txtPassword.getPassword();
            String pass = new String(p);
            
            if (vistaI.txtUsuario.getText().isEmpty() || pass.isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "debe ingresar usuario y password","erroren la operacion", JOptionPane.ERROR_MESSAGE);
                
            }else{
                    String usuario=vistaI.txtUsuario.getText();
                    String password=vistaI.txtPassword.getText();
                    
                    ArrayList<MetodoIniciar>list;
                    list = modI.ini(usuario, password);
                    
                    if(list.size()>0){
                    
                    
                        
                    }else{
                    JOptionPane.showMessageDialog(null, "debe ingresar usuario y password","erroren la operacion", JOptionPane.ERROR_MESSAGE);
                    }
                
            }
            
        }
        
    }

    
}
