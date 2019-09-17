package com.pvptowerdefense.database.users.models;

import com.pvptowerdefense.database.cards.models.Card;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true, name = "PHONE_ID", nullable = false)
    private String phoneId;

    @Column(unique = true, name = "USER_NAME", nullable = false)
    private String userName;

    @Column(unique=true, name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "XP", nullable = false)
    private int xp;

    @Column(name = "TROPHIES", nullable = false)
    private int trophies;

    @Column(name = "USER_TYPE", nullable = false)
    private String userType;

    @ElementCollection
    private List<Card> ownedCards;

    @ElementCollection
    private List<Card> currentDeck;

    private User() {}

    public User(String userName, String phoneId, String firstName, String lastName, String email, int xp, int trophies, String userType) {
        this.userName = userName;
        this.phoneId = phoneId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.xp = xp;
        this.trophies = trophies;
        setUserType(userType);
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

    public String getUserType(){ return userType; }

    public void setUserType(String type){
        if(type.equals("Debugger") || type.equals("Admin") || type.equals("User")){
            this.userType = type;
        }
        else { throw new IllegalArgumentException("Invalid user type!"); }

    }
}
