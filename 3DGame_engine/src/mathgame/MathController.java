package mathgame;

import java.util.ArrayList;

/**
* <h1>MathController</h1>
* The MathController class works as a mediator between the MathGUI and MathGame classes
* <p>

* @author  Tomi Lehto
* @version 1.0
*/

public class MathController implements MathController_IF{
	MathGUI_IF gui;
	MathGame_IF game;

	public MathController(MathGUI_IF gui, MathGame_IF game){
		this.gui = gui;
		this.game = game;
	}

	@Override
	public ArrayList<Integer> startTest(){
		return game.getTasks(gui.getSelectedValue());
	}

	@Override
	public boolean checkAnswer(String answer){
		return game.giveAnswer(gui.getValue1(), gui.getValue2(), answer);
	}

	public int count(){
		return game.count(gui.getValue1(), gui.getValue2());
	}
}
