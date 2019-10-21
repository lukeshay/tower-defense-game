package com.example.towerDefender.Game;

import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shared.PlayedCard;

/**
 * A wrapper class to house the game's played cards. Replaces adding playedCards by with updating based upon the existing card
 */
public class GameObjectSpritesHolder {
    private List<PlayedCard> playedCards;
    private List<GameObjectSprite> sprites;
    private Player player;
    public GameObjectSpritesHolder(List<PlayedCard> playedCards, Player player){
        this.playedCards = playedCards;
        this.sprites = new ArrayList<>();
        this.player = player;
    }

    public void add(PlayedCard playedCard){
        if(playedCards.contains(playedCard)){
            Log.d("SPRITES_HOLDER", "Did not add card, already existed in list without need to update");
        } else {
            //the card exists, but its position has changed
            if(removePlayedCardByCard(playedCard.getCard(), playedCard.getPlayer())){
                Log.d("SPRITES_HOLDER", "Updated card");
            }
            removePlayedCardByCard(playedCard.getCard(), playedCard.getPlayer());
            playedCards.add(playedCard);
            if(!playedCard.getPlayer().contains(this.player.getUserId())){
                sprites.add(CardUtilities.getEnemySprite(this.player.getPlayerContext(), playedCard.getCard(), playedCard.getxValue(), playedCard.getyValue()));
            } else{
                sprites.add(CardUtilities.getGameObjectSpriteForCard(this.player.getPlayerContext(), playedCard.getCard(), playedCard.getxValue(), playedCard.getyValue()));
            }
        }
        Log.d("SPRITES_HOLDER", "SpritesHolder has " + sprites.size() + " sprites.");
    }

    public List<Card> getWrappedCards(){
        ArrayList<Card> wrappedCards = new ArrayList<>();
        for(PlayedCard playedCard : playedCards){
            wrappedCards.add(playedCard.getCard());
        }
        return wrappedCards;
    }

    /**
     * Removes the {@link PlayedCard} that shares the base {@link Card} and player
     * @param card the card to remove all {@link PlayedCard}s for
     * @return true if a card was removed
     */
    public boolean removePlayedCardByCard(Card card, String player){
        for(int i = 0; i < playedCards.size(); i++){
            //the same card can exist twice: once for each player
            if(playedCards.get(i).getCard().equals(card) && playedCards.get(i).getPlayer().equals(player)){
                playedCards.remove(i);
                return true;
            }
        }
        return false;
    }

    public void clearSprites(){
        sprites.clear();
    }

    /**
     * @return the played cards in this GameObjectSpritesHolder
     */
    public Collection<PlayedCard> getPlayedCards(){
        return playedCards;
    }

    public Collection<GameObjectSprite> getSprites(){
        return sprites;
    }

    public void addAll(Collection<PlayedCard> cards){
        for(PlayedCard card : cards){
            add(card);
        }
    }
}
