package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class TileImporter {
    public static Tile[][] importTiledMapTileLayer(TiledMapTileLayer layer) {
        Vector2 layerSize = new Vector2(layer.getWidth(), layer.getHeight());
        Tile[][] result = new Tile[(int)layerSize.y][(int)layerSize.x];
        for (int y = 0; y < layerSize.y; y++) {
            for (int x = 0; x < layerSize.x; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                int id = (cell == null) ? -1 : cell.getTile().getId();
                result[y][x] = getTile(id);
            }
        }
        return result;
    }

    public static void debugPrint(Tile[][] grid) {
        for (int y = grid.length - 1; y >= 0; y--) {
            for (int x = 0; x < grid[0].length; x++) {
                System.out.printf("%10s, ", grid[y][x].kind.toString());
            }
            System.out.println("\n\n");
        }
    }


    private static Tile getTile(int id) {
        if (id <= 0 || id > tiles.length)
            return new Tile(TileKind.empty);
        return tiles[id - 1];
    }

    /**
     * This is a lookup table for all tiles in the spritesheet.
     * We need this to map tile-attributes to our Tile representation.
     * This should be the optimal solution to this mapping problem:
     * - constant lookup time -> O(n) map import
     * - reuse of tile objects within each map, and between maps
     * - just one line of code per tile variation, no map-layer dependencies
     *
     * "Double" walls/lasers/belts must be marked as such (isDouble=true).
     *
     * Direction of double walls => the "clock-wise" direction
     * Ex.: north-west -> north -> up
     *
     * Direction of turning belts => the final direction
     * Rotation of turning belts => +-90, counter-clockwise is positive
     */
    private static final Tile[] tiles = {

            // 1st row
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.hole),
            new Tile(TileKind.wrench, 1),
            new Tile(TileKind.wall, Direction.DOWN, true),

            // 2nd row
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.belt, Direction.UP, true),
            new Tile(TileKind.belt, Direction.RIGHT, true),
            new Tile(TileKind.wrench, 0),
            new Tile(TileKind.wall, Direction.RIGHT, true),

            // 3rd row
            new Tile(TileKind.belt, Direction.DOWN, 90, 1),
            new Tile(TileKind.belt, Direction.UP, 90, 1),
            new Tile(TileKind.belt, Direction.RIGHT, -90, 1),
            new Tile(TileKind.belt, Direction.DOWN, -90, 1),
            new Tile(TileKind.belt, Direction.DOWN, true),
            new Tile(TileKind.belt, Direction.LEFT, true),
            new Tile(TileKind.wall, Direction.RIGHT, false),
            new Tile(TileKind.wall, Direction.UP, true),

            // 4th row
            new Tile(TileKind.belt, Direction.RIGHT, 90, 1),
            new Tile(TileKind.belt, Direction.UP, 90, 1),
            new Tile(TileKind.belt, Direction.UP, -90, 1),
            new Tile(TileKind.belt, Direction.LEFT, -90, 1),
            new Tile(TileKind.wall, Direction.DOWN, false),
            new Tile(TileKind.wall, Direction.LEFT, false),
            new Tile(TileKind.wall, Direction.UP, false),
            new Tile(TileKind.wall, Direction.LEFT, true),

            // 5th row
            new Tile(TileKind.belt, Direction.DOWN, 90, 0),
            new Tile(TileKind.belt, Direction.LEFT, 90, 0),
            new Tile(TileKind.belt, Direction.RIGHT, -90, 0),
            new Tile(TileKind.belt, Direction.DOWN, -90, 0),
            new Tile(TileKind.laserWall, Direction.DOWN, false),
            new Tile(TileKind.laserWall, Direction.LEFT, false),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),

            // 6th row
            new Tile(TileKind.belt, Direction.RIGHT, 90, 0),
            new Tile(TileKind.belt, Direction.UP, 90, 0),
            new Tile(TileKind.belt, Direction.UP, -90, 0),
            new Tile(TileKind.belt, Direction.LEFT, -90, 0),
            new Tile(TileKind.laserWall, Direction.UP, false),
            new Tile(TileKind.laserWall, Direction.RIGHT, false),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),

            // 7th row
            new Tile(TileKind.belt, Direction.UP, false),
            new Tile(TileKind.belt, Direction.DOWN, false),
            new Tile(TileKind.belt, Direction.LEFT, false),
            new Tile(TileKind.belt, Direction.RIGHT, false),
            new Tile(TileKind.gear, null, 90, 0),
            new Tile(TileKind.gear, null, -90, 0),
            new Tile(TileKind.flag, null, 0, 1),
            new Tile(TileKind.empty),

            // 8th row
            // not quite sure what to do with these belt-junctions
            new Tile(TileKind.belt, Direction.UP, false),
            new Tile(TileKind.belt, Direction.RIGHT, false),
            new Tile(TileKind.belt, Direction.DOWN, false),
            new Tile(TileKind.belt, Direction.LEFT, false),
            new Tile(TileKind.belt, Direction.RIGHT, false),
            new Tile(TileKind.belt, Direction.DOWN, false),
            new Tile(TileKind.flag, 2),
            new Tile(TileKind.empty),

            // 9th row
            new Tile(TileKind.belt, Direction.UP, false),
            new Tile(TileKind.belt, Direction.RIGHT, false),
            new Tile(TileKind.belt, Direction.DOWN, false),
            new Tile(TileKind.belt, Direction.LEFT, false),
            new Tile(TileKind.belt, Direction.UP, false),
            new Tile(TileKind.belt, Direction.LEFT, false),
            new Tile(TileKind.flag, 3),
            new Tile(TileKind.empty),

            // 10th row
            new Tile(TileKind.belt, Direction.UP, true),
            new Tile(TileKind.belt, Direction.RIGHT, true),
            new Tile(TileKind.belt, Direction.DOWN, true),
            new Tile(TileKind.belt, Direction.LEFT, true),
            new Tile(TileKind.belt, Direction.UP, true),
            new Tile(TileKind.belt, Direction.RIGHT, true),
            new Tile(TileKind.flag, 4),
            new Tile(TileKind.empty),

            // 11th row
            new Tile(TileKind.belt, Direction.RIGHT, true),
            new Tile(TileKind.belt, Direction.DOWN, true),
            new Tile(TileKind.belt, Direction.LEFT, true),
            new Tile(TileKind.belt, Direction.UP, true),
            new Tile(TileKind.belt, Direction.LEFT, true),
            new Tile(TileKind.belt, Direction.DOWN, true),
            new Tile(TileKind.laserWall, Direction.DOWN, true),
            new Tile(TileKind.empty),

            // 12th row
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.empty),
            new Tile(TileKind.laserWall, Direction.LEFT, true),
            new Tile(TileKind.laserWall, Direction.UP, true),
            new Tile(TileKind.laserWall, Direction.RIGHT, true),
            new Tile(TileKind.empty),
    };
}
