package mathgame;

import java.util.ArrayList;

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
