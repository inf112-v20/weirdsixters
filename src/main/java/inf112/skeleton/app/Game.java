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
    private Vector2 playerPos;
    private Transform playerTransform; //må etter hvert flyttes inn i en robotklasse sammen med playerpos
    private Texture playerTexture;

    @Override
    public void create() {
        TiledMap map = new TmxMapLoader().load("testLevel.tmx");
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        renderer = Renderer.create(map);
        Vector2 mapSize = renderer.getMapSize();

        playerTexture = new Texture("player.png");
        playerPos = findPositions(playerLayer, mapSize).get(0);
        playerTransform = new Transform(new Vector2(0,1));

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void render() {
        renderer.begin();
        renderer.drawTileSprite(playerTexture, new Vector2(), playerPos);
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
            case Input.Keys.W: checkCardKind(new Card(CardKind.FORWARD, 2, 5)); break;
            case Input.Keys.S: checkCardKind(new Card(CardKind.REVERSE, 2, 5)); break;
        }
        movePlayer(deltaPos);
        return true;
    }

    /**
     * checks the action of given card
     * @param card card to check
     */
    private void checkCardKind(Card card) {
        switch(card.getAction()) {
            case FORWARD:
                movePlayer(Linear.multiply(playerTransform.getDirection(), card.getSteps()));
                break;
            case REVERSE:
                movePlayer(Linear.multiply(playerTransform.getDirection(), -card.getSteps()));
                break;
            case TURNRIGHT:
                // need to implement rotatePlayer() (render and logic)
                break;
            case TURNLEFT:
                // need to implement rotatePlayer()
                break;
            case FLIP:
                // 180 degree turn. need to implement rotatePlayer()
                break;
            default:
                break;
        }
    }

    private void movePlayer(Vector2 deltaPos){
        playerPos.add(deltaPos);
    }

    private ArrayList<Vector2> findPositions(TiledMapTileLayer layer, Vector2 size) {
        ArrayList<Vector2> result = new ArrayList<>();
        float hi = size.y - 1;
        for (int y = 0; y < size.y; y++){
            for (int x = 0; x < size.x; x++){
                if (layer.getCell(x, y) != null)
                    result.add(new Vector2(x, hi - y));
            }
        }
        return result;
    }
}
