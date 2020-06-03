package vista;


public class VisCuentaPorPagar extends javax.swing.JPanel {


    public VisCuentaPorPagar() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxFormaP = new javax.swing.JComboBox<>();
        txtDescripcion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        cbxCuentaT = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        cbxFondo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jDate = new com.toedter.calendar.JDateChooser();
        txtProveedor = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        btnProcesar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnMostrar = new javax.swing.JButton();
        btnPagos = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Seleccione", "Fecha", "Concepto", "Monto", "Saldo Restante", "Estado", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable.setRowHeight(20);
        jScrollPane1.setViewportView(jTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 750, 170));

        jPanel.setBackground(new java.awt.Color(0, 94, 159));
        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Proveedor:");
        jPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("<html>\nForma <br> de Pago:\n</html>");
        jPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 40));

        cbxFormaP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxFormaP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Transferencia", "Deposito", "Cheque", "Efectivo" }));
        jPanel.add(cbxFormaP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 240, -1));

        txtDescripcion.setBackground(new java.awt.Color(0, 94, 159));
        txtDescripcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setBorder(null);
        jPanel.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 240, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Descripción:");
        jPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 90, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("<html>\nFecha <br> de Pago:\n</html>");
        jPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("<html>\nNúmero de <br> Referencia:\n</html>");
        jPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, 40));

        txtReferencia.setBackground(new java.awt.Color(0, 94, 159));
        txtReferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtReferencia.setForeground(new java.awt.Color(255, 255, 255));
        txtReferencia.setBorder(null);
        jPanel.add(txtReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 260, 20));

        cbxCuentaT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel.add(cbxCuentaT, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 260, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("<html>\nCuenta <br> Transferida:\n</html>");
        jPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Monto:");
        jPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, -1, 20));

        txtMonto.setBackground(new java.awt.Color(0, 94, 159));
        txtMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMonto.setForeground(new java.awt.Color(255, 255, 255));
        txtMonto.setBorder(null);
        jPanel.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, 260, 20));

        cbxFondo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel.add(cbxFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 260, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("<html>\nFondo <br> Retirado:\n</html>");
        jPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, -1, -1));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 240, 20));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 260, 20));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 260, 20));
        jPanel.add(jDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 240, -1));

        txtProveedor.setBackground(new java.awt.Color(0, 94, 159));
        txtProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtProveedor.setBorder(null);
        txtProveedor.setEnabled(false);
        jPanel.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 260, 20));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 240, 20));

        add(jPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 750, 280));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 800, 300));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 510, 300));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 490, -1, 300));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 94, 159));
        jLabel11.setText("<html>\nSeleccione en la tabla las <br> facturas a procesar pago.\n</html>");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 210, -1));

        jSeparator5.setBackground(new java.awt.Color(0, 94, 159));
        jSeparator5.setForeground(new java.awt.Color(0, 94, 159));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 400, 20, 60));

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
        add(btnProcesar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 390, 260, 70));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setText("Cuentas por Pagar");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        btnMostrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMostrar.setForeground(new java.awt.Color(0, 94, 159));
        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrar.setText("Mostrar Procesado");
        btnMostrar.setBorder(null);
        btnMostrar.setBorderPainted(false);
        btnMostrar.setContentAreaFilled(false);
        btnMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMostrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management (1).png"))); // NOI18N
        btnMostrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, -1));

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
        add(btnPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 371, 160, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed


    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosActionPerformed

    }//GEN-LAST:event_btnPagosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnMostrar;
    public javax.swing.JButton btnPagos;
    public javax.swing.JButton btnProcesar;
    public javax.swing.JComboBox<String> cbxCuentaT;
    public javax.swing.JComboBox<String> cbxFondo;
    public javax.swing.JComboBox<String> cbxFormaP;
    public com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    public javax.swing.JTable jTable;
    public javax.swing.JTextField txtDescripcion;
    public javax.swing.JTextField txtMonto;
    public javax.swing.JTextField txtProveedor;
    public javax.swing.JTextField txtReferencia;
    // End of variables declaration//GEN-END:variables
}
