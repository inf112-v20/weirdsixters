package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot {
    public static final int REGISTER_SIZE = 5;
    public final Vector2 startPos;
    public final Color color;
    public ArrayList<Card> registers = new ArrayList<>();
    public Vector2 direction;
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

    public void kill() {
        registers.clear();
        direction.setAngle(0);
        lives--;
    }

    public void dealDamage(){
        health--;
        if (health <= 0)
            kill();
    }

    public boolean isDead(){
        return lives < 1;
    }

    /**
     * Add card to first idle register.
     * @return true on success.
     */
    public boolean addCard(Card card) {
        if (registers.size() >= REGISTER_SIZE)
            return false;
        registers.add(card);
        return true;
    }

    public Card getCard(int i) {
        if (i >= registers.size())
            return null;
        return registers.get(i);
    }

    public void clearRegisters() {
        registers.clear();
    }

    /**
     * @return true if the robot has been fully programmed.
     */
    public boolean isReady() {
        return registers.size() == REGISTER_SIZE;
    }

    public int cardCount() {
        return registers.size();
    }

    public Card removeCard(int index) {
        return registers.remove(index);
    }
}