package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Bitacora;
import vista.Catalogo;
import vista.VisBitacora;

public class CtrlBitacora implements ActionListener, KeyListener, MouseListener {

    private Catalogo catalogo;
    private VisBitacora vista;
    private Bitacora modelo;

    DefaultTableModel dm;
    ArrayList<Bitacora> listar;

    public CtrlBitacora() {

        this.catalogo = new Catalogo();
        this.vista = new VisBitacora();
        this.modelo = new Bitacora();

        this.catalogo.tabla.addMouseListener(this);
        this.catalogo.txtBuscar.addKeyListener(this);

        catalogo.lblTitulo.setText("Bitácora");
        CtrlVentana.cambiarVista(catalogo);
        llenarTabla(catalogo.tabla);
        catalogo.btnNuevo.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void llenarTabla(JTable tablaD) {

        listar = modelo.listar();
        dm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaD.setRowSorter(tr);
        tablaD.setModel(dm);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);

        dm.addColumn("Operación");
        dm.addColumn("Tabla");
        dm.addColumn("Usuario");
        dm.addColumn("Valor Viejo");
        dm.addColumn("Valor Nuevo");
        dm.addColumn("Fecha");

        int ind;
        Object[] columna = new Object[dm.getColumnCount()];

        int numRegistro = listar.size();

        for (int i = 0; i < numRegistro; i++) {
            ind = 0;

            columna[ind++] = listar.get(i).getOperacion();
            columna[ind++] = listar.get(i).getTabla();
            columna[ind++] = listar.get(i).getUsuario().getUsuario();
            columna[ind++] = listar.get(i).getValor_viejo();
            columna[ind++] = listar.get(i).getValor_nuevo();
            columna[ind++] = listar.get(i).getFecha_hora();

            dm.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < dm.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }

    }

    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));

    }

    @Override
    public void keyTyped(KeyEvent e) {
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
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == catalogo.tabla) {

            int fila = this.catalogo.tabla.getSelectedRow(); 
            int columna = this.catalogo.tabla.getSelectedColumn();
            String dato = String.valueOf(this.catalogo.tabla.getValueAt(fila, columna));
            
            modelo = listar.get(fila);

            
            vista.txtTabla.setText(modelo.getTabla());
            vista.txtUsuario.setText(modelo.getUsuario().getUsuario());
            vista.txtFecha.setText(modelo.getFecha_hora().toString());
            vista.txtOperacion.setText(modelo.getOperacion());
            vista.txtValorV.setText(modelo.getValor_viejo());
            vista.txtValorN.setText(modelo.getValor_nuevo());

            CtrlVentana.cambiarVista(vista);
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
}
