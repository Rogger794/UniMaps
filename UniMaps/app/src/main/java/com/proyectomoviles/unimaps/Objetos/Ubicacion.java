package com.proyectomoviles.unimaps.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Ubicacion {
    double latitud;
    double longitud;
    boolean conectado;

    public Ubicacion(){

    }

    public Ubicacion(double latitud, double longitud, boolean conectado) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.conectado = conectado;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public boolean getConectado() {
        return conectado;
    }
}
