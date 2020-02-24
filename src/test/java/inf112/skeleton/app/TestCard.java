package inf112.skeleton.app;

import org.junit.Test;

import static inf112.skeleton.app.CardKind.FORWARD;
import static org.junit.Assert.assertEquals;

public class TestCard {

    @Test
    public void testGetKind(){
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(FORWARD, card1.getKind());
    }

    @Test
    public void testGetSteps(){
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(2, card1.getSteps());
    }

    @Test
    public void testGetPriority(){
        Card card1 = new Card(FORWARD, 2, 12);
        assertEquals(12, card1.getPriority());
    }
}

