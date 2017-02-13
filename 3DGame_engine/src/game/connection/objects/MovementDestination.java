package game.connection.objects;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

import game.engine.entities.Movement;
import game.engine.main.Main;

public class MovementDestination implements Serializable, ReceiveAction{
	private static final long serialVersionUID = 4638471368151337603L;

	private final int id;
	private final Vector3f destination;

	public MovementDestination(int id, Vector3f destination){
		this.id = id;
		this.destination = destination;
	}

	public Vector3f getDestination() {
		return destination;
	}

	public int getId(){
		return this.id;
	}

	@Override
	public void unpack() {
		game.engine.characters.Character entity = (game.engine.characters.Character) Main.activeView.getAnimatedEntities().get(0);
		entity.setMovement(new Movement(entity, 60f, this.getDestination()));
	}

	public static class MovementTo implements Serializable{
		private static final long serialVersionUID = 8514737503753570478L;

		private final Vector3f destination;

		public MovementTo(Vector3f destination){
			this.destination = destination;
		}

		public Vector3f getDestination() {
			return this.destination;
		}
	}

}
