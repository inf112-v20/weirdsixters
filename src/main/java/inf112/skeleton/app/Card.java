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

    public final CardKind kind;
    public final int steps;
    public final int priority;

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

    private static void populateProgramCards(CardKind kind,
                                             int levels,
                                             int[] counts,
                                             int[] priorities,
                                             int interval) {
        for (int level = 0; level < levels; level++) {
            for (int i = 0; i < counts[level]; i++) {
                int priority = priorities[level] + i * interval;
                programCards.add(new Card(kind, level, priority));
            }
        }
    }

    private static void populateProgramCards(CardKind kind,
                                             int count,
                                             int priority,
                                             int interval) {
        populateProgramCards(kind, 1, new int[]{count}, new int[]{priority}, interval);
    }

    /**
     * backup: 6 kort (430 - 480, intervall 10)
     * u-turn: 6 kort (10 - 60, intervall 10)
     * rotate right: 18 kort (80-420, intervall 20)
     * rotate left: 18 kort (70-410, intervall 20)
     * move 1: 18 kort (490 - 650, intervall 10)
     * move 2: 12 kort (670 - 780, intervall 10)
     * move 3: 6 kort (790 - 840, intervall 10)
     */
    static {
        populateProgramCards(CardKind.REVERSE, 6, 430, 10);
        populateProgramCards(CardKind.FLIP, 6, 10, 10);
        populateProgramCards(CardKind.TURNRIGHT, 18, 80, 20);
        populateProgramCards(CardKind.TURNLEFT, 18, 70, 20);
        populateProgramCards(CardKind.FORWARD, 3, new int[]{18, 12, 6},
                new int[]{490, 670, 790}, 10);
    }
}


