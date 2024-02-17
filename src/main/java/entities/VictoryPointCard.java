package entities;

import java.util.HashMap;

public class VictoryPointCard extends Card {
    public VictoryPointCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply(HashMap<String, String> data) {
        if (!used) {
            owner.addPoints(1);
            this.used = true;
        } else {
            System.out.println("VictoryPointCard is already used");
        }
    }
}
