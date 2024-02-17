package entities;

import java.util.HashMap;

public class YearOfPlentyCard extends Card{
    public YearOfPlentyCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply(HashMap<String, String> data) {
        if (!used) {
            String r1 = data.get("resource1");
            String r2 = data.get("resource2");
            owner.addResource(r1, 1);
            owner.addResource(r2, 1);
            this.used = true;
        } else {
            System.out.println("YearOfPlentyCard is already used");
        }
    }
}
