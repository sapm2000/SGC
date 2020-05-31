package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Funcion;
import sgc.SGC;
import vista.PantallaPrincipal;

public class controladorMenu implements ActionListener {

    private PantallaPrincipal vista;
    private Object ctrl;

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
        vista.jTipoUnidad.addActionListener(this);
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
            ctrl = new controladorAsambleas();
        }
        if (e.getSource() == vista.jBanco) {
            ctrl = new controladorBanco();
        }
        if (e.getSource() == vista.jCategoria) {
            ctrl = new controladorCategoriaGastos();
        }
        if (e.getSource() == vista.jComunicados) {
            ctrl = new controladorComunicados();
        }
        if (e.getSource() == vista.jConcepto) {
            ctrl = new controladorConceptoGasto();
        }
        if (e.getSource() == vista.jCondominio) {
            ctrl = new controladorCondominio();
        }
        if (e.getSource() == vista.jCuenta) {
            ctrl = new controladorCuenta();
        }
        if (e.getSource() == vista.jCuotas) {
            ctrl = new controladorCuotasEspeciales();
        }
        if (e.getSource() == vista.jFondo) {
            ctrl = new controladorFondo();
        }
        if (e.getSource() == vista.jFormaPago) {
            ctrl = new controladorForma_pago();
        }
        if (e.getSource() == vista.jGastos) {
            ctrl = new controladorGastoComun();
        }
        if (e.getSource() == vista.jUsuario) {
            ctrl = new CtrlUsuario();
        }
        if (e.getSource() == vista.jInteres) {
            ctrl = new controladorInteres();
        }
        if (e.getSource() == vista.jPropietarios) {
            ctrl = new CtrlPropietario();
        }
        if (e.getSource() == vista.jProveedores) {
            ctrl = new controladorProveedores();
        }
        if (e.getSource() == vista.jResponsable) {
            ctrl = new CtrlResponsable();
        }
        if (e.getSource() == vista.jSancion) {
            ctrl = new controladorSancion();
        }
        if (e.getSource() == vista.jTipoUnidad) {
            ctrl = new CtrlTipoUnidad();
        }
        if (e.getSource() == vista.jTipo) {
            ctrl = new CtrlTipoUsuario();
        }
        if (e.getSource() == vista.jUnidades) {
            ctrl = new controladorUnidades();
        }

        //Procesos
        if (e.getSource() == vista.pCuentasporCobrar) {
            ctrl = new controladorCuentasPorCobrar();
        }
        if (e.getSource() == vista.pCuentapagar) {
            ctrl = new controladorCuenta_Pagar();
        }
        if (e.getSource() == vista.pVisitas) {
            ctrl = new CtrlVisita();
        }
        if (e.getSource() == vista.pCuotas) {
            ctrl = new controladorPagarCuotasEsp();
        }
        if (e.getSource() == vista.pCerrarMes) {
            ctrl = new controladorCerrarMes();
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
            if ("Tipo de unidad".equals(funcionesX.getNombre())) {
                vista.menuArchivo.add(vista.jTipoUnidad);
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
