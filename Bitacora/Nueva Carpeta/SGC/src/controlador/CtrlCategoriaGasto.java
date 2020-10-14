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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;
import modelo.ConexionBD;
import modelo.Funcion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sgc.SGC;
import vista.Catalogo;
import vista.VisCategoriaGasto;

public class CtrlCategoriaGasto implements ActionListener, MouseListener, KeyListener, WindowListener {

    private Catalogo catalogo;

    private VisCategoriaGasto vista;
    private CategoriaGasto modelo;
    DefaultTableModel dm;
    Funcion permiso;
    ArrayList<CategoriaGasto> listaCatGas;

    public CtrlCategoriaGasto() {

        this.catalogo = new Catalogo();
        this.vista = new VisCategoriaGasto();
        this.modelo = new CategoriaGasto();

        catalogo.lblTitulo.setText("Categoría Gastos");
        llenarTabla(catalogo.tabla);

        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }
        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.reportes.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.vista.txtnombre.addKeyListener(this);
        this.vista.txtdescripcion.addKeyListener(this);

        CtrlVentana.cambiarVista(catalogo);
    }

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == catalogo.reportes) {

            try {
                ConexionBD con = new ConexionBD();
                Connection conn = con.getConexion();

                JasperReport reporte = null;
                String path = "src\\reportes\\categoria_gasto.jasper";

                
               String x = catalogo.txtBuscar.getText();
               
                Map parametros = new HashMap();
               parametros.put("categoria", x);
             

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

            this.vista.btnGuardar.setEnabled(true);
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.txtId.setVisible(false);

            vista.txtId.setText("");
            vista.txtnombre.setText("");
            vista.txtdescripcion.setText("");

            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {

            if (validar()) {

                registrar();
            }
        }

        if (e.getSource() == vista.btnEliminar) {

            eliminar();
        }

        if (e.getSource() == vista.btnModificar) {

            if (validar()) {

                modificar();
            }
        }

        if (e.getSource() == vista.btnLimpiar) {

            vista.txtnombre.setText("");
            vista.txtdescripcion.setText("");

        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // primero, obtengo la fila seleccionada
        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }

        modelo.setNombre(String.valueOf(dato));

        modelo.buscar(modelo);

        vista.txtnombre.setText(modelo.getNombre());

        vista.txtdescripcion.setText(modelo.getDescripcion());
        vista.txtId.setText(Integer.toString(modelo.getId()));

        vista.btnGuardar.setEnabled(false);
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

    public void limpiar() {

        vista.txtnombre.setText(null);
        vista.txtdescripcion.setText(null);

    }

    public void llenarTabla(JTable tablaD) {

        listaCatGas = modelo.listar();
        
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

        modeloT.addColumn("Nombre");
        modeloT.addColumn("Descripción");

        Object[] columna = new Object[2];

        int numRegistro = listaCatGas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCatGas.get(i).getNombre();
            columna[1] = listaCatGas.get(i).getDescripcion();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);

    }

    private void modificar() {

        modelo.setNombre(vista.txtnombre.getText());
        modelo.setDescripcion(vista.txtdescripcion.getText());
        modelo.setId(Integer.parseInt(vista.txtId.getText()));

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

            JOptionPane.showMessageDialog(null, "No puede colocar el nombre de una categoria que ya existio, si quiere colocar este nombre debe registrarlo nuevamente", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);

        } else {

            if (modelo.modificar(modelo)) {

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

                JOptionPane.showMessageDialog(null, "Registro modificado", "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE, p);
                llenarTabla(catalogo.tabla);
                CtrlVentana.cambiarVista(catalogo);

            } else {

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

                JOptionPane.showMessageDialog(null, "Este registro ya existe", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
            }
        }
    }

    private void eliminar() {

        modelo.setId(Integer.parseInt(vista.txtId.getText()));

        if (modelo.Buscarcon(modelo)) {

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

            JOptionPane.showMessageDialog(null, "No se puede eliminar si tiene conceptos asignados", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE, p);

        } else {

            if (modelo.eliminar(modelo)) {

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

                JOptionPane.showMessageDialog(null, "Registro Eliminado", "ELIMINACIÓN SATISFACTORIA", JOptionPane.INFORMATION_MESSAGE, p);
                llenarTabla(catalogo.tabla);
                CtrlVentana.cambiarVista(catalogo);

            } else {

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

                JOptionPane.showMessageDialog(null, "Error al Eliminar", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
            }
        }
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == vista.txtnombre) {
            Validacion.soloLetras(ke);
            Validacion.limite(ke, vista.txtnombre.getText(), 120);
        }
        if (ke.getSource() == vista.txtdescripcion) {
            Validacion.limite(ke, vista.txtdescripcion.getText(), 120);
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

    @Override
    public void windowOpened(WindowEvent e) {

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtnombre
        };
        Validacion.copiar(components);
        Validacion.pegar(com);
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {

            if (funcionbtn.getNombre().equals("Responsables")) {

                permiso = funcionbtn;
            }
        }
    }

    private void registrar() {

        modelo.setNombre(vista.txtnombre.getText());
        modelo.setDescripcion(vista.txtdescripcion.getText());

        if (modelo.existe(true)) {

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

            JOptionPane.showMessageDialog(null, "Ya esxiste un registro con ese nombre ", "ERROR", JOptionPane.INFORMATION_MESSAGE, p);

        } else if (modelo.existe(false)) {

            if (modelo.reactivar(modelo)) {

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

                JOptionPane.showMessageDialog(null, "Registro guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, p);
                llenarTabla(catalogo.tabla);
                CtrlVentana.cambiarVista(catalogo);

            } else {

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

                JOptionPane.showMessageDialog(null, "No se pudo registrar", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
            }

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

                JOptionPane.showMessageDialog(null, "Registro guardado", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, p);
                llenarTabla(catalogo.tabla);
                CtrlVentana.cambiarVista(catalogo);

            } else {

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

                JOptionPane.showMessageDialog(null, "No se pudo registrar", "ERROR", JOptionPane.WARNING_MESSAGE, p);
            }
        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtnombre.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtdescripcion.getText().isEmpty()) {

            msj += "El campo Descripcion no puede estar vacío \n";
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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

}
