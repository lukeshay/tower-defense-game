package com.example.towerDefender.Util;

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

}
