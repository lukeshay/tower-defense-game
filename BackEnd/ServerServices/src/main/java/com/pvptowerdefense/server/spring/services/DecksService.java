package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.daos.DecksDao;
import com.pvptowerdefense.server.spring.models.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecksService {

    private DecksDao decksDao;

    @Autowired
    public DecksService(DecksDao decksDao){ this.decksDao = decksDao; }

    public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<Deck>();
        decksDao.findAll().forEach(decks::add);

        return decks;
    }
}
