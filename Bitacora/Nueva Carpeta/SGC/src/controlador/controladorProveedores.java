/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Condominio;
import modelo.Proveedores;
import vista.catalogoProveedores;
import vista.proveedores;

/**
 *
 * @author rma
 */
public class controladorProveedores implements ActionListener, WindowListener, KeyListener, MouseListener {

    private catalogoProveedores cataprov;
    private proveedores prov;
    private Proveedores modpro;

    ArrayList<Proveedores> listaProveedores;
    DefaultTableModel dm;
    DefaultComboBoxModel dmCbx;

    public controladorProveedores(catalogoProveedores cataprov, proveedores prov, Proveedores modpro) {
        this.cataprov = cataprov;
        this.prov = prov;
        this.modpro = modpro;

        this.cataprov.addWindowListener(this);
        this.cataprov.btn_NuevoProveedor.addActionListener(this);
        this.cataprov.TablaProveedores.addMouseListener(this);
        this.cataprov.txtBuscarProveedores.addKeyListener(this);

        this.prov.btnGuardar.addActionListener(this);
        this.prov.btnLimpiar.addActionListener(this);
        this.prov.btnModificar.addActionListener(this);
        this.prov.btnEliminar.addActionListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        listaProveedores = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Cedula/Rif");
        modeloT.addColumn("Nombre/razon social");
        modeloT.addColumn("Telefono");
        modeloT.addColumn("Correo");
        modeloT.addColumn("Contacto");
        modeloT.addColumn("Direccion");

        Object[] columna = new Object[6];

        int numRegistro = listaProveedores.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaProveedores.get(i).getCedula();
            columna[1] = listaProveedores.get(i).getNombre();
            columna[2] = listaProveedores.get(i).getTelefono();
            columna[3] = listaProveedores.get(i).getCorreo();
            columna[4] = listaProveedores.get(i).getContacto();
            columna[5] = listaProveedores.get(i).getDireccion();

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cataprov.btn_NuevoProveedor) {
            this.prov.setVisible(true);
            this.prov.btnModificar.setEnabled(false);
            this.prov.btnGuardar.setEnabled(true);
            this.prov.btnEliminar.setEnabled(true);
            this.prov.txtCedula.setEnabled(true);
            prov.txtCedula.setText("");
            prov.txtContacto.setText("");
            prov.txtCorreo.setText("");
            prov.txtNombre.setText("");
            prov.txtTelefono.setText("");
            prov.txaDireccion.setText("");

        }

        if (e.getSource() == prov.btnGuardar) {
            if (validar()) {
                modpro.setCedula(prov.txtCedula.getText());
                modpro.setNombre(prov.txtNombre.getText());
                modpro.setContacto(prov.txtContacto.getText());
                modpro.setCorreo(prov.txtCorreo.getText());
                modpro.setTelefono(prov.txtTelefono.getText());
                modpro.setDireccion(prov.txaDireccion.getText());

                if (modpro.registrar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(cataprov.TablaProveedores);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == prov.btnModificar) {
            if (validar()) {
                modpro.setCedula(prov.txtCedula.getText());
                modpro.setNombre(prov.txtNombre.getText());
                modpro.setContacto(prov.txtContacto.getText());
                modpro.setCorreo(prov.txtCorreo.getText());
                modpro.setTelefono(prov.txtTelefono.getText());
                modpro.setDireccion(prov.txaDireccion.getText());

                if (modpro.modificar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    Llenartabla(cataprov.TablaProveedores);
                    prov.dispose();

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == prov.btnEliminar) {

            if (modpro.eliminar(modpro)) {

                modpro.setCedula(prov.txtCedula.getText());
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                prov.dispose();
                Llenartabla(cataprov.TablaProveedores);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (prov.txtCedula.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(cataprov.TablaProveedores);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == cataprov.txtBuscarProveedores) {

            filtro(cataprov.txtBuscarProveedores.getText(), cataprov.TablaProveedores);
        } else {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.cataprov.TablaProveedores.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.cataprov.TablaProveedores.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.cataprov.TablaProveedores.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        cataprov.txtBuscarProveedores.setText(String.valueOf(dato));

        modpro.setCedula(String.valueOf(dato));

        modpro.buscar(modpro);

        prov.setVisible(true);
        prov.txtCedula.setText(modpro.getCedula());
        prov.txtContacto.setText(modpro.getContacto());
        prov.txtCorreo.setText(modpro.getCorreo());
        prov.txtNombre.setText(modpro.getNombre());
        prov.txtTelefono.setText(modpro.getTelefono());
        prov.txaDireccion.setText(modpro.getDireccion());
        prov.txtCedula.setEnabled(false);

        prov.btnGuardar.setEnabled(false);

        prov.btnModificar.setEnabled(true);
        prov.btnEliminar.setEnabled(true);

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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

}
