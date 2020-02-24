package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

enum TileKind {
    empty, belt, flag, gear, hole, laserWall, wall, wrench
}

public class Tile {
    public final TileKind kind;
    public final int level; // belt speed, laser count, wall count, flag number
    public final Vector2 direction; // belts, walls
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
    public Tile(TileKind kind, Vector2 direction, boolean isDouble) {
        this(kind, direction, 0, isDouble ? 1 : 0);
    }

    public Tile(TileKind kind, Vector2 direction, int degrees, int level) {
        this.kind = kind;
        this.direction = direction;
        this.level = level;
        this.rotation = (float)Math.toRadians(degrees);
    }

    /**
     * @return true if @tile at @tilePos is blocking @queryPos from moving in @queryDir
     */
    public static boolean isBlocking(Tile tile, Vector2 tilePos,
                                     Vector2 queryPos, Vector2 queryDir) {
        assert Linear.isUnit(queryDir);
        if (!tile.canBlock())
            return false;
        Vector2[] blockDirs = tile.blockDirections();
        for (Vector2 blockDir : blockDirs) {
            if (blockDir == null)
                continue;
            if (sameTile(tilePos, queryPos)) {
                if (blockDir == queryDir)
                    return true;
            } else if(Linear.neg(blockDir) == queryDir) {
                return true;
            }
        }
        return false;
    }

    // returns true if positions a and b are on the same tile
    private static boolean sameTile(Vector2 a, Vector2 b) {
        return Linear.floor(a) == Linear.floor(b);
    }

    /**
     * @return array of two directions, relative to tile, which may be null
     */
    private Vector2[] blockDirections() {
        Vector2[] result = new Vector2[2];
        if (canBlock()) {
            result[0] = direction;

            // second wall direction is counter-clockwise from direction
            if (level > 0 && kind != TileKind.laserWall)
                result[1] = direction.rotate(90);
        }
        return result;
    }

    private boolean canBlock() {
        return kind == TileKind.wall || kind == TileKind.laserWall;
    }
}
