package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {

    int id;

    private HashMap<String, Integer> resources;
    private List<Settlement> settlements;
    private List<Road> roads;

    private List<Card> cards;
    private int points = 0;

    private String color;

    // Constructor and other methods...
    public Player() {
        settlements = new ArrayList<>();
        roads = new ArrayList<>();
        cards = new ArrayList<>();
        initializeResources();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Player ID: ").append(id).append("\n");
        stringBuilder.append("Color: ").append(color).append("\n");
        stringBuilder.append("Points: ").append(points).append("\n");

        stringBuilder.append("Resources: ").append(resources).append("\n");
        stringBuilder.append("Settlements: ").append(settlements).append("\n");
        stringBuilder.append("Roads: ").append(roads).append("\n");
        stringBuilder.append("Development Cards: ").append(cards).append("\n");

        return stringBuilder.toString();
    }

    private void initializeResources() {
        resources = new HashMap<>();
        resources.put("sheep", 0);
        resources.put("ore", 0);
        resources.put("brick", 0);
        resources.put("wheat", 0);
        resources.put("wood", 0);
    }

    private void addResource(String resource, int quantity) {
        // Get the current quantity of the resource
        int oldValue = this.resources.getOrDefault(resource, 0);

        // Calculate the new quantity by adding the specified quantity
        int newValue = oldValue + quantity;

        // Update the resource quantity in the hashmap
        this.resources.put(resource, newValue);
    }

    // Getter for resources
    public HashMap<String, Integer> getResources() {
        return resources;
    }

    // Getter for settlements
    public List<Settlement> getSettlements() {
        return settlements;
    }

    // Getter for roads
    public List<Road> getRoads() {
        return roads;
    }

    // Getter for points
    public int getPoints() {
        return points;
    }

    public void setColor(String c) {
        this.color = c;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public boolean buildSettlement(Settlement s) {
        // Check if the player has enough resources for the settlement
        if (checkForEnoughResources(s.getCost())) {
            // Deduct the cost from the player's resources
            spendResources(s.getCost());

            // Add the settlement to the player's list of settlements
            settlements.add(s);

            return true; // Building successful
        } else {
            return false; // Not enough resources
        }
    }

    public boolean buildPrimarySettlement(int t_id, int v_id) {

        Settlement s = new Settlement();

        Tile clickedTile = Map.getTiles().getById(t_id);

        if (!clickedTile.isVertexAvailable(v_id)) {
            return false;
        }


        List<Pair<Integer, Integer>> neighborsToVertex = clickedTile.getNeighborsToVertex(v_id);
        clickedTile.removeVerticesAfterBuildingCity(v_id);
        List<Tile> settlementTiles = new ArrayList<>();
        settlementTiles.add(clickedTile);

        for (Pair<Integer, Integer> pair : neighborsToVertex) {
            int nT_id = pair.getFirst();  // tile id
            int nV_id = pair.getSecond(); // tile vertex id
            Tile t = Map.getTiles().getById(nT_id);
            settlementTiles.add(t);
        }

        for (Tile t : settlementTiles) {
            if (!t.isThife()) {
                this.addResource(t.getResource(), 1);
            }
        }

        System.out.println(resources);

        s.setTiles(settlementTiles);

        // Add the settlement to the player's list of settlements
        this.settlements.add(s);
        return true;
    }

    public boolean drawCard(Card c) {
        if (checkForEnoughResources(c.getCost())) {
            spendResources(c.getCost());
            this.cards.add(c);
            return true;
        } else {
            System.out.println("you don't have enough resource for buying development card");
            return false;
        }
    }

    public boolean buildSettlement(int t_id, int v_id) {
        Settlement s = new Settlement();

        // Check if the player has enough resources for the settlement
        if (checkForEnoughResources(s.getCost())) {

            Tile clickedTile = Map.getTiles().getById(t_id);
            if (!clickedTile.isVertexAvailable(v_id)) {
                return false;
            }
            clickedTile.removeAvailableVertex(v_id);

            // Deduct the cost from the player's resources
            spendResources(s.getCost());

            List<Pair<Integer, Integer>> neighborsToVertex = clickedTile.getNeighborsToVertex(v_id);

            List<Tile> settlementTiles = new ArrayList<>();

            for (Pair<Integer, Integer> pair : neighborsToVertex) {
                int nT_id = pair.getFirst();  // tile id
                int nV_id = pair.getSecond(); // tile vertex id
                Tile t = Map.getTiles().getById(nT_id);
                t.removeAvailableVertex(nV_id);
                settlementTiles.add(t);
            }

            s.setTiles(settlementTiles);

            // Add the settlement to the player's list of settlements
            this.settlements.add(s);

            return true; // Building successful
        } else {
            System.out.println("Player doesn't have enough resources to build settlement");
            return false; // Not enough resources
        }
    }

    //COMBINE THOSE TWO METHODS
    private boolean checkForEnoughResources(HashMap<String, Integer> cost) {
        // Check if the player has enough resources for the specified cost
        return resources.getOrDefault("wood", 0) >= cost.getOrDefault("wood", 0) &&
                resources.getOrDefault("brick", 0) >= cost.getOrDefault("brick", 0) &&
                resources.getOrDefault("wheat", 0) >= cost.getOrDefault("wheat", 0) &&
                resources.getOrDefault("ore", 0) >= cost.getOrDefault("ore", 0) &&
                resources.getOrDefault("sheep", 0) >= cost.getOrDefault("sheep", 0);
    }

    private void spendResources(HashMap<String, Integer> cost) {
        // Deduct the cost from the player's resources
        resources.compute("wood", (key, value) -> value - cost.getOrDefault("wood", 0));
        resources.compute("brick", (key, value) -> value - cost.getOrDefault("brick", 0));
        resources.compute("wheat", (key, value) -> value - cost.getOrDefault("wheat", 0));
        resources.compute("ore", (key, value) -> value - cost.getOrDefault("ore", 0));
        resources.compute("sheep", (key, value) -> value - cost.getOrDefault("sheep", 0));
    }



}
