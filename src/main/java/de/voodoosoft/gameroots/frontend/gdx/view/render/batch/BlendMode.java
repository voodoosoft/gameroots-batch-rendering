package de.voodoosoft.gameroots.frontend.gdx.view.render.batch;

public interface BlendMode<T extends BlendMode> {
	int getSrcFunction();

	int getDestFunction();
}
