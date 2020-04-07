package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Board {
    public final int width, height, size;

    private Robot[][] robotGrid;
    private Tile[][] tileGrid;

    /*
        public methods
     */

    public Board(Tile[][] tileGrid) {
        height = tileGrid.length;
        width = tileGrid[0].length;
        size = width * height;
        this.tileGrid = tileGrid;
        this.robotGrid = new Robot[height][width];
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

    public Vector2 getRobotPosition(Robot robot) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (robotGrid[y][x] == robot)
                    return new Vector2(x, y);
            }
        }
        return null;
    }

    /*public Robot getRobot(int i) {
        return getRobot(i%width, i/height);
    }*/

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

    public void update() {
        // move actions must be queued to avoid them affecting each other
        ArrayList<MoveAction> moveActions = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Robot robot = getRobot(x, y);
                if (robot == null)
                    continue;
                Tile tile = getTile(x, y);
                Vector2 dir = tile.direction.toVector2();
                int dx = (int)dir.x;
                int dy = (int)dir.y;
                switch (tile.kind) {
                    case belt:
                        moveActions.add(new MoveAction(x, y, dx, dy));
                        break;
                    case flag:
                        updateFlag(tile, robot);
                        break;
                }
            }
        }
        for (MoveAction ma : moveActions)
            move(ma.x, ma.y, ma.dx, ma.dy);
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

    private void resetRobotPosition(Robot robot) {
        int x = (int)robot.startPos.x;
        int y = (int)robot.startPos.y;
        assert(robotGrid[y][x] == null);
        robotGrid[y][x] = robot;
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < tileGrid[0].length && y < tileGrid.length;
    }

    private void updateFlag(Tile tile, Robot robot) {
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
    }
}

