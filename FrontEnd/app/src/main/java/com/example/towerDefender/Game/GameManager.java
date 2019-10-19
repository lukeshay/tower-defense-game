package com.example.towerDefender.Game;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.Card.CardUtilities;
import com.example.towerDefender.R;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.VolleyServices.JsonUtils;
//import com.example.towerDefender.SocketServices.WebSocketClientConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameManager handles all the {@link Player}s and {@link GameObjectSprite}s for the {@link GameView} to streamline code.
 */
public class GameManager {
    public static GameManager instance;
    private GameView gameView;
    private Player player;
    private List<GameObjectSprite> gameObjectSprites;
    private Turret[] turrets;
    //whether or not a card in the player's hand currently has status CardInHand.Status.PLACING
    private boolean isPlayingCard;
    //The index of the CardInHand to play from the player's CardInHand
    private int cardToPlayIndex;

    public GameManager(GameView gameView, Player player){
        instance = this;
        this.gameView = gameView;
        this.player = player;
        gameObjectSprites = new ArrayList<>();
        isPlayingCard = false;
        cardToPlayIndex = 0;
        initializeDeck();
        initializeTurrets();
        SocketUtilities.sendMessage("hello from the game manager");
//        socketConnection = new WebSocketClientConnection(this.player.getUserId());
    }

    //For testing purposes only!
    public GameManager(){
        this.gameView = null;
        this.player = new Player(null, new ArrayList<Card>());
        gameObjectSprites = new ArrayList<>();
        isPlayingCard = false;
        cardToPlayIndex = 0;
        initializeDeck();
    }

    public boolean isInitialized(){
        return instance != null;
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
        for(Turret turret : turrets){
            turret.draw(canvas);
        }
        player.draw(canvas);
        for(GameObjectSprite sprite : gameObjectSprites){
            sprite.draw(canvas);
        }
        for(CardInHand card : player.getHand()){
            card.draw(canvas);
        }
    }

    /**
     * Adds a {@link GameObjectSprite} to manage
     * @param gameObjectSprite the {@link GameObjectSprite} to add
     */
    public void addCharacter(GameObjectSprite gameObjectSprite){
        gameObjectSprites.add(gameObjectSprite);
    }

    /**
     * Updates the {@link GameObjectSprite}s and {@link CardInHand}s.
     */
    public void update(){
        player.update();
        for(GameObjectSprite gameObjectSprite : gameObjectSprites){
            gameObjectSprite.update();
        }
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
        SocketUtilities.sendMessage(JsonUtils.cardtoJson(player.getCardInHand(cardToPlayIndex).getCard()).toString());
        this.addCharacter(new GameObjectSprite(player.getCardInHand(cardToPlayIndex).getSprite().image, eventX, eventY));
        player.setCurrentMana(player.getCurrentMana() - player.getCardInHand(cardToPlayIndex).getCardManaCost());
        player.getCardInHand(cardToPlayIndex).setStatus(CardInHand.Status.PLAYED);
        //socketConnection.sendCardToPlay(player.getCardInHand(cardToPlayIndex).getCard(), eventX, eventY, this.player.getUserId());
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
     * Adds the opponent's played unit to this game's units
     */
    //TODO: play cards from socket messages
    public void addOpponentsCard(Card card){

    }

    public void initializeTurrets(){
        turrets = new Turret[6];
        turrets[0] = new Turret(BitmapFactory.decodeResource(this.player.getPlayerContext().getResources(),
                R.drawable.friendly_tower), 50, Sprite.screenHeight / 2 - 150);
        turrets[1] = new Turret(BitmapFactory.decodeResource(this.player.getPlayerContext().getResources(),
                R.drawable.friendly_tower),150, Sprite.screenHeight / 4 - 150);
        turrets[2] = new Turret(BitmapFactory.decodeResource(this.player.getPlayerContext().getResources(),
                R.drawable.friendly_tower),150, 3 * Sprite.screenHeight / 4 - 150);
        turrets[3] = new Turret(BitmapFactory.decodeResource(this.player.getPlayerContext().getResources(),
                R.drawable.enemy_tower),Sprite.screenWidth - 350, Sprite.screenHeight / 2 - 150);
        turrets[4] = new Turret(BitmapFactory.decodeResource(this.player.getPlayerContext().getResources(),
                R.drawable.enemy_tower),Sprite.screenWidth - 450, Sprite.screenHeight / 4 - 150);
        turrets[5] = new Turret(BitmapFactory.decodeResource(this.player.getPlayerContext().getResources(),
                R.drawable.enemy_tower), Sprite.screenWidth - 450, 3 * Sprite.screenHeight / 4 - 150);
    }

    /**
     * Sends a message to the game manager
     * @param message the message to send to the game manager
     */
    public void passMessageToManager(String message){
        Log.d("SOCKET_MESSAGE", message);
        //if(message.matches("\\{\"description\":.*,\"name\":.*,\"cost\":\\d+,\"damage\":\\d+,\"hitPoints\":\\d+,\"range\":\\d+,\"speed\":\\d+,\"type\":\".*\"}")){
        try{
            gameObjectSprites.add((GameObjectSprite)CardUtilities.getGameObjectSpriteForCard(this.player.getPlayerContext(), JsonUtils.jsonToCard(message), 0, 0));
        } catch(Exception e){
            // do nothing, the message was likely not a card
        }
    }
}
