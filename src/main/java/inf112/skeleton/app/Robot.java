package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot {
    public Transform transform;
    public final Vector2 startPos;
    public int nextFlag;
    public ArrayList<Card> registers = new ArrayList<>();

    private int health;
    private int lives;


    public Robot(Vector2 startPos) {
        transform = new Transform(startPos, new Vector2(1, 0));
        this.startPos = new Vector2(startPos);
        nextFlag = 1;
        health = 9;
        lives = 3;
    }

    public void resetPosition(){
        transform.position = new Vector2(startPos);
    }

    private void respawn(){
        resetPosition();
        System.out.println("Another one bites the dust! You have " + lives + " lives remaining!");
    }

    private void kill(){
        resetPosition();
        System.out.println("It's over, you're dead! Better luck next time, idiot!");
    }

    public void dealDamage(){
        health--;
        if (health >= 1){
            System.out.println("Oof, that will leave a mark. You have " + health + " health remaining!");
        }
        else {
            health = 9;
            lives--;
            if (lives >= 1) {
                respawn();
            }
            else {
                kill();
            }
        }
    }
}