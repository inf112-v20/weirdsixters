package inf112.skeleton.app;

enum CardKind {
    FORWARD,
    REVERSE,
    TURNLEFT,
    TURNRIGHT,
    FLIP
}

public class Card {

    private CardKind kind;
    private int steps;
    private int priority;

    /**
     *
     * @param kind CardKind the kind the card is
     * @param steps int the amount of times kind is performed
     * @param priority decides what order to play the cards
     */
    public Card(CardKind kind, int steps, int priority) {
        this.kind = kind;
        this.steps = steps;
        this.priority = priority;
    }

    public CardKind getKind() {
        return kind;
    }

    public int getSteps() {
        return steps;
    }

    public int getPriority() {
        return priority;
    }
}


