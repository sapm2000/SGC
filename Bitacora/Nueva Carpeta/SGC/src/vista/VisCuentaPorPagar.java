package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;
import sun.swing.table.DefaultTableCellHeaderRenderer;


public class VisCuentaPorPagar extends javax.swing.JPanel {

    //Aquí cambias la trasparencia de la barra mientras el cursor está encima. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
    //Aquí cambias la trasparencia de la barra. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA = 100;
    private static final int THUMB_BORDER_SIZE = 5;
    //Aquí cambias el grosor de la barra
    private static final int THUMB_SIZE = 8;
    //Aquí cambias el color de la barra
    private static final Color THUMB_COLOR = Color.BLUE;

    public VisCuentaPorPagar() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI());
        tablaGastos.getTableHeader().setDefaultRenderer(new Headercolor());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGastos = new javax.swing.JTable();
        jPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtGasto = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        cbxFormaPago = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        cbxMoneda = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbxFondo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        panelReferencia = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        cbxCuenta = new javax.swing.JComboBox<>();
        panelPariedad = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtPariedad = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        btnProcesar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnMostrarProcesados = new javax.swing.JButton();
        btnPagos = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        tablaGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaGastos.setRowHeight(35);
        jScrollPane1.setViewportView(tablaGastos);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 890, 170));

        jPanel.setBackground(new java.awt.Color(0, 94, 159));
        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Gasto:");
        jPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 20));

        txtGasto.setBackground(new java.awt.Color(0, 94, 159));
        txtGasto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtGasto.setForeground(new java.awt.Color(255, 255, 255));
        txtGasto.setBorder(null);
        txtGasto.setEnabled(false);
        txtGasto.setNextFocusableComponent(txtDescripcion);
        jPanel.add(txtGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 240, 20));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 240, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Descripción:");
        jPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 90, 30));

        txtDescripcion.setBackground(new java.awt.Color(0, 94, 159));
        txtDescripcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setBorder(null);
        txtDescripcion.setNextFocusableComponent(cbxFormaPago);
        jPanel.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 240, 20));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 240, 10));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("<html>\nForma <br> de Pago:\n</html>");
        jPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, 40));

        cbxFormaPago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxFormaPago.setNextFocusableComponent(fecha);
        jPanel.add(cbxFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 240, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("<html>\nFecha <br> de Pago:\n</html>");
        jPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, 40));

        fecha.setNextFocusableComponent(cbxMoneda);
        jPanel.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 240, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("<HTML>Moneda<BR>a pagar:</HTML>");
        jPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 60, 40));

        cbxMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Bolívar", "Dólar" }));
        cbxMoneda.setNextFocusableComponent(cbxFondo);
        jPanel.add(cbxMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 240, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("<html>\nFondo <br> Retirado:\n</html>");
        jPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        cbxFondo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxFondo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una moneda" }));
        cbxFondo.setEnabled(false);
        cbxFondo.setNextFocusableComponent(txtMonto);
        jPanel.add(cbxFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 260, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Monto:");
        jPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, 20));

        txtMonto.setBackground(new java.awt.Color(0, 94, 159));
        txtMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMonto.setForeground(new java.awt.Color(255, 255, 255));
        txtMonto.setText("Seleccione un Fondo");
        txtMonto.setBorder(null);
        txtMonto.setEnabled(false);
        txtMonto.setNextFocusableComponent(txtReferencia);
        jPanel.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 260, 20));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 260, 10));

        panelReferencia.setBackground(new java.awt.Color(0, 94, 159));
        panelReferencia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("<html>\nNúmero de <br> Referencia:\n</html>");
        panelReferencia.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        txtReferencia.setBackground(new java.awt.Color(0, 94, 159));
        txtReferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtReferencia.setForeground(new java.awt.Color(255, 255, 255));
        txtReferencia.setBorder(null);
        txtReferencia.setNextFocusableComponent(cbxCuenta);
        panelReferencia.add(txtReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 260, 20));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        panelReferencia.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 260, 10));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("<html> Cuenta a<br>Transferir: </html>");
        panelReferencia.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        cbxCuenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxCuenta.setNextFocusableComponent(txtPariedad);
        panelReferencia.add(cbxCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 260, -1));

        jPanel.add(panelReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 380, 120));

        panelPariedad.setBackground(new java.awt.Color(0, 94, 159));
        panelPariedad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("<HTML>Tasa de<BR>cambio:</HTML>");
        panelPariedad.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        txtPariedad.setBackground(new java.awt.Color(0, 94, 159));
        txtPariedad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPariedad.setForeground(new java.awt.Color(255, 255, 255));
        txtPariedad.setBorder(null);
        txtPariedad.setNextFocusableComponent(btnProcesar);
        panelPariedad.add(txtPariedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 260, 20));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        panelPariedad.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 260, 10));

        jPanel.add(panelPariedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 350, 60));

        add(jPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 750, 280));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 810, 310));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 510, 300));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, -1, 300));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 94, 159));
        jLabel11.setText("<html>\nSeleccione en la tabla las <br> facturas a procesar pago.\n</html>");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 210, -1));

        jSeparator5.setBackground(new java.awt.Color(0, 94, 159));
        jSeparator5.setForeground(new java.awt.Color(0, 94, 159));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 390, 20, 60));

        btnProcesar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnProcesar.setForeground(new java.awt.Color(0, 94, 159));
        btnProcesar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/1imageonline-co-merged-image.png"))); // NOI18N
        btnProcesar.setText("<html>\n<br><br><br>Procesar el Pago y Descargar el Recibo\n</html>");
        btnProcesar.setBorder(null);
        btnProcesar.setContentAreaFilled(false);
        btnProcesar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProcesar.setHideActionText(true);
        btnProcesar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProcesar.setIconTextGap(10);
        btnProcesar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2imageonline-co-merged-image.png"))); // NOI18N
        btnProcesar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2imageonline-co-merged-image.png"))); // NOI18N
        btnProcesar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2imageonline-co-merged-image.png"))); // NOI18N
        add(btnProcesar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 380, 260, 70));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setText("Cuentas por Pagar");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        btnMostrarProcesados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMostrarProcesados.setForeground(new java.awt.Color(0, 94, 159));
        btnMostrarProcesados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrarProcesados.setText("Mostrar Procesado");
        btnMostrarProcesados.setBorder(null);
        btnMostrarProcesados.setBorderPainted(false);
        btnMostrarProcesados.setContentAreaFilled(false);
        btnMostrarProcesados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMostrarProcesados.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management (1).png"))); // NOI18N
        btnMostrarProcesados.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrarProcesados.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrarProcesados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarProcesadosActionPerformed(evt);
            }
        });
        add(btnMostrarProcesados, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, -1, -1));

        btnPagos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPagos.setForeground(new java.awt.Color(0, 94, 159));
        btnPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice.png"))); // NOI18N
        btnPagos.setText("Mostrar Pagos");
        btnPagos.setBorder(null);
        btnPagos.setBorderPainted(false);
        btnPagos.setContentAreaFilled(false);
        btnPagos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPagos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice (1).png"))); // NOI18N
        btnPagos.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice.png"))); // NOI18N
        btnPagos.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice.png"))); // NOI18N
        btnPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosActionPerformed(evt);
            }
        });
        add(btnPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 160, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarProcesadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarProcesadosActionPerformed


    }//GEN-LAST:event_btnMostrarProcesadosActionPerformed

    private void btnPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosActionPerformed

    }//GEN-LAST:event_btnPagosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnMostrarProcesados;
    public javax.swing.JButton btnPagos;
    public javax.swing.JButton btnProcesar;
    public javax.swing.JComboBox<String> cbxCuenta;
    public javax.swing.JComboBox<String> cbxFondo;
    public javax.swing.JComboBox<String> cbxFormaPago;
    public javax.swing.JComboBox<String> cbxMoneda;
    public com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    public javax.swing.JPanel panelPariedad;
    public javax.swing.JPanel panelReferencia;
    public javax.swing.JTable tablaGastos;
    public javax.swing.JTextField txtDescripcion;
    public javax.swing.JTextField txtGasto;
    public javax.swing.JTextField txtMonto;
    public javax.swing.JTextField txtPariedad;
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
}
