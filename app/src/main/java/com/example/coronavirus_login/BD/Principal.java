package com.example.coronavirus_login.BD;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Principal {



    //HOST SERA LA IP DEL SERVIDOR QUE USAS AHORA, MAS BIEN EN ESTE CASO
    //YA QUE TRABAJAMOS LOCALMENTE  SERA LOCALHOST O 127.0.0.1
    public static String host="192.168.100.11", puerto="80";//PUERTO 80 ES POR DEFECTO PARA CUALQUIER SOLICITUD DE RECURSO
    //EN LA RED

    public static final String BASE_URL = "http://"+host+":"+puerto+"/";


    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    private static Retrofit.Builder builder = new Retrofit.Builder().
            baseUrl(BASE_URL).client( okHttpClient).
            addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();



//Service builder that creates implementation of those interfaces is created here.

    public static <T> T buildService(Class<T> type) {

        return retrofit.create(type);
    }

}
