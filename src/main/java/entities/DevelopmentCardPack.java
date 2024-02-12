package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DevelopmentCardPack {
    private List<Card> cardList = new ArrayList<>(); // Declare as static

    public DevelopmentCardPack() {
        // Adding knight cards
        cardList.addAll(Collections.nCopies(14, new KnightCard(null, "Knight")));

        // Adding victory point cards
        cardList.addAll(Collections.nCopies(5, new VictoryPointCard(null, "VictoryPoint")));

        // Adding road building cards
        cardList.addAll(Collections.nCopies(2, new RoadBuildingCard(null, "RoadBuildingCard")));

        // Adding year of plenty cards
        cardList.addAll(Collections.nCopies(2, new YearOfPlentyCard(null, "YearOfPlentyCard")));

        // Adding monopoly cards
        cardList.addAll(Collections.nCopies(2, new MonopolyCard(null, "MonopolyCard")));

        // Shuffle the cards
        Collections.shuffle(cardList);
    }

    public Card getTopCard() {
        if (!cardList.isEmpty()) {
            return this.cardList.get(0);
        } else {
            return null;
        }
    }

    public Card drawCard() {
        if (!cardList.isEmpty()) {
            return cardList.remove(0);
        } else {
            return null; // No cards left to draw
        }
    }
}
