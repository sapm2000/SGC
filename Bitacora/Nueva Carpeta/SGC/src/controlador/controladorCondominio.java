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
import static java.lang.String.valueOf;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Condominio;
import modelo.Unidades;
import sgc.SGC;
import vista.PantallaPrincipal;
import vista.PantallaPrincipal1;
import vista.catalogoCondominio;
import vista.catalogoInactivoCondominio;
import vista.condominio;

/**
 *
 * @author rma
 */
public class controladorCondominio implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoCondominio cataco;
    private condominio condo;
    private PantallaPrincipal1 panta1;
    private PantallaPrincipal panta;
    private Unidades moduni;
    DefaultTableModel dm;
    private Condominio co;
    private catalogoInactivoCondominio cataico;
    ArrayList<Condominio> listaCondo;
    ArrayList<Unidades> listaunidades;

    public controladorCondominio() {
        this.cataco = new catalogoCondominio();
        this.condo = new condominio();
        this.panta = new PantallaPrincipal();
        this.co = new Condominio();
        this.moduni = new Unidades();
        this.cataico = new catalogoInactivoCondominio();
        this.cataco.btnActivar.addActionListener(this);
        this.cataico.btnActivar.addActionListener(this);
        this.cataco.jButton2.addActionListener(this);
        this.condo.btnGuardar.addActionListener(this);
        this.condo.btnEliminar.addActionListener(this);
        this.condo.btnModificar.addActionListener(this);
        this.condo.btnLimpiar.addActionListener(this);
        this.cataco.jTable1.addMouseListener(this);
        this.cataco.txtBuscar.addKeyListener(this);
        this.cataco.addWindowListener(this);
        this.condo.txtRif.addKeyListener(this);
        this.condo.txtRazonS.addKeyListener(this);
        this.condo.txtTelefono.addKeyListener(this);
        this.condo.txtCorreo.addKeyListener(this);

        cataco.setVisible(true);
    }

    public void Llenartabla(JTable tablaD) {

        listaCondo = co.lPerson();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Rif");
        modeloT.addColumn("Razón Social");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo Electrónico");

        Object[] columna = new Object[4];

        int num = listaCondo.size();

        for (int i = 0; i < num; i++) {

            columna[0] = listaCondo.get(i).getRif();
            columna[1] = listaCondo.get(i).getRazonS();
            columna[2] = listaCondo.get(i).getTelefono();
            columna[3] = listaCondo.get(i).getCorreoElectro();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
    }

    public void Llenartablainactivos(JTable tablaD) {

        listaCondo = co.lPersoni();
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
                    resu = true;
                }

                return resu;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        modeloT.addColumn("Rif");
        modeloT.addColumn("Razón Social");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Correo Electrónico");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[5];

        int num = listaCondo.size();

        for (int i = 0; i < num; i++) {

            columna[0] = listaCondo.get(i).getRif();
            columna[1] = listaCondo.get(i).getRazonS();
            columna[2] = listaCondo.get(i).getTelefono();
            columna[3] = listaCondo.get(i).getCorreoElectro();

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

        if (e.getSource() == cataco.jButton2) {
            this.condo.setVisible(true);
            this.condo.btnModificar.setEnabled(false);
            this.condo.btnGuardar.setEnabled(true);
            this.condo.btnEliminar.setEnabled(false);
            this.condo.txtRif.setEnabled(true);
            condo.txtRif.setText("");
            condo.txtRazonS.setText("");
            condo.txtTelefono.setText("");
            condo.txtCorreo.setText("");
        }

        if (e.getSource() == cataico.btnActivar) {
            listaCondo = co.lPersoni();

            for (int i = 0; i < cataico.jTable1.getRowCount(); i++) {
                if (valueOf(cataico.jTable1.getValueAt(i, 4)) == "true") {

                    co.setRif(listaCondo.get(i).getRif());
                    co.activar(co);

                }
            }
            Llenartablainactivos(cataico.jTable1);
            addCheckBox(4, cataico.jTable1);
            Llenartabla(cataco.jTable1);
        }

        if (e.getSource() == cataco.btnActivar) {
            this.cataico.setVisible(true);
            Llenartablainactivos(cataico.jTable1);
            addCheckBox(4, cataico.jTable1);
        }

        if (e.getSource() == condo.btnGuardar) {
            if (validar()) {
                co.setRif(condo.txtRif.getText());
                co.setRazonS(condo.txtRazonS.getText());
                co.setTelefono(condo.txtTelefono.getText());
                co.setCorreoElectro(condo.txtCorreo.getText());

                if (co.registrar(co)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(cataco.jTable1);

                } else {

                    JOptionPane.showMessageDialog(null, "Este registro ya existe");

                }
            }

        }

        if (e.getSource() == condo.btnModificar) {
            if (validar()) {
                co.setRif(condo.txtRif.getText());
                co.setRazonS(condo.txtRazonS.getText());
                co.setTelefono(condo.txtTelefono.getText());
                co.setCorreoElectro(condo.txtCorreo.getText());

                if (co.modificar(co)) {

                    JOptionPane.showMessageDialog(null, "Registro modificado");
                    condo.dispose();
                    Llenartabla(cataco.jTable1);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Modificar");

                }
            }
        }

        if (e.getSource() == condo.btnEliminar) {
            co.setRif(condo.txtRif.getText());
            if (co.Buscargas(co) || co.Buscarcuo(co)) {
                JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene gastos por procesar asignados");

            } else {
                if (co.Buscarsan(co)) {
                    JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene Sanciones por procesar");
                } else {
                    if (co.Buscarcuen(co)) {
                        JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene Cuentas asignadas");
                    } else {
                        if (co.Buscarin(co)) {
                            JOptionPane.showMessageDialog(null, "no se puede eliminar si tiene Interese asignados");
                        } else {
                            if (co.eliminar(co)) {
                                co.eliminarunidadcondominio(co);
                                moduni.setId_condominio(co.getRif());
                                listaunidades = moduni.buscar();
                                int q = listaunidades.size();

                                for (int i = 0; i < q; i++) {
                                    moduni.setId(listaunidades.get(i).getId());
                                    moduni.eliminarPuenteUnidad(moduni);
                                }

                                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                                condo.dispose();
                                Llenartabla(cataco.jTable1);

                            } else {

                                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                            }
                        }
                    }
                }
            }

        }

        if (e.getSource() == condo.btnLimpiar) {

            condo.txtRif.setText("");
            condo.txtRazonS.setText("");
            condo.txtTelefono.setText("");
            condo.txtCorreo.setText("");

        }

    }

    public void limpiar() {

        condo.txtRif.setText(null);
        condo.txtRazonS.setText(null);
        condo.txtTelefono.setText(null);
        condo.txtCorreo.setText(null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        String[] options = {"Entrar al menu", "Modificar datos"};
        int result = JOptionPane.showOptionDialog(null, "Seleccione si desea entrar al menu o modificar datos", "MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 0) {
            int fila = this.cataco.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            int columna = this.cataco.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
            String dato = String.valueOf(this.cataco.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            co.setRif(String.valueOf(dato));
            SGC.condominioActual = co;
            this.cataco.dispose();
            this.panta1 = new PantallaPrincipal1();
            this.panta1.setVisible(true);
            panta1.rif.setText(dato);

        }
        if (result == 1) {

            int fila = this.cataco.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            int columna = this.cataco.jTable1.getSelectedColumn(); // luego, obtengo la columna seleccionada
            String dato = String.valueOf(this.cataco.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            co.setRif(String.valueOf(dato));

            co.Buscar(co);

            condo.setVisible(true);
            condo.txtRif.setText(co.getRif());
            condo.txtRazonS.setText(co.getRazonS());
            condo.txtTelefono.setText(co.getTelefono());
            condo.txtCorreo.setText(co.getCorreoElectro());
            condo.txtRif.setEnabled(false);

            condo.btnGuardar.setEnabled(false);

            condo.btnModificar.setEnabled(true);
            condo.btnEliminar.setEnabled(true);
        } else {

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
        if (ke.getSource() == condo.txtRif) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, condo.txtRif.getText(), 15);
        }
        if (ke.getSource() == condo.txtRazonS) {

            Validacion.limite(ke, condo.txtRazonS.getText(), 150);
        }
        if (ke.getSource() == condo.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, condo.txtTelefono.getText(), 11);
        }
        if (ke.getSource() == condo.txtCorreo) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, condo.txtCorreo.getText(), 70);

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == cataco.txtBuscar) {
            filtro(cataco.txtBuscar.getText(), cataco.jTable1);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(cataco.jTable1);

        Component[] components = condo.jPanel2.getComponents();
        JComponent[] com = {
            condo.txtRif, condo.txtRazonS, condo.txtTelefono, condo.txtCorreo
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

        if (condo.txtRif.getText().isEmpty()) {

            msj += "El campo rif no puede estar vacío\n";
            resultado = false;
        }
        if (condo.txtRazonS.getText().isEmpty()) {

            msj += "El campo razón social no puede estar vacío\n";
            resultado = false;
        }
        if (condo.txtTelefono.getText().isEmpty()) {

            msj += "El campo teléfono no puede estar vacío\n";
            resultado = false;
        }
        if (condo.txtCorreo.getText().isEmpty()) {

            msj += "El campo correo electrónico no puede estar vacío\n";
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
