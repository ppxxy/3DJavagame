package game.engine.rendering;

import game.engine.entities.AnimatedEntity;
import game.engine.models.AnimatedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ViewRenderer {

	private AnimatedModelRenderer animatedModelRenderer;
	private TerrainRenderer terrainRenderer;
	private InterfaceRenderer interfaceRenderer;

	protected ViewRenderer(){
		animatedModelRenderer = new AnimatedModelRenderer();
		terrainRenderer = new TerrainRenderer();
		interfaceRenderer = new InterfaceRenderer();
	}

	public void renderView(View view){
		prepare();
		terrainRenderer.render(view.getTerrain(), view.getCamera());
		for(AnimatedEntity e : view.getAnimatedEntities()){
			animatedModelRenderer.render(e, view.getCamera(), view.getLightDirection());
		}
		interfaceRenderer.render(view.getInterfaces(), view.getCamera());
		//After normal rendering, render depth buffer.
		view.depthBuffer.bind(Display.getWidth(), Display.getHeight());
		renderDepth(view);
		view.depthBuffer.unbind();
		view.useMousePicker();
	}

	public void renderDepth(View view){
		prepare();
		terrainRenderer.render(view.getTerrain(), view.getCamera());
		/*for(AnimatedEntity e : view.getAnimatedEntities()){
			animatedModelRenderer.render(e, view.getCamera(), view.getLightDirection());
		}*/
	}

	public void cleanUp(){
		animatedModelRenderer.cleanUp();
		terrainRenderer.cleanUp();
		interfaceRenderer.cleanUp();
	}

	private void prepare(){
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
}
