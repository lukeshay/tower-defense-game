package com.example.towerDefender.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.R;
import com.example.towerDefender.Util.LeaderboardUtility;
import com.example.towerDefender.VolleyServices.CardRestServices;
import com.example.towerDefender.VolleyServices.JsonUtils;
import com.example.towerDefender.VolleyServices.User;
import com.example.towerDefender.VolleyServices.UserRestServices;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_leaderboard);
        VolleyUtilities.getRequest(this.getApplicationContext(), UserRestServices.BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("ERROR", "Encountered an error while grabbing cards from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                initializeLeaderboard(new ArrayList<>(JsonUtils.jsonToUserList(response.toString())));
            }
        });
    }

    private void initializeLeaderboard(ArrayList<User> users){
        Log.i("leaderboard_info", users.toString());
        User[] userArray = new User[users.size()];
        users.toArray(userArray);
        sortUsers(userArray);
        displayUsers(userArray);
        Log.i("leaderboard_info", userArray.toString());
    }

    private void sortUsers(User[] users){
        LeaderboardUtility.mergeSort(users);
    }

    private void displayUsers(User[] users){
        LinearLayout layout = this.findViewById(R.id.leaderboardLayout);
        for (User user : users) {
            TextView textView = new TextView(this);
            textView.setText(user.phoneId + ": " + user.trophyCount + " trophies.");
            //textView.setTextColor(getResources().getColor(R.color.colorAccent));
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(textView);
        }
    }


}
