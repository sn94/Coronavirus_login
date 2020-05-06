package com.example.coronavirus_login.BD;

public class Diagnostico {


    String  id_persona, edad, tos_garganta, dificultad_resp, asmatico, cardiaco, casa, diabetes, observacion, rinon;
    int temperatura;

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public String getTos_garganta() {
        return tos_garganta;
    }

    public void setTos_garganta(String tos_garganta) {
        this.tos_garganta = tos_garganta;
    }

    public String getDificultad_resp() {
        return dificultad_resp;
    }

    public void setDificultad_resp(String dificultad_resp) {
        this.dificultad_resp = dificultad_resp;
    }

    public String getAsmatico() {
        return asmatico;
    }

    public void setAsmatico(String asmatico) {
        this.asmatico = asmatico;
    }

    public String getCardiaco() {
        return cardiaco;
    }

    public void setCardiaco(String cardiaco) {
        this.cardiaco = cardiaco;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRinon() {
        return rinon;
    }

    public void setRinon(String rinon) {
        this.rinon = rinon;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
