package com.example.towerDefender.Utility;

import com.example.towerDefender.Util.LoadingScreenUtility;

import junit.framework.TestCase;

import org.junit.Assert;

public class LoadingScreenUtilityTest extends TestCase {

    public void testTooltips(){
        LoadingScreenUtility.getToolTip();
        Assert.assertEquals(LoadingScreenUtility.getToolTip(), LoadingScreenUtility.getToolTip() );
    }
}
