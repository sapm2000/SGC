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
import vista.buscarProveedor.MyScrollBarUI;


public class VisRegistroVisita extends javax.swing.JPanel {

    //Aquí cambias la trasparencia de la barra mientras el cursor está encima. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
    //Aquí cambias la trasparencia de la barra. Mientras mál alto el valor, menos transparente
    private static final int SCROLL_BAR_ALPHA = 100;
    private static final int THUMB_BORDER_SIZE = 5;
    //Aquí cambias el grosor de la barra
    private static final int THUMB_SIZE = 8;
    //Aquí cambias el color de la barra
    private static final Color THUMB_COLOR = Color.BLUE;
    
    public VisRegistroVisita() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI());
        tabla.getTableHeader().setDefaultRenderer(new VisComunicados.Headercolor());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
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
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 94, 159), 5, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tabla.setRowHeight(35);
        jScrollPane1.setViewportView(tabla);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 830, 160));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Registro de Visitas");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 70, -1));

        txtApellido.setBackground(new java.awt.Color(0, 94, 159));
        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.setBorder(null);
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 260, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cédula:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apellido:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        txtNombre.setBackground(new java.awt.Color(0, 94, 159));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setBorder(null);
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 260, 20));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 260, 10));

        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setToolTipText("Agregar visita");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnAgregar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnAgregar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setOpaque(true);
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 740, -1));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 260, 10));

        btnCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCedula.setForeground(new java.awt.Color(255, 255, 255));
        btnCedula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda.png"))); // NOI18N
        btnCedula.setToolTipText("Buscar");
        btnCedula.setBorder(null);
        btnCedula.setBorderPainted(false);
        btnCedula.setContentAreaFilled(false);
        btnCedula.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCedula.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnCedula.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnCedula.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        jPanel2.add(btnCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 40, 40));

        btnVisitante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVisitante.setForeground(new java.awt.Color(255, 255, 255));
        btnVisitante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional.png"))); // NOI18N
        btnVisitante.setText("Nuevo Visitante");
        btnVisitante.setToolTipText("Registrar a un nuevo visitante");
        btnVisitante.setBorder(null);
        btnVisitante.setBorderPainted(false);
        btnVisitante.setContentAreaFilled(false);
        btnVisitante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisitante.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnVisitante.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnVisitante.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simbolo-grueso-adicional (1).png"))); // NOI18N
        btnVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisitanteActionPerformed(evt);
            }
        });
        jPanel2.add(btnVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, 40));

        txtCedula.setBackground(new java.awt.Color(0, 94, 159));
        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCedula.setForeground(new java.awt.Color(255, 255, 255));
        txtCedula.setBorder(null);
        jPanel2.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 210, 20));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 210, 10));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Unidad:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        cbxUnidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(cbxUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 260, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Modelo:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, 20));

        txtModelo.setBackground(new java.awt.Color(0, 94, 159));
        txtModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtModelo.setForeground(new java.awt.Color(255, 255, 255));
        txtModelo.setBorder(null);
        jPanel2.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 240, 20));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 240, 10));

        txtMatricula.setBackground(new java.awt.Color(0, 94, 159));
        txtMatricula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMatricula.setForeground(new java.awt.Color(255, 255, 255));
        txtMatricula.setBorder(null);
        jPanel2.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 240, 20));

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 240, 10));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Matrícula:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, 20));

        txtColor.setBackground(new java.awt.Color(0, 94, 159));
        txtColor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtColor.setForeground(new java.awt.Color(255, 255, 255));
        txtColor.setBorder(null);
        jPanel2.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 240, 20));

        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 240, 10));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Color:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, -1, 20));

        cbxCedula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxCedula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V", "E" }));
        jPanel2.add(cbxCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 40, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, 280));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 530, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu500-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, 510, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 910, 610));
    }// </editor-fold>//GEN-END:initComponents

    private void btnVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisitanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVisitanteActionPerformed


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
    private javax.swing.JPanel jPanel1;
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
