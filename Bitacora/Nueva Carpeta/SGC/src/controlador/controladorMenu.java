package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Funcion;
import sgc.SGC;
import vista.PantallaPrincipal;

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

    public controladorMenu() {
        SGC.panta = new PantallaPrincipal();
        vista = SGC.panta;
        filtrarMenu();

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
        if (e.getSource() == vista.jBanco) {
            conban = new controladorBanco();
        }
        if (e.getSource() == vista.jCategoria) {
            cataga = new controladorCategoriaGastos();
        }
        if (e.getSource() == vista.jComunicados) {
            controcom = new controladorComunicados();
        }
        if (e.getSource() == vista.jConcepto) {
            conco = new controladorConceptoGasto();
        }
        if (e.getSource() == vista.jCondominio) {
            concondo = new controladorCondominio();
        }
        if (e.getSource() == vista.jCuenta) {
            concu = new controladorCuenta();
        }
        if (e.getSource() == vista.jCuotas) {
            controce = new controladorCuotasEspeciales();
        }
        if (e.getSource() == vista.jFondo) {
            controfon = new controladorFondo();
        }
        if (e.getSource() == vista.jFormaPago) {
            controfor = new controladorForma_pago();
        }
        if (e.getSource() == vista.jGastos) {
            controgc = new controladorGastoComun();
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
        if (e.getSource() == vista.jProveedores) {
            conprov = new controladorProveedores();
        }
        if (e.getSource() == vista.jResponsable) {
            ctrlr = new CtrlResponsable();
        }
        if (e.getSource() == vista.jSancion) {
            controsan = new controladorSancion();
        }
        if (e.getSource() == vista.jUnidades) {
            controuni = new controladorUnidades();
        }
        if (e.getSource() == vista.jTipo) {
            controti = new CtrlTipoUsuario();
        }
        
        //Procesos
        if (e.getSource() == vista.pCuentasporCobrar) {
            controcpc = new controladorCuentasPorCobrar();
        }
        if (e.getSource() == vista.pCuentapagar) {
            ctrlCuentaP = new controladorCuenta_Pagar();
        }
        if (e.getSource() == vista.pVisitas) {
            contVisita = new controladorRegistroVisita();
        }
        if (e.getSource() == vista.pCuotas) {
            controPagarCE = new controladorPagarCuotasEsp();
        }
        if (e.getSource() == vista.pCerrarMes) {
            controc = new controladorCerrarMes();
        }
    }

    private void filtrarMenu() {
        // Primero se vacían todos los menú
        vista.menuArchivo.removeAll();
        vista.menuProceso.removeAll();
        vista.menuReporte.removeAll();
        vista.menuAyudas.removeAll();
        vista.menuPerfil.removeAll();

        // Se consulta cada función del usuario actual y se añaden las opciones al menú correspondientes
        for (Funcion funcionesX : SGC.usuarioActual.getTipoU().getFunciones()) {
            if ("Banco".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jBanco);
            }
            if ("Categoria Gastos".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jCategoria);
            }
            if ("Concepto Gastos".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jConcepto);
            }
            if ("Condominio".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jCondominio);
            }
            if ("Cuenta".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jCuenta);
            }
            if ("Gestionar Usuario".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jUsuario);
            }
            if ("Intereses".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jInteres);
            }
            if ("Propietarios".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jPropietarios);
            }
            if ("Proveedores".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jProveedores);
            }
            if ("Responsables".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jResponsable);
            }
            if ("Tipo de usuario".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jTipo);
            }
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
