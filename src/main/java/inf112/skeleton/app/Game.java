package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Game extends InputAdapter implements ApplicationListener {
    private Renderer renderer;
    private Board board;
    private Deck deck;
    private Player player1;
    private ArrayList<Robot> robots = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    private long lastTime;
    private long secondTimer;

    @Override
    public void create() {
        lastTime = System.currentTimeMillis();
        Gdx.input.setInputProcessor(this);

        TiledMap map = new TmxMapLoader().load("newBoard.tmx");
        TiledMapTileLayer objLayer = (TiledMapTileLayer)map.getLayers().get("Tiles");
        Tile[][] tileGrid = TileImporter.importTiledMapTileLayer(objLayer);
        renderer = Renderer.create(map);

        board = new Board(tileGrid);
        deck = new Deck(Card.programCards);

        player1 = addPlayer(new Vector2(1,0));
        addPlayer(new Vector2(0,4));
        addPlayer(new Vector2(0,5));

        startRound();

        // debug
        //TileImporter.debugPrint(tileGrid);
        //Card.debugPrint();
    }

    private Player addPlayer(Vector2 pos) {
        Color color1 = Color.RED;
        Robot robot = new Robot(pos, color1);
        assert(robot.color == color1);
        System.out.println("The robot is " + robot.color.toString()); //Prints color-code
        robots.add(robot);
        board.addRobot(robot, (int)pos.x, (int)pos.y);
        Player player = new Player(robot);
        players.add(player);
        return player;
    }

    private void startRound() {
        for(Player p: players){
            dealCards(deck, p);
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void loseCondition(){
        for(Robot robot: robots) {
            Tile tile = board.getTile(robot.transform.position);
            if (tile == null) {
                robot.resetPosition();
                msg("You went outside the board");
            } else if (tile.kind == TileKind.hole) {
                robot.resetPosition();
                msg("Ouch, you entered a hole!");
            }
        }
    }

    @Override
    public void render() {
        loseCondition();
        updateFlags();

        long time = System.currentTimeMillis();
        long deltaTime = time - lastTime;
        lastTime = time;
        secondTimer += deltaTime;
        if (secondTimer > 1000){
            secondTimer = 0;
            moveRobotsOnBelts();
        }

        renderer.begin();
        for (Robot robot: robots) {
            renderer.drawRobot(robot.transform);
            for (int i = 0; i < robot.registers.size(); i++)
                renderer.drawCard(robot.registers.get(i), 0, i);
        }
        for (Player player: players) {
            for (int i = 0; i < player.cards.size(); i++)
                renderer.drawCard(player.cards.get(i), 1, i);
        }
        renderer.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyDown(int key) {

        // stage/unstage card
        if (key >= Input.Keys.NUM_1 && key <= Input.Keys.NUM_9) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
                unstageCard(key - Input.Keys.NUM_1);
            else
                stageCard(key - Input.Keys.NUM_1);
        }

        // debug movement and damage actions
        Vector2 deltaPos = new Vector2(0,0);
        switch (key){
            case Input.Keys.RIGHT: deltaPos.x++; break;
            case Input.Keys.LEFT: deltaPos.x--; break;
            case Input.Keys.UP: deltaPos.y++; break;
            case Input.Keys.DOWN: deltaPos.y--; break;
            case Input.Keys.G: player1.robot.dealDamage(); break;

            // movement via cards
            case Input.Keys.W: executeCard(new Card(CardKind.FORWARD, 2, 0)); break;
            case Input.Keys.S: executeCard(new Card(CardKind.REVERSE, 1, 0)); break;
            case Input.Keys.D: executeCard(new Card(CardKind.TURN_RIGHT, 1, 0)); break;
            case Input.Keys.A: executeCard(new Card(CardKind.TURN_LEFT, 1, 0)); break;
            case Input.Keys.F: executeCard(new Card(CardKind.FLIP, 2, 0)); break;
        }
        if (deltaPos.len() != 0)
            movePlayer(deltaPos);
        return true;
    }

    /**
     * stages card on given index
     * @param index of card to be staged in player.cards
     */
    private void stageCard(int index) {
        if (index >= player1.cards.size()) return;
        player1.robot.registers.add(player1.cards.remove(index));
    }

    /**
     * un-stages card on given index
     * @param index of card to be un-staged from robot.registers
     */
    private void unstageCard(int index) {
        if (index >= player1.robot.registers.size()) return;
        player1.cards.add(player1.robot.registers.remove(index));
    }

    /**
     * checks the action of given card
     * @param card card to check
     */
    private void executeCard(Card card) {
        switch(card.kind) {
            case FORWARD:
                for (int i = 0; i < card.steps; i++) {
                    movePlayer(Linear.scl(player1.robot.transform.direction, 1));
                }
                break;
            case REVERSE:
                movePlayer(Linear.scl(player1.robot.transform.direction, -1));
                break;
            case TURN_RIGHT:
                player1.robot.transform.direction.rotate90(-1);
                break;
            case TURN_LEFT:
                player1.robot.transform.direction.rotate90(1);
                break;
            case FLIP:
                player1.robot.transform.direction.rotate(180f);
                break;
            default:
                break;
        }
    }

    private void movePlayer(Vector2 deltaPos) {
        Vector2 pos = player1.robot.transform.position;
        Vector2 dir = Linear.nor(deltaPos);
        Tile tile = board.getTile(pos);
        if(tile == null || tile.kind == TileKind.hole){
            return;
        }
        board.move((int)pos.x, (int)pos.y, (int)dir.x, (int)dir.y);
    }

    private void updateFlags() {
        Vector2 robotPosition = player1.robot.transform.position;
        Tile tile = board.getTile(robotPosition);
        assert(tile != null);
        if (tile.kind == TileKind.flag && tile.level == player1.robot.nextFlag) {
            player1.robot.nextFlag++;
            if (tile.kind == TileKind.flag && tile.level == (player1.robot.nextFlag-1))
                msg("You've landed on a flag nr: " + (player1.robot.nextFlag-1) + ", the next flag you need is flag nr: " + player1.robot.nextFlag + "");
            if (player1.robot.nextFlag == 5)
                msg("You've won!");
        }
    }

    private static void dealCards(Deck deck, Player player) {
        player.cards = deck.drawCards(9);
        msg("dealing cards to player " + player.number + ":");
        for (Card c : player.cards)
            msg(c.toString());
    }

    private void moveRobotsOnBelts() {
        Vector2 robotPosition = player1.robot.transform.position;
        Tile tile = board.getTile(robotPosition);
        if (tile.kind == TileKind.belt) {
            movePlayer(tile.direction.toVector2());
        }
    }

    private static void msg(String text) {
        System.out.println(text);
    }
}
