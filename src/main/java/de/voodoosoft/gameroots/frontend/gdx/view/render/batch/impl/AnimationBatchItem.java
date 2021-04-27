package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Pool;


public class AnimationBatchItem extends AbstractBatchItem implements Pool.Poolable {
    public final static float MILLIS_AS_NANO = 1000000;
    public final static float SECS_AS_NANO = MILLIS_AS_NANO * 1000;

    private static ShaderProgram shader;

    public static void setShader(ShaderProgram shader) {
        AnimationBatchItem.shader = shader;
    }

    public AnimationBatchItem() {
        setBlendMode(DefaultBlendMode.DEFAULT);
    }

    @Override
    public void reset() {
        setBlendMode(DefaultBlendMode.DEFAULT);
        startTime = 0;
        color = null;
        animation = null;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void setLocation(float x, float y) {
        this.x1 = x;
        this.y1 = y;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(SpriteBatch batch, long time) {
        if (getLastShaderProgram() != shader) {
            batch.setShader(shader);
            setLastShaderProgram(shader);
        }
        toggleBlending(batch);

        if (color != null) {
            batch.setColor(color);
        } else {
            batch.setColor(Color.WHITE);
        }

        if (startTime == 0) {
            startTime = time;
        }
        long stateTime = time - startTime;
        int idx = animation.getKeyFrameIndex(stateTime / SECS_AS_NANO);
        if (idx >= 0) {
            TextureRegion frame = animation.getKeyFrames()[idx];
            if (color != null) {
                batch.setColor(color);
            }
            batch.draw(frame, x1, y1);
        }
    }

    protected Animation<TextureRegion> animation;
    protected float x1, y1;
    protected long startTime;
    protected Color color;
}
