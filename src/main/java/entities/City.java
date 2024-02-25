package entities;

import java.util.ArrayList;

public class City extends Building {
    static {
        cost.put("wheat", 2);
        cost.put("ore", 3);
    }
    public City (Settlement s) {
        tiles = s.tiles;
        location = s.getLocation();
        id = s.getId();
    }
}
