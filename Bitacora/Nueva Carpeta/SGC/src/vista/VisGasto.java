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

public class VisGasto extends javax.swing.JPanel {

    /**
     * Creates new form VisGasto
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
    public VisGasto() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI());
        jScrollPane4.getVerticalScrollBar().setUI(new MyScrollBarUI());
        jTable.getTableHeader().setDefaultRenderer(new VisMensaje.Headercolor());
        cbxMoneda.setUI(new VisCerrarMes.CustomUI());
        jAsamblea.setUI(new VisCerrarMes.CustomUI());
        jCalcular.setUI(new VisCerrarMes.CustomUI());
        jcombotipo.setUI(new VisCerrarMes.CustomUI());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrpAsamblea = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        btnEliminar = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnBuscarproveedor = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCalcular = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        txtProveedor = new javax.swing.JTextField();
        jcombotipo = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelTipo = new javax.swing.JPanel();
        labelmense = new javax.swing.JLabel();
        txtNmeses = new javax.swing.JTextField();
        barritahorizontal = new javax.swing.JSeparator();
        fue_elegido = new javax.swing.JLabel();
        si = new javax.swing.JRadioButton();
        barrita = new javax.swing.JSeparator();
        no = new javax.swing.JRadioButton();
        panelAsamblea = new javax.swing.JPanel();
        labelAsamblea = new javax.swing.JLabel();
        jAsamblea = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxMoneda = new javax.swing.JComboBox<>();
        jSeparator5 = new javax.swing.JSeparator();
        txtNombre = new javax.swing.JTextField();
        txtAño = new rojeru_san.componentes.RSYearDate();
        txtMes = new newscomponents.RSDateMonth();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 94, 159), 5, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setText("Registro de Gastos");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Concepto", "Descripción", "Categoría", "Seleccione"
            }
        ));
        jTable.setRowHeight(35);
        jScrollPane1.setViewportView(jTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 760, 110));

        jPanel3.setBackground(new java.awt.Color(0, 94, 159));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salvar.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disco-flexible (2).png"))); // NOI18N
        btnGuardar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disco-flexible (2).png"))); // NOI18N
        btnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disco-flexible (2).png"))); // NOI18N
        jPanel3.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, -1));
        //btnGuardar.addActionListener(ctrl);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato.png"))); // NOI18N
        btnModificar.setToolTipText("Modificar");
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificar.setEnabled(false);
        btnModificar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        btnModificar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        btnModificar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contrato (1).png"))); // NOI18N
        jPanel3.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 70, 70));
        //btnMinimizar.addActionListener(ctrl);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo.png"))); // NOI18N
        btnLimpiar.setToolTipText("Limpiar todo");
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        btnLimpiar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        btnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barriendo (1).png"))); // NOI18N
        jPanel3.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 70, 70));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 10, 70));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 10, 70));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (4).png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminar.setEnabled(false);
        btnEliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (2).png"))); // NOI18N
        btnEliminar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (2).png"))); // NOI18N
        btnEliminar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar (2).png"))); // NOI18N
        jPanel3.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 70, 70));
        //btnMinimizar.addActionListener(ctrl);

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 10, 70));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 560, 360, 80));

        jPanel2.setBackground(new java.awt.Color(0, 94, 159));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBuscarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda.png"))); // NOI18N
        btnBuscarproveedor.setToolTipText("Buscar proveedor");
        btnBuscarproveedor.setBorderPainted(false);
        btnBuscarproveedor.setContentAreaFilled(false);
        btnBuscarproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarproveedor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnBuscarproveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        btnBuscarproveedor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busqueda (1).png"))); // NOI18N
        jPanel2.add(btnBuscarproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 40, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nombre:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, -1, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Calcular Por:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 90, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("<html>\nComienzo de <br> Cobro (Mes):\n</html>");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, -1, 30));

        jCalcular.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCalcular.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALICUOTA", "TOTAL DE INMUEBLES" }));
        jCalcular.setToolTipText("Calcular la cuota especial por...");
        jPanel2.add(jCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 280, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Observaciones:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 110, -1));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 280, 10));

        jScrollPane4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txaObservaciones.setBackground(new java.awt.Color(0, 94, 159));
        txaObservaciones.setColumns(20);
        txaObservaciones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txaObservaciones.setForeground(new java.awt.Color(255, 255, 255));
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        txaObservaciones.setToolTipText("Ingrese observaciones (opcional)");
        txaObservaciones.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txaObservaciones);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, 360, 120));

        txtProveedor.setEditable(false);
        txtProveedor.setBackground(new java.awt.Color(0, 94, 159));
        txtProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtProveedor.setBorder(null);
        jPanel2.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 240, 20));

        jcombotipo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcombotipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EXTRAORDINARIO", "ORDINARIO" }));
        jcombotipo.setToolTipText("Seleccione el tipo de gasto");
        jPanel2.add(jcombotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 280, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("<html>\nTipo de <br> Gasto:\n</html>");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 280, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Proveedor:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, 20));

        panelTipo.setBackground(new java.awt.Color(0, 94, 159));
        panelTipo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelmense.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelmense.setForeground(new java.awt.Color(255, 255, 255));
        labelmense.setText("<html> Número de Meses <br> que Aplica: </html>");
        panelTipo.add(labelmense, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, -1));

        txtNmeses.setBackground(new java.awt.Color(0, 94, 159));
        txtNmeses.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNmeses.setForeground(new java.awt.Color(255, 255, 255));
        txtNmeses.setToolTipText("Ingrese el número de meses que aplica");
        txtNmeses.setBorder(null);
        panelTipo.add(txtNmeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 200, 20));

        barritahorizontal.setForeground(new java.awt.Color(255, 255, 255));
        panelTipo.add(barritahorizontal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 200, 10));

        fue_elegido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        fue_elegido.setForeground(new java.awt.Color(255, 255, 255));
        fue_elegido.setText("¿Fué elegido en una asamblea?");
        panelTipo.add(fue_elegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        bgrpAsamblea.add(si);
        si.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        si.setForeground(new java.awt.Color(255, 255, 255));
        si.setSelected(true);
        si.setText("Si");
        si.setContentAreaFilled(false);
        si.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        si.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot.png"))); // NOI18N
        si.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot (1).png"))); // NOI18N
        si.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button (1).png"))); // NOI18N
        si.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button.png"))); // NOI18N
        panelTipo.add(si, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        barrita.setForeground(new java.awt.Color(255, 255, 255));
        barrita.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barrita.setOpaque(true);
        panelTipo.add(barrita, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, 20));

        bgrpAsamblea.add(no);
        no.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        no.setForeground(new java.awt.Color(255, 255, 255));
        no.setText("No");
        no.setToolTipText("");
        no.setContentAreaFilled(false);
        no.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        no.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot.png"))); // NOI18N
        no.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dot (1).png"))); // NOI18N
        no.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button (1).png"))); // NOI18N
        no.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/radio-on-button.png"))); // NOI18N
        panelTipo.add(no, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, -1, -1));

        panelAsamblea.setBackground(new java.awt.Color(0, 94, 159));
        panelAsamblea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelAsamblea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelAsamblea.setForeground(new java.awt.Color(255, 255, 255));
        labelAsamblea.setText("Asamblea:");
        panelAsamblea.add(labelAsamblea, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 80, 30));

        jAsamblea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jAsamblea.setToolTipText("Seleccione una asamblea");
        panelAsamblea.add(jAsamblea, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 240, 30));

        panelTipo.add(panelAsamblea, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 360, 40));

        jPanel2.add(panelTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 360, 170));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Seleccione el concepto gasto");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Moneda:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 60, 30));

        cbxMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "BOLÍVAR", "DÓLAR" }));
        cbxMoneda.setToolTipText("Calcular la cuota especial por...");
        jPanel2.add(cbxMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 280, 30));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 140, 10));

        txtNombre.setBackground(new java.awt.Color(0, 94, 159));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setBorder(null);
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 280, 20));

        txtAño.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        txtAño.setColorBackground(new java.awt.Color(0, 94, 159));
        txtAño.setColorButtonHover(new java.awt.Color(0, 151, 255));
        txtAño.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(txtAño, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 120, 30));

        txtMes.setColorArrow(new java.awt.Color(0, 94, 159));
        txtMes.setColorBorde(new java.awt.Color(255, 255, 255));
        txtMes.setColorBoton(new java.awt.Color(255, 255, 255));
        txtMes.setColorDisabledIndex(new java.awt.Color(255, 255, 255));
        txtMes.setColorDisabledIndexText(new java.awt.Color(0, 94, 159));
        txtMes.setColorFondo(new java.awt.Color(0, 94, 159));
        txtMes.setColorListaItemsTXT(new java.awt.Color(0, 94, 159));
        txtMes.setColorSeleccion(new java.awt.Color(0, 94, 159));
        txtMes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(txtMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 140, -1));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 280, 10));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondobtn1chiqui.png"))); // NOI18N
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, -1, 100));

        txtid.setEditable(false);
        jPanel1.add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 430, -1, -1));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnSalir.setToolTipText("Volver al catálogo");
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow (1).png"))); // NOI18N
        btnSalir.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow (1).png"))); // NOI18N
        btnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow (1).png"))); // NOI18N
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoformu700-350 (2).png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, -1, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondofomu340-130.png"))); // NOI18N
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 840, 640));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JSeparator barrita;
    public javax.swing.JSeparator barritahorizontal;
    private javax.swing.ButtonGroup bgrpAsamblea;
    public javax.swing.JButton btnBuscarproveedor;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnSalir;
    public javax.swing.JComboBox<String> cbxMoneda;
    public javax.swing.JLabel fue_elegido;
    public javax.swing.JComboBox<String> jAsamblea;
    public javax.swing.JComboBox<String> jCalcular;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public javax.swing.JTable jTable;
    public javax.swing.JComboBox<String> jcombotipo;
    public javax.swing.JLabel labelAsamblea;
    public javax.swing.JLabel labelmense;
    public javax.swing.JRadioButton no;
    public javax.swing.JPanel panelAsamblea;
    public javax.swing.JPanel panelTipo;
    public javax.swing.JRadioButton si;
    public javax.swing.JTextArea txaObservaciones;
    public rojeru_san.componentes.RSYearDate txtAño;
    public newscomponents.RSDateMonth txtMes;
    public javax.swing.JTextField txtNmeses;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtProveedor;
    public javax.swing.JTextField txtid;
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
