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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Persona;
import modelo.Unidades;
import modelo.Visita;
import vista.VisVisita;

public class CtrlVisita implements ActionListener, ItemListener, MouseListener, KeyListener {

    private VisVisita vista = new VisVisita();
    private Visita modelo;
    private ArrayList<Visita> lista;
    DefaultTableModel dm;
    private Persona modeloP;
    private Unidades modUnidad;
    private ArrayList<Unidades> listaUnidad;

    private boolean personaExiste;

    public CtrlVisita() {
        this.modelo = new Visita();
        this.vista = new VisVisita();
        this.modUnidad = new Unidades();
        this.modeloP = new Persona();

        this.vista.txtCedula.addActionListener(this);
        this.vista.btnCedula.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnEntrada.addActionListener(this);
        this.vista.btnSalida.addActionListener(this);
        vista.cbxCedula.addItemListener(this);
        vista.cbxUnidad.addItemListener(this);
        this.vista.tabla.addMouseListener(this);

        vista.btnSalida.setEnabled(false);
        vista.txtNombre.setEditable(false);
        vista.txtApellido.setEditable(false);

        stylecombo(vista.cbxCedula);
        stylecombo(vista.cbxUnidad);
        crearCbxUnidad();

        llenarTabla();

        CtrlVentana.cambiarVista(vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.txtCedula) {
            buscarVisitante();
        }

        if (e.getSource() == vista.btnCedula) {
            buscarVisitante();
        }

        if (e.getSource() == vista.btnEntrada) {

            if (validar()) {
                int ind;
                boolean auto;

                modelo.getVisitante().setCedula(modeloP.getCedula());
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
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            llenarTabla();
                            limpiar();

                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo registrar");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar a la persona");
                    }

                } else {

                    if (modelo.registrar(auto)) {
                        JOptionPane.showMessageDialog(null, "Registro guardado");
                        llenarTabla();
                        limpiar();

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar");
                    }
                }
            }
        }

        if (e.getSource() == vista.btnSalida) {

            if (modelo.registrarSalida()) {
                JOptionPane.showMessageDialog(null, "Salida registrada");
                vista.btnEntrada.setEnabled(true);
                vista.btnSalida.setEnabled(false);
                llenarTabla();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la salida");
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
            vista.cbxUnidad.setSelectedItem(modelo.getUnidad().getN_unidad());
            if (modelo.getMatricula() != null) {
                vista.rBtnSi.setSelected(true);
                vista.txtMatricula.setText(modelo.getMatricula());
                vista.txtModelo.setText(modelo.getModelo());
                vista.txtColor.setText(modelo.getColor());
                vista.txtAcompanantes.setText(modelo.getNumPeronas().toString());
            }
            vista.btnEntrada.setEnabled(false);

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
    }

    private void buscarVisitante() {
        String cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();
        modeloP.setCedula(cedula);

        if (modeloP.buscar()) {
            personaExiste = true;
            vista.txtNombre.setEditable(false);
            vista.txtApellido.setEditable(false);
            vista.txtNombre.setText(modeloP.getpNombre());
            vista.txtApellido.setText(modeloP.getpApellido());

        } else {
            personaExiste = false;
            vista.txtNombre.setText(null);
            vista.txtApellido.setText(null);
            vista.txtNombre.setEditable(true);
            vista.txtApellido.setEditable(true);
            JOptionPane.showMessageDialog(null, "No se encontró a la persona\n\nIngrese Nombre y Apellido");
        }
    }

    private void crearCbxUnidad() {
        listaUnidad = modUnidad.listar();
        vista.cbxUnidad.addItem("Seleccione...");
        vista.cbxUnidad.setFocusable(false);

        if (listaUnidad != null) {

            for (Unidades datosX : listaUnidad) {
                vista.cbxUnidad.addItem(datosX.getN_unidad());
            }
        }
    }

    public void limpiar() {
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
        vista.tabla.setRowSorter(tr);

        vista.tabla.setModel(modeloT);

        modeloT.addColumn("Unidad");
        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Fecha de entrada");
        modeloT.addColumn("Hora de entrada");
        modeloT.addColumn("Fecha de salida");
        modeloT.addColumn("Hora de salida");
        modeloT.addColumn("Matrícula");
        modeloT.addColumn("Modelo");
        modeloT.addColumn("Color");
        modeloT.addColumn("Número de Acompañantes");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getUnidad().getN_unidad();
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
            mensaje += "El campo Cédula no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtNombre.getText().isEmpty()) {
            mensaje += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtApellido.getText().isEmpty()) {
            mensaje += "El campo Apellido no puede estar vacío\n";
            resultado = false;
        }
        if (vista.cbxUnidad.getSelectedIndex() == 0) {
            mensaje += "Debe seleccionar una Unidad\n";
            resultado = false;
        }
        if (vista.rBtnSi.isSelected()) {
            if (vista.txtMatricula.getText().isEmpty()) {
                mensaje += "El campo Matrícula no puede estar vacío\n";
                resultado = false;
            }
            if (vista.txtColor.getText().isEmpty()) {
                mensaje += "El campo Color no puede estar vacío\n";
                resultado = false;
            }
            if (vista.txtModelo.getText().isEmpty()) {
                mensaje += "El campo Modelo no puede estar vacío\n";
                resultado = false;
            }
            if (vista.txtAcompanantes.getText().isEmpty()) {
                mensaje += "El campo Acompañantes no puede estar vacío\n";
                resultado = false;
            }
        }

        if (resultado == false) {
            JOptionPane.showMessageDialog(vista, mensaje);
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
