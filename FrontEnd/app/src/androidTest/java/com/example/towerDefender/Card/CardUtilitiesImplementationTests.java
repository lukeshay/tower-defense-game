package com.example.towerDefender.Card;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.towerDefender.Activities.MultiplayerGameActivity;
import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.R;

import junit.framework.TestCase;

import org.junit.Assert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardUtilitiesImplementationTests extends TestCase {
    private static Card card = new Card("Reaper", "basicReaper", 5, 5, 5, 5, "UNIT", 5);
    private static Card card1 = new Card("Wizard", "basicWizard", 5, 5, 5, 5, "UNIT", 5);

    public void testFriendlySpritesDiffer(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getResources();
        GameObjectSprite reaperSprite = CardUtilities.getGameObjectSpriteForCard(context, card, 15, 15);
        GameObjectSprite wizardSprite = CardUtilities.getGameObjectSpriteForCard(context, card1, 15, 15);
        Assert.assertFalse(reaperSprite.image.sameAs(wizardSprite.image));
    }

    public void testEnemySprite(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getResources();
        GameObjectSprite spriteActual = CardUtilities.getGameObjectSpriteForCard(context, card, 15, 15);
        GameObjectSprite spriteEnemy = CardUtilities.getEnemySprite(context, card, 15, 15);
        Assert.assertFalse(spriteActual.image.sameAs(spriteEnemy.image));
    }


    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
