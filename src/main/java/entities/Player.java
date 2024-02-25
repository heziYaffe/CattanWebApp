package entities;

import java.util.*;

public class Player {

    int id;

    private HashMap<String, Integer> resources;
    private List<Settlement> settlements;
    private List<Road> roads;

    //private List<Card> cards;
    private HashMap<String, Integer> cards;

    private int points = 0;
    private String color;
    private static GameManager gm;

    static {
        gm = GameManager.getInstance();
    }

    public Player() {
        settlements = new ArrayList<>();
        roads = new ArrayList<>();
        initializeCards();
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

    private void initializeCards() {
        cards = new HashMap<>();
        cards.put("knight", 0);
        cards.put("roadsBuilding", 0);
        cards.put("yearOfPlenty", 0);
        cards.put("monopoly", 0);
    }

    public void addResource(String resource, int quantity) {
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

        Settlement s = new Settlement(t_id, v_id);

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
            this.addResource(t.getResource(), 1);
        }

        System.out.println(resources);

        s.setTiles(settlementTiles);

        // Add the settlement to the player's list of settlements
        this.settlements.add(s);
        this.points++;
        return true;
    }

    public boolean buildSettlement(int t_id, int v_id) {
        Settlement s = new Settlement(t_id, v_id);

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
            this.points++;

            return true; // Building successful
        } else {
            System.out.println("Player doesn't have enough resources to build settlement");
            return false; // Not enough resources
        }
    }

    public boolean buildRoad(int t_id, int e_id) {
        Pair<Integer, Integer> roadLocation = new Pair<>(t_id, e_id);
        Road r = new Road(this.id, t_id, e_id);
        System.out.println("t_id: " + t_id);
        System.out.println("e_id: " + e_id);

        if (this.roads.size() <= 1) {
            this.addResource("wood", 1);
            this.addResource("brick", 1);
        }
        // Check if the player has enough resources for the road
        if (checkForEnoughResources(r.getCost())) {

            List<Pair<Integer, Integer>> possibilities = this.getAllRoadsPossibilities();
            System.out.println(possibilities);

            if (possibilities.contains(roadLocation)) {
                // Deduct the cost from the player's resources
                spendResources(r.getCost());

                // Add the road to the player's list of roads
                roads.add(r);
                gm.addRoad(r);

                return true; // Building successful
            } else {
                System.out.println("Player " + this.id + ", " + this.color + " Cant Build Road In This Location");
                return false; // Not Valid Location
            }

        } else {
            System.out.println("Player " + this.id + ", " + this.color + " doesn't have enough resources to build road");
            return false; // Not enough resources
        }
    }

    public boolean drawCard(Card c) {
        if (checkForEnoughResources(c.getCost())) {
            spendResources(c.getCost());
            this.cards.replace(c.getName(), this.cards.get(c.getName()) + 1);
            return true;
        } else {
            System.out.println("you don't have enough resource for buying development card");
            return false;
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


    public void addPoints(int i) {
        this.points += i;
    }

    public int removeAllResources(String r) {
        // Retrieve the quantity of the specified resource
        int quantity = this.resources.getOrDefault(r, 0);

        // Set the value of the specified resource to zero
        this.resources.put(r, 0);

        // Return the original quantity
        return quantity;
    }

    public List<Pair<Integer, Integer>> getAllRoadsPossibilities() {
        Set<Pair<Integer, Integer>> uniquePossibilities = new HashSet<>();

        List<Pair<Integer, Integer>> possibilitiesFromSettlements = new ArrayList<>();
        List<Pair<Integer, Integer>> possibilitiesFromRoads = new ArrayList<>();

        for (Settlement s : settlements) {
            possibilitiesFromSettlements.addAll(s.getRoadsPossibilities());
        }

        for (Road road : roads) {
            possibilitiesFromRoads.addAll(road.getRoadsPossibilities());
        }

        uniquePossibilities.addAll(possibilitiesFromRoads);
        uniquePossibilities.addAll(possibilitiesFromSettlements);

        gm.getRoadsPositions().forEach(uniquePossibilities::remove);


        List<Pair<Integer, Integer>> sortedPossibilities = new ArrayList<>(uniquePossibilities);

        sortedPossibilities.sort(Comparator.comparing(Pair::getFirst));


        return sortedPossibilities;
    }


    public boolean useDevelopmentCard(String cardName) {
        if (this.cards.get(cardName) > 0) {
            this.cards.put(cardName, this.cards.get(cardName) - 1);
            return true;
        } else {
            return false;
        }
    }

    /*
    public boolean buildCity(int cId) {
        Settlement cityBase = gm.getSettlements().get(cId);
        boolean rv = false;
        if (cityBase.getOwnerId() == this.id) {

            City c = new City(cityBase);

            // Check if the player has enough resources for the settlement
            if (checkForEnoughResources(c.getCost())) {

                // Deduct the cost from the player's resources
                spendResources(c.getCost());

                // Add the settlement to the player's list of settlements
                this.settlements.add(c);

                this.settlements.remove(cityBase);

                this.points += 2;

                rv = true; // Building successful
            } else {
                System.out.println("Player doesn't have enough resources to build settlement");
                rv = false; // Not enough resources
            }
        }

        return rv;
    }

     */

}
