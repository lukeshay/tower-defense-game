package com.example.towerDefender.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.VolleyServices.JsonUtils;
//import com.example.towerDefender.SocketServices.WebSocketClientConnection;

import java.util.ArrayList;

import com.example.towerDefender.Card.PlayedCard;

/**
 * The GameManager handles all the {@link Player}s and {@link GameObjectSprite}s for the {@link GameView} to streamline code.
 */
public class GameManager {
    /**
     * The side the {@link GameManager}'s player is on. (left or right)
     */
    public static String playerSide;
    private Player player;
    private boolean isConnected = false;
    private PlayedCardsHolder playedCards;
    //whether or not a card in the player's hand currently has status CardInHand.Status.PLACING
    private boolean isPlayingCard;
    //The index of the CardInHand to play from the player's CardInHand
    private int cardToPlayIndex;
    private long lastUpdate;
    private int cardsSent = 0;
    private boolean gameOver = false;
    private Paint textPaint;
    private boolean playerSideSet = false;
    private boolean wonOrLost = false; // true if they won

    /**
     * Constructs a new {@link GameManager}
     * @param player the {@link Player} to use
     */
    public GameManager(Player player){
        this.player = player;
        playedCards = new PlayedCardsHolder(new ArrayList<PlayedCard>(), this.player);
        isPlayingCard = false;
        cardToPlayIndex = 0;
        playerSide = "left";
        SocketUtilities.sendMessage("Hello from " + this.player.getUserId());
        lastUpdate = System.currentTimeMillis();
        textPaint = new Paint(Color.BLACK);
        textPaint.setTextSize(250);
    }

    //TODO: these pulls should be randomized, pulled from the server
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
     * Draws the {@link GameObjectSprite}s and {@link CardInHand}s on the provided canvas
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas){
        if(!gameOver){
            for(PlayedCard playedCard : playedCards.getPlayedCards()){
                    playedCard.draw(canvas);
            }
            for(CardInHand card : player.getHand()){
                card.draw(canvas);
            }
            player.draw(canvas);
        } else{
            if(this.wonOrLost){
                canvas.drawText("YOU WON", 0, Sprite.screenHeight / 2, textPaint);
            } else{
                canvas.drawText("YOU LOST", 0, Sprite.screenHeight / 2, textPaint);
            }

        }

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
     * Updates the card that is about to played (has been clicked on but not deployed)
     * @param index the index of this {@link GameManager}'s {@link Player}'s {@link CardInHand} to play
     * @param currentlyPlaying whether or not there is a {@link Card} in the process of being played
     */
    public void setPlayingCard(int index, boolean currentlyPlaying){
        this.cardToPlayIndex = index;
        if(index != -1){
            this.player.getCardInHand(cardToPlayIndex).setStatus(CardInHand.Status.PLACING);
        }
        this.isPlayingCard = currentlyPlaying;
    }

    /**
     * @return the {@link CardInHand} the manager is set to play
     */
    public CardInHand getPlayingCard(){
        return this.player.getCardInHand(cardToPlayIndex);
    }

    /**
     * Plays the {@link Card} represented by cardToPlayIndex.
     * @param eventX the X value of the event causing the card to be played
     * @param eventY the Y value of the event playing this card
     */
    public void playCard(int eventX, int eventY){
        try {
            Card toSend = new Card(player.getCardInHand(cardToPlayIndex).getCard());
            toSend.cardName = toSend.cardName + "@" + cardsSent++;
            SocketUtilities.sendMessage(JsonUtils.playedCardToJson(new PlayedCard(toSend, eventX, eventY, this.player.getUserId())).toString()  );
            player.setCurrentMana(player.getCurrentMana() - player.getCardInHand(cardToPlayIndex).getCardManaCost());
            player.getCardInHand(cardToPlayIndex).setStatus(CardInHand.Status.PLAYED);
        } catch (Exception e){
            Log.e("ERROR", "Encountered an error sending card over socket.");
        }

    }

    /**
     * @return true if a card is currently set to {@link CardInHand.Status} = Status.PLACING
     */
    public boolean isPlayingCard(){
        return isPlayingCard;
    }

    /**
     * @return the index of the {@link CardInHand} from the {@link Player}'s hand to play
     */
    public int getCardToPlayIndex(){
        return cardToPlayIndex;
    }

    /**
     * Sends a message to the game manager
     * @param message the message to send to the game manager
     */
    public void passMessageToManager(String message){
        if(message.contains("true") && !isConnected){
            Log.i("SOCKET_INFO", "Connected.");
            isConnected = true;
            if(message.contains("left")){
                playerSide = "left";
            } else if(message.contains("right")){
                playerSide = "right";
            }
            initializeDeck();
        } else if(message.contains("win")){
            Log.i("SOCKET_INFO", "GAME OVER: " + message);
            this.gameOver = true;
            this.wonOrLost = true;
        } else if(message.contains("loss")){
            Log.i("SOCKET_INFO", "GAME OVER: " + message);
            this.gameOver = true;
            this.wonOrLost = false;
        } else{
            try {
                if(message.contains("name")){
                    playedCards.addAll(JsonUtils.jsonToSocketMessage(message).getPlayedCards(), this);
                   // playedCards.addAll(JsonUtils.jsonToPlayedCardArray(message), this);
                    //If the player side hasn't already been updated, go through and check
                    if(!playerSideSet){
                        for(PlayedCard playedCard : playedCards.getPlayedCards()){
                            if(playedCard.getPlayer().equals(this.getPlayer().getUserId())
                                    && (playedCard.getCard().cardName.contains("tower4")
                                    || playedCard.getCard().cardName.contains("tower5")
                                    || playedCard.getCard().cardName.contains("tower6"))){
                                this.setPlayerSide("right");
                            }
                        }
                        //Either the side has been set to right, or the left-sided-ness of this Player has been confirmed
                        playerSideSet = true;
                        lastUpdate = System.currentTimeMillis();
                    }

                }
            } catch (Exception e){
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }
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
}
