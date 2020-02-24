package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

/**
 * Should only be used with Tile.
 * We might need a Vector2i (integer) for coordinates, etc.
 */
public enum Direction {
    RIGHT, UP, LEFT, DOWN;

    public static Direction fromVector2(Vector2 dir) {
        if (dir == null)
            return RIGHT;
        dir.nor();
        if (dir.x < -0.9)
            return LEFT;
        if (dir.y > 0.9)
            return UP;
        if (dir.y < 0 && Math.abs(dir.y) > Math.abs(dir.x))
            return DOWN;
        return RIGHT;
    }

    public Vector2 toVector2() {
        switch (this) {
            default:
            case RIGHT: return new Vector2(1,0);
            case UP:    return new Vector2(0,1);
            case DOWN:  return new Vector2(0,-1);
            case LEFT:  return new Vector2(-1,0);
        }
    }
}
