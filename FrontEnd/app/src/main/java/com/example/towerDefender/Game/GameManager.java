package com.example.towerDefender.Game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.VolleyServices.JsonUtils;
//import com.example.towerDefender.SocketServices.WebSocketClientConnection;

import java.util.ArrayList;
import java.util.Collection;

import com.example.towerDefender.Card.PlayedCard;

/**
 * The GameManager handles all the {@link Player}s and {@link GameObjectSprite}s for the {@link GameView} to streamline code.
 */
public class GameManager {
    public static GameManager instance;
    private GameView gameView;
    private Player player;
    private PlayedCardsHolder playedCards;
    //whether or not a card in the player's hand currently has status CardInHand.Status.PLACING
    private boolean isPlayingCard;
    //The index of the CardInHand to play from the player's CardInHand
    private int cardToPlayIndex;
    private long lastUpdate;
    private int cardsSent = 0;

    public GameManager(GameView gameView, Player player){
        this.gameView = gameView;
        this.player = player;
        playedCards = new PlayedCardsHolder(new ArrayList<PlayedCard>(), this.player);
        isPlayingCard = false;
        cardToPlayIndex = 0;
        initializeDeck();
        SocketUtilities.sendMessage("Hello from " + this.player.getUserId());
        lastUpdate = System.currentTimeMillis();
    }

    //TODO: these pulls should be randomized, pulled from the server
    /**
     * Initializes the {@link Player}'s deck.
     */
    public void initializeDeck(){
        player.drawHand();
    }

    public Player getPlayer(){
        return player;
    }

    /**
     * Draws the {@link GameObjectSprite}s and {@link CardInHand}s on the provided canvas
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas){
        for(PlayedCard playedCard : playedCards.getPlayedCards()){
            playedCard.draw(canvas);
        }
        for(CardInHand card : player.getHand()){
            card.draw(canvas);
        }
        player.draw(canvas);
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
        if(System.currentTimeMillis() - lastUpdate >= 250){
            lastUpdate = System.currentTimeMillis();
            //Update once a second
            if(message.contains("connected=true")){
                Log.i("SOCKET_INFO", "Connected.");
            } else {
                try {
                    //If message is not formatted yet it will be an array and contain "PlayedCard"
                    if(message.contains("PlayedCard")){
                        Collection<PlayedCard> playedCards = JsonUtils.socketCardsToPlayedCards(message);
                        for(PlayedCard playedCard : playedCards){
                            this.playedCards.addOrUpdate(playedCard, this);
                        }
                    } else if(message.contains("name")){
                        playedCards.addAll(JsonUtils.jsonToPlayedCardArray(message), this);
                    }

                } catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                    e.printStackTrace();
                }
            }
        }


    }

}
