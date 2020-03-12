package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    private int head;

    public Deck(ArrayList<Card> cards) {
        this.cards = new ArrayList(cards);
        reset();
    }

    /**
     * @return a new list of drawn cards.
     */
    public ArrayList<Card> drawCards(int count) {
        update();
        assert(count >= 0 && head >= count);
        ArrayList<Card> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            result.add(cards.remove(--head));
        return result;
    }

    private void reset() {
        head = cards.size();
        Collections.shuffle(cards);
    }

    // check internal state, re-shuffle if needed
    private void update() {
        assert(head >= 0 && head <= cards.size());
        if (head == 0)
            reset();
    }
}
