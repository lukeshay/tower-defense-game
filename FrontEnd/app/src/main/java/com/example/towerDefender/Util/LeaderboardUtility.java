package com.example.towerDefender.Util;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.BaseApplication;
import com.example.towerDefender.R;
import com.example.towerDefender.VolleyServices.User;
import com.example.towerDefender.VolleyServices.UserRestServices;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import java.util.ArrayList;

public class LeaderboardUtility {

    public static final int GOLD_CUTOFF = 50;
    public static final int SILVER_CUTOFF = 25;

    /**
     * Grabs the users from the database, storing them in the public users variable.
     */
    public static void initializeUsers(final AppCompatActivity activity){
        VolleyUtilities.getRequest(BaseApplication.getAppContext(), UserRestServices.BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("ERROR", "Encountered an error while grabbing cards from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                initializeLeaderboard(new ArrayList<>(JsonUtility.jsonToUserList(response.toString())),
                        activity);
            }
        });
    }

    /**
     * sorts and displays the users in the leaderboard
     * @param users the users in the leaderboard
     * @param activity the activity to display the leaderboard in
     */
    private static void initializeLeaderboard(ArrayList<User> users, AppCompatActivity activity){
        User[] userArray = new User[users.size()];
        users.toArray(userArray);
        LeaderboardUtility.mergeSort(userArray);
        LinearLayout layout = activity.findViewById(R.id.leaderboardLayout);
        TextView leaderboardTitle = activity.findViewById(R.id.leaderboardTitle);
        leaderboardTitle.setPadding(50, 50, 50, 50);
        leaderboardTitle.setTextColor(Color.BLACK);
        int numToDisplay  = Math.min(10, userArray.length);
        for(int i = 0; i < numToDisplay; i++){ // only display top ten
            User user = userArray[i];
            TextView textView = new TextView(activity.getApplicationContext());
            textView.setLayoutParams(leaderboardTitle.getLayoutParams());
            if(user.phoneId.equals(UserUtility.getUserId())){
                textView.setBackgroundColor(activity.getResources().getColor(R.color.selectorGreen));
            } else if(user.trophyCount >= LeaderboardUtility.GOLD_CUTOFF){ //user is the highest ranked
                textView.setBackgroundColor(activity.getResources().getColor(R.color.colorGold));
            } else if(userArray[i].trophyCount >= LeaderboardUtility.SILVER_CUTOFF){
                textView.setBackgroundColor(activity.getResources().getColor(R.color.colorSilver));
            } else {
                textView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            }
            textView.setText(user.userName + ": " + user.trophyCount + " trophies.");
            textView.setPadding(50, 50, 50, 50);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);
            layout.addView(textView);
        }
    }

    /**
     * Performs merge sort on the provided array of {@link Comparable}s
     * @param toSort the array to sort
     */
    public static void mergeSort(Comparable [ ] toSort)
    {
        Comparable[] tmp = new Comparable[toSort.length];
        mergeSort(toSort, tmp,  0,  toSort.length - 1);
    }


    private static void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right)
    {
        if( left < right )
        {
            int center = (left + right) / 2;
            mergeSort(a, tmp, left, center);
            mergeSort(a, tmp, center + 1, right);
            merge(a, tmp, left, center + 1, right);
        }
    }


    private static void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd )
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(a[left].compareTo(a[right]) > 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
    }
}
