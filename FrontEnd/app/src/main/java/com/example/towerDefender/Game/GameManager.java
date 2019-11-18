package com.example.towerDefender.Game;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.Collator;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.R;
import com.example.towerDefender.SocketServices.SocketMessage;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.Util.CanvasUtility;
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
    private int cardsSent = 0;
    private boolean gameOver = false;
    private Paint textPaint;
    private boolean playerSideSet = false;
    private boolean wonOrLost = false; // true if they won
    private Sprite closeButton;
    private Canvas canvas; //stored canvas so we can scale cards we played

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
        textPaint = new Paint(Color.BLACK);
        textPaint.setTextSize(150);
        textPaint.setColor(Color.WHITE);
        closeButton =new BackButton(BitmapFactory.decodeResource(player.getPlayerContext().getResources(), R.drawable.back_button));
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
        this.canvas = canvas;
        if(isConnected && !gameOver){ // in game
            closeButton.draw(canvas);
            for(PlayedCard playedCard : playedCards.getPlayedCards()){
                    playedCard.draw(canvas);
            }
            for(CardInHand card : player.getHand()){
                card.draw(canvas);
            }
            player.draw(canvas);
            CanvasUtility.drawChatPrompt(canvas, textPaint);
        } else if(!isConnected){ // waiting for game to start
            CanvasUtility.drawCenteredText(canvas, "Connected. Waiting for game start.", textPaint);
        } else { //game has ended
            if(this.wonOrLost){
                CanvasUtility.drawCenteredText(canvas, "You won!", textPaint);
            } else{
                CanvasUtility.drawCenteredText(canvas, "You lost!", textPaint);
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
            SocketUtilities.sendMessage(JsonUtils.playedCardToJson(new PlayedCard(toSend,
                    CanvasUtility.convertCanvasPositionToServerPosition(canvas, eventX), eventY, this.player.getUserId())).toString()  );
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
        SocketMessage socketMessage = JsonUtils.jsonToSocketMessage(message);
        if(!socketMessage.getWinner().trim().isEmpty()){
            if(socketMessage.getWinner().equals(this.getPlayer().getUserId())){
                this.gameOver = true;
                this.wonOrLost = true;
            } else {
                this.gameOver = true;
                this.wonOrLost = false;
            }
        }
        if(socketMessage.getGameState().equals("in-game") && !isConnected){
            Log.i("SOCKET_INFO", "Connected.");
            isConnected = true;
            initializeDeck();
        } else{
            try {
                playedCards.addAll(socketMessage.getPlayedCards(), this);
                //If the player side hasn't already been updated, go through and check
                if(!playerSideSet){
                    if(socketMessage.getPlayerOneId().equals(this.getPlayer().getUserId())){
                        playerSide = "left";
                    } else{
                        playerSide = "right";
                    }
                    playerSideSet = true;
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
