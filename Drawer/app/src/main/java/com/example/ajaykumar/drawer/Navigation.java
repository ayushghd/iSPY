package com.example.ajaykumar.drawer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajaykumar.drawer.activities.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import at.markushi.ui.CircleButton;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CircleButton bt1;
    CircleButton bt2;
    CircleButton bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("create","create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bt1 = (CircleButton)findViewById(R.id.bt1);
        bt2 = (CircleButton)findViewById(R.id.bt2);
        bt3 = (CircleButton)findViewById(R.id.bt3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Navigation.this,"Calling Police",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:100"));
                startActivity(in);
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Navigation.this,"Calling AMBULANCE",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:102"));
                startActivity(in);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Navigation.this,"Calling FIRE FIGHTERS",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:101"));
                startActivity(in);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        String UserName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();

        TextView mID   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userID);
        mID.setText(userID);
        mID   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userName);
        mID.setText(UserName);
     }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.e("OnBack","OnBack");
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        Log.e("OnResume","onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.e("Start","Start");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.e("Stop","Stop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("Destroy","Destroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.e("Pause","Pause");
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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

            startActivity(new Intent(getApplicationContext(), MyLocation.class));

        }else if (id == R.id.atk) {
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

        else if(id == R.id.abtus) {
            startActivity(new Intent(getApplicationContext(),about.class));
        }
        else if (id == R.id.lg) {
            AuthUI.getInstance()
                    .signOut(Navigation.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(Navigation.this, Login.class));
                            finish();
                        }
                    });           }
        else if (id == R.id.nav_share) {
            Intent sh=new Intent();
            sh.setAction(Intent.ACTION_SEND);
            sh.putExtra(Intent.EXTRA_TEXT,"Download iSPY from here -> www.iSPY.com");
            sh.setType("text/plain");
            startActivity(sh);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
