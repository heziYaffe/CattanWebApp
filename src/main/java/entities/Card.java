package entities;

import java.util.HashMap;

public abstract class Card {
    Player owner;
    String name;
    boolean used = false;

    static HashMap<String, Integer> cardCost = new HashMap<>();

    // Static initialization block
    static {
        cardCost.put("ore", 1);
        cardCost.put("wheat", 1);
        cardCost.put("sheep", 1);
    }
    public Card(Player owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    public abstract void apply(HashMap<String, String> data);

    public String getName() {
        return this.name;
    }

    public HashMap<String, Integer> getCost() {
        return cardCost;
    }

}
