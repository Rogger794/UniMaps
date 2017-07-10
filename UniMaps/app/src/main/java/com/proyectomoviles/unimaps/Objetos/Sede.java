package com.proyectomoviles.unimaps.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Sede {
    int idSede;
    String nombreSede;
    int cantDependencias;
    String idUbicacion;

    public Sede() {
    }

    public Sede(int idSede, String nombreSede, int cantDependencias, String idUbicacion) {
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.cantDependencias = cantDependencias;
        this.idUbicacion = idUbicacion;
    }

    public int getIdSede() {
        return idSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public int getCantDependencias() {
        return cantDependencias;
    }

    public String getIdUbicacion() {
        return idUbicacion;
    }
}