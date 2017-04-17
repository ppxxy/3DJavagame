package game.engine.main;

import java.util.ArrayList;
import java.util.List;

import game.engine.camera.Camera;
import game.engine.characters.Update;

public abstract class View {

	protected Camera camera;
	private List<Update> updates = new ArrayList<Update>();

	public abstract void cleanUp();

	public void update(){
		for(Update update : updates){
			update.update();
		}
		updates.clear();
	}

	public Camera getCamera(){
		return this.camera;
	}

	public void addUpdate(Update update) {
		updates.add(update);
	}

}
