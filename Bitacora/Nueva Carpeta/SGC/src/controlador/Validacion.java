package controlador;

import java.awt.Component;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Usuario
 */
public abstract class Validacion implements ActionListener, MouseListener, KeyListener {

    public static DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance(Locale.ENGLISH);

    public static DecimalFormat formato1 = new DecimalFormat("##.####", simbolos);
    //public static DecimalFormat formato1 = new DecimalFormat("#.##", simbolos);
    public static DecimalFormat formato2 = new DecimalFormat("##.####", simbolos);
    public static DecimalFormat formatoalicuota = new DecimalFormat("#.##%", simbolos);

    public static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getDate());
        return sDate;
    }

    /*---FUNCIÓN PARA EVITAR EL COPY & PASTE---*/
    public static String formatoDecimal(Double dec) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(4);
        format.setGroupingSize(3);
        format.setGroupingUsed(true);
        return format.format(dec);
    }

    public static void evitarPegar(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();

        if (evt.isControlDown() && (c == 'c' || c == 'v')) {

            JOptionPane.showMessageDialog(null, "No se permite copiar o pegar", "Error", JOptionPane.ERROR_MESSAGE);
            evt.consume();

        }

    }

    /*---FUNCIÓN PARA PERMITIR SOLO LETRAS---*/
    public static void soloLetras(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != KeyEvent.VK_SPACE && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {

            JOptionPane.showMessageDialog(null, "Este campo solo permite ingresar letras. No se permiten caracteres especiales o números.", "Error!", JOptionPane.ERROR_MESSAGE);
            evt.consume();
        }
    }

    /*---FUNCIÓN PARA PERMITIR SOLO NÚMEROS---*/
    public static void soloNumeros(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();

        if (!Character.isDigit(c) && c != KeyEvent.VK_SPACE && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            JOptionPane.showMessageDialog(null, "Este campo solo permite ingresar números. No se permiten caracteres especiales o letras.", "Error!", JOptionPane.ERROR_MESSAGE);
            evt.consume();
        }
    }

    /*---FUNCIÓN PARA VALIDAR LA CANTIDAD DE CARACTERES INGRESADO A CAJA DE TEXTOS---*/
    public static void limite(java.awt.event.KeyEvent evt, String texto, int limite) {

        if (texto.length() >= limite) {

            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    /*---FUNCIÓN PARA EVITAR ESPACIOS---*/
    public static void Espacio(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();

        if ((c == ' ')) {
            JOptionPane.showMessageDialog(null, "Este campo no permite ingresar espacios.", "Error!", JOptionPane.ERROR_MESSAGE);
            evt.consume();
        }
    }

    public static int contarCaracteres(String cadena, char caracter) {
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
       
        return contador;
    }

    public static void soloUnPunto(java.awt.event.KeyEvent e, String cadena) {

        
        int contador = contarCaracteres(cadena, '.');
        if (contador < 1) {

            char car = e.getKeyChar();
            if ((car < '0' || car > '9') && (car <= ',' || car > '.')) {
                e.consume();
            }
        } else {
            char car = e.getKeyChar();
            if ((car < '0' || car > '9')) {
                e.consume();
            }

        }

    }

    public static void pegar(JComponent[] components) {

        for (JComponent campo : components) {
            InputMap map = campo.getInputMap();
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, Event.SHIFT_MASK), "null");
        }

    }

    public static void copiar(Component[] components) {

        for (Component campo : components) {

            if (campo instanceof JComponent) {
                InputMap map = ((JComponent) campo).getInputMap();
                map.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK), "null");
            }
        }
    }

    public String encriptar(String textoSinEncriptar) {
        return DigestUtils.md5Hex(textoSinEncriptar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

}
