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
import static java.lang.String.valueOf;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Comunicados;
import modelo.CrudUsuario;
import vista.catalogoComunicados;
import vista.comunicados;

/**
 *
 * @author rma
 */
public class controladorComunicados implements ActionListener, KeyListener, WindowListener, MouseListener {

    private catalogoComunicados catacom;
    private comunicados com;
    private Comunicados modco;
   
    ArrayList<CrudUsuario> listausuarios;
    private CrudUsuario modus;
    ArrayList<Comunicados> listacomunicados;
    DefaultTableModel dm;
    ArrayList<Comunicados> listausuariosmod;

    public controladorComunicados() {
        this.catacom = new catalogoComunicados();
        this.com = new comunicados();
        this.modco = new Comunicados();
      
        this.modus = new CrudUsuario();
        this.catacom.addWindowListener(this);
        this.catacom.jTable1.addMouseListener(this);
        this.com.txtBuscarPropietarios.addKeyListener(this);

        this.catacom.jButton2.addActionListener(this);
        this.catacom.jTextField1.addKeyListener(this);

        this.com.btnEnviar.addActionListener(this);
        this.catacom.setVisible(true);

    }

    public void Llenartabla(JTable tablaD) {

        listausuarios = modus.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = false;
                }
                if (column == 3) {
                    resu = false;
                }
                if (column == 4) {
                    resu = false;
                }
                if (column == 5) {
                    resu = true;
                }
                return resu;
            }
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Cédula/<br>Rif</html>");
        modeloT.addColumn("<html>Nombre/<br>Razón Social</html>");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Tipo");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[6];

        int numRegistro = listausuarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listausuarios.get(i).getCedula();
            columna[1] = listausuarios.get(i).getNombre();
            columna[2] = listausuarios.get(i).getApellido();
            columna[3] = listausuarios.get(i).getNtelefono();
            columna[4] = listausuarios.get(i).getTipo();

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

    public void Llenartablamod(JTable tablaD) {

        listausuariosmod = modco.listarmod();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = false;
                }
                if (column == 3) {
                    resu = false;
                }
                if (column == 4) {
                    resu = false;
                }
                if (column == 5) {
                    resu = true;
                }
                return resu;
            }
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Cédula/<br>Rif</html>");
        modeloT.addColumn("<html>Nombre/<br>Razón Social</html>");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Tipo");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[6];

        int numRegistro = listausuariosmod.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listausuariosmod.get(i).getCedula();

            columna[1] = listausuariosmod.get(i).getNombre();
            columna[2] = listausuariosmod.get(i).getApellido();
            columna[3] = listausuariosmod.get(i).getNtelefono();
            columna[4] = listausuariosmod.get(i).getTipo();

            if (listausuariosmod.get(i).getId() != 0) {
                columna[5] = Boolean.TRUE;
            } else {
                columna[5] = Boolean.FALSE;
            }

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

    public void llenartablaComunicado(JTable tablaD) {

        listacomunicados = modco.listarComunicado();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("<html>Nº de <br> Comunicado</html>");
        modeloT.addColumn("Asunto");
        modeloT.addColumn("Enviado");
        modeloT.addColumn("Leído");

        Object[] columna = new Object[4];

        int numRegistro = listacomunicados.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listacomunicados.get(i).getId();
            columna[1] = listacomunicados.get(i).getAsunto();
            columna[2] = listacomunicados.get(i).getEnviado();
            columna[3] = listacomunicados.get(i).getLeido();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catacom.jButton2) {
            this.com.setVisible(true);
            com.txtid.setVisible(false);

            Llenartabla(com.jTable1);
            addCheckBox(5, com.jTable1);

        }

        if (e.getSource() == com.btnEnviar) {

            if (validar()) {
                int j = 0;
                modco.setAsunto(com.txtAsunto.getText());
                modco.setMensaje(com.txaMensaje.getText());
              
                for (int i = 0; i < com.jTable1.getRowCount(); i++) {
                    if (valueOf(com.jTable1.getValueAt(i, 5)) == "true") {

                        j = j + 1;

                    }
                }
                if (j == 0) {
                    JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                } else {

                    if (modco.registrarcomunicados(modco)) {

                        JOptionPane.showMessageDialog(null, "Mensaje Enviado");

                        modco.buscId(modco);

                        for (int i = 0; i < com.jTable1.getRowCount(); i++) {
                            if (valueOf(com.jTable1.getValueAt(i, 5)) == "true") {

                                String valor = String.valueOf(com.jTable1.getValueAt(i, 0));
                                modco.setId_usuario(valor);
                                modco.setLeido(0);

                                modco.registrar_comunicados_usuarios(modco);

                            }
                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }

        }

    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (com.txtAsunto.getText().isEmpty()) {

            msj += "El campo de asunto no puede estar vacío\n";
            resultado = false;
        }

        if (com.txaMensaje.getText().isEmpty()) {

            msj += "El campo de mensaje no puede estar vacío\n";
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catacom.jTextField1) {

            filtro(catacom.jTextField1.getText(), catacom.jTable1);
        }

        if (e.getSource() == com.txtBuscarPropietarios) {

            filtro(com.txtBuscarPropietarios.getText(), com.jTable1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
       
        llenartablaComunicado(catacom.jTable1);
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
    public void mouseClicked(MouseEvent e) {
        int fila = this.catacom.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada

        String dato = String.valueOf(this.catacom.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
        modco.setId(Integer.parseInt(dato));
       
        modco.buscarComunicado(modco);
        this.com.setVisible(true);
        com.txtid.setVisible(false);
        com.txtid.setText(dato);
        com.txtAsunto.setText(modco.getAsunto());
        com.txaMensaje.setText(modco.getMensaje());
        Llenartablamod(com.jTable1);
        addCheckBox(5, com.jTable1);
        com.btnEnviar.setEnabled(false);
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
