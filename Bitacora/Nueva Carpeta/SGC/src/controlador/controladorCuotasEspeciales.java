/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Asambleas;
import modelo.CerrarMes;
import modelo.CuotasEspeciales;
import modelo.ModeloConceptoGastos;
import modelo.Proveedores;
import vista.PantallaPrincipal1;
import vista.buscarProveedor;
import vista.catalogoCuotasEspeciales;
import vista.cuotasEspeciales;

/**
 *
 * @author rma
 */
public class controladorCuotasEspeciales implements ActionListener, MouseListener, KeyListener, WindowListener, ItemListener {
    
    private cuotasEspeciales cuotae;
    private catalogoCuotasEspeciales catacuoe;
    private Proveedores modpro;
    private ModeloConceptoGastos modcon;
    private Asambleas modasa;
    private PantallaPrincipal1 panta1;
    private CuotasEspeciales modcuo;
    private CerrarMes modc;
    private buscarProveedor buscpro;
    ArrayList<Proveedores> listaProveedores;
    DefaultTableModel dm;
    ArrayList<CuotasEspeciales> listacuotasEspeciales;
    ArrayList<ModeloConceptoGastos> listaConGas;
    ArrayList<Asambleas> listaasambleas;
    ArrayList<ModeloConceptoGastos> listaConcepto;
    ArrayList<CuotasEspeciales> listaConceptomod;
    
    public controladorCuotasEspeciales() {
        this.cuotae = new cuotasEspeciales();
        this.catacuoe = new catalogoCuotasEspeciales();
        this.modcon = new ModeloConceptoGastos();
        this.modpro = new Proveedores();
        this.modasa = new Asambleas();
        
        this.modcuo = new CuotasEspeciales();
        this.modc = new CerrarMes();
        this.buscpro = new buscarProveedor();
        
        this.catacuoe.jButton2.addActionListener(this);
        this.catacuoe.jTable1.addMouseListener(this);
        this.catacuoe.jTextField1.addKeyListener(this);
        this.catacuoe.addWindowListener(this);
        this.cuotae.btnEliminar.addActionListener(this);
        this.buscpro.jTable1.addMouseListener(this);
        this.cuotae.btnBuscarproveedor.addActionListener(this);
        this.cuotae.btnGuardar.addActionListener(this);
        this.cuotae.btnLimpiar.addActionListener(this);
        this.cuotae.btnModificar.addActionListener(this);
        this.cuotae.si.addMouseListener(this);
        this.cuotae.no.addMouseListener(this);
        this.cuotae.jTable.addMouseListener(this);
        this.cuotae.jTable.addKeyListener(this);
        this.cuotae.jcombotipo.addItemListener(this);
        cuotae.txtNmeses.addKeyListener(this);
        
        cuotae.txaObservaciones.addKeyListener(this);
        listaConGas = modcon.listarConcepto();
        listaasambleas = modasa.listarAsambleas();
        this.catacuoe.setVisible(true);
    }
    
