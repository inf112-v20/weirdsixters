package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTests {
    @Test
    public void emptyTileDoesNotBlock() {
        Tile tile = new Tile(TileKind.empty);
        for (Direction enumDir : Direction.values()) {
            Vector2 dir = enumDir.toVector2();
            assertFalse(tile.blocksDir(dir, false));
            assertFalse(tile.blocksDir(dir, true));
        }
    }

    @Test
    public void singleRightWallBlocksOneDir() {
        Tile tile = new Tile(TileKind.wall, Direction.RIGHT, false);
        assertTrue(tile.blocksDir(Direction.RIGHT.toVector2(), false));
        assertFalse(tile.blocksDir(Direction.LEFT.toVector2(), false));
        assertFalse(tile.blocksDir(Direction.UP.toVector2(), false));
        assertFalse(tile.blocksDir(Direction.DOWN.toVector2(), false));
    }
    @Test
    public void doubleRightWallBlocksTwoDirs() {
        Tile tile = new Tile(TileKind.wall, Direction.RIGHT, true);
        assertTrue(tile.blocksDir(Direction.RIGHT.toVector2(), false));
        assertFalse(tile.blocksDir(Direction.LEFT.toVector2(), false));
        assertTrue(tile.blocksDir(Direction.UP.toVector2(), false));
        assertFalse(tile.blocksDir(Direction.DOWN.toVector2(), false));
    }
}
