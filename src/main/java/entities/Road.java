package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Road {
    static int counter = 0;

    int id;
    int owner_id;
    Pair<Integer, Integer> edge; // t_id, e_id

    int t_id;
    int v_id1;
    int v_id2;


    Pair<Pair<Integer, Integer>,Pair<Integer, Integer>> vertices; // t_id1, v_id1 -- t_id2, v_id2

    static HashMap<String, Integer> roadCost = new HashMap<>();;

    static {
        roadCost.put("wood", 1);
        roadCost.put("brick", 1);
    }
    public Road(int owner_id, Pair<Pair<Integer, Integer>,Pair<Integer, Integer>> vertecies) {
        this.id = ++counter;
        this.owner_id = owner_id;
        this.vertices = vertecies;
    }

    public Road(int owner_id, int t_id, int e_id) {
        this.id = ++counter;
        this.owner_id = owner_id;
        this.edge = new Pair<>(t_id, e_id);
        Pair<Integer, Integer> vertices = getVerticesOfEdge(e_id);
        this.t_id = t_id;
        this.v_id1 = vertices.getFirst();
        this.v_id2 = vertices.getSecond();
        //this.vertices = new Pair<>(new Pair<>(t_id, e_id), new Pair<>(t_id,));
    }

    public Road(int owner_id, int t_id, int startV_id, int endV_id) {
        this.id = ++counter;
        this.owner_id = owner_id;
        this.t_id = t_id;
    }



    public List<Pair<Integer, Integer>> getRoadsPossibilities() {

        List<Pair<Integer, Integer>> possibilities = new ArrayList<>();

        // get all te copies of the vertex
        List<Pair<Integer, Integer>> Copies1 = Map.getTiles()
                .getById(t_id)
                .getVertexCopies(v_id1);

        // add all the possibilities from the vertex of any copy
        for (Pair<Integer,Integer> copy : Copies1) {
            int t_id = copy.getFirst();
            int v_id = copy.getSecond();
            List<Integer> adjacentEdges1 = Map.getTiles().getById(t_id).getAdjacentEdgesToVertex(v_id);

            for (int e_id : adjacentEdges1) {
                possibilities.add(new Pair<>(t_id, e_id));
            }
        }

        System.out.println("copy1:" + possibilities);

        List<Integer> adjacentEdges2 = Map.getTiles()
                .getById(t_id)
                .getAdjacentEdgesToVertex(v_id1);


        // add all the possibilities from the vertex
        for (int e_id : adjacentEdges2) {
            possibilities.add(new entities.Pair<>(t_id, e_id));
        }

        System.out.println("adjacentEdges2:" + possibilities);




        List<Pair<Integer, Integer>> Copies2 = Map.getTiles()
                .getById(t_id)
                .getVertexCopies(v_id2);

        List<Integer> adjacentEdges3 = Map.getTiles()
                .getById(t_id)
                .getAdjacentEdgesToVertex(v_id2);


        for (int e_id : adjacentEdges3) {
            possibilities.add(new Pair<>(t_id, e_id));
        }


        System.out.println("adjacentEdges3:" + possibilities);


        for (Pair<Integer,Integer> copy : Copies2) {
            int t_id = copy.getFirst();
            int v_id = copy.getSecond();
            List<Integer> adjacentEdges4 = Map.getTiles().getById(t_id).getAdjacentEdgesToVertex(v_id);

            for (int e_id : adjacentEdges4) {
                possibilities.add(new Pair<>(t_id, e_id));
            }
        }

        System.out.println("copy 2:" + possibilities);





        return possibilities;
    }

    public HashMap<String, Integer> getCost() {
        return roadCost;
    }

    public Pair<Integer,Integer> getVerticesOfEdge(int edge) {
        if (edge == 6) {
            return new Pair<>(6,1);
        } else {
            return new Pair<>(edge, edge + 1);
        }
    }
}
