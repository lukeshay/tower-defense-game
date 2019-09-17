package com.example.myapplication;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameManager handles all the {@link Player}s and {@link CharacterSprite}s for the {@link GameView} to streamline code.
 */
public class GameManager {
    private GameView gameView;
    private Player player;
    private List<CharacterSprite> characters;
    private boolean isPlacingSprite;
    private int spriteToPlace;

    public GameManager(GameView gameView){
        this.gameView = gameView;
        player = new Player();
        characters = new ArrayList<>();
        isPlacingSprite = false;
        spriteToPlace = 0;
        initializeDeck();
    }

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

    public void draw(Canvas canvas){
        for(CharacterSprite sprite : characters){
            sprite.draw(canvas);
        }
        for(CardInHand card : player.hand){
            card.draw(canvas);
        }
    }

    public void addCharacter(CharacterSprite character){
        characters.add(character);
    }

    public void update(){
        for(CharacterSprite character : characters){
            character.update();
        }
        for(CardInHand card :  getPlayer().hand){
            card.update();
        }
    }
}
