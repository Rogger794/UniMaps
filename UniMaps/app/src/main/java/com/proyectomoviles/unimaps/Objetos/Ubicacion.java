package com.proyectomoviles.unimaps.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Ubicacion {
    int idUbicacion;
    String nombreUbicacion;
    double latitud;
    double longitud;

    public Ubicacion(){

    }

    public Ubicacion(int idUbicacion, String nombreUbicacion, double latitud, double longitud) {
        this.idUbicacion = idUbicacion;
        this.nombreUbicacion = nombreUbicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
