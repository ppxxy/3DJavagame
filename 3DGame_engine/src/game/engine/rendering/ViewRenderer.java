package game.engine.rendering;

import game.engine.entities.AnimatedEntity;
import game.engine.main.View;
import game.engine.models.AnimatedModel;
import game.engine.view.InterfaceView;

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
		if(view instanceof GameView){
			GameView game = (GameView) view;
			terrainRenderer.render(game.getTerrain(), game.getCamera());
			for(AnimatedEntity e : game.getAnimatedEntities()){
				animatedModelRenderer.render(e, game.getCamera(), game.getLightDirection());
			}
			interfaceRenderer.render(game.getInterfaces(), game.getCamera());
			//After normal rendering, render depth buffer.
			game.depthBuffer.bind(Display.getWidth(), Display.getHeight());
			renderDepth(game);
			game.depthBuffer.unbind();
			game.useMousePicker();
		}
		else if(view instanceof InterfaceView){
			InterfaceView inter = (InterfaceView) view;
			interfaceRenderer.render(inter.getInterfaces(), inter.getCamera());
		}
	}

	public void renderDepth(GameView view){
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
