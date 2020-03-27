package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.UsuarioL;
import modelo.metodosUsuario;
import vista.InicioUsuario;
import vista.Principal;


public class CtrlUsuarioL implements ActionListener{
    
    private InicioUsuario vistaU;
    private metodosUsuario modelo =  new metodosUsuario();
    
    Principal pp = new Principal();
    
    public void eventos(){
    
        vistaU.btnEnviar.addActionListener(this);
        vistaU.btnSalir.addActionListener(this);
        vistaU.checkViewPass.addActionListener(this);
        
            }
    
    public CtrlUsuarioL(InicioUsuario vistau){
    
        this.vistaU=vistau;
        eventos();
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Object evt = e.getSource();
        
        if(evt.equals(vistaU.btnEnviar)){
        
            char p[ ] = vistaU.txtpassword.getPassword();
            String pass = new String(p);
            
            if (vistaU.txtUsuario.getText().isEmpty() || pass.isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "Debe Digitar un Usuario y una contrasenia", "Error en la operacion", JOptionPane.ERROR_MESSAGE);
                
            }else{
                
                String user=vistaU.txtUsuario.getText();
                
                
                ArrayList<UsuarioL> list;
                list = modelo.login(user, pass);
                
                if (list.size()>0) {
                    
                           JOptionPane.showConfirmDialog(null, "Bienvenido al sistema");
                           vistaU.dispose();
                           pp.setVisible(true);
                    
                }else{
                    
                JOptionPane.showConfirmDialog(null, "Acceso Denegado", "Error",JOptionPane.ERROR_MESSAGE);
                
                }
                
                
            }
            
        }else if(evt.equals(evt.equals(vistaU.btnSalir))){
        
                   int confirmar = JOptionPane.showConfirmDialog(null, "seguro desea salir?");
                   
                   if (confirmar==JOptionPane.YES_OPTION) {
                       
                       System.exit(0);
                                       
            }
        
        }else if(evt.equals(vistaU.checkViewPass)){
        
            if(vistaU.checkViewPass.isSelected()){
            
                vistaU.txtpassword.setEchoChar((char) 0);
                
            }else{
            
                vistaU.txtpassword.setEchoChar('*');//coloca * en la contrasenha;
                
            }
            
        }
        
    }
    
}
