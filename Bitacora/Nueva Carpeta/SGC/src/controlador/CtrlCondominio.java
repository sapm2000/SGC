package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.TableColumn;
import modelo.Condominio;
import modelo.Funcion;
import sgc.SGC;
import vista.VisCondominio;

public class CtrlCondominio implements ActionListener, MouseListener, KeyListener, WindowListener, FocusListener {

    private VisCondominio vista;
    private Condominio modelo;

    JFileChooser seleccionar = new JFileChooser();
    File archivo;
    byte[] imagen;
    FileInputStream entrada;
    FileOutputStream salida;
    ImageIcon img2 = null;
    ImageIcon img3 = null;
    private Funcion permiso;

    private JFrame ventana;

    public CtrlCondominio() {
        this.vista = new VisCondominio();
        this.modelo = new Condominio();

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.jlogo.addActionListener(this);
        this.vista.txtRif.addKeyListener(this);
        this.vista.txtRazonS.addKeyListener(this);
        this.vista.txtTelefono.addKeyListener(this);
        this.vista.txtCorreo.addKeyListener(this);

        if (modelo.buscar()) {
            vista.txtRif.setEditable(false);

            vista.txtRif.setText(modelo.getRif());
            vista.txtRazonS.setText(modelo.getRazonS());
            vista.txtTelefono.setText(modelo.getTelefono());
            vista.txtCorreo.setText(modelo.getCorreoElectro());
        }

        CtrlVentana.cambiarVista(vista);
    }

    public CtrlCondominio(Boolean boo) {
        this.vista = new VisCondominio();
        this.modelo = new Condominio();

        ventana = new JFrame();
        ventana.setUndecorated(true);
        ventana.setSize(1366, 662);
        ventana.add(vista);

        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.jlogo.addActionListener(this);
        this.vista.txtRif.addKeyListener(this);
        this.vista.txtRazonS.addKeyListener(this);
        this.vista.txtTelefono.addKeyListener(this);
        this.vista.txtCorreo.addKeyListener(this);

        ventana.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnEliminar) {
            vista.labelLogo.setIcon(null);
        }

        if (e.getSource() == vista.jlogo) {
            if (seleccionar.showDialog(null, null) == JFileChooser.APPROVE_OPTION) {
                archivo = seleccionar.getSelectedFile();
                if (archivo.canRead()) {
                    if (archivo.getName().endsWith("png"));

                    imagen = AbrirArchivo(archivo);
                    Image img = new ImageIcon(imagen).getImage();
                    img2 = new ImageIcon(img.getScaledInstance(125, 125, Image.SCALE_SMOOTH));
                    img3 = new ImageIcon(img);

                    vista.labelLogo.setIcon((img2));
                } else {
                    UIManager UI = new UIManager();
                    UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                    UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                    int botonDialogo = JOptionPane.OK_OPTION;
                    Icon p = new ImageIcon(getClass().getResource("/img/no.png"));
                    UIManager.put("Button.background", Color.white);
                    UIManager.put("Button.font", Color.blue);
                    UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                    UIManager.put("Label.background", Color.blue);
                    UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                    JOptionPane.showMessageDialog(null, "Archivo no compatible ", "INCOMPATIBILIDAD", JOptionPane.WARNING_MESSAGE, p);
                }
            }
        }

