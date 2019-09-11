package com.pvptowerdefense.database.cards.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {
    @Id
    String name;
}
