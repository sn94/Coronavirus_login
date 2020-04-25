package com.example.coronavirus_login.BD;

public class Persona {


    //esta clase Persona representa a tu tabla, pero a nivel programatico

    //equivalente a los campos de la tabla de persona
    String cedula="", nombre,apellido="",  telefono="", credencial="",
            latitud="", longitud="", nick="",clave="", detalle_domicilio="";




    public Persona() {
    }

    //los metodos de tipo get_* sirven para obtener el dato
    //los metodos de tipo set_* sirven para asignar un valor


    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDetalle_domicilio() {
        return detalle_domicilio;
    }

    public void setDetalle_domicilio(String detalle_domicilio) {
        this.detalle_domicilio = detalle_domicilio;
    }
}
