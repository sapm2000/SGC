package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Registro_visitante;
import modelo.Registro_visitas;
import modelo.Unidades;
import vista.registroVisitas;
import vista.visitasAutorizadas;

public class controladoRegistroVisita implements ActionListener, ItemListener, MouseListener, KeyListener, WindowListener {

    private registroVisitas visRvis = new registroVisitas();
    private visitasAutorizadas visAuto;
    private Registro_visitante modRvisitante;
    private Registro_visitas modRvtas;
    private Unidades modUni;
    private controladorVisitante contVisitante;
    ArrayList<Unidades> listaUnidad;
    ArrayList<Registro_visitas> listaRegistroVisita;
    DefaultTableModel dm;

    public controladoRegistroVisita(registroVisitas Rvis, Registro_visitas modRvtas) {

        this.visRvis = Rvis;
        this.modRvtas = modRvtas;
        this.modUni = new Unidades();
        this.modRvisitante = new Registro_visitante();
        this.visRvis.txtCedula.addActionListener(this);
        this.visRvis.btnBuscar.addActionListener(this);
        this.visRvis.btnAgregarVisita.addActionListener(this);
        this.visRvis.btnAgregarVisitante.addActionListener(this);

        listaUnidad = modUni.listarCbxUnidad();
        crearCbxUnidad(listaUnidad);

        llenarTabla(visRvis.jTable);

    }

    public void limpiar() {
        visRvis.txtCedula.setText(null);
        visRvis.txtNombre.setText(null);
        visRvis.txtApellido.setText(null);
        visRvis.txtMatricula.setText(null);
        visRvis.txtModelo.setText(null);
        visRvis.txtColor.setText(null);
        visRvis.cbxUnidad.setSelectedIndex(0);
    }

    private void crearCbxUnidad(ArrayList<Unidades> datos) {
        visRvis.cbxUnidad.removeAllItems();
        visRvis.cbxUnidad.addItem("Seleccione...");

        if (datos != null) {
            for (Unidades datosX : datos) {
                visRvis.cbxUnidad.addItem(datosX.getN_unidad() + " - " + datosX.getNombre() + " " + datosX.getApellido());
            }

        }
    }

    public void llenarTabla(JTable tablaD) {

        listaRegistroVisita = modRvtas.listarVisitas();

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Unidad");
        modeloT.addColumn("Placa");
        modeloT.addColumn("Modelo");
        modeloT.addColumn("Color");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Hora");
        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");

        Object[] columna = new Object[9];

        int numRegistro = listaRegistroVisita.size();

        for (int i = 0; i < numRegistro; i++) {
            columna[0] = listaRegistroVisita.get(i).getId_unidad();
            columna[1] = listaRegistroVisita.get(i).getMatricula();
            columna[2] = listaRegistroVisita.get(i).getModelo();
            columna[3] = listaRegistroVisita.get(i).getColor();
            columna[4] = listaRegistroVisita.get(i).getFecha();
            columna[5] = listaRegistroVisita.get(i).getHora();
            columna[6] = listaRegistroVisita.get(i).getCi_visitante();
            columna[7] = listaRegistroVisita.get(i).getModReVi().getNombre();
            columna[8] = listaRegistroVisita.get(i).getModReVi().getApellido();

            modeloT.addRow(columna);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visRvis.txtCedula) {
            buscarVisitante();

        }

        if (e.getSource() == visRvis.btnBuscar) {
            buscarVisitante();

        }

        if (e.getSource() == visRvis.btnAgregarVisita) {
            modRvtas.setCi_visitante(modRvisitante.getCedula());
            modRvtas.setMatricula(visRvis.txtMatricula.getText());
            modRvtas.setModelo(visRvis.txtModelo.getText());
            modRvtas.setColor(visRvis.txtColor.getText());
            int indice = visRvis.cbxUnidad.getSelectedIndex() - 1;
            modRvtas.setId_unidad(listaUnidad.get(indice).getN_unidad());

            modRvtas.registrarVisitas();

            llenarTabla(visRvis.jTable);
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

    @Override
    public void windowOpened(WindowEvent e) {
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

    private void buscarVisitante() {
        modRvisitante.setCedula(visRvis.txtCedula.getText());

        if (modRvisitante.buscarVisitante(modRvisitante)) {
            visRvis.txtNombre.setText(modRvisitante.getNombre());
            visRvis.txtApellido.setText(modRvisitante.getApellido());

        } else {
            visRvis.btnAgregarVisitante.setEnabled(true);
            JOptionPane.showMessageDialog(null, "REGISTRO NO ENCONTRADO");
        }
    }

}
