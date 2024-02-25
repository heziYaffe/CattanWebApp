package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Building {

    protected static int id = -1;

    protected int ownerId;

    protected Pair<Integer, Integer> location; //t_id, v_id
    protected List<Tile> tiles;
    static HashMap<String, Integer> cost = new HashMap<>();

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public HashMap<String, Integer> getCost() {
        return cost;
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

    public int getId() {
        return id;
    }

    protected Pair<Integer, Integer> getLocation() {
        return location;
    }

    protected int getOwnerId() {
        return ownerId;
    }
}
