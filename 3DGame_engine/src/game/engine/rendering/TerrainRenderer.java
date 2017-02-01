package game.engine.rendering;

import org.lwjgl.opengl.GL11;

import game.engine.entities.Camera;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;
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
		System.out.println("Starting shader");
		this.shader = new TerrainShader();
		System.out.println("Shader started");
	}

	/**
	 * Renders terrain using camera's view matrix, binds terrain's vertex data, draws polygons and unbinds the VAO.
	 * @param terrain Terrain to be rendered.
	 * @param camera Camera whose projectionViewMatrix will be used on rendering.
	 */
	public void render(TerrainModel terrain, Camera camera) {
		prepare(camera);
		terrain.getTexture().bindToUnit(0);
		terrain.getModel().bind(0, 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		terrain.getModel().unbind(0, 1);
		finish();
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	/**
	 * Sets shader variables ready for rendering.
	 * @param camera Camera whose projectionViewMatrix will be used for rendering.
	 */
	private void prepare(Camera camera){
		TerrainShader shader = (TerrainShader) this.shader;
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
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
		private static final String VERTEX_SHADER = "/game/engine/shader/vertexShader.txt";
		/**
		 * Path to terrain fragment shader file.
		 */
		private static final String FRAGMENT_SHADER = "/game/engine/shader/fragmentShader.txt";

		/**
		 * ProjectionViewMatrix that is passed to vertex shader.
		 */
		protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");

		/**
		 * Creates connections to shaders.
		 */
		public TerrainShader() {
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position", "textureCoords");
			super.storeUniformLocations(projectionViewMatrix);
		}

	}
}
