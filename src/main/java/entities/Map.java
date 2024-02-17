package entities;

import java.util.*;

public class Map {
    private static TwoKeyHashMap<Integer, Pair<Integer, Integer>, Tile> tiles = new TwoKeyHashMap<>();


    {
        initialTiles();
    }

    private Pair<Integer, Integer> getQAndR(int id) {
        switch (id) {
            case 0:
                return new Pair<>(0, -2);
            case 1:
                return new Pair<>(1, -2);
            case 2:
                return new Pair<>(2, -2);
            case 3:
                return new Pair<>(-1, -1);
            case 4:
                return new Pair<>(0, -1);
            case 5:
                return new Pair<>(1, -1);
            case 6:
                return new Pair<>(2, -1);
            case 7:
                return new Pair<>(-2, 0);
            case 8:
                return new Pair<>(-1, 0);
            case 9:
                return new Pair<>(0, 0);
            case 10:
                return new Pair<>(1, 0);
            case 11:
                return new Pair<>(2, 0);
            case 12:
                return new Pair<>(-2, 1);
            case 13:
                return new Pair<>(-1, 1);
            case 14:
                return new Pair<>(0, 1);
            case 15:
                return new Pair<>(1, 1);
            case 16:
                return new Pair<>(-2, -2);
            case 17:
                return new Pair<>(-1, -2);
            case 18:
                return new Pair<>(0, 2);
            default:
                return new Pair<>(-99999, -99999);
        }
    }

    private boolean checkIfEveryTileQAndRAppearOnlyOnce() {
        Set<Pair<Integer, Integer>> pairSet = new HashSet<>();

        // Iterate over all IDs
        for (int id = 0; id <= 18; id++) {
            // Call the function for the current ID and add the returned pair to the set
            Pair<Integer, Integer> pair = getQAndR(id);
            pairSet.add(pair);
        }

        // Check if every pair appears only once
        if (pairSet.size() == 19) {
            System.out.println("Every value appears only once.");
            return true;
        } else {
            System.out.println("Some values appear more than once.");
            return false;
        }
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

            int h_id = 0;
            for (int row = 0; row < numRows; row++) {
                int numTiles = tilesPerRow[row];

                for (int col = 0; col < numTiles; col++) {
                    Pair<Integer, Integer> qAndR = getQAndR(h_id);
                    int q = qAndR.getFirst();
                    int r = qAndR.getSecond();
                    Hexagon h = new Hexagon(q, r, h_id);
                    int number = -1;
                    String resource = resources.remove(0);
                    if (!"desert".equals(resource)) {
                        number = numbers.remove(0);
                    }

                    tiles.put(h_id, qAndR, new Tile(h, resource, number));
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