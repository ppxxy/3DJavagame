package game.engine.terrain;

import game.engine.models.VAO;

/**
 * Saves chunk data into VAO, ready for rendering.
 * @author Kim
 *
 */
public class TerrainModel {

	private final VAO model;

	public TerrainModel(VAO model){
		this.model = model;
	}

	public VAO getModel(){
		return this.model;
	}
}
