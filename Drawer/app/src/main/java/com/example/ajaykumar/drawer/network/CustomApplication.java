package com.example.ajaykumar.drawer.network;

import android.app.Application;

import com.android.volley.RequestQueue;

/**
 * Created by AJAY KUMAR on 9/26/2017.
 */

public class CustomApplication extends Application {
    private RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
    }
    public RequestQueue getVolleyRequestQueue(){
        return requestQueue;
    }
}
