package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
/*aqui llamamos los Modelos, y las vistas*/
import modelo.ConsultaPersona;
import modelo.Persona;
import vista.VistaPersona;

/**
 *
 * @author Anthony
 */
/*aqui con el actionlistener  especificamos los botones que van a ser escuchados. */
public class CtrlPersona implements ActionListener{
    /*inicializamos unas variables para el llamado del los botones*/
    private Persona mod;
    private ConsultaPersona modC;
    private VistaPersona vistaP;
    
    
    
    
    public CtrlPersona(Persona mod, ConsultaPersona modC, VistaPersona vistaP){
        /*le damos las acciones de escuchar a los botones*/
        this.mod = mod;
        this.modC = modC;
        this.vistaP = vistaP;
        this.vistaP.btnBuscar.addActionListener(this);
        this.vistaP.btnGuardar.addActionListener(this);
        this.vistaP.btnModificar.addActionListener(this);
        this.vistaP.btnEliminar.addActionListener(this);
        this.vistaP.btnLimpiar.addActionListener(this);
      

        
                
    }
    /*iniciamos la vista*/
    public void iniciar(){
    
        vistaP.setTitle("Persona");
        /*este txt id esta oculto en la vista lo utilizo como utoincrement en la base de datos*/
        vistaP.txtId.setVisible(false);
        vistaP.setLocationRelativeTo(null);
        
        }
    
    @Override
    public void actionPerformed(ActionEvent e){
         /*un constructor de acciones realizadas llamamos la accion del evento como e*/
        
         /*aqui decimos que cuando el evento obtenga la accion de boton guardar envie al modelo guardar los datos obtenidos en los campos*/
        if (e.getSource() == vistaP.btnGuardar) {
            
            mod.setClave(vistaP.txtClave.getText());
            mod.setNombre(vistaP.txtNombre.getText());
            mod.setDomicilio(vistaP.txtDomicilio.getText());
            mod.setTelefono(vistaP.txtTelefono.getText());
            mod.setCorreo_electronico(vistaP.txtCorreo_electronico.getText());
            mod.setFecha_nacimiento(vistaP.txtFecha_nacimiento.getText());
            mod.setGenero(vistaP.cbxGenero.getSelectedItem().toString());
            mod.setNacionalidad(vistaP.cbxNacionalidad.getSelectedItem().toString());
 
            
        
            /* aqui consultamos al modelo si el registro fue realizado o no para enviar una ventana emergente dependiendo del caso*/
            if (modC.registrar(mod)) {
                
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Error al Guardar");
                limpiar();
              
            }
             
        }
        /* aqui decimos que cuando el evento obtenga la opcion del boton modificar.obtenga los datos de los campos envie al modelos 
        y el modelo modifica en la base de datos*/
        if (e.getSource() == vistaP.btnModificar) {
            
            mod.setId(Integer.parseInt(vistaP.txtId.getText()));
            mod.setClave(vistaP.txtClave.getText());
            mod.setNombre(vistaP.txtNombre.getText());
            mod.setDomicilio(vistaP.txtDomicilio.getText());
            mod.setTelefono(vistaP.txtTelefono.getText());
            mod.setCorreo_electronico(vistaP.txtCorreo_electronico.getText());
            mod.setFecha_nacimiento(vistaP.txtFecha_nacimiento.getText());
            mod.setGenero(vistaP.cbxGenero.getSelectedItem().toString());
            mod.setNacionalidad(vistaP.cbxNacionalidad.getSelectedItem().toString());
            
            /*si la modificacion fue exitosa o no mandamos una ventana con un mensaje segun sea el caso.*/
            if (modC.modificar(mod)) {
                
                JOptionPane.showMessageDialog(null, "Registro modificado");
                limpiar();
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
            
        }
        /*decimos que los datos obtenidos por elevento  del boton eliminar  los enviamos al modelo y si coinciden eliminamos de la base de datos el ID*/
        if (e.getSource() == vistaP.btnEliminar) {
            /*el Id esta oculto en el sistema se auto incrementa solo*/
            mod.setId(Integer.parseInt(vistaP.txtId.getText()));
           
            /*enviamos una ventana emergente diciendo si los datos fueron eliminados correctamente o existe un error*/
            if (modC.eliminar(mod)) {
                
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
            
        }
        
        /*aqui decimos que si la accion del boton fue buscar. consultamos con el modelo la cedula obtenida de la vista.
        para traer la informacion de la base de datos a la vista*/
        if (e.getSource() == vistaP.btnBuscar) {
            
            mod.setClave(vistaP.txtClave.getText());
           
    
            if (modC.buscar(mod)) {
                
         
                
                vistaP.txtId.setText(String.valueOf(mod.getId()));
                vistaP.txtClave.setText(mod.getClave());
                vistaP.txtNombre.setText(mod.getNombre());
                vistaP.txtDomicilio.setText(mod.getDomicilio());
                vistaP.txtTelefono.setText(mod.getTelefono());
                vistaP.txtCorreo_electronico.setText(mod.getCorreo_electronico());
                vistaP.txtFecha_nacimiento.setText(mod.getFecha_nacimiento());
                vistaP.cbxGenero.setSelectedItem(mod.getGenero());
                vistaP.cbxNacionalidad.setSelectedItem(mod.getNacionalidad());
          
                
               
                
                
    
            }else{
                
                
                JOptionPane.showMessageDialog(null, "No se encontro registro");
                limpiar();
            }
            
        }
       if (e.getSource() == vistaP.btnLimpiar) {
       
           limpiar();
          
       } 
       
      
       
       
    }
    

    /*constructor para el boton limpiar. donde nos limpia los datos del los campos de la vista*/
    public void limpiar(){
    
    vistaP.txtId.setText(null);
    vistaP.txtClave.setText(null);
    vistaP.txtNombre.setText(null);
    vistaP.txtDomicilio.setText(null);
    vistaP.txtTelefono.setText(null);
    vistaP.txtCorreo_electronico.setText(null);
    vistaP.txtFecha_nacimiento.setText(null);
    vistaP.cbxGenero.setSelectedIndex(0);
    vistaP.cbxNacionalidad.setSelectedIndex(0);
    
        }
    
    /**
     *
    
    public void habilitar(){
    
        vistaP.txtClave.setEnabled(true);
        vistaP.txtNombre.setEnabled(false);
        vistaP.txtDomicilio.setEnabled(false);
        vistaP.txtTelefono.setEnabled(false);
        vistaP.txtCorreo_electronico.setEnabled(false);
        vistaP.txtFecha_nacimiento.setEnabled(false);
        vistaP.txtGenero.setEnabled(false);
        
        vistaP.btnBuscar.setEnabled(false);
    
        } */
}
