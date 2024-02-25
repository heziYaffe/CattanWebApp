package entities;

import java.util.HashMap;

public class Helpers {

    // Convert integer values to strings

    public static java.util.Map<String, String> getPlayerResourcesAsString(java.util.Map<String, Integer> playerResources) {
        java.util.Map<String, String> playerResourcesAsString = new HashMap<>();
        for (java.util.Map.Entry<String, Integer> entry : playerResources.entrySet()) {
            String resource = entry.getKey();
            Integer value = entry.getValue();
            String valueAsString = String.valueOf(value);
            playerResourcesAsString.put(resource, valueAsString);
        }
        return playerResourcesAsString;
    }

    public static java.util.Map<String,String> getPlayerState(Player p) {
        java.util.Map<String, String> playerState = new HashMap<>();
        playerState.put("id", String.valueOf(p.getId()));
        playerState.putAll(getPlayerResourcesAsString(p.getResources()));
        playerState.put("points", String.valueOf(p.getPoints()));
        //playerState.put("name", p.getName());
        //playerState.put("color", p.getColor());

        return playerState;

    }

}

