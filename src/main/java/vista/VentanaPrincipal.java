/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ControladorRutinas;
import modelo.IServicioTTS;
import modelo.ModeloAgenda;
import modelo.Rutina;
import modelo.RutinaDAO;
import modelo.ServicioRecordatorio;
import modelo.ServicioTTS;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private ControladorRutinas controlador;

    private JPanel panelSuperior;
    private JPanel panelLateral;
    private JPanel panelCentral;

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnEscuchar;
    private JButton btnLeer;

    private JTextArea areaRutinas;

    private ModeloAgenda modelo;
    private RutinaDAO rutinaDAO;

    private IServicioTTS servicioTTS;
    private ServicioRecordatorio servicioRecordatorio;

    public VentanaPrincipal() {

        modelo = new ModeloAgenda();
        controlador = new ControladorRutinas(modelo);

        rutinaDAO = new RutinaDAO();

        servicioTTS = new ServicioTTS("es");
        servicioRecordatorio =
                new ServicioRecordatorio(modelo, servicioTTS);

        cargarRutinasDesdeBD();

        servicioRecordatorio.iniciar();

        iniciarVista();
    }

    private void cargarRutinasDesdeBD() {

        List<Rutina> rutinas = rutinaDAO.obtenerRutinas();

        for (Rutina rutina : rutinas) {
            modelo.agregar(rutina);
        }
    }

    private void iniciarVista() {

        setTitle("Aplicación de Rutinas Inteligentes");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        crearPanelSuperior();
        crearPanelLateral();
        crearPanelCentral();

        add(panelSuperior, BorderLayout.NORTH);
        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        actualizarLista();

        setVisible(true);
    }

    private void crearPanelSuperior() {

        panelSuperior = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        btnEscuchar = new JButton("🎤 Voz");
        btnLeer = new JButton("🔊 Leer");

        panelSuperior.add(btnEscuchar);
        panelSuperior.add(btnLeer);

        btnEscuchar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Próximamente integración Whisper API"
            );
        });

        btnLeer.addActionListener(e -> {

            String textoSeleccionado =
                    areaRutinas.getSelectedText();

            if (textoSeleccionado == null
                    || textoSeleccionado.isBlank()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecciona una rutina o texto para leer."
                );

                return;
            }

            servicioTTS.leerTexto(textoSeleccionado);
        });
    }

    private void crearPanelLateral() {

        panelLateral = new JPanel();

        panelLateral.setPreferredSize(
                new Dimension(220, 0)
        );

        panelLateral.setLayout(
                new GridLayout(10, 1, 10, 10)
        );

        btnAgregar = new JButton("Agregar Rutina");
        btnEliminar = new JButton("Eliminar Rutina");

        panelLateral.add(btnAgregar);
        panelLateral.add(btnEliminar);

        btnAgregar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Formulario de nueva rutina próximamente"
            );
        });

        btnEliminar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Eliminar rutina próximamente"
            );
        });
    }

    private void crearPanelCentral() {

        panelCentral = new JPanel(
                new BorderLayout()
        );

        areaRutinas = new JTextArea();

        areaRutinas.setEditable(false);

        areaRutinas.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JScrollPane scroll =
                new JScrollPane(areaRutinas);

        panelCentral.add(scroll, BorderLayout.CENTER);
    }

    public void actualizarLista() {

        areaRutinas.setText(
                controlador.listarRutinas()
        );
    }

    public ControladorRutinas getControlador() {

        return controlador;
    }
}