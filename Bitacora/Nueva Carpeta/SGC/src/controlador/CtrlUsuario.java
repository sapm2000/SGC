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

    private GestionarUsuario gestU;
    private Usuario usu;

    public CtrlUsuario(GestionarUsuario gestU, Usuario usu) {
        this.gestU = gestU;
        this.usu = usu;
        this.gestU.btnBuscar.addActionListener(this);
        this.gestU.btnGuardar.addActionListener(this);
        this.gestU.bntModificar.addActionListener(this);
        this.gestU.btnEliminar.addActionListener(this);
    }

    public void iniciar() {

        gestU.setTitle("Usuario");
        gestU.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == gestU.btnGuardar) {

            usu.setCedula(gestU.txtCedula.getText());
            usu.setUsuario(gestU.txtUsuario.getText());
            usu.setContraseña(gestU.contraseña.getText());
            usu.setNombre(gestU.txtNombre.getText());
            usu.setApellido(gestU.txtApellido.getText());
            usu.setTelefono(gestU.txtTelefono.getText());
            usu.setTipo(gestU.cbxTipo.getSelectedItem().toString());

            if (usu.registrar(usu)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Guardar");

            }

        }

        if (e.getSource() == gestU.bntModificar) {

            usu.setCedula(gestU.txtCedula.getText());
            usu.setUsuario(gestU.txtUsuario.getText());
            usu.setContraseña(gestU.contraseña.getText());
            usu.setNombre(gestU.txtNombre.getText());
            usu.setApellido(gestU.txtApellido.getText());
            usu.setTelefono(gestU.txtTelefono.getText());
            usu.setTipo(gestU.cbxTipo.getSelectedItem().toString());

            /*si la modificacion fue exitosa o no mandamos una ventana con un mensaje segun sea el caso.*/
            if (usu.modificar(usu)) {

                JOptionPane.showMessageDialog(null, "Registro modificado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Modificar");

            }

        }

        if (e.getSource() == gestU.btnEliminar) {
            /*el Id esta oculto en el sistema se auto incrementa solo*/
            usu.setCedula(gestU.txtCedula.getText());

            /*enviamos una ventana emergente diciendo si los datos fueron eliminados correctamente o existe un error*/
            if (usu.eliminar(usu)) {

                JOptionPane.showMessageDialog(null, "Registro Eliminado");

            } else {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");

            }

        }

        if (e.getSource() == gestU.btnBuscar) {

            usu.setCedula(gestU.txtCedula.getText());

            if (usu.buscar(usu)) {

                
                gestU.txtCedula.setText(usu.getCedula());
                gestU.txtUsuario.setText(usu.getUsuario());
                gestU.contraseña.setText(usu.getContraseña());
                gestU.txtNombre.setText(usu.getNombre());
                gestU.txtApellido.setText(usu.getApellido());
                gestU.txtTelefono.setText(usu.getTelefono());
                gestU.cbxTipo.setSelectedItem(usu.getTipo());

            } else {

                JOptionPane.showMessageDialog(null, "No se encontro registro");
                
            }

        }

    }
}
