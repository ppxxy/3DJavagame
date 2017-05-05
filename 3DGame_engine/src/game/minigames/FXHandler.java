package game.minigames;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class FXHandler {

	private static FXHandler handler;
	
	private boolean started;
	
	private FXHandler(){
		Platform.setImplicitExit(false);
		this.started = false;
	}
	
	public static FXHandler getInstance(){
		if(handler == null){
			handler = new FXHandler();
		}
		return handler;
	}
	
	public static void runFX(Class<? extends Application> luokka){
		if(!getInstance().started){
			getInstance().started = true;
			Application.launch(luokka);
		}
		else{
			//System.out.println("Trying to launch second time.");
			Platform.runLater(new Runnable(){
				@Override
				public void run(){
					try {
						luokka.newInstance().start(new Stage());
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}
	
}
