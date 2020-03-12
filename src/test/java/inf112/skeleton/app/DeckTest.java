package inf112.skeleton.app;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void drawCard_zero_zeroCards() {
        Deck deck = new Deck(Card.programCards);
        assertEquals(0, deck.drawCards(0).size());
    }

    @Test
    public void drawCard_nine_nineCards() {
        Deck deck = new Deck(Card.programCards);
        ArrayList<Card> cards = deck.drawCards(9);
        assertEquals(9, cards.size());
        System.out.println(cards.get(0).toString());
    }
}
