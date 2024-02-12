package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Hexagon {

    int id;
    private int q;
    private int r;

    List<Integer> availableVerticiesForCities = new ArrayList<>();
    List<Integer> availableEdgesForRoads = new ArrayList<>();

    private static int maxR = 2;
    private static int maxQ = 2;
    private static int minR = -2;
    private static int minQ = -2;


    public Hexagon(int q, int r, int id) {
        this.q = q;
        this.r = r;
        this.id = id;
        for (int i = 1; i < 7; i++) {
            availableVerticiesForCities.add(i);
            availableEdgesForRoads.add(i);
        }

    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public int getId() {
        return id;
    }


    private List<Pair<Integer, Integer>> getVertexCopies(int v_id) {
        List<Pair<Integer, Integer>> copies = this.getNeighborsToVertex(v_id);
        /*System.out.println(this);
        System.out.println(copies);*/
        return copies;

    }
    public void removeVertexCopies(int v_id) {
        List<Pair<Integer, Integer>> copies = this.getVertexCopies(v_id);
        System.out.println("copies:" + copies);
        for (Pair<Integer, Integer> copy : copies) {
            int nT_id = copy.getFirst();  // tile id
            int nV_id = copy.getSecond(); // tile vertex id

            Tile t = Map.getTiles().getById(nT_id);
            t.removeAvailableVertex(nV_id);
        }
    }

    public List<Integer> getAdjacentVertices(int v_id) {
        List<Integer> adjacent = new ArrayList<>();
        if (v_id == 1) {
            adjacent.add(6);
            adjacent.add(2);
        } else if (v_id == 6) {
            adjacent.add(1);
            adjacent.add(5);
        } else {
            adjacent.add(v_id +1);
            adjacent.add(v_id -1);
        }
        return adjacent;
    }


    /*
    public void removeAdjacentVertices(int v_id) {

        if (v_id == 1) {
            final int adjacent_v_id1 = 6;
            final int adjacent_v_id2 = 2;
            removeAvailableVertex(adjacent_v_id1);
            removeAvailableVertex(adjacent_v_id2);

        } else if (v_id == 6) {
            final int adjacent_v_id1 = 1;
            final int adjacent_v_id2 = 5;
            removeAvailableVertex(adjacent_v_id1);
            removeAvailableVertex(adjacent_v_id2);

        } else {
            final int adjacent_v_id1 = v_id + 1;
            final int adjacent_v_id2 = v_id - 1;
            removeAvailableVertex(adjacent_v_id1);
            removeAvailableVertex(adjacent_v_id2);
        }
    }
    */
    public void removeAvailableVertex(int v_id) {
        this.availableVerticiesForCities.removeIf(vertex -> vertex == v_id);
    }

    public void removeVerticesAfterBuildingCity(int v_id) {

        //remove vertex and his copies
        removeAvailableVertex(v_id);
        removeVertexCopies(v_id);

        List<Pair<Integer, Integer>> copies = getVertexCopies(v_id);

        for (Pair<Integer, Integer> copy : copies) {
            int nT_id = copy.getFirst();  // tile id
            int nV_id = copy.getSecond(); // tile vertex id
            Tile t = Map.getTiles().getById(nT_id);
            List<Integer> adjacentVertices = t.getAdjacentVertices(nV_id);
            for (int id : adjacentVertices) {
                t.removeAvailableVertex(id);
                t.removeVertexCopies(id);
            }

        }
        /*
        //remove adjacent vertices in the same tiles and their copies
        List<Integer> adjacentVertices = this.getAdjacentVertices(v_id);
        System.out.println("Adjacent vertices: " + adjacentVertices);
        for (int id : adjacentVertices) {
            removeAvailableVertex(id);
            removeVertexCopies(id);
        }

         */


        /*
        List<Pair<Integer, Integer>> neighborsVertex = this.getNeighborsToVertex(v_id);
        for (Pair<Integer, Integer> pair : neighborsVertex) {

            int nT_id = pair.getFirst();  // tile id
            int nV_id = pair.getSecond(); // tile vertex id
            Tile t = Map.getTiles().getById(nT_id);
            List<Integer> vIds = t.getAdjacentVertices(nV_id);
            for (int id : vIds) {
                t.removeAvailableVertex(id);
                t.removeVertexCopies(id);
            }
        }

         */



        /*List<Pair<Integer, Integer>> neighborsVertex = this.getNeighborsToVertex(v_id);
        for (Pair<Integer, Integer> pair : neighborsVertex) {

            int nT_id = pair.getFirst();  // tile id
            int nV_id = pair.getSecond(); // tile vertex id
            Tile t = Map.getTiles().getById(nT_id);
            List<Integer> vIds = t.getAdjacentVertices(nV_id);
            for (int id : vIds) {
                removeAvailableVertex(id);
            }
        }*/


    }

    public boolean checkIfLocationIsValid(String s, int id) {
        boolean b = false;
        switch (s) {
            case "edge":
                b = availableEdgesForRoads.contains(id);
                break;
            case "vertex":
                b = availableVerticiesForCities.contains(id);
                break;
            default:
                break;
        }
        return b;
    }


    public List<Pair<Integer, Integer>> getNeighborsToVertex(int v_id) {
        List<Pair<Integer, Integer>> v_idOfnNeighbors = new ArrayList<>();

        List<Integer> neighbors = this.getNeighbors();
        switch (v_id) {
            case 1:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    /*if (t.getQ() == this.q - 1 && t.getR() == this.r - 1) { //upper left
                        //v_idOfnNeighbors.add(new Pair<>(id, 3));
                        v_idOfnNeighbors.add(new Pair<>(id, 5));
                    } else if (t.getQ() == this.q - 1 && t.getR() == this.r) { //lower right
                        //v_idOfnNeighbors.add(new Pair<>(id, 5));
                        v_idOfnNeighbors.add(new Pair<>(id, 3));

                    }*/
                    if (t.getQ() == this.q - 1 && t.getR() == this.r) { //left
                        //v_idOfnNeighbors.add(new Pair<>(id, 5));
                        v_idOfnNeighbors.add(new Pair<>(id, 3));

                    } else if (t.getQ() == this.q - 1 && t.getR() == this.r - 1) { //upper left
                        //v_idOfnNeighbors.add(new Pair<>(id, 3));
                        v_idOfnNeighbors.add(new Pair<>(id, 5));
                    }
                }
                break;

            case 2:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    if (t.getQ() == this.q - 1 && t.getR() == this.r - 1) { //upper left
                        v_idOfnNeighbors.add(new Pair<>(id, 4));

                    } else if (t.getQ() == this.q && t.getR() == this.r - 1) { //upper right
                        v_idOfnNeighbors.add(new Pair<>(id, 6));

                    }
                }
                break;
            case 3:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    if (t.getQ() == this.q && t.getR() == this.r - 1) { //upper right
                        v_idOfnNeighbors.add(new Pair<>(id, 5));

                    } else if (t.getQ() == this.q + 1 && t.getR() == this.r) { //right
                        v_idOfnNeighbors.add(new Pair<>(id, 1));

                    }
                }
                break;
            case 4:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    /*if (t.getQ() == this.q + 1 && t.getR() == this.r) { //right
                        v_idOfnNeighbors.add(new Pair<>(id, 6));

                    } else if (t.getQ() == this.q + 1 && t.getR() == this.r + 1) { //lower right
                        v_idOfnNeighbors.add(new Pair<>(id, 2));
                    }*/

                    if (t.getQ() == this.q + 1 && t.getR() == this.r) { //right
                        v_idOfnNeighbors.add(new Pair<>(id, 6));

                    } else if (t.getQ() == this.q && t.getR() == this.r + 1) { //lower right
                        v_idOfnNeighbors.add(new Pair<>(id, 2));
                    }
                }
                break;
            case 5:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    /*if (t.getQ() == this.q + 1 && t.getR() == this.r + 1) { //lower right
                        v_idOfnNeighbors.add(new Pair<>(id, 1));

                    } else if (t.getQ() == this.q && t.getR() == this.r + 1) { //lower left
                        v_idOfnNeighbors.add(new Pair<>(id, 3));

                    }*/

                    if (t.getQ() == this.q  && t.getR() == this.r + 1) { //lower right
                        v_idOfnNeighbors.add(new Pair<>(id, 1));

                    } else if (t.getQ() == this.q -1 && t.getR() == this.r + 1) { //lower left
                        v_idOfnNeighbors.add(new Pair<>(id, 3));

                    }
                }
                break;
            case 6:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    /*if (t.getQ() == this.q && t.getR() == this.r + 1) { //lower left
                        v_idOfnNeighbors.add(new Pair<>(id, 2));
                    } else if (t.getQ() == this.q - 1 && t.getR() == this.r) { //left
                        v_idOfnNeighbors.add(new Pair<>(id, 4));
                    }*/

                    if (t.getQ() == this.q-1 && t.getR() == this.r + 1) { //lower left
                        v_idOfnNeighbors.add(new Pair<>(id, 2));
                    } else if (t.getQ() == this.q - 1 && t.getR() == this.r) { //left
                        v_idOfnNeighbors.add(new Pair<>(id, 4));
                    }
                }
                break;
        }


        return v_idOfnNeighbors;

    }

    /*
    public List<Hexagon> getNeighborsToEdge(int e_id) {
        List<Hexagon> result = new ArrayList<>();
        List<Pair> edgesOfnNeighbors = new ArrayList<>();


        List<Hexagon> neighbors = this.getNeighbors();
        switch (e_id) {
            case 1:
                for (Hexagon n : neighbors) {
                    if (n.getQ() == this.q -1 && n.getR() == this.r - 1) {
                        result.add(n);
                        edgesOfnNeighbors.add(new Pair(n,4));
                    }
                }
                break;

            case 2:
                for (Hexagon n : neighbors) {
                    if (n.getQ() == this.q && n.getR() == this.r - 1) {
                        result.add(n);
                        edgesOfnNeighbors.add(new Pair(n,5));
                    }
                }
                break;
            case 3:
                for (Hexagon n : neighbors) {
                    if (n.getQ() == this.q +1 && n.getR() == this.r) {
                        result.add(n);
                        edgesOfnNeighbors.add(new Pair(n,6));
                    }
                }
                break;
            case 4:
                for (Hexagon n : neighbors) {
                    if (n.getQ() == this.q +1 && n.getR() == this.r +1) {
                        result.add(n);
                        edgesOfnNeighbors.add(new Pair(n,1));
                    }
                }
                break;
            case 5:
                for (Hexagon n : neighbors) {
                    if (n.getQ() == this.q && n.getR() == this.r +1) {
                        result.add(n);
                        edgesOfnNeighbors.add(new Pair(n,2));
                    }
                }
                break;
            case 6:
                for (Hexagon n : neighbors) {
                    if (n.getQ() == this.q -1 && n.getR() == this.r) {
                        result.add(n);
                        edgesOfnNeighbors.add(new Pair(n,3));
                    }
                }
                break;
        }


        return result;

    }

     */

    /*
    public List<Pair> getNeighbors() {
        List<Pair> result = new ArrayList<>();

        // Hexagons with common vertices have coordinates differing by 1 in either q or r
        int[] dq = {1, 1, 0, -1, -1, 0};
        int[] dr = {0, 1, 1, 0, -1, -1};

        for (int i = 0; i < 6; i++) {
            int neighborQ = this.q + dq[i];
            int neighborR = this.r + dr[i];

            if (neighborR <= maxR && neighborR >= minR && neighborQ <= maxQ && neighborQ >= minQ) {
                //result.add(new Hexagon(neighborQ, neighborR));
                result.add(new Pair(neighborQ, neighborR));
            }
        }

        return result;
    }
     */

    public List<Pair<Integer, Integer>> getNeighborsToEdgeIds(int e_id) {
        List<Pair<Integer, Integer>> edgesOfnNeighbors = new ArrayList<>();


        List<Integer> neighbors = this.getNeighbors();
        switch (e_id) {
            case 1:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q - 1 && t.getR() == this.r - 1) {
                        edgesOfnNeighbors.add(new Pair<>(id, 4));
                    }
                }
                break;

            case 2:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q && t.getR() == this.r - 1) {
                        edgesOfnNeighbors.add(new Pair<>(id, 5));
                    }
                }
                break;
            case 3:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q + 1 && t.getR() == this.r) {
                        edgesOfnNeighbors.add(new Pair<>(id, 6));
                    }
                }
                break;
            case 4:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q + 1 && t.getR() == this.r + 1) {
                        edgesOfnNeighbors.add(new Pair<>(id, 1));
                    }
                }
                break;
            case 5:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q && t.getR() == this.r + 1) {
                        edgesOfnNeighbors.add(new Pair<>(id, 2));
                    }
                }
                break;
            case 6:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q - 1 && t.getR() == this.r) {
                        edgesOfnNeighbors.add(new Pair<>(id, 3));
                    }
                }
                break;
        }


        return edgesOfnNeighbors;

    }

    public List<Integer> getNeighbors() {
        List<Integer> neighborsIds = new ArrayList<>();

        // Hexagons with common vertices have coordinates differing by 1 in either q or r

        //int[] dq = {1, 1, 0, -1, -1, 0};
        //int[] dr = {0, 1, 1, 0, -1, -1};

        int[] dq = {-1, 0, 1, 0, -1, -1};
        int[] dr = {-1, -1, 0, 1, 1, -0};

        for (int i = 0; i < 6; i++) {
            int neighborQ = this.q + dq[i];
            int neighborR = this.r + dr[i];

            if (neighborR <= maxR && neighborR >= minR && neighborQ <= maxQ && neighborQ >= minQ) {
                try {
                    int id = Map.getTiles().getByQandR(new Pair<>(neighborQ, neighborR)).getId();
                    neighborsIds.add(id);
                } catch (NullPointerException e) {
                    continue;
                }
            }

        }


        return neighborsIds;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", r: " + this.r + " ,q: " + this.q;
    }


    // Implement any additional methods or properties as needed
}