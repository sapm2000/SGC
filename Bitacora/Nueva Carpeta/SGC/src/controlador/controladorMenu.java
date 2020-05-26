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
public class controladorMenu implements ActionListener {

    private controladorCondominio concondo;
    private PantallaPrincipal vista;
    private controladorBanco conban;
    private controladorCategoriaGastos cataga;
    private controladorConceptoGasto conco;
    private controladorCuenta concu;
    private controladorProveedores conprov;
    private CtrlTipoUsuario controti;
    private CtrlUsuario ctrl;
    private controladorInteres controin;
    private CtrlPropietario ctrlp;
    private CtrlResponsable ctrlr;
    private controladorForma_pago controfor;

    public controladorMenu() {
        //this.concondo = new controladorCondominio();
        //this.catacon= new catalogoCondominio();
        SGC.panta = new PantallaPrincipal();
        vista = SGC.panta;
        vista.setVisible(true);
        vista.jCondominio.addActionListener(this);
        vista.jBanco.addActionListener(this);
        vista.jCategoria.addActionListener(this);
        vista.jConcepto.addActionListener(this);
        vista.jCuenta.addActionListener(this);
        vista.jProveedores.addActionListener(this);
        vista.jTipo.addActionListener(this);
        vista.jUsuario.addActionListener(this);
        vista.jInteres.addActionListener(this);
        vista.jPropietarios.addActionListener(this);
        vista.jResponsable.addActionListener(this);
        vista.jFormaPago.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jCondominio) {
            concondo = new controladorCondominio();
            //catacon.setVisible(true);
        }
        if (e.getSource() == vista.jBanco) {
            conban = new controladorBanco();
        }

        if (e.getSource() == vista.jCategoria) {
            cataga = new controladorCategoriaGastos();
        }

        if (e.getSource() == vista.jConcepto) {
            conco = new controladorConceptoGasto();
        }

        if (e.getSource() == vista.jCuenta) {
            concu = new controladorCuenta();
        }

        if (e.getSource() == vista.jProveedores) {
            conprov = new controladorProveedores();
        }

        if (e.getSource() == vista.jTipo) {
            controti = new CtrlTipoUsuario();
        }

        if (e.getSource() == vista.jUsuario) {
            ctrl = new CtrlUsuario();
        }
        if (e.getSource() == vista.jInteres) {
            controin = new controladorInteres();
        }

        if (e.getSource() == vista.jPropietarios) {
            ctrlp = new CtrlPropietario();
        }
          if (e.getSource() == vista.jResponsable) {
            ctrlr = new CtrlResponsable();
        }
          if (e.getSource() == vista.jFormaPago) {
            controfor = new controladorForma_pago();
        }
    }

}
