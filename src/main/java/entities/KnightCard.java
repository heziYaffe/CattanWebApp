package entities;

public class KnightCard extends Card{

    public KnightCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply() {
        this.used = true;
    }
}
