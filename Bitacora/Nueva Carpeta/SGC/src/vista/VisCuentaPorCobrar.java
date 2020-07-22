/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Jhen
 */
public class VisCuentaPorCobrar extends javax.swing.JPanel {

    /**
     * Creates new form VisCuentaPorCobrar
     */
    //Aquí cambias la trasparencia de la barra mientras el cursor está encima. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
    //Aquí cambias la trasparencia de la barra. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA = 100;
    private static final int THUMB_BORDER_SIZE = 5;
    //Aquí cambias el grosor de la barra
    private static final int THUMB_SIZE = 8;
    //Aquí cambias el color de la barra
    private static final Color THUMB_COLOR = Color.BLUE;
    public VisCuentaPorCobrar() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI());
        jScrollPane2.getVerticalScrollBar().setUI(new MyScrollBarUI());
        jScrollPane3.getVerticalScrollBar().setUI(new MyScrollBarUI());
        jTable1.getTableHeader().setDefaultRenderer(new Headercolor());
        jTable2.getTableHeader().setDefaultRenderer(new Headercolor());
        cbxMoneda.setUI(new VisCerrarMes.CustomUI());
        jComboCuenta.setUI(new VisCerrarMes.CustomUI());
        jComboFondo.setUI(new VisCerrarMes.CustomUI());
        jComboForma.setUI(new VisCerrarMes.CustomUI());
        jComboUnidad.setUI(new VisCerrarMes.CustomUI());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboUnidad = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboCuenta = new javax.swing.JComboBox<>();
        jComboForma = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboFondo = new javax.swing.JComboBox<>();
        txtMonto = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtParidad = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cbxMoneda = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jDateChooser1 = new rojeru_san.componentes.RSDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        btnGuardar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setToolTipText("Seleccione las facturas a procesar");
        jTable1.setRowHeight(35);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 640, 230));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 94, 159));
        jLabel8.setText("<html>\nSeleccione en la tabla las <br> facturas a procesar pago.\n</html>");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 240, 50));

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("<html>\nNº de <br> Referencia:\n</html>");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 40));

        txtReferencia.setBackground(new java.awt.Color(0, 94, 159));
        txtReferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtReferencia.setForeground(new java.awt.Color(255, 255, 255));
        txtReferencia.setToolTipText("Ingrese el número de referencia");
        txtReferencia.setBorder(null);
        jPanel2.add(txtReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 230, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("<html> Número de <br> la Unidad: </html>");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("<html> Fecha <br> del Pago: </html>");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 70, 40));

        jComboUnidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboUnidad.setToolTipText("Seleccione la unidad");
        jComboUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboUnidadActionPerformed(evt);
            }
        });
        jPanel2.add(jComboUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 3, 230, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("<html> Cuenta <br> Depositada: </html>");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 80, -1));

        jComboCuenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboCuenta.setToolTipText("Seleccione la cuenta depositada\nOtros\n");
        jPanel2.add(jComboCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 73, 240, 30));

        jComboForma.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboForma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Transferencia", "Deposito", "Cheque", "Efectivo" }));
        jComboForma.setToolTipText("Seleccione la forma de pago");
        jPanel2.add(jComboForma, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 3, 240, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("<html>\nForma <br> de Pago:\n</html>");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Monto:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 50, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("<html>\nFondo a <br> Depositar:\n</html>");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 210, 70, -1));

        jComboFondo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboFondo.setToolTipText("Seleccione el fondo a depositar");
        jComboFondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboFondoActionPerformed(evt);
            }
        });
        jPanel2.add(jComboFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 210, 280, 30));

        txtMonto.setBackground(new java.awt.Color(0, 94, 159));
        txtMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMonto.setForeground(new java.awt.Color(255, 255, 255));
        txtMonto.setToolTipText("Ingrese el monto");
        txtMonto.setBorder(null);
        jPanel2.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 240, 20));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 130, 270, 20));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 230, 20));

        jScrollPane3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtDescripcion.setBackground(new java.awt.Color(0, 94, 159));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setToolTipText("Ingrese una descripción");
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtDescripcion);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 230, 120));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Descripción:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 90, 20));

        txtParidad.setBackground(new java.awt.Color(0, 94, 159));
        txtParidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtParidad.setForeground(new java.awt.Color(255, 255, 255));
        txtParidad.setBorder(null);
        jPanel2.add(txtParidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 110, 270, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Paridad:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, -1, 20));

        cbxMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxMoneda.setForeground(new java.awt.Color(255, 255, 255));
        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BOLÍVAR", "DÓLAR" }));
        cbxMoneda.setToolTipText("Calcular la cuota especial por...");
        cbxMoneda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        cbxMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMonedaActionPerformed(evt);
            }
        });
        jPanel2.add(cbxMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 270, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("<html>\nMoneda <br>a Pagar:\n</html>");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, -1, 40));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 240, 20));
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 1230, 280));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Por Pagar");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 420, 70, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Pagadas");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 420, 70, 20));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 800, 300));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("Cuentas por Cobrar");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.setRowHeight(35);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 450, 630, 230));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 510, 300));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 410, -1, 300));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 440, 10, 250));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 94, 159));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/1imageonline-co-merged-image.png"))); // NOI18N
        btnGuardar.setText("<html>\n<br><br><br>Procesar el Pago y Descargar el Recibo\n</html>");
        btnGuardar.setToolTipText("Procesar el pago y descargar el recibo");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setHideActionText(true);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIconTextGap(10);
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2imageonline-co-merged-image.png"))); // NOI18N
        btnGuardar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2imageonline-co-merged-image.png"))); // NOI18N
        btnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2imageonline-co-merged-image.png"))); // NOI18N
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 340, 260, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, -1, 300));

        jSeparator6.setBackground(new java.awt.Color(0, 94, 159));
        jSeparator6.setForeground(new java.awt.Color(0, 94, 159));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 20, 50));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 800, 300));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboUnidadActionPerformed

    private void cbxMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxMonedaActionPerformed

    private void jComboFondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboFondoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboFondoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGuardar;
    public javax.swing.JComboBox<String> cbxMoneda;
    public javax.swing.JComboBox<String> jComboCuenta;
    public javax.swing.JComboBox<String> jComboFondo;
    public javax.swing.JComboBox<String> jComboForma;
    public javax.swing.JComboBox<String> jComboUnidad;
    private rojeru_san.componentes.RSDateChooser jDateChooser1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable2;
    public javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtMonto;
    public javax.swing.JTextField txtParidad;
    public javax.swing.JTextField txtReferencia;
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
    
    static public class CustomUI extends BasicComboBoxUI{
        
    
    
    public Color blue = new Color(0,94,159);
    
    public static ComboBoxUI createUI(JComponent cbxMoneda) {
        
         return new CustomUI();
     }
 
    @Override 
    public JButton createArrowButton() {        
        BasicArrowButton basicArrowButton = new BasicArrowButton(BasicArrowButton.SOUTH, //Direccion de la flecha
                Color.WHITE, //Color de fondo
                new Color(0,94,159),//sombra
                new Color(0,94,159),//darkShadow
                Color.WHITE //highlight
                );         
        //se quita el efecto 3d del boton, sombra y darkShadow no se aplican 
        basicArrowButton.setBorder(BorderFactory.createLineBorder(blue,2));
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
     
    
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus)
    {
        
        g.setColor( blue );
        g.setFont(new Font("Tahoma", Font.BOLD, 14));
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    
     //Pinta los items
    @Override
    public ListCellRenderer createRenderer()
    {
        return new DefaultListCellRenderer() {      
             
        @Override
        public Component getListCellRendererComponent(JList list,Object value,int index,
           boolean isSelected,boolean cellHasFocus) {
       
        super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        list.setSelectionBackground(blue);
        list.setForeground(white);
        
        if (isSelected)
        {
             setBackground( blue );
             setForeground(new Color(255,255,255));
        }
        else
        {
             setBackground( Color.WHITE );            
             setForeground( new Color(0,94,159));
        }
            
            return this;
            }
        };
        }
    }
}
