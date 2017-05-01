package mathgame;

import java.util.ArrayList;

public class MathController {
	MathGUI gui;
	MathGame game;

	public MathController(MathGUI gui, MathGame game){
		this.gui = gui;
		this.game = game;
	}

	public ArrayList<Integer> startTest(){
		return game.getTasks(gui.getSelectedValue());
	}

	public boolean checkAnswer(String answer){
		return game.giveAnswer(gui.getValue1(), gui.getValue2(), answer);
	}
}
