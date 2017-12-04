package com.example.ajaykumar.drawer;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap GoToMap;

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
        googleMap.clear();
        GoToMap = googleMap;
        Intent i = getIntent();
        ArrayList<LatLng> list1 = i.getParcelableArrayListExtra("key1");
        ArrayList<String> list2 = i.getStringArrayListExtra("key2");
        ArrayList<String> list3 = i.getStringArrayListExtra("key3");

        LatLng latLng = new LatLng(0, 0);
        int j = list1.size();
        j=j-1;
        while (j!=-1)
        {
            if(list1.get(j)!=latLng)
            {   Log.i(list1.get(j).toString(),list1.get(j).toString());
                GoToMap.addMarker(new MarkerOptions().position(list1.get(j)).draggable(true).title(list3.get(j).replace(',','.')+" Battery Power "+list2.get(j)));
                GoToMap.moveCamera(CameraUpdateFactory.newLatLng(list1.get(j)));
            }
            j--;
        }
        // Add a marker in Sydney and move the camera
    }
}
