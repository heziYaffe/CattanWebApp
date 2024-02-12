package entities;

public class VictoryPointCard extends Card{
    public VictoryPointCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply() {
        this.used = true;
    }
}
