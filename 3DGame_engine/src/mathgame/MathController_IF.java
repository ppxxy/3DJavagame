package mathgame;

import java.util.ArrayList;

public interface MathController_IF {

	public abstract ArrayList<Integer> startTest();
	public abstract boolean checkAnswer(String answer);
	public abstract int count();

}
