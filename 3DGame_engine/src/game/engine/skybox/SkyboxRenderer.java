package game.engine.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import game.engine.camera.Camera;
import game.engine.models.TexturedModel;
import game.engine.models.VAO;
import game.engine.rendering.Renderer;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;
import game.engine.shader.UniformSampler;
import game.engine.textures.Texture;
import game.engine.textures.TextureLoader;

public class SkyboxRenderer extends Renderer{

	private static final String[] SKYBOX_TEXTURES = {
			"TropicalSunnyDayRight2048",
			"TropicalSunnyDayLeft2048",
			"TropicalSunnyDayUp2048",
			"TropicalSunnyDayDown2048",
			"TropicalSunnyDayBack2048",
			"TropicalSunnyDayFront2048",
	};

	private Skybox skybox;

	public SkyboxRenderer(){
		this.shader = new SkyboxShader();
		skybox = new Skybox(TextureLoader.loadCubeMap(SKYBOX_TEXTURES));
	}

	public void render(Camera camera){
		prepare(camera);
		skybox.getVAO().bind(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skybox.getTexture());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, skybox.getVAO().getIndexCount());
		skybox.getVAO().unbind(0);
		finish();
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	private void prepare(Camera camera){
		SkyboxShader shader = (SkyboxShader) this.shader;
		shader.start();
		shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		Matrix4f viewMatrix = new Matrix4f();
		Matrix4f.load(camera.getViewMatrix(), viewMatrix);
		viewMatrix.m30 = 0;
		viewMatrix.m31 = 0;
		viewMatrix.m32 = 0;
		shader.viewMatrix.loadMatrix(viewMatrix);
	}

	private class SkyboxShader extends ShaderProgram{

		private static final int DIFFUSE_TEX_UNIT = 0;

		private static final String VERTEX_SHADER = "/game/engine/shader/skyboxVertexShader.glsl";
		private static final String FRAGMENT_SHADER = "/game/engine/shader/skyboxFragmentShader.glsl";

		protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
		protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");

		private UniformSampler samplerCube = new UniformSampler("cubeMap");

		private SkyboxShader(){
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position");
			super.storeUniformLocations(projectionMatrix, viewMatrix, samplerCube);
			connectTextureUnits();
		}

		private void connectTextureUnits(){
			super.start();
			samplerCube.loadTexUnit(DIFFUSE_TEX_UNIT);
			super.stop();
		}
	}

}
