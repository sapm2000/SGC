package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.CrudUsuario;
import modelo.Usuario;
import vista.GestionarUsuario;

public class CtrlUsuario implements ActionListener, KeyListener, ItemListener{
    
    private Usuario mod;
    private CrudUsuario modC;
    private GestionarUsuario vistaGU;


    
    //Constructor de inicializacion de variables. Desde la linea 16 a la 26
    
    public CtrlUsuario(Usuario mod, CrudUsuario modC, GestionarUsuario vistaGU){
    
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

  
    
    
    public void iniciar(){
  
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
    
        if (e.getSource() == vistaGU.btnGuardar) {
            
            mod.setCedula(vistaGU.txtCedula.getText());
            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());
            mod.setNombre(vistaGU.txtNombre.getText());
            mod.setApellido(vistaGU.txtApellido.getText());
            mod.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
            
            
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
            mod.setNombre(vistaGU.txtNombre.getText());
            mod.setApellido(vistaGU.txtApellido.getText());
            mod.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
            

                   
            if (modC.modificar(mod)) {
                
                JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
               
                limpiar();
                
            }else{
            
                JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
                limpiar();
                
            }
            
        }
    
            if (e.getSource() == vistaGU.btnEliminar) {
            
            mod.setCedula(vistaGU.txtCedula.getText());
            
            
            if (modC.eliminar(mod)) {
                
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
                limpiar();
                
            }else{
            
                JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR");
                limpiar();
                
            }
            
        }
            if (e.getSource() == vistaGU.btnBuscar) {
            
            mod.setCedula(vistaGU.txtCedula.getText());
            
            
            if (modC.buscar(mod)) {
                
                vistaGU.txtCedula.setText(mod.getCedula());
                vistaGU.txtUsuario.setText(mod.getUsuario());
                vistaGU.jpPassword.setText(mod.getPassword());
                vistaGU.txtNombre.setText(mod.getNombre());
                vistaGU.txtApellido.setText(mod.getApellido());
                vistaGU.cbxTipo.setSelectedItem(mod.getTipo());
                
            }else{
            
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO REGISTRO");
                limpiar();
                
            }
            
        }
            
            if (e.getSource() == vistaGU.btnLimpiar) {
                limpiar();
        }
        
    }
    
     
    public void limpiar(){
        
            vistaGU.txtCedula.setText(null);
            vistaGU.txtUsuario.setText(null);
            vistaGU.jpPassword.setText(null);
            vistaGU.txtNombre.setText(null);
            vistaGU.txtApellido.setText(null);
            vistaGU.cbxTipo.setToolTipText(null);
            
        }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        
}
