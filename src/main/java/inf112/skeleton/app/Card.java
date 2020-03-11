package inf112.skeleton.app;

import java.util.ArrayList;

enum CardKind {
    FORWARD,
    REVERSE,
    TURN_LEFT,
    TURN_RIGHT,
    FLIP
}

public class Card {
    public static final ArrayList<Card> programCards;

    public final CardKind kind;
    public final int steps;
    public final int priority;

    /**
     * @param kind CardKind the kind the card is
     * @param steps int the amount of times kind is performed
     * @param priority decides what order to play the cards
     */
    public Card(CardKind kind, int steps, int priority) {
        this.kind = kind;
        this.steps = steps;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Card{" +
                "kind=" + kind +
                ", steps=" + steps +
                ", priority=" + priority +
                '}';
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
        programCards = new ArrayList<>();
        populateProgramCards(CardKind.REVERSE, 6, 430, 10);
        populateProgramCards(CardKind.FLIP, 6, 10, 10);
        populateProgramCards(CardKind.TURN_RIGHT, 18, 80, 20);
        populateProgramCards(CardKind.TURN_LEFT, 18, 70, 20);
        populateProgramCards(CardKind.FORWARD, 3, new int[]{18, 12, 6},
                new int[]{490, 670, 790}, 10);
    }

    public static void debugPrint() {
        for (Card c : programCards)
            System.out.println(c.toString());
    }

    /**
     * @summary populates the programCards list with all combinations of cards.
     *
     * @param kind
     * @param levelCount how many levels of this kind
     * @param counts is the card count per for each level
     * @param priorities is the card priority for each level
     * @param interval is the priority interval
     */
    private static void populateProgramCards(CardKind kind,
                                             int levelCount,
                                             int[] counts,
                                             int[] priorities,
                                             int interval) {
        for (int level = 0; level < levelCount; level++) {
            for (int i = 0; i < counts[level]; i++) {
                int steps = level+1;
                int priority = priorities[level] + i * interval;
                programCards.add(new Card(kind, steps, priority));
            }
        }
    }

    private static void populateProgramCards(CardKind kind,
                                             int count,
                                             int priority,
                                             int interval) {
        populateProgramCards(kind, 1, new int[]{count}, new int[]{priority}, interval);
    }
}


