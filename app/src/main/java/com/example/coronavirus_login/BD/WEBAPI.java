package com.example.coronavirus_login.BD;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WEBAPI {


    //todas las llamadas que se haran al webservice


    @POST("covid_webservice/persona.php")
    Call<Respuesta> signup(@Body Persona datos);

    @POST("covid_webservice/signin.php")
    Call<Respuesta> signin(@Body Usuario datos);


    @POST("covid_webservice/diagnostico.php")
    Call<Respuesta> sintomas(@Body Diagnostico datos);

}
