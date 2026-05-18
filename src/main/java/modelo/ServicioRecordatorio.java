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
 *
 * @author crstc
 */
public class ServicioRecordatorio {
    private Timer timer;
    private ModeloAgenda agenda;
    private IServicioTTS tts;
    public static final int INTERVALO_MS=60000;
    public ServicioRecordatorio(ModeloAgenda agenda, IServicioTTS tts){
        this.agenda=agenda;
        this.tts=tts;
    }
    public void iniciar(){
        timer=new Timer(INTERVALO_MS, e->verificarRutinas());
        timer.start();
        System.out.println("Servicio de recordatorio iniciado.");
    }
    public void detener(){
        if(timer !=null){
            timer.stop();
            System.out.println("Servicio detenido");
        }
    }
    private void verificarRutinas(){
        ArrayList<Rutina> rutinas = agenda.getRutinas();
        for(Rutina r: rutinas){
            if(r.isActiva() && r instanceof RutinaDiaria ){
                RutinaDiaria rd= (RutinaDiaria) r;
                if (rd.debeEjecutarseAhora()){
                    mostrarRecordatorio(rd);
                }
            }
        }
    }
    private void mostrarRecordatorio(RutinaDiaria rutina){
        Toolkit.getDefaultToolkit().beep();
        SwingUtilities.invokeLater(()->{
            JOptionPane.showMessageDialog(null,
                "¡Es hora de tu rutina!\n\n" + rutina.getNombre()
                + "\n\nPresiona OK para comenzar.",
                "Recordatorio",
                JOptionPane.INFORMATION_MESSAGE);
        }
        );
        if (tts != null){
            tts.leerTexto("Es hora de tu rutina: " +rutina.getNombre());
        }
    }
}
