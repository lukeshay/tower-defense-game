package com.pvptowerdefense.database.users.models;

import com.pvptowerdefense.database.cards.models.Card;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "PHONE_ID", nullable = false)
    private String phoneId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(unique=true)
    @NotEmpty
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private int xp;

    @NotEmpty
    private int trophies;

    @ElementCollection
    private List<Card> ownedCards;

    @ElementCollection
    private List<Card> currentDeck;

    private User() {}

    public User(String userName, String phoneId, String firstName, String lastName, String email, int xp, int trophies) {
        this.userName = userName;
        this.phoneId = phoneId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.xp = xp;
        this.trophies = trophies;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }

    public void addOwnedCard(Card card) {
        ownedCards.add(card);
    }

    public List<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(List<Card> currentDeck) {
        this.currentDeck = currentDeck;
    }

    public void addToCurrentDeck(Card card) {
        currentDeck.add(card);
    }
}
