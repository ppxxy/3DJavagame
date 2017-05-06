package game.engine.rendering;

import game.engine.entities.AnimatedEntity;
import game.engine.entities.Entity;
import game.engine.entities.ObjectEntity;
import game.engine.main.View;
import game.engine.skybox.SkyboxRenderer;
import game.engine.view.InterfaceView;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ViewRenderer {

	private ObjectRenderer objectRenderer;
	private ObjectIDRenderer objectIDRenderer;
	private AnimatedModelRenderer animatedModelRenderer;
	private TerrainRenderer terrainRenderer;
	private InterfaceRenderer interfaceRenderer;
	private SkyboxRenderer skyboxRenderer;

	protected ViewRenderer(){
		objectRenderer = new ObjectRenderer();
		objectIDRenderer = new ObjectIDRenderer();
		animatedModelRenderer = new AnimatedModelRenderer();
		terrainRenderer = new TerrainRenderer();
		interfaceRenderer = new InterfaceRenderer();
		skyboxRenderer = new SkyboxRenderer();
	}

	public void renderView(View view){
		prepare();
		if(view instanceof GameView){
			GameView game = (GameView) view;
			terrainRenderer.render(game.getTerrain(), game.getCamera(), game.getLightDirection());
			for(Entity e : game.getEntities()){
				if(e instanceof AnimatedEntity){
					animatedModelRenderer.render((AnimatedEntity)e, game.getCamera(), game.getLightDirection());
				}
				else{
					objectRenderer.render((ObjectEntity)e, game.getCamera());
				}
			}
			interfaceRenderer.render(game.getInterfaces(), game.getCamera());
			skyboxRenderer.render(game.getCamera());
			//After normal rendering, render object buffer.
			game.objectBuffer.bind(Display.getWidth(), Display.getHeight());
			renderObjects(game);
			game.objectBuffer.unbind();
			//and after that render depth buffer.
			game.depthBuffer.bind(Display.getWidth(), Display.getHeight());
			renderDepth(game);
			game.depthBuffer.unbind();
			//and then use the mouse picker
			game.useMousePicker();
		}
		else if(view instanceof InterfaceView){
			InterfaceView inter = (InterfaceView) view;
			interfaceRenderer.render(inter.getInterfaces(), inter.getCamera());
		}
	}

	public void renderDepth(GameView view){
		prepare();
		terrainRenderer.render(view.getTerrain(), view.getCamera(), view.getLightDirection());
		/*for(AnimatedEntity e : view.getAnimatedEntities()){
			animatedModelRenderer.render(e, view.getCamera(), view.getLightDirection());
		}*/
	}

	public void renderObjects(GameView view){
		prepare();
		for(Entity e : view.getEntities()){
			if(e instanceof ObjectEntity){
				objectIDRenderer.render((ObjectEntity) e, view.getCamera());
			}
		}
	}

	public void cleanUp(){
		objectRenderer.cleanUp();
		objectIDRenderer.cleanUp();
		animatedModelRenderer.cleanUp();
		terrainRenderer.cleanUp();
		interfaceRenderer.cleanUp();
	}

	private void prepare(){
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
}