package com.pvptowerdefense.server.decks;

import com.pvptowerdefense.server.decks.models.Deck;
import com.pvptowerdefense.server.decks.services.DecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/decks")
public class DecksController {

    private DecksService decksService;

    @Autowired
    public DecksController(final DecksService autowiredDecksService){
        this.decksService = autowiredDecksService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<Deck> getAllDecks(){
        return decksService.getAllDecks();
    }
}
