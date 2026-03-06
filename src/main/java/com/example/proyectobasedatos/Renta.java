package com.example.proyectobasedatos;

import java.util.Date;

public class Renta {
    private int id;
    private String fechaentrega;
    private String fechadevolucion;
    private String metodopago;
    private int kminicial;
    private int kmfinal;
    private int vehiculo;
    private int cliente;

    public Renta(){}

    public Renta(int id, String fechaentrega, String fechadevolucion, String metodopago,int vehiculo, int cliente, int kminicial, int kmfinal) {
        this.id = id;
        this.fechaentrega = fechaentrega;
        this.fechadevolucion = fechadevolucion;
        this.metodopago = metodopago;
        this.kminicial = kminicial;
        this.kmfinal = kmfinal;
        this.vehiculo = vehiculo;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(String fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public String getFechadevolucion() {
        return fechadevolucion;
    }

    public void setFechadevolucion(String fechadevolucion) {
        this.fechadevolucion = fechadevolucion;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public int getKminicial() {
        return kminicial;
    }

    public void setKminicial(int kminicial) {
        this.kminicial = kminicial;
    }

    public int getKmfinal() {
        return kmfinal;
    }

    public void setKmfinal(int kmfinal) {
        this.kmfinal = kmfinal;
    }

    public int getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(int vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }
}
