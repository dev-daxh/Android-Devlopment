package com.example.googlemaps;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = findViewById(R.id.location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Nagpur and move the camera
        LatLng nagpur = new LatLng(21.1458, 79.0882);
        mMap.addMarker(new MarkerOptions().position(nagpur).title("Marker in Nagpur"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nagpur, 12));

        // On map click
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Clicked"));
                Geocoder geocoder = new Geocoder(getApplicationContext());

                try {
                    ArrayList<Address> arradr = (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (arradr != null && !arradr.isEmpty()) {
                        location.setText(arradr.get(0).getAddressLine(0));
                    } else {
                        location.setText("No address found for this location.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    location.setText("Geocoder service is unavailable.");
                }
            }
        });

        // Add a circle around Nagpur
        mMap.addCircle(new CircleOptions().center(nagpur)
                .radius(1000)
                .fillColor(Color.argb(50, 255, 255, 0))
                .strokeColor(Color.RED));
    }
}
