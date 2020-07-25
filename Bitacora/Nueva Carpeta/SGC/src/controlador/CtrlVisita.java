package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import modelo.Persona;
import modelo.Unidad;
import modelo.Visita;
import vista.Catalogo;
import vista.VisVisita;

public class CtrlVisita implements ActionListener, ItemListener, MouseListener, KeyListener {

    private VisVisita vista = new VisVisita();
    private Visita modelo;
    private ArrayList<Visita> lista;

    private Catalogo catPersonas;
    private Persona modPersona;
    private ArrayList<Persona> listaPersonas;
    private Unidad modUnidad;
    private ArrayList<Unidad> listaUnidad;

    private JFrame ventanaBuscar;

    private boolean personaExiste;
    private DefaultTableModel dm;

    public CtrlVisita() {
        
        this.modUnidad = new Unidad();
        
        if (modUnidad.contar() == 0) {

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

            JOptionPane.showMessageDialog(null, "No existen Unidades, debe registrar una para continuar ", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE, p);

            new CtrlUnidad();

        } else {
        
        this.modelo = new Visita();
        this.vista = new VisVisita();

        catPersonas = new Catalogo();
        catPersonas.lblTitulo.setText("Buscar Persona");
        catPersonas.remove(catPersonas.btnNuevo);

        ventanaBuscar = new JFrame("Buscar Persona");
        ventanaBuscar.setSize(1366, 740);
        ventanaBuscar.add(catPersonas);

        this.modPersona = new Persona();

        this.vista.btnBuscarPersona.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnEntrada.addActionListener(this);
        this.vista.btnSalida.addActionListener(this);
        vista.cbxCedula.addItemListener(this);
        vista.cbxUnidad.addItemListener(this);
        this.vista.tabla.addMouseListener(this);

        this.catPersonas.tabla.addMouseListener(this);

        vista.btnSalida.setEnabled(false);
        vista.txtNombre.setEditable(false);
        vista.txtApellido.setEditable(false);

        stylecombo(vista.cbxCedula);
        stylecombo(vista.cbxUnidad);
        crearCbxUnidad();

        llenarTabla();

        CtrlVentana.cambiarVista(vista);
        
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnBuscarPersona) {

            llenarTablaPersonas(catPersonas.tabla);
            ventanaBuscar.setVisible(true);
        }

        if (e.getSource() == vista.btnEntrada) {

            if (validar()) {

                int ind;
                boolean auto;

                modelo.getVisitante().setCedula(modPersona.getCedula());
                ind = vista.cbxUnidad.getSelectedIndex() - 1;

                if (!personaExiste) {

                    modelo.getVisitante().setpNombre(vista.txtNombre.getText());
                    modelo.getVisitante().setpApellido(vista.txtApellido.getText());
                }

                modelo.setUnidad(listaUnidad.get(ind));
                auto = vista.rBtnSi.isSelected();

                if (auto) {

                    modelo.setMatricula(vista.txtMatricula.getText());
                    modelo.setModelo(vista.txtModelo.getText());
                    modelo.setColor(vista.txtColor.getText());
                    modelo.setNumPeronas(Integer.parseInt(vista.txtAcompanantes.getText()));
                }

                if (!personaExiste) {

                    if (modelo.getVisitante().registrarPersona()) {

                        if (modelo.registrar(auto)) {

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

                            JOptionPane.showMessageDialog(null, "Registro guardado ", "Registro de datos", JOptionPane.INFORMATION_MESSAGE, p);
                            llenarTabla();
                            limpiar();

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

                            JOptionPane.showMessageDialog(null, "No se pudo registrar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                        }

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

                        JOptionPane.showMessageDialog(null, "No se pudo registrar a la persona ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    }

                } else {

                    if (modelo.registrar(auto)) {

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

                        JOptionPane.showMessageDialog(null, "Registro guardado ", "Registro de datos", JOptionPane.INFORMATION_MESSAGE, p);
                        llenarTabla();
                        limpiar();

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

                        JOptionPane.showMessageDialog(null, "No se pudo registrar ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
                    }
                }
            }
        }

        if (e.getSource() == vista.btnSalida) {

            if (modelo.registrarSalida()) {

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

                JOptionPane.showMessageDialog(null, "Salida registrada ", "Salida de visitas", JOptionPane.INFORMATION_MESSAGE, p);
                vista.btnEntrada.setEnabled(true);
                vista.btnSalida.setEnabled(false);
                llenarTabla();
                limpiar();

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

                JOptionPane.showMessageDialog(null, "No se pudo registrar la salida ", "Advertencia", JOptionPane.WARNING_MESSAGE, p);
            }
        }

        if (e.getSource() == vista.btnNuevo) {

            llenarTabla();
            limpiar();
            vista.btnEntrada.setEnabled(true);
            vista.btnSalida.setEnabled(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxCedula.setFocusable(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == vista.tabla) {

            int fila = this.vista.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            limpiar();
            vista.btnSalida.setEnabled(true);

            modelo = lista.get(fila);
            vista.txtCedula.setText(modelo.getVisitante().getCedula());
            vista.txtNombre.setText(modelo.getVisitante().getpNombre());
            vista.txtApellido.setText(modelo.getVisitante().getpApellido());
            vista.cbxUnidad.setSelectedItem(modelo.getUnidad().getNumeroUnidad());

            if (modelo.getMatricula() != null) {

                vista.rBtnSi.setSelected(true);
                vista.txtMatricula.setText(modelo.getMatricula());
                vista.txtModelo.setText(modelo.getModelo());
                vista.txtColor.setText(modelo.getColor());
                vista.txtAcompanantes.setText(modelo.getNumPeronas().toString());
            }

            vista.btnEntrada.setEnabled(false);
        }

        if (e.getSource() == catPersonas.tabla) {
            int fila;

            fila = catPersonas.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
            modPersona = listaPersonas.get(fila);

            vista.txtCedula.setText(modPersona.getCedula());
            vista.txtNombre.setText(modPersona.getpNombre());
            vista.txtApellido.setText(modPersona.getpApellido());

            ventanaBuscar.setVisible(false);
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

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getSource() == vista.txtCedula) {
            Validacion.soloNumeros(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtCedula.getText(), 8);
        }

        if (e.getSource() == vista.txtNombre) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtNombre.getText(), 25);
        }

        if (e.getSource() == vista.txtApellido) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtApellido.getText(), 25);
        }

        if (e.getSource() == vista.txtAcompanantes) {
            Validacion.soloNumeros(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtAcompanantes.getText(), 2);
        }

        if (e.getSource() == vista.txtMatricula) {
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtMatricula.getText(), 10);

        }

        if (e.getSource() == vista.txtColor) {
            Validacion.soloLetras(e);
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtColor.getText(), 15);
        }

        if (e.getSource() == vista.txtModelo) {
            Validacion.Espacio(e);
            Validacion.limite(e, vista.txtModelo.getText(), 25);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == vista.txtBusqueda) {

            filtro(vista.txtBusqueda.getText(), vista.tabla);
        }

        if (e.getSource() == catPersonas.txtBuscar) {

            filtro(catPersonas.txtBuscar.getText(), catPersonas.tabla);
        }
    }

    private void crearCbxUnidad() {

        listaUnidad = modUnidad.listar();
        vista.cbxUnidad.addItem("Seleccione...");
        vista.cbxUnidad.setFocusable(false);

        if (listaUnidad != null) {

            for (Unidad datosX : listaUnidad) {
                vista.cbxUnidad.addItem(datosX.getNumeroUnidad());
            }
        }
    }

    public void limpiar() {

        modelo = new Visita();
        modPersona = new Persona();
        modUnidad = new Unidad();
        vista.cbxCedula.setSelectedIndex(0);
        vista.txtCedula.setText("");
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
        vista.cbxUnidad.setSelectedIndex(0);
        vista.txtAcompanantes.setText("");
        vista.txtMatricula.setText("");
        vista.txtModelo.setText("");
        vista.txtColor.setText("");
    }

    public void llenarTabla() {

        lista = modelo.listar();
        DefaultTableModel modeloT = new DefaultTableModel();
        int ind;

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modeloT);
        vista.tabla.getTableHeader().setReorderingAllowed(false);
        vista.tabla.getTableHeader().setResizingAllowed(false);
        vista.tabla.setRowSorter(tr);

        vista.tabla.setModel(modeloT);

        modeloT.addColumn("<html>Número de <br> Unidad</html>");
        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("<html>Fecha de <br> Entrada</html>");
        modeloT.addColumn("<html>Hora de <br> Entrada</html>");
        modeloT.addColumn("<html>Fecha de <br> Salida</html>");
        modeloT.addColumn("<html>Hora de <br> Salida</html>");
        modeloT.addColumn("Matrícula");
        modeloT.addColumn("Modelo");
        modeloT.addColumn("Color");
        modeloT.addColumn("<html>Número de <br> Acompañantes</html>");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {

            ind = 0;
            columna[ind++] = lista.get(i).getUnidad().getNumeroUnidad();
            columna[ind++] = lista.get(i).getVisitante().getCedula();
            columna[ind++] = lista.get(i).getVisitante().getpNombre() + " " + lista.get(i).getVisitante().getpApellido();
            columna[ind++] = lista.get(i).getFechaEntrada();
            columna[ind++] = lista.get(i).getHoraEntrada();
            columna[ind++] = lista.get(i).getFechaSalida();
            columna[ind++] = lista.get(i).getHoraSalida();
            columna[ind++] = lista.get(i).getMatricula();
            columna[ind++] = lista.get(i).getModelo();
            columna[ind++] = lista.get(i).getColor();
            columna[ind++] = lista.get(i).getNumPeronas();

            modeloT.addRow(columna);
        }

        TableColumnModel columnModel = vista.tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(35);
        columnModel.getColumn(1).setPreferredWidth(10);
        columnModel.getColumn(2).setPreferredWidth(30);
        columnModel.getColumn(3).setPreferredWidth(25);
        columnModel.getColumn(4).setPreferredWidth(25);
        columnModel.getColumn(5).setPreferredWidth(25);
        columnModel.getColumn(6).setPreferredWidth(25);
        columnModel.getColumn(7).setPreferredWidth(20);
        columnModel.getColumn(8).setPreferredWidth(20);
        columnModel.getColumn(9).setPreferredWidth(15);
        columnModel.getColumn(10).setPreferredWidth(60);
    }

