package com.example.towerDefender.VolleyServices;

public interface VolleyResponseListener {
    void onError(String message);
    void onResponse(Object response);
}
