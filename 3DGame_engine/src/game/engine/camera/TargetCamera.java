package game.engine.camera;

import game.engine.entities.Entity;

public class TargetCamera extends Camera implements ActiveCamera{

	private Entity target;

	public TargetCamera(Entity target) {
		super();
		this.target = target;
	}

	@Override
	public void update() {
		if()
		this.updateViewMatrix();
	}

	public void onRotate(){

	}

}
