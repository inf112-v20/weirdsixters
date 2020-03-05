package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Robot {
    public Transform transform;
    public final Vector2 startPos;

    public Robot(Vector2 startPos) {
        transform = new Transform(startPos, new Vector2(0, 1));
        this.startPos = new Vector2(startPos);
    }
}