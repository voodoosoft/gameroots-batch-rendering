package de.voodoosoft.gameroots.frontend.gdx.view.render.batch.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Pool;



public class GlyphLayoutBatchItem extends AbstractBatchItem implements Pool.Poolable {
	private static ShaderProgram shader;

	private Color color;
	private float x, y;
	private GlyphLayout text;
	private BitmapFont font;

	public static void setShader(ShaderProgram shader) {
		GlyphLayoutBatchItem.shader = shader;
	}

	public GlyphLayoutBatchItem() {
		setBlendMode(DefaultBlendMode.DEFAULT);
	}

	@Override
	public void reset() {
		setBlendMode(DefaultBlendMode.DEFAULT);
		x = 0;
		y = 0;
		text = null;
		font = null;
		color = null;
	}

	@Override
	public void render(SpriteBatch batch, long time) {
		if (getLastShaderProgram() != shader) {
			batch.setShader(shader);
			setLastShaderProgram(shader);
		}
		toggleBlending(batch);

		if (color != null) {
			font.setColor(color);
		} else {
			font.setColor(Color.WHITE);
		}

		font.draw(batch, text, x, y);
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public void setText(GlyphLayout text) {
		this.text = text;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
