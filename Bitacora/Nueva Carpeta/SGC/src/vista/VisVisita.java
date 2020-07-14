package vista;

import java.awt.Color;
import static java.awt.Color.white;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import sun.swing.table.DefaultTableCellHeaderRenderer;

public class VisVisita extends javax.swing.JPanel {

    //Aquí cambias la trasparencia de la barra mientras el cursor está encima. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
    //Aquí cambias la trasparencia de la barra. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA = 100;
    private static final int THUMB_BORDER_SIZE = 5;
    //Aquí cambias el grosor de la barra
    private static final int THUMB_SIZE = 8;
    //Aquí cambias el color de la barra
    private static final Color THUMB_COLOR = Color.BLUE;

    public VisVisita() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI());
        tabla.getTableHeader().setDefaultRenderer(new VisMensaje.Headercolor());
        cbxUnidad.setUI(new VisCerrarMes.CustomUI());
        cbxCedula.setUI(new VisCerrarMes.CustomUI());
        panelAuto.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtApellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        btnBuscarPersona = new javax.swing.JButton();
        txtCedula = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        cbxUnidad = new javax.swing.JComboBox<>();
        cbxCedula = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelAuto = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        txtModelo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        txtAcompanantes = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rBtnSi = new javax.swing.JRadioButton();
        rBtnNo = new javax.swing.JRadioButton();
        btnEntrada = new javax.swing.JButton();
        btnSalida = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 94, 159), 5, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Registro de Visitas");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtApellido.setBackground(new java.awt.Color(0, 94, 159));
        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.setBorder(null);
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 270, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cédula:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 60, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apellido:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, 20));

        txtNombre.setBackground(new java.awt.Color(0, 94, 159));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setBorder(null);
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 270, 20));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 270, 10));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setOpaque(true);
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 740, 0));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 270, 10));

        btnBuscarPersona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscarPersona.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPersona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda.png"))); // NOI18N
        btnBuscarPersona.setToolTipText("Buscar");
        btnBuscarPersona.setBorder(null);
        btnBuscarPersona.setBorderPainted(false);
        btnBuscarPersona.setContentAreaFilled(false);
        btnBuscarPersona.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarPersona.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnBuscarPersona.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnBuscarPersona.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        jPanel2.add(btnBuscarPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 40, 40));

        txtCedula.setBackground(new java.awt.Color(0, 94, 159));
        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCedula.setForeground(new java.awt.Color(255, 255, 255));
        txtCedula.setBorder(null);
        jPanel2.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 170, 20));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 210, 10));

        cbxUnidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(cbxUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 270, 30));

        cbxCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxCedula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V", "E" }));
        jPanel2.add(cbxCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 50, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 70, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Automóvil:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 80, -1));

        panelAuto.setBackground(new java.awt.Color(0, 94, 159));
        panelAuto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Color:");
        panelAuto.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, 20));

        txtColor.setBackground(new java.awt.Color(0, 94, 159));
        txtColor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtColor.setForeground(new java.awt.Color(255, 255, 255));
        txtColor.setBorder(null);
        panelAuto.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 270, 20));

        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        panelAuto.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 270, 10));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        panelAuto.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 10));

        txtModelo.setBackground(new java.awt.Color(0, 94, 159));
        txtModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtModelo.setForeground(new java.awt.Color(255, 255, 255));
        txtModelo.setBorder(null);
        panelAuto.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 270, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Modelo:");
        panelAuto.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, 20));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        panelAuto.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 270, 10));

        txtAcompanantes.setBackground(new java.awt.Color(0, 94, 159));
        txtAcompanantes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAcompanantes.setForeground(new java.awt.Color(255, 255, 255));
        txtAcompanantes.setBorder(null);
        panelAuto.add(txtAcompanantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 270, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("<HTML>Número de<BR>acompañantes:</HTML>");
        panelAuto.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, -1, -1));

        txtMatricula.setBackground(new java.awt.Color(0, 94, 159));
        txtMatricula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMatricula.setForeground(new java.awt.Color(255, 255, 255));
        txtMatricula.setBorder(null);
        panelAuto.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 270, 20));

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        panelAuto.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 270, 10));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Matrícula:");
        panelAuto.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 20));

        jPanel2.add(panelAuto, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 420, 210));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("<html>\nNúmero de <br> Unidad:\n</html>");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, -1, -1));

        buttonGroup.add(rBtnSi);
        rBtnSi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rBtnSi.setForeground(new java.awt.Color(255, 255, 255));
        rBtnSi.setText("Sí");
        rBtnSi.setContentAreaFilled(false);
        rBtnSi.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rBtnSi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot.png"))); // NOI18N
        rBtnSi.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot (1).png"))); // NOI18N
        rBtnSi.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button (1).png"))); // NOI18N
        rBtnSi.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button.png"))); // NOI18N
        rBtnSi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rBtnSiItemStateChanged(evt);
            }
        });
        jPanel2.add(rBtnSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 50, -1));

        buttonGroup.add(rBtnNo);
        rBtnNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rBtnNo.setForeground(new java.awt.Color(255, 255, 255));
        rBtnNo.setSelected(true);
        rBtnNo.setText("No");
        rBtnNo.setToolTipText("");
        rBtnNo.setContentAreaFilled(false);
        rBtnNo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rBtnNo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot.png"))); // NOI18N
        rBtnNo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot (1).png"))); // NOI18N
        rBtnNo.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button (1).png"))); // NOI18N
        rBtnNo.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button.png"))); // NOI18N
        rBtnNo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rBtnNoItemStateChanged(evt);
            }
        });
        jPanel2.add(rBtnNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 60, -1));

        btnEntrada.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEntrada.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnEntrada.setText("Registrar Entrada");
        btnEntrada.setToolTipText("Agregar visita");
        btnEntrada.setBorder(null);
        btnEntrada.setBorderPainted(false);
        btnEntrada.setContentAreaFilled(false);
        btnEntrada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEntrada.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnEntrada.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnEntrada.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, -1, -1));

        btnSalida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalida.setForeground(new java.awt.Color(255, 255, 255));
        btnSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnSalida.setText("Registrar Salida");
        btnSalida.setToolTipText("Agregar visita");
        btnSalida.setBorder(null);
        btnSalida.setBorderPainted(false);
        btnSalida.setContentAreaFilled(false);
        btnSalida.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnSalida.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnSalida.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        jPanel2.add(btnSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, -1, -1));

        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnNuevo.setText("Nuevo registro");
        btnNuevo.setToolTipText("Agregar visita");
        btnNuevo.setBorder(null);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnNuevo.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnNuevo.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel2.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 880, 280));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 850, -1));

        jScrollPane3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabla.setRowHeight(35);
        jScrollPane3.setViewportView(tabla);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 970, 170));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Búsqueda:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, -1, 20));

        txtBusqueda.setBackground(new java.awt.Color(0, 94, 159));
        txtBusqueda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        txtBusqueda.setBorder(null);
        jPanel1.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 380, 240, 20));

        jSeparator12.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 400, 240, 10));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, 300));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 850, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, -1, 300));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 1030, 610));
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntradaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void rBtnSiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rBtnSiItemStateChanged
        if(rBtnSi.isSelected()){
                    panelAuto.setVisible(true);
        }
    }//GEN-LAST:event_rBtnSiItemStateChanged

    private void rBtnNoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rBtnNoItemStateChanged
        if(rBtnNo.isSelected()){
                    panelAuto.setVisible(false);
        }
    }//GEN-LAST:event_rBtnNoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscarPersona;
    public javax.swing.JButton btnEntrada;
    public javax.swing.JButton btnNuevo;
    public javax.swing.JButton btnSalida;
    public javax.swing.ButtonGroup buttonGroup;
    public javax.swing.JComboBox<String> cbxCedula;
    public javax.swing.JComboBox<String> cbxUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelAuto;
    public javax.swing.JRadioButton rBtnNo;
    public javax.swing.JRadioButton rBtnSi;
    public javax.swing.JTable tabla;
    public javax.swing.JTextField txtAcompanantes;
    public javax.swing.JTextField txtApellido;
    public javax.swing.JTextField txtBusqueda;
    public javax.swing.JTextField txtCedula;
    public javax.swing.JTextField txtColor;
    public javax.swing.JTextField txtMatricula;
    public javax.swing.JTextField txtModelo;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
