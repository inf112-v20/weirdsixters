package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> stack, discarded;

    public Deck() {
        stack = new ArrayList<>(Card.programCards);
        Collections.shuffle(stack);
    }

    public Card drawCard() {
        if (stack.isEmpty()) {
            assert(!discarded.isEmpty());
            swapStacks();
            Collections.shuffle(stack);
        }
        return ListUtils.pop(stack);
    }

    public void discardCard(Card card) {
        assert(!stack.contains(card));
        discarded.add(card);
    }

    private void swapStacks() {
        ArrayList<Card> tmp = stack;
        stack = discarded;
        discarded = tmp;
    }
}
