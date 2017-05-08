package game.engine.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.engine.interfaces.Interface;
import game.engine.main.View;

public class InterfaceView extends View {

	private final List<Interface> interfaces = new ArrayList<Interface>();

	public InterfaceView(Interface... inters) {
		interfaces.addAll(Arrays.asList(inters));
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		super.update();
	}

	public List<Interface> getInterfaces() {
		return this.interfaces;
	}

}
