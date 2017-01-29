package game.engine.main;

import java.io.File;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.animation.Animation;
import game.engine.entities.AnimatedEntity;
import game.engine.entities.Camera;
import game.engine.entities.Entity;
import game.engine.entities.StableEntity;
import game.engine.interfaces.Interface;
import game.engine.models.RawModel;
import game.engine.models.TexturedModel;
import game.engine.models.collada.AnimationData;
import game.engine.models.collada.AnimationData.AnimationLoader;
import game.engine.models.collada.ModelLoader;
import game.engine.rendering.AnimatedModelRenderer;
import game.engine.rendering.DisplayManager;
import game.engine.rendering.Loader;
import game.engine.rendering.RenderEngine;
import game.engine.rendering.Renderer;
import game.engine.rendering.View;
import game.engine.shader.StaticShader;
import game.engine.textures.ModelTexture;
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
		e.getModel().doAnimation(animation);;
		view.addEntity(e);
		view.addInterface(new Interface(Texture.loadTexture("/res/icon.png").load(), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f)));

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
