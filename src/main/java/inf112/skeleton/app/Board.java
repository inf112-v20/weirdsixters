package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Board {
    public Robot[][] robotGrid;

    private Tile[][] grid;

    public Board(Tile[][] grid) {
        this.grid = grid;
        this.robotGrid = new Robot[grid.length][grid[0].length];
    }

    public void addRobot(Robot r, int x, int y) {
        assert(robotGrid[y][x] == null);
        robotGrid[y][x] = r;
    }

    public boolean canMovePiece(Vector2 fromPos, Vector2 dir) {
        Vector2 toPos = Linear.add(fromPos, dir);
        Tile fromTile = getTile(fromPos);
        if (fromTile.blocksDir(dir, false))
            return false;
        Tile toTile = getTile(toPos);
        return (toTile == null) || !toTile.blocksDir(dir, true);
    }

    public Tile getTile(Vector2 pos) {
        int x = (int)pos.x;
        int y = (int)pos.y;
        if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length)
            return null;
        return grid[y][x];
    }

    private Robot getRobot(int x, int y) {
        if (x < 0 || y < 0 || x >= robotGrid[0].length || y >= robotGrid.length)
            return null;
        return robotGrid[y][x];
    }

    private void moveRobot(int x1, int y1, int x2, int y2) {
        Robot r = getRobot(x1, y1);
        if (r == null)
            return;
        //assert(r != null);
        r.transform.position.add(new Vector2(x2-x1, y2-y1));
        robotGrid[y1][x1] = null;
        if (x2 < 0 || y2 < 0 || x2 >= robotGrid[0].length || y2 >= robotGrid.length)
            return;
        robotGrid[y2][x2] = r;
    }

    public void move(int x1, int y1, int dx, int dy) {
        int x2 = x1 + dx;
        int y2 = y1 + dy;

        Robot r1 = getRobot(x1, y1);
        if (r1 == null)
            return;
        if (!canMovePiece(new Vector2(x1, y1), new Vector2(dx, dy)))
            return;

        Robot r2 = getRobot(x2, y2);
        if (r2 != null)
            move(x2, y2, dx, dy);
        moveRobot(x1, y1, x2, y2);
    }
}
