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
import javax.swing.table.DefaultTableModel;
import modelo.Propietarios;
import vista.PantallaPrincipal1;
import vista.catalogoPropietarios;
import vista.propietarios;

/**
 *
 * @author rma
 */
public class controladorPropietario implements ActionListener, MouseListener,KeyListener,  WindowListener{

    private propietarios pro;
    private catalogoPropietarios catapro;
    private Propietarios modpro;
    private PantallaPrincipal1 panta1;
    ArrayList<Propietarios> listaPropietarios;
    DefaultTableModel dm;

    public controladorPropietario(propietarios pro, catalogoPropietarios catapro, Propietarios modpro, PantallaPrincipal1 panta1) {
        this.pro = pro;
        this.catapro = catapro;
        this.modpro = modpro;
        this.panta1 = panta1;
        this.catapro.btn_NuevoPropietario.addActionListener(this);
        this.catapro.addWindowListener(this);

        this.pro.btnGuardar.addActionListener(this);
        this.pro.btnLimpiar.addActionListener(this);
        this.pro.btnModificar.addActionListener(this);
    }

    public void Llenartabla(JTable tablaD) {

        listaPropietarios = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);

        modeloT.addColumn("Cedula/Rif");
        modeloT.addColumn("Nombre/razon social");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Telefono");
        modeloT.addColumn("Correo");
        
       

        Object[] columna = new Object[5];

        int numRegistro = listaPropietarios.size();

        for (int i = 0; i < numRegistro; i++) {

            columna[0] = listaPropietarios.get(i).getCedula();
            columna[1] = listaPropietarios.get(i).getNombre();
            columna[2] = listaPropietarios.get(i).getApellido();
            columna[3] = listaPropietarios.get(i).getTelefono();
            columna[4] = listaPropietarios.get(i).getCorreo();
            
            

            modeloT.addRow(columna);

        }

    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == catapro.btn_NuevoPropietario) {
            this.pro.setVisible(true);
            this.pro.btnModificar.setEnabled(false);
            this.pro.btnGuardar.setEnabled(true);
            this.pro.btnEliminar.setEnabled(true);
            this.pro.txtCedula.setEnabled(true);
            this.catapro.addWindowListener(this);
            pro.txtCedula.setText("");
            pro.txtApellido.setText("");
            pro.txtCorreo.setText("");
            pro.txtNombre.setText("");
            pro.txtTelefono.setText("");
            

        }

        if (e.getSource() == pro.btnGuardar) {
            if (validar()) {
                modpro.setCedula(pro.txtCedula.getText());
                modpro.setNombre(pro.txtNombre.getText());
                modpro.setApellido(pro.txtApellido.getText());
                modpro.setCorreo(pro.txtCorreo.getText());
                modpro.setTelefono(pro.txtTelefono.getText());
                modpro.setId_condominio(panta1.rif.getText());

                if (modpro.registrar(modpro)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    Llenartabla(catapro.TablaPropietarios);

                } else {

                    JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");

                }
            }

        }

        if (e.getSource() == pro.btnModificar) {
            JOptionPane.showMessageDialog(null, "registro modificado");

        }
    }

    private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (pro.txtCedula.getText().isEmpty()) {

            msj += "El campo nombre categoria no puede estar vacío\n";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
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
        Llenartabla(catapro.TablaPropietarios);
        
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
