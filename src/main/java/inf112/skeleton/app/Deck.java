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
        assert(count >= 0);
        ArrayList<Card> result = new ArrayList<>(count);
        update();
        assert(head >= count);
        for (int i = 0; i < count; i++)
            result.add(cards.remove(--head));
        return result;
    }

    private void reset() {
        head = cards.size();
        Collections.shuffle(cards);
    }

    private void update() {
        assert(head >= 0 && head <= cards.size());
        if (head == 0)
            reset();
    }
}
