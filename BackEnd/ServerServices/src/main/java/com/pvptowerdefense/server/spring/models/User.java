package com.pvptowerdefense.server.spring.models;

import com.pvptowerdefense.server.spring.models.Card;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


/**
 * The type User.
 */
/*
 * User type class
 */
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
    private List<String> deckNames;

    /*
     * Creates a new empty user
     */
    private User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param phoneId   the phone id
     * @param userName  the user name
     * @param email     the email
     * @param firstName the first name
     * @param lastName  the last name
     * @param userType  the user type
     */
    /*
     * Creates a new User without initial set lup of trophies or xp or decks/cards
     */
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
        this.deckNames = new ArrayList<>();
    }

    /**
     * Instantiates a new User.
     *
     * @param phoneId    the phone id
     * @param userName   the user name
     * @param email      the email
     * @param firstName  the first name
     * @param lastName   the last name
     * @param xp         the xp
     * @param trophies   the trophies
     * @param userType   the user type
     * @param ownedCards the owned cards
     * @param deckNames  the deck names
     */
    /*
     * Creates a new users that completely fills all of the User's initial variables
     */
    public User(String phoneId, String userName, String email,
                String firstName, String lastName, int xp, int trophies,
                String userType, List<Card> ownedCards,
                List<String> deckNames) {
        this.phoneId = phoneId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.xp = xp;
        this.trophies = trophies;
        setUserType(userType);
        this.ownedCards = ownedCards;
        this.deckNames = deckNames;
    }

    /**
     * Gets phone id.
     *
     * @return the phone id
     */
    /*
     * Gets the user's phoneId
     * @return user's phoneId
     */
    public String getPhoneId() {
        return phoneId;
    }


    /**
     * Sets the the user's phoneId to the desired id
     *
     * @param phoneId - phoneId to be changed to
     */
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    /**
     * Gets the user's user name
     *
     * @return username user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's username to the desired username
     *
     * @param userName - username to be changed to
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's email address
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address to the desired email address
     *
     * @param email - email to be changed to
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's first name
     *
     * @return their first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name to the desired first name
     *
     * @param firstName - name to be changed to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name
     *
     * @return their last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name to the desired last name
     *
     * @param lastName - last name to be changed to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's current xp value
     *
     * @return the xp value
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the user's xp to the desired xp value
     *
     * @param xp - xp value to be set
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Gets the user's current trophy count
     *
     * @return the trophy count
     */
    public int getTrophies() {
        return trophies;
    }

    /**
     * Sets the user's trophy count to the desired trophy count
     *
     * @param trophies - trophy count to be set
     */
    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    /**
     * Gets the user's current type
     *
     * @return the user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Gets the list of the user's cards
     *
     * @return list of cards
     */
    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    /**
     * Sets the user's list of cards to the desired list
     *
     * @param ownedCards - list of cards to be changed to
     */
    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }

    /**
     * Gets the list of the user's deck names
     *
     * @return list of deck names
     */
    public List<String> getDeckNames() {
        return deckNames;
    }

    /**
     * Sets the user's list of deck names to the desired names
     *
     * @param deckNames - list of names to be changed to
     */
    public void setDeckNames(List<String> deckNames) {
        this.deckNames = deckNames;
    }

    /**
     * Sets the user's type to the given type if valid
     *
     * @param type - type to be changed to
     */
    public void setUserType(String type){
        if(type.equals("Debugger") || type.equals("Admin") || type.equals("User")){
            this.userType = type;
        }
        else { throw new IllegalArgumentException("Invalid user type!"); }

    }
}
