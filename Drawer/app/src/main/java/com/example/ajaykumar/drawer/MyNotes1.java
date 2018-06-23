package com.example.ajaykumar.drawer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.ajaykumar.drawer.activities.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class MyNotes1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView obj;
    NDb mydb;
    FloatingActionButton btnadd;
    ListView mylist;
    Menu menu;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    Context context = this;
    CoordinatorLayout coordinatorLayout;
    SimpleCursorAdapter adapter;
    Snackbar snackbar;
    private ImageView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mydb = new NDb(this);
        btnadd = (FloatingActionButton) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),
                        DisplayNote.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });
        Cursor c = mydb.fetchAll();
        String[] fieldNames = new String[] { NDb.name, NDb._id, NDb.dates, NDb.remark };
        int[] display = new int[] {  R.id.txtnamerow, R.id.txtidrow,
                R.id.txtdate,R.id.txtremark };
        adapter = new SimpleCursorAdapter(this, R.layout.listtemplate, c, fieldNames,
                display, 0);
        snackbar = Snackbar
                .make(coordinatorLayout, "Welcome to iSPY's notepad", Snackbar.LENGTH_LONG);
        snackbar.show();
        mylist = (ListView) findViewById(R.id.listView1);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                LinearLayout linearLayoutParent = (LinearLayout) arg1;
                LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent
                        .getChildAt(0);
                TextView m = (TextView) linearLayoutChild.getChildAt(1);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id",
                        Integer.parseInt(m.getText().toString()));
                Intent intent = new Intent(getApplicationContext(),
                        DisplayNote.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });
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
        profilepic = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.profileimage);
        Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString()).into(profilepic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            Intent intent = new Intent(this, MyLocation.class);
            startActivity(intent);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.btnadd:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),
                        DisplayNote.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.db) {
            startActivity(new Intent(getApplicationContext(), Trackedusernearby.class));
            // Handle the camera action
        }

        else if (id == R.id.ml) {
            startActivity(new Intent(getApplicationContext(), MyLocation.class));

        } else if (id == R.id.atk) {
            startActivity(new Intent(getApplicationContext(), TrackerActivity.class));

        }else if (id == R.id.tk) {
            startActivity(new Intent(getApplicationContext(),trackedusers.class));

        }else if (id == R.id.sr) {
            startActivity(new Intent(getApplicationContext(),MapsActivity2.class));

        } else if (id == R.id.nt) {
            startActivity(new Intent(getApplicationContext(), MyNotes1.class));
        } else if (id == R.id.ec) {
            startActivity(new Intent(getApplicationContext(), Navigation.class));
        }
        else if (id == R.id.el) {

        }
        else if(id==R.id.abtus)
        {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));

        }
        else if (id == R.id.lg) {
            AuthUI.getInstance()
                    .signOut(MyNotes1.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(MyNotes1.this, Login.class));
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
