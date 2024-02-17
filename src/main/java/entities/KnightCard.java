package entities;

import java.util.HashMap;
import java.util.List;

public class KnightCard extends Card{

    public KnightCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply(HashMap<String, String> data) {
        /*if (!used) {

            gm.getThife().getTile().setThife(false);
            String t_id = data.get("t_id");
            Tile t = Map.getTiles().getById(Integer.parseInt(t_id));
            t.setThife(true);
            List<Player> playersOnTile = t.getPlayers();

            String p_id = data.get("p_id");
            String r = players[Integer.parseInt(p_id)].removeRandomResource();
            owner.addResource(r,1);
            this.used = true;

        } else {
            System.out.println("KnightCard is already used");
        }

             */
    }

}
