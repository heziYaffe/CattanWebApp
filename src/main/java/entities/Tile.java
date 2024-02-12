package entities;

/*
public class Tile {

    private int id;
    private final Resource r;
    private final int number;

    public Tile(int id, Resource r, int number) {
        this.id = id;
        this.r = r;
        this.number = number;
    }

    public int getId() {
        return this.id;
    }
    public int getNumber() {
        return this.number;
    }

    public Resource getResource() {
        return this.r;
    }

    @Override
    public String toString() {
        return "Tile{id=" + id + ", resource=" + r + ", number=" + number + '}';
    }
}

 */

import java.util.List;

public class Tile {
    private Hexagon h;
    private String resource;
    private int number;
    private List<Integer> neighbors;
    boolean thife = false;


    public Tile(Hexagon h, String r, int n) {
        this.h = h;
        this.resource = r;
        this.number = n;
    }

    public int getId() {
        return h.getId();
    }

    public int getQ() {
        return this.h.getQ();
    }

    public int getR() {
        return this.h.getR();
    }

    public int getNumber() {
        return this.number;
    }

    public String getResource() {
        return this.resource;
    }

    /*public List<Integer> getNeighbors() {
        return h.getNeighbors();
    }*/


    public List<Pair<Integer,Integer>> getNeighborsToVertex(int vId) {
        return this.h.getNeighborsToVertex(vId);
    }

    public boolean isThife() {
        return thife;
    }

    public boolean isVertexAvailable(int v_id) {
        return this.h.checkIfLocationIsValid("vertex", v_id);
    }

    public boolean isEdgeAvailable(int e_id) {
        return this.h.checkIfLocationIsValid("edge", e_id);
    }
    public void removeAvailableVertex(int v_id) {
        this.h.removeAvailableVertex(v_id); // remove the vertex from the list of available vertices
    }

    public void removeVertexCopies(int v_id) {
        this.h.removeVertexCopies(v_id); // remove the vertex from the list of available vertices
    }

    /*public void removeAdjacentVertices(int v_id) {
        this.h.removeAdjacentVertices(v_id); // remove the vertex from the list of available vertices
    }*/

    public List<Integer> getAdjacentVertices(int v_id) {
        return this.h.getAdjacentVertices(v_id);
    }

    public void removeVerticesAfterBuildingCity(int v_id) {
        this.h.removeVerticesAfterBuildingCity(v_id); // remove the vertex from the list of available vertices
    }

        public String toString() {
        return "Hexagon " + h.toString() +
                ", Resource " + this.resource + ", number: " + this.number;
    }

    public List<Integer> getAvailableVertices() {
        return this.h.availableVerticiesForCities;
    }

    public void initNeighbors() {
        this.neighbors = this.getNeighbors();
    }

    public List<Integer> getNeighbors() {
        return this.neighbors;
    }
}
