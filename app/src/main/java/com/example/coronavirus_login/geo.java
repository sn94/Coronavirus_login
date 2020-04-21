package com.example.coronavirus_login;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class geo extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //aca empieza lo que me mostro el tutorial//
   // private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;



    //  conecta tu telefono mediante USB si tenes para probar  .....

    //cuando termine de compilar...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
//ACA SE RECIBEN LOS PARAMETROS QUE SE ENVIARON DESDE REGISTRO
        lat = getIntent().getExtras().getDouble("lat");
        lng = getIntent().getExtras().getDouble("lon");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }









    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 15.0f;

        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Paraguay"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Log.i("MIN ZOOM",  Float.toString(mMap.getMinZoomLevel()));
        mMap.setMinZoomPreference(zoom);

        //agregar evento al mapa cuando el usuario clickee
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLng coordenadas = latLng;
                double latitud = coordenadas.latitude;
                double longitud = coordenadas.longitude;
                Toast.makeText( getBaseContext(), "Latitud: "+latitud+" Longitud: "+ longitud, Toast.LENGTH_LONG).show();

            }
        });
       /*
        mMap = googleMap;
        miUbicacion(); // supuestamente aca llama a mi metodo

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

    //METODO QUE ME EXPLICA EN EL VIDEO




    public void cerrar(View v){
     finish();
    }

    //fin 4to metodo
}


