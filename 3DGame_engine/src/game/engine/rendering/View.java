package game.engine.rendering;

import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.entities.Entity;
import game.engine.models.AnimatedModel;
import game.engine.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class View {

	private final List<Entity> entities = new ArrayList<Entity>();
	private final Camera camera;
	private Terrain terrain;
	private Vector3f lightDirection = new Vector3f(0, -1, 0);
	private final List<AnimatedModel> animatedModels = new ArrayList<AnimatedModel>();

	public View(Camera camera){
		this.camera = camera;
	}

	public void addEntity(Entity entity){
		entities.add(entity);
		if(entity instanceof AnimatedEntity){
			animatedModels.add(((AnimatedEntity) entity).getModel());
		}
	}

	public Camera getCamera(){
		return camera;
	}

	public List<AnimatedModel> getAnimatedModels(){
		return animatedModels;
	}

	public Vector3f getLightDirection() {
		return lightDirection;
	}

	public void setLightDirection(Vector3f lightDir) {
		this.lightDirection.set(lightDir);
	}

	public void updateEntities() {
		for(Entity e : entities){
			if(e instanceof AnimatedEntity){
				((AnimatedEntity) e).getModel().update();
			}
		}
	}

	public Terrain getTerrain() {
		return this.terrain;
	}
}
