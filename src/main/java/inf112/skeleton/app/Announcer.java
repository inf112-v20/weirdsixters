package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Queue;

public class Announcer {
    private static final double DURATION = 3.0;

    private Queue<Announcement> announcements = new Queue<>();
    private double time, lastDuration;

    public void announce(String text) {
        hasten();
        announcements.addLast(new Announcement(text, DURATION));
        lastDuration = 0;
    }

    public void update(double time) {
        double dt = time - this.time;
        this.time = time;
        if (announcements.isEmpty())
            return;

        lastDuration += dt;
        if (lastDuration > announcements.first().duration)
            announcements.removeFirst();
    }

    public void draw(Renderer rnd) {
        if (announcements.isEmpty())
            return;
        Announcement a = announcements.first();
        rnd.drawAnnouncement(a.text, Color.RED);
    }

    private void hasten() {
        announcements.forEach(a -> a.duration = Math.min(a.duration, 0.5));
    }

    class Announcement {
        final String text;
        double duration;

        public Announcement(String text, double duration) {
            this.text = text;
            this.duration = duration;
        }
    }
}
