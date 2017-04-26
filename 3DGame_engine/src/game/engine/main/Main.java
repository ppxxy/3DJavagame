package game.engine.main;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import Localization.Localization;
import Networking.Chat;
import game.connection.objects.RequestData;
import game.engine.camera.Camera;
import game.engine.camera.TargetCamera;
import game.engine.characters.PlayerFactory;
import game.engine.connection.Connection;
import game.engine.interfaces.ChatControls;
import game.engine.interfaces.Interface;
import game.engine.interfaces.Inventory;
import game.engine.interfaces.InventoryInterface;
import game.engine.interfaces.Whiteboard;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.GameView;
import game.engine.rendering.RenderEngine;
import game.engine.textures.Texture;
import game.engine.view.InterfaceView;
import javafx.stage.Stage;

public class Main {

	public static Connection connection;

	public static View activeView;

	public Main(){

	}

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", new File("src/lib/jars/natives-win").getAbsolutePath());

		RenderEngine renderEngine = RenderEngine.init();

		Interface logging = new Interface(Texture.loadTexture("/res/logging.png").load(), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
		activeView = new InterfaceView(logging);

		connection = new Connection("127.0.0.1", 16304);

		/*
		 * Login screen here!!
		 */

		while(!Display.isCloseRequested()){
			activeView.update();
			renderEngine.renderView(activeView);
			DisplayManager.updateDisplay();
		}

		activeView.cleanUp();
		renderEngine.cleanUp();
		DisplayManager.closeDiplay();
	}

	public static void setPlayer(int id){
		System.out.println("Setting player.");
		game.engine.characters.Character player = PlayerFactory.createPlayer(id);
		Camera camera = new TargetCamera(player, 100f);

		GameView view = new GameView(camera);
		view.addEntity(player);
		activeView = view;

		connection.send(RequestData.REQUEST_PLAYERS);

		/*InventoryInterface testi = new InventoryInterface(Texture.loadTexture("/res/bag.png").load(), new Vector2f(-0.70f, 0.5f), new Vector2f(0.05f, 0.1f));
		view.addInterface(testi);

		//inventory interface
		try {
			Inventory inventory = new Inventory("/res/inventory.jpg", 0.70f, 0.0f, 0.25f, 0.35f);
			view.addInterface(inventory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


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
		ChatControls chatcontrols = new ChatControls(chat.getMessageBox(),chat.getChatbox());

		Localization.setNewLocale("fi", "FI");

		Thread t = new Thread(){
			@Override
			public void run(){
				Whiteboard.launch(Whiteboard.class);
			}
		};
		t.start();
	}

	public static GameView getGameView() {
		return (GameView) activeView;
	}

}