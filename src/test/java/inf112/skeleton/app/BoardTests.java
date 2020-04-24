package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTests {

    @Test
    public void belts_blueturn_works() {
        Tile[][] grid = new Tile[2][2];

        grid[1][1] = TileImporter.getTile(10);
        grid[0][0] = TileImporter.getTile(1);
        grid[1][0] = TileImporter.getTile(1);
        grid[0][1] = TileImporter.getTile(1);
        Board board = new Board(grid);
        Robot robot = board.addRobot(1, 0);
        board.move(1,0,1,1);
        board.updateBelts();
        Vector2 pos = board.getRobotPosition(robot);
        assertEquals(new Vector2(0,1), pos);
    }
}