static public class Headercolor extends DefaultTableCellHeaderRenderer {

        public Headercolor() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable jTable1, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(jTable1, value, selected, focused, row, column);
            setBackground(new java.awt.Color(0, 94, 159));
            setForeground(Color.white);
            setFont(new Font("Tahoma", Font.BOLD, 14));
            return this;
        }
    }

    public class MyScrollBarUI extends BasicScrollBarUI {

        @Override
        public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            trackBounds.contains(thumbRect);
            g.setColor(new java.awt.Color(255, 255, 255));
            g.drawRect(0, 0, 500, 500);
            g.fillRect(0, 0, 500, 500);
        }

        @Override
        public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
            int orientation = scrollbar.getOrientation();
            int arc = THUMB_SIZE;
            int x = thumbBounds.x + THUMB_BORDER_SIZE;
            int y = thumbBounds.y + THUMB_BORDER_SIZE;

            int width = orientation == JScrollBar.VERTICAL
                    ? THUMB_SIZE : thumbBounds.width - (THUMB_BORDER_SIZE * 2);
            width = Math.max(width, THUMB_SIZE);

            int height = orientation == JScrollBar.VERTICAL
                    ? thumbBounds.height - (THUMB_BORDER_SIZE * 2) : THUMB_SIZE;
            height = Math.max(height, THUMB_SIZE);

            Graphics2D graphics2D = (Graphics2D) g.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(new Color(THUMB_COLOR.getRed(),
                    THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
            graphics2D.fillRoundRect(x, y, width, height, arc, arc);
            graphics2D.dispose();
        }
    }

    static public class CustomUI extends BasicComboBoxUI {

        public Color blue = new Color(0, 94, 159);

        public static ComboBoxUI createUI(JComponent cbxMoneda) {

            return new CustomUI();
        }

        @Override
        public JButton createArrowButton() {
            BasicArrowButton basicArrowButton = new BasicArrowButton(BasicArrowButton.SOUTH, //Direccion de la flecha
                    Color.WHITE, //Color de fondo
                    new Color(0, 94, 159),//sombra
                    new Color(0, 94, 159),//darkShadow
                    Color.WHITE //highlight
            );
            //se quita el efecto 3d del boton, sombra y darkShadow no se aplican 
            basicArrowButton.setBorder(BorderFactory.createLineBorder(blue, 2));
            basicArrowButton.setContentAreaFilled(false);
            return basicArrowButton;
        }

        //Se puede usar un JButton para usar un icono personalizado en lugar del arrow
        /* 
45  @Override 
46  protected JButton createArrowButton() { 
47  JButton button = new JButton(); 
48  //se quita el efecto 3d del boton, sombra y darkShadow no se aplican 
49  button.setText("");
50  button.setBorder(BorderFactory.createLineBorder(red,2));
51  button.setContentAreaFilled(false);
52  button.setIcon( new ImageIcon(getClass().getResource("/org/bolivia/res/estrella.png")) );
53  return button;
54  } 
55  */
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {

            g.setColor(blue);
            g.setFont(new Font("Tahoma", Font.BOLD, 14));
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        //Pinta los items
        @Override
        public ListCellRenderer createRenderer() {
            return new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {

                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    list.setSelectionBackground(blue);
                    list.setForeground(white);

                    if (isSelected) {
                        setBackground(blue);
                        setForeground(new Color(255, 255, 255));
                    } else {
                        setBackground(Color.WHITE);
                        setForeground(new Color(0, 94, 159));
                    }

                    return this;
                }
            };
        }
    }
}
