package com.example.myapplication.VolleyServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleyUtilities {


    public static void getRequest(Context context, String url, final VolleyResponseListener listner){
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        listner.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        listner.onError(error.toString());
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void postRequest(Context context, String url, final VolleyResponseListener listner, Map<String, String> params){
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        listner.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        listner.onError(error.toString());
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams(){
                return params;
            }
        };

        // Add StringRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}