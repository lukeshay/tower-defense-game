package com.example.myapplication.VolleyServices;

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(Object response);
}
