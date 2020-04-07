package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;


import java.util.ArrayList;

public class Robot {
    public Transform transform;
    public final Vector2 startPos;
    public int nextFlag;
    public ArrayList<Card> registers = new ArrayList<>();
    public final Color color;

    public Robot(Vector2 startPos, Color color) {
        this.color = color;
        transform = new Transform(startPos, new Vector2(1, 0));
        this.startPos = new Vector2(startPos);
        nextFlag = 1;
    }
}