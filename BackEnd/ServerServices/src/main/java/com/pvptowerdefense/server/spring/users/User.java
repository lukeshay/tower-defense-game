package com.pvptowerdefense.server.spring.users;

import com.google.gson.Gson;
import com.pvptowerdefense.server.spring.cards.Card;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
	    xp = 0;
	    trophies = 0;
        setUserType(userType);
	    ownedCards = new ArrayList<>();
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
	 * Gets the user's user name
	 *
	 * @return username user name
	 */
	public String getUserName() {
        return userName;
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
	 * Gets the user's first name
	 *
	 * @return their first name
	 */
	public String getFirstName() {
        return firstName;
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
	 * Sets the user's type to the given type if valid
	 *
	 * @param type - type to be changed to
	 */
	private void setUserType(String type){
        if(type.equals("Debugger") || type.equals("Admin") || type.equals("User")){
	        userType = type;
        }
        else { throw new IllegalArgumentException("Invalid user type!"); }

    }

    @Override
	public String toString() {
		return new Gson().toJson(this);
    }
}
