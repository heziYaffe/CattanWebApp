package entities;

import java.util.HashMap;
import java.util.Map;

public class RoadBuildingCard extends Card{
    public RoadBuildingCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply(HashMap<String, String> data) {
        System.out.println("RoadBuildingCard is applied");
    }

        /*
        if (!used) {
            String roadTile1 = data.get("roadT_id1");
            String roadVertex1 = data.get("roadV_id1");
            String roadTile2 = data.get("roadT_id2");
            String roadVertex2 = data.get("roadV_id2");
// Create Pair objects for the vertices of the road
            Pair<Pair<Tile, Integer>, Pair<Tile, Integer>> roadVertices =
                    new Pair<>(new Pair<>(roadTile1, roadVertex1), new Pair<>(roadTile2, roadVertex2));

// Create a Road object with the road vertices
            Road road = new Road(roadVertices);

// Add the road to the player's list of roads
            owner.addRoad(road);
            owner.addResource(r1, 1);
            owner.addResource(r2, 1);
            this.used = true;
        } else {
            System.out.println("YearOfPlentyCard is already used");
        }*/


}
