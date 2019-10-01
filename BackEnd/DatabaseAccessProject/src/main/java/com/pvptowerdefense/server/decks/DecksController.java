package com.pvptowerdefense.server.decks;

import com.pvptowerdefense.server.decks.models.Deck;
import com.pvptowerdefense.server.decks.services.DecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(method = RequestMethod.POST, value = "/load")
    public void loadPresetDecksToDatabase(){
        decksService.loadPresetDecksToDatabase();
        successMap();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{deckId}")
    public void deleteDeckById(@PathVariable String deckId){
        decksService.deleteDeckById(deckId);

    }

    public Map<String, Boolean> successMap(){
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return map;
    }


}
