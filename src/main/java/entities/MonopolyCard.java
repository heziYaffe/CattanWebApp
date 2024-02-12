package entities;

public class MonopolyCard extends Card{
    public MonopolyCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply() {
        this.used = true;
    }
}
