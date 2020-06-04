package controlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import modelo.Funcion;
import sgc.SGC;
import vista.Inicio;
import vista.PantallaPrincipal;
import vista.Ventana;


public class CtrlVentana implements ActionListener {

    public static PantallaPrincipal marco;
    public static JPanel panelPrincipal;
    public static Ventana ventana;

    private Inicio vista;
    private Object ctrl;

    public CtrlVentana() {
        ventana = new Ventana();
        marco = new PantallaPrincipal();
        vista = new Inicio();

        
        
        ventana.getContentPane().removeAll();
        ventana.setSize(1366, 740);

        if (panelPrincipal != null) {
            marco.remove(panelPrincipal);
        }

        marco.setBounds(0, 0, 1366, 710);
        ventana.getContentPane().add(marco);
        vista.setBounds(10, 10, 1346, 652);
        marco.panel.add(vista);
        panelPrincipal = vista;
        ventana.repaint();
        ventana.validate();

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        SGC.panta = marco;
        filtrarMenu();

        marco.btnSalir.addActionListener(this);
        marco.btnMinimizar.addActionListener(this);
        ventana.jCondominio.addActionListener(this);
        ventana.jBanco.addActionListener(this);
        ventana.jCategoria.addActionListener(this);
        ventana.jConcepto.addActionListener(this);
        ventana.jCuenta.addActionListener(this);
        ventana.jProveedores.addActionListener(this);
        ventana.jTipoUnidad.addActionListener(this);
        ventana.jTipo.addActionListener(this);
        ventana.jUsuario.addActionListener(this);
        ventana.jInteres.addActionListener(this);
        ventana.jPropietarios.addActionListener(this);
        ventana.jResponsable.addActionListener(this);
        ventana.jFormaPago.addActionListener(this);
        ventana.jAsamblea.addActionListener(this);
        ventana.jGasto.addActionListener(this);
        ventana.jSancion.addActionListener(this);
        ventana.jUnidades.addActionListener(this);
        ventana.jFondo.addActionListener(this);
        ventana.jComunicados.addActionListener(this);
        ventana.pCuentasporCobrar.addActionListener(this);
        ventana.pCuentapagar.addActionListener(this);
        ventana.pVisitas.addActionListener(this);
        ventana.pCuotas.addActionListener(this);
        ventana.pCerrarMes.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == marco.btnMinimizar) {
            ventana.setState(JFrame.ICONIFIED);
        }

        if (e.getSource() == marco.btnSalir) {
            try {
                UIManager UI = new UIManager();

                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                int botonDialogo = JOptionPane.YES_NO_OPTION;
                Icon p = new ImageIcon(getClass().getResource("/img/pregunta.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                int result = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", "Salir", botonDialogo, JOptionPane.INFORMATION_MESSAGE, p);

                if (result == 0) {
                    System.exit(0);
                }

            } catch (Exception ex) {
                System.err.println(ex);
            }
        }

        //Maestros
        if (e.getSource() == ventana.jAsamblea) {
            ctrl = new CtrlAsamblea();
        }
        if (e.getSource() == ventana.jBanco) {
            ctrl = new CtrlBanco();
        }
        if (e.getSource() == ventana.jCategoria) {
            ctrl = new CtrlCategoriaGasto();
        }
        if (e.getSource() == ventana.jComunicados) {
            ctrl = new CtrlComunicado();
        }
        if (e.getSource() == ventana.jConcepto) {
            ctrl = new CtrlConceptoGasto();
        }
        if (e.getSource() == ventana.jCondominio) {
            ctrl = new CtrlCondominio();
        }
        if (e.getSource() == ventana.jCuenta) {
            ctrl = new CtrlCuenta();
        }
        if (e.getSource() == ventana.jGasto) {
            ctrl = new CtrlGasto();
        }
        if (e.getSource() == ventana.jFondo) {
            ctrl = new CtrlFondo();
        }
        if (e.getSource() == ventana.jFormaPago) {
            ctrl = new CtrlFormaPago();
        }
        if (e.getSource() == ventana.jUsuario) {
            ctrl = new CtrlUsuario();
        }
        if (e.getSource() == ventana.jInteres) {
            ctrl = new CtrlInteres();
        }
        if (e.getSource() == ventana.jPropietarios) {
            ctrl = new CtrlPropietario();
        }
        if (e.getSource() == ventana.jProveedores) {
            ctrl = new CtrlProveedor();
        }
        if (e.getSource() == ventana.jResponsable) {
            ctrl = new CtrlResponsable();
        }
        if (e.getSource() == ventana.jSancion) {
            ctrl = new CtrlSancion();
        }
        if (e.getSource() == ventana.jTipoUnidad) {
            ctrl = new CtrlTipoUnidad();
        }
        if (e.getSource() == ventana.jTipo) {
            ctrl = new CtrlTipoUsuario();
        }
        if (e.getSource() == ventana.jUnidades) {
            ctrl = new CtrlUnidad();
        }

        //Procesos
        if (e.getSource() == ventana.pCuentasporCobrar) {
            ctrl = new CtrlCuentaPorCobrar();
        }
        if (e.getSource() == ventana.pCuentapagar) {
            ctrl = new CtrlCuentaPagar();
        }
        if (e.getSource() == ventana.pVisitas) {
            ctrl = new CtrlVisita();
        }
        if (e.getSource() == ventana.pCuotas) {
            ctrl = new CtrlPagarGasto();
        }
        if (e.getSource() == ventana.pCerrarMes) {
            ctrl = new CtrlCerrarMes();
        }
    }

