package com.example.ajaykumar.drawer.network;

import android.app.DownloadManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by AJAY KUMAR on 9/26/2017.
 */

public class GsonRequest<T> extends Request<T> {
    // create variables
    private Gson mGson = new Gson();
    private Class<T> tClass;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Response.Listener<T> listener;
    public GsonRequest(int method, String url, Class<T> tClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.tClass = tClass;
        this.listener = listener;
        mGson = new Gson();
    }
    public GsonRequest(int method, String url, Class<T> tClass, Map<String, String> params, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.tClass = tClass;
        this.params = params;
        this.listener = listener;
        this.headers = null;
        mGson = new Gson();
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, tClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
