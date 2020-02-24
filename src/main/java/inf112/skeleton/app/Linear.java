package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

/**
 * Linear algebra helper functions.
 *
 * Following the naming convention of libGDX.
 */
public class Linear {
    public static Vector2 add(Vector2 fromPos, Vector2 dir) {
        return new Vector2(fromPos).add(dir);
    }

    public static Vector2 sub(Vector2 a, Vector2 b){
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 div(Vector2 a, Vector2 b){
        return new Vector2(a.x/b.x, a.y/b.y);
    }

    public static float min(Vector2 v) {
        return Math.min(v.x, v.y);
    }

    public static float max(Vector2 v) {
        return Math.max(v.x, v.y);
    }

    /**
     * @return copy of vector @v scaled by scalar @s
     */
    public static Vector2 scl(Vector2 v, float s) {
        return new Vector2(v.x * s, v.y * s);
    }

    /**
     * @return negated/inverted copy of vector @v
     */
    public static Vector2 neg(Vector2 v) {
        return scl(v, -1);
    }

    /**
     * @return floored copy of vector @v
     */
    public static Vector2 floor(Vector2 v) {
        return new Vector2((float)Math.floor(v.x), (float)Math.floor(v.y));
    }

    /**
     * @return floored copy of vector @v
     */
    public static Vector2 round(Vector2 v) {
        return new Vector2((float)Math.round(v.x), (float)Math.round(v.y));
    }

    /**
     * @return a normalized copy of v
     */
    public static Vector2 nor(Vector2 v) {
        Vector2 result = new Vector2(v);
        return result.nor();
    }
}
