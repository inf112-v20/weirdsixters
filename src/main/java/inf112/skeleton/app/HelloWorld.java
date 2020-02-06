package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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

public class HelloWorld implements ApplicationListener {
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();
        playerLayer.setCell((int) playerPos.x,(int) playerPos.y,playerCell);
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
}