    public void llenarTablaPersonas(JTable tablaD) {

        listaPersonas = modPersona.listarP();
        int ind;

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

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaPersonas.size();

        for (int i = 0; i < numRegistro; i++) {

            ind = 0;

            columna[ind++] = listaPersonas.get(i).getCedula();
            columna[ind++] = listaPersonas.get(i).getpNombre();
            columna[ind++] = listaPersonas.get(i).getpApellido();

            modeloT.addRow(columna);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < modeloT.getColumnCount(); i++) {

            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    public void stylecombo(JComboBox c) {

        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);
        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }

    private Boolean validar() {

        boolean resultado = true;
        String mensaje = "";

        if (vista.txtCedula.getText().isEmpty()) {
            mensaje += "El campo Cédula no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtNombre.getText().isEmpty()) {
            mensaje += "El campo Nombre no puede estar vacío \n";
            resultado = false;
        }
        if (vista.txtApellido.getText().isEmpty()) {
            mensaje += "El campo Apellido no puede estar vacío \n";
            resultado = false;
        }
        if (vista.cbxUnidad.getSelectedIndex() == 0) {
            mensaje += "Debe seleccionar una Unidad \n";
            resultado = false;
        }
        if (vista.rBtnSi.isSelected()) {
            if (vista.txtMatricula.getText().isEmpty()) {
                mensaje += "El campo Matrícula no puede estar vacío \n";
                resultado = false;
            }
            if (vista.txtColor.getText().isEmpty()) {
                mensaje += "El campo Color no puede estar vacío \n";
                resultado = false;
            }
            if (vista.txtModelo.getText().isEmpty()) {
                mensaje += "El campo Modelo no puede estar vacío \n";
                resultado = false;
            }
            if (vista.txtAcompanantes.getText().isEmpty()) {
                mensaje += "El campo Acompañantes no puede estar vacío \n";
                resultado = false;
            }
        }

        if (resultado == false) {

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

            JOptionPane.showMessageDialog(vista, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }
}
