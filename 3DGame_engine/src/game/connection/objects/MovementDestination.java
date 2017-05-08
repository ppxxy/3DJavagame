package game.connection.objects;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector2f;
import game.engine.main.Main;
import game.engine.rendering.GameView;

public class MovementDestination implements Serializable, ReceiveAction{
	private static final long serialVersionUID = 4638471368151337603L;

	private final int id;
	private final long startTime;
	private final Vector2f destination;

	public MovementDestination(int id, long time, Vector2f destination){
		this.id = id;
		this.startTime = time;
		this.destination = destination;
	}

	public Vector2f getDestination() {
		return destination;
	}

	public int getId(){
		return this.id;
	}

	@Override
	public void unpack() {
		game.engine.characters.Character entity = (game.engine.characters.Character) Main.getGameView().getEntityById(id);
		entity.setMoveTo(startTime, this.getDestination(), 2f);
	}

	public static class MovementTo implements Serializable{
		private static final long serialVersionUID = 8514737503753570478L;

		private final Vector2f destination;

		public MovementTo(Vector2f destination){
			this.destination = destination;
		}

		public Vector2f getDestination() {
			return this.destination;
		}
	}

}
