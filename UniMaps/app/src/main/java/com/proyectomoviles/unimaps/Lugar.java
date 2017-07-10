package com.proyectomoviles.unimaps;


public class Lugar {
    private int imagen;
    private String nombre;
    private String descripcion;
    public Lugar(int imagen , String nombre, String descripcion){
        this.imagen=imagen;
        this.nombre=nombre;
        this.descripcion=descripcion;
    }
    public int getImagen(){
        return imagen;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }

}
