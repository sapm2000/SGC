package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Unidades;
import modelo.Visita;
import modelo.Visitante;
import vista.VisRegistroVisita;

public class CtrlVisita implements ActionListener, ItemListener, MouseListener, KeyListener {

    private VisRegistroVisita vista = new VisRegistroVisita();
    private Visita modelo;
    private ArrayList<Visita> lista;

    private Unidades modUnidad;
    private Visitante modVisitante;
    private ArrayList<Unidades> listaUnidad;

    public CtrlVisita() {
        this.modelo = new Visita();
        this.vista = new VisRegistroVisita();
        this.modUnidad = new Unidades();
        this.modVisitante = new Visitante();

        this.vista.txtCedula.addActionListener(this);
        this.vista.btnCedula.addActionListener(this);
        this.vista.btnVisitante.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);

        CtrlVentana.cambiarVista(vista);
        crearCbxUnidad();

        llenarTabla();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.txtCedula) {
            buscarVisitante();
        }

        if (e.getSource() == vista.btnCedula) {
            buscarVisitante();
        }

        if (e.getSource() == vista.btnVisitante) {
            CtrlVisitante ctrl = new CtrlVisitante();
        }

        if (e.getSource() == vista.btnAgregar) {
            int ind;
            modelo.getVisitante().setCedula(modVisitante.getCedula());
            ind = vista.cbxUnidad.getSelectedIndex() - 1;
            modelo.getUnidad().setId(listaUnidad.get(ind).getId());
            modelo.setMatricula(vista.txtMatricula.getText());
            modelo.setModelo(vista.txtModelo.getText());
            modelo.setColor(vista.txtColor.getText());

            modelo.registrar();

            llenarTabla();
            limpiar();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void buscarVisitante() {
        String cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();

        if (modVisitante.buscar(cedula)) {
            vista.txtNombre.setText(modVisitante.getpNombre());
            vista.txtApellido.setText(modVisitante.getpApellido());

        } else {
            vista.btnVisitante.setEnabled(true);
            JOptionPane.showMessageDialog(null, "REGISTRO NO ENCONTRADO");
        }
    }

    private void crearCbxUnidad() {
        listaUnidad = modUnidad.listar();
        vista.cbxUnidad.addItem("Seleccione...");

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
        vista.txtMatricula.setText("");
        vista.txtModelo.setText("");
        vista.txtColor.setText("");
    }

    public void llenarTabla() {
        lista = modelo.listar();
        DefaultTableModel modeloT = new DefaultTableModel();
        int ind;

        vista.tabla.setModel(modeloT);

        modeloT.addColumn("Unidad");
        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Matrícula");
        modeloT.addColumn("Modelo");
        modeloT.addColumn("Color");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Hora");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getUnidad().getN_unidad();
            columna[ind++] = lista.get(i).getVisitante().getCedula();
            columna[ind++] = lista.get(i).getVisitante().getpNombre() + " " + lista.get(i).getVisitante().getpApellido();
            columna[ind++] = lista.get(i).getMatricula();
            columna[ind++] = lista.get(i).getModelo();
            columna[ind++] = lista.get(i).getColor();
            columna[ind++] = lista.get(i).getFecha();
            columna[ind++] = lista.get(i).getHora();

            modeloT.addRow(columna);
        }
    }

}
