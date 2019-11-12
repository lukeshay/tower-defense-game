package com.example.towerDefender.VolleyServices;

import com.example.towerDefender.Card.Card;
import com.google.gson.annotations.SerializedName;

public class User implements Comparable<User>{
    @SerializedName("phoneId")
    public String phoneId;
    @SerializedName("userName")
    public String userName;
    @SerializedName("email")
    public String email;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("xp")
    public int xp;
    @SerializedName("trophies")
    public int trophyCount;
    @SerializedName("userType")
    public String userType;
    @SerializedName("ownedCards")
    public Card[] ownedCards;
    @SerializedName("deckNames")
    public String[] deckNames;

    /**
     * Constructs a new User
     * @param phoneId the user's phone id
     * @param userName the user's username
     * @param email the user's email
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param xp the user's current experience
     * @param trophyCount the user's trophy count
     * @param userType the type of user
     * @param ownedCards the cards this user owns
     * @param deckNames the names of the decks this user owns
     */
    public User(String phoneId, String userName, String email, String firstName, String lastName,
                int xp, int trophyCount, String userType, Card[] ownedCards, String[] deckNames){
        this.phoneId = phoneId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.xp = xp;
        this.trophyCount = trophyCount;
        this.userType = userType;
        this.ownedCards = ownedCards;
        this.deckNames = deckNames;
    }

    /**
     * Compares this {@link User} to another by comparing trophy count
     * @param other the other {@link User} to compare against
     * @return a negative number if this < other, zero if this == other, a positive number if this > other</>
     */
    @Override
    public int compareTo(User other){
        return this.trophyCount - other.trophyCount;
    }
}
