/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.IOException;

/**
 *
 * @author crstc
 */
public class ServicioTTS implements IServicioTTS {

    private String idioma;

    public ServicioTTS(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public void leerTexto(String texto) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                String comando = "powershell -Command \"Add-Type -AssemblyName System.Speech; "
                        + "$s = New-Object System.Speech.Synthesis.SpeechSynthesizer; "
                        + "$s.Speak('" + texto + "')\"";
                Runtime.getRuntime().exec(comando);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"say", texto});
            } else {
                Runtime.getRuntime().exec(new String[]{"espeak", "-v", idioma, texto});
            }
        }    catch (IOException e

    
        ){
    System.out.println("Error al reproducir el siguiente texto: " + e.getMessage());
    }
    }
    @Override
    public void detener(){
        System.out.println("TTS detenido");
    }
    public String getIdioma(){
        return idioma;
    }
    public void setIdioma(String idioma){
        this.idioma=idioma;
    }
}
