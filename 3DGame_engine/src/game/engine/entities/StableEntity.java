package game.engine.entities;

import game.engine.models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

public class StableEntity extends Entity{

	private TexturedModel model;
	
	public StableEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(position, rotX, rotY, rotZ, scale);
		this.model = model;
	}
	
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

}
