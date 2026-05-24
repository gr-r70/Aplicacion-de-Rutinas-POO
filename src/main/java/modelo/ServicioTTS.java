/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ServicioTTS implements IServicioTTS {

    private Voice voice; // Se mantiene por compatibilidad, aunque usaremos el motor de sistema
    private String idioma;
    private Process procesoVoz;

    public ServicioTTS(String idioma) {
        this.idioma = idioma;
        // Ya no inicializamos FreeTTS (Kevin) porque no habla español
    }

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

    @Override
    public void detener() {
        if (procesoVoz != null && procesoVoz.isAlive()) {
            procesoVoz.destroy(); // Detiene el proceso de PowerShell inmediatamente
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}