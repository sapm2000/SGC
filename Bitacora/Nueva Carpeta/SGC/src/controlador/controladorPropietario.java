/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Propietarios;
import vista.PantallaPrincipal1;
import vista.catalogoPropietarios;
import vista.propietarios;

/**
 *
 * @author rma
 */
public class controladorPropietario implements ActionListener, MouseListener, KeyListener, WindowListener {

    private propietarios pro;
    private catalogoPropietarios catapro;
    private Propietarios modpro;
    private PantallaPrincipal1 panta1;
    ArrayList<Propietarios> listaPropietarios;
    DefaultTableModel dm;

    public controladorPropietario(propietarios pro, catalogoPropietarios catapro, Propietarios modpro, PantallaPrincipal1 panta1) {
        this.pro = pro;
        this.catapro = catapro;
        this.modpro = modpro;
        this.panta1 = panta1;
        this.catapro.btn_NuevoPropietario.addActionListener(this);
        this.catapro.addWindowListener(this);
        this.catapro.txtBuscarPropietarios.addKeyListener(this);
        this.catapro.TablaPropietarios.addMouseListener(this);
        this.pro.btnGuardar.addActionListener(this);
        this.pro.btnLimpiar.addActionListener(this);
        this.pro.btnModificar.addActionListener(this);
        this.pro.btnEliminar.addActionListener(this);
        pro.txtCedula.addKeyListener(this);
        pro.txtNombre.addKeyListener(this);
        pro.txtApellido.addKeyListener(this);
        pro.txtTelefono.addKeyListener(this);
        pro.txtCorreo.addKeyListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        listaPropietarios = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Cédula/<br>Rif</html>");
        modeloT.addColumn("<html>Nombre/<br>Razón Social</html>");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("<html>Correo <br> Electrónico</html>");

        Object[] columna = new Object[5];

        int numRegistro = listaPropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaPropietarios.get(i).getCedula();
            columna[1] = listaPropietarios.get(i).getNombre();
            columna[2] = listaPropietarios.get(i).getApellido();
            columna[3] = listaPropietarios.get(i).getTelefono();
            columna[4] = listaPropietarios.get(i).getCorreo();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catapro.btn_NuevoPropietario) {
            this.pro.setVisible(true);
            this.pro.btnModificar.setEnabled(false);
            this.pro.btnGuardar.setEnabled(true);
            this.pro.btnEliminar.setEnabled(false);
            this.pro.txtCedula.setEnabled(true);
            this.catapro.addWindowListener(this);
            pro.txtCedula.setText("");
            pro.txtApellido.setText("");
            pro.txtCorreo.setText("");
            pro.txtNombre.setText("");
            pro.txtTelefono.setText("");

        }

        if (e.getSource() == pro.btnGuardar) {
            if (validar()) {
                modpro.setCedula(pro.txtCedula.getText());
                modpro.setNombre(pro.txtNombre.getText());
                modpro.setApellido(pro.txtApellido.getText());
                modpro.setCorreo(pro.txtCorreo.getText());
                modpro.setTelefono(pro.txtTelefono.getText());
                modpro.setId_condominio(panta1.rif.getText());
                

                if (modpro.buscarepeprop(modpro)) {
                    JOptionPane.showMessageDialog(null, "Este condiminio ya tiene esta cedula asignada");
                } else {

                if (modpro.registrar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catapro.TablaPropietarios);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }
            }

        }

        if (e.getSource() == pro.btnModificar) {
            if (validar()) {
                modpro.setCedula(pro.txtCedula.getText());
                modpro.setNombre(pro.txtNombre.getText());
                modpro.setApellido(pro.txtApellido.getText());
                modpro.setCorreo(pro.txtCorreo.getText());
                modpro.setTelefono(pro.txtTelefono.getText());

                if (modpro.modificar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    Llenartabla(catapro.TablaPropietarios);
                    pro.dispose();

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == pro.btnEliminar) {

            if (modpro.eliminar(modpro)) {

                modpro.setCedula(pro.txtCedula.getText());
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                pro.dispose();
                Llenartabla(catapro.TablaPropietarios);

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
        if (e.getSource() == pro.btnLimpiar) {

            limpiar();

        }
    }

    public void limpiar() {

        pro.txtCedula.setText(null);
        pro.txtNombre.setText(null);
        pro.txtApellido.setText(null);
        pro.txtTelefono.setText(null);
        pro.txtCorreo.setText(null);
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (pro.txtCedula.getText().isEmpty()) {

            msj += "El campo C.I./RIF. no puede estar vacío\n";
            resultado = false;
        }

        if (pro.txtNombre.getText().isEmpty()) {

            msj += "El campo nombre no puede estar vacío\n";
            resultado = false;
        }

        if (pro.txtApellido.getText().isEmpty()) {

            msj += "El campo apellido no puede estar vacío\n";
            resultado = false;
        }

        if (pro.txtTelefono.getText().isEmpty()) {

            msj += "El campo teléfono no puede estar vacío\n";
            resultado = false;
        }

        if (pro.txtCorreo.getText().isEmpty()) {

            msj += "El campo correo electrónico no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catapro.TablaPropietarios.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catapro.TablaPropietarios.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catapro.TablaPropietarios.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        catapro.txtBuscarPropietarios.setText(String.valueOf(dato));

        modpro.setCedula(String.valueOf(dato));

        modpro.buscar(modpro);

        pro.setVisible(true);
        pro.txtCedula.setText(modpro.getCedula());
        pro.txtApellido.setText(modpro.getApellido());
        pro.txtCorreo.setText(modpro.getCorreo());
        pro.txtNombre.setText(modpro.getNombre());
        pro.txtTelefono.setText(modpro.getTelefono());

        pro.txtCedula.setEnabled(false);

        pro.btnGuardar.setEnabled(false);

        pro.btnModificar.setEnabled(true);
        pro.btnEliminar.setEnabled(true);
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
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == pro.txtCedula) {
            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, pro.txtCedula.getText(), 8);
        }
        if (ke.getSource() == pro.txtNombre) {

            Validacion.soloLetras(ke);
            
            Validacion.limite(ke, pro.txtNombre.getText(), 30);
        }
        if (ke.getSource() == pro.txtApellido) {

            Validacion.soloLetras(ke);

            Validacion.limite(ke, pro.txtApellido.getText(), 100);
        }
        if (ke.getSource() == pro.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, pro.txtTelefono.getText(), 11);
        }
        if (ke.getSource() == pro.txtCorreo) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, pro.txtCorreo.getText(), 100);

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catapro.txtBuscarPropietarios) {

            filtro(catapro.txtBuscarPropietarios.getText(), catapro.TablaPropietarios);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        modpro.setId_condominio(panta1.rif.getText());
        Llenartabla(catapro.TablaPropietarios);

        Component[] components =pro.jPanel2.getComponents();
        JComponent[] com = {
            pro.txtCedula,pro.txtNombre, pro.txtApellido, pro.txtTelefono, pro.txtCorreo
        };
        Validacion.copiar(components);
        Validacion.pegar(com);
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

}
