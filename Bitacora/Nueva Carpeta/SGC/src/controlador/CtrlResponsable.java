package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
import modelo.ConexionBD;
import modelo.Funcion;
import modelo.Responsable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sgc.SGC;
import vista.Catalogo;

public class CtrlResponsable implements ActionListener, MouseListener, KeyListener, ItemListener, FocusListener {

    private Catalogo catalogo;
    private vista.VisResponsable vista;
    private modelo.Responsable modelo;

    private ArrayList<Responsable> lista;
    DefaultTableModel dm;
    private Funcion permiso;

    int fila;

    public CtrlResponsable() {
        catalogo = new Catalogo();
        modelo = new modelo.Responsable();
        vista = new vista.VisResponsable();
        catalogo.lblTitulo.setText("Responsable");

        catalogo.btnNuevo.addActionListener(this);
        catalogo.tabla.addMouseListener(this);
        catalogo.txtBuscar.addKeyListener(this);
        catalogo.reportes.addActionListener(this);

        llenarTabla();
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        CtrlVentana.cambiarVista(catalogo);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
         if (e.getSource() == catalogo.reportes) {

            try {
                ConexionBD con = new ConexionBD();
                Connection conn = con.getConexion();

                JasperReport reporte = null;
                String path = "src\\reportes\\responsable.jasper";

                String x = catalogo.txtBuscar.getText();

                Map parametros = new HashMap();
                parametros.put("responsable", x);

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

            

            vista.btnGuardar.setEnabled(true);
            vista.btnModificar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);

            vista.btnGuardar.addActionListener(this);
            vista.btnLimpiar.addActionListener(this);
            vista.btnSalir.addActionListener(this);
            vista.txtCedula.addKeyListener(this);
            vista.txtPnombre.addKeyListener(this);
            vista.txtSnombre.addKeyListener(this);
            vista.txtPapellido.addKeyListener(this);
            vista.txtSapellido.addKeyListener(this);
            vista.txtTelefono.addKeyListener(this);
            vista.txtCorreo.addKeyListener(this);
            vista.txtCorreo.addFocusListener(this);
            
            CtrlVentana.cambiarVista(vista);
            vista.cbxCedula.addItemListener(this);
            stylecombo(vista.cbxCedula);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                String cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();
                modelo.setCedula(cedula);
                modelo.setpNombre(vista.txtPnombre.getText());
                modelo.setsNombre(vista.txtSnombre.getText());
                modelo.setpApellido(vista.txtPapellido.getText());
                modelo.setsApellido(vista.txtSapellido.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setTelefono(vista.txtTelefono.getText());

                if (modelo.existeInactivo()) {

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

                    JOptionPane.showMessageDialog(null, "Este responsable ya existe en la base de datos, se recuperarán los datos para el nuevo registro ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);

                    if (modelo.reactivar()) {

                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        Icon a = new ImageIcon(getClass().getResource("/img/check.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "Registro Guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, a);
                        CtrlVentana.cambiarVista(catalogo);
                        llenarTabla();

                    } else {

                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        Icon b = new ImageIcon(getClass().getResource("/img/warning.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "No se pudo reactivar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, b);
                    }

                } else {

                    if (modelo.existe()) {

                        UIManager UI = new UIManager();
                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        Icon d = new ImageIcon(getClass().getResource("/img/warning.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "Esta persona ya está registrada ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, d);

                    } else {

                        if (modelo.existePersona()) {

                            UIManager UI = new UIManager();
                            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                            Icon d = new ImageIcon(getClass().getResource("/img/warning.png"));
                            UIManager.put("Button.background", Color.white);
                            UIManager.put("Button.font", Color.blue);
                            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                            UIManager.put("Label.background", Color.blue);
                            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                            JOptionPane.showMessageDialog(null, "Esta persona está registrada en la base de datos como propietario, se utilizarán los datos de ese registro ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, d);

                            if (modelo.registrar(true)) {

                                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                                Icon f = new ImageIcon(getClass().getResource("/img/check.png"));
                                UIManager.put("Button.background", Color.white);
                                UIManager.put("Button.font", Color.blue);
                                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                                UIManager.put("Label.background", Color.blue);
                                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                                JOptionPane.showMessageDialog(null, "Registro guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, f);
                                CtrlVentana.cambiarVista(catalogo);
                                llenarTabla();

                            } else {

                                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                                Icon g = new ImageIcon(getClass().getResource("/img/warning.png"));
                                UIManager.put("Button.background", Color.white);
                                UIManager.put("Button.font", Color.blue);
                                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                                UIManager.put("Label.background", Color.blue);
                                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                                JOptionPane.showMessageDialog(null, "Error al registrar", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE, g);

                            }

                        } else {
                            if (modelo.registrar(false)) {

                                UIManager UI = new UIManager();
                                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                                Icon k = new ImageIcon(getClass().getResource("/img/check.png"));
                                UIManager.put("Button.background", Color.white);
                                UIManager.put("Button.font", Color.blue);
                                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                                UIManager.put("Label.background", Color.blue);
                                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                                JOptionPane.showMessageDialog(null, "Registro guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, k);
                                CtrlVentana.cambiarVista(catalogo);
                                llenarTabla();

                            } else {

                                UIManager UI = new UIManager();
                                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                                Icon j = new ImageIcon(getClass().getResource("/img/warning.png"));
                                UIManager.put("Button.background", Color.white);
                                UIManager.put("Button.font", Color.blue);
                                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                                UIManager.put("Label.background", Color.blue);
                                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                                JOptionPane.showMessageDialog(null, "Error al registrar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, j);

                            }
                        }
                    }
                }
            }
        }
        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modelo.setpNombre(vista.txtPnombre.getText());
                modelo.setsNombre(vista.txtSnombre.getText());
                modelo.setpApellido(vista.txtPapellido.getText());
                modelo.setsApellido(vista.txtSapellido.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setTelefono(vista.txtTelefono.getText());

                if (modelo.modificar()) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    Icon l = new ImageIcon(getClass().getResource("/img/check.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Registro modificado ", "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE, l);
                    CtrlVentana.cambiarVista(catalogo);
                    llenarTabla();

                } else {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    Icon m = new ImageIcon(getClass().getResource("/img/check.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Error al modificar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, m);

                }
            }
        }
        if (e.getSource() == vista.btnEliminar) {

            if (modelo.tieneUsuario()) {

                UIManager UI = new UIManager();
                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                Icon n = new ImageIcon(getClass().getResource("/img/multiplication-sign.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                JOptionPane.showMessageDialog(null, "No se pudo eliminar, el Responsable tiene un Usuario", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE, n);
                CtrlVentana.cambiarVista(catalogo);

            } else if (modelo.eliminar()) {

                UIManager UI = new UIManager();
                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                Icon n = new ImageIcon(getClass().getResource("/img/multiplication-sign.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                JOptionPane.showMessageDialog(null, "Registro eliminado ", "ELIMINACIÓN SATISFACTORIA", JOptionPane.INFORMATION_MESSAGE, n);
                CtrlVentana.cambiarVista(catalogo);
                llenarTabla();

            } else {

                UIManager UI = new UIManager();
                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                Icon o = new ImageIcon(getClass().getResource("/img/warning.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                JOptionPane.showMessageDialog(null, "Error al eliminar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, o);

            }
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catalogo.tabla) {
            fila = catalogo.tabla.getSelectedRow();

            modelo = new Responsable(lista.get(fila).getCedula(), lista.get(fila).getpNombre(), lista.get(fila).getsNombre(), lista.get(fila).getpApellido(), lista.get(fila).getsApellido(), lista.get(fila).getCorreo(), lista.get(fila).getTelefono());
            vista = new vista.VisResponsable();
            if (permiso.getModificar()) {
                vista.btnModificar.setEnabled(true);
            }
            if (permiso.getEliminar()) {
                vista.btnEliminar.setEnabled(true);
            }
            vista.cbxCedula.setSelectedItem(modelo.getCedula().split("-")[0]);
            vista.txtCedula.setText(modelo.getCedula().split("-")[1]);
            vista.txtPnombre.setText(modelo.getpNombre());
            vista.txtSnombre.setText(modelo.getsNombre());
            vista.txtPapellido.setText(modelo.getpApellido());
            vista.txtSapellido.setText(modelo.getsApellido());
            vista.txtCorreo.setText(modelo.getCorreo());
            vista.txtTelefono.setText(modelo.getTelefono());

            vista.txtCedula.setEditable(false);
            vista.btnGuardar.setEnabled(false);
            vista.btnLimpiar.setEnabled(false);

            vista.btnModificar.addActionListener(this);
            vista.btnEliminar.addActionListener(this);
            vista.btnLimpiar.addActionListener(this);
            vista.btnSalir.addActionListener(this);
            vista.txtCedula.addKeyListener(this);
            vista.txtPnombre.addKeyListener(this);
            vista.txtSnombre.addKeyListener(this);
            vista.txtPapellido.addKeyListener(this);
            vista.txtSapellido.addKeyListener(this);
            vista.txtTelefono.addKeyListener(this);
            vista.txtCorreo.addKeyListener(this);

            CtrlVentana.cambiarVista(vista);
        }
    }

    private void limpiar() {
        vista.cbxCedula.setSelectedIndex(0);
        vista.txtCedula.setText("");
        vista.txtPnombre.setText("");
        vista.txtSnombre.setText("");
        vista.txtPapellido.setText("");
        vista.txtSapellido.setText("");
        vista.txtCorreo.setText("");
        vista.txtTelefono.setText("");
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    private void llenarTabla() {
        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        catalogo.tabla.setRowSorter(tr);

        catalogo.tabla.setModel(modeloT);

        catalogo.tabla.getTableHeader().setReorderingAllowed(false);
        catalogo.tabla.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Teléfono");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;

            columna[ind++] = lista.get(i).getCedula();
            String nombre = lista.get(i).getpNombre() + " " + lista.get(i).getsNombre();
            columna[ind++] = nombre;
            String apellido = lista.get(i).getpApellido() + " " + lista.get(i).getsApellido();
            columna[ind++] = apellido;
            columna[ind++] = lista.get(i).getCorreo();
            columna[ind++] = lista.get(i).getTelefono();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            catalogo.tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    private Boolean validar() {
        boolean resultado = true;
        String mensaje = "";

        if (vista.cbxCedula.getSelectedIndex() == 0) {
            mensaje += "Debe seleccionar una Nacionalidad \n";
            resultado = false;
        }
        if (vista.txtCedula.getText().isEmpty()) {
            mensaje += "El campo Cédula no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtPnombre.getText().isEmpty()) {
            mensaje += "El campo Primer Nombre no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtPapellido.getText().isEmpty()) {
            mensaje += "El campo Primer Apellido no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtCorreo.getText().isEmpty()) {
            mensaje += "El campo Correo no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtTelefono.getText().isEmpty()) {
            mensaje += "El campo Teléfono no puede estar vacío \n";
            resultado = false;
        } else if (vista.txtTelefono.getText().length() < 11) {
            mensaje += "Número Incompleto";
            resultado = false;
        }

        if (resultado == false) {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            Icon q = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(vista, mensaje, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, q);
        }

        return resultado;
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
        if (e.getSource() == vista.txtCedula) {
            Validacion.soloNumeros(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtCedula.getText(), 8);
        }

        if (e.getSource() == vista.txtPnombre) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtPnombre.getText(), 25);
        }
        if (e.getSource() == vista.txtSnombre) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtSnombre.getText(), 25);
        }
        if (e.getSource() == vista.txtPapellido) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtPapellido.getText(), 25);
        }
        if (e.getSource() == vista.txtSapellido) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtSapellido.getText(), 25);
        }
        if (e.getSource() == vista.txtTelefono) {
            Validacion.soloNumeros(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtTelefono.getText(), 11);

        }
        if (e.getSource() == vista.txtCorreo) {
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtCorreo.getText(), 60);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxCedula.setFocusable(false);
    }

    public void stylecombo(JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);

        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (Validacion.email(vista.txtCorreo.getText())) {

        } else {
            JOptionPane.showMessageDialog(null, "Email incorrecto");
            vista.txtCorreo.requestFocus();
        }

    }

}
