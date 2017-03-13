package game.engine.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import game.engine.entities.Entity;
import game.engine.physics.MousePicker;

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
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw += yawSens;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw -= yawSens;
		}
		if(pitch < 90 && Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch += pitchSens;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch -= pitchSens;
		}
		float pitch = (float) Math.toRadians(this.pitch);
		float yaw = (float) Math.toRadians(this.yaw);
		System.out.println(this.yaw);
		this.position.y = (float) (Math.sin(pitch)*distance);
		this.position.x = (float) (Math.cos(pitch)*(Math.sin(yaw+Math.PI))*distance);
		this.position.z = (float) (Math.cos(pitch)*(Math.cos(yaw))*distance);
		updateViewMatrix();
	}

	public void onRotate(){

	}

}
