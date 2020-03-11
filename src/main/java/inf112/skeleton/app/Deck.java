package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    private int head;

    public Deck(ArrayList<Card> cards) {
        this.cards = new ArrayList(cards);
        head = this.cards.size();
        Collections.shuffle(this.cards);
    }

    public Card drawCard() {
        assert(head >= 0 && head <= cards.size());
        if (head == 0) {
            head = cards.size();
            Collections.shuffle(cards);
        }
        assert(head > 0);
        return cards.remove(--head);
    }
}
