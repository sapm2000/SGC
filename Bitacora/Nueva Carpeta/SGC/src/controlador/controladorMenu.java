/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sgc.SGC;
import vista.PantallaPrincipal;

/**
 *
 * @author rma
 */
public class controladorMenu implements ActionListener{
    
    private controladorCondominio concondo;
    private PantallaPrincipal vista;
    //private catalogoCondominio catacon;

    public controladorMenu() {
        //this.concondo = new controladorCondominio();
        //this.catacon= new catalogoCondominio();
        SGC.panta = new PantallaPrincipal();
        vista = SGC.panta;
        vista.setVisible(true);
        vista.jCondominio.addActionListener(this);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.jCondominio) {
            concondo = new controladorCondominio();
            //catacon.setVisible(true);
        }
    }
    
}
