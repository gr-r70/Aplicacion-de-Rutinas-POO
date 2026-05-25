/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.ControladorRutinas;
import modelo.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipalGUI extends javax.swing.JFrame {

    private ModeloAgenda modelo;
    private ControladorRutinas controlador;
    private DefaultTableModel modeloTabla;
    private ServicioTTS tts;
    private RutinaDAO rutinaDAO;

    public VentanaPrincipalGUI() {
        initComponents();
        modelo = new ModeloAgenda();
        controlador = new ControladorRutinas(modelo);
        modeloTabla = (DefaultTableModel) tablaRutinas.getModel();
        rutinaDAO = new RutinaDAO();

        try {
            tts = new ServicioTTS("es-ES");
        } catch (Exception e) {
            System.out.println("Error al iniciar TTS: " + e.getMessage());
        }

        ServicioRecordatorio recordatorio = new ServicioRecordatorio(modelo, tts);
        recordatorio.iniciar();

        controlador.cargarDesdeBD();
        configurarEventos();
        actualizarTabla();
        actualizarCamposTipo();
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> guardarRutina());
        btnEliminar.addActionListener(e -> eliminarRutina());
        btnLeer.addActionListener(e -> leerRutina());
        btnAgregarPaso.addActionListener(e -> agregarPaso());
        btnDetener.addActionListener(e -> {
            if (tts != null) {
                tts.detener();
            }
        });

        btnSiguiente.addActionListener(e -> {
            int fila = tablaRutinas.getSelectedRow();
            if (fila != -1) {
                Rutina r = modelo.getRutinas().get(fila);
                controlador.avanzarPaso(r);
                actualizarLabelEstado(r);
            }
        });

        btnAnterior.addActionListener(e -> {
            int fila = tablaRutinas.getSelectedRow();
            if (fila != -1) {
                Rutina r = modelo.getRutinas().get(fila);
                controlador.retrocederPaso(r);
                actualizarLabelEstado(r);
            }
        });
        comboTipo.addActionListener(e -> actualizarCamposVisibles());
        actualizarCamposVisibles();
    }

    private void actualizarCamposTipo() {

        boolean esPersonalizada
                = comboTipo.getSelectedItem()
                        .equals("Personalizada");

        lblCategoria.setVisible(esPersonalizada);
        txtCategoria.setVisible(esPersonalizada);

        lblNivel.setVisible(esPersonalizada);
        txtNivel.setVisible(esPersonalizada);

        lblHora.setVisible(!esPersonalizada);
        txtHora.setVisible(!esPersonalizada);

        lblDia.setVisible(!esPersonalizada);
        comboDia.setVisible(!esPersonalizada);
    }

    private void actualizarCamposVisibles() {

        boolean esPersonalizada = comboTipo.getSelectedItem().equals("Personalizada");

        lblCategoria.setVisible(esPersonalizada);
        txtCategoria.setVisible(esPersonalizada);

        lblNivel.setVisible(esPersonalizada);
        txtNivel.setVisible(esPersonalizada);
    }

    private void actualizarLabelEstado(Rutina r) {
        if (r != null && !r.getPasos().isEmpty()) {
            int actual = r.getIndicePasoActual() + 1;
            int total = r.getPasos().size();
            String desc = r.getPasos().get(r.getIndicePasoActual()).getDescripcion();

            lblEstadoPasos.setText("Paso " + actual + " de " + total + ": " + desc);

            // Ejecutar voz en un hilo separado para no congelar la ventana
            if (tts != null) {
                new Thread(() -> tts.leerTexto(desc)).start();
            }
        } else {
            lblEstadoPasos.setText("Sin pasos configurados.");
        }
    }

    private void agregarPaso() {

        int fila = tablaRutinas.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una rutina primero");
            return;
        }

        String descripcion = JOptionPane.showInputDialog(this,
                "Descripción del paso:");

        if (descripcion == null || descripcion.isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "La descripción no puede estar vacía.");
            return;
        }

        Rutina r = modelo.getRutinas().get(fila);

        if (r.getPasos().size() >= Paso.MAX_PASOS) {

            JOptionPane.showMessageDialog(this,
                    "La rutina ya tiene el máximo de "
                    + Paso.MAX_PASOS + " pasos.");

            return;
        }

        Paso nuevoPaso = new Paso(
                r.getPasos().size() + 1,
                descripcion
        );

        r.getPasos().add(nuevoPaso);

        boolean guardado = rutinaDAO.guardarPaso(r.getId(), nuevoPaso);

        if (guardado) {

            JOptionPane.showMessageDialog(this,
                    "Paso guardado correctamente.");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Error al guardar el paso en la BD.");
        }
    }

    private void guardarRutina() {
        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        String nivelStr = txtNivel.getText();
        String tipo = comboTipo.getSelectedItem().toString();

        if (nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return;
        }

        try {
            int nivel = nivelStr.isBlank() ? 1 : Integer.parseInt(nivelStr);
            Rutina nueva;

            if (tipo.equals("Diaria")) {
                String horaTexto = txtHora.getText();
                String diaTexto = comboDia.getSelectedItem().toString();
                LocalTime hora = LocalTime.parse(horaTexto);
                DayOfWeek dia = switch (diaTexto) {
                    case "Lunes" ->
                        DayOfWeek.MONDAY;
                    case "Martes" ->
                        DayOfWeek.TUESDAY;
                    case "Miércoles" ->
                        DayOfWeek.WEDNESDAY;
                    case "Jueves" ->
                        DayOfWeek.THURSDAY;
                    case "Viernes" ->
                        DayOfWeek.FRIDAY;
                    case "Sábado" ->
                        DayOfWeek.SATURDAY;
                    default ->
                        DayOfWeek.SUNDAY;
                };

                nueva = new RutinaDiaria(hora, new DayOfWeek[]{dia}, nombre);

            } else {
                nueva = new RutinaPersonalizada(nombre, categoria, nivel);
            }

            if (controlador.agregarRutina(nueva)) {
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "¡Guardado con éxito!");
                txtNombre.setText("");
                txtCategoria.setText("");
                txtNivel.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El nivel debe ser un número.");
        }
    }

    private void eliminarRutina() {
        int fila = tablaRutinas.getSelectedRow();
        if (fila != -1 && controlador.eliminarRutina(fila)) {
            actualizarTabla();
        }
    }

    private void leerRutina() {
        int fila = tablaRutinas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una rutina primero.");
            return;
        }

        Rutina r = modelo.getRutinas().get(fila);

        if (r.getPasos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Esta rutina no tiene pasos. Agrega pasos primero.");
            return;
        }

        r.setIndicePasoActual(0);
        actualizarLabelEstado(r);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Rutina r : modelo.getRutinas()) {
            String cat = "-", niv = "-", hora = "-", dia = "-";
            if (r instanceof RutinaPersonalizada rp) {
                cat = rp.getCategoria();
                niv = String.valueOf(rp.getNivel());
            } else if (r instanceof RutinaDiaria rd) {

                hora = rd.getHoraInicio()
                        .toString()
                        .substring(0, 5);

                if (rd.getDias().length > 0) {

                    DayOfWeek d = rd.getDias()[0];

                    dia = switch (d) {

                        case MONDAY ->
                            "Lunes";
                        case TUESDAY ->
                            "Martes";
                        case WEDNESDAY ->
                            "Miércoles";
                        case THURSDAY ->
                            "Jueves";
                        case FRIDAY ->
                            "Viernes";
                        case SATURDAY ->
                            "Sábado";
                        default ->
                            "Domingo";
                    };
                }
            }

            modeloTabla.addRow(new Object[]{
                r.getId(),
                r.getNombre(),
                r.getTipo(),
                cat,
                niv,
                hora,
                dia,
                r.isActiva() ? "Activa" : "Inactiva"
            });
        }
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
        panelSuperior = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnLeer = new javax.swing.JButton();
        btnDetener = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        lblEstadoPasos = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox<>();
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        lblNivel = new javax.swing.JLabel();
        txtNivel = new javax.swing.JTextField();
        btnAgregarPaso = new javax.swing.JButton();
        txtHora = new javax.swing.JTextField();
        lblHora = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        lblDia = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaRutinas = new javax.swing.JTable();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelSuperior.setBackground(new java.awt.Color(204, 204, 204));
        panelSuperior.setName("panelSuperior"); // NOI18N
        panelSuperior.setPreferredSize(new java.awt.Dimension(220, 100));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblTitulo.setText("Rutinas Inteligentes");
        lblTitulo.setToolTipText("");

        btnLeer.setText("🔊 Leer");
        btnLeer.setName("btnLeer"); // NOI18N

        btnDetener.setText("Detener");

        btnSiguiente.setText("Siguiente");

        btnAnterior.setText("Anterior");

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblEstadoPasos, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDetener, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAnterior, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSiguiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLeer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLeer)
                            .addComponent(btnDetener))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSiguiente)
                            .addComponent(btnAnterior)))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEstadoPasos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(panelSuperior, java.awt.BorderLayout.PAGE_START);
        panelSuperior.getAccessibleContext().setAccessibleParent(panelSuperior);

        panelMenu.setBackground(new java.awt.Color(204, 255, 255));
        panelMenu.setName("panelMenu"); // NOI18N
        panelMenu.setPreferredSize(new java.awt.Dimension(250, 350));

        lblMenu.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblMenu.setText("MENU");
        lblMenu.setName("lblMenu"); // NOI18N

        btnEliminar.setText("Eliminar Rutina");
        btnEliminar.setName("btnEliminar"); // NOI18N

        btnGuardar.setText("Guardar Rutina");
        btnGuardar.setName("btnGuardar"); // NOI18N

        lblNombre.setText("Nombre:");
        lblNombre.setName("lblNombre"); // NOI18N

        txtNombre.setName("txtNombre"); // NOI18N

        lblTipo.setText("Tipo:");
        lblTipo.setName("lblTipo"); // NOI18N

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diaria", "Personalizada" }));
        comboTipo.setName("comboTipo"); // NOI18N

        lblCategoria.setText("Categoria:");
        lblCategoria.setName("lblCategoria"); // NOI18N

        txtCategoria.setName("txtCategoria"); // NOI18N

        lblNivel.setText("Nivel:");
        lblNivel.setName("lblNivel"); // NOI18N

        txtNivel.setName("txtNivel"); // NOI18N

        btnAgregarPaso.setText("Agregar Paso");
        btnAgregarPaso.setName("btnEliminar"); // NOI18N

        txtHora.setText("08:00");
        txtHora.setName("txtNivel"); // NOI18N

        lblHora.setText("Hora:");
        lblHora.setName("lblNivel"); // NOI18N

        comboDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" }));
        comboDia.setPreferredSize(new java.awt.Dimension(64, 22));

        lblDia.setText("Dia:");
        lblDia.setName("lblNivel"); // NOI18N

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelMenuLayout.createSequentialGroup()
                                .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnGuardar)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregarPaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHora, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(txtNombre)
                            .addComponent(comboTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCategoria)
                            .addComponent(txtNivel, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(comboDia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarPaso)
                .addGap(26, 26, 26)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategoria)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivel)
                    .addComponent(txtNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHora))
                .addGap(28, 28, 28)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDia))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        getContentPane().add(panelMenu, java.awt.BorderLayout.LINE_START);

        panelCentral.setName("panelCentral"); // NOI18N
        panelCentral.setPreferredSize(new java.awt.Dimension(478, 101));

        tablaRutinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Tipo", "Categoria", "Nivel", "Hora", "Dia", "Estado"
            }
        ));
        tablaRutinas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaRutinas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaRutinas.setName("tablaRutinas"); // NOI18N
        jScrollPane2.setViewportView(tablaRutinas);

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        getContentPane().add(panelCentral, java.awt.BorderLayout.CENTER);
        panelCentral.getAccessibleContext().setAccessibleName("");
        panelCentral.getAccessibleContext().setAccessibleParent(panelCentral);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VentanaPrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipalGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPaso;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnDetener;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblEstadoPasos;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTable tablaRutinas;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNivel;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
