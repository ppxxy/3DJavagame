package game.engine.rendering;

import java.util.List;

import org.lwjgl.opengl.GL11;

import game.engine.camera.Camera;
import game.engine.interfaces.Interface;
import game.engine.models.VAO;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMatrix;

public class InterfaceRenderer extends Renderer{

	private static final VAO vao = VAO.create();

	public InterfaceRenderer(){
		this.shader = new InterfaceShader();
		vao.bind(0);
		vao.createAttribute(0, new float[]{-1, 1, -1, -1, 1, 1, 1, -1}, 2);
		vao.unbind(0);
	}

	private void prepare(Camera camera){
		this.shader.start();
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	public void render(List<Interface> interfaces, Camera camera){
		prepare(camera);
		vao.bind(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(Interface inter : interfaces){
			inter.getTexture().bindToUnit(0);
			((InterfaceShader) this.shader).transformationMatrix.loadMatrix(inter.getMatrix());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 8);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		vao.unbind(0);
		finish();
	}

	private class InterfaceShader extends ShaderProgram{

		private static final String VERTEX_SHADER = "/game/engine/shader/interfaceVertex.glsl";
		private static final String FRAGMENT_SHADER = "/game/engine/shader/interfaceFragment.glsl";

		protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");

		public InterfaceShader() {
			super(VERTEX_SHADER, FRAGMENT_SHADER, "position");
			super.storeUniformLocations(transformationMatrix);
		}

	}
}
