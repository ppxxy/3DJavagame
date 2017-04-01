package game.connection.objects;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

import game.engine.characters.PlayerFactory;
import game.engine.characters.Update;
import game.engine.main.Main;

public class SpawnPlayer implements Serializable, ReceiveAction{
	private static final long serialVersionUID = 5265020465088082700L;

	private int id;
	private Vector3f location;

	public SpawnPlayer(int id, Vector3f location){
		this.id = id;
		this.location = location;
	}

	@Override
	public void unpack() {
		Main.activeView.addUpdate(new Update(){

			@Override
			public void update() {
				Main.activeView.addEntity(PlayerFactory.createPlayer(id, location));
			}
		});
	}
}
