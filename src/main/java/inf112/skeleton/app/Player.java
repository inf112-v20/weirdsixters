package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Player {
    public final int number;
    public final Robot robot;

    public ArrayList<Card> cards = new ArrayList<>();
    public boolean committed;

    public Player(int number, Robot robot) {
        this.number = number;
        this.robot = robot;
    }
}
