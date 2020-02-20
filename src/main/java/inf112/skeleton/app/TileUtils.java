package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import org.apache.commons.lang3.ArrayUtils;

public class TileUtils {

    // right, up, left, down
    // low, high
    private static final int holeId = 5;
    private static final int[] wallIds = {22, 30, 29, 28, 15, 23, 31, 7};
    private static final int[] wrenchIds = {14, 6};

    public static Tile[][] importTiledMapTileLayer(TiledMapTileLayer layer) {
        Vector2 layerSize = new Vector2(layer.getWidth(), layer.getHeight());
        Tile[][] result = new Tile[(int)layerSize.y][(int)layerSize.x];
        for (int y = 0; y < layerSize.y; y++) {
            for (int x = 0; x < layerSize.x; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null)
                    result[y][x] = createEmptyTile();
                int id = cell.getTile().getId();
                result[y][x] = createTile(id);
            }
        }
        return result;
    }

    private static Tile createEmptyTile() {
        return new Tile(TileKind.empty, 0);
    }

    private static Tile createTile(int id) {
        int ix;
        if (id == holeId)
            return new Tile(TileKind.hole);
        ix = ArrayUtils.indexOf(wrenchIds, id);
        if (ix > 0)
            return new Tile(TileKind.wrench, ix);
        ix = ArrayUtils.indexOf(wallIds, id);
        if (ix > 0)
            return new Tile(TileKind.wall, ix);
        return createEmptyTile();
    }
}
