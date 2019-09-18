package com.example.myapplication;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameManager handles all the {@link Player}s and {@link Character}s for the {@link GameView} to streamline code.
 */
public class GameManager {
    public GameManager instance;
    private GameView gameView;
    private Player player;
    private List<Character> characters;
    boolean isPlayingCard;
    int cardToPlay;

    public GameManager(GameView gameView){
        instance = this;
        this.gameView = gameView;
        player = new Player();
        characters = new ArrayList<>();
        isPlayingCard = false;
        cardToPlay = 0;
        initializeDeck();
    }

    //TODO: these pulls should be randomized, pulled from the server
    /**
     * Initializes the {@link Player}'s deck.
     */
    public void initializeDeck(){
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper2)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper2)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper2)));
        player.deck.add(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.reaper2)));
        player.drawHand();
    }

    public Player getPlayer(){
        return player;
    }

    /**
     * Draws the {@link Character}s and {@link CardInHand}s on the provided canvas
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas){
        for(Character sprite : characters){
            sprite.draw(canvas);
        }
        for(CardInHand card : player.hand){
            card.draw(canvas);
        }
    }

    /**
     * Adds a {@link Character} to manage
     * @param character the {@link Character} to add
     */
    public void addCharacter(Character character){
        characters.add(character);
    }

    /**
     * Updates the {@link Character}s and {@link CardInHand}s.
     */
    public void update(){
        for(Character character : characters){
            character.update();
        }
        for(CardInHand card :  getPlayer().hand){
            card.update();
        }
    }

    /**
     * Updates the card that is about to played (has been clicked on but not deployed)
     * @param index the index of this {@link GameManager}'s {@link Player}'s {@link CardInHand} to play
     * @param currentlyPlaying whether or not there is a {@link Card} in the process of being played
     */
    public void setPlayingCard(int index, boolean currentlyPlaying){
        this.cardToPlay = index;
        if(index != -1){
            this.player.hand[cardToPlay].setStatus(CardInHand.Status.PLACING);
        }
        this.isPlayingCard = currentlyPlaying;
    }

    /**
     * Plays the {@link Card} represented by cardToPlay.
     * @param eventX the X value of the event causing the card to be played
     * @param eventY the Y value of the event playing this card
     */
    public void playCard(int eventX, int eventY){
        if(player.hand[cardToPlay].getCard().cardType == Card.CardType.UNIT){
            this.addCharacter(new Character(player.hand[cardToPlay].getCard().sprite.image, eventX, eventY));
        }
        player.hand[cardToPlay].setStatus(CardInHand.Status.NOT_READY);
    }
}
