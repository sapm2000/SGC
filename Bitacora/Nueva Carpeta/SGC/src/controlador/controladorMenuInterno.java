/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Funcion;
import sgc.SGC;
import vista.PantallaPrincipal1;

/**
 *
 * @author rma
 */
public class controladorMenuInterno implements ActionListener {

    private PantallaPrincipal1 vista;
    private controladorAsambleas controa;
    private controladorCuotasEspeciales controce;
    private controladorGastoComun controgc;
    private controladorSancion controsan;
    private controladorUnidades controuni;
    private controladorFondo controfon;
    private controladorComunicados controcom;
    private controladorCuentasPorCobrar controcpc;
    private controladorCuenta_Pagar ctrlCuentaP;
    private CtrlVisita contVisita;
    private controladorPagarCuotasEsp controPagarCE;
    private controladorCerrarMes controc;

    public controladorMenuInterno() {
        SGC.panta1 = new PantallaPrincipal1();
        vista = SGC.panta1;
        filtrarMenu();
        vista.setVisible(true);
        vista.jAsamblea.addActionListener(this);
        vista.jCuotas.addActionListener(this);
        vista.jGastos.addActionListener(this);
        vista.jSancion.addActionListener(this);
        vista.jUnidades.addActionListener(this);
        vista.jFondo.addActionListener(this);
        vista.jComunicados.addActionListener(this);
        vista.pCuentasporCobrar.addActionListener(this);
        vista.pCuentapagar.addActionListener(this);
        vista.pVisitas.addActionListener(this);
        vista.pCuotas.addActionListener(this);
        vista.pCerrarMes.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Maestros
        if (e.getSource() == vista.jAsamblea) {
            controa = new controladorAsambleas();
        }
        if (e.getSource() == vista.jComunicados) {
            controcom = new controladorComunicados();
        }
        if (e.getSource() == vista.jCuotas) {
            controce = new controladorCuotasEspeciales();
        }
        if (e.getSource() == vista.jFondo) {
            controfon = new controladorFondo();
        }
        if (e.getSource() == vista.jGastos) {
            controgc = new controladorGastoComun();
        }
        if (e.getSource() == vista.jSancion) {
            controsan = new controladorSancion();
        }
        if (e.getSource() == vista.jUnidades) {
            controuni = new controladorUnidades();
        }

        //Procesos
        if (e.getSource() == vista.pCuentasporCobrar) {
            controcpc = new controladorCuentasPorCobrar();
        }
        if (e.getSource() == vista.pCuentapagar) {
            ctrlCuentaP = new controladorCuenta_Pagar();
        }
        if (e.getSource() == vista.pVisitas) {
            contVisita = new CtrlVisita();
        }
        if (e.getSource() == vista.pCuotas) {
            controPagarCE = new controladorPagarCuotasEsp();
        }
        if (e.getSource() == vista.pCerrarMes) {
            controc = new controladorCerrarMes();
        }
    }

    private void filtrarMenu() {
        // Primero se vacían los menú
        vista.menuArchivo.removeAll();
        vista.menuProceso.removeAll();

        // Se consulta cada función del usuario actual y se añaden las opciones al menú correspondientes
        for (Funcion funcionesX : SGC.usuarioActual.getTipoU().getFunciones()) {
            if ("Asambleas".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jAsamblea);
            }
            if ("Cuotas especiales".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jCuotas);
            }
            if ("Gastos comunes".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jGastos);
            }
            if ("Sanciones".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jSancion);
            }
            if ("Unidades".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jUnidades);
            }
            if ("Fondo".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jFondo);
            }
            if ("Comunicados".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jComunicados);
            }
            if ("Cuentas por cobrar".equals(funcionesX.getNombre())) {
                vista.menuProceso.add(vista.pCuentasporCobrar);
            }
            if ("Cuentas por pagar".equals(funcionesX.getNombre())) {
                vista.menuProceso.add(vista.pCuentapagar);
            }
            if ("Visitas autorizadas".equals(funcionesX.getNombre())) {
                vista.menuProceso.add(vista.pVisitas);
            }
            if ("Pago de cuotas especiales".equals(funcionesX.getNombre())) {
                vista.menuProceso.add(vista.pCuotas);
            }
            if ("Generar recibo".equals(funcionesX.getNombre())) {
                vista.menuProceso.add(vista.pCerrarMes);
            }
        }
    }

}
