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
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.ConceptoGastos;
import vista.catalogoConceptoGasto;
import vista.conceptoGasto;


public class controladorConceptoGasto implements ActionListener, MouseListener, KeyListener, WindowListener {

    private catalogoConceptoGasto catConGas;
    private conceptoGasto vConcGas;
    private ConceptoGastos modCg;
    
    DefaultTableModel dm;

    public controladorConceptoGasto(catalogoConceptoGasto catConGas, conceptoGasto vConGas, ConceptoGastos modCg) {
        
        this.catConGas = catConGas;
        this.vConcGas = vConGas;
        this.modCg = modCg;
       
        this.catConGas.btnNuevoregistro.addActionListener(this);
        
        this.vConcGas.btnModificar.addActionListener(this);
        this.vConcGas.btnGuardar.addActionListener(this);
        this.vConcGas.btnLimpiar.addActionListener(this);
        this.vConcGas.btnEliminar.addActionListener(this);
        this.catConGas.jTable.addMouseListener(this);
        this.catConGas.txtBuscar.addKeyListener(this);
        this.catConGas.addWindowListener(this);
        this.vConcGas.txtNombreConcepto.addKeyListener(this);
        this.vConcGas.txtDescripcion.addKeyListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catConGas.btnNuevoregistro) {
              
            this.vConcGas.setVisible(true);
            this.vConcGas.btnEliminar.setEnabled(false);
            this.vConcGas.btnGuardar.setEnabled(true);
            this.vConcGas.txtId.setVisible(false);
            this.vConcGas.btnModificar.setEnabled(false);
            vConcGas.txtNombreConcepto.setText("");
            vConcGas.txtDescripcion.setText("");
            vConcGas.txtId.setText("");
             

        }

        if (e.getSource() == vConcGas.btnGuardar) {
         
            if(validar()){
            modCg.setNombreConcepto(vConcGas.txtNombreConcepto.getText());
            modCg.setDescripcion(vConcGas.txtDescripcion.getText());
            modCg.setCategoria(vConcGas.cbxCategoria.getSelectedItem().toString());
            

            if (modCg.registrar(modCg)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");
                Llenartabla(catConGas.jTable);
                

            } else {

                JOptionPane.showMessageDialog(null, "Registro Duplicado");

            }
            }

        }

        if (e.getSource() == vConcGas.btnModificar) {
            
            if(validar()){
             modCg.setNombreConcepto(vConcGas.txtNombreConcepto.getText());
             modCg.setDescripcion(vConcGas.txtDescripcion.getText());
             modCg.setIdConG(Integer.parseInt(vConcGas.txtId.getText()));
             modCg.setCategoria(vConcGas.cbxCategoria.getSelectedItem().toString());

             if (modCg.modificar(modCg)) {

                JOptionPane.showMessageDialog(null, "Registro modificado");
                vConcGas.dispose();
                Llenartabla(catConGas.jTable);
                

            } else {

                JOptionPane.showMessageDialog(null, "Este Registro ya Existe");
                
            }
            }

        }
        if (e.getSource() == vConcGas.btnEliminar) {
           
           

            if (modCg.eliminar(modCg)) {

                modCg.setIdConG(Integer.parseInt(vConcGas.txtId.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                vConcGas.dispose();
                Llenartabla(catConGas.jTable);
                

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }
       
    }
    
    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vConcGas.txtDescripcion.getText().isEmpty()) {

            msj += "El campo nombre Concepto no puede estar vacío\n";
            resultado = false;
        }
        if (vConcGas.txtDescripcion.getText().isEmpty()) {

            msj += "El campo Descripcion no puede estar vacío\n";
            resultado = false;
        }
        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }
    
    public void Llenartabla(JTable tablaD) {

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Nombre");
        modeloT.addColumn("Descripcion");
        modeloT.addColumn("Categoria");

        Object[] columna = new Object[3];

        int numRegistro = modCg.listar().size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = modCg.listar().get(i).getNombreConcepto();
            columna[1] = modCg.listar().get(i).getDescripcion();
            columna[2] = modCg.listar().get(i).getCategoria();

            modeloT.addRow(columna);

        }

    }
    
    private void filtro(String consulta, JTable jtableBuscar) {
        
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        int fila = this.catConGas.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
        int columna = this.catConGas.jTable.getSelectedColumn(); // luego, obtengo la columna seleccionada
        String dato = String.valueOf(this.catConGas.jTable.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda

        modCg.setNombreConcepto(String.valueOf(dato));

        modCg.buscar(modCg);

        vConcGas.setVisible(true);
        vConcGas.txtNombreConcepto.setText(modCg.getNombreConcepto());
       
        vConcGas.txtDescripcion.setText(modCg.getDescripcion());
        vConcGas.txtId.setText(Integer.toString(modCg.getIdConG()));
        vConcGas.cbxCategoria.setSelectedItem(modCg.getCategoria());
        vConcGas.btnGuardar.setEnabled(false);
        vConcGas.txtId.setVisible(false);
        vConcGas.btnModificar.setEnabled(true);
        vConcGas.btnEliminar.setEnabled(true);
        
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
     
        if (ke.getSource() == vConcGas.txtNombreConcepto) {
            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vConcGas.txtDescripcion.getText(), 15);
        }
        if (ke.getSource() == vConcGas.txtDescripcion) {

            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vConcGas.txtDescripcion.getText(), 15);
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getSource() == catConGas.txtBuscar) {
            
        
        filtro(catConGas.txtBuscar.getText(), catConGas.jTable);
        }
        else {
                
                }
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Llenartabla(catConGas.jTable);
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
