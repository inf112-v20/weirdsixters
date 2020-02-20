package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Transform {

    private Vector2 rotation;

    public Transform(Vector2 rotation) {
        this.rotation = rotation;
    }

    public Vector2 getRotation() {
        return rotation;
    }
}
