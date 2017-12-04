package com.example.ajaykumar.drawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ajaykumar.drawer.activities.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

//import static com.example.ajaykumar.map.MapsActivity.RC_SIGN_IN;

public class Login extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static final String TAG = "WelcomeActivity";
    public static final int RC_SIGN_IN = 123;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.w(TAG, "signed in");
                    onSignInIntialize(user);

                } else {
                    Log.w(TAG, "Not signed in");
                    onSignOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()/*,
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()*/))
                                    .build(),
                            RC_SIGN_IN);


                }
            }
        };
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == Activity.RESULT_OK) {
                Toast.makeText(this,"Signed in!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MyLocation.class).putExtra("my_token", response.getIdpToken()));
                finish();
                return;

            } else if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,"Sign In cancelled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void signOut() {
        mFirebaseAuth.signOut();
        onSignOutCleanup();
    }

    private void onSignOutCleanup() {
        mUsername = null;

    }

    private void onSignInIntialize(FirebaseUser user) {

        startActivity(new Intent(this,MyLocation.class));
        if(user.getDisplayName() != null) {
            mUsername = user.getDisplayName().toString();
        } else {
            mUsername = "User";
        }
    }


  /*  public void onBackPressed() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(Login.this);
        a_builder.setMessage("Do you want to Close this App !!!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent= new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finishAffinity();
                        startActivity(intent);

                        System.exit(0);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }) ;
        AlertDialog alert = a_builder.create();
        alert.show();
    }*/
}
