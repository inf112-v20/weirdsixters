package inf112.skeleton.app;

enum CardKind {
    FORWARD,
    REVERSE,
    TURNLEFT,
    TURNRIGHT,
    FLIP
}

public class Card {

    private CardKind action;
    private int steps;
    private int priority;

    public Card(CardKind action, int steps, int priority) {
        this.action = action;
        this.steps = steps;
        this.priority = priority;
    }

    public CardKind getAction() {
        return action;
    }

    public int getSteps() {
        return steps;
    }

    public int getPriority() {
        return priority;
    }
}


