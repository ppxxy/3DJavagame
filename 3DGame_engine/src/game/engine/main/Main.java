package game.engine.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.animation.Animation;
import game.engine.characters.Character;
import game.engine.connection.Connection;
import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.entities.Movement;
import game.engine.interfaces.Interface;
import game.engine.models.collada.ModelLoader;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.RenderEngine;
import game.engine.rendering.View;
import game.engine.textures.Texture;

public class Main {

	public static final Connection connection = new Connection("127.0.0.1", 16304);

	public static View activeView;

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", new File("src/lib/jars/natives-win").getAbsolutePath());

		/*
		 * Login screen here!!
		 */

		RenderEngine renderEngine = RenderEngine.init();

		View view = new View();
		activeView = view;
		String modelFile = "/res/model.dae";
		String textureFile = "/res/diffuse.png";
		game.engine.characters.Character player = Character.loadCharacter(modelFile, textureFile);
		Animation animation = ModelLoader.loadColladaAnimation(modelFile);
		player.getModel().doAnimation(animation);
		view.addEntity(player);

		Interface testi = new Interface(Texture.loadTexture("/res/bag.png").load(), new Vector2f(-0.70f, 0.5f), new Vector2f(0.05f, 0.1f));
		view.addInterface(testi);

		//inventory interface
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:/Users/Tomi/git/3DJavaGame/3DGame_engine/src/res/inventory.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(img, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        Interface inv = new Interface(Texture.loadTexture(image).load(), new Vector2f(0.70f, 0.0f), new Vector2f(0.25f, 0.35f));
        view.addInterface(inv);

        BufferedImage bagButton = null;
		try {
			bagButton = ImageIO.read(new File("C:/Users/Tomi/git/3DJavaGame/3DGame_engine/src/res/bag.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage laukkuKuva = new BufferedImage(bagButton.getWidth(), bagButton.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = laukkuKuva.getGraphics();
		g2.drawImage(bagButton, 0, 0, laukkuKuva.getWidth(), laukkuKuva.getHeight(), null);
		g.dispose();
		Interface laukku = new Interface(Texture.loadTexture(bagButton).load(), new Vector2f(0.0f, -0.3f), new Vector2f(0.05f, 0.1f));
		view.addInterface(laukku);



		/*BufferedImage image = new BufferedImage(11, 11, BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = image.getGraphics();
		g.setColor(new Color(0, 255, 0, 255));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		view.addInterface(new Interface(Texture.loadTexture(image).nearestFiltering().load(), new Vector2f(0.5f, 0.5f), new Vector2f(0.2f, 0.2f)));*/

		while(!Display.isCloseRequested()){
			view.updateEntities();
			renderEngine.renderView(view);
			DisplayManager.updateDisplay();
		}

		view.cleanUp();
		renderEngine.cleanUp();
		DisplayManager.closeDiplay();
	}


}
