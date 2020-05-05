package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

// TODO: Rename to GameRenderer
public class Renderer {
    private final Vector2 tileSize;
    private final Vector2 mapSize;

    private float fontSize;
    private Vector2 screenSize;
    private OrthogonalTiledMapRenderer tilemapRenderer;
    private SpriteBatch spriteBatch;
    private Texture robotTexture;
    private Texture cardTexture;
    private ShapeRenderer sr;

    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<TileSprite> tileSprites = new ArrayList<>();
    private SpriteBatch screenBatch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();
    private OrthographicCamera screenCamera = new OrthographicCamera();
    private ArrayList<TextCmd> textCmds = new ArrayList<>();

    // private as it should only be called from @create
    private Renderer(int width, int height, TiledMap map) {
        onWindowResized(width, height);

        TiledMapTileLayer firstLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileSize = getLayerTileSize(firstLayer);
        mapSize = getLayerSize(firstLayer);
        float pixelsPerTile = 1 / tileSize.x;

        OrthographicCamera tileCamera = new OrthographicCamera();
        tileCamera.setToOrtho(false, mapSize.x, mapSize.y);
        tileCamera.position.x = mapSize.x / 2;

        //offset camera to make space for card slots
        tileCamera.viewportHeight = mapSize.y+4;
        tileCamera.viewportWidth = mapSize.x+4;
        tileCamera.position.set(new Vector3(mapSize.x/2+2, mapSize.y/3, 0));

        tileCamera.update();

        tilemapRenderer = new OrthogonalTiledMapRenderer(map, pixelsPerTile);
        tilemapRenderer.setView(tileCamera);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(tileCamera.combined);

        robotTexture = new Texture("player.png");
        cardTexture = new Texture("cards.png");

        //Shape renderer
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(tileCamera.combined);
    }

    public void onWindowResized(int width, int height) {
        screenSize = new Vector2(width, height);
        screenCamera.setToOrtho(false, width, height);
        screenBatch.setProjectionMatrix(screenCamera.combined);
        fontSize = Linear.min(screenSize) * 0.003f;
        font.getData().setScale(fontSize);
    }

    // need this for a matching pair of @create and @dispose
    public static Renderer create(int width, int height, TiledMap map) {
        return new Renderer(width, height, map);
    }

    // need this to "destruct" libGDX stuff
    public void dispose(){
        spriteBatch.dispose();
        font.dispose();
    }

    // region public draw/render methods

    public void drawAnnouncement(String text, Color color) {
        Vector2 pos = new Vector2(0,0);
        textCmds.add(new TextCmd(text, pos, color));
    }

    public void drawCard(Card card, int row, int column) {
        Vector2 texIndex = cardTextureIndex(card);
        Vector2 coord = new Vector2(column, -1 - row);
        drawTileSprite(cardTexture, texIndex, coord, 0);
    }

    public void drawLaser(Vector2 start, Vector2 end, Vector2 dir) {
        Vector2 off = new Vector2(0.5f, 0.5f);
        Vector2 p0 = Linear.add(Linear.add(start, off), Linear.scl(dir, -0.5f));
        Vector2 p1 = Linear.add(Linear.add(end, off), Linear.scl(dir, 0.5f));
        drawLine(p0, p1, Color.RED);
    }

    public void drawLives(Vector2 pos, int count, Color color){
        for (int i = 0; i < count ; i++) {
            Vector2 newPos = Linear.add(pos, new Vector2(i,0));
            drawTileSprite(robotTexture, Vector2.Zero, newPos, 0, color);
        }
    }

    public void drawRobot(Vector2 pos, float angle, Color color) {
        drawTileSprite(robotTexture, new Vector2(), pos, angle, color);
    }

    public void render() {
        clearFramebuffer();
        tilemapRenderer.render();
        renderSprites();
        renderLines();
        renderTexts();
        clearQueues();
    }

    // endregion

    /**
     * @summary draws a sprite with the same camera transform as the map,
     * meaning that the sprite will be drawn as a tile, with @position being
     * the cell coordinate. The sprite will also be scaled to match the tile size.
     * @param tex is the texture to draw
     * @param texIndex is the sub-texture index
     * @param position is the board coordinate
     */
    private void drawTileSprite(Texture tex, Vector2 texIndex, Vector2 position, float rotation) {
        drawTileSprite(tex, texIndex, position, rotation, Color.WHITE);
    }

    private void drawTileSprite(Texture tex, Vector2 texIndex, Vector2 position, float rotation, Color color) {
        Vector2 coord = Linear.floor(position);
        TextureRegion subTex = getSubTexture(tex, texIndex);
        tileSprites.add(new TileSprite(subTex, coord, rotation, color));
    }

    private void drawLine(Vector2 start, Vector2 end, Color color) {
        lines.add(new Line(start, end, color));
    }

    private void renderLines() {
        for (Line line : lines) {
            sr.setColor(line.color);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.line(line.p0, line.p1);
            sr.end();
        }
    }

    private void renderSprites() {
        spriteBatch.begin();
        for (TileSprite spr : tileSprites) {
            TextureRegion tex = spr.subTexture;
            float x = spr.coord.x;
            float y = spr.coord.y;
            float angle = spr.rotation;
            spriteBatch.setColor(spr.color);
            spriteBatch.draw(tex, x, y, 0.5f, 0.5f, 1, 1, 1, 1, angle);
        }
        spriteBatch.end();
    }

    private void renderTexts() {
        screenBatch.begin();
        for (TextCmd cmd : textCmds) {
            font.setColor(cmd.color);
            float x = cmd.pos.x;
            float y = cmd.pos.y + fontSize * 14; // with magic factor that just works
            font.draw(screenBatch, cmd.text, x, y, 0, Align.bottomLeft, false);
        }
        textCmds.clear();
        screenBatch.end();
    }

    private void clearQueues() {
        lines.clear();
        tileSprites.clear();
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

    /**
     * @param card to get index of
     * @return V2 texIndex of given card in cards.png
     */
    private Vector2 cardTextureIndex(Card card) {
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

    // region private classes

    class Line {
        final Vector2 p0, p1;
        final Color color;

        public Line(Vector2 p0, Vector2 p1, Color color) {
            this.p0 = p0;
            this.p1 = p1;
            this.color = color;
        }
    }

    class TileSprite {
        final TextureRegion subTexture;
        final Vector2 coord;
        final float rotation;
        final Color color;

        public TileSprite(TextureRegion subTexture, Vector2 position, float rotation, Color color) {
            this.subTexture = subTexture;
            this.coord = position;
            this.rotation = rotation;
            this.color = color;
        }
    }

    class TextCmd {
        final String text;
        final Vector2 pos;
        final Color color;

        TextCmd(String text, Vector2 pos, Color color) {
            this.text = text;
            this.pos = pos;
            this.color = color;
        }
    }

    // endregion
}
