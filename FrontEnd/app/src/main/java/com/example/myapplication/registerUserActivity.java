package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
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

import static android.provider.Settings.Secure.ANDROID_ID;

public class registerUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.register_user_activity);

        Button submit = (Button) findViewById(R.id.submitButton);
        final EditText screenName = (EditText) findViewById(R.id.usernameField);
        final EditText password = (EditText) findViewById(R.id.passwordField);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL url;
                HttpURLConnection client = null;
                try {
                    url = new URL("hey this needs to be the server resource");
                    client = (HttpURLConnection) url.openConnection();
                    client.setRequestMethod("POST");
                    client.setRequestProperty("Content-Type", "application/json; utf-8");
                    client.setDoOutput(true);
                    OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                    osw.write("{"+ screenName.getText() + ", " + password + ":" + generateUUID() + "}");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private String generateUUID(){
        return ANDROID_ID;
    }
}
