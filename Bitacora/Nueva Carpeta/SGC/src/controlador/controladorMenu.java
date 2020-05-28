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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jCondominio) {
            concondo = new controladorCondominio();
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

    private void filtrarMenu() {
        // Primero se vacía el menú archivo
        vista.menuArchivo.removeAll();
        
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
        }
    }

}
