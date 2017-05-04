package game.engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import game.engine.camera.Camera;
import game.engine.entities.ObjectEntity;
import game.engine.models.VAO;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;
import game.engine.shader.UniformSampler;
import game.engine.textures.Texture;
import game.engine.tools.OpenGlUtils;

public class ObjectRenderer extends Renderer{

	public ObjectRenderer(){
		this.shader = new ObjectShader();
	}

	public void render(ObjectEntity e, Camera camera){
		prepare(e.getTransformationMatrix(), camera);
		Texture[] textures = e.getModel().getTextures();
		VAO[] vaos = e.getModel().getModels();
		for(int i = 0; i < textures.length; i++){
			textures[i].bindToUnit(0);
			vaos[i].bind(0, 1, 2, 3);
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getModel().getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			vaos[i].unbind(0, 1, 2, 3);
		}
		finish();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

	private void prepare(Matrix4f transformation, Camera camera){
		ObjectShader shader = (ObjectShader) this.shader;
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.transformationMatrix.loadMatrix(transformation);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	private class ObjectShader extends ShaderProgram{

		private static final int DIFFUSE_TEX_UNIT = 0;

		private static final String VERTEX_SHADER = "/game/engine/shader/objectVertexShader.glsl";
		private static final String FRAGMENT_SHADER = "/game/engine/shader/objectFragmentShader.glsl";

		protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
		protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");
		private UniformSampler diffuseMap = new UniformSampler("diffuseMap");

		public ObjectShader() {
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position", "textureCoords", "normals", "objectId");
			super.storeUniformLocations(projectionViewMatrix, transformationMatrix, diffuseMap);
			connectTextureUnits();
		}

		private void connectTextureUnits(){
			super.start();
			diffuseMap.loadTexUnit(DIFFUSE_TEX_UNIT);
			super.stop();
		}
	}
}
