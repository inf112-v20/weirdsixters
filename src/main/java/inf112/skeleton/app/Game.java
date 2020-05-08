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

import static java.lang.Thread.sleep;

enum GameState {
    START,
    DEALING_CARDS,
    STAGING_CARDS,
    COMMITTED,
    TURN,
    GAME_OVER,
}

public class Game extends InputAdapter implements ApplicationListener {
    private static final int PHASE_COUNT = 5;

    private Renderer renderer;
    private Board board;
    private Deck deck;
    private Player player1;
    private ArrayList<Player> players = new ArrayList<>();
    private GameState state;
    private int phaseIndex;
    private Announcer announcer = new Announcer();

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        TiledMap map = new TmxMapLoader().load("Board.tmx");
        TiledMapTileLayer objLayer = (TiledMapTileLayer)map.getLayers().get("Tiles");
        Tile[][] tileGrid = TileImporter.importTiledMapTileLayer(objLayer);
        renderer = Renderer.create(width, height, map);

        board = new Board(tileGrid);
        board.onRobotFlag = robot -> announcer.announce(robot.name + " got a flag!");

        deck = new Deck(Card.programCards);

        player1 = addPlayer();
        for (int i = 1; i < 4; i++)
            addPlayer();

        state = GameState.START;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private Player addPlayer() {
        int number = players.size() + 1;
        Robot robot = board.addRobot("Player " + number);
        Player player = new Player(number, robot);
        players.add(player);
        return player;
    }

    private void update() {
        double time = getTime();
        announcer.update(time);

        switch (state) {
            case START:
                resetGame();
                setState(GameState.DEALING_CARDS);
                break;
            case DEALING_CARDS:
                for(Player p : players)
                    dealCards(deck, p);
                setState(GameState.STAGING_CARDS);
                announcer.announce("Programming phase");
                break;
            case STAGING_CARDS:
                if (player1.committed)
                    setState(GameState.COMMITTED);
                break;
            case COMMITTED:
                autoCommitOtherPlayers();
                if (players.stream().allMatch(p -> p.committed)) {
                    setState(GameState.TURN);
                    startTurn();
                }
                break;
            case TURN:
                Player winner = getWinner();
                if (winner != null) {
                    setState(GameState.GAME_OVER);
                    if (winner == player1)
                        announcer.announce("You won!");
                    else
                        announcer.announce("AI won, you lost!");
                    break;
                }
                if (player1.robot.getLives() <= 0) {
                    setState(GameState.GAME_OVER);
                    announcer.announce("You lost!");
                } else if (phaseIndex >= PHASE_COUNT) {
                    endTurn();
                    setState(GameState.DEALING_CARDS);
                } else {
                    doPhase(phaseIndex++);
                }
                break;
            case GAME_OVER:
                delay();
                setState(GameState.START);
                break;
        }
    }

    // kill and reset any robots
    private void resetGame() {
        players.forEach(p -> {
            board.resetRobotPosition(p.robot);
            p.robot.reset();
        });
    }

    private Player getWinner() {
        for (Player p : players) {
            if (p.robot.hasWon())
                return p;
        }
        return null;
    }

    private double getTime() {
        return System.currentTimeMillis() / 1000.0;
    }

    private void startTurn() {
        phaseIndex = 0;
        announcer.announce("Round start");
    }

    private void endTurn() {
        for (Player p : players) {
            p.robot.clearRegisters();
            p.committed = false;
        }
        announcer.announce("Round end");
    }

    private void autoCommitOtherPlayers() {
        for (Player p : players) {
            if (p == player1)
                continue;
            for (int i = 0; i < Robot.REGISTER_SIZE; i++)
                stageCard(p, 0);
            commitCards(p);
        }
    }

    private void setState(GameState state) {
        this.state = state;
        System.out.println("New state: " + state.toString());
    }

    private void doPhase(int index) {
        System.out.println("Phase " + (index + 1));
        delay();

        // TODO: revealCards();
        executeMovementCards(index);
        board.updateBelts();
        rotateGears();
        board.fireLasers();
        board.moveBackup();
        board.registerFlags();
        killDeadRobots();
    }

