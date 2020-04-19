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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Banco;
import vista.banco;
import vista.catalogoBanco;

/**
 *
 * @author rma
 */
public class controladorBanco implements ActionListener, MouseListener, KeyListener, WindowListener {

    private banco ban;
    private catalogoBanco cban;
    private Banco modban;
    DefaultTableModel dm;

    public controladorBanco(banco ban, catalogoBanco cban, Banco modban) {
        this.ban = ban;
        this.cban = cban;
        this.modban = modban;
        
        crearCbxBanco(modban.listar());
        
        this.cban.btnNuevo_banco.addActionListener(this);

        this.ban.btnGuardar.addActionListener(this);
        this.ban.btnLimpiar.addActionListener(this);
        this.ban.btnEliminar.addActionListener(this);
        this.ban.btnModificar.addActionListener(this);
        this.cban.tabla_bancos.addMouseListener(this);
        this.cban.tabla_bancos.addKeyListener(this);
        this.cban.txtBuscar_banco.addKeyListener(this);
        this.cban.addWindowListener(this);

    }

    public void Llenartabla(JTable tablaD) {

        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Nombre Banco");

        Object[] columna = new Object[1];

        int numRegistro = modban.listar().size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = modban.listar().get(i).getNombre_banco();

            modeloT.addRow(columna);

        }

    }
    
    private void crearCbxBanco(ArrayList<Banco> datos) {
        ban.cbxBanco.addItem("Seleccione...");

        if (datos != null) {
            for (Banco datosX : datos) {
                modban = datosX;
                ban.cbxBanco.addItem(modban.getNombre_banco());
            }

            System.out.println("ComboBox banco creado");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }
        
        if (e.getSource() == ban.btnEliminar) {
           
           

            if (modban.eliminar(modban)) {

                modban.setId(Integer.parseInt(ban.txtid.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                ban.dispose();
                Llenartabla(cban.tabla_bancos);
                

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }

        if (e.getSource() == ban.btnModificar) {
            if(validar()){
             modban.setNombre_banco(ban.txtnombre_banco.getText());
              modban.setId(Integer.parseInt(ban.txtid.getText()));
            
             
             
             if (modban.modificar(modban)) {

                JOptionPane.showMessageDialog(null, "Registro modificado");
                ban.dispose();
                Llenartabla(cban.tabla_bancos);
                

            } else {

                JOptionPane.showMessageDialog(null, "Este Registro ya Existe");
                
            }
            }

        }
        
        if (e.getSource() == ban.btnLimpiar) {
             
             ban.txtnombre_banco.setText("");
            
        }

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
    public void keyTyped(KeyEvent e) {

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

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
