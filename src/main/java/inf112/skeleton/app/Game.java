package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Game extends InputAdapter implements ApplicationListener {
    private Renderer renderer;
    private Robot robot;
    private Texture playerTexture;
    private Texture cardTexture;
    private Board board;
    private Deck deck;
    private Player player;

    private ArrayList<Card> playerHand; //to be moved

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        TiledMap map = new TmxMapLoader().load("newBoard.tmx");
        TiledMapTileLayer objLayer = (TiledMapTileLayer)map.getLayers().get("Tiles");
        Tile[][] tileGrid = TileImporter.importTiledMapTileLayer(objLayer);
        renderer = Renderer.create(map);
        playerTexture = new Texture("player.png");
        cardTexture = new Texture("cards.png");

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
            robot.transform.position = new Vector2(robot.startPos);
            System.out.println("You went outside the board");
        }
        else if(tile.kind == TileKind.hole){
            robot.transform.position = new Vector2(robot.startPos);
            System.out.println("Ouch, you entered a hole!");
        }

    }

    @Override
    public void render() {
        loseCondition();
        updateFlags();

        renderer.begin();
        renderer.drawTileSprite(playerTexture, new Vector2(), robot.transform);

        //draw cardslot placeholders
        for (int i = 0; i < player.cards.size(); i++)
            renderer.drawTileSprite(cardTexture, getTexIndex(player.cards.get(i)), new Vector2((float) i, -1), 0);

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
        Vector2 deltaPos = new Vector2(0,0);
        switch (key){
            case Input.Keys.RIGHT: deltaPos.x++; break;
            case Input.Keys.LEFT: deltaPos.x--; break;
            case Input.Keys.UP: deltaPos.y++; break;
            case Input.Keys.DOWN: deltaPos.y--; break;

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
                System.out.println("You've landed on a flag nr: " + (robot.nextFlag-1) + ", the next flag you need is flag nr: " + robot.nextFlag + "");
            if (robot.nextFlag == 5)
                System.out.println("You've won!");
        }
    }

    private static void dealCards(Deck deck, Player player) {
        player.cards = deck.drawCards(9);
        msg("dealing cards to player " + player.number + ":");
        for (Card c : player.cards)
            msg(c.toString());
    }

    /**
     *
     * @param card to get index of
     * @return V2 texIndex of given card in cards.png
     */
    private Vector2 getTexIndex(Card card) {
        switch(card.kind) {
            case FORWARD:
                if (card.steps == 1) return new Vector2();
                if (card.steps == 2) return new Vector2(1, 0);
                return new Vector2(2, 0);
            case REVERSE:
                return new Vector2(3, 0);
            case TURN_RIGHT:
                return new Vector2(5, 0);
            case TURN_LEFT:
                return new Vector2(4, 0);
            case FLIP:
                return new Vector2(6, 0);
            default:
                break;
        }
        System.out.println("unknown card..");
        return new Vector2();
    }

    private static void msg(String text) {
        System.out.println(text);
    }
}
