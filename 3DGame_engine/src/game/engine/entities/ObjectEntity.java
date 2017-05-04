package game.engine.entities;

import org.lwjgl.util.vector.Vector3f;

import game.engine.models.TexturedModel;

public class ObjectEntity extends Entity{

	private TexturedModel model;
	private int id;

	public ObjectEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(position, rotX, rotY, rotZ, scale);
		this.model = model;
		this.id = model.getModel().id;
	}

	public TexturedModel getModel(){
		return this.model;
	}

}
