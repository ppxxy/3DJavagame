package game.engine.rendering;

import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class RenderEngine extends ViewRenderer{

	private RenderEngine(){
	}

	public void update(){
		DisplayManager.updateDisplay();
	}

	public static RenderEngine init(Canvas canvas){
		DisplayManager.createDisplay();
		try {
			Display.setParent(canvas);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new RenderEngine();
	}
}
