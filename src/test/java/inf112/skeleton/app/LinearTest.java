package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinearTest {
    @Test
    public void addTest(){
        assertEquals(new Vector2(0,1), Linear.add(new Vector2(0,0), new Vector2(0,1)));
    }
}
