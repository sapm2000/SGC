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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Fondo;
import vista.PantallaPrincipal1;
import vista.catalogoFondo;
import vista.fondo;

/**
 *
 * @author rma
 */
public class controladorFondo implements ActionListener, MouseListener, KeyListener, WindowListener {

    private fondo fon;
    private catalogoFondo catafon;
    private Fondo modfon;
    private PantallaPrincipal1 panta1;
    ArrayList<Fondo> listafondo;
    DefaultTableModel dm;
    double montoi;
    double saldo;

    public controladorFondo(fondo fon, catalogoFondo catafon, Fondo modfon, PantallaPrincipal1 panta1) {
        this.fon = fon;
        this.catafon = catafon;
        this.modfon = modfon;
        this.panta1 = panta1;
        this.catafon.addWindowListener(this);

        this.catafon.jButton2.addActionListener(this);
        this.catafon.jTextField1.addKeyListener(this);
        this.catafon.jTable1.addMouseListener(this);

        this.fon.btnGuardar.addActionListener(this);
        this.fon.btnLimpiar.addActionListener(this);
        this.fon.btnModificar.addActionListener(this);
        this.fon.btnEliminar.addActionListener(this);
        this.fon.txtMontoInicial.addKeyListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        listafondo = modfon.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);

        modeloT.addColumn("Fecha");
        modeloT.addColumn("Tipo");
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Observación");
        modeloT.addColumn("Monto Inicial");
        modeloT.addColumn("Saldo Actual");

        Object[] columna = new Object[6];

        int numRegistro = listafondo.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listafondo.get(i).getFecha();
            columna[1] = listafondo.get(i).getTipo();
            columna[2] = listafondo.get(i).getDescripcion();
            columna[3] = listafondo.get(i).getObservacion();
            columna[4] = Validacion.formato1.format(listafondo.get(i).getMonto_inicial());
            columna[5] = listafondo.get(i).getSaldo();

