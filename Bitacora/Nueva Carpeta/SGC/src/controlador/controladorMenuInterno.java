/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sgc.SGC;
import vista.PantallaPrincipal1;

/**
 *
 * @author rma
 */
public class controladorMenuInterno implements ActionListener {

    private PantallaPrincipal1 vista1;
    private controladorAsambleas controa;
    private controladorCuotasEspeciales controce;
    private controladorGastoComun controgc;
    private controladorSancion controsan;
    private controladorUnidades controuni;
    private controladorFondo controfon;
    private controladorComunicados controcom;
    private controladorCuentasPorCobrar controcpc;
    private controladorCuenta_Pagar ctrlCuentaP;
    private controladorRegistroVisita contVisita;
    private controladorPagarCuotasEsp controPagarCE;
    private controladorCerrarMes controc;

    public controladorMenuInterno() {
        SGC.panta1 = new PantallaPrincipal1();
        vista1 = SGC.panta1;
        vista1.setVisible(true);
        vista1.jAsamblea.addActionListener(this);
        vista1.jCuotas.addActionListener(this);
        vista1.jGastos.addActionListener(this);
        vista1.jSancion.addActionListener(this);
        vista1.jUnidades.addActionListener(this);
        vista1.jFondo.addActionListener(this);
        vista1.jComunicados.addActionListener(this);
        vista1.pCuentasporCobrar.addActionListener(this);
        vista1.pCuentapagar.addActionListener(this);
        vista1.pVisitas.addActionListener(this);
        vista1.pCuotas.addActionListener(this);
        vista1.pCerrarMes.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista1.jAsamblea) {
            controa = new controladorAsambleas();
        }

        if (e.getSource() == vista1.jCuotas) {
            controce = new controladorCuotasEspeciales();
        }
        if (e.getSource() == vista1.jGastos) {
            controgc = new controladorGastoComun();
        }
        if (e.getSource() == vista1.jSancion) {
            controsan = new controladorSancion();
        }

        if (e.getSource() == vista1.jUnidades) {
            controuni = new controladorUnidades();
        }
        if (e.getSource() == vista1.jFondo) {
            controfon = new controladorFondo();
        }
        if (e.getSource() == vista1.jComunicados) {
            controcom = new controladorComunicados();
        }

        if (e.getSource() == vista1.pCuentasporCobrar) {
            controcpc = new controladorCuentasPorCobrar();
        }

        if (e.getSource() == vista1.pCuentapagar) {
            ctrlCuentaP = new controladorCuenta_Pagar();
        }

        if (e.getSource() == vista1.pVisitas) {
            contVisita = new controladorRegistroVisita();
        }
        
         if (e.getSource() == vista1.pCuotas) {
            controPagarCE = new controladorPagarCuotasEsp();
        }
          if (e.getSource() == vista1.pCerrarMes) {
            controc = new controladorCerrarMes();
        }
    }

}
