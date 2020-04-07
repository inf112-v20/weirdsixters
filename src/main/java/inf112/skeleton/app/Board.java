package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Board {
    public final int width, height, size;

    private Robot[][] robotGrid;
    private Tile[][] tileGrid;
    private ArrayList<Vector2> belts, flags;

    /*
        public methods
     */

    public Board(Tile[][] tileGrid) {
        height = tileGrid.length;
        width = tileGrid[0].length;
        size = width * height;
        this.tileGrid = tileGrid;
        robotGrid = new Robot[height][width];

        // process tiles
        belts = new ArrayList<>();
        flags = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vector2 pos = new Vector2(x, y);
                Tile tile = getTile(pos);
                switch (tile.kind) {
                    case belt: belts.add(pos); break;
                    case flag: flags.add(pos); break;
                }
            }
        }
    }

    public Robot addRobot(int x, int y) {
        Robot robot = new Robot(new Vector2(x, y), Color.RED);
        assert(robotGrid[y][x] == null);
        robotGrid[y][x] = robot;
        return robot;
    }

    public Robot getRobot(int x, int y) {
        return isInside(x, y) ? robotGrid[y][x] : null;
    }

    public Robot getRobot(Vector2 pos) {
        return getRobot((int)pos.x, (int)pos.y);
    }

    public Vector2 getRobotPosition(Robot robot) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (robotGrid[y][x] == robot)
                    return new Vector2(x, y);
            }
        }
        return null;
    }

    public Tile getTile(int x, int y) {
        return isInside(x, y) ? tileGrid[y][x] : null;
    }

    public Tile getTile(Vector2 pos) {
        return getTile((int)pos.x, (int)pos.y);
    }

    /**
     * @summary Perform the action of moving a robot from (x1,y1) to (x2,y2).
     * @return false when the entire move is blocked.
     */
    public boolean move(int x1, int y1, int dx, int dy) {
        int x2 = x1 + dx;
        int y2 = y1 + dy;

        Robot r1 = getRobot(x1, y1);
        if (r1 == null)
            return true;
        if (!canMove(x1, y1, dx, dy))
            return false;

        Robot r2 = getRobot(x2, y2);
        if (r2 != null) {
            if (!move(x2, y2, dx, dy))
                return false;
        }
        moveRobotFromTo(x1, y1, x2, y2);
        return true;
    }

    public void updateBelts() {
        // move actions must be queued to avoid them affecting each other
        ArrayList<MoveAction> moves = new ArrayList<>();
        for (Vector2 pos : belts) {
            Robot robot = getRobot(pos);
            if (robot == null)
                continue;
            Tile tile = getTile(pos);
            Vector2 dir = tile.direction.toVector2();
            moves.add(new MoveAction(pos, dir));
        }
        for (MoveAction ma : moves)
            move(ma.x, ma.y, ma.dx, ma.dy);
    }

    public void registerFlags() {
        for (Vector2 pos : flags) {
            Robot robot = getRobot(pos);
            if (robot == null)
                continue;
            Tile tile = getTile(pos);
            assert(tile.kind == TileKind.flag);
            int flag = tile.level + 1;
            if (flag != robot.nextFlag)
                return;
            robot.nextFlag++;
            System.out.println("You've landed on flag " + flag);
            if (robot.nextFlag == 5)
                System.out.println("You've won!");
            else
                System.out.println("The next flag you need is " + robot.nextFlag);
        }
    }

    /*
        private methods
     */

    private boolean canMove(int x, int y, int dx, int dy) {
        Tile fromTile = getTile(x, y);
        Vector2 dir = new Vector2(dx, dy);
        if (fromTile.blocksDir(dir, false))
            return false;
        Tile toTile = getTile(x+dx, y+dy);
        return (toTile == null) || !toTile.blocksDir(dir, true);
    }

    private void moveRobotFromTo(int x1, int y1, int x2, int y2) {
        Robot robot = getRobot(x1, y1);
        if (robot == null)
            return;
        robotGrid[y1][x1] = null;
        if (!isInside(x2, y2) || getTile(x2, y2).kind == TileKind.hole) {
            resetRobotPosition(robot);
            return;
        }
        robotGrid[y2][x2] = robot;
    }

    // FIXME, doesn't handle being reset on top of another robot
    private void resetRobotPosition(Robot robot) {
        int x = (int)robot.startPos.x;
        int y = (int)robot.startPos.y;
        assert(robotGrid[y][x] == null);
        robotGrid[y][x] = robot;
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < tileGrid[0].length && y < tileGrid.length;
    }

    /*
        private classes
     */

    private class MoveAction {
        public int x;
        public int y;
        public int dx;
        public int dy;

        public MoveAction(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        public MoveAction(Vector2 pos, Vector2 dir) {
            this((int)pos.y, (int)pos.x, (int)dir.x, (int)dir.y);
        }
    }
}

