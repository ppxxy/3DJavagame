package game.engine.rendering;

import game.connection.objects.MovementDestination;
import game.engine.camera.ActiveCamera;
import game.engine.camera.Camera;
import game.engine.entities.AnimatedEntity;
import game.engine.entities.Entity;
import game.engine.entities.ObjectActivityHandler;
import game.engine.interfaces.ActiveInterface;
import game.engine.interfaces.Interface;
import game.engine.main.Main;
import game.engine.main.View;
import game.engine.physics.MousePicker;
import game.engine.physics.ObjectBuffer;
import game.engine.physics.ViewDepthBuffer;
import game.engine.terrain.Terrain;
import game.engine.terrain.TerrainModel;
import game.engine.tools.Maths;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GameView extends View{

	private final List<Entity> entities = new ArrayList<Entity>();
	private final List<Interface> interfaces = new ArrayList<Interface>();
	private final MousePicker mousePicker;
	private TerrainModel terrain;
	private Vector3f lightDirection = new Vector3f(0, -1, 0);
	private final List<AnimatedEntity> animatedEntities = new ArrayList<AnimatedEntity>();

	ViewDepthBuffer depthBuffer;
	ObjectBuffer objectBuffer;

	public GameView(){
		this.depthBuffer = new ViewDepthBuffer();
		this.objectBuffer = new ObjectBuffer();
		Terrain terrain = new Terrain(0, 0);
		terrain.loadChunks();
		this.camera = new Camera(new Vector3f(320, 200, 320));
		this.mousePicker = new MousePicker(camera);
		this.terrain = terrain.loadModel();
	}

	public GameView(Camera camera) {
		this.depthBuffer = new ViewDepthBuffer();
		this.objectBuffer = new ObjectBuffer();
		Terrain terrain = new Terrain(0, 0);
		terrain.loadChunks();
		this.camera = camera;
		this.mousePicker = new MousePicker(camera);
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

	public float getHeightAt(float x, float z){
		float[][] heightmap = terrain.getHeightMap();
		Vector3f a, b, c;
		if(x%1 <= 1-z%1){
			a = new Vector3f((int) 0, heightmap[(int) x][(int) z], (int) 0);
		}
		else{
			a = new Vector3f((int) 1, heightmap[(int) x+1][(int) z+1], (int) 1);
		}
		b = new Vector3f((int) 0, heightmap[(int) x][(int) z+1], (int) 1);
		c = new Vector3f((int) 1, heightmap[(int) x+1][(int) z], (int) 0);
		return Maths.barryCentric(a, b, c, new Vector2f(x%1, z%1));
	}

	public void setLightDirection(Vector3f lightDir) {
		this.lightDirection.set(lightDir);
	}

	private boolean checkInterfacePress(float x, float y){
		for(Interface inter : interfaces){
			if(inter instanceof ActiveInterface){
				if(((ActiveInterface)inter).MouseAction(x, y)){
					return true;
				}
			}
		}
		return false;
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
			float mouseRelativeX = Mouse.getX()/Display.getWidth();
			float mouseRelativeY = Mouse.getY()/Display.getHeight();
			if(checkInterfacePress(mouseRelativeX, mouseRelativeY)){
				return;
			}

			int id = objectIdAt(Mouse.getX(), Mouse.getY());
			System.out.println("Clicked object: " +id);
			if(ObjectActivityHandler.doActivity(id)){
				//System.out.println("Clicked object: " +id);
				return;
			}
			//System.out.println("Clicked object: " +id);

			float distance = distanceAt(Mouse.getX(), Mouse.getY());
			if(distance < Camera.FAR_PLANE){
				Vector2f location = mousePicker.update(distance);
				Main.connection.send(new MovementDestination.MovementTo(location));
			}
		}
	}

	private int objectIdAt(int x, int y) {
		return objectBuffer.getId(x, y);
	}

	@Override
	public void cleanUp() {
		this.depthBuffer.cleanUp();
	}

	public AnimatedEntity getEntityById(int id) {
		for(AnimatedEntity e : animatedEntities){
			if(e.getId() == id){
				return e;
			}
		}
		return null;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	@Override
	public void update() {
		super.update();
		if(camera instanceof ActiveCamera){
			((ActiveCamera) camera).update();
		}
		for(Entity e : entities){
			if(e instanceof AnimatedEntity){
				((AnimatedEntity) e).update();
			}
		}
	}

	public List<Entity> getEntities() {
		return this.entities;
	}
}