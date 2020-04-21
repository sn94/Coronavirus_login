package com.example.coronavirus_login.BD;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WEBAPI {


    //todas las llamadas que se haran al webservice


    @POST("covid_webservice/persona.php")
    Call<Respuesta> signup(@Body Persona datos);

}
