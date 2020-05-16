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

    TextView txt_lat, txt_long;


    //campos
    EditText cedula_edit,nom_edit,ape_edit , telef_edit, credpol_edit, nick_edit, clave_edit,domici_edit= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);//MODIFICADO, EL NOMBRE DEL LAYOUT PARA REGISTRO NO PARA LOGUEARSE!

        txt_lat= (TextView ) findViewById( R.id.latitud_text) ;
        txt_long= (TextView ) findViewById( R.id.longitud_text) ;
        //me falto inicializar
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient( this);

        //CAMPOS
        cedula_edit = (EditText) findViewById(R.id.CI);
        nom_edit = (EditText) findViewById(R.id.NOMBRES);
        ape_edit = (EditText) findViewById(R.id.APELLIDOS);
        telef_edit = (EditText) findViewById(R.id.TELEF);
        credpol_edit = (EditText) findViewById(R.id.CREDPOLI);
        nick_edit= (EditText) findViewById(R.id.NICK);
        clave_edit = (EditText) findViewById(R.id.PASSW);
        domici_edit= (EditText) findViewById(R.id.LOCALIDAD);
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
                //aqui seria bueno mostrar la latitud y longitud tomada en un label
                txt_lat.setText( String.valueOf( LATITUD ));
                txt_long.setText( String.valueOf( LONGITUD ));
                //*************/
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




    void mostrarFormLogin(){

        finish();
        Intent i = new Intent(registro.this,  MainActivity.class);
        startActivity(i);
    }



boolean camposLlenos(){
        boolean c1= cedula_edit.getText().toString().length() >0;
        boolean c2= ape_edit.getText().toString().length() >0;
        boolean c3= telef_edit.getText().toString().length() >0;
        boolean c4= credpol_edit.getText().toString().length() >0;
        boolean c5= nick_edit.getText().toString().length() >0;
        boolean c6= clave_edit.getText().toString().length() >0;
        boolean c7= domici_edit.getText().toString().length() >0;
        boolean c8= LATITUD > 0.0;
        boolean c9= LONGITUD > 0.0;
        if( !c1)
            Toast.makeText( getApplicationContext(), "Ingrese su numero de CI", Toast.LENGTH_LONG ).show();
    if( !c2)
        Toast.makeText( getApplicationContext(), "Ingrese su nombre", Toast.LENGTH_LONG ).show();
    if( !c3)
        Toast.makeText( getApplicationContext(), "Ingrese su apellido", Toast.LENGTH_LONG ).show();
    if( !c3)
        Toast.makeText( getApplicationContext(), "Ingrese su numero de telefono", Toast.LENGTH_LONG ).show();
    if( !c4)
        Toast.makeText( getApplicationContext(), "Ingrese su numero de credencial policial", Toast.LENGTH_LONG ).show();
    if( !c5)
        Toast.makeText( getApplicationContext(), "Ingrese su nombre de usuario", Toast.LENGTH_LONG ).show();
    if( !c6)
        Toast.makeText( getApplicationContext(), "Ingrese una clave", Toast.LENGTH_LONG ).show();
    if( !c7)
        Toast.makeText( getApplicationContext(), "Ingrese su Domicilio", Toast.LENGTH_LONG ).show();
    if( !c8   &&  !c9 )
        Toast.makeText( getApplicationContext(), "Proporcione su ubicacion", Toast.LENGTH_LONG ).show();

    return (  c1 &&  c2 && c3 && c4 && c5 &&  c6 && c7 && c8 && c9 ) ;

}
    public void createUser( View v) {

        if( camposLlenos()){
            WEBAPI consumidor = new Principal().buildService(WEBAPI.class);
            //RECOGEMOS LOS DATOS DEL FORMULARIO
            String cedula = cedula_edit.getText().toString();
            String nom = nom_edit.getText().toString();
            String ape = ape_edit.getText().toString();
            String telef = telef_edit.getText().toString();
            String credpol =credpol_edit.getText().toString();
            String nick =nick_edit.getText().toString();
            String clave = clave_edit.getText() .toString();
            String domici= domici_edit.getText().toString();
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
            personaX.setDetalle_domicilio(domici);
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
                    Toast.makeText( getApplicationContext(), response.body().getMensaje(), Toast.LENGTH_LONG ).show();
                    mostrarFormLogin();

                    //  Log.i("TEST EXITO ", response.body().getMensaje() );
                }

                @Override
                public void onFailure(Call<Respuesta> call, Throwable t) {
                    //esto corre cuando hay fallas
                    Toast.makeText( getApplicationContext(), "ERROR", Toast.LENGTH_LONG ).show();
                    //  Log.i("TEST en caso de ERROR ",  t.getMessage());

                    t.printStackTrace();

                }
            });
        }

    }/***   end create USER **/




}
