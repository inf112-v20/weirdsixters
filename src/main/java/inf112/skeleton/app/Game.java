package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class Game extends InputAdapter implements ApplicationListener {
    private Renderer renderer;
    private Robot robot;
    private Board board;
    private Deck deck;
    private Player player;

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
        robot = new Robot(new Vector2(0,0));
        player = new Player(robot);

        startRound();

        // debug
        //TileImporter.debugPrint(tileGrid);
        //Card.debugPrint();
    }

    private void startRound() {
        dealCards(deck, player);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void loseCondition(){
        Tile tile = board.getTile(robot.transform.position);
        if(tile == null){
            robot.resetPosition();
            msg("You went outside the board");
        }
        else if(tile.kind == TileKind.hole){
            robot.resetPosition();
            msg("Ouch, you entered a hole!");
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
        renderer.drawRobot(robot.transform);
        for (int i = 0; i < robot.registers.size(); i++)
            renderer.drawCard(robot.registers.get(i), 0, i);
        for (int i = 0; i < player.cards.size(); i++)
            renderer.drawCard(player.cards.get(i), 1, i);
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
            case Input.Keys.G: robot.dealDamage(); break;

            // movement via cards
            case Input.Keys.W: executeCard(new Card(CardKind.FORWARD, 2, 0)); break;
            case Input.Keys.S: executeCard(new Card(CardKind.REVERSE, 1, 0)); break;
            case Input.Keys.D: executeCard(new Card(CardKind.TURN_RIGHT, 1, 0)); break;
            case Input.Keys.A: executeCard(new Card(CardKind.TURN_LEFT, 1, 0)); break;
            case Input.Keys.F: executeCard(new Card(CardKind.FLIP, 2, 0)); break;
        }
        movePlayer(deltaPos);
        return true;
    }

    /**
     * stages card on given index
     * @param index of card to be staged in player.cards
     */
    private void stageCard(int index) {
        if (index >= player.cards.size()) return;
        robot.registers.add(player.cards.remove(index));
    }

    /**
     * un-stages card on given index
     * @param index of card to be un-staged from robot.registers
     */
    private void unstageCard(int index) {
        if (index >= robot.registers.size()) return;
        player.cards.add(robot.registers.remove(index));
    }

    /**
     * checks the action of given card
     * @param card card to check
     */
    private void executeCard(Card card) {
        switch(card.kind) {
            case FORWARD:
                for (int i = 0; i < card.steps; i++) {
                    movePlayer(Linear.scl(robot.transform.direction, 1));
                }
                break;
            case REVERSE:
                movePlayer(Linear.scl(robot.transform.direction, -1));
                break;
            case TURN_RIGHT:
                robot.transform.direction.rotate90(-1);
                break;
            case TURN_LEFT:
                robot.transform.direction.rotate90(1);
                break;
            case FLIP:
                robot.transform.direction.rotate(180f);
                break;
            default:
                break;
        }
    }

    private void movePlayer(Vector2 deltaPos) {
        Vector2 pos = robot.transform.position;
        Vector2 dir = Linear.nor(deltaPos);
        Tile tile = board.getTile(pos);
        if(tile == null || tile.kind == TileKind.hole){
            return;
        }
        if (board.canMovePiece(pos, dir))
            pos.add(deltaPos);
    }

    private void updateFlags() {
        Vector2 robotPosition = robot.transform.position;
        Tile tile = board.getTile(robotPosition);
        if (tile.kind == TileKind.flag && tile.level == robot.nextFlag) {
            robot.nextFlag++;
            if (tile.kind == TileKind.flag && tile.level == (robot.nextFlag-1))
                msg("You've landed on a flag nr: " + (robot.nextFlag-1) + ", the next flag you need is flag nr: " + robot.nextFlag + "");
            if (robot.nextFlag == 5)
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
        Vector2 robotPosition = robot.transform.position;
        Tile tile = board.getTile(robotPosition);
        if (tile.kind == TileKind.belt) {
            movePlayer(tile.direction.toVector2());
        }
    }

    private static void msg(String text) {
        System.out.println(text);
    }
}
