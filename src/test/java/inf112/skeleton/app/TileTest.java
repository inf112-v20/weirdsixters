package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {
    @Test
    public void blocksDir_empty_notBlocking() {
        Tile tile = new Tile(TileKind.empty);
        for (Direction enumDir : Direction.values()) {
            Vector2 dir = enumDir.toVector2();
            assertEquals(false, tile.blocksDir(dir, false));
            assertEquals(false, tile.blocksDir(dir, true));
        }
    }
}
