package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.voodoosoft.gameroots.frontend.gdx.view.render.batch.BatchRenderItem;
import de.voodoosoft.gameroots.frontend.gdx.view.render.batch.BlendMode;



/**
 * Default batch item that defines a post lighting flag, a layer and a blend mode.
 * <p/>
 * Draw order is as follows:
 * <br/>1. post/pre lighting
 * <br/>2. layer
 * <br/>3. blend mode
 */
public abstract class AbstractBatchItem implements BatchRenderItem<AbstractBatchItem> {
	public static void setDefaultShader(ShaderProgram defaultShader) {
		AbstractBatchItem.defaultShader = defaultShader;
	}

	public AbstractBatchItem() {
		autoReset = true;
	}

	public AbstractBatchItem(int layer) {
		this();
		this.layer = layer;
	}

	public void setAutoReset(boolean autoReset) {
		this.autoReset = autoReset;
	}

	@Override
	public boolean isAutoReset() {
		return autoReset;
	}

	public void setySorted(boolean ySorted) {
		this.ySorted = ySorted;
	}

	@Override
	public boolean isYSorted() {
		return ySorted;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public BlendMode getBlendMode() {
		return blendMode;
	}

	public void setBlendMode(BlendMode blendMode) {
		this.blendMode = blendMode;
		this.blending = blendMode != DefaultBlendMode.NONE;
	}

	public boolean isBlending() {
		return blending;
	}

	protected static ShaderProgram getLastShaderProgram() {
		return lastShaderProgram;
	}

	protected static void setLastShaderProgram(ShaderProgram lastShaderProgram) {
		AbstractBatchItem.lastShaderProgram = lastShaderProgram;
	}

	@Override
	public float getSortingX() {
		return 0;
	}

	@Override
	public float getSortingY() {
		return 0;
	}

	@Override
	public int getTextureHandle() {
		return 0;
	}

	protected void toggleBlending(SpriteBatch batch) {
		if (isBlending()) {
			if (!batch.isBlendingEnabled()) {
				batch.enableBlending();
			}
			BlendMode blendMode = getBlendMode();
			if (batch.getBlendSrcFunc() != blendMode.getSrcFunction() || batch.getBlendDstFunc() != blendMode.getDestFunction()) {
				batch.setBlendFunction(blendMode.getSrcFunction(), blendMode.getDestFunction());
			}
		}
		else {
			batch.disableBlending();
		}
	}

	private String tag;
	private BlendMode blendMode;
	private int layer;
	private boolean blending;
	private boolean autoReset;
	private boolean ySorted;
	private static ShaderProgram lastShaderProgram;
	protected static ShaderProgram defaultShader;
}