    public void llenartablaCuotasEspeciales(JTable tablaD) {
        
        listacuotasEspeciales = modcuo.listarCuotasEspeciales("");
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                
                return false;
            }
            
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);
        
        modeloT.addColumn("<html>Nº de <br> Cuota Especial</html>");
        modeloT.addColumn("Proveedor");
        
        modeloT.addColumn("<html>Calcular <br> Por</html>");
        modeloT.addColumn("<html>Comienzo de <br> Cobro</html>");
        modeloT.addColumn("<html>Monto <br> Inicial</html>");
        modeloT.addColumn("Saldo");
        modeloT.addColumn("Asamblea");
        modeloT.addColumn("<html>Meses <br> Iniciales</html>");
        modeloT.addColumn("<html>Meses <br> Restantes</html>");
        modeloT.addColumn("Observación");
        modeloT.addColumn("Estado");
        modeloT.addColumn("Estado Pago");
        
        Object[] columna = new Object[13];
        
        int numRegistro = listacuotasEspeciales.size();
        int ind;
        
        for (int i = 0; i < numRegistro; i++) {
            ind = 0;
            columna[ind++] = listacuotasEspeciales.get(i).getId();
            columna[ind++] = listacuotasEspeciales.get(i).prov.getCedula();
            
            String fecha = String.valueOf(listacuotasEspeciales.get(i).getMes()) + "-" + listacuotasEspeciales.get(i).getAño();
            columna[ind++] = listacuotasEspeciales.get(i).getCalcular();
            columna[ind++] = fecha;
            columna[ind++] = Validacion.formato1.format(listacuotasEspeciales.get(i).getMonto());
            columna[ind++] = Validacion.formato1.format(listacuotasEspeciales.get(i).getSaldo());
            columna[ind++] = listacuotasEspeciales.get(i).asa.getNombre_asamblea();
            columna[ind++] = listacuotasEspeciales.get(i).getN_meses();
            columna[ind++] = listacuotasEspeciales.get(i).getN_meses_restantes();
            columna[ind++] = listacuotasEspeciales.get(i).getObservacion();
            columna[ind++] = listacuotasEspeciales.get(i).getEstado();
            columna[ind++] = listacuotasEspeciales.get(i).getPagado();
            
            modeloT.addRow(columna);
            
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            tablaD.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    
    public void Llenartablaconcepto(JTable tablaD) {
        
        listaConcepto = modcon.listarConcepto();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                
                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = true;
                }
                if (column == 3) {
                    resu = true;
                }
                
                return resu;
            }
            
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);
        
        modeloT.addColumn("Categoria");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Monto");
        
        Object[] columna = new Object[4];
        
        int numRegistro = listaConcepto.size();
        
        for (int i = 0; i < numRegistro; i++) {
            
            columna[0] = listaConcepto.get(i).cate.getNombre();
            columna[1] = listaConcepto.get(i).getNombre_Concepto();
            
            modeloT.addRow(columna);
            
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        
    }
    
    public void Llenartablaconceptomodificar(JTable tablaD, int x) {
        
        if (x == 0) {
            listaConceptomod = modcuo.listarconceptosmodificar(0);
        }
        if (x == 1) {
            listaConceptomod = modcuo.listarconceptosmodificar(1);
        }
        
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                
                boolean resu = false;
                if (column == 0) {
                    resu = false;
                }
                if (column == 1) {
                    resu = false;
                }
                if (column == 2) {
                    resu = true;
                }
                if (column == 3) {
                    resu = true;
                }
                
                return resu;
            }
            
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);
        
        modeloT.addColumn("Categoria");
        modeloT.addColumn("Concepto");
        modeloT.addColumn("Seleccione");
        modeloT.addColumn("Monto");
        
        Object[] columna = new Object[4];
        
        int numRegistro = listaConceptomod.size();
        
        for (int i = 0; i < numRegistro; i++) {
            
            columna[0] = listaConceptomod.get(i).cate.getNombre();
            columna[1] = listaConceptomod.get(i).concep.getNombre_Concepto();
            if (listaConceptomod.get(i).concep.getId() == 0) {
                columna[2] = Boolean.FALSE;
            } else {
                columna[2] = Boolean.TRUE;
            }
            if (listaConceptomod.get(i).getMonto() == 0) {
                columna[3] = "";
            } else {
                columna[3] = listaConceptomod.get(i).getMonto();
            }
            
            modeloT.addRow(columna);
            
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        
    }
    
    public void Llenartabla(JTable tablaD) {
        
        listaProveedores = modpro.listar();
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                
                return false;
            }
            
        };
        tablaD.setModel(modeloT);
        tablaD.getTableHeader().setReorderingAllowed(false);
        tablaD.getTableHeader().setResizingAllowed(false);
        
        modeloT.addColumn("Cédula/Rif");
        modeloT.addColumn("Nombre/Razón Social");
        modeloT.addColumn("Teléfono");
        
        Object[] columna = new Object[3];
        
        int numRegistro = listaProveedores.size();
        
        for (int i = 0; i < numRegistro; i++) {
            
            columna[0] = listaProveedores.get(i).getCedula();
            columna[1] = listaProveedores.get(i).getNombre();
            columna[2] = listaProveedores.get(i).getTelefono();
            
            modeloT.addRow(columna);
            
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tablaD.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tablaD.getColumnModel().getColumn(2).setCellRenderer(tcr);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == catacuoe.jButton2) {
            this.cuotae.setVisible(true);
            this.cuotae.btnModificar.setEnabled(false);
            this.cuotae.btnGuardar.setEnabled(true);
            this.cuotae.btnEliminar.setEnabled(false);
            modcuo.setEstado("Pendiente");
            this.cuotae.jCalcular.setEnabled(true);
            Llenartablaconcepto(cuotae.jTable);
            addCheckBox(2, cuotae.jTable);
            cuotae.jAsamblea.removeAllItems();
            
            listaasambleas = modasa.listarAsambleas();
            listaConGas = modcon.listarConcepto();
            
            crearCbxAsamblea(listaasambleas);
            this.cuotae.txtid.setVisible(false);
            cuotae.txaObservaciones.setText("");
            
            cuotae.txtNmeses.setText("");
            cuotae.txtid.setText("");
            cuotae.txtProveedor.setText("");
            cuotae.btnBuscarproveedor.setVisible(true);
            cuotae.jLabel2.setText("");
            
        }
        if (e.getSource() == cuotae.btnBuscarproveedor) {
            this.buscpro.setVisible(true);
            Llenartabla(buscpro.jTable1);
        }
        
        if (e.getSource() == cuotae.btnGuardar) {
            if (validar()) {
                if (cuotae.jTable.isEditing()) {//si se esta edtando la tabla
                    cuotae.jTable.getCellEditor().stopCellEditing();//detenga la edicion
                }
                String var10 = cuotae.jCalcular.getSelectedItem().toString();
                if (var10.equals("Total de Inmuebles")) {
                    var10 = "Total de Inmuebles";
                }
                modasa.setNombre_asamblea(cuotae.jAsamblea.getSelectedItem().toString());
                if (modasa.getNombre_asamblea().equals("Seleccione la Asamblea") && cuotae.si.isSelected()) {
                    JOptionPane.showMessageDialog(null, "seleccione una asamblea");
                } else {
                    modcuo.setTipo(cuotae.jcombotipo.getSelectedItem().toString());
                    if (modcuo.getTipo().equals("Ordinario")) {
                        modcuo.setN_meses(1);
                        modcuo.setN_meses_restantes(1);
                    } else {
                        if (cuotae.txtNmeses.getText().equals("")) {
                            modcuo.setN_meses(1);
                            modcuo.setN_meses_restantes(1);
                        } else {
                            modcuo.setN_meses(Integer.parseInt(cuotae.txtNmeses.getText()));
                            modcuo.setN_meses_restantes(Integer.parseInt(cuotae.txtNmeses.getText()));
                        }
                    }
                    modcuo.setCalcular(var10);
                    modcuo.prov.setCedula(cuotae.txtProveedor.getText());
                    modcuo.setMes(cuotae.jMonthChooser1.getMonth() + 1);
                    modcuo.setAño(cuotae.jYearChooser1.getYear());
                    
                    if (modcuo.getN_meses() > 20) {
                        JOptionPane.showMessageDialog(null, "El maximo de meses para dividir el pago es 20");
                    } else {
                        
                        modcuo.setObservacion(cuotae.txaObservaciones.getText());
                        modcuo.setEstado("Pendiente");
                        
                        int var1 = 0;
                        int bre = 0;
                        var1 = modcuo.getMes();
                        
                        for (int i = 0; i < modcuo.getN_meses(); i++) {
                            
                            if (var1 + i > 12) {
                                var1 = var1 - 12;
                                if (var1 + 1 > 24) {
                                    var1 = var1 - 12;
                                }
                            }
                            
                            modc.setMes_cierre(var1 + i);
                            
                            modc.setAño_cierre(modcuo.getAño());
                            if (modc.buscarfechas(modc)) {
                                bre = 1;
                            } else {
                            }
                        }
                        
                        if (bre == 1) {
                            JOptionPane.showMessageDialog(null, "no puede registrar cuotas especiales que incluyan un periodo ya cerrado");
                            
                        } else {
                            
                            if (cuotae.jAsamblea.getSelectedItem().toString() == "Seleccione la Asamblea") {
                                modcuo.setId(0);
                            } else {
                                int ind1 = cuotae.jAsamblea.getSelectedIndex() - 1;
                                listaasambleas = modasa.listarAsambleas();
                                modcuo.setId(listaasambleas.get(ind1).getId());
                            }
                            int j = 0;
                            int x = 0;
                            int l = 0;
                            int xd = 0;
                            double monto = 0;
                            for (int i = 0; i < cuotae.jTable.getRowCount(); i++) {
                                if (valueOf(cuotae.jTable.getValueAt(i, 2)) == "true") {
                                    j = j + 1;
                                    
                                    if (!valueOf(cuotae.jTable.getValueAt(i, 3)).equals("")) {
                                        x = x + 1;
                                        String numero = valueOf(cuotae.jTable.getValueAt(i, 3));
                                        if (isValidDouble(numero)) {
                                            l = l + 1;
                                            monto = monto + Double.parseDouble(valueOf(cuotae.jTable.getValueAt(i, 3)));
                                            if (Double.parseDouble(valueOf(cuotae.jTable.getValueAt(i, 3))) == 0) {
                                                xd = xd + 1;
                                            }
                                        }
                                    }
                                    
                                }
                            }
                            if (j == 0) {
                                JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                            } else {
                                if (j != x) {
                                    JOptionPane.showMessageDialog(null, "debe ingresar el monto al concepto seleccionado");
                                } else {
                                    if (l != x) {
                                        JOptionPane.showMessageDialog(null, "por favor ingrese solo numeros en la tabla");
                                    } else {
                                        if (xd >= 1) {
                                            JOptionPane.showMessageDialog(null, "0 no es un monto valido");
                                        } else {
                                            modcuo.setMonto(monto);
                                            modcuo.setSaldo(monto);
                                            if (modcuo.registrar_cuota_especial(modcuo)) {
                                                modcuo.buscId(modcuo);
                                                listaConcepto = modcon.listarConcepto();
                                                
                                                for (int i = 0; i < cuotae.jTable.getRowCount(); i++) {
                                                    
                                                    if (valueOf(cuotae.jTable.getValueAt(i, 2)) == "true") {
                                                        modcuo.concep.setId(listaConcepto.get(i).getId());
                                                        modcuo.setMonto(Double.parseDouble(valueOf(cuotae.jTable.getValueAt(i, 3))));
                                                        modcuo.registrar_puente(modcuo);
                                                    }
                                                    
                                                    llenartablaCuotasEspeciales(catacuoe.jTable1);
                                                    
                                                }
                                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                            } else {
                                                
                                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");
                                                
                                            }
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        if (e.getSource() == cuotae.btnModificar) {
            if (validar()) {
                if (cuotae.jTable.isEditing()) {//si se esta edtando la tabla
                    cuotae.jTable.getCellEditor().stopCellEditing();//detenga la edicion
                }
                modasa.setNombre_asamblea(cuotae.jAsamblea.getSelectedItem().toString());
                if (modasa.getNombre_asamblea().equals("Seleccione la Asamblea") && cuotae.si.isSelected()) {
                    JOptionPane.showMessageDialog(null, "seleccione una asamblea");
                } else {
                    
                    modcuo.setCalcular(cuotae.jCalcular.getSelectedItem().toString());
                    modcuo.setMes(cuotae.jMonthChooser1.getMonth() + 1);
                    modcuo.setAño(cuotae.jYearChooser1.getYear());
                    
                    modcuo.setTipo(cuotae.jcombotipo.getSelectedItem().toString());
                    if (modcuo.getTipo().equals("Ordinario")) {
                        modcuo.setN_meses(1);
                        modcuo.setN_meses_restantes(1);
                    } else {
                        if (cuotae.txtNmeses.getText().equals("")) {
                            modcuo.setN_meses(1);
                            modcuo.setN_meses_restantes(1);
                        } else {
                            modcuo.setN_meses(Integer.parseInt(cuotae.txtNmeses.getText()));
                            modcuo.setN_meses_restantes(Integer.parseInt(cuotae.txtNmeses.getText()));
                        }
                    }
                    modcuo.prov.setCedula(cuotae.txtProveedor.getText());
                    if (modcuo.getN_meses() > 20) {
                        JOptionPane.showMessageDialog(null, "El maximo de meses para dividir el pago es 20");
                    } else {
                        
                        modcuo.setObservacion(cuotae.txaObservaciones.getText());
                        modcuo.setEstado("Pendiente");
                        modcuo.setId(Integer.parseInt(cuotae.txtid.getText()));
                        int var1 = 0;
                        int bre = 0;
                        var1 = modcuo.getMes();
                        
                        for (int i = 0; i < modcuo.getN_meses(); i++) {
                            
                            if (var1 + i > 12) {
                                var1 = var1 - 12;
                                if (var1 + 1 > 24) {
                                    var1 = var1 - 12;
                                }
                            }
                            
                            modc.setMes_cierre(var1 + i);
                            
                            modc.setAño_cierre(modcuo.getAño());
                            if (modc.buscarfechas(modc)) {
                                bre = 1;
                            } else {
                            }
                        }
                        
                        if (bre == 1) {
                            JOptionPane.showMessageDialog(null, "no puede registrar cuotas especiales que incluyan un periodo ya cerrado");
                            
                        } else {
                            if (cuotae.jAsamblea.getSelectedItem().toString() == "Seleccione la Asamblea") {
                                modcuo.asa.setId(0);
                            } else {
                                listaasambleas = modasa.listarAsambleas();
                                int ind1 = cuotae.jAsamblea.getSelectedIndex() - 1;
                                JOptionPane.showMessageDialog(null, ind1);
                                JOptionPane.showMessageDialog(null, listaasambleas.get(ind1).getId());
                                modcuo.asa.setId(listaasambleas.get(ind1).getId());
                            }
                            int j = 0;
                            int x = 0;
                            int l = 0;
                            int xd = 0;
                            double monto = 0;
                            for (int i = 0; i < cuotae.jTable.getRowCount(); i++) {
                                if (valueOf(cuotae.jTable.getValueAt(i, 2)) == "true") {
                                    j = j + 1;
                                    
                                    if (!valueOf(cuotae.jTable.getValueAt(i, 3)).equals("")) {
                                        x = x + 1;
                                        String numero = valueOf(cuotae.jTable.getValueAt(i, 3));
                                        if (isValidDouble(numero)) {
                                            l = l + 1;
                                            monto = monto + Double.parseDouble(valueOf(cuotae.jTable.getValueAt(i, 3)));
                                            if (Double.parseDouble(valueOf(cuotae.jTable.getValueAt(i, 3))) == 0) {
                                                xd = xd + 1;
                                            }
                                        }
                                    }
                                    
                                }
                            }
                            if (j == 0) {
                                JOptionPane.showMessageDialog(null, "debe seleccionar al menos 1 registro de la tabla");
                            } else {
                                if (j != x) {
                                    JOptionPane.showMessageDialog(null, "debe ingresar el monto al concepto seleccionado");
                                } else {
                                    if (l != x) {
                                        JOptionPane.showMessageDialog(null, "por favor ingrese solo numeros en la tabla");
                                    } else {
                                        if (xd >= 1) {
                                            JOptionPane.showMessageDialog(null, "0 no es un monto valido");
                                        } else {
                                            modcuo.setMonto(monto);
                                            modcuo.setSaldo(monto);
                                            
                                            if (modcuo.modificar_cuota_especial(modcuo)) {
                                                modcuo.eliminar_puente(modcuo);
                                                listaConcepto = modcon.listarConcepto();
                                                
                                                for (int i = 0; i < cuotae.jTable.getRowCount(); i++) {
                                                    
                                                    if (valueOf(cuotae.jTable.getValueAt(i, 2)) == "true") {
                                                        modcuo.concep.setId(listaConcepto.get(i).getId());
                                                        modcuo.setMonto(Double.parseDouble(valueOf(cuotae.jTable.getValueAt(i, 3))));
                                                        modcuo.registrar_puente(modcuo);
                                                    }
                                                    
                                                }
                                                JOptionPane.showMessageDialog(null, "registro modificado");
                                                llenartablaCuotasEspeciales(catacuoe.jTable1);
                                                this.cuotae.dispose();
                                            } else {
                                                
                                                JOptionPane.showMessageDialog(null, "Este Registro Ya Existe");
                                                
                                            }
                                            
                                        }
                                    }
                                    
                                }
                                
                            }
                            
                        }
                    }
                }
            }
        }
        
        if (e.getSource() == cuotae.btnEliminar) {
            
            modcuo.setId(Integer.parseInt(cuotae.txtid.getText()));
            
            if (modcuo.eliminar_cuotas_especiales(modcuo)) {
                modcuo.eliminar_puente(modcuo);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                llenartablaCuotasEspeciales(catacuoe.jTable1);
                this.cuotae.dispose();
                
            } else {
                
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                
            }
            
        }
        if (e.getSource() == cuotae.btnLimpiar) {
            limpiar();
        }
    }
    
    public void limpiar() {
        
        cuotae.txtNmeses.setText(null);
        
        cuotae.txaObservaciones.setText(null);
        cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
        
        cuotae.jMonthChooser1.setMonth(0);
        cuotae.txtProveedor.setText(null);
        cuotae.jYearChooser1.setYear(0);
        
    }
    
    private Boolean validar() {
        
        Boolean resultado = true;
        String msj = "";
        
        if (!resultado) {
            
            JOptionPane.showMessageDialog(null, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        return resultado;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == cuotae.si) {
            if (cuotae.si.isSelected()) {
                if (modcuo.getEstado().equals("Pendiente")) {
                    cuotae.jAsamblea.setVisible(true);
                    
                    listaasambleas = modasa.listarAsambleas();
                    cuotae.jAsamblea.removeAllItems();
                    crearCbxAsamblea(listaasambleas);
                    cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                }
            }
        }
        if (e.getSource() == cuotae.no) {
            if (cuotae.no.isSelected()) {
                if (modcuo.getEstado().equals("Pendiente")) {
                    cuotae.jAsamblea.setVisible(false);
                    cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                    
                }
            }
        }
        
        if (e.getSource() == catacuoe.jTable1) {
            cuotae.si.setEnabled(true);
            cuotae.no.setEnabled(true);
            
            int fila = this.catacuoe.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.catacuoe.jTable1.getValueAt(fila, 0)); // por ultimo, obtengo el valor de la celda
            modcuo.setId(Integer.parseInt(dato));
            
            modcuo.buscarCuotaEspecial(modcuo);
            this.cuotae.setVisible(true);
            cuotae.btnBuscarproveedor.setVisible(true);
            cuotae.txtid.setVisible(false);
            cuotae.txtid.setText(dato);
            cuotae.jAsamblea.removeAllItems();
            cuotae.jLabel2.setText(modcuo.prov.getNombre());
            
            cuotae.txtProveedor.setText(modcuo.prov.getCedula());
            
            cuotae.jCalcular.setSelectedItem(modcuo.getCalcular());
            
            cuotae.jMonthChooser1.setMonth(modcuo.getMes() - 1);
            cuotae.jYearChooser1.setYear(modcuo.getAño());
            cuotae.txaObservaciones.setText(modcuo.getObservacion());
            
            cuotae.txtNmeses.setText(String.valueOf(modcuo.getN_meses()));
            if (modcuo.getEstado().equals("Pendiente")) {
                cuotae.btnEliminar.setEnabled(true);
                cuotae.btnModificar.setEnabled(true);
                cuotae.btnGuardar.setEnabled(false);
                listaasambleas = modasa.listarAsambleas();
                Llenartablaconceptomodificar(cuotae.jTable, 0);
                addCheckBox(2, cuotae.jTable);
                
                crearCbxAsamblea(listaasambleas);
                if (modcuo.asa.getNombre_asamblea() == null) {
                    cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                    cuotae.jAsamblea.setVisible(false);
                    cuotae.no.setSelected(true);
                } else {
                    cuotae.jAsamblea.setSelectedItem(modcuo.asa.getNombre_asamblea() + " " + modcuo.getFecha());
                    cuotae.si.setSelected(true);
                    cuotae.jAsamblea.setVisible(true);
                }
                
            } else {
                Llenartablaconceptomodificar(cuotae.jTable, 1);
                addCheckBox(2, cuotae.jTable);
                if (modcuo.asa.getNombre_asamblea() == null) {
                    cuotae.jAsamblea.addItem("Seleccione la Asamblea");
                    cuotae.jAsamblea.setSelectedItem("Seleccione la Asamblea");
                    cuotae.jAsamblea.setVisible(false);
                    cuotae.no.setSelected(true);
                    cuotae.no.setEnabled(false);
                    cuotae.si.setEnabled(false);
                } else {
                    cuotae.jAsamblea.addItem(modcuo.asa.getNombre_asamblea() + " " + modcuo.getFecha());
                    cuotae.jAsamblea.setSelectedItem(modcuo.asa.getNombre_asamblea() + " " + modcuo.getFecha());
                    cuotae.si.setSelected(true);
                    cuotae.si.setEnabled(false);
                    cuotae.no.setEnabled(false);
                }
                cuotae.btnEliminar.setEnabled(false);
                cuotae.btnModificar.setEnabled(false);
                cuotae.btnGuardar.setEnabled(false);
                cuotae.btnBuscarproveedor.setVisible(false);
                JOptionPane.showMessageDialog(null, "las cuotas especiales en proceso no puenden ser modificadas ni eliminadas");
                
            }
            
        }
        if (e.getSource() == buscpro.jTable1) {
            int fila1 = this.buscpro.jTable1.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.buscpro.jTable1.getValueAt(fila1, 0)); // por ultimo, obtengo el valor de la celda
            String dato1 = String.valueOf(this.buscpro.jTable1.getValueAt(fila1, 1)); // por ultimo, obtengo el valor de la celda
            cuotae.txtProveedor.setText(dato);
            cuotae.jLabel2.setText(dato1);
            buscpro.dispose();
        }
        if (e.getSource() == cuotae.jTable) {
            int fila = this.cuotae.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            String dato = String.valueOf(this.cuotae.jTable.getValueAt(fila, 2)); // por ultimo, obtengo el valor de la celda

            if (dato.equals("true")) {
                cuotae.jTable.editCellAt(fila, 3);
            }
        }
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
        if (e.getSource() == cuotae.txaObservaciones) {
            
            Validacion.limite(e, cuotae.txaObservaciones.getText(), 500);
        }
        if (e.getSource() == cuotae.txtNmeses) {
            Validacion.Espacio(e);
            Validacion.soloNumeros(e);
            Validacion.limite(e, cuotae.txtNmeses.getText(), 2);
        }
        
        if (e.getSource() == cuotae.jTable) {
            
            int fila = this.cuotae.jTable.getSelectedRow(); // primero, obtengo la fila seleccionada
            String pepe = String.valueOf(this.cuotae.jTable.getValueAt(fila, 3));
            System.out.println(pepe);
            
            Validacion.soloUnPunto(e, pepe);
            
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == catacuoe.jTextField1) {
            
            filtro(catacuoe.jTextField1.getText(), catacuoe.jTable1);
        }
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        
        llenartablaCuotasEspeciales(catacuoe.jTable1);
        
        Component[] components = cuotae.jPanel2.getComponents();
        JComponent[] com = {
            cuotae.txtNmeses, cuotae.txaObservaciones
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
    
    private void filtro(String consulta, JTable jtableBuscar) {
        dm = (DefaultTableModel) jtableBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jtableBuscar.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consulta));
        
    }
    
    private void crearCbxAsamblea(ArrayList<Asambleas> datos) {
        cuotae.jAsamblea.addItem("Seleccione la Asamblea");
        
        if (datos != null) {
            for (Asambleas datosX : datos) {
                modasa = datosX;
                cuotae.jAsamblea.addItem(modasa.getNombre_asamblea() + " " + modasa.getFecha());
            }
            
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cuotae.jcombotipo) {
            int q = cuotae.jcombotipo.getSelectedIndex();
            if (q == 1) {
                cuotae.barrita.setVisible(false);
                cuotae.barritahorizontal.setVisible(false);
                cuotae.labelAsamblea.setVisible(false);
                cuotae.txtNmeses.setVisible(false);
                cuotae.labelmense.setVisible(false);
                cuotae.fue_elegido.setVisible(false);
                cuotae.no.setVisible(false);
                cuotae.si.setVisible(false);
                cuotae.jAsamblea.setVisible(false);
            }
            if (q == 0) {
                cuotae.barrita.setVisible(true);
                cuotae.barritahorizontal.setVisible(true);
                cuotae.labelAsamblea.setVisible(true);
                cuotae.txtNmeses.setVisible(true);
                cuotae.labelmense.setVisible(true);
                cuotae.fue_elegido.setVisible(true);
                cuotae.no.setVisible(true);
                cuotae.si.setVisible(true);
                cuotae.jAsamblea.setVisible(true);
            }
        }
    }
    
    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
    
    private static boolean isValidDouble(String s) {
        boolean isValid = true;
        
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            isValid = false;
        }
        
        return isValid;
    }
}
