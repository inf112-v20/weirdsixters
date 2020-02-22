package inf112.skeleton.app;

enum CardKind {
    FORWARD,
    REVERSE,
    TURNLEFT,
    TURNRIGHT,
    FLIP
}

public class Card {

    private CardKind cardKind;
    private int steps;
    private int priority;

    /**
     *
     * @param cardKind CardKind the cardKind the card is
     * @param steps int the amount of times cardKind is performed
     * @param priority decides what order to play the cards
     */
    public Card(CardKind cardKind, int steps, int priority) {
        this.cardKind = cardKind;
        this.steps = steps;
        this.priority = priority;
    }

    public CardKind getCardKind() {
        return cardKind;
    }

    public int getSteps() {
        return steps;
    }

    public int getPriority() {
        return priority;
    }
}


