package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Announcer {
    private static final float DURATION = 3.0f;

    private ArrayList<Announcement> announcements = new ArrayList<>();
    private double time;

    public void announce(String text) {
        announcements.add(new Announcement(text, time));
    }

    public void update(double time) {
        this.time = time;
        for (int i = announcements.size()-1; i >= 0; i--) {
            Announcement a = announcements.get(i);
            if ((time - a.time) > DURATION)
                del(i);
        }
    }

    public void draw(Renderer rnd) {
        for (Announcement a : announcements)
            rnd.drawAnnouncement(a.text, Color.RED);
    }

    // move last elem to elem at @i
    private void del(int i) {
        int hi = announcements.size() - 1;
        announcements.set(i, announcements.get(hi));
        announcements.remove(hi);
    }

    class Announcement {
        final String text;
        final double time;

        public Announcement(String text, double time) {
            this.text = text;
            this.time = time;
        }
    }
}
