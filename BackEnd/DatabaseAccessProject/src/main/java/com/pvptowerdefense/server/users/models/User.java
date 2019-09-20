package com.pvptowerdefense.server.users.models;

import com.pvptowerdefense.server.cards.models.Card;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

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
    private List<Deck> decks;



    private User() {}
    
     public User(String phoneId, String userName, String email, String firstName, String lastName, String userType) {
        this.phoneId = phoneId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.xp = 0;
        this.trophies = 0;
        setUserType(userType);
        this.ownedCards = new ArrayList<>();
        this.decks = new ArrayList<>();
     }

    public User(String phoneId, String userName, String email, String firstName, String lastName, int xp, int trophies, String userType, List<Card> ownedCards, List<Deck> decks) {
        this.phoneId = phoneId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.xp = xp;
        this.trophies = trophies;
        setUserType(userType);
        this.ownedCards = ownedCards;
        this.decks = decks;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserType() {
        return userType;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public void setUserType(String type){
        if(type.equals("Debugger") || type.equals("Admin") || type.equals("User")){
            this.userType = type;
        }
        else { throw new IllegalArgumentException("Invalid user type!"); }

    }
}
