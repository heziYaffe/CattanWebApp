package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map {
    private static TwoKeyHashMap<Integer, Pair<Integer, Integer>, Tile> tiles = new TwoKeyHashMap<>();


    {
        initialTiles();
    }

    private void initialTiles() {

        if (tiles.getAllValues().isEmpty()) {
            List<Integer> numbers = new ArrayList<>(List.of(2, 3, 3, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 10, 11, 11, 12));
            List<String> resources = new ArrayList<>(List.of("wood", "wood", "wood", "wood", "sheep", "sheep", "sheep", "sheep",
                    "wheat", "wheat", "wheat", "brick", "brick", "brick", "ore", "ore", "ore", "ore", "desert"));

            Collections.shuffle(numbers);
            Collections.shuffle(resources);

            int numRows = 5;
            int[] tilesPerRow = {3, 4, 5, 4, 3};


            int qOffset = -2;  // Adjust the starting q value
            int rOffset = -2;  // Adjust the starting r value

            int h_id = 0;
            for (int row = 0; row < numRows; row++) {
                int numTiles = tilesPerRow[row];

                for (int col = 0; col < numTiles; col++) {
                    Hexagon h = new Hexagon(qOffset + col, rOffset + row, h_id);
                    int number = -1;
                    String resource = resources.remove(0);
                    if (!"desert".equals(resource)) {
                        number = numbers.remove(0);
                    }

                    tiles.put(h_id, new Pair<>(qOffset + col, rOffset + row), new Tile(h, resource, number));
                    h_id++;
                }
            }

            for (Tile tile : tiles.getAllValues()) {
                tile.initNeighbors();
            }
            System.out.println(this);
        }

    }


    public static TwoKeyHashMap<Integer, Pair<Integer, Integer>, Tile> getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("TilesMap:\n");
        for (Tile tile : getTiles().getAllValues()) {
            result.append(tile.toString()).append("\n");
        }
        return result.toString();
    }

    // Implement any additional methods or properties as needed
}