package inf112.skeleton.app;

import org.junit.Test;

import static inf112.skeleton.app.CardKind.FORWARD;
import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void card_forward_fowardValue() {
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(FORWARD, card1.kind);
        assertEquals(2, card1.steps);
        assertEquals(12, card1.priority);

    }
}

