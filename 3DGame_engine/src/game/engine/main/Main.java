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
import game.minigames.FXHandler;

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
						FXHandler.runFX(Whiteboard.class);
					}
				};
				t.start();
				return true;
			}

		});

		TexturedModel tree_model1 = new TexturedModel(new OBJLoader("/res/tree1.obj").loadModel(), Texture.loadTexture("/res/Walnut_L.png").load());
		ObjectEntity tree1 = new ObjectEntity(tree_model1, new Vector3f(40, 20, 40), 0, 0, 0, 60);

		view.addEntity(taulu);
		view.addEntity(tree1);

		activeView = view;

		connection.send(RequestData.REQUEST_PLAYERS);

		connection.setChat(chat);
		ChatControls chatcontrols = new ChatControls(chat.getMessageBox(),chat.getChatbox());
		view.addInterface(chat.getChatbox());
		view.addInterface(chat.getMessageBox());
		chatcontrols.start();
		Localization.setNewLocale("ru", "RU");
	}
	public static GameView getGameView() {
		return (GameView) activeView;
	}

}