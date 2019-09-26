package com.example.myapplication.VolleyServices;

import java.util.Map;

public interface VolleyResponseListener {
    void onError(String message);
    void onResponse(Object response);
}
