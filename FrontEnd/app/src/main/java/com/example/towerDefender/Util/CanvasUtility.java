package com.example.towerDefender.Util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.Card.PlayedCard;
import com.example.towerDefender.Game.ChatBar;
import com.example.towerDefender.Game.GameManager;
import com.example.towerDefender.Game.MainThread;

public class CanvasUtility {

    /**
     * All draws to the canvas for information with position sent from the server must be normalized against this bound.
     */
    public static final int SERVER_X_BOUND = 1920;
    private static Canvas canvas;
    public static Paint textPaint;

    /**
     * Draws the provided text on the center of the provided {@link Canvas}
     * @param canvas the {@link Canvas} to draw on
     * @param text the text to draw
     * @param textPaint the {@link Paint} to use for the text
     */
    public static void drawCenteredText(Canvas canvas, String text, Paint textPaint){
        initializeTextPaint();
        int xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(text) / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(text, xPos, yPos, textPaint);
    }

    /**
     * Converts an x position returned from the server to the x position to draw on screen
     * @param canvas the canvas to scale for
     * @param xPos the initial played card position from the server message
     * @return the converted position
     */
    public static int convertServerPositionToCanvasPosition(Canvas canvas, int xPos){
        return ((int)(canvas.getWidth() * ((float)xPos / CanvasUtility.SERVER_X_BOUND)));
    }

    /**
     * Converts an x position from the provided canvas to the x position to store in the server
     * @param canvas the canvas to scale from
     * @param xPos the position on the canvas
     * @return the converted position to send to the server
     */
    public static int convertCanvasPositionToServerPosition(Canvas canvas, int xPos){
        return (int)((float)xPos * CanvasUtility.SERVER_X_BOUND / canvas.getWidth());
    }

    /**
     * Draws the game state to the provided {@link Canvas} using the data from the provided {@link GameManager}
     * @param manager the game manager to draw data from
     * @param canvas the {@link Canvas} to draw to
     */
    public static void drawGameState(GameManager manager, Canvas canvas){
        initializeTextPaint();
        CanvasUtility.canvas = canvas;
        if(manager.isConnected && !manager.gameOver){ // in game
            manager.closeButton.draw(canvas);
            for(PlayedCard playedCard : manager.playedCards.getPlayedCards()){
                playedCard.draw(canvas);
            }
            for(CardInHand card : manager.player.getHand()){
                card.draw(canvas);
            }
            manager.player.draw(canvas);
            ChatUtility.drawChatPrompt(canvas);
            ChatUtility.drawChatMessage(canvas, CanvasUtility.textPaint);
        } else if(!manager.isConnected){ // waiting for game to start
            CanvasUtility.drawCenteredText(canvas, "Connected. Waiting for game start.",
                    CanvasUtility.textPaint);
        } else { //game has ended
            if(manager.wonOrLost){
                CanvasUtility.drawCenteredText(canvas, "You won!", CanvasUtility.textPaint);
            } else{
                CanvasUtility.drawCenteredText(canvas, "You lost!", CanvasUtility.textPaint);
            }
        }
    }

    /**
     * @return the stored canvas
     */
    public static Canvas getCanvas(){
        if(canvas == null){
            canvas = new Canvas();
        }
        return canvas;
    }

    public static void initializeTextPaint(){
        if(textPaint == null){
            CanvasUtility.textPaint = new Paint(Color.BLACK);
            CanvasUtility.textPaint.setTextSize(150);
            CanvasUtility.textPaint.setAlpha(128);
            CanvasUtility.textPaint.setColor(Color.WHITE);
        }
    }

}
