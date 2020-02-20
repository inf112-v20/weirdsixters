package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Transform {

    private Vector2 direction;

    public Transform(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }
}
