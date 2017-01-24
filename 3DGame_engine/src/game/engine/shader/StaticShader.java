package game.engine.shader;

import game.engine.entities.Camera;
import game.engine.tools.Maths;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader /*extends ShaderProgram*/{

	/*private static final String VERTEX_FILE = "/game/engine/shader/vertexShader.txt",
			FRAGMENT_FILE = "/game/engine/shader/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public StaticShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}*/
	
}
