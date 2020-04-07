package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    private int head;

    public Deck(ArrayList<Card> cards) {
        this.cards = new ArrayList(cards);
        shuffle();
    }

    /**
     * @return a new list of drawn cards.
     */
    public ArrayList<Card> drawCards(int count) {
        assert(head >= 0 && head <= cards.size());
        if (!enoughCards(count))
            shuffle();

        ArrayList<Card> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            result.add(cards.get(--head));
        return result;
    }

    private boolean enoughCards(int count) {
        return head >= count;
    }

    private void shuffle() {
        System.out.println("Shuffling deck");
        head = cards.size();
        Collections.shuffle(cards);
    }
}
