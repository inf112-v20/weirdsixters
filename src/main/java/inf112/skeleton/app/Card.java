package inf112.skeleton.app;

import java.util.ArrayList;

enum CardKind {
    FORWARD,
    REVERSE,
    TURNLEFT,
    TURNRIGHT,
    FLIP
}

public class Card {

    public static final ArrayList<Card> programCards = new ArrayList<>();

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

    private static void populateProgramCards(CardKind kind, int levels, int interval, int[] counts, int[] priorities) {
        for (int level = 0; level < levels; level++) {
            for (int i = 0; i < counts[level]; i++) {
                int priority = priorities[level] + i * interval;
                programCards.add(new Card(CardKind.FORWARD, level, priority));
            }
        }
    }

    /**
     * har en bevegelse (Flytt frem 1, Flytt frem 2, Flytt frem 3, Flytt bakover 1, Roter 90 grader H, Roter 90 grader V, Roter 180 grader)
     * har en prioritet (alle bevegelseskort må ha unik prioritet innenfor sin klasse, og mellom klassene (bevegelsestypene) skal intervallet ikke være overlappende)
     * backup: 6 kort (430 - 480, intervall 10)
     * u-turn: 6 kort (10 - 60, intervall 10)
     * rotate right: 18 kort (80-420, intervall 20)
     * rotate left: 18 kort (70-410, intervall 20)
     * move 1: 18 kort (490 - 650, intervall 10)
     * move 2: 12 kort (670 - 780, intervall 10)
     * move 3: 6 kort (790 - 840, intervall 10)
     */
    static {
        populateProgramCards(CardKind.FORWARD, 3, 10, new int[]{80, 12, 6}, new int[]{490,670,790});
        populateProgramCards(CardKind.TURNLEFT, 3, 10, new int[]{80, 12, 6}, new int[]{490,670,790});
    }
}


