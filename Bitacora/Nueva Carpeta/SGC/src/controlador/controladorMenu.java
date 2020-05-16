/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.PantallaPrincipal;
import vista.catalogoCondominio;

/**
 *
 * @author rma
 */
public class controladorMenu implements ActionListener{
    
    private controladorCondominio concondo;
    private catalogoCondominio catacon;
    private PantallaPrincipal panta;

    public controladorMenu() {
        this.concondo = new controladorCondominio();
        this.catacon= new catalogoCondominio();
        this.panta = new PantallaPrincipal();
        this.panta.jCondominio.addActionListener(this);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==panta.jCondominio) {
            concondo = new controladorCondominio();
            catacon.setVisible(true);
        }
    }
    
}
