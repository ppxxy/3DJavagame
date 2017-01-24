package game.engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import game.engine.entities.Camera;
import game.engine.models.AnimatedModel;
import game.engine.shader.ShaderProgram;
import game.engine.shader.UniformMat4Array;
import game.engine.shader.UniformMatrix;
import game.engine.shader.UniformSampler;
import game.engine.shader.UniformVec3;
import game.engine.tools.OpenGlUtils;

public class AnimatedModelRenderer extends Renderer {
	
	public AnimatedModelRenderer() {
		this.shader = new AnimatedModelShader();
	}
	
	public void render(AnimatedModel model, Camera camera, Vector3f lightDirection){
		prepare(camera, lightDirection);
		model.getTexture().bindToUnit(0);
		model.getModel().bind(0, 1, 2, 3, 4);
		((AnimatedModelShader)shader).jointTransforms.loadMatrixArray(model.getJointTransforms());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		model.getModel().unbind(0, 1, 2, 3, 4);
		finish();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
	
	private void prepare(Camera camera, Vector3f lightDir){
		AnimatedModelShader shader = (AnimatedModelShader) this.shader;
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(lightDir);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}
	
	private void finish(){
		shader.stop();
	}
	
	private class AnimatedModelShader extends ShaderProgram{
		
		private static final int MAX_JOINTS = 50; //Max 50 joints per skeleton
		private static final int DIFFUSE_TEX_UNIT = 0;
		
		private static final String VERTEX_SHADER = "/game/engine/shader/animatedEntityVertex.glsl";
		private static final String FRAGMENT_SHADER = "/game/engine/shader/animatedEntityFragment.glsl";
	
		protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
		protected UniformVec3 lightDirection = new UniformVec3("lightDirection");
		protected UniformMat4Array jointTransforms = new UniformMat4Array("jointTransforms", MAX_JOINTS);
		private UniformSampler diffuseMap = new UniformSampler("diffuseMap");
		
		public AnimatedModelShader(){
			super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal", "in_jointIndices", "in_weights");
			super.storeUniformLocations(projectionViewMatrix, lightDirection, jointTransforms, diffuseMap);
			connectTextureUnits();
		}
		
		private void connectTextureUnits(){
			super.start();
			diffuseMap.loadTexUnit(DIFFUSE_TEX_UNIT);
			super.stop();
		}
	}
}
