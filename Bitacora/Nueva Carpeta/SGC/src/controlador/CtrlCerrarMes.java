/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.CerrarMes;
import modelo.Gasto;
import modelo.GastoComun;
import modelo.Interes;
import modelo.Sancion;
import modelo.Unidades;
import vista.Catalogo;
import vista.PantallaPrincipal;
import vista.VisCerrarMes;

/**
 *
 * @author rma
 */
public class CtrlCerrarMes implements ActionListener, KeyListener {
    
    private VisCerrarMes vista;
    private CerrarMes modc;
    private Unidades moduni;
    ArrayList<Unidades> listaunidades;
    ArrayList<GastoComun> listagastocomun;
    ArrayList<Gasto> listaGastos;
    ArrayList<Sancion> listasanciones;
    ArrayList<Interes> listainteres;
    ArrayList<CerrarMes> listaCierremes;
    private PantallaPrincipal panta1;
    private GastoComun modgac;
    private Gasto modcuo;
    private Sancion modsan;
    private Interes modin;
    private Catalogo catalogo;
    DefaultTableModel dm;
    
    public CtrlCerrarMes() {
        
        this.vista = new VisCerrarMes();
        this.modc = new CerrarMes();
        this.moduni = new Unidades();
        
        this.modgac = new GastoComun();
        this.modcuo = new Gasto();
        this.modsan = new Sancion();
        this.modin = new Interes();
        this.catalogo = new Catalogo();
        catalogo.lblTitulo.setText("Cerrar mes");
        
        CtrlVentana.cambiarVista(catalogo);
        Llenartabla(catalogo.tabla);
        vista.jButton1.addActionListener(this);
        vista.btnSalir.addActionListener(this);
        catalogo.btnNuevo.addActionListener(this);
        catalogo.txtBuscar.addKeyListener(this);
        
    }
    
