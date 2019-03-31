package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool;


public class RectBatchItem extends AbstractBatchItem implements Pool.Poolable {
	public RectBatchItem(ShapeRenderer shapeRenderer) {
		setBlendMode(DefaultBlendMode.NONE);
		this.shapeRenderer = shapeRenderer;
	}

	@Override
	public void reset() {
		setBlendMode(DefaultBlendMode.NONE);
		color = null;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setOffsets(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void setLocation(float x, float y) {
		this.x1 = x;
		this.y1 = y;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public void render(SpriteBatch batch, long time) {
		if (color != null) {
			shapeRenderer.setColor(color);
		}
		shapeRenderer.rect(x1 - xOffset, y1 - yOffset, width, height);
	}

	private ShapeRenderer shapeRenderer;
	private Color color;
	private float x1, y1, width, height;
	private float xOffset, yOffset;
}
