package com.example.aguirresabino.usogpsexemplo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView latitude, longitude, cidade, estado, pais;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        cidade = findViewById(R.id.cidade);
        estado = findViewById(R.id.estado);
        pais = findViewById(R.id.pais);

        //Verificação de permissão que deve ser usada a partir da api 23
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //nao implementado
        }else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        try {
            Address endereco = this.buscarEndereco(location.getLatitude(), location.getLongitude());
            cidade.setText("Cidade: " + endereco.getLocality());
            estado.setText("Estado: " + endereco.getAdminArea());
            pais.setText("Pais: " + endereco.getCountryName());

        } catch (IOException e) {
            e.printStackTrace();
        }

        latitude.setText("Latitude: " + location.getLatitude());
        longitude.setText("Longitude: " + location.getLongitude());

    }

    public Address buscarEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        if(addresses.size() > 0){
            address = addresses.get(0);
        }

        return address;
    }
}
