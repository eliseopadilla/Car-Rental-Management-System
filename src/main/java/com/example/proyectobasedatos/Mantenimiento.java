package com.example.proyectobasedatos;

public class Mantenimiento {
    private int id;
    private String fecha;
    private String descripcion;
    private float costo;
    private int kilometraje;
    private int vehiculo_id;

    public Mantenimiento(int id, String fecha, String descripcion, float costo, int kilometraje, int vehiculo_id) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.costo = costo;
        this.kilometraje = kilometraje;
        this.vehiculo_id = vehiculo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getVehiculo_id() {
        return vehiculo_id;
    }

    public void setVehiculo_id(int vehiculo_id) {
        this.vehiculo_id = vehiculo_id;
    }
}
