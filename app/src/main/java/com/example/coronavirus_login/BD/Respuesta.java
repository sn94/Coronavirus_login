package com.example.coronavirus_login.BD;

public class Respuesta {


    //aca definimos el formato de la respuesta que se te devolvera, tras haber hecho llamadas
    //al service



    //de una respuesta a una peticion web, es importante conocer el mensaje y algun
    //codigo d estado

    String mensaje="", codigo="";


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
