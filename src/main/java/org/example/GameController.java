package org.example;


import entities.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Map;

@RestController

public class GameController {

    private GameManager gm = new GameManager(1);


    @CrossOrigin
    @PostMapping("/endTurn")
    public ResponseEntity<java.util.Map<String, String>> endTurn() {
        // Perform end game logic here
        System.out.println("Old Turn is " + this.gm.getTurn());
        gm.endTurn();
        System.out.println("New Turn is " + this.gm.getTurn());
        gm.notifyTurnChange();
        java.util.Map<String, String> response = new HashMap<>();

        response.put("turn", String.valueOf(this.gm.getTurn()));

        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @PostMapping("/notify")
    public ResponseEntity<java.util.Map<String, String>> handleNotification(@RequestBody Map<String, String> payload) {
        // Handle the notification logic here
        System.out.println("Received notification from frontend: " + payload);
        String command = payload.get("command");
        payload.remove("command");

        boolean b = gm.handleInputFromClients(command, payload);

        // You can return a JSON response with a success message
        java.util.Map<String, String> response = new HashMap<>();

        response.put("message", "Notification received successfully");
        response.put("returnValue", Boolean.toString(b));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }



    /*
    @CrossOrigin
    @PostMapping("/rollDice")
    public ResponseEntity<Map<String, String>> handleRollingDice(@RequestBody Map<String, Object> payload) {
        // Access data from the payload directly
        int dice1 = (int) payload.get("dice1");
        int dice2 = (int) payload.get("dice2");
        gm.distributeResources(dice1 + dice2);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Notification received successfully");
        // Add logic for notify the front about every player new resources
        // (after distribute)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
     */

    @CrossOrigin
    @PostMapping("/card")
    public ResponseEntity<Map<String, String>> handleCardEvents(@RequestBody String payload) {
        // Handle the notification logic here
        System.out.println("Received notification from frontend: " + payload);

        Card c = gm.drawCard();

        // Add logic for notify the front about player's new card

        Map<String, String> response = new HashMap<>();
        response.put("message", "Notification received successfully");
        response.put("card", c.getName());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/tiles")
    public ResponseEntity<List<Tile>> getTiles() {
        try {
            TwoKeyHashMap<Integer, Pair<Integer, Integer>, Tile> tilesDict = gm.getTiles();
            List<Tile> tiles = tilesDict.getAllValues();
            // Sort the tiles based on their ID
            tiles.sort(Comparator.comparingInt(Tile::getId));
            System.out.println(tiles);

            //System.out.println(gm.getMap());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(tiles, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
