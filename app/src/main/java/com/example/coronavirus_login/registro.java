package com.example.coronavirus_login;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronavirus_login.BD.Persona;
import com.example.coronavirus_login.BD.Principal;
import com.example.coronavirus_login.BD.Respuesta;
import com.example.coronavirus_login.BD.WEBAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class registro extends AppCompatActivity {

    TextView tvBotton;

//ESTA CLASE PERMITE OBTENER LA UBICACION MEDIANTE LA SENHAL DEL GPS O DEL MISMO WIFI
    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;//PUEDE SER CUALQUIER VALOR
    double LATITUD=0.0, LONGITUD= 0.0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);//MODIFICADO, EL NOMBRE DEL LAYOUT PARA REGISTRO NO PARA LOGUEARSE!
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //me falto inicializar
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient( this);
    }





    private boolean checkPermissions() {//Verifica si la aplicacion cuenta con los siguientes permisos
        //coarse location Constant Value: "android.permission.ACCESS_COARSE_LOCATION"
        //Allows an app to access approximate location
        //access fine location Constant Value: "android.permission.ACCESS_FINE_LOCATION"
        //Allows an app to access precise location.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           Log.i("permiso", "CONCEDIDO");
            return true;
        }
        Log.i("permiso", "DENEGADO");
        return false;
    }


    private void requestPermissions() {
        //requests permissions to be granted to this application
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }


    //verificar si estan habilitados el gps y el wifi
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //habilitados el GPS y el WIFI
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(  LocationManager.NETWORK_PROVIDER
        );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //After the user has accepted or rejected the requested permissions you will receive a callback
        //reporting whether the permissions were granted or not.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            Log.i("GRANTED", grantResults[1]+"       ");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

    //ESTE METODO ES EL QUE REALMENTE OBTIENE LA UBICACION

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){//obtener una nueva ubicacion si no se encuentran datos de UNA ULTIMA UBICACION

        @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//NIVEL DE CALIDAD
        mLocationRequest.setInterval(0);//CADA CUANTO TIEMPO SE RECIBIRA NOTIFICACIONES
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        //Used for receiving notifications from the FusedLocationProviderApi when the device location has changed or
        // can no longer be determined. The methods are called if the LocationCallback has been registered with the
        // location client using the requestLocationUpdates(GoogleApiClient, LocationRequest, LocationCallback, Looper) method.
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location mLastLocation = locationResult.getLastLocation();
                //AQUIII
                LATITUD= mLastLocation.getLatitude();
                LONGITUD= mLastLocation.getLongitude();
                Log.i( "LAT",mLastLocation.getLatitude()+"");
                Log.i( "Long",mLastLocation.getLongitude()+"");
                CallMapActivity();  //ACA SE LLAMA A ACTIVITY_GOE

            }
        };
        //FusedLocationProviderClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(      mLocationRequest, mLocationCallback,     Looper.myLooper()  );

    }


    public void CallMapActivity(){
        Intent llamar= new Intent(  this ,  geo.class);
        //aqui le estamos pasando parametros a la clase geo, latitud y longitud
        llamar.putExtra("lat", LATITUD);
        llamar.putExtra("lon", LONGITUD);
        startActivity( llamar );
    }


    /**
     * este metodo es mas completo porque
     * se hace verificaciones
     * se verifica si la app tiene permisos para acceder a la ubicacion
     * se chequea si el gps del dispositivo esta activado
     * se crea un evento donde se recibe los resultados de la lectura  de ubicacion
     */


    @SuppressLint("MissingPermission")
    private void getUserLocation(){
        if (checkPermissions()) {//la app cuenta con los permisos de ubicacion por gps y red
            if (isLocationEnabled()) {//estan habilitados en el dispositivo
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                requestNewLocationData();

                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {//sI NO SE CUENTAN CON LOS PERMISOS
            requestPermissions();
        }
    }




    //ultimo
    //creamos UN METODO QUE SE LLAMARA DESDE EL BOTON DE UBICACION,
    public  void MostrarMapa(View v){
        getUserLocation();
    }







    public void createUser() {
        WEBAPI consumidor = new Principal().buildService(WEBAPI.class);
        //RECOGEMOS LOS DATOS DEL FORMULARIO
        String cedula = ((EditText) findViewById(R.id.CI)).getText().toString();
        String nom = ((EditText) findViewById(R.id.NOMBRES)).getText().toString();
        String ape = ((EditText) findViewById(R.id.APELLIDOS)).getText().toString();
        String telef = ((EditText) findViewById(R.id.TELEF)).getText().toString();
        String credpol = ((EditText) findViewById(R.id.CREDPOLI)).getText().toString();
        String nick = ((EditText) findViewById(R.id.NICK)).getText().toString();
        String clave = ((EditText) findViewById(R.id.PASSW)).getText().toString();
        //USAR LATITUD Y LONGITUD   convertimos a string
        String lati = String.valueOf(LATITUD);
        String longi = String.valueOf(LONGITUD);

        //CREAMOS UN OBJETO DE TIPO PERSONA
        Persona personaX = new Persona();//este objeto ya esta preparado para llenarse mas facilmente con los datos
        //solo basta llamar a los metodos
        personaX.setCedula(cedula);
        personaX.setNombre(nom);
        personaX.setApellido(ape);
        personaX.setTelefono(telef);
        personaX.setCredencial(credpol);
        personaX.setNick(nick);
        personaX.setClave(clave);
        personaX.setLatitud( lati);
        personaX.setLongitud( longi);


        //AHORA UTILIZAREMOS EL WEBSERVICE
        //LLAMAREMOS AL METODO SIGN UP
        //asignamos esto a una variable
        Call<Respuesta> respuesta= consumidor.signup(    personaX  );//recibe los datos de persona
        respuesta.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                //esto se ejecuta cuando la operacion tuvo exito
                Log.i("TEST EXITO ", response.body().getMensaje() );
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
            //esto corre cuando hay fallas
                Log.i("TEST en caso de ERROR ",  t.getMessage());

            }
        });

    }/***   end create USER **/




}
