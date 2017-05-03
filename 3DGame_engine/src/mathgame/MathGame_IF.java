package mathgame;

import java.util.ArrayList;

public interface MathGame_IF {

	public abstract ArrayList<Integer> getTasks(String selectedValue);
	public abstract int giveRandom();
	public abstract boolean giveAnswer(int a, int b, String answer);
	public abstract int count(int a, int b);

}
