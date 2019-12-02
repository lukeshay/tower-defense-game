package com.example.towerDefender.VolleyServices;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyUtilities {


    public static void getRequest(Context context, String url, final VolleyResponseListener listener){
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        listener.onError(error.toString());
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void deleteRequest(Context context, String url, final VolleyResponseListener listener){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        listener.onError(error.toString());
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void postRequest(Context context, String url, final VolleyResponseListener listner, final JSONObject params) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

         final String requestBody = params.toString();

        // Initialize a new StringRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };

        // Add StringRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}