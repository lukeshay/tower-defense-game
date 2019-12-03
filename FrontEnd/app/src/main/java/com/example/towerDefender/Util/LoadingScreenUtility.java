package com.example.towerDefender.Util;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.R;

import java.util.Random;

public class LoadingScreenUtility {
    public static Random random = new Random(System.currentTimeMillis());
    public static String[] toolTips = {
            "Remember: the player to do the most damage to the opponent's towers wins!",
            "The more mana you spend on a card, the better it will be!",
            "The direction your sprites are facing is the direction you should be trying to attack.",
            "Ready for a new game? Just hit the back button in the top left corner to forfeit the match."
    };
    /**
     * @return a string to display while the user is sitting in a loading screen
     */
    public static String getToolTip(){
        return toolTips[random.nextInt(toolTips.length - 1)];
    }

    /**
     * Displays a loading screen on the provided activity. The activity class is responsible for lifting the loading screen.
     * @param activity the activity for which a loading screen should be displayed.
     */
    public static void launchLoadingScreen(AppCompatActivity activity){
        activity.setContentView(R.layout.loading_screen);
        ((TextView)activity.findViewById(R.id.toolTip)).setText(getToolTip());
    }
}
