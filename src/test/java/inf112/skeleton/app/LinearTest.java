package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinearTest {
    @Test
    public void addTest(){
        assertEquals(new Vector2(0,1), Linear.add(new Vector2(0,0), new Vector2(0,1)));
    }
    @Test
    public void subTest(){
        assertEquals(new Vector2(0,-2), Linear.add(new Vector2(0,-1), new Vector2(0,-1)));
    }
    @Test
    public void divTest(){
        assertEquals(new Vector2(1,1), Linear.div(new Vector2(4,1),new Vector2(4,1)));
    }
    @Test
    public void minTest(){
        assertEquals(5, Linear.min(new Vector2(5,10)), 0.001);
    }


}