    public void Llenartabla(JTable tablaD) {
        
        listaCierremes = modc.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                
                return false;
            }
            
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);
        
        modeloT.addColumn("Nº de Unidad");
        modeloT.addColumn("Mes");
        modeloT.addColumn("Año");
        modeloT.addColumn("Monto");
        
        Object[] columna = new Object[4];
        
        int numRegistro = listaCierremes.size();
        
        for (int i = 0; i < numRegistro; i++) {
            
            columna[0] = listaCierremes.get(i).uni.getN_unidad();
            columna[1] = listaCierremes.get(i).getMes_cierre();
            columna[2] = listaCierremes.get(i).getAño_cierre();
            columna[3] = listaCierremes.get(i).getMonto();
            
            modeloT.addRow(columna);
            
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(3).setCellRenderer(tcr);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == catalogo.btnNuevo) {
            CtrlVentana.cambiarVista(vista);
        }
        if (e.getSource() == vista.jButton1) {
            Calendar c1 = Calendar.getInstance();
            int messis = (c1.get(Calendar.MONTH)) + 1;
            int anniosis = (c1.get(Calendar.YEAR));
            
            modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
            modc.setAño_cierre(vista.jYearChooser1.getYear());
            int mm = modc.getMes_cierre();
            if (modc.getAño_cierre() <= anniosis) {
                if (modc.getAño_cierre() < anniosis) {
                    mm = mm - 13;
                }
                if (mm <= messis) {
                    if (modc.buscarfechas(modc)) {
                        JOptionPane.showMessageDialog(null, "Este mes ya se ha cerrado");
                    } else {
                        modc.setMes_cierre(vista.jMonthChooser1.getMonth() + 1);
                        modc.setAño_cierre(vista.jYearChooser1.getYear());
                        listaunidades = moduni.listar();
                        
                        int numRegistro = listaunidades.size();
                        
                        listaGastos = modcuo.listarGastos();
                        int numCuotas = listaGastos.size();
                        Object[] tipo_cuota = new Object[numCuotas];
                        Object[] id_cuota = new Object[numCuotas];
                        
                        int numReales = 0;
                        
                        int mes = modc.getMes_cierre();
                        int año = modc.getAño_cierre();
                        if (numCuotas == 0) {
                            
                        } else {
                            
                            for (int z = 0; z < numCuotas; z++) {
                                id_cuota[z] = listaGastos.get(z).getId();
                                int mes_c = listaGastos.get(z).getMes();
                                int año_c = listaGastos.get(z).getAnio();
                                int meses_r = listaGastos.get(z).getNumMeses();
                                tipo_cuota[z] = listaGastos.get(z).getCalcular();
                                int var1 = mes_c + meses_r;
                                double monto_t = listaGastos.get(z).getMonto();
                                
                                double parte_periodo = monto_t / meses_r;
                                
                                if (año >= año_c) {
                                    
                                    if (año > año_c) {
                                        mes_c = mes_c - 11;
                                        
                                    }
                                    
                                    if (mes >= mes_c) {
                                        
                                        if (var1 > 13) {
                                            int var2 = var1;
                                            var1 = var1 - 12;
                                            año_c = año_c + 1;
                                            if (var2 > 25) {
                                                var1 = var1 - 12;
                                                año_c = año_c + 1;
                                            }
                                        }
                                        if (año <= año_c) {
                                            
                                            if (mes < var1) {
                                                
                                                String tipo = String.valueOf(tipo_cuota[z]);
                                                
                                                if (numRegistro > 0) {
                                                    modc.gasto.setMesesRestantes(meses_r - 1);
                                                    modc.gasto.setId(Integer.parseInt(String.valueOf(id_cuota[z])));
                                                    
                                                    if (modc.gasto.getMesesRestantes() == 0) {
                                                        modc.setEstado("Mensualidad Completada");
                                                        modc.actualizar_cuota(modc);
                                                        
                                                    } else {
                                                        modc.setEstado("Mensualidad en Proceso");
                                                        modc.actualizar_cuota(modc);
                                                        
                                                    }
                                                }
                                                
                                                if (tipo.equals("Alicuota")) {
                                                    
                                                    for (int w = 0; w < numRegistro; w++) {
                                                        
                                                        double parte_cuota = parte_periodo * listaunidades.get(w).getAlicuota();
                                                        if (listaGastos.get(z).getMoneda().equals("Bolívar")) {
                                                            double paridad = Double.parseDouble(vista.txtParidad.getText());
                                                            double total_dolar = parte_cuota / paridad;
                                                            modc.setMonto_bolivar(parte_cuota);
                                                            modc.setMonto_dolar(total_dolar);
                                                        } else {
                                                            double paridad = Double.parseDouble(vista.txtParidad.getText());
                                                            double total_bolivar = parte_cuota * paridad;
                                                            modc.setMonto_bolivar(total_bolivar);
                                                            modc.setMonto_dolar(parte_cuota);
                                                        }
                                                        modc.setId(listaGastos.get(z).getId());
                                                        modc.setParidad(Double.parseDouble(vista.txtParidad.getText()));
                                                        modc.setMoneda_dominante(vista.cbxMoneda.getSelectedItem().toString());
                                                        modc.gasto.setId(Integer.parseInt(String.valueOf(id_cuota[z])));
                                                        modc.setTipo_gasto(listaGastos.get(z).getTipo());
                                                        modc.uni.setId(listaunidades.get(w).getId());
                                                        modc.registrar_cuota(modc);
                                                        
                                                        numReales = numReales + 1;
                                                        
                                                    }
                                                } else {
                                                    for (int w = 0; w < numRegistro; w++) {
                                                        
                                                        double parte_cuota = parte_periodo / numRegistro;
                                                        modc.setId(listaGastos.get(z).getId());
                                                        if (listaGastos.get(z).getMoneda().equals("Bolívar")) {
                                                            double paridad = Double.parseDouble(vista.txtParidad.getText());
                                                            double total_dolar = parte_cuota / paridad;
                                                            modc.setMonto_bolivar(parte_cuota);
                                                            modc.setMonto_dolar(total_dolar);
                                                        } else {
                                                            double paridad = Double.parseDouble(vista.txtParidad.getText());
                                                            double total_bolivar = parte_cuota * paridad;
                                                            modc.setMonto_bolivar(total_bolivar);
                                                            modc.setMonto_dolar(parte_cuota);
                                                        }
                                                        modc.uni.setId(listaunidades.get(w).getId());
                                                        modc.setTipo_gasto(listaGastos.get(z).getTipo());
                                                        modc.setParidad(Double.parseDouble(vista.txtParidad.getText()));
                                                        modc.setMoneda_dominante(vista.cbxMoneda.getSelectedItem().toString());
                                                        modc.registrar_cuota(modc);
                                                        
                                                        numReales = numReales + 1;
                                                        
                                                    }
                                                }
                                                
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        modsan.setMes(modc.getMes_cierre());
                        modsan.setAño(modc.getAño_cierre());
                        listasanciones = modsan.listarSancionesCerrarmes();
                        int numSanciones = listasanciones.size();
                        Object[] tipo_sancion = new Object[numSanciones];
                        Object[] factor_sancion = new Object[numSanciones];
                        Object[] id_sancion = new Object[numSanciones];
                        if (numReales == 0) {
                            
                        } else {
                            for (int j = 0; j < numSanciones; j++) {
                                tipo_sancion[j] = listasanciones.get(j).getTipo();
                                factor_sancion[j] = listasanciones.get(j).getMonto();
                                id_sancion[j] = listasanciones.get(j).getId();
                                String var = String.valueOf(tipo_sancion[j]);
                                
                                if (var.equals("Interes de mora")) {
                                    
                                    modc.gasto.setId(listasanciones.get(j).getId());
                                    
                                    modc.buscartotal(modc);
                                    double var7 = modc.getMonto();
                                    
                                    double totalf = Double.parseDouble(String.valueOf(factor_sancion[j])) / 100;
                                    double var3 = var7 * totalf;
                                    
                                    modc.gasto.setId(Integer.parseInt(String.valueOf(id_sancion[j])));
                                    modc.uni.setId(listasanciones.get(j).uni.getId());
                                    modc.setMonto(var3);
                                    modc.setEstado("Procesado");
                                    modc.setTipo_gasto("Sancion");
                                    modc.guardarsancionpro(modc);
                                    modc.actualizarSancion(modc);
                                    
                                }
                                
                                if (var.equals("Multa")) {
                                    
                                    modc.setId(listasanciones.get(j).getId());
                                    modc.setMonto(listasanciones.get(j).getMonto());
                                    modc.gasto.setId(Integer.parseInt(String.valueOf(id_sancion[j])));
                                    modc.uni.setId(listasanciones.get(j).uni.getId());
                                    modc.setTipo_gasto("Sancion");
                                    
                                    modc.setEstado("Procesado");
                                    modc.guardarsancionpro(modc);
                                    modc.actualizarSancion(modc);
                                    
                                }
                            }
                        }
                        
                        listainteres = modin.listarInteres();
                        int numInteres = listainteres.size();
                        Object[] id_interes = new Object[numInteres];
                        Object[] factor = new Object[numInteres];
                        if (numReales == 0) {
                            
                        } else {
                            for (int l = 0; l < numInteres; l++) {
                                id_interes[l] = listainteres.get(l).getId();
                                factor[l] = listainteres.get(l).getFactor();
                                
                                for (int w = 0; w < numRegistro; w++) {
                                    modc.uni.setId(listaunidades.get(w).getId());
                                    
                                    if (modc.buscartotal(modc)) {
                                        
                                    } else {
                                        modc.setMonto(0);
                                    }
                                    
                                    double var6 = modc.getMonto();
                                    
                                    double var9 = Double.parseDouble(String.valueOf(factor[l])) / 100;
                                    
                                    double parte_cuota = var6 * var9;
                                    
                                    modc.gasto.setId(listainteres.get(l).getId());
                                    modc.setMonto(parte_cuota);
                                    
                                    modc.uni.setId(listaunidades.get(w).getId());
                                    modc.setTipo_gasto("Interes");
                                    modc.registrar_interes(modc);
                                    
                                }
                                
                            }
                        }
                        
                        if (numReales > 0) {
                            
                            JOptionPane.showMessageDialog(null, "Cierre satisfactorio");
                            Llenartabla(catalogo.tabla);
                            CtrlVentana.cambiarVista(catalogo);
                        } else {
                            
                            modc.borrarnulo(modc);
                            JOptionPane.showMessageDialog(null, "No hay gastos por cerrar");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No puede cerrar un mes que no ha concluido");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No puede cerrar un mes que no ha concluido");
            }
        }
        if (e.getSource() == vista.btnSalir) {
            CtrlVentana.cambiarVista(catalogo);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catalogo.txtBuscar) {
            
            filtro(catalogo.txtBuscar.getText(), catalogo.tabla);
        } else {
            
        }
    }
    
    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
        
    }
    
    public class SComboBox extends VisCerrarMes{
  
     /** Constructor */
     public SComboBox()
     {
         Dimension dimension = new Dimension(200,32);
         setPreferredSize(dimension);
         setSize(dimension);      
         setForeground(Color.WHITE);        
         setBorder(BorderFactory.createLineBorder(new Color(71, 71, 71), 2));
         setUI(CustomUI.createUI(this));                
         setVisible(true);
     }
 
 }
    
}
