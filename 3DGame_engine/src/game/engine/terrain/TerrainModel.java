package game.engine.terrain;

import game.engine.models.VAO;
import game.engine.textures.Texture;

/**
 * Saves chunk data into VAO, ready for rendering.
 * @author Kim
 *
 */
public class TerrainModel {

	private final VAO model;
	private final Texture texture;

	public TerrainModel(VAO model, Texture texture){
		this.model = model;
		this.texture = texture;
	}

	public VAO getModel(){
		return this.model;
	}

	public Texture getTexture() {
		return this.texture;
	}
}
