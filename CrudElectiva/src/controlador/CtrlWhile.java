package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ModeloWhile;
import vista.VistaCiclo;

public class CtrlWhile implements ActionListener {

    public vista.VistaCiclo vistaC;
    public modelo.ModeloWhile modW;

    public CtrlWhile(VistaCiclo vistaC, ModeloWhile modW) {

        this.vistaC = vistaC;
        this.modW = modW;
        this.vistaC.btnEnviar.addActionListener(this);

    }

    public void iniciar() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaC.btnEnviar) {
            modW.setNumeroA(Integer.parseInt(vistaC.txtArea.getText()));

            modW.whiledo();
            vistaC.txtArea.setText(String.valueOf(modW.getNumeroA()));
        }
    }

}
