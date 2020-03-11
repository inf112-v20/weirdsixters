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

    public Card drawCard() {
        assert(head >= 0 && head <= cards.size());
        if (head == 0)
            reset();
        assert(head > 0);
        return cards.remove(--head);
    }

    private void reset() {
        head = cards.size();
        Collections.shuffle(cards);
    }
}
