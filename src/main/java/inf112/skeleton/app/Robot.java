package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot {
    public static final int HEALTH = 9;
    public static final int LIVES = 3;
    public static final int REGISTER_SIZE = 5;

    public final String name;
    public final Color color;

    public ArrayList<Card> registers = new ArrayList<>();
    public Vector2 direction;
    public Vector2 backupPos;
    public int nextFlag;

    private int lives;
    private int health;

    public Robot(String name, Vector2 startPos, Color color) {
        this.name = name;
        this.backupPos = new Vector2(startPos);
        this.color = color;
        direction = new Vector2(1, 0);
        reset();
    }

    public void reset() {
        registers.clear();
        direction.setAngle(0);
        nextFlag = 1;
        lives = LIVES;
        restoreHealth();
    }

    public void respawn() {
        int nextLives = --lives;
        reset();
        lives = nextLives;
    }

    public boolean isPoweredDown() { return registers.isEmpty(); }

    public void hit() {
        health--;
        System.out.println("Health: " + health);
    }

    public void kill() {
        health = 0;
    }

    public boolean hasWon(){
        return nextFlag > 4;
    }

    public void heal() {
        health++;
    }

    public void restoreHealth() { health = HEALTH; }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
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
        return (registers.size() == REGISTER_SIZE) || isPoweredDown();
    }

    public int cardCount() {
        return registers.size();
    }

    public Card removeCard(int index) {
        return registers.remove(index);
    }
}