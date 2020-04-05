package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.CrudUsuario;
import modelo.Usuario;
import vista.GestionarUsuario;
import vista.catalogoUsuario;
import controlador.Validacion;

public class CtrlUsuario implements ActionListener, ItemListener, MouseListener, KeyListener {

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
        this.catausu.btnBuscar.addActionListener(this);
        this.catausu.btnNuevoUsuario.addActionListener(this);
        this.catausu.btnEditar.addActionListener(this);
        this.catausu.btnEliminar.addActionListener(this);
        this.vistaGU.txtCedula.addKeyListener(this);
        this.vistaGU.txtUsuario.addKeyListener(this);
        this.vistaGU.jpPassword.addKeyListener(this);
        this.vistaGU.txtNombre.addKeyListener(this);
        this.vistaGU.txtApellido.addKeyListener(this);
        this.vistaGU.txtTelefono.addKeyListener(this);
        

    }
    //Fin del constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaGU.btnGuardar) {
            if(validar()){
            mod.setCedula(vistaGU.txtCedula.getText());
            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());
            mod.setNombre(vistaGU.txtNombre.getText());
            mod.setApellido(vistaGU.txtApellido.getText());
            mod.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
            mod.setNtelefono(vistaGU.txtTelefono.getText());
            }
            if (modC.registrar(mod)) {

                JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                limpiar();

            } else {

                JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR");
                limpiar();

            }
        }

        if (e.getSource() == vistaGU.btnModificar) {
            if(validar()){
            mod.setUsuario(vistaGU.txtUsuario.getText());
            mod.setPassword(vistaGU.jpPassword.getText());
            mod.setNombre(vistaGU.txtNombre.getText());
            mod.setApellido(vistaGU.txtApellido.getText());
            mod.setTipo(vistaGU.cbxTipo.getSelectedItem().toString());
            mod.setNtelefono(vistaGU.txtTelefono.getText());
            }
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

        if (e.getSource() == catausu.btnBuscar) {
            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "ENCONTRO EL REGISTRO?", "REGISTRO", botonDialogo);
            if (result == 0) {

                this.catausu.btnEditar.setEnabled(true);
                this.catausu.btnEliminar.setEnabled(true);
                this.catausu.btnNuevoUsuario.setEnabled(false);
                this.catausu.btnNuevoUsuario.setForeground(Color.gray);
                this.catausu.btnEditar.setForeground(new java.awt.Color(0, 94, 159));
                this.catausu.btnEliminar.setForeground(new java.awt.Color(0, 94, 159));

            } else {
                this.catausu.btnNuevoUsuario.setEnabled(true);
                this.catausu.btnNuevoUsuario.setForeground(new java.awt.Color(0, 94, 159));
                this.catausu.btnEditar.setEnabled(false);
                this.catausu.btnEliminar.setEnabled(false);
                this.catausu.btnEditar.setForeground(Color.gray);
                this.catausu.btnEliminar.setForeground(Color.gray);

            }
        }

        if (e.getSource() == catausu.btnNuevoUsuario) {
            this.vistaGU.setVisible(true);
            this.vistaGU.btnModificar.setVisible(false);
            this.vistaGU.btnGuardar.setVisible(true);
            

        }

        if (e.getSource() == catausu.btnEditar) {
            this.vistaGU.setVisible(true);
            this.vistaGU.btnGuardar.setVisible(false);
            this.vistaGU.btnModificar.setVisible(true);
        }

        if (e.getSource() == catausu.btnEliminar) {
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
    public void keyTyped(KeyEvent ke) {
    if (ke.getSource() == vistaGU.txtCedula) {
            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtCedula.getText(), 8);
        }
        if (ke.getSource() == vistaGU.txtUsuario) {

            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtUsuario.getText(), 20);
        }
        if (ke.getSource() == vistaGU.jpPassword) {
            Validacion.Espacio(ke);
            
            Validacion.limite(ke, vistaGU.jpPassword.getText(), 15);
        }
        if (ke.getSource() == vistaGU.txtNombre) {
            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtNombre.getText(), 20);

        }
        if (ke.getSource() == vistaGU.txtApellido) {

            Validacion.soloLetras(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtApellido.getText(), 20);
        }
        if (ke.getSource() == vistaGU.txtTelefono) {

            Validacion.soloNumeros(ke);
            Validacion.Espacio(ke);
            Validacion.limite(ke, vistaGU.txtTelefono.getText(), 11);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
     private Boolean validar() {

        Boolean resultado = true;
        String msj = "";

        if (vistaGU.txtCedula.getText().isEmpty()) {

            msj += "El campo Cédula no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtUsuario.getText().isEmpty()) {

            msj += "El campo Usuario no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.jpPassword.getText().isEmpty()) {

            msj += "El campo Password no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtNombre.getText().isEmpty()) {

            msj += "El campo Nombre no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtApellido.getText().isEmpty()) {

            msj += "El campo Apellido no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.txtTelefono.getText().isEmpty()) {

            msj += "El campo Password no puede estar vacío\n";
            resultado = false;
        }
        if (vistaGU.cbxTipo.getSelectedItem() == null) {

            msj += "Debe seleccionar Tipo de Usuario";
            resultado = false;
        }

        if (!resultado) {

            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        return resultado;
    }

}
