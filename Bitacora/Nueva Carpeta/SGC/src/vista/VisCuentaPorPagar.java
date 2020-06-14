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
        jTable.getTableHeader().setDefaultRenderer(new Headercolor());
        cbxCuentaT.setUI(new CustomUI());
        cbxFondo.setUI(new CustomUI());
        cbxFormaP.setUI(new CustomUI());
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
        jTable.setRowHeight(35);
        jScrollPane1.setViewportView(jTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 890, 170));

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
        jPanel.add(cbxFormaP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 240, 30));

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
        jPanel.add(cbxCuentaT, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 93, 260, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("<html>\nCuenta <br> Transferida:\n</html>");
        jPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 94, -1, 30));

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
        jPanel.add(cbxFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 223, 260, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("<html>\nFondo <br> Retirado:\n</html>");
        jPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 224, -1, 30));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 240, 20));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 260, 20));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 260, 20));
        jPanel.add(jDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 240, 30));

        txtProveedor.setBackground(new java.awt.Color(0, 94, 159));
        txtProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtProveedor.setBorder(null);
        txtProveedor.setEnabled(false);
        jPanel.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 260, 20));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 240, 20));

        add(jPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 760, 280));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 800, 300));

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
        btnProcesar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        btnMostrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMostrar.setForeground(new java.awt.Color(0, 94, 159));
        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrar.setText("Mostrar Procesado");
        btnMostrar.setBorder(null);
        btnMostrar.setBorderPainted(false);
        btnMostrar.setContentAreaFilled(false);
        btnMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMostrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management (1).png"))); // NOI18N
        btnMostrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/management.png"))); // NOI18N
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, -1, -1));

        btnPagos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPagos.setForeground(new java.awt.Color(0, 94, 159));
        btnPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice.png"))); // NOI18N
        btnPagos.setText("Mostrar Pagos");
        btnPagos.setBorder(null);
        btnPagos.setBorderPainted(false);
        btnPagos.setContentAreaFilled(false);
        btnPagos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
