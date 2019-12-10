package com.example.towerDefender.VolleyServices;

import android.content.Context;
import android.util.Log;

import com.example.towerDefender.Util.UserUtility;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.Mock;

public class VolleyTest extends TestCase {

    @Mock
    private Context context;


    public void testCreateUser(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneId", "mockito");
            jsonObject.put("userName", "mockito");
            jsonObject.put("email", "mockito" + "@email.com");
            jsonObject.put("firstName", "FirstNameTest");
            jsonObject.put("lastName", "LastNameTest");
            jsonObject.put("xp", 0);
            jsonObject.put("trophies", 0);
            jsonObject.put("userType", "Admin");
        }
        catch(Exception e){}
        VolleyUtilities.postRequest(context, "http://coms-309-ss-5.misc.iastate.edu:8080/users", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("post-error", message);
            }

            @Override
            public void onResponse(Object response) {
                Log.e("user-request", response.toString());
                VolleyUtilities.getRequest(context,"http://coms-309-ss-5.misc.iastate.edu:8080/users", new VolleyResponseListener(){
                    @Override
                    public void onError(String message){Log.e("get-error", message);}

                    @Override
                    public void onResponse(Object response) {
                        Assert.assertTrue(response.toString().contains("mockito"));
                    }
                });
            }
        },jsonObject);

    }
}
