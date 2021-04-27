package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import de.voodoosoft.gameroots.frontend.gdx.view.render.batch.BlendMode;



/**
 * Batch item for rendering particles.
 * <p/>
 * As batch items are pooled and reused,
 * the concrete particle definition to be used for rendering is assigned during each render cycle on the fly
 * by calling  {@link #setParticleDef}.
 * <p/>
 *
 */
public class ParticleBatchItem extends AbstractBatchItem implements Pool.Poolable {
	public ParticleBatchItem() {
	}

	/**
	 * Prepares this item for returning to its item pool.
	 */
	@Override
	public void reset() {
		setBlendMode(DefaultBlendMode.DEFAULT);
	}

	public void setParticleDef(ParticleDef particleDef) {
		this.particleDef = particleDef;
	}

	public ParticleDef getParticleDef() {
		return particleDef;
	}

	public void setOffsets(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	@Override
	public void render(SpriteBatch batch, long time) {
		if (getLastShaderProgram() != defaultShader) {
			batch.setShader(defaultShader);
			setLastShaderProgram(defaultShader);
		}

		toggleBlending(batch);

		float ex = particleDef.getEmitterX();
		float ey = particleDef.getEmitterY();

		ParticleEffect effect = particleDef.getEffect();
		float x = ex - xOffset;
		float y = ey - yOffset;
		effect.setPosition(x, y);

		if (particleDef.getLastUpateTime() == 0) {
			particleDef.setLastUpdateTime(time);
		}

		long dns = time - particleDef.getLastUpateTime();
		boolean doUpdate = dns >= particleDef.getUpdateInterval();
		if (doUpdate) {
			particleDef.setLastUpdateTime(time);
			float ds = (float)dns / (float)SECS_AS_NANO;
			effect.draw(batch, ds);
		}
		else {
			effect.draw(batch);
		}

		if (doUpdate && effect.isComplete()) {
			particleDef.setRemove(true);
		}
	}

	@Override
	public int getTextureHandle() {
		return particleDef != null ? particleDef.getSharedTextureHandle() : 0;
	}

	@Override
	public String toString() {
		return "" + getTextureHandle();
	}

	private final static long MILLIS_AS_NANO = 1000000l;
	private final static long SECS_AS_NANO = MILLIS_AS_NANO * 1000l;

	private ParticleDef particleDef;
	private float xOffset, yOffset;
}
