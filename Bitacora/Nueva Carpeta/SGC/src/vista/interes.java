/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.JOptionPane;

/**
 *
 * @author rma
 */
public class interes extends javax.swing.JFrame {

    /**
     * Creates new form Interes
     */
    public interes() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox6 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnMinimizar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();

        jCheckBox6.setText("jCheckBox6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 94, 159), 5, true));
        jPanel1.setForeground(new java.awt.Color(0, 94, 159));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("<html>\nNombre de <br> Interes:\n</html>");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("<html>\nFactor de <br> Interes:\n</html>");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, -1));

        jTextField2.setBackground(new java.awt.Color(0, 94, 159));
        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setBorder(null);
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 200, 30));

        jTextField1.setBackground(new java.awt.Color(0, 94, 159));
        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(null);
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 200, 30));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 200, 10));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 200, 10));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("<html>\nVigencia <br> Hasta (mm):\n</html>");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 120, -1));

        jTextField4.setBackground(new java.awt.Color(0, 94, 159));
        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(null);
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 180, 30));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 180, 10));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 310, 190));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 340, 140));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Intereses");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        btnMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos (1).png"))); // NOI18N
        btnMinimizar.setBorder(null);
        btnMinimizar.setBorderPainted(false);
        btnMinimizar.setContentAreaFilled(false);
        btnMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMinimizar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos (2).png"))); // NOI18N
        btnMinimizar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos (2).png"))); // NOI18N
        btnMinimizar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menos (2).png"))); // NOI18N
        btnMinimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 40, 30));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancelar (1).png"))); // NOI18N
        btnSalir.setBorder(null);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancelar (2).png"))); // NOI18N
        btnSalir.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancelar (2).png"))); // NOI18N
        btnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancelar (2).png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, -1, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 94, 159));
        jLabel7.setText("Seleccione los condominios a aplicar el interes");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 94, 159));
        jCheckBox1.setText("condominio1");
        jCheckBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (2).png"))); // NOI18N
        jCheckBox1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jCheckBox1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (3).png"))); // NOI18N
        jCheckBox1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (3).png"))); // NOI18N
        jCheckBox1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 94, 159));
        jCheckBox2.setText("condominio2");
        jCheckBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (2).png"))); // NOI18N
        jCheckBox2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jCheckBox2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (3).png"))); // NOI18N
        jCheckBox2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (3).png"))); // NOI18N
        jCheckBox2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jPanel1.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, -1, -1));

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(0, 94, 159));
        jCheckBox3.setText("condominio3");
        jCheckBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (2).png"))); // NOI18N
        jCheckBox3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jCheckBox3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (3).png"))); // NOI18N
        jCheckBox3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (3).png"))); // NOI18N
        jCheckBox3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jPanel1.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 330, -1, -1));

        jCheckBox4.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(0, 94, 159));
        jCheckBox4.setText("condominio4");
        jCheckBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (2).png"))); // NOI18N
        jCheckBox4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jCheckBox4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (3).png"))); // NOI18N
        jCheckBox4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (3).png"))); // NOI18N
        jCheckBox4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jPanel1.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, -1, -1));

        jCheckBox5.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(0, 94, 159));
        jCheckBox5.setText("condominio5");
        jCheckBox5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (2).png"))); // NOI18N
        jCheckBox5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jCheckBox5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion-en-blanco (3).png"))); // NOI18N
        jCheckBox5.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (3).png"))); // NOI18N
        jCheckBox5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casilla-de-verificacion (2).png"))); // NOI18N
        jPanel1.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 340, 140));

        jPanel3.setBackground(new java.awt.Color(0, 94, 159));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salvar.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnModificar.setBorder(null);
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        btnModificar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        btnModificar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        jPanel3.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 70, 70));
        //btnMinimizar.addActionListener(ctrl);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo.png"))); // NOI18N
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        btnLimpiar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        btnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        jPanel3.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 70, 70));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 10, 70));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 10, 70));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 360, 70));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondobtn1chiqui.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, -1, 110));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMinimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizarActionPerformed
        this.setState(vista.InicioUsuario.ICONIFIED);
    }//GEN-LAST:event_btnMinimizarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        try {

            int botonDialogo = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "DESEA CERRAR LA VENTANA?", "SALIR", botonDialogo);
            if (result == 0) {

                System.exit(0);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(interes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnMinimizar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
