package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Responsable;
import vista.CatResponsable;

public class CtrlResponsable implements ActionListener, MouseListener, KeyListener {

    vista.Responsable vista;
    modelo.Responsable modelo;

    CatResponsable catalogo;

    DefaultTableModel dm;

    ArrayList<Responsable> listaResponsable;

    int fila;

    public CtrlResponsable() {
        catalogo = new CatResponsable();
        modelo = new modelo.Responsable();

        catalogo.btnNuevo.addActionListener(this);
        catalogo.tabla.addMouseListener(this);
        catalogo.txtBuscar.addKeyListener(this);

        llenarTabla();
        catalogo.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == catalogo.btnNuevo) {
            vista = new vista.Responsable();

            vista.btnModificar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);

            vista.btnGuardar.addActionListener(this);
            vista.btnLimpiar.addActionListener(this);
            vista.txtCedula.addKeyListener(this);
            vista.txtPnombre.addKeyListener(this);
            vista.txtSnombre.addKeyListener(this);
            vista.txtPapellido.addKeyListener(this);
            vista.txtSapellido.addKeyListener(this);
            vista.txtTelefono.addKeyListener(this);
            vista.txtCorreo.addKeyListener(this);

            vista.setVisible(true);

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

                if (modelo.registrar()) {
                    JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                    vista.dispose();
                    llenarTabla();

                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTAR");

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
                    JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
                    vista.dispose();
                    llenarTabla();

                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");

                }
            }
        }
        if (e.getSource() == vista.btnEliminar) {
            if (modelo.eliminar()) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
                vista.dispose();
                llenarTabla();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR");

            }
        }
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == catalogo.tabla) {
            fila = catalogo.tabla.getSelectedRow();

            modelo = new Responsable(listaResponsable.get(fila).getCedula(), listaResponsable.get(fila).getpNombre(), listaResponsable.get(fila).getsNombre(), listaResponsable.get(fila).getpApellido(), listaResponsable.get(fila).getsApellido(), listaResponsable.get(fila).getCorreo(), listaResponsable.get(fila).getTelefono());
            vista = new vista.Responsable();

            vista.cbxCedula.setSelectedItem(modelo.getCedula().split("-")[0]);
            vista.txtCedula.setText(modelo.getCedula().split("-")[1]);
            vista.txtPnombre.setText(modelo.getpNombre());
            vista.txtSnombre.setText(modelo.getsNombre());
            vista.txtPapellido.setText(modelo.getpApellido());
            vista.txtSapellido.setText(modelo.getsApellido());
            vista.txtCorreo.setText(modelo.getCorreo());
            vista.txtTelefono.setText(modelo.getTelefono());

            vista.cbxCedula.setEnabled(false);
            vista.txtCedula.setEditable(false);
            vista.btnGuardar.setEnabled(false);
            vista.btnLimpiar.setEnabled(false);

            vista.btnModificar.addActionListener(this);
            vista.btnEliminar.addActionListener(this);
            vista.btnLimpiar.addActionListener(this);
            vista.txtCedula.addKeyListener(this);
            vista.txtPnombre.addKeyListener(this);
            vista.txtSnombre.addKeyListener(this);
            vista.txtPapellido.addKeyListener(this);
            vista.txtSapellido.addKeyListener(this);
            vista.txtTelefono.addKeyListener(this);
            vista.txtCorreo.addKeyListener(this);

            vista.setVisible(true);
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
        listaResponsable = modelo.listar();

        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        catalogo.tabla.setModel(modeloT);
        catalogo.tabla.getTableHeader().setReorderingAllowed(false);
        catalogo.tabla.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Cédula");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Teléfono");

        Object[] columna = new Object[modeloT.getColumnCount()];

        int numRegistro = listaResponsable.size();
        int ind;

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;

            columna[ind++] = listaResponsable.get(i).getCedula();
            String nombre = listaResponsable.get(i).getpNombre() + " " + listaResponsable.get(i).getsNombre();
            columna[ind++] = nombre;
            String apellido = listaResponsable.get(i).getpApellido() + " " + listaResponsable.get(i).getsApellido();
            columna[ind++] = apellido;
            columna[ind++] = listaResponsable.get(i).getCorreo();
            columna[ind++] = listaResponsable.get(i).getTelefono();

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
            mensaje += "Debe seleccionar una Nacionalidad\n";
            resultado = false;
        }
        if (vista.txtCedula.getText().isEmpty()) {
            mensaje += "El campo Cédula no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtPnombre.getText().isEmpty()) {
            mensaje += "El campo Primer Nombre no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtPapellido.getText().isEmpty()) {
            mensaje += "El campo Primer Apellido no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtCorreo.getText().isEmpty()) {
            mensaje += "El campo Correo no puede estar vacío\n";
            resultado = false;
        }
        if (vista.txtTelefono.getText().isEmpty()) {
            mensaje += "El campo Teléfono no puede estar vacío\n";
            resultado = false;
        } else if (vista.txtTelefono.getText().length() < 11) {
            mensaje += "Número Incompleto";
            resultado = false;
        }

        if (resultado == false) {
            JOptionPane.showMessageDialog(vista, mensaje);
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
            Validacion.soloLetras(e);
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
}