    private void killDeadRobots() {
        players.forEach(p -> {
            if (p.robot.getHealth() <= 0) {
                board.respawnRobot(p.robot);
                announcer.announce(p.robot.name + " died");
            }
        });
    }

    private void delay() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private void executeMovementCards(int index) {
        for (Player p : players) {
            if (p.robot.isPoweredDown()) {
                p.robot.restoreHealth();
            }
            else executeCard(p.robot, p.robot.getCard(index));
        }
    }

    @Override
    public void render() {
        update();

        drawRobotLives();

        // draw robots
        for (Player player : players) {
            Robot robot = player.robot;
            Vector2 pos = board.getRobotPosition(robot);
            renderer.drawRobot(pos, robot.direction.angle(), robot.color);
        }

        // draw player1's robot registers
        for (int i = 0; i < player1.robot.cardCount(); i++)
            renderer.drawCard(player1.robot.getCard(i), 0, i);

        // draw player1 cards
        for (int i = 0; i < player1.cards.size(); i++)
            renderer.drawCard(player1.cards.get(i), 1, i);

        announcer.draw(renderer);

        board.draw(renderer);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.onWindowResized(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyDown(int key) {
        if (player1.committed)
            return false;

        // stage/unstage card
        if (key >= Input.Keys.NUM_1 && key <= Input.Keys.NUM_9) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
                unstageCard(player1, key - Input.Keys.NUM_1);
            else
                stageCard(player1, key - Input.Keys.NUM_1);
            return true;
        }

        Robot robot = player1.robot;
        switch (key){

            // commit staged cards
            case Input.Keys.ENTER: commitCards(player1); break;

            // inject movement cards (for debugging)
            case Input.Keys.W: robot.addCard(new Card(CardKind.FORWARD, 1, 0)); break;
            case Input.Keys.S: robot.addCard(new Card(CardKind.REVERSE, 1, 0)); break;
            case Input.Keys.A: robot.addCard(new Card(CardKind.TURN_LEFT, 1, 0)); break;
            case Input.Keys.D: robot.addCard(new Card(CardKind.TURN_RIGHT, 1 , 0)); break;
        }
        return true;
    }

    private void commitCards(Player player) {
        if (!player.robot.isReady())
            return;
        player.committed = true;
        System.out.println("Player " + player.number + " committed!");
        if (player.robot.isPoweredDown()) {
            announcer.announce(player.robot.name + " Powered Down");
        }
    }

    /**
     * stages card on given index
     * @param index of card to be staged in player.cards
     */
    private void stageCard(Player player, int index) {
        if (index >= player.cards.size())
            return;
        Card card = player.cards.get(index);
        if (player.robot.addCard(card))
            player.cards.remove(index);
    }

    /**
     * un-stages card on given index
     * @param index of card to be un-staged from robot.registers
     */
    private void unstageCard(Player player, int index) {
        if (index >= player.robot.cardCount())
            return;
        player.cards.add(player.robot.removeCard(index));
    }

    /**
     * checks the action of given card
     * @param card card to check
     */
    private void executeCard(Robot robot, Card card) {
        if (card == null)
            return;
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
        if (pos == null)
            return;
        Vector2 dir = Linear.round(Linear.nor(deltaPos));
        Tile tile = board.getTile(pos);
        if(tile == null || tile.kind == TileKind.hole){
            return;
        }
        board.move((int)pos.x, (int)pos.y, (int)dir.x, (int)dir.y);
    }

    private void rotateGears(){
        for (Player p : players){
            Vector2 pos = board.getRobotPosition(p.robot);
            if (pos == null)
                continue;
            Tile tile = board.getTile(pos);
            if (tile == null)
                continue;
            if (tile.kind == TileKind.gear) {
                p.robot.direction.rotate(tile.rotation);
            }
        }
    }

    private void drawRobotLives(){
        int y = board.height-1;
        for (Player p: players){
            int lives = p.robot.getLives();
            renderer.drawLives(new Vector2(board.width, y--),lives, p.robot.color);
        }
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
