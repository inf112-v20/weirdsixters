package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

/**
 * Linear algebra helper functions.
 */
public class Linear {
    public static Vector2 div(Vector2 a, Vector2 b){
        return new Vector2(a.x/b.x, a.y/b.y);
    }

    public static float min(Vector2 v) {
        return Math.min(v.x, v.y);
    }

    public static float max(Vector2 v) {
        return Math.max(v.x, v.y);
    }
}