        if (e.getSource() == vista.btnGuardar) {
            if (validar()) {
                modelo.setRif(vista.txtRif.getText());
                modelo.setRazonS(vista.txtRazonS.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setCorreoElectro(vista.txtCorreo.getText());

                if (modelo.existe()) {
                    if (modelo.modificar()) {
                        if (vista.labelLogo.getIcon() != null) {
                            ImageIcon icon = (ImageIcon) img3;

                            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2 = image.createGraphics();

                            g2.drawImage(icon.getImage(), 0, 0, icon.getImageObserver());

                            g2.dispose();
                            File fichero = new File("test.txt");

                            int cantidad = 8;
                            String cadena = fichero.getAbsolutePath();
                            /* Total de elementos a Eliminar*/
 /* Total de elementos a Mostrar*/
                            int m = Math.max(0, cadena.length() - cantidad);
                            String ruta = (cadena.substring(0, cadena.length() - cantidad));
                            String ruta2 = "\\src\\fondo\\logo.png";
                            System.out.println(ruta);
                            try {
                                ImageIO.write(image, "png", new File(ruta + ruta2));
                            } catch (IOException ex) {
                                UIManager UI = new UIManager();
                                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                                int botonDialogo = JOptionPane.OK_OPTION;
                                Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                                UIManager.put("Button.background", Color.white);
                                UIManager.put("Button.font", Color.blue);
                                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                                UIManager.put("Label.background", Color.blue);
                                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                                JOptionPane.showMessageDialog(null, "Error ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
                            }
                        }
                        UIManager UI = new UIManager();
                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        int botonDialogo = JOptionPane.OK_OPTION;
                        Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "Datos actualizados ", "ACTUALIZACIÓN DE DATOS", JOptionPane.WARNING_MESSAGE, p);
                    }

                } else {
                    if (modelo.registrar()) {
                        if (vista.labelLogo.getIcon() != null) {
                            ImageIcon icon = (ImageIcon) img3;

                            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2 = image.createGraphics();

                            g2.drawImage(icon.getImage(), 0, 0, icon.getImageObserver());

                            g2.dispose();
                            File fichero = new File("test.txt");

                            int cantidad = 8;
                            String cadena = fichero.getAbsolutePath();
                            /* Total de elementos a Eliminar*/
 /* Total de elementos a Mostrar*/
                            int m = Math.max(0, cadena.length() - cantidad);
                            String ruta = (cadena.substring(0, cadena.length() - cantidad));
                            String ruta2 = "\\src\\fondo\\logo.png";
                            System.out.println(ruta);
                            try {
                                ImageIO.write(image, "png", new File(ruta + ruta2));
                            } catch (IOException ex) {

                                UIManager UI = new UIManager();
                                UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                                UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                                int botonDialogo = JOptionPane.OK_OPTION;
                                Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
                                UIManager.put("Button.background", Color.white);
                                UIManager.put("Button.font", Color.blue);
                                UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                                UIManager.put("Label.background", Color.blue);
                                UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                                JOptionPane.showMessageDialog(null, "Error ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
                            }
                        }
                        UIManager UI = new UIManager();
                        UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
                        UI.put("Panel.background", new ColorUIResource(255, 255, 255));

                        int botonDialogo = JOptionPane.OK_OPTION;
                        Icon p = new ImageIcon(getClass().getResource("/img/check.png"));
                        UIManager.put("Button.background", Color.white);
                        UIManager.put("Button.font", Color.blue);
                        UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
                        UIManager.put("Label.background", Color.blue);
                        UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

                        JOptionPane.showMessageDialog(null, "Datos registrados ", "REGISTRO DE DATOS", JOptionPane.WARNING_MESSAGE, p);
                        SGC.condominioActual = modelo;

                        CtrlVentana ctrlMenu = new CtrlVentana();
                        ventana.dispose();

                    }
                }
            }
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }

    private void permisoBtn() {
        for (Funcion funcionbtn : SGC.usuarioActual.getTipoU().getFunciones()) {
            if (funcionbtn.getNombre().equals("Responsables")) {
                permiso = funcionbtn;
            }
        }
    }

    public void limpiar() {
        vista.txtRazonS.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
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
        if (ke.getSource() == vista.txtRif) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtRif.getText(), 15);
        }
        if (ke.getSource() == vista.txtRazonS) {

            Validacion.limite(ke, vista.txtRazonS.getText(), 150);
        }
        if (ke.getSource() == vista.txtTelefono) {
            Validacion.Espacio(ke);
            Validacion.soloNumeros(ke);
            Validacion.limite(ke, vista.txtTelefono.getText(), 11);
        }
        if (ke.getSource() == vista.txtCorreo) {

            Validacion.Espacio(ke);
            Validacion.limite(ke, vista.txtCorreo.getText(), 70);

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        permisoBtn();

        Component[] components = vista.jPanel2.getComponents();
        JComponent[] com = {
            vista.txtRif, vista.txtRazonS, vista.txtTelefono, vista.txtCorreo
        };
        Validacion.copiar(components);
        Validacion.pegar(com);
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

        if (vista.txtRif.getText().isEmpty()) {
            msj += "El campo RIF no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtRazonS.getText().isEmpty()) {
            msj += "El campo razón social no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtTelefono.getText().isEmpty()) {
            msj += "El campo teléfono no puede estar vacío \n";
            resultado = false;
        }

        if (vista.txtCorreo.getText().isEmpty()) {
            msj += "El campo correo electrónico no puede estar vacío \n";
            resultado = false;
        }

        if (!resultado) {
            UIManager UI = new UIManager();
            UI.put("OptionPane.border", createLineBorder(new Color(0, 94, 159), 5));
            UI.put("Panel.background", new ColorUIResource(255, 255, 255));

            int botonDialogo = JOptionPane.OK_OPTION;
            Icon p = new ImageIcon(getClass().getResource("/img/warning.png"));
            UIManager.put("Button.background", Color.white);
            UIManager.put("Button.font", Color.blue);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
            UIManager.put("Label.background", Color.blue);
            UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 12));

            JOptionPane.showMessageDialog(null, msj, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE, p);
        }

        return resultado;

    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public byte[] AbrirArchivo(File archivo) {
        byte[] imagen = new byte[1024 * 1000];
        try {
            entrada = new FileInputStream(archivo);
            entrada.read(imagen);

        } catch (Exception e) {

        }
        return imagen;
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

        if (Validacion.email(vista.txtCorreo.getText())) {

        } else {
            JOptionPane.showMessageDialog(null, "Email incorrecto");
            vista.txtCorreo.requestFocus();
        }

    }
}
