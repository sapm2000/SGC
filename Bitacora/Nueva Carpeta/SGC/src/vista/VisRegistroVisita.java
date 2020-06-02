package vista;


public class VisRegistroVisita extends javax.swing.JPanel {

    public VisRegistroVisita() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        btnAgregar = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        btnCedula = new javax.swing.JButton();
        btnVisitante = new javax.swing.JButton();
        txtCedula = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        cbxUnidad = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        txtMatricula = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        cbxCedula = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cédula", "Nombre", "Apellido", "Fecha Entrada", "Hora Entrada", "Unidad"
            }
        ));
        tabla.setRowHeight(20);
        jScrollPane1.setViewportView(tabla);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 440, 710, 170));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 510, 300));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 510, 300));

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 70, -1));

        txtApellido.setBackground(new java.awt.Color(0, 94, 159));
        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.setBorder(null);
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 240, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cédula:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 60, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apellido:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        txtNombre.setBackground(new java.awt.Color(0, 94, 159));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setBorder(null);
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 240, 20));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 240, 10));

        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnAgregar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnAgregar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setOpaque(true);
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 740, -1));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 240, 10));

        btnCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCedula.setForeground(new java.awt.Color(255, 255, 255));
        btnCedula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda.png"))); // NOI18N
        btnCedula.setText("Buscar");
        btnCedula.setBorder(null);
        btnCedula.setBorderPainted(false);
        btnCedula.setContentAreaFilled(false);
        btnCedula.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCedula.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnCedula.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnCedula.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        jPanel2.add(btnCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 90, 30));

        btnVisitante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVisitante.setForeground(new java.awt.Color(255, 255, 255));
        btnVisitante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnVisitante.setText("Nuevo Visitante");
        btnVisitante.setBorder(null);
        btnVisitante.setBorderPainted(false);
        btnVisitante.setContentAreaFilled(false);
        btnVisitante.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVisitante.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnVisitante.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnVisitante.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        jPanel2.add(btnVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, 30));

        txtCedula.setBackground(new java.awt.Color(0, 94, 159));
        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCedula.setForeground(new java.awt.Color(255, 255, 255));
        txtCedula.setBorder(null);
        jPanel2.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 200, 20));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 200, 10));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Unidad:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        cbxUnidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(cbxUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 250, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Modelo:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, -1, 20));

        txtModelo.setBackground(new java.awt.Color(0, 94, 159));
        txtModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtModelo.setForeground(new java.awt.Color(255, 255, 255));
        txtModelo.setBorder(null);
        jPanel2.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 240, 20));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 240, 10));

        txtMatricula.setBackground(new java.awt.Color(0, 94, 159));
        txtMatricula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMatricula.setForeground(new java.awt.Color(255, 255, 255));
        txtMatricula.setBorder(null);
        jPanel2.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 240, 20));

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 240, 10));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Matrícula:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, 20));

        txtColor.setBackground(new java.awt.Color(0, 94, 159));
        txtColor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtColor.setForeground(new java.awt.Color(255, 255, 255));
        txtColor.setBorder(null);
        jPanel2.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 240, 20));

        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 240, 10));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Color:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, 20));

        cbxCedula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V", "E" }));
        jPanel2.add(cbxCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 740, 280));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 800, 300));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Registro de Visitas");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAgregar;
    public javax.swing.JButton btnCedula;
    public javax.swing.JButton btnVisitante;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public javax.swing.JTable tabla;
    public javax.swing.JTextField txtApellido;
    public javax.swing.JTextField txtCedula;
    public javax.swing.JTextField txtColor;
    public javax.swing.JTextField txtMatricula;
    public javax.swing.JTextField txtModelo;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
