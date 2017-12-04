package com.example.ajaykumar.drawer;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajaykumar.drawer.activities.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Locale;

public class MyLocation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerClickListener,
    View.OnClickListener{

    private static final int PERMISSIONS_REQUEST = 1;
    private static String[] PERMISSIONS_REQUIRED = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.GET_ACCOUNTS };

        //Our Map
        private GoogleMap mMap;

        //To store longitude and latitude from map
        private double longitude;
        private double latitude;

        //Buttons
        private ImageButton buttonSave;
        private ImageButton buttonCurrent;
        private ImageButton buttonView;

        //Google ApiClient
        private GoogleApiClient googleApiClient;
        public String userID;
        public String UserName;
        public TextView mID;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
            int locationPermission = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
            int contact=ContextCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS);
            if (locationPermission != PackageManager.PERMISSION_GRANTED
                    ||contact != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST);
            }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            //Initializing googleapi client
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            //Initializing views and adding onclick listeners
            buttonCurrent = (ImageButton) findViewById(R.id.buttonCurrent);
            buttonView = (ImageButton) findViewById(R.id.buttonView);
            buttonCurrent.setOnClickListener(this);
            buttonView.setOnClickListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
         userID= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
            UserName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

            mID   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userID);
            mID.setText(userID);
            mID   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userName);
            mID.setText(UserName);
        }
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED )
        {
            //Creating a location object
            return ;}
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }
    public String getPreference() {
        SharedPreferences sp = getSharedPreferences("preferenceName", 0);
        return sp.getString("preferenceKeyValue", "default value if no preference is found");
    }
    public void setPreference(String data) {
        SharedPreferences.Editor editor = getSharedPreferences("preferenceName", 0).edit();
        editor.putString("preferenceKeyValue", data).commit();
    }

    //Function to move the map
    String msg,add;
    private void moveMap() {
        //String to display current latitude and longitude
        msg = latitude + ", "+longitude;
        add=msg;
        setPreference(add);


        getCompleteAddressString(latitude,longitude);
        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title
        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
                String address = addresses.get(0).getAddressLine(0);
                Log.i("address",address);
                String city = addresses.get(0).getLocality();
                Log.i("city",city);
                String state = addresses.get(0).getAdminArea();
                Log.i("state",state);
                String country = addresses.get(0).getCountryName();
                Log.i("country",country);
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                add=address+", "+city+", "+state+", "+country;

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(MyLocation.this);
            a_builder.setMessage("Do you want to Close this App !!!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.show();
        }
    }
    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonCurrent){
            getCurrentLocation();
            moveMap();
        }

        if(v == buttonView){
            Intent sh=new Intent();
            sh.setAction(Intent.ACTION_SEND);
            sh.putExtra(Intent.EXTRA_TEXT,"I am here -> " +add);
            sh.setType("text/plain");
            startActivity(sh);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.db) {
            startActivity(new Intent(getApplicationContext(), Trackedusernearby.class));
            // Handle the camera action
        } else if (id == R.id.ml) {
            startActivity(new Intent(getApplicationContext(),MyLocation.class));

        } else if (id == R.id.atk) {
            startActivity(new Intent(getApplicationContext(), TrackerActivity.class));

        }else if (id == R.id.tk) {
            startActivity(new Intent(getApplicationContext(),trackedusers.class));

        } else if (id == R.id.sr) {
            startActivity(new Intent(getApplicationContext(),MapsActivity2.class));

        } else if (id == R.id.nt) {
            startActivity(new Intent(getApplicationContext(), MyNotes1.class));
        } else if (id == R.id.ec) {
            startActivity(new Intent(getApplicationContext(), Navigation.class));
        }
        else if (id == R.id.el) {

        }
        else if (id == R.id.lg) {
            AuthUI.getInstance()
                    .signOut(MyLocation.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(MyLocation.this, Login.class));
                            finish();
                        }
                    });        }
        else if (id == R.id.nav_share) {
            Intent sh=new Intent();
            sh.setAction(Intent.ACTION_SEND);
            sh.putExtra(Intent.EXTRA_TEXT,"Download iSPY from here -> www.iSPY.com");
            sh.setType("text/plain");
            startActivity(sh);
        }
        else if(id == R.id.abtus) {
            startActivity(new Intent(getApplicationContext(),about.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
