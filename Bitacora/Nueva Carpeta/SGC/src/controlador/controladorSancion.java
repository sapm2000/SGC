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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Sancion;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.catalogoSancion;
import vista.sancion;

/**
 *
 * @author rma
 */
public class controladorSancion implements ActionListener, MouseListener, KeyListener, WindowListener {

    private sancion san;
    private catalogoSancion catasan;
    private Sancion modsan;
    private PantallaPrincipal1 panta1;
    ArrayList<Unidades> listaunidades;
    ArrayList<Sancion> listaSancion;

    public controladorSancion(sancion san, catalogoSancion catasan, Sancion modsan, PantallaPrincipal1 panta1) {
        this.san = san;
        this.catasan = catasan;
        this.modsan = modsan;
        this.panta1 = panta1;

        this.catasan.jButton2.addActionListener(this);
        this.catasan.addWindowListener(this);

        this.san.btnGuardar.addActionListener(this);
        this.san.btnLimpiar.addActionListener(this);
        this.san.btnModificar.addActionListener(this);
    }
    
    public void LlenartablaSancion(JTable tablaD) {

        listaSancion = modsan.listarSanciones();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Nº de sancion");
        modeloT.addColumn("Descripcion");
        modeloT.addColumn("Tipo de deuda");
        modeloT.addColumn("Monto/Porcentaje");
        modeloT.addColumn("Mes a aplicar");
        modeloT.addColumn("Nº de unidades");
        modeloT.addColumn("Estatus");

        Object[] columna = new Object[7];

        int numRegistro = listaSancion.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaSancion.get(i).getId();
            columna[1] = listaSancion.get(i).getDescripcion();
            columna[2] = listaSancion.get(i).getTipo();
            columna[3] = listaSancion.get(i).getMonto();
            String fecha = String.valueOf(listaSancion.get(i).getMes())+"-"+String.valueOf(listaSancion.get(i).getAño());
            columna[4] = fecha;
            columna[5] = listaSancion.get(i).getCantidad_de_unidades();
            columna[6] = listaSancion.get(i).getEstado();

            modeloT.addRow(columna);

        }

    }

    public void llenartablaunidades(JTable tablaD) {

        listaunidades = modsan.buscarUnidades();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Numero Unidad");
        modeloT.addColumn("Seleccione");

        Object[] columna = new Object[2];

        int numRegistro = listaunidades.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaunidades.get(i).getN_unidad();

            modeloT.addRow(columna);

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catasan.jButton2) {
            this.san.setVisible(true);
            this.san.btnModificar.setEnabled(false);
            this.san.btnGuardar.setEnabled(true);
            this.san.btnEliminar.setEnabled(false);
            modsan.setId_condominio(panta1.rif.getText());
            llenartablaunidades(san.jTable1);
            addCheckBox(1, san.jTable1);

        }

        if (e.getSource() == san.btnGuardar) {
            if (validar()) {
                modsan.setMes(san.jMonthChooser1.getMonth() + 1);
                modsan.setAño(san.jYearChooser1.getYear());
                modsan.setTipo(san.jComboBox1.getSelectedItem().toString());
                modsan.setDescripcion(san.txaDescripcion.getText());
                modsan.setId_condominio(panta1.rif.getText());
                modsan.setMonto(Double.parseDouble(san.txtmonto.getText()));
                modsan.setEstado("Pendiente");
                
                if (modsan.registrarsancion(modsan)) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    modsan.buscId(modsan);
                    
                    for (int i = 0; i < san.jTable1.getRowCount(); i++) {
                        if (valueOf(san.jTable1.getValueAt(i, 1)) == "true") {

                            String valor = String.valueOf(san.jTable1.getValueAt(i, 0));
                            modsan.setN_unidad(valor);

                            modsan.registrar_sancion_unidad(modsan);

                        }
                    }
                    LlenartablaSancion(catasan.jTable1);
                }
             else {

                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

            }

        }
        }

        if (e.getSource() == san.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
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
        modsan.setId_condominio(panta1.rif.getText());
        LlenartablaSancion(catasan.jTable1);
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

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
    
     private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (san.txtmonto.getText().isEmpty()) {

            msj += "El campo numero de Cuenta no puede estar vacio\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }
}
