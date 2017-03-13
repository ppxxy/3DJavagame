package game.engine.interfaces;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.textures.Texture;

public class Interface {

	private Texture texture;
	private Matrix4f matrix;

	public Interface(Texture texture, Vector2f position, Vector2f size){
		this.texture = texture;
		this.matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.scale(new Vector3f(size.x, size.y, 1f), matrix, matrix);

	}

	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture){
		this.texture=texture;
	}
	public Matrix4f getMatrix(){
		return this.matrix;
	}

}
