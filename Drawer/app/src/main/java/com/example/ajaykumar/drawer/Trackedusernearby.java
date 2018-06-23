package com.example.ajaykumar.drawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Trackedusernearby extends AppCompatActivity implements View.OnClickListener{
    Button btn;
    private ProgressBar progbar;
    private int progstatus=0;
    private Handler mHandler = new Handler();
    public double latitude;
    public double longitude;
    EditText userid;
    LatLng k=new LatLng(0,0);
    private DatabaseReference mFirebaseTransportRef;
    private DatabaseReference mFirebaseTransportRef1;
    private LinkedList<Map<String, Object>> mTransportStatuses = new LinkedList<>();
    ArrayList<LatLng> array1 = new ArrayList<LatLng>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackedusernearby);
        userid=(EditText)findViewById(R.id.us);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }

    public void onClick(View v){
        if(userid.getText().toString().length()>0) {
            array1.add(0,k);
            loadPreviousStatuses(userid.getText().toString().replace('.',','), 1);

        }
        else
            Toast.makeText(Trackedusernearby.this,  "Please Fll The User ID", Toast.LENGTH_SHORT).show();

    }
    private void loadPreviousStatuses(final String user,final int id) {
        final ProgressDialog progress = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progress.setMessage("Loading..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
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
                                latitude=(double)status.get("lat");
                                longitude=(double)status.get("lng");
                                LatLng l=new LatLng(latitude,longitude);
                                array1.add(0,l);
                                Log.i("insidedatachange", "belowLAT");
                                if(array1.get(0)!=k){
                                    Intent intent = new Intent(Trackedusernearby.this, MapsActivity3.class);
                                    intent.putParcelableArrayListExtra("key0", array1);
                                    progress.dismiss();
                                    startActivity(intent);
                                }
                                String username = user;
                                Log.i("insidedatachange", "BelowUSERNAME");

                                String battery = status.get("power").toString();
                                progress.dismiss();

                            }
                            else{
                                Log.i("insideelse", "insidedelse");

                                Toast.makeText(Trackedusernearby.this, user + " NOT FOUND ", Toast.LENGTH_SHORT).show();
                                progress.dismiss();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // TODO: Handle gracefully
                        }
                    });
                }

                else{
                    Toast.makeText(Trackedusernearby.this, user + " NOT FOUND ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
