package com.example.moviles.proyectomoviles.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Oficina {
    int idOficina;
    int idSede;
    int idDependencia;
    int cantEmpleados;
    String nombreOficina;

    public Oficina(){
    }
    public Oficina(int idOficina, int idSede, int idDependencia, int cantEmpleados, String nombreOficina) {
        this.idOficina = idOficina;
        this.idSede = idSede;
        this.idDependencia = idDependencia;
        this.cantEmpleados = cantEmpleados;
        this.nombreOficina = nombreOficina;
    }

    public int getIdOficina() {
        return idOficina;
    }

    public int getIdSede() {
        return idSede;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public int getCantEmpleados() {
        return cantEmpleados;
    }

    public String getNombreOficina() {
        return nombreOficina;
    }
}
