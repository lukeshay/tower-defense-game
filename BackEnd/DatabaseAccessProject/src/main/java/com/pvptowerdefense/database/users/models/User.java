package com.pvptowerdefense.database.users.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    String userName;

}
