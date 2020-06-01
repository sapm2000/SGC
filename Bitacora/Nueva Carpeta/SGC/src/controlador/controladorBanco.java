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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Banco;
import modelo.Funcion;
import sgc.SGC;
import vista.Catalogo;
import vista.banco;

public class controladorBanco implements ActionListener, MouseListener, KeyListener, WindowListener {

    private banco vista;
    private Catalogo catalogo;
    private Banco modban;
    private Funcion permiso;

    DefaultTableModel dm;
    DefaultComboBoxModel dmCbx;
    ArrayList<Banco> listaBanco;

    public controladorBanco() {
        this.vista = new banco();
        this.catalogo = new Catalogo();
        this.modban = new Banco();

        catalogo.lblTitulo.setText("Banco");
        
        this.catalogo.btnNuevo.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.tabla.addKeyListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);
        this.catalogo.addWindowListener(this);
        this.vista.txtnombre_banco.addKeyListener(this);
        catalogo.setVisible(true);

    }

    public void Llenartabla(JTable tablaD) {

        listaBanco = modban.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Nombre del Banco");

        Object[] columna = new Object[1];

        int numRegistro = listaBanco.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaBanco.get(i).getNombre_banco();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        tablaD.getColumnModel()
                .getColumn(0).setCellRenderer(tcr);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catalogo.btnNuevo) {

            this.vista.setVisible(true);
            this.vista.btnEliminar.setEnabled(false);
            this.vista.btnGuardar.setEnabled(true);
            this.vista.txtid.setVisible(false);
            this.vista.btnModificar.setEnabled(false);
            vista.txtnombre_banco.setText("");
            vista.txtid.setText("");

        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modban.setNombre_banco(vista.txtnombre_banco.getText());
                if (modban.buscarInactivo(modban)) {
                    modban.activar(modban);
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catalogo.tabla);
                    limpiar();
                } else {

                    if (modban.registrar(modban)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(catalogo.tabla);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }

        }

        if (e.getSource() == vista.btnEliminar) {

            modban.setId(Integer.parseInt(vista.txtid.getText()));

            if (modban.buscacuentas(modban)) {
                JOptionPane.showMessageDialog(null, "No puede eliminar el banco porque tiene cuentas asignadas");
            } else {

                if (modban.eliminar(modban)) {

                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    vista.dispose();
                    Llenartabla(catalogo.tabla);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }

            }
        }

        if (e.getSource() == vista.btnModificar) {
            if (validar()) {
                modban.setNombre_banco(vista.txtnombre_banco.getText());
                modban.setId(Integer.parseInt(vista.txtid.getText()));

                if (modban.buscarInactivo(modban)) {

                    JOptionPane.showMessageDialog(null, "no puede colocar el nombre de un banco que ya existio, si quiere colocar este nombre debe registrarlo nuevamente");

                } else {
                    if (modban.modificar(modban)) {

                        JOptionPane.showMessageDialog(null, "Registro modificado");
                        vista.dispose();
                        Llenartabla(catalogo.tabla);
                        limpiar();

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                    }
                }
            }

        }

        if (e.getSource() == vista.btnLimpiar) {

            limpiar();

        }

    }

    public void limpiar() {

        vista.txtnombre_banco.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.catalogo.tabla.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catalogo.tabla.getSelectedColumn(); // luego, obtengo la columna seleccionada
        if (permiso.getModificar()) {
            vista.btnModificar.setEnabled(true);
        }
        if (permiso.getEliminar()) {
            vista.btnEliminar.setEnabled(true);
        }
        String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda
        catalogo.txtBuscar.setText(String.valueOf(dato));

        modban.setNombre_banco(String.valueOf(dato));

        modban.buscar(modban);

        vista.setVisible(true);
        vista.txtnombre_banco.setText(modban.getNombre_banco());

        vista.txtid.setText(Integer.toString(modban.getId()));

        vista.btnGuardar.setEnabled(false);
        vista.txtid.setVisible(false);
        vista.btnModificar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
    }
    
        private void permisoBtn() {

        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;

            }

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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    @Override
    public void keyTyped(KeyEvent ke) {

        if (ke.getSource() == vista.txtnombre_banco) {
            Validacion.soloLetras(ke);
            Validacion.limite(ke, vista.txtnombre_banco.getText(), 30);

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == catalogo.txtBuscar) {

            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catalogo.tabla);
        permisoBtn();

        if (permiso.getRegistrar()) {
            catalogo.btnNuevo.setEnabled(true);
        }
        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtnombre_banco
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

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vista.txtnombre_banco.getText().isEmpty()) {

            msj += "El campo nombre del banco no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

}
