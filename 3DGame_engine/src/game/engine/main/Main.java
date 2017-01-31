package game.engine.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import game.engine.animation.Animation;
import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.interfaces.Interface;
import game.engine.models.collada.ModelLoader;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.RenderEngine;
import game.engine.rendering.View;
import game.engine.textures.Texture;

public class Main {

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", new File("src/lib/jars/natives-win").getAbsolutePath());

		/*
		 * Login screen here!!
		 */

		RenderEngine renderEngine = RenderEngine.init();

		Camera camera = new Camera();
		View view = new View(camera);
		String modelFile = "/res/model.dae";
		String textureFile = "/res/diffuse.png";
		AnimatedEntity e = AnimatedEntity.loadEntity(modelFile, textureFile);
		Animation animation = ModelLoader.loadColladaAnimation(modelFile);
		e.getModel().doAnimation(animation);
		view.addEntity(e);
		BufferedImage image = new BufferedImage(11, 11, BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = image.getGraphics();
		g.setColor(new Color(0, 255, 0, 255));
		g.fillOval(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		view.addInterface(new Interface(Texture.loadTexture(image).nearestFiltering().load(), new Vector2f(0.5f, 0.5f), new Vector2f(0.2f, 0.2f)));

		while(!Display.isCloseRequested()){
			camera.move();
			view.updateEntities();
			renderEngine.renderView(view);
			DisplayManager.updateDisplay();
		}

		renderEngine.cleanUp();
		DisplayManager.closeDiplay();
	}

}
