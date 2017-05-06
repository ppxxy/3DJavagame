package game.engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import game.engine.camera.Camera;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;
import game.engine.shader.UniformVec3;
import game.engine.terrain.TerrainModel;
import game.engine.tools.OpenGlUtils;

/**
 * TerrainRenderer is used to render the terrain.
 * @author Kim
 *
 */
public class TerrainRenderer extends Renderer{

	/**
	 * Terrain renderer is a renderer used for terrain rendering.
	 */
	public TerrainRenderer(){
		this.shader = new TerrainShader();
	}

	/**
	 * Renders terrain using camera's view matrix, binds terrain's vertex data, draws polygons and unbinds the VAO.
	 * @param terrain Terrain to be rendered.
	 * @param camera Camera whose projectionViewMatrix will be used on rendering.
	 */
	public void render(TerrainModel terrain, Camera camera, Vector3f lightDirection) {
		prepare(camera, lightDirection);
		terrain.getTexture().bindToUnit(0);
		terrain.getModel().bind(0, 1, 2);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		terrain.getModel().unbind(0, 1, 2);
		finish();
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	/**
	 * Sets shader variables ready for rendering.
	 * @param camera Camera whose projectionViewMatrix will be used for rendering.
	 */
	private void prepare(Camera camera, Vector3f lightDir){
		TerrainShader shader = (TerrainShader) this.shader;
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(lightDir);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	/**
	 * TerrainShader is used to set shader variables.
	 * @author Kim
	 *
	 */
	private class TerrainShader extends ShaderProgram{

		/**
		 * Path to terrain vertex shader file.
		 */
		private static final String VERTEX_SHADER = "/game/engine/shader/terrainVertexShader.glsl";
		/**
		 * Path to terrain fragment shader file.
		 */
		private static final String FRAGMENT_SHADER = "/game/engine/shader/terrainFragmentShader.glsl";

		/**
		 * ProjectionViewMatrix that is passed to vertex shader.
		 */
		protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
		protected UniformVec3 lightDirection = new UniformVec3("lightDirection");

		/**
		 * Creates connections to shaders.
		 */
		public TerrainShader() {
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position", "textureCoords", "normal");
			super.storeUniformLocations(projectionViewMatrix, lightDirection);
		}

	}
}
