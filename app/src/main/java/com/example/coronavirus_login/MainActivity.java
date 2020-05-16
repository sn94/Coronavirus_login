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

    //edit texts
    EditText nick_Edit= null, pass_Edit= null;


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
        //campos
        nick_Edit= (EditText) findViewById(R.id.LOGIN_USER);
        pass_Edit= (EditText) findViewById(R.id.LOGIN_PASS);

    }



    void irEncuesta(){
        //autenticado
        Intent i = new Intent(MainActivity.this,  encuesta.class);
        startActivity(i);

    }


   boolean camposLlenos(){

        if( nick_Edit.getText().toString().length() == 0){
            Toast.makeText( this,  "Ingrese el nick", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if( pass_Edit.getText().toString().length() == 0){
                Toast.makeText( this,  "Ingrese su clave", Toast.LENGTH_LONG).show();
                return false;
            }else{
                return true;
            }
        }
    }
    public void acceder(View v){
        if( camposLlenos()){
            Usuario usu= new Usuario();
            String nick= nick_Edit.getText().toString();
            String pass= pass_Edit.getText().toString();

            usu.setUsuario( nick);  usu.setClave(  pass);
            Log.i("usu", nick+" "+pass);
            WEBAPI consumidor = new Principal().buildService(WEBAPI.class);
            Call<RespuestaLogin> respuesta= consumidor.signin(    usu  );
            respuesta.enqueue(new Callback<RespuestaLogin>() {
                @Override
                public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {


                    //usuario no   clave no es correcta
                    if(  response.body().getCodigo().equals("No existe") ||  response.body().getCodigo().equals("error"))
                        Toast.makeText( getApplicationContext(),  response.body().getMensaje() , Toast.LENGTH_LONG).show();

                    if(  response.body().getCodigo().equals("ok")){  //todo bien
                        //DATO USUARIO
                        CURRENT_ID_USER=  response.body().getUsuario();
                        irEncuesta();
                    }


                }

                @Override
                public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                    Log.i("TEST en caso de ERROR ",  t.getMessage());
                }
            });
        }

    }//end function





}



