package game.engine.rendering;

import org.lwjgl.opengl.GL11;

import game.engine.entities.Camera;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;
import game.engine.terrain.TerrainModel;
import game.engine.tools.OpenGlUtils;

public class TerrainRenderer extends Renderer{

	public TerrainRenderer(){
		this.shader = new TerrainShader();
	}

	public void render(TerrainModel terrain, Camera camera) {
		prepare(camera);
		terrain.getModel().bind(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		terrain.getModel().unbind(0);
		finish();
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	private void prepare(Camera camera){
		TerrainShader shader = (TerrainShader) this.shader;
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	private void finish(){
		shader.stop();
	}

	private class TerrainShader extends ShaderProgram{

		private static final String VERTEX_SHADER = "/game/engine/shader/vertexShader.txt";
		private static final String FRAGMENT_SHADER = "/game/engine/shader/fragmentShader.txt";

		protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");

		public TerrainShader() {
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position", "textureCoords");
			super.storeUniformLocations(projectionViewMatrix);
		}

	}
}