            modeloT.addRow(columna);

        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(4).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(5).setCellRenderer(tcr);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catafon.jButton2) {
            this.fon.setVisible(true);
            this.fon.btnModificar.setEnabled(false);
            this.fon.btnGuardar.setEnabled(true);
            this.fon.btnEliminar.setEnabled(false);
            this.fon.txtTipo.setEnabled(true);
            fon.txaDescripcion.setText("");
            fon.txaObservaciones.setText("");
            fon.txtMontoInicial.setText("");
            fon.txtTipo.setText("");
            fon.jDateChooser1.setDate(null);

        }

        if (e.getSource() == fon.btnGuardar) {
            if (validar()) {
                modfon.setTipo(fon.txtTipo.getText());
                java.sql.Date sqlDate = convert(fon.jDateChooser1.getDate());
                modfon.setFecha(sqlDate);
                modfon.setDescripcion(fon.txaDescripcion.getText());
                modfon.setObservacion(fon.txaObservaciones.getText());
                modfon.setMonto_inicial(Double.parseDouble(fon.txtMontoInicial.getText()));
                modfon.setId_condominio(panta1.rif.getText());
                if (modfon.buscar(modfon)) {
                    JOptionPane.showMessageDialog(null, "este fondo ya esta registrado");
                } else {

                    if (modfon.registrar(modfon)) {

                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        Llenartabla(catafon.jTable1);

                    } else {

                        JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                    }
                }
            }

        }

        if (e.getSource() == fon.btnModificar) {
            if (validar()) {
                modfon.setTipo(fon.txtTipo.getText());
                java.sql.Date sqlDate = convert(fon.jDateChooser1.getDate());
                modfon.setFecha(sqlDate);
                modfon.setDescripcion(fon.txaDescripcion.getText());
                modfon.setObservacion(fon.txaObservaciones.getText());
                modfon.setMonto_inicial(Double.parseDouble(fon.txtMontoInicial.getText()));
                modfon.setId_condominio(panta1.rif.getText());
                modfon.setId(Integer.parseInt(fon.txtId.getText()));
                int var7 = 0;
                var7 = modfon.getId();

                if (modfon.buscar1(modfon)) {
                    if (var7 == modfon.getId()) {
                        double var1 = Double.parseDouble(fon.txtMontoInicial.getText());
                        double var2 = var1 - montoi;
                        double total = var2 + saldo;
                        modfon.setSaldo(total);

                        if (total > 0) {
                            if (modfon.modificar(modfon)) {

                                JOptionPane.showMessageDialog(null, "Registro Modificado");
                                Llenartabla(catafon.jTable1);
                                fon.dispose();

                            } else {

                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "el saldo no puede ser negativo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "este registro ya existe");
                    }

                } else {

                    double var1 = Double.parseDouble(fon.txtMontoInicial.getText());
                    double var2 = var1 - montoi;
                    double total = var2 + saldo;
                    modfon.setSaldo(total);

                    if (total > 0) {
                        if (modfon.modificar(modfon)) {

                            JOptionPane.showMessageDialog(null, "Registro Modificado");
                            Llenartabla(catafon.jTable1);
                            fon.dispose();

                        } else {

                            JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "el saldo no puede ser negativo");
                    }
                }

            }

        }

        if (e.getSource() == fon.btnEliminar) {

            modfon.setId_condominio(panta1.rif.getText());
            modfon.setTipo(fon.txtTipo.getText());

            if (saldo == 0) {
                if (modfon.eliminar(modfon)) {

                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    fon.dispose();
                    Llenartabla(catafon.jTable1);

                } else {

                    JOptionPane.showMessageDialog(null, "Error al Eliminar");

                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar un fondo con saldo mayor a 0");
            }

        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (fon.txaDescripcion.getText().isEmpty()) {

            msj += "El campo descripción no puede estar vacío\n";
            resultado = false;
        }

        if (fon.txtMontoInicial.getText().isEmpty()) {

            msj += "El campo monto inicial no puede estar vacío\n";
            resultado = false;
        }

        if (fon.txtTipo.getText().isEmpty()) {

            msj += "El campo tipo no puede estar vacío\n";
            resultado = false;
        }

        if (fon.txaObservaciones.getText().isEmpty()) {

            msj += "El campo observaciones no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.catafon.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada

        String dato = String.valueOf(this.catafon.jTable1.getValueAt(fila, 1)); // por ultimo, obtengo el valor de la celda

        modfon.setTipo(String.valueOf(dato));
        modfon.setId_condominio(panta1.rif.getText());
        this.fon.setVisible(true);
        fon.txtTipo.setEnabled(true);
        fon.btnGuardar.setEnabled(false);
        fon.btnEliminar.setEnabled(true);
        fon.btnModificar.setEnabled(true);

        modfon.buscar(modfon);

        fon.txaDescripcion.setText(modfon.getDescripcion());
        fon.txaObservaciones.setText(modfon.getObservacion());
        fon.txtId.setText(String.valueOf(modfon.getId()));
        fon.txtTipo.setText(modfon.getTipo());
        fon.jDateChooser1.setDate(modfon.getFecha());
        fon.txtMontoInicial.setText(String.valueOf(modfon.getMonto_inicial()));
        saldo = modfon.getSaldo();
        montoi = modfon.getMonto_inicial();

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
        if (ke.getSource() == fon.txtMontoInicial) {

            Validacion.Espacio(ke);
            Validacion.soloUnPunto(ke, fon.txtMontoInicial.getText());
            Validacion.limite(ke, fon.txtMontoInicial.getText(), 12);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public static int contarCaracteres(String cadena, char caracter) {
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        return contador;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catafon.jTextField1) {

            filtro(catafon.jTextField1.getText(), catafon.jTable1);
        } else {

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        modfon.setId_condominio(panta1.rif.getText());
        Llenartabla(catafon.jTable1);
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

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }
}
