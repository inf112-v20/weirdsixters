package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

enum TileKind {
    empty, belt, flag, gear, hole, laserWall, wall, wrench
}

public class Tile {

    // one bit per direction
    private class DirectionBit {
        public static final int RIGHT = 0b0001;
        public static final int UP    = 0b0010;
        public static final int LEFT  = 0b0100;
        public static final int DOWN  = 0b1000;
    }

    // right-angled walls
    private final static int CORNER_MASKS[] = {
            (DirectionBit.RIGHT & DirectionBit.UP),
            (DirectionBit.UP    & DirectionBit.LEFT),
            (DirectionBit.LEFT  & DirectionBit.DOWN),
            (DirectionBit.DOWN  & DirectionBit.RIGHT)};

    // all possible wall-direction combinations
    // matches the order in which tile-values are imported
    private final static int DIRECTION_MASKS[] = {
            DirectionBit.RIGHT, DirectionBit.UP, DirectionBit.LEFT, DirectionBit.DOWN,
            CORNER_MASKS[0], CORNER_MASKS[1], CORNER_MASKS[2], CORNER_MASKS[3]};

    // public fields
    // guaranteed immutable through "final", no getters needed.
    public final TileKind kind;
    public final int value;
    public final Vector2 position, direction;

    public Tile(TileKind kind, Vector2 position) {
        this(kind, 0, position, new Vector2(0,0));
    }

    public Tile(TileKind kind, int value, Vector2 position) {
        this(kind, value, position, new Vector2(0,0));
    }

    public Tile(TileKind kind, int value, Vector2 position, Vector2 direction) {
        this.kind = kind;
        this.value = value;
        this.position = position;
        this.direction = direction;
    }

    /**
     * @summary Gear rotation "direction", etc.
     * @return -1 or +1
     */
    public float sign() {
        assert(value >= 0 && value <= 1);
        return (value == 0) ? -1 : 1;
    }

    /**
     * @return true if the tile represents two walls/lasers.
     */
    public boolean isDouble() {
        return value > 3;
    }

    /**
     * @summary Treats tile-value as a direction and compares @fromDir with
     * the tile's blocking direction.
     * @param fromDir is the direction from the caller to the tile
     * @return true if this tile blocks movement from @fromDir
     */
    public boolean isBlocking(Vector2 fromDir) {
        if (kind != TileKind.wall && kind != TileKind.laserWall)
            return false;
        Vector2 toCaller = Linear.neg(fromDir);
        int toCallerMask = directionMask(toCaller);
        int blockDirMask = DIRECTION_MASKS[value];
        return (toCallerMask & blockDirMask) > 0;
    }

    private int directionMask(Vector2 dir) {
        assert(Linear.isUnit(dir));
        if (dir.x == 1)
            return DirectionBit.RIGHT;
        if (dir.y == 1)
            return DirectionBit.UP;
        if (dir.x == -1)
            return DirectionBit.LEFT;
        if (dir.y == -1)
            return DirectionBit.DOWN;
        return 0;
    }
}
