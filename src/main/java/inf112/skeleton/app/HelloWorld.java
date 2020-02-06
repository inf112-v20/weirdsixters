package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class HelloWorld extends InputAdapter implements ApplicationListener {
    private static final float MAP_SIZE_X = 5;
    private static final float MAP_SIZE_Y = 5;
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer boardLayer, playerLayer, holeLayer, flagLayer;
    private TiledMapTileLayer.Cell playerWonCell, playerDiedCell, playerCell;
    private Vector2 playerPos;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        map = new TmxMapLoader().load("testLevel.tmx");

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        renderer = new OrthogonalTiledMapRenderer(map, 1/300f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.position.x = 2.5f;
        camera.update();
        renderer.setView(camera);

        Texture playerTexture = new Texture("player.png");
        TextureRegion[][] playerTextureSplit = TextureRegion.split(playerTexture, 300, 300);
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextureSplit[0][0]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextureSplit[0][1]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextureSplit[0][2]));
        playerPos = new Vector2(0,0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        clearFramebuffer();

        // I'm not gonna read the player pos from the "presentation layer".
        // This is a one way street.
        // We're assigning all player positions each frame, for now.
        clearCells(playerLayer);
        playerLayer.setCell((int) playerPos.x,(int) playerPos.y, playerCell);

        renderer.render();
    }

    private void clearFramebuffer(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }

    private void clearCells(TiledMapTileLayer layer){
        for (int y = 0; y < MAP_SIZE_Y; y++){
            for (int x = 0; x < MAP_SIZE_X; x++){
                layer.setCell(x, y, null);
            }
        }
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
