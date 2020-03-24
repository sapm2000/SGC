/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.GestionarUsuario;

/**
 *
 * @author rma
 */
public class CtrlUsuario implements ActionListener {

    private GestionarUsuario VUsuario;
    private Usuario modU;

    public CtrlUsuario(GestionarUsuario VUsuario, Usuario usu) {
        this.VUsuario = VUsuario;
        this.modU = usu;
        this.VUsuario.btnBuscar.addActionListener(this);
        this.VUsuario.btnGuardar.addActionListener(this);
        this.VUsuario.bntModificar.addActionListener(this);
        this.VUsuario.btnEliminar.addActionListener(this);
    }

        public CtrlUsuario(GestionarUsuario aThis) {
       this.VUsuario = aThis;
    }
    
    public void iniciar() {

        VUsuario.setTitle("Usuario");
        VUsuario.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == VUsuario.btnGuardar) {

            modU.setCedula(VUsuario.txtCedula.getText());
            modU.setUsuario(VUsuario.txtUsuario.getText());
            modU.setContraseña(VUsuario.jpContraseña.getText());
            modU.setNombre(VUsuario.txtNombre.getText());
            modU.setApellido(VUsuario.txtApellido.getText());
            modU.setTelefono(VUsuario.txtTelefono.getText());
            modU.setTipo(VUsuario.cbxTipo.getSelectedItem().toString());

            if (modU.registrar(modU)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Guardar");

            }

        }

        if (e.getSource() == VUsuario.bntModificar) {

            modU.setCedula(VUsuario.txtCedula.getText());
            modU.setUsuario(VUsuario.txtUsuario.getText());
            modU.setContraseña(VUsuario.jpContraseña.getText());
            modU.setNombre(VUsuario.txtNombre.getText());
            modU.setApellido(VUsuario.txtApellido.getText());
            modU.setTelefono(VUsuario.txtTelefono.getText());
            modU.setTipo(VUsuario.cbxTipo.getSelectedItem().toString());

            /*si la modificacion fue exitosa o no mandamos una ventana con un mensaje segun sea el caso.*/
            if (modU.modificar(modU)) {

                JOptionPane.showMessageDialog(null, "Registro modificado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Modificar");

            }

        }

        if (e.getSource() == VUsuario.btnEliminar) {
            /*el Id esta oculto en el sistema se auto incrementa solo*/
            modU.setCedula(VUsuario.txtCedula.getText());

            /*enviamos una ventana emergente diciendo si los datos fueron eliminados correctamente o existe un error*/
            if (modU.eliminar(modU)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }

        if (e.getSource() == VUsuario.btnBuscar) {

            modU.setCedula(VUsuario.txtCedula.getText());

            if (modU.buscar(modU)) {

                
                VUsuario.txtCedula.setText(modU.getCedula());
                VUsuario.txtUsuario.setText(modU.getUsuario());
                VUsuario.jpContraseña.setText(modU.getContraseña());
                VUsuario.txtNombre.setText(modU.getNombre());
                VUsuario.txtApellido.setText(modU.getApellido());
                VUsuario.txtTelefono.setText(modU.getTelefono());
                VUsuario.cbxTipo.setSelectedItem(modU.getTipo());

            } else {

                JOptionPane.showMessageDialog(null, "No se encontro registro");
                
            }

        }

    }
}
