package com.example.proyectobasedatos;

public class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private String placa;
    private String estado;
    private float precio;
    private int kmservicio;

    public Vehiculo(){}

    public Vehiculo(int id, String marca, String modelo, String ano, String placa, String estado, float precio, int kmservicio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.estado = estado;
        this.precio = precio;
        this.kmservicio = kmservicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getKmservicio() {
        return kmservicio;
    }

    public void setKmservicio(int kmservicio) {
        this.kmservicio = kmservicio;
    }
}
