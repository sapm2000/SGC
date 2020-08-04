package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Funcion;
import modelo.TipoUnidad;
import modelo.Unidad;
import sgc.SGC;
import vista.Catalogo;
import vista.VisTipoUnidad;

public class CtrlTipoUnidad implements ActionListener, MouseListener, KeyListener {

    private VisTipoUnidad vista;
    private Catalogo catalogo;
    private TipoUnidad modelo;
    private Unidad unidades;
    private Funcion permiso;
    private ArrayList<Unidad> listaunidades;
    private ArrayList<TipoUnidad> lista;
    DefaultTableModel dm;

    public CtrlTipoUnidad() {
        this.catalogo = new Catalogo();
        this.vista = new VisTipoUnidad();
        this.modelo = new TipoUnidad();
        this.unidades = new Unidad();

        CtrlVentana.cambiarVista(catalogo);
        catalogo.lblTitulo.setText("Tipo de Unidad");
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtArea.addKeyListener(this);
        this.vista.txtNombre.addKeyListener(this);

        llenarTabla();
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }

        this.catalogo.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == catalogo.btnNuevo) {
            vista.txtNombre.setText("");
            vista.txtArea.setText("");
            this.vista.btnGuardar.setEnabled(true);
            CtrlVentana.cambiarVista(vista);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo = new TipoUnidad();
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setArea(Float.valueOf(vista.txtArea.getText()));

                if (modelo.buscarInactivo()) {

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

                    JOptionPane.showMessageDialog(null, "Este Tipo de unidad ya está registrado en la base de datos, se recuperarán los datos ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);

                    if (modelo.reactivar()) {

                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        Icon a = new ImageIcon(getClass().getResource("/img/check.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "Tipo de unidad habilitado ", "HABILITACIÓN DE TIPO DE UNIDAD", JOptionPane.WARNING_MESSAGE, a);
                        modelo.areaTotal();
                        float area = modelo.getArea();

                        listaunidades = unidades.listarArea();
                        int x = listaunidades.size();
                        for (int i = 0; i < x; i++) {
                            float total = listaunidades.get(i).getTipo().getArea() / area;
                            unidades.setAlicuota(total);

                            unidades.setId(listaunidades.get(i).getId());
                            unidades.actualizarAlicuota(unidades);
                        }
                        llenarTabla();
                        CtrlVentana.cambiarVista(catalogo);

                    } else {

                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        Icon b = new ImageIcon(getClass().getResource("/img/warning.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "No se pudo habilitar el Tipo de unidad ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, b);
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

                        JOptionPane.showMessageDialog(null, "Este Tipo de unidad ya existe ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, d);

                    } else {
                        if (modelo.registrar()) {

                            UIManager UI = new UIManager();
                            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                            int botonDialogo = JOptionPane.OK_OPTION;
                            Icon f = new ImageIcon(getClass().getResource("/img/check.png"));
                            UIManager.put("Button.background", Color.white);
                            UIManager.put("Button.font", Color.blue);
                            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                            UIManager.put("Label.background", Color.blue);
                            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                            JOptionPane.showMessageDialog(null, "Registro guardado ", "REGISTRO DE DATOS", JOptionPane.INFORMATION_MESSAGE, f);
                            llenarTabla();
                            modelo.areaTotal();
                            float area = modelo.getArea();

                            listaunidades = unidades.listarArea();
                            int x = listaunidades.size();
                            for (int i = 0; i < x; i++) {
                                float total = listaunidades.get(i).getTipo().getArea() / area;
                                unidades.setAlicuota(total);

                                unidades.setId(listaunidades.get(i).getId());
                                unidades.actualizarAlicuota(unidades);
                            }
                            CtrlVentana.cambiarVista(catalogo);
                            vista.txtNombre.setText("");
                            vista.txtArea.setText("");

                        } else {

                            UIManager UI = new UIManager();
                            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                            int botonDialogo = JOptionPane.OK_OPTION;
                            Icon g = new ImageIcon(getClass().getResource("/img/warning.png"));
                            UIManager.put("Button.background", Color.white);
                            UIManager.put("Button.font", Color.blue);
                            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                            UIManager.put("Label.background", Color.blue);
                            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                            JOptionPane.showMessageDialog(null, "Error al guardar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, g);
                        }
                    }
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setArea(Float.valueOf(vista.txtArea.getText()));

                if (modelo.modificar()) {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon h = new ImageIcon(getClass().getResource("/img/check.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Registro modificado ", "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE, h);
                    modelo.areaTotal();
                    float area = modelo.getArea();

                    listaunidades = unidades.listarArea();
                    int x = listaunidades.size();
                    for (int i = 0; i < x; i++) {
                        float total = listaunidades.get(i).getTipo().getArea() / area;
                        unidades.setAlicuota(total);

                        unidades.setId(listaunidades.get(i).getId());
                        unidades.actualizarAlicuota(unidades);
                    }
                    llenarTabla();
                    CtrlVentana.cambiarVista(catalogo);

                } else {

                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon j = new ImageIcon(getClass().getResource("/img/warning.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "No se pudo modificar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, j);
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            if (modelo.eliminar()) {

                UIManager UI = new UIManager();
                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                int botonDialogo = JOptionPane.OK_OPTION;
                Icon k = new ImageIcon(getClass().getResource("/img/multiplication-sign.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                JOptionPane.showMessageDialog(null, "Registro eliminado ", "ELIMINACIÓN SATISFACTORIA", JOptionPane.INFORMATION_MESSAGE, k);
                CtrlVentana.cambiarVista(catalogo);
                llenarTabla();

            } else {

                UIManager UI = new UIManager();
                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                int botonDialogo = JOptionPane.OK_OPTION;
                Icon l = new ImageIcon(getClass().getResource("/img/warning.png"));
                UIManager.put("Button.background", Color.white);
                UIManager.put("Button.font", Color.blue);
                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                UIManager.put("Label.background", Color.blue);
                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                JOptionPane.showMessageDialog(null, "No se pudo eliminar ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, l);
            }
        }

        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }

    private void permisoBtn() {
        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Tipo de unidad")) {
                permiso = funcionbtn;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catalogo.tabla) {
            int fila;

            fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            modelo = lista.get(fila);

            if (permiso.getModificar()) {
                vista.btnModificar.setEnabled(true);
            }
            if (permiso.getEliminar()) {
                vista.btnEliminar.setEnabled(true);
            }

            vista.txtNombre.setText(modelo.getNombre());
            vista.txtArea.setText(modelo.getArea().toString());

            vista.btnGuardar.setEnabled(false);
            vista.btnModificar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);

            CtrlVentana.cambiarVista(vista);
        }
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

    private void llenarTabla() {
        lista = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        catalogo.tabla.setRowSorter(tr);
        catalogo.tabla.setModel(modeloT);

        modeloT.addColumn("Tipo de Unidad");
        modeloT.addColumn("Área");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getNombre();
            columna[ind++] = lista.get(i).getArea();

            modeloT.addRow(columna);
        }
    }

    private Boolean validar() {

        String msj = "";
        Boolean resultado = true;

        if (vista.txtNombre.getText().isEmpty()) {
            msj += "El campo Tipo de unidad no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtArea.getText().isEmpty()) {
            msj += "El campo Área no puede estar vacío \n";
            resultado = false;
        } else if (Double.parseDouble(vista.txtArea.getText()) < 10) {
            msj += "El campo Área debe ser mayor que 10 ";
            resultado = false;
        } 

        if (!resultado) {

            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            int botonDialogo = JOptionPane.OK_OPTION;
            Icon m = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, msj, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, m);
        }

        return resultado;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

        if (ke.getSource() == vista.txtNombre) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtNombre.getText(), 10);
        }
        if (ke.getSource() == vista.txtArea) {

            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.soloUnPunto(ke, vista.txtArea.getText());
            Validacion.limite(ke, vista.txtArea.getText(), 4);
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

    private void filtro(String consulta, JTable tablaBuscar) {
        dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);

        tablaBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
    }

}
