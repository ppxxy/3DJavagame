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
	private final float[][] heightMap;

	public TerrainModel(VAO model, float[][] heightMap, Texture texture){
		this.model = model;
		this.heightMap = heightMap;
		this.texture = texture;
	}

	public VAO getModel(){
		return this.model;
	}

	public Texture getTexture() {
		return this.texture;
	}

	public float[][] getHeightMap() {
		return this.heightMap;
	}
}
