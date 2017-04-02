package game.engine.characters;

import org.lwjgl.util.vector.Vector3f;

import game.engine.animation.Animation;
import game.engine.entities.Entity;
import game.engine.models.collada.ModelLoader;

public class PlayerFactory {

	private static final String modelFile = "/res/model.dae";
	private static final String textureFile = "/res/diffuse.png";

	public static game.engine.characters.Character createPlayer(int id){
		game.engine.characters.Character player = Character.loadCharacter(id, modelFile, textureFile);
		Animation animation = ModelLoader.loadColladaAnimation(modelFile);
		player.getModel().doAnimation(animation);
		return player;
	}

	public static Entity createPlayer(int id, Vector3f location) {
		game.engine.characters.Character player = createPlayer(id);
		player.setPosition(location);
		return player;
	}

}
