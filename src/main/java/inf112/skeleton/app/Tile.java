package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

enum TileKind {
    empty, belt, flag, gear, hole, laserWall, spawn, wall, wrench
}

/**
 * Tile is a representation of a board tile.
 * It contains all the attributes a tile can have.
 *
 * The goal is to base actions on tile-attributes instead of tile-kinds, and
 * maybe remove TileKind completely. This may be achieved by making the
 * Tile-class contain all unique attributes of all kinds of tiles, and
 * "ask questions" based on those attributes.
 * Ex.:
 * - Is this tile blocking this direction?
 * - How much will this tile rotate me?
 * - How fast (if at all) will this tile push me forward?
 *
 * Right-angled walls have two directions. These are stored in
 * direction and secondDirection. `direction` is the clockwise one, while
 * `secondDirection` is the counter-clockwise one.
 */
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
        this.rotation = degrees;
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
