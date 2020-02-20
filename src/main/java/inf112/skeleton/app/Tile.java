package inf112.skeleton.app;

enum TileKind {
    empty, wall, hole, belt, gear, flag, wrench
}

public class Tile {
    private final int value;
    private final TileKind kind;

    public Tile(TileKind kind) {
        this.kind = kind;
        this.value = 0;
    }

    public Tile(TileKind kind, int value) {
        this.kind = kind;
        this.value = value;
    }
}
