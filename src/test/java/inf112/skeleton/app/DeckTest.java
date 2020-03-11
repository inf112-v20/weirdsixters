package inf112.skeleton.app;

import org.junit.Test;

public class DeckTest {

    @Test
    public void drawCard_programCards_works() {
        Deck deck = new Deck(Card.programCards);
        Card card = deck.drawCard();
        System.out.println(card.toString());
    }
}
