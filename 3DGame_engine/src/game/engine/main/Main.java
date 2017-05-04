package game.engine.main;

import java.io.File;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Localization.Localization;
import Networking.Chat;
import game.connection.objects.RequestData;
import game.engine.camera.Camera;
import game.engine.camera.TargetCamera;
import game.engine.characters.PlayerFactory;
import game.engine.connection.Connection;
import game.engine.entities.ObjectActivity;
import game.engine.entities.ObjectActivityHandler;
import game.engine.entities.ObjectEntity;
import game.engine.interfaces.ChatControls;
import game.engine.interfaces.Interface;
import game.engine.interfaces.Whiteboard;
import game.engine.models.TexturedModel;
import game.engine.models.obj.OBJLoader;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.GameView;
import game.engine.rendering.RenderEngine;
import game.engine.textures.Texture;
import game.engine.view.InterfaceView;

public class Main {

	public static Connection connection;

	public static View activeView;

	public static Chat chat;

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", new File("src/lib/jars/natives-win").getAbsolutePath());

		RenderEngine renderEngine = RenderEngine.init();
		chat=new Chat();
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
			chat.getChatbox().update();
			chat.getMessageBox().update();
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

		TexturedModel taulu_model = new TexturedModel(new OBJLoader("/res/whiteboard.obj").loadModel(), Texture.loadTexture("/res/taulu.png").load());
		ObjectEntity taulu = new ObjectEntity(taulu_model, new Vector3f(0, 15, -3), 0, 0, 0, 1);
		ObjectActivityHandler.addActivity(taulu_model.getId(), new ObjectActivity(){

			@Override
			public boolean activate() {
				Thread t = new Thread(){
					@Override
					public void run(){
						System.out.println("thread start");
						Whiteboard.launch(Whiteboard.class);
					}
				};
				t.start();
				return true;
			}

		});

		view.addEntity(taulu);

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
		connection.setChat(chat);
		ChatControls chatcontrols = new ChatControls(chat.getMessageBox(),chat.getChatbox());
		view.addInterface(chat.getChatbox());
		view.addInterface(chat.getMessageBox());
		chatcontrols.start();
		Localization.setNewLocale("fi", "FI");
	}
	public static GameView getGameView() {
		return (GameView) activeView;
	}

}