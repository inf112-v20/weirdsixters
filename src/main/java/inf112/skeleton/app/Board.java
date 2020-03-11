package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

public class Board {
    private Tile[][] grid;

    public Board(Tile[][] grid) {
        this.grid = grid;
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
}
