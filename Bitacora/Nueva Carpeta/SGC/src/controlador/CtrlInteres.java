package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Condominio;
import modelo.ConexionBD;
import modelo.Funcion;
import modelo.Interes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sgc.SGC;
import vista.Catalogo;
import vista.VisInteres;

public class CtrlInteres implements ActionListener, MouseListener, KeyListener {

    private VisInteres vista;
    private Catalogo catalogo;
    ArrayList<Condominio> listaCondo;

    private Interes modelo;

    Funcion permiso;

    DefaultTableModel dm;
    ArrayList<Interes> listainteres;
    ArrayList<Interes> listainteresmod;

    public CtrlInteres() {
        this.vista = new VisInteres();
        this.catalogo = new Catalogo();
        this.catalogo.setVisible(true);
        this.modelo = new Interes();

        catalogo.lblTitulo.setText("Interés");
        CtrlVentana.cambiarVista(catalogo);
        Llenartablainteres(catalogo.tabla);

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtFactor, vista.txtNombreinteres
        };
        Validacion.copiar(components);
        Validacion.pegar(com);

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.catalogo.reportes.addActionListener(this);
        vista.txtNombreinteres.addKeyListener(this);
        vista.txtFactor.addKeyListener(this);

    }

    public void Llenartablainteres(JTable tablaD) {

        listainteres = modelo.listarInteres();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        tablaD.setRowSorter(tr);
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Interes</html>");
        modeloT.addColumn("<html>Nombre de<br> Interes</html>");
        modeloT.addColumn("Factor");

        Object[] columna = new Object[5];

        int numRegistro = listainteres.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listainteres.get(i).getId();
            columna[1] = listainteres.get(i).getNombre();
            columna[2] = listainteres.get(i).getFactor();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {
        
          if (e.getSource() == catalogo.reportes) {

            try {
                ConexionBD con = new ConexionBD();
                Connection conn = con.getConexion();

                JasperReport reporte = null;
                String path = "src\\reportes\\interes.jasper";

                String x = catalogo.txtBuscar.getText();

                Map parametros = new HashMap();
                parametros.put("interes", x);

                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

                JasperPrint jprint = JasperFillManager.fillReport(path, parametros, conn);

                JasperViewer view = new JasperViewer(jprint, false);

                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                view.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(Catalogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == catalogo.btnNuevo) {

            this.vista.btnModificar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(false);

            vista.txtId.setVisible(false);
            vista.txtFactor.setText("");
            vista.txtNombreinteres.setText("");

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {

            if (validar()) {

                modelo.setNombre(vista.txtNombreinteres.getText());
                modelo.setFactor(Double.parseDouble(vista.txtFactor.getText()));

                if (modelo.buscarduplicados(modelo)) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Este registro ya existe ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);

                } else {

                    if (modelo.buscarInactivo(modelo)) {

                        modelo.activarInteres(modelo);

                        UIManager UI = new UIManager();
                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        int botonDialogo = JOptionPane.OK_OPTION;
                        Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "Registro Guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, p);
                        Llenartablainteres(catalogo.tabla);
                        CtrlVentana.cambiarVista(catalogo);
                        limpiar();

                    } else {

                        if (modelo.registrar(modelo)) {

                            UIManager UI = new UIManager();
                            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                            int botonDialogo = JOptionPane.OK_OPTION;
                            Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                            UIManager.put("Button.background", Color.white);
                            UIManager.put("Button.font", Color.blue);
                            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                            UIManager.put("Label.background", Color.blue);
                            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                            JOptionPane.showMessageDialog(null, "Registro Guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, p);
                            Llenartablainteres(catalogo.tabla);
                            CtrlVentana.cambiarVista(catalogo);
                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {

            if (validar()) {

                modelo.setNombre(vista.txtNombreinteres.getText());
                modelo.setFactor(Double.parseDouble(vista.txtFactor.getText()));

                modelo.setId(Integer.parseInt(vista.txtId.getText()));
                int x = modelo.getId();
                if (modelo.buscarduplicados(modelo) && modelo.getId() != x) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Este registro ya existe ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
                } else {
                    if (modelo.buscarInactivo(modelo)) {

                        UIManager UI = new UIManager();
                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        int botonDialogo = JOptionPane.OK_OPTION;
                        Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "No puede colocar un interes que ya existio, si quiere colocar este interes debe registrarlo nuevamente", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);

                    } else {
                        if (modelo.modificar_Interes(modelo)) {

                            UIManager UI = new UIManager();
                            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                            int botonDialogo = JOptionPane.OK_OPTION;
                            Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                            UIManager.put("Button.background", Color.white);
                            UIManager.put("Button.font", Color.blue);
                            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                            UIManager.put("Label.background", Color.blue);
                            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                            JOptionPane.showMessageDialog(null, "Registro Modificado ", "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE, p);

                            Llenartablainteres(catalogo.tabla);
                            CtrlVentana.cambiarVista(catalogo);
                        }

                    }
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            modelo.setId(Integer.parseInt(vista.txtId.getText()));

            modelo.eliminarInteres(modelo);

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            int botonDialogo = JOptionPane.OK_OPTION;
            Icon p = new ImageIcon(getClass().getResource("/img/multiplication-sign.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, "Registro eliminado ", "ELIMINACIÓN SATISFACTORIA", JOptionPane.INFORMATION_MESSAGE, p);
            CtrlVentana.cambiarVista(catalogo);
            Llenartablainteres(catalogo.tabla);
        }
        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    public void limpiar() {

        vista.txtNombreinteres.setText(null);
        vista.txtFactor.setText(null);

    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtFactor.getText().isEmpty()) {

            msj += "El campo factor de interes no puede estar vacio \n";
            resultado = false;
        } else if (Double.parseDouble(vista.txtFactor.getText()) <= 0) {
            msj += "El campo factor de interes tiene que ser mayor que 0 \n";
            resultado = false;
        } else if (Double.parseDouble(vista.txtFactor.getText()) > 100) {
            msj += "El campo factor de interes debe ser menor o igual a 100 \n";
            resultado = false;
        }

        if (vista.txtNombreinteres.getText().isEmpty()) {

            msj += "El campo nombre de interes no puede estar vacio \n";
            resultado = false;
        }

        if (!resultado) {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            int botonDialogo = JOptionPane.OK_OPTION;
            Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, msj, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        modelo.setId(Integer.parseInt(String.valueOf(dato)));

        modelo.buscarInteres(modelo);

        vista.btnEliminar.setEnabled(true);
        vista.btnGuardar.setEnabled(false);
        vista.btnModificar.setEnabled(true);

        vista.txtFactor.setText(String.valueOf(modelo.getFactor()));
        vista.txtNombreinteres.setText(modelo.getNombre());
        vista.txtId.setText(dato);
        vista.txtId.setVisible(false);

        CtrlVentana.cambiarVista(vista);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.txtFactor) {
            Validacion.limite(e, vista.txtFactor.getText(), 4);
            Validacion.Espacio(e);
            Validacion.soloUnPunto(e, vista.txtFactor.getText());
        }
        if (e.getSource() == vista.txtNombreinteres) {
            Validacion.limite(e, vista.txtNombreinteres.getText(), 100);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        } else {

        }
    }

}
