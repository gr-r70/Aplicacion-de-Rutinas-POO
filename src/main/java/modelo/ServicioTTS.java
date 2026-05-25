/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Servicio encargado de convertir texto a voz mediante
 * el motor de síntesis de voz del sistema operativo.
 * 
 * Implementa la interfaz {@link IServicioTTS} y utiliza
 * PowerShell junto con el sintetizador de voz de Windows
 * para reproducir mensajes en español.
 */
public class ServicioTTS implements IServicioTTS {
    
    /**
     * Voz utilizada por FreeTTS.
     * 
     * Se mantiene por compatibilidad, aunque la reproducción
     * de voz actual utiliza el sintetizador de Windows.
     */
    private Voice voice; // Se mantiene por compatibilidad, aunque usaremos el motor de sistema
    private String idioma;
    private Process procesoVoz;

    /**
     * Construye un nuevo servicio de texto a voz.
     * 
     * Inicializa el idioma de reproducción sin
     * configurar FreeTTS, ya que el sistema utiliza
     * el motor de voz de Windows.
     *
     * @param idioma Idioma de reproducción configurado.
     */
    public ServicioTTS(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Reproduce un texto mediante síntesis de voz.
     * 
     * Si el texto está vacío o es nulo, no se realiza
     * ninguna acción. La reproducción se ejecuta
     * en un hilo independiente para evitar bloquear
     * la interfaz gráfica.
     *
     * @param texto Texto que será leído en voz alta.
     */
    @Override
    public void leerTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return;
        }

        // Usamos un hilo para que la interfaz no se bloquee mientras habla
        new Thread(() -> {
            try {
                // Comando de PowerShell que usa la voz de Windows (que sí está en español)
                String comando = "Add-Type -AssemblyName System.Speech; " +
                                 "$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                                 "$speak.Speak('" + texto + "')";
                
                ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-Command", comando);
                procesoVoz = pb.start();
                procesoVoz.waitFor(); // Espera a que termine de hablar
            } catch (Exception e) {
                System.err.println("Error al reproducir voz en español: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Detiene inmediatamente la reproducción de voz.
     * 
     * Si existe un proceso activo de síntesis de voz,
     * este se finaliza automáticamente.
     */
    @Override
    public void detener() {
        if (procesoVoz != null && procesoVoz.isAlive()) {
            procesoVoz.destroy(); // Detiene el proceso de PowerShell inmediatamente
        }
    }
    
    /**
     * Obtiene el idioma configurado para la reproducción.
     *
     * @return El idioma actual del servicio.
     */
    public String getIdioma() {
        return idioma;
    }
    
    /**
     * Establece un nuevo idioma de reproducción.
     *
     * @param idioma Nuevo idioma del servicio.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}