package game.engine.rendering;

import game.connection.objects.MovementDestination;
import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.entities.Entity;
import game.engine.interfaces.Interface;
import game.engine.main.Main;
import game.engine.physics.MousePicker;
import game.engine.physics.ViewDepthBuffer;
import game.engine.terrain.Terrain;
import game.engine.terrain.TerrainModel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class View {

	private final List<Entity> entities = new ArrayList<Entity>();
	private final List<Interface> interfaces = new ArrayList<Interface>();
	private final Camera camera;
	private final MousePicker mousePicker;
	private TerrainModel terrain;
	private Vector3f lightDirection = new Vector3f(0, -1, 0);
	private final List<AnimatedEntity> animatedEntities = new ArrayList<AnimatedEntity>();

	ViewDepthBuffer depthBuffer;

	public View(Camera camera){
		this.depthBuffer = new ViewDepthBuffer();
		this.camera = camera;
		this.mousePicker = new MousePicker(camera);
		Terrain terrain = new Terrain(0, 0);
		terrain.loadChunks();
		this.terrain = terrain.loadModel();
	}

	public void addEntity(Entity entity){
		entities.add(entity);
		if(entity instanceof AnimatedEntity){
			animatedEntities.add((AnimatedEntity) entity);
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
	public List<AnimatedEntity> getAnimatedEntities(){
		return animatedEntities;
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
				((AnimatedEntity) e).update();
			}
		}
	}

	public TerrainModel getTerrain() {
		return this.terrain;
	}

	private float distanceAt(int x, int y){
		return depthBuffer.getDepth(x, y);
	}

	public List<Interface> getInterfaces() {
		return this.interfaces;
	}

	public void useMousePicker() {
		if(Mouse.next() && Mouse.getEventButton() == 0 && Mouse.getEventButtonState()){
			float distance = distanceAt(Mouse.getX(), Mouse.getY());
			if(distance < Camera.FAR_PLANE){
				Vector3f location = mousePicker.update(distance);
				Main.connection.send(new MovementDestination.MovementTo(location));
			}
		}
	}

	public void cleanUp() {
		this.depthBuffer.cleanUp();
	}
}
