package game.engine.rendering;

import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.entities.Entity;
import game.engine.interfaces.Interface;
import game.engine.models.AnimatedModel;
import game.engine.terrain.Terrain;
import game.engine.terrain.TerrainModel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class View {

	private final List<Entity> entities = new ArrayList<Entity>();
	private final List<Interface> interfaces = new ArrayList<Interface>();
	private final Camera camera;
	private TerrainModel terrain;
	private Vector3f lightDirection = new Vector3f(0, -1, 0);
	private final List<AnimatedModel> animatedModels = new ArrayList<AnimatedModel>();

	public View(Camera camera){
		this.camera = camera;
		Terrain terrain = new Terrain(0, 0);
		terrain.loadChunks();
		this.terrain = terrain.loadModel();
	}

	public void addEntity(Entity entity){
		entities.add(entity);
		if(entity instanceof AnimatedEntity){
			animatedModels.add(((AnimatedEntity) entity).getModel());
		}
	}

	public void addInterface(Interface inter){
		this.interfaces.add(inter);
	}

	/**
	 * Remove interface by giving the interface object as a parameter.
	 * @param inter Interface object to remove.
	 */
	public void removeInterface(Interface inter){
		this.interfaces.remove(inter);
	}

	/**
	 * Remove interface by index.
	 * @param index Index of interface to remove.
	 */
	public void removeInterface(int index){
		this.interfaces.remove(index);
	}

	/**
	 * Get the camera of this view.
	 * @return Camera object of this view.
	 */
	public Camera getCamera(){
		return camera;
	}

	/**
	 * Get list of AnimatedModels on this view.
	 * @return List of AnimatedModels on this view.
	 */
	public List<AnimatedModel> getAnimatedModels(){
		return animatedModels;
	}

	/**
	 * Get Vector3f representing light direction within this view. This needs to be changed later on.
	 * @return Light direction.
	 */
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

	public TerrainModel getTerrain() {
		return this.terrain;
	}

	public List<Interface> getInterfaces() {
		return this.interfaces;
	}
}
