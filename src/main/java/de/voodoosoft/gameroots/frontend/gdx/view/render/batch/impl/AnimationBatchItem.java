package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;


public class AnimationBatchItem extends AbstractBatchItem implements Pool.Poolable {
	public final static float MILLIS_AS_NANO = 1000000;
	public final static float SECS_AS_NANO = MILLIS_AS_NANO * 1000;

	public AnimationBatchItem() {
		setBlendMode(DefaultBlendMode.DEFAULT);
	}

	@Override
	public void reset() {
		setBlendMode(DefaultBlendMode.DEFAULT);
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

	@Override
	public void render(SpriteBatch batch, long time) {
		toggleBlending(batch);

		if (startTime == 0) {
			startTime = time;
		}
		long stateTime = time - startTime;
		TextureRegion frame = animation.getKeyFrame(stateTime / SECS_AS_NANO, true);
		batch.draw(frame, x1, y1);
	}

	private Animation<TextureRegion> animation;
	private float x1, y1;
	private long startTime;
}
