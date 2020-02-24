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
        Tile toTile = getTile(toPos);
        return !fromTile.blocksDir(dir, false) && !toTile.blocksDir(dir, true);
    }

    private Tile getTile(Vector2 pos) {
        int x = (int)pos.x;
        int y = (int)pos.y;
        return grid[y][x];
    }
}
