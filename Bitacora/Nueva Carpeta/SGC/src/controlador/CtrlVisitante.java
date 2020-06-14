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
import javax.swing.table.DefaultTableModel;
import modelo.Visitante;
import vista.VisVisitante;

    public class CtrlVisitante implements ActionListener, MouseListener, KeyListener, ItemListener{

    private VisVisitante vista;
    private Visitante modelo;
    private ArrayList<Visitante> lista;

    public CtrlVisitante() {
        this.vista = new VisVisitante();
        this.modelo = new Visitante();

        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.tabla.addMouseListener(this);
        this.vista.txtCedula.addKeyListener(this);
        this.vista.txtNombre.addKeyListener(this);
        this.vista.txtApellido.addKeyListener(this);
        CtrlVentana.cambiarVista(vista);
        vista.cbxCedula.addItemListener(this);
        stylecombo(vista.cbxCedula);
        llenarTabla(vista.tabla);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgregar) {
            if (validar()) {
                String cedula;

                cedula = vista.cbxCedula.getSelectedItem() + "-" + vista.txtCedula.getText();
                modelo.setCedula(cedula);
                modelo.setpNombre(vista.txtNombre.getText());
                modelo.setpApellido(vista.txtApellido.getText());

                if (modelo.existe()) {
                    JOptionPane.showMessageDialog(null, "Ya existe una persona registrada con esta cédula");

                } else {
                    if (modelo.existePersona()) {
                        JOptionPane.showMessageDialog(null, "Esta persona está registrada en la BD, se utilizarán los datos de ese registro");

                        if (modelo.registrar(true)) {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            limpiar();
                            llenarTabla(vista.tabla);

                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo registrar");
                        }

                    } else {
                        if (modelo.registrar(false)) {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                            limpiar();
                            llenarTabla(vista.tabla);

                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo registrar");
                        }
                    }
                }
            }
        }
        
        if (e.getSource() == vista.btnSalir) {
            CtrlVisita ctrl = new CtrlVisita();
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vista.tabla) {
            int fila = this.vista.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada

            modelo = lista.get(fila);

            vista.setVisible(true);

            vista.cbxCedula.setSelectedItem(modelo.getCedula().split("-")[0]);
            vista.txtCedula.setText(modelo.getCedula().split("-")[1]);
            vista.txtNombre.setText(modelo.getpNombre());
            vista.txtApellido.setText(modelo.getpApellido());
            vista.txtCedula.setEnabled(false);

            vista.btnAgregar.setEnabled(true);
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void limpiar() {
        vista.txtCedula.setText("");
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
    }

    public void llenarTabla(JTable tablaD) {
        lista = modelo.listar();
        DefaultTableModel modeloT = new DefaultTableModel();
        int ind;

        tablaD.setModel(modeloT);
        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = lista.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = lista.get(i).getCedula();
            columna[ind++] = lista.get(i).getpNombre();
            columna[ind++] = lista.get(i).getpApellido();

            modeloT.addRow(columna);
        }
    }

    private Boolean validar() {
        Boolean resultado = true;
        String msj = "";

        if (vista.txtCedula.getText().isEmpty()) {
            msj += "El campo Cédula no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtNombre.getText().isEmpty()) {
            msj += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }

        if (vista.txtApellido.getText().isEmpty()) {
            msj += "El campo Apellido no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        vista.cbxCedula.setFocusable(false);
    } 
    
    public void stylecombo (JComboBox c) {
        c.setFont(new Font("Tahoma", Font.BOLD, 14));
        c.setForeground(Color.WHITE);
        
        c.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
    }
    
}
