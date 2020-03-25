package inf112.skeleton.app;

import org.junit.Test;

import static inf112.skeleton.app.CardKind.FORWARD;
import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void card_forward_fowardValue(){
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(FORWARD, card1.kind);
    }

    @Test
    public void card_two_twoSteps(){
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(2, card1.steps);
    }

    @Test
    public void card_twelve_priorityTwelve(){
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(12, card1.priority);
    }
}

