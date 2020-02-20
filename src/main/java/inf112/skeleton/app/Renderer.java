package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Renderer {
    private final Vector2 tileSize;
    private final Vector2 mapSize;

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthogonalTiledMapRenderer tilemapRenderer;

    // private as it should only be called from @create
    private Renderer(TiledMap map) {
        TiledMapTileLayer firstLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileSize = getLayerTileSize(firstLayer);
        mapSize = getLayerSize(firstLayer);
        float pixelsPerTile = 1 / tileSize.x;

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, mapSize.x, mapSize.y);
        camera.position.x = mapSize.x / 2;
        camera.update();

        tilemapRenderer = new OrthogonalTiledMapRenderer(map, pixelsPerTile);
        tilemapRenderer.setView(camera);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    // need this for a matching pair of @create and @dispose
    public static Renderer create(TiledMap map) {
        return new Renderer(map);
    }

    // need this to "destruct" libGDX stuff
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

    public void begin() {
        clearFramebuffer();
        tilemapRenderer.render();
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void drawTileSprite(Texture tex, Vector2 texIndex, Vector2 pos) {
        int w = (int)tileSize.x;
        int h = (int)tileSize.y;
        int x = (int)texIndex.x * w;
        int y = (int)texIndex.y * h;
        TextureRegion subTex = new TextureRegion(tex, x, y, w, h);
        batch.draw(subTex, pos.x, pos.y, 1, 1);
    }

    public Vector2 getMapSize() {
        return mapSize;
    }

    private void clearFramebuffer(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }

    private Vector2 getLayerSize(TiledMapTileLayer playerLayer) {
        return new Vector2(playerLayer.getWidth(), playerLayer.getHeight());
    }

    private Vector2 getLayerTileSize(TiledMapTileLayer layer) {
        return new Vector2(layer.getTileWidth(), layer.getTileHeight());
    }
}
