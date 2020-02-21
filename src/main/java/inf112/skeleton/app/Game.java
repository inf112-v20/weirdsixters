package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class Game extends InputAdapter implements ApplicationListener {
    private Renderer renderer;
    private Vector2 playerPos;
    private Texture playerTexture;

    @Override
    public void create() {
        TiledMap map = new TmxMapLoader().load("newBoard.tmx");
        renderer = Renderer.create(map);

        playerTexture = new Texture("player.png");
        playerPos = new Vector2(0,0);

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

    /*
     * @keyUp makes it feel laggy, we're using @keyDown for immediate response.
     * This won't be a problem because it's not triggered on pressed keys.
     */
    @Override
    public boolean keyDown(int key) {
        int x = 0, y = 0;
        switch (key){
            case Input.Keys.RIGHT: x++; break;
            case Input.Keys.LEFT: x--; break;
            case Input.Keys.UP: y++; break;
            case Input.Keys.DOWN: y--; break;
        }
        movePlayer(x, y);
        return true;
    }

    /*
     * I'm not checking if the player goes outside of the board
     * because it's a bad idea to write code before we know it's needed.
     */
    private void movePlayer(int x, int y){
        playerPos.add((float)x, (float)y);
    }
}
