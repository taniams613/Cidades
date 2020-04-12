package com.example.a1casoprtico;

import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences dados = getSharedPreferences("info",0);
        String cidade = dados.getString("cidade", "");

        TextView c = findViewById(R.id.textViewCidade);
        c.setText(cidade);

        List<Address> localizacoes = null;
        try {
            localizacoes = new Geocoder(this).getFromLocationName(cidade,3);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Cidade não encontrada", Toast.LENGTH_LONG).show();
        }

        android.location.Address adress = localizacoes.get(0);

        LatLng coordenadas = new LatLng (adress.getLatitude(),adress.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 14));
    }

}
