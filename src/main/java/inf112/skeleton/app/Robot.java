package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Robot {
    public Transform transform;

    public Robot() {
        transform = new Transform(new Vector2(0, 0), new Vector2(0, 1));
    }
}