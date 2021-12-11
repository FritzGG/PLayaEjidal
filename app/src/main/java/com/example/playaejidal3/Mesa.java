package com.example.playaejidal3;

public class Mesa {


    private int idMesa;
    private int idsito;
    private String area;
    private String mesero;
    private String idMesero;
    private String detalles;
    private String ocupantes;
    private int imagen;
    private String idActualMesero;


    public Mesa() {
    }

    public Mesa(int idsito, int idMesa, String mesero,String idMesero ,String detalles, int imagen, String area,String idActualMesero,String ocupantes) {
        this.idMesa = idMesa;
        this.idsito=idsito;
        this.mesero = mesero;
        this.idMesero=idMesero;
        this.detalles = detalles;
        this.imagen=imagen;
        this.area=area;
        this.idActualMesero=idActualMesero;
        this.ocupantes=ocupantes;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public int getIdsito() {
        return idsito;
    }

    public String getMesero() {
        return mesero;
    }

    public String getIdMesero() {
        return idMesero;
    }

    public String getIdActualMesero() {
        return idActualMesero;
    }

    public String getArea() {
        return area;
    }

    public String getDetalles() {
        return detalles;
    }

    public int getImagen(){
        return imagen;
    }

    public String getOcupantes() {
        return ocupantes;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public void setIdsito(int idsito) {
        this.idsito = idsito;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public void setIdMesero(String idMesero) {
        this.idMesero = idMesero;
    }

    public void setIdActualMesero(String idActualMesero) {
        this.idActualMesero = idActualMesero;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public void setImagen(int imagen){
        this.imagen=imagen;
    }

    public void setOcupantes(String ocupantes) {
        this.ocupantes = ocupantes;
    }
}
