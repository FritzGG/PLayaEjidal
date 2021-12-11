package com.example.playaejidal3;

public class Orden {
    int id;
    String fecha;
    int cantidad;
    String descripcion;
    String menuid_menu;
    int usuarioid_usuario;
    int mesaid_mesa;
    String area;
    String tipo;

    public Orden() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMenuid_menu() {
        return menuid_menu;
    }

    public void setMenuid_menu(String menuid_menu) {
        this.menuid_menu = menuid_menu;
    }

    public int getUsuarioid_usuario() {
        return usuarioid_usuario;
    }

    public void setUsuarioid_usuario(int usuarioid_usuario) {
        this.usuarioid_usuario = usuarioid_usuario;
    }

    public int getMesaid_mesa() {
        return mesaid_mesa;
    }

    public void setMesaid_mesa(int mesaid_mesa) {
        this.mesaid_mesa = mesaid_mesa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }





}
