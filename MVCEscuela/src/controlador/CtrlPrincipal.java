package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConsultaPersona;
import modelo.Persona;
import vista.Principal;
import vista.VistaPersona;

public class CtrlPrincipal implements ActionListener {
    /*declaramos variables de tipo objeto */
    private Principal vista;//
    private CtrlPersona ctrlp;//
    
    private Persona mod = new Persona();//
    private ConsultaPersona modC = new ConsultaPersona();
    private VistaPersona vistap = new VistaPersona();//
    /*creamos contructor con 1 parametro*/
    public CtrlPrincipal(Principal vista) {
        this.ctrlp = new CtrlPersona(mod, modC, vistap);
        /*igualamos las propiedades*/
        this.vista = vista;
    }

    /*creamos metodo que inicia la vista*/
    public void iniciar() {
        vista.setTitle("Persona");
        vista.setLocationRelativeTo(null);
    }

    @Override

    /* recibe el evento */
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == vista.getItemPersona()) {
            
            vistap.setVisible(true);
            ctrlp.iniciar();

            vista.setVisible(false);

        }

    }
}
