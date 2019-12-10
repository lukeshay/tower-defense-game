package com.example.towerDefender.Game;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.PlayedCard;

import junit.framework.TestCase;

import org.junit.Assert;
import org.mockito.exceptions.base.MockitoException;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayedCardsHolderTests extends TestCase {

    private PlayedCardsHolder holder;

    public ArrayList<PlayedCard> setup(){
        ArrayList<PlayedCard> cards = new ArrayList<>();
        cards.add(new PlayedCard(new Card("Reaper", "basicReaper", 5, 5, 5, 5, "UNIT", 5), 5, 5, "testUser"));
        cards.add(new PlayedCard(new Card("Reaper2", "basicReaper", 5, 5, 5, 5, "UNIT", 5), 5, 5, "testUser"));
        cards.add(new PlayedCard(new Card("Reaper3", "basicReaper", 5, 5, 5, 5, "UNIT", 5), 5, 5, "testUser"));
        cards.add(new PlayedCard(new Card("Reaper", "basicReaper", 5, 5, 5, 5, "UNIT", 5), 5, 5, "testEnemy"));
        cards.add(new PlayedCard(new Card("Reaper2", "basicReaper", 5, 5, 5, 5, "UNIT", 5), 5, 5, "testEnemy"));
        return cards;
    }

    public void testUpdateCards(){
        Player player = mock(Player.class);
        GameManager manager = mock(GameManager.class);
        when(player.getUserId()).thenReturn("testUser");
        holder = new PlayedCardsHolder(setup());
        Assert.assertEquals(5, holder.getPlayedCards().size());
        holder.addOrUpdate(new PlayedCard(new Card("Reaper2", "basicReaper", 5, 5, 5, 5, "UNIT", 5), 15, 15, "testUser"), manager);
        Assert.assertEquals("Adding a card with the same name as another should NOT add a new card, simply update the old one. ",5, holder.getPlayedCards().size());
    }

    public void testAddCardNewToBoth(){
        Player player = mock(Player.class);
        GameManager manager = mock(GameManager.class);
        when(player.getUserId()).thenReturn("testUser");
        when(manager.getPlayer()).thenReturn(player);
        when(manager.getPlayerSide()).thenReturn("left");
        when(manager.getPlayer().getPlayerContext()).thenThrow(new MockitoException("Started to initialize new sprite!"));
        holder = new PlayedCardsHolder(setup());
        Assert.assertEquals(5, holder.getPlayedCards().size());
        try{
            holder.addOrUpdate(new PlayedCard(new Card("ReaperKing", "reaper", 5, 5, 5, 5, "UNIT", 5), 15, 15, "testUser"), manager);
        }catch(Exception e){
            if(e.getMessage().contains("Started to initialize a new sprite!")){
                System.out.println("Started making an new sprite instead of updating an old one. ");
                //TODO: find a better solution than catching an exception
                //Assert.assertEquals("A card with a new name should be ADDED, not updated!",5, holder.getPlayedCards().size());
            }
        }
    }

    public void testAddCardNewToEnemy(){
        Player player = mock(Player.class);
        GameManager manager = mock(GameManager.class);
        when(player.getUserId()).thenReturn("testUser");
        when(manager.getPlayer()).thenReturn(player);
        when(manager.getPlayerSide()).thenReturn("left");
        when(manager.getPlayer().getPlayerContext()).thenThrow(new MockitoException("Started to initialize new sprite!"));
        holder = new PlayedCardsHolder(setup());
        Assert.assertEquals(5, holder.getPlayedCards().size());
        try{
            holder.addOrUpdate(new PlayedCard(new Card("Reaper3", "reaper", 5, 5, 5, 5, "UNIT", 5), 15, 15, "testEnemy"), manager);
        }catch(Exception e){
            if(e.getMessage().contains("Started to initialize a new sprite!")){
                //TODO: find a better solution than catching an exception
                Assert.assertEquals("A card with a new name should be ADDED, not updated!",5, holder.getPlayedCards().size());
            }
        }
    }

}
