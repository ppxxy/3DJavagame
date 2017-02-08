package game.engine.main;

import java.io.File;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import game.engine.animation.Animation;
import game.engine.characters.Character;
import game.engine.connection.Connection;
import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.entities.Movement;
import game.engine.models.collada.ModelLoader;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.RenderEngine;
import game.engine.rendering.View;

public class Main {

	public static final Connection connection = new Connection("127.0.0.1", 16304);

	public static View activeView;

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", new File("src/lib/jars/natives-win").getAbsolutePath());

		/*
		 * Login screen here!!
		 */

		RenderEngine renderEngine = RenderEngine.init();

		Camera camera = new Camera();
		View view = new View(camera);
		activeView = view;
		String modelFile = "/res/model.dae";
		String textureFile = "/res/diffuse.png";
		game.engine.characters.Character player = Character.loadCharacter(modelFile, textureFile);
		Animation animation = ModelLoader.loadColladaAnimation(modelFile);
		player.getModel().doAnimation(animation);
		view.addEntity(player);

		/*BufferedImage image = new BufferedImage(11, 11, BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = image.getGraphics();
		g.setColor(new Color(0, 255, 0, 255));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		view.addInterface(new Interface(Texture.loadTexture(image).nearestFiltering().load(), new Vector2f(0.5f, 0.5f), new Vector2f(0.2f, 0.2f)));*/

		while(!Display.isCloseRequested()){
			camera.move();

			view.updateEntities();
			renderEngine.renderView(view);
			DisplayManager.updateDisplay();
		}

		view.cleanUp();
		renderEngine.cleanUp();
		DisplayManager.closeDiplay();
	}

}
