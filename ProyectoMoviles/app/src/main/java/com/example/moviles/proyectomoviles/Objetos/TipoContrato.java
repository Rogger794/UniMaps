package com.example.moviles.proyectomoviles.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class TipoContrato {
    int idContrato;
    String nombreTipoContrato;
    int numHoras;

    public TipoContrato(){

    }

    public TipoContrato(int idContrato, String nombreTipoContrato, int numHoras) {
        this.idContrato = idContrato;
        this.nombreTipoContrato = nombreTipoContrato;
        this.numHoras = numHoras;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public String getNombreTipoContrato() {
        return nombreTipoContrato;
    }

    public int getNumHoras() {
        return numHoras;
    }
}
