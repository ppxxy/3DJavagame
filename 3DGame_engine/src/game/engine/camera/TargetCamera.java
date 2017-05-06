package game.engine.camera;

import org.lwjgl.input.Keyboard;
import game.engine.entities.Entity;

public class TargetCamera extends Camera implements ActiveCamera{

	private static final float yawSens = 2f;
	private static final float pitchSens = 2f;

	private Entity target;
	private float distance;

	public TargetCamera(Entity target, float distance) {
		super();
		this.distance = distance;
		this.target = target;
	}

	public void update(){
		boolean rotated = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw += yawSens;
			//onRotate();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw -= yawSens;
			//onRotate();
		}
		if(pitch < 90 && Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch += pitchSens;
		}
		if(pitch > 0 && Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch -= pitchSens;
		}
		float pitch = (float) Math.toRadians(this.pitch);
		float yaw = (float) Math.toRadians(this.yaw);
		this.position.y = (float) (Math.sin(pitch)*distance+target.getPosition().y);
		this.position.x = (float) (Math.cos(pitch)*(Math.sin(yaw+Math.PI))*distance+target.getPosition().x);
		this.position.z = (float) (Math.cos(pitch)*(Math.cos(yaw))*distance+target.getPosition().z);
		updateViewMatrix();
	}

	public void onRotate(){
		target.setRotY(180-yaw);
	}

}
