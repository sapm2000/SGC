/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.CerrarMes;
import modelo.CuotasEspeciales;
import modelo.GastoComun;
import modelo.Interes;
import modelo.Sancion;
import modelo.Unidades;
import vista.PantallaPrincipal1;
import vista.recibo;

/**
 *
 * @author rma
 */
public class controladorCerrarMes implements ActionListener {

    private recibo rec;
    private CerrarMes modc;
    private Unidades moduni;
    ArrayList<Unidades> listaunidades;
    ArrayList<GastoComun> listagastocomun;
    ArrayList<CuotasEspeciales> listacuotasespeciales;
    ArrayList<Sancion> listasanciones;
    ArrayList<Interes> listainteres;
    private PantallaPrincipal1 panta1;
    private GastoComun modgac;
    private CuotasEspeciales modcuo;
    private Sancion modsan;
    private Interes modin;

    public controladorCerrarMes(recibo rec, CerrarMes modc, Unidades moduni, PantallaPrincipal1 panta1, GastoComun modgac, CuotasEspeciales modcuo, Sancion modsan, Interes modin) {
        this.rec = rec;
        this.modc = modc;
        this.moduni = moduni;
        this.panta1 = panta1;
        this.modgac = modgac;
        this.modcuo = modcuo;
        this.modsan = modsan;
        this.modin = modin;
        rec.jButton1.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rec.jButton1) {
            modc.setMes_cierre(rec.jMonthChooser1.getMonth() + 1);
            modc.setAño_cierre(rec.jYearChooser1.getYear());
            modc.setId_condominio(panta1.rif.getText());
            moduni.setId_condominio(panta1.rif.getText());
            listaunidades = moduni.buscarUnidades();

            int numRegistro = listaunidades.size();

            Object[] area = new Object[numRegistro];
            Object[] alicuota = new Object[numRegistro];
            Object[] num_casa = new Object[numRegistro];
            double totalarea = 0;
            for (int i = 0; i < numRegistro; i++) {

                area[i] = listaunidades.get(i).getArea();
                double areai = Double.parseDouble(String.valueOf(area[i]));
                totalarea = areai + totalarea;
                num_casa[i] = listaunidades.get(i).getN_unidad();

            }

            for (int i = 0; i < numRegistro; i++) {

                area[i] = listaunidades.get(i).getArea();
                double areai = Double.parseDouble(String.valueOf(area[i]));
                double ali = areai / totalarea;
                alicuota[i] = ali;

            }

            modgac.setId_condominio(panta1.rif.getText());
            modgac.setMes(modc.getMes_cierre());
            modgac.setAño(modc.getAño_cierre());
            listagastocomun = modgac.listarGastoComuncierremes();
            int numGastos = listagastocomun.size();

            if (numGastos == 0) {
            } else {
                Object[] concepto = new Object[numGastos];
                Object[] proveedor = new Object[numGastos];
                Object[] id_gasto = new Object[numGastos];
                Object[] monto_gasto = new Object[numGastos];
                Object[][] gastodes = new Object[numGastos][numRegistro];
                Object[][] id_unidad_gasto = new Object[numGastos][numRegistro];
                DecimalFormat formato1 = new DecimalFormat("#.00");

                for (int f = 0; f < numGastos; f++) {
                    concepto[f] = listagastocomun.get(f).getNombre_Concepto();
                    proveedor[f] = listagastocomun.get(f).getId_proveedor();
                    id_gasto[f] = listagastocomun.get(f).getId();
                    monto_gasto[f] = listagastocomun.get(f).getMonto();

                    for (int x = 0; x < numRegistro; x++) {

                        double gastoxunidad = Double.parseDouble(String.valueOf(monto_gasto[f])) * Double.parseDouble(String.valueOf(alicuota[x]));

                        gastodes[f][x] = gastoxunidad;
                        id_unidad_gasto[f][x] = num_casa[x];
                        modc.setMonto(gastoxunidad);
                        modc.setId_gasto(Integer.parseInt(String.valueOf(id_gasto[f])));
                        modc.setId_unidad(String.valueOf(num_casa[x]));
                        modc.registrarGasto(modc);

                    }

                }
            }
            modcuo.setId_condominio(panta1.rif.getText());
            listacuotasespeciales = modcuo.listarCuotasEspecialescerrarmes();
            int numCuotas = listacuotasespeciales.size();
            Object[] tipo_cuota = new Object[numCuotas];
            Object[] id_cuota = new Object[numCuotas];
            Object[] año_cuota = new Object[numCuotas];

            int mes = modc.getMes_cierre();
            int año = modc.getAño_cierre();

            for (int z = 0; z < numCuotas; z++) {
                id_cuota[z] = listacuotasespeciales.get(z).getId();
                int mes_c = listacuotasespeciales.get(z).getMes();
                int año_c = listacuotasespeciales.get(z).getAño();
                int meses_r = listacuotasespeciales.get(z).getN_meses();
                tipo_cuota[z] = listacuotasespeciales.get(z).getCalcular();
                int var1 = mes_c + meses_r;
                double monto_t = listacuotasespeciales.get(z).getMonto();
                double parte_periodo = monto_t / meses_r;

                if (año >= año_c) {

                    if (año > año_c) {
                        mes_c = mes_c - 11;

                    }

                    if (mes >= mes_c) {

                        if (var1 > 13) {
                            var1 = var1 - 12;
                            año_c = año_c + 1;
                            if (var1 > 25) {
                                var1 = var1 - 12;
                                año_c = año_c + 1;
                            }
                        }
                        if (año <= año_c) {

                            if (mes < var1) {

                                String tipo = String.valueOf(tipo_cuota[z]);

                                String clave = "Alicuota";

                                if (tipo.equals(clave)) {

                                    for (int w = 0; w < numRegistro; w++) {

                                        double parte_cuota = parte_periodo * Double.parseDouble(String.valueOf(alicuota[w]));
                                        modc.setId_unidad(String.valueOf(num_casa[w]));
                                        modc.setMonto(parte_cuota);
                                        modc.setId_gasto(Integer.parseInt(String.valueOf(id_cuota[z])));
                                        modc.registrar_cuota(modc);
                                    }
                                } else {
                                    for (int w = 0; w < numRegistro; w++) {

                                        double parte_cuota = parte_periodo / numRegistro;
                                        modc.setId_unidad(String.valueOf(num_casa[w]));
                                        modc.setMonto(parte_cuota);
                                        modc.setId_gasto(Integer.parseInt(String.valueOf(id_cuota[z])));
                                        modc.registrar_cuota(modc);
                                    }
                                }

                            }
                        }
                    }
                }
            }
            modsan.setId_condominio(panta1.rif.getText());
            modsan.setMes(modc.getMes_cierre());
            modsan.setAño(modc.getAño_cierre());
            listasanciones = modsan.listarSancionesCerrarmes();
            int numSanciones = listasanciones.size();
            Object[] tipo_sancion = new Object[numSanciones];
            Object[] factor_sancion = new Object[numSanciones];
            Object[] id_sancion = new Object[numSanciones];

            for (int j = 0; j < numSanciones; j++) {
                tipo_sancion[j] = listasanciones.get(j).getTipo();
                factor_sancion[j] = listasanciones.get(j).getMonto();
                id_sancion[j] = listasanciones.get(j).getId();
                String var = String.valueOf(tipo_sancion[j]);

                if (var.equals("Interes de mora")) {
                    String ncasa = listasanciones.get(j).getN_unidad();
                    modc.setId_unidad(ncasa);
                    modc.buscartotal(modc);
                    double var6 = modc.getMonto();
                    modc.buscartotal2(modc);
                    double var7 = modc.getMonto();

                    double total = var6 + var7;
                    double totalf = Double.parseDouble(String.valueOf(factor_sancion[j])) / 100;
                    double var3 = total * totalf;

                    modc.setId_gasto(Integer.parseInt(String.valueOf(id_sancion[j])));
                    modc.setMonto(var3);

                    modc.guardarsancionpro(modc);

                }

                if (var.equals("Multa")) {
                    String ncasa = listasanciones.get(j).getN_unidad();
                    modc.setId_unidad(ncasa);
                    modc.setMonto(listasanciones.get(j).getMonto());
                    modc.setId_gasto(Integer.parseInt(String.valueOf(id_sancion[j])));
                    modc.guardarsancionpro(modc);
                }
            }
            modin.setId_condominio(panta1.rif.getText());
            listainteres = modin.listarInteresCerrames();
            int numInteres = listainteres.size();
            Object[] id_interes = new Object[numInteres];
            Object[] factor = new Object[numInteres];
            
            for (int l = 0; l < numInteres; l++) {
                id_interes[l] = listainteres.get(l).getId();
                factor[l] = listainteres.get(l).getFactor();
               
                for (int w = 0; w < numRegistro; w++) {
                    modc.setId_unidad(String.valueOf(num_casa[w]));
                    modc.buscartotal(modc);
                    double var6 = modc.getMonto();
                    modc.buscartotal2(modc);
                    double var7 = modc.getMonto();
                    double var9= Double.parseDouble(String.valueOf(factor[l]))/100;
                    double total = var6 + var7;
                    double parte_cuota = total * var9 ;
                    modc.setId_unidad(String.valueOf(num_casa[w]));
                    modc.setMonto(parte_cuota);
                    modc.setId_gasto(Integer.parseInt(String.valueOf(id_interes[l])));
                    
                    modc.registrar_interes(modc);
                }

            }

        }
    }

}
