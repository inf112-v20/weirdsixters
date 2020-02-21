package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import org.apache.commons.lang3.ArrayUtils;

public class TileUtils {

    // right, up, left, down
    // low, high
    private static final int holeId = 5;
    private static final int[] gearIds = {52, 53};
    private static final int[] laserWallIds = {45, 44, 37, 36, 94, 93, 92, 86};
    private static final int[] wallIds = {22, 30, 29, 28, 15, 23, 31, 7};
    private static final int[] wrenchIds = {14, 6};

    public static Tile[][] importTiledMapTileLayer(TiledMapTileLayer layer) {
        Vector2 layerSize = new Vector2(layer.getWidth(), layer.getHeight());
        Tile[][] result = new Tile[(int)layerSize.y][(int)layerSize.x];
        for (int y = 0; y < layerSize.y; y++) {
            for (int x = 0; x < layerSize.x; x++) {
                TiledMapTile tile = layer.getCell(x, y).getTile();
                Vector2 position = new Vector2(x, y);
                if (tile == null)
                    result[y][x] = createEmptyTile(position);
                result[y][x] = createTile(position, tile.getId());
            }
        }
        return result;
    }

    private static Tile createEmptyTile(Vector2 position) {
        return new Tile(TileKind.empty, position);
    }

    // constructs Tile from tile id (spritesheet index)
    private static Tile createTile(Vector2 position, int id) {
        int ix;
        if (id == holeId)
            return new Tile(TileKind.hole, position);

        ix = ArrayUtils.indexOf(wrenchIds, id);
        if (ix > 0)
            return new Tile(TileKind.wrench, ix, position);

        ix = ArrayUtils.indexOf(gearIds, id);
        if (ix > 0)
            return new Tile(TileKind.gear, ix, position);

        ix = ArrayUtils.indexOf(wallIds, id);
        if (ix > 0)
            return new Tile(TileKind.wall, ix, position);

        ix = ArrayUtils.indexOf(laserWallIds, id);
        if (ix > 0)
            return new Tile(TileKind.laserWall, ix, position);

        return createEmptyTile(position);
    }
}
