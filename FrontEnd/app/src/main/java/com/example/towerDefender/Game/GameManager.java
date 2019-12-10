package com.example.towerDefender.Game;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.R;
import com.example.towerDefender.SocketServices.SocketMessage;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.Util.CanvasUtility;
import com.example.towerDefender.Util.ChatUtility;
import com.example.towerDefender.Util.JsonUtility;
//import com.example.towerDefender.SocketServices.WebSocketClientConnection;

import java.util.ArrayList;

import com.example.towerDefender.Card.PlayedCard;

/**
 * The GameManager handles all the {@link Player}s and {@link GameObjectSprite}s for the {@link GameView} to streamline code.
 */
public class GameManager {
    public static GameManager instance;
    /**
     * The side the {@link GameManager}'s player is on. (left or right)
     */
    public String playerSide;
    public Player player;
    public boolean isConnected = false;
    public PlayedCardsHolder playedCards;

    public boolean gameOver = false;
    public boolean playerSideSet = false;
    public boolean wonOrLost = false; // true if they won
    public Sprite closeButton;
    /**
     * Constructs a new {@link GameManager}
     * @param player the {@link Player} to use
     */
    public GameManager(Player player){
        GameManager.instance = this;
        this.player = player;
        playedCards = new PlayedCardsHolder(new ArrayList<PlayedCard>());
        playerSide = "left";
        closeButton = new BackButton(BitmapFactory.decodeResource(player.getPlayerContext().getResources(), R.drawable.back_button));
    }

    /**
     * Constructs a new {@link GameManager}
     * @param player the {@link Player} to use
     * @param test true if launching in 'test' mode. Limits context references
     */
    public GameManager(Player player, boolean test){
        GameManager.instance = this;
        this.player = player;
        playedCards = new PlayedCardsHolder(new ArrayList<PlayedCard>());
        playerSide = "left";
        if(!test){
            closeButton = new BackButton(BitmapFactory.decodeResource(player.getPlayerContext().getResources(), R.drawable.back_button));
        }
    }

    /**
     * Initializes the {@link Player}'s deck.
     */
    public void initializeDeck(){
        player.drawHand();
    }

    /**
     * @return this {@link GameManager}'s {@link Player}
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Updates the {@link GameObjectSprite}s and {@link CardInHand}s.
     */
    public void update(){
        player.update();
        for(CardInHand card :  getPlayer().getHand()){
            card.update();
        }
    }

    /**
     * @return this game manager's player's side (left or right)
     */
    public String getPlayerSide(){
        return playerSide;
    }

    /**
     * @return true if the socket has sent over a message confirming the connection
     */
    public boolean isConnected(){
        return isConnected;
    }

    /**
     * @return the {@link PlayedCardsHolder} containing all the played cards
     */
    public PlayedCardsHolder getPlayedCards(){
        return playedCards;
    }

    /**
     *
     * @return true if the game is over
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Sets the side this {@link GameManager}'s {@link Player} is on.
     * @param side the side to set to
     */
    public void setPlayerSide(String side){
        playerSide = side;
    }

    /**
     * Sets gameOver.
     * @param isGameOver the boolean to set gameOver to
     */
    public void setGameOver(boolean isGameOver){
        this.gameOver = true;
    }

    /**
     * Sets the winOrLost field of the manager. If win is '1', then the game was a win, otherwise it was a loss
     * @param win the value to set winOrLost to: if '1', its a win, otherwise a loss
     */
    public void setWinOrLoss(boolean win){
        this.wonOrLost = win;
    }
}
