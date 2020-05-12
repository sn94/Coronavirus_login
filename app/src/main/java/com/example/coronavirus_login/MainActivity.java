package com.example.coronavirus_login;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronavirus_login.BD.Principal;
import com.example.coronavirus_login.BD.Respuesta;
import com.example.coronavirus_login.BD.RespuestaLogin;
import com.example.coronavirus_login.BD.Usuario;
import com.example.coronavirus_login.BD.WEBAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    TextView textView2;

    public  static  String CURRENT_ID_USER="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = (TextView) findViewById(R.id.textView2);//registrarse

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,  registro.class);
                startActivity(i);
            }
        });
    }



    void irEncuesta(){
        //autenticado
        Intent i = new Intent(MainActivity.this,  encuesta.class);
        startActivity(i);

    }
    public void acceder(View v){
        Usuario usu= new Usuario();
        String nick= ((EditText) findViewById(R.id.LOGIN_USER)).getText().toString();
        String pass= ((EditText) findViewById(R.id.LOGIN_PASS)).getText().toString();

        usu.setUsuario( nick);  usu.setClave(  pass);
Log.i("usu", nick+" "+pass);
        WEBAPI consumidor = new Principal().buildService(WEBAPI.class);
        Call<RespuestaLogin> respuesta= consumidor.signin(    usu  );
        respuesta.enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                if( response.body().getCodigo().equals("ok")){
                    //DATO USUARIO
                    CURRENT_ID_USER=  response.body().getUsuario();
                    irEncuesta();
                }else{
                    Toast.makeText( getApplicationContext(),  response.body().getMensaje() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                Log.i("TEST en caso de ERROR ",  t.getMessage());
            }
        });


    }
}



