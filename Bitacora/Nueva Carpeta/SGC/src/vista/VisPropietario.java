/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author Jhen
 */
public class VisPropietario extends javax.swing.JPanel {

    /**
     * Creates new form VisPropietario
     */
    public VisPropietario() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtCedula = new javax.swing.JTextField();
        txtSnombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPapellido = new javax.swing.JTextField();
        txtSapellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        txtPnombre = new javax.swing.JTextField();
        cbxCedula = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        btnEliminar = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 94, 159), 5, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnSalir.setToolTipText("Volver al catálogo");
        btnSalir.setBorder(null);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow (1).png"))); // NOI18N
        btnSalir.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow (1).png"))); // NOI18N
        btnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow (1).png"))); // NOI18N
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Gestionar Propietario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 180, -1));

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCedula.setBackground(new java.awt.Color(0, 94, 159));
        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCedula.setForeground(new java.awt.Color(255, 255, 255));
        txtCedula.setToolTipText("Ingrese la cedula del propietario");
        txtCedula.setBorder(null);
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        jPanel2.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 280, 20));

        txtSnombre.setBackground(new java.awt.Color(0, 94, 159));
        txtSnombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSnombre.setForeground(new java.awt.Color(255, 255, 255));
        txtSnombre.setToolTipText("Ingrese el segundo nombre del propuetario");
        txtSnombre.setBorder(null);
        txtSnombre.setOpaque(false);
        txtSnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSnombreActionPerformed(evt);
            }
        });
        jPanel2.add(txtSnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 220, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Primer Nombre:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        txtPapellido.setBackground(new java.awt.Color(0, 94, 159));
        txtPapellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPapellido.setForeground(new java.awt.Color(255, 255, 255));
        txtPapellido.setToolTipText("Ingrese el primer apellido del propietario");
        txtPapellido.setBorder(null);
        txtPapellido.setOpaque(false);
        jPanel2.add(txtPapellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 220, 20));

        txtSapellido.setBackground(new java.awt.Color(0, 94, 159));
        txtSapellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSapellido.setForeground(new java.awt.Color(255, 255, 255));
        txtSapellido.setToolTipText("Ingrese el segundo apellido del propietario");
        txtSapellido.setBorder(null);
        txtSapellido.setOpaque(false);
        txtSapellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSapellidoActionPerformed(evt);
            }
        });
        jPanel2.add(txtSapellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 220, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Primer Apellido:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cédula:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Segundo Nombre:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, -1));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 280, 10));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 220, 10));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 220, 10));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, 220, 10));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 220, 10));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Segundo Apellido:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 130, -1));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 220, 10));

        txtTelefono.setBackground(new java.awt.Color(0, 94, 159));
        txtTelefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefono.setToolTipText("Ingrese el número de teléfono del propierario");
        txtTelefono.setBorder(null);
        txtTelefono.setOpaque(false);
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        jPanel2.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 220, 20));

        txtCorreo.setBackground(new java.awt.Color(0, 94, 159));
        txtCorreo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreo.setToolTipText("Ingrese el correo electrónico del propietario");
        txtCorreo.setBorder(null);
        txtCorreo.setOpaque(false);
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel2.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 220, 20));

        jSeparator16.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 220, 10));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("<html>\nNúmero de <br> Teléfono:\n</html>");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, -1, -1));

        txtPnombre.setBackground(new java.awt.Color(0, 94, 159));
        txtPnombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPnombre.setForeground(new java.awt.Color(255, 255, 255));
        txtPnombre.setToolTipText("Ingrese el primer nombre del propietario");
        txtPnombre.setBorder(null);
        txtPnombre.setOpaque(false);
        txtPnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPnombreActionPerformed(evt);
            }
        });
        jPanel2.add(txtPnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 220, 20));

        cbxCedula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "V", "E" }));
        jPanel2.add(cbxCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Correo Electrónico:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, -1, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 770, 270));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 94, 159));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salvar.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disco-flexible (2).png"))); // NOI18N
        btnGuardar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disco-flexible (2).png"))); // NOI18N
        btnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disco-flexible (2).png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 70, -1));
        //btnGuardar.addActionListener(ctrl);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato.png"))); // NOI18N
        btnModificar.setToolTipText("Modificar");
        btnModificar.setBorder(null);
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificar.setEnabled(false);
        btnModificar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        btnModificar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        btnModificar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        jPanel3.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 70, 70));
        //btnMinimizar.addActionListener(ctrl);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo.png"))); // NOI18N
        btnLimpiar.setToolTipText("Limpiar todo");
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        btnLimpiar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        btnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        jPanel3.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 70, 70));

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 0, -1, 70));

        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 10, 70));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (4).png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setEnabled(false);
        btnEliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (2).png"))); // NOI18N
        btnEliminar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (2).png"))); // NOI18N
        btnEliminar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (2).png"))); // NOI18N
        jPanel3.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 70, 70));
        //btnMinimizar.addActionListener(ctrl);

        jSeparator15.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 10, 70));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 430, 360, 80));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondobtn1chiqui.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 840, 510));
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtSnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSnombreActionPerformed

    private void txtSapellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSapellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSapellidoActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtPnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPnombreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnSalir;
    public javax.swing.JComboBox<String> cbxCedula;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public javax.swing.JTextField txtCedula;
    public javax.swing.JTextField txtCorreo;
    public javax.swing.JTextField txtPapellido;
    public javax.swing.JTextField txtPnombre;
    public javax.swing.JTextField txtSapellido;
    public javax.swing.JTextField txtSnombre;
    public javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
