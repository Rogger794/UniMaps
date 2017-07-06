package com.example.moviles.proyectomoviles.Objetos;

/**
 * Created by rogger on 05/07/17.
 */

public class Dependencia {
    int idDependencia;
    String nombreDependencia;
    int idUbicacion;
    int cantOficinas;
    int idSede;

    public Dependencia() {
    }

    public Dependencia(int idDependencia, String nombreDependencia, int idUbicacion, int cantOficinas, int idSede) {
        this.idDependencia = idDependencia;
        this.nombreDependencia = nombreDependencia;
        this.idUbicacion = idUbicacion;
        this.cantOficinas = cantOficinas;
        this.idSede = idSede;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public String getNombreDependencia() {
        return nombreDependencia;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public int getCantOficinas() {
        return cantOficinas;
    }

    public int getIdSede() {
        return idSede;
    }
}
