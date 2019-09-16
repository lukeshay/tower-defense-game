package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static android.provider.Settings.Secure.ANDROID_ID;

public class registerUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.register_user_activity);

        Button submit = (Button) findViewById(R.id.submitButton);
        final EditText screenName = (EditText) findViewById(R.id.usernameField);

<<<<<<< HEAD
       final Intent intent = new Intent(this, inventoryActivity.class);
=======
       final Intent intent = new Intent(this, NavigationActivity.class);
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL url;
                HttpURLConnection client = null;
                try {
                //    url = new URL("hey this needs to be the server resource");
                //    client = (HttpURLConnection) url.openConnection();
                //    client.setRequestMethod("POST");
                //    client.setRequestProperty("Content-Type", "application/json; utf-8");
                //    client.setDoOutput(true);
                //    OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                //    osw.write("{"+ screenName.getText() + ":" + generateUUID() + "}");
                    Log.e("user", "{" + screenName.getText() + ":" + generateUUID() + "}");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                   // BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }

    private String generateUUID(){
       return Settings.Secure.getString(getApplicationContext().getContentResolver(), ANDROID_ID);
    }
}
