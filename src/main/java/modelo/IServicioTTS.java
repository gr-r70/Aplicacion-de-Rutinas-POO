/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Interfaz que define el comportamiento básico
 * de un servicio de síntesis de voz (Text To Speech).
 * 
 * Permite reproducir texto mediante voz y detener
 * la reproducción cuando sea necesario.
 * 
 * @author David
 */
public interface IServicioTTS {

    /**
     * Reproduce un texto mediante síntesis de voz.
     *
     * @param texto contenido que será leído por voz.
     */
    void leerTexto(String texto);

    /**
     * Detiene la reproducción de voz en curso.
     */
    void detener();
}