package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;


/**
 * Batch item for rendering textures.
 */
public class TextureBatchItem extends AbstractBatchItem implements Pool.Poolable {

    public TextureBatchItem() {
        width = -1;
        height = -1;
        rotation = 0;
        xOrigin = -1;
        yOrigin = -1;
        setBlendMode(DefaultBlendMode.DEFAULT);
    }

    public TextureBatchItem(int layer) {
        super(layer);
        width = -1;
        height = -1;
        rotation = 0;
        xOrigin = -1;
        yOrigin = -1;
        setBlendMode(DefaultBlendMode.DEFAULT);
    }

    /**
     * Prepares this item for returning to its item pool.
     */
    @Override
    public void reset() {
        textureRegion = null;
        color = null;
        width = -1;
        height = -1;
        rotation = 0;
        xOrigin = -1;
        yOrigin = -1;
        setBlendMode(DefaultBlendMode.DEFAULT);
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void shift(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setDimension(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setOrigin(float xOrigin, float yOrigin) {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
    }

    public float getxOrigin() {
        return xOrigin;
    }

    public float getyOrigin() {
        return yOrigin;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(SpriteBatch batch, long time) {
        if (getLastShaderProgram() != defaultShader) {
            batch.setShader(defaultShader);
            setLastShaderProgram(defaultShader);
        }

        toggleBlending(batch);

        if (color != null) {
            batch.setColor(color);
        } else {
            batch.setColor(Color.WHITE);
        }

        if (rotation != 0) {
            if (xOrigin != -1 && yOrigin != -1) {
                batch.draw(textureRegion, x, y, xOrigin, yOrigin, width, height, 1, 1, rotation);
            } else {
                float w = width != -1 ? width : textureRegion.getRegionWidth();
                float h = height != -1 ? height : textureRegion.getRegionHeight();
                batch.draw(textureRegion, x, y, w / 2f, h / 2f, w, h, 1, 1, rotation);
            }
        } else {
            if (width == -1 || height == -1) {
                batch.draw(textureRegion, x, y);
            } else {
                batch.draw(textureRegion, x, y, width, height);
            }
        }
    }

    @Override
    public int getTextureHandle() {
        return getTextureRegion() != null ? getTextureRegion().getTexture().getTextureObjectHandle() : 0;
    }

    @Override
    public String toString() {
        return "" + textureRegion.getTexture().getTextureObjectHandle();
    }

    private Color color;
    private float x, y;
    private float width, height;
    private float xOrigin, yOrigin;
    private TextureRegion textureRegion;
    private float rotation;
}
