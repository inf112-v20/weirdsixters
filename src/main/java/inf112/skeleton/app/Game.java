package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Game extends InputAdapter implements ApplicationListener {
    private Renderer renderer;
    private Board board;
    private Deck deck;
    private Player player1;
    private ArrayList<Player> players = new ArrayList<>();

    private long lastTime;
    private long phaseTimer;

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
        Robot robot = board.addRobot((int)pos.x, (int)pos.y);
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

    private void update() {
        long time = System.currentTimeMillis();
        long deltaTime = time - lastTime;
        lastTime = time;
        phaseTimer += deltaTime;
        if (phaseTimer > 1000){
            phaseTimer = 0;
            board.update();
        }
    }

    @Override
    public void render() {
        update();

        renderer.begin();
        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                Robot robot = board.getRobot(x, y);
                if (robot == null)
                    continue;
                renderer.drawRobot(robot, x, y);
                for (int i = 0; i < robot.registers.size(); i++)
                    renderer.drawCard(robot.registers.get(i), 0, i);
            }
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
        Robot robot = player1.robot;
        Vector2 deltaPos = new Vector2(0,0);
        switch (key){
            case Input.Keys.RIGHT: deltaPos.x++; break;
            case Input.Keys.LEFT: deltaPos.x--; break;
            case Input.Keys.UP: deltaPos.y++; break;
            case Input.Keys.DOWN: deltaPos.y--; break;
            case Input.Keys.G: robot.dealDamage(); break;

            // movement via cards
            case Input.Keys.W: executeRobotCard(robot, new Card(CardKind.FORWARD, 2, 0)); break;
            case Input.Keys.S: executeRobotCard(robot, new Card(CardKind.REVERSE, 1, 0)); break;
            case Input.Keys.D: executeRobotCard(robot, new Card(CardKind.TURN_RIGHT, 1, 0)); break;
            case Input.Keys.A: executeRobotCard(robot, new Card(CardKind.TURN_LEFT, 1, 0)); break;
            case Input.Keys.F: executeRobotCard(robot, new Card(CardKind.FLIP , 2, 0)); break;
        }
        if (deltaPos.len() != 0)
            executeMoveAction(robot, deltaPos);

        // reset timer on input to avoid confusion
        phaseTimer = 0;
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
    private void executeRobotCard(Robot robot, Card card) {
        switch(card.kind) {
            case FORWARD:
                for (int i = 0; i < card.steps; i++) {
                    executeMoveAction(robot, Linear.scl(robot.direction, 1));
                }
                break;
            case REVERSE:
                executeMoveAction(robot, Linear.scl(robot.direction, -1));
                break;
            case TURN_RIGHT:
                robot.direction.rotate90(-1);
                break;
            case TURN_LEFT:
                robot.direction.rotate90(1);
                break;
            case FLIP:
                robot.direction.rotate(180f);
                break;
            default:
                break;
        }
    }

    private void executeMoveAction(Robot robot, Vector2 deltaPos) {
        Vector2 pos = board.getRobotPosition(robot);
        Vector2 dir = Linear.nor(deltaPos);
        Tile tile = board.getTile(pos);
        if(tile == null || tile.kind == TileKind.hole){
            return;
        }
        board.move((int)pos.x, (int)pos.y, (int)dir.x, (int)dir.y);
    }

    private static void dealCards(Deck deck, Player player) {
        player.cards = deck.drawCards(9);
        msg("dealing cards to player " + player.number + ":");
        for (Card c : player.cards)
            msg(c.toString());
    }

    private static void msg(String text) {
        System.out.println(text);
    }
}
