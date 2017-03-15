package game.engine.main;

import java.awt.Canvas;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import Networking.Chat;
import game.engine.animation.Animation;
import game.engine.camera.Camera;
import game.engine.camera.TargetCamera;
import game.engine.characters.Character;
import game.engine.connection.Connection;
import game.engine.interfaces.ChatControls;
import game.engine.interfaces.Inventory;
import game.engine.interfaces.InventoryInterface;
import game.engine.models.collada.ModelLoader;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.RenderEngine;
import game.engine.rendering.View;
import game.engine.textures.Texture;
import login.Window;

public class Main {

	public static final Connection connection = new Connection("127.0.0.1", 16304);

	public static View activeView;

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", new File("src/lib/jars/natives-win").getAbsolutePath());

		final JFrame frame = new JFrame();

		Window login = new Window(frame, "Log In"){
			public void onLogin(){
				startLWJGL(frame);
			}
		};
	}

	private static void startLWJGL(JFrame frame){

		Canvas canvas = new Canvas();
		frame.add(canvas);

		RenderEngine renderEngine = RenderEngine.init(canvas);

		String modelFile = "/res/model.dae";
		String textureFile = "/res/diffuse.png";
		game.engine.characters.Character player = Character.loadCharacter(modelFile, textureFile);
		Animation animation = ModelLoader.loadColladaAnimation(modelFile);
		player.getModel().doAnimation(animation);
		Camera camera = new TargetCamera(player, 100f);

		View view = new View(camera);
		view.addEntity(player);
		activeView = view;

		InventoryInterface testi = new InventoryInterface(Texture.loadTexture("/res/bag.png").load(), new Vector2f(-0.70f, 0.5f), new Vector2f(0.05f, 0.1f));
		view.addInterface(testi);

		//inventory interface
		try {
			Inventory inventory = new Inventory("/res/inventory.jpg", 0.70f, 0.0f, 0.25f, 0.35f);
			view.addInterface(inventory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        /*BufferedImage bagButton = null;
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
		view.addInterface(laukku);*/



		/*BufferedImage image = new BufferedImage(11, 11, BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = image.getGraphics();
		g.setColor(new Color(0, 255, 0, 255));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		view.addInterface(new Interface(Texture.loadTexture(image).nearestFiltering().load(), new Vector2f(0.5f, 0.5f), new Vector2f(0.2f, 0.2f)));*/
		Chat chat=new Chat();
		connection.setChat(chat);
		view.addInterface(chat.getChatbox().getInterface());
		view.addInterface(chat.getMessageBox().getInterface());
		ChatControls chatcontrols= new ChatControls(chat.getMessageBox(),chat.getChatbox());

		while(!Display.isCloseRequested()){
			view.updateEntities();
			renderEngine.renderView(view);
			DisplayManager.updateDisplay();
			chat.getChatbox().update();
			chat.getMessageBox().update();
			chatcontrols.poll();
		}

		view.cleanUp();
		renderEngine.cleanUp();
		DisplayManager.closeDiplay();
	}

}
