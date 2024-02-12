package entities;

public class RoadBuildingCard extends Card{
    public RoadBuildingCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply() {
        this.used = true;
    }
}
