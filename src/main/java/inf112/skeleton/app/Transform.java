package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Transform {
    public Vector2 position;
    public Vector2 direction;

    public Transform(Vector2 position, Vector2 direction) {
        this.position = position;
        this.direction = direction;
    }
}
