package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot {
    public final Vector2 startPos;
    public final Color color;

    public Vector2 direction;
    public ArrayList<Card> registers = new ArrayList<>();
    public int nextFlag;

    private int health;
    private int lives;

    public Robot(Vector2 startPos, Color color) {
        this.startPos = new Vector2(startPos);
        this.color = color;
        direction = new Vector2(1, 0);
        nextFlag = 1;
        health = 9;
        lives = 3;
    }

    public void dealDamage(){
        health--;
        if (health <= 0)
            lives--;
    }

    public boolean isDead(){
        return lives < 1;
    }
}