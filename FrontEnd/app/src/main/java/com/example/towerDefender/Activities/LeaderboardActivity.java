package com.example.towerDefender.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.R;
import com.example.towerDefender.Util.LeaderboardUtility;
import com.example.towerDefender.Util.UserUtility;
import com.example.towerDefender.VolleyServices.JsonUtils;
import com.example.towerDefender.VolleyServices.User;
import com.example.towerDefender.VolleyServices.UserRestServices;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import org.w3c.dom.Text;

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

    /**
     * Constructs a text view element for each provided user, displaying their trophy count along
     * with their username
     * @param users a sorted (in descending order) array of {@link User}s
     */
    private void displayUsers(User[] users){
        LinearLayout layout = this.findViewById(R.id.leaderboardLayout);
        TextView leaderboardTitle = this.findViewById(R.id.leaderboardTitle);
        leaderboardTitle.setPadding(50, 50, 50, 50);
        leaderboardTitle.setTextColor(Color.BLACK);
        for(int i = 0; i < 10; i++){ // only display top ten
            User user = users[i];
            TextView textView = new TextView(this);
            textView.setLayoutParams(leaderboardTitle.getLayoutParams());
            if(user.phoneId.equals(UserUtility.getUserId())){
                textView.setBackgroundColor(getResources().getColor(R.color.selectorGreen));
            } else if(user.trophyCount >= LeaderboardUtility.GOLD_CUTOFF){ //user is the highest ranked
                textView.setBackgroundColor(getResources().getColor(R.color.colorGold));
            } else if(users[i].trophyCount >= LeaderboardUtility.SILVER_CUTOFF){
                textView.setBackgroundColor(getResources().getColor(R.color.colorSilver));
            } else {
                textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            textView.setText(user.userName + ": " + user.trophyCount + " trophies.");
            textView.setPadding(50, 50, 50, 50);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);
            layout.addView(textView);
        }
    }


}
