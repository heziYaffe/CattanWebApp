package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Settlement {

    int id;

    Pair<Integer, Integer> location; //t_id, v_id
    List<Tile> tiles;

    static HashMap<String, Integer> settlementCost = new HashMap<>();;

    static {
        settlementCost.put("wood", 1);
        settlementCost.put("brick", 1);
        settlementCost.put("wheat", 1);
        settlementCost.put("sheep", 1);
    }
    public Settlement(int t_id, int v_id) {
        tiles = new ArrayList<>();
        location = new Pair<>(t_id, v_id);
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

    public List<Pair<Integer, Integer>> getRoadsPossibilities() {
        List<Pair<Integer, Integer>> possibilities = new ArrayList<>();
        List<Pair<Integer, Integer>> Copies = Map.getTiles()
                .getById(location.getFirst())
                .getVertexCopies(location.getSecond());

        List<Integer> adjacentEdges1 = Map.getTiles()
                .getById(this.location.getFirst())
                .getAdjacentEdgesToVertex(this.location.getSecond());

        for (int e_id : adjacentEdges1) {
            possibilities.add(new Pair<>(this.location.getFirst(), e_id));
        }

        for (Pair<Integer,Integer> copy : Copies) {
            int t_id = copy.getFirst();
            int v_id = copy.getSecond();
            List<Integer> adjacentEdges2 = Map.getTiles().getById(t_id).getAdjacentEdgesToVertex(v_id);

            for (int e_id : adjacentEdges2) {
                possibilities.add(new Pair<>(t_id, e_id));
            }
        }




        return possibilities;
    }
}
