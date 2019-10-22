package com.example.towerDefender.Game;

import android.util.Log;

import com.example.towerDefender.Card.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.towerDefender.Card.CardUtilities;
import com.example.towerDefender.Card.PlayedCard;

/**
 * A wrapper class to house the game's played cards. Replaces adding playedCards by with updating based upon the existing card
 */
public class PlayedCardsHolder {
    private List<PlayedCard> playedCards;
    private List<GameObjectSprite> sprites;
    private Player player;

    public PlayedCardsHolder(List<PlayedCard> playedCards, Player player){
        this.playedCards = playedCards;
        this.sprites = new ArrayList<>();
        this.player = player;
    }

    /**
     * Adds the provided card to the holder, or updates it if it is a
     * @param playedCard the {@link PlayedCard} to addOrUpdate
     */
    public void addOrUpdate(PlayedCard playedCard, GameManager manager){
        int index = contains(playedCard);
        if(index != -1){
            playedCards.get(index).setxValue(playedCard.getxValue());
            playedCards.get(index).setyValue(playedCard.getyValue());
            playedCards.get(index).setHitPoints(playedCard.getHitPoints());
            //Log.d("SPRITES_HOLDER", "Did not add card, already existed in list without need to update");
        } else {
            playedCards.add(playedCard);
            if(playedCard.getPlayer().equals(manager.getPlayer().getUserId())){
                playedCard.setSprite(CardUtilities.getGameObjectSpriteForCard(manager.getPlayer().getPlayerContext(), playedCard.getCard(), playedCard.getxValue(), playedCard.getyValue()));
            } else {
                playedCard.setSprite(CardUtilities.getEnemySprite(manager.getPlayer().getPlayerContext(), playedCard.getCard(), playedCard.getxValue(), playedCard.getyValue()));
            }
        }
        //Log.d("SPRITES_HOLDER", "PlayedCardsHolder has " + playedCards.size() + " cards.");
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

    /**
     * @return the played cards in this PlayedCardsHolder
     */
    public Collection<PlayedCard> getPlayedCards(){
        return playedCards;
    }

    public void addAll(Collection<PlayedCard> cards, GameManager manager){
        for(PlayedCard card : cards){
            addOrUpdate(card, manager);
        }
    }

    public int contains(PlayedCard playedCard){
        for(int i = 0; i < playedCards.size(); i++){
            //if the card name is the same, we treat the played card as the same
            if(playedCards.get(i).getCard().cardName.equals(playedCard.getCard().cardName) && playedCards.get(i).getPlayer().equals(playedCard.getPlayer())){
                return i;
            } else {
                continue;
            }
        }
        return -1;

    }
}