package game.engine.rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int WIDTH = 1280, HEIGHT = 720, FPS_CAP = 120;
	private static final String TITLE = "3D Game Engine";

	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay(){

		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}

	public static void updateDisplay(){

		Display.sync(FPS_CAP);
		Display.update();
		long currentTime = getCurrentTime();
		delta = (currentTime - lastFrameTime) / 1000f;
		lastFrameTime = currentTime;
	}

	public static void closeDiplay(){

		Display.destroy();
	}

	public static float getFrameTime() {
		return delta;
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

}