    public static void cambiarVista(JPanel vista) {

        if (panelPrincipal != null) {
            marco.panel.remove(panelPrincipal);
        }

        marco.setBounds(0, 0, 1366, 710);
        ventana.getContentPane().add(marco);
        vista.setBounds(10, 10, 1346, 652);
        marco.panel.add(vista);
        panelPrincipal = vista;
        ventana.repaint();
        ventana.validate();
        
        
        //vista.setSize(1346, 740);
        //vista.setBounds(10, 10, 1346, 740);
        
        
        
        
        //marco.panel.add(vista);
        //panelPrincipal = vista;
        //ventana.repaint();
        //ventana.validate();
        //centreWindow(panelPrincipal); 
    }

     public static void centreWindow(JPanel frame) {
       Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
       
       int panelX = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
       int panelY = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
      
       panelPrincipal.setLocation(panelX, panelY);
}
    private void filtrarMenu() {
        // Primero se vacían todos los menú
        ventana.menuArchivo.removeAll();
        ventana.menuProceso.removeAll();
        ventana.menuReporte.removeAll();
        ventana.menuAyudas.removeAll();
        ventana.menuPerfil.removeAll();

        // Se consulta cada función del usuario actual y se añaden las opciones al menú correspondientes
        for (Funcion funcionesX : SGC.usuarioActual.getTipoU().getFunciones()) {
            if ("Banco".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jBanco);
            }
            if ("Categoria Gastos".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jCategoria);
            }
            if ("Concepto Gastos".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jConcepto);
            }
            if ("Condominio".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jCondominio);
            }
            if ("Cuenta".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jCuenta);
            }
            if ("Gestionar Usuario".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jUsuario);
            }
            if ("Intereses".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jInteres);
            }
            if ("Propietarios".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jPropietarios);
            }
            if ("Proveedores".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jProveedores);
            }
            if ("Responsables".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jResponsable);
            }
            if ("Tipo de unidad".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jTipoUnidad);
            }
            if ("Tipo de usuario".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jTipo);
            }
            if ("Asambleas".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jAsamblea);
            }
            if ("Gasto".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jGasto);
            }
            if ("Sanciones".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jSancion);
            }
            if ("Unidades".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jUnidades);
            }
            if ("Fondo".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jFondo);
            }
            if ("Comunicados".equals(funcionesX.getNombre())) {
                ventana.menuArchivo.add(ventana.jComunicados);
            }
            if ("Cuentas por cobrar".equals(funcionesX.getNombre())) {
                ventana.menuProceso.add(ventana.pCuentasporCobrar);
            }
            if ("Cuentas por pagar".equals(funcionesX.getNombre())) {
                ventana.menuProceso.add(ventana.pCuentapagar);
            }
            if ("Visitas autorizadas".equals(funcionesX.getNombre())) {
                ventana.menuProceso.add(ventana.pVisitas);
            }
            if ("Pago de cuotas especiales".equals(funcionesX.getNombre())) {
                ventana.menuProceso.add(ventana.pCuotas);
            }
            if ("Generar recibo".equals(funcionesX.getNombre())) {
                ventana.menuProceso.add(ventana.pCerrarMes);
            }
        }
    }
}

   
