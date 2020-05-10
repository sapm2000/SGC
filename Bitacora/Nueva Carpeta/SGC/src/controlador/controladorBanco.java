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
import static java.lang.String.valueOf;
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
import vista.banco;
import vista.catalogoBanco;
import vista.catalogoInactivoBanco;

/**
 *
 * @author rma
 */
public class controladorBanco implements ActionListener, MouseListener, KeyListener, WindowListener {

    private banco ban;
    private catalogoBanco cban;
    private Banco modban;
    private catalogoInactivoBanco ciban;
    DefaultTableModel dm;
    DefaultComboBoxModel dmCbx;
    ArrayList<Banco> listaBanco;

    public controladorBanco(banco ban, catalogoBanco cban, Banco modban, catalogoInactivoBanco ciban) {
        this.ban = ban;
        this.cban = cban;
        this.modban = modban;
        this.ciban = ciban;

        //crearCbxBanco(modban.listar());
        //CrearCbx(ban.cbxBanco, modban.listar());      
        this.ciban.btnActivar.addActionListener(this);
        this.cban.btnNuevo_banco.addActionListener(this);
        this.cban.btnDesactivar.addActionListener(this);
        this.ban.btnGuardar.addActionListener(this);
        this.ban.btnLimpiar.addActionListener(this);
        this.ban.btnEliminar.addActionListener(this);
        this.ban.btnModificar.addActionListener(this);
        this.cban.tabla_bancos.addMouseListener(this);
        this.cban.tabla_bancos.addKeyListener(this);
        this.cban.txtBuscar_banco.addKeyListener(this);
        this.cban.addWindowListener(this);
        this.ban.txtnombre_banco.addKeyListener(this);

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

    public void Llenartablainactivos(JTable tablaD) {

        listaBanco = modban.listarinactivos();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = true;
                }

               
                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Nombre del Banco");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaBanco.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaBanco.get(i).getNombre_banco();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
    }

    /*public void CrearCbx(JComboBox comboD, ArrayList<Banco> dato) {
        
        
        DefaultComboBoxModel modelot = new DefaultComboBoxModel();
        comboD.setModel(modelot);

        modelot.addElement("Nombre ");

        //Object[] columna = new Object[1];

        int numRegistro = dato.size();

        for (int i = 0; i < numRegistro; i++) {

            //columna[0] = modban.listar().get(i).getNombre_banco();

            modelot.addElement(dato.get(i).getNombre_banco());

        }

    }
     */
 /*private void crearCbxBanco(ArrayList<Banco> datos) {
        ban.cbxBanco.addItem("Seleccione...");

        if (datos != null) {
            for (Banco datosX : datos) {
                modban = datosX;
                ban.cbxBanco.addItem(modban.getNombre_banco());
            }

            
        }
    }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==ciban.btnActivar) {
              listaBanco = modban.listarinactivos();

            for (int i = 0; i < ciban.jTable1.getRowCount(); i++) {
                if (valueOf(ciban.jTable1.getValueAt(i, 1)) == "true") {

                    modban.setId(listaBanco.get(i).getId());
                    modban.activar(modban);

                }
            }
            Llenartablainactivos(ciban.jTable1);
            addCheckBox(1, ciban.jTable1);
            Llenartabla(cban.tabla_bancos);
        }
        if (e.getSource()==cban.btnDesactivar) {
            this.ciban.setVisible(true);
            Llenartablainactivos(ciban.jTable1);
            addCheckBox(1, ciban.jTable1);
        }

        if (e.getSource() == cban.btnNuevo_banco) {

            this.ban.setVisible(true);
            this.ban.btnEliminar.setEnabled(false);
            this.ban.btnGuardar.setEnabled(true);
            this.ban.txtid.setVisible(false);
            this.ban.btnModificar.setEnabled(false);
            ban.txtnombre_banco.setText("");
            ban.txtid.setText("");

        }

        if (e.getSource() == ban.btnGuardar) {
            if (validar()) {
                modban.setNombre_banco(ban.txtnombre_banco.getText());

                if (modban.registrar(modban)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(cban.tabla_bancos);
                    limpiar();

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == ban.btnEliminar) {

            modban.setId(Integer.parseInt(ban.txtid.getText()));

            if (modban.buscacuentas(modban)) {
                JOptionPane.showMessageDialog(null, "No puede eliminar el banco porque tiene cuentas asignadas");
            } else {

                if (modban.eliminar(modban)) {

                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    ban.dispose();
                    Llenartabla(cban.tabla_bancos);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }

            }
        }

        if (e.getSource() == ban.btnModificar) {
            if (validar()) {
                modban.setNombre_banco(ban.txtnombre_banco.getText());
                modban.setId(Integer.parseInt(ban.txtid.getText()));

                if (modban.modificar(modban)) {

                    JOptionPane.showMessageDialog(null, "Registro modificado");
                    ban.dispose();
                    Llenartabla(cban.tabla_bancos);
                    limpiar();

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                }
            }

        }

        if (e.getSource() == ban.btnLimpiar) {

            limpiar();

        }

    }

    public void limpiar() {

        ban.txtid.setText(null);
        ban.txtnombre_banco.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.cban.tabla_bancos.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.cban.tabla_bancos.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.cban.tabla_bancos.getValueAt(fila, columna)); // por ultimo, obtengo el valor de la celda
        cban.txtBuscar_banco.setText(String.valueOf(dato));

        modban.setNombre_banco(String.valueOf(dato));

        modban.buscar(modban);

        ban.setVisible(true);
        ban.txtnombre_banco.setText(modban.getNombre_banco());

        ban.txtid.setText(Integer.toString(modban.getId()));

        ban.btnGuardar.setEnabled(false);
        ban.txtid.setVisible(false);
        ban.btnModificar.setEnabled(true);
        ban.btnEliminar.setEnabled(true);
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

        if (ke.getSource() == ban.txtnombre_banco) {
            Validacion.soloLetras(ke);
            Validacion.limite(ke, ban.txtnombre_banco.getText(), 30);

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == cban.txtBuscar_banco) {

            filtro(cban.txtBuscar_banco.getText(), cban.tabla_bancos);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(cban.tabla_bancos);

        Component[] components = ban.jPanel2.getComponents();
        JComponent[] com = {
            ban.txtnombre_banco
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

        if (ban.txtnombre_banco.getText().isEmpty()) {

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
