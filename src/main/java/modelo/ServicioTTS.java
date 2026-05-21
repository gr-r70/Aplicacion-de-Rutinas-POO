/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ServicioTTS implements IServicioTTS {

   
    private static final String NOMBRE_VOZ = "kevin16";

    private Voice voice;
    private String idioma;

    public ServicioTTS(String idioma) {

        this.idioma = idioma;

        inicializarVoz();
    }

    private void inicializarVoz() {

        System.setProperty(
                "freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"
        );

        VoiceManager voiceManager =
                VoiceManager.getInstance();

        voice = voiceManager.getVoice(NOMBRE_VOZ);

        if (voice == null) {
            throw new IllegalStateException(
                    "No se encontró la voz: "
                    + NOMBRE_VOZ
            );
        }

        voice.allocate();
    }

    public void leerTexto(String texto) {

        if (texto == null || texto.isBlank()) {
            return;
        }

        synchronized (this) {
            voice.speak(texto);
        }
    }

   
    @Override
    public void detener() {

        if (voice != null
                && voice.getAudioPlayer() != null) {

            voice.getAudioPlayer().cancel();
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}