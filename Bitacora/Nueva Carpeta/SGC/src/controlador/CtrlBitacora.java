package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Bitacora;
import vista.Catalogo;

public class CtrlBitacora implements ActionListener {

    private Catalogo catalogo;
    private Bitacora modelo;

    DefaultTableModel dm;
    ArrayList<Bitacora> listar;

    public CtrlBitacora() {

        this.catalogo = new Catalogo();
        this.modelo = new Bitacora();

        catalogo.lblTitulo.setText("Bitácora");
        CtrlVentana.cambiarVista(catalogo);
        llenarTabla(catalogo.tabla);

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
}
