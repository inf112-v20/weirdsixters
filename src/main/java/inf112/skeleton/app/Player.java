package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Player {
    public final int number = 1;
    public final Robot robot;

    public ArrayList<Card> cards = new ArrayList<>();
    public boolean committed;

    public Player(Robot robot) {
        this.robot = robot;
    }
}
