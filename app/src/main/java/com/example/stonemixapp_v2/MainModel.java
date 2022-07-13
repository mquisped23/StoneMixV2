package com.example.stonemixapp_v2;

public class MainModel {
    String cantidadMaterial, descripcionMaterial, nombreMaterial, urlMaterial;

    MainModel(){

    }

    public MainModel(String cantidadMaterial, String descripcionMaterial, String nombreMaterial, String urlMaterial) {
        this.cantidadMaterial = cantidadMaterial;
        this.descripcionMaterial = descripcionMaterial;
        this.nombreMaterial = nombreMaterial;
        this.urlMaterial = urlMaterial;
    }

    public String getCantidadMaterial() {
        return cantidadMaterial;
    }

    public void setCantidadMaterial(String cantidadMaterial) {
        this.cantidadMaterial = cantidadMaterial;
    }

    public String getDescripcionMaterial() {
        return descripcionMaterial;
    }

    public void setDescripcionMaterial(String descripcionMaterial) {
        this.descripcionMaterial = descripcionMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getUrlMaterial() {
        return urlMaterial;
    }

    public void setUrlMaterial(String urlMaterial) {
        this.urlMaterial = urlMaterial;
    }
}
