package entities;

public class YearOfPlentyCard extends Card{
    public YearOfPlentyCard(Player owner, String name) {
        super(owner, name);
    }

    @Override
    public void apply() {
        this.used = true;
    }
}
