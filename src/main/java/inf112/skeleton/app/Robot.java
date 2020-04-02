package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot {
    public Transform transform;
    public final Vector2 startPos;
    public int nextFlag;
    public int health;
    public int lives;
    public ArrayList<Card> registers = new ArrayList<>();

    public Robot(Vector2 startPos) {
        transform = new Transform(startPos, new Vector2(1, 0));
        this.startPos = new Vector2(startPos);
        nextFlag = 1;
        health = 9;
        lives = 3;
    }
}