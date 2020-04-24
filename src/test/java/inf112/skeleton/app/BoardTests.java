package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTests {

    @Test
    public void blueBeltTurnsLeft() {
        Tile[][] grid = new Tile[2][2];

        grid[1][1] = TileImporter.getTile(18);
        grid[0][0] = TileImporter.getTile(1);
        grid[1][0] = TileImporter.getTile(1);
        grid[0][1] = TileImporter.getTile(1);
        
        Board board = new Board(grid);
        Robot robot = board.addRobot(1, 0);
        board.move(1,0,0,1);

        board.updateBelts();

        Vector2 pos = board.getRobotPosition(robot);
        assertEquals(new Vector2(0,1), pos);
    }

    @Test
    public void blueBeltMovesRobotRight() {
        Tile[][] grid = new Tile[2][2];

        grid[0][0] = TileImporter.getTile(14);
        grid[0][1] = TileImporter.getTile(1);
        grid[1][0] = TileImporter.getTile(14);
        grid[1][1] = TileImporter.getTile(1);
        Board board = new Board(grid);
        Robot robot1 = board.addRobot(0, 0);
        Robot robot2 = board.addRobot(0, 1);

        board.updateBelts();

        assertEquals(new Vector2(1,0), board.getRobotPosition(robot1));
        assertEquals(new Vector2(1,1), board.getRobotPosition(robot2));
    }
}
