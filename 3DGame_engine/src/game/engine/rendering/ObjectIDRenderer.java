package game.engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import game.engine.camera.Camera;
import game.engine.entities.ObjectEntity;
import game.engine.models.VAO;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;
import game.engine.tools.OpenGlUtils;

public class ObjectIDRenderer extends Renderer{

	public ObjectIDRenderer(){
		this.shader = new ObjectIDShader();
	}

	public void render(ObjectEntity e, Camera camera){
		prepare(e.getTransformationMatrix(), camera);
		VAO[] vaos = e.getModel().getModels();
		for(int i = 0; i < vaos.length; i++){
			vaos[i].bind(0, 1, 2, 3);
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			vaos[i].unbind(0, 1, 2, 3);
		}
		finish();
	}

	private void prepare(Matrix4f transformation, Camera camera){
		ObjectIDShader shader = (ObjectIDShader) this.shader;
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.transformationMatrix.loadMatrix(transformation);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	private class ObjectIDShader extends ShaderProgram{

		private static final String VERTEX_SHADER = "/game/engine/shader/objectIdVertex.glsl";
		private static final String FRAGMENT_SHADER = "/game/engine/shader/objectIdFragment.glsl";

		protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
		protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");

		public ObjectIDShader() {
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position", "textureCoords", "normals", "objectId");
			super.storeUniformLocations(projectionViewMatrix, transformationMatrix);
		}
	}
}