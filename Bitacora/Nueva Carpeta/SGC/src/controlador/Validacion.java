
package controlador;
import static com.sun.tools.javac.tree.TreeInfo.args;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import jdk.internal.org.jline.reader.LineReader;

/**
 *
 * @author Usuario
 */
public abstract class Validacion implements ActionListener, MouseListener, KeyListener {

    /*---FUNCIÓN PARA EVITAR EL COPY & PASTE---*/
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

