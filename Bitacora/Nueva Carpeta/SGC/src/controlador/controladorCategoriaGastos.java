/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CategoriaGasto;
import vista.catalogoCategoriaGastos;
import vista.categoriaGastos;
import controlador.Validacion;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rma
 */
public class controladorCategoriaGastos implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoCategoriaGastos catacg;
    private categoriaGastos cg;
    private CategoriaGasto modcg;
    DefaultTableModel dm;
    ArrayList<CategoriaGasto> listaCatGas;

    public controladorCategoriaGastos(catalogoCategoriaGastos catacg, categoriaGastos cg, CategoriaGasto modcg) {
        this.catacg = catacg;
        this.cg = cg;
        this.modcg = modcg;
       
        this.catacg.btn_nuevaCategoriaGasto.addActionListener(this);
        
        this.cg.btnModificar.addActionListener(this);
        this.cg.btnGuardar.addActionListener(this);
        this.cg.btnLimpiar.addActionListener(this);
        this.cg.btnEliminar.addActionListener(this);
        this.catacg.tabla_categoria_gastos.addMouseListener(this);
        this.catacg.txt_buscarCategoriaGasto.addKeyListener(this);
        this.catacg.addWindowListener(this);
        this.cg.txtnombre.addKeyListener(this);
        this.cg.txtdescripcion.addKeyListener(this);

    }
    


    public void Llenartabla(JTable tablaD) {
        
        listaCatGas = modcg.lCategGas();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
            

        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);

        modeloT.addColumn("Nombre");
        modeloT.addColumn("Descripcion");

        Object[] columna = new Object[2];

        int numRegistro = listaCatGas.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaCatGas.get(i).getNombre();
            columna[1] = listaCatGas.get(i).getDescripcion();

            modeloT.addRow(columna);

        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        
    }

    public void actionPerformed(ActionEvent e) {

       

        if (e.getSource() == catacg.btn_nuevaCategoriaGasto) {
            this.cg.setVisible(true);
            this.cg.btnEliminar.setEnabled(false);
            this.cg.btnGuardar.setEnabled(true);
            this.cg.txtId.setVisible(false);
            this.cg.btnModificar.setEnabled(false);
            cg.txtnombre.setText("");
             cg.txtdescripcion.setText("");
             cg.txtId.setText("");
             

        }

      

       
       

        if (e.getSource() == cg.btnGuardar) {
            if(validar()){
            modcg.setNombre(cg.txtnombre.getText());
            modcg.setDescripcion(cg.txtdescripcion.getText());
            

            if (modcg.registrar(modcg)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");
                Llenartabla(catacg.tabla_categoria_gastos);
                cg.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Registro Duplicado");

            }
            }

        }
        
         if (e.getSource() == cg.btnEliminar) {
           
           

            if (modcg.eliminar(modcg)) {

                modcg.setId(Integer.parseInt(cg.txtId.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                cg.dispose();
                Llenartabla(catacg.tabla_categoria_gastos);
                

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }

        if (e.getSource() == cg.btnModificar) {
            if (validar()) {
                modcg.setNombre(cg.txtnombre.getText());
                modcg.setDescripcion(cg.txtdescripcion.getText());
                modcg.setId(Integer.parseInt(cg.txtId.getText()));

                if (modcg.modificar(modcg)) {

                    JOptionPane.showMessageDialog(null, "Registro modificado");
                    cg.dispose();
                    Llenartabla(catacg.tabla_categoria_gastos);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro ya Existe");

                }
            }
        }
        
        if (e.getSource() == cg.btnLimpiar) {
             
             cg.txtnombre.setText("");
             cg.txtdescripcion.setText("");        
            
        }
        
        

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // primero, obtengo la fila seleccionada

        int fila = this.catacg.tabla_categoria_gastos.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catacg.tabla_categoria_gastos.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catacg.tabla_categoria_gastos.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        modcg.setNombre(String.valueOf(dato));

        modcg.Buscar(modcg);

        cg.setVisible(true);
        cg.txtnombre.setText(modcg.getNombre());
       
        cg.txtdescripcion.setText(modcg.getDescripcion());
        cg.txtId.setText(Integer.toString(modcg.getId()));
        
        cg.btnGuardar.setEnabled(false);
        cg.txtId.setVisible(false);
        cg.btnModificar.setEnabled(true);
        cg.btnEliminar.setEnabled(true);

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
            if (ke.getSource() == cg.txtnombre) {
            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, cg.txtnombre.getText(), 8);
        }
        if (ke.getSource() == cg.txtdescripcion) {

            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, cg.txtdescripcion.getText(), 20);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catacg.txt_buscarCategoriaGasto) {
            
        
        filtro(catacg.txt_buscarCategoriaGasto.getText(), catacg.tabla_categoria_gastos);
        }
        else {
                
                }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catacg.tabla_categoria_gastos);
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

        if (cg.txtnombre.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }
        if (cg.txtdescripcion.getText().isEmpty()) {

            msj += "El campo Descripcion no puede estar vacío\n";
            resultado = false;
        }



        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
