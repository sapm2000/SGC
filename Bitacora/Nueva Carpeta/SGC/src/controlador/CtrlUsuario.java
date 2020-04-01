package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import modelo.CrudUsuario;
import modelo.Usuario;
import vista.GestionarUsuario;
import vista.catalogoUsuario;

public class CtrlUsuario implements ActionListener, ItemListener {

    private Usuario mod;
    private CrudUsuario modC;
    private GestionarUsuario vistaGU;
    private catalogoUsuario catausu;

    //Constructor de inicializacion de variables. Desde la linea 16 a la 26
    public CtrlUsuario(Usuario mod, CrudUsuario modC, GestionarUsuario vistaGU, catalogoUsuario catausu) {

        this.mod = mod;
        this.modC = modC;
        this.vistaGU = vistaGU;
        this.catausu = catausu;
        //this.vistaGU.btnBuscar.addActionListener(this);
        this.vistaGU.btnGuardar.addActionListener(this);

        this.vistaGU.btnModificar.addActionListener(this);
        this.vistaGU.btnLimpiar.addActionListener(this);
        this.catausu.jButton1.addActionListener(this);
        this.catausu.jButton2.addActionListener(this);
        this.catausu.jButton4.addActionListener(this);
        this.catausu.jButton5.addActionListener(this);

    }
    //Fin del constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaGU.btnGuardar) {

            mod.setCedula(vistaGU.txtCedula.getText());
            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());
            mod.setNombre(vistaGU.txtNombre.getText());
            mod.setApellido(vistaGU.txtApellido.getText());
            mod.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
            mod.setNtelefono(vistaGU.txtTelefono.getText());

            if (modC.registrar(mod)) {

                JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                limpiar();

            } else {

                JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR");
                limpiar();

            }
        }

        if (e.getSource() == vistaGU.btnModificar) {

            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());
            mod.setNombre(vistaGU.txtNombre.getText());
            mod.setApellido(vistaGU.txtApellido.getText());
            mod.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
            mod.setNtelefono(vistaGU.txtTelefono.getText());

            if (modC.modificar(mod)) {

                JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");

                limpiar();

            } else {

                JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
                limpiar();

            }

        }

        /* if (e.getSource() == vistaGU.btnBuscar) {

            mod.setCedula(vistaGU.txtCedula.getText());

            if (modC.buscar(mod)) {

                vistaGU.txtCedula.setText(mod.getCedula());
                vistaGU.txtUsuario.setText(mod.getUsuario());
                vistaGU.jpPassword.setText(mod.getPassword());
                vistaGU.txtNombre.setText(mod.getNombre());
                vistaGU.txtApellido.setText(mod.getApellido());
                vistaGU.cbxTipo.setSelectedItem(mod.getTipo());
                vistaGU.txtTelefono.setText(mod.getNtelefono());

            } else {

                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO REGISTRO");
        }
            }
         */
        if (e.getSource() == vistaGU.btnLimpiar) {
            limpiar();
        }

        if (e.getSource() == catausu.jButton1) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catausu.jButton4.setEnabled(true);
                this.catausu.jButton5.setEnabled(true);
                this.catausu.jButton2.setEnabled(false);
                this.catausu.jButton2.setForeground(Color.gray);
                this.catausu.jButton4.setForeground(new java.awt.Color(0, 94, 159));
                this.catausu.jButton5.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catausu.jButton2.setEnabled(true);
                this.catausu.jButton2.setForeground(new java.awt.Color(0, 94, 159));
                this.catausu.jButton4.setEnabled(false);
                this.catausu.jButton5.setEnabled(false);
                this.catausu.jButton4.setForeground(Color.gray);
                this.catausu.jButton5.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catausu.jButton2) {
            this.vistaGU.setVisible(true);
            this.vistaGU.btnModificar.setVisible(false);

        }

        if (e.getSource() == catausu.jButton4) {
            this.vistaGU.setVisible(true);
            this.vistaGU.btnGuardar.setVisible(false);

        }

        if (e.getSource() == catausu.jButton5) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?", "ELIMINAR", botonDialogo);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO");
            } else {

            }

        }

    }

    public void limpiar() {

        vistaGU.txtCedula.setText(null);
        vistaGU.txtUsuario.setText(null);
        vistaGU.jpPassword.setText(null);
        vistaGU.txtNombre.setText(null);
        vistaGU.txtApellido.setText(null);
        vistaGU.cbxTipo.setSelectedItem(0);
        vistaGU.txtTelefono.setText(null);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
