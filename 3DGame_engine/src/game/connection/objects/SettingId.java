package game.connection.objects;

import java.io.Serializable;

import game.engine.characters.Update;
import game.engine.main.Main;

public class SettingId implements Serializable, ReceiveAction{
	private static final long serialVersionUID = 210858302231648899L;

	private int id;

	public SettingId(int id){
		this.id = id;
	}

	public int getId(){
		return this.id;
	}

	@Override
	public void unpack() {
		Main.activeView.addUpdate(new Update(){

			@Override
			public void update() {
				Main.setPlayer(id);
			}
		});
	}
}
