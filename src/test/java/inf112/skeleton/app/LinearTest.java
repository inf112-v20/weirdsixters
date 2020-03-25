package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinearTest {
    @Test
    public void Linear_someNumbers_correctResult(){
        assertEquals(new Vector2(0,1), Linear.add(new Vector2(0,0), new Vector2(0,1)));
        assertEquals(new Vector2(0,-2), Linear.add(new Vector2(0,-1), new Vector2(0,-1)));
        assertEquals(new Vector2(3,-8), Linear.mul(new Vector2(1,2), new Vector2(3,-4)));
        assertEquals(new Vector2(1,1), Linear.div(new Vector2(4,1),new Vector2(4,1)));
        assertEquals(5, Linear.min(new Vector2(5,10)), 0.001);
        assertEquals(10, Linear.max(new Vector2(5,10)), 0);
        assertEquals(new Vector2(6,3), Linear.scl(new Vector2(2,1), (3)));
        assertEquals(new Vector2(-2,-2), Linear.neg(new Vector2(2,2)));
        assertEquals(new Vector2(0,0), Linear.floor(new Vector2(0.5f, 0.5f)));
        assertEquals(new Vector2(1,1), Linear.round(new Vector2(0.9f,0.8f)));
    }
}
