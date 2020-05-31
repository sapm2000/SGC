package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Funcion;
import modelo.TipoUnidad;
import sgc.SGC;
import vista.catalogoCuenta;
import vista.tipoUnidad;

public class CtrlTipoUnidad implements ActionListener, MouseListener {

    private tipoUnidad vista;
    private catalogoCuenta catalogo;
    private TipoUnidad modelo;

    private Funcion permiso;

    private ArrayList<TipoUnidad> lista;

    public CtrlTipoUnidad() {
        this.catalogo = new catalogoCuenta();
        this.vista = new tipoUnidad();
        this.modelo = new TipoUnidad();

        catalogo.lblTitulo.setText("Tipo de Unidad");

        this.catalogo.btnNuevo.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);

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
            this.vista.setVisible(true);
            this.vista.btnGuardar.setEnabled(true);
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo = new TipoUnidad();
                modelo.setNombre(vista.txtNombre.getText());
                modelo.setArea(Float.valueOf(vista.txtArea.getText()));

                if (modelo.buscarInactivo()) {
                    JOptionPane.showMessageDialog(null, "Este Tipo de unidad ya está registrado en la BD, se recuperarán los datos");

                    if (modelo.reactivar()) {
                        JOptionPane.showMessageDialog(null, "Tipo de unidad habilitado");
                        llenarTabla();
                        vista.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo habilitar el Tipo de unidad");
                    }

                } else {
                    if (modelo.existe()) {
                        JOptionPane.showMessageDialog(null, "Este Tipo de unidad ya existe");

                    } else {
                        if (modelo.registrar()) {
                            JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                            llenarTabla();
                            vista.dispose();
                            vista.txtNombre.setText("");
                            vista.txtArea.setText("");

                        } else {
                            JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
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
                    JOptionPane.showMessageDialog(null, "Registro modificado");
                    llenarTabla();
                    vista.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar");
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            if (modelo.eliminar()) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                vista.dispose();
                llenarTabla();

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");
            }
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

//            vista.txtN_cuenta.setEditable(false);
            vista.btnGuardar.setEnabled(false);
            vista.btnModificar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);

            vista.setVisible(true);
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
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
