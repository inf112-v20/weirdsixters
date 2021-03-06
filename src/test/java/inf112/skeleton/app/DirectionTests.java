package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTests {

    @Test
    public void fromVector2MatchesDirection() {
        assertEquals(Direction.RIGHT, Direction.fromVector2(null));
        assertEquals(Direction.RIGHT, Direction.fromVector2(new Vector2(0,0)));

        assertEquals(Direction.RIGHT, Direction.fromVector2(new Vector2(1,0)));
        assertEquals(Direction.UP,    Direction.fromVector2(new Vector2(0,1)));
        assertEquals(Direction.LEFT,  Direction.fromVector2(new Vector2(-1,0)));
        assertEquals(Direction.DOWN,  Direction.fromVector2(new Vector2(0,-1)));
    }
}
