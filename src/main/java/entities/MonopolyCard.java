package entities;

import java.util.HashMap;

public class MonopolyCard extends Card{
    public MonopolyCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply(HashMap<String, String> data) {

        // MAKE PLAYERS STATIC? ALL GAME MANAGER?

        /*if (!used) {
            String r = data.get("resource");
            for (Player p : players) {
                int quantity = p.removeAllResources(r);
                owner.addResource(r, quantity);
            }
        } else {
            System.out.println("MonopolyCard is already used");
        }

         */
    }
}
