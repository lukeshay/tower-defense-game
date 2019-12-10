package com.example.towerDefender.Activities;import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.util.Log;import android.view.View;import android.widget.Button;import android.widget.EditText;import com.example.towerDefender.Util.UserUtility;import com.example.towerDefender.VolleyServices.VolleyResponseListener;import com.example.towerDefender.VolleyServices.VolleyUtilities;import org.json.JSONException;import org.json.JSONObject;public class RegisterUserActivity extends Activity {    @Override    protected void onCreate(Bundle savedInstance){        super.onCreate(savedInstance);        setContentView(com.example.towerDefender.R.layout.register_user_activity);        Button submit = (Button) findViewById(com.example.towerDefender.R.id.submitButton);        final EditText screenName = (EditText) findViewById(com.example.towerDefender.R.id.usernameField);        final Intent intent = new Intent(this, NavigationActivity.class);        submit.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                JSONObject jsonObject = new JSONObject();                try {                    jsonObject.put("phoneId", UserUtility.getUserId());                    jsonObject.put("userName",screenName.getText());                    jsonObject.put("email", screenName.getText() + "@email.com");                    jsonObject.put("firstName", "FirstNameTest");                    jsonObject.put("lastName", "LastNameTest");                    jsonObject.put("xp", 0);                    jsonObject.put("trophies", 0);                    jsonObject.put("userType", "Admin");                    jsonObject.put("ownedCards", null);                    System.out.println(jsonObject.toString());                }                catch(JSONException e){Log.e("e", e.toString());}                VolleyUtilities.postRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users", new VolleyResponseListener() {                    @Override                    public void onError(String message) {                        Log.e("post-error", message);                    }                    @Override                    public void onResponse(Object response) {                        Log.e("user-request", response.toString());                    }                },jsonObject);                startActivity(intent);            }        });    }}