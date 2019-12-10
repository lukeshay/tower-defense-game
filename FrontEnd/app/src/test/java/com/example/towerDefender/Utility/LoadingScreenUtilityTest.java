package com.example.towerDefender.Utility;

import com.example.towerDefender.Util.LoadingScreenUtility;

import junit.framework.TestCase;

import org.junit.Assert;

public class LoadingScreenUtilityTest extends TestCase {

    public void testUniqueTooltips(){
        Assert.assertNotNull(LoadingScreenUtility.getToolTip());
        String[] toolTips = new String[5];
        toolTips[0] = LoadingScreenUtility.getToolTip();
        toolTips[1] = LoadingScreenUtility.getToolTip();
        toolTips[2] = LoadingScreenUtility.getToolTip();
        toolTips[3] = LoadingScreenUtility.getToolTip();
        toolTips[4] = LoadingScreenUtility.getToolTip();
        boolean allSame = true;
        //Sometimes back-to-back calls will have the same result but after 5 they should never all be the same
        for(int i = 0; i < toolTips.length; i++){
            for(int j = 1; j < toolTips.length; j++){
                if(toolTips[i] != toolTips[j]){
                    allSame = false;
                }
            }
        }
        Assert.assertFalse(allSame);
    }
}
