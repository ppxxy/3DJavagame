package game.engine.rendering;

public class RenderEngine extends ViewRenderer{
	
	private RenderEngine(){
	}
	
	public void update(){
		DisplayManager.updateDisplay();
	}
	
	public static RenderEngine init(){
		DisplayManager.createDisplay();
		
		return new RenderEngine();
	}
}
