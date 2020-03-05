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
import com.badlogic.gdx.math.Vector3;

public class Renderer {
    private final Vector2 tileSize;
    private final Vector2 mapSize;

    private SpriteBatch tileSpriteBatch;
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

        //offset camera to make space for card slots
        camera.viewportHeight = mapSize.y+4;
        camera.position.set(new Vector3(mapSize.x/2, mapSize.y/3, 0));

        camera.update();

        tilemapRenderer = new OrthogonalTiledMapRenderer(map, pixelsPerTile);
        tilemapRenderer.setView(camera);

        tileSpriteBatch = new SpriteBatch();
        tileSpriteBatch.setProjectionMatrix(camera.combined);

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    // need this for a matching pair of @create and @dispose
    public static Renderer create(TiledMap map) {
        return new Renderer(map);
    }

    // need this to "destruct" libGDX stuff
    public void dispose(){
        tileSpriteBatch.dispose();
        font.dispose();
    }

    public void begin() {
        clearFramebuffer();
        tilemapRenderer.render();
        tileSpriteBatch.begin();
    }

    public void end() {
        tileSpriteBatch.end();
    }

    /**
     * @summary draws a sprite with the same camera transform as the map,
     * meaning that the sprite will be drawn as a tile, with @position being
     * the cell coordinate. The sprite will also be scaled to match the tile size.
     * @param tex is the texture to draw
     * @param texIndex is the sub-texture index
     * @param position is the board coordinate
     */
    public void drawTileSprite(Texture tex, Vector2 texIndex, Vector2 position, float rotation) {
        Vector2 coord = Linear.floor(position);
        TextureRegion subTex = getSubTexture(tex, texIndex);
        tileSpriteBatch.draw(subTex, coord.x, coord.y, 0.5f, 0.5f, 1, 1, 1, 1, rotation);
    }

    public void drawTileSprite(Texture tex, Vector2 texIndex, Transform transform) {
        drawTileSprite(tex, texIndex, transform.position, transform.direction.angle());
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

    // WARNING: TextureRegion expects texture coordinates (0..1) if constructed
    // with float arguments. We need to pass ints here for pixels values.
    private TextureRegion getSubTexture(Texture tex, Vector2 texIndex) {
        Vector2 texOffset = Linear.mul(tileSize, texIndex);
        int x = (int)texOffset.x;
        int y = (int)texOffset.y;
        int w = (int)tileSize.x;
        int h = (int)tileSize.y;
        return new TextureRegion(tex, x, y, w, h);
    }
}
