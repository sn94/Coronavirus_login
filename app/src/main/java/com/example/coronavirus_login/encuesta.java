package com.example.coronavirus_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.coronavirus_login.BD.Diagnostico;
import com.example.coronavirus_login.BD.Principal;
import com.example.coronavirus_login.BD.Respuesta;
import com.example.coronavirus_login.BD.WEBAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class encuesta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
    }

void mostrarInicio(){
        finish();
        startActivity( new Intent( this, MainActivity.class));
}

boolean camposLlenos(){
   boolean c1=  ((EditText) findViewById( R.id.temperatura)).getText().toString().length() > 0;
   if( !c1 )   Toast.makeText( this , "Indique su temperatura corporal actual", Toast.LENGTH_LONG).show();
   return c1;
}

    public void registrarDiagnostico( View v){
        if( camposLlenos()) {
            Diagnostico diag = new Diagnostico();
            //datos
            String user = MainActivity.CURRENT_ID_USER;
            String temperatura = ((EditText) findViewById(R.id.temperatura)).getText().toString();
            String tos = ((RadioButton) findViewById(R.id.dg_si)).isChecked() ? "si" : "no";
            String dif_Resp = ((RadioButton) findViewById(R.id.dr_si)).isChecked() ? "si" : "no";
            String anciano = ((RadioButton) findViewById(R.id.m50_si)).isChecked() ? "si" : "no";
            String asmatico = ((RadioButton) findViewById(R.id.asma_si)).isChecked() ? "si" : "no";
            String diabetes= ((RadioButton) findViewById(R.id.diab_si)).isChecked() ? "si" : "no";
            String cardiaco = ((RadioButton) findViewById(R.id.card_si)).isChecked() ? "si" : "no";
            String casa = ((RadioButton) findViewById(R.id.alquiler)).isChecked() ? "si" : "no";
            String rinon = ((RadioButton) findViewById(R.id.rinon_si)).isChecked() ? "si" : "no";
            String otrosSintomas = ((EditText) findViewById(R.id.otros_sinto)).getText().toString();
            diag.setTemperatura(Integer.parseInt(temperatura));
            diag.setTos_garganta(tos);
            diag.setDificultad_resp(dif_Resp);
            diag.setEdad(anciano);
            diag.setAsmatico(asmatico);
            diag.setDiabetes(  diabetes);
            diag.setCardiaco(cardiaco);
            diag.setCasa(casa);
            diag.setRinon(rinon);
            diag.setObservacion(otrosSintomas);
            diag.setId_persona(user);

            WEBAPI consumidor = new Principal().buildService(WEBAPI.class);
            Call<Respuesta> respuesta = consumidor.sintomas(diag);
            respuesta.enqueue(new Callback<Respuesta>() {
                @Override
                public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                    Toast.makeText(getApplicationContext(), response.body().getMensaje(), Toast.LENGTH_LONG).show();
                    mostrarInicio();
                    // Log.i( "respuesta", response.body().getMensaje()) ;
                }

                @Override
                public void onFailure(Call<Respuesta> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    //Log.i("TEST en caso de ERROR ",  t.getMessage());
                }
            });
        }
    }//end method
}
