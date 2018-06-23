package com.example.ajaykumar.drawer;
import com.example.ajaykumar.drawer.MapsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ajaykumar.drawer.sql.GPSTracker;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import static com.example.ajaykumar.drawer.R.id.button1;
import static com.example.ajaykumar.drawer.R.id.transition_current_scene;

public class trackedusers extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog progress;
    EditText user1;
    EditText user2;
    EditText user3;
    EditText user4;
    EditText user5;
    String transportId2;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b11;
    Button b12;
    Button b13;
    Button b14;
    Button b15;
    Button b6;
    Button pl;
    Intent i2;
    int flag=0;
    private DatabaseReference mFirebaseTransportRef;
    private DatabaseReference mFirebaseTransportRef1;
    private DatabaseReference mFirebaseTransportRef2;
    private SharedPreferences mPrefs;
    private LinkedList<Map<String, Object>> mTransportStatuses = new LinkedList<>();
    ArrayList<LatLng> array1 = new ArrayList<LatLng>();
    ArrayList<String> array2 = new ArrayList<String>();
    ArrayList<String> array3 = new ArrayList<String>();
    ArrayList<LatLng> array4 = new ArrayList<LatLng>();
    int i=0;
    LatLng l = new LatLng(0, 0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackedusers);
        mPrefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);


        user1 = (EditText) findViewById(R.id.user1);
        user2 = (EditText) findViewById(R.id.user2);
        user3 = (EditText) findViewById(R.id.user3);
        user4 = (EditText) findViewById(R.id.user4);
        user5 = (EditText) findViewById(R.id.user5);
        while (i < 5) {
            array1.add(i, l);
            array2.add(i,"a");
            array3.add(i,"a");
            i++;
        }
        i=0;
        array4.add(0,l);
        array4.add(1,l);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b1.setOnClickListener(trackedusers.this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b11 = (Button) findViewById(R.id.button11);
        b12 = (Button) findViewById(R.id.button12);
        b13 = (Button) findViewById(R.id.button13);
        b14 = (Button) findViewById(R.id.button14);
        b15 = (Button) findViewById(R.id.button15);
        b11.setOnClickListener(trackedusers.this);
        b12.setOnClickListener(this);
        b13.setOnClickListener(this);
        b14.setOnClickListener(this);
        b15.setOnClickListener(this);
        b6=(Button)findViewById(R.id.gotomap1);
        b6.setOnClickListener(this);
        GPSTracker gps = new GPSTracker (trackedusers.this);
        double latitude = gps.getLatitude();
        double longitude= gps.getLongitude();
        LatLng k=new LatLng(latitude,longitude);
        array4.add(0,k);
        progress = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

    }


    public void onClick(final View v) {

            switch (v.getId()) {

                case button1:
                    //Inform the user the button1 has been clicked

                    if(user1.getText().toString().length()>0) {
                        loadPreviousStatuses(user1.getText().toString().replace('.', ','), 1);
                    }else
                        Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.button2:
                    //Inform the user the button2 has been clicked
                    if(user2.getText().toString().length()>0) {
                        loadPreviousStatuses(user2.getText().toString().replace('.', ','), 2);

                    }else
                        Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();


                    break;
                case R.id.button3:
                    //Inform the user the button2 has been clicked

                    if(user3.getText().toString().length()>0)
                    {
                        loadPreviousStatuses(user3.getText().toString().replace('.',','),3);

                    }
                    else
                        Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();


                    break;
                case R.id.button4:
                    //Inform the user the button2 has been clicked
                    if(user4.getText().toString().length()>0) {
                        progress.show();
                        loadPreviousStatuses(user4.getText().toString().replace('.', ','), 4);
                        //pr
                    }
                    else
                        Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();


                    break;
                case R.id.button5:
                    if(user5.getText().toString().length()>0)
                        loadPreviousStatuses(user5.getText().toString().replace('.',','),5);
                    else
                        Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();


                    break;
                case R.id.gotomap1:
                    if(flag==1) {
                        Intent intent = new Intent(trackedusers.this, MapsActivity.class);
                        intent.putParcelableArrayListExtra("key1", array1);
                        intent.putStringArrayListExtra("key2", array2);
                        intent.putStringArrayListExtra("key3", array3);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(trackedusers.this,"Please Fill atleast one User ID",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.button11:
                    if(user1.getText().toString().length()>0) {
                    //    transportId2="ayushghd";
                        loadPreviousStatuses(user1.getText().toString().replace('.',','), 100);
                      //  loadPreviousStatuses(transportId2, 0);
                    }
                    else
                        Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.button12:
                    if(user2.getText().toString().length()>0) {
                        transportId2="ayushghd";
                        loadPreviousStatuses(user2.getText().toString().replace('.',','), 100);

                    }
                    else
                    Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button13:
                    if(user3.getText().toString().length()>0) {
                        transportId2="ayushghd";
                        loadPreviousStatuses(user3.getText().toString().replace('.',','), 100);

                    }
                    else
                    Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.button14:
                    if(user4.getText().toString().length()>0) {
                        transportId2="ayushghd";
                        loadPreviousStatuses(user4.getText().toString().replace('.',','), 100);

                    }
                    else
                    Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.button15:
                    if(user5.getText().toString().length()>0) {
                        transportId2="ayushghd";
                        loadPreviousStatuses(user5.getText().toString().replace('.',','), 100);

                    }else
                    Toast.makeText(trackedusers.this, " Please Fill The User ID ", Toast.LENGTH_SHORT).show();

                    break;
            }
        }

    private void loadPreviousStatuses(final String user,final int id) {
    progress.show();
        String transportId = user;
        FirebaseAnalytics.getInstance(this).setUserProperty("transportID", transportId);
        String path = getString(R.string.firebase_path) + transportId;
        Log.i("beforeFirebase", "beforeFirebase" );
        mFirebaseTransportRef = FirebaseDatabase.getInstance().getReference(path);
        mFirebaseTransportRef1 = FirebaseDatabase.getInstance().getReference("raw-locations");
        mFirebaseTransportRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if ((user!="")&&snapshot.hasChild(user)) {
                    // run some code

        mFirebaseTransportRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("insidedatachange","insidedatachange");
                if (snapshot != null) {
                    if (id!=0 && id!=100)
                    Toast.makeText(trackedusers.this, "TRACKING STARTED", Toast.LENGTH_SHORT).show();
                    flag=1;
                    Log.i("insideforloop", "insidedforloop");
                    for (DataSnapshot transportStatus : snapshot.getChildren()) {
                        mTransportStatuses.add(Integer.parseInt(transportStatus.getKey()),
                                (Map<String, Object>) transportStatus.getValue());
                    }
                        Log.i("insidedatachange", "insidedatachange");
                        Map<String, Object> status = mTransportStatuses.get(0);
                        Location locationForStatus = new Location("");
                        locationForStatus.setLatitude((double) status.get("lat"));
                        locationForStatus.setLongitude((double) status.get("lng"));
                        LatLng lat = new LatLng((double) status.get("lat"), (double) status.get("lng"));
                    Log.i("insidedatachange", "belowLAT");

                    String username = user;
                    Log.i("insidedatachange", "BelowUSERNAME");

                    String battery = status.get("power").toString();
                    Log.i("insidedatachange", "BELOWbattery");
                    if (id == 1) {
                            array1.add(0, lat);
                            array2.add(0, battery);
                            array3.add(0, user);
                        } else if (id == 2) {
                            array1.add(1, lat);
                            array2.add(1, battery);
                            array3.add(1, user);
                        } else if (id == 3) {
                            array1.add(2, lat);
                            array2.add(2, battery);
                            array3.add(2, user);
                        } else if (id == 4) {
                            array1.add(3, lat);
                            array2.add(3, battery);
                            array3.add(3, user);
                        } else if (id == 5) {
                            array1.add(4, lat);
                            array2.add(4, battery);
                            array3.add(4, user);
                        }
                        /*else if(id==0){
                            Log.i("insideID0  ",lat.toString());
                        GPSTracker gps = new GPSTracker (trackedusers.this);
                        double latitude = gps.getLatitude();
                        double longitude= gps.getLongitude();
                        LatLng k=new LatLng(latitude,longitude);
                           array4.add(0,k);
                        Log.i("insideID0",k.toString());
                    }*/
                    else if(id==100) {

                        Log.i("insideID-1  ", lat.toString());

                        array4.add(1, lat);
                    }                        Log.i("belowforloop", "belowforloop");
                    if(array4.get(0)!=l && array4.get(1)!=l && array4.get(0)!=array4.get(1)) {
                        Log.i("inside2NDintent","s0etjs");
                        Log.i("inside2NDintent",array4.get(0).toString());
                        Log.i("inside2NDintent",array4.get(1).toString());
                        i2 = new Intent(trackedusers.this, ShortestDistance.class);
                        i2.putParcelableArrayListExtra("key11", array4);
                        progress.dismiss();
                        startActivity(i2);
                        array4.add(1,l);
                    }
                    else {
                        Log.i("insideELSE", "else" + array4.get(0).toString() + " " + array4.get(1).toString());
                        progress.dismiss();
                    }

                }
                else{
                    progress.dismiss();
                    Log.i("insideelse", "insidedelse");
                    if(id!=0) {
                        Toast.makeText(trackedusers.this, user.replace(',', '.') + " NOT FOUND ", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(trackedusers.this, user.replace(',', '.') + " FIRST ALLOW YOUR TRACKING ", Toast.LENGTH_SHORT).show();
                    }
                    array4.add(1,l);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // TODO: Handle gracefully
            }
        });
                }

                else{
                    Toast.makeText(trackedusers.this, user + " NOT FOUND ", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
