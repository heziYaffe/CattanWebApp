package entities;

import java.util.ArrayList;
import java.util.List;

public class Hexagon {

    int id;
    private int q;
    private int r;

    List<Integer> availableVerticiesForCities = new ArrayList<>();
    List<Integer> availableEdgesForRoads = new ArrayList<>();

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


    public List<Pair<Integer, Integer>> getVertexCopies(int v_id) {
        List<Pair<Integer, Integer>> copies = this.getNeighborsToVertex(v_id);
        System.out.println("neighbors to vertex " + v_id + ": " + copies);
        return copies;

    }

    public List<Pair<Integer, Integer>> getEdgeCopies(int e_id) {
        List<Pair<Integer, Integer>> copies = this.getNeighborsToEdgeIds(e_id);
        System.out.println("neighbors to edge " + e_id + ": " + copies);
        return copies;

    }

    public void removeVertexCopies(int v_id) {
        List<Pair<Integer, Integer>> copies = this.getVertexCopies(v_id);
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

    public List<Integer> getAdjacentEdgesToVertex(int v_id) {
        List<Integer> adjacent = new ArrayList<>();
        if (v_id == 1) {
            adjacent.add(1);
            adjacent.add(6);
        } else if (v_id == 6) {
            adjacent.add(5);
            adjacent.add(6);
        } else {
            adjacent.add(v_id);
            adjacent.add(v_id -1);
        }
        return adjacent;
    }

    public List<Integer> getAdjacentEdgesToEdge(int e_id) {
        List<Integer> adjacent = new ArrayList<>();
        if (e_id == 1) {
            adjacent.add(2);
            adjacent.add(6);
        } else if (e_id == 6) {
            adjacent.add(1);
            adjacent.add(5);
        } else {
            adjacent.add(e_id +1);
            adjacent.add(e_id -1);
        }
        return adjacent;
    }



    public void removeAvailableVertex(int v_id) {
        this.availableVerticiesForCities.removeIf(vertex -> vertex == v_id);
    }

    public void removeVerticesAfterBuildingCity(int v_id) {

        //remove vertex and his copies
        removeAvailableVertex(v_id);
        System.out.println("copies of vertex " + v_id + ":");
        System.out.println(getVertexCopies(v_id));
        removeVertexCopies(v_id);

        List<Integer> adjacentVertices = this.getAdjacentVertices(v_id);
        for (int id : adjacentVertices) {
            this.removeAvailableVertex(id);
            this.removeVertexCopies(id);
        }

        for (int i = 0; i < 2; i++) {
            System.out.println(Map.getTiles().getById(i).getAvailableVertices());
        }

        List<Pair<Integer, Integer>> copies = getVertexCopies(v_id);

        for (Pair<Integer, Integer> copy : copies) {
            int nT_id = copy.getFirst();  // tile id
            int nV_id = copy.getSecond(); // tile vertex id
            Tile t = Map.getTiles().getById(nT_id);
            List<Integer> adjacentVertices2 = t.getAdjacentVertices(nV_id);
            for (int id : adjacentVertices2) {
                t.removeAvailableVertex(id);
                t.removeVertexCopies(id);
            }

        }

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
        System.out.println("neighbors:" + neighbors);
        switch (v_id) {
            case 1:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);
                    if (t.getQ() == this.q - 1 && t.getR() == this.r) { //left
                        v_idOfnNeighbors.add(new Pair<>(id, 3));
                    } else if (t.getQ() == this.q && t.getR() == this.r - 1) { //upper left
                        v_idOfnNeighbors.add(new Pair<>(id, 5));
                    }
                }
                break;

            case 2:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    if (t.getQ() == this.q && t.getR() == this.r - 1) { //upper left
                        v_idOfnNeighbors.add(new Pair<>(id, 4));

                    } else if (t.getQ() == this.q +1 && t.getR() == this.r - 1) { //upper right
                        v_idOfnNeighbors.add(new Pair<>(id, 6));

                    }
                }
                break;
            case 3:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

                    if (t.getQ() == this.q +1 && t.getR() == this.r - 1) { //upper right
                        v_idOfnNeighbors.add(new Pair<>(id, 5));

                    } else if (t.getQ() == this.q +1 && t.getR() == this.r) { //right
                        v_idOfnNeighbors.add(new Pair<>(id, 1));

                    }
                }
                break;
            case 4:
                for (int id : neighbors) {
                    Tile t = Map.getTiles().getById(id);

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

        int[] dq = {0, 1, 1, 0, -1, -1};
        int[] dr = {-1, -1, 0, 1, 1, -0};

        for (int i = 0; i < 6; i++) {
            int neighborQ = this.q + dq[i];
            int neighborR = this.r + dr[i];

            try {
                int id = Map.getTiles().getByQandR(new Pair<>(neighborQ, neighborR)).getId();
                neighborsIds.add(id);
            } catch (NullPointerException e) {
                continue;
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