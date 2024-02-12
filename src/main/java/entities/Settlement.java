package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Settlement {

    int id;
    List<Tile> tiles;

    static HashMap<String, Integer> settlementCost = new HashMap<>();;

    static {
        settlementCost.put("wood", 1);
        settlementCost.put("brick", 1);
        settlementCost.put("wheat", 1);
        settlementCost.put("sheep", 1);
    }
    public Settlement() {
        tiles = new ArrayList<>();
    }
    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public HashMap<String, Integer> getCost() {
        return settlementCost;
    }
}
