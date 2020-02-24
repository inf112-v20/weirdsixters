package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

enum TileKind {
    empty, belt, flag, gear, hole, laserWall, wall, wrench
}

public class Tile {
    public final TileKind kind;
    public final int level; // belt speed, laser count, wall count, flag number
    public final Direction direction, secondDirection; // belts, walls
    public final float rotation; // turning belts, gears

    /**
     * Constructs a tile with no attributes: empty, hole
     */
    public Tile(TileKind kind) {
        this(kind, null, 0, 0);
    }

    /**
     * Constructs a tile with a level attribute: flag, wrench
     */
    public Tile(TileKind kind, int level) {
        this(kind, null, 0, level);
    }

    /**
     * Constructs a tile with a direction and a binary attribute: belt, laserWall, wall
     */
    public Tile(TileKind kind, Direction dir, boolean isDouble) {
        this(kind, dir, 0, isDouble ? 1 : 0);
    }

    public Tile(TileKind kind, Direction dir, int degrees, int level) {
        if (dir == null)
            dir = Direction.RIGHT;

        this.kind = kind;
        this.level = level;
        this.rotation = (float)Math.toRadians(degrees);
        this.direction = dir;
        this.secondDirection = Direction.fromVector2(dir.toVector2().rotate(90));
    }

    public boolean blocksDir(Vector2 dir, boolean incoming) {
        if (!canBlock())
            return false;
        if (incoming)
            dir = Linear.neg(dir);
        Direction enumDir = Direction.fromVector2(dir);
        return direction == enumDir || (level > 0 && secondDirection == enumDir);
    }

    private boolean canBlock() {
        return kind == TileKind.wall || kind == TileKind.laserWall;
    }
}
