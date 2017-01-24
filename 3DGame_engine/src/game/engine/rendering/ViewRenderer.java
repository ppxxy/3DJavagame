package game.engine.rendering;

import game.engine.models.AnimatedModel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class ViewRenderer {

	private AnimatedModelRenderer animatedModelRenderer;
	
	protected ViewRenderer(){
		animatedModelRenderer = new AnimatedModelRenderer();
	}
	
	public void renderView(View view){
		prepare();
		for(AnimatedModel model : view.getAnimatedModels()){
			animatedModelRenderer.render(model, view.getCamera(), view.getLightDirection());
		}
	}
	
	public void cleanUp(){
		animatedModelRenderer.cleanUp();
	}
	
	private void prepare(){
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
}
