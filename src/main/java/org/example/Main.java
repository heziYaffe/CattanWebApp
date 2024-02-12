package org.example;

import entities.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        GameController gc = new GameController();
        SpringApplication.run(Main.class, args);

/*
        GameManager gm = new GameManager(2);



        List<Tile> tilesHex = Map.getTiles().getAllValues();

        // Sort the tiles based on their ID
        Collections.sort(tilesHex, new Comparator<Tile>() {
            @Override
            public int compare(Tile tile1, Tile tile2) {
                // Compare the IDs of the tiles
                return Integer.compare(tile1.getId(), tile2.getId());
            }
        });

        // Print the sorted tiles
        System.out.println("Sorted tiles:");
        for (Tile tile : tilesHex) {
            System.out.println(tile);
        }


        int cureectH_id = 0;
        System.out.println("\nHexagon Neighbors To: " + Map.getTiles().getById(cureectH_id));

        System.out.println("\nHexagon Neighbors:");
        for (int id : Map.getTiles().getById(cureectH_id).getNeighbors()) {
            Tile t = Map.getTiles().getById(id);
            System.out.println(t);
        }

        int v_id = 6;
        System.out.println("\nHexagons ids Neighbors to vertex " + v_id + " on hexagon with id: " + cureectH_id +  ":");
        for (Pair<Integer, Integer> p : Map.getTiles().getById(cureectH_id).getNeighborsToVertex(v_id)) {
            int h_id = p.getFirst();
            int nV_id = p.getSecond();
            Tile t = Map.getTiles().getById(h_id);
            System.out.println(t);
        }

        gm.runGame();
        Player p = gm.getPlayers()[0];
        System.out.println(p);
*/
    }


}