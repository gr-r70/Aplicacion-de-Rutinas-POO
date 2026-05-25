/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.Toolkit;

/**
 * Servicio encargado de gestionar recordatorios automáticos
 * para las rutinas activas de la agenda.
 * 
 * Verifica periódicamente si alguna rutina diaria debe
 * ejecutarse y, en caso afirmativo, muestra una notificación
 * visual y reproduce instrucciones mediante un servicio
 * de texto a voz.
 */
public class ServicioRecordatorio {
    private Timer timer;
    private ModeloAgenda agenda;
    private IServicioTTS tts;
    public static final int INTERVALO_MS=60000;
    
    /**
     * Construye un servicio de recordatorio asociado
     * a una agenda y un servicio de texto a voz.
     *
     * @param agenda Agenda que contiene las rutinas.
     * @param tts Servicio de texto a voz utilizado para
     * reproducir mensajes.
     */
    public ServicioRecordatorio(ModeloAgenda agenda, IServicioTTS tts){
        this.agenda=agenda;
        this.tts=tts;
    }
    
    /**
     * Inicia el servicio de recordatorios.
     * 
     * Se crea un temporizador que verifica periódicamente
     * las rutinas activas según el intervalo configurado.
     */
    public void iniciar(){
        timer=new Timer(INTERVALO_MS, e->verificarRutinas());
        timer.start();
        System.out.println("Servicio de recordatorio iniciado.");
    }
    
    /**
     * Detiene el servicio de recordatorios.
     * 
     * Si el temporizador está activo, se detiene
     * la verificación automática de rutinas.
     */
    public void detener(){
        if(timer !=null){
            timer.stop();
            System.out.println("Servicio detenido");
        }
    }
    
    /**
     * Verifica las rutinas de la agenda para determinar
     * si alguna debe ejecutarse en el momento actual.
     * 
     * Si una rutina diaria está activa y coincide con
     * el horario configurado, se reproduce un mensaje
     * de voz y se muestra un recordatorio visual.
     */
    private void verificarRutinas() {

        for (Rutina rutina : agenda.getRutinas()) {

            if (rutina instanceof RutinaDiaria rutinaDiaria
                    && rutinaDiaria.isActiva()
                    && rutinaDiaria.debeEjecutarseAhora()) {

                String mensaje = "Ya es hora de realizar tu rutina "
                        + rutinaDiaria.getNombre();

                tts.leerTexto(mensaje);

                mostrarRecordatorio(rutinaDiaria);
            }
        }
    }
    
    /**
     * Muestra un recordatorio visual y auditivo
     * para una rutina diaria.
     * 
     * Se genera un sonido del sistema, un cuadro
     * de diálogo informativo y se leen en voz alta
     * los pasos de la rutina si existe un servicio
     * TTS configurado.
     *
     * @param rutina Rutina diaria que será recordada.
     */
    private void mostrarRecordatorio(RutinaDiaria rutina) {

        Toolkit.getDefaultToolkit().beep();

        SwingUtilities.invokeLater(() ->{

            JOptionPane.showMessageDialog(
                    null,
                    "¡Es hora de tu rutina!\n\n"
                    + rutina.getNombre()
                    + "\n\nPresiona OK para comenzar.",
                    "Recordatorio",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        if (tts != null) {

           
           
            tts.leerTexto(
                    "Ya es hora de realizar tu rutina "
                    + rutina.getNombre()
            );

            
            for (Paso paso : rutina.getPasos()) {

                tts.leerTexto(
                        "Paso "
                        + paso.getNumero()
                        + ". "
                        + paso.getDescripcion()
                );

               
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
